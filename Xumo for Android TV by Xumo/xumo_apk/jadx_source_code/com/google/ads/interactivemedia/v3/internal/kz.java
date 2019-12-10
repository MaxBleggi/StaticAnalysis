package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class kz {
    static void a(Object obj, Object obj2) {
        StringBuilder stringBuilder;
        if (obj == null) {
            obj2 = String.valueOf(obj2);
            stringBuilder = new StringBuilder(String.valueOf(obj2).length() + 24);
            stringBuilder.append("null key in entry: null=");
            stringBuilder.append(obj2);
            throw new NullPointerException(stringBuilder.toString());
        } else if (obj2 == null) {
            obj = String.valueOf(obj);
            stringBuilder = new StringBuilder(String.valueOf(obj).length() + 26);
            stringBuilder.append("null value in entry: ");
            stringBuilder.append(obj);
            stringBuilder.append("=null");
            throw new NullPointerException(stringBuilder.toString());
        }
    }

    static int a(int i, String str) {
        if (i >= 0) {
            return i;
        }
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 40);
        stringBuilder.append(str);
        stringBuilder.append(" cannot be negative but was: ");
        stringBuilder.append(i);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
