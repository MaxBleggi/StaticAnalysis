package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.internal.cast.zzcv;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

@Class(creator = "AdBreakInfoCreator")
@Reserved({1})
public class AdBreakInfo extends AbstractSafeParcelable {
    public static final Creator<AdBreakInfo> CREATOR = new zzb();
    @Field(getter = "getId", id = 3)
    private final String zze;
    @Field(getter = "getDurationInMs", id = 4)
    private final long zzg;
    @Field(getter = "getPlaybackPositionInMs", id = 2)
    private final long zzq;
    @Field(getter = "isWatched", id = 5)
    private final boolean zzr;
    @Field(getter = "getBreakClipIds", id = 6)
    private String[] zzs;
    @Field(getter = "isEmbedded", id = 7)
    private final boolean zzt;

    public static class Builder {
        private String zze = null;
        private long zzg = 0;
        private long zzq = 0;
        private boolean zzr = false;
        private String[] zzs = null;
        private boolean zzt = false;

        public Builder(long j) {
            this.zzq = j;
        }

        public Builder setId(String str) {
            this.zze = str;
            return this;
        }

        public Builder setDurationInMs(long j) {
            this.zzg = j;
            return this;
        }

        public Builder setIsWatched(boolean z) {
            this.zzr = z;
            return this;
        }

        public Builder setIsEmbedded(boolean z) {
            this.zzt = z;
            return this;
        }

        public Builder setBreakClipIds(String[] strArr) {
            this.zzs = strArr;
            return this;
        }

        public AdBreakInfo build() {
            return new AdBreakInfo(this.zzq, this.zze, this.zzg, this.zzr, this.zzs, this.zzt);
        }
    }

    @Constructor
    public AdBreakInfo(@Param(id = 2) long j, @Param(id = 3) String str, @Param(id = 4) long j2, @Param(id = 5) boolean z, @Param(id = 6) String[] strArr, @Param(id = 7) boolean z2) {
        this.zzq = j;
        this.zze = str;
        this.zzg = j2;
        this.zzr = z;
        this.zzs = strArr;
        this.zzt = z2;
    }

    public long getPlaybackPositionInMs() {
        return this.zzq;
    }

    public String getId() {
        return this.zze;
    }

    public long getDurationInMs() {
        return this.zzg;
    }

    public boolean isWatched() {
        return this.zzr;
    }

    public boolean isEmbedded() {
        return this.zzt;
    }

    public String[] getBreakClipIds() {
        return this.zzs;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 2, getPlaybackPositionInMs());
        SafeParcelWriter.writeString(parcel, 3, getId(), false);
        SafeParcelWriter.writeLong(parcel, 4, getDurationInMs());
        SafeParcelWriter.writeBoolean(parcel, 5, isWatched());
        SafeParcelWriter.writeStringArray(parcel, 6, getBreakClipIds(), false);
        SafeParcelWriter.writeBoolean(parcel, 7, isEmbedded());
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public int hashCode() {
        return this.zze.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdBreakInfo)) {
            return false;
        }
        AdBreakInfo adBreakInfo = (AdBreakInfo) obj;
        return zzcv.zza(this.zze, adBreakInfo.zze) && this.zzq == adBreakInfo.zzq && this.zzg == adBreakInfo.zzg && this.zzr == adBreakInfo.zzr && Arrays.equals(this.zzs, adBreakInfo.zzs) && this.zzt == adBreakInfo.zzt;
    }

    static AdBreakInfo zzb(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.has("id")) {
            if (jSONObject.has("position")) {
                try {
                    String[] strArr;
                    String string = jSONObject.getString("id");
                    double d = (double) jSONObject.getLong("position");
                    Double.isNaN(d);
                    long j = (long) (d * 1000.0d);
                    boolean optBoolean = jSONObject.optBoolean("isWatched");
                    d = (double) jSONObject.optLong("duration");
                    Double.isNaN(d);
                    long j2 = (long) (d * 1000.0d);
                    JSONArray optJSONArray = jSONObject.optJSONArray("breakClipIds");
                    if (optJSONArray != null) {
                        String[] strArr2 = new String[optJSONArray.length()];
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            strArr2[i] = optJSONArray.getString(i);
                        }
                        strArr = strArr2;
                    } else {
                        strArr = null;
                    }
                    return new AdBreakInfo(j, string, j2, optBoolean, strArr, jSONObject.optBoolean("isEmbedded"));
                } catch (JSONObject jSONObject2) {
                    Log.d("AdBreakInfo", String.format(Locale.ROOT, "Error while creating an AdBreakInfo from JSON: %s", new Object[]{jSONObject2.getMessage()}));
                    return null;
                }
            }
        }
        return null;
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
        r6 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "id";	 Catch:{ JSONException -> 0x0053 }
        r2 = r6.zze;	 Catch:{ JSONException -> 0x0053 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0053 }
        r1 = "position";	 Catch:{ JSONException -> 0x0053 }
        r2 = r6.zzq;	 Catch:{ JSONException -> 0x0053 }
        r2 = (double) r2;
        r4 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        java.lang.Double.isNaN(r2);
        r2 = r2 / r4;
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0053 }
        r1 = "isWatched";	 Catch:{ JSONException -> 0x0053 }
        r2 = r6.zzr;	 Catch:{ JSONException -> 0x0053 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0053 }
        r1 = "isEmbedded";	 Catch:{ JSONException -> 0x0053 }
        r2 = r6.zzt;	 Catch:{ JSONException -> 0x0053 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0053 }
        r1 = "duration";	 Catch:{ JSONException -> 0x0053 }
        r2 = r6.zzg;	 Catch:{ JSONException -> 0x0053 }
        r2 = (double) r2;
        java.lang.Double.isNaN(r2);
        r2 = r2 / r4;
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0053 }
        r1 = r6.zzs;	 Catch:{ JSONException -> 0x0053 }
        if (r1 == 0) goto L_0x0053;	 Catch:{ JSONException -> 0x0053 }
    L_0x003b:
        r1 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0053 }
        r1.<init>();	 Catch:{ JSONException -> 0x0053 }
        r2 = r6.zzs;	 Catch:{ JSONException -> 0x0053 }
        r3 = r2.length;	 Catch:{ JSONException -> 0x0053 }
        r4 = 0;	 Catch:{ JSONException -> 0x0053 }
    L_0x0044:
        if (r4 >= r3) goto L_0x004e;	 Catch:{ JSONException -> 0x0053 }
    L_0x0046:
        r5 = r2[r4];	 Catch:{ JSONException -> 0x0053 }
        r1.put(r5);	 Catch:{ JSONException -> 0x0053 }
        r4 = r4 + 1;	 Catch:{ JSONException -> 0x0053 }
        goto L_0x0044;	 Catch:{ JSONException -> 0x0053 }
    L_0x004e:
        r2 = "breakClipIds";	 Catch:{ JSONException -> 0x0053 }
        r0.put(r2, r1);	 Catch:{ JSONException -> 0x0053 }
    L_0x0053:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.AdBreakInfo.toJson():org.json.JSONObject");
    }
}
