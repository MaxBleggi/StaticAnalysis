package com.google.android.gms.measurement.internal;

final class zzdq implements Runnable {
    private final /* synthetic */ zzcy zzarr;
    private final /* synthetic */ long zzarz;

    zzdq(zzcy com_google_android_gms_measurement_internal_zzcy, long j) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzarz = j;
    }

    public final void run() {
        this.zzarr.zzgu().zzanw.set(this.zzarz);
        this.zzarr.zzgt().zzjn().zzg("Minimum session duration set", Long.valueOf(this.zzarz));
    }
}
