package com.google.android.gms.measurement.internal;

final class zzfd implements Runnable {
    private final /* synthetic */ long zzafv;
    private final /* synthetic */ zzez zzatq;

    zzfd(zzez com_google_android_gms_measurement_internal_zzez, long j) {
        this.zzatq = com_google_android_gms_measurement_internal_zzez;
        this.zzafv = j;
    }

    public final void run() {
        this.zzatq.zzam(this.zzafv);
    }
}
