package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.view.View;
import android.view.View.OnLayoutChangeListener;

final class zzd implements OnLayoutChangeListener {
    private final /* synthetic */ zza zzkh;
    private final /* synthetic */ Runnable zzkl = null;

    zzd(zza com_google_android_gms_cast_framework_internal_featurehighlight_zza, Runnable runnable) {
        this.zzkh = com_google_android_gms_cast_framework_internal_featurehighlight_zza;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (this.zzkl != null) {
            this.zzkl.run();
        }
        this.zzkh.zzaq();
        this.zzkh.removeOnLayoutChangeListener(this);
    }
}
