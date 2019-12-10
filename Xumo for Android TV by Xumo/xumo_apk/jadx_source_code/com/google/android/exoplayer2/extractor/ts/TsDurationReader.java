package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.IOException;

final class TsDurationReader {
    private static final int DURATION_READ_BYTES = 37600;
    private static final int DURATION_READ_PACKETS = 200;
    private long durationUs = C.TIME_UNSET;
    private long firstPcrValue = C.TIME_UNSET;
    private boolean isDurationRead;
    private boolean isFirstPcrValueRead;
    private boolean isLastPcrValueRead;
    private long lastPcrValue = C.TIME_UNSET;
    private final ParsableByteArray packetBuffer = new ParsableByteArray((int) DURATION_READ_BYTES);
    private final TimestampAdjuster pcrTimestampAdjuster = new TimestampAdjuster(0);

    TsDurationReader() {
    }

    public boolean isDurationReadFinished() {
        return this.isDurationRead;
    }

    public int readDuration(ExtractorInput extractorInput, PositionHolder positionHolder, int i) throws IOException, InterruptedException {
        if (i <= 0) {
            return finishReadDuration(extractorInput);
        }
        if (!this.isLastPcrValueRead) {
            return readLastPcrValue(extractorInput, positionHolder, i);
        }
        if (this.lastPcrValue == C.TIME_UNSET) {
            return finishReadDuration(extractorInput);
        }
        if (!this.isFirstPcrValueRead) {
            return readFirstPcrValue(extractorInput, positionHolder, i);
        }
        if (this.firstPcrValue == -9223372036854775807) {
            return finishReadDuration(extractorInput);
        }
        this.durationUs = this.pcrTimestampAdjuster.adjustTsTimestamp(this.lastPcrValue) - this.pcrTimestampAdjuster.adjustTsTimestamp(this.firstPcrValue);
        return finishReadDuration(extractorInput);
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public TimestampAdjuster getPcrTimestampAdjuster() {
        return this.pcrTimestampAdjuster;
    }

    private int finishReadDuration(ExtractorInput extractorInput) {
        this.isDurationRead = true;
        extractorInput.resetPeekPosition();
        return null;
    }

    private int readFirstPcrValue(ExtractorInput extractorInput, PositionHolder positionHolder, int i) throws IOException, InterruptedException {
        if (extractorInput.getPosition() != 0) {
            positionHolder.position = 0;
            return 1;
        }
        positionHolder = (int) Math.min(37600, extractorInput.getLength());
        extractorInput.resetPeekPosition();
        extractorInput.peekFully(this.packetBuffer.data, 0, positionHolder);
        this.packetBuffer.setPosition(0);
        this.packetBuffer.setLimit(positionHolder);
        this.firstPcrValue = readFirstPcrValueFromBuffer(this.packetBuffer, i);
        this.isFirstPcrValueRead = true;
        return 0;
    }

    private long readFirstPcrValueFromBuffer(ParsableByteArray parsableByteArray, int i) {
        int limit = parsableByteArray.limit();
        for (int position = parsableByteArray.getPosition(); position < limit; position++) {
            if (parsableByteArray.data[position] == (byte) 71) {
                long readPcrFromPacket = TsUtil.readPcrFromPacket(parsableByteArray, position, i);
                if (readPcrFromPacket != C.TIME_UNSET) {
                    return readPcrFromPacket;
                }
            }
        }
        return C.TIME_UNSET;
    }

    private int readLastPcrValue(ExtractorInput extractorInput, PositionHolder positionHolder, int i) throws IOException, InterruptedException {
        int min = (int) Math.min(37600, extractorInput.getLength());
        long length = extractorInput.getLength() - ((long) min);
        if (extractorInput.getPosition() != length) {
            positionHolder.position = length;
            return 1;
        }
        extractorInput.resetPeekPosition();
        extractorInput.peekFully(this.packetBuffer.data, 0, min);
        this.packetBuffer.setPosition(0);
        this.packetBuffer.setLimit(min);
        this.lastPcrValue = readLastPcrValueFromBuffer(this.packetBuffer, i);
        this.isLastPcrValueRead = true;
        return 0;
    }

    private long readLastPcrValueFromBuffer(ParsableByteArray parsableByteArray, int i) {
        int position = parsableByteArray.getPosition();
        for (int limit = parsableByteArray.limit() - 1; limit >= position; limit--) {
            if (parsableByteArray.data[limit] == (byte) 71) {
                long readPcrFromPacket = TsUtil.readPcrFromPacket(parsableByteArray, limit, i);
                if (readPcrFromPacket != C.TIME_UNSET) {
                    return readPcrFromPacket;
                }
            }
        }
        return C.TIME_UNSET;
    }
}
