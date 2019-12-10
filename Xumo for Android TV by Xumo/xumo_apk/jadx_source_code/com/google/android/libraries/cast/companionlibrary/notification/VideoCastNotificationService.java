package com.google.android.libraries.cast.companionlibrary.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import androidx.core.app.NotificationCompat.Action;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.TaskStackBuilder;
import androidx.media.app.NotificationCompat.MediaStyle;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.common.images.WebImage;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.cast.MediaQueue;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumerImpl;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.CastException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.google.android.libraries.cast.companionlibrary.remotecontrol.VideoIntentReceiver;
import com.google.android.libraries.cast.companionlibrary.utils.FetchBitmapTask;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import com.google.android.libraries.cast.companionlibrary.utils.ResponseCustomData;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VideoCastNotificationService extends Service {
    public static final String ACTION_FORWARD = "com.google.android.libraries.cast.companionlibrary.action.forward";
    public static final String ACTION_PLAY_NEXT = "com.google.android.libraries.cast.companionlibrary.action.playnext";
    public static final String ACTION_PLAY_PREV = "com.google.android.libraries.cast.companionlibrary.action.playprev";
    public static final String ACTION_REWIND = "com.google.android.libraries.cast.companionlibrary.action.rewind";
    public static final String ACTION_STOP = "com.google.android.libraries.cast.companionlibrary.action.stop";
    public static final String ACTION_TOGGLE_PLAYBACK = "com.google.android.libraries.cast.companionlibrary.action.toggleplayback";
    public static final String ACTION_VISIBILITY = "com.google.android.libraries.cast.companionlibrary.action.notificationvisibility";
    public static final String EXTRA_FORWARD_STEP_MS = "ccl_extra_forward_step_ms";
    protected static final int NOTIFICATION_ID = 1;
    public static final String NOTIFICATION_VISIBILITY = "visible";
    private static final String TAG = LogUtils.makeLogTag(VideoCastNotificationService.class);
    private static final long TEN_SECONDS_MILLIS = TimeUnit.SECONDS.toMillis(10);
    private static final long THIRTY_SECONDS_MILLIS = TimeUnit.SECONDS.toMillis(30);
    private FetchBitmapTask mBitmapDecoderTask;
    protected VideoCastManager mCastManager;
    private VideoCastConsumerImpl mConsumer;
    private int mDimensionInPixels;
    private long mForwardTimeInMillis;
    private boolean mHasNext;
    private boolean mHasPrev;
    private boolean mIsPlaying;
    protected Notification mNotification;
    private List<Integer> mNotificationActions;
    private int[] mNotificationCompactActionsArray;
    private int mOldStatus = -1;
    private Class<?> mTargetActivity;
    private Bitmap mVideoArtBitmap;
    private boolean mVisible;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.mDimensionInPixels = Utils.convertDpToPixel(this, getResources().getDimension(R.dimen.ccl_notification_image_size));
        this.mCastManager = VideoCastManager.getInstance();
        readPersistedData();
        if (!(this.mCastManager.isConnected() || this.mCastManager.isConnecting())) {
            this.mCastManager.reconnectSessionIfPossible();
        }
        MediaQueue mediaQueue = this.mCastManager.getMediaQueue();
        if (mediaQueue != null) {
            int currentItemPosition = mediaQueue.getCurrentItemPosition();
            boolean z = true;
            this.mHasNext = currentItemPosition < mediaQueue.getCount() - 1;
            if (currentItemPosition <= 0) {
                z = false;
            }
            this.mHasPrev = z;
        }
        this.mConsumer = new VideoCastConsumerImpl() {
            public void onApplicationDisconnected(int i) {
                LogUtils.LOGD(VideoCastNotificationService.TAG, "onApplicationDisconnected() was reached, stopping the notification service");
                VideoCastNotificationService.this.stopSelf();
            }

            public void onDisconnected() {
                VideoCastNotificationService.this.stopSelf();
            }

            public void onRemoteMediaPlayerStatusUpdated() {
                VideoCastNotificationService.this.onRemoteMediaPlayerStatusUpdated(VideoCastNotificationService.this.mCastManager.getPlaybackStatus());
            }

            public void onUiVisibilityChanged(boolean z) {
                VideoCastNotificationService.this.mVisible = z ^ true;
                if (!VideoCastNotificationService.this.mNotification) {
                    try {
                        VideoCastNotificationService.this.setUpNotification(VideoCastNotificationService.this.mCastManager.getRemoteMediaInformation());
                    } catch (boolean z2) {
                        LogUtils.LOGE(VideoCastNotificationService.TAG, "onStartCommand() failed to get media", z2);
                    }
                }
                if (VideoCastNotificationService.this.mVisible && VideoCastNotificationService.this.mNotification) {
                    VideoCastNotificationService.this.startForeground(1, VideoCastNotificationService.this.mNotification);
                } else {
                    VideoCastNotificationService.this.stopForeground(true);
                }
            }

            public void onMediaQueueUpdated(List<MediaQueueItem> list, MediaQueueItem mediaQueueItem, int i, boolean z) {
                i = 0;
                if (list != null) {
                    z = list.size();
                    list = list.indexOf(mediaQueueItem);
                } else {
                    list = null;
                    z = false;
                }
                VideoCastNotificationService.this.mHasNext = list < z - true;
                mediaQueueItem = VideoCastNotificationService.this;
                if (list > null) {
                    i = 1;
                }
                mediaQueueItem.mHasPrev = i;
            }
        };
        this.mCastManager.addVideoCastConsumer(this.mConsumer);
        this.mNotificationActions = this.mCastManager.getCastConfiguration().getNotificationActions();
        List notificationCompactActions = this.mCastManager.getCastConfiguration().getNotificationCompactActions();
        if (notificationCompactActions != null) {
            this.mNotificationCompactActionsArray = new int[notificationCompactActions.size()];
            for (int i = 0; i < notificationCompactActions.size(); i++) {
                this.mNotificationCompactActionsArray[i] = ((Integer) notificationCompactActions.get(i)).intValue();
            }
        }
        this.mForwardTimeInMillis = TimeUnit.SECONDS.toMillis((long) this.mCastManager.getCastConfiguration().getForwardStep());
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        LogUtils.LOGD(TAG, "onStartCommand");
        if (intent != null) {
            if (ACTION_VISIBILITY.equals(intent.getAction()) != 0) {
                this.mVisible = intent.getBooleanExtra(NOTIFICATION_VISIBILITY, false);
                intent = TAG;
                i2 = new StringBuilder();
                i2.append("onStartCommand(): Action: ACTION_VISIBILITY ");
                i2.append(this.mVisible);
                LogUtils.LOGD(intent, i2.toString());
                onRemoteMediaPlayerStatusUpdated(this.mCastManager.getPlaybackStatus());
                if (this.mNotification == null) {
                    try {
                        setUpNotification(this.mCastManager.getRemoteMediaInformation());
                    } catch (Intent intent2) {
                        LogUtils.LOGE(TAG, "onStartCommand() failed to get media", intent2);
                    }
                }
                if (this.mVisible == null || this.mNotification == null) {
                    stopForeground(true);
                } else {
                    startForeground(1, this.mNotification);
                }
            }
        }
        return 1;
    }

    private void setUpNotification(final MediaInfo mediaInfo) throws TransientNetworkDisconnectionException, NoConnectionException {
        if (mediaInfo != null) {
            Uri adThumbnailUri;
            if (this.mBitmapDecoderTask != null) {
                this.mBitmapDecoderTask.cancel(false);
            }
            ResponseCustomData responseCustomData = new ResponseCustomData(mediaInfo.getCustomData());
            if (responseCustomData.isAd) {
                adThumbnailUri = Utils.getAdThumbnailUri(responseCustomData.playSubType, responseCustomData.channelId);
            } else {
                adThumbnailUri = responseCustomData.assetThumbnailUri;
            }
            try {
                if (mediaInfo.getMetadata().hasImages()) {
                    adThumbnailUri = ((WebImage) mediaInfo.getMetadata().getImages().get(0)).getUrl();
                    this.mBitmapDecoderTask = new FetchBitmapTask() {
                        protected void onPostExecute(Bitmap bitmap) {
                            try {
                                VideoCastNotificationService.this.mVideoArtBitmap = Utils.scaleAndCenterCropBitmap(bitmap, VideoCastNotificationService.this.mDimensionInPixels, VideoCastNotificationService.this.mDimensionInPixels);
                                VideoCastNotificationService.this.build(mediaInfo, VideoCastNotificationService.this.mVideoArtBitmap, VideoCastNotificationService.this.mIsPlaying);
                            } catch (Bitmap bitmap2) {
                                String access$000 = VideoCastNotificationService.TAG;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Failed to set notification for ");
                                stringBuilder.append(mediaInfo.toString());
                                LogUtils.LOGE(access$000, stringBuilder.toString(), bitmap2);
                            }
                            if (!(VideoCastNotificationService.this.mVisible == null || VideoCastNotificationService.this.mNotification == null)) {
                                VideoCastNotificationService.this.startForeground(1, VideoCastNotificationService.this.mNotification);
                            }
                            if (this == VideoCastNotificationService.this.mBitmapDecoderTask) {
                                VideoCastNotificationService.this.mBitmapDecoderTask = null;
                            }
                        }
                    };
                    this.mBitmapDecoderTask.execute(adThumbnailUri);
                    return;
                }
                build(mediaInfo, null, this.mIsPlaying);
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, "Failed to build notification", e);
            }
        }
    }

    private void removeNotification() {
        ((NotificationManager) getSystemService("notification")).cancel(1);
    }

    protected void onRemoteMediaPlayerStatusUpdated(int i) {
        if (this.mOldStatus != i) {
            this.mOldStatus = i;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onRemoteMediaPlayerStatusUpdated() reached with status: ");
            stringBuilder.append(i);
            LogUtils.LOGD(str, stringBuilder.toString());
            switch (i) {
                case 0:
                    this.mIsPlaying = false;
                    stopForeground(true);
                    break;
                case 1:
                    this.mIsPlaying = false;
                    if (this.mCastManager.shouldRemoteUiBeVisible(i, this.mCastManager.getIdleReason()) != 0) {
                        setUpNotification(this.mCastManager.getRemoteMediaInformation());
                        break;
                    } else {
                        stopForeground(true);
                        break;
                    }
                case 2:
                    this.mIsPlaying = true;
                    setUpNotification(this.mCastManager.getRemoteMediaInformation());
                    break;
                case 3:
                    this.mIsPlaying = false;
                    setUpNotification(this.mCastManager.getRemoteMediaInformation());
                    break;
                case 4:
                    try {
                        this.mIsPlaying = false;
                        setUpNotification(this.mCastManager.getRemoteMediaInformation());
                        break;
                    } catch (int i2) {
                        LogUtils.LOGE(TAG, "Failed to update the playback status due to network issues", i2);
                        break;
                    }
                default:
                    break;
            }
        }
    }

    public void onDestroy() {
        if (this.mBitmapDecoderTask != null) {
            this.mBitmapDecoderTask.cancel(false);
        }
        removeNotification();
        if (this.mCastManager != null && this.mConsumer != null) {
            this.mCastManager.removeVideoCastConsumer(this.mConsumer);
            this.mCastManager = null;
        }
    }

    protected void build(MediaInfo mediaInfo, Bitmap bitmap, boolean z) throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        ResponseCustomData responseCustomData = new ResponseCustomData(mediaInfo.getCustomData());
        boolean z2 = responseCustomData.isAd;
        boolean isBroadcast = responseCustomData.isBroadcast();
        CharSequence charSequence = responseCustomData.assetTitle;
        if (z2) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.album_art_placeholder);
        }
        bitmap = new Builder(this).setSmallIcon(R.drawable.ic_stat_action_notification).setContentTitle(charSequence).setContentText(getResources().getString(R.string.ccl_casting_to_device, new Object[]{this.mCastManager.getDeviceName()})).setContentIntent(getContentIntent(mediaInfo)).setLargeIcon(bitmap).setStyle(new MediaStyle().setShowActionsInCompactView(this.mNotificationCompactActionsArray).setMediaSession(this.mCastManager.getMediaSessionCompatToken())).setOngoing(true).setShowWhen(false).setVisibility(1);
        for (Integer intValue : this.mNotificationActions) {
            switch (intValue.intValue()) {
                case 1:
                    bitmap.addAction(getPlayPauseAction(mediaInfo, z, isBroadcast));
                    break;
                case 2:
                    if (!(!responseCustomData.availableNextAsset || isBroadcast || z2)) {
                        bitmap.addAction(getSkipNextAction());
                        break;
                    }
                case 3:
                    if (!(!responseCustomData.availablePrevAsset || isBroadcast || z2)) {
                        bitmap.addAction(getSkipPreviousAction());
                        break;
                    }
                case 4:
                    bitmap.addAction(getDisconnectAction());
                    break;
                case 5:
                    if (!(isBroadcast || z2)) {
                        bitmap.addAction(getRewindAction(this.mForwardTimeInMillis));
                        break;
                    }
                case 6:
                    if (!(isBroadcast || z2)) {
                        bitmap.addAction(getForwardAction(this.mForwardTimeInMillis));
                        break;
                    }
                default:
                    break;
            }
        }
        this.mNotification = bitmap.build();
    }

    protected Action getForwardAction(long j) {
        Intent intent = new Intent(this, VideoIntentReceiver.class);
        intent.setAction(ACTION_FORWARD);
        intent.setPackage(getPackageName());
        intent.putExtra(EXTRA_FORWARD_STEP_MS, (int) j);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent, 134217728);
        int i = R.drawable.ic_notification_forward_48dp;
        if (j == TEN_SECONDS_MILLIS) {
            i = R.drawable.ic_notification_forward10_48dp;
        } else if (j == THIRTY_SECONDS_MILLIS) {
            i = R.drawable.ic_notification_forward30_48dp;
        }
        return new Action.Builder(i, getString(R.string.ccl_forward), broadcast).build();
    }

    protected Action getRewindAction(long j) {
        Intent intent = new Intent(this, VideoIntentReceiver.class);
        intent.setAction(ACTION_REWIND);
        intent.setPackage(getPackageName());
        intent.putExtra(EXTRA_FORWARD_STEP_MS, (int) (-j));
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent, 134217728);
        int i = R.drawable.ic_notification_rewind_48dp;
        if (j == TEN_SECONDS_MILLIS) {
            i = R.drawable.ic_notification_rewind10_48dp;
        } else if (j == THIRTY_SECONDS_MILLIS) {
            i = R.drawable.ic_notification_rewind30_48dp;
        }
        return new Action.Builder(i, getString(R.string.ccl_rewind), broadcast).build();
    }

    protected Action getSkipNextAction() {
        PendingIntent pendingIntent;
        int i = R.drawable.ic_notification_skip_next_semi_48dp;
        if (this.mHasNext) {
            Intent intent = new Intent(this, VideoIntentReceiver.class);
            intent.setAction(ACTION_PLAY_NEXT);
            intent.setPackage(getPackageName());
            PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent, 0);
            pendingIntent = broadcast;
            i = R.drawable.ic_notification_skip_next_48dp;
        } else {
            pendingIntent = null;
        }
        return new Action.Builder(i, getString(R.string.ccl_skip_next), pendingIntent).build();
    }

    protected Action getSkipPreviousAction() {
        PendingIntent pendingIntent;
        int i = R.drawable.ic_notification_skip_prev_semi_48dp;
        if (this.mHasPrev) {
            Intent intent = new Intent(this, VideoIntentReceiver.class);
            intent.setAction(ACTION_PLAY_PREV);
            intent.setPackage(getPackageName());
            PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent, 0);
            pendingIntent = broadcast;
            i = R.drawable.ic_notification_skip_prev_48dp;
        } else {
            pendingIntent = null;
        }
        return new Action.Builder(i, getString(R.string.ccl_skip_previous), pendingIntent).build();
    }

    protected Action getPlayPauseAction(MediaInfo mediaInfo, boolean z, boolean z2) {
        if (mediaInfo.getStreamType() == true) {
            mediaInfo = R.drawable.ic_notification_stop_48dp;
        } else {
            mediaInfo = R.drawable.ic_notification_pause_48dp;
        }
        z2 = z ? R.string.ccl_pause : R.string.ccl_play;
        if (!z) {
            mediaInfo = R.drawable.ic_notification_play_48dp;
        }
        z = new Intent(this, VideoIntentReceiver.class);
        z.setAction(ACTION_TOGGLE_PLAYBACK);
        z.setPackage(getPackageName());
        return new Action.Builder(mediaInfo, getString(z2), PendingIntent.getBroadcast(this, 0, z, 0)).build();
    }

    protected Action getDisconnectAction() {
        Intent intent = new Intent(this, VideoIntentReceiver.class);
        intent.setAction(ACTION_STOP);
        intent.setPackage(getPackageName());
        return new Action.Builder(R.drawable.ic_notification_disconnect_24dp, getString(R.string.ccl_disconnect), PendingIntent.getBroadcast(this, 0, intent, 0)).build();
    }

    protected PendingIntent getContentIntent(MediaInfo mediaInfo) {
        mediaInfo = Utils.mediaInfoToBundle(mediaInfo);
        Intent intent = new Intent(this, this.mTargetActivity);
        intent.putExtra(VideoCastManager.EXTRA_MEDIA, mediaInfo);
        TaskStackBuilder create = TaskStackBuilder.create(this);
        create.addParentStack(this.mTargetActivity);
        create.addNextIntent(intent);
        if (create.getIntentCount() > 1) {
            create.editIntentAt(1).putExtra(VideoCastManager.EXTRA_MEDIA, mediaInfo);
        }
        return create.getPendingIntent(1, 134217728);
    }

    private void readPersistedData() {
        this.mTargetActivity = this.mCastManager.getCastConfiguration().getTargetActivity();
        if (this.mTargetActivity == null) {
            this.mTargetActivity = VideoCastManager.DEFAULT_TARGET_ACTIVITY;
        }
    }
}
