package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzwk<K> implements Iterator<Entry<K, Object>> {
    private Iterator<Entry<K, Object>> zzcax;

    public zzwk(Iterator<Entry<K, Object>> it) {
        this.zzcax = it;
    }

    public final boolean hasNext() {
        return this.zzcax.hasNext();
    }

    public final void remove() {
        this.zzcax.remove();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zzcax.next();
        return entry.getValue() instanceof zzwh ? new zzwj(entry) : entry;
    }
}
