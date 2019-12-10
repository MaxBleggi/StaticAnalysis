package com.google.android.gms.measurement.internal;

import java.util.concurrent.Callable;

final class zzcj implements Callable<byte[]> {
    private final /* synthetic */ zzbw zzaqw;
    private final /* synthetic */ String zzaqy;
    private final /* synthetic */ zzae zzaqz;

    zzcj(zzbw com_google_android_gms_measurement_internal_zzbw, zzae com_google_android_gms_measurement_internal_zzae, String str) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzaqz = com_google_android_gms_measurement_internal_zzae;
        this.zzaqy = str;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzaqw.zzang.zzme();
        return this.zzaqw.zzang.zzlw().zzb(this.zzaqz, this.zzaqy);
    }
}
