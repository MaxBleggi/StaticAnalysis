package com.google.android.exoplayer2.video;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaCodec;
import android.media.MediaCodec.OnFrameRenderedListener;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Surface;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.mediacodec.MediaFormatUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener.EventDispatcher;
import java.util.List;

@TargetApi(16)
public class MediaCodecVideoRenderer extends MediaCodecRenderer {
    private static final float INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR = 1.5f;
    private static final String KEY_CROP_BOTTOM = "crop-bottom";
    private static final String KEY_CROP_LEFT = "crop-left";
    private static final String KEY_CROP_RIGHT = "crop-right";
    private static final String KEY_CROP_TOP = "crop-top";
    private static final int MAX_PENDING_OUTPUT_STREAM_OFFSET_COUNT = 10;
    private static final int[] STANDARD_LONG_EDGE_VIDEO_PX = new int[]{1920, 1600, 1440, 1280, 960, 854, 640, 540, 480};
    private static final String TAG = "MediaCodecVideoRenderer";
    private static boolean deviceNeedsSetOutputSurfaceWorkaround;
    private static boolean evaluatedDeviceNeedsSetOutputSurfaceWorkaround;
    private final long allowedJoiningTimeMs;
    private int buffersInCodecCount;
    private CodecMaxValues codecMaxValues;
    private boolean codecNeedsSetOutputSurfaceWorkaround;
    private int consecutiveDroppedFrameCount;
    private final Context context;
    private int currentHeight;
    private float currentPixelWidthHeightRatio;
    private int currentUnappliedRotationDegrees;
    private int currentWidth;
    private final boolean deviceNeedsAutoFrcWorkaround;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;
    private Surface dummySurface;
    private final EventDispatcher eventDispatcher;
    @Nullable
    private VideoFrameMetadataListener frameMetadataListener;
    private final VideoFrameReleaseTimeHelper frameReleaseTimeHelper;
    private long initialPositionUs;
    private long joiningDeadlineMs;
    private long lastInputTimeUs;
    private long lastRenderTimeUs;
    private final int maxDroppedFramesToNotify;
    private long outputStreamOffsetUs;
    private int pendingOutputStreamOffsetCount;
    private final long[] pendingOutputStreamOffsetsUs;
    private final long[] pendingOutputStreamSwitchTimesUs;
    private float pendingPixelWidthHeightRatio;
    private int pendingRotationDegrees;
    private boolean renderedFirstFrame;
    private int reportedHeight;
    private float reportedPixelWidthHeightRatio;
    private int reportedUnappliedRotationDegrees;
    private int reportedWidth;
    private int scalingMode;
    private Surface surface;
    private boolean tunneling;
    private int tunnelingAudioSessionId;
    OnFrameRenderedListenerV23 tunnelingOnFrameRenderedListener;

    protected static final class CodecMaxValues {
        public final int height;
        public final int inputSize;
        public final int width;

        public CodecMaxValues(int i, int i2, int i3) {
            this.width = i;
            this.height = i2;
            this.inputSize = i3;
        }
    }

    @TargetApi(23)
    private final class OnFrameRenderedListenerV23 implements OnFrameRenderedListener {
        private OnFrameRenderedListenerV23(MediaCodec mediaCodec) {
            mediaCodec.setOnFrameRenderedListener(this, new Handler());
        }

        public void onFrameRendered(@NonNull MediaCodec mediaCodec, long j, long j2) {
            if (this == MediaCodecVideoRenderer.this.tunnelingOnFrameRenderedListener) {
                MediaCodecVideoRenderer.this.onProcessedTunneledBuffer(j);
            }
        }
    }

    private static boolean isBufferLate(long j) {
        return j < -30000;
    }

