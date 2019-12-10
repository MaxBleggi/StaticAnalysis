package com.google.ads.interactivemedia.v3.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: IMASDK */
public abstract class ld<E> extends AbstractCollection<E> implements Serializable {
    private static final Object[] a = new Object[0];

    /* compiled from: IMASDK */
    public static abstract class a<E> {
        static int a(int i, int i2) {
            if (i2 >= 0) {
                i = (i + (i >> 1)) + 1;
                if (i < i2) {
                    i = Integer.highestOneBit(i2 - 1) << 1;
                }
                return i < 0 ? Integer.MAX_VALUE : i;
            } else {
                throw new AssertionError("cannot store more than MAX_VALUE elements");
            }
        }
    }

    ld() {
    }

    public abstract lr<E> a();

    abstract boolean c();

    public abstract boolean contains(Object obj);

    public final Object[] toArray() {
        int size = size();
        if (size == 0) {
            return a;
        }
        Object[] objArr = new Object[size];
        a(objArr, 0);
        return objArr;
    }

    public final <T> T[] toArray(T[] tArr) {
        kv.a(tArr);
        int size = size();
        if (tArr.length < size) {
            tArr = lk.a((Object[]) tArr, size);
        } else if (tArr.length > size) {
            tArr[size] = null;
        }
        a(tArr, 0);
        return tArr;
    }

    @Deprecated
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public le<E> b() {
        return isEmpty() ? le.d() : le.a(toArray());
    }

    int a(Object[] objArr, int i) {
        Iterator a = a();
        while (a.hasNext()) {
            int i2 = i + 1;
            objArr[i] = a.next();
            i = i2;
        }
        return i;
    }

    public /* synthetic */ Iterator iterator() {
        return a();
    }
}
