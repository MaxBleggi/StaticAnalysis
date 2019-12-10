package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzcm implements Callable<List<zzft>> {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzbw zzaqw;

    zzcm(zzbw com_google_android_gms_measurement_internal_zzbw, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzaqw.zzang.zzme();
        return this.zzaqw.zzang.zzjt().zzbn(this.zzaqv.packageName);
    }
}
