package com.google.android.gms.cast.framework;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;

public final class zza extends zzg {
    private final AppVisibilityListener zzhd;

    public zza(AppVisibilityListener appVisibilityListener) {
        this.zzhd = appVisibilityListener;
    }

    public final int zzm() {
        return 12451009;
    }

    public final IObjectWrapper zzn() {
        return ObjectWrapper.wrap(this.zzhd);
    }

    public final void onAppEnteredForeground() {
        this.zzhd.onAppEnteredForeground();
    }

    public final void onAppEnteredBackground() {
        this.zzhd.onAppEnteredBackground();
    }
}
