package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzdk implements Runnable {
    private final /* synthetic */ String zzaeh;
    private final /* synthetic */ String zzaeo;
    private final /* synthetic */ boolean zzaev;
    private final /* synthetic */ String zzaqy;
    private final /* synthetic */ AtomicReference zzarq;
    private final /* synthetic */ zzcy zzarr;

    zzdk(zzcy com_google_android_gms_measurement_internal_zzcy, AtomicReference atomicReference, String str, String str2, String str3, boolean z) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzarq = atomicReference;
        this.zzaqy = str;
        this.zzaeh = str2;
        this.zzaeo = str3;
        this.zzaev = z;
    }

    public final void run() {
        this.zzarr.zzadp.zzgl().zza(this.zzarq, this.zzaqy, this.zzaeh, this.zzaeo, this.zzaev);
    }
}
