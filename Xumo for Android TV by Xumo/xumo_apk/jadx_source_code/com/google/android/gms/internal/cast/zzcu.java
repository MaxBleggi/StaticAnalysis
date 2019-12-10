package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.Cast.MessageReceivedCallback;

final class zzcu implements Runnable {
    private final /* synthetic */ String zzad;
    private final /* synthetic */ zzco zzxh;
    private final /* synthetic */ String zzxl;

    zzcu(zzcq com_google_android_gms_internal_cast_zzcq, zzco com_google_android_gms_internal_cast_zzco, String str, String str2) {
        this.zzxh = com_google_android_gms_internal_cast_zzco;
        this.zzad = str;
        this.zzxl = str2;
    }

    public final void run() {
        synchronized (this.zzxh.zzwj) {
            MessageReceivedCallback messageReceivedCallback = (MessageReceivedCallback) this.zzxh.zzwj.get(this.zzad);
        }
        if (messageReceivedCallback != null) {
            messageReceivedCallback.onMessageReceived(this.zzxh.zzih, this.zzad, this.zzxl);
            return;
        }
        zzco.zzbe.d("Discarded message for unknown namespace '%s'", this.zzad);
    }
}
