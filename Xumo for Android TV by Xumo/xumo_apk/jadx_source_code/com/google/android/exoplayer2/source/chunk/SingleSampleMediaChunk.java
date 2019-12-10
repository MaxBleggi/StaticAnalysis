package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public final class SingleSampleMediaChunk extends BaseMediaChunk {
    private boolean loadCompleted;
    private long nextLoadPosition;
    private final Format sampleFormat;
    private final int trackType;

    public void cancelLoad() {
    }

    public SingleSampleMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, Object obj, long j, long j2, long j3, int i2, Format format2) {
        SingleSampleMediaChunk singleSampleMediaChunk = this;
        super(dataSource, dataSpec, format, i, obj, j, j2, C.TIME_UNSET, C.TIME_UNSET, j3);
        this.trackType = i2;
        this.sampleFormat = format2;
    }

    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    public void load() throws IOException, InterruptedException {
        try {
            long open = this.dataSource.open(this.dataSpec.subrange(this.nextLoadPosition));
            if (open != -1) {
                open += this.nextLoadPosition;
            }
            ExtractorInput defaultExtractorInput = new DefaultExtractorInput(this.dataSource, this.nextLoadPosition, open);
            BaseMediaChunkOutput output = getOutput();
            output.setSampleOffsetUs(0);
            int i = 0;
            TrackOutput track = output.track(0, this.trackType);
            track.format(this.sampleFormat);
            while (i != -1) {
                this.nextLoadPosition += (long) i;
                i = track.sampleData(defaultExtractorInput, Integer.MAX_VALUE, true);
            }
            track.sampleMetadata(this.startTimeUs, 1, (int) this.nextLoadPosition, 0, null);
            this.loadCompleted = true;
        } finally {
            Util.closeQuietly(this.dataSource);
        }
    }
}
