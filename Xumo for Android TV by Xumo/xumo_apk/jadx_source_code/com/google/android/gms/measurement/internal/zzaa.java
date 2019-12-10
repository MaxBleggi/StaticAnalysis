package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

final class zzaa {
    final String name;
    final long zzaih;
    final long zzaii;
    final long zzaij;
    final long zzaik;
    final Long zzail;
    final Long zzaim;
    final Long zzain;
    final Boolean zzaio;
    final String zztt;

    zzaa(String str, String str2, long j, long j2, long j3, long j4, Long l, Long l2, Long l3, Boolean bool) {
        zzaa com_google_android_gms_measurement_internal_zzaa = this;
        long j5 = j;
        long j6 = j2;
        long j7 = j4;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        boolean z = false;
        Preconditions.checkArgument(j5 >= 0);
        Preconditions.checkArgument(j6 >= 0);
        if (j7 >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z);
        com_google_android_gms_measurement_internal_zzaa.zztt = str;
        com_google_android_gms_measurement_internal_zzaa.name = str2;
        com_google_android_gms_measurement_internal_zzaa.zzaih = j5;
        com_google_android_gms_measurement_internal_zzaa.zzaii = j6;
        com_google_android_gms_measurement_internal_zzaa.zzaij = j3;
        com_google_android_gms_measurement_internal_zzaa.zzaik = j7;
        com_google_android_gms_measurement_internal_zzaa.zzail = l;
        com_google_android_gms_measurement_internal_zzaa.zzaim = l2;
        com_google_android_gms_measurement_internal_zzaa.zzain = l3;
        com_google_android_gms_measurement_internal_zzaa.zzaio = bool;
    }

    final zzaa zzai(long j) {
        return new zzaa(this.zztt, this.name, this.zzaih, this.zzaii, j, this.zzaik, this.zzail, this.zzaim, this.zzain, this.zzaio);
    }

    final zzaa zza(long j, long j2) {
        return new zzaa(this.zztt, this.name, this.zzaih, this.zzaii, this.zzaij, j, Long.valueOf(j2), this.zzaim, this.zzain, this.zzaio);
    }

    final zzaa zza(Long l, Long l2, Boolean bool) {
        zzaa com_google_android_gms_measurement_internal_zzaa = this;
        Boolean bool2 = (bool == null || bool.booleanValue()) ? bool : null;
        return new zzaa(com_google_android_gms_measurement_internal_zzaa.zztt, com_google_android_gms_measurement_internal_zzaa.name, com_google_android_gms_measurement_internal_zzaa.zzaih, com_google_android_gms_measurement_internal_zzaa.zzaii, com_google_android_gms_measurement_internal_zzaa.zzaij, com_google_android_gms_measurement_internal_zzaa.zzaik, com_google_android_gms_measurement_internal_zzaa.zzail, l, l2, bool2);
    }
}
