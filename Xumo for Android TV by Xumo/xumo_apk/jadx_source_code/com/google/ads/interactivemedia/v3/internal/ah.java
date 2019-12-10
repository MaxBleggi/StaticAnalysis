package com.google.ads.interactivemedia.v3.internal;

import android.view.View;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: IMASDK */
public class ah {
    private final HashMap<View, String> a = new HashMap();
    private final HashMap<View, ArrayList<String>> b = new HashMap();
    private final HashSet<View> c = new HashSet();
    private final HashSet<String> d = new HashSet();
    private final HashSet<String> e = new HashSet();
    private boolean f;

    public HashSet<String> a() {
        return this.d;
    }

    public HashSet<String> b() {
        return this.e;
    }

    public void c() {
        p a = p.a();
        if (a != null) {
            for (g gVar : a.c()) {
                View g = gVar.g();
                if (gVar.h()) {
                    if (g == null || !d(g)) {
                        this.e.add(gVar.f());
                    } else {
                        this.d.add(gVar.f());
                        this.a.put(g, gVar.f());
                        a(gVar);
                    }
                }
            }
        }
    }

    private boolean d(View view) {
        if (!view.hasWindowFocus()) {
            return false;
        }
        Collection hashSet = new HashSet();
        while (view != null) {
            if (!ag.d(view)) {
                return false;
            }
            hashSet.add(view);
            view = view.getParent();
            view = view instanceof View ? view : null;
        }
        this.c.addAll(hashSet);
        return true;
    }

    private void a(g gVar) {
        for (ar arVar : gVar.d()) {
            View view = (View) arVar.get();
            if (view != null) {
                a(view, gVar);
            }
        }
    }

    private void a(View view, g gVar) {
        ArrayList arrayList = (ArrayList) this.b.get(view);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.b.put(view, arrayList);
        }
        arrayList.add(gVar.f());
    }

    public void d() {
        this.a.clear();
        this.b.clear();
        this.c.clear();
        this.d.clear();
        this.e.clear();
        this.f = false;
    }

    public void e() {
        this.f = true;
    }

    public String a(View view) {
        if (this.a.size() == 0) {
            return null;
        }
        String str = (String) this.a.get(view);
        if (str != null) {
            this.a.remove(view);
        }
        return str;
    }

    public ArrayList<String> b(View view) {
        if (this.b.size() == 0) {
            return null;
        }
        ArrayList arrayList = (ArrayList) this.b.get(view);
        if (arrayList != null) {
            this.b.remove(view);
            Collections.sort(arrayList);
        }
        return arrayList;
    }

    public ak c(View view) {
        if (this.c.contains(view) != null) {
            return ak.PARENT_VIEW;
        }
        return this.f != null ? ak.OBSTRUCTION_VIEW : ak.UNDERLYING_VIEW;
    }
}
