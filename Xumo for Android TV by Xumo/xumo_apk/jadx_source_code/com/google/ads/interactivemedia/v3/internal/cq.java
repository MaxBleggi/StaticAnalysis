package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class cq implements a {
    private final long a;
    private final int b;
    private final long c;

    public cq(long j, int i, long j2) {
        this.a = j;
        this.b = i;
        j = -1;
        if (j2 != -1) {
            j = a(j2);
        }
        this.c = j;
    }

    public boolean a() {
        return this.c != -1;
    }

    public long b(long j) {
        if (this.c == -1) {
            return 0;
        }
        return ((j * ((long) this.b)) / 8000000) + this.a;
    }

    public long a(long j) {
        return ((Math.max(0, j - this.a) * 1000000) * 8) / ((long) this.b);
    }

    public long b() {
        return this.c;
    }
}
