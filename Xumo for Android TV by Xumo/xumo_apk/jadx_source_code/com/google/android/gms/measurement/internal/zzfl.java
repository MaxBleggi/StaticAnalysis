package com.google.android.gms.measurement.internal;

final class zzfl implements Runnable {
    private final /* synthetic */ zzfp zzauq;
    private final /* synthetic */ zzfk zzaur;

    zzfl(zzfk com_google_android_gms_measurement_internal_zzfk, zzfp com_google_android_gms_measurement_internal_zzfp) {
        this.zzaur = com_google_android_gms_measurement_internal_zzfk;
        this.zzauq = com_google_android_gms_measurement_internal_zzfp;
    }

    public final void run() {
        this.zzaur.zza(this.zzauq);
        this.zzaur.start();
    }
}
