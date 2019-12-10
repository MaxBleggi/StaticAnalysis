package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap.SeekPoints;
import com.google.android.exoplayer2.util.Util;

public class ConstantBitrateSeekMap implements SeekMap {
    private final int bitrate;
    private final long dataSize;
    private final long durationUs;
    private final long firstFrameBytePosition;
    private final int frameSize;
    private final long inputLength;

    public ConstantBitrateSeekMap(long j, long j2, int i, int i2) {
        this.inputLength = j;
        this.firstFrameBytePosition = j2;
        if (i2 == -1) {
            i2 = 1;
        }
        this.frameSize = i2;
        this.bitrate = i;
        if (j == -1) {
            this.dataSize = -1;
            this.durationUs = C.TIME_UNSET;
            return;
        }
        this.dataSize = j - j2;
        this.durationUs = getTimeUsAtPosition(j, j2, i);
    }

    public boolean isSeekable() {
        return this.dataSize != -1;
    }

    public SeekPoints getSeekPoints(long j) {
        if (this.dataSize == -1) {
            return new SeekPoints(new SeekPoint(0, this.firstFrameBytePosition));
        }
        long framePositionForTimeUs = getFramePositionForTimeUs(j);
        long timeUsAtPosition = getTimeUsAtPosition(framePositionForTimeUs);
        SeekPoint seekPoint = new SeekPoint(timeUsAtPosition, framePositionForTimeUs);
        if (timeUsAtPosition < j) {
            if (((long) this.frameSize) + framePositionForTimeUs < this.inputLength) {
                framePositionForTimeUs += (long) this.frameSize;
                return new SeekPoints(seekPoint, new SeekPoint(getTimeUsAtPosition(framePositionForTimeUs), framePositionForTimeUs));
            }
        }
        return new SeekPoints(seekPoint);
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public long getTimeUsAtPosition(long j) {
        return getTimeUsAtPosition(j, this.firstFrameBytePosition, this.bitrate);
    }

    private static long getTimeUsAtPosition(long j, long j2, int i) {
        return ((Math.max(0, j - j2) * 8) * 1000000) / ((long) i);
    }

    private long getFramePositionForTimeUs(long j) {
        return this.firstFrameBytePosition + Util.constrainValue((((j * ((long) this.bitrate)) / 8000000) / ((long) this.frameSize)) * ((long) this.frameSize), 0, this.dataSize - ((long) this.frameSize));
    }
}
