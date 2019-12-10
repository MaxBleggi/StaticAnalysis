package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class zzwq extends zzwo {
    private static final Class<?> zzcbg = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzwq() {
        super();
    }

    final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    final void zzb(Object obj, long j) {
        Object zzxj;
        List list = (List) zzys.zzp(obj, j);
        if (list instanceof zzwn) {
            zzxj = ((zzwn) list).zzxj();
        } else if (!zzcbg.isAssignableFrom(list.getClass())) {
            if ((list instanceof zzxp) && (list instanceof zzwd)) {
                zzwd com_google_android_gms_internal_measurement_zzwd = (zzwd) list;
                if (com_google_android_gms_internal_measurement_zzwd.zzug() != null) {
                    com_google_android_gms_internal_measurement_zzwd.zzsw();
                }
                return;
            }
            zzxj = Collections.unmodifiableList(list);
        } else {
            return;
        }
        zzys.zza(obj, j, zzxj);
    }

    private static <L> List<L> zza(Object obj, long j, int i) {
        List<L> zzc = zzc(obj, j);
        Object com_google_android_gms_internal_measurement_zzwm;
        if (zzc.isEmpty()) {
            if (zzc instanceof zzwn) {
                com_google_android_gms_internal_measurement_zzwm = new zzwm(i);
            } else if ((zzc instanceof zzxp) && (zzc instanceof zzwd)) {
                com_google_android_gms_internal_measurement_zzwm = ((zzwd) zzc).zzak(i);
            } else {
                com_google_android_gms_internal_measurement_zzwm = new ArrayList(i);
            }
            zzys.zza(obj, j, com_google_android_gms_internal_measurement_zzwm);
            return com_google_android_gms_internal_measurement_zzwm;
        }
        ArrayList arrayList;
        if (zzcbg.isAssignableFrom(zzc.getClass())) {
            arrayList = new ArrayList(zzc.size() + i);
            arrayList.addAll(zzc);
            zzys.zza(obj, j, (Object) arrayList);
        } else if (zzc instanceof zzyp) {
            Object com_google_android_gms_internal_measurement_zzwm2 = new zzwm(zzc.size() + i);
            com_google_android_gms_internal_measurement_zzwm2.addAll((zzyp) zzc);
            zzys.zza(obj, j, com_google_android_gms_internal_measurement_zzwm2);
        } else if (!(zzc instanceof zzxp) || !(zzc instanceof zzwd)) {
            return zzc;
        } else {
            zzwd com_google_android_gms_internal_measurement_zzwd = (zzwd) zzc;
            if (com_google_android_gms_internal_measurement_zzwd.zzug()) {
                return zzc;
            }
            com_google_android_gms_internal_measurement_zzwm = com_google_android_gms_internal_measurement_zzwd.zzak(zzc.size() + i);
            zzys.zza(obj, j, com_google_android_gms_internal_measurement_zzwm);
            return com_google_android_gms_internal_measurement_zzwm;
        }
        return arrayList;
    }

    final <E> void zza(Object obj, Object obj2, long j) {
        obj2 = zzc(obj2, j);
        List zza = zza(obj, j, obj2.size());
        int size = zza.size();
        int size2 = obj2.size();
        if (size > 0 && size2 > 0) {
            zza.addAll(obj2);
        }
        if (size > 0) {
            obj2 = zza;
        }
        zzys.zza(obj, j, obj2);
    }

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zzys.zzp(obj, j);
    }
}
