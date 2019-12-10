package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/* compiled from: IMASDK */
public final class fz {
    private static final hw<?> a = new hw<Object>() {
    };
    private final ThreadLocal<Map<hw<?>, a<?>>> b;
    private final Map<hw<?>, gp<?>> c;
    private final List<gq> d;
    private final gy e;
    private final gz f;
    private final fy g;
    private final boolean h;
    private final boolean i;
    private final boolean j;
    private final boolean k;
    private final boolean l;
    private final hk m;

    /* compiled from: IMASDK */
    static class a<T> extends gp<T> {
        private gp<T> a;

        a() {
        }

        public void a(gp<T> gpVar) {
            if (this.a == null) {
                this.a = gpVar;
                return;
            }
            throw new AssertionError();
        }

        public T read(hx hxVar) throws IOException {
            if (this.a != null) {
                return this.a.read(hxVar);
            }
            throw new IllegalStateException();
        }

        public void write(hz hzVar, T t) throws IOException {
            if (this.a != null) {
                this.a.write(hzVar, t);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public fz() {
        this(gz.a, fx.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, false, go.DEFAULT, Collections.emptyList());
    }

    fz(gz gzVar, fy fyVar, Map<Type, gb<?>> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, go goVar, List<gq> list) {
        this.b = new ThreadLocal();
        this.c = new ConcurrentHashMap();
        this.e = new gy(map);
        this.f = gzVar;
        this.g = fyVar;
        this.h = z;
        this.j = z3;
        this.i = z4;
        this.k = z5;
        this.l = z6;
        map = new ArrayList();
        map.add(hu.Y);
        map.add(ho.a);
        map.add(gzVar);
        map.addAll(list);
        map.add(hu.D);
        map.add(hu.m);
        map.add(hu.g);
        map.add(hu.i);
        map.add(hu.k);
        gp a = a(goVar);
        map.add(hu.a(Long.TYPE, Long.class, a));
        map.add(hu.a(Double.TYPE, Double.class, a(z7)));
        map.add(hu.a(Float.TYPE, Float.class, b(z7)));
        map.add(hu.x);
        map.add(hu.o);
        map.add(hu.q);
        map.add(hu.a(AtomicLong.class, a(a)));
        map.add(hu.a(AtomicLongArray.class, b(a)));
        map.add(hu.s);
        map.add(hu.z);
        map.add(hu.F);
        map.add(hu.H);
        map.add(hu.a(BigDecimal.class, hu.B));
        map.add(hu.a(BigInteger.class, hu.C));
        map.add(hu.J);
        map.add(hu.L);
        map.add(hu.P);
        map.add(hu.R);
        map.add(hu.W);
        map.add(hu.N);
        map.add(hu.d);
        map.add(hj.a);
        map.add(hu.U);
        map.add(hr.a);
        map.add(hq.a);
        map.add(hu.S);
        map.add(hh.a);
        map.add(hu.b);
        map.add(new hi(this.e));
        map.add(new hn(this.e, z2));
        this.m = new hk(this.e);
        map.add(this.m);
        map.add(hu.Z);
        map.add(new hp(this.e, fyVar, gzVar, this.m));
        this.d = Collections.unmodifiableList(map);
    }

    private gp<Number> a(boolean z) {
        if (z) {
            return hu.v;
        }
        return new gp<Number>(this) {
            final /* synthetic */ fz a;

            {
                this.a = r1;
            }

            public Double a(hx hxVar) throws IOException {
                if (hxVar.f() != hy.NULL) {
                    return Double.valueOf(hxVar.k());
                }
                hxVar.j();
                return null;
            }

            public void a(hz hzVar, Number number) throws IOException {
                if (number == null) {
                    hzVar.f();
                    return;
                }
                fz.a(number.doubleValue());
                hzVar.a(number);
            }

            public /* synthetic */ Object read(hx hxVar) throws IOException {
                return a(hxVar);
            }

            public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
                a(hzVar, (Number) obj);
            }
        };
    }

    private gp<Number> b(boolean z) {
        if (z) {
            return hu.u;
        }
        return new gp<Number>(this) {
            final /* synthetic */ fz a;

            {
                this.a = r1;
            }

            public Float a(hx hxVar) throws IOException {
                if (hxVar.f() != hy.NULL) {
                    return Float.valueOf((float) hxVar.k());
                }
                hxVar.j();
                return null;
            }

            public void a(hz hzVar, Number number) throws IOException {
                if (number == null) {
                    hzVar.f();
                    return;
                }
                fz.a((double) number.floatValue());
                hzVar.a(number);
            }

            public /* synthetic */ Object read(hx hxVar) throws IOException {
                return a(hxVar);
            }

            public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
                a(hzVar, (Number) obj);
            }
        };
    }

