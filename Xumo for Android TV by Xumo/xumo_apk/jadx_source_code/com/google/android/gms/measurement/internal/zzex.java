package com.google.android.gms.measurement.internal;

final class zzex implements Runnable {
    private final /* synthetic */ Runnable zzacf;
    private final /* synthetic */ zzfk zzatk;

    zzex(zzeu com_google_android_gms_measurement_internal_zzeu, zzfk com_google_android_gms_measurement_internal_zzfk, Runnable runnable) {
        this.zzatk = com_google_android_gms_measurement_internal_zzfk;
        this.zzacf = runnable;
    }

    public final void run() {
        this.zzatk.zzme();
        this.zzatk.zzg(this.zzacf);
        this.zzatk.zzlz();
    }
}
