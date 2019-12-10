package com.google.android.exoplayer2.source.hls;

import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.metadata.id3.PrivFrame;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist.HlsUrl;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

final class HlsMediaChunk extends MediaChunk {
    private static final String PRIV_TIMESTAMP_FRAME_OWNER = "com.apple.streaming.transportStreamTimestamp";
    private static final AtomicInteger uidSource = new AtomicInteger();
    public final int discontinuitySequenceNumber;
    private final DrmInitData drmInitData;
    private Extractor extractor;
    private final HlsExtractorFactory extractorFactory;
    private final boolean hasGapTag;
    public final HlsUrl hlsUrl;
    private final ParsableByteArray id3Data;
    private final Id3Decoder id3Decoder;
    private final DataSource initDataSource;
    private final DataSpec initDataSpec;
    private boolean initLoadCompleted;
    private int initSegmentBytesLoaded;
    private final boolean isEncrypted;
    private final boolean isMasterTimestampSource;
    private volatile boolean loadCanceled;
    private boolean loadCompleted;
    private final List<Format> muxedCaptionFormats;
    private int nextLoadPosition;
    private HlsSampleStreamWrapper output;
    private final Extractor previousExtractor;
    private final boolean shouldSpliceIn;
    private final TimestampAdjuster timestampAdjuster;
    public final int uid;

    public HlsMediaChunk(HlsExtractorFactory hlsExtractorFactory, DataSource dataSource, DataSpec dataSpec, DataSpec dataSpec2, HlsUrl hlsUrl, List<Format> list, int i, Object obj, long j, long j2, long j3, int i2, boolean z, boolean z2, TimestampAdjuster timestampAdjuster, HlsMediaChunk hlsMediaChunk, DrmInitData drmInitData, byte[] bArr, byte[] bArr2) {
        HlsUrl hlsUrl2 = hlsUrl;
        int i3 = i2;
        byte[] bArr3 = bArr;
        HlsMediaChunk hlsMediaChunk2 = hlsMediaChunk;
        byte[] bArr4 = bArr3;
        super(buildDataSource(dataSource, bArr3, bArr2), dataSpec, hlsUrl2.format, i, obj, j, j2, j3);
        this.discontinuitySequenceNumber = i3;
        this.initDataSpec = dataSpec2;
        this.hlsUrl = hlsUrl2;
        this.isMasterTimestampSource = z2;
        this.timestampAdjuster = timestampAdjuster;
        boolean z3 = true;
        r12.isEncrypted = bArr4 != null;
        r12.hasGapTag = z;
        r12.extractorFactory = hlsExtractorFactory;
        r12.muxedCaptionFormats = list;
        r12.drmInitData = drmInitData;
        Extractor extractor = null;
        if (hlsMediaChunk2 != null) {
            r12.id3Decoder = hlsMediaChunk2.id3Decoder;
            r12.id3Data = hlsMediaChunk2.id3Data;
            if (hlsMediaChunk2.hlsUrl == hlsUrl2) {
                if (hlsMediaChunk2.loadCompleted) {
                    z3 = false;
                }
            }
            r12.shouldSpliceIn = z3;
            if (hlsMediaChunk2.discontinuitySequenceNumber == i3) {
                if (!r12.shouldSpliceIn) {
                    extractor = hlsMediaChunk2.extractor;
                }
            }
        } else {
            r12.id3Decoder = new Id3Decoder();
            r12.id3Data = new ParsableByteArray(10);
            r12.shouldSpliceIn = false;
        }
        r12.previousExtractor = extractor;
        r12.initDataSource = dataSource;
        r12.uid = uidSource.getAndIncrement();
    }

    public void init(HlsSampleStreamWrapper hlsSampleStreamWrapper) {
        this.output = hlsSampleStreamWrapper;
    }

    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    public void cancelLoad() {
        this.loadCanceled = true;
    }

    public void load() throws IOException, InterruptedException {
        maybeLoadInitData();
        if (!this.loadCanceled) {
            if (!this.hasGapTag) {
                loadMedia();
            }
            this.loadCompleted = true;
        }
    }

    private void maybeLoadInitData() throws IOException, InterruptedException {
        if (!this.initLoadCompleted) {
            if (this.initDataSpec != null) {
                DefaultExtractorInput prepareExtraction;
                try {
                    prepareExtraction = prepareExtraction(this.initDataSource, this.initDataSpec.subrange((long) this.initSegmentBytesLoaded));
                    int i = 0;
                    while (i == 0) {
                        if (this.loadCanceled) {
                            break;
                        }
                        i = this.extractor.read(prepareExtraction, null);
                    }
                    this.initSegmentBytesLoaded = (int) (prepareExtraction.getPosition() - this.initDataSpec.absoluteStreamPosition);
                    Util.closeQuietly(this.initDataSource);
                    this.initLoadCompleted = true;
                } catch (Throwable th) {
                    Util.closeQuietly(this.initDataSource);
                }
            }
        }
    }