    static void a(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(d);
            stringBuilder.append(" is not a valid double value as per JSON specification. To override this");
            stringBuilder.append(" behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    private static gp<Number> a(go goVar) {
        if (goVar == go.DEFAULT) {
            return hu.t;
        }
        return new gp<Number>() {
            public Number a(hx hxVar) throws IOException {
                if (hxVar.f() != hy.NULL) {
                    return Long.valueOf(hxVar.l());
                }
                hxVar.j();
                return null;
            }

            public void a(hz hzVar, Number number) throws IOException {
                if (number == null) {
                    hzVar.f();
                } else {
                    hzVar.b(number.toString());
                }
            }

            public /* synthetic */ Object read(hx hxVar) throws IOException {
                return a(hxVar);
            }

            public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
                a(hzVar, (Number) obj);
            }
        };
    }

    private static gp<AtomicLong> a(final gp<Number> gpVar) {
        return new gp<AtomicLong>() {
            public void a(hz hzVar, AtomicLong atomicLong) throws IOException {
                gpVar.write(hzVar, Long.valueOf(atomicLong.get()));
            }

            public AtomicLong a(hx hxVar) throws IOException {
                return new AtomicLong(((Number) gpVar.read(hxVar)).longValue());
            }

            public /* synthetic */ Object read(hx hxVar) throws IOException {
                return a(hxVar);
            }

            public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
                a(hzVar, (AtomicLong) obj);
            }
        }.nullSafe();
    }

