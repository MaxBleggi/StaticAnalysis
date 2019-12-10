package com.google.ads.interactivemedia.v3.internal;

import java.lang.reflect.Array;

/* compiled from: IMASDK */
final class lm {
    static <T> T[] a(T[] tArr, int i) {
        return (Object[]) Array.newInstance(tArr.getClass().getComponentType(), i);
    }
}
