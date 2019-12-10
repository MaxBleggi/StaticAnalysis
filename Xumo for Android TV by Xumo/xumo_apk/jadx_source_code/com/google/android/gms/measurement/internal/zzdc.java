package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzdc implements Runnable {
    private final /* synthetic */ boolean zzaev;
    private final /* synthetic */ AtomicReference zzarq;
    private final /* synthetic */ zzcy zzarr;

    zzdc(zzcy com_google_android_gms_measurement_internal_zzcy, AtomicReference atomicReference, boolean z) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzarq = atomicReference;
        this.zzaev = z;
    }

    public final void run() {
        this.zzarr.zzgl().zza(this.zzarq, this.zzaev);
    }
}
