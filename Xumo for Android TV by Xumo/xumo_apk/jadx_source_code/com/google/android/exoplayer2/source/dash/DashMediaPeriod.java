package com.google.android.exoplayer2.source.dash;

import android.util.Pair;
import android.util.SparseIntArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.SequenceableLoader.Callback;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream.EmbeddedSampleStream;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream.ReleaseCallback;
import com.google.android.exoplayer2.source.dash.DashChunkSource.Factory;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerEmsgCallback;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerTrackEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.Descriptor;
import com.google.android.exoplayer2.source.dash.manifest.EventStream;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;

final class DashMediaPeriod implements MediaPeriod, Callback<ChunkSampleStream<DashChunkSource>>, ReleaseCallback<DashChunkSource> {
    private final Allocator allocator;
    @Nullable
    private MediaPeriod.Callback callback;
    private final Factory chunkSourceFactory;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final long elapsedRealtimeOffset;
    private final EventDispatcher eventDispatcher;
    private EventSampleStream[] eventSampleStreams = new EventSampleStream[0];
    private List<EventStream> eventStreams;
    final int id;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private boolean notifiedReadingStarted;
    private int periodIndex;
    private final PlayerEmsgHandler playerEmsgHandler;
    private ChunkSampleStream<DashChunkSource>[] sampleStreams = newSampleStreamArray(0);
    private final IdentityHashMap<ChunkSampleStream<DashChunkSource>, PlayerTrackEmsgHandler> trackEmsgHandlerBySampleStream = new IdentityHashMap();
    private final TrackGroupInfo[] trackGroupInfos;
    private final TrackGroupArray trackGroups;
    @Nullable
    private final TransferListener transferListener;

    private static final class TrackGroupInfo {
        private static final int CATEGORY_EMBEDDED = 1;
        private static final int CATEGORY_MANIFEST_EVENTS = 2;
        private static final int CATEGORY_PRIMARY = 0;
        public final int[] adaptationSetIndices;
        public final int embeddedCea608TrackGroupIndex;
        public final int embeddedEventMessageTrackGroupIndex;
        public final int eventStreamGroupIndex;
        public final int primaryTrackGroupIndex;
        public final int trackGroupCategory;
        public final int trackType;

        @Retention(RetentionPolicy.SOURCE)
        public @interface TrackGroupCategory {
        }

        public static TrackGroupInfo primaryTrack(int i, int[] iArr, int i2, int i3, int i4) {
            return new TrackGroupInfo(i, 0, iArr, i2, i3, i4, -1);
        }

        public static TrackGroupInfo embeddedEmsgTrack(int[] iArr, int i) {
            return new TrackGroupInfo(4, 1, iArr, i, -1, -1, -1);
        }

        public static TrackGroupInfo embeddedCea608Track(int[] iArr, int i) {
            return new TrackGroupInfo(3, 1, iArr, i, -1, -1, -1);
        }

        public static TrackGroupInfo mpdEventTrack(int i) {
            return new TrackGroupInfo(4, 2, null, -1, -1, -1, i);
        }

        private TrackGroupInfo(int i, int i2, int[] iArr, int i3, int i4, int i5, int i6) {
            this.trackType = i;
            this.adaptationSetIndices = iArr;
            this.trackGroupCategory = i2;
            this.primaryTrackGroupIndex = i3;
            this.embeddedEventMessageTrackGroupIndex = i4;
            this.embeddedCea608TrackGroupIndex = i5;
            this.eventStreamGroupIndex = i6;
        }
    }

