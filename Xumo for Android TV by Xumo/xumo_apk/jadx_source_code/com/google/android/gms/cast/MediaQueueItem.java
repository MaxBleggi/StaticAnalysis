package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.cast.zzcv;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Class(creator = "MediaQueueItemCreator")
@Reserved({1})
public class MediaQueueItem extends AbstractSafeParcelable {
    public static final Creator<MediaQueueItem> CREATOR = new zzap();
    public static final double DEFAULT_PLAYBACK_DURATION = Double.POSITIVE_INFINITY;
    public static final int INVALID_ITEM_ID = 0;
    @Field(getter = "getAutoplay", id = 4)
    private boolean zzdm;
    @Field(getter = "getActiveTrackIds", id = 8)
    private long[] zzdp;
    @Field(getter = "getStartTime", id = 5)
    private double zzei;
    @Field(getter = "getMedia", id = 2)
    private MediaInfo zzej;
    @Field(getter = "getItemId", id = 3)
    private int zzek;
    @Field(getter = "getPlaybackDuration", id = 6)
    private double zzel;
    @Field(getter = "getPreloadTime", id = 7)
    private double zzem;
    @Field(id = 9)
    private String zzj;
    private JSONObject zzp;

    @VisibleForTesting
    public static class Builder {
        private final MediaQueueItem zzen;

        public Builder(MediaInfo mediaInfo) throws IllegalArgumentException {
            this.zzen = new MediaQueueItem(mediaInfo);
        }

        public Builder(JSONObject jSONObject) throws JSONException {
            this.zzen = new MediaQueueItem(jSONObject);
        }

        public Builder(MediaQueueItem mediaQueueItem) throws IllegalArgumentException {
            this.zzen = new MediaQueueItem();
        }

        public Builder clearItemId() {
            this.zzen.zza(0);
            return this;
        }

        public Builder setAutoplay(boolean z) {
            this.zzen.zze(z);
            return this;
        }

        public Builder setStartTime(double d) throws IllegalArgumentException {
            this.zzen.zza(d);
            return this;
        }

        public Builder setPlaybackDuration(double d) {
            this.zzen.zzb(d);
            return this;
        }

        public Builder setPreloadTime(double d) throws IllegalArgumentException {
            this.zzen.zzc(d);
            return this;
        }

        public Builder setActiveTrackIds(long[] jArr) {
            this.zzen.zza(jArr);
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.zzen.setCustomData(jSONObject);
            return this;
        }

        public MediaQueueItem build() {
            this.zzen.zzi();
            return this.zzen;
        }
    }

