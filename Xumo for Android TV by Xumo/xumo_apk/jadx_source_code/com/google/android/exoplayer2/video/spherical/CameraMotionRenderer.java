package com.google.android.exoplayer2.video.spherical;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

public class CameraMotionRenderer extends BaseRenderer {
    private static final int SAMPLE_WINDOW_DURATION_US = 100000;
    private final DecoderInputBuffer buffer = new DecoderInputBuffer(1);
    private final FormatHolder formatHolder = new FormatHolder();
    private long lastTimestampUs;
    @Nullable
    private CameraMotionListener listener;
    private long offsetUs;
    private final ParsableByteArray scratch = new ParsableByteArray();

    public boolean isReady() {
        return true;
    }

    public CameraMotionRenderer() {
        super(5);
    }

    public int supportsFormat(Format format) {
        return MimeTypes.APPLICATION_CAMERA_MOTION.equals(format.sampleMimeType) != null ? 4 : null;
    }

    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        if (i == 7) {
            this.listener = (CameraMotionListener) obj;
        } else {
            super.handleMessage(i, obj);
        }
    }

    protected void onStreamChanged(Format[] formatArr, long j) throws ExoPlaybackException {
        this.offsetUs = j;
    }

    protected void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        reset();
    }

    protected void onDisabled() {
        reset();
    }

    public void render(long j, long j2) throws ExoPlaybackException {
        while (hasReadStreamToEnd() == null && this.lastTimestampUs < 100000 + j) {
            this.buffer.clear();
            if (readSource(this.formatHolder, this.buffer, false) == -4) {
                if (this.buffer.isEndOfStream() == null) {
                    this.buffer.flip();
                    this.lastTimestampUs = this.buffer.timeUs;
                    if (this.listener != null) {
                        j2 = parseMetadata(this.buffer.data);
                        if (j2 != null) {
                            ((CameraMotionListener) Util.castNonNull(this.listener)).onCameraMotion(this.lastTimestampUs - this.offsetUs, j2);
                        }
                    }
                }
            }
            return;
        }
    }

    public boolean isEnded() {
        return hasReadStreamToEnd();
    }

    @Nullable
    private float[] parseMetadata(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() != 16) {
            return null;
        }
        this.scratch.reset(byteBuffer.array(), byteBuffer.limit());
        this.scratch.setPosition(byteBuffer.arrayOffset() + 4);
        float[] fArr = new float[3];
        for (int i = 0; i < 3; i++) {
            fArr[i] = Float.intBitsToFloat(this.scratch.readLittleEndianInt());
        }
        return fArr;
    }

    private void reset() {
        this.lastTimestampUs = 0;
        if (this.listener != null) {
            this.listener.onCameraMotionReset();
        }
    }
}
