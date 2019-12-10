package com.google.ads.interactivemedia.v3.internal;

import android.text.TextUtils;

/* compiled from: IMASDK */
public class af {
    public static void a() {
        if (!a.b()) {
            throw new IllegalStateException("Method called before OMID activation");
        }
    }

    public static void a(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void a(String str, String str2) {
        if (TextUtils.isEmpty(str) != null) {
            throw new IllegalArgumentException(str2);
        }
    }

    public static void a(String str, int i, String str2) {
        if (str.length() > i) {
            throw new IllegalArgumentException(str2);
        }
    }

    public static void a(h hVar) {
        if (hVar.equals(h.NONE) != null) {
            throw new IllegalArgumentException("Impression owner is none");
        }
    }
}
