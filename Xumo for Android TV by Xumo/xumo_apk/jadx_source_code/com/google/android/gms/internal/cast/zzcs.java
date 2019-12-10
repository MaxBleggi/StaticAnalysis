package com.google.android.gms.internal.cast;

final class zzcs implements Runnable {
    private final /* synthetic */ zzco zzxh;
    private final /* synthetic */ zzcw zzxj;

    zzcs(zzcq com_google_android_gms_internal_cast_zzcq, zzco com_google_android_gms_internal_cast_zzco, zzcw com_google_android_gms_internal_cast_zzcw) {
        this.zzxh = com_google_android_gms_internal_cast_zzco;
        this.zzxj = com_google_android_gms_internal_cast_zzcw;
    }

    public final void run() {
        this.zzxh.zza(this.zzxj);
    }
}
