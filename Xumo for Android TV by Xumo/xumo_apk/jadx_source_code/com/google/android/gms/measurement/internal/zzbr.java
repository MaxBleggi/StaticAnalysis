package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread.UncaughtExceptionHandler;

final class zzbr implements UncaughtExceptionHandler {
    private final String zzapl;
    private final /* synthetic */ zzbp zzapm;

    public zzbr(zzbp com_google_android_gms_measurement_internal_zzbp, String str) {
        this.zzapm = com_google_android_gms_measurement_internal_zzbp;
        Preconditions.checkNotNull(str);
        this.zzapl = str;
    }

    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        this.zzapm.zzgt().zzjg().zzg(this.zzapl, th);
    }
}
