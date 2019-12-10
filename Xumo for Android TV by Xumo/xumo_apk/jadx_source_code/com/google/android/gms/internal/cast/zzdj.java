package com.google.android.gms.internal.cast;

final class zzdj implements zzdn {
    private final /* synthetic */ zzdn zzyw;
    private final /* synthetic */ zzdi zzyx;

    zzdj(zzdi com_google_android_gms_internal_cast_zzdi, zzdn com_google_android_gms_internal_cast_zzdn) {
        this.zzyx = com_google_android_gms_internal_cast_zzdi;
        this.zzyw = com_google_android_gms_internal_cast_zzdn;
    }

    public final void zza(long j, int i, Object obj) {
        this.zzyx.zzyc = null;
        if (this.zzyw != null) {
            this.zzyw.zza(j, i, obj);
        }
    }

    public final void zzb(long j) {
        if (this.zzyw != null) {
            this.zzyw.zzb(j);
        }
    }
}
