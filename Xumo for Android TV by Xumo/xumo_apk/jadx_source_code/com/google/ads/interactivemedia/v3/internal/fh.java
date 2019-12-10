package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
public final class fh {
    private final long[] a;
    private final long[] b;

    public static fh a(fp fpVar) {
        fpVar.d(1);
        int j = fpVar.j() / 18;
        long[] jArr = new long[j];
        long[] jArr2 = new long[j];
        for (int i = 0; i < j; i++) {
            jArr[i] = fpVar.o();
            jArr2[i] = fpVar.o();
            fpVar.d(2);
        }
        return new fh(jArr, jArr2);
    }

    private fh(long[] jArr, long[] jArr2) {
        this.a = jArr;
        this.b = jArr2;
    }

    public cj a(long j, long j2) {
        final long j3 = j2;
        final long j4 = j;
        return new cj(this) {
            final /* synthetic */ fh c;

            public boolean a() {
                return true;
            }

            public long b(long j) {
                return j4 + this.c.b[ft.a(this.c.a, (j * j3) / 1000000, true, true)];
            }
        };
    }
}
