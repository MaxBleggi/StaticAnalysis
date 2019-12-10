package com.google.android.gms.internal.measurement;

import java.io.IOException;

abstract class zzym<T, B> {
    zzym() {
    }

    abstract void zza(B b, int i, long j);

    abstract void zza(B b, int i, zzun com_google_android_gms_internal_measurement_zzun);

    abstract void zza(B b, int i, T t);

    abstract void zza(T t, zzzh com_google_android_gms_internal_measurement_zzzh) throws IOException;

    abstract boolean zza(zzxt com_google_android_gms_internal_measurement_zzxt);

    abstract T zzaf(B b);

    abstract int zzai(T t);

    abstract T zzal(Object obj);

    abstract B zzam(Object obj);

    abstract int zzan(T t);

    abstract void zzb(B b, int i, long j);

    abstract void zzc(B b, int i, int i2);

    abstract void zzc(T t, zzzh com_google_android_gms_internal_measurement_zzzh) throws IOException;

    abstract void zzf(Object obj, T t);

    abstract void zzg(Object obj, B b);

    abstract T zzh(T t, T t2);

    abstract void zzy(Object obj);

    abstract B zzyr();

    final boolean zza(B b, zzxt com_google_android_gms_internal_measurement_zzxt) throws IOException {
        int tag = com_google_android_gms_internal_measurement_zzxt.getTag();
        int i = tag >>> 3;
        switch (tag & 7) {
            case 0:
                zza((Object) b, i, com_google_android_gms_internal_measurement_zzxt.zzus());
                return true;
            case 1:
                zzb(b, i, com_google_android_gms_internal_measurement_zzxt.zzuu());
                return true;
            case 2:
                zza((Object) b, i, com_google_android_gms_internal_measurement_zzxt.zzuy());
                return true;
            case 3:
                Object zzyr = zzyr();
                int i2 = (i << 3) | 4;
                while (com_google_android_gms_internal_measurement_zzxt.zzvo() != Integer.MAX_VALUE) {
                    if (!zza(zzyr, com_google_android_gms_internal_measurement_zzxt)) {
                        if (i2 != com_google_android_gms_internal_measurement_zzxt.getTag()) {
                            zza((Object) b, i, zzaf(zzyr));
                            return true;
                        }
                        throw zzwe.zzxa();
                    }
                }
                if (i2 != com_google_android_gms_internal_measurement_zzxt.getTag()) {
                    throw zzwe.zzxa();
                }
                zza((Object) b, i, zzaf(zzyr));
                return true;
            case 4:
                return null;
            case 5:
                zzc(b, i, com_google_android_gms_internal_measurement_zzxt.zzuv());
                return true;
            default:
                throw zzwe.zzxb();
        }
    }
}
