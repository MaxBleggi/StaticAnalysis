package com.google.android.gms.internal.measurement;

import java.util.Map.Entry;

final class zzwj<K> implements Entry<K, Object> {
    private Entry<K, zzwh> zzcaw;

    private zzwj(Entry<K, zzwh> entry) {
        this.zzcaw = entry;
    }

    public final K getKey() {
        return this.zzcaw.getKey();
    }

    public final Object getValue() {
        if (((zzwh) this.zzcaw.getValue()) == null) {
            return null;
        }
        return zzwh.zzxg();
    }

    public final zzwh zzxh() {
        return (zzwh) this.zzcaw.getValue();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzxe) {
            return ((zzwh) this.zzcaw.getValue()).zzi((zzxe) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }
}
