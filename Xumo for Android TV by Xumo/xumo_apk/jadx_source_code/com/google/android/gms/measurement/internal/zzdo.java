package com.google.android.gms.measurement.internal;

final class zzdo implements Runnable {
    private final /* synthetic */ boolean zzaes;
    private final /* synthetic */ zzcy zzarr;

    zzdo(zzcy com_google_android_gms_measurement_internal_zzcy, boolean z) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzaes = z;
    }

    public final void run() {
        this.zzarr.zzj(this.zzaes);
    }
}
