package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import android.text.TextUtils;

final class zzei implements Runnable {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ String zzaqy;
    private final /* synthetic */ zzae zzaqz;
    private final /* synthetic */ zzdz zzasv;
    private final /* synthetic */ boolean zzasx;
    private final /* synthetic */ boolean zzasy;

    zzei(zzdz com_google_android_gms_measurement_internal_zzdz, boolean z, boolean z2, zzae com_google_android_gms_measurement_internal_zzae, zzi com_google_android_gms_measurement_internal_zzi, String str) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
        this.zzasx = z;
        this.zzasy = z2;
        this.zzaqz = com_google_android_gms_measurement_internal_zzae;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
        this.zzaqy = str;
    }

    public final void run() {
        zzah zzd = this.zzasv.zzasp;
        if (zzd == null) {
            this.zzasv.zzgt().zzjg().zzca("Discarding data. Failed to send event to service");
            return;
        }
        if (this.zzasx) {
            this.zzasv.zza(zzd, this.zzasy ? null : this.zzaqz, this.zzaqv);
        } else {
            try {
                if (TextUtils.isEmpty(this.zzaqy)) {
                    zzd.zza(this.zzaqz, this.zzaqv);
                } else {
                    zzd.zza(this.zzaqz, this.zzaqy, this.zzasv.zzgt().zzjq());
                }
            } catch (RemoteException e) {
                this.zzasv.zzgt().zzjg().zzg("Failed to send event to the service", e);
            }
        }
        this.zzasv.zzcy();
    }
}
