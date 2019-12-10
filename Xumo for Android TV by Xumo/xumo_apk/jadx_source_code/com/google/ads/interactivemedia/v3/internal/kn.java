package com.google.ads.interactivemedia.v3.internal;

import java.util.Arrays;

/* compiled from: IMASDK */
public class kn implements fv {
    public boolean a(Class<?> cls) {
        return false;
    }

    public boolean a(fw fwVar) {
        km kmVar = (km) fwVar.a().getAnnotation(km.class);
        return (kmVar == null || Arrays.asList(kmVar.b()).contains(fwVar.b()) == null) ? null : true;
    }
}
