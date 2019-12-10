package com.google.ads.interactivemedia.v3.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* compiled from: IMASDK */
public class p {
    private static p a = new p();
    private final ArrayList<g> b = new ArrayList();
    private final ArrayList<g> c = new ArrayList();

    private p() {
    }

    public static p a() {
        return a;
    }

    public void a(g gVar) {
        this.b.add(gVar);
    }

    public void b(g gVar) {
        boolean d = d();
        this.c.add(gVar);
        if (!d) {
            t.a().b();
        }
    }

    public void c(g gVar) {
        boolean d = d();
        this.b.remove(gVar);
        this.c.remove(gVar);
        if (d && d() == null) {
            t.a().c();
        }
    }

    public Collection<g> b() {
        return Collections.unmodifiableCollection(this.b);
    }

    public Collection<g> c() {
        return Collections.unmodifiableCollection(this.c);
    }

    public boolean d() {
        return this.c.size() > 0;
    }
}
