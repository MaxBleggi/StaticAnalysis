package com.google.android.exoplayer2.audio;

import android.media.AudioTrack;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.reflect.Method;

final class AudioTrackPositionTracker {
    private static final long FORCE_RESET_WORKAROUND_TIMEOUT_MS = 200;
    private static final long MAX_AUDIO_TIMESTAMP_OFFSET_US = 5000000;
    private static final long MAX_LATENCY_US = 5000000;
    private static final int MAX_PLAYHEAD_OFFSET_COUNT = 10;
    private static final int MIN_LATENCY_SAMPLE_INTERVAL_US = 500000;
    private static final int MIN_PLAYHEAD_OFFSET_SAMPLE_INTERVAL_US = 30000;
    private static final int PLAYSTATE_PAUSED = 2;
    private static final int PLAYSTATE_PLAYING = 3;
    private static final int PLAYSTATE_STOPPED = 1;
    @Nullable
    private AudioTimestampPoller audioTimestampPoller;
    @Nullable
    private AudioTrack audioTrack;
    private int bufferSize;
    private long bufferSizeUs;
    private long endPlaybackHeadPosition;
    private long forceResetWorkaroundTimeMs;
    @Nullable
    private Method getLatencyMethod;
    private boolean hasData;
    private boolean isOutputPcm;
    private long lastLatencySampleTimeUs;
    private long lastPlayheadSampleTimeUs;
    private long lastRawPlaybackHeadPosition;
    private long latencyUs;
    private final Listener listener;
    private boolean needsPassthroughWorkarounds;
    private int nextPlayheadOffsetIndex;
    private int outputPcmFrameSize;
    private int outputSampleRate;
    private long passthroughWorkaroundPauseOffset;
    private int playheadOffsetCount;
    private final long[] playheadOffsets;
    private long rawPlaybackHeadWrapCount;
    private long smoothedPlayheadOffsetUs;
    private long stopPlaybackHeadPosition;
    private long stopTimestampUs;

    public interface Listener {
        void onInvalidLatency(long j);

        void onPositionFramesMismatch(long j, long j2, long j3, long j4);

        void onSystemTimeUsMismatch(long j, long j2, long j3, long j4);

        void onUnderrun(int i, long j);
    }

