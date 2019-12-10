package com.google.android.gms.internal.cast;

final class zzcr implements Runnable {
    private final /* synthetic */ zzco zzxh;
    private final /* synthetic */ int zzxi;

    zzcr(zzcq com_google_android_gms_internal_cast_zzcq, zzco com_google_android_gms_internal_cast_zzco, int i) {
        this.zzxh = com_google_android_gms_internal_cast_zzco;
        this.zzxi = i;
    }

    public final void run() {
        this.zzxh.zzaj.onApplicationDisconnected(this.zzxi);
    }
}
