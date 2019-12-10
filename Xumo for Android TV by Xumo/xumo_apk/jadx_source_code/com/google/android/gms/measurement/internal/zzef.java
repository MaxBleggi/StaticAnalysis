package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

final class zzef implements Runnable {
    private final /* synthetic */ zzdv zzasn;
    private final /* synthetic */ zzdz zzasv;

    zzef(zzdz com_google_android_gms_measurement_internal_zzdz, zzdv com_google_android_gms_measurement_internal_zzdv) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
        this.zzasn = com_google_android_gms_measurement_internal_zzdv;
    }

    public final void run() {
        zzah zzd = this.zzasv.zzasp;
        if (zzd == null) {
            this.zzasv.zzgt().zzjg().zzca("Failed to send current screen to service");
            return;
        }
        try {
            if (this.zzasn == null) {
                zzd.zza(0, null, null, this.zzasv.getContext().getPackageName());
            } else {
                zzd.zza(this.zzasn.zzasb, this.zzasn.zzuw, this.zzasn.zzasa, this.zzasv.getContext().getPackageName());
            }
            this.zzasv.zzcy();
        } catch (RemoteException e) {
            this.zzasv.zzgt().zzjg().zzg("Failed to send current screen to the service", e);
        }
    }
}
