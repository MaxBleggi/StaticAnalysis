package com.google.android.gms.measurement.internal;

final class zzdb implements Runnable {
    private final /* synthetic */ String val$name;
    private final /* synthetic */ String zzaeh;
    private final /* synthetic */ zzcy zzarr;
    private final /* synthetic */ long zzars;
    private final /* synthetic */ Object zzarw;

    zzdb(zzcy com_google_android_gms_measurement_internal_zzcy, String str, String str2, Object obj, long j) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzaeh = str;
        this.val$name = str2;
        this.zzarw = obj;
        this.zzars = j;
    }

    public final void run() {
        this.zzarr.zza(this.zzaeh, this.val$name, this.zzarw, this.zzars);
    }
}
