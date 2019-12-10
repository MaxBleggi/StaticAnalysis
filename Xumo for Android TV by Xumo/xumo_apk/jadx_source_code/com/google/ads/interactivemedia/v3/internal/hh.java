package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* compiled from: IMASDK */
public final class hh<E> extends gp<Object> {
    public static final gq a = new gq() {
        public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
            hwVar = hwVar.b();
            if (!(hwVar instanceof GenericArrayType) && (!(hwVar instanceof Class) || !((Class) hwVar).isArray())) {
                return null;
            }
            Type g = gx.g(hwVar);
            return new hh(fzVar, fzVar.a(hw.a(g)), gx.e(g));
        }
    };
    private final Class<E> b;
    private final gp<E> c;

    public hh(fz fzVar, gp<E> gpVar, Class<E> cls) {
        this.c = new ht(fzVar, gpVar, cls);
        this.b = cls;
    }

    public Object read(hx hxVar) throws IOException {
        if (hxVar.f() == hy.NULL) {
            hxVar.j();
            return null;
        }
        List arrayList = new ArrayList();
        hxVar.a();
        while (hxVar.e()) {
            arrayList.add(this.c.read(hxVar));
        }
        hxVar.b();
        hxVar = Array.newInstance(this.b, arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Array.set(hxVar, i, arrayList.get(i));
        }
        return hxVar;
    }

    public void write(hz hzVar, Object obj) throws IOException {
        if (obj == null) {
            hzVar.f();
            return;
        }
        hzVar.b();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.c.write(hzVar, Array.get(obj, i));
        }
        hzVar.c();
    }
}
