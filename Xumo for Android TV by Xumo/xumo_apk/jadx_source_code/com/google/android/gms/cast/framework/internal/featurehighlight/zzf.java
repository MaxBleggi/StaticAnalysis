package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

final class zzf extends AnimatorListenerAdapter {
    private final /* synthetic */ zza zzkh;
    private final /* synthetic */ Runnable zzkm;

    zzf(zza com_google_android_gms_cast_framework_internal_featurehighlight_zza, Runnable runnable) {
        this.zzkh = com_google_android_gms_cast_framework_internal_featurehighlight_zza;
        this.zzkm = runnable;
    }

    public final void onAnimationEnd(Animator animator) {
        this.zzkh.setVisibility(8);
        this.zzkh.zzkb = null;
        if (this.zzkm != null) {
            this.zzkm.run();
        }
    }
}
