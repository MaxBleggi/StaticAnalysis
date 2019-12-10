package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzyf implements Iterator<Entry<K, V>> {
    private int pos;
    private Iterator<Entry<K, V>> zzcdb;
    private final /* synthetic */ zzxx zzcdc;
    private boolean zzcdg;

    private zzyf(zzxx com_google_android_gms_internal_measurement_zzxx) {
        this.zzcdc = com_google_android_gms_internal_measurement_zzxx;
        this.pos = -1;
    }

    public final boolean hasNext() {
        if (this.pos + 1 >= this.zzcdc.zzccw.size()) {
            if (this.zzcdc.zzccx.isEmpty() || !zzyo().hasNext()) {
                return false;
            }
        }
        return true;
    }

    public final void remove() {
        if (this.zzcdg) {
            this.zzcdg = false;
            this.zzcdc.zzym();
            if (this.pos < this.zzcdc.zzccw.size()) {
                zzxx com_google_android_gms_internal_measurement_zzxx = this.zzcdc;
                int i = this.pos;
                this.pos = i - 1;
                com_google_android_gms_internal_measurement_zzxx.zzbx(i);
                return;
            }
            zzyo().remove();
            return;
        }
        throw new IllegalStateException("remove() was called before next()");
    }

    private final Iterator<Entry<K, V>> zzyo() {
        if (this.zzcdb == null) {
            this.zzcdb = this.zzcdc.zzccx.entrySet().iterator();
        }
        return this.zzcdb;
    }

    public final /* synthetic */ Object next() {
        this.zzcdg = true;
        int i = this.pos + 1;
        this.pos = i;
        if (i < this.zzcdc.zzccw.size()) {
            return (Entry) this.zzcdc.zzccw.get(this.pos);
        }
        return (Entry) zzyo().next();
    }
}
