package com.google.android.exoplayer2;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayer.ExoPlayerMessage;
import com.google.android.exoplayer2.Player.AudioComponent;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Player.TextComponent;
import com.google.android.exoplayer2.Player.VideoComponent;
import com.google.android.exoplayer2.PlayerMessage.Target;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

final class ExoPlayerImpl implements ExoPlayer {
    private static final String TAG = "ExoPlayerImpl";
    final TrackSelectorResult emptyTrackSelectorResult;
    private final Handler eventHandler;
    private boolean hasPendingPrepare;
    private boolean hasPendingSeek;
    private boolean internalPlayWhenReady;
    private final ExoPlayerImplInternal internalPlayer;
    private final Handler internalPlayerHandler;
    private final CopyOnWriteArraySet<EventListener> listeners;
    private int maskingPeriodIndex;
    private int maskingWindowIndex;
    private long maskingWindowPositionMs;
    private MediaSource mediaSource;
    private int pendingOperationAcks;
    private final ArrayDeque<PlaybackInfoUpdate> pendingPlaybackInfoUpdates;
    private final Period period;
    private boolean playWhenReady;
    @Nullable
    private ExoPlaybackException playbackError;
    private PlaybackInfo playbackInfo;
    private PlaybackParameters playbackParameters;
    private final Renderer[] renderers;
    private int repeatMode;
    private SeekParameters seekParameters;
    private boolean shuffleModeEnabled;
    private final TrackSelector trackSelector;
    private final Window window;

    private static final class PlaybackInfoUpdate {
        private final boolean isLoadingChanged;
        private final Set<EventListener> listeners;
        private final boolean playWhenReady;
        private final PlaybackInfo playbackInfo;
        private final boolean playbackStateOrPlayWhenReadyChanged;
        private final boolean positionDiscontinuity;
        private final int positionDiscontinuityReason;
        private final boolean seekProcessed;
        private final int timelineChangeReason;
        private final boolean timelineOrManifestChanged;
        private final TrackSelector trackSelector;
        private final boolean trackSelectorResultChanged;

        public PlaybackInfoUpdate(PlaybackInfo playbackInfo, PlaybackInfo playbackInfo2, Set<EventListener> set, TrackSelector trackSelector, boolean z, int i, int i2, boolean z2, boolean z3, boolean z4) {
            this.playbackInfo = playbackInfo;
            this.listeners = set;
            this.trackSelector = trackSelector;
            this.positionDiscontinuity = z;
            this.positionDiscontinuityReason = i;
            this.timelineChangeReason = i2;
            this.seekProcessed = z2;
            this.playWhenReady = z3;
            set = null;
            if (!z4) {
                if (playbackInfo2.playbackState == playbackInfo.playbackState) {
                    z = false;
                    this.playbackStateOrPlayWhenReadyChanged = z;
                    if (playbackInfo2.timeline == playbackInfo.timeline) {
                        if (playbackInfo2.manifest != playbackInfo.manifest) {
                            z = false;
                            this.timelineOrManifestChanged = z;
                            this.isLoadingChanged = playbackInfo2.isLoading == playbackInfo.isLoading;
                            if (playbackInfo2.trackSelectorResult != playbackInfo.trackSelectorResult) {
                                set = true;
                            }
                            this.trackSelectorResultChanged = set;
                        }
                    }
                    z = true;
                    this.timelineOrManifestChanged = z;
                    if (playbackInfo2.isLoading == playbackInfo.isLoading) {
                    }
                    this.isLoadingChanged = playbackInfo2.isLoading == playbackInfo.isLoading;
                    if (playbackInfo2.trackSelectorResult != playbackInfo.trackSelectorResult) {
                        set = true;
                    }
                    this.trackSelectorResultChanged = set;
                }
            }
            z = true;
            this.playbackStateOrPlayWhenReadyChanged = z;
            if (playbackInfo2.timeline == playbackInfo.timeline) {
                if (playbackInfo2.manifest != playbackInfo.manifest) {
                    z = false;
                    this.timelineOrManifestChanged = z;
                    if (playbackInfo2.isLoading == playbackInfo.isLoading) {
                    }
                    this.isLoadingChanged = playbackInfo2.isLoading == playbackInfo.isLoading;
                    if (playbackInfo2.trackSelectorResult != playbackInfo.trackSelectorResult) {
                        set = true;
                    }
                    this.trackSelectorResultChanged = set;
                }
            }
            z = true;
            this.timelineOrManifestChanged = z;
            if (playbackInfo2.isLoading == playbackInfo.isLoading) {
            }
            this.isLoadingChanged = playbackInfo2.isLoading == playbackInfo.isLoading;
            if (playbackInfo2.trackSelectorResult != playbackInfo.trackSelectorResult) {
                set = true;
            }
            this.trackSelectorResultChanged = set;
        }

