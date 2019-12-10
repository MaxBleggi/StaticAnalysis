package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

final class zzc extends SimpleOnGestureListener {
    private final /* synthetic */ View zzki;
    private final /* synthetic */ boolean zzkj = true;
    private final /* synthetic */ zzh zzkk;

    zzc(zza com_google_android_gms_cast_framework_internal_featurehighlight_zza, View view, boolean z, zzh com_google_android_gms_cast_framework_internal_featurehighlight_zzh) {
        this.zzki = view;
        this.zzkk = com_google_android_gms_cast_framework_internal_featurehighlight_zzh;
    }

    public final boolean onSingleTapUp(MotionEvent motionEvent) {
        if (this.zzki.getParent() != null) {
            this.zzki.performClick();
        }
        if (this.zzkj != null) {
            this.zzkk.zzao();
        }
        return true;
    }
}
