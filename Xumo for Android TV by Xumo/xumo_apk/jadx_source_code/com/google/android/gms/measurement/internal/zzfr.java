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

@Class(creator = "UserAttributeParcelCreator")
public final class zzfr extends AbstractSafeParcelable {
    public static final Creator<zzfr> CREATOR = new zzfs();
    @Field(id = 2)
    public final String name;
    @Field(id = 7)
    public final String origin;
    @Field(id = 1)
    private final int versionCode;
    @Field(id = 6)
    private final String zzamw;
    @Field(id = 3)
    public final long zzaux;
    @Field(id = 4)
    private final Long zzauy;
    @Field(id = 5)
    private final Float zzauz;
    @Field(id = 8)
    private final Double zzava;

    zzfr(zzft com_google_android_gms_measurement_internal_zzft) {
        this(com_google_android_gms_measurement_internal_zzft.name, com_google_android_gms_measurement_internal_zzft.zzaux, com_google_android_gms_measurement_internal_zzft.value, com_google_android_gms_measurement_internal_zzft.origin);
    }

    zzfr(String str, long j, Object obj, String str2) {
        Preconditions.checkNotEmpty(str);
        this.versionCode = 2;
        this.name = str;
        this.zzaux = j;
        this.origin = str2;
        if (obj == null) {
            this.zzauy = null;
            this.zzauz = null;
            this.zzava = null;
            this.zzamw = null;
        } else if ((obj instanceof Long) != null) {
            this.zzauy = (Long) obj;
            this.zzauz = null;
            this.zzava = null;
            this.zzamw = null;
        } else if ((obj instanceof String) != null) {
            this.zzauy = null;
            this.zzauz = null;
            this.zzava = null;
            this.zzamw = (String) obj;
        } else if ((obj instanceof Double) != null) {
            this.zzauy = null;
            this.zzauz = null;
            this.zzava = (Double) obj;
            this.zzamw = null;
        } else {
            throw new IllegalArgumentException("User attribute given of un-supported type");
        }
    }

    @Constructor
    zzfr(@Param(id = 1) int i, @Param(id = 2) String str, @Param(id = 3) long j, @Param(id = 4) Long l, @Param(id = 5) Float f, @Param(id = 6) String str2, @Param(id = 7) String str3, @Param(id = 8) Double d) {
        this.versionCode = i;
        this.name = str;
        this.zzaux = j;
        this.zzauy = l;
        str = null;
        this.zzauz = null;
        if (i == 1) {
            if (f != null) {
                str = Double.valueOf(f.doubleValue());
            }
            this.zzava = str;
        } else {
            this.zzava = d;
        }
        this.zzamw = str2;
        this.origin = str3;
    }

    public final Object getValue() {
        if (this.zzauy != null) {
            return this.zzauy;
        }
        if (this.zzava != null) {
            return this.zzava;
        }
        return this.zzamw != null ? this.zzamw : null;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzaux);
        SafeParcelWriter.writeLongObject(parcel, 4, this.zzauy, false);
        SafeParcelWriter.writeFloatObject(parcel, 5, null, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzamw, false);
        SafeParcelWriter.writeString(parcel, 7, this.origin, false);
        SafeParcelWriter.writeDoubleObject(parcel, 8, this.zzava, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