    private static boolean isBufferVeryLate(long j) {
        return j < -500000;
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, 0);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j) {
        this(context, mediaCodecSelector, j, null, null, -1);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        this(context, mediaCodecSelector, j, null, false, handler, videoRendererEventListener, i);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        super(2, mediaCodecSelector, drmSessionManager, z, 30.0f);
        this.allowedJoiningTimeMs = j;
        this.maxDroppedFramesToNotify = i;
        this.context = context.getApplicationContext();
        this.frameReleaseTimeHelper = new VideoFrameReleaseTimeHelper(this.context);
        this.eventDispatcher = new EventDispatcher(handler, videoRendererEventListener);
        this.deviceNeedsAutoFrcWorkaround = deviceNeedsAutoFrcWorkaround();
        this.pendingOutputStreamOffsetsUs = new long[10];
        this.pendingOutputStreamSwitchTimesUs = new long[10];
        this.outputStreamOffsetUs = C.TIME_UNSET;
        this.lastInputTimeUs = C.TIME_UNSET;
        this.joiningDeadlineMs = C.TIME_UNSET;
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.pendingPixelWidthHeightRatio = -1.0f;
        this.scalingMode = 1;
        clearReportedVideoSize();
    }

    protected int supportsFormat(MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, Format format) throws DecoderQueryException {
        int i = 0;
        if (!MimeTypes.isVideo(format.sampleMimeType)) {
            return 0;
        }
        boolean z;
        DrmInitData drmInitData = format.drmInitData;
        if (drmInitData != null) {
            z = false;
            for (int i2 = 0; i2 < drmInitData.schemeDataCount; i2++) {
                z |= drmInitData.get(i2).requiresSecureDecryption;
            }
        } else {
            z = false;
        }
        List decoderInfos = mediaCodecSelector.getDecoderInfos(format.sampleMimeType, z);
        int i3 = 2;
        if (decoderInfos.isEmpty()) {
            if (!z || mediaCodecSelector.getDecoderInfos(format.sampleMimeType, false).isEmpty() != null) {
                i3 = 1;
            }
            return i3;
        } else if (BaseRenderer.supportsFormatDrm(drmSessionManager, drmInitData) == null) {
            return 2;
        } else {
            MediaCodecInfo mediaCodecInfo = (MediaCodecInfo) decoderInfos.get(0);
            drmSessionManager = mediaCodecInfo.isFormatSupported(format);
            format = mediaCodecInfo.isSeamlessAdaptationSupported(format) != null ? 16 : 8;
            if (mediaCodecInfo.tunneling != null) {
                i = 32;
            }
            return (drmSessionManager != null ? 4 : 3) | (format | i);
        }
    }

    protected void onEnabled(boolean z) throws ExoPlaybackException {
        super.onEnabled(z);
        this.tunnelingAudioSessionId = getConfiguration().tunnelingAudioSessionId;
        this.tunneling = this.tunnelingAudioSessionId;
        this.eventDispatcher.enabled(this.decoderCounters);
        this.frameReleaseTimeHelper.enable();
    }

    protected void onStreamChanged(Format[] formatArr, long j) throws ExoPlaybackException {
        if (this.outputStreamOffsetUs == C.TIME_UNSET) {
            this.outputStreamOffsetUs = j;
        } else {
            if (this.pendingOutputStreamOffsetCount == this.pendingOutputStreamOffsetsUs.length) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Too many stream changes, so dropping offset: ");
                stringBuilder.append(this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1]);
                Log.w(str, stringBuilder.toString());
            } else {
                this.pendingOutputStreamOffsetCount++;
            }
            this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1] = j;
            this.pendingOutputStreamSwitchTimesUs[this.pendingOutputStreamOffsetCount - 1] = this.lastInputTimeUs;
        }
        super.onStreamChanged(formatArr, j);
    }

    protected void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        super.onPositionReset(j, z);
        clearRenderedFirstFrame();
        this.initialPositionUs = C.TIME_UNSET;
        this.consecutiveDroppedFrameCount = 0;
        this.lastInputTimeUs = C.TIME_UNSET;
        if (this.pendingOutputStreamOffsetCount != 0) {
            this.outputStreamOffsetUs = this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1];
            this.pendingOutputStreamOffsetCount = 0;
        }
        if (z) {
            setJoiningDeadlineMs();
        } else {
            this.joiningDeadlineMs = C.TIME_UNSET;
        }
    }

    public boolean isReady() {
        if (super.isReady() && (this.renderedFirstFrame || ((this.dummySurface != null && this.surface == this.dummySurface) || getCodec() == null || this.tunneling))) {
            this.joiningDeadlineMs = C.TIME_UNSET;
            return true;
        } else if (this.joiningDeadlineMs == C.TIME_UNSET) {
            return false;
        } else {
            if (SystemClock.elapsedRealtime() < this.joiningDeadlineMs) {
                return true;
            }
            this.joiningDeadlineMs = C.TIME_UNSET;
            return false;
        }
    }

    protected void onStarted() {
        super.onStarted();
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
    }

    protected void onStopped() {
        this.joiningDeadlineMs = C.TIME_UNSET;
        maybeNotifyDroppedFrames();
        super.onStopped();
    }

    protected void onDisabled() {
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.pendingPixelWidthHeightRatio = -1.0f;
        this.outputStreamOffsetUs = C.TIME_UNSET;
        this.lastInputTimeUs = C.TIME_UNSET;
        this.pendingOutputStreamOffsetCount = 0;
        clearReportedVideoSize();
        clearRenderedFirstFrame();
        this.frameReleaseTimeHelper.disable();
        this.tunnelingOnFrameRenderedListener = null;
        this.tunneling = false;
        try {
            super.onDisabled();
        } finally {
            this.decoderCounters.ensureUpdated();
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        if (i == 1) {
            setSurface((Surface) obj);
        } else if (i == 4) {
            this.scalingMode = ((Integer) obj).intValue();
            i = getCodec();
            if (i != 0) {
                i.setVideoScalingMode(this.scalingMode);
            }
        } else if (i == 6) {
            this.frameMetadataListener = (VideoFrameMetadataListener) obj;
        } else {
            super.handleMessage(i, obj);
        }
    }

    private void setSurface(Surface surface) throws ExoPlaybackException {
        if (surface == null) {
            if (this.dummySurface != null) {
                surface = this.dummySurface;
            } else {
                MediaCodecInfo codecInfo = getCodecInfo();
                if (codecInfo != null && shouldUseDummySurface(codecInfo)) {
                    this.dummySurface = DummySurface.newInstanceV17(this.context, codecInfo.secure);
                    surface = this.dummySurface;
                }
            }
        }
        if (this.surface != surface) {
            this.surface = surface;
            int state = getState();
            if (state == 1 || state == 2) {
                MediaCodec codec = getCodec();
                if (Util.SDK_INT < 23 || codec == null || surface == null || this.codecNeedsSetOutputSurfaceWorkaround) {
                    releaseCodec();
                    maybeInitCodec();
                } else {
                    setOutputSurfaceV23(codec, surface);
                }
            }
            if (surface == null || surface == this.dummySurface) {
                clearReportedVideoSize();
                clearRenderedFirstFrame();
                return;
            }
            maybeRenotifyVideoSizeChanged();
            clearRenderedFirstFrame();
            if (state == 2) {
                setJoiningDeadlineMs();
            }
        } else if (surface != null && surface != this.dummySurface) {
            maybeRenotifyVideoSizeChanged();
            maybeRenotifyRenderedFirstFrame();
        }
    }

    protected boolean shouldInitCodec(MediaCodecInfo mediaCodecInfo) {
        if (this.surface == null) {
            if (shouldUseDummySurface(mediaCodecInfo) == null) {
                return null;
            }
        }
        return true;
    }

    protected boolean getCodecNeedsEosPropagation() {
        return this.tunneling;
    }

    protected void configureCodec(MediaCodecInfo mediaCodecInfo, MediaCodec mediaCodec, Format format, MediaCrypto mediaCrypto, float f) throws DecoderQueryException {
        this.codecMaxValues = getCodecMaxValues(mediaCodecInfo, format, getStreamFormats());
        format = getMediaFormat(format, this.codecMaxValues, f, this.deviceNeedsAutoFrcWorkaround, this.tunnelingAudioSessionId);
        if (this.surface == null) {
            Assertions.checkState(shouldUseDummySurface(mediaCodecInfo));
            if (this.dummySurface == null) {
                this.dummySurface = DummySurface.newInstanceV17(this.context, mediaCodecInfo.secure);
            }
            this.surface = this.dummySurface;
        }
        mediaCodec.configure(format, this.surface, mediaCrypto, 0.0f);
        if (Util.SDK_INT >= 23 && this.tunneling != null) {
            this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(mediaCodec);
        }
    }

    protected int canKeepCodec(MediaCodec mediaCodec, MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        mediaCodec = true;
        if (!mediaCodecInfo.isSeamlessAdaptationSupported(format, format2, true) || format2.width > this.codecMaxValues.width || format2.height > this.codecMaxValues.height || getMaxInputSize(mediaCodecInfo, format2) > this.codecMaxValues.inputSize) {
            return null;
        }
        if (format.initializationDataEquals(format2) == null) {
            mediaCodec = 3;
        }
        return mediaCodec;
    }

    @CallSuper
    protected void releaseCodec() {
        try {
            super.releaseCodec();
        } finally {
            this.buffersInCodecCount = 0;
            if (this.dummySurface != null) {
                if (this.surface == this.dummySurface) {
                    this.surface = null;
                }
                this.dummySurface.release();
                this.dummySurface = null;
            }
        }
    }

    @CallSuper
    protected void flushCodec() throws ExoPlaybackException {
        super.flushCodec();
        this.buffersInCodecCount = 0;
    }

    protected float getCodecOperatingRate(float f, Format format, Format[] formatArr) {
        float f2 = -1.0f;
        for (Format format2 : formatArr) {
            float f3 = format2.frameRate;
            if (f3 != -1.0f) {
                f2 = Math.max(f2, f3);
            }
        }
        if (f2 == -1.0f) {
            return -1.0f;
        }
        return f2 * f;
    }

    protected void onCodecInitialized(String str, long j, long j2) {
        this.eventDispatcher.decoderInitialized(str, j, j2);
        this.codecNeedsSetOutputSurfaceWorkaround = codecNeedsSetOutputSurfaceWorkaround(str);
    }

    protected void onInputFormatChanged(Format format) throws ExoPlaybackException {
        super.onInputFormatChanged(format);
        this.eventDispatcher.inputFormatChanged(format);
        this.pendingPixelWidthHeightRatio = format.pixelWidthHeightRatio;
        this.pendingRotationDegrees = format.rotationDegrees;
    }

    @CallSuper
    protected void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) {
        this.buffersInCodecCount++;
        this.lastInputTimeUs = Math.max(decoderInputBuffer.timeUs, this.lastInputTimeUs);
        if (Util.SDK_INT < 23 && this.tunneling) {
            onProcessedTunneledBuffer(decoderInputBuffer.timeUs);
        }
    }

    protected void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        int integer;
        int integer2;
        Object obj = (mediaFormat.containsKey(KEY_CROP_RIGHT) && mediaFormat.containsKey(KEY_CROP_LEFT) && mediaFormat.containsKey(KEY_CROP_BOTTOM) && mediaFormat.containsKey(KEY_CROP_TOP)) ? 1 : null;
        if (obj != null) {
            integer = (mediaFormat.getInteger(KEY_CROP_RIGHT) - mediaFormat.getInteger(KEY_CROP_LEFT)) + 1;
        } else {
            integer = mediaFormat.getInteger("width");
        }
        if (obj != null) {
            integer2 = (mediaFormat.getInteger(KEY_CROP_BOTTOM) - mediaFormat.getInteger(KEY_CROP_TOP)) + 1;
        } else {
            integer2 = mediaFormat.getInteger("height");
        }
        processOutputFormat(mediaCodec, integer, integer2);
    }

    protected boolean processOutputBuffer(long r22, long r24, android.media.MediaCodec r26, java.nio.ByteBuffer r27, int r28, int r29, long r30, boolean r32, com.google.android.exoplayer2.Format r33) throws com.google.android.exoplayer2.ExoPlaybackException {
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
        r21 = this;
        r7 = r21;
        r5 = r22;
        r8 = r24;
        r10 = r26;
        r11 = r28;
        r0 = r30;
        r2 = r7.initialPositionUs;
        r12 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r4 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1));
        if (r4 != 0) goto L_0x0019;
    L_0x0017:
        r7.initialPositionUs = r5;
    L_0x0019:
        r2 = r7.outputStreamOffsetUs;
        r12 = r0 - r2;
        r14 = 1;
        if (r32 == 0) goto L_0x0024;
    L_0x0020:
        r7.skipOutputBuffer(r10, r11, r12);
        return r14;
    L_0x0024:
        r2 = 0;
        r2 = r0 - r5;
        r4 = r7.surface;
        r15 = r7.dummySurface;
        r16 = 0;
        if (r4 != r15) goto L_0x003a;
    L_0x002f:
        r0 = isBufferLate(r2);
        if (r0 == 0) goto L_0x0039;
    L_0x0035:
        r7.skipOutputBuffer(r10, r11, r12);
        return r14;
    L_0x0039:
        return r16;
    L_0x003a:
        r17 = android.os.SystemClock.elapsedRealtime();
        r19 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r17 = r17 * r19;
        r4 = r21.getState();
        r15 = 2;
        if (r4 != r15) goto L_0x004b;
    L_0x0049:
        r4 = 1;
        goto L_0x004c;
    L_0x004b:
        r4 = 0;
    L_0x004c:
        r15 = r7.renderedFirstFrame;
        if (r15 == 0) goto L_0x00f0;
    L_0x0050:
        if (r4 == 0) goto L_0x005e;
    L_0x0052:
        r14 = r7.lastRenderTimeUs;
        r14 = r17 - r14;
        r14 = r7.shouldForceRenderOutputBuffer(r2, r14);
        if (r14 == 0) goto L_0x005e;
    L_0x005c:
        goto L_0x00f0;
    L_0x005e:
        if (r4 == 0) goto L_0x00ef;
    L_0x0060:
        r14 = r7.initialPositionUs;
        r4 = (r5 > r14 ? 1 : (r5 == r14 ? 0 : -1));
        if (r4 != 0) goto L_0x0068;
    L_0x0066:
        goto L_0x00ef;
    L_0x0068:
        r4 = 0;
        r17 = r17 - r8;
        r2 = r2 - r17;
        r14 = java.lang.System.nanoTime();
        r2 = r2 * r19;
        r2 = r2 + r14;
        r4 = r7.frameReleaseTimeHelper;
        r17 = r4.adjustReleaseTime(r0, r2);
        r0 = r17 - r14;
        r14 = r0 / r19;
        r0 = r7.shouldDropBuffersToKeyframe(r14, r8);
        if (r0 == 0) goto L_0x0094;
    L_0x0084:
        r0 = r21;
        r1 = r26;
        r2 = r28;
        r3 = r12;
        r5 = r22;
        r0 = r0.maybeDropBuffersToKeyframe(r1, r2, r3, r5);
        if (r0 == 0) goto L_0x0094;
    L_0x0093:
        return r16;
    L_0x0094:
        r0 = r7.shouldDropOutputBuffer(r14, r8);
        if (r0 == 0) goto L_0x009f;
    L_0x009a:
        r7.dropOutputBuffer(r10, r11, r12);
        r0 = 1;
        return r0;
    L_0x009f:
        r0 = com.google.android.exoplayer2.util.Util.SDK_INT;
        r1 = 21;
        if (r0 < r1) goto L_0x00c2;
    L_0x00a5:
        r0 = 50000; // 0xc350 float:7.0065E-41 double:2.47033E-319;
        r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1));
        if (r2 >= 0) goto L_0x00ee;
    L_0x00ac:
        r0 = r21;
        r1 = r12;
        r3 = r17;
        r5 = r33;
        r0.notifyFrameMetadataListener(r1, r3, r5);
        r1 = r26;
        r2 = r28;
        r3 = r12;
        r5 = r17;
        r0.renderOutputBufferV21(r1, r2, r3, r5);
        r0 = 1;
        return r0;
    L_0x00c2:
        r0 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1));
        if (r2 >= 0) goto L_0x00ee;
    L_0x00c8:
        r0 = 11000; // 0x2af8 float:1.5414E-41 double:5.4347E-320;
        r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1));
        if (r2 <= 0) goto L_0x00df;
    L_0x00ce:
        r0 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r14 = r14 - r0;
        r14 = r14 / r19;	 Catch:{ InterruptedException -> 0x00d7 }
        java.lang.Thread.sleep(r14);	 Catch:{ InterruptedException -> 0x00d7 }
        goto L_0x00df;
    L_0x00d7:
        r0 = java.lang.Thread.currentThread();
        r0.interrupt();
        return r16;
    L_0x00df:
        r0 = r21;
        r1 = r12;
        r3 = r17;
        r5 = r33;
        r0.notifyFrameMetadataListener(r1, r3, r5);
        r7.renderOutputBuffer(r10, r11, r12);
        r0 = 1;
        return r0;
    L_0x00ee:
        return r16;
    L_0x00ef:
        return r16;
    L_0x00f0:
        r8 = java.lang.System.nanoTime();
        r0 = r21;
        r1 = r12;
        r3 = r8;
        r5 = r33;
        r0.notifyFrameMetadataListener(r1, r3, r5);
        r0 = com.google.android.exoplayer2.util.Util.SDK_INT;
        r1 = 21;
        if (r0 < r1) goto L_0x0110;
    L_0x0103:
        r0 = r21;
        r1 = r26;
        r2 = r28;
        r3 = r12;
        r5 = r8;
        r0.renderOutputBufferV21(r1, r2, r3, r5);
    L_0x010e:
        r0 = 1;
        goto L_0x0114;
    L_0x0110:
        r7.renderOutputBuffer(r10, r11, r12);
        goto L_0x010e;
    L_0x0114:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.processOutputBuffer(long, long, android.media.MediaCodec, java.nio.ByteBuffer, int, int, long, boolean, com.google.android.exoplayer2.Format):boolean");
    }

    private void processOutputFormat(MediaCodec mediaCodec, int i, int i2) {
        this.currentWidth = i;
        this.currentHeight = i2;
        this.currentPixelWidthHeightRatio = this.pendingPixelWidthHeightRatio;
        if (Util.SDK_INT < 21) {
            this.currentUnappliedRotationDegrees = this.pendingRotationDegrees;
        } else if (this.pendingRotationDegrees == 90 || this.pendingRotationDegrees == 270) {
            i = this.currentWidth;
            this.currentWidth = this.currentHeight;
            this.currentHeight = i;
            this.currentPixelWidthHeightRatio = 1065353216 / this.currentPixelWidthHeightRatio;
        }
        mediaCodec.setVideoScalingMode(this.scalingMode);
    }

    private void notifyFrameMetadataListener(long j, long j2, Format format) {
        if (this.frameMetadataListener != null) {
            this.frameMetadataListener.onVideoFrameAboutToBeRendered(j, j2, format);
        }
    }

    protected long getOutputStreamOffsetUs() {
        return this.outputStreamOffsetUs;
    }

    protected void onProcessedTunneledBuffer(long j) {
        Format updateOutputFormatForTime = updateOutputFormatForTime(j);
        if (updateOutputFormatForTime != null) {
            processOutputFormat(getCodec(), updateOutputFormatForTime.width, updateOutputFormatForTime.height);
        }
        maybeNotifyVideoSizeChanged();
        maybeNotifyRenderedFirstFrame();
        onProcessedOutputBuffer(j);
    }

    @CallSuper
    protected void onProcessedOutputBuffer(long j) {
        this.buffersInCodecCount--;
        while (this.pendingOutputStreamOffsetCount != 0 && j >= this.pendingOutputStreamSwitchTimesUs[0]) {
            this.outputStreamOffsetUs = this.pendingOutputStreamOffsetsUs[0];
            this.pendingOutputStreamOffsetCount--;
            System.arraycopy(this.pendingOutputStreamOffsetsUs, 1, this.pendingOutputStreamOffsetsUs, 0, this.pendingOutputStreamOffsetCount);
            System.arraycopy(this.pendingOutputStreamSwitchTimesUs, 1, this.pendingOutputStreamSwitchTimesUs, 0, this.pendingOutputStreamOffsetCount);
        }
    }

    protected boolean shouldDropOutputBuffer(long j, long j2) {
        return isBufferLate(j);
    }

    protected boolean shouldDropBuffersToKeyframe(long j, long j2) {
        return isBufferVeryLate(j);
    }

    protected boolean shouldForceRenderOutputBuffer(long j, long j2) {
        return (isBufferLate(j) == null || j2 <= 100000) ? 0 : 1;
    }

    protected void skipOutputBuffer(MediaCodec mediaCodec, int i, long j) {
        TraceUtil.beginSection("skipVideoBuffer");
        mediaCodec.releaseOutputBuffer(i, 0);
        TraceUtil.endSection();
        mediaCodec = this.decoderCounters;
        mediaCodec.skippedOutputBufferCount++;
    }

    protected void dropOutputBuffer(MediaCodec mediaCodec, int i, long j) {
        TraceUtil.beginSection("dropVideoBuffer");
        mediaCodec.releaseOutputBuffer(i, 0);
        TraceUtil.endSection();
        updateDroppedBufferCounters(1);
    }

    protected boolean maybeDropBuffersToKeyframe(MediaCodec mediaCodec, int i, long j, long j2) throws ExoPlaybackException {
        mediaCodec = skipSource(j2);
        if (mediaCodec == null) {
            return null;
        }
        i = this.decoderCounters;
        i.droppedToKeyframeCount++;
        updateDroppedBufferCounters(this.buffersInCodecCount + mediaCodec);
        flushCodec();
        return true;
    }

    protected void updateDroppedBufferCounters(int i) {
        DecoderCounters decoderCounters = this.decoderCounters;
        decoderCounters.droppedBufferCount += i;
        this.droppedFrames += i;
        this.consecutiveDroppedFrameCount += i;
        this.decoderCounters.maxConsecutiveDroppedBufferCount = Math.max(this.consecutiveDroppedFrameCount, this.decoderCounters.maxConsecutiveDroppedBufferCount);
        if (this.maxDroppedFramesToNotify > 0 && this.droppedFrames >= this.maxDroppedFramesToNotify) {
            maybeNotifyDroppedFrames();
        }
    }

    protected void renderOutputBuffer(MediaCodec mediaCodec, int i, long j) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodec.releaseOutputBuffer(i, true);
        TraceUtil.endSection();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
        mediaCodec = this.decoderCounters;
        mediaCodec.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = null;
        maybeNotifyRenderedFirstFrame();
    }

    @TargetApi(21)
    protected void renderOutputBufferV21(MediaCodec mediaCodec, int i, long j, long j2) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodec.releaseOutputBuffer(i, j2);
        TraceUtil.endSection();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
        mediaCodec = this.decoderCounters;
        mediaCodec.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = null;
        maybeNotifyRenderedFirstFrame();
    }

    private boolean shouldUseDummySurface(MediaCodecInfo mediaCodecInfo) {
        return (Util.SDK_INT < 23 || this.tunneling || codecNeedsSetOutputSurfaceWorkaround(mediaCodecInfo.name) || (mediaCodecInfo.secure != null && DummySurface.isSecureSupported(this.context) == null)) ? null : true;
    }

    private void setJoiningDeadlineMs() {
        this.joiningDeadlineMs = this.allowedJoiningTimeMs > 0 ? SystemClock.elapsedRealtime() + this.allowedJoiningTimeMs : C.TIME_UNSET;
    }

    private void clearRenderedFirstFrame() {
        this.renderedFirstFrame = false;
        if (Util.SDK_INT >= 23 && this.tunneling) {
            MediaCodec codec = getCodec();
            if (codec != null) {
                this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(codec);
            }
        }
    }

    void maybeNotifyRenderedFirstFrame() {
        if (!this.renderedFirstFrame) {
            this.renderedFirstFrame = true;
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void maybeRenotifyRenderedFirstFrame() {
        if (this.renderedFirstFrame) {
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void clearReportedVideoSize() {
        this.reportedWidth = -1;
        this.reportedHeight = -1;
        this.reportedPixelWidthHeightRatio = -1.0f;
        this.reportedUnappliedRotationDegrees = -1;
    }

    private void maybeNotifyVideoSizeChanged() {
        if (this.currentWidth != -1 || this.currentHeight != -1) {
            if (this.reportedWidth != this.currentWidth || this.reportedHeight != this.currentHeight || this.reportedUnappliedRotationDegrees != this.currentUnappliedRotationDegrees || this.reportedPixelWidthHeightRatio != this.currentPixelWidthHeightRatio) {
                this.eventDispatcher.videoSizeChanged(this.currentWidth, this.currentHeight, this.currentUnappliedRotationDegrees, this.currentPixelWidthHeightRatio);
                this.reportedWidth = this.currentWidth;
                this.reportedHeight = this.currentHeight;
                this.reportedUnappliedRotationDegrees = this.currentUnappliedRotationDegrees;
                this.reportedPixelWidthHeightRatio = this.currentPixelWidthHeightRatio;
            }
        }
    }

    private void maybeRenotifyVideoSizeChanged() {
        if (this.reportedWidth != -1 || this.reportedHeight != -1) {
            this.eventDispatcher.videoSizeChanged(this.reportedWidth, this.reportedHeight, this.reportedUnappliedRotationDegrees, this.reportedPixelWidthHeightRatio);
        }
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.eventDispatcher.droppedFrames(this.droppedFrames, elapsedRealtime - this.droppedFrameAccumulationStartTimeMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = elapsedRealtime;
        }
    }

    @TargetApi(23)
    private static void setOutputSurfaceV23(MediaCodec mediaCodec, Surface surface) {
        mediaCodec.setOutputSurface(surface);
    }

    @TargetApi(21)
    private static void configureTunnelingV21(MediaFormat mediaFormat, int i) {
        mediaFormat.setFeatureEnabled("tunneled-playback", true);
        mediaFormat.setInteger("audio-session-id", i);
    }

    @SuppressLint({"InlinedApi"})
    protected MediaFormat getMediaFormat(Format format, CodecMaxValues codecMaxValues, float f, boolean z, int i) {
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", format.sampleMimeType);
        mediaFormat.setInteger("width", format.width);
        mediaFormat.setInteger("height", format.height);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "rotation-degrees", format.rotationDegrees);
        MediaFormatUtil.maybeSetColorInfo(mediaFormat, format.colorInfo);
        mediaFormat.setInteger("max-width", codecMaxValues.width);
        mediaFormat.setInteger("max-height", codecMaxValues.height);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxValues.inputSize);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger("priority", 0);
            if (f != -1.0f) {
                mediaFormat.setFloat("operating-rate", f);
            }
        }
        if (z) {
            mediaFormat.setInteger("auto-frc", 0);
        }
        if (i != 0) {
            configureTunnelingV21(mediaFormat, i);
        }
        return mediaFormat;
    }

    protected CodecMaxValues getCodecMaxValues(MediaCodecInfo mediaCodecInfo, Format format, Format[] formatArr) throws DecoderQueryException {
        int i = format.width;
        int i2 = format.height;
        int maxInputSize = getMaxInputSize(mediaCodecInfo, format);
        if (formatArr.length == 1) {
            if (maxInputSize != -1) {
                mediaCodecInfo = getCodecMaxInputSize(mediaCodecInfo, format.sampleMimeType, format.width, format.height);
                if (mediaCodecInfo != -1) {
                    maxInputSize = Math.min((int) (((float) maxInputSize) * 1069547520), mediaCodecInfo);
                }
            }
            return new CodecMaxValues(i, i2, maxInputSize);
        }
        int i3 = i2;
        int i4 = maxInputSize;
        i2 = 0;
        maxInputSize = i;
        for (Format format2 : formatArr) {
            if (mediaCodecInfo.isSeamlessAdaptationSupported(format, format2, false)) {
                int i5;
                if (format2.width != -1) {
                    if (format2.height != -1) {
                        i5 = 0;
                        i2 |= i5;
                        maxInputSize = Math.max(maxInputSize, format2.width);
                        i3 = Math.max(i3, format2.height);
                        i4 = Math.max(i4, getMaxInputSize(mediaCodecInfo, format2));
                    }
                }
                i5 = 1;
                i2 |= i5;
                maxInputSize = Math.max(maxInputSize, format2.width);
                i3 = Math.max(i3, format2.height);
                i4 = Math.max(i4, getMaxInputSize(mediaCodecInfo, format2));
            }
        }
        if (i2 != 0) {
            formatArr = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resolutions unknown. Codec max resolution: ");
            stringBuilder.append(maxInputSize);
            stringBuilder.append("x");
            stringBuilder.append(i3);
            Log.w(formatArr, stringBuilder.toString());
            formatArr = getCodecMaxSize(mediaCodecInfo, format);
            if (formatArr != null) {
                maxInputSize = Math.max(maxInputSize, formatArr.x);
                i3 = Math.max(i3, formatArr.y);
                i4 = Math.max(i4, getCodecMaxInputSize(mediaCodecInfo, format.sampleMimeType, maxInputSize, i3));
                mediaCodecInfo = TAG;
                format = new StringBuilder();
                format.append("Codec max resolution adjusted to: ");
                format.append(maxInputSize);
                format.append("x");
                format.append(i3);
                Log.w(mediaCodecInfo, format.toString());
            }
        }
        return new CodecMaxValues(maxInputSize, i3, i4);
    }

    private static Point getCodecMaxSize(MediaCodecInfo mediaCodecInfo, Format format) throws DecoderQueryException {
        int i = 0;
        Object obj = format.height > format.width ? 1 : null;
        int i2 = obj != null ? format.height : format.width;
        int i3 = obj != null ? format.width : format.height;
        float f = ((float) i3) / ((float) i2);
        int[] iArr = STANDARD_LONG_EDGE_VIDEO_PX;
        int length = iArr.length;
        while (i < length) {
            int i4 = iArr[i];
            int i5 = (int) (((float) i4) * f);
            if (i4 > i2) {
                if (i5 > i3) {
                    int i6;
                    if (Util.SDK_INT >= 21) {
                        i6 = obj != null ? i5 : i4;
                        if (obj == null) {
                            i4 = i5;
                        }
                        Point alignVideoSizeV21 = mediaCodecInfo.alignVideoSizeV21(i6, i4);
                        if (mediaCodecInfo.isVideoSizeAndRateSupportedV21(alignVideoSizeV21.x, alignVideoSizeV21.y, (double) format.frameRate)) {
                            return alignVideoSizeV21;
                        }
                    } else {
                        i4 = Util.ceilDivide(i4, 16) * 16;
                        i6 = Util.ceilDivide(i5, 16) * 16;
                        if (i4 * i6 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                            format = obj != null ? i6 : i4;
                            if (obj != null) {
                                i6 = i4;
                            }
                            return new Point(format, i6);
                        }
                    }
                    i++;
                }
            }
            return null;
        }
        return null;
    }

    private static int getMaxInputSize(MediaCodecInfo mediaCodecInfo, Format format) {
        if (format.maxInputSize == -1) {
            return getCodecMaxInputSize(mediaCodecInfo, format.sampleMimeType, format.width, format.height);
        }
        int i = 0;
        for (int i2 = 0; i2 < format.initializationData.size(); i2++) {
            i += ((byte[]) format.initializationData.get(i2)).length;
        }
        return format.maxInputSize + i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getCodecMaxInputSize(com.google.android.exoplayer2.mediacodec.MediaCodecInfo r5, java.lang.String r6, int r7, int r8) {
        /*
        r0 = -1;
        if (r7 == r0) goto L_0x00a1;
    L_0x0003:
        if (r8 != r0) goto L_0x0007;
    L_0x0005:
        goto L_0x00a1;
    L_0x0007:
        r1 = r6.hashCode();
        r2 = 3;
        r3 = 4;
        r4 = 2;
        switch(r1) {
            case -1664118616: goto L_0x0044;
            case -1662541442: goto L_0x003a;
            case 1187890754: goto L_0x0030;
            case 1331836730: goto L_0x0026;
            case 1599127256: goto L_0x001c;
            case 1599127257: goto L_0x0012;
            default: goto L_0x0011;
        };
    L_0x0011:
        goto L_0x004e;
    L_0x0012:
        r1 = "video/x-vnd.on2.vp9";
        r6 = r6.equals(r1);
        if (r6 == 0) goto L_0x004e;
    L_0x001a:
        r6 = 5;
        goto L_0x004f;
    L_0x001c:
        r1 = "video/x-vnd.on2.vp8";
        r6 = r6.equals(r1);
        if (r6 == 0) goto L_0x004e;
    L_0x0024:
        r6 = 3;
        goto L_0x004f;
    L_0x0026:
        r1 = "video/avc";
        r6 = r6.equals(r1);
        if (r6 == 0) goto L_0x004e;
    L_0x002e:
        r6 = 2;
        goto L_0x004f;
    L_0x0030:
        r1 = "video/mp4v-es";
        r6 = r6.equals(r1);
        if (r6 == 0) goto L_0x004e;
    L_0x0038:
        r6 = 1;
        goto L_0x004f;
    L_0x003a:
        r1 = "video/hevc";
        r6 = r6.equals(r1);
        if (r6 == 0) goto L_0x004e;
    L_0x0042:
        r6 = 4;
        goto L_0x004f;
    L_0x0044:
        r1 = "video/3gpp";
        r6 = r6.equals(r1);
        if (r6 == 0) goto L_0x004e;
    L_0x004c:
        r6 = 0;
        goto L_0x004f;
    L_0x004e:
        r6 = -1;
    L_0x004f:
        switch(r6) {
            case 0: goto L_0x0098;
            case 1: goto L_0x0098;
            case 2: goto L_0x0059;
            case 3: goto L_0x0056;
            case 4: goto L_0x0053;
            case 5: goto L_0x0053;
            default: goto L_0x0052;
        };
    L_0x0052:
        return r0;
    L_0x0053:
        r7 = r7 * r8;
        goto L_0x009b;
    L_0x0056:
        r7 = r7 * r8;
        goto L_0x009a;
    L_0x0059:
        r6 = "BRAVIA 4K 2015";
        r1 = com.google.android.exoplayer2.util.Util.MODEL;
        r6 = r6.equals(r1);
        if (r6 != 0) goto L_0x0097;
    L_0x0063:
        r6 = "Amazon";
        r1 = com.google.android.exoplayer2.util.Util.MANUFACTURER;
        r6 = r6.equals(r1);
        if (r6 == 0) goto L_0x0086;
    L_0x006d:
        r6 = "KFSOWI";
        r1 = com.google.android.exoplayer2.util.Util.MODEL;
        r6 = r6.equals(r1);
        if (r6 != 0) goto L_0x0097;
    L_0x0077:
        r6 = "AFTS";
        r1 = com.google.android.exoplayer2.util.Util.MODEL;
        r6 = r6.equals(r1);
        if (r6 == 0) goto L_0x0086;
    L_0x0081:
        r5 = r5.secure;
        if (r5 == 0) goto L_0x0086;
    L_0x0085:
        goto L_0x0097;
    L_0x0086:
        r5 = 16;
        r6 = com.google.android.exoplayer2.util.Util.ceilDivide(r7, r5);
        r7 = com.google.android.exoplayer2.util.Util.ceilDivide(r8, r5);
        r6 = r6 * r7;
        r6 = r6 * 16;
        r7 = r6 * 16;
        goto L_0x009a;
    L_0x0097:
        return r0;
    L_0x0098:
        r7 = r7 * r8;
    L_0x009a:
        r3 = 2;
    L_0x009b:
        r7 = r7 * 3;
        r3 = r3 * 2;
        r7 = r7 / r3;
        return r7;
    L_0x00a1:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.getCodecMaxInputSize(com.google.android.exoplayer2.mediacodec.MediaCodecInfo, java.lang.String, int, int):int");
    }

    private static boolean deviceNeedsAutoFrcWorkaround() {
        return Util.SDK_INT <= 22 && "foster".equals(Util.DEVICE) && "NVIDIA".equals(Util.MANUFACTURER);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected boolean codecNeedsSetOutputSurfaceWorkaround(java.lang.String r7) {
        /*
        r6 = this;
        r0 = com.google.android.exoplayer2.util.Util.SDK_INT;
        r1 = 27;
        r2 = 0;
        if (r0 >= r1) goto L_0x05d5;
    L_0x0007:
        r0 = "OMX.google";
        r7 = r7.startsWith(r0);
        if (r7 == 0) goto L_0x0011;
    L_0x000f:
        goto L_0x05d5;
    L_0x0011:
        r7 = com.google.android.exoplayer2.video.MediaCodecVideoRenderer.class;
        monitor-enter(r7);
        r0 = evaluatedDeviceNeedsSetOutputSurfaceWorkaround;	 Catch:{ all -> 0x05d2 }
        if (r0 != 0) goto L_0x05ce;
    L_0x0018:
        r0 = com.google.android.exoplayer2.util.Util.DEVICE;	 Catch:{ all -> 0x05d2 }
        r3 = r0.hashCode();	 Catch:{ all -> 0x05d2 }
        r4 = -1;
        r5 = 1;
        switch(r3) {
            case -2144781245: goto L_0x058f;
            case -2144781185: goto L_0x0584;
            case -2144781160: goto L_0x0579;
            case -2097309513: goto L_0x056e;
            case -2022874474: goto L_0x0563;
            case -1978993182: goto L_0x0558;
            case -1978990237: goto L_0x054d;
            case -1936688988: goto L_0x0542;
            case -1936688066: goto L_0x0537;
            case -1936688065: goto L_0x052b;
            case -1931988508: goto L_0x051f;
            case -1696512866: goto L_0x0513;
            case -1680025915: goto L_0x0507;
            case -1615810839: goto L_0x04fb;
            case -1554255044: goto L_0x04ef;
            case -1481772737: goto L_0x04e3;
            case -1481772730: goto L_0x04d7;
            case -1481772729: goto L_0x04cb;
            case -1320080169: goto L_0x04bf;
            case -1217592143: goto L_0x04b3;
            case -1180384755: goto L_0x04a7;
            case -1139198265: goto L_0x049b;
            case -1052835013: goto L_0x048f;
            case -993250464: goto L_0x0484;
            case -965403638: goto L_0x0478;
            case -958336948: goto L_0x046c;
            case -879245230: goto L_0x0460;
            case -842500323: goto L_0x0454;
            case -821392978: goto L_0x0449;
            case -797483286: goto L_0x043d;
            case -794946968: goto L_0x0431;
            case -788334647: goto L_0x0425;
            case -782144577: goto L_0x0419;
            case -575125681: goto L_0x040d;
            case -521118391: goto L_0x0401;
            case -430914369: goto L_0x03f5;
            case -290434366: goto L_0x03e9;
            case -282781963: goto L_0x03dd;
            case -277133239: goto L_0x03d1;
            case -173639913: goto L_0x03c5;
            case -56598463: goto L_0x03b9;
            case 2126: goto L_0x03ad;
            case 2564: goto L_0x03a1;
            case 2715: goto L_0x0395;
            case 2719: goto L_0x0389;
            case 3483: goto L_0x037d;
            case 73405: goto L_0x0371;
            case 75739: goto L_0x0365;
            case 76779: goto L_0x0359;
            case 78669: goto L_0x034d;
            case 79305: goto L_0x0341;
            case 80618: goto L_0x0335;
            case 88274: goto L_0x0329;
            case 98846: goto L_0x031d;
            case 98848: goto L_0x0311;
            case 99329: goto L_0x0305;
            case 101481: goto L_0x02f9;
            case 1513190: goto L_0x02ee;
            case 1514184: goto L_0x02e3;
            case 1514185: goto L_0x02d8;
            case 2436959: goto L_0x02cc;
            case 2463773: goto L_0x02c0;
            case 2464648: goto L_0x02b4;
            case 2689555: goto L_0x02a8;
            case 3351335: goto L_0x029c;
            case 3386211: goto L_0x0290;
            case 41325051: goto L_0x0284;
            case 55178625: goto L_0x0278;
            case 61542055: goto L_0x026d;
            case 65355429: goto L_0x0261;
            case 66214468: goto L_0x0255;
            case 66214470: goto L_0x0249;
            case 66214473: goto L_0x023d;
            case 66215429: goto L_0x0231;
            case 66215431: goto L_0x0225;
            case 66215433: goto L_0x0219;
            case 66216390: goto L_0x020d;
            case 76402249: goto L_0x0201;
            case 76404105: goto L_0x01f5;
            case 76404911: goto L_0x01e9;
            case 80963634: goto L_0x01dd;
            case 82882791: goto L_0x01d1;
            case 102844228: goto L_0x01c5;
            case 165221241: goto L_0x01ba;
            case 182191441: goto L_0x01ae;
            case 245388979: goto L_0x01a2;
            case 287431619: goto L_0x0196;
            case 307593612: goto L_0x018a;
            case 308517133: goto L_0x017e;
            case 316215098: goto L_0x0172;
            case 316215116: goto L_0x0166;
            case 316246811: goto L_0x015a;
            case 316246818: goto L_0x014e;
            case 407160593: goto L_0x0142;
            case 507412548: goto L_0x0136;
            case 793982701: goto L_0x012a;
            case 794038622: goto L_0x011e;
            case 794040393: goto L_0x0112;
            case 835649806: goto L_0x0106;
            case 917340916: goto L_0x00fb;
            case 958008161: goto L_0x00ef;
            case 1060579533: goto L_0x00e3;
            case 1150207623: goto L_0x00d7;
            case 1176899427: goto L_0x00cb;
            case 1280332038: goto L_0x00bf;
            case 1306947716: goto L_0x00b5;
            case 1349174697: goto L_0x00a9;
            case 1522194893: goto L_0x009d;
            case 1691543273: goto L_0x0091;
            case 1709443163: goto L_0x0085;
            case 1865889110: goto L_0x0079;
            case 1906253259: goto L_0x006d;
            case 1977196784: goto L_0x0061;
            case 2029784656: goto L_0x0055;
            case 2030379515: goto L_0x0049;
            case 2047190025: goto L_0x003d;
            case 2047252157: goto L_0x0031;
            case 2048319463: goto L_0x0025;
            default: goto L_0x0023;
        };	 Catch:{ all -> 0x05d2 }
    L_0x0023:
        goto L_0x059a;
    L_0x0025:
        r1 = "HWVNS-H";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x002d:
        r1 = 50;
        goto L_0x059b;
    L_0x0031:
        r1 = "ELUGA_Prim";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0039:
        r1 = 25;
        goto L_0x059b;
    L_0x003d:
        r1 = "ELUGA_Note";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0045:
        r1 = 24;
        goto L_0x059b;
    L_0x0049:
        r1 = "HWCAM-H";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0051:
        r1 = 49;
        goto L_0x059b;
    L_0x0055:
        r1 = "HWBLN-H";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x005d:
        r1 = 48;
        goto L_0x059b;
    L_0x0061:
        r1 = "Infinix-X572";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0069:
        r1 = 52;
        goto L_0x059b;
    L_0x006d:
        r1 = "PB2-670M";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0075:
        r1 = 79;
        goto L_0x059b;
    L_0x0079:
        r1 = "santoni";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0081:
        r1 = 95;
        goto L_0x059b;
    L_0x0085:
        r1 = "iball8735_9806";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x008d:
        r1 = 51;
        goto L_0x059b;
    L_0x0091:
        r1 = "CPH1609";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0099:
        r1 = 17;
        goto L_0x059b;
    L_0x009d:
        r1 = "woods_f";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x00a5:
        r1 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
        goto L_0x059b;
    L_0x00a9:
        r1 = "htc_e56ml_dtul";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x00b1:
        r1 = 46;
        goto L_0x059b;
    L_0x00b5:
        r3 = "EverStar_S";
        r0 = r0.equals(r3);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x00bd:
        goto L_0x059b;
    L_0x00bf:
        r1 = "hwALE-H";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x00c7:
        r1 = 47;
        goto L_0x059b;
    L_0x00cb:
        r1 = "itel_S41";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x00d3:
        r1 = 54;
        goto L_0x059b;
    L_0x00d7:
        r1 = "LS-5017";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x00df:
        r1 = 59;
        goto L_0x059b;
    L_0x00e3:
        r1 = "panell_d";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x00eb:
        r1 = 75;
        goto L_0x059b;
    L_0x00ef:
        r1 = "j2xlteins";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x00f7:
        r1 = 55;
        goto L_0x059b;
    L_0x00fb:
        r1 = "A7000plus";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0103:
        r1 = 7;
        goto L_0x059b;
    L_0x0106:
        r1 = "manning";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x010e:
        r1 = 61;
        goto L_0x059b;
    L_0x0112:
        r1 = "GIONEE_WBL7519";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x011a:
        r1 = 44;
        goto L_0x059b;
    L_0x011e:
        r1 = "GIONEE_WBL7365";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0126:
        r1 = 43;
        goto L_0x059b;
    L_0x012a:
        r1 = "GIONEE_WBL5708";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0132:
        r1 = 42;
        goto L_0x059b;
    L_0x0136:
        r1 = "QM16XE_U";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x013e:
        r1 = 93;
        goto L_0x059b;
    L_0x0142:
        r1 = "Pixi5-10_4G";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x014a:
        r1 = 85;
        goto L_0x059b;
    L_0x014e:
        r1 = "TB3-850M";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0156:
        r1 = 103; // 0x67 float:1.44E-43 double:5.1E-322;
        goto L_0x059b;
    L_0x015a:
        r1 = "TB3-850F";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0162:
        r1 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        goto L_0x059b;
    L_0x0166:
        r1 = "TB3-730X";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x016e:
        r1 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x059b;
    L_0x0172:
        r1 = "TB3-730F";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x017a:
        r1 = 100;
        goto L_0x059b;
    L_0x017e:
        r1 = "A7020a48";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0186:
        r1 = 9;
        goto L_0x059b;
    L_0x018a:
        r1 = "A7010a48";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0192:
        r1 = 8;
        goto L_0x059b;
    L_0x0196:
        r1 = "griffin";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x019e:
        r1 = 45;
        goto L_0x059b;
    L_0x01a2:
        r1 = "marino_f";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x01aa:
        r1 = 62;
        goto L_0x059b;
    L_0x01ae:
        r1 = "CPY83_I00";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x01b6:
        r1 = 18;
        goto L_0x059b;
    L_0x01ba:
        r1 = "A2016a40";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x01c2:
        r1 = 5;
        goto L_0x059b;
    L_0x01c5:
        r1 = "le_x6";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x01cd:
        r1 = 58;
        goto L_0x059b;
    L_0x01d1:
        r1 = "X3_HK";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x01d9:
        r1 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        goto L_0x059b;
    L_0x01dd:
        r1 = "V23GB";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x01e5:
        r1 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        goto L_0x059b;
    L_0x01e9:
        r1 = "Q4310";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x01f1:
        r1 = 91;
        goto L_0x059b;
    L_0x01f5:
        r1 = "Q4260";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x01fd:
        r1 = 89;
        goto L_0x059b;
    L_0x0201:
        r1 = "PRO7S";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0209:
        r1 = 87;
        goto L_0x059b;
    L_0x020d:
        r1 = "F3311";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0215:
        r1 = 34;
        goto L_0x059b;
    L_0x0219:
        r1 = "F3215";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0221:
        r1 = 33;
        goto L_0x059b;
    L_0x0225:
        r1 = "F3213";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x022d:
        r1 = 32;
        goto L_0x059b;
    L_0x0231:
        r1 = "F3211";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0239:
        r1 = 31;
        goto L_0x059b;
    L_0x023d:
        r1 = "F3116";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0245:
        r1 = 30;
        goto L_0x059b;
    L_0x0249:
        r1 = "F3113";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0251:
        r1 = 29;
        goto L_0x059b;
    L_0x0255:
        r1 = "F3111";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x025d:
        r1 = 28;
        goto L_0x059b;
    L_0x0261:
        r1 = "E5643";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0269:
        r1 = 22;
        goto L_0x059b;
    L_0x026d:
        r1 = "A1601";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0275:
        r1 = 4;
        goto L_0x059b;
    L_0x0278:
        r1 = "Aura_Note_2";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0280:
        r1 = 11;
        goto L_0x059b;
    L_0x0284:
        r1 = "MEIZU_M5";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x028c:
        r1 = 63;
        goto L_0x059b;
    L_0x0290:
        r1 = "p212";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0298:
        r1 = 72;
        goto L_0x059b;
    L_0x029c:
        r1 = "mido";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x02a4:
        r1 = 65;
        goto L_0x059b;
    L_0x02a8:
        r1 = "XE2X";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x02b0:
        r1 = 114; // 0x72 float:1.6E-43 double:5.63E-322;
        goto L_0x059b;
    L_0x02b4:
        r1 = "Q427";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x02bc:
        r1 = 90;
        goto L_0x059b;
    L_0x02c0:
        r1 = "Q350";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x02c8:
        r1 = 88;
        goto L_0x059b;
    L_0x02cc:
        r1 = "P681";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x02d4:
        r1 = 73;
        goto L_0x059b;
    L_0x02d8:
        r1 = "1714";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x02e0:
        r1 = 2;
        goto L_0x059b;
    L_0x02e3:
        r1 = "1713";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x02eb:
        r1 = 1;
        goto L_0x059b;
    L_0x02ee:
        r1 = "1601";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x02f6:
        r1 = 0;
        goto L_0x059b;
    L_0x02f9:
        r1 = "flo";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0301:
        r1 = 35;
        goto L_0x059b;
    L_0x0305:
        r1 = "deb";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x030d:
        r1 = 21;
        goto L_0x059b;
    L_0x0311:
        r1 = "cv3";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0319:
        r1 = 20;
        goto L_0x059b;
    L_0x031d:
        r1 = "cv1";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0325:
        r1 = 19;
        goto L_0x059b;
    L_0x0329:
        r1 = "Z80";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0331:
        r1 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
        goto L_0x059b;
    L_0x0335:
        r1 = "QX1";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x033d:
        r1 = 94;
        goto L_0x059b;
    L_0x0341:
        r1 = "PLE";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0349:
        r1 = 86;
        goto L_0x059b;
    L_0x034d:
        r1 = "P85";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0355:
        r1 = 74;
        goto L_0x059b;
    L_0x0359:
        r1 = "MX6";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0361:
        r1 = 66;
        goto L_0x059b;
    L_0x0365:
        r1 = "M5c";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x036d:
        r1 = 60;
        goto L_0x059b;
    L_0x0371:
        r1 = "JGZ";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0379:
        r1 = 56;
        goto L_0x059b;
    L_0x037d:
        r1 = "mh";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0385:
        r1 = 64;
        goto L_0x059b;
    L_0x0389:
        r1 = "V5";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0391:
        r1 = 107; // 0x6b float:1.5E-43 double:5.3E-322;
        goto L_0x059b;
    L_0x0395:
        r1 = "V1";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x039d:
        r1 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
        goto L_0x059b;
    L_0x03a1:
        r1 = "Q5";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x03a9:
        r1 = 92;
        goto L_0x059b;
    L_0x03ad:
        r1 = "C1";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x03b5:
        r1 = 14;
        goto L_0x059b;
    L_0x03b9:
        r1 = "woods_fn";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x03c1:
        r1 = 112; // 0x70 float:1.57E-43 double:5.53E-322;
        goto L_0x059b;
    L_0x03c5:
        r1 = "ELUGA_A3_Pro";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x03cd:
        r1 = 23;
        goto L_0x059b;
    L_0x03d1:
        r1 = "Z12_PRO";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x03d9:
        r1 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
        goto L_0x059b;
    L_0x03dd:
        r1 = "BLACK-1X";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x03e5:
        r1 = 12;
        goto L_0x059b;
    L_0x03e9:
        r1 = "taido_row";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x03f1:
        r1 = 99;
        goto L_0x059b;
    L_0x03f5:
        r1 = "Pixi4-7_3G";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x03fd:
        r1 = 84;
        goto L_0x059b;
    L_0x0401:
        r1 = "GIONEE_GBL7360";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0409:
        r1 = 38;
        goto L_0x059b;
    L_0x040d:
        r1 = "GiONEE_CBL7513";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0415:
        r1 = 36;
        goto L_0x059b;
    L_0x0419:
        r1 = "OnePlus5T";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0421:
        r1 = 71;
        goto L_0x059b;
    L_0x0425:
        r1 = "whyred";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x042d:
        r1 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        goto L_0x059b;
    L_0x0431:
        r1 = "watson";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0439:
        r1 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        goto L_0x059b;
    L_0x043d:
        r1 = "SVP-DTV15";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0445:
        r1 = 97;
        goto L_0x059b;
    L_0x0449:
        r1 = "A7000-a";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0451:
        r1 = 6;
        goto L_0x059b;
    L_0x0454:
        r1 = "nicklaus_f";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x045c:
        r1 = 68;
        goto L_0x059b;
    L_0x0460:
        r1 = "tcl_eu";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0468:
        r1 = 104; // 0x68 float:1.46E-43 double:5.14E-322;
        goto L_0x059b;
    L_0x046c:
        r1 = "ELUGA_Ray_X";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0474:
        r1 = 26;
        goto L_0x059b;
    L_0x0478:
        r1 = "s905x018";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0480:
        r1 = 98;
        goto L_0x059b;
    L_0x0484:
        r1 = "A10-70F";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x048c:
        r1 = 3;
        goto L_0x059b;
    L_0x048f:
        r1 = "namath";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0497:
        r1 = 67;
        goto L_0x059b;
    L_0x049b:
        r1 = "Slate_Pro";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x04a3:
        r1 = 96;
        goto L_0x059b;
    L_0x04a7:
        r1 = "iris60";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x04af:
        r1 = 53;
        goto L_0x059b;
    L_0x04b3:
        r1 = "BRAVIA_ATV2";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x04bb:
        r1 = 13;
        goto L_0x059b;
    L_0x04bf:
        r1 = "GiONEE_GBL7319";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x04c7:
        r1 = 37;
        goto L_0x059b;
    L_0x04cb:
        r1 = "panell_dt";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x04d3:
        r1 = 78;
        goto L_0x059b;
    L_0x04d7:
        r1 = "panell_ds";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x04df:
        r1 = 77;
        goto L_0x059b;
    L_0x04e3:
        r1 = "panell_dl";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x04eb:
        r1 = 76;
        goto L_0x059b;
    L_0x04ef:
        r1 = "vernee_M5";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x04f7:
        r1 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        goto L_0x059b;
    L_0x04fb:
        r1 = "Phantom6";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0503:
        r1 = 83;
        goto L_0x059b;
    L_0x0507:
        r1 = "ComioS1";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x050f:
        r1 = 15;
        goto L_0x059b;
    L_0x0513:
        r1 = "XT1663";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x051b:
        r1 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        goto L_0x059b;
    L_0x051f:
        r1 = "AquaPowerM";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0527:
        r1 = 10;
        goto L_0x059b;
    L_0x052b:
        r1 = "PGN611";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0533:
        r1 = 82;
        goto L_0x059b;
    L_0x0537:
        r1 = "PGN610";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x053f:
        r1 = 81;
        goto L_0x059b;
    L_0x0542:
        r1 = "PGN528";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x054a:
        r1 = 80;
        goto L_0x059b;
    L_0x054d:
        r1 = "NX573J";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0555:
        r1 = 70;
        goto L_0x059b;
    L_0x0558:
        r1 = "NX541J";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0560:
        r1 = 69;
        goto L_0x059b;
    L_0x0563:
        r1 = "CP8676_I02";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x056b:
        r1 = 16;
        goto L_0x059b;
    L_0x056e:
        r1 = "K50a40";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0576:
        r1 = 57;
        goto L_0x059b;
    L_0x0579:
        r1 = "GIONEE_SWW1631";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0581:
        r1 = 41;
        goto L_0x059b;
    L_0x0584:
        r1 = "GIONEE_SWW1627";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x058c:
        r1 = 40;
        goto L_0x059b;
    L_0x058f:
        r1 = "GIONEE_SWW1609";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x059a;
    L_0x0597:
        r1 = 39;
        goto L_0x059b;
    L_0x059a:
        r1 = -1;
    L_0x059b:
        switch(r1) {
            case 0: goto L_0x059f;
            case 1: goto L_0x059f;
            case 2: goto L_0x059f;
            case 3: goto L_0x059f;
            case 4: goto L_0x059f;
            case 5: goto L_0x059f;
            case 6: goto L_0x059f;
            case 7: goto L_0x059f;
            case 8: goto L_0x059f;
            case 9: goto L_0x059f;
            case 10: goto L_0x059f;
            case 11: goto L_0x059f;
            case 12: goto L_0x059f;
            case 13: goto L_0x059f;
            case 14: goto L_0x059f;
            case 15: goto L_0x059f;
            case 16: goto L_0x059f;
            case 17: goto L_0x059f;
            case 18: goto L_0x059f;
            case 19: goto L_0x059f;
            case 20: goto L_0x059f;
            case 21: goto L_0x059f;
            case 22: goto L_0x059f;
            case 23: goto L_0x059f;
            case 24: goto L_0x059f;
            case 25: goto L_0x059f;
            case 26: goto L_0x059f;
            case 27: goto L_0x059f;
            case 28: goto L_0x059f;
            case 29: goto L_0x059f;
            case 30: goto L_0x059f;
            case 31: goto L_0x059f;
            case 32: goto L_0x059f;
            case 33: goto L_0x059f;
            case 34: goto L_0x059f;
            case 35: goto L_0x059f;
            case 36: goto L_0x059f;
            case 37: goto L_0x059f;
            case 38: goto L_0x059f;
            case 39: goto L_0x059f;
            case 40: goto L_0x059f;
            case 41: goto L_0x059f;
            case 42: goto L_0x059f;
            case 43: goto L_0x059f;
            case 44: goto L_0x059f;
            case 45: goto L_0x059f;
            case 46: goto L_0x059f;
            case 47: goto L_0x059f;
            case 48: goto L_0x059f;
            case 49: goto L_0x059f;
            case 50: goto L_0x059f;
            case 51: goto L_0x059f;
            case 52: goto L_0x059f;
            case 53: goto L_0x059f;
            case 54: goto L_0x059f;
            case 55: goto L_0x059f;
            case 56: goto L_0x059f;
            case 57: goto L_0x059f;
            case 58: goto L_0x059f;
            case 59: goto L_0x059f;
            case 60: goto L_0x059f;
            case 61: goto L_0x059f;
            case 62: goto L_0x059f;
            case 63: goto L_0x059f;
            case 64: goto L_0x059f;
            case 65: goto L_0x059f;
            case 66: goto L_0x059f;
            case 67: goto L_0x059f;
            case 68: goto L_0x059f;
            case 69: goto L_0x059f;
            case 70: goto L_0x059f;
            case 71: goto L_0x059f;
            case 72: goto L_0x059f;
            case 73: goto L_0x059f;
            case 74: goto L_0x059f;
            case 75: goto L_0x059f;
            case 76: goto L_0x059f;
            case 77: goto L_0x059f;
            case 78: goto L_0x059f;
            case 79: goto L_0x059f;
            case 80: goto L_0x059f;
            case 81: goto L_0x059f;
            case 82: goto L_0x059f;
            case 83: goto L_0x059f;
            case 84: goto L_0x059f;
            case 85: goto L_0x059f;
            case 86: goto L_0x059f;
            case 87: goto L_0x059f;
            case 88: goto L_0x059f;
            case 89: goto L_0x059f;
            case 90: goto L_0x059f;
            case 91: goto L_0x059f;
            case 92: goto L_0x059f;
            case 93: goto L_0x059f;
            case 94: goto L_0x059f;
            case 95: goto L_0x059f;
            case 96: goto L_0x059f;
            case 97: goto L_0x059f;
            case 98: goto L_0x059f;
            case 99: goto L_0x059f;
            case 100: goto L_0x059f;
            case 101: goto L_0x059f;
            case 102: goto L_0x059f;
            case 103: goto L_0x059f;
            case 104: goto L_0x059f;
            case 105: goto L_0x059f;
            case 106: goto L_0x059f;
            case 107: goto L_0x059f;
            case 108: goto L_0x059f;
            case 109: goto L_0x059f;
            case 110: goto L_0x059f;
            case 111: goto L_0x059f;
            case 112: goto L_0x059f;
            case 113: goto L_0x059f;
            case 114: goto L_0x059f;
            case 115: goto L_0x059f;
            case 116: goto L_0x059f;
            case 117: goto L_0x059f;
            default: goto L_0x059e;
        };	 Catch:{ all -> 0x05d2 }
    L_0x059e:
        goto L_0x05a1;
    L_0x059f:
        deviceNeedsSetOutputSurfaceWorkaround = r5;	 Catch:{ all -> 0x05d2 }
    L_0x05a1:
        r0 = com.google.android.exoplayer2.util.Util.MODEL;	 Catch:{ all -> 0x05d2 }
        r1 = r0.hashCode();	 Catch:{ all -> 0x05d2 }
        r3 = 2006354; // 0x1e9d52 float:2.811501E-39 double:9.912706E-318;
        if (r1 == r3) goto L_0x05bc;
    L_0x05ac:
        r2 = 2006367; // 0x1e9d5f float:2.811519E-39 double:9.91277E-318;
        if (r1 == r2) goto L_0x05b2;
    L_0x05b1:
        goto L_0x05c5;
    L_0x05b2:
        r1 = "AFTN";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x05c5;
    L_0x05ba:
        r2 = 1;
        goto L_0x05c6;
    L_0x05bc:
        r1 = "AFTA";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x05d2 }
        if (r0 == 0) goto L_0x05c5;
    L_0x05c4:
        goto L_0x05c6;
    L_0x05c5:
        r2 = -1;
    L_0x05c6:
        switch(r2) {
            case 0: goto L_0x05ca;
            case 1: goto L_0x05ca;
            default: goto L_0x05c9;
        };	 Catch:{ all -> 0x05d2 }
    L_0x05c9:
        goto L_0x05cc;
    L_0x05ca:
        deviceNeedsSetOutputSurfaceWorkaround = r5;	 Catch:{ all -> 0x05d2 }
    L_0x05cc:
        evaluatedDeviceNeedsSetOutputSurfaceWorkaround = r5;	 Catch:{ all -> 0x05d2 }
    L_0x05ce:
        monitor-exit(r7);	 Catch:{ all -> 0x05d2 }
        r7 = deviceNeedsSetOutputSurfaceWorkaround;
        return r7;
    L_0x05d2:
        r0 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x05d2 }
        throw r0;
    L_0x05d5:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.codecNeedsSetOutputSurfaceWorkaround(java.lang.String):boolean");
    }
}
