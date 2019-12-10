package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import android.text.TextUtils;

final class zzej implements Runnable {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzdz zzasv;
    private final /* synthetic */ boolean zzasx;
    private final /* synthetic */ boolean zzasy;
    private final /* synthetic */ zzm zzasz;
    private final /* synthetic */ zzm zzata;

    zzej(zzdz com_google_android_gms_measurement_internal_zzdz, boolean z, boolean z2, zzm com_google_android_gms_measurement_internal_zzm, zzi com_google_android_gms_measurement_internal_zzi, zzm com_google_android_gms_measurement_internal_zzm2) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
        this.zzasx = z;
        this.zzasy = z2;
        this.zzasz = com_google_android_gms_measurement_internal_zzm;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
        this.zzata = com_google_android_gms_measurement_internal_zzm2;
    }

    public final void run() {
        zzah zzd = this.zzasv.zzasp;
        if (zzd == null) {
            this.zzasv.zzgt().zzjg().zzca("Discarding data. Failed to send conditional user property to service");
            return;
        }
        if (this.zzasx) {
            this.zzasv.zza(zzd, this.zzasy ? null : this.zzasz, this.zzaqv);
        } else {
            try {
                if (TextUtils.isEmpty(this.zzata.packageName)) {
                    zzd.zza(this.zzasz, this.zzaqv);
                } else {
                    zzd.zzb(this.zzasz);
                }
            } catch (RemoteException e) {
                this.zzasv.zzgt().zzjg().zzg("Failed to send conditional user property to the service", e);
            }
        }
        this.zzasv.zzcy();
    }
}
