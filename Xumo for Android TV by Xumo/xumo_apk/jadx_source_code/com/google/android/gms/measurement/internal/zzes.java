package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;

final class zzes implements Runnable {
    private final /* synthetic */ zzeo zzate;

    zzes(zzeo com_google_android_gms_measurement_internal_zzeo) {
        this.zzate = com_google_android_gms_measurement_internal_zzeo;
    }

    public final void run() {
        zzdz com_google_android_gms_measurement_internal_zzdz = this.zzate.zzasv;
        Context context = this.zzate.zzasv.getContext();
        this.zzate.zzasv.zzgw();
        com_google_android_gms_measurement_internal_zzdz.onServiceDisconnected(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
