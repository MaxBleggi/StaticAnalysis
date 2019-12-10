package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzya extends zzyg {
    private final /* synthetic */ zzxx zzcdc;

    private zzya(zzxx com_google_android_gms_internal_measurement_zzxx) {
        this.zzcdc = com_google_android_gms_internal_measurement_zzxx;
        super(com_google_android_gms_internal_measurement_zzxx);
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzxz(this.zzcdc);
    }
}
