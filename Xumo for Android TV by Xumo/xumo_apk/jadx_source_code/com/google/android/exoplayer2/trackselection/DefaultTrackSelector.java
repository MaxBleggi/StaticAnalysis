package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelection.Factory;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

public class DefaultTrackSelector extends MappingTrackSelector {
    private static final float FRACTION_TO_CONSIDER_FULLSCREEN = 0.98f;
    private static final int[] NO_TRACKS = new int[0];
    private static final int WITHIN_RENDERER_CAPABILITIES_BONUS = 1000;
    private final Factory adaptiveTrackSelectionFactory;
    private final AtomicReference<Parameters> parametersReference;

    private static final class AudioConfigurationTuple {
        public final int channelCount;
        @Nullable
        public final String mimeType;
        public final int sampleRate;

        public AudioConfigurationTuple(int i, int i2, @Nullable String str) {
            this.channelCount = i;
            this.sampleRate = i2;
            this.mimeType = str;
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj != null) {
                if (getClass() == obj.getClass()) {
                    AudioConfigurationTuple audioConfigurationTuple = (AudioConfigurationTuple) obj;
                    if (this.channelCount != audioConfigurationTuple.channelCount || this.sampleRate != audioConfigurationTuple.sampleRate || TextUtils.equals(this.mimeType, audioConfigurationTuple.mimeType) == null) {
                        z = false;
                    }
                    return z;
                }
            }
            return false;
        }

