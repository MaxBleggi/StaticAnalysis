package com.google.android.gms.measurement.internal;

final class zzer implements Runnable {
    private final /* synthetic */ zzeo zzate;
    private final /* synthetic */ zzah zzatf;

    zzer(zzeo com_google_android_gms_measurement_internal_zzeo, zzah com_google_android_gms_measurement_internal_zzah) {
        this.zzate = com_google_android_gms_measurement_internal_zzeo;
        this.zzatf = com_google_android_gms_measurement_internal_zzah;
    }

    public final void run() {
        synchronized (this.zzate) {
            this.zzate.zzatb = false;
            if (!this.zzate.zzasv.isConnected()) {
                this.zzate.zzasv.zzgt().zzjn().zzca("Connected to remote service");
                this.zzate.zzasv.zza(this.zzatf);
            }
        }
    }
}
