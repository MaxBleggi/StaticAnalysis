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

@Class(creator = "ConditionalUserPropertyParcelCreator")
public final class zzm extends AbstractSafeParcelable {
    public static final Creator<zzm> CREATOR = new zzn();
    @Field(id = 6)
    public boolean active;
    @Field(id = 5)
    public long creationTimestamp;
    @Field(id = 3)
    public String origin;
    @Field(id = 2)
    public String packageName;
    @Field(id = 11)
    public long timeToLive;
    @Field(id = 7)
    public String triggerEventName;
    @Field(id = 9)
    public long triggerTimeout;
    @Field(id = 4)
    public zzfr zzahe;
    @Field(id = 8)
    public zzae zzahf;
    @Field(id = 10)
    public zzae zzahg;
    @Field(id = 12)
    public zzae zzahh;

    zzm(zzm com_google_android_gms_measurement_internal_zzm) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm);
        this.packageName = com_google_android_gms_measurement_internal_zzm.packageName;
        this.origin = com_google_android_gms_measurement_internal_zzm.origin;
        this.zzahe = com_google_android_gms_measurement_internal_zzm.zzahe;
        this.creationTimestamp = com_google_android_gms_measurement_internal_zzm.creationTimestamp;
        this.active = com_google_android_gms_measurement_internal_zzm.active;
        this.triggerEventName = com_google_android_gms_measurement_internal_zzm.triggerEventName;
        this.zzahf = com_google_android_gms_measurement_internal_zzm.zzahf;
        this.triggerTimeout = com_google_android_gms_measurement_internal_zzm.triggerTimeout;
        this.zzahg = com_google_android_gms_measurement_internal_zzm.zzahg;
        this.timeToLive = com_google_android_gms_measurement_internal_zzm.timeToLive;
        this.zzahh = com_google_android_gms_measurement_internal_zzm.zzahh;
    }

    @Constructor
    zzm(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) zzfr com_google_android_gms_measurement_internal_zzfr, @Param(id = 5) long j, @Param(id = 6) boolean z, @Param(id = 7) String str3, @Param(id = 8) zzae com_google_android_gms_measurement_internal_zzae, @Param(id = 9) long j2, @Param(id = 10) zzae com_google_android_gms_measurement_internal_zzae2, @Param(id = 11) long j3, @Param(id = 12) zzae com_google_android_gms_measurement_internal_zzae3) {
        this.packageName = str;
        this.origin = str2;
        this.zzahe = com_google_android_gms_measurement_internal_zzfr;
        this.creationTimestamp = j;
        this.active = z;
        this.triggerEventName = str3;
        this.zzahf = com_google_android_gms_measurement_internal_zzae;
        this.triggerTimeout = j2;
        this.zzahg = com_google_android_gms_measurement_internal_zzae2;
        this.timeToLive = j3;
        this.zzahh = com_google_android_gms_measurement_internal_zzae3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.packageName, false);
        SafeParcelWriter.writeString(parcel, 3, this.origin, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzahe, i, false);
        SafeParcelWriter.writeLong(parcel, 5, this.creationTimestamp);
        SafeParcelWriter.writeBoolean(parcel, 6, this.active);
        SafeParcelWriter.writeString(parcel, 7, this.triggerEventName, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzahf, i, false);
        SafeParcelWriter.writeLong(parcel, 9, this.triggerTimeout);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzahg, i, false);
        SafeParcelWriter.writeLong(parcel, 11, this.timeToLive);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzahh, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
