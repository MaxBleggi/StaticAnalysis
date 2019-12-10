package com.google.android.gms.measurement.internal;

final class zzfh extends zzw {
    private final /* synthetic */ zzfk zzatk;
    private final /* synthetic */ zzfg zzats;

    zzfh(zzfg com_google_android_gms_measurement_internal_zzfg, zzcr com_google_android_gms_measurement_internal_zzcr, zzfk com_google_android_gms_measurement_internal_zzfk) {
        this.zzats = com_google_android_gms_measurement_internal_zzfg;
        this.zzatk = com_google_android_gms_measurement_internal_zzfk;
        super(com_google_android_gms_measurement_internal_zzcr);
    }

    public final void run() {
        this.zzats.cancel();
        this.zzats.zzgt().zzjo().zzca("Starting upload from DelayedRunnable");
        this.zzatk.zzlz();
    }
}
