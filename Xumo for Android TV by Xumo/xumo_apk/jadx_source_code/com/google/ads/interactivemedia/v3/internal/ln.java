package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
class ln<E> extends le<E> {
    static final le<Object> a = new ln(new Object[0], 0);
    final transient Object[] b;
    private final transient int c;

    ln(Object[] objArr, int i) {
        this.b = objArr;
        this.c = i;
    }

    boolean c() {
        return false;
    }

    public int size() {
        return this.c;
    }

    int a(Object[] objArr, int i) {
        System.arraycopy(this.b, 0, objArr, i, this.c);
        return i + this.c;
    }

    public E get(int i) {
        kv.a(i, this.c);
        return this.b[i];
    }
}
