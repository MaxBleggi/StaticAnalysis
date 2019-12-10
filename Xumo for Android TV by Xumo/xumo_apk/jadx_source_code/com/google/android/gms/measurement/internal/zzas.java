package com.google.android.gms.measurement.internal;

public final class zzas {
    private final int priority;
    private final /* synthetic */ zzaq zzamt;
    private final boolean zzamu;
    private final boolean zzamv;

    zzas(zzaq com_google_android_gms_measurement_internal_zzaq, int i, boolean z, boolean z2) {
        this.zzamt = com_google_android_gms_measurement_internal_zzaq;
        this.priority = i;
        this.zzamu = z;
        this.zzamv = z2;
    }

    public final void zzca(String str) {
        this.zzamt.zza(this.priority, this.zzamu, this.zzamv, str, null, null, null);
    }

    public final void zzg(String str, Object obj) {
        this.zzamt.zza(this.priority, this.zzamu, this.zzamv, str, obj, null, null);
    }

    public final void zze(String str, Object obj, Object obj2) {
        this.zzamt.zza(this.priority, this.zzamu, this.zzamv, str, obj, obj2, null);
    }

    public final void zzd(String str, Object obj, Object obj2, Object obj3) {
        this.zzamt.zza(this.priority, this.zzamu, this.zzamv, str, obj, obj2, obj3);
    }
}
