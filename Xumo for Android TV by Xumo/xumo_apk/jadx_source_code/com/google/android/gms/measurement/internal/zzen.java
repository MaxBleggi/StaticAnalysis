package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

final class zzen implements Runnable {
    private final /* synthetic */ boolean zzaev;
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzdz zzasv;
    private final /* synthetic */ AtomicReference zzasw;

    zzen(zzdz com_google_android_gms_measurement_internal_zzdz, AtomicReference atomicReference, zzi com_google_android_gms_measurement_internal_zzi, boolean z) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
        this.zzasw = atomicReference;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
        this.zzaev = z;
    }

    public final void run() {
        synchronized (this.zzasw) {
            try {
                zzah zzd = this.zzasv.zzasp;
                if (zzd == null) {
                    this.zzasv.zzgt().zzjg().zzca("Failed to get user properties");
                    this.zzasw.notify();
                    return;
                }
                this.zzasw.set(zzd.zza(this.zzaqv, this.zzaev));
                this.zzasv.zzcy();
                this.zzasw.notify();
            } catch (RemoteException e) {
                try {
                    this.zzasv.zzgt().zzjg().zzg("Failed to get user properties", e);
                    this.zzasw.notify();
                } catch (Throwable th) {
                    this.zzasw.notify();
                }
            }
        }
    }
}
