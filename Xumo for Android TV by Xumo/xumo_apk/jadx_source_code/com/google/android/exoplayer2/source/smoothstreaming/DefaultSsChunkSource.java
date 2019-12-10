package com.google.android.exoplayer2.source.smoothstreaming;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Track;
import com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest.StreamElement;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;

public class DefaultSsChunkSource implements SsChunkSource {
    private int currentManifestChunkOffset;
    private final DataSource dataSource;
    private final ChunkExtractorWrapper[] extractorWrappers;
    private IOException fatalError;
    private SsManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int streamElementIndex;
    private final TrackSelection trackSelection;

    public static final class Factory implements com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory {
        private final com.google.android.exoplayer2.upstream.DataSource.Factory dataSourceFactory;

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
            this.dataSourceFactory = factory;
        }

        public SsChunkSource createChunkSource(LoaderErrorThrower loaderErrorThrower, SsManifest ssManifest, int i, TrackSelection trackSelection, TrackEncryptionBox[] trackEncryptionBoxArr, @Nullable TransferListener transferListener) {
            DataSource createDataSource = this.dataSourceFactory.createDataSource();
            if (transferListener != null) {
                createDataSource.addTransferListener(transferListener);
            }
            return new DefaultSsChunkSource(loaderErrorThrower, ssManifest, i, trackSelection, createDataSource, trackEncryptionBoxArr);
        }
    }

    private static final class StreamElementIterator extends BaseMediaChunkIterator {
        private final StreamElement streamElement;
        private final int trackIndex;

        public StreamElementIterator(StreamElement streamElement, int i, int i2) {
            super((long) i2, (long) (streamElement.chunkCount - 1));
            this.streamElement = streamElement;
            this.trackIndex = i;
        }

        public DataSpec getDataSpec() {
            checkInBounds();
            return new DataSpec(this.streamElement.buildRequestUri(this.trackIndex, (int) getCurrentIndex()));
        }

        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.streamElement.getStartTimeUs((int) getCurrentIndex());
        }

        public long getChunkEndTimeUs() {
            return getChunkStartTimeUs() + this.streamElement.getChunkDurationUs((int) getCurrentIndex());
        }
    }

    public void onChunkLoadCompleted(Chunk chunk) {
    }

    public DefaultSsChunkSource(LoaderErrorThrower loaderErrorThrower, SsManifest ssManifest, int i, TrackSelection trackSelection, DataSource dataSource, TrackEncryptionBox[] trackEncryptionBoxArr) {
        SsManifest ssManifest2 = ssManifest;
        int i2 = i;
        TrackSelection trackSelection2 = trackSelection;
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.manifest = ssManifest2;
        this.streamElementIndex = i2;
        this.trackSelection = trackSelection2;
        this.dataSource = dataSource;
        StreamElement streamElement = ssManifest2.streamElements[i2];
        this.extractorWrappers = new ChunkExtractorWrapper[trackSelection.length()];
        int i3 = 0;
        while (i3 < r0.extractorWrappers.length) {
            int indexInTrackGroup = trackSelection2.getIndexInTrackGroup(i3);
            Format format = streamElement.formats[indexInTrackGroup];
            int i4 = i3;
            Track track = r7;
            Track track2 = new Track(indexInTrackGroup, streamElement.type, streamElement.timescale, C.TIME_UNSET, ssManifest2.durationUs, format, 0, trackEncryptionBoxArr, streamElement.type == 2 ? 4 : 0, null, null);
            r0.extractorWrappers[i4] = new ChunkExtractorWrapper(new FragmentedMp4Extractor(3, null, track, null), streamElement.type, format);
            i3 = i4 + 1;
        }
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        int chunkIndex = streamElement.getChunkIndex(j);
        long startTimeUs = streamElement.getStartTimeUs(chunkIndex);
        long startTimeUs2 = (startTimeUs >= j || chunkIndex >= streamElement.chunkCount - 1) ? startTimeUs : streamElement.getStartTimeUs(chunkIndex + 1);
        return Util.resolveSeekPositionUs(j, seekParameters, startTimeUs, startTimeUs2);
    }

    public void updateManifest(SsManifest ssManifest) {
        StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        int i = streamElement.chunkCount;
        StreamElement streamElement2 = ssManifest.streamElements[this.streamElementIndex];
        if (i != 0) {
            if (streamElement2.chunkCount != 0) {
                int i2 = i - 1;
                long startTimeUs = streamElement.getStartTimeUs(i2) + streamElement.getChunkDurationUs(i2);
                long startTimeUs2 = streamElement2.getStartTimeUs(0);
                if (startTimeUs <= startTimeUs2) {
                    this.currentManifestChunkOffset += i;
                } else {
                    this.currentManifestChunkOffset += streamElement.getChunkIndex(startTimeUs2);
                }
                this.manifest = ssManifest;
            }
        }
        this.currentManifestChunkOffset += i;
        this.manifest = ssManifest;
    }

    public void maybeThrowError() throws IOException {
        if (this.fatalError == null) {
            this.manifestLoaderErrorThrower.maybeThrowError();
            return;
        }
        throw this.fatalError;
    }

    public int getPreferredQueueSize(long j, List<? extends MediaChunk> list) {
        if (this.fatalError == null) {
            if (this.trackSelection.length() >= 2) {
                return this.trackSelection.evaluateQueueSize(j, list);
            }
        }
        return list.size();
    }

    public final void getNextChunk(long j, long j2, List<? extends MediaChunk> list, ChunkHolder chunkHolder) {
        long j3 = j2;
        ChunkHolder chunkHolder2 = chunkHolder;
        if (this.fatalError == null) {
            StreamElement streamElement = r0.manifest.streamElements[r0.streamElementIndex];
            if (streamElement.chunkCount == 0) {
                chunkHolder2.endOfStream = r0.manifest.isLive ^ 1;
                return;
            }
            int chunkIndex;
            if (list.isEmpty()) {
                chunkIndex = streamElement.getChunkIndex(j3);
                List<? extends MediaChunk> list2 = list;
            } else {
                chunkIndex = (int) (((MediaChunk) list.get(list.size() - 1)).getNextChunkIndex() - ((long) r0.currentManifestChunkOffset));
                if (chunkIndex < 0) {
                    r0.fatalError = new BehindLiveWindowException();
                    return;
                }
            }
            if (chunkIndex >= streamElement.chunkCount) {
                chunkHolder2.endOfStream = r0.manifest.isLive ^ 1;
                return;
            }
            long j4 = j3 - j;
            long resolveTimeToLiveEdgeUs = resolveTimeToLiveEdgeUs(j);
            MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[r0.trackSelection.length()];
            int i = 0;
            while (i < mediaChunkIteratorArr.length) {
                mediaChunkIteratorArr[i] = new StreamElementIterator(streamElement, r0.trackSelection.getIndexInTrackGroup(i), chunkIndex);
                i++;
                j3 = j2;
            }
            r0.trackSelection.updateSelectedTrack(j, j4, resolveTimeToLiveEdgeUs, list, mediaChunkIteratorArr);
            long startTimeUs = streamElement.getStartTimeUs(chunkIndex);
            long chunkDurationUs = startTimeUs + streamElement.getChunkDurationUs(chunkIndex);
            long j5 = list.isEmpty() ? j2 : C.TIME_UNSET;
            int i2 = chunkIndex + r0.currentManifestChunkOffset;
            int selectedIndex = r0.trackSelection.getSelectedIndex();
            ChunkExtractorWrapper chunkExtractorWrapper = r0.extractorWrappers[selectedIndex];
            chunkHolder2.chunk = newMediaChunk(r0.trackSelection.getSelectedFormat(), r0.dataSource, streamElement.buildRequestUri(r0.trackSelection.getIndexInTrackGroup(selectedIndex), chunkIndex), null, i2, startTimeUs, chunkDurationUs, j5, r0.trackSelection.getSelectionReason(), r0.trackSelection.getSelectionData(), chunkExtractorWrapper);
        }
    }

    public boolean onChunkLoadError(Chunk chunk, boolean z, Exception exception, long j) {
        return (!z || j == true || this.trackSelection.blacklist(this.trackSelection.indexOf(chunk.trackFormat), j) == null) ? null : true;
    }

    private static MediaChunk newMediaChunk(Format format, DataSource dataSource, Uri uri, String str, int i, long j, long j2, long j3, int i2, Object obj, ChunkExtractorWrapper chunkExtractorWrapper) {
        Format format2 = format;
        DataSource dataSource2 = dataSource;
        long j4 = j;
        long j5 = j;
        long j6 = j2;
        long j7 = j3;
        int i3 = i2;
        Object obj2 = obj;
        ChunkExtractorWrapper chunkExtractorWrapper2 = chunkExtractorWrapper;
        DataSpec dataSpec = j;
        j = new DataSpec(uri, 0, -1, str);
        return new ContainerMediaChunk(dataSource2, dataSpec, format2, i3, obj2, j4, j6, j7, C.TIME_UNSET, (long) i, 1, j5, chunkExtractorWrapper2);
    }

    private long resolveTimeToLiveEdgeUs(long j) {
        if (!this.manifest.isLive) {
            return C.TIME_UNSET;
        }
        StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        int i = streamElement.chunkCount - 1;
        return (streamElement.getStartTimeUs(i) + streamElement.getChunkDurationUs(i)) - j;
    }
}
