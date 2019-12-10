package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
public final class by implements cj {
    public final int a;
    public final int[] b;
    public final long[] c;
    public final long[] d;
    public final long[] e;

    public by(int[] iArr, long[] jArr, long[] jArr2, long[] jArr3) {
        this.a = iArr.length;
        this.b = iArr;
        this.c = jArr;
        this.d = jArr2;
        this.e = jArr3;
    }

    public boolean a() {
        return true;
    }

    public int a(long j) {
        return ft.a(this.e, j, true, true);
    }

    public long b(long j) {
        return this.c[a(j)];
    }
}
