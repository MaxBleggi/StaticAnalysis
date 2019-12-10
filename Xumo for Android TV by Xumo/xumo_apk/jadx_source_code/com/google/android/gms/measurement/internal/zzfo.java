package com.google.android.gms.measurement.internal;

import java.util.concurrent.Callable;

final class zzfo implements Callable<String> {
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ zzfk zzaur;

    zzfo(zzfk com_google_android_gms_measurement_internal_zzfk, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzaur = com_google_android_gms_measurement_internal_zzfk;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final /* synthetic */ Object call() throws Exception {
        zzg zza;
        if (this.zzaur.zzgv().zzbc(this.zzaqv.packageName)) {
            zza = this.zzaur.zzg(this.zzaqv);
        } else {
            zza = this.zzaur.zzjt().zzbo(this.zzaqv.packageName);
        }
        if (zza != null) {
            return zza.getAppInstanceId();
        }
        this.zzaur.zzgt().zzjj().zzca("App info was null when attempting to get app instance id");
        return null;
    }
}
