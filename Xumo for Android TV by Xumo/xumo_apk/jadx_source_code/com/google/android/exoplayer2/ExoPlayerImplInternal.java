package com.google.android.exoplayer2;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.DefaultMediaClock.PlaybackParameterListener;
import com.google.android.exoplayer2.PlayerMessage.Sender;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSource.SourceInfoRefreshListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector.InvalidationListener;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

final class ExoPlayerImplInternal implements Callback, MediaPeriod.Callback, InvalidationListener, SourceInfoRefreshListener, PlaybackParameterListener, Sender {
    private static final int IDLE_INTERVAL_MS = 1000;
    private static final int MSG_DO_SOME_WORK = 2;
    public static final int MSG_ERROR = 2;
    private static final int MSG_PERIOD_PREPARED = 9;
    public static final int MSG_PLAYBACK_INFO_CHANGED = 0;
    public static final int MSG_PLAYBACK_PARAMETERS_CHANGED = 1;
    private static final int MSG_PLAYBACK_PARAMETERS_CHANGED_INTERNAL = 16;
    private static final int MSG_PREPARE = 0;
    private static final int MSG_REFRESH_SOURCE_INFO = 8;
    private static final int MSG_RELEASE = 7;
    private static final int MSG_SEEK_TO = 3;
    private static final int MSG_SEND_MESSAGE = 14;
    private static final int MSG_SEND_MESSAGE_TO_TARGET_THREAD = 15;
    private static final int MSG_SET_PLAYBACK_PARAMETERS = 4;
    private static final int MSG_SET_PLAY_WHEN_READY = 1;
    private static final int MSG_SET_REPEAT_MODE = 12;
    private static final int MSG_SET_SEEK_PARAMETERS = 5;
    private static final int MSG_SET_SHUFFLE_ENABLED = 13;
    private static final int MSG_SOURCE_CONTINUE_LOADING_REQUESTED = 10;
    private static final int MSG_STOP = 6;
    private static final int MSG_TRACK_SELECTION_INVALIDATED = 11;
    private static final int PREPARING_SOURCE_INTERVAL_MS = 10;
    private static final int RENDERING_INTERVAL_MS = 10;
    private static final String TAG = "ExoPlayerImplInternal";
    private final long backBufferDurationUs;
    private final BandwidthMeter bandwidthMeter;
    private final Clock clock;
    private final TrackSelectorResult emptyTrackSelectorResult;
    private Renderer[] enabledRenderers;
    private final Handler eventHandler;
    private final HandlerWrapper handler;
    private final HandlerThread internalPlaybackThread;
    private final LoadControl loadControl;
    private final DefaultMediaClock mediaClock;
    private MediaSource mediaSource;
    private int nextPendingMessageIndex;
    private SeekPosition pendingInitialSeekPosition;
    private final ArrayList<PendingMessageInfo> pendingMessages;
    private int pendingPrepareCount;
    private final Period period;
    private boolean playWhenReady;
    private PlaybackInfo playbackInfo;
    private final PlaybackInfoUpdate playbackInfoUpdate;
    private final ExoPlayer player;
    private final MediaPeriodQueue queue = new MediaPeriodQueue();
    private boolean rebuffering;
    private boolean released;
    private final RendererCapabilities[] rendererCapabilities;
    private long rendererPositionUs;
    private final Renderer[] renderers;
    private int repeatMode;
    private final boolean retainBackBufferFromKeyframe;
    private SeekParameters seekParameters;
    private boolean shuffleModeEnabled;
    private final TrackSelector trackSelector;
    private final Window window;

    private static final class MediaSourceRefreshInfo {
        public final Object manifest;
        public final MediaSource source;
        public final Timeline timeline;

        public MediaSourceRefreshInfo(MediaSource mediaSource, Timeline timeline, Object obj) {
            this.source = mediaSource;
            this.timeline = timeline;
            this.manifest = obj;
        }
    }

    private static final class PendingMessageInfo implements Comparable<PendingMessageInfo> {
        public final PlayerMessage message;
        public int resolvedPeriodIndex;
        public long resolvedPeriodTimeUs;
        @Nullable
        public Object resolvedPeriodUid;

        public PendingMessageInfo(PlayerMessage playerMessage) {
            this.message = playerMessage;
        }

        public void setResolvedPosition(int i, long j, Object obj) {
            this.resolvedPeriodIndex = i;
            this.resolvedPeriodTimeUs = j;
            this.resolvedPeriodUid = obj;
        }

        public int compareTo(@NonNull PendingMessageInfo pendingMessageInfo) {
            int i = 1;
            if ((this.resolvedPeriodUid == null ? 1 : null) != (pendingMessageInfo.resolvedPeriodUid == null ? 1 : null)) {
                if (this.resolvedPeriodUid != null) {
                    i = -1;
                }
                return i;
            } else if (this.resolvedPeriodUid == null) {
                return 0;
            } else {
                int i2 = this.resolvedPeriodIndex - pendingMessageInfo.resolvedPeriodIndex;
                if (i2 != 0) {
                    return i2;
                }
                return Util.compareLong(this.resolvedPeriodTimeUs, pendingMessageInfo.resolvedPeriodTimeUs);
            }
        }
    }

    private static final class PlaybackInfoUpdate {
        private int discontinuityReason;
        private PlaybackInfo lastPlaybackInfo;
        private int operationAcks;
        private boolean positionDiscontinuity;

        private PlaybackInfoUpdate() {
        }

        public boolean hasPendingUpdate(PlaybackInfo playbackInfo) {
            if (playbackInfo == this.lastPlaybackInfo && this.operationAcks <= null) {
                if (this.positionDiscontinuity == null) {
                    return null;
                }
            }
            return true;
        }

        public void reset(PlaybackInfo playbackInfo) {
            this.lastPlaybackInfo = playbackInfo;
            this.operationAcks = 0;
            this.positionDiscontinuity = false;
        }

        public void incrementPendingOperationAcks(int i) {
            this.operationAcks += i;
        }

        public void setPositionDiscontinuity(int i) {
            boolean z = true;
            if (!this.positionDiscontinuity || this.discontinuityReason == 4) {
                this.positionDiscontinuity = true;
                this.discontinuityReason = i;
                return;
            }
            if (i != 4) {
                z = false;
            }
            Assertions.checkArgument(z);
        }
    }

    private static final class SeekPosition {
        public final Timeline timeline;
        public final int windowIndex;
        public final long windowPositionUs;

        public SeekPosition(Timeline timeline, int i, long j) {
            this.timeline = timeline;
            this.windowIndex = i;
            this.windowPositionUs = j;
        }
    }

    public ExoPlayerImplInternal(Renderer[] rendererArr, TrackSelector trackSelector, TrackSelectorResult trackSelectorResult, LoadControl loadControl, BandwidthMeter bandwidthMeter, boolean z, int i, boolean z2, Handler handler, ExoPlayer exoPlayer, Clock clock) {
        this.renderers = rendererArr;
        this.trackSelector = trackSelector;
        this.emptyTrackSelectorResult = trackSelectorResult;
        this.loadControl = loadControl;
        this.bandwidthMeter = bandwidthMeter;
        this.playWhenReady = z;
        this.repeatMode = i;
        this.shuffleModeEnabled = z2;
        this.eventHandler = handler;
        this.player = exoPlayer;
        this.clock = clock;
        this.backBufferDurationUs = loadControl.getBackBufferDurationUs();
        this.retainBackBufferFromKeyframe = loadControl.retainBackBufferFromKeyframe();
        this.seekParameters = SeekParameters.DEFAULT;
        this.playbackInfo = PlaybackInfo.createDummy(true, trackSelectorResult);
        this.playbackInfoUpdate = new PlaybackInfoUpdate();
        this.rendererCapabilities = new RendererCapabilities[rendererArr.length];
        for (loadControl = null; loadControl < rendererArr.length; loadControl++) {
            rendererArr[loadControl].setIndex(loadControl);
            this.rendererCapabilities[loadControl] = rendererArr[loadControl].getCapabilities();
        }
        this.mediaClock = new DefaultMediaClock(this, clock);
        this.pendingMessages = new ArrayList();
        this.enabledRenderers = new Renderer[null];
        this.window = new Window();
        this.period = new Period();
        trackSelector.init(this, bandwidthMeter);
        this.internalPlaybackThread = new HandlerThread("ExoPlayerImplInternal:Handler", -16);
        this.internalPlaybackThread.start();
        this.handler = clock.createHandler(this.internalPlaybackThread.getLooper(), this);
    }

    public void prepare(MediaSource mediaSource, boolean z, boolean z2) {
        this.handler.obtainMessage(0, z, z2, mediaSource).sendToTarget();
    }

    public void setPlayWhenReady(boolean z) {
        this.handler.obtainMessage(1, z, 0).sendToTarget();
    }

    public void setRepeatMode(int i) {
        this.handler.obtainMessage(12, i, 0).sendToTarget();
    }

    public void setShuffleModeEnabled(boolean z) {
        this.handler.obtainMessage(13, z, 0).sendToTarget();
    }

    public void seekTo(Timeline timeline, int i, long j) {
        this.handler.obtainMessage(3, new SeekPosition(timeline, i, j)).sendToTarget();
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.handler.obtainMessage(4, playbackParameters).sendToTarget();
    }

    public void setSeekParameters(SeekParameters seekParameters) {
        this.handler.obtainMessage(5, seekParameters).sendToTarget();
    }

    public void stop(boolean z) {
        this.handler.obtainMessage(6, z, 0).sendToTarget();
    }

    public synchronized void sendMessage(PlayerMessage playerMessage) {
        if (this.released) {
            Log.w(TAG, "Ignoring messages sent after release.");
            playerMessage.markAsProcessed(false);
            return;
        }
        this.handler.obtainMessage(14, playerMessage).sendToTarget();
    }

