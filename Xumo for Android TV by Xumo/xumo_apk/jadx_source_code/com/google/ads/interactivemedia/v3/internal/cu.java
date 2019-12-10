package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class cu implements a {
    private final long a;
    private final long b;
    private final long c;
    private final long[] d;
    private final long e;
    private final int g;

    public static cu a(fm fmVar, fp fpVar, long j, long j2) {
        fm fmVar2 = fmVar;
        int i = fmVar2.g;
        int i2 = fmVar2.d;
        long j3 = j + ((long) fmVar2.c);
        int m = fpVar.m();
        if ((m & 1) == 1) {
            int s = fpVar.s();
            if (s != 0) {
                long a = ft.a((long) s, ((long) i) * 1000000, (long) i2);
                if ((m & 6) != 6) {
                    return new cu(j3, a, j2);
                }
                long s2 = (long) fpVar.s();
                fpVar.d(1);
                long[] jArr = new long[99];
                for (m = 0; m < 99; m++) {
                    jArr[m] = (long) fpVar.f();
                }
                return new cu(j3, a, j2, jArr, s2, fmVar2.c);
            }
        }
        return null;
    }

    private cu(long j, long j2, long j3) {
        this(j, j2, j3, null, 0, 0);
    }

    private cu(long j, long j2, long j3, long[] jArr, long j4, int i) {
        this.a = j;
        this.b = j2;
        this.c = j3;
        this.d = jArr;
        this.e = j4;
        this.g = i;
    }

    public boolean a() {
        return this.d != null;
    }

    public long b(long j) {
        if (!a()) {
            return this.a;
        }
        long j2;
        j = (((float) j) * 1120403456) / ((float) this.b);
        float f = 256.0f;
        float f2 = 0.0f;
        if (j <= 0) {
            f = 0.0f;
        } else if (j < 1120403456) {
            int i = (int) j;
            if (i != 0) {
                f2 = (float) this.d[i - 1];
            }
            if (i < 99) {
                f = (float) this.d[i];
            }
            f = ((f - f2) * (j - ((float) i))) + f2;
        }
        double d = (double) f;
        Double.isNaN(d);
        d *= 4571153621781053440L;
        j = (double) this.e;
        Double.isNaN(j);
        j = Math.round(d * j) + this.a;
        if (this.c != -1) {
            j2 = this.c - 1;
        } else {
            j2 = ((this.a - ((long) this.g)) + this.e) - 1;
        }
        return Math.min(j, j2);
    }

    public long a(long j) {
        long j2 = 0;
        if (a()) {
            if (j >= this.a) {
                long j3;
                long j4;
                j = (double) (j - this.a);
                Double.isNaN(j);
                j *= 4643211215818981376L;
                double d = (double) this.e;
                Double.isNaN(d);
                j /= d;
                int a = ft.a(this.d, (long) j, true, false) + 1;
                long a2 = a(a);
                if (a == 0) {
                    j3 = 0;
                } else {
                    j3 = this.d[a - 1];
                }
                if (a == 99) {
                    j4 = 256;
                } else {
                    j4 = this.d[a];
                }
                long a3 = a(a + 1);
                if (j4 != j3) {
                    double d2 = (double) (a3 - a2);
                    double d3 = (double) j3;
                    Double.isNaN(d3);
                    j -= d3;
                    Double.isNaN(d2);
                    d2 *= j;
                    j = (double) (j4 - j3);
                    Double.isNaN(j);
                    j2 = (long) (d2 / j);
                }
                return a2 + j2;
            }
        }
        return 0;
    }

    public long b() {
        return this.b;
    }

    private long a(int i) {
        return (this.b * ((long) i)) / 100;
    }
}
