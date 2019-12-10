package com.google.ads.interactivemedia.v3.internal;

import java.util.Comparator;

/* compiled from: IMASDK */
public abstract class ll<T> implements Comparator<T> {
    public static <T> ll<T> a(Comparator<T> comparator) {
        if (comparator instanceof ll) {
            return (ll) comparator;
        }
        return new lb(comparator);
    }

    public abstract int compare(T t, T t2);

    protected ll() {
    }

    public <F> ll<F> a(kr<F, ? extends T> krVar) {
        return new ky(krVar, this);
    }
}
