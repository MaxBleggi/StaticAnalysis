package com.google.android.gms.measurement.internal;

final class zzci implements Runnable {
    private final /* synthetic */ zzbw zzaqw;
    private final /* synthetic */ String zzaqy;
    private final /* synthetic */ zzae zzaqz;

    zzci(zzbw com_google_android_gms_measurement_internal_zzbw, zzae com_google_android_gms_measurement_internal_zzae, String str) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzaqz = com_google_android_gms_measurement_internal_zzae;
        this.zzaqy = str;
    }

    public final void run() {
        this.zzaqw.zzang.zzme();
        this.zzaqw.zzang.zzd(this.zzaqz, this.zzaqy);
    }
}