        public void notifyListeners() {
            if (this.timelineOrManifestChanged || this.timelineChangeReason == 0) {
                for (EventListener onTimelineChanged : this.listeners) {
                    onTimelineChanged.onTimelineChanged(this.playbackInfo.timeline, this.playbackInfo.manifest, this.timelineChangeReason);
                }
            }
            if (this.positionDiscontinuity) {
                for (EventListener onTimelineChanged2 : this.listeners) {
                    onTimelineChanged2.onPositionDiscontinuity(this.positionDiscontinuityReason);
                }
            }
            if (this.trackSelectorResultChanged) {
                this.trackSelector.onSelectionActivated(this.playbackInfo.trackSelectorResult.info);
                for (EventListener onTimelineChanged22 : this.listeners) {
                    onTimelineChanged22.onTracksChanged(this.playbackInfo.trackGroups, this.playbackInfo.trackSelectorResult.selections);
                }
            }
            if (this.isLoadingChanged) {
                for (EventListener onTimelineChanged222 : this.listeners) {
                    onTimelineChanged222.onLoadingChanged(this.playbackInfo.isLoading);
                }
            }
            if (this.playbackStateOrPlayWhenReadyChanged) {
                for (EventListener onTimelineChanged2222 : this.listeners) {
                    onTimelineChanged2222.onPlayerStateChanged(this.playWhenReady, this.playbackInfo.playbackState);
                }
            }
            if (this.seekProcessed) {
                for (EventListener onTimelineChanged22222 : this.listeners) {
                    onTimelineChanged22222.onSeekProcessed();
                }
            }
        }
    }

    public AudioComponent getAudioComponent() {
        return null;
    }

    public TextComponent getTextComponent() {
        return null;
    }

    public VideoComponent getVideoComponent() {
        return null;
    }

