package com.google.android.gms.internal.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.zzad;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "DeviceStatusCreator")
@Reserved({1})
public final class zzcw extends AbstractSafeParcelable {
    public static final Creator<zzcw> CREATOR = new zzcx();
    @Field(getter = "getVolume", id = 2)
    private double zzex;
    @Field(getter = "getMuteState", id = 3)
    private boolean zzey;
    @Field(getter = "getEqualizerSettings", id = 7)
    private zzad zzwq;
    @Field(getter = "getActiveInputState", id = 4)
    private int zzwr;
    @Field(getter = "getStandbyState", id = 6)
    private int zzws;
    @Field(getter = "getApplicationMetadata", id = 5)
    private ApplicationMetadata zzxc;

    @Constructor
    zzcw(@Param(id = 2) double d, @Param(id = 3) boolean z, @Param(id = 4) int i, @Param(id = 5) ApplicationMetadata applicationMetadata, @Param(id = 6) int i2, @Param(id = 7) zzad com_google_android_gms_cast_zzad) {
        this.zzex = d;
        this.zzey = z;
        this.zzwr = i;
        this.zzxc = applicationMetadata;
        this.zzws = i2;
        this.zzwq = com_google_android_gms_cast_zzad;
    }

    public zzcw() {
        this(Double.NaN, false, -1, null, -1, null);
    }

    public final double getVolume() {
        return this.zzex;
    }

    public final boolean zzdl() {
        return this.zzey;
    }

    public final int getActiveInputState() {
        return this.zzwr;
    }

    public final int getStandbyState() {
        return this.zzws;
    }

    public final ApplicationMetadata getApplicationMetadata() {
        return this.zzxc;
    }

    public final zzad zzdm() {
        return this.zzwq;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeDouble(parcel, 2, this.zzex);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzey);
        SafeParcelWriter.writeInt(parcel, 4, this.zzwr);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzxc, i, false);
        SafeParcelWriter.writeInt(parcel, 6, this.zzws);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzwq, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcw)) {
            return false;
        }
        zzcw com_google_android_gms_internal_cast_zzcw = (zzcw) obj;
        return this.zzex == com_google_android_gms_internal_cast_zzcw.zzex && this.zzey == com_google_android_gms_internal_cast_zzcw.zzey && this.zzwr == com_google_android_gms_internal_cast_zzcw.zzwr && zzcv.zza(this.zzxc, com_google_android_gms_internal_cast_zzcw.zzxc) && this.zzws == com_google_android_gms_internal_cast_zzcw.zzws && zzcv.zza(this.zzwq, this.zzwq) != null;
    }

    public final int hashCode() {
        return Objects.hashCode(Double.valueOf(this.zzex), Boolean.valueOf(this.zzey), Integer.valueOf(this.zzwr), this.zzxc, Integer.valueOf(this.zzws), this.zzwq);
    }
}
