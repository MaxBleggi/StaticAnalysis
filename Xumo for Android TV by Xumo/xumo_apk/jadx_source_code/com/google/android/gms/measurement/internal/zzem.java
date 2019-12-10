package com.google.android.gms.measurement.internal;

final class zzem implements Runnable {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzfr zzara;
    private final /* synthetic */ zzdz zzasv;
    private final /* synthetic */ boolean zzasy;

    zzem(zzdz com_google_android_gms_measurement_internal_zzdz, boolean z, zzfr com_google_android_gms_measurement_internal_zzfr, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
        this.zzasy = z;
        this.zzara = com_google_android_gms_measurement_internal_zzfr;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final void run() {
        zzah zzd = this.zzasv.zzasp;
        if (zzd == null) {
            this.zzasv.zzgt().zzjg().zzca("Discarding data. Failed to set user attribute");
            return;
        }
        this.zzasv.zza(zzd, this.zzasy ? null : this.zzara, this.zzaqv);
        this.zzasv.zzcy();
    }
}
