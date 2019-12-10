package com.google.android.exoplayer2.audio;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioAttributes.Builder;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.ConditionVariable;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.audio.AudioSink.ConfigurationException;
import com.google.android.exoplayer2.audio.AudioSink.InitializationException;
import com.google.android.exoplayer2.audio.AudioSink.Listener;
import com.google.android.exoplayer2.audio.AudioSink.WriteException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class DefaultAudioSink implements AudioSink {
    private static final int BUFFER_MULTIPLICATION_FACTOR = 4;
    private static final int ERROR_BAD_VALUE = -2;
    private static final long MAX_BUFFER_DURATION_US = 750000;
    private static final long MIN_BUFFER_DURATION_US = 250000;
    private static final int MODE_STATIC = 0;
    private static final int MODE_STREAM = 1;
    private static final long PASSTHROUGH_BUFFER_DURATION_US = 250000;
    private static final int START_IN_SYNC = 1;
    private static final int START_NEED_SYNC = 2;
    private static final int START_NOT_SET = 0;
    private static final int STATE_INITIALIZED = 1;
    private static final String TAG = "AudioTrack";
    @SuppressLint({"InlinedApi"})
    private static final int WRITE_NON_BLOCKING = 1;
    public static boolean enablePreV21AudioSessionWorkaround = false;
    public static boolean failOnSpuriousAudioTimestamp = false;
    private AudioProcessor[] activeAudioProcessors;
    @Nullable
    private PlaybackParameters afterDrainPlaybackParameters;
    private AudioAttributes audioAttributes;
    @Nullable
    private final AudioCapabilities audioCapabilities;
    private final AudioProcessorChain audioProcessorChain;
    private int audioSessionId;
    private AudioTrack audioTrack;
    private final AudioTrackPositionTracker audioTrackPositionTracker;
    private AuxEffectInfo auxEffectInfo;
    @Nullable
    private ByteBuffer avSyncHeader;
    private int bufferSize;
    private int bytesUntilNextAvSync;
    private boolean canApplyPlaybackParameters;
    private final ChannelMappingAudioProcessor channelMappingAudioProcessor;
    private int drainingAudioProcessorIndex;
    private final boolean enableConvertHighResIntPcmToFloat;
    private int framesPerEncodedSample;
    private boolean handledEndOfStream;
    @Nullable
    private ByteBuffer inputBuffer;
    private int inputSampleRate;
    private boolean isInputPcm;
    @Nullable
    private AudioTrack keepSessionIdAudioTrack;
    private long lastFeedElapsedRealtimeMs;
    @Nullable
    private Listener listener;
    @Nullable
    private ByteBuffer outputBuffer;
    private ByteBuffer[] outputBuffers;
    private int outputChannelConfig;
    private int outputEncoding;
    private int outputPcmFrameSize;
    private int outputSampleRate;
    private int pcmFrameSize;
    private PlaybackParameters playbackParameters;
    private final ArrayDeque<PlaybackParametersCheckpoint> playbackParametersCheckpoints;
    private long playbackParametersOffsetUs;
    private long playbackParametersPositionUs;
    private boolean playing;
    private byte[] preV21OutputBuffer;
    private int preV21OutputBufferOffset;
    private boolean processingEnabled;
    private final ConditionVariable releasingConditionVariable;
    private boolean shouldConvertHighResIntPcmToFloat;
    private int startMediaTimeState;
    private long startMediaTimeUs;
    private long submittedEncodedFrames;
    private long submittedPcmBytes;
    private final AudioProcessor[] toFloatPcmAvailableAudioProcessors;
    private final AudioProcessor[] toIntPcmAvailableAudioProcessors;
    private final TrimmingAudioProcessor trimmingAudioProcessor;
    private boolean tunneling;
    private float volume;
    private long writtenEncodedFrames;
    private long writtenPcmBytes;

    public interface AudioProcessorChain {
        PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters);

        AudioProcessor[] getAudioProcessors();

        long getMediaDuration(long j);

        long getSkippedOutputFrameCount();
    }

    public static final class InvalidAudioTrackTimestampException extends RuntimeException {
        private InvalidAudioTrackTimestampException(String str) {
            super(str);
        }
    }

    private static final class PlaybackParametersCheckpoint {
        private final long mediaTimeUs;
        private final PlaybackParameters playbackParameters;
        private final long positionUs;

        private PlaybackParametersCheckpoint(PlaybackParameters playbackParameters, long j, long j2) {
            this.playbackParameters = playbackParameters;
            this.mediaTimeUs = j;
            this.positionUs = j2;
        }
    }

    public static class DefaultAudioProcessorChain implements AudioProcessorChain {
        private final AudioProcessor[] audioProcessors;
        private final SilenceSkippingAudioProcessor silenceSkippingAudioProcessor = new SilenceSkippingAudioProcessor();
        private final SonicAudioProcessor sonicAudioProcessor = new SonicAudioProcessor();

        public DefaultAudioProcessorChain(AudioProcessor... audioProcessorArr) {
            this.audioProcessors = (AudioProcessor[]) Arrays.copyOf(audioProcessorArr, audioProcessorArr.length + 2);
            this.audioProcessors[audioProcessorArr.length] = this.silenceSkippingAudioProcessor;
            this.audioProcessors[audioProcessorArr.length + 1] = this.sonicAudioProcessor;
        }

        public AudioProcessor[] getAudioProcessors() {
            return this.audioProcessors;
        }

        public PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters) {
            this.silenceSkippingAudioProcessor.setEnabled(playbackParameters.skipSilence);
            return new PlaybackParameters(this.sonicAudioProcessor.setSpeed(playbackParameters.speed), this.sonicAudioProcessor.setPitch(playbackParameters.pitch), playbackParameters.skipSilence);
        }

        public long getMediaDuration(long j) {
            return this.sonicAudioProcessor.scaleDurationForSpeedup(j);
        }

        public long getSkippedOutputFrameCount() {
            return this.silenceSkippingAudioProcessor.getSkippedFrames();
        }
    }

    private final class PositionTrackerListener implements AudioTrackPositionTracker.Listener {
        private PositionTrackerListener() {
        }

        public void onPositionFramesMismatch(long j, long j2, long j3, long j4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Spurious audio timestamp (frame position mismatch): ");
            stringBuilder.append(j);
            stringBuilder.append(", ");
            stringBuilder.append(j2);
            stringBuilder.append(", ");
            stringBuilder.append(j3);
            stringBuilder.append(", ");
            stringBuilder.append(j4);
            stringBuilder.append(", ");
            stringBuilder.append(DefaultAudioSink.this.getSubmittedFrames());
            stringBuilder.append(", ");
            stringBuilder.append(DefaultAudioSink.this.getWrittenFrames());
            j = stringBuilder.toString();
            if (DefaultAudioSink.failOnSpuriousAudioTimestamp) {
                throw new InvalidAudioTrackTimestampException(j);
            }
            Log.w(DefaultAudioSink.TAG, j);
        }

        public void onSystemTimeUsMismatch(long j, long j2, long j3, long j4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Spurious audio timestamp (system clock mismatch): ");
            stringBuilder.append(j);
            stringBuilder.append(", ");
            stringBuilder.append(j2);
            stringBuilder.append(", ");
            stringBuilder.append(j3);
            stringBuilder.append(", ");
            stringBuilder.append(j4);
            stringBuilder.append(", ");
            stringBuilder.append(DefaultAudioSink.this.getSubmittedFrames());
            stringBuilder.append(", ");
            stringBuilder.append(DefaultAudioSink.this.getWrittenFrames());
            j = stringBuilder.toString();
            if (DefaultAudioSink.failOnSpuriousAudioTimestamp) {
                throw new InvalidAudioTrackTimestampException(j);
            }
            Log.w(DefaultAudioSink.TAG, j);
        }

        public void onInvalidLatency(long j) {
            String str = DefaultAudioSink.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignoring impossibly large audio latency: ");
            stringBuilder.append(j);
            Log.w(str, stringBuilder.toString());
        }

        public void onUnderrun(int i, long j) {
            if (DefaultAudioSink.this.listener != null) {
                DefaultAudioSink.this.listener.onUnderrun(i, j, SystemClock.elapsedRealtime() - DefaultAudioSink.this.lastFeedElapsedRealtimeMs);
            }
        }
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessorArr) {
        this(audioCapabilities, audioProcessorArr, false);
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessorArr, boolean z) {
        this(audioCapabilities, new DefaultAudioProcessorChain(audioProcessorArr), z);
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessorChain audioProcessorChain, boolean z) {
        this.audioCapabilities = audioCapabilities;
        this.audioProcessorChain = (AudioProcessorChain) Assertions.checkNotNull(audioProcessorChain);
        this.enableConvertHighResIntPcmToFloat = z;
        this.releasingConditionVariable = new ConditionVariable(true);
        this.audioTrackPositionTracker = new AudioTrackPositionTracker(new PositionTrackerListener());
        this.channelMappingAudioProcessor = new ChannelMappingAudioProcessor();
        this.trimmingAudioProcessor = new TrimmingAudioProcessor();
        audioCapabilities = new ArrayList();
        Collections.addAll(audioCapabilities, new AudioProcessor[]{new ResamplingAudioProcessor(), this.channelMappingAudioProcessor, this.trimmingAudioProcessor});
        Collections.addAll(audioCapabilities, audioProcessorChain.getAudioProcessors());
        this.toIntPcmAvailableAudioProcessors = (AudioProcessor[]) audioCapabilities.toArray(new AudioProcessor[audioCapabilities.size()]);
        this.toFloatPcmAvailableAudioProcessors = new AudioProcessor[]{new FloatResamplingAudioProcessor()};
        this.volume = 1.0f;
        this.startMediaTimeState = 0;
        this.audioAttributes = AudioAttributes.DEFAULT;
        this.audioSessionId = 0;
        this.auxEffectInfo = new AuxEffectInfo(0, null);
        this.playbackParameters = PlaybackParameters.DEFAULT;
        this.drainingAudioProcessorIndex = -1;
        this.activeAudioProcessors = new AudioProcessor[0];
        this.outputBuffers = new ByteBuffer[0];
        this.playbackParametersCheckpoints = new ArrayDeque();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public boolean isEncodingSupported(int i) {
        boolean z = false;
        if (Util.isEncodingLinearPcm(i)) {
            if (i != 4 || Util.SDK_INT >= 21) {
                z = true;
            }
            return z;
        }
        if (!(this.audioCapabilities == null || this.audioCapabilities.supportsEncoding(i) == 0)) {
            z = true;
        }
        return z;
    }

    public long getCurrentPositionUs(boolean z) {
        if (isInitialized()) {
            if (this.startMediaTimeState != 0) {
                return this.startMediaTimeUs + applySkipping(applySpeedup(Math.min(this.audioTrackPositionTracker.getCurrentPositionUs(z), framesToDurationUs(getWrittenFrames()))));
            }
        }
        return Long.MIN_VALUE;
    }

    public void configure(int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5, int i6) throws ConfigurationException {
        this.inputSampleRate = i3;
        this.isInputPcm = Util.isEncodingLinearPcm(i);
        boolean z = true;
        int i7 = 0;
        boolean z2 = this.enableConvertHighResIntPcmToFloat && isEncodingSupported(1073741824) && Util.isEncodingHighResolutionIntegerPcm(i);
        this.shouldConvertHighResIntPcmToFloat = z2;
        if (this.isInputPcm) {
            this.pcmFrameSize = Util.getPcmFrameSize(i, i2);
        }
        z2 = this.isInputPcm && i != 4;
        if (!z2 || this.shouldConvertHighResIntPcmToFloat) {
            z = false;
        }
        this.canApplyPlaybackParameters = z;
        if (Util.SDK_INT < 21 && i2 == 8 && iArr == null) {
            iArr = new int[6];
            for (int i8 = 0; i8 < iArr.length; i8++) {
                iArr[i8] = i8;
            }
        }
        if (z2) {
            this.trimmingAudioProcessor.setTrimFrameCount(i5, i6);
            this.channelMappingAudioProcessor.setChannelMap(iArr);
            iArr = getAvailableAudioProcessors();
            i5 = iArr.length;
            i6 = i;
            i = 0;
            while (i7 < i5) {
                AudioProcessor audioProcessor = iArr[i7];
                try {
                    i |= audioProcessor.configure(i3, i2, i6);
                    if (audioProcessor.isActive()) {
                        i2 = audioProcessor.getOutputChannelCount();
                        i3 = audioProcessor.getOutputSampleRateHz();
                        i6 = audioProcessor.getOutputEncoding();
                    }
                    i7++;
                } catch (Throwable e) {
                    throw new ConfigurationException(e);
                }
            }
        }
        i6 = i;
        i = 0;
        iArr = getChannelConfig(i2, this.isInputPcm);
        if (iArr == null) {
            i3 = new StringBuilder();
            i3.append("Unsupported channel count: ");
            i3.append(i2);
            throw new ConfigurationException(i3.toString());
        } else if (i != 0 || isInitialized() == 0 || this.outputEncoding != i6 || this.outputSampleRate != i3 || this.outputChannelConfig != iArr) {
            reset();
            this.processingEnabled = z2;
            this.outputSampleRate = i3;
            this.outputChannelConfig = iArr;
            this.outputEncoding = i6;
            this.outputPcmFrameSize = this.isInputPcm != 0 ? Util.getPcmFrameSize(this.outputEncoding, i2) : -1;
            if (i4 == 0) {
                i4 = getDefaultBufferSize();
            }
            this.bufferSize = i4;
        }
    }

    private int getDefaultBufferSize() {
        if (!this.isInputPcm) {
            return (int) ((((long) getMaximumEncodedRateBytesPerSecond(this.outputEncoding)) * 250000) / 1000000);
        }
        int minBufferSize = AudioTrack.getMinBufferSize(this.outputSampleRate, this.outputChannelConfig, this.outputEncoding);
        Assertions.checkState(minBufferSize != -2);
        return Util.constrainValue(minBufferSize * 4, ((int) durationUsToFrames(250000)) * this.outputPcmFrameSize, (int) Math.max((long) minBufferSize, durationUsToFrames(MAX_BUFFER_DURATION_US) * ((long) this.outputPcmFrameSize)));
    }

    private void setupAudioProcessors() {
        ArrayList arrayList = new ArrayList();
        for (AudioProcessor audioProcessor : getAvailableAudioProcessors()) {
            if (audioProcessor.isActive()) {
                arrayList.add(audioProcessor);
            } else {
                audioProcessor.flush();
            }
        }
        int size = arrayList.size();
        this.activeAudioProcessors = (AudioProcessor[]) arrayList.toArray(new AudioProcessor[size]);
        this.outputBuffers = new ByteBuffer[size];
        flushAudioProcessors();
    }

    private void flushAudioProcessors() {
        for (int i = 0; i < this.activeAudioProcessors.length; i++) {
            AudioProcessor audioProcessor = this.activeAudioProcessors[i];
            audioProcessor.flush();
            this.outputBuffers[i] = audioProcessor.getOutput();
        }
    }

    private void initialize() throws InitializationException {
        this.releasingConditionVariable.block();
        this.audioTrack = initializeAudioTrack();
        int audioSessionId = this.audioTrack.getAudioSessionId();
        if (enablePreV21AudioSessionWorkaround && Util.SDK_INT < 21) {
            if (!(this.keepSessionIdAudioTrack == null || audioSessionId == this.keepSessionIdAudioTrack.getAudioSessionId())) {
                releaseKeepSessionIdAudioTrack();
            }
            if (this.keepSessionIdAudioTrack == null) {
                this.keepSessionIdAudioTrack = initializeKeepSessionIdAudioTrack(audioSessionId);
            }
        }
        if (this.audioSessionId != audioSessionId) {
            this.audioSessionId = audioSessionId;
            if (this.listener != null) {
                this.listener.onAudioSessionId(audioSessionId);
            }
        }
        this.playbackParameters = this.canApplyPlaybackParameters ? this.audioProcessorChain.applyPlaybackParameters(this.playbackParameters) : PlaybackParameters.DEFAULT;
        setupAudioProcessors();
        this.audioTrackPositionTracker.setAudioTrack(this.audioTrack, this.outputEncoding, this.outputPcmFrameSize, this.bufferSize);
        setVolumeInternal();
        if (this.auxEffectInfo.effectId != 0) {
            this.audioTrack.attachAuxEffect(this.auxEffectInfo.effectId);
            this.audioTrack.setAuxEffectSendLevel(this.auxEffectInfo.sendLevel);
        }
    }

    public void play() {
        this.playing = true;
        if (isInitialized()) {
            this.audioTrackPositionTracker.start();
            this.audioTrack.play();
        }
    }

    public void handleDiscontinuity() {
        if (this.startMediaTimeState == 1) {
            this.startMediaTimeState = 2;
        }
    }

    public boolean handleBuffer(ByteBuffer byteBuffer, long j) throws InitializationException, WriteException {
        boolean z;
        long inputFramesToDurationUs;
        ByteBuffer byteBuffer2 = byteBuffer;
        long j2 = j;
        if (this.inputBuffer != null) {
            if (byteBuffer2 != r0.inputBuffer) {
                z = false;
                Assertions.checkArgument(z);
                if (!isInitialized()) {
                    initialize();
                    if (r0.playing) {
                        play();
                    }
                }
                if (!r0.audioTrackPositionTracker.mayHandleBuffer(getWrittenFrames())) {
                    return false;
                }
                if (r0.inputBuffer == null) {
                    if (!byteBuffer.hasRemaining()) {
                        return true;
                    }
                    if (!r0.isInputPcm && r0.framesPerEncodedSample == 0) {
                        r0.framesPerEncodedSample = getFramesPerEncodedSample(r0.outputEncoding, byteBuffer2);
                        if (r0.framesPerEncodedSample == 0) {
                            return true;
                        }
                    }
                    if (r0.afterDrainPlaybackParameters != null) {
                        if (!drainAudioProcessorsToEndOfStream()) {
                            return false;
                        }
                        PlaybackParameters playbackParameters = r0.afterDrainPlaybackParameters;
                        r0.afterDrainPlaybackParameters = null;
                        r0.playbackParametersCheckpoints.add(new PlaybackParametersCheckpoint(r0.audioProcessorChain.applyPlaybackParameters(playbackParameters), Math.max(0, j2), framesToDurationUs(getWrittenFrames())));
                        setupAudioProcessors();
                    }
                    if (r0.startMediaTimeState != 0) {
                        r0.startMediaTimeUs = Math.max(0, j2);
                        r0.startMediaTimeState = 1;
                    } else {
                        inputFramesToDurationUs = r0.startMediaTimeUs + inputFramesToDurationUs(getSubmittedFrames() - r0.trimmingAudioProcessor.getTrimmedFrameCount());
                        if (r0.startMediaTimeState == 1 && Math.abs(inputFramesToDurationUs - j2) > 200000) {
                            String str = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Discontinuity detected [expected ");
                            stringBuilder.append(inputFramesToDurationUs);
                            stringBuilder.append(", got ");
                            stringBuilder.append(j2);
                            stringBuilder.append("]");
                            Log.e(str, stringBuilder.toString());
                            r0.startMediaTimeState = 2;
                        }
                        if (r0.startMediaTimeState == 2) {
                            inputFramesToDurationUs = j2 - inputFramesToDurationUs;
                            r0.startMediaTimeUs += inputFramesToDurationUs;
                            r0.startMediaTimeState = 1;
                            if (!(r0.listener == null || inputFramesToDurationUs == 0)) {
                                r0.listener.onPositionDiscontinuity();
                            }
                        }
                    }
                    if (r0.isInputPcm) {
                        r0.submittedEncodedFrames += (long) r0.framesPerEncodedSample;
                    } else {
                        r0.submittedPcmBytes += (long) byteBuffer.remaining();
                    }
                    r0.inputBuffer = byteBuffer2;
                }
                if (r0.processingEnabled) {
                    writeBuffer(r0.inputBuffer, j2);
                } else {
                    processBuffers(j2);
                }
                if (!r0.inputBuffer.hasRemaining()) {
                    r0.inputBuffer = null;
                    return true;
                } else if (r0.audioTrackPositionTracker.isStalled(getWrittenFrames())) {
                    return false;
                } else {
                    Log.w(TAG, "Resetting stalled audio track");
                    reset();
                    return true;
                }
            }
        }
        z = true;
        Assertions.checkArgument(z);
        if (isInitialized()) {
            initialize();
            if (r0.playing) {
                play();
            }
        }
        if (!r0.audioTrackPositionTracker.mayHandleBuffer(getWrittenFrames())) {
            return false;
        }
        if (r0.inputBuffer == null) {
            if (!byteBuffer.hasRemaining()) {
                return true;
            }
            r0.framesPerEncodedSample = getFramesPerEncodedSample(r0.outputEncoding, byteBuffer2);
            if (r0.framesPerEncodedSample == 0) {
                return true;
            }
            if (r0.afterDrainPlaybackParameters != null) {
                if (!drainAudioProcessorsToEndOfStream()) {
                    return false;
                }
                PlaybackParameters playbackParameters2 = r0.afterDrainPlaybackParameters;
                r0.afterDrainPlaybackParameters = null;
                r0.playbackParametersCheckpoints.add(new PlaybackParametersCheckpoint(r0.audioProcessorChain.applyPlaybackParameters(playbackParameters2), Math.max(0, j2), framesToDurationUs(getWrittenFrames())));
                setupAudioProcessors();
            }
            if (r0.startMediaTimeState != 0) {
                inputFramesToDurationUs = r0.startMediaTimeUs + inputFramesToDurationUs(getSubmittedFrames() - r0.trimmingAudioProcessor.getTrimmedFrameCount());
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Discontinuity detected [expected ");
                stringBuilder2.append(inputFramesToDurationUs);
                stringBuilder2.append(", got ");
                stringBuilder2.append(j2);
                stringBuilder2.append("]");
                Log.e(str2, stringBuilder2.toString());
                r0.startMediaTimeState = 2;
                if (r0.startMediaTimeState == 2) {
                    inputFramesToDurationUs = j2 - inputFramesToDurationUs;
                    r0.startMediaTimeUs += inputFramesToDurationUs;
                    r0.startMediaTimeState = 1;
                    r0.listener.onPositionDiscontinuity();
                }
            } else {
                r0.startMediaTimeUs = Math.max(0, j2);
                r0.startMediaTimeState = 1;
            }
            if (r0.isInputPcm) {
                r0.submittedEncodedFrames += (long) r0.framesPerEncodedSample;
            } else {
                r0.submittedPcmBytes += (long) byteBuffer.remaining();
            }
            r0.inputBuffer = byteBuffer2;
        }
        if (r0.processingEnabled) {
            writeBuffer(r0.inputBuffer, j2);
        } else {
            processBuffers(j2);
        }
        if (!r0.inputBuffer.hasRemaining()) {
            r0.inputBuffer = null;
            return true;
        } else if (r0.audioTrackPositionTracker.isStalled(getWrittenFrames())) {
            return false;
        } else {
            Log.w(TAG, "Resetting stalled audio track");
            reset();
            return true;
        }
    }

    private void processBuffers(long j) throws WriteException {
        int length = this.activeAudioProcessors.length;
        int i = length;
        while (i >= 0) {
            ByteBuffer byteBuffer = i > 0 ? this.outputBuffers[i - 1] : this.inputBuffer != null ? this.inputBuffer : AudioProcessor.EMPTY_BUFFER;
            if (i == length) {
                writeBuffer(byteBuffer, j);
            } else {
                AudioProcessor audioProcessor = this.activeAudioProcessors[i];
                audioProcessor.queueInput(byteBuffer);
                ByteBuffer output = audioProcessor.getOutput();
                this.outputBuffers[i] = output;
                if (output.hasRemaining()) {
                    i++;
                }
            }
            if (!byteBuffer.hasRemaining()) {
                i--;
            } else {
                return;
            }
        }
    }

    private void writeBuffer(ByteBuffer byteBuffer, long j) throws WriteException {
        if (byteBuffer.hasRemaining()) {
            int remaining;
            boolean z = true;
            int i = 0;
            if (this.outputBuffer != null) {
                Assertions.checkArgument(this.outputBuffer == byteBuffer);
            } else {
                this.outputBuffer = byteBuffer;
                if (Util.SDK_INT < 21) {
                    remaining = byteBuffer.remaining();
                    if (this.preV21OutputBuffer == null || this.preV21OutputBuffer.length < remaining) {
                        this.preV21OutputBuffer = new byte[remaining];
                    }
                    int position = byteBuffer.position();
                    byteBuffer.get(this.preV21OutputBuffer, 0, remaining);
                    byteBuffer.position(position);
                    this.preV21OutputBufferOffset = 0;
                }
            }
            remaining = byteBuffer.remaining();
            if (Util.SDK_INT < 21) {
                j = this.audioTrackPositionTracker.getAvailableBufferSize(this.writtenPcmBytes);
                if (j > null) {
                    i = this.audioTrack.write(this.preV21OutputBuffer, this.preV21OutputBufferOffset, Math.min(remaining, j));
                    if (i > 0) {
                        this.preV21OutputBufferOffset += i;
                        byteBuffer.position(byteBuffer.position() + i);
                    }
                }
            } else if (this.tunneling) {
                if (j == C.TIME_UNSET) {
                    z = false;
                }
                Assertions.checkState(z);
                i = writeNonBlockingWithAvSyncV21(this.audioTrack, byteBuffer, remaining, j);
            } else {
                i = writeNonBlockingV21(this.audioTrack, byteBuffer, remaining);
            }
            this.lastFeedElapsedRealtimeMs = SystemClock.elapsedRealtime();
            if (i >= 0) {
                if (this.isInputPcm != null) {
                    this.writtenPcmBytes += (long) i;
                }
                if (i == remaining) {
                    if (this.isInputPcm == null) {
                        this.writtenEncodedFrames += (long) this.framesPerEncodedSample;
                    }
                    this.outputBuffer = null;
                }
                return;
            }
            throw new WriteException(i);
        }
    }

    public void playToEndOfStream() throws WriteException {
        if (!this.handledEndOfStream) {
            if (isInitialized()) {
                if (drainAudioProcessorsToEndOfStream()) {
                    this.audioTrackPositionTracker.handleEndOfStream(getWrittenFrames());
                    this.audioTrack.stop();
                    this.bytesUntilNextAvSync = 0;
                    this.handledEndOfStream = true;
                }
            }
        }
    }

    private boolean drainAudioProcessorsToEndOfStream() throws com.google.android.exoplayer2.audio.AudioSink.WriteException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = this;
        r0 = r8.drainingAudioProcessorIndex;
        r1 = -1;
        r2 = 1;
        r3 = 0;
        if (r0 != r1) goto L_0x0014;
    L_0x0007:
        r0 = r8.processingEnabled;
        if (r0 == 0) goto L_0x000d;
    L_0x000b:
        r0 = 0;
        goto L_0x0010;
    L_0x000d:
        r0 = r8.activeAudioProcessors;
        r0 = r0.length;
    L_0x0010:
        r8.drainingAudioProcessorIndex = r0;
    L_0x0012:
        r0 = 1;
        goto L_0x0015;
    L_0x0014:
        r0 = 0;
    L_0x0015:
        r4 = r8.drainingAudioProcessorIndex;
        r5 = r8.activeAudioProcessors;
        r5 = r5.length;
        r6 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        if (r4 >= r5) goto L_0x003c;
    L_0x0021:
        r4 = r8.activeAudioProcessors;
        r5 = r8.drainingAudioProcessorIndex;
        r4 = r4[r5];
        if (r0 == 0) goto L_0x002c;
    L_0x0029:
        r4.queueEndOfStream();
    L_0x002c:
        r8.processBuffers(r6);
        r0 = r4.isEnded();
        if (r0 != 0) goto L_0x0036;
    L_0x0035:
        return r3;
    L_0x0036:
        r0 = r8.drainingAudioProcessorIndex;
        r0 = r0 + r2;
        r8.drainingAudioProcessorIndex = r0;
        goto L_0x0012;
    L_0x003c:
        r0 = r8.outputBuffer;
        if (r0 == 0) goto L_0x004a;
    L_0x0040:
        r0 = r8.outputBuffer;
        r8.writeBuffer(r0, r6);
        r0 = r8.outputBuffer;
        if (r0 == 0) goto L_0x004a;
    L_0x0049:
        return r3;
    L_0x004a:
        r8.drainingAudioProcessorIndex = r1;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.DefaultAudioSink.drainAudioProcessorsToEndOfStream():boolean");
    }

    public boolean isEnded() {
        if (isInitialized()) {
            if (!this.handledEndOfStream || hasPendingData()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasPendingData() {
        return isInitialized() && this.audioTrackPositionTracker.hasPendingData(getWrittenFrames());
    }

    public PlaybackParameters setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (!isInitialized() || this.canApplyPlaybackParameters) {
            Object access$200 = this.afterDrainPlaybackParameters != null ? this.afterDrainPlaybackParameters : !this.playbackParametersCheckpoints.isEmpty() ? ((PlaybackParametersCheckpoint) this.playbackParametersCheckpoints.getLast()).playbackParameters : this.playbackParameters;
            if (!playbackParameters.equals(access$200)) {
                if (isInitialized()) {
                    this.afterDrainPlaybackParameters = playbackParameters;
                } else {
                    this.playbackParameters = this.audioProcessorChain.applyPlaybackParameters(playbackParameters);
                }
            }
            return this.playbackParameters;
        }
        this.playbackParameters = PlaybackParameters.DEFAULT;
        return this.playbackParameters;
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.playbackParameters;
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) {
        if (!this.audioAttributes.equals(audioAttributes)) {
            this.audioAttributes = audioAttributes;
            if (this.tunneling == null) {
                reset();
                this.audioSessionId = null;
            }
        }
    }

    public void setAudioSessionId(int i) {
        if (this.audioSessionId != i) {
            this.audioSessionId = i;
            reset();
        }
    }

    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        if (!this.auxEffectInfo.equals(auxEffectInfo)) {
            int i = auxEffectInfo.effectId;
            float f = auxEffectInfo.sendLevel;
            if (this.audioTrack != null) {
                if (this.auxEffectInfo.effectId != i) {
                    this.audioTrack.attachAuxEffect(i);
                }
                if (i != 0) {
                    this.audioTrack.setAuxEffectSendLevel(f);
                }
            }
            this.auxEffectInfo = auxEffectInfo;
        }
    }

    public void enableTunnelingV21(int i) {
        Assertions.checkState(Util.SDK_INT >= 21);
        if (!this.tunneling || this.audioSessionId != i) {
            this.tunneling = true;
            this.audioSessionId = i;
            reset();
        }
    }

    public void disableTunneling() {
        if (this.tunneling) {
            this.tunneling = false;
            this.audioSessionId = 0;
            reset();
        }
    }

    public void setVolume(float f) {
        if (this.volume != f) {
            this.volume = f;
            setVolumeInternal();
        }
    }

    private void setVolumeInternal() {
        if (!isInitialized()) {
            return;
        }
        if (Util.SDK_INT >= 21) {
            setVolumeInternalV21(this.audioTrack, this.volume);
        } else {
            setVolumeInternalV3(this.audioTrack, this.volume);
        }
    }

    public void pause() {
        this.playing = false;
        if (isInitialized() && this.audioTrackPositionTracker.pause()) {
            this.audioTrack.pause();
        }
    }

    public void reset() {
        if (isInitialized()) {
            this.submittedPcmBytes = 0;
            this.submittedEncodedFrames = 0;
            this.writtenPcmBytes = 0;
            this.writtenEncodedFrames = 0;
            this.framesPerEncodedSample = 0;
            if (this.afterDrainPlaybackParameters != null) {
                this.playbackParameters = this.afterDrainPlaybackParameters;
                this.afterDrainPlaybackParameters = null;
            } else if (!this.playbackParametersCheckpoints.isEmpty()) {
                this.playbackParameters = ((PlaybackParametersCheckpoint) this.playbackParametersCheckpoints.getLast()).playbackParameters;
            }
            this.playbackParametersCheckpoints.clear();
            this.playbackParametersOffsetUs = 0;
            this.playbackParametersPositionUs = 0;
            this.trimmingAudioProcessor.resetTrimmedFrameCount();
            this.inputBuffer = null;
            this.outputBuffer = null;
            flushAudioProcessors();
            this.handledEndOfStream = false;
            this.drainingAudioProcessorIndex = -1;
            this.avSyncHeader = null;
            this.bytesUntilNextAvSync = 0;
            this.startMediaTimeState = 0;
            if (this.audioTrackPositionTracker.isPlaying()) {
                this.audioTrack.pause();
            }
            final AudioTrack audioTrack = this.audioTrack;
            this.audioTrack = null;
            this.audioTrackPositionTracker.reset();
            this.releasingConditionVariable.close();
            new Thread() {
                public void run() {
                    try {
                        audioTrack.flush();
                        audioTrack.release();
                    } finally {
                        DefaultAudioSink.this.releasingConditionVariable.open();
                    }
                }
            }.start();
        }
    }

    public void release() {
        reset();
        releaseKeepSessionIdAudioTrack();
        for (AudioProcessor reset : this.toIntPcmAvailableAudioProcessors) {
            reset.reset();
        }
        for (AudioProcessor reset2 : this.toFloatPcmAvailableAudioProcessors) {
            reset2.reset();
        }
        this.audioSessionId = 0;
        this.playing = false;
    }

    private void releaseKeepSessionIdAudioTrack() {
        if (this.keepSessionIdAudioTrack != null) {
            final AudioTrack audioTrack = this.keepSessionIdAudioTrack;
            this.keepSessionIdAudioTrack = null;
            new Thread() {
                public void run() {
                    audioTrack.release();
                }
            }.start();
        }
    }

    private long applySpeedup(long j) {
        PlaybackParametersCheckpoint playbackParametersCheckpoint = null;
        while (!this.playbackParametersCheckpoints.isEmpty() && j >= ((PlaybackParametersCheckpoint) this.playbackParametersCheckpoints.getFirst()).positionUs) {
            playbackParametersCheckpoint = (PlaybackParametersCheckpoint) this.playbackParametersCheckpoints.remove();
        }
        if (playbackParametersCheckpoint != null) {
            this.playbackParameters = playbackParametersCheckpoint.playbackParameters;
            this.playbackParametersPositionUs = playbackParametersCheckpoint.positionUs;
            this.playbackParametersOffsetUs = playbackParametersCheckpoint.mediaTimeUs - this.startMediaTimeUs;
        }
        if (this.playbackParameters.speed == 1.0f) {
            return (j + this.playbackParametersOffsetUs) - this.playbackParametersPositionUs;
        }
        if (this.playbackParametersCheckpoints.isEmpty()) {
            return this.playbackParametersOffsetUs + this.audioProcessorChain.getMediaDuration(j - this.playbackParametersPositionUs);
        }
        return this.playbackParametersOffsetUs + Util.getMediaDurationForPlayoutDuration(j - this.playbackParametersPositionUs, this.playbackParameters.speed);
    }

    private long applySkipping(long j) {
        return j + framesToDurationUs(this.audioProcessorChain.getSkippedOutputFrameCount());
    }

    private boolean isInitialized() {
        return this.audioTrack != null;
    }

    private long inputFramesToDurationUs(long j) {
        return (j * 1000000) / ((long) this.inputSampleRate);
    }

    private long framesToDurationUs(long j) {
        return (j * 1000000) / ((long) this.outputSampleRate);
    }

    private long durationUsToFrames(long j) {
        return (j * ((long) this.outputSampleRate)) / 1000000;
    }

    private long getSubmittedFrames() {
        return this.isInputPcm ? this.submittedPcmBytes / ((long) this.pcmFrameSize) : this.submittedEncodedFrames;
    }

    private long getWrittenFrames() {
        return this.isInputPcm ? this.writtenPcmBytes / ((long) this.outputPcmFrameSize) : this.writtenEncodedFrames;
    }

    private android.media.AudioTrack initializeAudioTrack() throws com.google.android.exoplayer2.audio.AudioSink.InitializationException {
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
        r9 = this;
        r0 = com.google.android.exoplayer2.util.Util.SDK_INT;
        r1 = 21;
        if (r0 < r1) goto L_0x000b;
    L_0x0006:
        r0 = r9.createAudioTrackV21();
        goto L_0x0038;
    L_0x000b:
        r0 = r9.audioAttributes;
        r0 = r0.usage;
        r2 = com.google.android.exoplayer2.util.Util.getStreamTypeForAudioUsage(r0);
        r0 = r9.audioSessionId;
        if (r0 != 0) goto L_0x0027;
    L_0x0017:
        r0 = new android.media.AudioTrack;
        r3 = r9.outputSampleRate;
        r4 = r9.outputChannelConfig;
        r5 = r9.outputEncoding;
        r6 = r9.bufferSize;
        r7 = 1;
        r1 = r0;
        r1.<init>(r2, r3, r4, r5, r6, r7);
        goto L_0x0038;
    L_0x0027:
        r0 = new android.media.AudioTrack;
        r3 = r9.outputSampleRate;
        r4 = r9.outputChannelConfig;
        r5 = r9.outputEncoding;
        r6 = r9.bufferSize;
        r7 = 1;
        r8 = r9.audioSessionId;
        r1 = r0;
        r1.<init>(r2, r3, r4, r5, r6, r7, r8);
    L_0x0038:
        r1 = r0.getState();
        r2 = 1;
        if (r1 != r2) goto L_0x0040;
    L_0x003f:
        return r0;
    L_0x0040:
        r0.release();	 Catch:{ Exception -> 0x0043 }
    L_0x0043:
        r0 = new com.google.android.exoplayer2.audio.AudioSink$InitializationException;
        r2 = r9.outputSampleRate;
        r3 = r9.outputChannelConfig;
        r4 = r9.bufferSize;
        r0.<init>(r1, r2, r3, r4);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.DefaultAudioSink.initializeAudioTrack():android.media.AudioTrack");
    }

    @TargetApi(21)
    private AudioTrack createAudioTrackV21() {
        AudioAttributes build;
        if (this.tunneling) {
            build = new Builder().setContentType(3).setFlags(16).setUsage(1).build();
        } else {
            build = this.audioAttributes.getAudioAttributesV21();
        }
        return new AudioTrack(build, new AudioFormat.Builder().setChannelMask(this.outputChannelConfig).setEncoding(this.outputEncoding).setSampleRate(this.outputSampleRate).build(), this.bufferSize, 1, this.audioSessionId != 0 ? this.audioSessionId : 0);
    }

    private AudioTrack initializeKeepSessionIdAudioTrack(int i) {
        return new AudioTrack(3, 4000, 4, 2, 2, 0, i);
    }

    private AudioProcessor[] getAvailableAudioProcessors() {
        return this.shouldConvertHighResIntPcmToFloat ? this.toFloatPcmAvailableAudioProcessors : this.toIntPcmAvailableAudioProcessors;
    }

    private static int getChannelConfig(int i, boolean z) {
        if (Util.SDK_INT <= 28 && !z) {
            if (i == 7) {
                i = true;
            } else if (i == 3 || i == 4 || i == 5) {
                i = true;
            }
        }
        if (Util.SDK_INT <= 26 && "fugu".equals(Util.DEVICE) && !z && r2 == true) {
            i = 2;
        }
        return Util.getAudioTrackChannelConfig(i);
    }

    private static int getMaximumEncodedRateBytesPerSecond(int i) {
        if (i == 14) {
            return 3062500;
        }
        switch (i) {
            case 5:
                return 80000;
            case 6:
                return 768000;
            case 7:
                return 192000;
            case 8:
                return 2250000;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static int getFramesPerEncodedSample(int i, ByteBuffer byteBuffer) {
        if (i != 7) {
            if (i != 8) {
                if (i == 5) {
                    return Ac3Util.getAc3SyncframeAudioSampleCount();
                }
                if (i == 6) {
                    return Ac3Util.parseEAc3SyncframeAudioSampleCount(byteBuffer);
                }
                if (i == 14) {
                    i = Ac3Util.findTrueHdSyncframeOffset(byteBuffer);
                    if (i == -1) {
                        i = 0;
                    } else {
                        i = Ac3Util.parseTrueHdSyncframeAudioSampleCount(byteBuffer, i) * 16;
                    }
                    return i;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected audio encoding: ");
                stringBuilder.append(i);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        return DtsUtil.parseDtsAudioSampleCount(byteBuffer);
    }

    @TargetApi(21)
    private static int writeNonBlockingV21(AudioTrack audioTrack, ByteBuffer byteBuffer, int i) {
        return audioTrack.write(byteBuffer, i, 1);
    }

    @TargetApi(21)
    private int writeNonBlockingWithAvSyncV21(AudioTrack audioTrack, ByteBuffer byteBuffer, int i, long j) {
        if (this.avSyncHeader == null) {
            this.avSyncHeader = ByteBuffer.allocate(16);
            this.avSyncHeader.order(ByteOrder.BIG_ENDIAN);
            this.avSyncHeader.putInt(1431633921);
        }
        if (this.bytesUntilNextAvSync == 0) {
            this.avSyncHeader.putInt(4, i);
            this.avSyncHeader.putLong(8, j * 1000);
            this.avSyncHeader.position(0);
            this.bytesUntilNextAvSync = i;
        }
        j = this.avSyncHeader.remaining();
        if (j > null) {
            int write = audioTrack.write(this.avSyncHeader, j, 1);
            if (write < 0) {
                this.bytesUntilNextAvSync = 0;
                return write;
            } else if (write < j) {
                return 0;
            }
        }
        audioTrack = writeNonBlockingV21(audioTrack, byteBuffer, i);
        if (audioTrack < null) {
            this.bytesUntilNextAvSync = 0;
            return audioTrack;
        }
        this.bytesUntilNextAvSync -= audioTrack;
        return audioTrack;
    }

    @TargetApi(21)
    private static void setVolumeInternalV21(AudioTrack audioTrack, float f) {
        audioTrack.setVolume(f);
    }

    private static void setVolumeInternalV3(AudioTrack audioTrack, float f) {
        audioTrack.setStereoVolume(f, f);
    }
}
