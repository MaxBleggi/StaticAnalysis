package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "EventParcelCreator")
@Reserved({1})
public final class zzae extends AbstractSafeParcelable {
    public static final Creator<zzae> CREATOR = new zzaf();
    @Field(id = 2)
    public final String name;
    @Field(id = 4)
    public final String origin;
    @Field(id = 3)
    public final zzab zzaig;
    @Field(id = 5)
    public final long zzais;

    @Constructor
    public zzae(@Param(id = 2) String str, @Param(id = 3) zzab com_google_android_gms_measurement_internal_zzab, @Param(id = 4) String str2, @Param(id = 5) long j) {
        this.name = str;
        this.zzaig = com_google_android_gms_measurement_internal_zzab;
        this.origin = str2;
        this.zzais = j;
    }

    zzae(zzae com_google_android_gms_measurement_internal_zzae, long j) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzae);
        this.name = com_google_android_gms_measurement_internal_zzae.name;
        this.zzaig = com_google_android_gms_measurement_internal_zzae.zzaig;
        this.origin = com_google_android_gms_measurement_internal_zzae.origin;
        this.zzais = j;
    }

    public final String toString() {
        String str = this.origin;
        String str2 = this.name;
        String valueOf = String.valueOf(this.zzaig);
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 21) + String.valueOf(str2).length()) + String.valueOf(valueOf).length());
        stringBuilder.append("origin=");
        stringBuilder.append(str);
        stringBuilder.append(",name=");
        stringBuilder.append(str2);
        stringBuilder.append(",params=");
        stringBuilder.append(valueOf);
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzaig, i, false);
        SafeParcelWriter.writeString(parcel, 4, this.origin, false);
        SafeParcelWriter.writeLong(parcel, 5, this.zzais);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
