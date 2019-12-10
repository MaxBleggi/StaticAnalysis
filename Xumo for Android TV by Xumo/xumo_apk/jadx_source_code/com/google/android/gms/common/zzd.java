package com.google.android.gms.common;

import java.util.concurrent.Callable;

final /* synthetic */ class zzd implements Callable {
    private final boolean zzq;
    private final String zzr;
    private final zze zzs;

    zzd(boolean z, String str, zze com_google_android_gms_common_zze) {
        this.zzq = z;
        this.zzr = str;
        this.zzs = com_google_android_gms_common_zze;
    }

    public final Object call() {
        return zzc.zza(this.zzq, this.zzr, this.zzs);
    }
}
