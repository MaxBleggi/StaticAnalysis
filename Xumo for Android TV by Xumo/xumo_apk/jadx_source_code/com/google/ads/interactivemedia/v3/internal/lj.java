package com.google.ads.interactivemedia.v3.internal;

import java.util.Map;
import java.util.Map.Entry;

/* compiled from: IMASDK */
public final class lj {

    /* compiled from: IMASDK */
    class AnonymousClass1 extends lq<Entry<K, V>, K> {
        K a(Entry<K, V> entry) {
            return entry.getKey();
        }
    }

    /* compiled from: IMASDK */
    private enum a implements kr<Entry<?, ?>, Object> {
        KEY {
            public Object a(Entry<?, ?> entry) {
                return entry.getKey();
            }
        },
        VALUE {
            public Object a(Entry<?, ?> entry) {
                return entry.getValue();
            }
        };
    }

    static <V> kr<Entry<?, V>, V> a() {
        return a.VALUE;
    }

    static boolean a(Map<?, ?> map, Object obj) {
        if (map == obj) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return null;
        }
        return map.entrySet().equals(((Map) obj).entrySet());
    }

    static String a(Map<?, ?> map) {
        StringBuilder a = la.a(map.size());
        a.append('{');
        map = map.entrySet().iterator();
        Object obj = 1;
        while (map.hasNext()) {
            Entry entry = (Entry) map.next();
            if (obj == null) {
                a.append(", ");
            }
            obj = null;
            a.append(entry.getKey());
            a.append('=');
            a.append(entry.getValue());
        }
        a.append('}');
        return a.toString();
    }
}
