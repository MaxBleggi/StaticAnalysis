package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.DataChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist.HlsUrl;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist.Segment;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.trackselection.BaseTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

class HlsChunkSource {
    private final DataSource encryptionDataSource;
    private byte[] encryptionIv;
    private String encryptionIvString;
    private byte[] encryptionKey;
    private Uri encryptionKeyUri;
    private HlsUrl expectedPlaylistUrl;
    private final HlsExtractorFactory extractorFactory;
    private IOException fatalError;
    private boolean independentSegments;
    private boolean isTimestampMaster;
    private long liveEdgeInPeriodTimeUs = 1;
    private final DataSource mediaDataSource;
    private final List<Format> muxedCaptionFormats;
    private final HlsPlaylistTracker playlistTracker;
    private byte[] scratchSpace;
    private boolean seenExpectedPlaylistError;
    private final TimestampAdjusterProvider timestampAdjusterProvider;
    private final TrackGroup trackGroup;
    private TrackSelection trackSelection;
    private final HlsUrl[] variants;

    public static final class HlsChunkHolder {
        public Chunk chunk;
        public boolean endOfStream;
        public HlsUrl playlist;

        public HlsChunkHolder() {
            clear();
        }

        public void clear() {
            this.chunk = null;
            this.endOfStream = false;
            this.playlist = null;
        }
    }

    private static final class HlsMediaPlaylistSegmentIterator extends BaseMediaChunkIterator {
        private final HlsMediaPlaylist playlist;
        private final long startOfPlaylistInPeriodUs;

        public HlsMediaPlaylistSegmentIterator(HlsMediaPlaylist hlsMediaPlaylist, long j, int i) {
            super((long) i, (long) (hlsMediaPlaylist.segments.size() - 1));
            this.playlist = hlsMediaPlaylist;
            this.startOfPlaylistInPeriodUs = j;
        }

        public DataSpec getDataSpec() {
            checkInBounds();
            Segment segment = (Segment) this.playlist.segments.get((int) getCurrentIndex());
            return new DataSpec(UriUtil.resolveToUri(this.playlist.baseUri, segment.url), segment.byterangeOffset, segment.byterangeLength, null);
        }

        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.startOfPlaylistInPeriodUs + ((Segment) this.playlist.segments.get((int) getCurrentIndex())).relativeStartTimeUs;
        }

