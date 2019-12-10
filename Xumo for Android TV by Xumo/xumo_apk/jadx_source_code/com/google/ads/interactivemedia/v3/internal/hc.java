package com.google.ads.interactivemedia.v3.internal;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: IMASDK */
public final class hc<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean f = (hc.class.desiredAssertionStatus() ^ 1);
    private static final Comparator<Comparable> g = new Comparator<Comparable>() {
        public int a(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return a((Comparable) obj, (Comparable) obj2);
        }
    };
    Comparator<? super K> a;
    d<K, V> b;
    int c;
    int d;
    final d<K, V> e;
    private a h;
    private b i;

    /* compiled from: IMASDK */
    class a extends AbstractSet<Entry<K, V>> {
        final /* synthetic */ hc a;

        a(hc hcVar) {
            this.a = hcVar;
        }

        public int size() {
            return this.a.c;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new c<Entry<K, V>>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                    r1 = r1.a;
                }

                public Entry<K, V> a() {
                    return b();
                }

                public /* synthetic */ Object next() {
                    return a();
                }
            };
        }

        public boolean contains(Object obj) {
            return (!(obj instanceof Entry) || this.a.a((Entry) obj) == null) ? null : true;
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            d a = this.a.a((Entry) obj);
            if (a == null) {
                return false;
            }
            this.a.a(a, true);
            return true;
        }

        public void clear() {
            this.a.clear();
        }
    }

    /* compiled from: IMASDK */
    final class b extends AbstractSet<K> {
        final /* synthetic */ hc a;

        b(hc hcVar) {
            this.a = hcVar;
        }

        public int size() {
            return this.a.c;
        }

        public Iterator<K> iterator() {
            return new c<K>(this) {
                final /* synthetic */ b a;

                {
                    this.a = r1;
                    r1 = r1.a;
                }

                public K next() {
                    return b().f;
                }
            };
        }

        public boolean contains(Object obj) {
            return this.a.containsKey(obj);
        }

        public boolean remove(Object obj) {
            return this.a.b(obj) != null ? true : null;
        }

        public void clear() {
            this.a.clear();
        }
    }

    /* compiled from: IMASDK */
    private abstract class c<T> implements Iterator<T> {
        d<K, V> b = this.e.e.d;
        d<K, V> c = null;
        int d = this.e.d;
        final /* synthetic */ hc e;

        c(hc hcVar) {
            this.e = hcVar;
        }

        public final boolean hasNext() {
            return this.b != this.e.e;
        }

        final d<K, V> b() {
            d<K, V> dVar = this.b;
            if (dVar == this.e.e) {
                throw new NoSuchElementException();
            } else if (this.e.d == this.d) {
                this.b = dVar.d;
                this.c = dVar;
                return dVar;
            } else {
                throw new ConcurrentModificationException();
            }
        }

        public final void remove() {
            if (this.c != null) {
                this.e.a(this.c, true);
                this.c = null;
                this.d = this.e.d;
                return;
            }
            throw new IllegalStateException();
        }
    }

    /* compiled from: IMASDK */
    static final class d<K, V> implements Entry<K, V> {
        d<K, V> a;
        d<K, V> b;
        d<K, V> c;
        d<K, V> d;
        d<K, V> e;
        final K f;
        V g;
        int h;

        d() {
            this.f = null;
            this.e = this;
            this.d = this;
        }

        d(d<K, V> dVar, K k, d<K, V> dVar2, d<K, V> dVar3) {
            this.a = dVar;
            this.f = k;
            this.h = 1;
            this.d = dVar2;
            this.e = dVar3;
            dVar3.d = this;
            dVar2.e = this;
        }

        public K getKey() {
            return this.f;
        }

        public V getValue() {
            return this.g;
        }

        public V setValue(V v) {
            V v2 = this.g;
            this.g = v;
            return v2;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean equals(java.lang.Object r4) {
            /*
            r3 = this;
            r0 = r4 instanceof java.util.Map.Entry;
            r1 = 0;
            if (r0 == 0) goto L_0x0037;
        L_0x0005:
            r4 = (java.util.Map.Entry) r4;
            r0 = r3.f;
            if (r0 != 0) goto L_0x0012;
        L_0x000b:
            r0 = r4.getKey();
            if (r0 != 0) goto L_0x0036;
        L_0x0011:
            goto L_0x001e;
        L_0x0012:
            r0 = r3.f;
            r2 = r4.getKey();
            r0 = r0.equals(r2);
            if (r0 == 0) goto L_0x0036;
        L_0x001e:
            r0 = r3.g;
            if (r0 != 0) goto L_0x0029;
        L_0x0022:
            r4 = r4.getValue();
            if (r4 != 0) goto L_0x0036;
        L_0x0028:
            goto L_0x0035;
        L_0x0029:
            r0 = r3.g;
            r4 = r4.getValue();
            r4 = r0.equals(r4);
            if (r4 == 0) goto L_0x0036;
        L_0x0035:
            r1 = 1;
        L_0x0036:
            return r1;
        L_0x0037:
            return r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.hc.d.equals(java.lang.Object):boolean");
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this.f == null ? 0 : this.f.hashCode();
            if (this.g != null) {
                i = this.g.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.f);
            stringBuilder.append("=");
            stringBuilder.append(this.g);
            return stringBuilder.toString();
        }

        public d<K, V> a() {
            d<K, V> dVar = this;
            for (d<K, V> dVar2 = this.b; dVar2 != null; dVar2 = dVar2.b) {
                dVar = dVar2;
            }
            return dVar;
        }

        public d<K, V> b() {
            d<K, V> dVar = this;
            for (d<K, V> dVar2 = this.c; dVar2 != null; dVar2 = dVar2.c) {
                dVar = dVar2;
            }
            return dVar;
        }
    }

    public hc() {
        this(g);
    }

    public hc(Comparator<? super K> comparator) {
        this.c = 0;
        this.d = 0;
        this.e = new d();
        if (comparator == null) {
            comparator = g;
        }
        this.a = comparator;
    }

    public int size() {
        return this.c;
    }

    public V get(Object obj) {
        obj = a(obj);
        return obj != null ? obj.g : null;
    }

    public boolean containsKey(Object obj) {
        return a(obj) != null ? true : null;
    }

    public V put(K k, V v) {
        if (k != null) {
            k = a((Object) k, true);
            V v2 = k.g;
            k.g = v;
            return v2;
        }
        throw new NullPointerException("key == null");
    }

    public void clear() {
        this.b = null;
        this.c = 0;
        this.d++;
        d dVar = this.e;
        dVar.e = dVar;
        dVar.d = dVar;
    }

    public V remove(Object obj) {
        obj = b(obj);
        return obj != null ? obj.g : null;
    }

    d<K, V> a(K k, boolean z) {
        int compareTo;
        Comparator comparator = this.a;
        d dVar = this.b;
        if (dVar != null) {
            Comparable comparable = comparator == g ? (Comparable) k : null;
            while (true) {
                if (comparable != null) {
                    compareTo = comparable.compareTo(dVar.f);
                } else {
                    compareTo = comparator.compare(k, dVar.f);
                }
                if (compareTo == 0) {
                    return dVar;
                }
                d dVar2 = compareTo < 0 ? dVar.b : dVar.c;
                if (dVar2 == null) {
                    break;
                }
                dVar = dVar2;
            }
        } else {
            compareTo = 0;
        }
        if (!z) {
            return null;
        }
        d<K, V> dVar3;
        z = this.e;
        if (dVar == null) {
            if (comparator == g) {
                if (!(k instanceof Comparable)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(k.getClass().getName());
                    stringBuilder.append(" is not Comparable");
                    throw new ClassCastException(stringBuilder.toString());
                }
            }
            dVar3 = new d(dVar, k, z, z.e);
            this.b = dVar3;
        } else {
            dVar3 = new d(dVar, k, z, z.e);
            if (compareTo < 0) {
                dVar.b = dVar3;
            } else {
                dVar.c = dVar3;
            }
            b(dVar, true);
        }
        this.c += 1;
        this.d += 1;
        return dVar3;
    }

    com.google.ads.interactivemedia.v3.internal.hc.d<K, V> a(java.lang.Object r3) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r2 = this;
        r0 = 0;
        if (r3 == 0) goto L_0x000a;
    L_0x0003:
        r1 = 0;
        r3 = r2.a(r3, r1);	 Catch:{ ClassCastException -> 0x0009 }
        goto L_0x000b;
    L_0x0009:
        return r0;
    L_0x000a:
        r3 = r0;
    L_0x000b:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.hc.a(java.lang.Object):com.google.ads.interactivemedia.v3.internal.hc$d<K, V>");
    }

    d<K, V> a(Entry<?, ?> entry) {
        d<K, V> a = a(entry.getKey());
        entry = (a == null || a(a.g, entry.getValue()) == null) ? null : true;
        return entry != null ? a : null;
    }

    private boolean a(Object obj, Object obj2) {
        if (obj != obj2) {
            if (obj == null || obj.equals(obj2) == null) {
                return null;
            }
        }
        return true;
    }

    void a(d<K, V> dVar, boolean z) {
        if (z) {
            dVar.e.d = dVar.d;
            dVar.d.e = dVar.e;
        }
        d dVar2 = dVar.b;
        d dVar3 = dVar.c;
        d dVar4 = dVar.a;
        int i = 0;
        if (!dVar2 != false || dVar3 == null) {
            if (dVar2 == true) {
                a((d) dVar, dVar2);
                dVar.b = null;
            } else if (dVar3 != null) {
                a((d) dVar, dVar3);
                dVar.c = null;
            } else {
                a((d) dVar, null);
            }
            b(dVar4, false);
            this.c--;
            this.d++;
            return;
        }
        int i2;
        dVar2 = dVar2.h > dVar3.h ? dVar2.b() : dVar3.a();
        a(dVar2, false);
        dVar3 = dVar.b;
        if (dVar3 != null) {
            i2 = dVar3.h;
            dVar2.b = dVar3;
            dVar3.a = dVar2;
            dVar.b = null;
        } else {
            i2 = 0;
        }
        dVar3 = dVar.c;
        if (dVar3 != null) {
            i = dVar3.h;
            dVar2.c = dVar3;
            dVar3.a = dVar2;
            dVar.c = null;
        }
        dVar2.h = Math.max(i2, i) + 1;
        a((d) dVar, dVar2);
    }

    d<K, V> b(Object obj) {
        d a = a(obj);
        if (a != null) {
            a(a, true);
        }
        return a;
    }

    private void a(d<K, V> dVar, d<K, V> dVar2) {
        d dVar3 = dVar.a;
        dVar.a = null;
        if (dVar2 != null) {
            dVar2.a = dVar3;
        }
        if (dVar3 == null) {
            this.b = dVar2;
        } else if (dVar3.b == dVar) {
            dVar3.b = dVar2;
        } else {
            if (!f) {
                if (dVar3.c != dVar) {
                    throw new AssertionError();
                }
            }
            dVar3.c = dVar2;
        }
    }

    private void b(d<K, V> dVar, boolean z) {
        d dVar2;
        while (dVar2 != null) {
            d dVar3 = dVar2.b;
            d dVar4 = dVar2.c;
            int i = 0;
            int i2 = dVar3 != null ? dVar3.h : 0;
            int i3 = dVar4 != null ? dVar4.h : 0;
            int i4 = i2 - i3;
            d dVar5;
            if (i4 == -2) {
                dVar3 = dVar4.b;
                dVar5 = dVar4.c;
                i2 = dVar5 != null ? dVar5.h : 0;
                if (dVar3 != null) {
                    i = dVar3.h;
                }
                i -= i2;
                if (i != -1) {
                    if (i != 0 || z) {
                        if (!f) {
                            if (i != 1) {
                                throw new AssertionError();
                            }
                        }
                        b(dVar4);
                        a(dVar2);
                        if (z) {
                            return;
                        }
                    }
                }
                a(dVar2);
                if (z) {
                    return;
                }
            } else if (i4 == 2) {
                dVar4 = dVar3.b;
                dVar5 = dVar3.c;
                i2 = dVar5 != null ? dVar5.h : 0;
                if (dVar4 != null) {
                    i = dVar4.h;
                }
                i -= i2;
                if (i != 1) {
                    if (i != 0 || z) {
                        if (!f) {
                            if (i != -1) {
                                throw new AssertionError();
                            }
                        }
                        a(dVar3);
                        b(dVar2);
                        if (z) {
                            return;
                        }
                    }
                }
                b(dVar2);
                if (z) {
                    return;
                }
            } else if (i4 == 0) {
                dVar2.h = i2 + 1;
                if (z) {
                    return;
                }
            } else {
                if (!(f || i4 == -1)) {
                    if (i4 != 1) {
                        throw new AssertionError();
                    }
                }
                dVar2.h = Math.max(i2, i3) + 1;
                if (!z) {
                    return;
                }
            }
            dVar2 = dVar2.a;
        }
    }

    private void a(d<K, V> dVar) {
        d dVar2 = dVar.b;
        d dVar3 = dVar.c;
        d dVar4 = dVar3.b;
        d dVar5 = dVar3.c;
        dVar.c = dVar4;
        if (dVar4 != null) {
            dVar4.a = dVar;
        }
        a((d) dVar, dVar3);
        dVar3.b = dVar;
        dVar.a = dVar3;
        int i = 0;
        dVar.h = Math.max(dVar2 != null ? dVar2.h : 0, dVar4 != null ? dVar4.h : 0) + 1;
        dVar = dVar.h;
        if (dVar5 != null) {
            i = dVar5.h;
        }
        dVar3.h = Math.max(dVar, i) + 1;
    }

    private void b(d<K, V> dVar) {
        d dVar2 = dVar.b;
        d dVar3 = dVar.c;
        d dVar4 = dVar2.b;
        d dVar5 = dVar2.c;
        dVar.b = dVar5;
        if (dVar5 != null) {
            dVar5.a = dVar;
        }
        a((d) dVar, dVar2);
        dVar2.c = dVar;
        dVar.a = dVar2;
        int i = 0;
        dVar.h = Math.max(dVar3 != null ? dVar3.h : 0, dVar5 != null ? dVar5.h : 0) + 1;
        dVar = dVar.h;
        if (dVar4 != null) {
            i = dVar4.h;
        }
        dVar2.h = Math.max(dVar, i) + 1;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = this.h;
        if (set != null) {
            return set;
        }
        Set aVar = new a(this);
        this.h = aVar;
        return aVar;
    }

    public Set<K> keySet() {
        Set<K> set = this.i;
        if (set != null) {
            return set;
        }
        Set bVar = new b(this);
        this.i = bVar;
        return bVar;
    }
}
