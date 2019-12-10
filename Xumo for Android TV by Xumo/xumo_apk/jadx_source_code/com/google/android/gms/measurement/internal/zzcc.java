package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzcc implements Callable<List<zzft>> {
    private final /* synthetic */ String zzaeh;
    private final /* synthetic */ String zzaeo;
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzbw zzaqw;

    zzcc(zzbw com_google_android_gms_measurement_internal_zzbw, zzi com_google_android_gms_measurement_internal_zzi, String str, String str2) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
        this.zzaeh = str;
        this.zzaeo = str2;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzaqw.zzang.zzme();
        return this.zzaqw.zzang.zzjt().zzb(this.zzaqv.packageName, this.zzaeh, this.zzaeo);
    }
}
