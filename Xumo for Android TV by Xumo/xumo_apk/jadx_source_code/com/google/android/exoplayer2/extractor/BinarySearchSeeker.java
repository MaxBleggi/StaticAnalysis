package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap.SeekPoints;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class BinarySearchSeeker {
    private static final long MAX_SKIP_BYTES = 262144;
    private final int minimumSearchRange;
    protected final BinarySearchSeekMap seekMap;
    @Nullable
    protected SeekOperationParams seekOperationParams;
    protected final TimestampSeeker timestampSeeker;

    public static final class OutputFrameHolder {
        public ByteBuffer byteBuffer;
        public long timeUs = 0;

        public OutputFrameHolder(ByteBuffer byteBuffer) {
            this.byteBuffer = byteBuffer;
        }
    }

    protected static class SeekOperationParams {
        private final long approxBytesPerFrame;
        private long ceilingBytePosition;
        private long ceilingTimePosition;
        private long floorBytePosition;
        private long floorTimePosition;
        private long nextSearchBytePosition;
        private final long seekTimeUs;
        private final long targetTimePosition;

        protected static long calculateNextSearchBytePosition(long j, long j2, long j3, long j4, long j5, long j6) {
            if (j4 + 1 < j5) {
                if (j2 + 1 < j3) {
                    long j7 = (long) (((float) (j - j2)) * (((float) (j5 - j4)) / ((float) (j3 - j2))));
                    return Util.constrainValue(((j4 + j7) - j6) - (j7 / 20), j4, j5 - 1);
                }
            }
            return j4;
        }

        protected SeekOperationParams(long j, long j2, long j3, long j4, long j5, long j6, long j7) {
            this.seekTimeUs = j;
            this.targetTimePosition = j2;
            this.floorTimePosition = j3;
            this.ceilingTimePosition = j4;
            this.floorBytePosition = j5;
            this.ceilingBytePosition = j6;
            this.approxBytesPerFrame = j7;
            this.nextSearchBytePosition = calculateNextSearchBytePosition(j2, j3, j4, j5, j6, j7);
        }

        private long getFloorBytePosition() {
            return this.floorBytePosition;
        }

        private long getCeilingBytePosition() {
            return this.ceilingBytePosition;
        }

        private long getTargetTimePosition() {
            return this.targetTimePosition;
        }

        private long getSeekTimeUs() {
            return this.seekTimeUs;
        }

        private void updateSeekFloor(long j, long j2) {
            this.floorTimePosition = j;
            this.floorBytePosition = j2;
            updateNextSearchBytePosition();
        }

        private void updateSeekCeiling(long j, long j2) {
            this.ceilingTimePosition = j;
            this.ceilingBytePosition = j2;
            updateNextSearchBytePosition();
        }

        private long getNextSearchBytePosition() {
            return this.nextSearchBytePosition;
        }

        private void updateNextSearchBytePosition() {
            this.nextSearchBytePosition = calculateNextSearchBytePosition(this.targetTimePosition, this.floorTimePosition, this.ceilingTimePosition, this.floorBytePosition, this.ceilingBytePosition, this.approxBytesPerFrame);
        }
    }

    protected interface SeekTimestampConverter {
        long timeUsToTargetTime(long j);
    }

    public static final class TimestampSearchResult {
        public static final TimestampSearchResult NO_TIMESTAMP_IN_RANGE_RESULT = new TimestampSearchResult(-3, C.TIME_UNSET, -1);
        public static final int RESULT_NO_TIMESTAMP = -3;
        public static final int RESULT_POSITION_OVERESTIMATED = -1;
        public static final int RESULT_POSITION_UNDERESTIMATED = -2;
        public static final int RESULT_TARGET_TIMESTAMP_FOUND = 0;
        private final long bytePositionToUpdate;
        private final int result;
        private final long timestampToUpdate;

        private TimestampSearchResult(int i, long j, long j2) {
            this.result = i;
            this.timestampToUpdate = j;
            this.bytePositionToUpdate = j2;
        }

        public static TimestampSearchResult overestimatedResult(long j, long j2) {
            return new TimestampSearchResult(-1, j, j2);
        }

        public static TimestampSearchResult underestimatedResult(long j, long j2) {
            return new TimestampSearchResult(-2, j, j2);
        }

        public static TimestampSearchResult targetFoundResult(long j) {
            return new TimestampSearchResult(0, C.TIME_UNSET, j);
        }
    }

    protected interface TimestampSeeker {
        TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j, OutputFrameHolder outputFrameHolder) throws IOException, InterruptedException;
    }

    public static class BinarySearchSeekMap implements SeekMap {
        private final long approxBytesPerFrame;
        private final long ceilingBytePosition;
        private final long ceilingTimePosition;
        private final long durationUs;
        private final long floorBytePosition;
        private final long floorTimePosition;
        private final SeekTimestampConverter seekTimestampConverter;

        public boolean isSeekable() {
            return true;
        }

        public BinarySearchSeekMap(SeekTimestampConverter seekTimestampConverter, long j, long j2, long j3, long j4, long j5, long j6) {
            this.seekTimestampConverter = seekTimestampConverter;
            this.durationUs = j;
            this.floorTimePosition = j2;
            this.ceilingTimePosition = j3;
            this.floorBytePosition = j4;
            this.ceilingBytePosition = j5;
            this.approxBytesPerFrame = j6;
        }

        public SeekPoints getSeekPoints(long j) {
            return new SeekPoints(new SeekPoint(j, SeekOperationParams.calculateNextSearchBytePosition(this.seekTimestampConverter.timeUsToTargetTime(j), this.floorTimePosition, this.ceilingTimePosition, this.floorBytePosition, this.ceilingBytePosition, this.approxBytesPerFrame)));
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long timeUsToTargetTime(long j) {
            return this.seekTimestampConverter.timeUsToTargetTime(j);
        }
    }

    public static final class DefaultSeekTimestampConverter implements SeekTimestampConverter {
        public long timeUsToTargetTime(long j) {
            return j;
        }
    }

    protected void onSeekOperationFinished(boolean z, long j) {
    }

    protected BinarySearchSeeker(SeekTimestampConverter seekTimestampConverter, TimestampSeeker timestampSeeker, long j, long j2, long j3, long j4, long j5, long j6, int i) {
        this.timestampSeeker = timestampSeeker;
        this.minimumSearchRange = i;
        this.seekMap = new BinarySearchSeekMap(seekTimestampConverter, j, j2, j3, j4, j5, j6);
    }

    public final SeekMap getSeekMap() {
        return this.seekMap;
    }

    public final void setSeekTargetUs(long j) {
        if (this.seekOperationParams == null || this.seekOperationParams.getSeekTimeUs() != j) {
            this.seekOperationParams = createSeekParamsForTargetTimeUs(j);
        }
    }

    public final boolean isSeeking() {
        return this.seekOperationParams != null;
    }

    public int handlePendingSeek(ExtractorInput extractorInput, PositionHolder positionHolder, OutputFrameHolder outputFrameHolder) throws InterruptedException, IOException {
        TimestampSeeker timestampSeeker = (TimestampSeeker) Assertions.checkNotNull(this.timestampSeeker);
        while (true) {
            SeekOperationParams seekOperationParams = (SeekOperationParams) Assertions.checkNotNull(this.seekOperationParams);
            long access$100 = seekOperationParams.getFloorBytePosition();
            long access$200 = seekOperationParams.getCeilingBytePosition();
            long access$300 = seekOperationParams.getNextSearchBytePosition();
            if (access$200 - access$100 <= ((long) this.minimumSearchRange)) {
                markSeekOperationFinished(false, access$100);
                return seekToPosition(extractorInput, access$100, positionHolder);
            } else if (!skipInputUntilPosition(extractorInput, access$300)) {
                return seekToPosition(extractorInput, access$300, positionHolder);
            } else {
                extractorInput.resetPeekPosition();
                TimestampSearchResult searchForTimestamp = timestampSeeker.searchForTimestamp(extractorInput, seekOperationParams.getTargetTimePosition(), outputFrameHolder);
                switch (searchForTimestamp.result) {
                    case -3:
                        markSeekOperationFinished(false, access$300);
                        return seekToPosition(extractorInput, access$300, positionHolder);
                    case -2:
                        seekOperationParams.updateSeekFloor(searchForTimestamp.timestampToUpdate, searchForTimestamp.bytePositionToUpdate);
                        break;
                    case -1:
                        seekOperationParams.updateSeekCeiling(searchForTimestamp.timestampToUpdate, searchForTimestamp.bytePositionToUpdate);
                        break;
                    case 0:
                        markSeekOperationFinished(true, searchForTimestamp.bytePositionToUpdate);
                        skipInputUntilPosition(extractorInput, searchForTimestamp.bytePositionToUpdate);
                        return seekToPosition(extractorInput, searchForTimestamp.bytePositionToUpdate, positionHolder);
                    default:
                        throw new IllegalStateException("Invalid case");
                }
            }
        }
    }

    protected SeekOperationParams createSeekParamsForTargetTimeUs(long j) {
        long j2 = j;
        return new SeekOperationParams(j2, this.seekMap.timeUsToTargetTime(j2), this.seekMap.floorTimePosition, this.seekMap.ceilingTimePosition, this.seekMap.floorBytePosition, this.seekMap.ceilingBytePosition, this.seekMap.approxBytesPerFrame);
    }

    protected final void markSeekOperationFinished(boolean z, long j) {
        this.seekOperationParams = null;
        onSeekOperationFinished(z, j);
    }

    protected final boolean skipInputUntilPosition(ExtractorInput extractorInput, long j) throws IOException, InterruptedException {
        j -= extractorInput.getPosition();
        if (j < 0 || j > 262144) {
            return null;
        }
        extractorInput.skipFully((int) j);
        return true;
    }

    protected final int seekToPosition(ExtractorInput extractorInput, long j, PositionHolder positionHolder) {
        if (j == extractorInput.getPosition()) {
            return null;
        }
        positionHolder.position = j;
        return 1;
    }
}
