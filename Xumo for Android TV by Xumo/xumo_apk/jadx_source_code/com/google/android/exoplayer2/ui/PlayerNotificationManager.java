package com.google.android.exoplayer2.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.MediaSessionCompat.Token;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat.Action;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.NotificationCompat.Style;
import androidx.core.app.NotificationManagerCompat;
import androidx.media.app.NotificationCompat.MediaStyle;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Player.EventListener.-CC;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public class PlayerNotificationManager {
    public static final String ACTION_FAST_FORWARD = "com.google.android.exoplayer.ffwd";
    public static final String ACTION_NEXT = "com.google.android.exoplayer.next";
    public static final String ACTION_PAUSE = "com.google.android.exoplayer.pause";
    public static final String ACTION_PLAY = "com.google.android.exoplayer.play";
    public static final String ACTION_PREVIOUS = "com.google.android.exoplayer.prev";
    public static final String ACTION_REWIND = "com.google.android.exoplayer.rewind";
    public static final String ACTION_STOP = "com.google.android.exoplayer.stop";
    public static final int DEFAULT_FAST_FORWARD_MS = 15000;
    public static final int DEFAULT_REWIND_MS = 5000;
    public static final String EXTRA_INSTANCE_ID = "INSTANCE_ID";
    private static final long MAX_POSITION_FOR_SEEK_TO_PREVIOUS = 3000;
    private static int instanceIdCounter;
    private int badgeIconType;
    private final String channelId;
    private int color;
    private boolean colorized;
    private final Context context;
    private ControlDispatcher controlDispatcher;
    private int currentNotificationTag;
    @Nullable
    private final CustomActionReceiver customActionReceiver;
    private final Map<String, Action> customActions;
    private int defaults;
    private long fastForwardMs;
    private final int instanceId;
    private final IntentFilter intentFilter;
    private boolean isNotificationStarted;
    private int lastPlaybackState;
    private final Handler mainHandler;
    private final MediaDescriptionAdapter mediaDescriptionAdapter;
    @Nullable
    private Token mediaSessionToken;
    private final NotificationBroadcastReceiver notificationBroadcastReceiver;
    private final int notificationId;
    @Nullable
    private NotificationListener notificationListener;
    private final NotificationManagerCompat notificationManager;
    private boolean ongoing;
    private final Map<String, Action> playbackActions;
    @Nullable
    private Player player;
    private final EventListener playerListener;
    private int priority;
    private long rewindMs;
    @DrawableRes
    private int smallIconResourceId;
    @Nullable
    private String stopAction;
    @Nullable
    private PendingIntent stopPendingIntent;
    private boolean useChronometer;
    private boolean useNavigationActions;
    private boolean usePlayPauseActions;
    private int visibility;
    private boolean wasPlayWhenReady;

    public final class BitmapCallback {
        private final int notificationTag;

        private BitmapCallback(int i) {
            this.notificationTag = i;
        }

        public void onBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                PlayerNotificationManager.this.mainHandler.post(new -$$Lambda$PlayerNotificationManager$BitmapCallback$ai-lvTgLEQ8d7uyKftaUKVPjkgA(this, bitmap));
            }
        }

        public static /* synthetic */ void lambda$onBitmap$0(BitmapCallback bitmapCallback, Bitmap bitmap) {
            if (PlayerNotificationManager.this.player != null && bitmapCallback.notificationTag == PlayerNotificationManager.this.currentNotificationTag && PlayerNotificationManager.this.isNotificationStarted) {
                PlayerNotificationManager.this.updateNotification(bitmap);
            }
        }
    }

    public interface CustomActionReceiver {
        Map<String, Action> createCustomActions(Context context, int i);

        List<String> getCustomActions(Player player);

        void onCustomAction(Player player, String str, Intent intent);
    }

    public interface MediaDescriptionAdapter {
        @Nullable
        PendingIntent createCurrentContentIntent(Player player);

        @Nullable
        String getCurrentContentText(Player player);

        String getCurrentContentTitle(Player player);

        @Nullable
        Bitmap getCurrentLargeIcon(Player player, BitmapCallback bitmapCallback);
    }

    private class NotificationBroadcastReceiver extends BroadcastReceiver {
        private final Window window = new Window();

        public void onReceive(Context context, Intent intent) {
            context = PlayerNotificationManager.this.player;
            if (context != null && PlayerNotificationManager.this.isNotificationStarted) {
                if (intent.getIntExtra(PlayerNotificationManager.EXTRA_INSTANCE_ID, PlayerNotificationManager.this.instanceId) == PlayerNotificationManager.this.instanceId) {
                    String action = intent.getAction();
                    if (!PlayerNotificationManager.ACTION_PLAY.equals(action)) {
                        if (!PlayerNotificationManager.ACTION_PAUSE.equals(action)) {
                            if (!PlayerNotificationManager.ACTION_FAST_FORWARD.equals(action)) {
                                if (!PlayerNotificationManager.ACTION_REWIND.equals(action)) {
                                    if (PlayerNotificationManager.ACTION_NEXT.equals(action)) {
                                        intent = context.getNextWindowIndex();
                                        if (intent != -1) {
                                            PlayerNotificationManager.this.controlDispatcher.dispatchSeekTo(context, intent, C.TIME_UNSET);
                                        }
                                    } else if (PlayerNotificationManager.ACTION_PREVIOUS.equals(action)) {
                                        context.getCurrentTimeline().getWindow(context.getCurrentWindowIndex(), this.window);
                                        intent = context.getPreviousWindowIndex();
                                        if (intent == -1 || (context.getCurrentPosition() > PlayerNotificationManager.MAX_POSITION_FOR_SEEK_TO_PREVIOUS && (!this.window.isDynamic || this.window.isSeekable))) {
                                            PlayerNotificationManager.this.controlDispatcher.dispatchSeekTo(context, context.getCurrentWindowIndex(), C.TIME_UNSET);
                                        } else {
                                            PlayerNotificationManager.this.controlDispatcher.dispatchSeekTo(context, intent, C.TIME_UNSET);
                                        }
                                    } else if (PlayerNotificationManager.ACTION_STOP.equals(action)) {
                                        PlayerNotificationManager.this.controlDispatcher.dispatchStop(context, true);
                                        PlayerNotificationManager.this.stopNotification();
                                    } else if (PlayerNotificationManager.this.customActionReceiver != null && PlayerNotificationManager.this.customActions.containsKey(action)) {
                                        PlayerNotificationManager.this.customActionReceiver.onCustomAction(context, action, intent);
                                    }
                                }
                            }
                            PlayerNotificationManager.this.controlDispatcher.dispatchSeekTo(context, context.getCurrentWindowIndex(), context.getCurrentPosition() + (PlayerNotificationManager.ACTION_FAST_FORWARD.equals(action) != null ? PlayerNotificationManager.this.fastForwardMs : -PlayerNotificationManager.this.rewindMs));
                        }
                    }
                    PlayerNotificationManager.this.controlDispatcher.dispatchSetPlayWhenReady(context, PlayerNotificationManager.ACTION_PLAY.equals(action));
                }
            }
        }
    }

    public interface NotificationListener {
        void onNotificationCancelled(int i);

        void onNotificationStarted(int i, Notification notification);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    private class PlayerListener implements EventListener {
        public /* synthetic */ void onLoadingChanged(boolean z) {
            -CC.$default$onLoadingChanged(this, z);
        }

        public /* synthetic */ void onPlayerError(ExoPlaybackException exoPlaybackException) {
            -CC.$default$onPlayerError(this, exoPlaybackException);
        }

        public /* synthetic */ void onSeekProcessed() {
            -CC.$default$onSeekProcessed(this);
        }

        public /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
            -CC.$default$onShuffleModeEnabledChanged(this, z);
        }

        public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            -CC.$default$onTracksChanged(this, trackGroupArray, trackSelectionArray);
        }

        private PlayerListener() {
        }

        public void onPlayerStateChanged(boolean z, int i) {
            if (!((PlayerNotificationManager.this.wasPlayWhenReady == z || i == 1) && PlayerNotificationManager.this.lastPlaybackState == i)) {
                PlayerNotificationManager.this.startOrUpdateNotification();
            }
            PlayerNotificationManager.this.wasPlayWhenReady = z;
            PlayerNotificationManager.this.lastPlaybackState = i;
        }

        public void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i) {
            if (PlayerNotificationManager.this.player != null) {
                if (PlayerNotificationManager.this.player.getPlaybackState() != 1) {
                    PlayerNotificationManager.this.startOrUpdateNotification();
                }
            }
        }

        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            if (PlayerNotificationManager.this.player != null) {
                if (PlayerNotificationManager.this.player.getPlaybackState() != 1) {
                    PlayerNotificationManager.this.startOrUpdateNotification();
                }
            }
        }

        public void onPositionDiscontinuity(int i) {
            PlayerNotificationManager.this.startOrUpdateNotification();
        }

        public void onRepeatModeChanged(int i) {
            if (PlayerNotificationManager.this.player != 0) {
                if (PlayerNotificationManager.this.player.getPlaybackState() != 1) {
                    PlayerNotificationManager.this.startOrUpdateNotification();
                }
            }
        }
    }

    public static PlayerNotificationManager createWithNotificationChannel(Context context, String str, @StringRes int i, int i2, MediaDescriptionAdapter mediaDescriptionAdapter) {
        NotificationUtil.createNotificationChannel(context, str, i, 2);
        return new PlayerNotificationManager(context, str, i2, mediaDescriptionAdapter);
    }

    public PlayerNotificationManager(Context context, String str, int i, MediaDescriptionAdapter mediaDescriptionAdapter) {
        this(context, str, i, mediaDescriptionAdapter, null);
    }

    public PlayerNotificationManager(Context context, String str, int i, MediaDescriptionAdapter mediaDescriptionAdapter, @Nullable CustomActionReceiver customActionReceiver) {
        this.context = context.getApplicationContext();
        this.channelId = str;
        this.notificationId = i;
        this.mediaDescriptionAdapter = mediaDescriptionAdapter;
        this.customActionReceiver = customActionReceiver;
        this.controlDispatcher = new DefaultControlDispatcher();
        str = instanceIdCounter;
        instanceIdCounter = str + 1;
        this.instanceId = str;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.notificationManager = NotificationManagerCompat.from(context);
        this.playerListener = new PlayerListener();
        this.notificationBroadcastReceiver = new NotificationBroadcastReceiver();
        this.intentFilter = new IntentFilter();
        this.useNavigationActions = true;
        this.usePlayPauseActions = true;
        this.ongoing = true;
        this.colorized = true;
        this.useChronometer = true;
        this.color = 0;
        this.smallIconResourceId = R.drawable.exo_notification_small_icon;
        this.defaults = 0;
        this.priority = -1;
        this.fastForwardMs = 15000;
        this.rewindMs = 5000;
        this.stopAction = ACTION_STOP;
        this.badgeIconType = 1;
        this.visibility = 1;
        this.playbackActions = createPlaybackActions(context, this.instanceId);
        for (String addAction : this.playbackActions.keySet()) {
            this.intentFilter.addAction(addAction);
        }
        if (customActionReceiver != null) {
            context = customActionReceiver.createCustomActions(context, this.instanceId);
        } else {
            context = Collections.emptyMap();
        }
        this.customActions = context;
        for (String str2 : this.customActions.keySet()) {
            this.intentFilter.addAction(str2);
        }
        this.stopPendingIntent = ((Action) Assertions.checkNotNull(this.playbackActions.get(ACTION_STOP))).actionIntent;
    }

    public final void setPlayer(@Nullable Player player) {
        boolean z = false;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (player == null || player.getApplicationLooper() == Looper.getMainLooper()) {
            z = true;
        }
        Assertions.checkArgument(z);
        if (this.player != player) {
            if (this.player != null) {
                this.player.removeListener(this.playerListener);
                if (player == null) {
                    stopNotification();
                }
            }
            this.player = player;
            if (player != null) {
                this.wasPlayWhenReady = player.getPlayWhenReady();
                this.lastPlaybackState = player.getPlaybackState();
                player.addListener(this.playerListener);
                if (this.lastPlaybackState != 1) {
                    startOrUpdateNotification();
                }
            }
        }
    }

    public final void setControlDispatcher(ControlDispatcher controlDispatcher) {
        if (controlDispatcher == null) {
            controlDispatcher = new DefaultControlDispatcher();
        }
        this.controlDispatcher = controlDispatcher;
    }

    public final void setNotificationListener(NotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    public final void setFastForwardIncrementMs(long j) {
        if (this.fastForwardMs != j) {
            this.fastForwardMs = j;
            invalidate();
        }
    }

    public final void setRewindIncrementMs(long j) {
        if (this.rewindMs != j) {
            this.rewindMs = j;
            invalidate();
        }
    }

    public final void setUseNavigationActions(boolean z) {
        if (this.useNavigationActions != z) {
            this.useNavigationActions = z;
            invalidate();
        }
    }

    public final void setUsePlayPauseActions(boolean z) {
        if (this.usePlayPauseActions != z) {
            this.usePlayPauseActions = z;
            invalidate();
        }
    }

    public final void setStopAction(@Nullable String str) {
        if (!Util.areEqual(str, this.stopAction)) {
            this.stopAction = str;
            if (ACTION_STOP.equals(str)) {
                this.stopPendingIntent = ((Action) Assertions.checkNotNull(this.playbackActions.get(ACTION_STOP))).actionIntent;
            } else if (str != null) {
                this.stopPendingIntent = ((Action) Assertions.checkNotNull(this.customActions.get(str))).actionIntent;
            } else {
                this.stopPendingIntent = null;
            }
            invalidate();
        }
    }

    public final void setMediaSessionToken(Token token) {
        if (!Util.areEqual(this.mediaSessionToken, token)) {
            this.mediaSessionToken = token;
            invalidate();
        }
    }

    public final void setBadgeIconType(int i) {
        if (this.badgeIconType != i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                    this.badgeIconType = i;
                    invalidate();
                    return;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public final void setColorized(boolean z) {
        if (this.colorized != z) {
            this.colorized = z;
            invalidate();
        }
    }

    public final void setDefaults(int i) {
        if (this.defaults != i) {
            this.defaults = i;
            invalidate();
        }
    }

    public final void setColor(int i) {
        if (this.color != i) {
            this.color = i;
            invalidate();
        }
    }

    public final void setOngoing(boolean z) {
        if (this.ongoing != z) {
            this.ongoing = z;
            invalidate();
        }
    }

    public final void setPriority(int i) {
        if (this.priority != i) {
            switch (i) {
                case -2:
                case -1:
                case 0:
                case 1:
                case 2:
                    this.priority = i;
                    invalidate();
                    return;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public final void setSmallIcon(@DrawableRes int i) {
        if (this.smallIconResourceId != i) {
            this.smallIconResourceId = i;
            invalidate();
        }
    }

    public final void setUseChronometer(boolean z) {
        if (this.useChronometer != z) {
            this.useChronometer = z;
            invalidate();
        }
    }

    public final void setVisibility(int i) {
        if (this.visibility != i) {
            switch (i) {
                case -1:
                case 0:
                case 1:
                    this.visibility = i;
                    invalidate();
                    return;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    public void invalidate() {
        if (this.isNotificationStarted && this.player != null) {
            updateNotification(null);
        }
    }

    @RequiresNonNull({"player"})
    private Notification updateNotification(@Nullable Bitmap bitmap) {
        bitmap = createNotification(this.player, bitmap);
        this.notificationManager.notify(this.notificationId, bitmap);
        return bitmap;
    }

    private void startOrUpdateNotification() {
        if (this.player != null) {
            Notification updateNotification = updateNotification(null);
            if (!this.isNotificationStarted) {
                this.isNotificationStarted = true;
                this.context.registerReceiver(this.notificationBroadcastReceiver, this.intentFilter);
                if (this.notificationListener != null) {
                    this.notificationListener.onNotificationStarted(this.notificationId, updateNotification);
                }
            }
        }
    }

    private void stopNotification() {
        if (this.isNotificationStarted) {
            this.notificationManager.cancel(this.notificationId);
            this.isNotificationStarted = false;
            this.context.unregisterReceiver(this.notificationBroadcastReceiver);
            if (this.notificationListener != null) {
                this.notificationListener.onNotificationCancelled(this.notificationId);
            }
        }
    }

    protected Notification createNotification(Player player, @Nullable Bitmap bitmap) {
        Builder builder = new Builder(this.context, this.channelId);
        List actions = getActions(player);
        for (int i = 0; i < actions.size(); i++) {
            Action action;
            String str = (String) actions.get(i);
            if (this.playbackActions.containsKey(str)) {
                action = (Action) this.playbackActions.get(str);
            } else {
                action = (Action) this.customActions.get(str);
            }
            if (action != null) {
                builder.addAction(action);
            }
        }
        Style mediaStyle = new MediaStyle();
        if (this.mediaSessionToken != null) {
            mediaStyle.setMediaSession(this.mediaSessionToken);
        }
        mediaStyle.setShowActionsInCompactView(getActionIndicesForCompactView(actions, player));
        boolean z = this.stopAction != null;
        mediaStyle.setShowCancelButton(z);
        if (z && this.stopPendingIntent != null) {
            builder.setDeleteIntent(this.stopPendingIntent);
            mediaStyle.setCancelButtonIntent(this.stopPendingIntent);
        }
        builder.setStyle(mediaStyle);
        builder.setBadgeIconType(this.badgeIconType).setOngoing(this.ongoing).setColor(this.color).setColorized(this.colorized).setSmallIcon(this.smallIconResourceId).setVisibility(this.visibility).setPriority(this.priority).setDefaults(this.defaults);
        if (this.useChronometer && !player.isPlayingAd() && !player.isCurrentWindowDynamic() && player.getPlayWhenReady() && player.getPlaybackState() == 3) {
            builder.setWhen(System.currentTimeMillis() - player.getContentPosition()).setShowWhen(true).setUsesChronometer(true);
        } else {
            builder.setShowWhen(false).setUsesChronometer(false);
        }
        builder.setContentTitle(this.mediaDescriptionAdapter.getCurrentContentTitle(player));
        builder.setContentText(this.mediaDescriptionAdapter.getCurrentContentText(player));
        if (bitmap == null) {
            bitmap = this.mediaDescriptionAdapter;
            int i2 = this.currentNotificationTag + 1;
            this.currentNotificationTag = i2;
            bitmap = bitmap.getCurrentLargeIcon(player, new BitmapCallback(i2));
        }
        if (bitmap != null) {
            builder.setLargeIcon(bitmap);
        }
        player = this.mediaDescriptionAdapter.createCurrentContentIntent(player);
        if (player != null) {
            builder.setContentIntent(player);
        }
        return builder.build();
    }

    protected List<String> getActions(Player player) {
        boolean isPlayingAd = player.isPlayingAd();
        List<String> arrayList = new ArrayList();
        if (!isPlayingAd) {
            if (this.useNavigationActions) {
                arrayList.add(ACTION_PREVIOUS);
            }
            if (this.rewindMs > 0) {
                arrayList.add(ACTION_REWIND);
            }
        }
        if (this.usePlayPauseActions) {
            if (player.getPlayWhenReady()) {
                arrayList.add(ACTION_PAUSE);
            } else {
                arrayList.add(ACTION_PLAY);
            }
        }
        if (!isPlayingAd) {
            if (this.fastForwardMs > 0) {
                arrayList.add(ACTION_FAST_FORWARD);
            }
            if (this.useNavigationActions && player.getNextWindowIndex() != -1) {
                arrayList.add(ACTION_NEXT);
            }
        }
        if (this.customActionReceiver != null) {
            arrayList.addAll(this.customActionReceiver.getCustomActions(player));
        }
        if (ACTION_STOP.equals(this.stopAction) != null) {
            arrayList.add(this.stopAction);
        }
        return arrayList;
    }

    protected int[] getActionIndicesForCompactView(List<String> list, Player player) {
        player = list.indexOf(ACTION_PAUSE);
        list = list.indexOf(ACTION_PLAY);
        if (player != -1) {
            return new int[]{player};
        } else if (list == -1) {
            return new int[0];
        } else {
            return new int[]{list};
        }
    }

    private static Map<String, Action> createPlaybackActions(Context context, int i) {
        Map<String, Action> hashMap = new HashMap();
        hashMap.put(ACTION_PLAY, new Action(R.drawable.exo_notification_play, context.getString(R.string.exo_controls_play_description), createBroadcastIntent(ACTION_PLAY, context, i)));
        hashMap.put(ACTION_PAUSE, new Action(R.drawable.exo_notification_pause, context.getString(R.string.exo_controls_pause_description), createBroadcastIntent(ACTION_PAUSE, context, i)));
        hashMap.put(ACTION_STOP, new Action(R.drawable.exo_notification_stop, context.getString(R.string.exo_controls_stop_description), createBroadcastIntent(ACTION_STOP, context, i)));
        hashMap.put(ACTION_REWIND, new Action(R.drawable.exo_notification_rewind, context.getString(R.string.exo_controls_rewind_description), createBroadcastIntent(ACTION_REWIND, context, i)));
        hashMap.put(ACTION_FAST_FORWARD, new Action(R.drawable.exo_notification_fastforward, context.getString(R.string.exo_controls_fastforward_description), createBroadcastIntent(ACTION_FAST_FORWARD, context, i)));
        hashMap.put(ACTION_PREVIOUS, new Action(R.drawable.exo_notification_previous, context.getString(R.string.exo_controls_previous_description), createBroadcastIntent(ACTION_PREVIOUS, context, i)));
        hashMap.put(ACTION_NEXT, new Action(R.drawable.exo_notification_next, context.getString(R.string.exo_controls_next_description), createBroadcastIntent(ACTION_NEXT, context, i)));
        return hashMap;
    }

    private static PendingIntent createBroadcastIntent(String str, Context context, int i) {
        str = new Intent(str).setPackage(context.getPackageName());
        str.putExtra(EXTRA_INSTANCE_ID, i);
        return PendingIntent.getBroadcast(context, i, str, C.ENCODING_PCM_MU_LAW);
    }
}
