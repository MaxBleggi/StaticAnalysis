package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzdd implements Runnable {
    private final /* synthetic */ AtomicReference zzarq;
    private final /* synthetic */ zzcy zzarr;

    zzdd(zzcy com_google_android_gms_measurement_internal_zzcy, AtomicReference atomicReference) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzarq = atomicReference;
    }

    public final void run() {
        this.zzarr.zzgl().zza(this.zzarq);
    }
}
