package com.google.ads.interactivemedia.v3.internal;

import java.util.Iterator;

/* compiled from: IMASDK */
abstract class lq<F, T> implements Iterator<T> {
    final Iterator<? extends F> a;

    abstract T a(F f);

    public final boolean hasNext() {
        return this.a.hasNext();
    }

    public final T next() {
        return a(this.a.next());
    }

    public final void remove() {
        this.a.remove();
    }
}
