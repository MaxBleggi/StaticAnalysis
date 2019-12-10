package com.google.android.gms.internal.cast;

final class zzct implements Runnable {
    private final /* synthetic */ zzco zzxh;
    private final /* synthetic */ zzce zzxk;

    zzct(zzcq com_google_android_gms_internal_cast_zzcq, zzco com_google_android_gms_internal_cast_zzco, zzce com_google_android_gms_internal_cast_zzce) {
        this.zzxh = com_google_android_gms_internal_cast_zzco;
        this.zzxk = com_google_android_gms_internal_cast_zzce;
    }

    public final void run() {
        this.zzxh.zza(this.zzxk);
    }
}
