package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzdm implements Runnable {
    private final /* synthetic */ AtomicReference zzarq;
    private final /* synthetic */ zzcy zzarr;

    zzdm(zzcy com_google_android_gms_measurement_internal_zzcy, AtomicReference atomicReference) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzarq = atomicReference;
    }

    public final void run() {
        synchronized (this.zzarq) {
            try {
                this.zzarq.set(Integer.valueOf(this.zzarr.zzgv().zzb(this.zzarr.zzgk().zzal(), zzag.zzakq)));
                this.zzarq.notify();
            } catch (Throwable th) {
                this.zzarq.notify();
            }
        }
    }
}
