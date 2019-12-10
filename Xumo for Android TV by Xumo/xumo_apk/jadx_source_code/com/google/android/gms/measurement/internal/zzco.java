package com.google.android.gms.measurement.internal;

final class zzco implements Runnable {
    private final /* synthetic */ String zzaeq;
    private final /* synthetic */ zzbw zzaqw;
    private final /* synthetic */ String zzaqy;
    private final /* synthetic */ String zzarb;
    private final /* synthetic */ long zzarc;

    zzco(zzbw com_google_android_gms_measurement_internal_zzbw, String str, String str2, String str3, long j) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzarb = str;
        this.zzaqy = str2;
        this.zzaeq = str3;
        this.zzarc = j;
    }

    public final void run() {
        if (this.zzarb == null) {
            this.zzaqw.zzang.zzmh().zzgm().zza(this.zzaqy, null);
            return;
        }
        this.zzaqw.zzang.zzmh().zzgm().zza(this.zzaqy, new zzdv(this.zzaeq, this.zzarb, this.zzarc));
    }
}
