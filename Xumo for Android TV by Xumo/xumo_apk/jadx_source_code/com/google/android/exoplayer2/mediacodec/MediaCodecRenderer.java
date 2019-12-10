package com.google.android.exoplayer2.mediacodec;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodec.CodecException;
import android.media.MediaCodec.CryptoInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

@TargetApi(16)
public abstract class MediaCodecRenderer extends BaseRenderer {
    private static final byte[] ADAPTATION_WORKAROUND_BUFFER = Util.getBytesFromHexString("0000016742C00BDA259000000168CE0F13200000016588840DCE7118A0002FBF1C31C3275D78");
    private static final int ADAPTATION_WORKAROUND_MODE_ALWAYS = 2;
    private static final int ADAPTATION_WORKAROUND_MODE_NEVER = 0;
    private static final int ADAPTATION_WORKAROUND_MODE_SAME_RESOLUTION = 1;
    private static final int ADAPTATION_WORKAROUND_SLICE_WIDTH_HEIGHT = 32;
    protected static final float CODEC_OPERATING_RATE_UNSET = -1.0f;
    protected static final int KEEP_CODEC_RESULT_NO = 0;
    protected static final int KEEP_CODEC_RESULT_YES_WITHOUT_RECONFIGURATION = 1;
    protected static final int KEEP_CODEC_RESULT_YES_WITH_RECONFIGURATION = 3;
    private static final long MAX_CODEC_HOTSWAP_TIME_MS = 1000;
    private static final int RECONFIGURATION_STATE_NONE = 0;
    private static final int RECONFIGURATION_STATE_QUEUE_PENDING = 2;
    private static final int RECONFIGURATION_STATE_WRITE_PENDING = 1;
    private static final int REINITIALIZATION_STATE_NONE = 0;
    private static final int REINITIALIZATION_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int REINITIALIZATION_STATE_WAIT_END_OF_STREAM = 2;
    private static final String TAG = "MediaCodecRenderer";
    private final float assumedMinimumCodecOperatingRate;
    @Nullable
    private ArrayDeque<MediaCodecInfo> availableCodecInfos;
    private final DecoderInputBuffer buffer;
    private MediaCodec codec;
    private int codecAdaptationWorkaroundMode;
    private boolean codecConfiguredWithOperatingRate;
    private long codecHotswapDeadlineMs;
    @Nullable
    private MediaCodecInfo codecInfo;
    private boolean codecNeedsAdaptationWorkaroundBuffer;
    private boolean codecNeedsDiscardToSpsWorkaround;
    private boolean codecNeedsEosFlushWorkaround;
    private boolean codecNeedsEosOutputExceptionWorkaround;
    private boolean codecNeedsEosPropagation;
    private boolean codecNeedsFlushWorkaround;
    private boolean codecNeedsMonoChannelCountWorkaround;
    private boolean codecNeedsReconfigureWorkaround;
    private float codecOperatingRate;
    private boolean codecReceivedBuffers;
    private boolean codecReceivedEos;
    private int codecReconfigurationState;
    private boolean codecReconfigured;
    private int codecReinitializationState;
    private final List<Long> decodeOnlyPresentationTimestamps;
    protected DecoderCounters decoderCounters;
    private DrmSession<FrameworkMediaCrypto> drmSession;
    @Nullable
    private final DrmSessionManager<FrameworkMediaCrypto> drmSessionManager;
    private final DecoderInputBuffer flagsOnlyBuffer;
    private Format format;
    private final FormatHolder formatHolder;
    private final TimedValueQueue<Format> formatQueue;
    private ByteBuffer[] inputBuffers;
    private int inputIndex;
    private boolean inputStreamEnded;
    private final MediaCodecSelector mediaCodecSelector;
    private ByteBuffer outputBuffer;
    private final BufferInfo outputBufferInfo;
    private ByteBuffer[] outputBuffers;
    private Format outputFormat;
    private int outputIndex;
    private boolean outputStreamEnded;
    private DrmSession<FrameworkMediaCrypto> pendingDrmSession;
    private Format pendingFormat;
    private final boolean playClearSamplesWithoutKeys;
    @Nullable
    private DecoderInitializationException preferredDecoderInitializationException;
    private float rendererOperatingRate;
    private boolean shouldSkipAdaptationWorkaroundOutputBuffer;
    private boolean shouldSkipOutputBuffer;
    private boolean waitingForFirstSyncFrame;
    private boolean waitingForKeys;

    public static class DecoderInitializationException extends Exception {
        private static final int CUSTOM_ERROR_CODE_BASE = -50000;
        private static final int DECODER_QUERY_ERROR = -49998;
        private static final int NO_SUITABLE_DECODER_ERROR = -49999;
        public final String decoderName;
        public final String diagnosticInfo;
        @Nullable
        public final DecoderInitializationException fallbackDecoderInitializationException;
        public final String mimeType;
        public final boolean secureDecoderRequired;

        public DecoderInitializationException(Format format, Throwable th, boolean z, int i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Decoder init failed: [");
            stringBuilder.append(i);
            stringBuilder.append("], ");
            stringBuilder.append(format);
            this(stringBuilder.toString(), th, format.sampleMimeType, z, null, buildCustomDiagnosticInfo(i), null);
        }

        public DecoderInitializationException(Format format, Throwable th, boolean z, String str) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Decoder init failed: ");
            stringBuilder.append(str);
            stringBuilder.append(", ");
            stringBuilder.append(format);
            String stringBuilder2 = stringBuilder.toString();
            String str2 = format.sampleMimeType;
            if (Util.SDK_INT >= 21) {
                format = getDiagnosticInfoV21(th);
            } else {
                format = null;
            }
            this(stringBuilder2, th, str2, z, str, format, null);
        }

