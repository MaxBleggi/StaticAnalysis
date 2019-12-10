package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

final class zzec implements Runnable {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzdz zzasv;
    private final /* synthetic */ AtomicReference zzasw;

    zzec(zzdz com_google_android_gms_measurement_internal_zzdz, AtomicReference atomicReference, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
        this.zzasw = atomicReference;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final void run() {
        synchronized (this.zzasw) {
            try {
                zzah zzd = this.zzasv.zzasp;
                if (zzd == null) {
                    this.zzasv.zzgt().zzjg().zzca("Failed to get app instance id");
                    this.zzasw.notify();
                    return;
                }
                this.zzasw.set(zzd.zzc(this.zzaqv));
                String str = (String) this.zzasw.get();
                if (str != null) {
                    this.zzasv.zzgj().zzcr(str);
                    this.zzasv.zzgu().zzans.zzcf(str);
                }
                this.zzasv.zzcy();
                this.zzasw.notify();
            } catch (RemoteException e) {
                try {
                    this.zzasv.zzgt().zzjg().zzg("Failed to get app instance id", e);
                    this.zzasw.notify();
                } catch (Throwable th) {
                    this.zzasw.notify();
                }
            }
        }
    }
}
