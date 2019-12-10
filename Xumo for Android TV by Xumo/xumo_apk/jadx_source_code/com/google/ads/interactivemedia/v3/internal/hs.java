package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;

/* compiled from: IMASDK */
public final class hs<T> extends gp<T> {
    private final gm<T> a;
    private final ge<T> b;
    private final fz c;
    private final hw<T> d;
    private final gq e;
    private final a f = new a();
    private gp<T> g;

    /* compiled from: IMASDK */
    private final class a implements gd, gl {
        final /* synthetic */ hs a;

        private a(hs hsVar) {
            this.a = hsVar;
        }
    }

    /* compiled from: IMASDK */
    private static final class b implements gq {
        private final hw<?> a;
        private final boolean b;
        private final Class<?> c;
        private final gm<?> d;
        private final ge<?> e;

        b(Object obj, hw<?> hwVar, boolean z, Class<?> cls) {
            boolean z2;
            ge geVar = null;
            this.d = obj instanceof gm ? (gm) obj : null;
            if (obj instanceof ge) {
                geVar = (ge) obj;
            }
            this.e = geVar;
            if (this.d == null) {
                if (this.e == null) {
                    z2 = null;
                    gw.a(z2);
                    this.a = hwVar;
                    this.b = z;
                    this.c = cls;
                }
            }
            z2 = true;
            gw.a(z2);
            this.a = hwVar;
            this.b = z;
            this.c = cls;
        }

        public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
            boolean z;
            if (this.a != null) {
                if (!this.a.equals(hwVar)) {
                    if (!this.b || this.a.b() != hwVar.a()) {
                        z = false;
                    }
                }
                z = true;
            } else {
                z = this.c.isAssignableFrom(hwVar.a());
            }
            return z ? new hs(this.d, this.e, fzVar, hwVar, this) : null;
        }
    }

    public hs(gm<T> gmVar, ge<T> geVar, fz fzVar, hw<T> hwVar, gq gqVar) {
        this.a = gmVar;
        this.b = geVar;
        this.c = fzVar;
        this.d = hwVar;
        this.e = gqVar;
    }

    public T read(hx hxVar) throws IOException {
        if (this.b == null) {
            return a().read(hxVar);
        }
        hxVar = hf.a(hxVar);
        if (hxVar.j()) {
            return null;
        }
        try {
            return this.b.b(hxVar, this.d.b(), this.f);
        } catch (hx hxVar2) {
            throw hxVar2;
        } catch (Throwable e) {
            throw new gj(e);
        }
    }

    public void write(hz hzVar, T t) throws IOException {
        if (this.a == null) {
            a().write(hzVar, t);
        } else if (t == null) {
            hzVar.f();
        } else {
            hf.a(this.a.a(t, this.d.b(), this.f), hzVar);
        }
    }

    private gp<T> a() {
        gp<T> gpVar = this.g;
        if (gpVar != null) {
            return gpVar;
        }
        gpVar = this.c.a(this.e, this.d);
        this.g = gpVar;
        return gpVar;
    }

    public static gq a(hw<?> hwVar, Object obj) {
        return new b(obj, hwVar, false, null);
    }

    public static gq b(hw<?> hwVar, Object obj) {
        return new b(obj, hwVar, hwVar.b() == hwVar.a(), null);
    }
}
