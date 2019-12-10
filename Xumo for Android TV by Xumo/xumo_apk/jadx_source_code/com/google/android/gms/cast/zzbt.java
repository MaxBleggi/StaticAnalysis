package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.internal.cast.zzcv;
import org.json.JSONException;
import org.json.JSONObject;

@Class(creator = "RequestItemCreator")
@Reserved({1})
public final class zzbt extends AbstractSafeParcelable {
    public static final Creator<zzbt> CREATOR = new zzbu();
    @Field(getter = "getUrl", id = 2)
    private final String url;
    @Field(getter = "getProtocolType", id = 3)
    private final int zzgu;
    @Field(defaultValue = "0", getter = "getInitialTime", id = 4)
    private final int zzgv;
    @Field(getter = "getHlsSegmentFormat", id = 5)
    private final String zzn;

    @Constructor
    public zzbt(@Param(id = 2) String str, @Param(id = 3) int i, @Param(id = 4) int i2, @HlsSegmentFormat @Param(id = 5) String str2) {
        this.url = str;
        this.zzgu = i;
        this.zzgv = i2;
        this.zzn = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbt)) {
            return false;
        }
        zzbt com_google_android_gms_cast_zzbt = (zzbt) obj;
        return zzcv.zza(this.url, com_google_android_gms_cast_zzbt.url) && zzcv.zza(Integer.valueOf(this.zzgu), Integer.valueOf(com_google_android_gms_cast_zzbt.zzgu)) && zzcv.zza(Integer.valueOf(this.zzgv), Integer.valueOf(com_google_android_gms_cast_zzbt.zzgv)) && zzcv.zza(com_google_android_gms_cast_zzbt.zzn, this.zzn) != null;
    }

    public final JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("url", this.url);
        jSONObject.put("protocolType", this.zzgu);
        jSONObject.put("initialTime", this.zzgv);
        jSONObject.put("hlsSegmentFormat", this.zzn);
        return jSONObject;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.url, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzgu);
        SafeParcelWriter.writeInt(parcel, 4, this.zzgv);
        SafeParcelWriter.writeString(parcel, 5, this.zzn, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    @VisibleForTesting
    public final int hashCode() {
        return Objects.hashCode(this.url, Integer.valueOf(this.zzgu), Integer.valueOf(this.zzgv), this.zzn);
    }
}
