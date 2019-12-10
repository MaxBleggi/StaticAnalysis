package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.Cast.Listener;

final class zzba extends Listener {
    private final /* synthetic */ zzaz zzrv;

    zzba(zzaz com_google_android_gms_internal_cast_zzaz) {
        this.zzrv = com_google_android_gms_internal_cast_zzaz;
    }

    public final void onVolumeChanged() {
        this.zzrv.zzcp();
    }
}
