package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

final class zze extends AnimatorListenerAdapter {
    private final /* synthetic */ zza zzkh;

    zze(zza com_google_android_gms_cast_framework_internal_featurehighlight_zza) {
        this.zzkh = com_google_android_gms_cast_framework_internal_featurehighlight_zza;
    }

    public final void onAnimationEnd(Animator animator) {
        this.zzkh.zzkb = this.zzkh.zzau();
        this.zzkh.zzkb.start();
    }
}
