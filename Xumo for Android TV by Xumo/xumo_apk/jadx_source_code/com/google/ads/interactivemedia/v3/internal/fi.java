package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
public final class fi {
    public final int a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;
    public final int f;
    public final int g;
    public final long h;

    public fi(byte[] bArr, int i) {
        fo foVar = new fo(bArr);
        foVar.a(i * 8);
        this.a = foVar.c(16);
        this.b = foVar.c(16);
        this.c = foVar.c(24);
        this.d = foVar.c(24);
        this.e = foVar.c(20);
        this.f = foVar.c(3) + 1;
        this.g = foVar.c(5) + 1;
        this.h = (long) foVar.c(36);
    }

    public int a() {
        return this.g * this.e;
    }

    public long b() {
        return (this.h * 1000000) / ((long) this.e);
    }
}
