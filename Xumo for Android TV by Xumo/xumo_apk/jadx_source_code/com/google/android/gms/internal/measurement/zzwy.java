package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzwy<K, V> extends LinkedHashMap<K, V> {
    private static final zzwy zzcbp;
    private boolean zzbup = true;

    private zzwy() {
    }

    private zzwy(Map<K, V> map) {
        super(map);
    }

    public static <K, V> zzwy<K, V> zzxn() {
        return zzcbp;
    }

    public final void zza(zzwy<K, V> com_google_android_gms_internal_measurement_zzwy_K__V) {
        zzxp();
        if (!com_google_android_gms_internal_measurement_zzwy_K__V.isEmpty()) {
            putAll(com_google_android_gms_internal_measurement_zzwy_K__V);
        }
    }

    public final Set<Entry<K, V>> entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    public final void clear() {
        zzxp();
        super.clear();
    }

    public final V put(K k, V v) {
        zzxp();
        zzvz.checkNotNull(k);
        zzvz.checkNotNull(v);
        return super.put(k, v);
    }

    public final void putAll(Map<? extends K, ? extends V> map) {
        zzxp();
        for (Object next : map.keySet()) {
            zzvz.checkNotNull(next);
            zzvz.checkNotNull(map.get(next));
        }
        super.putAll(map);
    }

    public final V remove(Object obj) {
        zzxp();
        return super.remove(obj);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Map) {
            obj = (Map) obj;
            if (this != obj) {
                if (size() == obj.size()) {
                    for (Entry entry : entrySet()) {
                        if (obj.containsKey(entry.getKey())) {
                            boolean equals;
                            Object value = entry.getValue();
                            Object obj2 = obj.get(entry.getKey());
                            if ((value instanceof byte[]) && (obj2 instanceof byte[])) {
                                equals = Arrays.equals((byte[]) value, (byte[]) obj2);
                                continue;
                            } else {
                                equals = value.equals(obj2);
                                continue;
                            }
                            if (!equals) {
                            }
                        }
                    }
                }
                obj = null;
                if (obj != null) {
                }
            }
            obj = true;
            return obj != null;
        }
    }

    private static int zzab(Object obj) {
        if (obj instanceof byte[]) {
            return zzvz.hashCode((byte[]) obj);
        }
        if (!(obj instanceof zzwa)) {
            return obj.hashCode();
        }
        throw new UnsupportedOperationException();
    }

    public final int hashCode() {
        int i = 0;
        for (Entry entry : entrySet()) {
            i += zzab(entry.getValue()) ^ zzab(entry.getKey());
        }
        return i;
    }

    public final zzwy<K, V> zzxo() {
        return isEmpty() ? new zzwy() : new zzwy(this);
    }

    public final void zzsw() {
        this.zzbup = false;
    }

    public final boolean isMutable() {
        return this.zzbup;
    }

    private final void zzxp() {
        if (!this.zzbup) {
            throw new UnsupportedOperationException();
        }
    }

    static {
        zzwy com_google_android_gms_internal_measurement_zzwy = new zzwy();
        zzcbp = com_google_android_gms_internal_measurement_zzwy;
        com_google_android_gms_internal_measurement_zzwy.zzbup = false;
    }
}
