package com.google.android.gms.measurement.internal;

final class zzep implements Runnable {
    private final /* synthetic */ zzah zzatd;
    private final /* synthetic */ zzeo zzate;

    zzep(zzeo com_google_android_gms_measurement_internal_zzeo, zzah com_google_android_gms_measurement_internal_zzah) {
        this.zzate = com_google_android_gms_measurement_internal_zzeo;
        this.zzatd = com_google_android_gms_measurement_internal_zzah;
    }

    public final void run() {
        synchronized (this.zzate) {
            this.zzate.zzatb = false;
            if (!this.zzate.zzasv.isConnected()) {
                this.zzate.zzasv.zzgt().zzjo().zzca("Connected to service");
                this.zzate.zzasv.zza(this.zzatd);
            }
        }
    }
}
