package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker.DefaultSeekTimestampConverter;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker.OutputFrameHolder;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSearchResult;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.IOException;

final class TsBinarySearchSeeker extends BinarySearchSeeker {
    private static final int MINIMUM_SEARCH_RANGE_BYTES = 940;
    private static final long SEEK_TOLERANCE_US = 100000;
    private static final int TIMESTAMP_SEARCH_BYTES = 37600;
    private static final int TIMESTAMP_SEARCH_PACKETS = 200;

    private static final class TsPcrSeeker implements TimestampSeeker {
        private final ParsableByteArray packetBuffer = new ParsableByteArray((int) TsBinarySearchSeeker.TIMESTAMP_SEARCH_BYTES);
        private final int pcrPid;
        private final TimestampAdjuster pcrTimestampAdjuster;

        public TsPcrSeeker(int i, TimestampAdjuster timestampAdjuster) {
            this.pcrPid = i;
            this.pcrTimestampAdjuster = timestampAdjuster;
        }

        public TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j, OutputFrameHolder outputFrameHolder) throws IOException, InterruptedException {
            long position = extractorInput.getPosition();
            outputFrameHolder = (int) Math.min(37600, extractorInput.getLength() - extractorInput.getPosition());
            this.packetBuffer.reset(outputFrameHolder);
            extractorInput.peekFully(this.packetBuffer.data, 0, outputFrameHolder);
            return searchForPcrValueInBuffer(this.packetBuffer, j, position);
        }

        private TimestampSearchResult searchForPcrValueInBuffer(ParsableByteArray parsableByteArray, long j, long j2) {
            TsPcrSeeker tsPcrSeeker = this;
            ParsableByteArray parsableByteArray2 = parsableByteArray;
            long j3 = j2;
            int limit = parsableByteArray.limit();
            long j4 = -1;
            long j5 = j4;
            long j6 = C.TIME_UNSET;
            while (parsableByteArray.bytesLeft() >= TsExtractor.TS_PACKET_SIZE) {
                int findSyncBytePosition = TsUtil.findSyncBytePosition(parsableByteArray2.data, parsableByteArray.getPosition(), limit);
                int i = findSyncBytePosition + TsExtractor.TS_PACKET_SIZE;
                if (i > limit) {
                    break;
                }
                j4 = TsUtil.readPcrFromPacket(parsableByteArray2, findSyncBytePosition, tsPcrSeeker.pcrPid);
                if (j4 != C.TIME_UNSET) {
                    j4 = tsPcrSeeker.pcrTimestampAdjuster.adjustTsTimestamp(j4);
                    if (j4 > j) {
                        if (j6 == C.TIME_UNSET) {
                            return TimestampSearchResult.overestimatedResult(j4, j3);
                        }
                        return TimestampSearchResult.targetFoundResult(j3 + j5);
                    } else if (TsBinarySearchSeeker.SEEK_TOLERANCE_US + j4 > j) {
                        return TimestampSearchResult.targetFoundResult(((long) findSyncBytePosition) + j3);
                    } else {
                        j5 = (long) findSyncBytePosition;
                        j6 = j4;
                    }
                }
                parsableByteArray2.setPosition(i);
                j4 = (long) i;
            }
            if (j6 != C.TIME_UNSET) {
                return TimestampSearchResult.underestimatedResult(j6, j3 + j4);
            }
            return TimestampSearchResult.NO_TIMESTAMP_IN_RANGE_RESULT;
        }
    }

    public TsBinarySearchSeeker(TimestampAdjuster timestampAdjuster, long j, long j2, int i) {
        long j3 = j;
        long j4 = 0;
        super(new DefaultSeekTimestampConverter(), new TsPcrSeeker(i, timestampAdjuster), j3, j4, j + 1, 0, j2, 188, MINIMUM_SEARCH_RANGE_BYTES);
    }
}
