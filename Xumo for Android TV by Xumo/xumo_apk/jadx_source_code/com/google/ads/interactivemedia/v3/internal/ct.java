package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class ct implements a {
    private final long[] a;
    private final long[] b;
    private final long c;

    public static ct a(fm fmVar, fp fpVar, long j, long j2) {
        fm fmVar2 = fmVar;
        fp fpVar2 = fpVar;
        long j3 = j2;
        fpVar2.d(10);
        int m = fpVar.m();
        ct ctVar = null;
        if (m <= 0) {
            return null;
        }
        int i = fmVar2.d;
        long a = ft.a((long) m, 1000000 * ((long) (i >= 32000 ? 1152 : 576)), (long) i);
        m = fpVar.g();
        int g = fpVar.g();
        int g2 = fpVar.g();
        fpVar2.d(2);
        long j4 = j + ((long) fmVar2.c);
        int i2 = m + 1;
        long[] jArr = new long[i2];
        long[] jArr2 = new long[i2];
        jArr[0] = 0;
        jArr2[0] = j4;
        int i3 = 1;
        while (i3 < jArr.length) {
            int f;
            long j5;
            switch (g2) {
                case 1:
                    f = fpVar.f();
                    break;
                case 2:
                    f = fpVar.g();
                    break;
                case 3:
                    f = fpVar.j();
                    break;
                case 4:
                    f = fpVar.s();
                    break;
                default:
                    return ctVar;
            }
            j4 += (long) (f * g);
            long j6 = a;
            jArr[i3] = (((long) i3) * a) / ((long) m);
            if (j3 == -1) {
                j5 = j4;
            } else {
                j5 = Math.min(j3, j4);
            }
            jArr2[i3] = j5;
            i3++;
            a = j6;
            ctVar = null;
        }
        return new ct(jArr, jArr2, a);
    }

    public boolean a() {
        return true;
    }

    private ct(long[] jArr, long[] jArr2, long j) {
        this.a = jArr;
        this.b = jArr2;
        this.c = j;
    }

    public long b(long j) {
        return this.b[ft.a(this.a, j, true, true)];
    }

    public long a(long j) {
        return this.a[ft.a(this.b, j, true, true)];
    }

    public long b() {
        return this.c;
    }
}
