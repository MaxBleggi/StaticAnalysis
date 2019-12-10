package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
public final class lk {
    public static <T> T[] a(T[] tArr, int i) {
        return lm.a(tArr, i);
    }

    static Object[] a(Object... objArr) {
        return b(objArr, objArr.length);
    }

    static Object[] b(Object[] objArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            a(objArr[i2], i2);
        }
        return objArr;
    }

    static Object a(Object obj, int i) {
        if (obj != null) {
            return obj;
        }
        StringBuilder stringBuilder = new StringBuilder(20);
        stringBuilder.append("at index ");
        stringBuilder.append(i);
        throw new NullPointerException(stringBuilder.toString());
    }
}
