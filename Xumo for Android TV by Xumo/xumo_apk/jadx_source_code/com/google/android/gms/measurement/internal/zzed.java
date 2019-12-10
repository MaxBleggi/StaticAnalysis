package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzef;

final class zzed implements Runnable {
    private final /* synthetic */ zzef zzagv;
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzdz zzasv;

    zzed(zzdz com_google_android_gms_measurement_internal_zzdz, zzi com_google_android_gms_measurement_internal_zzi, zzef com_google_android_gms_internal_measurement_zzef) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
        this.zzagv = com_google_android_gms_internal_measurement_zzef;
    }

    public final void run() {
        Object e;
        Throwable th;
        String zzc;
        try {
            zzah zzd = this.zzasv.zzasp;
            if (zzd == null) {
                this.zzasv.zzgt().zzjg().zzca("Failed to get app instance id");
                this.zzasv.zzgr().zzb(this.zzagv, null);
                return;
            }
            zzc = zzd.zzc(this.zzaqv);
            if (zzc != null) {
                try {
                    this.zzasv.zzgj().zzcr(zzc);
                    this.zzasv.zzgu().zzans.zzcf(zzc);
                } catch (RemoteException e2) {
                    e = e2;
                    try {
                        this.zzasv.zzgt().zzjg().zzg("Failed to get app instance id", e);
                        this.zzasv.zzgr().zzb(this.zzagv, zzc);
                    } catch (Throwable th2) {
                        th = th2;
                        this.zzasv.zzgr().zzb(this.zzagv, zzc);
                        throw th;
                    }
                }
            }
            this.zzasv.zzcy();
            this.zzasv.zzgr().zzb(this.zzagv, zzc);
        } catch (RemoteException e3) {
            RemoteException remoteException = e3;
            zzc = null;
            e = remoteException;
            this.zzasv.zzgt().zzjg().zzg("Failed to get app instance id", e);
            this.zzasv.zzgr().zzb(this.zzagv, zzc);
        } catch (Throwable th3) {
            Throwable th4 = th3;
            zzc = null;
            th = th4;
            this.zzasv.zzgr().zzb(this.zzagv, zzc);
            throw th;
        }
    }
}
