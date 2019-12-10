package com.google.ads.interactivemedia.v3.internal;

import java.io.Serializable;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: IMASDK */
public abstract class lf<K, V> implements Serializable, Map<K, V> {
    static final Entry<?, ?>[] a = new Entry[0];
    private transient lg<Entry<K, V>> b;
    private transient lg<K> c;
    private transient ld<V> d;

    /* compiled from: IMASDK */
    public static class a<K, V> {
        Comparator<? super V> a;
        Object[] b;
        int c;
        boolean d;

        public a() {
            this(4);
        }

        a(int i) {
            this.b = new Object[(i * 2)];
            this.c = 0;
            this.d = false;
        }

        private void a(int i) {
            i *= 2;
            if (i > this.b.length) {
                this.b = Arrays.copyOf(this.b, com.google.ads.interactivemedia.v3.internal.ld.a.a(this.b.length, i));
                this.d = false;
            }
        }

        public a<K, V> a(K k, V v) {
            a(this.c + 1);
            kz.a((Object) k, (Object) v);
            this.b[this.c * 2] = k;
            this.b[(this.c * 2) + 1] = v;
            this.c++;
            return this;
        }

        public lf<K, V> a() {
            b();
            this.d = true;
            return lo.a(this.c, this.b);
        }

        void b() {
            if (this.a != null) {
                if (this.d) {
                    this.b = Arrays.copyOf(this.b, this.c * 2);
                }
                Entry[] entryArr = new Entry[this.c];
                int i = 0;
                for (int i2 = 0; i2 < this.c; i2++) {
                    int i3 = i2 * 2;
                    entryArr[i2] = new SimpleImmutableEntry(this.b[i3], this.b[i3 + 1]);
                }
                Arrays.sort(entryArr, 0, this.c, ll.a(this.a).a(lj.a()));
                while (i < this.c) {
                    int i4 = i * 2;
                    this.b[i4] = entryArr[i].getKey();
                    this.b[i4 + 1] = entryArr[i].getValue();
                    i++;
                }
            }
        }
    }

    lf() {
    }

    abstract lg<Entry<K, V>> b();

    abstract lg<K> d();

    abstract ld<V> f();

    public abstract V get(Object obj);

    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Object obj) {
        return get(obj) != null ? true : null;
    }

    public boolean containsValue(Object obj) {
        return e().contains(obj);
    }

    public final V getOrDefault(Object obj, V v) {
        obj = get(obj);
        return obj != null ? obj : v;
    }

    public lg<Entry<K, V>> a() {
        lg<Entry<K, V>> lgVar = this.b;
        if (lgVar != null) {
            return lgVar;
        }
        lgVar = b();
        this.b = lgVar;
        return lgVar;
    }

    public lg<K> c() {
        lg<K> lgVar = this.c;
        if (lgVar != null) {
            return lgVar;
        }
        lgVar = d();
        this.c = lgVar;
        return lgVar;
    }

    public ld<V> e() {
        ld<V> ldVar = this.d;
        if (ldVar != null) {
            return ldVar;
        }
        ldVar = f();
        this.d = ldVar;
        return ldVar;
    }

    public boolean equals(Object obj) {
        return lj.a(this, obj);
    }

    public int hashCode() {
        return lp.a(a());
    }

    public String toString() {
        return lj.a(this);
    }

    public /* synthetic */ Set entrySet() {
        return a();
    }

    public /* synthetic */ Collection values() {
        return e();
    }

    public /* synthetic */ Set keySet() {
        return c();
    }
}
