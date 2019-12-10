package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzxz implements Iterator<Entry<K, V>> {
    private int pos;
    private Iterator<Entry<K, V>> zzcdb;
    private final /* synthetic */ zzxx zzcdc;

    private zzxz(zzxx com_google_android_gms_internal_measurement_zzxx) {
        this.zzcdc = com_google_android_gms_internal_measurement_zzxx;
        this.pos = this.zzcdc.zzccw.size();
    }

    public final boolean hasNext() {
        return (this.pos > 0 && this.pos <= this.zzcdc.zzccw.size()) || zzyo().hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Iterator<Entry<K, V>> zzyo() {
        if (this.zzcdb == null) {
            this.zzcdb = this.zzcdc.zzccz.entrySet().iterator();
        }
        return this.zzcdb;
    }

    public final /* synthetic */ Object next() {
        if (zzyo().hasNext()) {
            return (Entry) zzyo().next();
        }
        List zzb = this.zzcdc.zzccw;
        int i = this.pos - 1;
        this.pos = i;
        return (Entry) zzb.get(i);
    }
}
