package com.google.ads.interactivemedia.v3.internal;

import android.view.View;

/* compiled from: IMASDK */
public abstract class c {
    public abstract void a();

    public abstract void a(View view);

    public abstract void b();

    public static c a(d dVar, e eVar) {
        af.a();
        af.a((Object) dVar, "AdSessionConfiguration is null");
        af.a((Object) eVar, "AdSessionContext is null");
        return new g(dVar, eVar);
    }
}
