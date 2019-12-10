package com.google.android.gms.measurement.internal;

final class zzba implements Runnable {
    private final /* synthetic */ boolean zzanh;
    private final /* synthetic */ zzaz zzani;

    zzba(zzaz com_google_android_gms_measurement_internal_zzaz, boolean z) {
        this.zzani = com_google_android_gms_measurement_internal_zzaz;
        this.zzanh = z;
    }

    public final void run() {
        this.zzani.zzang.zzm(this.zzanh);
    }
}
