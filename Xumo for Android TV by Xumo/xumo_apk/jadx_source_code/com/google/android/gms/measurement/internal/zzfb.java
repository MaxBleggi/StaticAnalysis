package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;

final class zzfb extends zzw {
    private final /* synthetic */ zzez zzatq;

    zzfb(zzez com_google_android_gms_measurement_internal_zzez, zzcr com_google_android_gms_measurement_internal_zzcr) {
        this.zzatq = com_google_android_gms_measurement_internal_zzez;
        super(com_google_android_gms_measurement_internal_zzcr);
    }

    @WorkerThread
    public final void run() {
        this.zzatq.zzlq();
    }
}
