package com.google.android.gms.measurement.internal;

final class zzcb implements Runnable {
    private final /* synthetic */ zzbw zzaqw;
    private final /* synthetic */ zzm zzaqx;

    zzcb(zzbw com_google_android_gms_measurement_internal_zzbw, zzm com_google_android_gms_measurement_internal_zzm) {
        this.zzaqw = com_google_android_gms_measurement_internal_zzbw;
        this.zzaqx = com_google_android_gms_measurement_internal_zzm;
    }

    public final void run() {
        this.zzaqw.zzang.zzme();
        this.zzaqw.zzang.zze(this.zzaqx);
    }
}
