package com.google.android.exoplayer2.trackselection;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public abstract class MappingTrackSelector extends TrackSelector {
    @Nullable
    private MappedTrackInfo currentMappedTrackInfo;

    public static final class MappedTrackInfo {
        public static final int RENDERER_SUPPORT_EXCEEDS_CAPABILITIES_TRACKS = 2;
        public static final int RENDERER_SUPPORT_NO_TRACKS = 0;
        public static final int RENDERER_SUPPORT_PLAYABLE_TRACKS = 3;
        public static final int RENDERER_SUPPORT_UNSUPPORTED_TRACKS = 1;
        @Deprecated
        public final int length = this.rendererCount;
        private final int rendererCount;
        private final int[][][] rendererFormatSupports;
        private final int[] rendererMixedMimeTypeAdaptiveSupports;
        private final TrackGroupArray[] rendererTrackGroups;
        private final int[] rendererTrackTypes;
        private final TrackGroupArray unmappedTrackGroups;

        MappedTrackInfo(int[] iArr, TrackGroupArray[] trackGroupArrayArr, int[] iArr2, int[][][] iArr3, TrackGroupArray trackGroupArray) {
            this.rendererTrackTypes = iArr;
            this.rendererTrackGroups = trackGroupArrayArr;
            this.rendererFormatSupports = iArr3;
            this.rendererMixedMimeTypeAdaptiveSupports = iArr2;
            this.unmappedTrackGroups = trackGroupArray;
            this.rendererCount = iArr.length;
        }

        public int getRendererCount() {
            return this.rendererCount;
        }

        public int getRendererType(int i) {
            return this.rendererTrackTypes[i];
        }

        public TrackGroupArray getTrackGroups(int i) {
            return this.rendererTrackGroups[i];
        }

        public int getRendererSupport(int i) {
            i = this.rendererFormatSupports[i];
            int i2 = 0;
            int i3 = 0;
            while (i2 < i.length) {
                int i4 = i3;
                for (int i5 : i[i2]) {
                    int i52;
                    switch (i52 & 7) {
                        case 3:
                            i52 = 2;
                            break;
                        case 4:
                            return 3;
                        default:
                            i52 = 1;
                            break;
                    }
                    i4 = Math.max(i4, i52);
                }
                i2++;
                i3 = i4;
            }
            return i3;
        }

        @Deprecated
        public int getTrackTypeRendererSupport(int i) {
            return getTypeSupport(i);
        }

        public int getTypeSupport(int i) {
            int i2 = 0;
            for (int i3 = 0; i3 < this.rendererCount; i3++) {
                if (this.rendererTrackTypes[i3] == i) {
                    i2 = Math.max(i2, getRendererSupport(i3));
                }
            }
            return i2;
        }

        @Deprecated
        public int getTrackFormatSupport(int i, int i2, int i3) {
            return getTrackSupport(i, i2, i3);
        }

        public int getTrackSupport(int i, int i2, int i3) {
            return this.rendererFormatSupports[i][i2][i3] & 7;
        }

        public int getAdaptiveSupport(int i, int i2, boolean z) {
            int i3 = this.rendererTrackGroups[i].get(i2).length;
            int[] iArr = new int[i3];
            int i4 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                int trackSupport = getTrackSupport(i, i2, i5);
                if (trackSupport == 4 || (z && trackSupport == 3)) {
                    trackSupport = i4 + 1;
                    iArr[i4] = i5;
                    i4 = trackSupport;
                }
            }
            return getAdaptiveSupport(i, i2, Arrays.copyOf(iArr, i4));
        }

        public int getAdaptiveSupport(int i, int i2, int[] iArr) {
            int i3 = 0;
            Object obj = null;
            int i4 = 0;
            int i5 = 0;
            int i6 = 16;
            while (i3 < iArr.length) {
                String str = this.rendererTrackGroups[i].get(i2).getFormat(iArr[i3]).sampleMimeType;
                int i7 = i4 + 1;
                if (i4 == 0) {
                    obj = str;
                } else {
                    i5 = (Util.areEqual(obj, str) ^ 1) | i5;
                }
                i6 = Math.min(i6, this.rendererFormatSupports[i][i2][i3] & 24);
                i3++;
                i4 = i7;
            }
            return i5 != 0 ? Math.min(i6, this.rendererMixedMimeTypeAdaptiveSupports[i]) : i6;
        }

        @Deprecated
        public TrackGroupArray getUnassociatedTrackGroups() {
            return getUnmappedTrackGroups();
        }

        public TrackGroupArray getUnmappedTrackGroups() {
            return this.unmappedTrackGroups;
        }
    }

    protected abstract Pair<RendererConfiguration[], TrackSelection[]> selectTracks(MappedTrackInfo mappedTrackInfo, int[][][] iArr, int[] iArr2) throws ExoPlaybackException;

    @Nullable
    public final MappedTrackInfo getCurrentMappedTrackInfo() {
        return this.currentMappedTrackInfo;
    }

    public final void onSelectionActivated(Object obj) {
        this.currentMappedTrackInfo = (MappedTrackInfo) obj;
    }

    public final TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilitiesArr, TrackGroupArray trackGroupArray) throws ExoPlaybackException {
        int i;
        int[] iArr = new int[(rendererCapabilitiesArr.length + 1)];
        TrackGroup[][] trackGroupArr = new TrackGroup[(rendererCapabilitiesArr.length + 1)][];
        int[][][] iArr2 = new int[(rendererCapabilitiesArr.length + 1)][][];
        for (i = 0; i < trackGroupArr.length; i++) {
            trackGroupArr[i] = new TrackGroup[trackGroupArray.length];
            iArr2[i] = new int[trackGroupArray.length][];
        }
        int[] mixedMimeTypeAdaptationSupports = getMixedMimeTypeAdaptationSupports(rendererCapabilitiesArr);
        for (i = 0; i < trackGroupArray.length; i++) {
            int[] iArr3;
            TrackGroup trackGroup = trackGroupArray.get(i);
            int findRenderer = findRenderer(rendererCapabilitiesArr, trackGroup);
            if (findRenderer == rendererCapabilitiesArr.length) {
                iArr3 = new int[trackGroup.length];
            } else {
                iArr3 = getFormatSupport(rendererCapabilitiesArr[findRenderer], trackGroup);
            }
            int i2 = iArr[findRenderer];
            trackGroupArr[findRenderer][i2] = trackGroup;
            iArr2[findRenderer][i2] = iArr3;
            iArr[findRenderer] = iArr[findRenderer] + 1;
        }
        TrackGroupArray[] trackGroupArrayArr = new TrackGroupArray[rendererCapabilitiesArr.length];
        int[] iArr4 = new int[rendererCapabilitiesArr.length];
        for (int i3 = 0; i3 < rendererCapabilitiesArr.length; i3++) {
            trackGroupArray = iArr[i3];
            trackGroupArrayArr[i3] = new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(trackGroupArr[i3], trackGroupArray));
            iArr2[i3] = (int[][]) Util.nullSafeArrayCopy(iArr2[i3], trackGroupArray);
            iArr4[i3] = rendererCapabilitiesArr[i3].getTrackType();
        }
        int[] iArr5 = mixedMimeTypeAdaptationSupports;
        int[][][] iArr6 = iArr2;
        MappedTrackInfo mappedTrackInfo = new MappedTrackInfo(iArr4, trackGroupArrayArr, iArr5, iArr6, new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(trackGroupArr[rendererCapabilitiesArr.length], iArr[rendererCapabilitiesArr.length])));
        trackGroupArray = selectTracks(mappedTrackInfo, iArr2, mixedMimeTypeAdaptationSupports);
        return new TrackSelectorResult((RendererConfiguration[]) trackGroupArray.first, (TrackSelection[]) trackGroupArray.second, mappedTrackInfo);
    }

    private static int findRenderer(RendererCapabilities[] rendererCapabilitiesArr, TrackGroup trackGroup) throws ExoPlaybackException {
        int length = rendererCapabilitiesArr.length;
        int i = 0;
        int i2 = 0;
        while (i < rendererCapabilitiesArr.length) {
            RendererCapabilities rendererCapabilities = rendererCapabilitiesArr[i];
            int i3 = length;
            for (length = 0; length < trackGroup.length; length++) {
                int supportsFormat = rendererCapabilities.supportsFormat(trackGroup.getFormat(length)) & 7;
                if (supportsFormat > i2) {
                    if (supportsFormat == 4) {
                        return i;
                    }
                    i3 = i;
                    i2 = supportsFormat;
                }
            }
            i++;
            length = i3;
        }
        return length;
    }

    private static int[] getFormatSupport(RendererCapabilities rendererCapabilities, TrackGroup trackGroup) throws ExoPlaybackException {
        int[] iArr = new int[trackGroup.length];
        for (int i = 0; i < trackGroup.length; i++) {
            iArr[i] = rendererCapabilities.supportsFormat(trackGroup.getFormat(i));
        }
        return iArr;
    }

    private static int[] getMixedMimeTypeAdaptationSupports(RendererCapabilities[] rendererCapabilitiesArr) throws ExoPlaybackException {
        int[] iArr = new int[rendererCapabilitiesArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = rendererCapabilitiesArr[i].supportsMixedMimeTypeAdaptation();
        }
        return iArr;
    }
}
