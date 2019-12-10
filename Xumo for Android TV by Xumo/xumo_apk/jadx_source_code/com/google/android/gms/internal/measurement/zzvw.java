package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzvx.zze;

final class zzvw implements zzxd {
    private static final zzvw zzbzg = new zzvw();

    private zzvw() {
    }

    public static zzvw zzwl() {
        return zzbzg;
    }

    public final boolean zze(Class<?> cls) {
        return zzvx.class.isAssignableFrom(cls);
    }

    public final zzxc zzf(Class<?> cls) {
        if (zzvx.class.isAssignableFrom(cls)) {
            try {
                return (zzxc) zzvx.zzg(cls.asSubclass(zzvx.class)).zza(zze.zzbzq, null, null);
            } catch (Throwable e) {
                String str = "Unable to get message info for ";
                cls = String.valueOf(cls.getName());
                throw new RuntimeException(cls.length() != 0 ? str.concat(cls) : new String(str), e);
            }
        }
        String str2 = "Unsupported message type: ";
        cls = String.valueOf(cls.getName());
        throw new IllegalArgumentException(cls.length() != 0 ? str2.concat(cls) : new String(str2));
    }
}
