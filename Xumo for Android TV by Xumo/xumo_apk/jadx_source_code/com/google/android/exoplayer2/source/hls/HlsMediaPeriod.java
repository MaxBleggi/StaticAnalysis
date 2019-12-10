package com.google.android.exoplayer2.source.hls;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist.HlsUrl;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;

public final class HlsMediaPeriod implements MediaPeriod, Callback, PlaylistEventListener {
    private final Allocator allocator;
    private final boolean allowChunklessPreparation;
    @Nullable
    private MediaPeriod.Callback callback;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private HlsSampleStreamWrapper[] enabledSampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private final EventDispatcher eventDispatcher;
    private final HlsExtractorFactory extractorFactory;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    @Nullable
    private final TransferListener mediaTransferListener;
    private boolean notifiedReadingStarted;
    private int pendingPrepareCount;
    private final HlsPlaylistTracker playlistTracker;
    private HlsSampleStreamWrapper[] sampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private final IdentityHashMap<SampleStream, Integer> streamWrapperIndices = new IdentityHashMap();
    private final TimestampAdjusterProvider timestampAdjusterProvider = new TimestampAdjusterProvider();
    private TrackGroupArray trackGroups;

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return j;
    }

    public HlsMediaPeriod(HlsExtractorFactory hlsExtractorFactory, HlsPlaylistTracker hlsPlaylistTracker, HlsDataSourceFactory hlsDataSourceFactory, @Nullable TransferListener transferListener, LoadErrorHandlingPolicy loadErrorHandlingPolicy, EventDispatcher eventDispatcher, Allocator allocator, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, boolean z) {
        this.extractorFactory = hlsExtractorFactory;
        this.playlistTracker = hlsPlaylistTracker;
        this.dataSourceFactory = hlsDataSourceFactory;
        this.mediaTransferListener = transferListener;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.eventDispatcher = eventDispatcher;
        this.allocator = allocator;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.allowChunklessPreparation = z;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[0]);
        eventDispatcher.mediaPeriodCreated();
    }

    public void release() {
        this.playlistTracker.removeListener(this);
        for (HlsSampleStreamWrapper release : this.sampleStreamWrappers) {
            release.release();
        }
        this.callback = null;
        this.eventDispatcher.mediaPeriodReleased();
    }

    public void prepare(MediaPeriod.Callback callback, long j) {
        this.callback = callback;
        this.playlistTracker.addListener(this);
        buildAndPrepareSampleStreamWrappers(j);
    }

    public void maybeThrowPrepareError() throws IOException {
        for (HlsSampleStreamWrapper maybeThrowPrepareError : this.sampleStreamWrappers) {
            maybeThrowPrepareError.maybeThrowPrepareError();
        }
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        int i;
        int i2;
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr;
        HlsMediaPeriod hlsMediaPeriod = this;
        TrackSelection[] trackSelectionArr2 = trackSelectionArr;
        SampleStream[] sampleStreamArr2 = sampleStreamArr;
        int[] iArr = new int[trackSelectionArr2.length];
        int[] iArr2 = new int[trackSelectionArr2.length];
        for (int i3 = 0; i3 < trackSelectionArr2.length; i3++) {
            int i4;
            if (sampleStreamArr2[i3] == null) {
                i4 = -1;
            } else {
                i4 = ((Integer) hlsMediaPeriod.streamWrapperIndices.get(sampleStreamArr2[i3])).intValue();
            }
            iArr[i3] = i4;
            iArr2[i3] = -1;
            if (trackSelectionArr2[i3] != null) {
                TrackGroup trackGroup = trackSelectionArr2[i3].getTrackGroup();
                for (i = 0; i < hlsMediaPeriod.sampleStreamWrappers.length; i++) {
                    if (hlsMediaPeriod.sampleStreamWrappers[i].getTrackGroups().indexOf(trackGroup) != -1) {
                        iArr2[i3] = i;
                        break;
                    }
                }
            }
        }
        hlsMediaPeriod.streamWrapperIndices.clear();
        Object obj = new SampleStream[trackSelectionArr2.length];
        SampleStream[] sampleStreamArr3 = new SampleStream[trackSelectionArr2.length];
        TrackSelection[] trackSelectionArr3 = new TrackSelection[trackSelectionArr2.length];
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr2 = new HlsSampleStreamWrapper[hlsMediaPeriod.sampleStreamWrappers.length];
        int i5 = 0;
        int i6 = 0;
        boolean z = false;
        while (i6 < hlsMediaPeriod.sampleStreamWrappers.length) {
            int i7 = 0;
            while (i7 < trackSelectionArr2.length) {
                TrackSelection trackSelection = null;
                sampleStreamArr3[i7] = iArr[i7] == i6 ? sampleStreamArr2[i7] : null;
                if (iArr2[i7] == i6) {
                    trackSelection = trackSelectionArr2[i7];
                }
                trackSelectionArr3[i7] = trackSelection;
                i7++;
            }
            HlsSampleStreamWrapper hlsSampleStreamWrapper = hlsMediaPeriod.sampleStreamWrappers[i6];
            HlsSampleStreamWrapper hlsSampleStreamWrapper2 = hlsSampleStreamWrapper;
            i2 = i5;
            hlsSampleStreamWrapperArr = hlsSampleStreamWrapperArr2;
            int i8 = i6;
            TrackSelection[] trackSelectionArr4 = trackSelectionArr3;
            boolean selectTracks = hlsSampleStreamWrapper.selectTracks(trackSelectionArr3, zArr, sampleStreamArr3, zArr2, j, z);
            i = 0;
            Object obj2 = null;
            while (true) {
                boolean z2 = true;
                if (i >= trackSelectionArr2.length) {
                    break;
                }
                if (iArr2[i] == i8) {
                    Assertions.checkState(sampleStreamArr3[i] != null);
                    obj[i] = sampleStreamArr3[i];
                    hlsMediaPeriod.streamWrapperIndices.put(sampleStreamArr3[i], Integer.valueOf(i8));
                    obj2 = 1;
                } else if (iArr[i] == i8) {
                    if (sampleStreamArr3[i] != null) {
                        z2 = false;
                    }
                    Assertions.checkState(z2);
                }
                i++;
            }
            if (obj2 != null) {
                hlsSampleStreamWrapperArr[i2] = hlsSampleStreamWrapper2;
                i = i2 + 1;
                if (i2 == 0) {
                    hlsSampleStreamWrapper2.setIsTimestampMaster(true);
                    if (!selectTracks && hlsMediaPeriod.enabledSampleStreamWrappers.length != 0) {
                        if (hlsSampleStreamWrapper2 != hlsMediaPeriod.enabledSampleStreamWrappers[0]) {
                        }
                    }
                    hlsMediaPeriod.timestampAdjusterProvider.reset();
                    i5 = i;
                    z = true;
                } else {
                    hlsSampleStreamWrapper2.setIsTimestampMaster(false);
                }
                i5 = i;
            } else {
                i5 = i2;
            }
            i6 = i8 + 1;
            hlsSampleStreamWrapperArr2 = hlsSampleStreamWrapperArr;
            trackSelectionArr3 = trackSelectionArr4;
            sampleStreamArr2 = sampleStreamArr;
        }
        i2 = i5;
        hlsSampleStreamWrapperArr = hlsSampleStreamWrapperArr2;
        System.arraycopy(obj, 0, sampleStreamArr, 0, obj.length);
        hlsMediaPeriod.enabledSampleStreamWrappers = (HlsSampleStreamWrapper[]) Arrays.copyOf(hlsSampleStreamWrapperArr, i5);
        hlsMediaPeriod.compositeSequenceableLoader = hlsMediaPeriod.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(hlsMediaPeriod.enabledSampleStreamWrappers);
        return j;
    }

    public void discardBuffer(long j, boolean z) {
        for (HlsSampleStreamWrapper discardBuffer : this.enabledSampleStreamWrappers) {
            discardBuffer.discardBuffer(j, z);
        }
    }

    public void reevaluateBuffer(long j) {
        this.compositeSequenceableLoader.reevaluateBuffer(j);
    }

    public boolean continueLoading(long j) {
        if (this.trackGroups != null) {
            return this.compositeSequenceableLoader.continueLoading(j);
        }
        for (HlsSampleStreamWrapper continuePreparing : this.sampleStreamWrappers) {
            continuePreparing.continuePreparing();
        }
        return false;
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
        if (this.enabledSampleStreamWrappers.length > 0) {
            boolean seekToUs = this.enabledSampleStreamWrappers[0].seekToUs(j, false);
            for (int i = 1; i < this.enabledSampleStreamWrappers.length; i++) {
                this.enabledSampleStreamWrappers[i].seekToUs(j, seekToUs);
            }
            if (seekToUs) {
                this.timestampAdjusterProvider.reset();
            }
        }
        return j;
    }

    public void onPrepared() {
        int i = this.pendingPrepareCount - 1;
        this.pendingPrepareCount = i;
        if (i <= 0) {
            int i2 = 0;
            for (HlsSampleStreamWrapper trackGroups : this.sampleStreamWrappers) {
                i2 += trackGroups.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArr = new TrackGroup[i2];
            HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.sampleStreamWrappers;
            int length = hlsSampleStreamWrapperArr.length;
            i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                HlsSampleStreamWrapper hlsSampleStreamWrapper = hlsSampleStreamWrapperArr[i2];
                int i4 = hlsSampleStreamWrapper.getTrackGroups().length;
                int i5 = i3;
                i3 = 0;
                while (i3 < i4) {
                    int i6 = i5 + 1;
                    trackGroupArr[i5] = hlsSampleStreamWrapper.getTrackGroups().get(i3);
                    i3++;
                    i5 = i6;
                }
                i2++;
                i3 = i5;
            }
            this.trackGroups = new TrackGroupArray(trackGroupArr);
            this.callback.onPrepared(this);
        }
    }

    public void onPlaylistRefreshRequired(HlsUrl hlsUrl) {
        this.playlistTracker.refreshPlaylist(hlsUrl);
    }

    public void onContinueLoadingRequested(HlsSampleStreamWrapper hlsSampleStreamWrapper) {
        this.callback.onContinueLoadingRequested(this);
    }

    public void onPlaylistChanged() {
        this.callback.onContinueLoadingRequested(this);
    }

    public boolean onPlaylistError(HlsUrl hlsUrl, long j) {
        boolean z = true;
        for (HlsSampleStreamWrapper onPlaylistError : this.sampleStreamWrappers) {
            z &= onPlaylistError.onPlaylistError(hlsUrl, j);
        }
        this.callback.onContinueLoadingRequested(this);
        return z;
    }

    private void buildAndPrepareSampleStreamWrappers(long j) {
        HlsMasterPlaylist masterPlaylist = this.playlistTracker.getMasterPlaylist();
        List list = masterPlaylist.audios;
        List list2 = masterPlaylist.subtitles;
        int size = (list.size() + 1) + list2.size();
        this.sampleStreamWrappers = new HlsSampleStreamWrapper[size];
        this.pendingPrepareCount = size;
        buildAndPrepareMainSampleStreamWrapper(masterPlaylist, j);
        int i = 0;
        int i2 = 0;
        int i3 = 1;
        while (i2 < list.size()) {
            HlsUrl hlsUrl = (HlsUrl) list.get(i2);
            HlsSampleStreamWrapper buildSampleStreamWrapper = buildSampleStreamWrapper(1, new HlsUrl[]{(HlsUrl) list.get(i2)}, null, Collections.emptyList(), j);
            int i4 = i3 + 1;
            r7.sampleStreamWrappers[i3] = buildSampleStreamWrapper;
            Format format = hlsUrl.format;
            if (!r7.allowChunklessPreparation || format.codecs == null) {
                buildSampleStreamWrapper.continuePreparing();
            } else {
                TrackGroup[] trackGroupArr = new TrackGroup[1];
                trackGroupArr[0] = new TrackGroup(hlsUrl.format);
                buildSampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroupArray(trackGroupArr), 0, TrackGroupArray.EMPTY);
            }
            i2++;
            i3 = i4;
            i = 0;
        }
        int i5 = 0;
        while (i5 < list2.size()) {
            hlsUrl = (HlsUrl) list2.get(i5);
            buildSampleStreamWrapper = buildSampleStreamWrapper(3, new HlsUrl[]{hlsUrl}, null, Collections.emptyList(), j);
            i4 = i3 + 1;
            r7.sampleStreamWrappers[i3] = buildSampleStreamWrapper;
            trackGroupArr = new TrackGroup[1];
            trackGroupArr[0] = new TrackGroup(hlsUrl.format);
            buildSampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroupArray(trackGroupArr), 0, TrackGroupArray.EMPTY);
            i5++;
            i3 = i4;
        }
        r7.enabledSampleStreamWrappers = r7.sampleStreamWrappers;
    }

    private void buildAndPrepareMainSampleStreamWrapper(HlsMasterPlaylist hlsMasterPlaylist, long j) {
        List list;
        HlsMediaPeriod hlsMediaPeriod = this;
        HlsMasterPlaylist hlsMasterPlaylist2 = hlsMasterPlaylist;
        List arrayList = new ArrayList(hlsMasterPlaylist2.variants);
        ArrayList arrayList2 = new ArrayList();
        Collection arrayList3 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            HlsUrl hlsUrl = (HlsUrl) arrayList.get(i);
            Format format = hlsUrl.format;
            if (format.height <= 0) {
                if (Util.getCodecsOfType(format.codecs, 2) == null) {
                    if (Util.getCodecsOfType(format.codecs, 1) != null) {
                        arrayList3.add(hlsUrl);
                    }
                }
            }
            arrayList2.add(hlsUrl);
        }
        if (arrayList2.isEmpty()) {
            if (arrayList3.size() < arrayList.size()) {
                arrayList.removeAll(arrayList3);
            }
            list = arrayList;
        } else {
            list = arrayList2;
        }
        Assertions.checkArgument(list.isEmpty() ^ true);
        HlsUrl[] hlsUrlArr = (HlsUrl[]) list.toArray(new HlsUrl[0]);
        String str = hlsUrlArr[0].format.codecs;
        HlsSampleStreamWrapper buildSampleStreamWrapper = buildSampleStreamWrapper(0, hlsUrlArr, hlsMasterPlaylist2.muxedAudioFormat, hlsMasterPlaylist2.muxedCaptionFormats, j);
        hlsMediaPeriod.sampleStreamWrappers[0] = buildSampleStreamWrapper;
        if (!hlsMediaPeriod.allowChunklessPreparation || str == null) {
            buildSampleStreamWrapper.setIsTimestampMaster(true);
            buildSampleStreamWrapper.continuePreparing();
            return;
        }
        Object obj = Util.getCodecsOfType(str, 2) != null ? 1 : null;
        Object obj2 = Util.getCodecsOfType(str, 1) != null ? 1 : null;
        List arrayList4 = new ArrayList();
        Format[] formatArr;
        int i2;
        if (obj != null) {
            formatArr = new Format[list.size()];
            for (int i3 = 0; i3 < formatArr.length; i3++) {
                formatArr[i3] = deriveVideoFormat(hlsUrlArr[i3].format);
            }
            arrayList4.add(new TrackGroup(formatArr));
            if (obj2 != null && (hlsMasterPlaylist2.muxedAudioFormat != null || hlsMasterPlaylist2.audios.isEmpty())) {
                arrayList4.add(new TrackGroup(deriveAudioFormat(hlsUrlArr[0].format, hlsMasterPlaylist2.muxedAudioFormat, false)));
            }
            List list2 = hlsMasterPlaylist2.muxedCaptionFormats;
            if (list2 != null) {
                for (i2 = 0; i2 < list2.size(); i2++) {
                    arrayList4.add(new TrackGroup((Format) list2.get(i2)));
                }
            }
        } else if (obj2 != null) {
            formatArr = new Format[list.size()];
            for (i2 = 0; i2 < formatArr.length; i2++) {
                formatArr[i2] = deriveAudioFormat(hlsUrlArr[i2].format, hlsMasterPlaylist2.muxedAudioFormat, true);
            }
            arrayList4.add(new TrackGroup(formatArr));
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected codecs attribute: ");
            stringBuilder.append(str);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        arrayList4.add(new TrackGroup(Format.createSampleFormat("ID3", MimeTypes.APPLICATION_ID3, null, -1, null)));
        buildSampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroupArray((TrackGroup[]) arrayList4.toArray(new TrackGroup[0])), 0, new TrackGroupArray(r1));
    }

    private HlsSampleStreamWrapper buildSampleStreamWrapper(int i, HlsUrl[] hlsUrlArr, Format format, List<Format> list, long j) {
        return new HlsSampleStreamWrapper(i, this, new HlsChunkSource(this.extractorFactory, this.playlistTracker, hlsUrlArr, this.dataSourceFactory, this.mediaTransferListener, this.timestampAdjusterProvider, list), this.allocator, j, format, this.loadErrorHandlingPolicy, this.eventDispatcher);
    }

    private static Format deriveVideoFormat(Format format) {
        String codecsOfType = Util.getCodecsOfType(format.codecs, 2);
        return Format.createVideoContainerFormat(format.id, format.label, format.containerMimeType, MimeTypes.getMediaMimeType(codecsOfType), codecsOfType, format.bitrate, format.width, format.height, format.frameRate, null, format.selectionFlags);
    }

    private static Format deriveAudioFormat(Format format, Format format2, boolean z) {
        String str;
        String str2;
        int i;
        int i2;
        String str3;
        Format format3 = format;
        Format format4 = format2;
        int i3;
        if (format4 != null) {
            String str4 = format4.codecs;
            int i4 = format4.channelCount;
            i3 = format4.selectionFlags;
            String str5 = format4.language;
            str = format4.label;
            str2 = str4;
            i = i4;
            i2 = i3;
            str3 = str5;
        } else {
            String codecsOfType = Util.getCodecsOfType(format3.codecs, 1);
            if (z) {
                int i5 = format3.channelCount;
                i3 = format3.selectionFlags;
                str2 = codecsOfType;
                i = i5;
                str3 = format3.label;
                i2 = i3;
                str = format3.label;
            } else {
                str2 = codecsOfType;
                str = null;
                str3 = str;
                i = -1;
                i2 = 0;
            }
        }
        return Format.createAudioContainerFormat(format3.id, str, format3.containerMimeType, MimeTypes.getMediaMimeType(str2), str2, z ? format3.bitrate : -1, i, -1, null, i2, str3);
    }
}
