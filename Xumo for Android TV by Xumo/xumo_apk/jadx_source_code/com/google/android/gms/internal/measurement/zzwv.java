package com.google.android.gms.internal.measurement;

final class zzwv implements zzxd {
    private zzxd[] zzcbl;

    zzwv(zzxd... com_google_android_gms_internal_measurement_zzxdArr) {
        this.zzcbl = com_google_android_gms_internal_measurement_zzxdArr;
    }

    public final boolean zze(Class<?> cls) {
        for (zzxd zze : this.zzcbl) {
            if (zze.zze(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzxc zzf(Class<?> cls) {
        for (zzxd com_google_android_gms_internal_measurement_zzxd : this.zzcbl) {
            if (com_google_android_gms_internal_measurement_zzxd.zze(cls)) {
                return com_google_android_gms_internal_measurement_zzxd.zzf(cls);
            }
        }
        String str = "No factory is available for message type: ";
        cls = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(cls.length() != 0 ? str.concat(cls) : new String(str));
    }
}
