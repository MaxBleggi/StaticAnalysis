package com.google.android.gms.cast.framework;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.cast.zzdh;

public final class zze {
    private static final zzdh zzbe = new zzdh("DiscoveryManager");
    private final zzp zzil;

    zze(zzp com_google_android_gms_cast_framework_zzp) {
        this.zzil = com_google_android_gms_cast_framework_zzp;
    }

    public final IObjectWrapper zzu() {
        try {
            return this.zzil.zzy();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "getWrappedThis", zzp.class.getSimpleName());
            return null;
        }
    }
}
