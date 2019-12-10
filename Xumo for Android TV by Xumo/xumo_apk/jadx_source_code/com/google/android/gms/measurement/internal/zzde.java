package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzde implements Runnable {
    private final /* synthetic */ zzcy zzarr;
    private final /* synthetic */ long zzarx;

    zzde(zzcy com_google_android_gms_measurement_internal_zzcy, long j) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzarx = j;
    }

    public final void run() {
        zzcp com_google_android_gms_measurement_internal_zzcp = this.zzarr;
        long j = this.zzarx;
        com_google_android_gms_measurement_internal_zzcp.zzaf();
        com_google_android_gms_measurement_internal_zzcp.zzgg();
        com_google_android_gms_measurement_internal_zzcp.zzcl();
        com_google_android_gms_measurement_internal_zzcp.zzgt().zzjn().zzca("Resetting analytics data (FE)");
        com_google_android_gms_measurement_internal_zzcp.zzgo().zzln();
        if (com_google_android_gms_measurement_internal_zzcp.zzgv().zzbe(com_google_android_gms_measurement_internal_zzcp.zzgk().zzal())) {
            com_google_android_gms_measurement_internal_zzcp.zzgu().zzanq.set(j);
        }
        boolean isEnabled = com_google_android_gms_measurement_internal_zzcp.zzadp.isEnabled();
        if (!com_google_android_gms_measurement_internal_zzcp.zzgv().zzhz()) {
            com_google_android_gms_measurement_internal_zzcp.zzgu().zzi(isEnabled ^ 1);
        }
        com_google_android_gms_measurement_internal_zzcp.zzgl().resetAnalyticsData();
        com_google_android_gms_measurement_internal_zzcp.zzarp = isEnabled ^ 1;
        if (this.zzarr.zzgv().zza(zzag.zzaln)) {
            this.zzarr.zzgl().zza(new AtomicReference());
        }
    }
}
