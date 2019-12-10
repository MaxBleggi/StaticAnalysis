package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzdj implements Runnable {
    private final /* synthetic */ AtomicReference zzarq;
    private final /* synthetic */ zzcy zzarr;

    zzdj(zzcy com_google_android_gms_measurement_internal_zzcy, AtomicReference atomicReference) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzarq = atomicReference;
    }

    public final void run() {
        synchronized (this.zzarq) {
            try {
                this.zzarq.set(this.zzarr.zzgv().zzba(this.zzarr.zzgk().zzal()));
                this.zzarq.notify();
            } catch (Throwable th) {
                this.zzarq.notify();
            }
        }
    }
}