    @SuppressLint({"HandlerLeak"})
    public ExoPlayerImpl(Renderer[] rendererArr, TrackSelector trackSelector, LoadControl loadControl, BandwidthMeter bandwidthMeter, Clock clock, Looper looper) {
        ExoPlayerImpl exoPlayerImpl = this;
        Renderer[] rendererArr2 = rendererArr;
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Init ");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" [");
        stringBuilder.append(ExoPlayerLibraryInfo.VERSION_SLASHY);
        stringBuilder.append("] [");
        stringBuilder.append(Util.DEVICE_DEBUG_INFO);
        stringBuilder.append("]");
        Log.i(str, stringBuilder.toString());
        Assertions.checkState(rendererArr2.length > 0);
        exoPlayerImpl.renderers = (Renderer[]) Assertions.checkNotNull(rendererArr);
        exoPlayerImpl.trackSelector = (TrackSelector) Assertions.checkNotNull(trackSelector);
        exoPlayerImpl.playWhenReady = false;
        exoPlayerImpl.repeatMode = 0;
        exoPlayerImpl.shuffleModeEnabled = false;
        exoPlayerImpl.listeners = new CopyOnWriteArraySet();
        exoPlayerImpl.emptyTrackSelectorResult = new TrackSelectorResult(new RendererConfiguration[rendererArr2.length], new TrackSelection[rendererArr2.length], null);
        exoPlayerImpl.window = new Window();
        exoPlayerImpl.period = new Period();
        exoPlayerImpl.playbackParameters = PlaybackParameters.DEFAULT;
        exoPlayerImpl.seekParameters = SeekParameters.DEFAULT;
        exoPlayerImpl.eventHandler = new Handler(looper) {
            public void handleMessage(Message message) {
                ExoPlayerImpl.this.handleEvent(message);
            }
        };
        exoPlayerImpl.playbackInfo = PlaybackInfo.createDummy(0, exoPlayerImpl.emptyTrackSelectorResult);
        exoPlayerImpl.pendingPlaybackInfoUpdates = new ArrayDeque();
        exoPlayerImpl.internalPlayer = new ExoPlayerImplInternal(rendererArr, trackSelector, exoPlayerImpl.emptyTrackSelectorResult, loadControl, bandwidthMeter, exoPlayerImpl.playWhenReady, exoPlayerImpl.repeatMode, exoPlayerImpl.shuffleModeEnabled, exoPlayerImpl.eventHandler, this, clock);
        exoPlayerImpl.internalPlayerHandler = new Handler(exoPlayerImpl.internalPlayer.getPlaybackLooper());
    }

    public Looper getPlaybackLooper() {
        return this.internalPlayer.getPlaybackLooper();
    }

    public Looper getApplicationLooper() {
        return this.eventHandler.getLooper();
    }

    public void addListener(EventListener eventListener) {
        this.listeners.add(eventListener);
    }

    public void removeListener(EventListener eventListener) {
        this.listeners.remove(eventListener);
    }

    public int getPlaybackState() {
        return this.playbackInfo.playbackState;
    }

    @Nullable
    public ExoPlaybackException getPlaybackError() {
        return this.playbackError;
    }

    public void retry() {
        if (this.mediaSource == null) {
            return;
        }
        if (this.playbackError != null || this.playbackInfo.playbackState == 1) {
            prepare(this.mediaSource, false, false);
        }
    }

    public void prepare(MediaSource mediaSource) {
        prepare(mediaSource, true, true);
    }

    public void prepare(MediaSource mediaSource, boolean z, boolean z2) {
        this.playbackError = null;
        this.mediaSource = mediaSource;
        PlaybackInfo resetPlaybackInfo = getResetPlaybackInfo(z, z2, 2);
        this.hasPendingPrepare = true;
        this.pendingOperationAcks++;
        this.internalPlayer.prepare(mediaSource, z, z2);
        updatePlaybackInfo(resetPlaybackInfo, false, 4, 1, false, false);
    }

    public void setPlayWhenReady(boolean z) {
        setPlayWhenReady(z, false);
    }

    public void setPlayWhenReady(boolean z, boolean z2) {
        z2 = z && !z2;
        if (this.internalPlayWhenReady != z2) {
            this.internalPlayWhenReady = z2;
            this.internalPlayer.setPlayWhenReady(z2);
        }
        if (this.playWhenReady != z) {
            this.playWhenReady = z;
            updatePlaybackInfo(this.playbackInfo, false, 4, 1, false, true);
        }
    }

    public boolean getPlayWhenReady() {
        return this.playWhenReady;
    }

    public void setRepeatMode(int i) {
        if (this.repeatMode != i) {
            this.repeatMode = i;
            this.internalPlayer.setRepeatMode(i);
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((EventListener) it.next()).onRepeatModeChanged(i);
            }
        }
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    public void setShuffleModeEnabled(boolean z) {
        if (this.shuffleModeEnabled != z) {
            this.shuffleModeEnabled = z;
            this.internalPlayer.setShuffleModeEnabled(z);
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((EventListener) it.next()).onShuffleModeEnabledChanged(z);
            }
        }
    }

    public boolean getShuffleModeEnabled() {
        return this.shuffleModeEnabled;
    }

    public boolean isLoading() {
        return this.playbackInfo.isLoading;
    }

    public void seekToDefaultPosition() {
        seekToDefaultPosition(getCurrentWindowIndex());
    }

    public void seekToDefaultPosition(int i) {
        seekTo(i, C.TIME_UNSET);
    }

    public void seekTo(long j) {
        seekTo(getCurrentWindowIndex(), j);
    }

