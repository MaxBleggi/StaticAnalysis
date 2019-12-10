package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.internal.cast.zzcv;
import org.json.JSONObject;

@Class(creator = "VastAdsRequestCreator")
@Reserved({1})
public class VastAdsRequest extends AbstractSafeParcelable {
    public static final Creator<VastAdsRequest> CREATOR = new zzbw();
    @Field(getter = "getAdTagUrl", id = 2)
    private final String zzha;
    @Field(getter = "getAdsResponse", id = 3)
    private final String zzhb;

    public static class Builder {
        private String zzha = null;
        private String zzhb = null;

        public Builder setAdTagUrl(String str) {
            this.zzha = str;
            return this;
        }

        public Builder setAdsResponse(String str) {
            this.zzhb = str;
            return this;
        }

        public VastAdsRequest build() {
            return new VastAdsRequest(this.zzha, this.zzhb);
        }
    }

    @Constructor
    VastAdsRequest(@Param(id = 2) String str, @Param(id = 3) String str2) {
        this.zzha = str;
        this.zzhb = str2;
    }

    public String getAdTagUrl() {
        return this.zzha;
    }

    public String getAdsResponse() {
        return this.zzhb;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getAdTagUrl(), false);
        SafeParcelWriter.writeString(parcel, 3, getAdsResponse(), false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public int hashCode() {
        return Objects.hashCode(this.zzha, this.zzhb);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VastAdsRequest)) {
            return false;
        }
        VastAdsRequest vastAdsRequest = (VastAdsRequest) obj;
        return zzcv.zza(this.zzha, vastAdsRequest.zzha) && zzcv.zza(this.zzhb, vastAdsRequest.zzhb) != null;
    }

    public static VastAdsRequest fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        return new VastAdsRequest(jSONObject.optString("adTagUrl", null), jSONObject.optString("adsResponse", null));
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
        r3 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r3.zzha;	 Catch:{ JSONException -> 0x001b }
        if (r1 == 0) goto L_0x0010;	 Catch:{ JSONException -> 0x001b }
    L_0x0009:
        r1 = "adTagUrl";	 Catch:{ JSONException -> 0x001b }
        r2 = r3.zzha;	 Catch:{ JSONException -> 0x001b }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x001b }
    L_0x0010:
        r1 = r3.zzhb;	 Catch:{ JSONException -> 0x001b }
        if (r1 == 0) goto L_0x001b;	 Catch:{ JSONException -> 0x001b }
    L_0x0014:
        r1 = "adsResponse";	 Catch:{ JSONException -> 0x001b }
        r2 = r3.zzhb;	 Catch:{ JSONException -> 0x001b }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x001b }
    L_0x001b:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.VastAdsRequest.toJson():org.json.JSONObject");
    }
}