        public int hashCode() {
            return (((this.channelCount * 31) + this.sampleRate) * 31) + (this.mimeType != null ? this.mimeType.hashCode() : 0);
        }
    }

    private static final class AudioTrackScore implements Comparable<AudioTrackScore> {
        private final int bitrate;
        private final int channelCount;
        private final int defaultSelectionFlagScore;
        private final int matchLanguageScore;
        private final Parameters parameters;
        private final int sampleRate;
        private final int withinRendererCapabilitiesScore;

        public AudioTrackScore(Format format, Parameters parameters, int i) {
            this.parameters = parameters;
            this.withinRendererCapabilitiesScore = DefaultTrackSelector.isSupported(i, false);
            this.matchLanguageScore = DefaultTrackSelector.formatHasLanguage(format, parameters.preferredAudioLanguage);
            i = 1;
            if ((format.selectionFlags & 1) == null) {
                i = 0;
            }
            this.defaultSelectionFlagScore = i;
            this.channelCount = format.channelCount;
            this.sampleRate = format.sampleRate;
            this.bitrate = format.bitrate;
        }

        public int compareTo(@NonNull AudioTrackScore audioTrackScore) {
            if (this.withinRendererCapabilitiesScore != audioTrackScore.withinRendererCapabilitiesScore) {
                return DefaultTrackSelector.compareInts(this.withinRendererCapabilitiesScore, audioTrackScore.withinRendererCapabilitiesScore);
            }
            if (this.matchLanguageScore != audioTrackScore.matchLanguageScore) {
                return DefaultTrackSelector.compareInts(this.matchLanguageScore, audioTrackScore.matchLanguageScore);
            }
            if (this.defaultSelectionFlagScore != audioTrackScore.defaultSelectionFlagScore) {
                return DefaultTrackSelector.compareInts(this.defaultSelectionFlagScore, audioTrackScore.defaultSelectionFlagScore);
            }
            if (this.parameters.forceLowestBitrate) {
                return DefaultTrackSelector.compareInts(audioTrackScore.bitrate, this.bitrate);
            }
            int i = 1;
            if (this.withinRendererCapabilitiesScore != 1) {
                i = -1;
            }
            if (this.channelCount != audioTrackScore.channelCount) {
                return i * DefaultTrackSelector.compareInts(this.channelCount, audioTrackScore.channelCount);
            }
            if (this.sampleRate != audioTrackScore.sampleRate) {
                return i * DefaultTrackSelector.compareInts(this.sampleRate, audioTrackScore.sampleRate);
            }
            return i * DefaultTrackSelector.compareInts(this.bitrate, audioTrackScore.bitrate);
        }
    }

    public static final class Parameters implements Parcelable {
        public static final Creator<Parameters> CREATOR = new Creator<Parameters>() {
            public Parameters createFromParcel(Parcel parcel) {
                return new Parameters(parcel);
            }

            public Parameters[] newArray(int i) {
                return new Parameters[i];
            }
        };
        public static final Parameters DEFAULT = new Parameters();
        public final boolean allowMixedMimeAdaptiveness;
        public final boolean allowNonSeamlessAdaptiveness;
        public final int disabledTextTrackSelectionFlags;
        public final boolean exceedRendererCapabilitiesIfNecessary;
        public final boolean exceedVideoConstraintsIfNecessary;
        public final boolean forceHighestSupportedBitrate;
        public final boolean forceLowestBitrate;
        public final int maxVideoBitrate;
        public final int maxVideoFrameRate;
        public final int maxVideoHeight;
        public final int maxVideoWidth;
        @Nullable
        public final String preferredAudioLanguage;
        @Nullable
        public final String preferredTextLanguage;
        private final SparseBooleanArray rendererDisabledFlags;
        public final boolean selectUndeterminedTextLanguage;
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides;
        public final int tunnelingAudioSessionId;
        public final int viewportHeight;
        public final boolean viewportOrientationMayChange;
        public final int viewportWidth;

        public int describeContents() {
            return 0;
        }

        private Parameters() {
            SparseArray sparseArray = r2;
            SparseArray sparseArray2 = new SparseArray();
            SparseBooleanArray sparseBooleanArray = r3;
            SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray();
            this(sparseArray, sparseBooleanArray, null, null, false, 0, false, false, false, true, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, true, true, Integer.MAX_VALUE, Integer.MAX_VALUE, true, 0);
        }

        Parameters(SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray, SparseBooleanArray sparseBooleanArray, @Nullable String str, @Nullable String str2, boolean z, int i, boolean z2, boolean z3, boolean z4, boolean z5, int i2, int i3, int i4, int i5, boolean z6, boolean z7, int i6, int i7, boolean z8, int i8) {
            this.selectionOverrides = sparseArray;
            this.rendererDisabledFlags = sparseBooleanArray;
            this.preferredAudioLanguage = Util.normalizeLanguageCode(str);
            this.preferredTextLanguage = Util.normalizeLanguageCode(str2);
            this.selectUndeterminedTextLanguage = z;
            this.disabledTextTrackSelectionFlags = i;
            this.forceLowestBitrate = z2;
            this.forceHighestSupportedBitrate = z3;
            this.allowMixedMimeAdaptiveness = z4;
            this.allowNonSeamlessAdaptiveness = z5;
            this.maxVideoWidth = i2;
            this.maxVideoHeight = i3;
            this.maxVideoFrameRate = i4;
            this.maxVideoBitrate = i5;
            this.exceedVideoConstraintsIfNecessary = z6;
            this.exceedRendererCapabilitiesIfNecessary = z7;
            this.viewportWidth = i6;
            this.viewportHeight = i7;
            this.viewportOrientationMayChange = z8;
            this.tunnelingAudioSessionId = i8;
        }

        Parameters(Parcel parcel) {
            this.selectionOverrides = readSelectionOverrides(parcel);
            this.rendererDisabledFlags = parcel.readSparseBooleanArray();
            this.preferredAudioLanguage = parcel.readString();
            this.preferredTextLanguage = parcel.readString();
            this.selectUndeterminedTextLanguage = Util.readBoolean(parcel);
            this.disabledTextTrackSelectionFlags = parcel.readInt();
            this.forceLowestBitrate = Util.readBoolean(parcel);
            this.forceHighestSupportedBitrate = Util.readBoolean(parcel);
            this.allowMixedMimeAdaptiveness = Util.readBoolean(parcel);
            this.allowNonSeamlessAdaptiveness = Util.readBoolean(parcel);
            this.maxVideoWidth = parcel.readInt();
            this.maxVideoHeight = parcel.readInt();
            this.maxVideoFrameRate = parcel.readInt();
            this.maxVideoBitrate = parcel.readInt();
            this.exceedVideoConstraintsIfNecessary = Util.readBoolean(parcel);
            this.exceedRendererCapabilitiesIfNecessary = Util.readBoolean(parcel);
            this.viewportWidth = parcel.readInt();
            this.viewportHeight = parcel.readInt();
            this.viewportOrientationMayChange = Util.readBoolean(parcel);
            this.tunnelingAudioSessionId = parcel.readInt();
        }

        public final boolean getRendererDisabled(int i) {
            return this.rendererDisabledFlags.get(i);
        }

        public final boolean hasSelectionOverride(int i, TrackGroupArray trackGroupArray) {
            Map map = (Map) this.selectionOverrides.get(i);
            return (map == null || map.containsKey(trackGroupArray) == 0) ? false : true;
        }

        @Nullable
        public final SelectionOverride getSelectionOverride(int i, TrackGroupArray trackGroupArray) {
            Map map = (Map) this.selectionOverrides.get(i);
            return map != null ? (SelectionOverride) map.get(trackGroupArray) : 0;
        }

        public ParametersBuilder buildUpon() {
            return new ParametersBuilder();
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj != null) {
                if (getClass() == obj.getClass()) {
                    Parameters parameters = (Parameters) obj;
                    if (this.selectUndeterminedTextLanguage != parameters.selectUndeterminedTextLanguage || this.disabledTextTrackSelectionFlags != parameters.disabledTextTrackSelectionFlags || this.forceLowestBitrate != parameters.forceLowestBitrate || this.forceHighestSupportedBitrate != parameters.forceHighestSupportedBitrate || this.allowMixedMimeAdaptiveness != parameters.allowMixedMimeAdaptiveness || this.allowNonSeamlessAdaptiveness != parameters.allowNonSeamlessAdaptiveness || this.maxVideoWidth != parameters.maxVideoWidth || this.maxVideoHeight != parameters.maxVideoHeight || this.maxVideoFrameRate != parameters.maxVideoFrameRate || this.exceedVideoConstraintsIfNecessary != parameters.exceedVideoConstraintsIfNecessary || this.exceedRendererCapabilitiesIfNecessary != parameters.exceedRendererCapabilitiesIfNecessary || this.viewportOrientationMayChange != parameters.viewportOrientationMayChange || this.viewportWidth != parameters.viewportWidth || this.viewportHeight != parameters.viewportHeight || this.maxVideoBitrate != parameters.maxVideoBitrate || this.tunnelingAudioSessionId != parameters.tunnelingAudioSessionId || !TextUtils.equals(this.preferredAudioLanguage, parameters.preferredAudioLanguage) || !TextUtils.equals(this.preferredTextLanguage, parameters.preferredTextLanguage) || !areRendererDisabledFlagsEqual(this.rendererDisabledFlags, parameters.rendererDisabledFlags) || areSelectionOverridesEqual(this.selectionOverrides, parameters.selectionOverrides) == null) {
                        z = false;
                    }
                    return z;
                }
            }
            return false;
        }

        public int hashCode() {
            int i;
            int i2 = ((((((((((((((((((((((((((((((this.selectUndeterminedTextLanguage * 31) + this.disabledTextTrackSelectionFlags) * 31) + this.forceLowestBitrate) * 31) + this.forceHighestSupportedBitrate) * 31) + this.allowMixedMimeAdaptiveness) * 31) + this.allowNonSeamlessAdaptiveness) * 31) + this.maxVideoWidth) * 31) + this.maxVideoHeight) * 31) + this.maxVideoFrameRate) * 31) + this.exceedVideoConstraintsIfNecessary) * 31) + this.exceedRendererCapabilitiesIfNecessary) * 31) + this.viewportOrientationMayChange) * 31) + this.viewportWidth) * 31) + this.viewportHeight) * 31) + this.maxVideoBitrate) * 31) + this.tunnelingAudioSessionId) * 31;
            int i3 = 0;
            if (this.preferredAudioLanguage == null) {
                i = 0;
            } else {
                i = this.preferredAudioLanguage.hashCode();
            }
            i2 = (i2 + i) * 31;
            if (this.preferredTextLanguage != null) {
                i3 = this.preferredTextLanguage.hashCode();
            }
            return i2 + i3;
        }

        public void writeToParcel(Parcel parcel, int i) {
            writeSelectionOverridesToParcel(parcel, this.selectionOverrides);
            parcel.writeSparseBooleanArray(this.rendererDisabledFlags);
            parcel.writeString(this.preferredAudioLanguage);
            parcel.writeString(this.preferredTextLanguage);
            Util.writeBoolean(parcel, this.selectUndeterminedTextLanguage);
            parcel.writeInt(this.disabledTextTrackSelectionFlags);
            Util.writeBoolean(parcel, this.forceLowestBitrate);
            Util.writeBoolean(parcel, this.forceHighestSupportedBitrate);
            Util.writeBoolean(parcel, this.allowMixedMimeAdaptiveness);
            Util.writeBoolean(parcel, this.allowNonSeamlessAdaptiveness);
            parcel.writeInt(this.maxVideoWidth);
            parcel.writeInt(this.maxVideoHeight);
            parcel.writeInt(this.maxVideoFrameRate);
            parcel.writeInt(this.maxVideoBitrate);
            Util.writeBoolean(parcel, this.exceedVideoConstraintsIfNecessary);
            Util.writeBoolean(parcel, this.exceedRendererCapabilitiesIfNecessary);
            parcel.writeInt(this.viewportWidth);
            parcel.writeInt(this.viewportHeight);
            Util.writeBoolean(parcel, this.viewportOrientationMayChange);
            parcel.writeInt(this.tunnelingAudioSessionId);
        }

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> readSelectionOverrides(Parcel parcel) {
            int readInt = parcel.readInt();
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = new SparseArray(readInt);
            for (int i = 0; i < readInt; i++) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                Map hashMap = new HashMap(readInt3);
                for (int i2 = 0; i2 < readInt3; i2++) {
                    hashMap.put((TrackGroupArray) parcel.readParcelable(TrackGroupArray.class.getClassLoader()), (SelectionOverride) parcel.readParcelable(SelectionOverride.class.getClassLoader()));
                }
                sparseArray.put(readInt2, hashMap);
            }
            return sparseArray;
        }

        private static void writeSelectionOverridesToParcel(Parcel parcel, SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray) {
            int size = sparseArray.size();
            parcel.writeInt(size);
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                Map map = (Map) sparseArray.valueAt(i);
                int size2 = map.size();
                parcel.writeInt(keyAt);
                parcel.writeInt(size2);
                for (Entry entry : map.entrySet()) {
                    parcel.writeParcelable((Parcelable) entry.getKey(), 0);
                    parcel.writeParcelable((Parcelable) entry.getValue(), 0);
                }
            }
        }

        private static boolean areRendererDisabledFlagsEqual(SparseBooleanArray sparseBooleanArray, SparseBooleanArray sparseBooleanArray2) {
            int size = sparseBooleanArray.size();
            if (sparseBooleanArray2.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (sparseBooleanArray2.indexOfKey(sparseBooleanArray.keyAt(i)) < 0) {
                    return false;
                }
            }
            return true;
        }

        private static boolean areSelectionOverridesEqual(SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray, SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray2) {
            int size = sparseArray.size();
            if (sparseArray2.size() != size) {
                return false;
            }
            int i = 0;
            while (i < size) {
                int indexOfKey = sparseArray2.indexOfKey(sparseArray.keyAt(i));
                if (indexOfKey >= 0) {
                    if (areSelectionOverridesEqual((Map) sparseArray.valueAt(i), (Map) sparseArray2.valueAt(indexOfKey))) {
                        i++;
                    }
                }
                return false;
            }
            return true;
        }

        private static boolean areSelectionOverridesEqual(Map<TrackGroupArray, SelectionOverride> map, Map<TrackGroupArray, SelectionOverride> map2) {
            if (map2.size() != map.size()) {
                return false;
            }
            map = map.entrySet().iterator();
            while (map.hasNext()) {
                Entry entry = (Entry) map.next();
                TrackGroupArray trackGroupArray = (TrackGroupArray) entry.getKey();
                if (map2.containsKey(trackGroupArray)) {
                    if (!Util.areEqual(entry.getValue(), map2.get(trackGroupArray))) {
                    }
                }
                return false;
            }
            return true;
        }
    }

    public static final class ParametersBuilder {
        private boolean allowMixedMimeAdaptiveness;
        private boolean allowNonSeamlessAdaptiveness;
        private int disabledTextTrackSelectionFlags;
        private boolean exceedRendererCapabilitiesIfNecessary;
        private boolean exceedVideoConstraintsIfNecessary;
        private boolean forceHighestSupportedBitrate;
        private boolean forceLowestBitrate;
        private int maxVideoBitrate;
        private int maxVideoFrameRate;
        private int maxVideoHeight;
        private int maxVideoWidth;
        @Nullable
        private String preferredAudioLanguage;
        @Nullable
        private String preferredTextLanguage;
        private final SparseBooleanArray rendererDisabledFlags;
        private boolean selectUndeterminedTextLanguage;
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides;
        private int tunnelingAudioSessionId;
        private int viewportHeight;
        private boolean viewportOrientationMayChange;
        private int viewportWidth;

        public ParametersBuilder() {
            this(Parameters.DEFAULT);
        }

        private ParametersBuilder(Parameters parameters) {
            this.selectionOverrides = cloneSelectionOverrides(parameters.selectionOverrides);
            this.rendererDisabledFlags = parameters.rendererDisabledFlags.clone();
            this.preferredAudioLanguage = parameters.preferredAudioLanguage;
            this.preferredTextLanguage = parameters.preferredTextLanguage;
            this.selectUndeterminedTextLanguage = parameters.selectUndeterminedTextLanguage;
            this.disabledTextTrackSelectionFlags = parameters.disabledTextTrackSelectionFlags;
            this.forceLowestBitrate = parameters.forceLowestBitrate;
            this.forceHighestSupportedBitrate = parameters.forceHighestSupportedBitrate;
            this.allowMixedMimeAdaptiveness = parameters.allowMixedMimeAdaptiveness;
            this.allowNonSeamlessAdaptiveness = parameters.allowNonSeamlessAdaptiveness;
            this.maxVideoWidth = parameters.maxVideoWidth;
            this.maxVideoHeight = parameters.maxVideoHeight;
            this.maxVideoFrameRate = parameters.maxVideoFrameRate;
            this.maxVideoBitrate = parameters.maxVideoBitrate;
            this.exceedVideoConstraintsIfNecessary = parameters.exceedVideoConstraintsIfNecessary;
            this.exceedRendererCapabilitiesIfNecessary = parameters.exceedRendererCapabilitiesIfNecessary;
            this.viewportWidth = parameters.viewportWidth;
            this.viewportHeight = parameters.viewportHeight;
            this.viewportOrientationMayChange = parameters.viewportOrientationMayChange;
            this.tunnelingAudioSessionId = parameters.tunnelingAudioSessionId;
        }

        public ParametersBuilder setPreferredAudioLanguage(String str) {
            this.preferredAudioLanguage = str;
            return this;
        }

        public ParametersBuilder setPreferredTextLanguage(String str) {
            this.preferredTextLanguage = str;
            return this;
        }

        public ParametersBuilder setSelectUndeterminedTextLanguage(boolean z) {
            this.selectUndeterminedTextLanguage = z;
            return this;
        }

        public ParametersBuilder setDisabledTextTrackSelectionFlags(int i) {
            this.disabledTextTrackSelectionFlags = i;
            return this;
        }

        public ParametersBuilder setForceLowestBitrate(boolean z) {
            this.forceLowestBitrate = z;
            return this;
        }

        public ParametersBuilder setForceHighestSupportedBitrate(boolean z) {
            this.forceHighestSupportedBitrate = z;
            return this;
        }

        public ParametersBuilder setAllowMixedMimeAdaptiveness(boolean z) {
            this.allowMixedMimeAdaptiveness = z;
            return this;
        }

        public ParametersBuilder setAllowNonSeamlessAdaptiveness(boolean z) {
            this.allowNonSeamlessAdaptiveness = z;
            return this;
        }

        public ParametersBuilder setMaxVideoSizeSd() {
            return setMaxVideoSize(1279, 719);
        }

        public ParametersBuilder clearVideoSizeConstraints() {
            return setMaxVideoSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        public ParametersBuilder setMaxVideoSize(int i, int i2) {
            this.maxVideoWidth = i;
            this.maxVideoHeight = i2;
            return this;
        }

        public ParametersBuilder setMaxVideoFrameRate(int i) {
            this.maxVideoFrameRate = i;
            return this;
        }

        public ParametersBuilder setMaxVideoBitrate(int i) {
            this.maxVideoBitrate = i;
            return this;
        }

        public ParametersBuilder setExceedVideoConstraintsIfNecessary(boolean z) {
            this.exceedVideoConstraintsIfNecessary = z;
            return this;
        }

        public ParametersBuilder setExceedRendererCapabilitiesIfNecessary(boolean z) {
            this.exceedRendererCapabilitiesIfNecessary = z;
            return this;
        }

        public ParametersBuilder setViewportSizeToPhysicalDisplaySize(Context context, boolean z) {
            context = Util.getPhysicalDisplaySize(context);
            return setViewportSize(context.x, context.y, z);
        }

        public ParametersBuilder clearViewportSizeConstraints() {
            return setViewportSize(Integer.MAX_VALUE, Integer.MAX_VALUE, true);
        }

        public ParametersBuilder setViewportSize(int i, int i2, boolean z) {
            this.viewportWidth = i;
            this.viewportHeight = i2;
            this.viewportOrientationMayChange = z;
            return this;
        }

        public final ParametersBuilder setRendererDisabled(int i, boolean z) {
            if (this.rendererDisabledFlags.get(i) == z) {
                return this;
            }
            if (z) {
                this.rendererDisabledFlags.put(i, true);
            } else {
                this.rendererDisabledFlags.delete(i);
            }
            return this;
        }

        public final ParametersBuilder setSelectionOverride(int i, TrackGroupArray trackGroupArray, SelectionOverride selectionOverride) {
            Map map = (Map) this.selectionOverrides.get(i);
            if (map == null) {
                map = new HashMap();
                this.selectionOverrides.put(i, map);
            }
            if (map.containsKey(trackGroupArray) != 0 && Util.areEqual(map.get(trackGroupArray), selectionOverride) != 0) {
                return this;
            }
            map.put(trackGroupArray, selectionOverride);
            return this;
        }

        public final ParametersBuilder clearSelectionOverride(int i, TrackGroupArray trackGroupArray) {
            Map map = (Map) this.selectionOverrides.get(i);
            if (map != null) {
                if (map.containsKey(trackGroupArray)) {
                    map.remove(trackGroupArray);
                    if (map.isEmpty() != null) {
                        this.selectionOverrides.remove(i);
                    }
                    return this;
                }
            }
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides(int i) {
            Map map = (Map) this.selectionOverrides.get(i);
            if (map != null) {
                if (!map.isEmpty()) {
                    this.selectionOverrides.remove(i);
                    return this;
                }
            }
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides() {
            if (this.selectionOverrides.size() == 0) {
                return this;
            }
            this.selectionOverrides.clear();
            return this;
        }

        public ParametersBuilder setTunnelingAudioSessionId(int i) {
            if (this.tunnelingAudioSessionId == i) {
                return this;
            }
            this.tunnelingAudioSessionId = i;
            return this;
        }

        public Parameters build() {
            return new Parameters(this.selectionOverrides, this.rendererDisabledFlags, this.preferredAudioLanguage, this.preferredTextLanguage, this.selectUndeterminedTextLanguage, this.disabledTextTrackSelectionFlags, this.forceLowestBitrate, this.forceHighestSupportedBitrate, this.allowMixedMimeAdaptiveness, this.allowNonSeamlessAdaptiveness, this.maxVideoWidth, this.maxVideoHeight, this.maxVideoFrameRate, this.maxVideoBitrate, this.exceedVideoConstraintsIfNecessary, this.exceedRendererCapabilitiesIfNecessary, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange, this.tunnelingAudioSessionId);
        }

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> cloneSelectionOverrides(SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray) {
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray2 = new SparseArray();
            for (int i = 0; i < sparseArray.size(); i++) {
                sparseArray2.put(sparseArray.keyAt(i), new HashMap((Map) sparseArray.valueAt(i)));
            }
            return sparseArray2;
        }
    }

    public static final class SelectionOverride implements Parcelable {
        public static final Creator<SelectionOverride> CREATOR = new Creator<SelectionOverride>() {
            public SelectionOverride createFromParcel(Parcel parcel) {
                return new SelectionOverride(parcel);
            }

            public SelectionOverride[] newArray(int i) {
                return new SelectionOverride[i];
            }
        };
        public final int groupIndex;
        public final int length;
        public final int[] tracks;

        public int describeContents() {
            return 0;
        }

        public SelectionOverride(int i, int... iArr) {
            this.groupIndex = i;
            this.tracks = Arrays.copyOf(iArr, iArr.length);
            this.length = iArr.length;
            Arrays.sort(this.tracks);
        }

        SelectionOverride(Parcel parcel) {
            this.groupIndex = parcel.readInt();
            this.length = parcel.readByte();
            this.tracks = new int[this.length];
            parcel.readIntArray(this.tracks);
        }

        public boolean containsTrack(int i) {
            for (int i2 : this.tracks) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (this.groupIndex * 31) + Arrays.hashCode(this.tracks);
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj != null) {
                if (getClass() == obj.getClass()) {
                    SelectionOverride selectionOverride = (SelectionOverride) obj;
                    if (this.groupIndex != selectionOverride.groupIndex || Arrays.equals(this.tracks, selectionOverride.tracks) == null) {
                        z = false;
                    }
                    return z;
                }
            }
            return false;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.groupIndex);
            parcel.writeInt(this.tracks.length);
            parcel.writeIntArray(this.tracks);
        }
    }

    private static int compareFormatValues(int i, int i2) {
        return i == -1 ? i2 == -1 ? 0 : -1 : i2 == -1 ? 1 : i - i2;
    }

    private static int compareInts(int i, int i2) {
        return i > i2 ? 1 : i2 > i ? -1 : 0;
    }

    protected static boolean isSupported(int i, boolean z) {
        i &= 7;
        if (i != 4) {
            if (!z || i != 3) {
                return false;
            }
        }
        return true;
    }

    public DefaultTrackSelector() {
        this(new AdaptiveTrackSelection.Factory());
    }

    @Deprecated
    public DefaultTrackSelector(BandwidthMeter bandwidthMeter) {
        this(new AdaptiveTrackSelection.Factory(bandwidthMeter));
    }

    public DefaultTrackSelector(Factory factory) {
        this.adaptiveTrackSelectionFactory = factory;
        this.parametersReference = new AtomicReference(Parameters.DEFAULT);
    }

    public void setParameters(Parameters parameters) {
        Assertions.checkNotNull(parameters);
        if (((Parameters) this.parametersReference.getAndSet(parameters)).equals(parameters) == null) {
            invalidate();
        }
    }

    public void setParameters(ParametersBuilder parametersBuilder) {
        setParameters(parametersBuilder.build());
    }

    public Parameters getParameters() {
        return (Parameters) this.parametersReference.get();
    }

    public ParametersBuilder buildUponParameters() {
        return getParameters().buildUpon();
    }

    @Deprecated
    public final void setRendererDisabled(int i, boolean z) {
        setParameters(buildUponParameters().setRendererDisabled(i, z));
    }

    @Deprecated
    public final boolean getRendererDisabled(int i) {
        return getParameters().getRendererDisabled(i);
    }

    @Deprecated
    public final void setSelectionOverride(int i, TrackGroupArray trackGroupArray, SelectionOverride selectionOverride) {
        setParameters(buildUponParameters().setSelectionOverride(i, trackGroupArray, selectionOverride));
    }

    @Deprecated
    public final boolean hasSelectionOverride(int i, TrackGroupArray trackGroupArray) {
        return getParameters().hasSelectionOverride(i, trackGroupArray);
    }

    @Deprecated
    @Nullable
    public final SelectionOverride getSelectionOverride(int i, TrackGroupArray trackGroupArray) {
        return getParameters().getSelectionOverride(i, trackGroupArray);
    }

    @Deprecated
    public final void clearSelectionOverride(int i, TrackGroupArray trackGroupArray) {
        setParameters(buildUponParameters().clearSelectionOverride(i, trackGroupArray));
    }

    @Deprecated
    public final void clearSelectionOverrides(int i) {
        setParameters(buildUponParameters().clearSelectionOverrides(i));
    }

    @Deprecated
    public final void clearSelectionOverrides() {
        setParameters(buildUponParameters().clearSelectionOverrides());
    }

    @Deprecated
    public void setTunnelingAudioSessionId(int i) {
        setParameters(buildUponParameters().setTunnelingAudioSessionId(i));
    }

    protected final Pair<RendererConfiguration[], TrackSelection[]> selectTracks(MappedTrackInfo mappedTrackInfo, int[][][] iArr, int[] iArr2) throws ExoPlaybackException {
        Parameters parameters = (Parameters) this.parametersReference.get();
        int rendererCount = mappedTrackInfo.getRendererCount();
        iArr2 = selectAllTracks(mappedTrackInfo, iArr, iArr2, parameters);
        for (int i = 0; i < rendererCount; i++) {
            if (parameters.getRendererDisabled(i)) {
                iArr2[i] = 0;
            } else {
                TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
                if (parameters.hasSelectionOverride(i, trackGroups)) {
                    SelectionOverride selectionOverride = parameters.getSelectionOverride(i, trackGroups);
                    if (selectionOverride == null) {
                        iArr2[i] = 0;
                    } else if (selectionOverride.length == 1) {
                        iArr2[i] = new FixedTrackSelection(trackGroups.get(selectionOverride.groupIndex), selectionOverride.tracks[0]);
                    } else {
                        iArr2[i] = ((Factory) Assertions.checkNotNull(this.adaptiveTrackSelectionFactory)).createTrackSelection(trackGroups.get(selectionOverride.groupIndex), getBandwidthMeter(), selectionOverride.tracks);
                    }
                }
            }
        }
        Object obj = new RendererConfiguration[rendererCount];
        for (int i2 = 0; i2 < rendererCount; i2++) {
            Object obj2 = (parameters.getRendererDisabled(i2) || (mappedTrackInfo.getRendererType(i2) != 6 && iArr2[i2] == null)) ? null : 1;
            obj[i2] = obj2 != null ? RendererConfiguration.DEFAULT : null;
        }
        maybeConfigureRenderersForTunneling(mappedTrackInfo, iArr, obj, iArr2, parameters.tunnelingAudioSessionId);
        return Pair.create(obj, iArr2);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected com.google.android.exoplayer2.trackselection.TrackSelection[] selectAllTracks(com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo r20, int[][][] r21, int[] r22, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters r23) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
        r19 = this;
        r6 = r19;
        r7 = r20;
        r9 = r23;
        r10 = r20.getRendererCount();
        r11 = new com.google.android.exoplayer2.trackselection.TrackSelection[r10];
        r12 = 0;
        r0 = 0;
        r13 = 0;
        r14 = 0;
    L_0x0010:
        if (r13 >= r10) goto L_0x0047;
    L_0x0012:
        r1 = 2;
        r2 = r7.getRendererType(r13);
        if (r1 != r2) goto L_0x0044;
    L_0x0019:
        r15 = 1;
        if (r0 != 0) goto L_0x0037;
    L_0x001c:
        r1 = r7.getTrackGroups(r13);
        r2 = r21[r13];
        r3 = r22[r13];
        r5 = r6.adaptiveTrackSelectionFactory;
        r0 = r19;
        r4 = r23;
        r0 = r0.selectVideoTrack(r1, r2, r3, r4, r5);
        r11[r13] = r0;
        r0 = r11[r13];
        if (r0 == 0) goto L_0x0036;
    L_0x0034:
        r0 = 1;
        goto L_0x0037;
    L_0x0036:
        r0 = 0;
    L_0x0037:
        r1 = r7.getTrackGroups(r13);
        r1 = r1.length;
        if (r1 <= 0) goto L_0x0040;
    L_0x003f:
        goto L_0x0041;
    L_0x0040:
        r15 = 0;
    L_0x0041:
        r1 = r14 | r15;
        r14 = r1;
    L_0x0044:
        r13 = r13 + 1;
        goto L_0x0010;
    L_0x0047:
        r0 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r13 = -1;
        r15 = 0;
        r3 = r15;
        r2 = -1;
        r4 = -1;
        r5 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
    L_0x0050:
        if (r12 >= r10) goto L_0x00f2;
    L_0x0052:
        r0 = r7.getRendererType(r12);
        switch(r0) {
            case 1: goto L_0x00a2;
            case 2: goto L_0x009a;
            case 3: goto L_0x006e;
            default: goto L_0x0059;
        };
    L_0x0059:
        r13 = r2;
        r8 = r3;
        r16 = r4;
        r17 = r5;
        r1 = -1;
        r2 = r7.getTrackGroups(r12);
        r3 = r21[r12];
        r0 = r6.selectOtherTrack(r0, r2, r3, r9);
        r11[r12] = r0;
        goto L_0x00e7;
    L_0x006e:
        r0 = r7.getTrackGroups(r12);
        r1 = r21[r12];
        r0 = r6.selectTextTrack(r0, r1, r9);
        if (r0 == 0) goto L_0x009a;
    L_0x007a:
        r1 = r0.second;
        r1 = (java.lang.Integer) r1;
        r1 = r1.intValue();
        if (r1 <= r5) goto L_0x009a;
    L_0x0084:
        if (r4 == r13) goto L_0x0088;
    L_0x0086:
        r11[r4] = r15;
    L_0x0088:
        r1 = r0.first;
        r1 = (com.google.android.exoplayer2.trackselection.TrackSelection) r1;
        r11[r12] = r1;
        r0 = r0.second;
        r0 = (java.lang.Integer) r0;
        r0 = r0.intValue();
        r5 = r0;
        r4 = r12;
        r1 = -1;
        goto L_0x00ed;
    L_0x009a:
        r13 = r2;
        r8 = r3;
        r16 = r4;
        r17 = r5;
    L_0x00a0:
        r1 = -1;
        goto L_0x00e7;
    L_0x00a2:
        r1 = r7.getTrackGroups(r12);
        r16 = r21[r12];
        r17 = r22[r12];
        if (r14 == 0) goto L_0x00af;
    L_0x00ac:
        r18 = r15;
        goto L_0x00b3;
    L_0x00af:
        r0 = r6.adaptiveTrackSelectionFactory;
        r18 = r0;
    L_0x00b3:
        r0 = r19;
        r13 = r2;
        r2 = r16;
        r8 = r3;
        r3 = r17;
        r16 = r4;
        r4 = r23;
        r17 = r5;
        r5 = r18;
        r0 = r0.selectAudioTrack(r1, r2, r3, r4, r5);
        if (r0 == 0) goto L_0x00a0;
    L_0x00c9:
        if (r8 == 0) goto L_0x00d5;
    L_0x00cb:
        r1 = r0.second;
        r1 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.AudioTrackScore) r1;
        r1 = r1.compareTo(r8);
        if (r1 <= 0) goto L_0x00a0;
    L_0x00d5:
        r1 = -1;
        if (r13 == r1) goto L_0x00da;
    L_0x00d8:
        r11[r13] = r15;
    L_0x00da:
        r2 = r0.first;
        r2 = (com.google.android.exoplayer2.trackselection.TrackSelection) r2;
        r11[r12] = r2;
        r0 = r0.second;
        r0 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.AudioTrackScore) r0;
        r3 = r0;
        r2 = r12;
        goto L_0x00e9;
    L_0x00e7:
        r3 = r8;
        r2 = r13;
    L_0x00e9:
        r4 = r16;
        r5 = r17;
    L_0x00ed:
        r12 = r12 + 1;
        r13 = -1;
        goto L_0x0050;
    L_0x00f2:
        return r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.selectAllTracks(com.google.android.exoplayer2.trackselection.MappingTrackSelector$MappedTrackInfo, int[][][], int[], com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters):com.google.android.exoplayer2.trackselection.TrackSelection[]");
    }

    @Nullable
    protected TrackSelection selectVideoTrack(TrackGroupArray trackGroupArray, int[][] iArr, int i, Parameters parameters, @Nullable Factory factory) throws ExoPlaybackException {
        i = (parameters.forceHighestSupportedBitrate || parameters.forceLowestBitrate || factory == null) ? 0 : selectAdaptiveVideoTrack(trackGroupArray, iArr, i, parameters, factory, getBandwidthMeter());
        return i == 0 ? selectFixedVideoTrack(trackGroupArray, iArr, parameters) : i;
    }

    @Nullable
    private static TrackSelection selectAdaptiveVideoTrack(TrackGroupArray trackGroupArray, int[][] iArr, int i, Parameters parameters, Factory factory, BandwidthMeter bandwidthMeter) throws ExoPlaybackException {
        TrackGroupArray trackGroupArray2 = trackGroupArray;
        Parameters parameters2 = parameters;
        int i2 = parameters2.allowNonSeamlessAdaptiveness ? 24 : 16;
        boolean z = parameters2.allowMixedMimeAdaptiveness && (i & i2) != 0;
        int i3 = 0;
        while (i3 < trackGroupArray2.length) {
            TrackGroup trackGroup = trackGroupArray2.get(i3);
            TrackGroup trackGroup2 = trackGroup;
            int[] adaptiveVideoTracksForGroup = getAdaptiveVideoTracksForGroup(trackGroup, iArr[i3], z, i2, parameters2.maxVideoWidth, parameters2.maxVideoHeight, parameters2.maxVideoFrameRate, parameters2.maxVideoBitrate, parameters2.viewportWidth, parameters2.viewportHeight, parameters2.viewportOrientationMayChange);
            if (adaptiveVideoTracksForGroup.length > 0) {
                return ((Factory) Assertions.checkNotNull(factory)).createTrackSelection(trackGroup2, bandwidthMeter, adaptiveVideoTracksForGroup);
            }
            BandwidthMeter bandwidthMeter2 = bandwidthMeter;
            i3++;
            trackGroupArray2 = trackGroupArray;
        }
        return null;
    }

    private static int[] getAdaptiveVideoTracksForGroup(TrackGroup trackGroup, int[] iArr, boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z2) {
        TrackGroup trackGroup2 = trackGroup;
        if (trackGroup2.length < 2) {
            return NO_TRACKS;
        }
        List viewportFilteredTrackIndices = getViewportFilteredTrackIndices(trackGroup2, i6, i7, z2);
        if (viewportFilteredTrackIndices.size() < 2) {
            return NO_TRACKS;
        }
        String str;
        if (z) {
            str = null;
        } else {
            HashSet hashSet = new HashSet();
            String str2 = null;
            int i8 = 0;
            for (int i9 = 0; i9 < viewportFilteredTrackIndices.size(); i9++) {
                String str3 = trackGroup2.getFormat(((Integer) viewportFilteredTrackIndices.get(i9)).intValue()).sampleMimeType;
                if (hashSet.add(str3)) {
                    String str4 = str3;
                    int adaptiveVideoTrackCountForMimeType = getAdaptiveVideoTrackCountForMimeType(trackGroup, iArr, i, str3, i2, i3, i4, i5, viewportFilteredTrackIndices);
                    if (adaptiveVideoTrackCountForMimeType > i8) {
                        i8 = adaptiveVideoTrackCountForMimeType;
                        str2 = str4;
                    }
                }
            }
            str = str2;
        }
        filterAdaptiveVideoTrackCountForMimeType(trackGroup, iArr, i, str, i2, i3, i4, i5, viewportFilteredTrackIndices);
        return viewportFilteredTrackIndices.size() < 2 ? NO_TRACKS : Util.toArray(viewportFilteredTrackIndices);
    }

    private static int getAdaptiveVideoTrackCountForMimeType(TrackGroup trackGroup, int[] iArr, int i, @Nullable String str, int i2, int i3, int i4, int i5, List<Integer> list) {
        int i6 = 0;
        for (int i7 = 0; i7 < list.size(); i7++) {
            int intValue = ((Integer) list.get(i7)).intValue();
            TrackGroup trackGroup2 = trackGroup;
            if (isSupportedAdaptiveVideoTrack(trackGroup.getFormat(intValue), str, iArr[intValue], i, i2, i3, i4, i5)) {
                i6++;
            }
        }
        return i6;
    }

    private static void filterAdaptiveVideoTrackCountForMimeType(TrackGroup trackGroup, int[] iArr, int i, @Nullable String str, int i2, int i3, int i4, int i5, List<Integer> list) {
        List<Integer> list2 = list;
        for (int size = list.size() - 1; size >= 0; size--) {
            int intValue = ((Integer) list2.get(size)).intValue();
            TrackGroup trackGroup2 = trackGroup;
            if (!isSupportedAdaptiveVideoTrack(trackGroup.getFormat(intValue), str, iArr[intValue], i, i2, i3, i4, i5)) {
                list2.remove(size);
            }
        }
    }

    private static boolean isSupportedAdaptiveVideoTrack(Format format, @Nullable String str, int i, int i2, int i3, int i4, int i5, int i6) {
        if (!isSupported(i, false) || (i & i2) == 0) {
            return false;
        }
        if (str != null && Util.areEqual(format.sampleMimeType, str) == null) {
            return false;
        }
        if (format.width != -1 && format.width > i3) {
            return false;
        }
        if (format.height != -1 && format.height > i4) {
            return false;
        }
        if (format.frameRate != -1082130432 && format.frameRate > ((float) i5)) {
            return false;
        }
        if (format.bitrate == -1 || format.bitrate <= i6) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @androidx.annotation.Nullable
    private static com.google.android.exoplayer2.trackselection.TrackSelection selectFixedVideoTrack(com.google.android.exoplayer2.source.TrackGroupArray r20, int[][] r21, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters r22) {
        /*
        r0 = r20;
        r1 = r22;
        r3 = -1;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = -1;
        r10 = -1;
    L_0x000b:
        r11 = r0.length;
        if (r5 >= r11) goto L_0x00ec;
    L_0x000f:
        r11 = r0.get(r5);
        r12 = r1.viewportWidth;
        r13 = r1.viewportHeight;
        r14 = r1.viewportOrientationMayChange;
        r12 = getViewportFilteredTrackIndices(r11, r12, r13, r14);
        r14 = r21[r5];
        r15 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = 0;
    L_0x0025:
        r2 = r11.length;
        if (r6 >= r2) goto L_0x00dd;
    L_0x0029:
        r2 = r14[r6];
        r4 = r1.exceedRendererCapabilitiesIfNecessary;
        r2 = isSupported(r2, r4);
        if (r2 == 0) goto L_0x00d2;
    L_0x0033:
        r2 = r11.getFormat(r6);
        r4 = java.lang.Integer.valueOf(r6);
        r4 = r12.contains(r4);
        r18 = 1;
        if (r4 == 0) goto L_0x0076;
    L_0x0043:
        r4 = r2.width;
        if (r4 == r3) goto L_0x004d;
    L_0x0047:
        r4 = r2.width;
        r3 = r1.maxVideoWidth;
        if (r4 > r3) goto L_0x0076;
    L_0x004d:
        r3 = r2.height;
        r4 = -1;
        if (r3 == r4) goto L_0x0058;
    L_0x0052:
        r3 = r2.height;
        r4 = r1.maxVideoHeight;
        if (r3 > r4) goto L_0x0076;
    L_0x0058:
        r3 = r2.frameRate;
        r4 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1));
        if (r3 == 0) goto L_0x0069;
    L_0x0060:
        r3 = r2.frameRate;
        r4 = r1.maxVideoFrameRate;
        r4 = (float) r4;
        r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1));
        if (r3 > 0) goto L_0x0076;
    L_0x0069:
        r3 = r2.bitrate;
        r4 = -1;
        if (r3 == r4) goto L_0x0074;
    L_0x006e:
        r3 = r2.bitrate;
        r4 = r1.maxVideoBitrate;
        if (r3 > r4) goto L_0x0076;
    L_0x0074:
        r3 = 1;
        goto L_0x0077;
    L_0x0076:
        r3 = 0;
    L_0x0077:
        if (r3 != 0) goto L_0x007e;
    L_0x0079:
        r4 = r1.exceedVideoConstraintsIfNecessary;
        if (r4 != 0) goto L_0x007e;
    L_0x007d:
        goto L_0x00d2;
    L_0x007e:
        if (r3 == 0) goto L_0x0082;
    L_0x0080:
        r4 = 2;
        goto L_0x0083;
    L_0x0082:
        r4 = 1;
    L_0x0083:
        r0 = r14[r6];
        r19 = r8;
        r8 = 0;
        r0 = isSupported(r0, r8);
        if (r0 == 0) goto L_0x0090;
    L_0x008e:
        r4 = r4 + 1000;
    L_0x0090:
        if (r4 <= r9) goto L_0x0095;
    L_0x0092:
        r17 = 1;
        goto L_0x0097;
    L_0x0095:
        r17 = 0;
    L_0x0097:
        if (r4 != r9) goto L_0x00c6;
    L_0x0099:
        r8 = r1.forceLowestBitrate;
        if (r8 == 0) goto L_0x00ab;
    L_0x009d:
        r0 = r2.bitrate;
        r0 = compareFormatValues(r0, r10);
        if (r0 >= 0) goto L_0x00a8;
    L_0x00a5:
        r17 = 1;
        goto L_0x00c6;
    L_0x00a8:
        r17 = 0;
        goto L_0x00c6;
    L_0x00ab:
        r8 = r2.getPixelCount();
        if (r8 == r15) goto L_0x00b6;
    L_0x00b1:
        r8 = compareFormatValues(r8, r15);
        goto L_0x00bc;
    L_0x00b6:
        r8 = r2.bitrate;
        r8 = compareFormatValues(r8, r10);
    L_0x00bc:
        if (r0 == 0) goto L_0x00c3;
    L_0x00be:
        if (r3 == 0) goto L_0x00c3;
    L_0x00c0:
        if (r8 <= 0) goto L_0x00a8;
    L_0x00c2:
        goto L_0x00a5;
    L_0x00c3:
        if (r8 >= 0) goto L_0x00a8;
    L_0x00c5:
        goto L_0x00a5;
    L_0x00c6:
        if (r17 == 0) goto L_0x00d4;
    L_0x00c8:
        r10 = r2.bitrate;
        r15 = r2.getPixelCount();
        r9 = r4;
        r8 = r6;
        r7 = r11;
        goto L_0x00d6;
    L_0x00d2:
        r19 = r8;
    L_0x00d4:
        r8 = r19;
    L_0x00d6:
        r6 = r6 + 1;
        r0 = r20;
        r3 = -1;
        goto L_0x0025;
    L_0x00dd:
        r19 = r8;
        r5 = r5 + 1;
        r6 = r7;
        r8 = r9;
        r9 = r10;
        r10 = r15;
        r7 = r19;
        r0 = r20;
        r3 = -1;
        goto L_0x000b;
    L_0x00ec:
        if (r6 != 0) goto L_0x00f1;
    L_0x00ee:
        r16 = 0;
        goto L_0x00f8;
    L_0x00f1:
        r2 = new com.google.android.exoplayer2.trackselection.FixedTrackSelection;
        r2.<init>(r6, r7);
        r16 = r2;
    L_0x00f8:
        return r16;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.selectFixedVideoTrack(com.google.android.exoplayer2.source.TrackGroupArray, int[][], com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters):com.google.android.exoplayer2.trackselection.TrackSelection");
    }

    @Nullable
    protected Pair<TrackSelection, AudioTrackScore> selectAudioTrack(TrackGroupArray trackGroupArray, int[][] iArr, int i, Parameters parameters, @Nullable Factory factory) throws ExoPlaybackException {
        TrackGroupArray trackGroupArray2 = trackGroupArray;
        Parameters parameters2 = parameters;
        Factory factory2 = factory;
        Object obj = null;
        AudioTrackScore audioTrackScore = null;
        int i2 = 0;
        int i3 = -1;
        int i4 = -1;
        while (i2 < trackGroupArray2.length) {
            TrackGroup trackGroup = trackGroupArray2.get(i2);
            int[] iArr2 = iArr[i2];
            int i5 = i4;
            AudioTrackScore audioTrackScore2 = audioTrackScore;
            int i6 = i3;
            for (i3 = 0; i3 < trackGroup.length; i3++) {
                if (isSupported(iArr2[i3], parameters2.exceedRendererCapabilitiesIfNecessary)) {
                    AudioTrackScore audioTrackScore3 = new AudioTrackScore(trackGroup.getFormat(i3), parameters2, iArr2[i3]);
                    if (audioTrackScore2 == null || audioTrackScore3.compareTo(audioTrackScore2) > 0) {
                        i6 = i2;
                        i5 = i3;
                        audioTrackScore2 = audioTrackScore3;
                    }
                }
            }
            i2++;
            i3 = i6;
            audioTrackScore = audioTrackScore2;
            i4 = i5;
        }
        if (i3 == -1) {
            return null;
        }
        TrackGroup trackGroup2 = trackGroupArray2.get(i3);
        if (!(parameters2.forceHighestSupportedBitrate || parameters2.forceLowestBitrate || factory2 == null)) {
            int[] adaptiveAudioTracks = getAdaptiveAudioTracks(trackGroup2, iArr[i3], parameters2.allowMixedMimeAdaptiveness);
            if (adaptiveAudioTracks.length > 0) {
                obj = factory2.createTrackSelection(trackGroup2, getBandwidthMeter(), adaptiveAudioTracks);
            }
        }
        if (obj == null) {
            obj = new FixedTrackSelection(trackGroup2, i4);
        }
        return Pair.create(obj, Assertions.checkNotNull(audioTrackScore));
    }

    private static int[] getAdaptiveAudioTracks(TrackGroup trackGroup, int[] iArr, boolean z) {
        HashSet hashSet = new HashSet();
        Object obj = null;
        boolean z2 = false;
        for (int i = 0; i < trackGroup.length; i++) {
            Format format = trackGroup.getFormat(i);
            AudioConfigurationTuple audioConfigurationTuple = new AudioConfigurationTuple(format.channelCount, format.sampleRate, z ? null : format.sampleMimeType);
            if (hashSet.add(audioConfigurationTuple)) {
                boolean adaptiveAudioTrackCount = getAdaptiveAudioTrackCount(trackGroup, iArr, audioConfigurationTuple);
                if (adaptiveAudioTrackCount > z2) {
                    z2 = adaptiveAudioTrackCount;
                    obj = audioConfigurationTuple;
                }
            }
        }
        if (z2 <= true) {
            return NO_TRACKS;
        }
        z = new int[z2];
        int i2 = 0;
        for (int i3 = 0; i3 < trackGroup.length; i3++) {
            if (isSupportedAdaptiveAudioTrack(trackGroup.getFormat(i3), iArr[i3], (AudioConfigurationTuple) Assertions.checkNotNull(obj))) {
                int i4 = i2 + 1;
                z[i2] = i3;
                i2 = i4;
            }
        }
        return z;
    }

    private static int getAdaptiveAudioTrackCount(TrackGroup trackGroup, int[] iArr, AudioConfigurationTuple audioConfigurationTuple) {
        int i = 0;
        for (int i2 = 0; i2 < trackGroup.length; i2++) {
            if (isSupportedAdaptiveAudioTrack(trackGroup.getFormat(i2), iArr[i2], audioConfigurationTuple)) {
                i++;
            }
        }
        return i;
    }

    private static boolean isSupportedAdaptiveAudioTrack(Format format, int i, AudioConfigurationTuple audioConfigurationTuple) {
        if (isSupported(i, false) == 0 || format.channelCount != audioConfigurationTuple.channelCount || format.sampleRate != audioConfigurationTuple.sampleRate) {
            return false;
        }
        if (audioConfigurationTuple.mimeType == 0 || TextUtils.equals(audioConfigurationTuple.mimeType, format.sampleMimeType) != null) {
            return true;
        }
        return false;
    }

    @Nullable
    protected Pair<TrackSelection, Integer> selectTextTrack(TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters) throws ExoPlaybackException {
        TrackGroupArray trackGroupArray2 = trackGroupArray;
        Parameters parameters2 = parameters;
        int i = 0;
        TrackGroup trackGroup = null;
        int i2 = 0;
        int i3 = 0;
        while (i < trackGroupArray2.length) {
            TrackGroup trackGroup2 = trackGroupArray2.get(i);
            int[] iArr2 = iArr[i];
            int i4 = i3;
            i3 = i2;
            TrackGroup trackGroup3 = trackGroup;
            for (int i5 = 0; i5 < trackGroup2.length; i5++) {
                if (isSupported(iArr2[i5], parameters2.exceedRendererCapabilitiesIfNecessary)) {
                    int i6;
                    Format format = trackGroup2.getFormat(i5);
                    int i7 = format.selectionFlags & (parameters2.disabledTextTrackSelectionFlags ^ -1);
                    Object obj = (i7 & 1) != 0 ? 1 : null;
                    Object obj2 = (i7 & 2) != 0 ? 1 : null;
                    boolean formatHasLanguage = formatHasLanguage(format, parameters2.preferredTextLanguage);
                    if (!formatHasLanguage) {
                        if (!parameters2.selectUndeterminedTextLanguage || !formatHasNoLanguage(format)) {
                            if (obj != null) {
                                i6 = 3;
                            } else if (obj2 != null) {
                                i6 = formatHasLanguage(format, parameters2.preferredAudioLanguage) ? 2 : 1;
                            }
                            if (isSupported(iArr2[i5], false)) {
                                i6 += 1000;
                            }
                            if (i6 > i4) {
                                i3 = i5;
                                trackGroup3 = trackGroup2;
                                i4 = i6;
                            }
                        }
                    }
                    int i8 = obj != null ? 8 : obj2 == null ? 6 : 4;
                    i6 = i8 + formatHasLanguage;
                    if (isSupported(iArr2[i5], false)) {
                        i6 += 1000;
                    }
                    if (i6 > i4) {
                        i3 = i5;
                        trackGroup3 = trackGroup2;
                        i4 = i6;
                    }
                }
            }
            i++;
            trackGroup = trackGroup3;
            i2 = i3;
            i3 = i4;
        }
        if (trackGroup == null) {
            return null;
        }
        return Pair.create(new FixedTrackSelection(trackGroup, i2), Integer.valueOf(i3));
    }

    @Nullable
    protected TrackSelection selectOtherTrack(int i, TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters) throws ExoPlaybackException {
        TrackGroup trackGroup = null;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < trackGroupArray.length) {
            TrackGroup trackGroup2 = trackGroupArray.get(i2);
            int[] iArr2 = iArr[i2];
            int i5 = i4;
            i4 = i3;
            TrackGroup trackGroup3 = trackGroup;
            for (int i6 = 0; i6 < trackGroup2.length; i6++) {
                if (isSupported(iArr2[i6], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    int i7 = 1;
                    if (((trackGroup2.getFormat(i6).selectionFlags & 1) != 0 ? 1 : null) != null) {
                        i7 = 2;
                    }
                    if (isSupported(iArr2[i6], false)) {
                        i7 += 1000;
                    }
                    if (i7 > i5) {
                        i4 = i6;
                        trackGroup3 = trackGroup2;
                        i5 = i7;
                    }
                }
            }
            i2++;
            trackGroup = trackGroup3;
            i3 = i4;
            i4 = i5;
        }
        if (trackGroup == null) {
            return null;
        }
        return new FixedTrackSelection(trackGroup, i3);
    }

    private static void maybeConfigureRenderersForTunneling(MappedTrackInfo mappedTrackInfo, int[][][] iArr, RendererConfiguration[] rendererConfigurationArr, TrackSelection[] trackSelectionArr, int i) {
        if (i != 0) {
            int i2 = 0;
            int i3 = 0;
            int i4 = -1;
            int i5 = -1;
            while (i3 < mappedTrackInfo.getRendererCount()) {
                int rendererType = mappedTrackInfo.getRendererType(i3);
                TrackSelection trackSelection = trackSelectionArr[i3];
                if ((rendererType == 1 || rendererType == 2) && trackSelection != null && rendererSupportsTunneling(iArr[i3], mappedTrackInfo.getTrackGroups(i3), trackSelection)) {
                    if (rendererType == 1) {
                        if (i4 == -1) {
                            i4 = i3;
                        }
                    } else if (i5 == -1) {
                        i5 = i3;
                    }
                    mappedTrackInfo = null;
                    break;
                }
                i3++;
            }
            mappedTrackInfo = true;
            if (!(i4 == -1 || i5 == -1)) {
                i2 = 1;
            }
            if ((mappedTrackInfo & i2) != null) {
                mappedTrackInfo = new RendererConfiguration(i);
                rendererConfigurationArr[i4] = mappedTrackInfo;
                rendererConfigurationArr[i5] = mappedTrackInfo;
            }
        }
    }

    private static boolean rendererSupportsTunneling(int[][] iArr, TrackGroupArray trackGroupArray, TrackSelection trackSelection) {
        if (trackSelection == null) {
            return false;
        }
        trackGroupArray = trackGroupArray.indexOf(trackSelection.getTrackGroup());
        for (int i = 0; i < trackSelection.length(); i++) {
            if ((iArr[trackGroupArray][trackSelection.getIndexInTrackGroup(i)] & 32) != 32) {
                return false;
            }
        }
        return 1;
    }

    protected static boolean formatHasNoLanguage(Format format) {
        if (!TextUtils.isEmpty(format.language)) {
            if (formatHasLanguage(format, C.LANGUAGE_UNDETERMINED) == null) {
                return null;
            }
        }
        return true;
    }

    protected static boolean formatHasLanguage(Format format, @Nullable String str) {
        return (str == null || TextUtils.equals(str, Util.normalizeLanguageCode(format.language)) == null) ? null : true;
    }

    private static List<Integer> getViewportFilteredTrackIndices(TrackGroup trackGroup, int i, int i2, boolean z) {
        List<Integer> arrayList = new ArrayList(trackGroup.length);
        for (int i3 = 0; i3 < trackGroup.length; i3++) {
            arrayList.add(Integer.valueOf(i3));
        }
        if (i != Integer.MAX_VALUE) {
            if (i2 != Integer.MAX_VALUE) {
                boolean z2 = true;
                for (int i4 = 0; i4 < trackGroup.length; i4++) {
                    Format format = trackGroup.getFormat(i4);
                    if (format.width > 0 && format.height > 0) {
                        Point maxVideoSizeInViewport = getMaxVideoSizeInViewport(z, i, i2, format.width, format.height);
                        boolean z3 = format.width * format.height;
                        if (format.width >= ((int) (((float) maxVideoSizeInViewport.x) * FRACTION_TO_CONSIDER_FULLSCREEN)) && format.height >= ((int) (((float) maxVideoSizeInViewport.y) * FRACTION_TO_CONSIDER_FULLSCREEN)) && z3 < z2) {
                            z2 = z3;
                        }
                    }
                }
                if (!z2) {
                    for (i = arrayList.size() - 1; i >= 0; i--) {
                        boolean pixelCount = trackGroup.getFormat(((Integer) arrayList.get(i)).intValue()).getPixelCount();
                        if (pixelCount || pixelCount > z2) {
                            arrayList.remove(i);
                        }
                    }
                }
                return arrayList;
            }
        }
        return arrayList;
    }

    private static Point getMaxVideoSizeInViewport(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (z) {
            z = false;
            boolean z2 = i3 > i4;
            if (i > i2) {
                z = true;
            }
            if (z2 != z) {
                i5 = i3 * i;
                i6 = i4 * i2;
                if (i5 < i6) {
                    return new Point(i2, Util.ceilDivide(i6, i3));
                }
                return new Point(Util.ceilDivide(i5, i4), i);
            }
        }
        int i7 = i2;
        i2 = i;
        i = i7;
        i5 = i3 * i;
        i6 = i4 * i2;
        if (i5 < i6) {
            return new Point(Util.ceilDivide(i5, i4), i);
        }
        return new Point(i2, Util.ceilDivide(i6, i3));
    }
}