    public void seekTo(int i, long j) {
        Timeline timeline = this.playbackInfo.timeline;
        if (i < 0 || (!timeline.isEmpty() && i >= timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(timeline, i, j);
        }
        this.hasPendingSeek = true;
        this.pendingOperationAcks++;
        if (isPlayingAd()) {
            Log.w(TAG, "seekTo ignored because an ad is playing");
            this.eventHandler.obtainMessage(0, 1, -1, this.playbackInfo).sendToTarget();
            return;
        }
        this.maskingWindowIndex = i;
        if (timeline.isEmpty()) {
            this.maskingWindowPositionMs = j == C.TIME_UNSET ? 0 : j;
            this.maskingPeriodIndex = 0;
        } else {
            long defaultPositionUs = j == C.TIME_UNSET ? timeline.getWindow(i, this.window).getDefaultPositionUs() : C.msToUs(j);
            Pair periodPosition = timeline.getPeriodPosition(this.window, this.period, i, defaultPositionUs);
            this.maskingWindowPositionMs = C.usToMs(defaultPositionUs);
            this.maskingPeriodIndex = timeline.getIndexOfPeriod(periodPosition.first);
        }
        this.internalPlayer.seekTo(timeline, i, C.msToUs(j));
        i = this.listeners.iterator();
        while (i.hasNext() != null) {
            ((EventListener) i.next()).onPositionDiscontinuity(1);
        }
    }

    public void setPlaybackParameters(@Nullable PlaybackParameters playbackParameters) {
        if (playbackParameters == null) {
            playbackParameters = PlaybackParameters.DEFAULT;
        }
        this.internalPlayer.setPlaybackParameters(playbackParameters);
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.playbackParameters;
    }

    public void setSeekParameters(@Nullable SeekParameters seekParameters) {
        if (seekParameters == null) {
            seekParameters = SeekParameters.DEFAULT;
        }
        if (!this.seekParameters.equals(seekParameters)) {
            this.seekParameters = seekParameters;
            this.internalPlayer.setSeekParameters(seekParameters);
        }
    }

    public SeekParameters getSeekParameters() {
        return this.seekParameters;
    }

    @Nullable
    public Object getCurrentTag() {
        int currentWindowIndex = getCurrentWindowIndex();
        if (currentWindowIndex >= this.playbackInfo.timeline.getWindowCount()) {
            return null;
        }
        return this.playbackInfo.timeline.getWindow(currentWindowIndex, this.window, true).tag;
    }

    public void stop() {
        stop(false);
    }

    public void stop(boolean z) {
        if (z) {
            this.playbackError = null;
            this.mediaSource = null;
        }
        PlaybackInfo resetPlaybackInfo = getResetPlaybackInfo(z, z, 1);
        this.pendingOperationAcks++;
        this.internalPlayer.stop(z);
        updatePlaybackInfo(resetPlaybackInfo, false, 4, 1, false, false);
    }

    public void release() {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Release ");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" [");
        stringBuilder.append(ExoPlayerLibraryInfo.VERSION_SLASHY);
        stringBuilder.append("] [");
        stringBuilder.append(Util.DEVICE_DEBUG_INFO);
        stringBuilder.append("] [");
        stringBuilder.append(ExoPlayerLibraryInfo.registeredModules());
        stringBuilder.append("]");
        Log.i(str, stringBuilder.toString());
        this.mediaSource = null;
        this.internalPlayer.release();
        this.eventHandler.removeCallbacksAndMessages(null);
    }

    @Deprecated
    public void sendMessages(ExoPlayerMessage... exoPlayerMessageArr) {
        for (ExoPlayerMessage exoPlayerMessage : exoPlayerMessageArr) {
            createMessage(exoPlayerMessage.target).setType(exoPlayerMessage.messageType).setPayload(exoPlayerMessage.message).send();
        }
    }

    public PlayerMessage createMessage(Target target) {
        return new PlayerMessage(this.internalPlayer, target, this.playbackInfo.timeline, getCurrentWindowIndex(), this.internalPlayerHandler);
    }

    @java.lang.Deprecated
    public void blockingSendMessages(com.google.android.exoplayer2.ExoPlayer.ExoPlayerMessage... r8) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r7 = this;
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = r8.length;
        r2 = 0;
        r3 = 0;
    L_0x0008:
        if (r3 >= r1) goto L_0x0028;
    L_0x000a:
        r4 = r8[r3];
        r5 = r4.target;
        r5 = r7.createMessage(r5);
        r6 = r4.messageType;
        r5 = r5.setType(r6);
        r4 = r4.message;
        r4 = r5.setPayload(r4);
        r4 = r4.send();
        r0.add(r4);
        r3 = r3 + 1;
        goto L_0x0008;
    L_0x0028:
        r8 = r0.iterator();
        r0 = 0;
    L_0x002d:
        r1 = r8.hasNext();
        if (r1 == 0) goto L_0x0047;
    L_0x0033:
        r1 = r8.next();
        r1 = (com.google.android.exoplayer2.PlayerMessage) r1;
        r3 = 1;
        r4 = r0;
        r0 = 1;
    L_0x003c:
        if (r0 == 0) goto L_0x0045;
    L_0x003e:
        r1.blockUntilDelivered();	 Catch:{ InterruptedException -> 0x0043 }
        r0 = 0;
        goto L_0x003c;
    L_0x0043:
        r4 = 1;
        goto L_0x003c;
    L_0x0045:
        r0 = r4;
        goto L_0x002d;
    L_0x0047:
        if (r0 == 0) goto L_0x0050;
    L_0x0049:
        r8 = java.lang.Thread.currentThread();
        r8.interrupt();
    L_0x0050:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImpl.blockingSendMessages(com.google.android.exoplayer2.ExoPlayer$ExoPlayerMessage[]):void");
    }

    public int getCurrentPeriodIndex() {
        if (shouldMaskPosition()) {
            return this.maskingPeriodIndex;
        }
        return this.playbackInfo.timeline.getIndexOfPeriod(this.playbackInfo.periodId.periodUid);
    }

    public int getCurrentWindowIndex() {
        if (shouldMaskPosition()) {
            return this.maskingWindowIndex;
        }
        return this.playbackInfo.timeline.getPeriodByUid(this.playbackInfo.periodId.periodUid, this.period).windowIndex;
    }

    public int getNextWindowIndex() {
        Timeline timeline = this.playbackInfo.timeline;
        if (timeline.isEmpty()) {
            return -1;
        }
        return timeline.getNextWindowIndex(getCurrentWindowIndex(), this.repeatMode, this.shuffleModeEnabled);
    }

    public int getPreviousWindowIndex() {
        Timeline timeline = this.playbackInfo.timeline;
        if (timeline.isEmpty()) {
            return -1;
        }
        return timeline.getPreviousWindowIndex(getCurrentWindowIndex(), this.repeatMode, this.shuffleModeEnabled);
    }

    public long getDuration() {
        if (!isPlayingAd()) {
            return getContentDuration();
        }
        MediaPeriodId mediaPeriodId = this.playbackInfo.periodId;
        this.playbackInfo.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        return C.usToMs(this.period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup));
    }

