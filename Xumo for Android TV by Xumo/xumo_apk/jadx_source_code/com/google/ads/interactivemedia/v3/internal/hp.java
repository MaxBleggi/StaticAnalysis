package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: IMASDK */
public final class hp implements gq {
    private final gy a;
    private final fy b;
    private final gz c;
    private final hk d;

    /* compiled from: IMASDK */
    static abstract class b {
        final String h;
        final boolean i;
        final boolean j;

        protected b(String str, boolean z, boolean z2) {
            this.h = str;
            this.i = z;
            this.j = z2;
        }

        abstract void a(hx hxVar, Object obj) throws IOException, IllegalAccessException;

        abstract void a(hz hzVar, Object obj) throws IOException, IllegalAccessException;

        abstract boolean a(Object obj) throws IOException, IllegalAccessException;
    }

    /* compiled from: IMASDK */
    public static final class a<T> extends gp<T> {
        private final hd<T> a;
        private final Map<String, b> b;

        a(hd<T> hdVar, Map<String, b> map) {
            this.a = hdVar;
            this.b = map;
        }

        public T read(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            Object a = this.a.a();
            try {
                hxVar.c();
                while (hxVar.e()) {
                    b bVar = (b) this.b.get(hxVar.g());
                    if (bVar != null) {
                        if (bVar.j) {
                            bVar.a(hxVar, a);
                        }
                    }
                    hxVar.n();
                }
                hxVar.d();
                return a;
            } catch (Throwable e) {
                throw new gn(e);
            } catch (hx hxVar2) {
                throw new AssertionError(hxVar2);
            }
        }

        public void write(hz hzVar, T t) throws IOException {
            if (t == null) {
                hzVar.f();
                return;
            }
            hzVar.d();
            try {
                for (b bVar : this.b.values()) {
                    if (bVar.a(t)) {
                        hzVar.a(bVar.h);
                        bVar.a(hzVar, (Object) t);
                    }
                }
                hzVar.e();
            } catch (hz hzVar2) {
                throw new AssertionError(hzVar2);
            }
        }
    }

    public hp(gy gyVar, fy fyVar, gz gzVar, hk hkVar) {
        this.a = gyVar;
        this.b = fyVar;
        this.c = gzVar;
        this.d = hkVar;
    }

    public boolean a(Field field, boolean z) {
        return a(field, z, this.c);
    }

    static boolean a(Field field, boolean z, gz gzVar) {
        return (gzVar.a(field.getType(), z) || gzVar.a(field, z) != null) ? null : true;
    }

    private List<String> a(Field field) {
        gt gtVar = (gt) field.getAnnotation(gt.class);
        if (gtVar == null) {
            return Collections.singletonList(this.b.a(field));
        }
        field = gtVar.a();
        String[] b = gtVar.b();
        if (b.length == 0) {
            return Collections.singletonList(field);
        }
        List<String> arrayList = new ArrayList(b.length + 1);
        arrayList.add(field);
        for (Object add : b) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
        Class a = hwVar.a();
        if (Object.class.isAssignableFrom(a)) {
            return new a(this.a.a((hw) hwVar), a(fzVar, (hw) hwVar, a));
        }
        return null;
    }

    private b a(fz fzVar, Field field, String str, hw<?> hwVar, boolean z, boolean z2) {
        hp hpVar = this;
        fz fzVar2 = fzVar;
        hw hwVar2 = hwVar;
        final boolean a = he.a(hwVar.a());
        Field field2 = field;
        gs gsVar = (gs) field.getAnnotation(gs.class);
        gp a2 = gsVar != null ? hpVar.d.a(hpVar.a, fzVar, hwVar2, gsVar) : null;
        final boolean z3 = a2 != null;
        if (a2 == null) {
            a2 = fzVar.a(hwVar2);
        }
        final gp gpVar = a2;
        field2 = field;
        fzVar2 = fzVar;
        final hw<?> hwVar3 = hwVar;
        return new b(this, str, z, z2) {
            final /* synthetic */ hp g;

            void a(hz hzVar, Object obj) throws IOException, IllegalAccessException {
                gp gpVar;
                obj = field2.get(obj);
                if (z3) {
                    gpVar = gpVar;
                } else {
                    gpVar = new ht(fzVar2, gpVar, hwVar3.b());
                }
                gpVar.write(hzVar, obj);
            }

            void a(hx hxVar, Object obj) throws IOException, IllegalAccessException {
                hxVar = gpVar.read(hxVar);
                if (hxVar != null || !a) {
                    field2.set(obj, hxVar);
                }
            }

            public boolean a(Object obj) throws IOException, IllegalAccessException {
                boolean z = false;
                if (!this.i) {
                    return false;
                }
                if (field2.get(obj) != obj) {
                    z = true;
                }
                return z;
            }
        };
    }

    private Map<String, b> a(fz fzVar, hw<?> hwVar, Class<?> cls) {
        hp hpVar = this;
        Map<String, b> linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type b = hwVar.b();
        hw hwVar2 = hwVar;
        Class cls2 = cls;
        while (cls2 != Object.class) {
            Field[] declaredFields = cls2.getDeclaredFields();
            int length = declaredFields.length;
            boolean z = false;
            int i = 0;
            while (i < length) {
                Field field = declaredFields[i];
                boolean a = a(field, true);
                boolean a2 = a(field, z);
                if (a || a2) {
                    b bVar;
                    field.setAccessible(true);
                    Type a3 = gx.a(hwVar2.b(), cls2, field.getGenericType());
                    List a4 = a(field);
                    b bVar2 = null;
                    int i2 = 0;
                    while (i2 < a4.size()) {
                        String str = (String) a4.get(i2);
                        boolean z2 = i2 != 0 ? false : a;
                        String str2 = str;
                        bVar = bVar2;
                        int i3 = i2;
                        List list = a4;
                        Field field2 = field;
                        bVar2 = bVar == null ? (b) linkedHashMap.put(str2, a(fzVar, field, str2, hw.a(a3), z2, a2)) : bVar;
                        i2 = i3 + 1;
                        a = z2;
                        a4 = list;
                        field = field2;
                    }
                    bVar = bVar2;
                    if (bVar != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(b);
                        stringBuilder.append(" declares multiple JSON fields named ");
                        stringBuilder.append(bVar.h);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                }
                i++;
                z = false;
            }
            hwVar2 = hw.a(gx.a(hwVar2.b(), cls2, cls2.getGenericSuperclass()));
            cls2 = hwVar2.a();
        }
        return linkedHashMap;
    }
}
