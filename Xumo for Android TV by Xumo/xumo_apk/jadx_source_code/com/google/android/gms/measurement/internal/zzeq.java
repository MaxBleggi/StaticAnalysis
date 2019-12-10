package com.google.android.gms.measurement.internal;

import android.content.ComponentName;

final class zzeq implements Runnable {
    private final /* synthetic */ ComponentName val$name;
    private final /* synthetic */ zzeo zzate;

    zzeq(zzeo com_google_android_gms_measurement_internal_zzeo, ComponentName componentName) {
        this.zzate = com_google_android_gms_measurement_internal_zzeo;
        this.val$name = componentName;
    }

    public final void run() {
        this.zzate.zzasv.onServiceDisconnected(this.val$name);
    }
}
