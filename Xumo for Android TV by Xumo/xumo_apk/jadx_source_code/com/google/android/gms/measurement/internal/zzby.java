package com.google.android.gms.measurement.internal;

final class zzby implements Runnable {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzbw zzaqw;
    private final /* synthetic */ zzm zzaqx;

    zzby(zzbw com_google_android_gms_measurement_internal_zzbw, zzm com_google_android_gms_measurement_internal_zzm, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzaqx = com_google_android_gms_measurement_internal_zzm;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final void run() {
        this.zzaqw.zzang.zzme();
        this.zzaqw.zzang.zzc(this.zzaqx, this.zzaqv);
    }
}
