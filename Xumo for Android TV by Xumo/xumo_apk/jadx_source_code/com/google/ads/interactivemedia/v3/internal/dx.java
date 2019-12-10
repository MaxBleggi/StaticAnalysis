package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import java.util.Collections;

/* compiled from: IMASDK */
final class dx extends du {
    private boolean b;
    private final ed c;
    private final boolean[] d = new boolean[3];
    private final ea e = new ea(32, 128);
    private final ea f = new ea(33, 128);
    private final ea g = new ea(34, 128);
    private final ea h = new ea(39, 128);
    private final ea i = new ea(40, 128);
    private final a j;
    private long k;
    private long l;
    private final fp m;

    /* compiled from: IMASDK */
    private static final class a {
        private final ck a;
        private long b;
        private boolean c;
        private int d;
        private long e;
        private boolean f;
        private boolean g;
        private boolean h;
        private boolean i;
        private boolean j;
        private long k;
        private long l;
        private boolean m;

        public a(ck ckVar) {
            this.a = ckVar;
        }

        public void a() {
            this.f = false;
            this.g = false;
            this.h = false;
            this.i = false;
            this.j = false;
        }

        public void a(long j, int i, int i2, long j2) {
            this.g = false;
            this.h = false;
            this.e = j2;
            this.d = 0;
            this.b = j;
            j = 1;
            if (i2 >= 32) {
                if (!this.j && this.i) {
                    a(i);
                    this.i = false;
                }
                if (i2 <= 34) {
                    this.h = this.j ^ true;
                    this.j = true;
                }
            }
            boolean z = i2 >= 16 && i2 <= 21;
            this.c = z;
            if (!this.c) {
                if (i2 > 9) {
                    j = null;
                }
            }
            this.f = j;
        }

        public void a(byte[] bArr, int i, int i2) {
            if (this.f) {
                int i3 = (i + 2) - this.d;
                if (i3 < i2) {
                    this.g = (bArr[i3] & 128) != null ? 1 : null;
                    this.f = false;
                    return;
                }
                this.d += i2 - i;
            }
        }

        public void a(long j, int i) {
            if (this.j && this.g) {
                this.m = this.c;
                this.j = 0;
            } else if (this.h || this.g) {
                if (this.i) {
                    a(i + ((int) (j - this.b)));
                }
                this.k = this.b;
                this.l = this.e;
                this.i = 1;
                this.m = this.c;
            }
        }

        private void a(int i) {
            this.a.a(this.l, this.m, (int) (this.b - this.k), i, null);
        }
    }

    public dx(ck ckVar, ed edVar) {
        super(ckVar);
        this.c = edVar;
        this.j = new a(ckVar);
        this.m = new fp();
    }

    public void b() {
    }

    public void a() {
        fn.a(this.d);
        this.e.a();
        this.f.a();
        this.g.a();
        this.h.a();
        this.i.a();
        this.j.a();
        this.k = 0;
    }

    public void a(long j, boolean z) {
        this.l = j;
    }

    public void a(fp fpVar) {
        dx dxVar = this;
        fp fpVar2 = fpVar;
        while (fpVar.b() > 0) {
            int d = fpVar.d();
            int c = fpVar.c();
            byte[] bArr = fpVar2.a;
            dxVar.k += (long) fpVar.b();
            dxVar.a.a(fpVar2, fpVar.b());
            while (d < c) {
                int a = fn.a(bArr, d, c, dxVar.d);
                if (a == c) {
                    a(bArr, d, c);
                    return;
                }
                int c2 = fn.c(bArr, a);
                int i = a - d;
                if (i > 0) {
                    a(bArr, d, a);
                }
                int i2 = c - a;
                long j = dxVar.k - ((long) i2);
                int i3 = i < 0 ? -i : 0;
                long j2 = j;
                int i4 = i2;
                b(j2, i4, i3, dxVar.l);
                a(j2, i4, c2, dxVar.l);
                d = a + 3;
            }
        }
    }

    private void a(long j, int i, int i2, long j2) {
        if (this.b) {
            this.j.a(j, i, i2, j2);
        } else {
            this.e.a(i2);
            this.f.a(i2);
            this.g.a(i2);
        }
        this.h.a(i2);
        this.i.a(i2);
    }

    private void a(byte[] bArr, int i, int i2) {
        if (this.b) {
            this.j.a(bArr, i, i2);
        } else {
            this.e.a(bArr, i, i2);
            this.f.a(bArr, i, i2);
            this.g.a(bArr, i, i2);
        }
        this.h.a(bArr, i, i2);
        this.i.a(bArr, i, i2);
    }

    private void b(long j, int i, int i2, long j2) {
        if (this.b) {
            this.j.a(j, i);
        } else {
            this.e.b(i2);
            this.f.b(i2);
            this.g.b(i2);
            if (!(this.e.b() == null || this.f.b() == null || this.g.b() == null)) {
                this.a.a(a(this.e, this.f, this.g));
                this.b = 1;
            }
        }
        if (this.h.b(i2) != null) {
            this.m.a(this.h.a, fn.a(this.h.a, this.h.b));
            this.m.d(5);
            this.c.a(j2, this.m);
        }
        if (this.i.b(i2) != null) {
            this.m.a(this.i.a, fn.a(this.i.a, this.i.b));
            this.m.d(5);
            this.c.a(j2, this.m);
        }
    }

