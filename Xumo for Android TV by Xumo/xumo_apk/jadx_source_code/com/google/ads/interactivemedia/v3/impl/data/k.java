package com.google.ads.interactivemedia.v3.impl.data;

import com.google.ads.interactivemedia.v3.internal.km;

@km(a = g.class)
/* compiled from: IMASDK */
public abstract class k {
    public abstract double end();

    public abstract boolean played();

    public abstract double start();

    private static k create(double d, double d2, boolean z) {
        return new g(d, d2, z);
    }
}