    public long getCurrentPosition() {
        if (shouldMaskPosition()) {
            return this.maskingWindowPositionMs;
        }
        if (this.playbackInfo.periodId.isAd()) {
            return C.usToMs(this.playbackInfo.positionUs);
        }
        return periodPositionUsToWindowPositionMs(this.playbackInfo.periodId, this.playbackInfo.positionUs);
    }

    public long getBufferedPosition() {
        if (!isPlayingAd()) {
            return getContentBufferedPosition();
        }
        long usToMs;
        if (this.playbackInfo.loadingMediaPeriodId.equals(this.playbackInfo.periodId)) {
            usToMs = C.usToMs(this.playbackInfo.bufferedPositionUs);
        } else {
            usToMs = getDuration();
        }
        return usToMs;
    }

    public int getBufferedPercentage() {
        long bufferedPosition = getBufferedPosition();
        long duration = getDuration();
        if (bufferedPosition != C.TIME_UNSET) {
            if (duration != C.TIME_UNSET) {
                if (duration == 0) {
                    return 100;
                }
                return Util.constrainValue((int) ((bufferedPosition * 100) / duration), 0, 100);
            }
        }
        return 0;
    }

    public long getTotalBufferedDuration() {
        return Math.max(0, C.usToMs(this.playbackInfo.totalBufferedDurationUs));
    }

