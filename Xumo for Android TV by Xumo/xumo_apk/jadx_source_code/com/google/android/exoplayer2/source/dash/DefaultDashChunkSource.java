package com.google.android.exoplayer2.source.dash;

import android.os.SystemClock;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.rawcc.RawCcExtractor;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.SingleSampleMediaChunk;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerTrackEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultDashChunkSource implements DashChunkSource {
    private final int[] adaptationSetIndices;
    private final DataSource dataSource;
    private final long elapsedRealtimeOffsetMs;
    private IOException fatalError;
    private long liveEdgeTimeUs = C.TIME_UNSET;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int maxSegmentsPerLoad;
    private boolean missingLastSegment;
    private int periodIndex;
    @Nullable
    private final PlayerTrackEmsgHandler playerTrackEmsgHandler;
    protected final RepresentationHolder[] representationHolders;
    private final TrackSelection trackSelection;
    private final int trackType;

    protected static final class RepresentationHolder {
        @Nullable
        final ChunkExtractorWrapper extractorWrapper;
        private final long periodDurationUs;
        public final Representation representation;
        @Nullable
        public final DashSegmentIndex segmentIndex;
        private final long segmentNumShift;

        RepresentationHolder(long j, int i, Representation representation, boolean z, boolean z2, TrackOutput trackOutput) {
            this(j, representation, createExtractorWrapper(i, representation, z, z2, trackOutput), 0, representation.getIndex());
        }

        private RepresentationHolder(long j, Representation representation, @Nullable ChunkExtractorWrapper chunkExtractorWrapper, long j2, @Nullable DashSegmentIndex dashSegmentIndex) {
            this.periodDurationUs = j;
            this.representation = representation;
            this.segmentNumShift = j2;
            this.extractorWrapper = chunkExtractorWrapper;
            this.segmentIndex = dashSegmentIndex;
        }

        @CheckResult
        RepresentationHolder copyWithNewRepresentation(long j, Representation representation) throws BehindLiveWindowException {
            long j2 = j;
            DashSegmentIndex index = this.representation.getIndex();
            DashSegmentIndex index2 = representation.getIndex();
            if (index == null) {
                return new RepresentationHolder(j, representation, r0.extractorWrapper, r0.segmentNumShift, index);
            } else if (index.isExplicit()) {
                int segmentCount = index.getSegmentCount(j2);
                if (segmentCount == 0) {
                    return new RepresentationHolder(j, representation, r0.extractorWrapper, r0.segmentNumShift, index2);
                }
                long firstSegmentNum = (index.getFirstSegmentNum() + ((long) segmentCount)) - 1;
                long timeUs = index.getTimeUs(firstSegmentNum) + index.getDurationUs(firstSegmentNum, j2);
                long firstSegmentNum2 = index2.getFirstSegmentNum();
                long timeUs2 = index2.getTimeUs(firstSegmentNum2);
                long j3 = r0.segmentNumShift;
                if (timeUs == timeUs2) {
                    j3 += (firstSegmentNum + 1) - firstSegmentNum2;
                } else if (timeUs >= timeUs2) {
                    j3 += index.getSegmentNum(timeUs2, j2) - firstSegmentNum2;
                } else {
                    throw new BehindLiveWindowException();
                }
                return new RepresentationHolder(j, representation, r0.extractorWrapper, j3, index2);
            } else {
                return new RepresentationHolder(j, representation, r0.extractorWrapper, r0.segmentNumShift, index2);
            }
        }

        @CheckResult
        RepresentationHolder copyWithNewSegmentIndex(DashSegmentIndex dashSegmentIndex) {
            return new RepresentationHolder(this.periodDurationUs, this.representation, this.extractorWrapper, this.segmentNumShift, dashSegmentIndex);
        }

        public long getFirstSegmentNum() {
            return this.segmentIndex.getFirstSegmentNum() + this.segmentNumShift;
        }

        public int getSegmentCount() {
            return this.segmentIndex.getSegmentCount(this.periodDurationUs);
        }

        public long getSegmentStartTimeUs(long j) {
            return this.segmentIndex.getTimeUs(j - this.segmentNumShift);
        }

        public long getSegmentEndTimeUs(long j) {
            return getSegmentStartTimeUs(j) + this.segmentIndex.getDurationUs(j - this.segmentNumShift, this.periodDurationUs);
        }

        public long getSegmentNum(long j) {
            return this.segmentIndex.getSegmentNum(j, this.periodDurationUs) + this.segmentNumShift;
        }

        public RangedUri getSegmentUrl(long j) {
            return this.segmentIndex.getSegmentUrl(j - this.segmentNumShift);
        }

        public long getFirstAvailableSegmentNum(DashManifest dashManifest, int i, long j) {
            if (getSegmentCount() != -1 || dashManifest.timeShiftBufferDepthMs == C.TIME_UNSET) {
                return getFirstSegmentNum();
            }
            return Math.max(getFirstSegmentNum(), getSegmentNum(((j - C.msToUs(dashManifest.availabilityStartTimeMs)) - C.msToUs(dashManifest.getPeriod(i).startMs)) - C.msToUs(dashManifest.timeShiftBufferDepthMs)));
        }

        public long getLastAvailableSegmentNum(DashManifest dashManifest, int i, long j) {
            int segmentCount = getSegmentCount();
            if (segmentCount == -1) {
                return getSegmentNum((j - C.msToUs(dashManifest.availabilityStartTimeMs)) - C.msToUs(dashManifest.getPeriod(i).startMs)) - 1;
            }
            return (getFirstSegmentNum() + ((long) segmentCount)) - 1;
        }

        private static boolean mimeTypeIsWebm(String str) {
            if (!(str.startsWith(MimeTypes.VIDEO_WEBM) || str.startsWith(MimeTypes.AUDIO_WEBM))) {
                if (str.startsWith(MimeTypes.APPLICATION_WEBM) == null) {
                    return null;
                }
            }
            return true;
        }

        private static boolean mimeTypeIsRawText(String str) {
            if (!MimeTypes.isText(str)) {
                if (MimeTypes.APPLICATION_TTML.equals(str) == null) {
                    return null;
                }
            }
            return true;
        }

        @Nullable
        private static ChunkExtractorWrapper createExtractorWrapper(int i, Representation representation, boolean z, boolean z2, TrackOutput trackOutput) {
            String str = representation.format.containerMimeType;
            if (mimeTypeIsRawText(str)) {
                return null;
            }
            if (MimeTypes.APPLICATION_RAWCC.equals(str)) {
                z = new RawCcExtractor(representation.format);
            } else if (mimeTypeIsWebm(str)) {
                z = new MatroskaExtractor(true);
            } else {
                int i2 = z ? 4 : 0;
                if (z2) {
                    z = Collections.singletonList(Format.createTextSampleFormat(null, MimeTypes.APPLICATION_CEA608, 0, null));
                } else {
                    z = Collections.emptyList();
                }
                FragmentedMp4Extractor fragmentedMp4Extractor = new FragmentedMp4Extractor(i2, null, null, null, z, trackOutput);
            }
            return new ChunkExtractorWrapper(z, i, representation.format);
        }
    }

    public static final class Factory implements com.google.android.exoplayer2.source.dash.DashChunkSource.Factory {
        private final com.google.android.exoplayer2.upstream.DataSource.Factory dataSourceFactory;
        private final int maxSegmentsPerLoad;

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
            this(factory, 1);
        }

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory, int i) {
            this.dataSourceFactory = factory;
            this.maxSegmentsPerLoad = i;
        }

        public DashChunkSource createDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, int i, int[] iArr, TrackSelection trackSelection, int i2, long j, boolean z, boolean z2, @Nullable PlayerTrackEmsgHandler playerTrackEmsgHandler, @Nullable TransferListener transferListener) {
            TransferListener transferListener2 = transferListener;
            DataSource createDataSource = this.dataSourceFactory.createDataSource();
            if (transferListener2 != null) {
                createDataSource.addTransferListener(transferListener2);
            }
            return new DefaultDashChunkSource(loaderErrorThrower, dashManifest, i, iArr, trackSelection, i2, createDataSource, j, r0.maxSegmentsPerLoad, z, z2, playerTrackEmsgHandler);
        }
    }

    protected static final class RepresentationSegmentIterator extends BaseMediaChunkIterator {
        private final RepresentationHolder representationHolder;

        public RepresentationSegmentIterator(RepresentationHolder representationHolder, long j, long j2) {
            super(j, j2);
            this.representationHolder = representationHolder;
        }

        public DataSpec getDataSpec() {
            checkInBounds();
            Representation representation = this.representationHolder.representation;
            RangedUri segmentUrl = this.representationHolder.getSegmentUrl(getCurrentIndex());
            return new DataSpec(segmentUrl.resolveUri(representation.baseUrl), segmentUrl.start, segmentUrl.length, representation.getCacheKey());
        }

        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentStartTimeUs(getCurrentIndex());
        }

        public long getChunkEndTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentEndTimeUs(getCurrentIndex());
        }
    }

    public DefaultDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, int i, int[] iArr, TrackSelection trackSelection, int i2, DataSource dataSource, long j, int i3, boolean z, boolean z2, @Nullable PlayerTrackEmsgHandler playerTrackEmsgHandler) {
        TrackSelection trackSelection2 = trackSelection;
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.manifest = dashManifest;
        this.adaptationSetIndices = iArr;
        this.trackSelection = trackSelection2;
        this.trackType = i2;
        this.dataSource = dataSource;
        this.periodIndex = i;
        this.elapsedRealtimeOffsetMs = j;
        this.maxSegmentsPerLoad = i3;
        this.playerTrackEmsgHandler = playerTrackEmsgHandler;
        long periodDurationUs = dashManifest.getPeriodDurationUs(i);
        List representations = getRepresentations();
        this.representationHolders = new RepresentationHolder[trackSelection.length()];
        for (int i4 = 0; i4 < r0.representationHolders.length; i4++) {
            r0.representationHolders[i4] = new RepresentationHolder(periodDurationUs, i2, (Representation) representations.get(trackSelection2.getIndexInTrackGroup(i4)), z, z2, playerTrackEmsgHandler);
        }
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        for (RepresentationHolder representationHolder : this.representationHolders) {
            if (representationHolder.segmentIndex != null) {
                long segmentNum = representationHolder.getSegmentNum(j);
                long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(segmentNum);
                long segmentStartTimeUs2 = (segmentStartTimeUs >= j || segmentNum >= ((long) (representationHolder.getSegmentCount() - 1))) ? segmentStartTimeUs : representationHolder.getSegmentStartTimeUs(segmentNum + 1);
                return Util.resolveSeekPositionUs(j, seekParameters, segmentStartTimeUs, segmentStartTimeUs2);
            }
        }
        return j;
    }

    public void updateManifest(DashManifest dashManifest, int i) {
        try {
            this.manifest = dashManifest;
            this.periodIndex = i;
            dashManifest = this.manifest.getPeriodDurationUs(this.periodIndex);
            List representations = getRepresentations();
            for (int i2 = 0; i2 < this.representationHolders.length; i2++) {
                this.representationHolders[i2] = this.representationHolders[i2].copyWithNewRepresentation(dashManifest, (Representation) representations.get(this.trackSelection.getIndexInTrackGroup(i2)));
            }
        } catch (DashManifest dashManifest2) {
            this.fatalError = dashManifest2;
        }
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

    public void getNextChunk(long j, long j2, List<? extends MediaChunk> list, ChunkHolder chunkHolder) {
        ChunkHolder chunkHolder2 = chunkHolder;
        if (this.fatalError == null) {
            long j3 = j2 - j;
            long resolveTimeToLiveEdgeUs = resolveTimeToLiveEdgeUs(j);
            long msToUs = (C.msToUs(r12.manifest.availabilityStartTimeMs) + C.msToUs(r12.manifest.getPeriod(r12.periodIndex).startMs)) + j2;
            if (r12.playerTrackEmsgHandler == null || !r12.playerTrackEmsgHandler.maybeRefreshManifestBeforeLoadingNextChunk(msToUs)) {
                List<? extends MediaChunk> list2;
                MediaChunk mediaChunk;
                long j4;
                long nowUnixTimeUs = getNowUnixTimeUs();
                RangedUri rangedUri = null;
                if (list.isEmpty()) {
                    list2 = list;
                    mediaChunk = null;
                } else {
                    mediaChunk = (MediaChunk) list.get(list.size() - 1);
                }
                MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[r12.trackSelection.length()];
                int i = 0;
                while (i < mediaChunkIteratorArr.length) {
                    int i2;
                    MediaChunkIterator[] mediaChunkIteratorArr2;
                    RepresentationHolder representationHolder = r12.representationHolders[i];
                    if (representationHolder.segmentIndex == null) {
                        mediaChunkIteratorArr[i] = MediaChunkIterator.EMPTY;
                        i2 = i;
                        mediaChunkIteratorArr2 = mediaChunkIteratorArr;
                        j4 = nowUnixTimeUs;
                    } else {
                        long firstAvailableSegmentNum = representationHolder.getFirstAvailableSegmentNum(r12.manifest, r12.periodIndex, nowUnixTimeUs);
                        long lastAvailableSegmentNum = representationHolder.getLastAvailableSegmentNum(r12.manifest, r12.periodIndex, nowUnixTimeUs);
                        i2 = i;
                        RepresentationHolder representationHolder2 = representationHolder;
                        mediaChunkIteratorArr2 = mediaChunkIteratorArr;
                        j4 = nowUnixTimeUs;
                        long segmentNum = getSegmentNum(representationHolder, mediaChunk, j2, firstAvailableSegmentNum, lastAvailableSegmentNum);
                        if (segmentNum < firstAvailableSegmentNum) {
                            mediaChunkIteratorArr2[i2] = MediaChunkIterator.EMPTY;
                        } else {
                            mediaChunkIteratorArr2[i2] = new RepresentationSegmentIterator(representationHolder2, segmentNum, lastAvailableSegmentNum);
                        }
                    }
                    i = i2 + 1;
                    list2 = list;
                    mediaChunkIteratorArr = mediaChunkIteratorArr2;
                    nowUnixTimeUs = j4;
                }
                j4 = nowUnixTimeUs;
                boolean z = true;
                r12.trackSelection.updateSelectedTrack(j, j3, resolveTimeToLiveEdgeUs, list, mediaChunkIteratorArr);
                RepresentationHolder representationHolder3 = r12.representationHolders[r12.trackSelection.getSelectedIndex()];
                if (representationHolder3.extractorWrapper != null) {
                    Representation representation = representationHolder3.representation;
                    RangedUri initializationUri = representationHolder3.extractorWrapper.getSampleFormats() == null ? representation.getInitializationUri() : null;
                    if (representationHolder3.segmentIndex == null) {
                        rangedUri = representation.getIndexUri();
                    }
                    if (!(initializationUri == null && rangedUri == null)) {
                        chunkHolder2.chunk = newInitializationChunk(representationHolder3, r12.dataSource, r12.trackSelection.getSelectedFormat(), r12.trackSelection.getSelectionReason(), r12.trackSelection.getSelectionData(), initializationUri, rangedUri);
                        return;
                    }
                }
                if (representationHolder3.getSegmentCount() == 0) {
                    if (r12.manifest.dynamic) {
                        if (r12.periodIndex >= r12.manifest.getPeriodCount() - 1) {
                            z = false;
                        }
                    }
                    chunkHolder2.endOfStream = z;
                    return;
                }
                long j5 = j4;
                long firstAvailableSegmentNum2 = representationHolder3.getFirstAvailableSegmentNum(r12.manifest, r12.periodIndex, j5);
                long lastAvailableSegmentNum2 = representationHolder3.getLastAvailableSegmentNum(r12.manifest, r12.periodIndex, j5);
                updateLiveEdgeTimeUs(representationHolder3, lastAvailableSegmentNum2);
                j3 = lastAvailableSegmentNum2;
                nowUnixTimeUs = getSegmentNum(representationHolder3, mediaChunk, j2, firstAvailableSegmentNum2, j3);
                if (nowUnixTimeUs < firstAvailableSegmentNum2) {
                    r12.fatalError = new BehindLiveWindowException();
                    return;
                }
                boolean z2;
                if (nowUnixTimeUs <= j3) {
                    if (!r12.missingLastSegment || nowUnixTimeUs < j3) {
                        msToUs = representationHolder3.periodDurationUs;
                        if (msToUs == C.TIME_UNSET || representationHolder3.getSegmentStartTimeUs(nowUnixTimeUs) < msToUs) {
                            int min = (int) Math.min((long) r12.maxSegmentsPerLoad, (j3 - nowUnixTimeUs) + 1);
                            if (msToUs != C.TIME_UNSET) {
                                while (min > 1 && representationHolder3.getSegmentStartTimeUs((((long) min) + nowUnixTimeUs) - 1) >= msToUs) {
                                    min--;
                                }
                            }
                            chunkHolder2.chunk = newMediaChunk(representationHolder3, this.dataSource, r12.trackType, r12.trackSelection.getSelectedFormat(), r12.trackSelection.getSelectionReason(), r12.trackSelection.getSelectionData(), nowUnixTimeUs, min, list.isEmpty() ? j2 : C.TIME_UNSET);
                            return;
                        }
                        chunkHolder2.endOfStream = true;
                        return;
                    }
                }
                if (r12.manifest.dynamic) {
                    z2 = true;
                    if (r12.periodIndex >= r12.manifest.getPeriodCount() - 1) {
                        z2 = false;
                    }
                } else {
                    z2 = true;
                }
                chunkHolder2.endOfStream = z2;
            }
        }
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        if (chunk instanceof InitializationChunk) {
            int indexOf = this.trackSelection.indexOf(((InitializationChunk) chunk).trackFormat);
            RepresentationHolder representationHolder = this.representationHolders[indexOf];
            if (representationHolder.segmentIndex == null) {
                SeekMap seekMap = representationHolder.extractorWrapper.getSeekMap();
                if (seekMap != null) {
                    this.representationHolders[indexOf] = representationHolder.copyWithNewSegmentIndex(new DashWrappingSegmentIndex((ChunkIndex) seekMap, representationHolder.representation.presentationTimeOffsetUs));
                }
            }
        }
        if (this.playerTrackEmsgHandler != null) {
            this.playerTrackEmsgHandler.onChunkLoadCompleted(chunk);
        }
    }

    public boolean onChunkLoadError(Chunk chunk, boolean z, Exception exception, long j) {
        boolean z2 = false;
        if (!z) {
            return false;
        }
        if (this.playerTrackEmsgHandler && this.playerTrackEmsgHandler.maybeRefreshManifestOnLoadingError(chunk)) {
            return true;
        }
        if (!this.manifest.dynamic && (chunk instanceof MediaChunk) && (exception instanceof InvalidResponseCodeException) && ((InvalidResponseCodeException) exception).responseCode) {
            z = this.representationHolders[this.trackSelection.indexOf(chunk.trackFormat)];
            exception = z.getSegmentCount();
            if (!(exception == -1 || exception == null)) {
                if (((MediaChunk) chunk).getNextChunkIndex() > (z.getFirstSegmentNum() + ((long) exception)) - true) {
                    this.missingLastSegment = true;
                    return true;
                }
            }
        }
        if (!(j == true || this.trackSelection.blacklist(this.trackSelection.indexOf(chunk.trackFormat), j) == null)) {
            z2 = true;
        }
        return z2;
    }

    private long getSegmentNum(RepresentationHolder representationHolder, @Nullable MediaChunk mediaChunk, long j, long j2, long j3) {
        if (mediaChunk != null) {
            return mediaChunk.getNextChunkIndex();
        }
        return Util.constrainValue(representationHolder.getSegmentNum(j), j2, j3);
    }

    private ArrayList<Representation> getRepresentations() {
        List list = this.manifest.getPeriod(this.periodIndex).adaptationSets;
        ArrayList<Representation> arrayList = new ArrayList();
        for (int i : this.adaptationSetIndices) {
            arrayList.addAll(((AdaptationSet) list.get(i)).representations);
        }
        return arrayList;
    }

    private void updateLiveEdgeTimeUs(RepresentationHolder representationHolder, long j) {
        this.liveEdgeTimeUs = this.manifest.dynamic ? representationHolder.getSegmentEndTimeUs(j) : 1;
    }

    private long getNowUnixTimeUs() {
        if (this.elapsedRealtimeOffsetMs != 0) {
            return (SystemClock.elapsedRealtime() + this.elapsedRealtimeOffsetMs) * 1000;
        }
        return System.currentTimeMillis() * 1000;
    }

    private long resolveTimeToLiveEdgeUs(long j) {
        Object obj = (!this.manifest.dynamic || this.liveEdgeTimeUs == C.TIME_UNSET) ? null : 1;
        return obj != null ? this.liveEdgeTimeUs - j : C.TIME_UNSET;
    }

    protected Chunk newInitializationChunk(RepresentationHolder representationHolder, DataSource dataSource, Format format, int i, Object obj, RangedUri rangedUri, RangedUri rangedUri2) {
        String str = representationHolder.representation.baseUrl;
        if (rangedUri != null) {
            rangedUri2 = rangedUri.attemptMerge(rangedUri2, str);
            if (rangedUri2 == null) {
                rangedUri2 = rangedUri;
            }
        }
        return new InitializationChunk(dataSource, new DataSpec(rangedUri2.resolveUri(str), rangedUri2.start, rangedUri2.length, representationHolder.representation.getCacheKey()), format, i, obj, representationHolder.extractorWrapper);
    }

    protected Chunk newMediaChunk(RepresentationHolder representationHolder, DataSource dataSource, int i, Format format, int i2, Object obj, long j, int i3, long j2) {
        RepresentationHolder representationHolder2 = representationHolder;
        long j3 = j;
        Representation representation = representationHolder2.representation;
        long segmentStartTimeUs = representationHolder2.getSegmentStartTimeUs(j3);
        RangedUri segmentUrl = representationHolder2.getSegmentUrl(j3);
        String str = representation.baseUrl;
        if (representationHolder2.extractorWrapper == null) {
            return new SingleSampleMediaChunk(dataSource, new DataSpec(segmentUrl.resolveUri(str), segmentUrl.start, segmentUrl.length, representation.getCacheKey()), format, i2, obj, segmentStartTimeUs, representationHolder2.getSegmentEndTimeUs(j3), j, i, format);
        }
        int i4 = 1;
        RangedUri rangedUri = segmentUrl;
        int i5 = 1;
        int i6 = i3;
        while (i4 < i6) {
            RangedUri attemptMerge = rangedUri.attemptMerge(representationHolder2.getSegmentUrl(((long) i4) + j3), str);
            if (attemptMerge == null) {
                break;
            }
            i5++;
            i4++;
            rangedUri = attemptMerge;
        }
        long segmentEndTimeUs = representationHolder2.getSegmentEndTimeUs((((long) i5) + j3) - 1);
        long access$000 = representationHolder.periodDurationUs;
        long j4 = (access$000 == C.TIME_UNSET || access$000 >= segmentEndTimeUs) ? C.TIME_UNSET : access$000;
        DataSpec dataSpec = r18;
        DataSpec dataSpec2 = new DataSpec(rangedUri.resolveUri(str), rangedUri.start, rangedUri.length, representation.getCacheKey());
        return new ContainerMediaChunk(dataSource, dataSpec, format, i2, obj, segmentStartTimeUs, segmentEndTimeUs, j2, j4, j, i5, -representation.presentationTimeOffsetUs, representationHolder2.extractorWrapper);
    }
}
