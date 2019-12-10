package com.google.android.gms.measurement.internal;

final class zzch implements Runnable {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzbw zzaqw;
    private final /* synthetic */ zzae zzaqz;

    zzch(zzbw com_google_android_gms_measurement_internal_zzbw, zzae com_google_android_gms_measurement_internal_zzae, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzaqz = com_google_android_gms_measurement_internal_zzae;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final void run() {
        zzae zzb = this.zzaqw.zzb(this.zzaqz, this.zzaqv);
        this.zzaqw.zzang.zzme();
        this.zzaqw.zzang.zzc(zzb, this.zzaqv);
    }
}
