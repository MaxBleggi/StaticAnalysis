package com.google.android.gms.internal.cast;

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

@Class(creator = "ApplicationStatusCreator")
@Reserved({1})
public final class zzce extends AbstractSafeParcelable {
    public static final Creator<zzce> CREATOR = new zzcf();
    @Field(getter = "getApplicationStatusText", id = 2)
    private String zzwc;

    @Constructor
    zzce(@Param(id = 2) String str) {
        this.zzwc = str;
    }

    public zzce() {
        this(null);
    }

    public final String zzdc() {
        return this.zzwc;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzwc, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzce)) {
            return null;
        }
        return zzcv.zza(this.zzwc, ((zzce) obj).zzwc);
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzwc);
    }
}
