package com.google.android.libraries.cast.companionlibrary.cast;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompat.Callback;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.media.session.PlaybackStateCompat.Builder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.view.accessibility.CaptioningManager.CaptionStyle;
import android.view.accessibility.CaptioningManager.CaptioningChangeListener;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.mediarouter.media.MediaRouter.RouteInfo;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast.CastOptions;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.cast.RemoteMediaPlayer.OnMetadataUpdatedListener;
import com.google.android.gms.cast.RemoteMediaPlayer.OnPreloadStatusUpdatedListener;
import com.google.android.gms.cast.RemoteMediaPlayer.OnQueueStatusUpdatedListener;
import com.google.android.gms.cast.RemoteMediaPlayer.OnStatusUpdatedListener;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.images.WebImage;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumer;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.CastException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.OnFailedListener;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.player.MediaAuthService;
import com.google.android.libraries.cast.companionlibrary.cast.player.VideoCastControllerActivity;
import com.google.android.libraries.cast.companionlibrary.cast.tracks.OnTracksSelectedListener;
import com.google.android.libraries.cast.companionlibrary.cast.tracks.TracksPreferenceManager;
import com.google.android.libraries.cast.companionlibrary.notification.VideoCastNotificationService;
import com.google.android.libraries.cast.companionlibrary.remotecontrol.VideoIntentReceiver;
import com.google.android.libraries.cast.companionlibrary.utils.FetchBitmapTask;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import com.google.android.libraries.cast.companionlibrary.utils.ResponseCustomData;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;
import com.google.android.libraries.cast.companionlibrary.widgets.IMiniController;
import com.google.android.libraries.cast.companionlibrary.widgets.MiniController.OnMiniControllerChangedListener;
import com.google.android.libraries.cast.companionlibrary.widgets.ProgressWatcher;
import com.xumo.xumo.util.LogUtil;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

public class VideoCastManager extends BaseCastManager implements OnMiniControllerChangedListener, OnFailedListener {
    public static final long DEFAULT_LIVE_STREAM_DURATION_MS = TimeUnit.HOURS.toMillis(2);
    public static final Class<?> DEFAULT_TARGET_ACTIVITY = VideoCastControllerActivity.class;
    public static final double DEFAULT_VOLUME_STEP = 0.05d;
    public static final String EXTRA_CUSTOM_DATA = "customData";
    public static final String EXTRA_HAS_AUTH = "hasAuth";
    public static final String EXTRA_MEDIA = "media";
    public static final String EXTRA_SHOULD_START = "shouldStart";
    public static final String EXTRA_START_POINT = "startPoint";
    public static final String PREFS_KEY_START_ACTIVITY = "ccl-start-cast-activity";
    private static final long PROGRESS_UPDATE_INTERVAL_MS = TimeUnit.SECONDS.toMillis(1);
    public static final int QUEUE_OPERATION_APPEND = 9;
    public static final int QUEUE_OPERATION_INSERT_ITEMS = 2;
    public static final int QUEUE_OPERATION_JUMP = 4;
    public static final int QUEUE_OPERATION_LOAD = 1;
    public static final int QUEUE_OPERATION_MOVE = 8;
    public static final int QUEUE_OPERATION_NEXT = 10;
    public static final int QUEUE_OPERATION_PREV = 11;
    public static final int QUEUE_OPERATION_REMOVE_ITEM = 5;
    public static final int QUEUE_OPERATION_REMOVE_ITEMS = 6;
    public static final int QUEUE_OPERATION_REORDER = 7;
    public static final int QUEUE_OPERATION_SET_REPEAT = 12;
    public static final int QUEUE_OPERATION_UPDATE_ITEMS = 3;
    private static final String TAG = LogUtils.makeLogTag(VideoCastManager.class);
    private static VideoCastManager sInstance;
    private AudioManager mAudioManager;
    private MediaAuthService mAuthService;
    private MessageReceivedCallback mDataChannel;
    private String mDataNamespace;
    private int mIdleReason;
    private long mLiveStreamDuration = DEFAULT_LIVE_STREAM_DURATION_MS;
    private FetchBitmapTask mLockScreenFetchTask;
    private MediaQueue mMediaQueue;
    private MediaSessionCompat mMediaSessionCompat;
    private FetchBitmapTask mMediaSessionIconFetchTask;
    private MediaStatus mMediaStatus;
    private final Set<IMiniController> mMiniControllers = Collections.synchronizedSet(new HashSet());
    private Class<? extends Service> mNotificationServiceClass;
    private MediaQueueItem mPreLoadingItem;
    private UpdateProgressTask mProgressTask;
    private Timer mProgressTimer;
    private final Set<ProgressWatcher> mProgressWatchers = new CopyOnWriteArraySet();
    private RemoteMediaPlayer mRemoteMediaPlayer;
    private int mState = 1;
    private Class<?> mTargetActivity;
    private TracksPreferenceManager mTrackManager;
    private final Set<OnTracksSelectedListener> mTracksSelectedListeners = new CopyOnWriteArraySet();
    private final Set<VideoCastConsumer> mVideoConsumers = new CopyOnWriteArraySet();
    private double mVolumeStep = DEFAULT_VOLUME_STEP;
    private VolumeType mVolumeType = VolumeType.DEVICE;

    private class UpdateProgressTask extends TimerTask {
        private UpdateProgressTask() {
        }

        public void run() {
            if (VideoCastManager.this.mState != 4 && VideoCastManager.this.isConnected()) {
                if (VideoCastManager.this.mRemoteMediaPlayer != null) {
                    try {
                        int mediaDuration = (int) VideoCastManager.this.getMediaDuration();
                        if (mediaDuration > 0) {
                            VideoCastManager.this.updateProgress((int) VideoCastManager.this.getCurrentMediaPosition(), mediaDuration);
                        }
                    } catch (Throwable e) {
                        LogUtils.LOGE(VideoCastManager.TAG, "Failed to update the progress tracker due to network issues", e);
                    }
                }
            }
        }
    }

    public enum VolumeType {
        STREAM,
        DEVICE
    }

    class CastListener extends Listener {
        CastListener() {
        }

        public void onApplicationDisconnected(int i) {
            VideoCastManager.this.onApplicationDisconnected(i);
        }

        public void onApplicationStatusChanged() {
            VideoCastManager.this.onApplicationStatusChanged();
        }

        public void onVolumeChanged() {
            VideoCastManager.this.onVolumeChanged();
        }
    }

    private VideoCastManager() {
    }

    protected VideoCastManager(Context context, CastConfiguration castConfiguration) {
        super(context, castConfiguration);
        LogUtils.LOGD(TAG, "VideoCastManager is instantiated");
        if (castConfiguration.getNamespaces() == null) {
            context = null;
        } else {
            context = (String) castConfiguration.getNamespaces().get(0);
        }
        this.mDataNamespace = context;
        context = castConfiguration.getTargetActivity();
        if (context == null) {
            context = DEFAULT_TARGET_ACTIVITY;
        }
        this.mTargetActivity = context;
        this.mPreferenceAccessor.saveStringToPreference(BaseCastManager.PREFS_KEY_CAST_ACTIVITY_NAME, this.mTargetActivity.getName());
        if (TextUtils.isEmpty(this.mDataNamespace) == null) {
            this.mPreferenceAccessor.saveStringToPreference(BaseCastManager.PREFS_KEY_CAST_CUSTOM_DATA_NAMESPACE, this.mDataNamespace);
        }
        this.mAudioManager = (AudioManager) this.mContext.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.mNotificationServiceClass = castConfiguration.getCustomNotificationService();
        if (this.mNotificationServiceClass == null) {
            this.mNotificationServiceClass = VideoCastNotificationService.class;
        }
    }

    public final Class<? extends Service> getNotificationServiceClass() {
        return this.mNotificationServiceClass;
    }

