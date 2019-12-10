package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

final class zzee implements Runnable {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzdz zzasv;

    zzee(zzdz com_google_android_gms_measurement_internal_zzdz, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final void run() {
        zzah zzd = this.zzasv.zzasp;
        if (zzd == null) {
            this.zzasv.zzgt().zzjg().zzca("Discarding data. Failed to send app launch");
            return;
        }
        try {
            zzd.zza(this.zzaqv);
            this.zzasv.zza(zzd, null, this.zzaqv);
            this.zzasv.zzcy();
        } catch (RemoteException e) {
            this.zzasv.zzgt().zzjg().zzg("Failed to send app launch to the service", e);
        }
    }
}
