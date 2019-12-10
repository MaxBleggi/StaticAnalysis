package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;

final class zzfc extends zzw {
    private final /* synthetic */ zzez zzatq;

    zzfc(zzez com_google_android_gms_measurement_internal_zzez, zzcr com_google_android_gms_measurement_internal_zzcr) {
        this.zzatq = com_google_android_gms_measurement_internal_zzez;
        super(com_google_android_gms_measurement_internal_zzcr);
    }

    @WorkerThread
    public final void run() {
        zzcp com_google_android_gms_measurement_internal_zzcp = this.zzatq;
        com_google_android_gms_measurement_internal_zzcp.zzaf();
        com_google_android_gms_measurement_internal_zzcp.zzgt().zzjo().zzca("Current session is expired, remove the session number and Id");
        if (com_google_android_gms_measurement_internal_zzcp.zzgv().zzbi(com_google_android_gms_measurement_internal_zzcp.zzgk().zzal())) {
            com_google_android_gms_measurement_internal_zzcp.zzgj().zza("auto", "_sid", null, com_google_android_gms_measurement_internal_zzcp.zzbx().currentTimeMillis());
        }
        if (com_google_android_gms_measurement_internal_zzcp.zzgv().zzbj(com_google_android_gms_measurement_internal_zzcp.zzgk().zzal())) {
            com_google_android_gms_measurement_internal_zzcp.zzgj().zza("auto", "_sno", null, com_google_android_gms_measurement_internal_zzcp.zzbx().currentTimeMillis());
        }
    }
}
