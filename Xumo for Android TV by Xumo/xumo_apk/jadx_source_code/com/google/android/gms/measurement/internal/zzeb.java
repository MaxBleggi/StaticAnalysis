package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

final class zzeb implements Runnable {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzdz zzasv;

    zzeb(zzdz com_google_android_gms_measurement_internal_zzdz, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final void run() {
        zzah zzd = this.zzasv.zzasp;
        if (zzd == null) {
            this.zzasv.zzgt().zzjg().zzca("Failed to reset data on the service; null service");
            return;
        }
        try {
            zzd.zzd(this.zzaqv);
        } catch (RemoteException e) {
            this.zzasv.zzgt().zzjg().zzg("Failed to reset data on the service", e);
        }
        this.zzasv.zzcy();
    }
}
