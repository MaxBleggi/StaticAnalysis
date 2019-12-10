package com.google.ads.interactivemedia.v3.internal;

import android.util.Pair;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;
import java.util.Arrays;
import java.util.Collections;

/* compiled from: IMASDK */
final class dv extends du {
    private static final double[] b = new double[]{23.976023976023978d, 24.0d, 25.0d, 29.97002997002997d, 30.0d, 50.0d, 59.94005994005994d, 60.0d};
    private boolean c;
    private long d;
    private final boolean[] e = new boolean[4];
    private final a f = new a(128);
    private boolean g;
    private long h;
    private long i;
    private boolean j;
    private boolean k;
    private long l;
    private long m;

    /* compiled from: IMASDK */
    private static final class a {
        public int a;
        public int b;
        public byte[] c;
        private boolean d;

        public a(int i) {
            this.c = new byte[i];
        }

        public void a() {
            this.d = false;
            this.a = 0;
            this.b = 0;
        }

        public boolean a(int i, int i2) {
            if (this.d) {
                if (this.b == 0 && i == 181) {
                    this.b = this.a;
                } else {
                    this.a -= i2;
                    this.d = false;
                    return true;
                }
            } else if (i == 179) {
                this.d = true;
            }
            return false;
        }

        public void a(byte[] bArr, int i, int i2) {
            if (this.d) {
                i2 -= i;
                if (this.c.length < this.a + i2) {
                    this.c = Arrays.copyOf(this.c, (this.a + i2) * 2);
                }
                System.arraycopy(bArr, i, this.c, this.a, i2);
                this.a += i2;
            }
        }
    }

    public dv(ck ckVar) {
        super(ckVar);
    }

    public void b() {
    }

    public void a() {
        fn.a(this.e);
        this.f.a();
        this.j = false;
        this.g = false;
        this.h = 0;
    }

    public void a(long j, boolean z) {
        this.j = j != -1;
        if (this.j) {
            this.i = j;
        }
    }

    public void a(fp fpVar) {
        dv dvVar = this;
        fp fpVar2 = fpVar;
        if (fpVar.b() > 0) {
            int d = fpVar.d();
            int c = fpVar.c();
            byte[] bArr = fpVar2.a;
            dvVar.h += (long) fpVar.b();
            dvVar.a.a(fpVar2, fpVar.b());
            int i = d;
            while (true) {
                d = fn.a(bArr, d, c, dvVar.e);
                if (d == c) {
                    break;
                }
                int i2;
                int i3 = d + 3;
                int i4 = fpVar2.a[i3] & 255;
                if (!dvVar.c) {
                    i2 = d - i;
                    if (i2 > 0) {
                        dvVar.f.a(bArr, i, d);
                    }
                    if (dvVar.f.a(i4, i2 < 0 ? -i2 : 0)) {
                        Pair a = a(dvVar.f);
                        dvVar.a.a((bj) a.first);
                        dvVar.d = ((Long) a.second).longValue();
                        dvVar.c = true;
                    }
                }
                if (dvVar.c && (i4 == 184 || i4 == 0)) {
                    i2 = c - d;
                    if (dvVar.g) {
                        int i5 = i4;
                        dvVar.a.a(dvVar.m, dvVar.k, ((int) (dvVar.h - dvVar.l)) - i2, i2, null);
                        dvVar.k = false;
                        i = i5;
                    } else {
                        i = i4;
                    }
                    if (i == 184) {
                        dvVar.g = false;
                        dvVar.k = true;
                    } else {
                        dvVar.m = dvVar.j ? dvVar.i : dvVar.m + dvVar.d;
                        dvVar.l = dvVar.h - ((long) i2);
                        dvVar.j = false;
                        dvVar.g = true;
                    }
                }
                i = d;
                d = i3;
            }
            if (!dvVar.c) {
                dvVar.f.a(bArr, i, c);
            }
        }
    }

    private static Pair<bj, Long> a(a aVar) {
        float f;
        float f2;
        a aVar2 = aVar;
        Object copyOf = Arrays.copyOf(aVar2.c, aVar2.a);
        int i = copyOf[5] & 255;
        int i2 = ((copyOf[4] & 255) << 4) | (i >> 4);
        int i3 = ((i & 15) << 8) | (copyOf[6] & 255);
        switch ((copyOf[7] & PsExtractor.VIDEO_STREAM_MASK) >> 4) {
            case 2:
                f = ((float) (i3 * 4)) / ((float) (i2 * 3));
                break;
            case 3:
                f = ((float) (i3 * 16)) / ((float) (i2 * 9));
                break;
            case 4:
                f = ((float) (i3 * 121)) / ((float) (i2 * 100));
                break;
            default:
                f2 = 1.0f;
                break;
        }
        f2 = f;
        bj a = bj.a(null, MimeTypes.VIDEO_MPEG2, -1, -1, -1, i2, i3, Collections.singletonList(copyOf), -1, f2);
        long j = 0;
        int i4 = (copyOf[7] & 15) - 1;
        if (i4 >= 0 && i4 < b.length) {
            double d = b[i4];
            int i5 = aVar2.b + 9;
            i4 = (copyOf[i5] & 96) >> 5;
            i5 = copyOf[i5] & 31;
            if (i4 != i5) {
                double d2 = (double) i4;
                Double.isNaN(d2);
                d2 += 1.0d;
                double d3 = (double) (i5 + 1);
                Double.isNaN(d3);
                d *= d2 / d3;
            }
            j = (long) (1000000.0d / d);
        }
        return Pair.create(a, Long.valueOf(j));
    }
}