    public boolean isCurrentWindowDynamic() {
        Timeline timeline = this.playbackInfo.timeline;
        return !timeline.isEmpty() && timeline.getWindow(getCurrentWindowIndex(), this.window).isDynamic;
    }

    public boolean isCurrentWindowSeekable() {
        Timeline timeline = this.playbackInfo.timeline;
        return !timeline.isEmpty() && timeline.getWindow(getCurrentWindowIndex(), this.window).isSeekable;
    }

    public boolean isPlayingAd() {
        return !shouldMaskPosition() && this.playbackInfo.periodId.isAd();
    }

    public int getCurrentAdGroupIndex() {
        return isPlayingAd() ? this.playbackInfo.periodId.adGroupIndex : -1;
    }

    public int getCurrentAdIndexInAdGroup() {
        return isPlayingAd() ? this.playbackInfo.periodId.adIndexInAdGroup : -1;
    }

    public long getContentDuration() {
        if (this.playbackInfo.timeline.isEmpty()) {
            return C.TIME_UNSET;
        }
        return this.playbackInfo.timeline.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
    }

    public long getContentPosition() {
        if (!isPlayingAd()) {
            return getCurrentPosition();
        }
        this.playbackInfo.timeline.getPeriodByUid(this.playbackInfo.periodId.periodUid, this.period);
        return this.period.getPositionInWindowMs() + C.usToMs(this.playbackInfo.contentPositionUs);
    }

    public long getContentBufferedPosition() {
        if (shouldMaskPosition()) {
            return this.maskingWindowPositionMs;
        }
        if (this.playbackInfo.loadingMediaPeriodId.windowSequenceNumber != this.playbackInfo.periodId.windowSequenceNumber) {
            return this.playbackInfo.timeline.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
        }
        long j = this.playbackInfo.bufferedPositionUs;
        if (this.playbackInfo.loadingMediaPeriodId.isAd()) {
            Period periodByUid = this.playbackInfo.timeline.getPeriodByUid(this.playbackInfo.loadingMediaPeriodId.periodUid, this.period);
            long adGroupTimeUs = periodByUid.getAdGroupTimeUs(this.playbackInfo.loadingMediaPeriodId.adGroupIndex);
            j = adGroupTimeUs == Long.MIN_VALUE ? periodByUid.durationUs : adGroupTimeUs;
        }
        return periodPositionUsToWindowPositionMs(this.playbackInfo.loadingMediaPeriodId, j);
    }

    public int getRendererCount() {
        return this.renderers.length;
    }

    public int getRendererType(int i) {
        return this.renderers[i].getTrackType();
    }

    public TrackGroupArray getCurrentTrackGroups() {
        return this.playbackInfo.trackGroups;
    }

    public TrackSelectionArray getCurrentTrackSelections() {
        return this.playbackInfo.trackSelectorResult.selections;
    }

    public Timeline getCurrentTimeline() {
        return this.playbackInfo.timeline;
    }

    public Object getCurrentManifest() {
        return this.playbackInfo.manifest;
    }

    void handleEvent(Message message) {
        Iterator it;
        switch (message.what) {
            case 0:
                handlePlaybackInfo((PlaybackInfo) message.obj, message.arg1, message.arg2 != -1, message.arg2);
                return;
            case 1:
                PlaybackParameters playbackParameters = (PlaybackParameters) message.obj;
                if (!this.playbackParameters.equals(playbackParameters)) {
                    this.playbackParameters = playbackParameters;
                    it = this.listeners.iterator();
                    while (it.hasNext()) {
                        ((EventListener) it.next()).onPlaybackParametersChanged(playbackParameters);
                    }
                    return;
                }
                return;
            case 2:
                ExoPlaybackException exoPlaybackException = (ExoPlaybackException) message.obj;
                this.playbackError = exoPlaybackException;
                it = this.listeners.iterator();
                while (it.hasNext()) {
                    ((EventListener) it.next()).onPlayerError(exoPlaybackException);
                }
                return;
            default:
                throw new IllegalStateException();
        }
    }

