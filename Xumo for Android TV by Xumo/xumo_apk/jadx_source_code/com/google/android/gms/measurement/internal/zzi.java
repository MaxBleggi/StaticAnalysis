package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "AppMetadataCreator")
@Reserved({1, 20})
public final class zzi extends AbstractSafeParcelable {
    public static final Creator<zzi> CREATOR = new zzj();
    @Field(id = 2)
    public final String packageName;
    @Field(id = 6)
    public final long zzadt;
    @Field(id = 3)
    public final String zzafx;
    @Field(id = 12)
    public final String zzafz;
    @Field(defaultValueUnchecked = "Integer.MIN_VALUE", id = 11)
    public final long zzagd;
    @Field(id = 5)
    public final String zzage;
    @Field(id = 7)
    public final long zzagf;
    @Field(defaultValue = "true", id = 9)
    public final boolean zzagg;
    @Field(id = 13)
    public final long zzagh;
    @Field(defaultValue = "true", id = 16)
    public final boolean zzagi;
    @Field(defaultValue = "true", id = 17)
    public final boolean zzagj;
    @Field(id = 19)
    public final String zzagk;
    @Field(id = 8)
    public final String zzagy;
    @Field(id = 10)
    public final boolean zzagz;
    @Field(id = 14)
    public final long zzaha;
    @Field(id = 15)
    public final int zzahb;
    @Field(id = 18)
    public final boolean zzahc;
    @Field(id = 4)
    public final String zzts;

    zzi(String str, String str2, String str3, long j, String str4, long j2, long j3, String str5, boolean z, boolean z2, String str6, long j4, long j5, int i, boolean z3, boolean z4, boolean z5, String str7) {
        Preconditions.checkNotEmpty(str);
        this.packageName = str;
        r0.zzafx = TextUtils.isEmpty(str2) ? null : str2;
        r0.zzts = str3;
        r0.zzagd = j;
        r0.zzage = str4;
        r0.zzadt = j2;
        r0.zzagf = j3;
        r0.zzagy = str5;
        r0.zzagg = z;
        r0.zzagz = z2;
        r0.zzafz = str6;
        r0.zzagh = j4;
        r0.zzaha = j5;
        r0.zzahb = i;
        r0.zzagi = z3;
        r0.zzagj = z4;
        r0.zzahc = z5;
        r0.zzagk = str7;
    }

    @Constructor
    zzi(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3, @Param(id = 5) String str4, @Param(id = 6) long j, @Param(id = 7) long j2, @Param(id = 8) String str5, @Param(id = 9) boolean z, @Param(id = 10) boolean z2, @Param(id = 11) long j3, @Param(id = 12) String str6, @Param(id = 13) long j4, @Param(id = 14) long j5, @Param(id = 15) int i, @Param(id = 16) boolean z3, @Param(id = 17) boolean z4, @Param(id = 18) boolean z5, @Param(id = 19) String str7) {
        this.packageName = str;
        this.zzafx = str2;
        this.zzts = str3;
        this.zzagd = j3;
        this.zzage = str4;
        this.zzadt = j;
        this.zzagf = j2;
        this.zzagy = str5;
        this.zzagg = z;
        this.zzagz = z2;
        this.zzafz = str6;
        this.zzagh = j4;
        this.zzaha = j5;
        this.zzahb = i;
        this.zzagi = z3;
        this.zzagj = z4;
        this.zzahc = z5;
        this.zzagk = str7;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.packageName, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzafx, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzts, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzage, false);
        SafeParcelWriter.writeLong(parcel, 6, this.zzadt);
        SafeParcelWriter.writeLong(parcel, 7, this.zzagf);
        SafeParcelWriter.writeString(parcel, 8, this.zzagy, false);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzagg);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzagz);
        SafeParcelWriter.writeLong(parcel, 11, this.zzagd);
        SafeParcelWriter.writeString(parcel, 12, this.zzafz, false);
        SafeParcelWriter.writeLong(parcel, 13, this.zzagh);
        SafeParcelWriter.writeLong(parcel, 14, this.zzaha);
        SafeParcelWriter.writeInt(parcel, 15, this.zzahb);
        SafeParcelWriter.writeBoolean(parcel, 16, this.zzagi);
        SafeParcelWriter.writeBoolean(parcel, 17, this.zzagj);
        SafeParcelWriter.writeBoolean(parcel, 18, this.zzahc);
        SafeParcelWriter.writeString(parcel, 19, this.zzagk, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
