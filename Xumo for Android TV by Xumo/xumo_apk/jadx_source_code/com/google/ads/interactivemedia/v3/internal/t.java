package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.os.Handler;
import com.google.ads.interactivemedia.v3.internal.q.a;

/* compiled from: IMASDK */
public class t implements m, a {
    private static t a;
    private float b = 0.0f;
    private final o c;
    private final l d;
    private n e;
    private p f;

    public t(o oVar, l lVar) {
        this.c = oVar;
        this.d = lVar;
    }

    public static t a() {
        if (a == null) {
            a = new t(new o(), new l());
        }
        return a;
    }

    public void a(Context context) {
        this.e = this.c.a(new Handler(), context, this.d.a(), this);
    }

    public void b() {
        q.a().a((a) this);
        q.a().b();
        if (q.a().d()) {
            aj.a().b();
        }
        this.e.a();
    }

    public void c() {
        aj.a().c();
        q.a().c();
        this.e.b();
    }

    public void a(boolean z) {
        if (z) {
            aj.a().b();
        } else {
            aj.a().d();
        }
    }

    public void a(float f) {
        this.b = f;
        for (g e : e().c()) {
            e.e().a(f);
        }
    }

    private p e() {
        if (this.f == null) {
            this.f = p.a();
        }
        return this.f;
    }

    public float d() {
        return this.b;
    }
}
