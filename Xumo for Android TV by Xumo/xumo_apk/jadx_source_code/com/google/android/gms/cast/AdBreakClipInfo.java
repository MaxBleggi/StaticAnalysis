package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.internal.cast.zzcv;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

@Class(creator = "AdBreakClipInfoCreator")
@Reserved({1})
public class AdBreakClipInfo extends AbstractSafeParcelable {
    public static final long AD_BREAK_CLIP_NOT_SKIPPABLE = -1;
    public static final Creator<AdBreakClipInfo> CREATOR = new zza();
    @Field(getter = "getMimeType", id = 6)
    private final String mimeType;
    @Field(getter = "getId", id = 2)
    private final String zze;
    @Field(getter = "getTitle", id = 3)
    private final String zzf;
    @Field(getter = "getDurationInMs", id = 4)
    private final long zzg;
    @Field(getter = "getContentUrl", id = 5)
    private final String zzh;
    @Field(getter = "getClickThroughUrl", id = 7)
    private final String zzi;
    @Field(getter = "getCustomDataAsString", id = 8)
    private String zzj;
    @Field(getter = "getContentId", id = 9)
    private String zzk;
    @Field(getter = "getImageUrl", id = 10)
    private String zzl;
    @Field(getter = "getWhenSkippableInMs", id = 11)
    private final long zzm;
    @HlsSegmentFormat
    @Field(getter = "getHlsSegmentFormat", id = 12)
    private final String zzn;
    @Field(getter = "getVastAdsRequest", id = 13)
    private final VastAdsRequest zzo;
    private JSONObject zzp;

    public static class Builder {
        private String mimeType = null;
        private String zze = null;
        private String zzf = null;
        private long zzg = 0;
        private String zzh = null;
        private String zzi = null;
        private String zzj = null;
        private String zzk = null;
        private String zzl = null;
        private long zzm = -1;
        @HlsSegmentFormat
        private String zzn;
        private VastAdsRequest zzo = null;

        public Builder(String str) {
            this.zze = str;
        }

        public Builder setTitle(String str) {
            this.zzf = str;
            return this;
        }

        public Builder setDurationInMs(long j) {
            this.zzg = j;
            return this;
        }

        public Builder setContentUrl(String str) {
            this.zzh = str;
            return this;
        }

        public Builder setMimeType(String str) {
            this.mimeType = str;
            return this;
        }

        public Builder setClickThroughUrl(String str) {
            this.zzi = str;
            return this;
        }

        public Builder setCustomDataJsonString(String str) {
            this.zzj = str;
            return this;
        }

        public Builder setContentId(String str) {
            this.zzk = str;
            return this;
        }

        public Builder setImageUrl(String str) {
            this.zzl = str;
            return this;
        }

        public Builder setWhenSkippableInMs(long j) {
            this.zzm = j;
            return this;
        }

        public Builder setHlsSegmentFormat(String str) {
            this.zzn = str;
            return this;
        }

        public Builder setVastAdsRequest(VastAdsRequest vastAdsRequest) {
            this.zzo = vastAdsRequest;
            return this;
        }

        public AdBreakClipInfo build() {
            return new AdBreakClipInfo(this.zze, this.zzf, this.zzg, this.zzh, this.mimeType, this.zzi, this.zzj, this.zzk, this.zzl, this.zzm, this.zzn, this.zzo);
        }
    }