    private void loadMedia() throws IOException, InterruptedException {
        DataSpec dataSpec;
        Object obj;
        ExtractorInput prepareExtraction;
        int i = 0;
        if (this.isEncrypted) {
            dataSpec = this.dataSpec;
            if (this.nextLoadPosition != 0) {
                obj = 1;
                if (!this.isMasterTimestampSource) {
                    this.timestampAdjuster.waitUntilInitialized();
                } else if (this.timestampAdjuster.getFirstSampleTimestampUs() == Long.MAX_VALUE) {
                    this.timestampAdjuster.setFirstSampleTimestampUs(this.startTimeUs);
                }
                prepareExtraction = prepareExtraction(this.dataSource, dataSpec);
                if (obj != null) {
                    prepareExtraction.skipFully(this.nextLoadPosition);
                }
                while (i == 0) {
                    if (!this.loadCanceled) {
                        break;
                    }
                    i = this.extractor.read(prepareExtraction, null);
                }
                this.nextLoadPosition = (int) (prepareExtraction.getPosition() - this.dataSpec.absoluteStreamPosition);
                Util.closeQuietly(this.dataSource);
            }
        }
        dataSpec = this.dataSpec.subrange((long) this.nextLoadPosition);
        obj = null;
        if (!this.isMasterTimestampSource) {
            this.timestampAdjuster.waitUntilInitialized();
        } else if (this.timestampAdjuster.getFirstSampleTimestampUs() == Long.MAX_VALUE) {
            this.timestampAdjuster.setFirstSampleTimestampUs(this.startTimeUs);
        }
        try {
            prepareExtraction = prepareExtraction(this.dataSource, dataSpec);
            if (obj != null) {
                prepareExtraction.skipFully(this.nextLoadPosition);
            }
            while (i == 0) {
                if (!this.loadCanceled) {
                    break;
                }
                i = this.extractor.read(prepareExtraction, null);
            }
            this.nextLoadPosition = (int) (prepareExtraction.getPosition() - this.dataSpec.absoluteStreamPosition);
            Util.closeQuietly(this.dataSource);
        } catch (Throwable th) {
            Util.closeQuietly(this.dataSource);
        }
    }

    private DefaultExtractorInput prepareExtraction(DataSource dataSource, DataSpec dataSpec) throws IOException, InterruptedException {
        DataSpec dataSpec2 = dataSpec;
        DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(dataSource, dataSpec2.absoluteStreamPosition, dataSource.open(dataSpec));
        if (this.extractor != null) {
            return defaultExtractorInput;
        }
        long peekId3PrivTimestamp = peekId3PrivTimestamp(defaultExtractorInput);
        defaultExtractorInput.resetPeekPosition();
        DefaultExtractorInput defaultExtractorInput2 = defaultExtractorInput;
        Pair createExtractor = r0.extractorFactory.createExtractor(r0.previousExtractor, dataSpec2.uri, r0.trackFormat, r0.muxedCaptionFormats, r0.drmInitData, r0.timestampAdjuster, dataSource.getResponseHeaders(), defaultExtractorInput2);
        r0.extractor = (Extractor) createExtractor.first;
        boolean z = false;
        boolean z2 = r0.extractor == r0.previousExtractor;
        if (((Boolean) createExtractor.second).booleanValue()) {
            r0.output.setSampleOffsetUs(peekId3PrivTimestamp != C.TIME_UNSET ? r0.timestampAdjuster.adjustTsTimestamp(peekId3PrivTimestamp) : r0.startTimeUs);
        }
        if (z2 && r0.initDataSpec != null) {
            z = true;
        }
        r0.initLoadCompleted = z;
        r0.output.init(r0.uid, r0.shouldSpliceIn, z2);
        if (z2) {
            return defaultExtractorInput2;
        }
        r0.extractor.init(r0.output);
        return defaultExtractorInput2;
    }

    private long peekId3PrivTimestamp(ExtractorInput extractorInput) throws IOException, InterruptedException {
        extractorInput.resetPeekPosition();
        if (extractorInput.getLength() >= 10) {
            if (extractorInput.peekFully(this.id3Data.data, 0, 10, true)) {
                this.id3Data.reset(10);
                if (this.id3Data.readUnsignedInt24() != Id3Decoder.ID3_TAG) {
                    return C.TIME_UNSET;
                }
                this.id3Data.skipBytes(3);
                int readSynchSafeInt = this.id3Data.readSynchSafeInt();
                int i = readSynchSafeInt + 10;
                if (i > this.id3Data.capacity()) {
                    Object obj = this.id3Data.data;
                    this.id3Data.reset(i);
                    System.arraycopy(obj, 0, this.id3Data.data, 0, 10);
                }
                if (extractorInput.peekFully(this.id3Data.data, 10, readSynchSafeInt, true) == null) {
                    return C.TIME_UNSET;
                }
                extractorInput = this.id3Decoder.decode(this.id3Data.data, readSynchSafeInt);
                if (extractorInput == null) {
                    return C.TIME_UNSET;
                }
                readSynchSafeInt = extractorInput.length();
                for (int i2 = 0; i2 < readSynchSafeInt; i2++) {
                    Entry entry = extractorInput.get(i2);
                    if (entry instanceof PrivFrame) {
                        PrivFrame privFrame = (PrivFrame) entry;
                        if (PRIV_TIMESTAMP_FRAME_OWNER.equals(privFrame.owner)) {
                            System.arraycopy(privFrame.privateData, 0, this.id3Data.data, 0, 8);
                            this.id3Data.reset(8);
                            return this.id3Data.readLong() & 8589934591L;
                        }
                    }
                }
                return C.TIME_UNSET;
            }
        }
        return C.TIME_UNSET;
    }

    private static DataSource buildDataSource(DataSource dataSource, byte[] bArr, byte[] bArr2) {
        return bArr != null ? new Aes128DataSource(dataSource, bArr, bArr2) : dataSource;
    }
}
