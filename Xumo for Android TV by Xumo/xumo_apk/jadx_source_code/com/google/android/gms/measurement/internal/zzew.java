package com.google.android.gms.measurement.internal;

import android.app.job.JobParameters;

final /* synthetic */ class zzew implements Runnable {
    private final JobParameters zzace;
    private final zzeu zzatg;
    private final zzaq zzatj;

    zzew(zzeu com_google_android_gms_measurement_internal_zzeu, zzaq com_google_android_gms_measurement_internal_zzaq, JobParameters jobParameters) {
        this.zzatg = com_google_android_gms_measurement_internal_zzeu;
        this.zzatj = com_google_android_gms_measurement_internal_zzaq;
        this.zzace = jobParameters;
    }

    public final void run() {
        this.zzatg.zza(this.zzatj, this.zzace);
    }
}
