package com.google.ads.interactivemedia.v3.internal;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

/* compiled from: IMASDK */
final class lo<K, V> extends lf<K, V> {
    static final lf<Object, Object> b = new lo(null, new Object[0], 0);
    final transient Object[] c;
    private final transient int[] d;
    private final transient int e;

    /* compiled from: IMASDK */
    static class a<K, V> extends lg<Entry<K, V>> {
        private final transient lf<K, V> a;
        private final transient Object[] b;
        private final transient int c;
        private final transient int d;

        a(lf<K, V> lfVar, Object[] objArr, int i, int i2) {
            this.a = lfVar;
            this.b = objArr;
            this.c = i;
            this.d = i2;
        }

        boolean c() {
            return true;
        }

        public lr<Entry<K, V>> a() {
            return b().a();
        }

        int a(Object[] objArr, int i) {
            return b().a(objArr, i);
        }

        le<Entry<K, V>> e() {
            return new le<Entry<K, V>>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public boolean c() {
                    return true;
                }

                public Entry<K, V> b(int i) {
                    kv.a(i, this.a.d);
                    i *= 2;
                    return new SimpleImmutableEntry(this.a.b[this.a.c + i], this.a.b[i + (this.a.c ^ 1)]);
                }

                public int size() {
                    return this.a.d;
                }

                public /* synthetic */ Object get(int i) {
                    return b(i);
                }
            };
        }

        public boolean contains(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            obj = entry.getValue();
            if (!(obj == null || obj.equals(this.a.get(key)) == null)) {
                z = true;
            }
            return z;
        }

        public int size() {
            return this.d;
        }

        public /* synthetic */ Iterator iterator() {
            return a();
        }
    }

    /* compiled from: IMASDK */
    static final class b<K> extends lg<K> {
        private final transient lf<K, ?> a;
        private final transient le<K> b;

        b(lf<K, ?> lfVar, le<K> leVar) {
            this.a = lfVar;
            this.b = leVar;
        }

        boolean c() {
            return true;
        }

        public lr<K> a() {
            return b().a();
        }

        int a(Object[] objArr, int i) {
            return b().a(objArr, i);
        }

        public le<K> b() {
            return this.b;
        }

        public boolean contains(Object obj) {
            return this.a.get(obj) != null ? true : null;
        }

        public int size() {
            return this.a.size();
        }

        public /* synthetic */ Iterator iterator() {
            return a();
        }
    }

    /* compiled from: IMASDK */
    static final class c extends le<Object> {
        private final transient Object[] a;
        private final transient int b;
        private final transient int c;

        c(Object[] objArr, int i, int i2) {
            this.a = objArr;
            this.b = i;
            this.c = i2;
        }

        boolean c() {
            return true;
        }

        public Object get(int i) {
            kv.a(i, this.c);
            return this.a[(i * 2) + this.b];
        }

        public int size() {
            return this.c;
        }
    }

    static <K, V> lo<K, V> a(int i, Object[] objArr) {
        if (i == 0) {
            return (lo) b;
        }
        if (i == 1) {
            kz.a(objArr[0], objArr[1]);
            return new lo(null, objArr, 1);
        }
        kv.b(i, objArr.length >> 1);
        return new lo(a(objArr, i, lg.a(i), 0), objArr, i);
    }

    static int[] a(Object[] objArr, int i, int i2, int i3) {
        if (i == 1) {
            kz.a(objArr[i3], objArr[i3 ^ 1]);
            return null;
        }
        int i4 = i2 - 1;
        i2 = new int[i2];
        Arrays.fill(i2, -1);
        for (int i5 = 0; i5 < i; i5++) {
            int i6 = i5 * 2;
            int i7 = i6 + i3;
            Object obj = objArr[i7];
            Object obj2 = objArr[i6 + (i3 ^ 1)];
            kz.a(obj, obj2);
            int a = lc.a(obj.hashCode());
            while (true) {
                a &= i4;
                int i8 = i2[a];
                if (i8 == -1) {
                    break;
                } else if (objArr[i8].equals(obj)) {
                    i2 = String.valueOf(obj);
                    i3 = String.valueOf(obj2);
                    String valueOf = String.valueOf(objArr[i8]);
                    objArr = String.valueOf(objArr[1 ^ i8]);
                    StringBuilder stringBuilder = new StringBuilder((((String.valueOf(i2).length() + 39) + String.valueOf(i3).length()) + String.valueOf(valueOf).length()) + String.valueOf(objArr).length());
                    stringBuilder.append("Multiple entries with same key: ");
                    stringBuilder.append(i2);
                    stringBuilder.append("=");
                    stringBuilder.append(i3);
                    stringBuilder.append(" and ");
                    stringBuilder.append(valueOf);
                    stringBuilder.append("=");
                    stringBuilder.append(objArr);
                    throw new IllegalArgumentException(stringBuilder.toString());
                } else {
                    a++;
                }
            }
            i2[a] = i7;
        }
        return i2;
    }

    private lo(int[] iArr, Object[] objArr, int i) {
        this.d = iArr;
        this.c = objArr;
        this.e = i;
    }

    public int size() {
        return this.e;
    }

    public V get(Object obj) {
        return a(this.d, this.c, this.e, 0, obj);
    }

    static Object a(int[] iArr, Object[] objArr, int i, int i2, Object obj) {
        Object obj2 = null;
        if (obj == null) {
            return null;
        }
        if (i == 1) {
            if (objArr[i2].equals(obj) != null) {
                obj2 = objArr[i2 ^ 1];
            }
            return obj2;
        } else if (iArr == null) {
            return null;
        } else {
            i = iArr.length - 1;
            i2 = lc.a(obj.hashCode());
            while (true) {
                i2 &= i;
                int i3 = iArr[i2];
                if (i3 == -1) {
                    return null;
                }
                if (objArr[i3].equals(obj)) {
                    return objArr[i3 ^ 1];
                }
                i2++;
            }
        }
    }

    lg<Entry<K, V>> b() {
        return new a(this, this.c, 0, this.e);
    }

    lg<K> d() {
        return new b(this, new c(this.c, 0, this.e));
    }

    ld<V> f() {
        return new c(this.c, 1, this.e);
    }
}
