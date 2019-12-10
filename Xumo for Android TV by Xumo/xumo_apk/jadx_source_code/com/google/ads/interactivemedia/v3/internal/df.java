package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;

/* compiled from: IMASDK */
final class df {
    public cx a;
    public long b;
    public long c;
    public int d;
    public int[] e;
    public int[] f;
    public long[] g;
    public boolean[] h;
    public boolean i;
    public boolean[] j;
    public int k;
    public fp l;
    public boolean m;
    public de n;
    public long o;

    df() {
    }

    public void a() {
        this.d = 0;
        this.o = 0;
        this.i = false;
        this.m = false;
        this.n = null;
    }

    public void a(int i) {
        this.d = i;
        if (this.e == null || this.e.length < this.d) {
            i = (i * 125) / 100;
            this.e = new int[i];
            this.f = new int[i];
            this.g = new long[i];
            this.h = new boolean[i];
            this.j = new boolean[i];
        }
    }

    public void b(int i) {
        if (this.l == null || this.l.c() < i) {
            this.l = new fp(i);
        }
        this.k = i;
        this.i = true;
        this.m = true;
    }

    public void a(cd cdVar) throws IOException, InterruptedException {
        cdVar.b(this.l.a, 0, this.k);
        this.l.c(0);
        this.m = false;
    }

    public void a(fp fpVar) {
        fpVar.a(this.l.a, 0, this.k);
        this.l.c(0);
        this.m = false;
    }

    public long c(int i) {
        return this.g[i] + ((long) this.f[i]);
    }
}