    public static synchronized VideoCastManager initialize(Context context, CastConfiguration castConfiguration) {
        synchronized (VideoCastManager.class) {
            if (sInstance == null) {
                LogUtils.LOGD(TAG, "New instance of VideoCastManager is created");
                if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) != 0) {
                    LogUtils.LOGE(TAG, "Couldn't find the appropriate version of Google Play Services");
                }
                sInstance = new VideoCastManager(context, castConfiguration);
                sInstance.restartProgressTimer();
            }
            sInstance.setupTrackManager();
            context = sInstance;
        }
        return context;
    }

    public static VideoCastManager getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        String str = "No VideoCastManager instance was found, did you forget to initialize it?";
        LogUtils.LOGE(TAG, str);
        throw new IllegalStateException(str);
    }

    protected void setupTrackManager() {
        if (isFeatureEnabled(16)) {
            this.mTrackManager = new TracksPreferenceManager(this.mContext.getApplicationContext());
            registerCaptionListener(this.mContext.getApplicationContext());
        }
    }

    private void updateMiniController(IMiniController iMiniController) throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        checkRemoteMediaPlayerAvailable();
        if (this.mRemoteMediaPlayer.getStreamDuration() > 0 || isRemoteStreamLive()) {
            ResponseCustomData responseCustomData = new ResponseCustomData(getRemoteMediaInformation().getCustomData());
            String str = responseCustomData.assetTitle;
            Uri uri = responseCustomData.assetThumbnailUri;
            String str2 = LogUtil.CCL;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("title=");
            stringBuilder.append(str);
            stringBuilder.append(" imageUri=");
            stringBuilder.append(uri);
            LogUtils.d(str2, stringBuilder.toString());
            if (responseCustomData.isAd) {
                Uri adThumbnailUri = Utils.getAdThumbnailUri(responseCustomData.playSubType, responseCustomData.channelId);
                iMiniController.setPlaybackStatus(1, 2);
                iMiniController.setStreamType(0);
                iMiniController.setTitle(responseCustomData.assetTitle);
                iMiniController.setIcon(adThumbnailUri);
                iMiniController.setProgressVisibility(false);
            } else {
                if (responseCustomData.isBroadcast()) {
                    getCastConfiguration().setNextPrevVisibilityPolicy(4);
                    iMiniController.setProgressVisibility(false);
                } else {
                    getCastConfiguration().setNextPrevVisibilityPolicy(3);
                    iMiniController.setProgressVisibility(true);
                }
                iMiniController.setStreamType(1);
                iMiniController.setPlaybackStatus(this.mState, this.mIdleReason);
                if (str == null) {
                    str = "";
                }
                iMiniController.setTitle(str);
                iMiniController.setIcon(uri);
            }
            iMiniController.setSubtitle(this.mContext.getResources().getString(R.string.ccl_casting_to_device, new Object[]{this.mDeviceName}));
        }
    }

    private void updateMiniControllers() {
        synchronized (this.mMiniControllers) {
            for (IMiniController updateMiniController : this.mMiniControllers) {
                try {
                    updateMiniController(updateMiniController);
                } catch (Throwable e) {
                    LogUtils.LOGE(TAG, "updateMiniControllers() Failed to update mini controller", e);
                }
            }
        }
    }

    public void onPlayPauseClicked(View view) throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mState == 2) {
            pause();
            return;
        }
        view = isRemoteStreamLive();
        if ((this.mState == 3 && view == null) || (this.mState == 1 && view != null)) {
            play();
        }
    }

    public void onTargetActivityInvoked(Context context) throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.d(LogUtil.CCL, "");
        context = new Intent("CastController");
        context.putExtra(EXTRA_MEDIA, Utils.mediaInfoToBundle(getRemoteMediaInformation()));
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(context);
    }

    public void onUpcomingPlayClicked(View view, MediaQueueItem mediaQueueItem) {
        for (VideoCastConsumer onUpcomingPlayClicked : this.mVideoConsumers) {
            onUpcomingPlayClicked.onUpcomingPlayClicked(view, mediaQueueItem);
        }
    }

    public void onUpcomingStopClicked(View view, MediaQueueItem mediaQueueItem) {
        for (VideoCastConsumer onUpcomingStopClicked : this.mVideoConsumers) {
            onUpcomingStopClicked.onUpcomingStopClicked(view, mediaQueueItem);
        }
    }

    public void updateMiniControllersVisibility(boolean z) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateMiniControllersVisibility() reached with visibility: ");
        stringBuilder.append(z);
        LogUtils.LOGD(str, stringBuilder.toString());
        synchronized (this.mMiniControllers) {
            for (IMiniController visibility : this.mMiniControllers) {
                visibility.setVisibility(z ? 0 : 8);
            }
        }
    }

    public void updateMiniControllersVisibilityForUpcoming(MediaQueueItem mediaQueueItem) {
        synchronized (this.mMiniControllers) {
            for (IMiniController iMiniController : this.mMiniControllers) {
                iMiniController.setUpcomingItem(mediaQueueItem);
                iMiniController.setUpcomingVisibility(mediaQueueItem != null);
            }
        }
    }

    private void setFlagForStartCastControllerActivity() {
        this.mPreferenceAccessor.saveBooleanToPreference(PREFS_KEY_START_ACTIVITY, Boolean.valueOf(true));
    }

    public void startVideoCastControllerActivity(Context context, Bundle bundle, int i, boolean z, JSONObject jSONObject) {
        Intent intent = new Intent(context, VideoCastControllerActivity.class);
        intent.putExtra(EXTRA_MEDIA, bundle);
        intent.putExtra(EXTRA_START_POINT, i);
        intent.putExtra(EXTRA_SHOULD_START, z);
        if (jSONObject != null) {
            intent.putExtra(EXTRA_CUSTOM_DATA, jSONObject.toString());
        }
        intent.setFlags(C.ENCODING_PCM_MU_LAW);
        setFlagForStartCastControllerActivity();
        context.startActivity(intent);
    }

    public void startVideoCastControllerActivity(Context context, Bundle bundle, int i, boolean z) {
        startVideoCastControllerActivity(context, bundle, i, z, null);
    }

    public void startVideoCastControllerActivity(Context context, MediaAuthService mediaAuthService) {
        if (mediaAuthService != null) {
            this.mAuthService = mediaAuthService;
            mediaAuthService = new Intent(context, VideoCastControllerActivity.class);
            mediaAuthService.putExtra(EXTRA_HAS_AUTH, true);
            setFlagForStartCastControllerActivity();
            context.startActivity(mediaAuthService);
        }
    }

    public void startVideoCastControllerActivity(Context context, MediaInfo mediaInfo, int i, boolean z) {
        startVideoCastControllerActivity(context, Utils.mediaInfoToBundle(mediaInfo), i, z);
    }

    public MediaAuthService getMediaAuthService() {
        return this.mAuthService;
    }

    public void setMediaAuthService(MediaAuthService mediaAuthService) {
        this.mAuthService = mediaAuthService;
    }

    public void removeMediaAuthService() {
        this.mAuthService = null;
    }

    public final RemoteMediaPlayer getRemoteMediaPlayer() {
        return this.mRemoteMediaPlayer;
    }

    public final boolean isRemoteStreamLive() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        MediaInfo remoteMediaInformation = getRemoteMediaInformation();
        return remoteMediaInformation != null && remoteMediaInformation.getStreamType() == 2;
    }

    public boolean shouldRemoteUiBeVisible(int i, int i2) throws TransientNetworkDisconnectionException, NoConnectionException {
        boolean z = false;
        switch (i) {
            case 1:
                if (isRemoteStreamLive() != 0 && i2 == 2) {
                    return true;
                }
                if (!(this.mMediaStatus == 0 || this.mMediaStatus.getLoadingItemId() == 0)) {
                    z = true;
                }
                return z;
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    private void checkRemoteMediaPlayerAvailable() throws NoConnectionException {
        if (this.mRemoteMediaPlayer == null) {
            throw new NoConnectionException();
        }
    }

    public final void setVolumeType(VolumeType volumeType) {
        this.mVolumeType = volumeType;
    }

    public String getRemoteMediaUrl() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mRemoteMediaPlayer == null || this.mRemoteMediaPlayer.getMediaInfo() == null) {
            throw new NoConnectionException();
        }
        MediaInfo mediaInfo = this.mRemoteMediaPlayer.getMediaInfo();
        this.mRemoteMediaPlayer.getMediaStatus().getPlayerState();
        return mediaInfo.getContentId();
    }

    public boolean isRemoteMediaPlaying() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mState != 4) {
            if (this.mState != 2) {
                return false;
            }
        }
        return true;
    }

    public boolean isRemoteMediaPaused() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        return this.mState == 3;
    }

    public boolean isRemoteMediaLoaded() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (!isRemoteMediaPaused()) {
            if (!isRemoteMediaPlaying()) {
                return false;
            }
        }
        return true;
    }

    public MediaInfo getRemoteMediaInformation() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        checkRemoteMediaPlayerAvailable();
        return this.mRemoteMediaPlayer.getMediaInfo();
    }

    public double getVolume() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mVolumeType != VolumeType.STREAM) {
            return getDeviceVolume();
        }
        checkRemoteMediaPlayerAvailable();
        return this.mRemoteMediaPlayer.getMediaStatus().getStreamVolume();
    }

    public void setVolume(double d) throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (d > 1.0d) {
            d = 1.0d;
        } else if (d < 0.0d) {
            d = 0.0d;
        }
        if (this.mVolumeType == VolumeType.STREAM) {
            checkRemoteMediaPlayerAvailable();
            this.mRemoteMediaPlayer.setStreamVolume(this.mApiClient, d).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    if (!mediaChannelResult.getStatus().isSuccess()) {
                        VideoCastManager.this.onFailed(R.string.ccl_failed_setting_volume, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
            return;
        }
        setDeviceVolume(d);
    }

    public void adjustVolume(double d) throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        double volume = getVolume() + d;
        d = 0.0d;
        if (volume > 1.0d) {
            d = 1.0d;
        } else if (volume >= 0.0d) {
            d = volume;
        }
        setVolume(d);
    }

    public void updateVolume(int i) {
        this.mMediaRouter.getSelectedRoute().requestUpdateVolume(i);
    }

    public boolean isMute() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mVolumeType != VolumeType.STREAM) {
            return isDeviceMute();
        }
        checkRemoteMediaPlayerAvailable();
        return this.mRemoteMediaPlayer.getMediaStatus().isMute();
    }

    public void setMute(boolean z) throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mVolumeType == VolumeType.STREAM) {
            checkRemoteMediaPlayerAvailable();
            this.mRemoteMediaPlayer.setStreamMute(this.mApiClient, z);
            return;
        }
        setDeviceMute(z);
    }

    public long getMediaDuration() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        checkRemoteMediaPlayerAvailable();
        return this.mRemoteMediaPlayer.getStreamDuration();
    }

    public long getMediaTimeRemaining() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mRemoteMediaPlayer == null) {
            return -1;
        }
        long j;
        if (isRemoteStreamLive()) {
            j = this.mLiveStreamDuration;
        } else {
            j = this.mRemoteMediaPlayer.getStreamDuration() - this.mRemoteMediaPlayer.getApproximateStreamPosition();
        }
        return j;
    }

    public long getCurrentMediaPosition() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        checkRemoteMediaPlayerAvailable();
        return this.mRemoteMediaPlayer.getApproximateStreamPosition();
    }

    private boolean startNotificationService() {
        boolean z = true;
        if (!isFeatureEnabled(4)) {
            return true;
        }
        LogUtils.LOGD(TAG, "startNotificationService()");
        Intent intent = new Intent(this.mContext, this.mNotificationServiceClass);
        intent.setPackage(this.mContext.getPackageName());
        intent.setAction(VideoCastNotificationService.ACTION_VISIBILITY);
        intent.putExtra(VideoCastNotificationService.NOTIFICATION_VISIBILITY, this.mUiVisible ^ true);
        if (this.mContext.startService(intent) == null) {
            z = false;
        }
        return z;
    }

    private void stopNotificationService() {
        if (isFeatureEnabled(4) && this.mContext != null) {
            this.mContext.stopService(new Intent(this.mContext, this.mNotificationServiceClass));
        }
    }

    private void onApplicationDisconnected(int i) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onApplicationDisconnected() reached with error code: ");
        stringBuilder.append(i);
        LogUtils.LOGD(str, stringBuilder.toString());
        this.mApplicationErrorCode = i;
        updateMediaSession(false);
        if (this.mMediaSessionCompat != null && isFeatureEnabled(2)) {
            this.mMediaRouter.setMediaSessionCompat(null);
        }
        for (VideoCastConsumer onApplicationDisconnected : this.mVideoConsumers) {
            onApplicationDisconnected.onApplicationDisconnected(i);
        }
        if (this.mMediaRouter != 0) {
            i = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onApplicationDisconnected(): Cached RouteInfo: ");
            stringBuilder.append(getRouteInfo());
            LogUtils.LOGD(i, stringBuilder.toString());
            i = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onApplicationDisconnected(): Selected RouteInfo: ");
            stringBuilder.append(this.mMediaRouter.getSelectedRoute());
            LogUtils.LOGD(i, stringBuilder.toString());
            if (getRouteInfo() == 0 || this.mMediaRouter.getSelectedRoute().equals(getRouteInfo()) != 0) {
                LogUtils.LOGD(TAG, "onApplicationDisconnected(): Setting route to default");
                this.mMediaRouter.selectRoute(this.mMediaRouter.getDefaultRoute());
            }
        }
        onDeviceSelected(null, null);
        updateMiniControllersVisibility(false);
        stopNotificationService();
    }

    private void onApplicationStatusChanged() {
        if (isConnected()) {
            try {
                String applicationStatus = Cast.CastApi.getApplicationStatus(this.mApiClient);
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onApplicationStatusChanged() reached: ");
                stringBuilder.append(applicationStatus);
                LogUtils.LOGD(str, stringBuilder.toString());
                for (VideoCastConsumer onApplicationStatusChanged : this.mVideoConsumers) {
                    onApplicationStatusChanged.onApplicationStatusChanged(applicationStatus);
                }
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, "onApplicationStatusChanged()", e);
            }
        }
    }

    private void onVolumeChanged() {
        LogUtils.LOGD(TAG, "onVolumeChanged() reached");
        try {
            double volume = getVolume();
            boolean isMute = isMute();
            for (VideoCastConsumer onVolumeChanged : this.mVideoConsumers) {
                onVolumeChanged.onVolumeChanged(volume, isMute);
            }
        } catch (Throwable e) {
            LogUtils.LOGE(TAG, "Failed to get volume", e);
        }
    }

    protected void onApplicationConnected(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
        str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onApplicationConnected() reached with sessionId: ");
        stringBuilder.append(str2);
        stringBuilder.append(", and mReconnectionStatus=");
        stringBuilder.append(this.mReconnectionStatus);
        LogUtils.LOGD(str, stringBuilder.toString());
        this.mApplicationErrorCode = null;
        if (this.mReconnectionStatus == 2) {
            String<RouteInfo> routes = this.mMediaRouter.getRoutes();
            if (routes != null) {
                String stringFromPreference = this.mPreferenceAccessor.getStringFromPreference(BaseCastManager.PREFS_KEY_ROUTE_ID);
                for (RouteInfo routeInfo : routes) {
                    if (stringFromPreference.equals(routeInfo.getId())) {
                        LogUtils.LOGD(TAG, "Found the correct route during reconnection attempt");
                        this.mReconnectionStatus = 3;
                        this.mMediaRouter.selectRoute(routeInfo);
                        break;
                    }
                }
            }
        }
        startNotificationService();
        try {
            attachDataChannel();
            attachMediaChannel();
            this.mSessionId = str2;
            this.mPreferenceAccessor.saveStringToPreference(BaseCastManager.PREFS_KEY_SESSION_ID, this.mSessionId);
            this.mRemoteMediaPlayer.requestStatus(this.mApiClient).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    if (!mediaChannelResult.getStatus().isSuccess()) {
                        VideoCastManager.this.onFailed(R.string.ccl_failed_status_request, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
            for (VideoCastConsumer onApplicationConnected : this.mVideoConsumers) {
                onApplicationConnected.onApplicationConnected(applicationMetadata, this.mSessionId, z);
            }
        } catch (ApplicationMetadata applicationMetadata2) {
            LogUtils.LOGE(TAG, "Failed to attach media/data channel due to network issues", applicationMetadata2);
            onFailed(R.string.ccl_failed_no_connection_trans, -1);
        } catch (ApplicationMetadata applicationMetadata22) {
            LogUtils.LOGE(TAG, "Failed to attach media/data channel due to network issues", applicationMetadata22);
            onFailed(R.string.ccl_failed_no_connection, -1);
        }
    }

    public void onConnectivityRecovered() {
        reattachMediaChannel();
        reattachDataChannel();
        super.onConnectivityRecovered();
    }

    public void onApplicationStopFailed(int i) {
        for (VideoCastConsumer onApplicationStopFailed : this.mVideoConsumers) {
            onApplicationStopFailed.onApplicationStopFailed(i);
        }
    }

    public void onApplicationConnectionFailed(int i) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onApplicationConnectionFailed() reached with errorCode: ");
        stringBuilder.append(i);
        LogUtils.LOGD(str, stringBuilder.toString());
        this.mApplicationErrorCode = i;
        if (this.mReconnectionStatus != 2) {
            for (VideoCastConsumer onApplicationConnectionFailed : this.mVideoConsumers) {
                onApplicationConnectionFailed.onApplicationConnectionFailed(i);
            }
            onDeviceSelected(null, null);
            if (this.mMediaRouter != 0) {
                LogUtils.LOGD(TAG, "onApplicationConnectionFailed(): Setting route to default");
                this.mMediaRouter.selectRoute(this.mMediaRouter.getDefaultRoute());
            }
        } else if (i == CastStatusCodes.APPLICATION_NOT_RUNNING) {
            this.mReconnectionStatus = 4;
            onDeviceSelected(null, null);
        }
    }

    public void loadMedia(MediaInfo mediaInfo, boolean z, int i) throws TransientNetworkDisconnectionException, NoConnectionException {
        loadMedia(mediaInfo, z, i, null);
    }

    public void loadMedia(MediaInfo mediaInfo, boolean z, int i, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        loadMedia(mediaInfo, null, z, i, jSONObject);
    }

    public void loadMedia(MediaInfo mediaInfo, long[] jArr, boolean z, int i, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "loadMedia");
        checkConnectivity();
        if (mediaInfo != null) {
            if (this.mRemoteMediaPlayer != null) {
                this.mRemoteMediaPlayer.load(this.mApiClient, mediaInfo, z, (long) i, jArr, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                    public void onResult(MediaChannelResult mediaChannelResult) {
                        for (VideoCastConsumer onMediaLoadResult : VideoCastManager.this.mVideoConsumers) {
                            onMediaLoadResult.onMediaLoadResult(mediaChannelResult.getStatus().getStatusCode());
                        }
                    }
                });
                return;
            }
            LogUtils.LOGE(TAG, "Trying to load a video with no active media session");
            throw new NoConnectionException();
        }
    }

    public void queueLoad(MediaQueueItem[] mediaQueueItemArr, int i, int i2, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "queueLoad");
        checkConnectivity();
        if (mediaQueueItemArr != null) {
            if (mediaQueueItemArr.length != 0) {
                if (this.mRemoteMediaPlayer != null) {
                    this.mRemoteMediaPlayer.queueLoad(this.mApiClient, mediaQueueItemArr, i, i2, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                        public void onResult(MediaChannelResult mediaChannelResult) {
                            for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                                onMediaQueueOperationResult.onMediaQueueOperationResult(1, mediaChannelResult.getStatus().getStatusCode());
                            }
                        }
                    });
                } else {
                    LogUtils.LOGE(TAG, "Trying to queue one or more videos with no active media session");
                    throw new NoConnectionException();
                }
            }
        }
    }

    public void queueInsertItems(MediaQueueItem[] mediaQueueItemArr, int i, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "queueInsertItems");
        checkConnectivity();
        if (mediaQueueItemArr == null || mediaQueueItemArr.length == 0) {
            throw new IllegalArgumentException("items cannot be empty or null");
        } else if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.queueInsertItems(this.mApiClient, mediaQueueItemArr, i, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                        onMediaQueueOperationResult.onMediaQueueOperationResult(2, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to insert into queue with no active media session");
            throw new NoConnectionException();
        }
    }

    public void queueUpdateItems(MediaQueueItem[] mediaQueueItemArr, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.queueUpdateItems(this.mApiClient, mediaQueueItemArr, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    String access$100 = VideoCastManager.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("queueUpdateItems() ");
                    stringBuilder.append(mediaChannelResult.getStatus());
                    stringBuilder.append(mediaChannelResult.getStatus().isSuccess());
                    LogUtils.LOGD(access$100, stringBuilder.toString());
                    for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                        onMediaQueueOperationResult.onMediaQueueOperationResult(3, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to update the queue with no active media session");
            throw new NoConnectionException();
        }
    }

    public void queueJumpToItem(int i, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException, IllegalArgumentException {
        checkConnectivity();
        if (i == 0) {
            throw new IllegalArgumentException("itemId is not valid");
        } else if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.queueJumpToItem(this.mApiClient, i, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                        onMediaQueueOperationResult.onMediaQueueOperationResult(4, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to jump in a queue with no active media session");
            throw new NoConnectionException();
        }
    }

    public void queueRemoveItems(int[] iArr, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException, IllegalArgumentException {
        LogUtils.LOGD(TAG, "queueRemoveItems");
        checkConnectivity();
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("itemIds cannot be empty or null");
        } else if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.queueRemoveItems(this.mApiClient, iArr, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                        onMediaQueueOperationResult.onMediaQueueOperationResult(6, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to remove items from queue with no active media session");
            throw new NoConnectionException();
        }
    }

    public void queueRemoveItem(int i, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException, IllegalArgumentException {
        LogUtils.LOGD(TAG, "queueRemoveItem");
        checkConnectivity();
        if (i == 0) {
            throw new IllegalArgumentException("itemId is invalid");
        } else if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.queueRemoveItem(this.mApiClient, i, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                        onMediaQueueOperationResult.onMediaQueueOperationResult(5, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to remove an item from queue with no active media session");
            throw new NoConnectionException();
        }
    }

    public void queueReorderItems(int[] iArr, int i, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException, IllegalArgumentException {
        LogUtils.LOGD(TAG, "queueReorderItems");
        checkConnectivity();
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("itemIdsToReorder cannot be empty or null");
        } else if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.queueReorderItems(this.mApiClient, iArr, i, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                        onMediaQueueOperationResult.onMediaQueueOperationResult(7, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to reorder items in a queue with no active media session");
            throw new NoConnectionException();
        }
    }

    public void queueMoveItemToNewIndex(int i, int i2, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        this.mRemoteMediaPlayer.queueMoveItemToNewIndex(this.mApiClient, i, i2, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
            public void onResult(MediaChannelResult mediaChannelResult) {
                for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                    onMediaQueueOperationResult.onMediaQueueOperationResult(8, mediaChannelResult.getStatus().getStatusCode());
                }
            }
        });
    }

    public void queueAppendItem(MediaQueueItem mediaQueueItem, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        this.mRemoteMediaPlayer.queueAppendItem(this.mApiClient, mediaQueueItem, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
            public void onResult(MediaChannelResult mediaChannelResult) {
                for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                    onMediaQueueOperationResult.onMediaQueueOperationResult(9, mediaChannelResult.getStatus().getStatusCode());
                }
            }
        });
    }

    public void queueNext(JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.queueNext(this.mApiClient, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                        onMediaQueueOperationResult.onMediaQueueOperationResult(10, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to update the queue with no active media session");
            throw new NoConnectionException();
        }
    }

    public void queuePrev(JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.queuePrev(this.mApiClient, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                        onMediaQueueOperationResult.onMediaQueueOperationResult(11, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to update the queue with no active media session");
            throw new NoConnectionException();
        }
    }

    public void queueInsertBeforeCurrentAndPlay(MediaQueueItem mediaQueueItem, int i, final JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mRemoteMediaPlayer == null) {
            LogUtils.LOGE(TAG, "Trying to insert into queue with no active media session");
            throw new NoConnectionException();
        } else if (mediaQueueItem == null || i == 0) {
            throw new IllegalArgumentException("item cannot be empty or insertBeforeItemId cannot be invalid");
        } else {
            this.mRemoteMediaPlayer.queueInsertItems(this.mApiClient, new MediaQueueItem[]{mediaQueueItem}, i, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    if (mediaChannelResult.getStatus().isSuccess()) {
                        try {
                            VideoCastManager.this.queuePrev(jSONObject);
                        } catch (Throwable e) {
                            LogUtils.LOGE(VideoCastManager.TAG, "queuePrev() Failed to skip to previous", e);
                        }
                    }
                    for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                        onMediaQueueOperationResult.onMediaQueueOperationResult(2, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        }
    }

    public void queueSetRepeatMode(int i, JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.queueSetRepeatMode(this.mApiClient, i, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    if (!mediaChannelResult.getStatus().isSuccess()) {
                        String access$100 = VideoCastManager.TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Failed with status: ");
                        stringBuilder.append(mediaChannelResult.getStatus());
                        LogUtils.LOGD(access$100, stringBuilder.toString());
                    }
                    for (VideoCastConsumer onMediaQueueOperationResult : VideoCastManager.this.mVideoConsumers) {
                        onMediaQueueOperationResult.onMediaQueueOperationResult(12, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to update the queue with no active media session");
            throw new NoConnectionException();
        }
    }

    public void play(int i) throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("attempting to play media at position ");
        stringBuilder.append(i);
        stringBuilder.append(" seconds");
        LogUtils.LOGD(str, stringBuilder.toString());
        if (this.mRemoteMediaPlayer != null) {
            seekAndPlay(i);
        } else {
            LogUtils.LOGE(TAG, "Trying to play a video with no active media session");
            throw new NoConnectionException();
        }
    }

    public void play(JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "play(customData)");
        checkConnectivity();
        if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.play(this.mApiClient, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    if (!mediaChannelResult.getStatus().isSuccess()) {
                        VideoCastManager.this.onFailed(R.string.ccl_failed_to_play, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to play a video with no active media session");
            throw new NoConnectionException();
        }
    }

    public void play() throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        play(null);
    }

    public void stop(JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "stop()");
        checkConnectivity();
        if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.stop(this.mApiClient, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    if (!mediaChannelResult.getStatus().isSuccess()) {
                        VideoCastManager.this.onFailed(R.string.ccl_failed_to_stop, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to stop a stream with no active media session");
            throw new NoConnectionException();
        }
    }

    public void stop() throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        stop(null);
    }

    public void pause() throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        pause(null);
    }

    public void pause(JSONObject jSONObject) throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "attempting to pause media");
        checkConnectivity();
        if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.pause(this.mApiClient, jSONObject).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    if (!mediaChannelResult.getStatus().isSuccess()) {
                        VideoCastManager.this.onFailed(R.string.ccl_failed_to_pause, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to pause a video with no active media session");
            throw new NoConnectionException();
        }
    }

    public void seek(int i) throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "attempting to seek media");
        checkConnectivity();
        if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.seek(this.mApiClient, (long) i, 0).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    if (!mediaChannelResult.getStatus().isSuccess()) {
                        VideoCastManager.this.onFailed(R.string.ccl_failed_seek, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
        } else {
            LogUtils.LOGE(TAG, "Trying to seek a video with no active media session");
            throw new NoConnectionException();
        }
    }

    public void forward(int i) throws TransientNetworkDisconnectionException, NoConnectionException {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("forward(): attempting to forward media by ");
        stringBuilder.append(i);
        LogUtils.LOGD(str, stringBuilder.toString());
        checkConnectivity();
        if (this.mRemoteMediaPlayer != null) {
            seek((int) (this.mRemoteMediaPlayer.getApproximateStreamPosition() + ((long) i)));
        } else {
            LogUtils.LOGE(TAG, "Trying to seek a video with no active media session");
            throw new NoConnectionException();
        }
    }

    public void seekAndPlay(int i) throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "attempting to seek media");
        checkConnectivity();
        if (this.mRemoteMediaPlayer != null) {
            this.mRemoteMediaPlayer.seek(this.mApiClient, (long) i, 1).setResultCallback(new ResultCallback<MediaChannelResult>() {
                public void onResult(MediaChannelResult mediaChannelResult) {
                    if (!mediaChannelResult.getStatus().isSuccess()) {
                        VideoCastManager.this.onFailed(R.string.ccl_failed_seek, mediaChannelResult.getStatus().getStatusCode());
                    }
                }
            });
            return;
        }
        LogUtils.LOGE(TAG, "Trying to seekAndPlay a video with no active media session");
        throw new NoConnectionException();
    }

    public void togglePlayback() throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (isRemoteMediaPlaying()) {
            pause();
        } else if (this.mState == 1 && this.mIdleReason == 1) {
            loadMedia(getRemoteMediaInformation(), true, 0);
        } else {
            play();
        }
    }

    private void attachMediaChannel() throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "attachMediaChannel()");
        checkConnectivity();
        if (this.mRemoteMediaPlayer == null) {
            this.mRemoteMediaPlayer = new RemoteMediaPlayer();
            this.mRemoteMediaPlayer.setOnStatusUpdatedListener(new OnStatusUpdatedListener() {
                public void onStatusUpdated() {
                    LogUtils.LOGD(VideoCastManager.TAG, "RemoteMediaPlayer::onStatusUpdated() is reached");
                    VideoCastManager.this.onRemoteMediaPlayerStatusUpdated();
                }
            });
            this.mRemoteMediaPlayer.setOnPreloadStatusUpdatedListener(new OnPreloadStatusUpdatedListener() {
                public void onPreloadStatusUpdated() {
                    LogUtils.LOGD(VideoCastManager.TAG, "RemoteMediaPlayer::onPreloadStatusUpdated() is reached");
                    VideoCastManager.this.onRemoteMediaPreloadStatusUpdated();
                }
            });
            this.mRemoteMediaPlayer.setOnMetadataUpdatedListener(new OnMetadataUpdatedListener() {
                public void onMetadataUpdated() {
                    LogUtils.LOGD(VideoCastManager.TAG, "RemoteMediaPlayer::onMetadataUpdated() is reached");
                    VideoCastManager.this.onRemoteMediaPlayerMetadataUpdated();
                }
            });
            this.mRemoteMediaPlayer.setOnQueueStatusUpdatedListener(new OnQueueStatusUpdatedListener() {
                public void onQueueStatusUpdated() {
                    LogUtils.LOGD(VideoCastManager.TAG, "RemoteMediaPlayer::onQueueStatusUpdated() is reached");
                    VideoCastManager.this.mMediaStatus = VideoCastManager.this.mRemoteMediaPlayer != null ? VideoCastManager.this.mRemoteMediaPlayer.getMediaStatus() : null;
                    if (VideoCastManager.this.mMediaStatus == null || VideoCastManager.this.mMediaStatus.getQueueItems() == null) {
                        VideoCastManager.this.onQueueUpdated(null, null, 0, false);
                    } else {
                        VideoCastManager.this.onQueueUpdated(VideoCastManager.this.mMediaStatus.getQueueItems(), VideoCastManager.this.mMediaStatus.getQueueItemById(VideoCastManager.this.mMediaStatus.getCurrentItemId()), VideoCastManager.this.mMediaStatus.getQueueRepeatMode(), false);
                    }
                }
            });
        }
        try {
            LogUtils.LOGD(TAG, "Registering MediaChannel namespace");
            Cast.CastApi.setMessageReceivedCallbacks(this.mApiClient, this.mRemoteMediaPlayer.getNamespace(), this.mRemoteMediaPlayer);
        } catch (Throwable e) {
            LogUtils.LOGE(TAG, "attachMediaChannel()", e);
        }
        setUpMediaSession(null);
    }

    private void reattachMediaChannel() {
        if (this.mRemoteMediaPlayer != null && this.mApiClient != null) {
            try {
                LogUtils.LOGD(TAG, "Registering MediaChannel namespace");
                Cast.CastApi.setMessageReceivedCallbacks(this.mApiClient, this.mRemoteMediaPlayer.getNamespace(), this.mRemoteMediaPlayer);
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, "reattachMediaChannel()", e);
            }
        }
    }

    private void detachMediaChannel() {
        LogUtils.LOGD(TAG, "trying to detach media channel");
        if (this.mRemoteMediaPlayer != null) {
            try {
                Cast.CastApi.removeMessageReceivedCallbacks(this.mApiClient, this.mRemoteMediaPlayer.getNamespace());
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, "detachMediaChannel()", e);
            }
            this.mRemoteMediaPlayer = null;
        }
    }

    public int getPlaybackStatus() {
        return this.mState;
    }

    public final MediaStatus getMediaStatus() {
        return this.mMediaStatus;
    }

    public int getIdleReason() {
        return this.mIdleReason;
    }

    private void attachDataChannel() throws TransientNetworkDisconnectionException, NoConnectionException {
        if (!TextUtils.isEmpty(this.mDataNamespace) && this.mDataChannel == null) {
            checkConnectivity();
            this.mDataChannel = new MessageReceivedCallback() {
                public void onMessageReceived(CastDevice castDevice, String str, String str2) {
                    for (VideoCastConsumer onDataMessageReceived : VideoCastManager.this.mVideoConsumers) {
                        onDataMessageReceived.onDataMessageReceived(str2);
                    }
                }
            };
            try {
                Cast.CastApi.setMessageReceivedCallbacks(this.mApiClient, this.mDataNamespace, this.mDataChannel);
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, "attachDataChannel()", e);
            }
        }
    }

    private void reattachDataChannel() {
        if (!TextUtils.isEmpty(this.mDataNamespace) && this.mDataChannel != null) {
            try {
                Cast.CastApi.setMessageReceivedCallbacks(this.mApiClient, this.mDataNamespace, this.mDataChannel);
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, "reattachDataChannel()", e);
            }
        }
    }

    private void onMessageSendFailed(int i) {
        for (VideoCastConsumer onDataMessageSendFailed : this.mVideoConsumers) {
            onDataMessageSendFailed.onDataMessageSendFailed(i);
        }
    }

    public void sendDataMessage(String str) throws TransientNetworkDisconnectionException, NoConnectionException {
        if (TextUtils.isEmpty(this.mDataNamespace)) {
            throw new IllegalStateException("No Data Namespace is configured");
        }
        checkConnectivity();
        Cast.CastApi.sendMessage(this.mApiClient, this.mDataNamespace, str).setResultCallback(new ResultCallback<Status>() {
            public void onResult(Status status) {
                if (!status.isSuccess()) {
                    VideoCastManager.this.onMessageSendFailed(status.getStatusCode());
                }
            }
        });
    }

    public boolean removeDataChannel() {
        if (TextUtils.isEmpty(this.mDataNamespace)) {
            return false;
        }
        try {
            if (this.mApiClient != null) {
                Cast.CastApi.removeMessageReceivedCallbacks(this.mApiClient, this.mDataNamespace);
            }
            this.mDataChannel = null;
            this.mPreferenceAccessor.saveStringToPreference(BaseCastManager.PREFS_KEY_CAST_CUSTOM_DATA_NAMESPACE, null);
            return true;
        } catch (Throwable e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removeDataChannel() failed to remove namespace ");
            stringBuilder.append(this.mDataNamespace);
            LogUtils.LOGE(str, stringBuilder.toString(), e);
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onRemoteMediaPlayerStatusUpdated() {
        /*
        r8 = this;
        r0 = TAG;
        r1 = "onRemoteMediaPlayerStatusUpdated() reached";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r0, r1);
        r0 = r8.mApiClient;
        if (r0 == 0) goto L_0x0168;
    L_0x000b:
        r0 = r8.mRemoteMediaPlayer;
        if (r0 == 0) goto L_0x0168;
    L_0x000f:
        r0 = r8.mRemoteMediaPlayer;
        r0 = r0.getMediaStatus();
        if (r0 != 0) goto L_0x0019;
    L_0x0017:
        goto L_0x0168;
    L_0x0019:
        r0 = r8.mRemoteMediaPlayer;
        r0 = r0.getMediaStatus();
        r8.mMediaStatus = r0;
        r0 = r8.mMediaStatus;
        r0 = r0.getQueueItems();
        r1 = 0;
        if (r0 == 0) goto L_0x0040;
    L_0x002a:
        r2 = r8.mMediaStatus;
        r2 = r2.getCurrentItemId();
        r3 = r8.mMediaStatus;
        r2 = r3.getQueueItemById(r2);
        r3 = r8.mMediaStatus;
        r3 = r3.getQueueRepeatMode();
        r8.onQueueUpdated(r0, r2, r3, r1);
        goto L_0x0044;
    L_0x0040:
        r0 = 0;
        r8.onQueueUpdated(r0, r0, r1, r1);
    L_0x0044:
        r0 = r8.mMediaStatus;
        r0 = r0.getPlayerState();
        r8.mState = r0;
        r0 = r8.mMediaStatus;
        r0 = r0.getIdleReason();
        r8.mIdleReason = r0;
        r0 = TAG;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "new mState=";
        r2.append(r3);
        r3 = r8.mState;
        r2.append(r3);
        r3 = " mIdleReason=";
        r2.append(r3);
        r3 = r8.mIdleReason;
        r2.append(r3);
        r2 = r2.toString();
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.d(r0, r2);
        r2 = r8.getVolume();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r0 = r8.isMute();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4 = r8.mState;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5 = 2;
        r6 = 1;
        if (r4 != r5) goto L_0x009a;
    L_0x0084:
        r4 = TAG;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5 = "onRemoteMediaPlayerStatusUpdated(): Player status = playing";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r4, r5);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r8.updateMediaSession(r6);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4 = r8.getMediaTimeRemaining();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r8.startReconnectionService(r4);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r8.startNotificationService();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        goto L_0x0137;
    L_0x009a:
        r4 = r8.mState;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5 = 3;
        if (r4 != r5) goto L_0x00ae;
    L_0x009f:
        r4 = TAG;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5 = "onRemoteMediaPlayerStatusUpdated(): Player status = paused";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r4, r5);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r8.updateMediaSession(r1);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r8.startNotificationService();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        goto L_0x0137;
    L_0x00ae:
        r4 = r8.mState;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        if (r4 != r6) goto L_0x0122;
    L_0x00b2:
        r4 = TAG;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5 = new java.lang.StringBuilder;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5.<init>();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r7 = "onRemoteMediaPlayerStatusUpdated(): Player status = IDLE with reason: ";
        r5.append(r7);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r7 = r8.mIdleReason;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5.append(r7);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5 = r5.toString();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r4, r5);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r8.updateMediaSession(r1);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4 = r8.mIdleReason;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        switch(r4) {
            case 1: goto L_0x00ff;
            case 2: goto L_0x00f2;
            case 3: goto L_0x00e6;
            case 4: goto L_0x00d5;
            default: goto L_0x00d2;
        };	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
    L_0x00d2:
        r4 = TAG;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        goto L_0x010b;
    L_0x00d5:
        r1 = TAG;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4 = "onRemoteMediaPlayerStatusUpdated(): IDLE reason = ERROR";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r1, r4);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r8.clearMediaSession();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r1 = com.google.android.libraries.cast.companionlibrary.R.string.ccl_failed_receiver_player_error;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4 = -1;
        r8.onFailed(r1, r4);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        goto L_0x0136;
    L_0x00e6:
        r4 = r8.mMediaStatus;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4 = r4.getLoadingItemId();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        if (r4 != 0) goto L_0x0137;
    L_0x00ee:
        r8.clearMediaSession();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        goto L_0x0136;
    L_0x00f2:
        r1 = TAG;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4 = "onRemoteMediaPlayerStatusUpdated(): IDLE reason = CANCELLED";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r1, r4);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r1 = r8.isRemoteStreamLive();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r1 = r1 ^ r6;
        goto L_0x0137;
    L_0x00ff:
        r4 = r8.mMediaStatus;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4 = r4.getLoadingItemId();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        if (r4 != 0) goto L_0x0137;
    L_0x0107:
        r8.clearMediaSession();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        goto L_0x0136;
    L_0x010b:
        r5 = new java.lang.StringBuilder;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5.<init>();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r7 = "onRemoteMediaPlayerStatusUpdated(): Unexpected Idle Reason ";
        r5.append(r7);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r7 = r8.mIdleReason;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5.append(r7);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5 = r5.toString();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r4, r5);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        goto L_0x0137;
    L_0x0122:
        r4 = r8.mState;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5 = 4;
        if (r4 != r5) goto L_0x012f;
    L_0x0127:
        r4 = TAG;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r5 = "onRemoteMediaPlayerStatusUpdated(): Player status = buffering";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r4, r5);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        goto L_0x0137;
    L_0x012f:
        r1 = TAG;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4 = "onRemoteMediaPlayerStatusUpdated(): Player status = unknown";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r1, r4);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
    L_0x0136:
        r1 = 1;
    L_0x0137:
        if (r1 == 0) goto L_0x013f;
    L_0x0139:
        r8.stopReconnectionService();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r8.stopNotificationService();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
    L_0x013f:
        r1 = r1 ^ r6;
        r8.updateMiniControllersVisibility(r1);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r8.updateMiniControllers();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r1 = r8.mVideoConsumers;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r1 = r1.iterator();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
    L_0x014c:
        r4 = r1.hasNext();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        if (r4 == 0) goto L_0x0167;
    L_0x0152:
        r4 = r1.next();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4 = (com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumer) r4;	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4.onRemoteMediaPlayerStatusUpdated();	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        r4.onVolumeChanged(r2, r0);	 Catch:{ TransientNetworkDisconnectionException -> 0x015f, TransientNetworkDisconnectionException -> 0x015f }
        goto L_0x014c;
    L_0x015f:
        r0 = move-exception;
        r1 = TAG;
        r2 = "Failed to get volume state due to network issues";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r1, r2, r0);
    L_0x0167:
        return;
    L_0x0168:
        r0 = TAG;
        r1 = "mApiClient or mRemoteMediaPlayer is null, so will not proceed";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r0, r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager.onRemoteMediaPlayerStatusUpdated():void");
    }

    private void onRemoteMediaPreloadStatusUpdated() {
        String str;
        StringBuilder stringBuilder;
        MediaQueueItem mediaQueueItem = null;
        this.mMediaStatus = this.mRemoteMediaPlayer != null ? this.mRemoteMediaPlayer.getMediaStatus() : null;
        if (this.mMediaStatus != null) {
            mediaQueueItem = this.mMediaStatus.getQueueItemById(this.mMediaStatus.getPreloadedItemId());
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("status=");
            stringBuilder.append(this.mMediaStatus.getPlayerState());
            LogUtils.d(str, stringBuilder.toString());
        }
        this.mPreLoadingItem = mediaQueueItem;
        updateMiniControllersVisibilityForUpcoming(mediaQueueItem);
        str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("onRemoteMediaPreloadStatusUpdated() ");
        stringBuilder.append(mediaQueueItem);
        LogUtils.LOGD(str, stringBuilder.toString());
        for (VideoCastConsumer onRemoteMediaPreloadStatusUpdated : this.mVideoConsumers) {
            onRemoteMediaPreloadStatusUpdated.onRemoteMediaPreloadStatusUpdated(mediaQueueItem);
        }
    }

    public MediaQueueItem getPreLoadingItem() {
        return this.mPreLoadingItem;
    }

    private void onQueueUpdated(List<MediaQueueItem> list, MediaQueueItem mediaQueueItem, int i, boolean z) {
        int i2;
        LogUtils.LOGD(TAG, "onQueueUpdated() reached");
        String str = TAG;
        String str2 = "Queue Items size: %d, Item: %s, Repeat Mode: %d, Shuffle: %s";
        Object[] objArr = new Object[4];
        if (list == null) {
            i2 = 0;
        } else {
            i2 = list.size();
        }
        objArr[0] = Integer.valueOf(i2);
        objArr[1] = mediaQueueItem;
        objArr[2] = Integer.valueOf(i);
        objArr[3] = Boolean.valueOf(z);
        LogUtils.LOGD(str, String.format(str2, objArr));
        if (list != null) {
            this.mMediaQueue = new MediaQueue(new CopyOnWriteArrayList(list), mediaQueueItem, z, i);
        } else {
            this.mMediaQueue = new MediaQueue(new CopyOnWriteArrayList(), null, false, 0);
        }
        for (VideoCastConsumer onMediaQueueUpdated : this.mVideoConsumers) {
            onMediaQueueUpdated.onMediaQueueUpdated(list, mediaQueueItem, i, z);
        }
    }

    public void onRemoteMediaPlayerMetadataUpdated() {
        LogUtils.LOGD(TAG, "onRemoteMediaPlayerMetadataUpdated() reached");
        updateMediaSessionMetadata();
        MediaStatus mediaStatus = this.mRemoteMediaPlayer.getMediaStatus();
        if (mediaStatus != null) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("status=");
            stringBuilder.append(mediaStatus.getPlayerState());
            LogUtils.d(str, stringBuilder.toString());
        }
        for (VideoCastConsumer onRemoteMediaPlayerMetadataUpdated : this.mVideoConsumers) {
            onRemoteMediaPlayerMetadataUpdated.onRemoteMediaPlayerMetadataUpdated();
        }
        try {
            updateLockScreenImage(getRemoteMediaInformation());
        } catch (Throwable e) {
            LogUtils.LOGE(TAG, "Failed to update lock screen metadata due to a network issue", e);
        }
    }

    public Token getMediaSessionCompatToken() {
        return this.mMediaSessionCompat == null ? null : this.mMediaSessionCompat.getSessionToken();
    }

    @SuppressLint({"InlinedApi"})
    private void setUpMediaSession(MediaInfo mediaInfo) {
        if (isFeatureEnabled(2)) {
            if (this.mMediaSessionCompat == null) {
                this.mMediaSessionCompat = new MediaSessionCompat(this.mContext, "TAG", new ComponentName(this.mContext, VideoIntentReceiver.class.getName()), null);
                this.mMediaSessionCompat.setFlags(3);
                this.mMediaSessionCompat.setActive(true);
                this.mMediaSessionCompat.setCallback(new Callback() {
                    public boolean onMediaButtonEvent(Intent intent) {
                        KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
                        if (keyEvent != null && (keyEvent.getKeyCode() == 127 || keyEvent.getKeyCode() == 126)) {
                            toggle();
                        }
                        return true;
                    }

                    public void onPlay() {
                        toggle();
                    }

                    public void onPause() {
                        toggle();
                    }

                    private void toggle() {
                        try {
                            VideoCastManager.this.togglePlayback();
                        } catch (Throwable e) {
                            LogUtils.LOGE(VideoCastManager.TAG, "MediaSessionCompat.Callback(): Failed to toggle playback", e);
                        }
                    }
                });
            }
            this.mAudioManager.requestAudioFocus(null, 3, 3);
            PendingIntent castControllerPendingIntent = getCastControllerPendingIntent();
            if (castControllerPendingIntent != null) {
                this.mMediaSessionCompat.setSessionActivity(castControllerPendingIntent);
            }
            if (mediaInfo == null) {
                this.mMediaSessionCompat.setPlaybackState(new Builder().setState(0, 0, 1.0f).build());
            } else {
                this.mMediaSessionCompat.setPlaybackState(new Builder().setState(3, 0, 1.0f).setActions(512).build());
            }
            updateLockScreenImage(mediaInfo);
            updateMediaSessionMetadata();
            this.mMediaRouter.setMediaSessionCompat(this.mMediaSessionCompat);
        }
    }

    private android.app.PendingIntent getCastControllerPendingIntent() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = this;
        r0 = r4.getRemoteMediaInformation();	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r0 = com.google.android.libraries.cast.companionlibrary.utils.Utils.mediaInfoToBundle(r0);	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r1 = new android.content.Intent;	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r2 = r4.mContext;	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r3 = r4.mTargetActivity;	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r1.<init>(r2, r3);	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r2 = "media";	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r1.putExtra(r2, r0);	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r0 = r4.mContext;	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r2 = 0;	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r3 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        r0 = android.app.PendingIntent.getActivity(r0, r2, r1, r3);	 Catch:{ TransientNetworkDisconnectionException -> 0x0020, TransientNetworkDisconnectionException -> 0x0020 }
        return r0;
    L_0x0020:
        r0 = TAG;
        r1 = "getCastControllerPendingIntent(): Failed to get the remote media information";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r0, r1);
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager.getCastControllerPendingIntent():android.app.PendingIntent");
    }

    private void updateLockScreenImage(MediaInfo mediaInfo) {
        if (mediaInfo != null) {
            setBitmapForLockScreen(mediaInfo);
        }
    }

    private void setBitmapForLockScreen(MediaInfo mediaInfo) {
        if (mediaInfo != null) {
            if (this.mMediaSessionCompat != null) {
                Point displaySize;
                Bitmap bitmap = null;
                mediaInfo = mediaInfo.getMetadata() != null ? mediaInfo.getMetadata().getImages() : null;
                if (mediaInfo != null) {
                    if (VERSION.SDK_INT > 18) {
                        if (mediaInfo.size() > 1) {
                            mediaInfo = ((WebImage) mediaInfo.get(1)).getUrl();
                        } else if (mediaInfo.size() == 1) {
                            mediaInfo = ((WebImage) mediaInfo.get(0)).getUrl();
                        } else if (this.mContext != null) {
                            mediaInfo = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.album_art_placeholder_large);
                        }
                        if (bitmap == null) {
                            mediaInfo = this.mMediaSessionCompat.getController().getMetadata();
                            this.mMediaSessionCompat.setMetadata((mediaInfo != null ? new MediaMetadataCompat.Builder() : new MediaMetadataCompat.Builder(mediaInfo)).putBitmap(MediaMetadataCompat.METADATA_KEY_ART, bitmap).build());
                        } else {
                            if (this.mLockScreenFetchTask != null) {
                                this.mLockScreenFetchTask.cancel(true);
                            }
                            displaySize = Utils.getDisplaySize(this.mContext);
                            this.mLockScreenFetchTask = new FetchBitmapTask(displaySize.x, displaySize.y, false) {
                                protected void onPostExecute(Bitmap bitmap) {
                                    if (!(bitmap == null || VideoCastManager.this.mMediaSessionCompat == null)) {
                                        MediaMetadataCompat metadata = VideoCastManager.this.mMediaSessionCompat.getController().getMetadata();
                                        VideoCastManager.this.mMediaSessionCompat.setMetadata((metadata == null ? new MediaMetadataCompat.Builder() : new MediaMetadataCompat.Builder(metadata)).putBitmap(MediaMetadataCompat.METADATA_KEY_ART, bitmap).build());
                                    }
                                    VideoCastManager.this.mLockScreenFetchTask = null;
                                }
                            };
                            this.mLockScreenFetchTask.execute(mediaInfo);
                        }
                    } else if (mediaInfo.isEmpty()) {
                        mediaInfo = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.album_art_placeholder);
                    } else {
                        mediaInfo = ((WebImage) mediaInfo.get(0)).getUrl();
                        if (bitmap == null) {
                            if (this.mLockScreenFetchTask != null) {
                                this.mLockScreenFetchTask.cancel(true);
                            }
                            displaySize = Utils.getDisplaySize(this.mContext);
                            this.mLockScreenFetchTask = /* anonymous class already generated */;
                            this.mLockScreenFetchTask.execute(mediaInfo);
                        } else {
                            mediaInfo = this.mMediaSessionCompat.getController().getMetadata();
                            if (mediaInfo != null) {
                            }
                            this.mMediaSessionCompat.setMetadata((mediaInfo != null ? new MediaMetadataCompat.Builder() : new MediaMetadataCompat.Builder(mediaInfo)).putBitmap(MediaMetadataCompat.METADATA_KEY_ART, bitmap).build());
                        }
                    }
                    bitmap = mediaInfo;
                    mediaInfo = null;
                    if (bitmap == null) {
                        mediaInfo = this.mMediaSessionCompat.getController().getMetadata();
                        if (mediaInfo != null) {
                        }
                        this.mMediaSessionCompat.setMetadata((mediaInfo != null ? new MediaMetadataCompat.Builder() : new MediaMetadataCompat.Builder(mediaInfo)).putBitmap(MediaMetadataCompat.METADATA_KEY_ART, bitmap).build());
                    } else {
                        if (this.mLockScreenFetchTask != null) {
                            this.mLockScreenFetchTask.cancel(true);
                        }
                        displaySize = Utils.getDisplaySize(this.mContext);
                        this.mLockScreenFetchTask = /* anonymous class already generated */;
                        this.mLockScreenFetchTask.execute(mediaInfo);
                    }
                }
                mediaInfo = null;
                if (bitmap == null) {
                    if (this.mLockScreenFetchTask != null) {
                        this.mLockScreenFetchTask.cancel(true);
                    }
                    displaySize = Utils.getDisplaySize(this.mContext);
                    this.mLockScreenFetchTask = /* anonymous class already generated */;
                    this.mLockScreenFetchTask.execute(mediaInfo);
                } else {
                    mediaInfo = this.mMediaSessionCompat.getController().getMetadata();
                    if (mediaInfo != null) {
                    }
                    this.mMediaSessionCompat.setMetadata((mediaInfo != null ? new MediaMetadataCompat.Builder() : new MediaMetadataCompat.Builder(mediaInfo)).putBitmap(MediaMetadataCompat.METADATA_KEY_ART, bitmap).build());
                }
            }
        }
    }

    @TargetApi(14)
    private void updateMediaSession(boolean z) {
        int i = 2;
        if (isFeatureEnabled(2) && isConnected()) {
            try {
                if (this.mMediaSessionCompat == null && z) {
                    setUpMediaSession(getRemoteMediaInformation());
                }
                if (this.mMediaSessionCompat != null) {
                    int i2 = isRemoteStreamLive() ? 6 : 3;
                    if (z) {
                        i = i2;
                    }
                    z = getCastControllerPendingIntent();
                    if (z) {
                        this.mMediaSessionCompat.setSessionActivity(z);
                    }
                    this.mMediaSessionCompat.setPlaybackState(new Builder().setState(i, 0, 1.0f).setActions(512).build());
                }
            } catch (boolean z2) {
                LogUtils.LOGE(TAG, "Failed to set up MediaSessionCompat due to network issues", z2);
            }
        }
    }

    private void updateMediaSessionMetadata() {
        if (this.mMediaSessionCompat != null) {
            if (isFeatureEnabled(2)) {
                try {
                    MediaInfo remoteMediaInformation = getRemoteMediaInformation();
                    if (remoteMediaInformation != null) {
                        MediaMetadata metadata = remoteMediaInformation.getMetadata();
                        MediaMetadataCompat metadata2 = this.mMediaSessionCompat.getController().getMetadata();
                        MediaMetadataCompat.Builder builder = metadata2 == null ? new MediaMetadataCompat.Builder() : new MediaMetadataCompat.Builder(metadata2);
                        this.mMediaSessionCompat.setMetadata(builder.putString("android.media.metadata.TITLE", metadata.getString(MediaMetadata.KEY_TITLE)).putString("android.media.metadata.ALBUM_ARTIST", this.mContext.getResources().getString(R.string.ccl_casting_to_device, new Object[]{getDeviceName()})).putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, metadata.getString(MediaMetadata.KEY_TITLE)).putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, metadata.getString(MediaMetadata.KEY_SUBTITLE)).putLong("android.media.metadata.DURATION", remoteMediaInformation.getStreamDuration()).build());
                        Uri url = metadata.hasImages() ? ((WebImage) metadata.getImages().get(0)).getUrl() : null;
                        if (url == null) {
                            this.mMediaSessionCompat.setMetadata(builder.putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.album_art_placeholder)).build());
                        } else {
                            if (this.mMediaSessionIconFetchTask != null) {
                                this.mMediaSessionIconFetchTask.cancel(true);
                            }
                            this.mMediaSessionIconFetchTask = new FetchBitmapTask() {
                                protected void onPostExecute(Bitmap bitmap) {
                                    if (!(bitmap == null || VideoCastManager.this.mMediaSessionCompat == null)) {
                                        MediaMetadataCompat metadata = VideoCastManager.this.mMediaSessionCompat.getController().getMetadata();
                                        VideoCastManager.this.mMediaSessionCompat.setMetadata((metadata == null ? new MediaMetadataCompat.Builder() : new MediaMetadataCompat.Builder(metadata)).putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, bitmap).build());
                                    }
                                    VideoCastManager.this.mMediaSessionIconFetchTask = null;
                                }
                            };
                            this.mMediaSessionIconFetchTask.execute(url);
                        }
                    }
                } catch (Throwable e) {
                    LogUtils.LOGE(TAG, "Failed to update Media Session due to resource not found", e);
                } catch (Throwable e2) {
                    LogUtils.LOGE(TAG, "Failed to update Media Session due to network issues", e2);
                }
            }
        }
    }

    public void clearMediaSession() {
        LogUtils.LOGD(TAG, "clearMediaSession()");
        if (isFeatureEnabled(2)) {
            if (this.mLockScreenFetchTask != null) {
                this.mLockScreenFetchTask.cancel(true);
            }
            if (this.mMediaSessionIconFetchTask != null) {
                this.mMediaSessionIconFetchTask.cancel(true);
            }
            this.mAudioManager.abandonAudioFocus(null);
            if (this.mMediaSessionCompat != null) {
                this.mMediaSessionCompat.setMetadata(null);
                this.mMediaSessionCompat.setPlaybackState(new Builder().setState(0, 0, 1.0f).build());
                this.mMediaSessionCompat.release();
                this.mMediaSessionCompat.setActive(false);
                this.mMediaSessionCompat = null;
            }
        }
    }

    public synchronized void addVideoCastConsumer(VideoCastConsumer videoCastConsumer) {
        if (videoCastConsumer != null) {
            addBaseCastConsumer(videoCastConsumer);
            this.mVideoConsumers.add(videoCastConsumer);
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Successfully added the new CastConsumer listener ");
            stringBuilder.append(videoCastConsumer);
            LogUtils.LOGD(str, stringBuilder.toString());
        }
    }

    public synchronized void removeVideoCastConsumer(VideoCastConsumer videoCastConsumer) {
        if (videoCastConsumer != null) {
            removeBaseCastConsumer(videoCastConsumer);
            this.mVideoConsumers.remove(videoCastConsumer);
        }
    }

    public synchronized void addProgressWatcher(ProgressWatcher progressWatcher) {
        if (progressWatcher != null) {
            this.mProgressWatchers.add(progressWatcher);
        }
    }

    public synchronized void removeProgressWatcher(ProgressWatcher progressWatcher) {
        if (progressWatcher != null) {
            this.mProgressWatchers.remove(progressWatcher);
        }
    }

    public void addMiniController(IMiniController iMiniController, OnMiniControllerChangedListener onMiniControllerChangedListener) {
        if (iMiniController != null) {
            boolean add;
            synchronized (this.mMiniControllers) {
                add = this.mMiniControllers.add(iMiniController);
            }
            StringBuilder stringBuilder;
            if (add) {
                if (onMiniControllerChangedListener == null) {
                    onMiniControllerChangedListener = this;
                }
                iMiniController.setOnMiniControllerChangedListener(onMiniControllerChangedListener);
                try {
                    if (!(isConnected() == null || isRemoteMediaLoaded() == null)) {
                        updateMiniController(iMiniController);
                        iMiniController.setVisibility(null);
                    }
                } catch (OnMiniControllerChangedListener onMiniControllerChangedListener2) {
                    LogUtils.LOGE(TAG, "Failed to get the status of media playback on receiver", onMiniControllerChangedListener2);
                }
                onMiniControllerChangedListener2 = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Successfully added the new MiniController ");
                stringBuilder.append(iMiniController);
                LogUtils.LOGD(onMiniControllerChangedListener2, stringBuilder.toString());
                return;
            }
            onMiniControllerChangedListener2 = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Attempting to adding ");
            stringBuilder.append(iMiniController);
            stringBuilder.append(" but it was already registered, skipping this step");
            LogUtils.LOGD(onMiniControllerChangedListener2, stringBuilder.toString());
        }
    }

    public void addMiniController(IMiniController iMiniController) {
        addMiniController(iMiniController, null);
    }

    public void removeMiniController(IMiniController iMiniController) {
        if (iMiniController != null) {
            iMiniController.setOnMiniControllerChangedListener(null);
            synchronized (this.mMiniControllers) {
                this.mMiniControllers.remove(iMiniController);
            }
        }
    }

    protected void onDeviceUnselected() {
        stopNotificationService();
        detachMediaChannel();
        removeDataChannel();
        this.mState = 1;
        this.mMediaStatus = null;
    }

    protected CastOptions.Builder getCastOptionBuilder(CastDevice castDevice) {
        castDevice = CastOptions.builder(this.mSelectedCastDevice, new CastListener());
        if (isFeatureEnabled(1)) {
            castDevice.setVerboseLoggingEnabled(true);
        }
        return castDevice;
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        super.onConnectionFailed(connectionResult);
        updateMediaSession(null);
        this.mState = 1;
        this.mMediaStatus = null;
        stopNotificationService();
    }

    public void onDisconnected(boolean z, boolean z2, boolean z3) {
        super.onDisconnected(z, z2, z3);
        updateMiniControllersVisibility(false);
        if (z2 && !this.mConnectionSuspended) {
            clearMediaSession();
        }
        this.mState = true;
        this.mMediaStatus = null;
        this.mMediaQueue = null;
    }

    public void onFailed(int i, int i2) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onFailed: ");
        stringBuilder.append(this.mContext.getString(i));
        stringBuilder.append(", code: ");
        stringBuilder.append(i2);
        LogUtils.LOGD(str, stringBuilder.toString());
        super.onFailed(i, i2);
    }

    public Class<?> getTargetActivity() {
        return this.mTargetActivity;
    }

    public boolean onDispatchVolumeKeyEvent(KeyEvent keyEvent, double d) {
        if (isConnected()) {
            boolean z = keyEvent.getAction() == 0;
            switch (keyEvent.getKeyCode()) {
                case 24:
                    if (changeVolume(d, z) != null) {
                        return true;
                    }
                    break;
                case 25:
                    if (changeVolume(-d, z) != null) {
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    private boolean changeVolume(double d, boolean z) {
        if (VERSION.SDK_INT >= 16 && getPlaybackStatus() == 2 && isFeatureEnabled(2)) {
            return 0.0d;
        }
        if (z) {
            try {
                adjustVolume(d);
            } catch (double d2) {
                LogUtils.LOGE(TAG, "Failed to change volume", d2);
            }
        }
        return Double.MIN_VALUE;
    }

    public VideoCastManager setVolumeStep(double d) {
        if (d > 1.0d || d < 0.0d) {
            throw new IllegalArgumentException("Volume Step should be between 0 and 1, inclusive");
        }
        this.mVolumeStep = d;
        return this;
    }

    public double getVolumeStep() {
        return this.mVolumeStep;
    }

    public void setLiveStreamDuration(long j) {
        this.mLiveStreamDuration = j;
    }

    public void setActiveTracks(List<MediaTrack> list) {
        long[] jArr;
        int i = 0;
        if (list.isEmpty()) {
            jArr = new long[0];
        } else {
            jArr = new long[list.size()];
            while (i < list.size()) {
                jArr[i] = ((MediaTrack) list.get(i)).getId();
                i++;
            }
        }
        setActiveTrackIds(jArr);
        if (list.size() > null) {
            setTextTrackStyle(getTracksPreferenceManager().getTextTrackStyle());
        }
    }

    public void setActiveTrackIds(long[] jArr) {
        if (this.mRemoteMediaPlayer != null) {
            if (this.mRemoteMediaPlayer.getMediaInfo() != null) {
                this.mRemoteMediaPlayer.setActiveMediaTracks(this.mApiClient, jArr).setResultCallback(new ResultCallback<MediaChannelResult>() {
                    public void onResult(MediaChannelResult mediaChannelResult) {
                        String access$100 = VideoCastManager.TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Setting track result was successful? ");
                        stringBuilder.append(mediaChannelResult.getStatus().isSuccess());
                        LogUtils.LOGD(access$100, stringBuilder.toString());
                        if (!mediaChannelResult.getStatus().isSuccess()) {
                            access$100 = VideoCastManager.TAG;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Failed since: ");
                            stringBuilder.append(mediaChannelResult.getStatus());
                            stringBuilder.append(" and status code:");
                            stringBuilder.append(mediaChannelResult.getStatus().getStatusCode());
                            LogUtils.LOGD(access$100, stringBuilder.toString());
                        }
                    }
                });
            }
        }
    }

    public void setTextTrackStyle(TextTrackStyle textTrackStyle) {
        this.mRemoteMediaPlayer.setTextTrackStyle(this.mApiClient, textTrackStyle).setResultCallback(new ResultCallback<MediaChannelResult>() {
            public void onResult(MediaChannelResult mediaChannelResult) {
                if (!mediaChannelResult.getStatus().isSuccess()) {
                    VideoCastManager.this.onFailed(R.string.ccl_failed_to_set_track_style, mediaChannelResult.getStatus().getStatusCode());
                }
            }
        });
        for (VideoCastConsumer videoCastConsumer : this.mVideoConsumers) {
            try {
                videoCastConsumer.onTextTrackStyleChanged(textTrackStyle);
            } catch (Throwable e) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onTextTrackStyleChanged(): Failed to inform ");
                stringBuilder.append(videoCastConsumer);
                LogUtils.LOGE(str, stringBuilder.toString(), e);
            }
        }
    }

    public void onTextTrackStyleChanged(TextTrackStyle textTrackStyle) {
        LogUtils.LOGD(TAG, "onTextTrackStyleChanged() reached");
        if (this.mRemoteMediaPlayer != null) {
            if (this.mRemoteMediaPlayer.getMediaInfo() != null) {
                this.mRemoteMediaPlayer.setTextTrackStyle(this.mApiClient, textTrackStyle).setResultCallback(new ResultCallback<MediaChannelResult>() {
                    public void onResult(MediaChannelResult mediaChannelResult) {
                        if (!mediaChannelResult.getStatus().isSuccess()) {
                            VideoCastManager.this.onFailed(R.string.ccl_failed_to_set_track_style, mediaChannelResult.getStatus().getStatusCode());
                        }
                    }
                });
                for (VideoCastConsumer videoCastConsumer : this.mVideoConsumers) {
                    try {
                        videoCastConsumer.onTextTrackStyleChanged(textTrackStyle);
                    } catch (Throwable e) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("onTextTrackStyleChanged(): Failed to inform ");
                        stringBuilder.append(videoCastConsumer);
                        LogUtils.LOGE(str, stringBuilder.toString(), e);
                    }
                }
            }
        }
    }

    public void onTextTrackEnabledChanged(boolean z) {
        LogUtils.LOGD(TAG, "onTextTrackEnabledChanged() reached");
        if (!z) {
            setActiveTrackIds(new long[0]);
        }
        for (VideoCastConsumer onTextTrackEnabledChanged : this.mVideoConsumers) {
            onTextTrackEnabledChanged.onTextTrackEnabledChanged(z);
        }
    }

    public void onTextTrackLocaleChanged(Locale locale) {
        LogUtils.LOGD(TAG, "onTextTrackLocaleChanged() reached");
        for (VideoCastConsumer onTextTrackLocaleChanged : this.mVideoConsumers) {
            onTextTrackLocaleChanged.onTextTrackLocaleChanged(locale);
        }
    }

    @SuppressLint({"NewApi"})
    private void registerCaptionListener(Context context) {
        if (Utils.IS_KITKAT_OR_ABOVE) {
            ((CaptioningManager) context.getSystemService("captioning")).addCaptioningChangeListener(new CaptioningChangeListener() {
                public void onEnabledChanged(boolean z) {
                    VideoCastManager.this.onTextTrackEnabledChanged(z);
                }

                public void onUserStyleChanged(@NonNull CaptionStyle captionStyle) {
                    VideoCastManager.this.onTextTrackStyleChanged(VideoCastManager.this.mTrackManager.getTextTrackStyle());
                }

                public void onFontScaleChanged(float f) {
                    VideoCastManager.this.onTextTrackStyleChanged(VideoCastManager.this.mTrackManager.getTextTrackStyle());
                }

                public void onLocaleChanged(Locale locale) {
                    VideoCastManager.this.onTextTrackLocaleChanged(locale);
                }
            });
        }
    }

    public void updateCaptionSummary(String str, PreferenceScreen preferenceScreen) {
        int i = R.string.ccl_info_na;
        if (isFeatureEnabled(16)) {
            i = this.mTrackManager.isCaptionEnabled() ? R.string.ccl_on : R.string.ccl_off;
        }
        preferenceScreen.findPreference(str).setSummary(i);
    }

    public TracksPreferenceManager getTracksPreferenceManager() {
        return this.mTrackManager;
    }

    public long[] getActiveTrackIds() {
        return (this.mRemoteMediaPlayer == null || this.mRemoteMediaPlayer.getMediaStatus() == null) ? null : this.mRemoteMediaPlayer.getMediaStatus().getActiveTrackIds();
    }

    public void addTracksSelectedListener(OnTracksSelectedListener onTracksSelectedListener) {
        if (onTracksSelectedListener != null) {
            this.mTracksSelectedListeners.add(onTracksSelectedListener);
        }
    }

    public void removeTracksSelectedListener(OnTracksSelectedListener onTracksSelectedListener) {
        if (onTracksSelectedListener != null) {
            this.mTracksSelectedListeners.remove(onTracksSelectedListener);
        }
    }

    public void notifyTracksSelectedListeners(List<MediaTrack> list) {
        if (list == null) {
            throw new IllegalArgumentException("tracks must not be null");
        } else if (this.mTracksSelectedListeners.isEmpty()) {
            setActiveTracks(list);
        } else {
            for (OnTracksSelectedListener onTracksSelected : this.mTracksSelectedListeners) {
                onTracksSelected.onTracksSelected(list);
            }
        }
    }

    public final MediaQueue getMediaQueue() {
        return this.mMediaQueue;
    }

    private void stopProgressTimer() {
        LogUtils.LOGD(TAG, "Stopped TrickPlay Timer");
        if (this.mProgressTask != null) {
            this.mProgressTask.cancel();
            this.mProgressTask = null;
        }
        if (this.mProgressTimer != null) {
            this.mProgressTimer.cancel();
            this.mProgressTimer = null;
        }
    }

    private void restartProgressTimer() {
        stopProgressTimer();
        this.mProgressTimer = new Timer();
        this.mProgressTask = new UpdateProgressTask();
        this.mProgressTimer.scheduleAtFixedRate(this.mProgressTask, 100, PROGRESS_UPDATE_INTERVAL_MS);
        LogUtils.LOGD(TAG, "Restarted Progress Timer");
    }

    private void updateProgress(int i, int i2) {
        synchronized (this.mMiniControllers) {
            for (IMiniController progress : this.mMiniControllers) {
                progress.setProgress(i, i2);
            }
        }
        for (ProgressWatcher progress2 : this.mProgressWatchers) {
            progress2.setProgress(i, i2);
        }
    }

    protected String getDataNamespace() {
        return this.mDataNamespace;
    }
}
