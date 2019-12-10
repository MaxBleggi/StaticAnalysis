package com.google.android.gms.measurement.internal;

import android.os.Bundle;

final class zzda implements Runnable {
    private final /* synthetic */ String val$name;
    private final /* synthetic */ String zzaeh;
    private final /* synthetic */ boolean zzafg;
    private final /* synthetic */ String zzaqy;
    private final /* synthetic */ zzcy zzarr;
    private final /* synthetic */ long zzars;
    private final /* synthetic */ Bundle zzart;
    private final /* synthetic */ boolean zzaru;
    private final /* synthetic */ boolean zzarv;

    zzda(zzcy com_google_android_gms_measurement_internal_zzcy, String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzaeh = str;
        this.val$name = str2;
        this.zzars = j;
        this.zzart = bundle;
        this.zzafg = z;
        this.zzaru = z2;
        this.zzarv = z3;
        this.zzaqy = str3;
    }

    public final void run() {
        this.zzarr.zza(this.zzaeh, this.val$name, this.zzars, this.zzart, this.zzafg, this.zzaru, this.zzarv, this.zzaqy);
    }
}
