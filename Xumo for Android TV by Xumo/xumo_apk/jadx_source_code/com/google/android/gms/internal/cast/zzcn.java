package com.google.android.gms.internal.cast;

import android.text.TextUtils;
import androidx.annotation.NonNull;

public class zzcn {
    private final String namespace;
    protected final zzdh zzwg;
    private zzdm zzwh;

    protected zzcn(String str, String str2, String str3) {
        zzcv.zzo(str);
        this.namespace = str;
        this.zzwg = new zzdh(str2);
        setSessionLabel(str3);
    }

    public void zza(long j, int i) {
    }

    public void zzdd() {
    }

    public void zzn(@NonNull String str) {
    }

    public final void setSessionLabel(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.zzwg.zzt(str);
        }
    }

    public final String getNamespace() {
        return this.namespace;
    }

    public final void zza(zzdm com_google_android_gms_internal_cast_zzdm) {
        this.zzwh = com_google_android_gms_internal_cast_zzdm;
        if (this.zzwh == null) {
            zzdd();
        }
    }

    protected final void zza(String str, long j, String str2) throws IllegalStateException {
        str2 = new Object[]{str, null};
        this.zzwh.zza(this.namespace, str, j, null);
    }

    protected final long zzdf() {
        return this.zzwh.zzl();
    }
}