    public synchronized void release() {
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
        r2 = this;
        monitor-enter(r2);
        r0 = r2.released;	 Catch:{ all -> 0x0023 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r2);
        return;
    L_0x0007:
        r0 = r2.handler;	 Catch:{ all -> 0x0023 }
        r1 = 7;	 Catch:{ all -> 0x0023 }
        r0.sendEmptyMessage(r1);	 Catch:{ all -> 0x0023 }
        r0 = 0;	 Catch:{ all -> 0x0023 }
    L_0x000e:
        r1 = r2.released;	 Catch:{ all -> 0x0023 }
        if (r1 != 0) goto L_0x0018;
    L_0x0012:
        r2.wait();	 Catch:{ InterruptedException -> 0x0016 }
        goto L_0x000e;
    L_0x0016:
        r0 = 1;
        goto L_0x000e;
    L_0x0018:
        if (r0 == 0) goto L_0x0021;
    L_0x001a:
        r0 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0023 }
        r0.interrupt();	 Catch:{ all -> 0x0023 }
    L_0x0021:
        monitor-exit(r2);
        return;
    L_0x0023:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.release():void");
    }

    public Looper getPlaybackLooper() {
        return this.internalPlaybackThread.getLooper();
    }

    public void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline, Object obj) {
        this.handler.obtainMessage(8, new MediaSourceRefreshInfo(mediaSource, timeline, obj)).sendToTarget();
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        this.handler.obtainMessage(9, mediaPeriod).sendToTarget();
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        this.handler.obtainMessage(10, mediaPeriod).sendToTarget();
    }

    public void onTrackSelectionsInvalidated() {
        this.handler.sendEmptyMessage(11);
    }

    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        this.handler.obtainMessage(16, playbackParameters).sendToTarget();
    }

    public boolean handleMessage(Message message) {
        try {
            switch (message.what) {
                case 0:
                    prepareInternal((MediaSource) message.obj, message.arg1 != 0, message.arg2 != null ? true : null);
                    break;
                case 1:
                    setPlayWhenReadyInternal(message.arg1 != null ? true : null);
                    break;
                case 2:
                    doSomeWork();
                    break;
                case 3:
                    seekToInternal((SeekPosition) message.obj);
                    break;
                case 4:
                    setPlaybackParametersInternal((PlaybackParameters) message.obj);
                    break;
                case 5:
                    setSeekParametersInternal((SeekParameters) message.obj);
                    break;
                case 6:
                    stopInternal(message.arg1 != null ? true : null, true);
                    break;
                case 7:
                    releaseInternal();
                    return true;
                case 8:
                    handleSourceInfoRefreshed((MediaSourceRefreshInfo) message.obj);
                    break;
                case 9:
                    handlePeriodPrepared((MediaPeriod) message.obj);
                    break;
                case 10:
                    handleContinueLoadingRequested((MediaPeriod) message.obj);
                    break;
                case 11:
                    reselectTracksInternal();
                    break;
                case 12:
                    setRepeatModeInternal(message.arg1);
                    break;
                case 13:
                    setShuffleModeEnabledInternal(message.arg1 != null ? true : null);
                    break;
                case 14:
                    sendMessageInternal((PlayerMessage) message.obj);
                    break;
                case 15:
                    sendMessageToTargetThread((PlayerMessage) message.obj);
                    break;
                case 16:
                    handlePlaybackParameters((PlaybackParameters) message.obj);
                    break;
                default:
                    return false;
            }
            maybeNotifyPlaybackInfoChanged();
        } catch (Message message2) {
            Log.e(TAG, "Playback error.", message2);
            stopInternal(false, false);
            this.eventHandler.obtainMessage(2, message2).sendToTarget();
            maybeNotifyPlaybackInfoChanged();
        } catch (Message message22) {
            Log.e(TAG, "Source error.", message22);
            stopInternal(false, false);
            this.eventHandler.obtainMessage(2, ExoPlaybackException.createForSource(message22)).sendToTarget();
            maybeNotifyPlaybackInfoChanged();
        } catch (Message message222) {
            Log.e(TAG, "Internal runtime error.", message222);
            stopInternal(false, false);
            this.eventHandler.obtainMessage(2, ExoPlaybackException.createForUnexpected(message222)).sendToTarget();
            maybeNotifyPlaybackInfoChanged();
        }
        return true;
    }

    private void setState(int i) {
        if (this.playbackInfo.playbackState != i) {
            this.playbackInfo = this.playbackInfo.copyWithPlaybackState(i);
        }
    }

    private void setIsLoading(boolean z) {
        if (this.playbackInfo.isLoading != z) {
            this.playbackInfo = this.playbackInfo.copyWithIsLoading(z);
        }
    }

    private void maybeNotifyPlaybackInfoChanged() {
        if (this.playbackInfoUpdate.hasPendingUpdate(this.playbackInfo)) {
            this.eventHandler.obtainMessage(0, this.playbackInfoUpdate.operationAcks, this.playbackInfoUpdate.positionDiscontinuity ? this.playbackInfoUpdate.discontinuityReason : -1, this.playbackInfo).sendToTarget();
            this.playbackInfoUpdate.reset(this.playbackInfo);
        }
    }

    private void prepareInternal(MediaSource mediaSource, boolean z, boolean z2) {
        this.pendingPrepareCount++;
        resetInternal(true, z, z2);
        this.loadControl.onPrepared();
        this.mediaSource = mediaSource;
        setState(2);
        mediaSource.prepareSource(this.player, true, this, this.bandwidthMeter.getTransferListener());
        this.handler.sendEmptyMessage(2);
    }

    private void setPlayWhenReadyInternal(boolean z) throws ExoPlaybackException {
        this.rebuffering = false;
        this.playWhenReady = z;
        if (!z) {
            stopRenderers();
            updatePlaybackPositions();
        } else if (this.playbackInfo.playbackState) {
            startRenderers();
            this.handler.sendEmptyMessage(2);
        } else if (this.playbackInfo.playbackState) {
            this.handler.sendEmptyMessage(2);
        }
    }

    private void setRepeatModeInternal(int i) throws ExoPlaybackException {
        this.repeatMode = i;
        if (this.queue.updateRepeatMode(i) == 0) {
            seekToCurrentPosition(1);
        }
        handleLoadingMediaPeriodChanged(0);
    }

    private void setShuffleModeEnabledInternal(boolean z) throws ExoPlaybackException {
        this.shuffleModeEnabled = z;
        if (!this.queue.updateShuffleModeEnabled(z)) {
            seekToCurrentPosition(true);
        }
        handleLoadingMediaPeriodChanged(false);
    }

    private void seekToCurrentPosition(boolean z) throws ExoPlaybackException {
        MediaPeriodId mediaPeriodId = this.queue.getPlayingPeriod().info.id;
        long seekToPeriodPosition = seekToPeriodPosition(mediaPeriodId, this.playbackInfo.positionUs, true);
        if (seekToPeriodPosition != this.playbackInfo.positionUs) {
            this.playbackInfo = this.playbackInfo.fromNewPosition(mediaPeriodId, seekToPeriodPosition, this.playbackInfo.contentPositionUs);
            if (z) {
                this.playbackInfoUpdate.setPositionDiscontinuity(4);
            }
        }
    }

    private void startRenderers() throws ExoPlaybackException {
        int i = 0;
        this.rebuffering = false;
        this.mediaClock.start();
        Renderer[] rendererArr = this.enabledRenderers;
        int length = rendererArr.length;
        while (i < length) {
            rendererArr[i].start();
            i++;
        }
    }

    private void stopRenderers() throws ExoPlaybackException {
        this.mediaClock.stop();
        for (Renderer ensureStopped : this.enabledRenderers) {
            ensureStopped(ensureStopped);
        }
    }

    private void updatePlaybackPositions() throws ExoPlaybackException {
        if (this.queue.hasPlayingPeriod()) {
            MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
            long readDiscontinuity = playingPeriod.mediaPeriod.readDiscontinuity();
            if (readDiscontinuity != C.TIME_UNSET) {
                resetRendererPosition(readDiscontinuity);
                if (readDiscontinuity != this.playbackInfo.positionUs) {
                    this.playbackInfo = this.playbackInfo.fromNewPosition(this.playbackInfo.periodId, readDiscontinuity, this.playbackInfo.contentPositionUs);
                    this.playbackInfoUpdate.setPositionDiscontinuity(4);
                }
            } else {
                this.rendererPositionUs = this.mediaClock.syncAndGetPositionUs();
                long toPeriodTime = playingPeriod.toPeriodTime(this.rendererPositionUs);
                maybeTriggerPendingMessages(this.playbackInfo.positionUs, toPeriodTime);
                this.playbackInfo.positionUs = toPeriodTime;
            }
            playingPeriod = this.queue.getLoadingPeriod();
            this.playbackInfo.bufferedPositionUs = playingPeriod.getBufferedPositionUs(true);
            this.playbackInfo.totalBufferedDurationUs = this.playbackInfo.bufferedPositionUs - playingPeriod.toPeriodTime(this.rendererPositionUs);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void doSomeWork() throws com.google.android.exoplayer2.ExoPlaybackException, java.io.IOException {
        /*
        r16 = this;
        r0 = r16;
        r1 = r0.clock;
        r1 = r1.uptimeMillis();
        r16.updatePeriods();
        r3 = r0.queue;
        r3 = r3.hasPlayingPeriod();
        r4 = 10;
        if (r3 != 0) goto L_0x001c;
    L_0x0015:
        r16.maybeThrowPeriodPrepareError();
        r0.scheduleNextWork(r1, r4);
        return;
    L_0x001c:
        r3 = r0.queue;
        r3 = r3.getPlayingPeriod();
        r6 = "doSomeWork";
        com.google.android.exoplayer2.util.TraceUtil.beginSection(r6);
        r16.updatePlaybackPositions();
        r6 = android.os.SystemClock.elapsedRealtime();
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r6 = r6 * r8;
        r10 = r3.mediaPeriod;
        r11 = r0.playbackInfo;
        r11 = r11.positionUs;
        r13 = r0.backBufferDurationUs;
        r11 = r11 - r13;
        r13 = r0.retainBackBufferFromKeyframe;
        r10.discardBuffer(r11, r13);
        r10 = r0.enabledRenderers;
        r11 = r10.length;
        r12 = 1;
        r14 = 0;
        r15 = 1;
    L_0x0046:
        if (r14 >= r11) goto L_0x0081;
    L_0x0048:
        r13 = r10[r14];
        r8 = r0.rendererPositionUs;
        r13.render(r8, r6);
        if (r15 == 0) goto L_0x0059;
    L_0x0051:
        r8 = r13.isEnded();
        if (r8 == 0) goto L_0x0059;
    L_0x0057:
        r15 = 1;
        goto L_0x005a;
    L_0x0059:
        r15 = 0;
    L_0x005a:
        r8 = r13.isReady();
        if (r8 != 0) goto L_0x006f;
    L_0x0060:
        r8 = r13.isEnded();
        if (r8 != 0) goto L_0x006f;
    L_0x0066:
        r8 = r0.rendererWaitingForNextStream(r13);
        if (r8 == 0) goto L_0x006d;
    L_0x006c:
        goto L_0x006f;
    L_0x006d:
        r8 = 0;
        goto L_0x0070;
    L_0x006f:
        r8 = 1;
    L_0x0070:
        if (r8 != 0) goto L_0x0075;
    L_0x0072:
        r13.maybeThrowStreamError();
    L_0x0075:
        if (r12 == 0) goto L_0x007b;
    L_0x0077:
        if (r8 == 0) goto L_0x007b;
    L_0x0079:
        r12 = 1;
        goto L_0x007c;
    L_0x007b:
        r12 = 0;
    L_0x007c:
        r14 = r14 + 1;
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        goto L_0x0046;
    L_0x0081:
        if (r12 != 0) goto L_0x0086;
    L_0x0083:
        r16.maybeThrowPeriodPrepareError();
    L_0x0086:
        r6 = r3.info;
        r6 = r6.durationUs;
        r8 = 4;
        r9 = 3;
        r10 = 2;
        if (r15 == 0) goto L_0x00ad;
    L_0x008f:
        r13 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r11 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1));
        if (r11 == 0) goto L_0x00a0;
    L_0x0098:
        r11 = r0.playbackInfo;
        r13 = r11.positionUs;
        r11 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1));
        if (r11 > 0) goto L_0x00ad;
    L_0x00a0:
        r3 = r3.info;
        r3 = r3.isFinal;
        if (r3 == 0) goto L_0x00ad;
    L_0x00a6:
        r0.setState(r8);
        r16.stopRenderers();
        goto L_0x00e2;
    L_0x00ad:
        r3 = r0.playbackInfo;
        r3 = r3.playbackState;
        if (r3 != r10) goto L_0x00c4;
    L_0x00b3:
        r3 = r0.shouldTransitionToReadyState(r12);
        if (r3 == 0) goto L_0x00c4;
    L_0x00b9:
        r0.setState(r9);
        r3 = r0.playWhenReady;
        if (r3 == 0) goto L_0x00e2;
    L_0x00c0:
        r16.startRenderers();
        goto L_0x00e2;
    L_0x00c4:
        r3 = r0.playbackInfo;
        r3 = r3.playbackState;
        if (r3 != r9) goto L_0x00e2;
    L_0x00ca:
        r3 = r0.enabledRenderers;
        r3 = r3.length;
        if (r3 != 0) goto L_0x00d6;
    L_0x00cf:
        r3 = r16.isTimelineReady();
        if (r3 == 0) goto L_0x00d8;
    L_0x00d5:
        goto L_0x00e2;
    L_0x00d6:
        if (r12 != 0) goto L_0x00e2;
    L_0x00d8:
        r3 = r0.playWhenReady;
        r0.rebuffering = r3;
        r0.setState(r10);
        r16.stopRenderers();
    L_0x00e2:
        r3 = r0.playbackInfo;
        r3 = r3.playbackState;
        if (r3 != r10) goto L_0x00f6;
    L_0x00e8:
        r3 = r0.enabledRenderers;
        r6 = r3.length;
        r7 = 0;
    L_0x00ec:
        if (r7 >= r6) goto L_0x00f6;
    L_0x00ee:
        r11 = r3[r7];
        r11.maybeThrowStreamError();
        r7 = r7 + 1;
        goto L_0x00ec;
    L_0x00f6:
        r3 = r0.playWhenReady;
        if (r3 == 0) goto L_0x0100;
    L_0x00fa:
        r3 = r0.playbackInfo;
        r3 = r3.playbackState;
        if (r3 == r9) goto L_0x0106;
    L_0x0100:
        r3 = r0.playbackInfo;
        r3 = r3.playbackState;
        if (r3 != r10) goto L_0x010a;
    L_0x0106:
        r0.scheduleNextWork(r1, r4);
        goto L_0x0120;
    L_0x010a:
        r3 = r0.enabledRenderers;
        r3 = r3.length;
        if (r3 == 0) goto L_0x011b;
    L_0x010f:
        r3 = r0.playbackInfo;
        r3 = r3.playbackState;
        if (r3 == r8) goto L_0x011b;
    L_0x0115:
        r3 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0.scheduleNextWork(r1, r3);
        goto L_0x0120;
    L_0x011b:
        r1 = r0.handler;
        r1.removeMessages(r10);
    L_0x0120:
        com.google.android.exoplayer2.util.TraceUtil.endSection();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.doSomeWork():void");
    }

    private void scheduleNextWork(long j, long j2) {
        this.handler.removeMessages(2);
        this.handler.sendEmptyMessageAtTime(2, j + j2);
    }

    private void seekToInternal(SeekPosition seekPosition) throws ExoPlaybackException {
        MediaPeriodId firstMediaPeriodId;
        long j;
        long j2;
        int i;
        long j3;
        long j4;
        int i2;
        SeekPosition seekPosition2 = seekPosition;
        long j5 = 1;
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        Pair resolveSeekPosition = resolveSeekPosition(seekPosition2, true);
        if (resolveSeekPosition == null) {
            firstMediaPeriodId = getFirstMediaPeriodId();
            j = C.TIME_UNSET;
            j2 = j;
        } else {
            Object obj = resolveSeekPosition.first;
            long longValue = ((Long) resolveSeekPosition.second).longValue();
            MediaPeriodId resolveMediaPeriodIdForAds = r1.queue.resolveMediaPeriodIdForAds(obj, longValue);
            if (resolveMediaPeriodIdForAds.isAd()) {
                j = 0;
                firstMediaPeriodId = resolveMediaPeriodIdForAds;
                j2 = longValue;
            } else {
                j = ((Long) resolveSeekPosition.second).longValue();
                i = seekPosition2.windowPositionUs == C.TIME_UNSET ? 1 : 0;
                firstMediaPeriodId = resolveMediaPeriodIdForAds;
                j2 = longValue;
                if (r1.mediaSource != null) {
                    if (r1.pendingPrepareCount > 0) {
                        if (j != C.TIME_UNSET) {
                            setState(4);
                            resetInternal(false, true, false);
                            j3 = j;
                            r1.playbackInfo = r1.playbackInfo.fromNewPosition(firstMediaPeriodId, j3, j2);
                            if (i != 0) {
                                r1.playbackInfoUpdate.setPositionDiscontinuity(2);
                            }
                        }
                        if (firstMediaPeriodId.equals(r1.playbackInfo.periodId)) {
                            j4 = j;
                        } else {
                            MediaPeriodHolder playingPeriod = r1.queue.getPlayingPeriod();
                            j4 = (playingPeriod != null || j == 0) ? j : playingPeriod.mediaPeriod.getAdjustedSeekPositionUs(j, r1.seekParameters);
                            if (C.usToMs(j4) == C.usToMs(r1.playbackInfo.positionUs)) {
                                j5 = r1.playbackInfo.positionUs;
                                return;
                            }
                        }
                        j4 = seekToPeriodPosition(firstMediaPeriodId, j4);
                        if (j != j4) {
                            i2 = 0;
                        }
                        i |= i2;
                        j3 = j4;
                        r1.playbackInfo = r1.playbackInfo.fromNewPosition(firstMediaPeriodId, j3, j2);
                        if (i != 0) {
                            r1.playbackInfoUpdate.setPositionDiscontinuity(2);
                        }
                    }
                }
                r1.pendingInitialSeekPosition = seekPosition2;
                j3 = j;
                r1.playbackInfo = r1.playbackInfo.fromNewPosition(firstMediaPeriodId, j3, j2);
                if (i != 0) {
                    r1.playbackInfoUpdate.setPositionDiscontinuity(2);
                }
            }
        }
        i = 1;
        try {
            if (r1.mediaSource != null) {
                if (r1.pendingPrepareCount > 0) {
                    if (j != C.TIME_UNSET) {
                        if (firstMediaPeriodId.equals(r1.playbackInfo.periodId)) {
                            j4 = j;
                        } else {
                            MediaPeriodHolder playingPeriod2 = r1.queue.getPlayingPeriod();
                            if (playingPeriod2 != null) {
                            }
                            if (C.usToMs(j4) == C.usToMs(r1.playbackInfo.positionUs)) {
                                j5 = r1.playbackInfo.positionUs;
                                return;
                            }
                        }
                        j4 = seekToPeriodPosition(firstMediaPeriodId, j4);
                        if (j != j4) {
                            i2 = 0;
                        }
                        i |= i2;
                        j3 = j4;
                        r1.playbackInfo = r1.playbackInfo.fromNewPosition(firstMediaPeriodId, j3, j2);
                        if (i != 0) {
                            r1.playbackInfoUpdate.setPositionDiscontinuity(2);
                        }
                    }
                    setState(4);
                    resetInternal(false, true, false);
                    j3 = j;
                    r1.playbackInfo = r1.playbackInfo.fromNewPosition(firstMediaPeriodId, j3, j2);
                    if (i != 0) {
                        r1.playbackInfoUpdate.setPositionDiscontinuity(2);
                    }
                }
            }
            r1.pendingInitialSeekPosition = seekPosition2;
            j3 = j;
            r1.playbackInfo = r1.playbackInfo.fromNewPosition(firstMediaPeriodId, j3, j2);
            if (i != 0) {
                r1.playbackInfoUpdate.setPositionDiscontinuity(2);
            }
        } finally {
            r1.playbackInfo = r1.playbackInfo.fromNewPosition(firstMediaPeriodId, 
/*
Method generation error in method: com.google.android.exoplayer2.ExoPlayerImplInternal.seekToInternal(com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition):void
jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x00d9: IPUT  (wrap: com.google.android.exoplayer2.PlaybackInfo
  0x00d5: INVOKE  (r3_5 com.google.android.exoplayer2.PlaybackInfo) = (wrap: com.google.android.exoplayer2.PlaybackInfo
  0x00d1: IGET  (r14_2 com.google.android.exoplayer2.PlaybackInfo) = (r1_0 com.google.android.exoplayer2.ExoPlayerImplInternal) com.google.android.exoplayer2.ExoPlayerImplInternal.playbackInfo com.google.android.exoplayer2.PlaybackInfo), (r15_4 'firstMediaPeriodId' com.google.android.exoplayer2.source.MediaSource$MediaPeriodId), (wrap: long
  ?: MERGE  (r12_7 long) = (r12_6 'j' long), (r3_4 'j5' long)), (r18_4 'j2' long) com.google.android.exoplayer2.PlaybackInfo.fromNewPosition(com.google.android.exoplayer2.source.MediaSource$MediaPeriodId, long, long):com.google.android.exoplayer2.PlaybackInfo type: VIRTUAL), (r1_0 com.google.android.exoplayer2.ExoPlayerImplInternal) com.google.android.exoplayer2.ExoPlayerImplInternal.playbackInfo com.google.android.exoplayer2.PlaybackInfo in method: com.google.android.exoplayer2.ExoPlayerImplInternal.seekToInternal(com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition):void
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:203)
	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:100)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:50)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:297)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x00d5: INVOKE  (r3_5 com.google.android.exoplayer2.PlaybackInfo) = (wrap: com.google.android.exoplayer2.PlaybackInfo
  0x00d1: IGET  (r14_2 com.google.android.exoplayer2.PlaybackInfo) = (r1_0 com.google.android.exoplayer2.ExoPlayerImplInternal) com.google.android.exoplayer2.ExoPlayerImplInternal.playbackInfo com.google.android.exoplayer2.PlaybackInfo), (r15_4 'firstMediaPeriodId' com.google.android.exoplayer2.source.MediaSource$MediaPeriodId), (wrap: long
  ?: MERGE  (r12_7 long) = (r12_6 'j' long), (r3_4 'j5' long)), (r18_4 'j2' long) com.google.android.exoplayer2.PlaybackInfo.fromNewPosition(com.google.android.exoplayer2.source.MediaSource$MediaPeriodId, long, long):com.google.android.exoplayer2.PlaybackInfo type: VIRTUAL in method: com.google.android.exoplayer2.ExoPlayerImplInternal.seekToInternal(com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition):void
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:101)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:393)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 21 more
Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r12_7 long) = (r12_6 'j' long), (r3_4 'j5' long) in method: com.google.android.exoplayer2.ExoPlayerImplInternal.seekToInternal(com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition):void
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:101)
	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:679)
	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:649)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:343)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:211)
	... 24 more
Caused by: jadx.core.utils.exceptions.CodegenException: MERGE can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:211)
	... 29 more

*/

            private long seekToPeriodPosition(MediaPeriodId mediaPeriodId, long j) throws ExoPlaybackException {
                return seekToPeriodPosition(mediaPeriodId, j, this.queue.getPlayingPeriod() != this.queue.getReadingPeriod());
            }

            private long seekToPeriodPosition(MediaPeriodId mediaPeriodId, long j, boolean z) throws ExoPlaybackException {
                stopRenderers();
                this.rebuffering = false;
                setState(2);
                MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
                MediaPeriodHolder mediaPeriodHolder = playingPeriod;
                while (mediaPeriodHolder != null) {
                    if (mediaPeriodId.equals(mediaPeriodHolder.info.id) && mediaPeriodHolder.prepared) {
                        this.queue.removeAfter(mediaPeriodHolder);
                        break;
                    }
                    mediaPeriodHolder = this.queue.advancePlayingPeriod();
                }
                if (playingPeriod != mediaPeriodHolder || z) {
                    for (Renderer disableRenderer : this.enabledRenderers) {
                        disableRenderer(disableRenderer);
                    }
                    this.enabledRenderers = new Renderer[0];
                    playingPeriod = null;
                }
                if (mediaPeriodHolder != null) {
                    updatePlayingPeriodRenderers(playingPeriod);
                    if (mediaPeriodHolder.hasEnabledTracks != null) {
                        j = mediaPeriodHolder.mediaPeriod.seekToUs(j);
                        mediaPeriodHolder.mediaPeriod.discardBuffer(j - this.backBufferDurationUs, this.retainBackBufferFromKeyframe);
                    }
                    resetRendererPosition(j);
                    maybeContinueLoading();
                } else {
                    this.queue.clear(true);
                    this.playbackInfo = this.playbackInfo.copyWithTrackInfo(TrackGroupArray.EMPTY, this.emptyTrackSelectorResult);
                    resetRendererPosition(j);
                }
                handleLoadingMediaPeriodChanged(false);
                this.handler.sendEmptyMessage(2);
                return j;
            }

            private void resetRendererPosition(long j) throws ExoPlaybackException {
                if (this.queue.hasPlayingPeriod()) {
                    j = this.queue.getPlayingPeriod().toRendererTime(j);
                }
                this.rendererPositionUs = j;
                this.mediaClock.resetPosition(this.rendererPositionUs);
                for (Renderer resetPosition : this.enabledRenderers) {
                    resetPosition.resetPosition(this.rendererPositionUs);
                }
            }

            private void setPlaybackParametersInternal(PlaybackParameters playbackParameters) {
                this.mediaClock.setPlaybackParameters(playbackParameters);
            }

            private void setSeekParametersInternal(SeekParameters seekParameters) {
                this.seekParameters = seekParameters;
            }

            private void stopInternal(boolean z, boolean z2) {
                resetInternal(true, z, z);
                this.playbackInfoUpdate.incrementPendingOperationAcks(this.pendingPrepareCount + z2);
                this.pendingPrepareCount = false;
                this.loadControl.onStopped();
                setState(1);
            }

            private void releaseInternal() {
                resetInternal(true, true, true);
                this.loadControl.onReleased();
                setState(1);
                this.internalPlaybackThread.quit();
                synchronized (this) {
                    this.released = true;
                    notifyAll();
                }
            }

            private MediaPeriodId getFirstMediaPeriodId() {
                Timeline timeline = this.playbackInfo.timeline;
                if (timeline.isEmpty()) {
                    return PlaybackInfo.DUMMY_MEDIA_PERIOD_ID;
                }
                return new MediaPeriodId(timeline.getUidOfPeriod(timeline.getWindow(timeline.getFirstWindowIndex(this.shuffleModeEnabled), this.window).firstPeriodIndex));
            }

            private void resetInternal(boolean z, boolean z2, boolean z3) {
                this.handler.removeMessages(2);
                this.rebuffering = false;
                this.mediaClock.stop();
                this.rendererPositionUs = 0;
                for (Renderer disableRenderer : this.enabledRenderers) {
                    try {
                        disableRenderer(disableRenderer);
                    } catch (Throwable e) {
                        Log.e(TAG, "Stop failed.", e);
                    }
                }
                r1.enabledRenderers = new Renderer[0];
                r1.queue.clear(z2 ^ 1);
                setIsLoading(false);
                if (z2) {
                    r1.pendingInitialSeekPosition = null;
                }
                if (z3) {
                    r1.queue.setTimeline(Timeline.EMPTY);
                    Iterator it = r1.pendingMessages.iterator();
                    while (it.hasNext()) {
                        ((PendingMessageInfo) it.next()).message.markAsProcessed(false);
                    }
                    r1.pendingMessages.clear();
                    r1.nextPendingMessageIndex = 0;
                }
                MediaPeriodId firstMediaPeriodId = z2 ? getFirstMediaPeriodId() : r1.playbackInfo.periodId;
                long j = C.TIME_UNSET;
                long j2 = z2 ? C.TIME_UNSET : r1.playbackInfo.positionUs;
                if (!z2) {
                    j = r1.playbackInfo.contentPositionUs;
                }
                r1.playbackInfo = new PlaybackInfo(z3 ? Timeline.EMPTY : r1.playbackInfo.timeline, z3 ? null : r1.playbackInfo.manifest, firstMediaPeriodId, j2, j, r1.playbackInfo.playbackState, false, z3 ? TrackGroupArray.EMPTY : r1.playbackInfo.trackGroups, z3 ? r1.emptyTrackSelectorResult : r1.playbackInfo.trackSelectorResult, firstMediaPeriodId, j2, 0, j2);
                if (z && r1.mediaSource != null) {
                    r1.mediaSource.releaseSource(r1);
                    r1.mediaSource = null;
                }
            }

            private void sendMessageInternal(PlayerMessage playerMessage) throws ExoPlaybackException {
                if (playerMessage.getPositionMs() == C.TIME_UNSET) {
                    sendMessageToTarget(playerMessage);
                    return;
                }
                if (this.mediaSource != null) {
                    if (this.pendingPrepareCount <= 0) {
                        PendingMessageInfo pendingMessageInfo = new PendingMessageInfo(playerMessage);
                        if (resolvePendingMessagePosition(pendingMessageInfo)) {
                            this.pendingMessages.add(pendingMessageInfo);
                            Collections.sort(this.pendingMessages);
                            return;
                        }
                        playerMessage.markAsProcessed(false);
                        return;
                    }
                }
                this.pendingMessages.add(new PendingMessageInfo(playerMessage));
            }

            private void sendMessageToTarget(PlayerMessage playerMessage) throws ExoPlaybackException {
                if (playerMessage.getHandler().getLooper() == this.handler.getLooper()) {
                    deliverMessage(playerMessage);
                    if (this.playbackInfo.playbackState == 3 || this.playbackInfo.playbackState == 2) {
                        this.handler.sendEmptyMessage(2);
                        return;
                    }
                    return;
                }
                this.handler.obtainMessage(15, playerMessage).sendToTarget();
            }

            private void sendMessageToTargetThread(PlayerMessage playerMessage) {
                playerMessage.getHandler().post(new -$$Lambda$ExoPlayerImplInternal$XwFxncwlyfAWA4k618O8BNtCsr0(this, playerMessage));
            }

            public static /* synthetic */ void lambda$sendMessageToTargetThread$0(ExoPlayerImplInternal exoPlayerImplInternal, PlayerMessage playerMessage) {
                try {
                    exoPlayerImplInternal.deliverMessage(playerMessage);
                } catch (PlayerMessage playerMessage2) {
                    Log.e(TAG, "Unexpected error delivering message on external thread.", playerMessage2);
                    throw new RuntimeException(playerMessage2);
                }
            }

            private void deliverMessage(PlayerMessage playerMessage) throws ExoPlaybackException {
                if (!playerMessage.isCanceled()) {
                    try {
                        playerMessage.getTarget().handleMessage(playerMessage.getType(), playerMessage.getPayload());
                    } finally {
                        playerMessage.markAsProcessed(true);
                    }
                }
            }

            private void resolvePendingMessagePositions() {
                for (int size = this.pendingMessages.size() - 1; size >= 0; size--) {
                    if (!resolvePendingMessagePosition((PendingMessageInfo) this.pendingMessages.get(size))) {
                        ((PendingMessageInfo) this.pendingMessages.get(size)).message.markAsProcessed(false);
                        this.pendingMessages.remove(size);
                    }
                }
                Collections.sort(this.pendingMessages);
            }

            private boolean resolvePendingMessagePosition(PendingMessageInfo pendingMessageInfo) {
                if (pendingMessageInfo.resolvedPeriodUid == null) {
                    Pair resolveSeekPosition = resolveSeekPosition(new SeekPosition(pendingMessageInfo.message.getTimeline(), pendingMessageInfo.message.getWindowIndex(), C.msToUs(pendingMessageInfo.message.getPositionMs())), false);
                    if (resolveSeekPosition == null) {
                        return false;
                    }
                    pendingMessageInfo.setResolvedPosition(this.playbackInfo.timeline.getIndexOfPeriod(resolveSeekPosition.first), ((Long) resolveSeekPosition.second).longValue(), resolveSeekPosition.first);
                } else {
                    int indexOfPeriod = this.playbackInfo.timeline.getIndexOfPeriod(pendingMessageInfo.resolvedPeriodUid);
                    if (indexOfPeriod == -1) {
                        return false;
                    }
                    pendingMessageInfo.resolvedPeriodIndex = indexOfPeriod;
                }
                return true;
            }

            private void maybeTriggerPendingMessages(long r7, long r9) throws com.google.android.exoplayer2.ExoPlaybackException {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r6 = this;
                r0 = r6.pendingMessages;
                r0 = r0.isEmpty();
                if (r0 != 0) goto L_0x00fe;
            L_0x0008:
                r0 = r6.playbackInfo;
                r0 = r0.periodId;
                r0 = r0.isAd();
                if (r0 == 0) goto L_0x0014;
            L_0x0012:
                goto L_0x00fe;
            L_0x0014:
                r0 = r6.playbackInfo;
                r0 = r0.startPositionUs;
                r2 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1));
                if (r2 != 0) goto L_0x001f;
            L_0x001c:
                r0 = 1;
                r7 = r7 - r0;
            L_0x001f:
                r0 = r6.playbackInfo;
                r0 = r0.timeline;
                r1 = r6.playbackInfo;
                r1 = r1.periodId;
                r1 = r1.periodUid;
                r0 = r0.getIndexOfPeriod(r1);
                r1 = r6.nextPendingMessageIndex;
                r2 = 0;
                if (r1 <= 0) goto L_0x003f;
            L_0x0032:
                r1 = r6.pendingMessages;
                r3 = r6.nextPendingMessageIndex;
                r3 = r3 + -1;
                r1 = r1.get(r3);
                r1 = (com.google.android.exoplayer2.ExoPlayerImplInternal.PendingMessageInfo) r1;
                goto L_0x0040;
            L_0x003f:
                r1 = r2;
            L_0x0040:
                if (r1 == 0) goto L_0x0067;
            L_0x0042:
                r3 = r1.resolvedPeriodIndex;
                if (r3 > r0) goto L_0x0050;
            L_0x0046:
                r3 = r1.resolvedPeriodIndex;
                if (r3 != r0) goto L_0x0067;
            L_0x004a:
                r3 = r1.resolvedPeriodTimeUs;
                r1 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1));
                if (r1 <= 0) goto L_0x0067;
            L_0x0050:
                r1 = r6.nextPendingMessageIndex;
                r1 = r1 + -1;
                r6.nextPendingMessageIndex = r1;
                r1 = r6.nextPendingMessageIndex;
                if (r1 <= 0) goto L_0x003f;
            L_0x005a:
                r1 = r6.pendingMessages;
                r3 = r6.nextPendingMessageIndex;
                r3 = r3 + -1;
                r1 = r1.get(r3);
                r1 = (com.google.android.exoplayer2.ExoPlayerImplInternal.PendingMessageInfo) r1;
                goto L_0x0040;
            L_0x0067:
                r1 = r6.nextPendingMessageIndex;
                r3 = r6.pendingMessages;
                r3 = r3.size();
                if (r1 >= r3) goto L_0x007c;
            L_0x0071:
                r1 = r6.pendingMessages;
                r3 = r6.nextPendingMessageIndex;
                r1 = r1.get(r3);
                r1 = (com.google.android.exoplayer2.ExoPlayerImplInternal.PendingMessageInfo) r1;
                goto L_0x007d;
            L_0x007c:
                r1 = r2;
            L_0x007d:
                if (r1 == 0) goto L_0x00ac;
            L_0x007f:
                r3 = r1.resolvedPeriodUid;
                if (r3 == 0) goto L_0x00ac;
            L_0x0083:
                r3 = r1.resolvedPeriodIndex;
                if (r3 < r0) goto L_0x0091;
            L_0x0087:
                r3 = r1.resolvedPeriodIndex;
                if (r3 != r0) goto L_0x00ac;
            L_0x008b:
                r3 = r1.resolvedPeriodTimeUs;
                r5 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1));
                if (r5 > 0) goto L_0x00ac;
            L_0x0091:
                r1 = r6.nextPendingMessageIndex;
                r1 = r1 + 1;
                r6.nextPendingMessageIndex = r1;
                r1 = r6.nextPendingMessageIndex;
                r3 = r6.pendingMessages;
                r3 = r3.size();
                if (r1 >= r3) goto L_0x007c;
            L_0x00a1:
                r1 = r6.pendingMessages;
                r3 = r6.nextPendingMessageIndex;
                r1 = r1.get(r3);
                r1 = (com.google.android.exoplayer2.ExoPlayerImplInternal.PendingMessageInfo) r1;
                goto L_0x007d;
            L_0x00ac:
                if (r1 == 0) goto L_0x00fd;
            L_0x00ae:
                r3 = r1.resolvedPeriodUid;
                if (r3 == 0) goto L_0x00fd;
            L_0x00b2:
                r3 = r1.resolvedPeriodIndex;
                if (r3 != r0) goto L_0x00fd;
            L_0x00b6:
                r3 = r1.resolvedPeriodTimeUs;
                r5 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1));
                if (r5 <= 0) goto L_0x00fd;
            L_0x00bc:
                r3 = r1.resolvedPeriodTimeUs;
                r5 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1));
                if (r5 > 0) goto L_0x00fd;
            L_0x00c2:
                r3 = r1.message;
                r6.sendMessageToTarget(r3);
                r3 = r1.message;
                r3 = r3.getDeleteAfterDelivery();
                if (r3 != 0) goto L_0x00df;
            L_0x00cf:
                r1 = r1.message;
                r1 = r1.isCanceled();
                if (r1 == 0) goto L_0x00d8;
            L_0x00d7:
                goto L_0x00df;
            L_0x00d8:
                r1 = r6.nextPendingMessageIndex;
                r1 = r1 + 1;
                r6.nextPendingMessageIndex = r1;
                goto L_0x00e6;
            L_0x00df:
                r1 = r6.pendingMessages;
                r3 = r6.nextPendingMessageIndex;
                r1.remove(r3);
            L_0x00e6:
                r1 = r6.nextPendingMessageIndex;
                r3 = r6.pendingMessages;
                r3 = r3.size();
                if (r1 >= r3) goto L_0x00fb;
            L_0x00f0:
                r1 = r6.pendingMessages;
                r3 = r6.nextPendingMessageIndex;
                r1 = r1.get(r3);
                r1 = (com.google.android.exoplayer2.ExoPlayerImplInternal.PendingMessageInfo) r1;
                goto L_0x00ac;
            L_0x00fb:
                r1 = r2;
                goto L_0x00ac;
            L_0x00fd:
                return;
            L_0x00fe:
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.maybeTriggerPendingMessages(long, long):void");
            }

            private void ensureStopped(Renderer renderer) throws ExoPlaybackException {
                if (renderer.getState() == 2) {
                    renderer.stop();
                }
            }

            private void disableRenderer(Renderer renderer) throws ExoPlaybackException {
                this.mediaClock.onRendererDisabled(renderer);
                ensureStopped(renderer);
                renderer.disable();
            }

            private void reselectTracksInternal() throws ExoPlaybackException {
                if (this.queue.hasPlayingPeriod()) {
                    float f = this.mediaClock.getPlaybackParameters().speed;
                    MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
                    MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
                    Object obj = 1;
                    while (playingPeriod != null) {
                        if (!playingPeriod.prepared) {
                            break;
                        } else if (playingPeriod.selectTracks(f)) {
                            if (obj != null) {
                                playingPeriod = this.queue.getPlayingPeriod();
                                boolean[] zArr = new boolean[this.renderers.length];
                                long applyTrackSelection = playingPeriod.applyTrackSelection(this.playbackInfo.positionUs, this.queue.removeAfter(playingPeriod), zArr);
                                if (!(this.playbackInfo.playbackState == 4 || applyTrackSelection == this.playbackInfo.positionUs)) {
                                    this.playbackInfo = this.playbackInfo.fromNewPosition(this.playbackInfo.periodId, applyTrackSelection, this.playbackInfo.contentPositionUs);
                                    this.playbackInfoUpdate.setPositionDiscontinuity(4);
                                    resetRendererPosition(applyTrackSelection);
                                }
                                boolean[] zArr2 = new boolean[this.renderers.length];
                                int i = 0;
                                for (int i2 = 0; i2 < this.renderers.length; i2++) {
                                    Renderer renderer = this.renderers[i2];
                                    zArr2[i2] = renderer.getState() != 0;
                                    SampleStream sampleStream = playingPeriod.sampleStreams[i2];
                                    if (sampleStream != null) {
                                        i++;
                                    }
                                    if (zArr2[i2]) {
                                        if (sampleStream != renderer.getStream()) {
                                            disableRenderer(renderer);
                                        } else if (zArr[i2]) {
                                            renderer.resetPosition(this.rendererPositionUs);
                                        }
                                    }
                                }
                                this.playbackInfo = this.playbackInfo.copyWithTrackInfo(playingPeriod.trackGroups, playingPeriod.trackSelectorResult);
                                enableRenderers(zArr2, i);
                            } else {
                                this.queue.removeAfter(playingPeriod);
                                if (playingPeriod.prepared) {
                                    playingPeriod.applyTrackSelection(Math.max(playingPeriod.info.startPositionUs, playingPeriod.toPeriodTime(this.rendererPositionUs)), false);
                                }
                            }
                            handleLoadingMediaPeriodChanged(true);
                            if (this.playbackInfo.playbackState != 4) {
                                maybeContinueLoading();
                                updatePlaybackPositions();
                                this.handler.sendEmptyMessage(2);
                            }
                            return;
                        } else {
                            if (playingPeriod == readingPeriod) {
                                obj = null;
                            }
                            playingPeriod = playingPeriod.next;
                        }
                    }
                }
            }

            private void updateTrackSelectionPlaybackSpeed(float f) {
                for (MediaPeriodHolder frontPeriod = this.queue.getFrontPeriod(); frontPeriod != null; frontPeriod = frontPeriod.next) {
                    if (frontPeriod.trackSelectorResult != null) {
                        for (TrackSelection trackSelection : frontPeriod.trackSelectorResult.selections.getAll()) {
                            if (trackSelection != null) {
                                trackSelection.onPlaybackSpeed(f);
                            }
                        }
                    }
                }
            }

            private boolean shouldTransitionToReadyState(boolean z) {
                if (this.enabledRenderers.length == 0) {
                    return isTimelineReady();
                }
                boolean z2 = false;
                if (!z) {
                    return false;
                }
                if (!this.playbackInfo.isLoading) {
                    return true;
                }
                z = this.queue.getLoadingPeriod();
                long bufferedPositionUs = z.getBufferedPositionUs(z.info.isFinal ^ true);
                if (bufferedPositionUs == Long.MIN_VALUE || this.loadControl.shouldStartPlayback(bufferedPositionUs - z.toPeriodTime(this.rendererPositionUs), this.mediaClock.getPlaybackParameters().speed, this.rebuffering)) {
                    z2 = true;
                }
                return z2;
            }

            private boolean isTimelineReady() {
                MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
                long j = playingPeriod.info.durationUs;
                if (j != C.TIME_UNSET && this.playbackInfo.positionUs >= j) {
                    if (playingPeriod.next != null) {
                        if (!playingPeriod.next.prepared) {
                            if (playingPeriod.next.info.id.isAd()) {
                            }
                        }
                    }
                    return false;
                }
                return true;
            }

            private void maybeThrowSourceInfoRefreshError() throws IOException {
                if (this.queue.getLoadingPeriod() != null) {
                    Renderer[] rendererArr = this.enabledRenderers;
                    int length = rendererArr.length;
                    int i = 0;
                    while (i < length) {
                        if (rendererArr[i].hasReadStreamToEnd()) {
                            i++;
                        } else {
                            return;
                        }
                    }
                }
                this.mediaSource.maybeThrowSourceInfoRefreshError();
            }

            private void maybeThrowPeriodPrepareError() throws IOException {
                MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
                MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
                if (!(loadingPeriod == null || loadingPeriod.prepared || (readingPeriod != null && readingPeriod.next != loadingPeriod))) {
                    Renderer[] rendererArr = this.enabledRenderers;
                    int length = rendererArr.length;
                    int i = 0;
                    while (i < length) {
                        if (rendererArr[i].hasReadStreamToEnd()) {
                            i++;
                        } else {
                            return;
                        }
                    }
                    loadingPeriod.mediaPeriod.maybeThrowPrepareError();
                }
            }

            private void handleSourceInfoRefreshed(MediaSourceRefreshInfo mediaSourceRefreshInfo) throws ExoPlaybackException {
                if (mediaSourceRefreshInfo.source == this.mediaSource) {
                    Timeline timeline = this.playbackInfo.timeline;
                    Timeline timeline2 = mediaSourceRefreshInfo.timeline;
                    mediaSourceRefreshInfo = mediaSourceRefreshInfo.manifest;
                    this.queue.setTimeline(timeline2);
                    this.playbackInfo = this.playbackInfo.copyWithTimeline(timeline2, mediaSourceRefreshInfo);
                    resolvePendingMessagePositions();
                    long j = 0;
                    Object obj;
                    long longValue;
                    MediaPeriodId resolveMediaPeriodIdForAds;
                    if (this.pendingPrepareCount > null) {
                        this.playbackInfoUpdate.incrementPendingOperationAcks(this.pendingPrepareCount);
                        this.pendingPrepareCount = 0;
                        if (this.pendingInitialSeekPosition != null) {
                            try {
                                mediaSourceRefreshInfo = resolveSeekPosition(this.pendingInitialSeekPosition, true);
                                this.pendingInitialSeekPosition = null;
                                if (mediaSourceRefreshInfo == null) {
                                    handleSourceInfoRefreshEndedPlayback();
                                } else {
                                    obj = mediaSourceRefreshInfo.first;
                                    longValue = ((Long) mediaSourceRefreshInfo.second).longValue();
                                    resolveMediaPeriodIdForAds = this.queue.resolveMediaPeriodIdForAds(obj, longValue);
                                    this.playbackInfo = this.playbackInfo.fromNewPosition(resolveMediaPeriodIdForAds, resolveMediaPeriodIdForAds.isAd() != null ? 0 : longValue, longValue);
                                }
                            } catch (MediaSourceRefreshInfo mediaSourceRefreshInfo2) {
                                this.playbackInfo = this.playbackInfo.fromNewPosition(getFirstMediaPeriodId(), C.TIME_UNSET, C.TIME_UNSET);
                                throw mediaSourceRefreshInfo2;
                            }
                        } else if (this.playbackInfo.startPositionUs == C.TIME_UNSET) {
                            if (timeline2.isEmpty() != null) {
                                handleSourceInfoRefreshEndedPlayback();
                            } else {
                                mediaSourceRefreshInfo2 = getPeriodPosition(timeline2, timeline2.getFirstWindowIndex(this.shuffleModeEnabled), C.TIME_UNSET);
                                obj = mediaSourceRefreshInfo2.first;
                                longValue = ((Long) mediaSourceRefreshInfo2.second).longValue();
                                resolveMediaPeriodIdForAds = this.queue.resolveMediaPeriodIdForAds(obj, longValue);
                                this.playbackInfo = this.playbackInfo.fromNewPosition(resolveMediaPeriodIdForAds, resolveMediaPeriodIdForAds.isAd() != null ? 0 : longValue, longValue);
                            }
                        }
                    } else if (timeline.isEmpty() != null) {
                        if (timeline2.isEmpty() == null) {
                            mediaSourceRefreshInfo2 = getPeriodPosition(timeline2, timeline2.getFirstWindowIndex(this.shuffleModeEnabled), C.TIME_UNSET);
                            obj = mediaSourceRefreshInfo2.first;
                            longValue = ((Long) mediaSourceRefreshInfo2.second).longValue();
                            resolveMediaPeriodIdForAds = this.queue.resolveMediaPeriodIdForAds(obj, longValue);
                            this.playbackInfo = this.playbackInfo.fromNewPosition(resolveMediaPeriodIdForAds, resolveMediaPeriodIdForAds.isAd() != null ? 0 : longValue, longValue);
                        }
                    } else {
                        mediaSourceRefreshInfo2 = this.queue.getFrontPeriod();
                        long j2 = this.playbackInfo.contentPositionUs;
                        Object obj2 = mediaSourceRefreshInfo2 == null ? this.playbackInfo.periodId.periodUid : mediaSourceRefreshInfo2.uid;
                        if (timeline2.getIndexOfPeriod(obj2) == -1) {
                            obj = resolveSubsequentPeriod(obj2, timeline, timeline2);
                            if (obj == null) {
                                handleSourceInfoRefreshEndedPlayback();
                                return;
                            }
                            Pair periodPosition = getPeriodPosition(timeline2, timeline2.getPeriodByUid(obj, this.period).windowIndex, C.TIME_UNSET);
                            Object obj3 = periodPosition.first;
                            longValue = ((Long) periodPosition.second).longValue();
                            resolveMediaPeriodIdForAds = this.queue.resolveMediaPeriodIdForAds(obj3, longValue);
                            if (mediaSourceRefreshInfo2 != null) {
                                while (mediaSourceRefreshInfo2.next != null) {
                                    mediaSourceRefreshInfo2 = mediaSourceRefreshInfo2.next;
                                    if (mediaSourceRefreshInfo2.info.id.equals(resolveMediaPeriodIdForAds)) {
                                        mediaSourceRefreshInfo2.info = this.queue.getUpdatedMediaPeriodInfo(mediaSourceRefreshInfo2.info);
                                    }
                                }
                            }
                            if (resolveMediaPeriodIdForAds.isAd() == null) {
                                j = longValue;
                            }
                            this.playbackInfo = this.playbackInfo.fromNewPosition(resolveMediaPeriodIdForAds, seekToPeriodPosition(resolveMediaPeriodIdForAds, j), longValue);
                            return;
                        }
                        mediaSourceRefreshInfo2 = this.playbackInfo.periodId;
                        if (mediaSourceRefreshInfo2.isAd()) {
                            MediaPeriodId resolveMediaPeriodIdForAds2 = this.queue.resolveMediaPeriodIdForAds(obj2, j2);
                            if (!resolveMediaPeriodIdForAds2.equals(mediaSourceRefreshInfo2)) {
                                if (resolveMediaPeriodIdForAds2.isAd() == null) {
                                    j = j2;
                                }
                                this.playbackInfo = this.playbackInfo.fromNewPosition(resolveMediaPeriodIdForAds2, seekToPeriodPosition(resolveMediaPeriodIdForAds2, j), j2);
                                return;
                            }
                        }
                        if (this.queue.updateQueuedPeriods(mediaSourceRefreshInfo2, this.rendererPositionUs) == null) {
                            seekToCurrentPosition(false);
                        }
                        handleLoadingMediaPeriodChanged(false);
                    }
                }
            }

            private void handleSourceInfoRefreshEndedPlayback() {
                setState(4);
                resetInternal(false, true, false);
            }

            @Nullable
            private Object resolveSubsequentPeriod(Object obj, Timeline timeline, Timeline timeline2) {
                obj = timeline.getIndexOfPeriod(obj);
                int periodCount = timeline.getPeriodCount();
                int i = obj;
                obj = -1;
                for (int i2 = 0; i2 < periodCount && obj == -1; i2++) {
                    i = timeline.getNextPeriodIndex(i, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
                    if (i == -1) {
                        break;
                    }
                    obj = timeline2.getIndexOfPeriod(timeline.getUidOfPeriod(i));
                }
                if (obj == -1) {
                    return null;
                }
                return timeline2.getUidOfPeriod(obj);
            }

            private android.util.Pair<java.lang.Object, java.lang.Long> resolveSeekPosition(com.google.android.exoplayer2.ExoPlayerImplInternal.SeekPosition r11, boolean r12) {
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
                r10 = this;
                r0 = r10.playbackInfo;
                r0 = r0.timeline;
                r1 = r11.timeline;
                r2 = r0.isEmpty();
                r3 = 0;
                if (r2 == 0) goto L_0x000e;
            L_0x000d:
                return r3;
            L_0x000e:
                r2 = r1.isEmpty();
                if (r2 == 0) goto L_0x0015;
            L_0x0014:
                r1 = r0;
            L_0x0015:
                r5 = r10.window;	 Catch:{ IndexOutOfBoundsException -> 0x004c }
                r6 = r10.period;	 Catch:{ IndexOutOfBoundsException -> 0x004c }
                r7 = r11.windowIndex;	 Catch:{ IndexOutOfBoundsException -> 0x004c }
                r8 = r11.windowPositionUs;	 Catch:{ IndexOutOfBoundsException -> 0x004c }
                r4 = r1;	 Catch:{ IndexOutOfBoundsException -> 0x004c }
                r2 = r4.getPeriodPosition(r5, r6, r7, r8);	 Catch:{ IndexOutOfBoundsException -> 0x004c }
                if (r0 != r1) goto L_0x0025;
            L_0x0024:
                return r2;
            L_0x0025:
                r11 = r2.first;
                r11 = r0.getIndexOfPeriod(r11);
                r4 = -1;
                if (r11 == r4) goto L_0x002f;
            L_0x002e:
                return r2;
            L_0x002f:
                if (r12 == 0) goto L_0x004b;
            L_0x0031:
                r12 = r2.first;
                r12 = r10.resolveSubsequentPeriod(r12, r1, r0);
                if (r12 == 0) goto L_0x004b;
            L_0x0039:
                r12 = r10.period;
                r11 = r0.getPeriod(r11, r12);
                r11 = r11.windowIndex;
                r1 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
                r11 = r10.getPeriodPosition(r0, r11, r1);
                return r11;
            L_0x004b:
                return r3;
            L_0x004c:
                r12 = new com.google.android.exoplayer2.IllegalSeekPositionException;
                r1 = r11.windowIndex;
                r2 = r11.windowPositionUs;
                r12.<init>(r0, r1, r2);
                throw r12;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.resolveSeekPosition(com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition, boolean):android.util.Pair<java.lang.Object, java.lang.Long>");
            }

            private Pair<Object, Long> getPeriodPosition(Timeline timeline, int i, long j) {
                return timeline.getPeriodPosition(this.window, this.period, i, j);
            }

            private void updatePeriods() throws ExoPlaybackException, IOException {
                if (this.mediaSource != null) {
                    if (this.pendingPrepareCount > 0) {
                        this.mediaSource.maybeThrowSourceInfoRefreshError();
                        return;
                    }
                    MediaPeriodHolder readingPeriod;
                    Object obj;
                    int i;
                    MediaPeriodHolder advancePlayingPeriod;
                    Renderer renderer;
                    SampleStream sampleStream;
                    int i2;
                    Renderer renderer2;
                    SampleStream sampleStream2;
                    TrackSelectorResult trackSelectorResult;
                    TrackSelectorResult trackSelectorResult2;
                    Object obj2;
                    int i3;
                    Renderer renderer3;
                    TrackSelection trackSelection;
                    boolean isRendererEnabled;
                    Object obj3;
                    Object obj4;
                    RendererConfiguration rendererConfiguration;
                    maybeUpdateLoadingPeriod();
                    MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
                    int i4 = 0;
                    if (loadingPeriod != null) {
                        if (!loadingPeriod.isFullyBuffered()) {
                            if (!this.playbackInfo.isLoading) {
                                maybeContinueLoading();
                            }
                            if (!this.queue.hasPlayingPeriod()) {
                                loadingPeriod = this.queue.getPlayingPeriod();
                                readingPeriod = this.queue.getReadingPeriod();
                                obj = null;
                                while (this.playWhenReady && loadingPeriod != readingPeriod && this.rendererPositionUs >= loadingPeriod.next.getStartPositionRendererTime()) {
                                    if (obj != null) {
                                        maybeNotifyPlaybackInfoChanged();
                                    }
                                    i = loadingPeriod.info.isLastInTimelinePeriod ? 0 : 3;
                                    advancePlayingPeriod = this.queue.advancePlayingPeriod();
                                    updatePlayingPeriodRenderers(loadingPeriod);
                                    this.playbackInfo = this.playbackInfo.fromNewPosition(advancePlayingPeriod.info.id, advancePlayingPeriod.info.startPositionUs, advancePlayingPeriod.info.contentPositionUs);
                                    this.playbackInfoUpdate.setPositionDiscontinuity(i);
                                    updatePlaybackPositions();
                                    loadingPeriod = advancePlayingPeriod;
                                    obj = 1;
                                }
                                if (readingPeriod.info.isFinal) {
                                    while (i4 < this.renderers.length) {
                                        renderer = this.renderers[i4];
                                        sampleStream = readingPeriod.sampleStreams[i4];
                                        if (sampleStream != null && renderer.getStream() == sampleStream && renderer.hasReadStreamToEnd()) {
                                            renderer.setCurrentStreamFinal();
                                        }
                                        i4++;
                                    }
                                } else if (readingPeriod.next == null) {
                                    i2 = 0;
                                    while (i2 < this.renderers.length) {
                                        renderer2 = this.renderers[i2];
                                        sampleStream2 = readingPeriod.sampleStreams[i2];
                                        if (renderer2.getStream() != sampleStream2) {
                                            if (sampleStream2 != null || renderer2.hasReadStreamToEnd()) {
                                                i2++;
                                            }
                                        }
                                        return;
                                    }
                                    if (readingPeriod.next.prepared) {
                                        maybeThrowPeriodPrepareError();
                                    }
                                    trackSelectorResult = readingPeriod.trackSelectorResult;
                                    readingPeriod = this.queue.advanceReadingPeriod();
                                    trackSelectorResult2 = readingPeriod.trackSelectorResult;
                                    obj2 = readingPeriod.mediaPeriod.readDiscontinuity() == C.TIME_UNSET ? 1 : null;
                                    for (i3 = 0; i3 < this.renderers.length; i3++) {
                                        renderer3 = this.renderers[i3];
                                        if (!trackSelectorResult.isRendererEnabled(i3)) {
                                            if (obj2 != null) {
                                                renderer3.setCurrentStreamFinal();
                                            } else if (!renderer3.isCurrentStreamFinal()) {
                                                trackSelection = trackSelectorResult2.selections.get(i3);
                                                isRendererEnabled = trackSelectorResult2.isRendererEnabled(i3);
                                                obj3 = this.rendererCapabilities[i3].getTrackType() != 6 ? 1 : null;
                                                obj4 = trackSelectorResult.rendererConfigurations[i3];
                                                rendererConfiguration = trackSelectorResult2.rendererConfigurations[i3];
                                                if (!isRendererEnabled && rendererConfiguration.equals(obj4) && obj3 == null) {
                                                    renderer3.replaceStream(getFormats(trackSelection), readingPeriod.sampleStreams[i3], readingPeriod.getRendererOffset());
                                                } else {
                                                    renderer3.setCurrentStreamFinal();
                                                }
                                            }
                                        }
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    setIsLoading(false);
                    if (!this.queue.hasPlayingPeriod()) {
                        loadingPeriod = this.queue.getPlayingPeriod();
                        readingPeriod = this.queue.getReadingPeriod();
                        obj = null;
                        while (this.playWhenReady) {
                            if (obj != null) {
                                maybeNotifyPlaybackInfoChanged();
                            }
                            if (loadingPeriod.info.isLastInTimelinePeriod) {
                            }
                            advancePlayingPeriod = this.queue.advancePlayingPeriod();
                            updatePlayingPeriodRenderers(loadingPeriod);
                            this.playbackInfo = this.playbackInfo.fromNewPosition(advancePlayingPeriod.info.id, advancePlayingPeriod.info.startPositionUs, advancePlayingPeriod.info.contentPositionUs);
                            this.playbackInfoUpdate.setPositionDiscontinuity(i);
                            updatePlaybackPositions();
                            loadingPeriod = advancePlayingPeriod;
                            obj = 1;
                        }
                        if (readingPeriod.info.isFinal) {
                            while (i4 < this.renderers.length) {
                                renderer = this.renderers[i4];
                                sampleStream = readingPeriod.sampleStreams[i4];
                                renderer.setCurrentStreamFinal();
                                i4++;
                            }
                        } else if (readingPeriod.next == null) {
                            i2 = 0;
                            while (i2 < this.renderers.length) {
                                renderer2 = this.renderers[i2];
                                sampleStream2 = readingPeriod.sampleStreams[i2];
                                if (renderer2.getStream() != sampleStream2) {
                                    if (sampleStream2 != null) {
                                    }
                                    i2++;
                                }
                                return;
                            }
                            if (readingPeriod.next.prepared) {
                                trackSelectorResult = readingPeriod.trackSelectorResult;
                                readingPeriod = this.queue.advanceReadingPeriod();
                                trackSelectorResult2 = readingPeriod.trackSelectorResult;
                                if (readingPeriod.mediaPeriod.readDiscontinuity() == C.TIME_UNSET) {
                                }
                                while (i3 < this.renderers.length) {
                                    renderer3 = this.renderers[i3];
                                    if (!trackSelectorResult.isRendererEnabled(i3)) {
                                        if (obj2 != null) {
                                            renderer3.setCurrentStreamFinal();
                                        } else if (!renderer3.isCurrentStreamFinal()) {
                                            trackSelection = trackSelectorResult2.selections.get(i3);
                                            isRendererEnabled = trackSelectorResult2.isRendererEnabled(i3);
                                            if (this.rendererCapabilities[i3].getTrackType() != 6) {
                                            }
                                            obj4 = trackSelectorResult.rendererConfigurations[i3];
                                            rendererConfiguration = trackSelectorResult2.rendererConfigurations[i3];
                                            if (!isRendererEnabled) {
                                            }
                                            renderer3.setCurrentStreamFinal();
                                        }
                                    }
                                }
                                return;
                            }
                            maybeThrowPeriodPrepareError();
                        }
                    }
                }
            }

            private void maybeUpdateLoadingPeriod() throws IOException {
                this.queue.reevaluateBuffer(this.rendererPositionUs);
                if (this.queue.shouldLoadNextMediaPeriod()) {
                    MediaPeriodInfo nextMediaPeriodInfo = this.queue.getNextMediaPeriodInfo(this.rendererPositionUs, this.playbackInfo);
                    if (nextMediaPeriodInfo == null) {
                        maybeThrowSourceInfoRefreshError();
                        return;
                    }
                    this.queue.enqueueNextMediaPeriod(this.rendererCapabilities, this.trackSelector, this.loadControl.getAllocator(), this.mediaSource, nextMediaPeriodInfo).prepare(this, nextMediaPeriodInfo.startPositionUs);
                    setIsLoading(true);
                    handleLoadingMediaPeriodChanged(false);
                }
            }

            private void handlePeriodPrepared(MediaPeriod mediaPeriod) throws ExoPlaybackException {
                if (this.queue.isLoading(mediaPeriod) != null) {
                    mediaPeriod = this.queue.getLoadingPeriod();
                    mediaPeriod.handlePrepared(this.mediaClock.getPlaybackParameters().speed);
                    updateLoadControlTrackSelection(mediaPeriod.trackGroups, mediaPeriod.trackSelectorResult);
                    if (this.queue.hasPlayingPeriod() == null) {
                        resetRendererPosition(this.queue.advancePlayingPeriod().info.startPositionUs);
                        updatePlayingPeriodRenderers(null);
                    }
                    maybeContinueLoading();
                }
            }

            private void handleContinueLoadingRequested(MediaPeriod mediaPeriod) {
                if (this.queue.isLoading(mediaPeriod) != null) {
                    this.queue.reevaluateBuffer(this.rendererPositionUs);
                    maybeContinueLoading();
                }
            }

            private void handlePlaybackParameters(PlaybackParameters playbackParameters) throws ExoPlaybackException {
                this.eventHandler.obtainMessage(1, playbackParameters).sendToTarget();
                updateTrackSelectionPlaybackSpeed(playbackParameters.speed);
                for (Renderer renderer : this.renderers) {
                    if (renderer != null) {
                        renderer.setOperatingRate(playbackParameters.speed);
                    }
                }
            }

            private void maybeContinueLoading() {
                MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
                long nextLoadPositionUs = loadingPeriod.getNextLoadPositionUs();
                if (nextLoadPositionUs == Long.MIN_VALUE) {
                    setIsLoading(false);
                    return;
                }
                boolean shouldContinueLoading = this.loadControl.shouldContinueLoading(nextLoadPositionUs - loadingPeriod.toPeriodTime(this.rendererPositionUs), this.mediaClock.getPlaybackParameters().speed);
                setIsLoading(shouldContinueLoading);
                if (shouldContinueLoading) {
                    loadingPeriod.continueLoading(this.rendererPositionUs);
                }
            }

            private void updatePlayingPeriodRenderers(@Nullable MediaPeriodHolder mediaPeriodHolder) throws ExoPlaybackException {
                MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
                if (playingPeriod != null) {
                    if (mediaPeriodHolder != playingPeriod) {
                        boolean[] zArr = new boolean[this.renderers.length];
                        int i = 0;
                        int i2 = 0;
                        while (i < this.renderers.length) {
                            Renderer renderer = this.renderers[i];
                            zArr[i] = renderer.getState() != 0;
                            if (playingPeriod.trackSelectorResult.isRendererEnabled(i)) {
                                i2++;
                            }
                            if (zArr[i] && (!playingPeriod.trackSelectorResult.isRendererEnabled(i) || (renderer.isCurrentStreamFinal() && renderer.getStream() == mediaPeriodHolder.sampleStreams[i]))) {
                                disableRenderer(renderer);
                            }
                            i++;
                        }
                        this.playbackInfo = this.playbackInfo.copyWithTrackInfo(playingPeriod.trackGroups, playingPeriod.trackSelectorResult);
                        enableRenderers(zArr, i2);
                    }
                }
            }

            private void enableRenderers(boolean[] zArr, int i) throws ExoPlaybackException {
                this.enabledRenderers = new Renderer[i];
                i = this.queue.getPlayingPeriod();
                int i2 = 0;
                for (int i3 = 0; i3 < this.renderers.length; i3++) {
                    if (i.trackSelectorResult.isRendererEnabled(i3)) {
                        int i4 = i2 + 1;
                        enableRenderer(i3, zArr[i3], i2);
                        i2 = i4;
                    }
                }
            }

            private void enableRenderer(int i, boolean z, int i2) throws ExoPlaybackException {
                MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
                Renderer renderer = this.renderers[i];
                this.enabledRenderers[i2] = renderer;
                if (renderer.getState() == 0) {
                    RendererConfiguration rendererConfiguration = playingPeriod.trackSelectorResult.rendererConfigurations[i];
                    Format[] formats = getFormats(playingPeriod.trackSelectorResult.selections.get(i));
                    i2 = (this.playWhenReady == 0 || this.playbackInfo.playbackState != 3) ? 0 : 1;
                    boolean z2 = (z || i2 == 0) ? false : true;
                    renderer.enable(rendererConfiguration, formats, playingPeriod.sampleStreams[i], this.rendererPositionUs, z2, playingPeriod.getRendererOffset());
                    this.mediaClock.onRendererEnabled(renderer);
                    if (i2 != 0) {
                        renderer.start();
                    }
                }
            }

            private boolean rendererWaitingForNextStream(Renderer renderer) {
                MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
                return (readingPeriod.next == null || !readingPeriod.next.prepared || renderer.hasReadStreamToEnd() == null) ? null : true;
            }

            private void handleLoadingMediaPeriodChanged(boolean z) {
                MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
                MediaPeriodId mediaPeriodId = loadingPeriod == null ? this.playbackInfo.periodId : loadingPeriod.info.id;
                int equals = this.playbackInfo.loadingMediaPeriodId.equals(mediaPeriodId) ^ 1;
                if (equals != 0) {
                    this.playbackInfo = this.playbackInfo.copyWithLoadingMediaPeriodId(mediaPeriodId);
                }
                if ((equals != 0 || z) && loadingPeriod != null && loadingPeriod.prepared) {
                    updateLoadControlTrackSelection(loadingPeriod.trackGroups, loadingPeriod.trackSelectorResult);
                }
            }

            private void updateLoadControlTrackSelection(TrackGroupArray trackGroupArray, TrackSelectorResult trackSelectorResult) {
                this.loadControl.onTracksSelected(this.renderers, trackGroupArray, trackSelectorResult.selections);
            }

            private static Format[] getFormats(TrackSelection trackSelection) {
                int length = trackSelection != null ? trackSelection.length() : 0;
                Format[] formatArr = new Format[length];
                for (int i = 0; i < length; i++) {
                    formatArr[i] = trackSelection.getFormat(i);
                }
                return formatArr;
            }
        }
