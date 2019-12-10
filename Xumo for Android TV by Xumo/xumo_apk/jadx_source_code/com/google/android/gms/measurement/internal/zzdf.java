package com.google.android.gms.measurement.internal;

final class zzdf implements Runnable {
    private final /* synthetic */ zzcv zzaeu;
    private final /* synthetic */ zzcy zzarr;

    zzdf(zzcy com_google_android_gms_measurement_internal_zzcy, zzcv com_google_android_gms_measurement_internal_zzcv) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzaeu = com_google_android_gms_measurement_internal_zzcv;
    }

    public final void run() {
        this.zzarr.zza(this.zzaeu);
    }
}
