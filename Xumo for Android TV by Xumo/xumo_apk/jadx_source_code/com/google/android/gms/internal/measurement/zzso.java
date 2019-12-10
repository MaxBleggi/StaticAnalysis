package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

final class zzso extends ContentObserver {
    private final /* synthetic */ zzsm zzbrt;

    zzso(zzsm com_google_android_gms_internal_measurement_zzsm, Handler handler) {
        this.zzbrt = com_google_android_gms_internal_measurement_zzsm;
        super(null);
    }

    public final void onChange(boolean z) {
        this.zzbrt.zztl();
    }
}
