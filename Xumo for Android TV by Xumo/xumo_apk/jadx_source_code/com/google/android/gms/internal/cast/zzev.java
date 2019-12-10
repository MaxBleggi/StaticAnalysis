package com.google.android.gms.internal.cast;

import android.annotation.TargetApi;
import android.view.Choreographer;

@TargetApi(16)
final class zzev extends zzep {
    private Choreographer zzzz = Choreographer.getInstance();

    public final void zza(zzer com_google_android_gms_internal_cast_zzer) {
        this.zzzz.postFrameCallback(com_google_android_gms_internal_cast_zzer.zzdv());
    }
}
