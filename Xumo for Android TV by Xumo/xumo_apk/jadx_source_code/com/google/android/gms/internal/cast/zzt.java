package com.google.android.gms.internal.cast;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

final class zzt extends AnimatorListenerAdapter {
    private final /* synthetic */ zzs zzjp;

    zzt(zzs com_google_android_gms_internal_cast_zzs) {
        this.zzjp = com_google_android_gms_internal_cast_zzs;
    }

    public final void onAnimationEnd(Animator animator) {
        this.zzjp.zzjo.zzap();
    }
}