    private void handlePlaybackInfo(PlaybackInfo playbackInfo, int i, boolean z, int i2) {
        this.pendingOperationAcks -= i;
        if (this.pendingOperationAcks == 0) {
            if (playbackInfo.startPositionUs == C.TIME_UNSET) {
                playbackInfo = playbackInfo.fromNewPosition(playbackInfo.periodId, 0, playbackInfo.contentPositionUs);
            }
            PlaybackInfo playbackInfo2 = playbackInfo;
            if ((this.playbackInfo.timeline.isEmpty() == null || this.hasPendingPrepare != null) && playbackInfo2.timeline.isEmpty() != null) {
                this.maskingPeriodIndex = 0;
                this.maskingWindowIndex = 0;
                this.maskingWindowPositionMs = 0;
            }
            int i3 = this.hasPendingPrepare != null ? 0 : 2;
            boolean z2 = this.hasPendingSeek;
            this.hasPendingPrepare = false;
            this.hasPendingSeek = false;
            updatePlaybackInfo(playbackInfo2, z, i2, i3, z2, false);
        }
    }

    private PlaybackInfo getResetPlaybackInfo(boolean z, boolean z2, int i) {
        ExoPlayerImpl exoPlayerImpl = this;
        if (z) {
            exoPlayerImpl.maskingWindowIndex = 0;
            exoPlayerImpl.maskingPeriodIndex = 0;
            exoPlayerImpl.maskingWindowPositionMs = 0;
        } else {
            exoPlayerImpl.maskingWindowIndex = getCurrentWindowIndex();
            exoPlayerImpl.maskingPeriodIndex = getCurrentPeriodIndex();
            exoPlayerImpl.maskingWindowPositionMs = getCurrentPosition();
        }
        return new PlaybackInfo(z2 ? Timeline.EMPTY : exoPlayerImpl.playbackInfo.timeline, z2 ? null : exoPlayerImpl.playbackInfo.manifest, exoPlayerImpl.playbackInfo.periodId, exoPlayerImpl.playbackInfo.startPositionUs, exoPlayerImpl.playbackInfo.contentPositionUs, i, false, z2 ? TrackGroupArray.EMPTY : exoPlayerImpl.playbackInfo.trackGroups, z2 ? exoPlayerImpl.emptyTrackSelectorResult : exoPlayerImpl.playbackInfo.trackSelectorResult, exoPlayerImpl.playbackInfo.periodId, exoPlayerImpl.playbackInfo.startPositionUs, 0, exoPlayerImpl.playbackInfo.startPositionUs);
    }

    private void updatePlaybackInfo(PlaybackInfo playbackInfo, boolean z, int i, int i2, boolean z2, boolean z3) {
        int isEmpty = this.pendingPlaybackInfoUpdates.isEmpty() ^ 1;
        this.pendingPlaybackInfoUpdates.addLast(new PlaybackInfoUpdate(playbackInfo, this.playbackInfo, this.listeners, this.trackSelector, z, i, i2, z2, this.playWhenReady, z3));
        this.playbackInfo = playbackInfo;
        if (isEmpty == 0) {
            while (!r0.pendingPlaybackInfoUpdates.isEmpty()) {
                ((PlaybackInfoUpdate) r0.pendingPlaybackInfoUpdates.peekFirst()).notifyListeners();
                r0.pendingPlaybackInfoUpdates.removeFirst();
            }
        }
    }

    private long periodPositionUsToWindowPositionMs(MediaPeriodId mediaPeriodId, long j) {
        j = C.usToMs(j);
        this.playbackInfo.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        return j + this.period.getPositionInWindowMs();
    }

    private boolean shouldMaskPosition() {
        if (!this.playbackInfo.timeline.isEmpty()) {
            if (this.pendingOperationAcks <= 0) {
                return false;
            }
        }
        return true;
    }
}
