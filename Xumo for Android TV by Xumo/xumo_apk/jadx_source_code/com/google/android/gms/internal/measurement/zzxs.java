package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzvx.zze;

final class zzxs implements zzxc {
    private final int flags;
    private final String info;
    private final Object[] zzcbv;
    private final zzxe zzcby;

    zzxs(zzxe com_google_android_gms_internal_measurement_zzxe, String str, Object[] objArr) {
        this.zzcby = com_google_android_gms_internal_measurement_zzxe;
        this.info = str;
        this.zzcbv = objArr;
        com_google_android_gms_internal_measurement_zzxe = str.charAt(null);
        if (com_google_android_gms_internal_measurement_zzxe < 55296) {
            this.flags = com_google_android_gms_internal_measurement_zzxe;
            return;
        }
        com_google_android_gms_internal_measurement_zzxe &= 8191;
        int i = 13;
        int i2 = 1;
        while (true) {
            int i3 = i2 + 1;
            char charAt = str.charAt(i2);
            if (charAt >= '?') {
                com_google_android_gms_internal_measurement_zzxe |= (charAt & 8191) << i;
                i += 13;
                i2 = i3;
            } else {
                this.flags = com_google_android_gms_internal_measurement_zzxe | (charAt << i);
                return;
            }
        }
    }

    final String zzyc() {
        return this.info;
    }

    final Object[] zzyd() {
        return this.zzcbv;
    }

    public final zzxe zzxv() {
        return this.zzcby;
    }

    public final int zzxt() {
        return (this.flags & 1) == 1 ? zze.zzbzw : zze.zzbzx;
    }

    public final boolean zzxu() {
        return (this.flags & 2) == 2;
    }
}
