package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap.Unseekable;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader.DvbSubtitleInfo;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader.EsInfo;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader.Factory;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader.TrackIdGenerator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class TsExtractor implements Extractor {
    private static final long AC3_FORMAT_IDENTIFIER = ((long) Util.getIntegerCodeForString("AC-3"));
    private static final int BUFFER_SIZE = 9400;
    private static final long E_AC3_FORMAT_IDENTIFIER = ((long) Util.getIntegerCodeForString("EAC3"));
    public static final ExtractorsFactory FACTORY = -$$Lambda$TsExtractor$f-UE6PC86cqq4V-qVoFQnPhfFZ8.INSTANCE;
    private static final long HEVC_FORMAT_IDENTIFIER = ((long) Util.getIntegerCodeForString("HEVC"));
    private static final int MAX_PID_PLUS_ONE = 8192;
    public static final int MODE_HLS = 2;
    public static final int MODE_MULTI_PMT = 0;
    public static final int MODE_SINGLE_PMT = 1;
    private static final int SNIFF_TS_PACKET_COUNT = 5;
    public static final int TS_PACKET_SIZE = 188;
    private static final int TS_PAT_PID = 0;
    public static final int TS_STREAM_TYPE_AAC_ADTS = 15;
    public static final int TS_STREAM_TYPE_AAC_LATM = 17;
    public static final int TS_STREAM_TYPE_AC3 = 129;
    public static final int TS_STREAM_TYPE_DTS = 138;
    public static final int TS_STREAM_TYPE_DVBSUBS = 89;
    public static final int TS_STREAM_TYPE_E_AC3 = 135;
    public static final int TS_STREAM_TYPE_H262 = 2;
    public static final int TS_STREAM_TYPE_H264 = 27;
    public static final int TS_STREAM_TYPE_H265 = 36;
    public static final int TS_STREAM_TYPE_HDMV_DTS = 130;
    public static final int TS_STREAM_TYPE_ID3 = 21;
    public static final int TS_STREAM_TYPE_MPA = 3;
    public static final int TS_STREAM_TYPE_MPA_LSF = 4;
    public static final int TS_STREAM_TYPE_SPLICE_INFO = 134;
    public static final int TS_SYNC_BYTE = 71;
    private int bytesSinceLastSync;
    private final SparseIntArray continuityCounters;
    private final TsDurationReader durationReader;
    private boolean hasOutputSeekMap;
    private TsPayloadReader id3Reader;
    private final int mode;
    private ExtractorOutput output;
    private final Factory payloadReaderFactory;
    private int pcrPid;
    private boolean pendingSeekToStart;
    private int remainingPmts;
    private final List<TimestampAdjuster> timestampAdjusters;
    private final SparseBooleanArray trackIds;
    private final SparseBooleanArray trackPids;
    private boolean tracksEnded;
    private TsBinarySearchSeeker tsBinarySearchSeeker;
    private final ParsableByteArray tsPacketBuffer;
    private final SparseArray<TsPayloadReader> tsPayloadReaders;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    private class PatReader implements SectionPayloadReader {
        private final ParsableBitArray patScratch = new ParsableBitArray(new byte[4]);

        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        }

        public void consume(ParsableByteArray parsableByteArray) {
            if (parsableByteArray.readUnsignedByte() == 0) {
                parsableByteArray.skipBytes(7);
                int bytesLeft = parsableByteArray.bytesLeft() / 4;
                for (int i = 0; i < bytesLeft; i++) {
                    parsableByteArray.readBytes(this.patScratch, 4);
                    int readBits = this.patScratch.readBits(16);
                    this.patScratch.skipBits(3);
                    if (readBits == 0) {
                        this.patScratch.skipBits(13);
                    } else {
                        readBits = this.patScratch.readBits(13);
                        TsExtractor.this.tsPayloadReaders.put(readBits, new SectionReader(new PmtReader(readBits)));
                        TsExtractor.this.remainingPmts = TsExtractor.this.remainingPmts + 1;
                    }
                }
                if (TsExtractor.this.mode != 2) {
                    TsExtractor.this.tsPayloadReaders.remove(0);
                }
            }
        }
    }

    private class PmtReader implements SectionPayloadReader {
        private static final int TS_PMT_DESC_AC3 = 106;
        private static final int TS_PMT_DESC_DTS = 123;
        private static final int TS_PMT_DESC_DVBSUBS = 89;
        private static final int TS_PMT_DESC_EAC3 = 122;
        private static final int TS_PMT_DESC_ISO639_LANG = 10;
        private static final int TS_PMT_DESC_REGISTRATION = 5;
        private final int pid;
        private final ParsableBitArray pmtScratch = new ParsableBitArray(new byte[5]);
        private final SparseIntArray trackIdToPidScratch = new SparseIntArray();
        private final SparseArray<TsPayloadReader> trackIdToReaderScratch = new SparseArray();

        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        }

        public PmtReader(int i) {
            this.pid = i;
        }

        public void consume(ParsableByteArray parsableByteArray) {
            PmtReader pmtReader = this;
            ParsableByteArray parsableByteArray2 = parsableByteArray;
            if (parsableByteArray.readUnsignedByte() == 2) {
                TimestampAdjuster timestampAdjuster;
                int readUnsignedShort;
                int i;
                int i2;
                int i3;
                int bytesLeft;
                int readBits;
                int readBits2;
                EsInfo readEsInfo;
                Object createPayloadReader;
                int size;
                TsPayloadReader tsPayloadReader;
                TsExtractor tsExtractor;
                int i4 = 0;
                if (!(TsExtractor.this.mode == 1 || TsExtractor.this.mode == 2)) {
                    if (TsExtractor.this.remainingPmts != 1) {
                        timestampAdjuster = new TimestampAdjuster(((TimestampAdjuster) TsExtractor.this.timestampAdjusters.get(0)).getFirstSampleTimestampUs());
                        TsExtractor.this.timestampAdjusters.add(timestampAdjuster);
                        parsableByteArray2.skipBytes(2);
                        readUnsignedShort = parsableByteArray.readUnsignedShort();
                        i = 3;
                        parsableByteArray2.skipBytes(3);
                        parsableByteArray2.readBytes(pmtReader.pmtScratch, 2);
                        pmtReader.pmtScratch.skipBits(3);
                        i2 = 13;
                        TsExtractor.this.pcrPid = pmtReader.pmtScratch.readBits(13);
                        parsableByteArray2.readBytes(pmtReader.pmtScratch, 2);
                        i3 = 4;
                        pmtReader.pmtScratch.skipBits(4);
                        parsableByteArray2.skipBytes(pmtReader.pmtScratch.readBits(12));
                        if (TsExtractor.this.mode == 2 && TsExtractor.this.id3Reader == null) {
                            TsExtractor.this.id3Reader = TsExtractor.this.payloadReaderFactory.createPayloadReader(21, new EsInfo(21, null, null, Util.EMPTY_BYTE_ARRAY));
                            TsExtractor.this.id3Reader.init(timestampAdjuster, TsExtractor.this.output, new TrackIdGenerator(readUnsignedShort, 21, 8192));
                        }
                        pmtReader.trackIdToReaderScratch.clear();
                        pmtReader.trackIdToPidScratch.clear();
                        bytesLeft = parsableByteArray.bytesLeft();
                        while (bytesLeft > 0) {
                            parsableByteArray2.readBytes(pmtReader.pmtScratch, 5);
                            readBits = pmtReader.pmtScratch.readBits(8);
                            pmtReader.pmtScratch.skipBits(i);
                            readBits2 = pmtReader.pmtScratch.readBits(i2);
                            pmtReader.pmtScratch.skipBits(i3);
                            i = pmtReader.pmtScratch.readBits(12);
                            readEsInfo = readEsInfo(parsableByteArray2, i);
                            if (readBits == 6) {
                                readBits = readEsInfo.streamType;
                            }
                            bytesLeft -= i + 5;
                            i = TsExtractor.this.mode != 2 ? readBits : readBits2;
                            if (TsExtractor.this.trackIds.get(i)) {
                                if (TsExtractor.this.mode == 2 || readBits != 21) {
                                    createPayloadReader = TsExtractor.this.payloadReaderFactory.createPayloadReader(readBits, readEsInfo);
                                } else {
                                    createPayloadReader = TsExtractor.this.id3Reader;
                                }
                                if (TsExtractor.this.mode == 2 || readBits2 < pmtReader.trackIdToPidScratch.get(i, 8192)) {
                                    pmtReader.trackIdToPidScratch.put(i, readBits2);
                                    pmtReader.trackIdToReaderScratch.put(i, createPayloadReader);
                                }
                            }
                            i = 3;
                            i3 = 4;
                            i2 = 13;
                        }
                        size = pmtReader.trackIdToPidScratch.size();
                        for (i = 0; i < size; i++) {
                            bytesLeft = pmtReader.trackIdToPidScratch.keyAt(i);
                            i3 = pmtReader.trackIdToPidScratch.valueAt(i);
                            TsExtractor.this.trackIds.put(bytesLeft, true);
                            TsExtractor.this.trackPids.put(i3, true);
                            tsPayloadReader = (TsPayloadReader) pmtReader.trackIdToReaderScratch.valueAt(i);
                            if (tsPayloadReader == null) {
                                if (tsPayloadReader != TsExtractor.this.id3Reader) {
                                    tsPayloadReader.init(timestampAdjuster, TsExtractor.this.output, new TrackIdGenerator(readUnsignedShort, bytesLeft, 8192));
                                }
                                TsExtractor.this.tsPayloadReaders.put(i3, tsPayloadReader);
                            }
                        }
                        if (TsExtractor.this.mode == 2) {
                            TsExtractor.this.tsPayloadReaders.remove(pmtReader.pid);
                            tsExtractor = TsExtractor.this;
                            if (TsExtractor.this.mode == 1) {
                                i4 = TsExtractor.this.remainingPmts - 1;
                            }
                            tsExtractor.remainingPmts = i4;
                            if (TsExtractor.this.remainingPmts == 0) {
                                TsExtractor.this.output.endTracks();
                                TsExtractor.this.tracksEnded = true;
                            }
                        } else if (!TsExtractor.this.tracksEnded) {
                            TsExtractor.this.output.endTracks();
                            TsExtractor.this.remainingPmts = 0;
                            TsExtractor.this.tracksEnded = true;
                        }
                    }
                }
                timestampAdjuster = (TimestampAdjuster) TsExtractor.this.timestampAdjusters.get(0);
                parsableByteArray2.skipBytes(2);
                readUnsignedShort = parsableByteArray.readUnsignedShort();
                i = 3;
                parsableByteArray2.skipBytes(3);
                parsableByteArray2.readBytes(pmtReader.pmtScratch, 2);
                pmtReader.pmtScratch.skipBits(3);
                i2 = 13;
                TsExtractor.this.pcrPid = pmtReader.pmtScratch.readBits(13);
                parsableByteArray2.readBytes(pmtReader.pmtScratch, 2);
                i3 = 4;
                pmtReader.pmtScratch.skipBits(4);
                parsableByteArray2.skipBytes(pmtReader.pmtScratch.readBits(12));
                TsExtractor.this.id3Reader = TsExtractor.this.payloadReaderFactory.createPayloadReader(21, new EsInfo(21, null, null, Util.EMPTY_BYTE_ARRAY));
                TsExtractor.this.id3Reader.init(timestampAdjuster, TsExtractor.this.output, new TrackIdGenerator(readUnsignedShort, 21, 8192));
                pmtReader.trackIdToReaderScratch.clear();
                pmtReader.trackIdToPidScratch.clear();
                bytesLeft = parsableByteArray.bytesLeft();
                while (bytesLeft > 0) {
                    parsableByteArray2.readBytes(pmtReader.pmtScratch, 5);
                    readBits = pmtReader.pmtScratch.readBits(8);
                    pmtReader.pmtScratch.skipBits(i);
                    readBits2 = pmtReader.pmtScratch.readBits(i2);
                    pmtReader.pmtScratch.skipBits(i3);
                    i = pmtReader.pmtScratch.readBits(12);
                    readEsInfo = readEsInfo(parsableByteArray2, i);
                    if (readBits == 6) {
                        readBits = readEsInfo.streamType;
                    }
                    bytesLeft -= i + 5;
                    if (TsExtractor.this.mode != 2) {
                    }
                    if (TsExtractor.this.trackIds.get(i)) {
                        if (TsExtractor.this.mode == 2) {
                        }
                        createPayloadReader = TsExtractor.this.payloadReaderFactory.createPayloadReader(readBits, readEsInfo);
                        if (TsExtractor.this.mode == 2) {
                        }
                        pmtReader.trackIdToPidScratch.put(i, readBits2);
                        pmtReader.trackIdToReaderScratch.put(i, createPayloadReader);
                    }
                    i = 3;
                    i3 = 4;
                    i2 = 13;
                }
                size = pmtReader.trackIdToPidScratch.size();
                for (i = 0; i < size; i++) {
                    bytesLeft = pmtReader.trackIdToPidScratch.keyAt(i);
                    i3 = pmtReader.trackIdToPidScratch.valueAt(i);
                    TsExtractor.this.trackIds.put(bytesLeft, true);
                    TsExtractor.this.trackPids.put(i3, true);
                    tsPayloadReader = (TsPayloadReader) pmtReader.trackIdToReaderScratch.valueAt(i);
                    if (tsPayloadReader == null) {
                        if (tsPayloadReader != TsExtractor.this.id3Reader) {
                            tsPayloadReader.init(timestampAdjuster, TsExtractor.this.output, new TrackIdGenerator(readUnsignedShort, bytesLeft, 8192));
                        }
                        TsExtractor.this.tsPayloadReaders.put(i3, tsPayloadReader);
                    }
                }
                if (TsExtractor.this.mode == 2) {
                    TsExtractor.this.tsPayloadReaders.remove(pmtReader.pid);
                    tsExtractor = TsExtractor.this;
                    if (TsExtractor.this.mode == 1) {
                        i4 = TsExtractor.this.remainingPmts - 1;
                    }
                    tsExtractor.remainingPmts = i4;
                    if (TsExtractor.this.remainingPmts == 0) {
                        TsExtractor.this.output.endTracks();
                        TsExtractor.this.tracksEnded = true;
                    }
                } else if (TsExtractor.this.tracksEnded) {
                    TsExtractor.this.output.endTracks();
                    TsExtractor.this.remainingPmts = 0;
                    TsExtractor.this.tracksEnded = true;
                }
            }
        }

        private EsInfo readEsInfo(ParsableByteArray parsableByteArray, int i) {
            int position = parsableByteArray.getPosition();
            i += position;
            String str = null;
            int i2 = -1;
            List list = null;
            while (parsableByteArray.getPosition() < i) {
                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                int position2 = parsableByteArray.getPosition() + parsableByteArray.readUnsignedByte();
                if (readUnsignedByte == 5) {
                    long readUnsignedInt = parsableByteArray.readUnsignedInt();
                    if (readUnsignedInt != TsExtractor.AC3_FORMAT_IDENTIFIER) {
                        if (readUnsignedInt == TsExtractor.E_AC3_FORMAT_IDENTIFIER) {
                            i2 = TsExtractor.TS_STREAM_TYPE_E_AC3;
                            parsableByteArray.skipBytes(position2 - parsableByteArray.getPosition());
                        } else {
                            if (readUnsignedInt == TsExtractor.HEVC_FORMAT_IDENTIFIER) {
                                i2 = 36;
                            }
                            parsableByteArray.skipBytes(position2 - parsableByteArray.getPosition());
                        }
                    }
                } else if (readUnsignedByte != 106) {
                    if (readUnsignedByte != TS_PMT_DESC_EAC3) {
                        if (readUnsignedByte == TS_PMT_DESC_DTS) {
                            i2 = TsExtractor.TS_STREAM_TYPE_DTS;
                        } else if (readUnsignedByte == 10) {
                            str = parsableByteArray.readString(3).trim();
                        } else if (readUnsignedByte == 89) {
                            List arrayList = new ArrayList();
                            while (parsableByteArray.getPosition() < position2) {
                                String trim = parsableByteArray.readString(3).trim();
                                readUnsignedByte = parsableByteArray.readUnsignedByte();
                                byte[] bArr = new byte[4];
                                parsableByteArray.readBytes(bArr, 0, 4);
                                arrayList.add(new DvbSubtitleInfo(trim, readUnsignedByte, bArr));
                            }
                            list = arrayList;
                            i2 = 89;
                        }
                        parsableByteArray.skipBytes(position2 - parsableByteArray.getPosition());
                    }
                    i2 = TsExtractor.TS_STREAM_TYPE_E_AC3;
                    parsableByteArray.skipBytes(position2 - parsableByteArray.getPosition());
                }
                i2 = TsExtractor.TS_STREAM_TYPE_AC3;
                parsableByteArray.skipBytes(position2 - parsableByteArray.getPosition());
            }
            parsableByteArray.setPosition(i);
            return new EsInfo(i2, str, list, Arrays.copyOfRange(parsableByteArray.data, position, i));
        }
    }

    public void release() {
    }

    public TsExtractor() {
        this(0);
    }

    public TsExtractor(int i) {
        this(1, i);
    }

    public TsExtractor(int i, int i2) {
        this(i, new TimestampAdjuster(0), new DefaultTsPayloadReaderFactory(i2));
    }

    public TsExtractor(int i, TimestampAdjuster timestampAdjuster, Factory factory) {
        this.payloadReaderFactory = (Factory) Assertions.checkNotNull(factory);
        this.mode = i;
        if (i != 1) {
            if (i != 2) {
                this.timestampAdjusters = new ArrayList();
                this.timestampAdjusters.add(timestampAdjuster);
                this.tsPacketBuffer = new ParsableByteArray(new byte[BUFFER_SIZE], null);
                this.trackIds = new SparseBooleanArray();
                this.trackPids = new SparseBooleanArray();
                this.tsPayloadReaders = new SparseArray();
                this.continuityCounters = new SparseIntArray();
                this.durationReader = new TsDurationReader();
                this.pcrPid = -1;
                resetPayloadReaders();
            }
        }
        this.timestampAdjusters = Collections.singletonList(timestampAdjuster);
        this.tsPacketBuffer = new ParsableByteArray(new byte[BUFFER_SIZE], null);
        this.trackIds = new SparseBooleanArray();
        this.trackPids = new SparseBooleanArray();
        this.tsPayloadReaders = new SparseArray();
        this.continuityCounters = new SparseIntArray();
        this.durationReader = new TsDurationReader();
        this.pcrPid = -1;
        resetPayloadReaders();
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException, InterruptedException {
        byte[] bArr = this.tsPacketBuffer.data;
        extractorInput.peekFully(bArr, 0, 940);
        for (int i = 0; i < TS_PACKET_SIZE; i++) {
            Object obj;
            for (int i2 = 0; i2 < 5; i2++) {
                if (bArr[(i2 * TS_PACKET_SIZE) + i] != (byte) 71) {
                    obj = null;
                    break;
                }
            }
            obj = 1;
            if (obj != null) {
                extractorInput.skipFully(i);
                return true;
            }
        }
        return false;
    }

    public void init(ExtractorOutput extractorOutput) {
        this.output = extractorOutput;
    }

    public void seek(long j, long j2) {
        Assertions.checkState(this.mode != 2 ? 1 : null);
        j = this.timestampAdjusters.size();
        for (int i = 0; i < j; i++) {
            TimestampAdjuster timestampAdjuster = (TimestampAdjuster) this.timestampAdjusters.get(i);
            if (!((timestampAdjuster.getTimestampOffsetUs() == C.TIME_UNSET ? 1 : null) == null && (timestampAdjuster.getTimestampOffsetUs() == 0 || timestampAdjuster.getFirstSampleTimestampUs() == j2))) {
                timestampAdjuster.reset();
                timestampAdjuster.setFirstSampleTimestampUs(j2);
            }
        }
        if (!(j2 == 0 || this.tsBinarySearchSeeker == null)) {
            this.tsBinarySearchSeeker.setSeekTargetUs(j2);
        }
        this.tsPacketBuffer.reset();
        this.continuityCounters.clear();
        for (j = null; j < this.tsPayloadReaders.size(); j++) {
            ((TsPayloadReader) this.tsPayloadReaders.valueAt(j)).seek();
        }
        this.bytesSinceLastSync = 0;
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException {
        ExtractorInput extractorInput2 = extractorInput;
        PositionHolder positionHolder2 = positionHolder;
        long length = extractorInput.getLength();
        TsPayloadReader tsPayloadReader = null;
        if (this.tracksEnded) {
            Object obj = (length == -1 || r0.mode == 2) ? null : 1;
            if (obj != null && !r0.durationReader.isDurationReadFinished()) {
                return r0.durationReader.readDuration(extractorInput2, positionHolder2, r0.pcrPid);
            }
            maybeOutputSeekMap(length);
            if (r0.pendingSeekToStart) {
                r0.pendingSeekToStart = false;
                seek(0, 0);
                if (extractorInput.getPosition() != 0) {
                    positionHolder2.position = 0;
                    return 1;
                }
            }
            if (r0.tsBinarySearchSeeker != null && r0.tsBinarySearchSeeker.isSeeking()) {
                return r0.tsBinarySearchSeeker.handlePendingSeek(extractorInput2, positionHolder2, null);
            }
        }
        if (!fillBufferWithAtLeastOnePacket(extractorInput)) {
            return -1;
        }
        int findEndOfFirstTsPacketInBuffer = findEndOfFirstTsPacketInBuffer();
        int limit = r0.tsPacketBuffer.limit();
        if (findEndOfFirstTsPacketInBuffer > limit) {
            return 0;
        }
        int readInt = r0.tsPacketBuffer.readInt();
        if ((8388608 & readInt) != 0) {
            r0.tsPacketBuffer.setPosition(findEndOfFirstTsPacketInBuffer);
            return 0;
        }
        boolean z = (4194304 & readInt) != 0;
        int i = (2096896 & readInt) >> 8;
        Object obj2 = (readInt & 32) != 0 ? 1 : null;
        if (((readInt & 16) != 0 ? 1 : null) != null) {
            tsPayloadReader = (TsPayloadReader) r0.tsPayloadReaders.get(i);
        }
        if (tsPayloadReader == null) {
            r0.tsPacketBuffer.setPosition(findEndOfFirstTsPacketInBuffer);
            return 0;
        }
        if (r0.mode != 2) {
            readInt &= 15;
            int i2 = r0.continuityCounters.get(i, readInt - 1);
            r0.continuityCounters.put(i, readInt);
            if (i2 == readInt) {
                r0.tsPacketBuffer.setPosition(findEndOfFirstTsPacketInBuffer);
                return 0;
            } else if (readInt != ((i2 + 1) & 15)) {
                tsPayloadReader.seek();
            }
        }
        if (obj2 != null) {
            r0.tsPacketBuffer.skipBytes(r0.tsPacketBuffer.readUnsignedByte());
        }
        boolean z2 = r0.tracksEnded;
        if (shouldConsumePacketPayload(i)) {
            r0.tsPacketBuffer.setLimit(findEndOfFirstTsPacketInBuffer);
            tsPayloadReader.consume(r0.tsPacketBuffer, z);
            r0.tsPacketBuffer.setLimit(limit);
        }
        if (!(r0.mode == 2 || z2 || !r0.tracksEnded || length == -1)) {
            r0.pendingSeekToStart = true;
        }
        r0.tsPacketBuffer.setPosition(findEndOfFirstTsPacketInBuffer);
        return 0;
    }

    private void maybeOutputSeekMap(long j) {
        if (!this.hasOutputSeekMap) {
            this.hasOutputSeekMap = true;
            if (this.durationReader.getDurationUs() != C.TIME_UNSET) {
                this.tsBinarySearchSeeker = new TsBinarySearchSeeker(this.durationReader.getPcrTimestampAdjuster(), this.durationReader.getDurationUs(), j, this.pcrPid);
                this.output.seekMap(this.tsBinarySearchSeeker.getSeekMap());
                return;
            }
            this.output.seekMap(new Unseekable(this.durationReader.getDurationUs()));
        }
    }

    private boolean fillBufferWithAtLeastOnePacket(ExtractorInput extractorInput) throws IOException, InterruptedException {
        Object obj = this.tsPacketBuffer.data;
        if (9400 - this.tsPacketBuffer.getPosition() < TS_PACKET_SIZE) {
            int bytesLeft;
            bytesLeft = this.tsPacketBuffer.bytesLeft();
            if (bytesLeft > 0) {
                System.arraycopy(obj, this.tsPacketBuffer.getPosition(), obj, 0, bytesLeft);
            }
            this.tsPacketBuffer.reset(obj, bytesLeft);
        }
        while (this.tsPacketBuffer.bytesLeft() < TS_PACKET_SIZE) {
            bytesLeft = this.tsPacketBuffer.limit();
            int read = extractorInput.read(obj, bytesLeft, 9400 - bytesLeft);
            if (read == -1) {
                return false;
            }
            this.tsPacketBuffer.setLimit(bytesLeft + read);
        }
        return true;
    }

    private int findEndOfFirstTsPacketInBuffer() throws ParserException {
        int position = this.tsPacketBuffer.getPosition();
        int limit = this.tsPacketBuffer.limit();
        int findSyncBytePosition = TsUtil.findSyncBytePosition(this.tsPacketBuffer.data, position, limit);
        this.tsPacketBuffer.setPosition(findSyncBytePosition);
        int i = findSyncBytePosition + TS_PACKET_SIZE;
        if (i > limit) {
            this.bytesSinceLastSync += findSyncBytePosition - position;
            if (this.mode == 2) {
                if (this.bytesSinceLastSync > 376) {
                    throw new ParserException("Cannot find sync byte. Most likely not a Transport Stream.");
                }
            }
        } else {
            this.bytesSinceLastSync = 0;
        }
        return i;
    }

    private boolean shouldConsumePacketPayload(int i) {
        if (this.mode == 2 || this.tracksEnded || this.trackPids.get(i, false) == 0) {
            return true;
        }
        return false;
    }

    private void resetPayloadReaders() {
        this.trackIds.clear();
        this.tsPayloadReaders.clear();
        SparseArray createInitialPayloadReaders = this.payloadReaderFactory.createInitialPayloadReaders();
        int size = createInitialPayloadReaders.size();
        for (int i = 0; i < size; i++) {
            this.tsPayloadReaders.put(createInitialPayloadReaders.keyAt(i), createInitialPayloadReaders.valueAt(i));
        }
        this.tsPayloadReaders.put(0, new SectionReader(new PatReader()));
        this.id3Reader = null;
    }
}
