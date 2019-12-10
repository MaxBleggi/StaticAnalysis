package com.google.android.gms.measurement.internal;

import android.content.Intent;

final /* synthetic */ class zzev implements Runnable {
    private final int zzacb;
    private final zzeu zzatg;
    private final zzaq zzath;
    private final Intent zzati;

    zzev(zzeu com_google_android_gms_measurement_internal_zzeu, int i, zzaq com_google_android_gms_measurement_internal_zzaq, Intent intent) {
        this.zzatg = com_google_android_gms_measurement_internal_zzeu;
        this.zzacb = i;
        this.zzath = com_google_android_gms_measurement_internal_zzaq;
        this.zzati = intent;
    }

    public final void run() {
        this.zzatg.zza(this.zzacb, this.zzath, this.zzati);
    }
}
