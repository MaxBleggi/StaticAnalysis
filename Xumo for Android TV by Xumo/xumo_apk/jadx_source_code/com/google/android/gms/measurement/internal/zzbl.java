package com.google.android.gms.measurement.internal;

final class zzbl implements Runnable {
    private final /* synthetic */ zzbu zzaop;
    private final /* synthetic */ zzaq zzaoq;

    zzbl(zzbk com_google_android_gms_measurement_internal_zzbk, zzbu com_google_android_gms_measurement_internal_zzbu, zzaq com_google_android_gms_measurement_internal_zzaq) {
        this.zzaop = com_google_android_gms_measurement_internal_zzbu;
        this.zzaoq = com_google_android_gms_measurement_internal_zzaq;
    }

    public final void run() {
        if (this.zzaop.zzkk() == null) {
            this.zzaoq.zzjg().zzca("Install Referrer Reporter is null");
            return;
        }
        zzbh zzkk = this.zzaop.zzkk();
        zzkk.zzadp.zzgg();
        zzkk.zzcg(zzkk.zzadp.getContext().getPackageName());
    }
}
