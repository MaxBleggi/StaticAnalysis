package com.google.android.gms.measurement.internal;

final class zzdr implements Runnable {
    private final /* synthetic */ zzcy zzarr;
    private final /* synthetic */ long zzarz;

    zzdr(zzcy com_google_android_gms_measurement_internal_zzcy, long j) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzarz = j;
    }

    public final void run() {
        this.zzarr.zzgu().zzanx.set(this.zzarz);
        this.zzarr.zzgt().zzjn().zzg("Session timeout duration set", Long.valueOf(this.zzarz));
    }
}