    private static gp<AtomicLongArray> b(final gp<Number> gpVar) {
        return new gp<AtomicLongArray>() {
            public void a(hz hzVar, AtomicLongArray atomicLongArray) throws IOException {
                hzVar.b();
                int length = atomicLongArray.length();
                for (int i = 0; i < length; i++) {
                    gpVar.write(hzVar, Long.valueOf(atomicLongArray.get(i)));
                }
                hzVar.c();
            }

            public AtomicLongArray a(hx hxVar) throws IOException {
                List arrayList = new ArrayList();
                hxVar.a();
                while (hxVar.e()) {
                    arrayList.add(Long.valueOf(((Number) gpVar.read(hxVar)).longValue()));
                }
                hxVar.b();
                hxVar = arrayList.size();
                AtomicLongArray atomicLongArray = new AtomicLongArray(hxVar);
                for (int i = 0; i < hxVar; i++) {
                    atomicLongArray.set(i, ((Long) arrayList.get(i)).longValue());
                }
                return atomicLongArray;
            }

            public /* synthetic */ Object read(hx hxVar) throws IOException {
                return a(hxVar);
            }

            public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
                a(hzVar, (AtomicLongArray) obj);
            }
        }.nullSafe();
    }

    public <T> gp<T> a(hw<T> hwVar) {
        gp<T> gpVar = (gp) this.c.get(hwVar == null ? a : hwVar);
        if (gpVar != null) {
            return gpVar;
        }
        Map map = (Map) this.b.get();
        Object obj = null;
        if (map == null) {
            map = new HashMap();
            this.b.set(map);
            obj = 1;
        }
        a aVar = (a) map.get(hwVar);
        if (aVar != null) {
            return aVar;
        }
        try {
            gp<T> hasNext;
            aVar = new a();
            map.put(hwVar, aVar);
            Iterator it = this.d.iterator();
            while (true) {
                hasNext = it.hasNext();
                if (hasNext != null) {
                    hasNext = ((gq) it.next()).a(this, hwVar);
                    if (hasNext != null) {
                        break;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("GSON cannot handle ");
                stringBuilder.append(hwVar);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            aVar.a(hasNext);
            this.c.put(hwVar, hasNext);
            return hasNext;
        } finally {
            map.remove(hwVar);
            if (obj != null) {
                this.b.remove();
            }
        }
    }

    public <T> gp<T> a(gq gqVar, hw<T> hwVar) {
        if (!this.d.contains(gqVar)) {
            gqVar = this.m;
        }
        Object obj = null;
        for (gq gqVar2 : this.d) {
            if (obj != null) {
                gp<T> a = gqVar2.a(this, hwVar);
                if (a != null) {
                    return a;
                }
            } else if (gqVar2 == gqVar) {
                obj = 1;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GSON cannot serialize ");
        stringBuilder.append(hwVar);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public <T> gp<T> a(Class<T> cls) {
        return a(hw.b(cls));
    }

    public String a(Object obj) {
        if (obj == null) {
            return a(gh.a);
        }
        return a(obj, obj.getClass());
    }

    public String a(Object obj, Type type) {
        Appendable stringWriter = new StringWriter();
        a(obj, type, stringWriter);
        return stringWriter.toString();
    }

    public void a(Object obj, Type type, Appendable appendable) throws gg {
        try {
            a(obj, type, a(hf.a(appendable)));
        } catch (Throwable e) {
            throw new gg(e);
        }
    }

    public void a(Object obj, Type type, hz hzVar) throws gg {
        type = a(hw.a(type));
        boolean g = hzVar.g();
        hzVar.b(true);
        boolean h = hzVar.h();
        hzVar.c(this.i);
        boolean i = hzVar.i();
        hzVar.d(this.h);
        try {
            type.write(hzVar, obj);
            hzVar.b(g);
            hzVar.c(h);
            hzVar.d(i);
        } catch (Throwable e) {
            throw new gg(e);
        } catch (Throwable th) {
            hzVar.b(g);
            hzVar.c(h);
            hzVar.d(i);
        }
    }

    public String a(gf gfVar) {
        Appendable stringWriter = new StringWriter();
        a(gfVar, stringWriter);
        return stringWriter.toString();
    }

    public void a(gf gfVar, Appendable appendable) throws gg {
        try {
            a(gfVar, a(hf.a(appendable)));
        } catch (Throwable e) {
            throw new gg(e);
        }
    }

    public hz a(Writer writer) throws IOException {
        if (this.j) {
            writer.write(")]}'\n");
        }
        hz hzVar = new hz(writer);
        if (this.k != null) {
            hzVar.c("  ");
        }
        hzVar.d(this.h);
        return hzVar;
    }

    public hx a(Reader reader) {
        hx hxVar = new hx(reader);
        hxVar.a(this.l);
        return hxVar;
    }

    public void a(gf gfVar, hz hzVar) throws gg {
        boolean g = hzVar.g();
        hzVar.b(true);
        boolean h = hzVar.h();
        hzVar.c(this.i);
        boolean i = hzVar.i();
        hzVar.d(this.h);
        try {
            hf.a(gfVar, hzVar);
            hzVar.b(g);
            hzVar.c(h);
            hzVar.d(i);
        } catch (Throwable e) {
            throw new gg(e);
        } catch (Throwable th) {
            hzVar.b(g);
            hzVar.c(h);
            hzVar.d(i);
        }
    }

    public <T> T a(String str, Class<T> cls) throws gn {
        return he.a((Class) cls).cast(a(str, (Type) cls));
    }

    public <T> T a(String str, Type type) throws gn {
        if (str == null) {
            return null;
        }
        return a(new StringReader(str), type);
    }

    public <T> T a(Reader reader, Type type) throws gg, gn {
        hx a = a(reader);
        Object a2 = a(a, type);
        a(a2, a);
        return a2;
    }

    private static void a(Object obj, hx hxVar) {
        if (obj != null) {
            try {
                if (hxVar.f() != hy.END_DOCUMENT) {
                    throw new gg("JSON document was not fully consumed.");
                }
            } catch (Throwable e) {
                throw new gn(e);
            } catch (Throwable e2) {
                throw new gg(e2);
            }
        }
    }

    public <T> T a(hx hxVar, Type type) throws gg, gn {
        boolean q = hxVar.q();
        boolean z = true;
        hxVar.a(true);
        try {
            hxVar.f();
            z = false;
            type = a(hw.a(type)).read(hxVar);
            hxVar.a(q);
            return type;
        } catch (Throwable e) {
            if (z) {
                hxVar.a(q);
                return null;
            }
            throw new gn(e);
        } catch (Throwable e2) {
            throw new gn(e2);
        } catch (Throwable e22) {
            throw new gn(e22);
        } catch (Throwable th) {
            hxVar.a(q);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{serializeNulls:");
        stringBuilder.append(this.h);
        stringBuilder.append("factories:");
        stringBuilder.append(this.d);
        stringBuilder.append(",instanceCreators:");
        stringBuilder.append(this.e);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
