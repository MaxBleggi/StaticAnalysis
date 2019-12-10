package com.google.android.gms.internal.measurement;

import java.util.List;

final class zzwr extends zzwo {
    private zzwr() {
        super();
    }

    final <L> List<L> zza(Object obj, long j) {
        List<L> zzd = zzd(obj, j);
        if (zzd.zzug()) {
            return zzd;
        }
        int size = zzd.size();
        Object zzak = zzd.zzak(size == 0 ? 10 : size << 1);
        zzys.zza(obj, j, zzak);
        return zzak;
    }

    final void zzb(Object obj, long j) {
        zzd(obj, j).zzsw();
    }

    final <E> void zza(Object obj, Object obj2, long j) {
        zzwd zzd = zzd(obj, j);
        obj2 = zzd(obj2, j);
        int size = zzd.size();
        int size2 = obj2.size();
        if (size > 0 && size2 > 0) {
            if (!zzd.zzug()) {
                zzd = zzd.zzak(size2 + size);
            }
            zzd.addAll(obj2);
        }
        if (size > 0) {
            obj2 = zzd;
        }
        zzys.zza(obj, j, obj2);
    }

    private static <E> zzwd<E> zzd(Object obj, long j) {
        return (zzwd) zzys.zzp(obj, j);
    }
}
