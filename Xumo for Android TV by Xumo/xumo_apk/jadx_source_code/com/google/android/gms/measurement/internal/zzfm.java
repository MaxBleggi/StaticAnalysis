package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.Map;

final class zzfm implements zzaw {
    private final /* synthetic */ zzfk zzaur;
    private final /* synthetic */ String zzaus;

    zzfm(zzfk com_google_android_gms_measurement_internal_zzfk, String str) {
        this.zzaur = com_google_android_gms_measurement_internal_zzfk;
        this.zzaus = str;
    }

    public final void zza(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        this.zzaur.zza(i, th, bArr, this.zzaus);
    }
}
