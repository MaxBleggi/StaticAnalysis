package com.google.android.gms.internal.cast;

import android.view.ViewGroup;

final class zzq implements Runnable {
    private final /* synthetic */ zzo zzjl;

    zzq(zzo com_google_android_gms_internal_cast_zzo) {
        this.zzjl = com_google_android_gms_internal_cast_zzo;
    }

    public final void run() {
        if (this.zzjl.zzjk.zzjj) {
            ((ViewGroup) this.zzjl.zzjk.zzim.getWindow().getDecorView()).removeView(this.zzjl.zzjk);
            if (this.zzjl.zzjk.zziq != null) {
                this.zzjl.zzjk.zziq.onOverlayDismissed();
            }
            this.zzjl.zzjk.reset();
        }
    }
}
