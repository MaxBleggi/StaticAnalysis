package com.google.android.libraries.cast.companionlibrary.cast.player;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumerImpl;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.CastException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.google.android.libraries.cast.companionlibrary.utils.FetchBitmapTask;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

public class VideoCastControllerFragment extends Fragment implements OnVideoCastControllerListener, MediaAuthListener {
    private static final String EXTRAS = "extras";
    private static final String TAG = LogUtils.makeLogTag(VideoCastControllerFragment.class);
    private static boolean sDialogCanceled = false;
    protected boolean mAuthSuccess = true;
    private Thread mAuthThread;
    private MyCastConsumer mCastConsumer;
    private VideoCastController mCastController;
    private VideoCastManager mCastManager;
    private Handler mHandler;
    private FetchBitmapTask mImageAsyncTask;
    private boolean mIsFresh = true;
    private MediaAuthService mMediaAuthService;
    private Timer mMediaAuthTimer;
    private MediaStatus mMediaStatus;
    private OverallState mOverallState = OverallState.UNKNOWN;
    private int mPlaybackState;
    private Timer mSeekbarTimer;
    private MediaInfo mSelectedMedia;
    private UrlAndBitmap mUrlAndBitmap;

    class MediaAuthServiceTimerTask extends TimerTask {
        private final Thread mThread;

        public MediaAuthServiceTimerTask(Thread thread) {
            this.mThread = thread;
        }

        public void run() {
            if (this.mThread != null) {
                LogUtils.LOGD(VideoCastControllerFragment.TAG, "Timer is expired, going to interrupt the thread");
                this.mThread.interrupt();
                VideoCastControllerFragment.this.mHandler.post(new Runnable() {
                    public void run() {
                        VideoCastControllerFragment.this.mCastController.showLoading(false);
                        VideoCastControllerFragment.this.showErrorDialog(VideoCastControllerFragment.this.getString(R.string.ccl_failed_authorization_timeout));
                        VideoCastControllerFragment.this.mAuthSuccess = false;
                        if (VideoCastControllerFragment.this.mMediaAuthService != null && VideoCastControllerFragment.this.mMediaAuthService.getStatus() == MediaAuthStatus.PENDING) {
                            VideoCastControllerFragment.this.mMediaAuthService.abortAuthorization(MediaAuthStatus.TIMED_OUT);
                        }
                    }
                });
            }
        }
    }

    private enum OverallState {
        AUTHORIZING,
        PLAYBACK,
        UNKNOWN
    }

    private class UpdateSeekbarTask extends TimerTask {
        private UpdateSeekbarTask() {
        }

        public void run() {
            VideoCastControllerFragment.this.mHandler.post(new Runnable() {
                public void run() {
                    if (VideoCastControllerFragment.this.mPlaybackState != 4 && VideoCastControllerFragment.this.mCastManager.isConnected()) {
                        try {
                            int mediaDuration = (int) VideoCastControllerFragment.this.mCastManager.getMediaDuration();
                            if (mediaDuration > 0) {
                                try {
                                    VideoCastControllerFragment.this.mCastController.updateSeekbar((int) VideoCastControllerFragment.this.mCastManager.getCurrentMediaPosition(), mediaDuration);
                                } catch (Throwable e) {
                                    LogUtils.LOGE(VideoCastControllerFragment.TAG, "Failed to get current media position", e);
                                }
                            }
                        } catch (Throwable e2) {
                            LogUtils.LOGE(VideoCastControllerFragment.TAG, "Failed to update the progress bar due to network issues", e2);
                        }
                    }
                }
            });
        }
    }

    private class UrlAndBitmap {
        private Bitmap mBitmap;
        private Uri mUrl;

        private UrlAndBitmap() {
        }

        private boolean isMatch(Uri uri) {
            return (uri == null || this.mBitmap == null || uri.equals(this.mUrl) == null) ? null : true;
        }
    }

    public static class ErrorDialogFragment extends DialogFragment {
        private static final String MESSAGE = "message";
        private VideoCastController mController;

        public static ErrorDialogFragment newInstance(String str) {
            ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("message", str);
            errorDialogFragment.setArguments(bundle);
            return errorDialogFragment;
        }

        public void onAttach(Activity activity) {
            this.mController = (VideoCastController) activity;
            super.onAttach(activity);
            setCancelable(null);
        }