        public long getChunkEndTimeUs() {
            checkInBounds();
            Segment segment = (Segment) this.playlist.segments.get((int) getCurrentIndex());
            return (this.startOfPlaylistInPeriodUs + segment.relativeStartTimeUs) + segment.durationUs;
        }
    }

    private static final class InitializationTrackSelection extends BaseTrackSelection {
        private int selectedIndex;

        public Object getSelectionData() {
            return null;
        }

        public int getSelectionReason() {
            return 0;
        }

        public InitializationTrackSelection(TrackGroup trackGroup, int[] iArr) {
            super(trackGroup, iArr);
            this.selectedIndex = indexOf((Format) trackGroup.getFormat(null));
        }

        public void updateSelectedTrack(long j, long j2, long j3, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
            j = SystemClock.elapsedRealtime();
            if (isBlacklisted(this.selectedIndex, j) != null) {
                j2 = this.length - 1;
                while (j2 >= null) {
                    if (isBlacklisted(j2, j)) {
                        j2--;
                    } else {
                        this.selectedIndex = j2;
                        return;
                    }
                }
                throw new IllegalStateException();
            }
        }

        public int getSelectedIndex() {
            return this.selectedIndex;
        }
    }

    private static final class EncryptionKeyChunk extends DataChunk {
        public final String iv;
        private byte[] result;

        public EncryptionKeyChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, Object obj, byte[] bArr, String str) {
            super(dataSource, dataSpec, 3, format, i, obj, bArr);
            this.iv = str;
        }

        protected void consume(byte[] bArr, int i) throws IOException {
            this.result = Arrays.copyOf(bArr, i);
        }

        public byte[] getResult() {
            return this.result;
        }
    }

    public HlsChunkSource(HlsExtractorFactory hlsExtractorFactory, HlsPlaylistTracker hlsPlaylistTracker, HlsUrl[] hlsUrlArr, HlsDataSourceFactory hlsDataSourceFactory, @Nullable TransferListener transferListener, TimestampAdjusterProvider timestampAdjusterProvider, List<Format> list) {
        this.extractorFactory = hlsExtractorFactory;
        this.playlistTracker = hlsPlaylistTracker;
        this.variants = hlsUrlArr;
        this.timestampAdjusterProvider = timestampAdjusterProvider;
        this.muxedCaptionFormats = list;
        Format[] formatArr = new Format[hlsUrlArr.length];
        hlsPlaylistTracker = new int[hlsUrlArr.length];
        for (timestampAdjusterProvider = null; timestampAdjusterProvider < hlsUrlArr.length; timestampAdjusterProvider++) {
            formatArr[timestampAdjusterProvider] = hlsUrlArr[timestampAdjusterProvider].format;
            hlsPlaylistTracker[timestampAdjusterProvider] = timestampAdjusterProvider;
        }
        this.mediaDataSource = hlsDataSourceFactory.createDataSource(1);
        if (transferListener != null) {
            this.mediaDataSource.addTransferListener(transferListener);
        }
        this.encryptionDataSource = hlsDataSourceFactory.createDataSource(3);
        this.trackGroup = new TrackGroup(formatArr);
        this.trackSelection = new InitializationTrackSelection(this.trackGroup, hlsPlaylistTracker);
    }

    public void maybeThrowError() throws IOException {
        if (this.fatalError != null) {
            throw this.fatalError;
        } else if (this.expectedPlaylistUrl != null && this.seenExpectedPlaylistError) {
            this.playlistTracker.maybeThrowPlaylistRefreshError(this.expectedPlaylistUrl);
        }
    }

    public TrackGroup getTrackGroup() {
        return this.trackGroup;
    }

    public void selectTracks(TrackSelection trackSelection) {
        this.trackSelection = trackSelection;
    }

    public TrackSelection getTrackSelection() {
        return this.trackSelection;
    }

    public void reset() {
        this.fatalError = null;
    }

    public void setIsTimestampMaster(boolean z) {
        this.isTimestampMaster = z;
    }

    public void getNextChunk(long j, long j2, List<HlsMediaChunk> list, HlsChunkHolder hlsChunkHolder) {
        HlsMediaChunk hlsMediaChunk;
        int i;
        long max;
        int selectedIndexInTrackGroup;
        int i2;
        boolean z;
        HlsUrl hlsUrl;
        HlsMediaPlaylist playlistSnapshot;
        long initialStartTimeUs;
        HlsMediaChunk hlsMediaChunk2;
        long chunkMediaSequence;
        long j3;
        int i3;
        HlsUrl hlsUrl2;
        int i4;
        Segment segment;
        Uri resolveToUri;
        Segment segment2;
        HlsChunkSource hlsChunkSource = this;
        long j4 = j2;
        HlsChunkHolder hlsChunkHolder2 = hlsChunkHolder;
        if (list.isEmpty()) {
            List<HlsMediaChunk> list2 = list;
            hlsMediaChunk = null;
        } else {
            hlsMediaChunk = (HlsMediaChunk) list.get(list.size() - 1);
        }
        if (hlsMediaChunk == null) {
            i = -1;
        } else {
            i = hlsChunkSource.trackGroup.indexOf(hlsMediaChunk.trackFormat);
        }
        long j5 = j4 - j;
        long resolveTimeToLiveEdgeUs = resolveTimeToLiveEdgeUs(j);
        if (!(hlsMediaChunk == null || hlsChunkSource.independentSegments)) {
            long durationUs = hlsMediaChunk.getDurationUs();
            j5 = Math.max(0, j5 - durationUs);
            if (resolveTimeToLiveEdgeUs != C.TIME_UNSET) {
                max = Math.max(0, resolveTimeToLiveEdgeUs - durationUs);
                hlsChunkSource.trackSelection.updateSelectedTrack(j, j5, max, list, createMediaChunkIterators(hlsMediaChunk, j4));
                selectedIndexInTrackGroup = hlsChunkSource.trackSelection.getSelectedIndexInTrackGroup();
                i2 = 0;
                z = i == selectedIndexInTrackGroup;
                hlsUrl = hlsChunkSource.variants[selectedIndexInTrackGroup];
                if (hlsChunkSource.playlistTracker.isSnapshotValid(hlsUrl)) {
                    hlsChunkHolder2.playlist = hlsUrl;
                    boolean z2 = hlsChunkSource.seenExpectedPlaylistError;
                    if (hlsChunkSource.expectedPlaylistUrl == hlsUrl) {
                        i2 = 1;
                    }
                    hlsChunkSource.seenExpectedPlaylistError = z2 & i2;
                    hlsChunkSource.expectedPlaylistUrl = hlsUrl;
                }
                playlistSnapshot = hlsChunkSource.playlistTracker.getPlaylistSnapshot(hlsUrl);
                hlsChunkSource.independentSegments = playlistSnapshot.hasIndependentSegments;
                updateLiveEdgeTimeUs(playlistSnapshot);
                initialStartTimeUs = playlistSnapshot.startTimeUs - hlsChunkSource.playlistTracker.getInitialStartTimeUs();
                hlsMediaChunk2 = hlsMediaChunk;
                int i5 = i;
                chunkMediaSequence = getChunkMediaSequence(hlsMediaChunk, z, playlistSnapshot, initialStartTimeUs, j2);
                if (chunkMediaSequence < playlistSnapshot.mediaSequence) {
                    j3 = chunkMediaSequence;
                    i3 = selectedIndexInTrackGroup;
                    hlsUrl2 = hlsUrl;
                } else if (hlsMediaChunk2 == null && z) {
                    hlsUrl2 = hlsChunkSource.variants[i5];
                    playlistSnapshot = hlsChunkSource.playlistTracker.getPlaylistSnapshot(hlsUrl2);
                    initialStartTimeUs = playlistSnapshot.startTimeUs - hlsChunkSource.playlistTracker.getInitialStartTimeUs();
                    j3 = hlsMediaChunk2.getNextChunkIndex();
                    i3 = i5;
                } else {
                    hlsChunkSource.fatalError = new BehindLiveWindowException();
                    return;
                }
                i4 = (int) (j3 - playlistSnapshot.mediaSequence);
                if (i4 < playlistSnapshot.segments.size()) {
                    if (playlistSnapshot.hasEndTag) {
                        hlsChunkHolder2.playlist = hlsUrl2;
                        boolean z3 = hlsChunkSource.seenExpectedPlaylistError;
                        if (hlsChunkSource.expectedPlaylistUrl == hlsUrl2) {
                            i2 = 1;
                        }
                        hlsChunkSource.seenExpectedPlaylistError = z3 & i2;
                        hlsChunkSource.expectedPlaylistUrl = hlsUrl2;
                    } else {
                        hlsChunkHolder2.endOfStream = true;
                    }
                    return;
                }
                hlsChunkSource.seenExpectedPlaylistError = false;
                HlsUrl hlsUrl3 = null;
                hlsChunkSource.expectedPlaylistUrl = null;
                segment = (Segment) playlistSnapshot.segments.get(i4);
                if (segment.fullSegmentEncryptionKeyUri == null) {
                    resolveToUri = UriUtil.resolveToUri(playlistSnapshot.baseUri, segment.fullSegmentEncryptionKeyUri);
                    if (!resolveToUri.equals(hlsChunkSource.encryptionKeyUri)) {
                        hlsChunkHolder2.chunk = newEncryptionKeyChunk(resolveToUri, segment.encryptionIV, i3, hlsChunkSource.trackSelection.getSelectionReason(), hlsChunkSource.trackSelection.getSelectionData());
                        return;
                    } else if (!Util.areEqual(segment.encryptionIV, hlsChunkSource.encryptionIvString)) {
                        setEncryptionData(resolveToUri, segment.encryptionIV, hlsChunkSource.encryptionKey);
                    }
                } else {
                    clearEncryptionData();
                }
                segment2 = segment.initializationSegment;
                if (segment2 != null) {
                    HlsUrl dataSpec = new DataSpec(UriUtil.resolveToUri(playlistSnapshot.baseUri, segment2.url), segment2.byterangeOffset, segment2.byterangeLength, null);
                }
                long j6 = segment.relativeStartTimeUs + initialStartTimeUs;
                long j7 = j6;
                i = playlistSnapshot.discontinuitySequence + segment.relativeDiscontinuitySequence;
                int i6 = i;
                TimestampAdjuster adjuster = hlsChunkSource.timestampAdjusterProvider.getAdjuster(i);
                DataSpec dataSpec2 = r32;
                DataSpec dataSpec3 = new DataSpec(UriUtil.resolveToUri(playlistSnapshot.baseUri, segment.url), segment.byterangeOffset, segment.byterangeLength, null);
                hlsChunkHolder2.chunk = new HlsMediaChunk(hlsChunkSource.extractorFactory, hlsChunkSource.mediaDataSource, dataSpec2, hlsUrl3, hlsUrl2, hlsChunkSource.muxedCaptionFormats, hlsChunkSource.trackSelection.getSelectionReason(), hlsChunkSource.trackSelection.getSelectionData(), j7, j6 + segment.durationUs, j3, i6, segment.hasGapTag, hlsChunkSource.isTimestampMaster, adjuster, hlsMediaChunk2, segment.drmInitData, hlsChunkSource.encryptionKey, hlsChunkSource.encryptionIv);
                return;
            }
        }
        max = resolveTimeToLiveEdgeUs;
        hlsChunkSource.trackSelection.updateSelectedTrack(j, j5, max, list, createMediaChunkIterators(hlsMediaChunk, j4));
        selectedIndexInTrackGroup = hlsChunkSource.trackSelection.getSelectedIndexInTrackGroup();
        i2 = 0;
        if (i == selectedIndexInTrackGroup) {
        }
        hlsUrl = hlsChunkSource.variants[selectedIndexInTrackGroup];
        if (hlsChunkSource.playlistTracker.isSnapshotValid(hlsUrl)) {
            playlistSnapshot = hlsChunkSource.playlistTracker.getPlaylistSnapshot(hlsUrl);
            hlsChunkSource.independentSegments = playlistSnapshot.hasIndependentSegments;
            updateLiveEdgeTimeUs(playlistSnapshot);
            initialStartTimeUs = playlistSnapshot.startTimeUs - hlsChunkSource.playlistTracker.getInitialStartTimeUs();
            hlsMediaChunk2 = hlsMediaChunk;
            int i52 = i;
            chunkMediaSequence = getChunkMediaSequence(hlsMediaChunk, z, playlistSnapshot, initialStartTimeUs, j2);
            if (chunkMediaSequence < playlistSnapshot.mediaSequence) {
                j3 = chunkMediaSequence;
                i3 = selectedIndexInTrackGroup;
                hlsUrl2 = hlsUrl;
            } else {
                if (hlsMediaChunk2 == null) {
                }
                hlsChunkSource.fatalError = new BehindLiveWindowException();
                return;
            }
            i4 = (int) (j3 - playlistSnapshot.mediaSequence);
            if (i4 < playlistSnapshot.segments.size()) {
                hlsChunkSource.seenExpectedPlaylistError = false;
                HlsUrl hlsUrl32 = null;
                hlsChunkSource.expectedPlaylistUrl = null;
                segment = (Segment) playlistSnapshot.segments.get(i4);
                if (segment.fullSegmentEncryptionKeyUri == null) {
                    clearEncryptionData();
                } else {
                    resolveToUri = UriUtil.resolveToUri(playlistSnapshot.baseUri, segment.fullSegmentEncryptionKeyUri);
                    if (!resolveToUri.equals(hlsChunkSource.encryptionKeyUri)) {
                        hlsChunkHolder2.chunk = newEncryptionKeyChunk(resolveToUri, segment.encryptionIV, i3, hlsChunkSource.trackSelection.getSelectionReason(), hlsChunkSource.trackSelection.getSelectionData());
                        return;
                    } else if (Util.areEqual(segment.encryptionIV, hlsChunkSource.encryptionIvString)) {
                        setEncryptionData(resolveToUri, segment.encryptionIV, hlsChunkSource.encryptionKey);
                    }
                }
                segment2 = segment.initializationSegment;
                if (segment2 != null) {
                    HlsUrl dataSpec4 = new DataSpec(UriUtil.resolveToUri(playlistSnapshot.baseUri, segment2.url), segment2.byterangeOffset, segment2.byterangeLength, null);
                }
                long j62 = segment.relativeStartTimeUs + initialStartTimeUs;
                long j72 = j62;
                i = playlistSnapshot.discontinuitySequence + segment.relativeDiscontinuitySequence;
                int i62 = i;
                TimestampAdjuster adjuster2 = hlsChunkSource.timestampAdjusterProvider.getAdjuster(i);
                DataSpec dataSpec22 = dataSpec3;
                DataSpec dataSpec32 = new DataSpec(UriUtil.resolveToUri(playlistSnapshot.baseUri, segment.url), segment.byterangeOffset, segment.byterangeLength, null);
                hlsChunkHolder2.chunk = new HlsMediaChunk(hlsChunkSource.extractorFactory, hlsChunkSource.mediaDataSource, dataSpec22, hlsUrl32, hlsUrl2, hlsChunkSource.muxedCaptionFormats, hlsChunkSource.trackSelection.getSelectionReason(), hlsChunkSource.trackSelection.getSelectionData(), j72, j62 + segment.durationUs, j3, i62, segment.hasGapTag, hlsChunkSource.isTimestampMaster, adjuster2, hlsMediaChunk2, segment.drmInitData, hlsChunkSource.encryptionKey, hlsChunkSource.encryptionIv);
                return;
            }
            if (playlistSnapshot.hasEndTag) {
                hlsChunkHolder2.playlist = hlsUrl2;
                boolean z32 = hlsChunkSource.seenExpectedPlaylistError;
                if (hlsChunkSource.expectedPlaylistUrl == hlsUrl2) {
                    i2 = 1;
                }
                hlsChunkSource.seenExpectedPlaylistError = z32 & i2;
                hlsChunkSource.expectedPlaylistUrl = hlsUrl2;
            } else {
                hlsChunkHolder2.endOfStream = true;
            }
            return;
        }
        hlsChunkHolder2.playlist = hlsUrl;
        boolean z22 = hlsChunkSource.seenExpectedPlaylistError;
        if (hlsChunkSource.expectedPlaylistUrl == hlsUrl) {
            i2 = 1;
        }
        hlsChunkSource.seenExpectedPlaylistError = z22 & i2;
        hlsChunkSource.expectedPlaylistUrl = hlsUrl;
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        if (chunk instanceof EncryptionKeyChunk) {
            EncryptionKeyChunk encryptionKeyChunk = (EncryptionKeyChunk) chunk;
            this.scratchSpace = encryptionKeyChunk.getDataHolder();
            setEncryptionData(encryptionKeyChunk.dataSpec.uri, encryptionKeyChunk.iv, encryptionKeyChunk.getResult());
        }
    }

    public boolean maybeBlacklistTrack(Chunk chunk, long j) {
        return this.trackSelection.blacklist(this.trackSelection.indexOf(this.trackGroup.indexOf(chunk.trackFormat)), j);
    }

    public boolean onPlaylistError(HlsUrl hlsUrl, long j) {
        int indexOf = this.trackGroup.indexOf(hlsUrl.format);
        boolean z = true;
        if (indexOf == -1) {
            return true;
        }
        indexOf = this.trackSelection.indexOf(indexOf);
        if (indexOf == -1) {
            return true;
        }
        this.seenExpectedPlaylistError = (this.expectedPlaylistUrl == hlsUrl ? true : null) | this.seenExpectedPlaylistError;
        if (j != C.TIME_UNSET) {
            if (this.trackSelection.blacklist(indexOf, j) == null) {
                z = false;
            }
        }
        return z;
    }

    public MediaChunkIterator[] createMediaChunkIterators(@Nullable HlsMediaChunk hlsMediaChunk, long j) {
        int i;
        HlsChunkSource hlsChunkSource = this;
        HlsMediaChunk hlsMediaChunk2 = hlsMediaChunk;
        if (hlsMediaChunk2 == null) {
            i = -1;
        } else {
            i = hlsChunkSource.trackGroup.indexOf(hlsMediaChunk2.trackFormat);
        }
        MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[hlsChunkSource.trackSelection.length()];
        for (int i2 = 0; i2 < mediaChunkIteratorArr.length; i2++) {
            int indexInTrackGroup = hlsChunkSource.trackSelection.getIndexInTrackGroup(i2);
            HlsUrl hlsUrl = hlsChunkSource.variants[indexInTrackGroup];
            if (hlsChunkSource.playlistTracker.isSnapshotValid(hlsUrl)) {
                HlsMediaPlaylist playlistSnapshot = hlsChunkSource.playlistTracker.getPlaylistSnapshot(hlsUrl);
                long initialStartTimeUs = playlistSnapshot.startTimeUs - hlsChunkSource.playlistTracker.getInitialStartTimeUs();
                long j2 = initialStartTimeUs;
                long chunkMediaSequence = getChunkMediaSequence(hlsMediaChunk, indexInTrackGroup != i, playlistSnapshot, initialStartTimeUs, j);
                if (chunkMediaSequence < playlistSnapshot.mediaSequence) {
                    mediaChunkIteratorArr[i2] = MediaChunkIterator.EMPTY;
                } else {
                    mediaChunkIteratorArr[i2] = new HlsMediaPlaylistSegmentIterator(playlistSnapshot, j2, (int) (chunkMediaSequence - playlistSnapshot.mediaSequence));
                }
            } else {
                mediaChunkIteratorArr[i2] = MediaChunkIterator.EMPTY;
            }
        }
        return mediaChunkIteratorArr;
    }

    private long getChunkMediaSequence(@Nullable HlsMediaChunk hlsMediaChunk, boolean z, HlsMediaPlaylist hlsMediaPlaylist, long j, long j2) {
        if (hlsMediaChunk != null) {
            if (!z) {
                return hlsMediaChunk.getNextChunkIndex();
            }
        }
        long j3 = hlsMediaPlaylist.durationUs + j;
        if (hlsMediaChunk != null) {
            if (!this.independentSegments) {
                j2 = hlsMediaChunk.startTimeUs;
            }
        }
        if (!hlsMediaPlaylist.hasEndTag && j2 >= j3) {
            return hlsMediaPlaylist.mediaSequence + ((long) hlsMediaPlaylist.segments.size());
        }
        boolean z2;
        j2 -= j;
        List list = hlsMediaPlaylist.segments;
        Comparable valueOf = Long.valueOf(j2);
        if (this.playlistTracker.isLive()) {
            if (hlsMediaChunk != null) {
                z2 = null;
                return ((long) Util.binarySearchFloor(list, valueOf, (boolean) 1, z2)) + hlsMediaPlaylist.mediaSequence;
            }
        }
        z2 = true;
        return ((long) Util.binarySearchFloor(list, valueOf, (boolean) 1, z2)) + hlsMediaPlaylist.mediaSequence;
    }

    private long resolveTimeToLiveEdgeUs(long j) {
        if ((this.liveEdgeInPeriodTimeUs != C.TIME_UNSET ? 1 : null) != null) {
            return this.liveEdgeInPeriodTimeUs - j;
        }
        return C.TIME_UNSET;
    }

    private void updateLiveEdgeTimeUs(HlsMediaPlaylist hlsMediaPlaylist) {
        long j;
        if (hlsMediaPlaylist.hasEndTag) {
            j = C.TIME_UNSET;
        } else {
            j = hlsMediaPlaylist.getEndTimeUs() - this.playlistTracker.getInitialStartTimeUs();
        }
        this.liveEdgeInPeriodTimeUs = j;
    }

    private EncryptionKeyChunk newEncryptionKeyChunk(Uri uri, String str, int i, int i2, Object obj) {
        return new EncryptionKeyChunk(this.encryptionDataSource, new DataSpec(uri, 0, -1, null, 1), this.variants[i].format, i2, obj, this.scratchSpace, str);
    }

    private void setEncryptionData(Uri uri, String str, byte[] bArr) {
        Object toByteArray = new BigInteger(Util.toLowerInvariant(str).startsWith("0x") ? str.substring(2) : str, 16).toByteArray();
        Object obj = new byte[16];
        int length = toByteArray.length > 16 ? toByteArray.length - 16 : 0;
        System.arraycopy(toByteArray, length, obj, (obj.length - toByteArray.length) + length, toByteArray.length - length);
        this.encryptionKeyUri = uri;
        this.encryptionKey = bArr;
        this.encryptionIvString = str;
        this.encryptionIv = obj;
    }

    private void clearEncryptionData() {
        this.encryptionKeyUri = null;
        this.encryptionKey = null;
        this.encryptionIvString = null;
        this.encryptionIv = null;
    }
}
