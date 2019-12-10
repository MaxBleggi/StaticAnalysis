package com.google.android.gms.internal.measurement;

import java.util.List;

abstract class zzwo {
    private static final zzwo zzcbe = new zzwq();
    private static final zzwo zzcbf = new zzwr();

    private zzwo() {
    }

    abstract <L> List<L> zza(Object obj, long j);

    abstract <L> void zza(Object obj, Object obj2, long j);

    abstract void zzb(Object obj, long j);

    static zzwo zzxk() {
        return zzcbe;
    }

    static zzwo zzxl() {
        return zzcbf;
    }
}