    public DashMediaPeriod(int i, DashManifest dashManifest, int i2, Factory factory, @Nullable TransferListener transferListener, LoadErrorHandlingPolicy loadErrorHandlingPolicy, EventDispatcher eventDispatcher, long j, LoaderErrorThrower loaderErrorThrower, Allocator allocator, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, PlayerEmsgCallback playerEmsgCallback) {
        this.id = i;
        this.manifest = dashManifest;
        this.periodIndex = i2;
        this.chunkSourceFactory = factory;
        this.transferListener = transferListener;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.eventDispatcher = eventDispatcher;
        this.elapsedRealtimeOffset = j;
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.allocator = allocator;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.playerEmsgHandler = new PlayerEmsgHandler(dashManifest, playerEmsgCallback, allocator);
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.sampleStreams);
        i = dashManifest.getPeriod(i2);
        this.eventStreams = i.eventStreams;
        i = buildTrackGroups(i.adaptationSets, this.eventStreams);
        this.trackGroups = (TrackGroupArray) i.first;
        this.trackGroupInfos = (TrackGroupInfo[]) i.second;
        eventDispatcher.mediaPeriodCreated();
    }

    public void updateManifest(DashManifest dashManifest, int i) {
        this.manifest = dashManifest;
        this.periodIndex = i;
        this.playerEmsgHandler.updateManifest(dashManifest);
        if (this.sampleStreams != null) {
            for (ChunkSampleStream chunkSource : this.sampleStreams) {
                ((DashChunkSource) chunkSource.getChunkSource()).updateManifest(dashManifest, i);
            }
            this.callback.onContinueLoadingRequested(this);
        }
        this.eventStreams = dashManifest.getPeriod(i).eventStreams;
        for (EventSampleStream eventSampleStream : this.eventSampleStreams) {
            for (EventStream eventStream : this.eventStreams) {
                if (eventStream.id().equals(eventSampleStream.eventStreamId())) {
                    boolean z = true;
                    int periodCount = dashManifest.getPeriodCount() - 1;
                    if (!dashManifest.dynamic || i != periodCount) {
                        z = false;
                    }
                    eventSampleStream.updateEventStream(eventStream, z);
                }
            }
        }
    }

    public void release() {
        this.playerEmsgHandler.release();
        for (ChunkSampleStream release : this.sampleStreams) {
            release.release(this);
        }
        this.callback = null;
        this.eventDispatcher.mediaPeriodReleased();
    }

    public synchronized void onSampleStreamReleased(ChunkSampleStream<DashChunkSource> chunkSampleStream) {
        PlayerTrackEmsgHandler playerTrackEmsgHandler = (PlayerTrackEmsgHandler) this.trackEmsgHandlerBySampleStream.remove(chunkSampleStream);
        if (playerTrackEmsgHandler != null) {
            playerTrackEmsgHandler.release();
        }
    }

    public void prepare(MediaPeriod.Callback callback, long j) {
        this.callback = callback;
        callback.onPrepared(this);
    }

    public void maybeThrowPrepareError() throws IOException {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        int[] streamIndexToTrackGroupIndex = getStreamIndexToTrackGroupIndex(trackSelectionArr);
        releaseDisabledStreams(trackSelectionArr, zArr, sampleStreamArr);
        releaseOrphanEmbeddedStreams(trackSelectionArr, sampleStreamArr, streamIndexToTrackGroupIndex);
        selectNewStreams(trackSelectionArr, sampleStreamArr, zArr2, j, streamIndexToTrackGroupIndex);
        trackSelectionArr = new ArrayList();
        zArr = new ArrayList();
        for (SampleStream sampleStream : sampleStreamArr) {
            if (sampleStream instanceof ChunkSampleStream) {
                trackSelectionArr.add((ChunkSampleStream) sampleStream);
            } else if (sampleStream instanceof EventSampleStream) {
                zArr.add((EventSampleStream) sampleStream);
            }
        }
        this.sampleStreams = newSampleStreamArray(trackSelectionArr.size());
        trackSelectionArr.toArray(this.sampleStreams);
        this.eventSampleStreams = new EventSampleStream[zArr.size()];
        zArr.toArray(this.eventSampleStreams);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.sampleStreams);
        return j;
    }

    public void discardBuffer(long j, boolean z) {
        for (ChunkSampleStream discardBuffer : this.sampleStreams) {
            discardBuffer.discardBuffer(j, z);
        }
    }

    public void reevaluateBuffer(long j) {
        this.compositeSequenceableLoader.reevaluateBuffer(j);
    }

    public boolean continueLoading(long j) {
        return this.compositeSequenceableLoader.continueLoading(j);
    }

    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    public long readDiscontinuity() {
        if (!this.notifiedReadingStarted) {
            this.eventDispatcher.readingStarted();
            this.notifiedReadingStarted = true;
        }
        return C.TIME_UNSET;
    }

    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    public long seekToUs(long j) {
        for (ChunkSampleStream seekToUs : this.sampleStreams) {
            seekToUs.seekToUs(j);
        }
        for (EventSampleStream seekToUs2 : this.eventSampleStreams) {
            seekToUs2.seekToUs(j);
        }
        return j;
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        for (ChunkSampleStream chunkSampleStream : this.sampleStreams) {
            if (chunkSampleStream.primaryTrackType == 2) {
                return chunkSampleStream.getAdjustedSeekPositionUs(j, seekParameters);
            }
        }
        return j;
    }

    public void onContinueLoadingRequested(ChunkSampleStream<DashChunkSource> chunkSampleStream) {
        this.callback.onContinueLoadingRequested(this);
    }

    private int[] getStreamIndexToTrackGroupIndex(TrackSelection[] trackSelectionArr) {
        int[] iArr = new int[trackSelectionArr.length];
        for (int i = 0; i < trackSelectionArr.length; i++) {
            if (trackSelectionArr[i] != null) {
                iArr[i] = this.trackGroups.indexOf(trackSelectionArr[i].getTrackGroup());
            } else {
                iArr[i] = -1;
            }
        }
        return iArr;
    }

    private void releaseDisabledStreams(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr) {
        int i = 0;
        while (i < trackSelectionArr.length) {
            if (trackSelectionArr[i] == null || !zArr[i]) {
                if (sampleStreamArr[i] instanceof ChunkSampleStream) {
                    ((ChunkSampleStream) sampleStreamArr[i]).release(this);
                } else if (sampleStreamArr[i] instanceof EmbeddedSampleStream) {
                    ((EmbeddedSampleStream) sampleStreamArr[i]).release();
                }
                sampleStreamArr[i] = null;
            }
            i++;
        }
    }

    private void releaseOrphanEmbeddedStreams(TrackSelection[] trackSelectionArr, SampleStream[] sampleStreamArr, int[] iArr) {
        int i = 0;
        while (i < trackSelectionArr.length) {
            if ((sampleStreamArr[i] instanceof EmptySampleStream) || (sampleStreamArr[i] instanceof EmbeddedSampleStream)) {
                int primaryStreamIndex = getPrimaryStreamIndex(i, iArr);
                boolean z = primaryStreamIndex == -1 ? sampleStreamArr[i] instanceof EmptySampleStream : (sampleStreamArr[i] instanceof EmbeddedSampleStream) && ((EmbeddedSampleStream) sampleStreamArr[i]).parent == sampleStreamArr[primaryStreamIndex];
                if (!z) {
                    if (sampleStreamArr[i] instanceof EmbeddedSampleStream) {
                        ((EmbeddedSampleStream) sampleStreamArr[i]).release();
                    }
                    sampleStreamArr[i] = null;
                }
            }
            i++;
        }
    }

    private void selectNewStreams(TrackSelection[] trackSelectionArr, SampleStream[] sampleStreamArr, boolean[] zArr, long j, int[] iArr) {
        int i = 0;
        int i2 = 0;
        while (i2 < trackSelectionArr.length) {
            if (sampleStreamArr[i2] == null && trackSelectionArr[i2] != null) {
                zArr[i2] = true;
                TrackGroupInfo trackGroupInfo = this.trackGroupInfos[iArr[i2]];
                if (trackGroupInfo.trackGroupCategory == 0) {
                    sampleStreamArr[i2] = buildSampleStream(trackGroupInfo, trackSelectionArr[i2], j);
                } else if (trackGroupInfo.trackGroupCategory == 2) {
                    sampleStreamArr[i2] = new EventSampleStream((EventStream) this.eventStreams.get(trackGroupInfo.eventStreamGroupIndex), trackSelectionArr[i2].getTrackGroup().getFormat(0), this.manifest.dynamic);
                }
            }
            i2++;
        }
        while (i < trackSelectionArr.length) {
            if (sampleStreamArr[i] == null && trackSelectionArr[i] != null) {
                zArr = this.trackGroupInfos[iArr[i]];
                if (zArr.trackGroupCategory == 1) {
                    i2 = getPrimaryStreamIndex(i, iArr);
                    if (i2 == -1) {
                        sampleStreamArr[i] = new EmptySampleStream();
                    } else {
                        sampleStreamArr[i] = ((ChunkSampleStream) sampleStreamArr[i2]).selectEmbeddedTrack(j, zArr.trackType);
                    }
                }
            }
            i++;
        }
    }

    private int getPrimaryStreamIndex(int i, int[] iArr) {
        i = iArr[i];
        if (i == -1) {
            return -1;
        }
        i = this.trackGroupInfos[i].primaryTrackGroupIndex;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = iArr[i2];
            if (i3 == i && this.trackGroupInfos[i3].trackGroupCategory == 0) {
                return i2;
            }
        }
        return -1;
    }

    private static Pair<TrackGroupArray, TrackGroupInfo[]> buildTrackGroups(List<AdaptationSet> list, List<EventStream> list2) {
        int[][] groupedAdaptationSetIndices = getGroupedAdaptationSetIndices(list);
        int length = groupedAdaptationSetIndices.length;
        boolean[] zArr = new boolean[length];
        boolean[] zArr2 = new boolean[length];
        int identifyEmbeddedTracks = (identifyEmbeddedTracks(length, list, groupedAdaptationSetIndices, zArr, zArr2) + length) + list2.size();
        TrackGroup[] trackGroupArr = new TrackGroup[identifyEmbeddedTracks];
        Object obj = new TrackGroupInfo[identifyEmbeddedTracks];
        buildManifestEventTrackGroupInfos(list2, trackGroupArr, obj, buildPrimaryAndEmbeddedTrackGroupInfos(list, groupedAdaptationSetIndices, length, zArr, zArr2, trackGroupArr, obj));
        return Pair.create(new TrackGroupArray(trackGroupArr), obj);
    }

    private static int[][] getGroupedAdaptationSetIndices(List<AdaptationSet> list) {
        int size = list.size();
        SparseIntArray sparseIntArray = new SparseIntArray(size);
        for (int i = 0; i < size; i++) {
            sparseIntArray.put(((AdaptationSet) list.get(i)).id, i);
        }
        int[][] iArr = new int[size][];
        boolean[] zArr = new boolean[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (!zArr[i3]) {
                zArr[i3] = true;
                Descriptor findAdaptationSetSwitchingProperty = findAdaptationSetSwitchingProperty(((AdaptationSet) list.get(i3)).supplementalProperties);
                if (findAdaptationSetSwitchingProperty == null) {
                    int i4 = i2 + 1;
                    iArr[i2] = new int[]{i3};
                    i2 = i4;
                } else {
                    String[] split = findAdaptationSetSwitchingProperty.value.split(",");
                    int[] iArr2 = new int[(split.length + 1)];
                    iArr2[0] = i3;
                    int i5 = 0;
                    while (i5 < split.length) {
                        int i6 = sparseIntArray.get(Integer.parseInt(split[i5]));
                        zArr[i6] = true;
                        i5++;
                        iArr2[i5] = i6;
                    }
                    int i7 = i2 + 1;
                    iArr[i2] = iArr2;
                    i2 = i7;
                }
            }
        }
        return i2 < size ? (int[][]) Arrays.copyOf(iArr, i2) : iArr;
    }

    private static int identifyEmbeddedTracks(int i, List<AdaptationSet> list, int[][] iArr, boolean[] zArr, boolean[] zArr2) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (hasEventMessageTrack(list, iArr[i3])) {
                zArr[i3] = true;
                i2++;
            }
            if (hasCea608Track(list, iArr[i3])) {
                zArr2[i3] = true;
                i2++;
            }
        }
        return i2;
    }

    private static int buildPrimaryAndEmbeddedTrackGroupInfos(List<AdaptationSet> list, int[][] iArr, int i, boolean[] zArr, boolean[] zArr2, TrackGroup[] trackGroupArr, TrackGroupInfo[] trackGroupInfoArr) {
        List<AdaptationSet> list2 = list;
        int i2 = 0;
        int i3 = i;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i3) {
            int i6;
            int i7;
            int i8;
            int i9;
            int[] iArr2 = iArr[i4];
            List arrayList = new ArrayList();
            for (int i10 : iArr2) {
                arrayList.addAll(((AdaptationSet) list.get(i10)).representations);
            }
            Format[] formatArr = new Format[arrayList.size()];
            for (i6 = 0; i6 < formatArr.length; i6++) {
                formatArr[i6] = ((Representation) arrayList.get(i6)).format;
            }
            AdaptationSet adaptationSet = (AdaptationSet) list.get(iArr2[i2]);
            i6 = i5 + 1;
            if (zArr[i4]) {
                i7 = i6 + 1;
                i8 = i6;
            } else {
                i7 = i6;
                i8 = -1;
            }
            if (zArr2[i4]) {
                i9 = i7 + 1;
            } else {
                i9 = i7;
                i7 = -1;
            }
            trackGroupArr[i5] = new TrackGroup(formatArr);
            trackGroupInfoArr[i5] = TrackGroupInfo.primaryTrack(adaptationSet.type, iArr2, i5, i8, i7);
            if (i8 != -1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(adaptationSet.id);
                stringBuilder.append(":emsg");
                trackGroupArr[i8] = new TrackGroup(Format.createSampleFormat(stringBuilder.toString(), MimeTypes.APPLICATION_EMSG, null, -1, null));
                trackGroupInfoArr[i8] = TrackGroupInfo.embeddedEmsgTrack(iArr2, i5);
            }
            if (i7 != -1) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(adaptationSet.id);
                stringBuilder2.append(":cea608");
                trackGroupArr[i7] = new TrackGroup(Format.createTextSampleFormat(stringBuilder2.toString(), MimeTypes.APPLICATION_CEA608, 0, null));
                trackGroupInfoArr[i7] = TrackGroupInfo.embeddedCea608Track(iArr2, i5);
            }
            i4++;
            i5 = i9;
            list2 = list;
            i2 = 0;
        }
        return i5;
    }

    private static void buildManifestEventTrackGroupInfos(List<EventStream> list, TrackGroup[] trackGroupArr, TrackGroupInfo[] trackGroupInfoArr, int i) {
        int i2 = i;
        i = 0;
        while (i < list.size()) {
            trackGroupArr[i2] = new TrackGroup(Format.createSampleFormat(((EventStream) list.get(i)).id(), MimeTypes.APPLICATION_EMSG, null, -1, null));
            int i3 = i2 + 1;
            trackGroupInfoArr[i2] = TrackGroupInfo.mpdEventTrack(i);
            i++;
            i2 = i3;
        }
    }

    private ChunkSampleStream<DashChunkSource> buildSampleStream(TrackGroupInfo trackGroupInfo, TrackSelection trackSelection, long j) {
        int i;
        DashMediaPeriod dashMediaPeriod = this;
        TrackGroupInfo trackGroupInfo2 = trackGroupInfo;
        int[] iArr = new int[2];
        Format[] formatArr = new Format[2];
        boolean z = trackGroupInfo2.embeddedEventMessageTrackGroupIndex != -1;
        if (z) {
            formatArr[0] = dashMediaPeriod.trackGroups.get(trackGroupInfo2.embeddedEventMessageTrackGroupIndex).getFormat(0);
            iArr[0] = 4;
            i = 1;
        } else {
            i = 0;
        }
        boolean z2 = trackGroupInfo2.embeddedCea608TrackGroupIndex != -1;
        if (z2) {
            formatArr[i] = dashMediaPeriod.trackGroups.get(trackGroupInfo2.embeddedCea608TrackGroupIndex).getFormat(0);
            int i2 = i + 1;
            iArr[i] = 3;
            i = i2;
        }
        if (i < iArr.length) {
            formatArr = (Format[]) Arrays.copyOf(formatArr, i);
            iArr = Arrays.copyOf(iArr, i);
        }
        Format[] formatArr2 = formatArr;
        int[] iArr2 = iArr;
        PlayerTrackEmsgHandler newPlayerTrackEmsgHandler = (dashMediaPeriod.manifest.dynamic && z) ? dashMediaPeriod.playerEmsgHandler.newPlayerTrackEmsgHandler() : null;
        PlayerTrackEmsgHandler playerTrackEmsgHandler = newPlayerTrackEmsgHandler;
        PlayerTrackEmsgHandler playerTrackEmsgHandler2 = playerTrackEmsgHandler;
        ChunkSampleStream<DashChunkSource> chunkSampleStream = new ChunkSampleStream(trackGroupInfo2.trackType, iArr2, formatArr2, dashMediaPeriod.chunkSourceFactory.createDashChunkSource(dashMediaPeriod.manifestLoaderErrorThrower, dashMediaPeriod.manifest, dashMediaPeriod.periodIndex, trackGroupInfo2.adaptationSetIndices, trackSelection, trackGroupInfo2.trackType, dashMediaPeriod.elapsedRealtimeOffset, z, z2, playerTrackEmsgHandler, dashMediaPeriod.transferListener), (Callback) this, dashMediaPeriod.allocator, j, dashMediaPeriod.loadErrorHandlingPolicy, dashMediaPeriod.eventDispatcher);
        synchronized (this) {
            dashMediaPeriod.trackEmsgHandlerBySampleStream.put(chunkSampleStream, playerTrackEmsgHandler2);
        }
        return chunkSampleStream;
    }

    private static Descriptor findAdaptationSetSwitchingProperty(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("urn:mpeg:dash:adaptation-set-switching:2016".equals(descriptor.schemeIdUri)) {
                return descriptor;
            }
        }
        return null;
    }

    private static boolean hasEventMessageTrack(List<AdaptationSet> list, int[] iArr) {
        for (int i : iArr) {
            List list2 = ((AdaptationSet) list.get(i)).representations;
            for (int i2 = 0; i2 < list2.size(); i2++) {
                if (!((Representation) list2.get(i2)).inbandEventStreams.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasCea608Track(List<AdaptationSet> list, int[] iArr) {
        for (int i : iArr) {
            List list2 = ((AdaptationSet) list.get(i)).accessibilityDescriptors;
            for (int i2 = 0; i2 < list2.size(); i2++) {
                if ("urn:scte:dash:cc:cea-608:2015".equals(((Descriptor) list2.get(i2)).schemeIdUri)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ChunkSampleStream<DashChunkSource>[] newSampleStreamArray(int i) {
        return new ChunkSampleStream[i];
    }
}
