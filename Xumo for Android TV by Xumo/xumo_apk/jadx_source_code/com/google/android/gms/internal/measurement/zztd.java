package com.google.android.gms.internal.measurement;

import android.net.Uri;

public final class zztd {
    private final String zzbsd;
    private final Uri zzbse;
    private final String zzbsf;
    private final String zzbsg;
    private final boolean zzbsh;
    private final boolean zzbsi;
    private final boolean zzbsj;

    public zztd(Uri uri) {
        this(null, uri, "", "", false, false, false);
    }

    private zztd(String str, Uri uri, String str2, String str3, boolean z, boolean z2, boolean z3) {
        this.zzbsd = null;
        this.zzbse = uri;
        this.zzbsf = str2;
        this.zzbsg = str3;
        this.zzbsh = false;
        this.zzbsi = false;
        this.zzbsj = false;
    }

    public final zzsx<Long> zze(String str, long j) {
        return zzsx.zza(this, str, j);
    }

    public final zzsx<Boolean> zzd(String str, boolean z) {
        return zzsx.zza(this, str, z);
    }

    public final zzsx<Integer> zzd(String str, int i) {
        return zzsx.zza(this, str, i);
    }

    public final zzsx<Double> zzb(String str, double d) {
        return zzsx.zza(this, str, d);
    }

    public final zzsx<String> zzy(String str, String str2) {
        return zzsx.zza(this, str, str2);
    }
}
