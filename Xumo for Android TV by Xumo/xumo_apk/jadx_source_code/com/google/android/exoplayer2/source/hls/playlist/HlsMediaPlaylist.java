package com.google.android.exoplayer2.source.hls.playlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.offline.StreamKey;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

public final class HlsMediaPlaylist extends HlsPlaylist {
    public static final int PLAYLIST_TYPE_EVENT = 2;
    public static final int PLAYLIST_TYPE_UNKNOWN = 0;
    public static final int PLAYLIST_TYPE_VOD = 1;
    public final int discontinuitySequence;
    public final long durationUs;
    public final boolean hasDiscontinuitySequence;
    public final boolean hasEndTag;
    public final boolean hasProgramDateTime;
    public final long mediaSequence;
    public final int playlistType;
    @Nullable
    public final DrmInitData protectionSchemes;
    public final List<Segment> segments;
    public final long startOffsetUs;
    public final long startTimeUs;
    public final long targetDurationUs;
    public final int version;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaylistType {
    }

    public static final class Segment implements Comparable<Long> {
        public final long byterangeLength;
        public final long byterangeOffset;
        @Nullable
        public final DrmInitData drmInitData;
        public final long durationUs;
        @Nullable
        public final String encryptionIV;
        @Nullable
        public final String fullSegmentEncryptionKeyUri;
        public final boolean hasGapTag;
        @Nullable
        public final Segment initializationSegment;
        public final int relativeDiscontinuitySequence;
        public final long relativeStartTimeUs;
        public final String title;
        public final String url;

        public Segment(String str, long j, long j2) {
            this(str, null, "", 0, -1, C.TIME_UNSET, null, null, null, j, j2, false);
        }

        public Segment(String str, @Nullable Segment segment, String str2, long j, int i, long j2, @Nullable DrmInitData drmInitData, @Nullable String str3, @Nullable String str4, long j3, long j4, boolean z) {
            this.url = str;
            this.initializationSegment = segment;
            this.title = str2;
            this.durationUs = j;
            this.relativeDiscontinuitySequence = i;
            this.relativeStartTimeUs = j2;
            this.drmInitData = drmInitData;
            this.fullSegmentEncryptionKeyUri = str3;
            this.encryptionIV = str4;
            this.byterangeOffset = j3;
            this.byterangeLength = j4;
            this.hasGapTag = z;
        }

        public int compareTo(@NonNull Long l) {
            if (this.relativeStartTimeUs > l.longValue()) {
                return 1;
            }
            return this.relativeStartTimeUs < l.longValue() ? -1 : null;
        }
    }

    public HlsMediaPlaylist copy(List<StreamKey> list) {
        return this;
    }

    public HlsMediaPlaylist(int i, String str, List<String> list, long j, long j2, boolean z, int i2, long j3, int i3, long j4, boolean z2, boolean z3, boolean z4, @Nullable DrmInitData drmInitData, List<Segment> list2) {
        long j5;
        String str2 = str;
        List<String> list3 = list;
        super(str, list, z2);
        this.playlistType = i;
        this.startTimeUs = j2;
        this.hasDiscontinuitySequence = z;
        this.discontinuitySequence = i2;
        this.mediaSequence = j3;
        this.version = i3;
        this.targetDurationUs = j4;
        this.hasEndTag = z3;
        this.hasProgramDateTime = z4;
        this.protectionSchemes = drmInitData;
        this.segments = Collections.unmodifiableList(list2);
        if (list2.isEmpty()) {
            r0.durationUs = 0;
        } else {
            Segment segment = (Segment) list2.get(list2.size() - 1);
            r0.durationUs = segment.relativeStartTimeUs + segment.durationUs;
        }
        if (j == C.TIME_UNSET) {
            j5 = C.TIME_UNSET;
        } else if (j >= 0) {
            j5 = j;
        } else {
            j5 = r0.durationUs + j;
        }
        r0.startOffsetUs = j5;
    }

    public boolean isNewerThan(HlsMediaPlaylist hlsMediaPlaylist) {
        boolean z = true;
        if (hlsMediaPlaylist != null) {
            if (this.mediaSequence <= hlsMediaPlaylist.mediaSequence) {
                if (this.mediaSequence < hlsMediaPlaylist.mediaSequence) {
                    return false;
                }
                int size = this.segments.size();
                int size2 = hlsMediaPlaylist.segments.size();
                if (size <= size2) {
                    if (size != size2 || !this.hasEndTag || hlsMediaPlaylist.hasEndTag != null) {
                        z = false;
                    }
                }
                return z;
            }
        }
        return true;
    }

    public long getEndTimeUs() {
        return this.startTimeUs + this.durationUs;
    }

    public HlsMediaPlaylist copyWith(long j, int i) {
        return new HlsMediaPlaylist(this.playlistType, this.baseUri, this.tags, this.startOffsetUs, j, true, i, this.mediaSequence, this.version, this.targetDurationUs, this.hasIndependentSegments, this.hasEndTag, this.hasProgramDateTime, this.protectionSchemes, this.segments);
    }

    public HlsMediaPlaylist copyWithEndTag() {
        if (this.hasEndTag) {
            return r0;
        }
        HlsMediaPlaylist hlsMediaPlaylist = r2;
        HlsMediaPlaylist hlsMediaPlaylist2 = new HlsMediaPlaylist(r0.playlistType, r0.baseUri, r0.tags, r0.startOffsetUs, r0.startTimeUs, r0.hasDiscontinuitySequence, r0.discontinuitySequence, r0.mediaSequence, r0.version, r0.targetDurationUs, r0.hasIndependentSegments, true, r0.hasProgramDateTime, r0.protectionSchemes, r0.segments);
        return hlsMediaPlaylist;
    }
}
