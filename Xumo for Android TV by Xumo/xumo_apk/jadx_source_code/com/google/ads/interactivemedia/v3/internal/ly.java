package com.google.ads.interactivemedia.v3.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: IMASDK */
public class ly {
    public static final String a = String.valueOf('.');
    public static final String b = String.valueOf('$');
    private static final Map<String, Class<?>> c = new HashMap();
    private static final Map<Class<?>, Class<?>> d = new HashMap();
    private static final Map<Class<?>, Class<?>> e = new HashMap();
    private static final Map<String, String> f;
    private static final Map<String, String> g;

    public static boolean a(Class<?> cls) {
        boolean z = false;
        if (cls == null) {
            return false;
        }
        if (cls.isPrimitive() || b(cls) != null) {
            z = true;
        }
        return z;
    }

    public static boolean b(Class<?> cls) {
        return e.containsKey(cls);
    }

    static {
        c.put("boolean", Boolean.TYPE);
        c.put("byte", Byte.TYPE);
        c.put("char", Character.TYPE);
        c.put("short", Short.TYPE);
        c.put("int", Integer.TYPE);
        c.put("long", Long.TYPE);
        c.put("double", Double.TYPE);
        c.put("float", Float.TYPE);
        c.put("void", Void.TYPE);
        d.put(Boolean.TYPE, Boolean.class);
        d.put(Byte.TYPE, Byte.class);
        d.put(Character.TYPE, Character.class);
        d.put(Short.TYPE, Short.class);
        d.put(Integer.TYPE, Integer.class);
        d.put(Long.TYPE, Long.class);
        d.put(Double.TYPE, Double.class);
        d.put(Float.TYPE, Float.class);
        d.put(Void.TYPE, Void.TYPE);
        for (Entry entry : d.entrySet()) {
            Class cls = (Class) entry.getKey();
            Class cls2 = (Class) entry.getValue();
            if (!cls.equals(cls2)) {
                e.put(cls2, cls);
            }
        }
        Map hashMap = new HashMap();
        hashMap.put("int", "I");
        hashMap.put("boolean", "Z");
        hashMap.put("float", "F");
        hashMap.put("long", "J");
        hashMap.put("short", "S");
        hashMap.put("byte", "B");
        hashMap.put("double", "D");
        hashMap.put("char", "C");
        Map hashMap2 = new HashMap();
        for (Entry entry2 : hashMap.entrySet()) {
            hashMap2.put(entry2.getValue(), entry2.getKey());
        }
        f = Collections.unmodifiableMap(hashMap);
        g = Collections.unmodifiableMap(hashMap2);
    }
}
