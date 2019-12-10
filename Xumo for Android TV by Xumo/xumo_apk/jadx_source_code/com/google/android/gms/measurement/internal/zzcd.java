package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzcd implements Callable<List<zzft>> {
    private final /* synthetic */ String zzaeh;
    private final /* synthetic */ String zzaeo;
    private final /* synthetic */ zzbw zzaqw;
    private final /* synthetic */ String zzaqy;

    zzcd(zzbw com_google_android_gms_measurement_internal_zzbw, String str, String str2, String str3) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzaqy = str;
        this.zzaeh = str2;
        this.zzaeo = str3;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzaqw.zzang.zzme();
        return this.zzaqw.zzang.zzjt().zzb(this.zzaqy, this.zzaeh, this.zzaeo);
    }
}
