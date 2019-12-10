package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekMap.Unseekable;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

abstract class StreamReader {
    private static final int STATE_END_OF_INPUT = 3;
    private static final int STATE_READ_HEADERS = 0;
    private static final int STATE_READ_PAYLOAD = 2;
    private static final int STATE_SKIP_HEADERS = 1;
    private long currentGranule;
    private ExtractorOutput extractorOutput;
    private boolean formatSet;
    private long lengthOfReadPacket;
    private final OggPacket oggPacket = new OggPacket();
    private OggSeeker oggSeeker;
    private long payloadStartPosition;
    private int sampleRate;
    private boolean seekMapSet;
    private SetupData setupData;
    private int state;
    private long targetGranule;
    private TrackOutput trackOutput;

    static class SetupData {
        Format format;
        OggSeeker oggSeeker;

        SetupData() {
        }
    }

    private static final class UnseekableOggSeeker implements OggSeeker {
        public long read(ExtractorInput extractorInput) throws IOException, InterruptedException {
            return -1;
        }

        public long startSeek(long j) {
            return 0;
        }

        private UnseekableOggSeeker() {
        }

        public SeekMap createSeekMap() {
            return new Unseekable(C.TIME_UNSET);
        }
    }

    protected abstract long preparePayload(ParsableByteArray parsableByteArray);

    protected abstract boolean readHeaders(ParsableByteArray parsableByteArray, long j, SetupData setupData) throws IOException, InterruptedException;

    void init(ExtractorOutput extractorOutput, TrackOutput trackOutput) {
        this.extractorOutput = extractorOutput;
        this.trackOutput = trackOutput;
        reset(true);
    }

    protected void reset(boolean z) {
        if (z) {
            this.setupData = new SetupData();
            this.payloadStartPosition = 0;
            this.state = false;
        } else {
            this.state = true;
        }
        this.targetGranule = -1;
        this.currentGranule = 0;
    }

    final void seek(long j, long j2) {
        this.oggPacket.reset();
        if (j == 0) {
            reset(this.seekMapSet ^ 1);
        } else if (this.state != null) {
            this.targetGranule = this.oggSeeker.startSeek(j2);
            this.state = 2;
        }
    }

    final int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException {
        switch (this.state) {
            case 0:
                return readHeaders(extractorInput);
            case 1:
                extractorInput.skipFully((int) this.payloadStartPosition);
                this.state = 2;
                return null;
            case 2:
                return readPayload(extractorInput, positionHolder);
            default:
                throw new IllegalStateException();
        }
    }

    private int readHeaders(ExtractorInput extractorInput) throws IOException, InterruptedException {
        StreamReader streamReader = this;
        boolean z = true;
        while (z) {
            if (streamReader.oggPacket.populate(extractorInput)) {
                streamReader.lengthOfReadPacket = extractorInput.getPosition() - streamReader.payloadStartPosition;
                z = readHeaders(streamReader.oggPacket.getPayload(), streamReader.payloadStartPosition, streamReader.setupData);
                if (z) {
                    streamReader.payloadStartPosition = extractorInput.getPosition();
                }
            } else {
                streamReader.state = 3;
                return -1;
            }
        }
        ExtractorInput extractorInput2 = extractorInput;
        streamReader.sampleRate = streamReader.setupData.format.sampleRate;
        if (!streamReader.formatSet) {
            streamReader.trackOutput.format(streamReader.setupData.format);
            streamReader.formatSet = true;
        }
        if (streamReader.setupData.oggSeeker != null) {
            streamReader.oggSeeker = streamReader.setupData.oggSeeker;
        } else if (extractorInput.getLength() == -1) {
            streamReader.oggSeeker = new UnseekableOggSeeker();
        } else {
            OggPageHeader pageHeader = streamReader.oggPacket.getPageHeader();
            streamReader.oggSeeker = new DefaultOggSeeker(streamReader.payloadStartPosition, extractorInput.getLength(), this, (long) (pageHeader.headerSize + pageHeader.bodySize), pageHeader.granulePosition, (pageHeader.type & 4) != 0);
        }
        streamReader.setupData = null;
        streamReader.state = 2;
        streamReader.oggPacket.trimPayload();
        return 0;
    }

    private int readPayload(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException {
        ExtractorInput extractorInput2 = extractorInput;
        long read = this.oggSeeker.read(extractorInput2);
        if (read >= 0) {
            positionHolder.position = read;
            return 1;
        }
        if (read < -1) {
            onSeekEnd(-(read + 2));
        }
        if (!r0.seekMapSet) {
            r0.extractorOutput.seekMap(r0.oggSeeker.createSeekMap());
            r0.seekMapSet = true;
        }
        if (r0.lengthOfReadPacket <= 0) {
            if (!r0.oggPacket.populate(extractorInput2)) {
                r0.state = 3;
                return -1;
            }
        }
        r0.lengthOfReadPacket = 0;
        ParsableByteArray payload = r0.oggPacket.getPayload();
        read = preparePayload(payload);
        if (read >= 0 && r0.currentGranule + read >= r0.targetGranule) {
            long convertGranuleToTime = convertGranuleToTime(r0.currentGranule);
            r0.trackOutput.sampleData(payload, payload.limit());
            r0.trackOutput.sampleMetadata(convertGranuleToTime, 1, payload.limit(), 0, null);
            r0.targetGranule = -1;
        }
        r0.currentGranule += read;
        return 0;
    }

    protected long convertGranuleToTime(long j) {
        return (j * 1000000) / ((long) this.sampleRate);
    }

    protected long convertTimeToGranule(long j) {
        return (((long) this.sampleRate) * j) / 1000000;
    }

    protected void onSeekEnd(long j) {
        this.currentGranule = j;
    }
}
