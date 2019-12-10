package com.google.android.gms.measurement.internal;

final class zzcl implements Runnable {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzbw zzaqw;
    private final /* synthetic */ zzfr zzara;

    zzcl(zzbw com_google_android_gms_measurement_internal_zzbw, zzfr com_google_android_gms_measurement_internal_zzfr, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzara = com_google_android_gms_measurement_internal_zzfr;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final void run() {
        this.zzaqw.zzang.zzme();
        this.zzaqw.zzang.zzb(this.zzara, this.zzaqv);
    }
}
