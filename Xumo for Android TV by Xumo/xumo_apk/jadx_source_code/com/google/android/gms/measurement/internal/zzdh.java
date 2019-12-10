package com.google.android.gms.measurement.internal;

import com.google.android.gms.measurement.AppMeasurement.ConditionalUserProperty;

final class zzdh implements Runnable {
    private final /* synthetic */ zzcy zzarr;
    private final /* synthetic */ ConditionalUserProperty zzary;

    zzdh(zzcy com_google_android_gms_measurement_internal_zzcy, ConditionalUserProperty conditionalUserProperty) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzary = conditionalUserProperty;
    }

    public final void run() {
        this.zzarr.zzc(this.zzary);
    }
}
