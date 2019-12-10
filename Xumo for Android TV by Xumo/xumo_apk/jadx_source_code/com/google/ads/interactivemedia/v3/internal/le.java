package com.google.ads.interactivemedia.v3.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: IMASDK */
public abstract class le<E> extends ld<E> implements List<E>, RandomAccess {
    private static final ls<Object> a = new a(ln.a, 0);

    /* compiled from: IMASDK */
    class b extends le<E> {
        final transient int a;
        final transient int b;
        final /* synthetic */ le c;

        b(le leVar, int i, int i2) {
            this.c = leVar;
            this.a = i;
            this.b = i2;
        }

        boolean c() {
            return true;
        }

        public int size() {
            return this.b;
        }

        public E get(int i) {
            kv.a(i, this.b);
            return this.c.get(i + this.a);
        }

        public le<E> a(int i, int i2) {
            kv.a(i, i2, this.b);
            return this.c.a(i + this.a, i2 + this.a);
        }

        public /* synthetic */ List subList(int i, int i2) {
            return a(i, i2);
        }
    }

    /* compiled from: IMASDK */
    static class a<E> extends kx<E> {
        private final le<E> a;

        a(le<E> leVar, int i) {
            super(leVar.size(), i);
            this.a = leVar;
        }

        protected E a(int i) {
            return this.a.get(i);
        }
    }

    public static <E> le<E> d() {
        return ln.a;
    }

    public final le<E> b() {
        return this;
    }

    public static <E> le<E> a(Collection<? extends E> collection) {
        if (!(collection instanceof ld)) {
            return b(collection.toArray());
        }
        collection = ((ld) collection).b();
        if (collection.c()) {
            collection = a(collection.toArray());
        }
        return collection;
    }

    private static <E> le<E> b(Object... objArr) {
        return a(lk.a(objArr));
    }

    static <E> le<E> a(Object[] objArr) {
        return b(objArr, objArr.length);
    }

    static <E> le<E> b(Object[] objArr, int i) {
        if (i == 0) {
            return d();
        }
        return new ln(objArr, i);
    }

    le() {
    }

    public lr<E> a() {
        return e();
    }

    public ls<E> e() {
        return a(0);
    }

    public ls<E> a(int i) {
        kv.b(i, size());
        if (isEmpty()) {
            return a;
        }
        return new a(this, i);
    }

    public int indexOf(Object obj) {
        return obj == null ? -1 : li.b(this, obj);
    }

    public int lastIndexOf(Object obj) {
        return obj == null ? -1 : li.c(this, obj);
    }

    public boolean contains(Object obj) {
        return indexOf(obj) >= null ? true : null;
    }

    public le<E> a(int i, int i2) {
        kv.a(i, i2, size());
        int i3 = i2 - i;
        if (i3 == size()) {
            return this;
        }
        if (i3 == 0) {
            return d();
        }
        return b(i, i2);
    }

    le<E> b(int i, int i2) {
        return new b(this, i, i2 - i);
    }

    @Deprecated
    public final boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final E remove(int i) {
        throw new UnsupportedOperationException();
    }

    int a(Object[] objArr, int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i + i2] = get(i2);
        }
        return i + size;
    }

    public boolean equals(Object obj) {
        return li.a(this, obj);
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < size(); i2++) {
            i = (((i * 31) + get(i2).hashCode()) ^ -1) ^ -1;
        }
        return i;
    }

    public /* synthetic */ Iterator iterator() {
        return a();
    }

    public /* synthetic */ List subList(int i, int i2) {
        return a(i, i2);
    }

    public /* synthetic */ ListIterator listIterator(int i) {
        return a(i);
    }

    public /* synthetic */ ListIterator listIterator() {
        return e();
    }
}
