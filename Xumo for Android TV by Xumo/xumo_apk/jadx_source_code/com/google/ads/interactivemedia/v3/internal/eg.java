package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class eg {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private long g;
    private long h;

    public eg(int i, int i2, int i3, int i4, int i5, int i6) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = i5;
        this.f = i6;
    }

    public long a() {
        return ((this.h / ((long) this.d)) * 1000000) / ((long) this.b);
    }

    public int b() {
        return this.d;
    }

    public int c() {
        return (this.b * this.e) * this.a;
    }

    public int d() {
        return this.b;
    }

    public int e() {
        return this.a;
    }

    public long a(long j) {
        return ((((j * ((long) this.c)) / 1000000) / ((long) this.d)) * ((long) this.d)) + this.g;
    }

    public long b(long j) {
        return (j * 1000000) / ((long) this.c);
    }

    public boolean f() {
        return (this.g == 0 || this.h == 0) ? false : true;
    }

    public void a(long j, long j2) {
        this.g = j;
        this.h = j2;
    }

    public int g() {
        return this.f;
    }
}
