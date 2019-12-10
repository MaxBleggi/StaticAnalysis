package com.google.android.exoplayer2.audio;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioProcessor.UnhandledFormatException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public final class SonicAudioProcessor implements AudioProcessor {
    private static final float CLOSE_THRESHOLD = 0.01f;
    public static final float MAXIMUM_PITCH = 8.0f;
    public static final float MAXIMUM_SPEED = 8.0f;
    public static final float MINIMUM_PITCH = 0.1f;
    public static final float MINIMUM_SPEED = 0.1f;
    private static final int MIN_BYTES_FOR_SPEEDUP_CALCULATION = 1024;
    public static final int SAMPLE_RATE_NO_CHANGE = -1;
    private ByteBuffer buffer = EMPTY_BUFFER;
    private int channelCount = -1;
    private long inputBytes;
    private boolean inputEnded;
    private ByteBuffer outputBuffer = EMPTY_BUFFER;
    private long outputBytes;
    private int outputSampleRateHz = -1;
    private int pendingOutputSampleRateHz = -1;
    private float pitch = 1.0f;
    private int sampleRateHz = -1;
    private ShortBuffer shortBuffer = this.buffer.asShortBuffer();
    @Nullable
    private Sonic sonic;
    private float speed = 1.0f;

    public int getOutputEncoding() {
        return 2;
    }

    public float setSpeed(float f) {
        f = Util.constrainValue(f, 0.1f, 8.0f);
        if (this.speed != f) {
            this.speed = f;
            this.sonic = null;
        }
        flush();
        return f;
    }

    public float setPitch(float f) {
        f = Util.constrainValue(f, 0.1f, 8.0f);
        if (this.pitch != f) {
            this.pitch = f;
            this.sonic = null;
        }
        flush();
        return f;
    }

    public void setOutputSampleRateHz(int i) {
        this.pendingOutputSampleRateHz = i;
    }

    public long scaleDurationForSpeedup(long j) {
        if (this.outputBytes >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            if (this.outputSampleRateHz == this.sampleRateHz) {
                j = Util.scaleLargeTimestamp(j, this.inputBytes, this.outputBytes);
            } else {
                j = Util.scaleLargeTimestamp(j, this.inputBytes * ((long) this.outputSampleRateHz), this.outputBytes * ((long) this.sampleRateHz));
            }
            return j;
        }
        double d = (double) this.speed;
        j = (double) j;
        Double.isNaN(d);
        Double.isNaN(j);
        return (long) (d * j);
    }

    public boolean configure(int i, int i2, int i3) throws UnhandledFormatException {
        if (i3 == 2) {
            i3 = this.pendingOutputSampleRateHz == -1 ? i : this.pendingOutputSampleRateHz;
            if (this.sampleRateHz == i && this.channelCount == i2 && this.outputSampleRateHz == i3) {
                return false;
            }
            this.sampleRateHz = i;
            this.channelCount = i2;
            this.outputSampleRateHz = i3;
            this.sonic = 0;
            return true;
        }
        throw new UnhandledFormatException(i, i2, i3);
    }

    public boolean isActive() {
        return this.sampleRateHz != -1 && (Math.abs(this.speed - 1.0f) >= CLOSE_THRESHOLD || Math.abs(this.pitch - 1.0f) >= CLOSE_THRESHOLD || this.outputSampleRateHz != this.sampleRateHz);
    }

    public int getOutputChannelCount() {
        return this.channelCount;
    }

    public int getOutputSampleRateHz() {
        return this.outputSampleRateHz;
    }

    public void queueInput(ByteBuffer byteBuffer) {
        Assertions.checkState(this.sonic != null);
        if (byteBuffer.hasRemaining()) {
            ShortBuffer asShortBuffer = byteBuffer.asShortBuffer();
            int remaining = byteBuffer.remaining();
            this.inputBytes += (long) remaining;
            this.sonic.queueInput(asShortBuffer);
            byteBuffer.position(byteBuffer.position() + remaining);
        }
        byteBuffer = (this.sonic.getFramesAvailable() * this.channelCount) * 2;
        if (byteBuffer > null) {
            if (this.buffer.capacity() < byteBuffer) {
                this.buffer = ByteBuffer.allocateDirect(byteBuffer).order(ByteOrder.nativeOrder());
                this.shortBuffer = this.buffer.asShortBuffer();
            } else {
                this.buffer.clear();
                this.shortBuffer.clear();
            }
            this.sonic.getOutput(this.shortBuffer);
            this.outputBytes += (long) byteBuffer;
            this.buffer.limit(byteBuffer);
            this.outputBuffer = this.buffer;
        }
    }

    public void queueEndOfStream() {
        Assertions.checkState(this.sonic != null);
        this.sonic.queueEndOfStream();
        this.inputEnded = true;
    }

    public ByteBuffer getOutput() {
        ByteBuffer byteBuffer = this.outputBuffer;
        this.outputBuffer = EMPTY_BUFFER;
        return byteBuffer;
    }

    public boolean isEnded() {
        return this.inputEnded && (this.sonic == null || this.sonic.getFramesAvailable() == 0);
    }

    public void flush() {
        if (isActive()) {
            if (this.sonic == null) {
                this.sonic = new Sonic(this.sampleRateHz, this.channelCount, this.speed, this.pitch, this.outputSampleRateHz);
            } else {
                this.sonic.flush();
            }
        }
        this.outputBuffer = EMPTY_BUFFER;
        this.inputBytes = 0;
        this.outputBytes = 0;
        this.inputEnded = false;
    }

    public void reset() {
        this.speed = 1.0f;
        this.pitch = 1.0f;
        this.channelCount = -1;
        this.sampleRateHz = -1;
        this.outputSampleRateHz = -1;
        this.buffer = EMPTY_BUFFER;
        this.shortBuffer = this.buffer.asShortBuffer();
        this.outputBuffer = EMPTY_BUFFER;
        this.pendingOutputSampleRateHz = -1;
        this.sonic = null;
        this.inputBytes = 0;
        this.outputBytes = 0;
        this.inputEnded = false;
    }
}
