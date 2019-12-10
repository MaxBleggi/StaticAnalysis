package com.google.android.gms.measurement.internal;

final class zzbx implements Runnable {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzbw zzaqw;

    zzbx(zzbw com_google_android_gms_measurement_internal_zzbw, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final void run() {
        this.zzaqw.zzang.zzme();
        this.zzaqw.zzang.zze(this.zzaqv);
    }
}
