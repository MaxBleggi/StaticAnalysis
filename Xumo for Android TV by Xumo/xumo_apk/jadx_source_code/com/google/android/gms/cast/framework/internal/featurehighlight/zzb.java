package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

final class zzb extends SimpleOnGestureListener {
    private final /* synthetic */ zza zzkh;

    zzb(zza com_google_android_gms_cast_framework_internal_featurehighlight_zza) {
        this.zzkh = com_google_android_gms_cast_framework_internal_featurehighlight_zza;
    }

    public final boolean onSingleTapUp(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (!this.zzkh.zza(x, y) || this.zzkh.zzjx.zzb(x, y) == null) {
            this.zzkh.zzkf.dismiss();
        }
        return true;
    }
}
