package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/* compiled from: IMASDK */
public final class hi implements gq {
    private final gy a;

    /* compiled from: IMASDK */
    private static final class a<E> extends gp<Collection<E>> {
        private final gp<E> a;
        private final hd<? extends Collection<E>> b;

        public a(fz fzVar, Type type, gp<E> gpVar, hd<? extends Collection<E>> hdVar) {
            this.a = new ht(fzVar, gpVar, type);
            this.b = hdVar;
        }

        public Collection<E> a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            Collection<E> collection = (Collection) this.b.a();
            hxVar.a();
            while (hxVar.e()) {
                collection.add(this.a.read(hxVar));
            }
            hxVar.b();
            return collection;
        }

        public void a(hz hzVar, Collection<E> collection) throws IOException {
            if (collection == null) {
                hzVar.f();
                return;
            }
            hzVar.b();
            for (E write : collection) {
                this.a.write(hzVar, write);
            }
            hzVar.c();
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Collection) obj);
        }
    }

    public hi(gy gyVar) {
        this.a = gyVar;
    }

    public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
        Type b = hwVar.b();
        Class a = hwVar.a();
        if (!Collection.class.isAssignableFrom(a)) {
            return null;
        }
        b = gx.a(b, a);
        return new a(fzVar, b, fzVar.a(hw.a(b)), this.a.a((hw) hwVar));
    }
}