        private DecoderInitializationException(String str, Throwable th, String str2, boolean z, @Nullable String str3, @Nullable String str4, @Nullable DecoderInitializationException decoderInitializationException) {
            super(str, th);
            this.mimeType = str2;
            this.secureDecoderRequired = z;
            this.decoderName = str3;
            this.diagnosticInfo = str4;
            this.fallbackDecoderInitializationException = decoderInitializationException;
        }

        @CheckResult
        private DecoderInitializationException copyWithFallbackException(DecoderInitializationException decoderInitializationException) {
            return new DecoderInitializationException(getMessage(), getCause(), this.mimeType, this.secureDecoderRequired, this.decoderName, this.diagnosticInfo, decoderInitializationException);
        }

        @TargetApi(21)
        private static String getDiagnosticInfoV21(Throwable th) {
            return th instanceof CodecException ? ((CodecException) th).getDiagnosticInfo() : null;
        }

        private static String buildCustomDiagnosticInfo(int i) {
            String str = i < 0 ? "neg_" : "";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("com.google.android.exoplayer.MediaCodecTrackRenderer_");
            stringBuilder.append(str);
            stringBuilder.append(Math.abs(i));
            return stringBuilder.toString();
        }
    }

    private boolean drainOutputBuffer(long r1, long r3) throws com.google.android.exoplayer2.ExoPlaybackException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.drainOutputBuffer(long, long):boolean
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
*/
        /*
        r0 = this;
        r13 = r16;
        r0 = r16.hasOutputBuffer();
        r14 = 1;
        r15 = 0;
        if (r0 != 0) goto L_0x00aa;
        r0 = r13.codecNeedsEosOutputExceptionWorkaround;
        if (r0 == 0) goto L_0x002b;
        r0 = r13.codecReceivedEos;
        if (r0 == 0) goto L_0x002b;
        r0 = r13.codec;
        r1 = r13.outputBufferInfo;
        r2 = r16.getDequeueOutputBufferTimeoutUs();
        r0 = r0.dequeueOutputBuffer(r1, r2);
        goto L_0x0037;
        r16.processEndOfStream();
        r0 = r13.outputStreamEnded;
        if (r0 == 0) goto L_0x002a;
        r16.releaseCodec();
        return r15;
        r0 = r13.codec;
        r1 = r13.outputBufferInfo;
        r2 = r16.getDequeueOutputBufferTimeoutUs();
        r0 = r0.dequeueOutputBuffer(r1, r2);
        if (r0 >= 0) goto L_0x0058;
        r1 = -2;
        if (r0 != r1) goto L_0x0040;
        r16.processOutputFormat();
        return r14;
        r1 = -3;
        if (r0 != r1) goto L_0x0047;
        r16.processOutputBuffersChanged();
        return r14;
        r0 = r13.codecNeedsEosPropagation;
        if (r0 == 0) goto L_0x0057;
        r0 = r13.inputStreamEnded;
        if (r0 != 0) goto L_0x0054;
        r0 = r13.codecReinitializationState;
        r1 = 2;
        if (r0 != r1) goto L_0x0057;
        r16.processEndOfStream();
        return r15;
        r1 = r13.shouldSkipAdaptationWorkaroundOutputBuffer;
        if (r1 == 0) goto L_0x0064;
        r13.shouldSkipAdaptationWorkaroundOutputBuffer = r15;
        r1 = r13.codec;
        r1.releaseOutputBuffer(r0, r15);
        return r14;
        r1 = r13.outputBufferInfo;
        r1 = r1.size;
        if (r1 != 0) goto L_0x0076;
        r1 = r13.outputBufferInfo;
        r1 = r1.flags;
        r1 = r1 & 4;
        if (r1 == 0) goto L_0x0076;
        r16.processEndOfStream();
        return r15;
        r13.outputIndex = r0;
        r0 = r13.getOutputBuffer(r0);
        r13.outputBuffer = r0;
        r0 = r13.outputBuffer;
        if (r0 == 0) goto L_0x0099;
        r0 = r13.outputBuffer;
        r1 = r13.outputBufferInfo;
        r1 = r1.offset;
        r0.position(r1);
        r0 = r13.outputBuffer;
        r1 = r13.outputBufferInfo;
        r1 = r1.offset;
        r2 = r13.outputBufferInfo;
        r2 = r2.size;
        r1 = r1 + r2;
        r0.limit(r1);
        r0 = r13.outputBufferInfo;
        r0 = r0.presentationTimeUs;
        r0 = r13.shouldSkipOutputBuffer(r0);
        r13.shouldSkipOutputBuffer = r0;
        r0 = r13.outputBufferInfo;
        r0 = r0.presentationTimeUs;
        r13.updateOutputFormatForTime(r0);
        r0 = r13.codecNeedsEosOutputExceptionWorkaround;
        if (r0 == 0) goto L_0x00db;
        r0 = r13.codecReceivedEos;
        if (r0 == 0) goto L_0x00db;
        r5 = r13.codec;
        r6 = r13.outputBuffer;
        r7 = r13.outputIndex;
        r0 = r13.outputBufferInfo;
        r8 = r0.flags;
        r0 = r13.outputBufferInfo;
        r9 = r0.presentationTimeUs;
        r11 = r13.shouldSkipOutputBuffer;
        r12 = r13.outputFormat;
        r0 = r16;
        r1 = r17;
        r3 = r19;
        r0 = r0.processOutputBuffer(r1, r3, r5, r6, r7, r8, r9, r11, r12);
        goto L_0x00f7;
        r16.processEndOfStream();
        r0 = r13.outputStreamEnded;
        if (r0 == 0) goto L_0x00da;
        r16.releaseCodec();
        return r15;
        r5 = r13.codec;
        r6 = r13.outputBuffer;
        r7 = r13.outputIndex;
        r0 = r13.outputBufferInfo;
        r8 = r0.flags;
        r0 = r13.outputBufferInfo;
        r9 = r0.presentationTimeUs;
        r11 = r13.shouldSkipOutputBuffer;
        r12 = r13.outputFormat;
        r0 = r16;
        r1 = r17;
        r3 = r19;
        r0 = r0.processOutputBuffer(r1, r3, r5, r6, r7, r8, r9, r11, r12);
        if (r0 == 0) goto L_0x0114;
        r0 = r13.outputBufferInfo;
        r0 = r0.presentationTimeUs;
        r13.onProcessedOutputBuffer(r0);
        r0 = r13.outputBufferInfo;
        r0 = r0.flags;
        r0 = r0 & 4;
        if (r0 == 0) goto L_0x010a;
        r0 = 1;
        goto L_0x010b;
        r0 = 0;
        r16.resetOutputBuffer();
        if (r0 != 0) goto L_0x0111;
        return r14;
        r16.processEndOfStream();
        return r15;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.drainOutputBuffer(long, long):boolean");
    }

    protected int canKeepCodec(MediaCodec mediaCodec, MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        return 0;
    }

    protected abstract void configureCodec(MediaCodecInfo mediaCodecInfo, MediaCodec mediaCodec, Format format, MediaCrypto mediaCrypto, float f) throws DecoderQueryException;

    protected boolean getCodecNeedsEosPropagation() {
        return false;
    }

    protected float getCodecOperatingRate(float f, Format format, Format[] formatArr) {
        return CODEC_OPERATING_RATE_UNSET;
    }

    protected long getDequeueOutputBufferTimeoutUs() {
        return 0;
    }

    protected void onCodecInitialized(String str, long j, long j2) {
    }

    protected void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) throws ExoPlaybackException {
    }

    protected void onProcessedOutputBuffer(long j) {
    }

    protected void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) {
    }

    protected void onStarted() {
    }

    protected void onStopped() {
    }

    protected abstract boolean processOutputBuffer(long j, long j2, MediaCodec mediaCodec, ByteBuffer byteBuffer, int i, int i2, long j3, boolean z, Format format) throws ExoPlaybackException;

    protected void renderToEndOfStream() throws ExoPlaybackException {
    }

    protected boolean shouldInitCodec(MediaCodecInfo mediaCodecInfo) {
        return true;
    }

    protected abstract int supportsFormat(MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, Format format) throws DecoderQueryException;

    public final int supportsMixedMimeTypeAdaptation() {
        return 8;
    }

    public MediaCodecRenderer(int i, MediaCodecSelector mediaCodecSelector, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, float f) {
        super(i);
        Assertions.checkState(Util.SDK_INT >= 16 ? 1 : 0);
        this.mediaCodecSelector = (MediaCodecSelector) Assertions.checkNotNull(mediaCodecSelector);
        this.drmSessionManager = drmSessionManager;
        this.playClearSamplesWithoutKeys = z;
        this.assumedMinimumCodecOperatingRate = f;
        this.buffer = new DecoderInputBuffer(0);
        this.flagsOnlyBuffer = DecoderInputBuffer.newFlagsOnlyInstance();
        this.formatHolder = new FormatHolder();
        this.formatQueue = new TimedValueQueue();
        this.decodeOnlyPresentationTimestamps = new ArrayList();
        this.outputBufferInfo = new BufferInfo();
        this.codecReconfigurationState = 0;
        this.codecReinitializationState = 0;
        this.codecOperatingRate = -1082130432;
        this.rendererOperatingRate = 1065353216;
    }

    public final int supportsFormat(Format format) throws ExoPlaybackException {
        try {
            return supportsFormat(this.mediaCodecSelector, this.drmSessionManager, format);
        } catch (Format format2) {
            throw ExoPlaybackException.createForRenderer(format2, getIndex());
        }
    }

    protected List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z) throws DecoderQueryException {
        return mediaCodecSelector.getDecoderInfos(format.sampleMimeType, z);
    }

    protected final void maybeInitCodec() throws ExoPlaybackException {
        if (this.codec == null) {
            if (this.format != null) {
                boolean requiresSecureDecoderComponent;
                this.drmSession = this.pendingDrmSession;
                String str = this.format.sampleMimeType;
                MediaCrypto mediaCrypto = null;
                boolean z = false;
                if (this.drmSession != null) {
                    FrameworkMediaCrypto frameworkMediaCrypto = (FrameworkMediaCrypto) this.drmSession.getMediaCrypto();
                    if (frameworkMediaCrypto != null) {
                        mediaCrypto = frameworkMediaCrypto.getWrappedMediaCrypto();
                        requiresSecureDecoderComponent = frameworkMediaCrypto.requiresSecureDecoderComponent(str);
                    } else if (this.drmSession.getError() != null) {
                        requiresSecureDecoderComponent = false;
                    } else {
                        return;
                    }
                    if (deviceNeedsDrmKeysToConfigureCodecWorkaround()) {
                        int state = this.drmSession.getState();
                        if (state == 1) {
                            throw ExoPlaybackException.createForRenderer(this.drmSession.getError(), getIndex());
                        } else if (state != 4) {
                            return;
                        }
                    }
                }
                requiresSecureDecoderComponent = false;
                try {
                    if (initCodecWithFallback(mediaCrypto, requiresSecureDecoderComponent)) {
                        str = this.codecInfo.name;
                        this.codecAdaptationWorkaroundMode = codecAdaptationWorkaroundMode(str);
                        this.codecNeedsReconfigureWorkaround = codecNeedsReconfigureWorkaround(str);
                        this.codecNeedsDiscardToSpsWorkaround = codecNeedsDiscardToSpsWorkaround(str, this.format);
                        this.codecNeedsFlushWorkaround = codecNeedsFlushWorkaround(str);
                        this.codecNeedsEosFlushWorkaround = codecNeedsEosFlushWorkaround(str);
                        this.codecNeedsEosOutputExceptionWorkaround = codecNeedsEosOutputExceptionWorkaround(str);
                        this.codecNeedsMonoChannelCountWorkaround = codecNeedsMonoChannelCountWorkaround(str, this.format);
                        if (codecNeedsEosPropagationWorkaround(this.codecInfo) || getCodecNeedsEosPropagation()) {
                            z = true;
                        }
                        this.codecNeedsEosPropagation = z;
                        this.codecHotswapDeadlineMs = getState() == 2 ? SystemClock.elapsedRealtime() + 1000 : C.TIME_UNSET;
                        resetInputBuffer();
                        resetOutputBuffer();
                        this.waitingForFirstSyncFrame = true;
                        DecoderCounters decoderCounters = this.decoderCounters;
                        decoderCounters.decoderInitCount++;
                    }
                } catch (Exception e) {
                    throw ExoPlaybackException.createForRenderer(e, getIndex());
                }
            }
        }
    }

    @Nullable
    protected final Format updateOutputFormatForTime(long j) {
        Format format = (Format) this.formatQueue.pollFloor(j);
        if (format != null) {
            this.outputFormat = format;
        }
        return format;
    }

    protected final MediaCodec getCodec() {
        return this.codec;
    }

    @Nullable
    protected final MediaCodecInfo getCodecInfo() {
        return this.codecInfo;
    }

    protected void onEnabled(boolean z) throws ExoPlaybackException {
        this.decoderCounters = new DecoderCounters();
    }

    protected void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        if (this.codec != null) {
            flushCodec();
        }
        this.formatQueue.clear();
    }

    public final void setOperatingRate(float f) throws ExoPlaybackException {
        this.rendererOperatingRate = f;
        updateCodecOperatingRate();
    }

    protected void onDisabled() {
        this.format = null;
        this.availableCodecInfos = null;
        try {
            releaseCodec();
            try {
                if (this.drmSession != null) {
                    this.drmSessionManager.releaseSession(this.drmSession);
                }
                try {
                    if (!(this.pendingDrmSession == null || this.pendingDrmSession == this.drmSession)) {
                        this.drmSessionManager.releaseSession(this.pendingDrmSession);
                    }
                    this.drmSession = null;
                    this.pendingDrmSession = null;
                } catch (Throwable th) {
                    this.drmSession = null;
                    this.pendingDrmSession = null;
                }
            } catch (Throwable th2) {
                this.drmSession = null;
                this.pendingDrmSession = null;
            }
        } catch (Throwable th3) {
            this.drmSession = null;
            this.pendingDrmSession = null;
        }
    }

    protected void releaseCodec() {
        this.codecHotswapDeadlineMs = C.TIME_UNSET;
        resetInputBuffer();
        resetOutputBuffer();
        this.waitingForKeys = false;
        this.shouldSkipOutputBuffer = false;
        this.decodeOnlyPresentationTimestamps.clear();
        resetCodecBuffers();
        this.codecInfo = null;
        this.codecReconfigured = false;
        this.codecReceivedBuffers = false;
        this.codecNeedsDiscardToSpsWorkaround = false;
        this.codecNeedsFlushWorkaround = false;
        this.codecAdaptationWorkaroundMode = 0;
        this.codecNeedsReconfigureWorkaround = false;
        this.codecNeedsEosFlushWorkaround = false;
        this.codecNeedsMonoChannelCountWorkaround = false;
        this.codecNeedsAdaptationWorkaroundBuffer = false;
        this.shouldSkipAdaptationWorkaroundOutputBuffer = false;
        this.codecNeedsEosPropagation = false;
        this.codecReceivedEos = false;
        this.codecReconfigurationState = 0;
        this.codecReinitializationState = 0;
        this.codecConfiguredWithOperatingRate = false;
        if (this.codec != null) {
            DecoderCounters decoderCounters = this.decoderCounters;
            decoderCounters.decoderReleaseCount++;
            try {
                this.codec.stop();
                try {
                    this.codec.release();
                    this.codec = null;
                    if (this.drmSession != null && this.pendingDrmSession != this.drmSession) {
                        try {
                            this.drmSessionManager.releaseSession(this.drmSession);
                        } finally {
                            this.drmSession = null;
                        }
                    }
                } catch (Throwable th) {
                    this.codec = null;
                    if (!(this.drmSession == null || this.pendingDrmSession == this.drmSession)) {
                        this.drmSessionManager.releaseSession(this.drmSession);
                    }
                } finally {
                    this.drmSession = null;
                }
            } catch (Throwable th2) {
                this.codec = null;
                if (!(this.drmSession == null || this.pendingDrmSession == this.drmSession)) {
                    try {
                        this.drmSessionManager.releaseSession(this.drmSession);
                    } finally {
                        this.drmSession = null;
                    }
                }
            } finally {
                this.drmSession = null;
            }
        }
    }

    public void render(long j, long j2) throws ExoPlaybackException {
        if (this.outputStreamEnded) {
            renderToEndOfStream();
            return;
        }
        if (this.format == null) {
            this.flagsOnlyBuffer.clear();
            int readSource = readSource(this.formatHolder, this.flagsOnlyBuffer, true);
            if (readSource == -5) {
                onInputFormatChanged(this.formatHolder.format);
            } else if (readSource == -4) {
                Assertions.checkState(this.flagsOnlyBuffer.isEndOfStream());
                this.inputStreamEnded = true;
                processEndOfStream();
                return;
            } else {
                return;
            }
        }
        maybeInitCodec();
        if (this.codec != null) {
            TraceUtil.beginSection("drainAndFeed");
            while (drainOutputBuffer(j, j2)) {
            }
            while (feedInputBuffer() != null) {
            }
            TraceUtil.endSection();
        } else {
            j2 = this.decoderCounters;
            j2.skippedInputBufferCount += skipSource(j);
            this.flagsOnlyBuffer.clear();
            j = readSource(this.formatHolder, this.flagsOnlyBuffer, 0);
            if (j == -5) {
                onInputFormatChanged(this.formatHolder.format);
            } else if (j == -4) {
                Assertions.checkState(this.flagsOnlyBuffer.isEndOfStream());
                this.inputStreamEnded = true;
                processEndOfStream();
            }
        }
        this.decoderCounters.ensureUpdated();
    }

    protected void flushCodec() throws ExoPlaybackException {
        this.codecHotswapDeadlineMs = C.TIME_UNSET;
        resetInputBuffer();
        resetOutputBuffer();
        this.waitingForFirstSyncFrame = true;
        this.waitingForKeys = false;
        this.shouldSkipOutputBuffer = false;
        this.decodeOnlyPresentationTimestamps.clear();
        this.codecNeedsAdaptationWorkaroundBuffer = false;
        this.shouldSkipAdaptationWorkaroundOutputBuffer = false;
        if (!this.codecNeedsFlushWorkaround) {
            if (!this.codecNeedsEosFlushWorkaround || !this.codecReceivedEos) {
                if (this.codecReinitializationState != 0) {
                    releaseCodec();
                    maybeInitCodec();
                } else {
                    this.codec.flush();
                    this.codecReceivedBuffers = false;
                }
                if (this.codecReconfigured && this.format != null) {
                    this.codecReconfigurationState = 1;
                    return;
                }
            }
        }
        releaseCodec();
        maybeInitCodec();
        if (this.codecReconfigured) {
        }
    }

    private boolean initCodecWithFallback(MediaCrypto mediaCrypto, boolean z) throws DecoderInitializationException {
        if (this.availableCodecInfos == null) {
            try {
                this.availableCodecInfos = new ArrayDeque(getAvailableCodecInfos(z));
                this.preferredDecoderInitializationException = null;
            } catch (Throwable e) {
                throw new DecoderInitializationException(this.format, e, z, -49998);
            }
        }
        if (this.availableCodecInfos.isEmpty()) {
            throw new DecoderInitializationException(this.format, null, z, -49999);
        }
        while (true) {
            MediaCodecInfo mediaCodecInfo = (MediaCodecInfo) this.availableCodecInfos.peekFirst();
            if (!shouldInitCodec(mediaCodecInfo)) {
                return null;
            }
            try {
                initCodec(mediaCodecInfo, mediaCrypto);
                break;
            } catch (Throwable e2) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to initialize decoder: ");
                stringBuilder.append(mediaCodecInfo);
                Log.w(str, stringBuilder.toString(), e2);
                this.availableCodecInfos.removeFirst();
                DecoderInitializationException decoderInitializationException = new DecoderInitializationException(this.format, e2, z, mediaCodecInfo.name);
                if (this.preferredDecoderInitializationException == null) {
                    this.preferredDecoderInitializationException = decoderInitializationException;
                } else {
                    this.preferredDecoderInitializationException = this.preferredDecoderInitializationException.copyWithFallbackException(decoderInitializationException);
                }
                if (this.availableCodecInfos.isEmpty()) {
                    throw this.preferredDecoderInitializationException;
                }
            }
        }
        return true;
    }

    private List<MediaCodecInfo> getAvailableCodecInfos(boolean z) throws DecoderQueryException {
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(this.mediaCodecSelector, this.format, z);
        if (decoderInfos.isEmpty() && z) {
            decoderInfos = getDecoderInfos(this.mediaCodecSelector, this.format, false);
            if (!decoderInfos.isEmpty()) {
                z = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Drm session requires secure decoder for ");
                stringBuilder.append(this.format.sampleMimeType);
                stringBuilder.append(", but no secure decoder available. Trying to proceed with ");
                stringBuilder.append(decoderInfos);
                stringBuilder.append(".");
                Log.w(z, stringBuilder.toString());
            }
        }
        return decoderInfos;
    }

    private void initCodec(MediaCodecInfo mediaCodecInfo, MediaCrypto mediaCrypto) throws Exception {
        String str = mediaCodecInfo.name;
        updateCodecOperatingRate();
        boolean z = this.codecOperatingRate > this.assumedMinimumCodecOperatingRate;
        MediaCodec createByCodecName;
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("createCodec:");
            stringBuilder.append(str);
            TraceUtil.beginSection(stringBuilder.toString());
            createByCodecName = MediaCodec.createByCodecName(str);
            try {
                TraceUtil.endSection();
                TraceUtil.beginSection("configureCodec");
                configureCodec(mediaCodecInfo, createByCodecName, this.format, mediaCrypto, z ? this.codecOperatingRate : CODEC_OPERATING_RATE_UNSET);
                this.codecConfiguredWithOperatingRate = z;
                TraceUtil.endSection();
                TraceUtil.beginSection("startCodec");
                createByCodecName.start();
                TraceUtil.endSection();
                long elapsedRealtime2 = SystemClock.elapsedRealtime();
                getCodecBuffers(createByCodecName);
                this.codec = createByCodecName;
                this.codecInfo = mediaCodecInfo;
                onCodecInitialized(str, elapsedRealtime2, elapsedRealtime2 - elapsedRealtime);
            } catch (Exception e) {
                mediaCodecInfo = e;
                if (createByCodecName != null) {
                    resetCodecBuffers();
                    createByCodecName.release();
                }
                throw mediaCodecInfo;
            }
        } catch (Exception e2) {
            mediaCodecInfo = e2;
            createByCodecName = null;
            if (createByCodecName != null) {
                resetCodecBuffers();
                createByCodecName.release();
            }
            throw mediaCodecInfo;
        }
    }

    private void getCodecBuffers(MediaCodec mediaCodec) {
        if (Util.SDK_INT < 21) {
            this.inputBuffers = mediaCodec.getInputBuffers();
            this.outputBuffers = mediaCodec.getOutputBuffers();
        }
    }

    private void resetCodecBuffers() {
        if (Util.SDK_INT < 21) {
            this.inputBuffers = null;
            this.outputBuffers = null;
        }
    }

    private ByteBuffer getInputBuffer(int i) {
        if (Util.SDK_INT >= 21) {
            return this.codec.getInputBuffer(i);
        }
        return this.inputBuffers[i];
    }

    private ByteBuffer getOutputBuffer(int i) {
        if (Util.SDK_INT >= 21) {
            return this.codec.getOutputBuffer(i);
        }
        return this.outputBuffers[i];
    }

    private boolean hasOutputBuffer() {
        return this.outputIndex >= 0;
    }

    private void resetInputBuffer() {
        this.inputIndex = -1;
        this.buffer.data = null;
    }

    private void resetOutputBuffer() {
        this.outputIndex = -1;
        this.outputBuffer = null;
    }

    private boolean feedInputBuffer() throws ExoPlaybackException {
        if (!(this.codec == null || this.codecReinitializationState == 2)) {
            if (!this.inputStreamEnded) {
                if (this.inputIndex < 0) {
                    this.inputIndex = this.codec.dequeueInputBuffer(0);
                    if (this.inputIndex < 0) {
                        return false;
                    }
                    this.buffer.data = getInputBuffer(this.inputIndex);
                    this.buffer.clear();
                }
                if (this.codecReinitializationState == 1) {
                    if (!this.codecNeedsEosPropagation) {
                        this.codecReceivedEos = true;
                        this.codec.queueInputBuffer(this.inputIndex, 0, 0, 0, 4);
                        resetInputBuffer();
                    }
                    this.codecReinitializationState = 2;
                    return false;
                } else if (this.codecNeedsAdaptationWorkaroundBuffer) {
                    this.codecNeedsAdaptationWorkaroundBuffer = false;
                    this.buffer.data.put(ADAPTATION_WORKAROUND_BUFFER);
                    this.codec.queueInputBuffer(this.inputIndex, 0, ADAPTATION_WORKAROUND_BUFFER.length, 0, 0);
                    resetInputBuffer();
                    this.codecReceivedBuffers = true;
                    return true;
                } else {
                    int i;
                    int i2;
                    if (this.waitingForKeys) {
                        i = -4;
                        i2 = 0;
                    } else {
                        if (this.codecReconfigurationState == 1) {
                            for (i = 0; i < this.format.initializationData.size(); i++) {
                                this.buffer.data.put((byte[]) this.format.initializationData.get(i));
                            }
                            this.codecReconfigurationState = 2;
                        }
                        i = this.buffer.data.position();
                        i2 = i;
                        i = readSource(this.formatHolder, this.buffer, false);
                    }
                    if (i == -3) {
                        return false;
                    }
                    if (i == -5) {
                        if (this.codecReconfigurationState == 2) {
                            this.buffer.clear();
                            this.codecReconfigurationState = 1;
                        }
                        onInputFormatChanged(this.formatHolder.format);
                        return true;
                    } else if (this.buffer.isEndOfStream()) {
                        if (this.codecReconfigurationState == 2) {
                            this.buffer.clear();
                            this.codecReconfigurationState = 1;
                        }
                        this.inputStreamEnded = true;
                        if (this.codecReceivedBuffers) {
                            try {
                                if (!this.codecNeedsEosPropagation) {
                                    this.codecReceivedEos = true;
                                    this.codec.queueInputBuffer(this.inputIndex, 0, 0, 0, 4);
                                    resetInputBuffer();
                                }
                                return false;
                            } catch (Exception e) {
                                throw ExoPlaybackException.createForRenderer(e, getIndex());
                            }
                        }
                        processEndOfStream();
                        return false;
                    } else if (!this.waitingForFirstSyncFrame || this.buffer.isKeyFrame()) {
                        this.waitingForFirstSyncFrame = false;
                        boolean isEncrypted = this.buffer.isEncrypted();
                        this.waitingForKeys = shouldWaitForKeys(isEncrypted);
                        if (this.waitingForKeys) {
                            return false;
                        }
                        if (this.codecNeedsDiscardToSpsWorkaround && !isEncrypted) {
                            NalUnitUtil.discardToSps(this.buffer.data);
                            if (this.buffer.data.position() == 0) {
                                return true;
                            }
                            this.codecNeedsDiscardToSpsWorkaround = false;
                        }
                        try {
                            long j = this.buffer.timeUs;
                            if (this.buffer.isDecodeOnly()) {
                                this.decodeOnlyPresentationTimestamps.add(Long.valueOf(j));
                            }
                            if (this.pendingFormat != null) {
                                this.formatQueue.add(j, this.pendingFormat);
                                this.pendingFormat = null;
                            }
                            this.buffer.flip();
                            onQueueInputBuffer(this.buffer);
                            if (isEncrypted) {
                                this.codec.queueSecureInputBuffer(this.inputIndex, 0, getFrameworkCryptoInfo(this.buffer, i2), j, 0);
                            } else {
                                this.codec.queueInputBuffer(this.inputIndex, 0, this.buffer.data.limit(), j, 0);
                            }
                            resetInputBuffer();
                            this.codecReceivedBuffers = true;
                            this.codecReconfigurationState = 0;
                            DecoderCounters decoderCounters = this.decoderCounters;
                            decoderCounters.inputBufferCount++;
                            return true;
                        } catch (Exception e2) {
                            throw ExoPlaybackException.createForRenderer(e2, getIndex());
                        }
                    } else {
                        this.buffer.clear();
                        if (this.codecReconfigurationState == 2) {
                            this.codecReconfigurationState = 1;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean shouldWaitForKeys(boolean z) throws ExoPlaybackException {
        if (this.drmSession != null) {
            if (z || !this.playClearSamplesWithoutKeys) {
                z = this.drmSession.getState();
                boolean z2 = true;
                if (!z) {
                    if (z) {
                        z2 = false;
                    }
                    return z2;
                }
                throw ExoPlaybackException.createForRenderer(this.drmSession.getError(), getIndex());
            }
        }
        return false;
    }

    protected void onInputFormatChanged(Format format) throws ExoPlaybackException {
        Format format2 = this.format;
        this.format = format;
        this.pendingFormat = format;
        boolean z = true;
        if ((Util.areEqual(this.format.drmInitData, format2 == null ? null : format2.drmInitData) ^ 1) != null) {
            if (this.format.drmInitData == null) {
                this.pendingDrmSession = null;
            } else if (this.drmSessionManager != null) {
                this.pendingDrmSession = this.drmSessionManager.acquireSession(Looper.myLooper(), this.format.drmInitData);
                if (this.pendingDrmSession == this.drmSession) {
                    this.drmSessionManager.releaseSession(this.pendingDrmSession);
                }
            } else {
                throw ExoPlaybackException.createForRenderer(new IllegalStateException("Media requires a DrmSessionManager"), getIndex());
            }
        }
        boolean z2 = false;
        if (this.pendingDrmSession == this.drmSession && this.codec != null) {
            format = canKeepCodec(this.codec, this.codecInfo, format2, this.format);
            if (format != 3) {
                switch (format) {
                    case null:
                        break;
                    case 1:
                        break;
                    default:
                        throw new IllegalStateException();
                }
            } else if (this.codecNeedsReconfigureWorkaround == null) {
                this.codecReconfigured = true;
                this.codecReconfigurationState = 1;
                if (this.codecAdaptationWorkaroundMode == 2 || (this.codecAdaptationWorkaroundMode == 1 && this.format.width == format2.width && this.format.height == format2.height)) {
                    z2 = true;
                }
                this.codecNeedsAdaptationWorkaroundBuffer = z2;
                if (z) {
                    reinitializeCodec();
                } else {
                    updateCodecOperatingRate();
                }
            }
        }
        z = false;
        if (z) {
            updateCodecOperatingRate();
        } else {
            reinitializeCodec();
        }
    }

    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    public boolean isReady() {
        return (this.format == null || this.waitingForKeys || (!isSourceReady() && !hasOutputBuffer() && (this.codecHotswapDeadlineMs == C.TIME_UNSET || SystemClock.elapsedRealtime() >= this.codecHotswapDeadlineMs))) ? false : true;
    }

    private void updateCodecOperatingRate() throws ExoPlaybackException {
        if (this.format != null) {
            if (Util.SDK_INT >= 23) {
                float codecOperatingRate = getCodecOperatingRate(this.rendererOperatingRate, this.format, getStreamFormats());
                if (this.codecOperatingRate != codecOperatingRate) {
                    this.codecOperatingRate = codecOperatingRate;
                    if (this.codec != null) {
                        if (this.codecReinitializationState == 0) {
                            if (codecOperatingRate == CODEC_OPERATING_RATE_UNSET && this.codecConfiguredWithOperatingRate) {
                                reinitializeCodec();
                            } else if (codecOperatingRate != CODEC_OPERATING_RATE_UNSET && (this.codecConfiguredWithOperatingRate || codecOperatingRate > this.assumedMinimumCodecOperatingRate)) {
                                Bundle bundle = new Bundle();
                                bundle.putFloat("operating-rate", codecOperatingRate);
                                this.codec.setParameters(bundle);
                                this.codecConfiguredWithOperatingRate = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void reinitializeCodec() throws ExoPlaybackException {
        this.availableCodecInfos = null;
        if (this.codecReceivedBuffers) {
            this.codecReinitializationState = 1;
            return;
        }
        releaseCodec();
        maybeInitCodec();
    }

    private void processOutputFormat() throws ExoPlaybackException {
        MediaFormat outputFormat = this.codec.getOutputFormat();
        if (this.codecAdaptationWorkaroundMode != 0 && outputFormat.getInteger("width") == 32 && outputFormat.getInteger("height") == 32) {
            this.shouldSkipAdaptationWorkaroundOutputBuffer = true;
            return;
        }
        if (this.codecNeedsMonoChannelCountWorkaround) {
            outputFormat.setInteger("channel-count", 1);
        }
        onOutputFormatChanged(this.codec, outputFormat);
    }

    private void processOutputBuffersChanged() {
        if (Util.SDK_INT < 21) {
            this.outputBuffers = this.codec.getOutputBuffers();
        }
    }

    private void processEndOfStream() throws ExoPlaybackException {
        if (this.codecReinitializationState == 2) {
            releaseCodec();
            maybeInitCodec();
            return;
        }
        this.outputStreamEnded = true;
        renderToEndOfStream();
    }

    private boolean shouldSkipOutputBuffer(long j) {
        int size = this.decodeOnlyPresentationTimestamps.size();
        for (int i = 0; i < size; i++) {
            if (((Long) this.decodeOnlyPresentationTimestamps.get(i)).longValue() == j) {
                this.decodeOnlyPresentationTimestamps.remove(i);
                return 1;
            }
        }
        return false;
    }

    private static CryptoInfo getFrameworkCryptoInfo(DecoderInputBuffer decoderInputBuffer, int i) {
        decoderInputBuffer = decoderInputBuffer.cryptoInfo.getFrameworkCryptoInfoV16();
        if (i == 0) {
            return decoderInputBuffer;
        }
        if (decoderInputBuffer.numBytesOfClearData == null) {
            decoderInputBuffer.numBytesOfClearData = new int[1];
        }
        int[] iArr = decoderInputBuffer.numBytesOfClearData;
        iArr[0] = iArr[0] + i;
        return decoderInputBuffer;
    }

    private boolean deviceNeedsDrmKeysToConfigureCodecWorkaround() {
        return "Amazon".equals(Util.MANUFACTURER) && ("AFTM".equals(Util.MODEL) || "AFTB".equals(Util.MODEL));
    }

    private static boolean codecNeedsFlushWorkaround(String str) {
        if (Util.SDK_INT >= 18 && !(Util.SDK_INT == 18 && ("OMX.SEC.avc.dec".equals(str) || "OMX.SEC.avc.dec.secure".equals(str)))) {
            if (Util.SDK_INT == 19 && Util.MODEL.startsWith("SM-G800")) {
                if (!"OMX.Exynos.avc.dec".equals(str)) {
                    if ("OMX.Exynos.avc.dec.secure".equals(str) != null) {
                    }
                }
            }
            return null;
        }
        return true;
    }

    private int codecAdaptationWorkaroundMode(String str) {
        if (Util.SDK_INT <= 25 && "OMX.Exynos.avc.dec.secure".equals(str) && (Util.MODEL.startsWith("SM-T585") || Util.MODEL.startsWith("SM-A510") || Util.MODEL.startsWith("SM-A520") || Util.MODEL.startsWith("SM-J700"))) {
            return 2;
        }
        return (Util.SDK_INT >= 24 || ((!"OMX.Nvidia.h264.decode".equals(str) && "OMX.Nvidia.h264.decode.secure".equals(str) == null) || ("flounder".equals(Util.DEVICE) == null && "flounder_lte".equals(Util.DEVICE) == null && "grouper".equals(Util.DEVICE) == null && "tilapia".equals(Util.DEVICE) == null))) ? null : 1;
    }

    private static boolean codecNeedsReconfigureWorkaround(String str) {
        return (!Util.MODEL.startsWith("SM-T230") || "OMX.MARVELL.VIDEO.HW.CODA7542DECODER".equals(str) == null) ? null : true;
    }

    private static boolean codecNeedsDiscardToSpsWorkaround(String str, Format format) {
        return (Util.SDK_INT >= 21 || format.initializationData.isEmpty() == null || "OMX.MTK.VIDEO.DECODER.AVC".equals(str) == null) ? null : true;
    }

    private static boolean codecNeedsEosPropagationWorkaround(MediaCodecInfo mediaCodecInfo) {
        String str = mediaCodecInfo.name;
        return ((Util.SDK_INT > 17 || !("OMX.rk.video_decoder.avc".equals(str) || "OMX.allwinner.video.decoder.avc".equals(str))) && !("Amazon".equals(Util.MANUFACTURER) && "AFTS".equals(Util.MODEL) && mediaCodecInfo.secure != null)) ? null : true;
    }

    private static boolean codecNeedsEosFlushWorkaround(String str) {
        return ((Util.SDK_INT > 23 || !"OMX.google.vorbis.decoder".equals(str)) && (Util.SDK_INT > 19 || !"hb2000".equals(Util.DEVICE) || (!"OMX.amlogic.avc.decoder.awesome".equals(str) && "OMX.amlogic.avc.decoder.awesome.secure".equals(str) == null))) ? null : true;
    }

    private static boolean codecNeedsEosOutputExceptionWorkaround(String str) {
        return (Util.SDK_INT != 21 || "OMX.google.aac.decoder".equals(str) == null) ? null : true;
    }

    private static boolean codecNeedsMonoChannelCountWorkaround(String str, Format format) {
        if (Util.SDK_INT > 18 || format.channelCount != 1 || "OMX.MTK.AUDIO.DECODER.MP3".equals(str) == null) {
            return false;
        }
        return true;
    }
}
