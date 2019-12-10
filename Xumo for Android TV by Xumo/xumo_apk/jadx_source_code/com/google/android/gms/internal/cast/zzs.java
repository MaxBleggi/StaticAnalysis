package com.google.android.gms.internal.cast;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.gms.common.util.PlatformVersion;

final class zzs implements OnClickListener {
    final /* synthetic */ zzr zzjo;

    zzs(zzr com_google_android_gms_internal_cast_zzr) {
        this.zzjo = com_google_android_gms_internal_cast_zzr;
    }

    public final void onClick(View view) {
        if (PlatformVersion.isAtLeastJellyBean() != null) {
            view = ObjectAnimator.ofFloat(this, "alpha", new float[]{0.0f});
            view.setDuration(400).addListener(new zzt(this));
            view.start();
            return;
        }
        this.zzjo.zzap();
    }
}