    private static bj a(ea eaVar, ea eaVar2, ea eaVar3) {
        float f;
        ea eaVar4 = eaVar;
        ea eaVar5 = eaVar2;
        ea eaVar6 = eaVar3;
        Object obj = new byte[((eaVar4.b + eaVar5.b) + eaVar6.b)];
        int i = 0;
        System.arraycopy(eaVar4.a, 0, obj, 0, eaVar4.b);
        System.arraycopy(eaVar5.a, 0, obj, eaVar4.b, eaVar5.b);
        System.arraycopy(eaVar6.a, 0, obj, eaVar4.b + eaVar5.b, eaVar6.b);
        fn.a(eaVar5.a, eaVar5.b);
        fo foVar = new fo(eaVar5.a);
        foVar.b(44);
        int c = foVar.c(3);
        foVar.b(1);
        foVar.b(88);
        foVar.b(8);
        int i2 = 0;
        for (int i3 = 0; i3 < c; i3++) {
            if (foVar.b()) {
                i2 += 89;
            }
            if (foVar.b()) {
                i2 += 8;
            }
        }
        foVar.b(i2);
        if (c > 0) {
            foVar.b((8 - c) * 2);
        }
        foVar.d();
        i2 = foVar.d();
        if (i2 == 3) {
            foVar.b(1);
        }
        int d = foVar.d();
        int d2 = foVar.d();
        if (foVar.b()) {
            int i4;
            int d3 = foVar.d();
            int d4 = foVar.d();
            int d5 = foVar.d();
            int d6 = foVar.d();
            if (i2 != 1) {
                if (i2 != 2) {
                    i4 = 1;
                    d -= i4 * (d3 + d4);
                    d2 -= (i2 != 1 ? 2 : 1) * (d5 + d6);
                }
            }
            i4 = 2;
            if (i2 != 1) {
            }
            d -= i4 * (d3 + d4);
            d2 -= (i2 != 1 ? 2 : 1) * (d5 + d6);
        }
        int i5 = d;
        int i6 = d2;
        foVar.d();
        foVar.d();
        d = foVar.d();
        i2 = foVar.b() ? 0 : c;
        while (i2 <= c) {
            foVar.d();
            foVar.d();
            foVar.d();
            i2++;
        }
        foVar.d();
        foVar.d();
        foVar.d();
        foVar.d();
        foVar.d();
        foVar.d();
        if (foVar.b() && foVar.b()) {
            a(foVar);
        }
        foVar.b(2);
        if (foVar.b()) {
            foVar.b(8);
            foVar.d();
            foVar.d();
            foVar.b(1);
        }
        b(foVar);
        if (foVar.b()) {
            while (i < foVar.d()) {
                foVar.b((d + 4) + 1);
                i++;
            }
        }
        foVar.b(2);
        float f2 = 1.0f;
        if (foVar.b() && foVar.b()) {
            c = foVar.c(8);
            if (c == 255) {
                int c2 = foVar.c(16);
                int c3 = foVar.c(16);
                if (!(c2 == 0 || c3 == 0)) {
                    f2 = ((float) c2) / ((float) c3);
                }
                f = f2;
            } else if (c < fn.b.length) {
                f = fn.b[c];
            } else {
                StringBuilder stringBuilder = new StringBuilder(46);
                stringBuilder.append("Unexpected aspect_ratio_idc value: ");
                stringBuilder.append(c);
                Log.w("H265Reader", stringBuilder.toString());
            }
            return bj.a(null, MimeTypes.VIDEO_H265, -1, -1, -1, i5, i6, Collections.singletonList(obj), -1, f);
        }
        f = 1.0f;
        return bj.a(null, MimeTypes.VIDEO_H265, -1, -1, -1, i5, i6, Collections.singletonList(obj), -1, f);
    }

    private static void a(fo foVar) {
        for (int i = 0; i < 4; i++) {
            int i2 = 0;
            while (i2 < 6) {
                int min;
                if (foVar.b()) {
                    min = Math.min(64, 1 << ((i << 1) + 4));
                    if (i > 1) {
                        foVar.e();
                    }
                    for (int i3 = 0; i3 < min; i3++) {
                        foVar.e();
                    }
                } else {
                    foVar.d();
                }
                min = 3;
                if (i != 3) {
                    min = 1;
                }
                i2 += min;
            }
        }
    }

    private static void b(fo foVar) {
        int d = foVar.d();
        boolean z = false;
        int i = 0;
        for (int i2 = 0; i2 < d; i2++) {
            if (i2 != 0) {
                z = foVar.b();
            }
            int i3;
            if (z) {
                foVar.b(1);
                foVar.d();
                for (i3 = 0; i3 <= i; i3++) {
                    if (foVar.b()) {
                        foVar.b(1);
                    }
                }
            } else {
                i = foVar.d();
                i3 = foVar.d();
                int i4 = i + i3;
                for (int i5 = 0; i5 < i; i5++) {
                    foVar.d();
                    foVar.b(1);
                }
                for (i = 0; i < i3; i++) {
                    foVar.d();
                    foVar.b(1);
                }
                i = i4;
            }
        }
    }
}