        @NonNull
        public Dialog onCreateDialog(Bundle bundle) {
            return new Builder(getActivity()).setTitle(R.string.ccl_error).setMessage(getArguments().getString("message")).setPositiveButton(R.string.ccl_ok, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    VideoCastControllerFragment.sDialogCanceled = true;
                    ErrorDialogFragment.this.mController.closeActivity();
                }
            }).create();
        }
    }

    private class MyCastConsumer extends VideoCastConsumerImpl {
        private MyCastConsumer() {
        }

        public void onDisconnected() {
            VideoCastControllerFragment.this.mCastController.closeActivity();
        }

        public void onApplicationDisconnected(int i) {
            VideoCastControllerFragment.this.mCastController.closeActivity();
        }

        public void onRemoteMediaPlayerMetadataUpdated() {
            try {
                VideoCastControllerFragment.this.mSelectedMedia = VideoCastControllerFragment.this.mCastManager.getRemoteMediaInformation();
                VideoCastControllerFragment.this.updateClosedCaptionState();
                VideoCastControllerFragment.this.updateMetadata();
            } catch (Throwable e) {
                LogUtils.LOGE(VideoCastControllerFragment.TAG, "Failed to update the metadata due to network issues", e);
            }
        }

        public void onMediaLoadResult(int i) {
            if (i != 0) {
                String access$100 = VideoCastControllerFragment.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onMediaLoadResult(): Failed to load media with status code: ");
                stringBuilder.append(i);
                LogUtils.LOGD(access$100, stringBuilder.toString());
                Utils.showToast(VideoCastControllerFragment.this.getActivity(), R.string.ccl_failed_to_load_media);
                VideoCastControllerFragment.this.mCastController.closeActivity();
            }
        }

        public void onFailed(int i, int i2) {
            String access$100 = VideoCastControllerFragment.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onFailed(): ");
            stringBuilder.append(VideoCastControllerFragment.this.getString(i));
            stringBuilder.append(", status code: ");
            stringBuilder.append(i2);
            LogUtils.LOGD(access$100, stringBuilder.toString());
            if (i2 == 2100 || i2 == RemoteMediaPlayer.STATUS_TIMED_OUT) {
                Utils.showToast(VideoCastControllerFragment.this.getActivity(), i);
                VideoCastControllerFragment.this.mCastController.closeActivity();
            }
        }

        public void onRemoteMediaPlayerStatusUpdated() {
            VideoCastControllerFragment.this.updatePlayerStatus();
        }

        public void onMediaQueueUpdated(List<MediaQueueItem> list, MediaQueueItem mediaQueueItem, int i, boolean z) {
            i = 0;
            if (list != null) {
                i = list.size();
                list = list.indexOf(mediaQueueItem);
            } else {
                list = null;
            }
            VideoCastControllerFragment.this.mCastController.onQueueItemsUpdated(i, list);
        }

        public void onConnectionSuspended(int i) {
            VideoCastControllerFragment.this.mCastController.updateControllersStatus(false);
        }

        public void onConnectivityRecovered() {
            VideoCastControllerFragment.this.mCastController.updateControllersStatus(true);
        }
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sDialogCanceled = false;
        this.mCastController = (VideoCastController) activity;
        this.mHandler = new Handler();
        this.mCastManager = VideoCastManager.getInstance();
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        JSONObject jSONObject = null;
        this.mCastConsumer = new MyCastConsumer();
        bundle = getArguments();
        if (bundle != null) {
            bundle = bundle.getBundle(EXTRAS);
            Bundle bundle2 = bundle.getBundle(VideoCastManager.EXTRA_MEDIA);
            boolean z = true;
            setRetainInstance(true);
            this.mCastManager.addTracksSelectedListener(this);
            boolean booleanFromPreference = this.mCastManager.getPreferenceAccessor().getBooleanFromPreference(VideoCastManager.PREFS_KEY_START_ACTIVITY, false);
            if (booleanFromPreference) {
                this.mIsFresh = true;
            }
            this.mCastManager.getPreferenceAccessor().saveBooleanToPreference(VideoCastManager.PREFS_KEY_START_ACTIVITY, Boolean.valueOf(false));
            this.mCastController.setNextPreviousVisibilityPolicy(this.mCastManager.getCastConfiguration().getNextPrevVisibilityPolicy());
            if (bundle.getBoolean(VideoCastManager.EXTRA_HAS_AUTH)) {
                if (this.mIsFresh != null) {
                    this.mOverallState = OverallState.AUTHORIZING;
                    this.mMediaAuthService = this.mCastManager.getMediaAuthService();
                    handleMediaAuthTask(this.mMediaAuthService);
                    showImage(Utils.getImageUri(this.mMediaAuthService.getMediaInfo(), 1));
                }
            } else if (bundle2 != null) {
                this.mOverallState = OverallState.PLAYBACK;
                boolean z2 = bundle.getBoolean(VideoCastManager.EXTRA_SHOULD_START);
                String string = bundle.getString(VideoCastManager.EXTRA_CUSTOM_DATA);
                if (!TextUtils.isEmpty(string)) {
                    try {
                        jSONObject = new JSONObject(string);
                    } catch (Throwable e) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Failed to unmarshalize custom data string: customData=");
                        stringBuilder.append(string);
                        LogUtils.LOGE(str, stringBuilder.toString(), e);
                    }
                }
                MediaInfo bundleToMediaInfo = Utils.bundleToMediaInfo(bundle2);
                bundle = bundle.getInt(VideoCastManager.EXTRA_START_POINT, 0);
                if (!z2 || !booleanFromPreference) {
                    z = false;
                }
                onReady(bundleToMediaInfo, z, bundle, jSONObject);
            }
        }
    }

    private void handleMediaAuthTask(final MediaAuthService mediaAuthService) {
        this.mCastController.showLoading(true);
        if (mediaAuthService != null) {
            this.mCastController.setSubTitle(mediaAuthService.getPendingMessage() != null ? mediaAuthService.getPendingMessage() : "");
            this.mAuthThread = new Thread(new Runnable() {
                public void run() {
                    mediaAuthService.setMediaAuthListener(VideoCastControllerFragment.this);
                    mediaAuthService.startAuthorization();
                }
            });
            this.mAuthThread.start();
            this.mMediaAuthTimer = new Timer();
            this.mMediaAuthTimer.schedule(new MediaAuthServiceTimerTask(this.mAuthThread), mediaAuthService.getTimeout());
        }
    }

    private void onReady(MediaInfo mediaInfo, boolean z, int i, JSONObject jSONObject) {
        this.mSelectedMedia = mediaInfo;
        updateClosedCaptionState();
        try {
            this.mCastController.setStreamType(this.mSelectedMedia.getStreamType());
            if (z) {
                this.mPlaybackState = 4;
                this.mCastController.setPlaybackStatus(this.mPlaybackState);
                this.mCastManager.loadMedia(this.mSelectedMedia, true, i, jSONObject);
            } else {
                if (this.mCastManager.isRemoteMediaPlaying() != null) {
                    this.mPlaybackState = 2;
                } else {
                    this.mPlaybackState = 3;
                }
                this.mCastController.setPlaybackStatus(this.mPlaybackState);
            }
        } catch (MediaInfo mediaInfo2) {
            LogUtils.LOGE(TAG, "Failed to get playback and media information", mediaInfo2);
            this.mCastController.closeActivity();
        }
        mediaInfo2 = this.mCastManager.getMediaQueue();
        z = false;
        if (mediaInfo2 != null) {
            z = mediaInfo2.getCount();
            mediaInfo2 = mediaInfo2.getCurrentItemPosition();
        } else {
            mediaInfo2 = null;
        }
        this.mCastController.onQueueItemsUpdated(z, mediaInfo2);
        updateMetadata();
        restartTrickplayTimer();
    }

    private void updateClosedCaptionState() {
        int i = (this.mCastManager.isFeatureEnabled(16) && this.mSelectedMedia != null && this.mCastManager.getTracksPreferenceManager().isCaptionEnabled()) ? Utils.hasAudioOrTextTrack(this.mSelectedMedia.getMediaTracks()) ? 1 : 2 : 3;
        this.mCastController.setClosedCaptionState(i);
    }

    private void stopTrickplayTimer() {
        LogUtils.LOGD(TAG, "Stopped TrickPlay Timer");
        if (this.mSeekbarTimer != null) {
            this.mSeekbarTimer.cancel();
        }
    }

    private void restartTrickplayTimer() {
        stopTrickplayTimer();
        this.mSeekbarTimer = new Timer();
        this.mSeekbarTimer.scheduleAtFixedRate(new UpdateSeekbarTask(), 100, 1000);
        LogUtils.LOGD(TAG, "Restarted TrickPlay Timer");
    }

    private void updateOverallState() {
        switch (this.mOverallState) {
            case AUTHORIZING:
                MediaAuthService mediaAuthService = this.mCastManager.getMediaAuthService();
                if (mediaAuthService != null) {
                    this.mCastController.setSubTitle(mediaAuthService.getPendingMessage() != null ? mediaAuthService.getPendingMessage() : "");
                    this.mCastController.showLoading(true);
                    return;
                }
                return;
            case PLAYBACK:
                return;
            default:
                return;
        }
    }

    private void updateMetadata() {
        boolean z = true;
        Uri imageUri = this.mSelectedMedia == null ? this.mMediaAuthService != null ? Utils.getImageUri(this.mMediaAuthService.getMediaInfo(), 1) : null : Utils.getImageUri(this.mSelectedMedia, 1);
        showImage(imageUri);
        if (this.mSelectedMedia != null) {
            MediaMetadata metadata = this.mSelectedMedia.getMetadata();
            this.mCastController.setTitle(metadata.getString(MediaMetadata.KEY_TITLE) != null ? metadata.getString(MediaMetadata.KEY_TITLE) : "");
            if (this.mSelectedMedia.getStreamType() != 2) {
                z = false;
            }
            this.mCastController.adjustControllersForLiveStream(z);
        }
    }

    private void updatePlayerStatus() {
        int playbackStatus = this.mCastManager.getPlaybackStatus();
        this.mMediaStatus = this.mCastManager.getMediaStatus();
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updatePlayerStatus(), state: ");
        stringBuilder.append(playbackStatus);
        LogUtils.LOGD(str, stringBuilder.toString());
        if (this.mSelectedMedia != null) {
            this.mCastController.setStreamType(this.mSelectedMedia.getStreamType());
            if (playbackStatus == 4) {
                this.mCastController.setSubTitle(getString(R.string.ccl_loading));
            } else {
                this.mCastController.setSubTitle(getString(R.string.ccl_casting_to_device, this.mCastManager.getDeviceName()));
            }
            switch (playbackStatus) {
                case 1:
                    String str2 = TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Idle Reason: ");
                    stringBuilder2.append(this.mCastManager.getIdleReason());
                    LogUtils.LOGD(str2, stringBuilder2.toString());
                    switch (this.mCastManager.getIdleReason()) {
                        case 1:
                            if (!this.mIsFresh && (this.mMediaStatus == null || this.mMediaStatus.getLoadingItemId() == 0)) {
                                this.mCastController.closeActivity();
                                break;
                            }
                        case 2:
                            try {
                                if (this.mCastManager.isRemoteStreamLive()) {
                                    if (this.mPlaybackState != 1) {
                                        this.mPlaybackState = 1;
                                        this.mCastController.setPlaybackStatus(this.mPlaybackState);
                                        break;
                                    }
                                }
                                this.mCastController.closeActivity();
                                break;
                            } catch (Throwable e) {
                                LogUtils.LOGD(TAG, "Failed to determine if stream is live", e);
                                break;
                            }
                            break;
                        case 3:
                            this.mPlaybackState = 1;
                            this.mCastController.setPlaybackStatus(this.mPlaybackState);
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    this.mIsFresh = false;
                    if (this.mPlaybackState != 2) {
                        this.mPlaybackState = 2;
                        this.mCastController.setPlaybackStatus(this.mPlaybackState);
                        break;
                    }
                    break;
                case 3:
                    this.mIsFresh = false;
                    if (this.mPlaybackState != 3) {
                        this.mPlaybackState = 3;
                        this.mCastController.setPlaybackStatus(this.mPlaybackState);
                        break;
                    }
                    break;
                case 4:
                    this.mIsFresh = false;
                    if (this.mPlaybackState != 4) {
                        this.mPlaybackState = 4;
                        this.mCastController.setPlaybackStatus(this.mPlaybackState);
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void onDestroy() {
        LogUtils.LOGD(TAG, "onDestroy()");
        stopTrickplayTimer();
        cleanup();
        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
        try {
            boolean z = false;
            if ((this.mCastManager.isRemoteMediaPaused() || this.mCastManager.isRemoteMediaPlaying()) && this.mCastManager.getRemoteMediaInformation() != null && this.mSelectedMedia != null && TextUtils.equals(this.mSelectedMedia.getContentId(), this.mCastManager.getRemoteMediaInformation().getContentId())) {
                this.mIsFresh = false;
            }
            if (!this.mCastManager.isConnecting()) {
                if (!this.mCastManager.isConnected() || (this.mCastManager.getPlaybackStatus() == 1 && this.mCastManager.getIdleReason() == 1)) {
                    z = true;
                }
                if (z && !this.mIsFresh) {
                    this.mCastController.closeActivity();
                    this.mCastManager.incrementUiCounter();
                    return;
                }
            }
            this.mMediaStatus = this.mCastManager.getMediaStatus();
            this.mCastManager.addVideoCastConsumer(this.mCastConsumer);
            if (!this.mIsFresh) {
                updatePlayerStatus();
                this.mSelectedMedia = this.mCastManager.getRemoteMediaInformation();
                updateClosedCaptionState();
                updateMetadata();
            }
        } catch (Throwable e) {
            LogUtils.LOGE(TAG, "Failed to get media information or status of media playback", e);
        } catch (Throwable th) {
            this.mCastManager.incrementUiCounter();
        }
        this.mCastManager.incrementUiCounter();
    }

    public void onPause() {
        this.mCastManager.removeVideoCastConsumer(this.mCastConsumer);
        this.mCastManager.decrementUiCounter();
        this.mIsFresh = false;
        super.onPause();
    }

    public static VideoCastControllerFragment newInstance(Bundle bundle) {
        VideoCastControllerFragment videoCastControllerFragment = new VideoCastControllerFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putBundle(EXTRAS, bundle);
        videoCastControllerFragment.setArguments(bundle2);
        return videoCastControllerFragment;
    }

    private void showImage(Uri uri) {
        if (uri == null) {
            this.mCastController.setImage(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.album_art_placeholder_large));
        } else if (this.mUrlAndBitmap == null || !this.mUrlAndBitmap.isMatch(uri)) {
            this.mUrlAndBitmap = null;
            if (this.mImageAsyncTask != null) {
                this.mImageAsyncTask.cancel(true);
            }
            Point displaySize = Utils.getDisplaySize(getActivity());
            final Uri uri2 = uri;
            this.mImageAsyncTask = new FetchBitmapTask(displaySize.x, displaySize.y, false) {
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap != null) {
                        VideoCastControllerFragment.this.mUrlAndBitmap = new UrlAndBitmap();
                        VideoCastControllerFragment.this.mUrlAndBitmap.mBitmap = bitmap;
                        VideoCastControllerFragment.this.mUrlAndBitmap.mUrl = uri2;
                        if (!isCancelled()) {
                            VideoCastControllerFragment.this.mCastController.setImage(bitmap);
                        }
                    }
                    if (this == VideoCastControllerFragment.this.mImageAsyncTask) {
                        VideoCastControllerFragment.this.mImageAsyncTask = null;
                    }
                }
            };
            this.mImageAsyncTask.execute(uri);
        } else {
            this.mCastController.setImage(this.mUrlAndBitmap.mBitmap);
        }
    }

    private void showErrorDialog(String str) {
        ErrorDialogFragment.newInstance(str).show(getFragmentManager(), "dlg");
    }

    public void onStop() {
        super.onStop();
        if (this.mImageAsyncTask != null) {
            this.mImageAsyncTask.cancel(true);
            this.mImageAsyncTask = null;
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        try {
            if (this.mPlaybackState == 2) {
                this.mPlaybackState = 4;
                this.mCastController.setPlaybackStatus(this.mPlaybackState);
                this.mCastManager.play(seekBar.getProgress());
            } else if (this.mPlaybackState == 3) {
                this.mCastManager.seek(seekBar.getProgress());
            }
            restartTrickplayTimer();
        } catch (SeekBar seekBar2) {
            LogUtils.LOGE(TAG, "Failed to complete seek", seekBar2);
            this.mCastController.closeActivity();
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        stopTrickplayTimer();
    }

    public void onPlayPauseClicked(View view) throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        view = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isConnected returning: ");
        stringBuilder.append(this.mCastManager.isConnected());
        LogUtils.LOGD(view, stringBuilder.toString());
        togglePlayback();
    }

    private void togglePlayback() throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        switch (this.mPlaybackState) {
            case 1:
                if (this.mSelectedMedia.getStreamType() == 2 && this.mCastManager.getIdleReason() == 2) {
                    this.mCastManager.play();
                } else {
                    this.mCastManager.loadMedia(this.mSelectedMedia, true, 0);
                }
                this.mPlaybackState = 4;
                restartTrickplayTimer();
                break;
            case 2:
                this.mCastManager.pause();
                this.mPlaybackState = 4;
                break;
            case 3:
                this.mCastManager.play();
                this.mPlaybackState = 4;
                restartTrickplayTimer();
                break;
            default:
                break;
        }
        this.mCastController.setPlaybackStatus(this.mPlaybackState);
    }

    public void onConfigurationChanged() {
        updateOverallState();
        if (this.mSelectedMedia != null) {
            updateMetadata();
            updatePlayerStatus();
            this.mCastController.updateControllersStatus(this.mCastManager.isConnected());
        } else if (this.mMediaAuthService != null) {
            showImage(Utils.getImageUri(this.mMediaAuthService.getMediaInfo(), 1));
        }
    }

    public void onAuthResult(MediaAuthStatus mediaAuthStatus, final MediaInfo mediaInfo, final String str, final int i, final JSONObject jSONObject) {
        if (mediaAuthStatus != MediaAuthStatus.AUTHORIZED || this.mAuthSuccess == null) {
            if (this.mMediaAuthTimer != null) {
                this.mMediaAuthTimer.cancel();
            }
            this.mHandler.post(new Runnable() {
                public void run() {
                    VideoCastControllerFragment.this.mOverallState = OverallState.UNKNOWN;
                    VideoCastControllerFragment.this.showErrorDialog(str);
                }
            });
            return;
        }
        this.mMediaAuthService = null;
        if (this.mMediaAuthTimer != null) {
            this.mMediaAuthTimer.cancel();
        }
        this.mSelectedMedia = mediaInfo;
        updateClosedCaptionState();
        this.mHandler.post(new Runnable() {
            public void run() {
                VideoCastControllerFragment.this.mOverallState = OverallState.PLAYBACK;
                VideoCastControllerFragment.this.onReady(mediaInfo, true, i, jSONObject);
            }
        });
    }

    public void onAuthFailure(final String str) {
        if (this.mMediaAuthTimer != null) {
            this.mMediaAuthTimer.cancel();
        }
        this.mHandler.post(new Runnable() {
            public void run() {
                VideoCastControllerFragment.this.mOverallState = OverallState.UNKNOWN;
                VideoCastControllerFragment.this.showErrorDialog(str);
            }
        });
    }

    public void onTracksSelected(List<MediaTrack> list) {
        this.mCastManager.setActiveTracks(list);
    }

    private void cleanup() {
        MediaAuthService mediaAuthService = this.mCastManager.getMediaAuthService();
        if (this.mMediaAuthTimer != null) {
            this.mMediaAuthTimer.cancel();
        }
        if (this.mAuthThread != null) {
            this.mAuthThread = null;
        }
        if (this.mCastManager.getMediaAuthService() != null) {
            mediaAuthService.setMediaAuthListener(null);
            this.mCastManager.removeMediaAuthService();
        }
        if (this.mCastManager != null) {
            this.mCastManager.removeVideoCastConsumer(this.mCastConsumer);
        }
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
        if (this.mUrlAndBitmap != null) {
            this.mUrlAndBitmap.mBitmap = null;
        }
        if (!(sDialogCanceled || this.mMediaAuthService == null)) {
            this.mMediaAuthService.abortAuthorization(MediaAuthStatus.CANCELED_BY_USER);
        }
        this.mCastManager.removeTracksSelectedListener(this);
    }

    public void onSkipNextClicked(View view) throws TransientNetworkDisconnectionException, NoConnectionException {
        this.mCastController.showLoading(true);
        this.mCastManager.queueNext(null);
    }

    public void onSkipPreviousClicked(View view) throws TransientNetworkDisconnectionException, NoConnectionException {
        this.mCastController.showLoading(true);
        this.mCastManager.queuePrev(null);
    }
}
