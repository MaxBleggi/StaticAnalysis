package com.google.android.gms.internal.measurement;

import java.util.Map;
import java.util.Map.Entry;

final class zzxa implements zzwz {
    zzxa() {
    }

    public final Map<?, ?> zzac(Object obj) {
        return (zzwy) obj;
    }

    public final zzwx<?, ?> zzah(Object obj) {
        throw new NoSuchMethodError();
    }

    public final Map<?, ?> zzad(Object obj) {
        return (zzwy) obj;
    }

    public final boolean zzae(Object obj) {
        return ((zzwy) obj).isMutable() == null ? true : null;
    }

    public final Object zzaf(Object obj) {
        ((zzwy) obj).zzsw();
        return obj;
    }

    public final Object zzag(Object obj) {
        return zzwy.zzxn().zzxo();
    }

    public final Object zzc(Object obj, Object obj2) {
        obj = (zzwy) obj;
        zzwy com_google_android_gms_internal_measurement_zzwy = (zzwy) obj2;
        if (!com_google_android_gms_internal_measurement_zzwy.isEmpty()) {
            if (!obj.isMutable()) {
                obj = obj.zzxo();
            }
            obj.zza(com_google_android_gms_internal_measurement_zzwy);
        }
        return obj;
    }

    public final int zzb(int i, Object obj, Object obj2) {
        zzwy com_google_android_gms_internal_measurement_zzwy = (zzwy) obj;
        if (com_google_android_gms_internal_measurement_zzwy.isEmpty() != 0) {
            return 0;
        }
        i = com_google_android_gms_internal_measurement_zzwy.entrySet().iterator();
        if (i.hasNext() == null) {
            return 0;
        }
        Entry entry = (Entry) i.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
