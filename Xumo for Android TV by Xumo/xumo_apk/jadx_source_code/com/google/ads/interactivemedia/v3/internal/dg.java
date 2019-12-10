package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class dg {
    public final int a;
    public final long[] b;
    public final int[] c;
    public final int d;
    public final long[] e;
    public final int[] f;

    dg(long[] jArr, int[] iArr, int i, long[] jArr2, int[] iArr2) {
        boolean z = false;
        fe.a(iArr.length == jArr2.length);
        fe.a(jArr.length == jArr2.length);
        if (iArr2.length == jArr2.length) {
            z = true;
        }
        fe.a(z);
        this.b = jArr;
        this.c = iArr;
        this.d = i;
        this.e = jArr2;
        this.f = iArr2;
        this.a = jArr.length;
    }

    public int a(long j) {
        for (j = ft.a(this.e, j, true, false); j >= null; j--) {
            if ((this.f[j] & 1) != 0) {
                return j;
            }
        }
        return -1;
    }

    public int b(long j) {
        for (j = ft.b(this.e, j, true, false); j < this.e.length; j++) {
            if ((this.f[j] & 1) != 0) {
                return j;
            }
        }
        return -1;
    }
}