    public AudioTrackPositionTracker(com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener r3) {
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
        r2 = this;
        r2.<init>();
        r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3);
        r3 = (com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener) r3;
        r2.listener = r3;
        r3 = com.google.android.exoplayer2.util.Util.SDK_INT;
        r0 = 18;
        if (r3 < r0) goto L_0x001e;
    L_0x0011:
        r3 = android.media.AudioTrack.class;	 Catch:{ NoSuchMethodException -> 0x001e }
        r0 = "getLatency";	 Catch:{ NoSuchMethodException -> 0x001e }
        r1 = 0;	 Catch:{ NoSuchMethodException -> 0x001e }
        r1 = (java.lang.Class[]) r1;	 Catch:{ NoSuchMethodException -> 0x001e }
        r3 = r3.getMethod(r0, r1);	 Catch:{ NoSuchMethodException -> 0x001e }
        r2.getLatencyMethod = r3;	 Catch:{ NoSuchMethodException -> 0x001e }
    L_0x001e:
        r3 = 10;
        r3 = new long[r3];
        r2.playheadOffsets = r3;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.AudioTrackPositionTracker.<init>(com.google.android.exoplayer2.audio.AudioTrackPositionTracker$Listener):void");
    }

    public void setAudioTrack(AudioTrack audioTrack, int i, int i2, int i3) {
        this.audioTrack = audioTrack;
        this.outputPcmFrameSize = i2;
        this.bufferSize = i3;
        this.audioTimestampPoller = new AudioTimestampPoller(audioTrack);
        this.outputSampleRate = audioTrack.getSampleRate();
        this.needsPassthroughWorkarounds = needsPassthroughWorkarounds(i);
        this.isOutputPcm = Util.isEncodingLinearPcm(i);
        this.bufferSizeUs = this.isOutputPcm != null ? framesToDurationUs((long) (i3 / i2)) : -9223372036854775807;
        this.lastRawPlaybackHeadPosition = 0;
        this.rawPlaybackHeadWrapCount = 0;
        this.passthroughWorkaroundPauseOffset = 0;
        this.hasData = false;
        this.stopTimestampUs = C.TIME_UNSET;
        this.forceResetWorkaroundTimeMs = C.TIME_UNSET;
        this.latencyUs = 0;
    }

    public long getCurrentPositionUs(boolean z) {
        if (((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 3) {
            maybeSampleSyncParams();
        }
        long nanoTime = System.nanoTime() / 1000;
        AudioTimestampPoller audioTimestampPoller = (AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller);
        if (audioTimestampPoller.hasTimestamp()) {
            long framesToDurationUs = framesToDurationUs(audioTimestampPoller.getTimestampPositionFrames());
            if (audioTimestampPoller.isTimestampAdvancing()) {
                return framesToDurationUs + (nanoTime - audioTimestampPoller.getTimestampSystemTimeUs());
            }
            return framesToDurationUs;
        }
        if (this.playheadOffsetCount == 0) {
            nanoTime = getPlaybackHeadPositionUs();
        } else {
            nanoTime += this.smoothedPlayheadOffsetUs;
        }
        if (!z) {
            nanoTime -= this.latencyUs;
        }
        return nanoTime;
    }

    public void start() {
        ((AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller)).reset();
    }

    public boolean isPlaying() {
        return ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 3;
    }

    public boolean mayHandleBuffer(long j) {
        int playState = ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState();
        if (this.needsPassthroughWorkarounds) {
            if (playState == 2) {
                this.hasData = false;
                return false;
            } else if (playState == 1 && getPlaybackHeadPosition() == 0) {
                return false;
            }
        }
        boolean z = this.hasData;
        this.hasData = hasPendingData(j);
        if (z && this.hasData == null && playState != 1 && this.listener != null) {
            this.listener.onUnderrun(this.bufferSize, C.usToMs(this.bufferSizeUs));
        }
        return true;
    }

    public int getAvailableBufferSize(long j) {
        return this.bufferSize - ((int) (j - (getPlaybackHeadPosition() * ((long) this.outputPcmFrameSize))));
    }

    public boolean isStalled(long j) {
        return (this.forceResetWorkaroundTimeMs == C.TIME_UNSET || j <= 0 || SystemClock.elapsedRealtime() - this.forceResetWorkaroundTimeMs < FORCE_RESET_WORKAROUND_TIMEOUT_MS) ? 0 : 1;
    }

    public void handleEndOfStream(long j) {
        this.stopPlaybackHeadPosition = getPlaybackHeadPosition();
        this.stopTimestampUs = SystemClock.elapsedRealtime() * 1000;
        this.endPlaybackHeadPosition = j;
    }

    public boolean hasPendingData(long j) {
        if (j <= getPlaybackHeadPosition()) {
            if (forceHasPendingData() == null) {
                return 0;
            }
        }
        return 1;
    }

    public boolean pause() {
        resetSyncParams();
        if (this.stopTimestampUs != C.TIME_UNSET) {
            return false;
        }
        ((AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller)).reset();
        return true;
    }

    public void reset() {
        resetSyncParams();
        this.audioTrack = null;
        this.audioTimestampPoller = null;
    }

    private void maybeSampleSyncParams() {
        long playbackHeadPositionUs = getPlaybackHeadPositionUs();
        if (playbackHeadPositionUs != 0) {
            long nanoTime = System.nanoTime() / 1000;
            if (nanoTime - this.lastPlayheadSampleTimeUs >= 30000) {
                this.playheadOffsets[this.nextPlayheadOffsetIndex] = playbackHeadPositionUs - nanoTime;
                this.nextPlayheadOffsetIndex = (this.nextPlayheadOffsetIndex + 1) % 10;
                if (this.playheadOffsetCount < 10) {
                    this.playheadOffsetCount++;
                }
                this.lastPlayheadSampleTimeUs = nanoTime;
                this.smoothedPlayheadOffsetUs = 0;
                for (int i = 0; i < this.playheadOffsetCount; i++) {
                    this.smoothedPlayheadOffsetUs += this.playheadOffsets[i] / ((long) this.playheadOffsetCount);
                }
            }
            if (!this.needsPassthroughWorkarounds) {
                maybePollAndCheckTimestamp(nanoTime, playbackHeadPositionUs);
                maybeUpdateLatency(nanoTime);
            }
        }
    }

    private void maybePollAndCheckTimestamp(long j, long j2) {
        AudioTimestampPoller audioTimestampPoller = (AudioTimestampPoller) Assertions.checkNotNull(this.audioTimestampPoller);
        if (audioTimestampPoller.maybePollTimestamp(j)) {
            long timestampSystemTimeUs = audioTimestampPoller.getTimestampSystemTimeUs();
            long timestampPositionFrames = audioTimestampPoller.getTimestampPositionFrames();
            if (Math.abs(timestampSystemTimeUs - j) > 5000000) {
                this.listener.onSystemTimeUsMismatch(timestampPositionFrames, timestampSystemTimeUs, j, j2);
                audioTimestampPoller.rejectTimestamp();
            } else if (Math.abs(framesToDurationUs(timestampPositionFrames) - j2) > 5000000) {
                this.listener.onPositionFramesMismatch(timestampPositionFrames, timestampSystemTimeUs, j, j2);
                audioTimestampPoller.rejectTimestamp();
            } else {
                audioTimestampPoller.acceptTimestamp();
            }
        }
    }

    private void maybeUpdateLatency(long r8) {
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
        r0 = r7.isOutputPcm;
        if (r0 == 0) goto L_0x005b;
    L_0x0004:
        r0 = r7.getLatencyMethod;
        if (r0 == 0) goto L_0x005b;
    L_0x0008:
        r0 = r7.lastLatencySampleTimeUs;
        r0 = r8 - r0;
        r2 = 500000; // 0x7a120 float:7.00649E-40 double:2.47033E-318;
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 < 0) goto L_0x005b;
    L_0x0013:
        r0 = r7.getLatencyMethod;	 Catch:{ Exception -> 0x0056 }
        r1 = r7.audioTrack;	 Catch:{ Exception -> 0x0056 }
        r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1);	 Catch:{ Exception -> 0x0056 }
        r2 = 0;	 Catch:{ Exception -> 0x0056 }
        r2 = new java.lang.Object[r2];	 Catch:{ Exception -> 0x0056 }
        r0 = r0.invoke(r1, r2);	 Catch:{ Exception -> 0x0056 }
        r0 = (java.lang.Integer) r0;	 Catch:{ Exception -> 0x0056 }
        r0 = com.google.android.exoplayer2.util.Util.castNonNull(r0);	 Catch:{ Exception -> 0x0056 }
        r0 = (java.lang.Integer) r0;	 Catch:{ Exception -> 0x0056 }
        r0 = r0.intValue();	 Catch:{ Exception -> 0x0056 }
        r0 = (long) r0;	 Catch:{ Exception -> 0x0056 }
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;	 Catch:{ Exception -> 0x0056 }
        r0 = r0 * r2;	 Catch:{ Exception -> 0x0056 }
        r2 = r7.bufferSizeUs;	 Catch:{ Exception -> 0x0056 }
        r4 = 0;	 Catch:{ Exception -> 0x0056 }
        r0 = r0 - r2;	 Catch:{ Exception -> 0x0056 }
        r7.latencyUs = r0;	 Catch:{ Exception -> 0x0056 }
        r0 = r7.latencyUs;	 Catch:{ Exception -> 0x0056 }
        r2 = 0;	 Catch:{ Exception -> 0x0056 }
        r0 = java.lang.Math.max(r0, r2);	 Catch:{ Exception -> 0x0056 }
        r7.latencyUs = r0;	 Catch:{ Exception -> 0x0056 }
        r0 = r7.latencyUs;	 Catch:{ Exception -> 0x0056 }
        r4 = 5000000; // 0x4c4b40 float:7.006492E-39 double:2.470328E-317;	 Catch:{ Exception -> 0x0056 }
        r6 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));	 Catch:{ Exception -> 0x0056 }
        if (r6 <= 0) goto L_0x0059;	 Catch:{ Exception -> 0x0056 }
    L_0x004c:
        r0 = r7.listener;	 Catch:{ Exception -> 0x0056 }
        r4 = r7.latencyUs;	 Catch:{ Exception -> 0x0056 }
        r0.onInvalidLatency(r4);	 Catch:{ Exception -> 0x0056 }
        r7.latencyUs = r2;	 Catch:{ Exception -> 0x0056 }
        goto L_0x0059;
    L_0x0056:
        r0 = 0;
        r7.getLatencyMethod = r0;
    L_0x0059:
        r7.lastLatencySampleTimeUs = r8;
    L_0x005b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.AudioTrackPositionTracker.maybeUpdateLatency(long):void");
    }

    private long framesToDurationUs(long j) {
        return (j * 1000000) / ((long) this.outputSampleRate);
    }

    private void resetSyncParams() {
        this.smoothedPlayheadOffsetUs = 0;
        this.playheadOffsetCount = 0;
        this.nextPlayheadOffsetIndex = 0;
        this.lastPlayheadSampleTimeUs = 0;
    }

    private boolean forceHasPendingData() {
        return this.needsPassthroughWorkarounds && ((AudioTrack) Assertions.checkNotNull(this.audioTrack)).getPlayState() == 2 && getPlaybackHeadPosition() == 0;
    }

    private static boolean needsPassthroughWorkarounds(int i) {
        return Util.SDK_INT < 23 && (i == 5 || i == 6);
    }

    private long getPlaybackHeadPositionUs() {
        return framesToDurationUs(getPlaybackHeadPosition());
    }

    private long getPlaybackHeadPosition() {
        AudioTrack audioTrack = (AudioTrack) Assertions.checkNotNull(this.audioTrack);
        if (this.stopTimestampUs != C.TIME_UNSET) {
            return Math.min(this.endPlaybackHeadPosition, this.stopPlaybackHeadPosition + ((((SystemClock.elapsedRealtime() * 1000) - this.stopTimestampUs) * ((long) this.outputSampleRate)) / 1000000));
        }
        int playState = audioTrack.getPlayState();
        if (playState == 1) {
            return 0;
        }
        long playbackHeadPosition = 4294967295L & ((long) audioTrack.getPlaybackHeadPosition());
        if (this.needsPassthroughWorkarounds) {
            if (playState == 2 && playbackHeadPosition == 0) {
                this.passthroughWorkaroundPauseOffset = this.lastRawPlaybackHeadPosition;
            }
            playbackHeadPosition += this.passthroughWorkaroundPauseOffset;
        }
        if (Util.SDK_INT <= 28) {
            if (playbackHeadPosition == 0 && this.lastRawPlaybackHeadPosition > 0 && playState == 3) {
                if (this.forceResetWorkaroundTimeMs == C.TIME_UNSET) {
                    this.forceResetWorkaroundTimeMs = SystemClock.elapsedRealtime();
                }
                return this.lastRawPlaybackHeadPosition;
            }
            this.forceResetWorkaroundTimeMs = C.TIME_UNSET;
        }
        if (this.lastRawPlaybackHeadPosition > playbackHeadPosition) {
            this.rawPlaybackHeadWrapCount++;
        }
        this.lastRawPlaybackHeadPosition = playbackHeadPosition;
        return playbackHeadPosition + (this.rawPlaybackHeadWrapCount << 32);
    }
}
