package com.google.android.gms.measurement.internal;

final class zzbv implements Runnable {
    private final /* synthetic */ zzcx zzaqr;
    private final /* synthetic */ zzbu zzaqs;

    zzbv(zzbu com_google_android_gms_measurement_internal_zzbu, zzcx com_google_android_gms_measurement_internal_zzcx) {
        this.zzaqs = com_google_android_gms_measurement_internal_zzbu;
        this.zzaqr = com_google_android_gms_measurement_internal_zzcx;
    }

    public final void run() {
        this.zzaqs.zza(this.zzaqr);
        this.zzaqs.start();
    }
}