    @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
    MediaQueueItem(@com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 2) com.google.android.gms.cast.MediaInfo r1, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 3) int r2, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 4) boolean r3, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 5) double r4, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 6) double r6, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 7) double r8, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 8) long[] r10, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 9) java.lang.String r11) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = this;
        r0.<init>();
        r0.zzej = r1;
        r0.zzek = r2;
        r0.zzdm = r3;
        r0.zzei = r4;
        r0.zzel = r6;
        r0.zzem = r8;
        r0.zzdp = r10;
        r0.zzj = r11;
        r1 = r0.zzj;
        r2 = 0;
        if (r1 == 0) goto L_0x0027;
    L_0x0018:
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0022 }
        r3 = r0.zzj;	 Catch:{ JSONException -> 0x0022 }
        r1.<init>(r3);	 Catch:{ JSONException -> 0x0022 }
        r0.zzp = r1;	 Catch:{ JSONException -> 0x0022 }
        return;
    L_0x0022:
        r0.zzp = r2;
        r0.zzj = r2;
        return;
    L_0x0027:
        r0.zzp = r2;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaQueueItem.<init>(com.google.android.gms.cast.MediaInfo, int, boolean, double, double, double, long[], java.lang.String):void");
    }

    private MediaQueueItem(MediaInfo mediaInfo) throws IllegalArgumentException {
        this(mediaInfo, 0, true, 0.0d, DEFAULT_PLAYBACK_DURATION, 0.0d, null, null);
        if (mediaInfo == null) {
            throw new IllegalArgumentException("media cannot be null.");
        }
    }

    MediaQueueItem(JSONObject jSONObject) throws JSONException {
        this(null, 0, true, 0.0d, DEFAULT_PLAYBACK_DURATION, 0.0d, null, null);
        zzf(jSONObject);
    }

    private MediaQueueItem(MediaQueueItem mediaQueueItem) throws IllegalArgumentException {
        this(mediaQueueItem.getMedia(), mediaQueueItem.getItemId(), mediaQueueItem.getAutoplay(), mediaQueueItem.getStartTime(), mediaQueueItem.getPlaybackDuration(), mediaQueueItem.getPreloadTime(), mediaQueueItem.getActiveTrackIds(), null);
        if (this.zzej != null) {
            this.zzp = mediaQueueItem.getCustomData();
            return;
        }
        throw new IllegalArgumentException("media cannot be null.");
    }

    public final boolean zzf(JSONObject jSONObject) throws JSONException {
        boolean z;
        int i;
        double d;
        Object obj = null;
        if (jSONObject.has(VideoCastManager.EXTRA_MEDIA)) {
            this.zzej = new MediaInfo(jSONObject.getJSONObject(VideoCastManager.EXTRA_MEDIA));
            z = true;
        } else {
            z = false;
        }
        if (jSONObject.has("itemId")) {
            i = jSONObject.getInt("itemId");
            if (this.zzek != i) {
                this.zzek = i;
                z = true;
            }
        }
        if (jSONObject.has("autoplay")) {
            boolean z2 = jSONObject.getBoolean("autoplay");
            if (this.zzdm != z2) {
                this.zzdm = z2;
                z = true;
            }
        }
        if (jSONObject.has("startTime")) {
            d = jSONObject.getDouble("startTime");
            if (Math.abs(d - this.zzei) > 1.0E-7d) {
                this.zzei = d;
                z = true;
            }
        }
        if (jSONObject.has("playbackDuration")) {
            d = jSONObject.getDouble("playbackDuration");
            if (Math.abs(d - this.zzel) > 1.0E-7d) {
                this.zzel = d;
                z = true;
            }
        }
        if (jSONObject.has("preloadTime")) {
            d = jSONObject.getDouble("preloadTime");
            if (Math.abs(d - this.zzem) > 1.0E-7d) {
                this.zzem = d;
                z = true;
            }
        }
        long[] jArr;
        if (jSONObject.has("activeTrackIds")) {
            JSONArray jSONArray = jSONObject.getJSONArray("activeTrackIds");
            int length = jSONArray.length();
            jArr = new long[length];
            for (int i2 = 0; i2 < length; i2++) {
                jArr[i2] = jSONArray.getLong(i2);
            }
            if (this.zzdp != null) {
                if (this.zzdp.length == length) {
                    i = 0;
                    while (i < length) {
                        if (this.zzdp[i] == jArr[i]) {
                            i++;
                        }
                    }
                }
            }
            obj = 1;
            break;
        }
        jArr = null;
        if (obj != null) {
            this.zzdp = jArr;
            z = true;
        }
        if (!jSONObject.has(VideoCastManager.EXTRA_CUSTOM_DATA)) {
            return z;
        }
        this.zzp = jSONObject.getJSONObject(VideoCastManager.EXTRA_CUSTOM_DATA);
        return true;
    }

    public MediaInfo getMedia() {
        return this.zzej;
    }

    public int getItemId() {
        return this.zzek;
    }

    final void zza(int i) {
        this.zzek = 0;
    }

    public boolean getAutoplay() {
        return this.zzdm;
    }

    final void zze(boolean z) {
        this.zzdm = z;
    }

    public double getStartTime() {
        return this.zzei;
    }

    final void zza(double d) throws IllegalArgumentException {
        if (Double.isNaN(d) || d < 0.0d) {
            throw new IllegalArgumentException("startTime cannot be negative or NaN.");
        }
        this.zzei = d;
    }

    public double getPlaybackDuration() {
        return this.zzel;
    }

    final void zzb(double d) throws IllegalArgumentException {
        if (Double.isNaN(d)) {
            throw new IllegalArgumentException("playbackDuration cannot be NaN.");
        }
        this.zzel = d;
    }

    public double getPreloadTime() {
        return this.zzem;
    }

    final void zzc(double d) throws IllegalArgumentException {
        if (Double.isNaN(d) || d < 0.0d) {
            throw new IllegalArgumentException("preloadTime cannot be negative or NaN.");
        }
        this.zzem = d;
    }

    public long[] getActiveTrackIds() {
        return this.zzdp;
    }

    final void zza(long[] jArr) {
        this.zzdp = jArr;
    }

    public JSONObject getCustomData() {
        return this.zzp;
    }

    final void setCustomData(JSONObject jSONObject) {
        this.zzp = jSONObject;
    }

    final void zzi() throws IllegalArgumentException {
        if (this.zzej == null) {
            throw new IllegalArgumentException("media cannot be null.");
        } else if (Double.isNaN(this.zzei) || this.zzei < 0.0d) {
            throw new IllegalArgumentException("startTime cannot be negative or NaN.");
        } else if (Double.isNaN(this.zzel)) {
            throw new IllegalArgumentException("playbackDuration cannot be NaN.");
        } else if (Double.isNaN(this.zzem) || this.zzem < 0.0d) {
            throw new IllegalArgumentException("preloadTime cannot be negative or Nan.");
        }
    }

    public final org.json.JSONObject toJson() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r7 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "media";	 Catch:{ JSONException -> 0x0066 }
        r2 = r7.zzej;	 Catch:{ JSONException -> 0x0066 }
        r2 = r2.toJson();	 Catch:{ JSONException -> 0x0066 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0066 }
        r1 = r7.zzek;	 Catch:{ JSONException -> 0x0066 }
        if (r1 == 0) goto L_0x001b;	 Catch:{ JSONException -> 0x0066 }
    L_0x0014:
        r1 = "itemId";	 Catch:{ JSONException -> 0x0066 }
        r2 = r7.zzek;	 Catch:{ JSONException -> 0x0066 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0066 }
    L_0x001b:
        r1 = "autoplay";	 Catch:{ JSONException -> 0x0066 }
        r2 = r7.zzdm;	 Catch:{ JSONException -> 0x0066 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0066 }
        r1 = "startTime";	 Catch:{ JSONException -> 0x0066 }
        r2 = r7.zzei;	 Catch:{ JSONException -> 0x0066 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0066 }
        r1 = r7.zzel;	 Catch:{ JSONException -> 0x0066 }
        r3 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;	 Catch:{ JSONException -> 0x0066 }
        r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));	 Catch:{ JSONException -> 0x0066 }
        if (r5 == 0) goto L_0x0038;	 Catch:{ JSONException -> 0x0066 }
    L_0x0031:
        r1 = "playbackDuration";	 Catch:{ JSONException -> 0x0066 }
        r2 = r7.zzel;	 Catch:{ JSONException -> 0x0066 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0066 }
    L_0x0038:
        r1 = "preloadTime";	 Catch:{ JSONException -> 0x0066 }
        r2 = r7.zzem;	 Catch:{ JSONException -> 0x0066 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0066 }
        r1 = r7.zzdp;	 Catch:{ JSONException -> 0x0066 }
        if (r1 == 0) goto L_0x005b;	 Catch:{ JSONException -> 0x0066 }
    L_0x0043:
        r1 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0066 }
        r1.<init>();	 Catch:{ JSONException -> 0x0066 }
        r2 = r7.zzdp;	 Catch:{ JSONException -> 0x0066 }
        r3 = r2.length;	 Catch:{ JSONException -> 0x0066 }
        r4 = 0;	 Catch:{ JSONException -> 0x0066 }
    L_0x004c:
        if (r4 >= r3) goto L_0x0056;	 Catch:{ JSONException -> 0x0066 }
    L_0x004e:
        r5 = r2[r4];	 Catch:{ JSONException -> 0x0066 }
        r1.put(r5);	 Catch:{ JSONException -> 0x0066 }
        r4 = r4 + 1;	 Catch:{ JSONException -> 0x0066 }
        goto L_0x004c;	 Catch:{ JSONException -> 0x0066 }
    L_0x0056:
        r2 = "activeTrackIds";	 Catch:{ JSONException -> 0x0066 }
        r0.put(r2, r1);	 Catch:{ JSONException -> 0x0066 }
    L_0x005b:
        r1 = r7.zzp;	 Catch:{ JSONException -> 0x0066 }
        if (r1 == 0) goto L_0x0066;	 Catch:{ JSONException -> 0x0066 }
    L_0x005f:
        r1 = "customData";	 Catch:{ JSONException -> 0x0066 }
        r2 = r7.zzp;	 Catch:{ JSONException -> 0x0066 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0066 }
    L_0x0066:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaQueueItem.toJson():org.json.JSONObject");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaQueueItem)) {
            return false;
        }
        MediaQueueItem mediaQueueItem = (MediaQueueItem) obj;
        if ((this.zzp == null ? 1 : null) != (mediaQueueItem.zzp == null ? 1 : null)) {
            return false;
        }
        return (this.zzp == null || mediaQueueItem.zzp == null || JsonUtils.areJsonValuesEquivalent(this.zzp, mediaQueueItem.zzp)) && zzcv.zza(this.zzej, mediaQueueItem.zzej) && this.zzek == mediaQueueItem.zzek && this.zzdm == mediaQueueItem.zzdm && this.zzei == mediaQueueItem.zzei && this.zzel == mediaQueueItem.zzel && this.zzem == mediaQueueItem.zzem && Arrays.equals(this.zzdp, mediaQueueItem.zzdp) != null;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzej, Integer.valueOf(this.zzek), Boolean.valueOf(this.zzdm), Double.valueOf(this.zzei), Double.valueOf(this.zzel), Double.valueOf(this.zzem), Integer.valueOf(Arrays.hashCode(this.zzdp)), String.valueOf(this.zzp));
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.zzj = this.zzp == null ? null : this.zzp.toString();
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, getMedia(), i, false);
        SafeParcelWriter.writeInt(parcel, 3, getItemId());
        SafeParcelWriter.writeBoolean(parcel, 4, getAutoplay());
        SafeParcelWriter.writeDouble(parcel, 5, getStartTime());
        SafeParcelWriter.writeDouble(parcel, 6, getPlaybackDuration());
        SafeParcelWriter.writeDouble(parcel, 7, getPreloadTime());
        SafeParcelWriter.writeLongArray(parcel, 8, getActiveTrackIds(), false);
        SafeParcelWriter.writeString(parcel, 9, this.zzj, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
