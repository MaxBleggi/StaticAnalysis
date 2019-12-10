package com.google.android.gms.internal.measurement;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

class zzyg extends AbstractSet<Entry<K, V>> {
    private final /* synthetic */ zzxx zzcdc;

    private zzyg(zzxx com_google_android_gms_internal_measurement_zzxx) {
        this.zzcdc = com_google_android_gms_internal_measurement_zzxx;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new zzyf(this.zzcdc);
    }

    public int size() {
        return this.zzcdc.size();
    }

    public boolean contains(Object obj) {
        Entry entry = (Entry) obj;
        Object obj2 = this.zzcdc.get(entry.getKey());
        obj = entry.getValue();
        if (obj2 != obj) {
            if (obj2 == null || obj2.equals(obj) == null) {
                return null;
            }
        }
        return true;
    }

    public boolean remove(Object obj) {
        Entry entry = (Entry) obj;
        if (!contains(entry)) {
            return null;
        }
        this.zzcdc.remove(entry.getKey());
        return true;
    }

    public void clear() {
        this.zzcdc.clear();
    }

    public /* synthetic */ boolean add(Object obj) {
        Entry entry = (Entry) obj;
        if (contains(entry)) {
            return null;
        }
        this.zzcdc.zza((Comparable) entry.getKey(), entry.getValue());
        return true;
    }
}
