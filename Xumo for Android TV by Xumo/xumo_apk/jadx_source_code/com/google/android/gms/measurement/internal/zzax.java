package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import java.util.List;
import java.util.Map;

@WorkerThread
final class zzax implements Runnable {
    private final String packageName;
    private final int status;
    private final zzaw zzamy;
    private final Throwable zzamz;
    private final byte[] zzana;
    private final Map<String, List<String>> zzanb;

    private zzax(String str, zzaw com_google_android_gms_measurement_internal_zzaw, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzaw);
        this.zzamy = com_google_android_gms_measurement_internal_zzaw;
        this.status = i;
        this.zzamz = th;
        this.zzana = bArr;
        this.packageName = str;
        this.zzanb = map;
    }

    public final void run() {
        this.zzamy.zza(this.packageName, this.status, this.zzamz, this.zzana, this.zzanb);
    }
}
