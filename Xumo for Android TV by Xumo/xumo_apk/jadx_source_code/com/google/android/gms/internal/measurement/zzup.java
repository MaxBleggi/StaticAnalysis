package com.google.android.gms.internal.measurement;

import java.util.Comparator;

final class zzup implements Comparator<zzun> {
    zzup() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzun com_google_android_gms_internal_measurement_zzun = (zzun) obj;
        zzun com_google_android_gms_internal_measurement_zzun2 = (zzun) obj2;
        zzuu com_google_android_gms_internal_measurement_zzuu = (zzuu) com_google_android_gms_internal_measurement_zzun.iterator();
        zzuu com_google_android_gms_internal_measurement_zzuu2 = (zzuu) com_google_android_gms_internal_measurement_zzun2.iterator();
        while (com_google_android_gms_internal_measurement_zzuu.hasNext() && com_google_android_gms_internal_measurement_zzuu2.hasNext()) {
            int compare = Integer.compare(zzun.zza(com_google_android_gms_internal_measurement_zzuu.nextByte()), zzun.zza(com_google_android_gms_internal_measurement_zzuu2.nextByte()));
            if (compare != 0) {
                return compare;
            }
        }
        return Integer.compare(com_google_android_gms_internal_measurement_zzun.size(), com_google_android_gms_internal_measurement_zzun2.size());
    }
}