    @Constructor
    AdBreakClipInfo(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) long j, @Param(id = 5) String str3, @Param(id = 6) String str4, @Param(id = 7) String str5, @Param(id = 8) String str6, @Param(id = 9) String str7, @Param(id = 10) String str8, @Param(id = 11) long j2, @HlsSegmentFormat @Param(id = 12) String str9, @Param(id = 13) VastAdsRequest vastAdsRequest) {
        this.zze = str;
        this.zzf = str2;
        this.zzg = j;
        this.zzh = str3;
        this.mimeType = str4;
        this.zzi = str5;
        this.zzj = str6;
        this.zzk = str7;
        this.zzl = str8;
        this.zzm = j2;
        this.zzn = str9;
        this.zzo = vastAdsRequest;
        if (TextUtils.isEmpty(this.zzj) == null) {
            try {
                this.zzp = new JSONObject(str6);
                return;
            } catch (String str10) {
                Log.w("AdBreakClipInfo", String.format(Locale.ROOT, "Error creating AdBreakClipInfo: %s", new Object[]{str10.getMessage()}));
                this.zzj = null;
                this.zzp = new JSONObject();
                return;
            }
        }
        this.zzp = new JSONObject();
    }

    public String getId() {
        return this.zze;
    }

    public String getTitle() {
        return this.zzf;
    }

    public long getDurationInMs() {
        return this.zzg;
    }

    public String getContentUrl() {
        return this.zzh;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public String getClickThroughUrl() {
        return this.zzi;
    }

    public JSONObject getCustomData() {
        return this.zzp;
    }

    public String getContentId() {
        return this.zzk;
    }

    public String getImageUrl() {
        return this.zzl;
    }

    public long getWhenSkippableInMs() {
        return this.zzm;
    }

    public String getHlsSegmentFormat() {
        return this.zzn;
    }

    public VastAdsRequest getVastAdsRequest() {
        return this.zzo;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getId(), false);
        SafeParcelWriter.writeString(parcel, 3, getTitle(), false);
        SafeParcelWriter.writeLong(parcel, 4, getDurationInMs());
        SafeParcelWriter.writeString(parcel, 5, getContentUrl(), false);
        SafeParcelWriter.writeString(parcel, 6, getMimeType(), false);
        SafeParcelWriter.writeString(parcel, 7, getClickThroughUrl(), false);
        SafeParcelWriter.writeString(parcel, 8, this.zzj, false);
        SafeParcelWriter.writeString(parcel, 9, getContentId(), false);
        SafeParcelWriter.writeString(parcel, 10, getImageUrl(), false);
        SafeParcelWriter.writeLong(parcel, 11, getWhenSkippableInMs());
        SafeParcelWriter.writeString(parcel, 12, getHlsSegmentFormat(), false);
        SafeParcelWriter.writeParcelable(parcel, 13, getVastAdsRequest(), i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public int hashCode() {
        return Objects.hashCode(this.zze, this.zzf, Long.valueOf(this.zzg), this.zzh, this.mimeType, this.zzi, this.zzj, this.zzk, this.zzl, Long.valueOf(this.zzm), this.zzn, this.zzo);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdBreakClipInfo)) {
            return false;
        }
        AdBreakClipInfo adBreakClipInfo = (AdBreakClipInfo) obj;
        return zzcv.zza(this.zze, adBreakClipInfo.zze) && zzcv.zza(this.zzf, adBreakClipInfo.zzf) && this.zzg == adBreakClipInfo.zzg && zzcv.zza(this.zzh, adBreakClipInfo.zzh) && zzcv.zza(this.mimeType, adBreakClipInfo.mimeType) && zzcv.zza(this.zzi, adBreakClipInfo.zzi) && zzcv.zza(this.zzj, adBreakClipInfo.zzj) && zzcv.zza(this.zzk, adBreakClipInfo.zzk) && zzcv.zza(this.zzl, adBreakClipInfo.zzl) && this.zzm == adBreakClipInfo.zzm && zzcv.zza(this.zzn, adBreakClipInfo.zzn) && zzcv.zza(this.zzo, adBreakClipInfo.zzo) != null;
    }

    static AdBreakClipInfo zza(JSONObject jSONObject) {
        JSONObject jSONObject2 = jSONObject;
        if (jSONObject2 == null || !jSONObject2.has("id")) {
            return null;
        }
        try {
            long j;
            String jSONObject3;
            String string = jSONObject2.getString("id");
            double optLong = (double) jSONObject2.optLong("duration");
            Double.isNaN(optLong);
            long j2 = (long) (optLong * 1000.0d);
            String optString = jSONObject2.optString("clickThroughUrl", null);
            String optString2 = jSONObject2.optString("contentUrl", null);
            String optString3 = jSONObject2.optString("mimeType", null);
            if (optString3 == null) {
                optString3 = jSONObject2.optString(JsonKey.CONTENT_TYPE, null);
            }
            String str = optString3;
            String optString4 = jSONObject2.optString("title", null);
            JSONObject optJSONObject = jSONObject2.optJSONObject(VideoCastManager.EXTRA_CUSTOM_DATA);
            String optString5 = jSONObject2.optString("contentId", null);
            String optString6 = jSONObject2.optString("posterUrl", null);
            if (jSONObject2.has("whenSkippable")) {
                double intValue = (double) ((Integer) jSONObject2.get("whenSkippable")).intValue();
                Double.isNaN(intValue);
                j = (long) (intValue * 1000.0d);
            } else {
                j = -1;
            }
            String optString7 = jSONObject2.optString("hlsSegmentFormat", null);
            VastAdsRequest fromJson = VastAdsRequest.fromJson(jSONObject2.optJSONObject("vastAdsRequest"));
            if (optJSONObject != null) {
                if (optJSONObject.length() != 0) {
                    jSONObject3 = optJSONObject.toString();
                    return new AdBreakClipInfo(string, optString4, j2, optString2, str, optString, jSONObject3, optString5, optString6, j, optString7, fromJson);
                }
            }
            jSONObject3 = null;
            return new AdBreakClipInfo(string, optString4, j2, optString2, str, optString, jSONObject3, optString5, optString6, j, optString7, fromJson);
        } catch (JSONException e) {
            Log.d("AdBreakClipInfo", String.format(Locale.ROOT, "Error while creating an AdBreakClipInfo from JSON: %s", new Object[]{e.getMessage()}));
            return null;
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
        r8 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "id";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zze;	 Catch:{ JSONException -> 0x0098 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
        r1 = "duration";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zzg;	 Catch:{ JSONException -> 0x0098 }
        r2 = (double) r2;
        r4 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        java.lang.Double.isNaN(r2);
        r2 = r2 / r4;
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
        r1 = r8.zzm;	 Catch:{ JSONException -> 0x0098 }
        r6 = -1;	 Catch:{ JSONException -> 0x0098 }
        r3 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));	 Catch:{ JSONException -> 0x0098 }
        if (r3 == 0) goto L_0x0031;	 Catch:{ JSONException -> 0x0098 }
    L_0x0025:
        r1 = "whenSkippable";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zzm;	 Catch:{ JSONException -> 0x0098 }
        r2 = (double) r2;
        java.lang.Double.isNaN(r2);
        r2 = r2 / r4;
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
    L_0x0031:
        r1 = r8.zzk;	 Catch:{ JSONException -> 0x0098 }
        if (r1 == 0) goto L_0x003c;	 Catch:{ JSONException -> 0x0098 }
    L_0x0035:
        r1 = "contentId";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zzk;	 Catch:{ JSONException -> 0x0098 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
    L_0x003c:
        r1 = r8.mimeType;	 Catch:{ JSONException -> 0x0098 }
        if (r1 == 0) goto L_0x0047;	 Catch:{ JSONException -> 0x0098 }
    L_0x0040:
        r1 = "contentType";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.mimeType;	 Catch:{ JSONException -> 0x0098 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
    L_0x0047:
        r1 = r8.zzf;	 Catch:{ JSONException -> 0x0098 }
        if (r1 == 0) goto L_0x0052;	 Catch:{ JSONException -> 0x0098 }
    L_0x004b:
        r1 = "title";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zzf;	 Catch:{ JSONException -> 0x0098 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
    L_0x0052:
        r1 = r8.zzh;	 Catch:{ JSONException -> 0x0098 }
        if (r1 == 0) goto L_0x005d;	 Catch:{ JSONException -> 0x0098 }
    L_0x0056:
        r1 = "contentUrl";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zzh;	 Catch:{ JSONException -> 0x0098 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
    L_0x005d:
        r1 = r8.zzi;	 Catch:{ JSONException -> 0x0098 }
        if (r1 == 0) goto L_0x0068;	 Catch:{ JSONException -> 0x0098 }
    L_0x0061:
        r1 = "clickThroughUrl";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zzi;	 Catch:{ JSONException -> 0x0098 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
    L_0x0068:
        r1 = r8.zzp;	 Catch:{ JSONException -> 0x0098 }
        if (r1 == 0) goto L_0x0073;	 Catch:{ JSONException -> 0x0098 }
    L_0x006c:
        r1 = "customData";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zzp;	 Catch:{ JSONException -> 0x0098 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
    L_0x0073:
        r1 = r8.zzl;	 Catch:{ JSONException -> 0x0098 }
        if (r1 == 0) goto L_0x007e;	 Catch:{ JSONException -> 0x0098 }
    L_0x0077:
        r1 = "posterUrl";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zzl;	 Catch:{ JSONException -> 0x0098 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
    L_0x007e:
        r1 = r8.zzn;	 Catch:{ JSONException -> 0x0098 }
        if (r1 == 0) goto L_0x0089;	 Catch:{ JSONException -> 0x0098 }
    L_0x0082:
        r1 = "hlsSegmentFormat";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zzn;	 Catch:{ JSONException -> 0x0098 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
    L_0x0089:
        r1 = r8.zzo;	 Catch:{ JSONException -> 0x0098 }
        if (r1 == 0) goto L_0x0098;	 Catch:{ JSONException -> 0x0098 }
    L_0x008d:
        r1 = "vastAdsRequest";	 Catch:{ JSONException -> 0x0098 }
        r2 = r8.zzo;	 Catch:{ JSONException -> 0x0098 }
        r2 = r2.toJson();	 Catch:{ JSONException -> 0x0098 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0098 }
    L_0x0098:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.AdBreakClipInfo.toJson():org.json.JSONObject");
    }
}
