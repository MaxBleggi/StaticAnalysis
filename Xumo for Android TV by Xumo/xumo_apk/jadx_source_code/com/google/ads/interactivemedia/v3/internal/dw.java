package com.google.ads.interactivemedia.v3.internal;

import android.util.SparseArray;
import com.google.ads.interactivemedia.v3.internal.fn.b;
import com.google.android.exoplayer2.util.MimeTypes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: IMASDK */
final class dw extends du {
    private boolean b;
    private final ed c;
    private final boolean[] d = new boolean[3];
    private final a e;
    private final ea f;
    private final ea g;
    private final ea h;
    private long i;
    private long j;
    private final fp k;

    /* compiled from: IMASDK */
    private static final class a {
        private final ck a;
        private final boolean b;
        private final boolean c;
        private final fo d = new fo();
        private final SparseArray<b> e = new SparseArray();
        private final SparseArray<com.google.ads.interactivemedia.v3.internal.fn.a> f = new SparseArray();
        private byte[] g = new byte[128];
        private int h;
        private int i;
        private long j;
        private boolean k;
        private long l;
        private a m = new a();
        private a n = new a();
        private boolean o;
        private long p;
        private long q;
        private boolean r;

        /* compiled from: IMASDK */
        private static final class a {
            private boolean a;
            private boolean b;
            private b c;
            private int d;
            private int e;
            private int f;
            private int g;
            private boolean h;
            private boolean i;
            private boolean j;
            private boolean k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;

            private a() {
            }

            public void a() {
                this.b = false;
                this.a = false;
            }

            public void a(int i) {
                this.e = i;
                this.b = true;
            }

            public void a(b bVar, int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, boolean z4, int i5, int i6, int i7, int i8, int i9) {
                this.c = bVar;
                this.d = i;
                this.e = i2;
                this.f = i3;
                this.g = i4;
                this.h = z;
                this.i = z2;
                this.j = z3;
                this.k = z4;
                this.l = i5;
                this.m = i6;
                this.n = i7;
                this.o = i8;
                this.p = i9;
                this.a = true;
                this.b = true;
            }

            public boolean b() {
                return this.b && (this.e == 7 || this.e == 2);
            }

            private boolean a(a aVar) {
                if (this.a) {
                    if (!aVar.a || this.f != aVar.f || this.g != aVar.g || this.h != aVar.h) {
                        return true;
                    }
                    if (this.i && aVar.i && this.j != aVar.j) {
                        return true;
                    }
                    if (this.d != aVar.d && (this.d == 0 || aVar.d == 0)) {
                        return true;
                    }
                    if (this.c.h == 0 && aVar.c.h == 0 && (this.m != aVar.m || this.n != aVar.n)) {
                        return true;
                    }
                    if ((this.c.h == 1 && aVar.c.h == 1 && (this.o != aVar.o || this.p != aVar.p)) || this.k != aVar.k) {
                        return true;
                    }
                    if (this.k && aVar.k && this.l != aVar.l) {
                        return true;
                    }
                }
                return false;
            }
        }

        public a(ck ckVar, boolean z, boolean z2) {
            this.a = ckVar;
            this.b = z;
            this.c = z2;
            b();
        }

        public boolean a() {
            return this.c;
        }

        public void a(b bVar) {
            this.e.append(bVar.a, bVar);
        }

        public void a(com.google.ads.interactivemedia.v3.internal.fn.a aVar) {
            this.f.append(aVar.a, aVar);
        }

        public void b() {
            this.k = false;
            this.o = false;
            this.n.a();
        }

        public void a(long j, int i, long j2) {
            this.i = i;
            this.l = j2;
            this.j = j;
            if (this.b == null || this.i != 1) {
                if (this.c == null) {
                    return;
                }
                if (!(this.i == 5 || this.i == 1 || this.i == 2)) {
                    return;
                }
            }
            j = this.m;
            this.m = this.n;
            this.n = j;
            this.n.a();
            this.h = 0;
            this.k = true;
        }

        public void a(byte[] bArr, int i, int i2) {
            int i3 = i;
            if (this.k) {
                int i4 = i2 - i3;
                if (r0.g.length < r0.h + i4) {
                    r0.g = Arrays.copyOf(r0.g, (r0.h + i4) * 2);
                }
                System.arraycopy(bArr, i3, r0.g, r0.h, i4);
                r0.h += i4;
                r0.d.a(r0.g, r0.h);
                if (r0.d.a() >= 8) {
                    r0.d.b(1);
                    int c = r0.d.c(2);
                    r0.d.b(5);
                    if (r0.d.c()) {
                        r0.d.d();
                        if (r0.d.c()) {
                            int d = r0.d.d();
                            if (!r0.c) {
                                r0.k = false;
                                r0.n.a(d);
                            } else if (r0.d.c()) {
                                int d2 = r0.d.d();
                                if (r0.f.indexOfKey(d2) < 0) {
                                    r0.k = false;
                                    return;
                                }
                                com.google.ads.interactivemedia.v3.internal.fn.a aVar = (com.google.ads.interactivemedia.v3.internal.fn.a) r0.f.get(d2);
                                b bVar = (b) r0.e.get(aVar.b);
                                if (bVar.e) {
                                    if (r0.d.a() >= 2) {
                                        r0.d.b(2);
                                    } else {
                                        return;
                                    }
                                }
                                if (r0.d.a() >= bVar.g) {
                                    boolean z;
                                    boolean b;
                                    boolean z2;
                                    boolean z3;
                                    int i5;
                                    int i6;
                                    int e;
                                    int i7;
                                    int i8;
                                    int c2 = r0.d.c(bVar.g);
                                    if (bVar.f) {
                                        z = false;
                                    } else if (r0.d.a() >= 1) {
                                        boolean b2 = r0.d.b();
                                        if (!b2) {
                                            z = b2;
                                        } else if (r0.d.a() >= 1) {
                                            z = b2;
                                            b = r0.d.b();
                                            z2 = true;
                                            z3 = r0.i != 5;
                                            if (z3) {
                                                i5 = 0;
                                            } else if (!r0.d.c()) {
                                                i5 = r0.d.d();
                                            } else {
                                                return;
                                            }
                                            if (bVar.h != 0) {
                                                if (r0.d.a() < bVar.i) {
                                                    i4 = r0.d.c(bVar.i);
                                                    if (aVar.c || z) {
                                                        i6 = i4;
                                                    } else if (r0.d.c()) {
                                                        e = r0.d.e();
                                                        i6 = i4;
                                                        i7 = 0;
                                                        i8 = 0;
                                                        r0.n.a(bVar, c, d, c2, d2, z, z2, b, z3, i5, i6, e, i7, i8);
                                                        r0.k = false;
                                                    } else {
                                                        return;
                                                    }
                                                }
                                                return;
                                            } else if (bVar.h == 1 || bVar.j) {
                                                i6 = 0;
                                            } else if (r0.d.c()) {
                                                i4 = r0.d.e();
                                                if (!aVar.c || z) {
                                                    i7 = i4;
                                                    i6 = 0;
                                                    e = 0;
                                                    i8 = 0;
                                                    r0.n.a(bVar, c, d, c2, d2, z, z2, b, z3, i5, i6, e, i7, i8);
                                                    r0.k = false;
                                                } else if (r0.d.c()) {
                                                    i8 = r0.d.e();
                                                    i7 = i4;
                                                    i6 = 0;
                                                    e = 0;
                                                    r0.n.a(bVar, c, d, c2, d2, z, z2, b, z3, i5, i6, e, i7, i8);
                                                    r0.k = false;
                                                } else {
                                                    return;
                                                }
                                            } else {
                                                return;
                                            }
                                            e = 0;
                                            i7 = 0;
                                            i8 = 0;
                                            r0.n.a(bVar, c, d, c2, d2, z, z2, b, z3, i5, i6, e, i7, i8);
                                            r0.k = false;
                                        } else {
                                            return;
                                        }
                                    } else {
                                        return;
                                    }
                                    z2 = false;
                                    b = false;
                                    if (r0.i != 5) {
                                    }
                                    if (z3) {
                                        i5 = 0;
                                    } else if (!r0.d.c()) {
                                        i5 = r0.d.d();
                                    } else {
                                        return;
                                    }
                                    if (bVar.h != 0) {
                                        if (bVar.h == 1) {
                                        }
                                        i6 = 0;
                                    } else if (r0.d.a() < bVar.i) {
                                        i4 = r0.d.c(bVar.i);
                                        if (aVar.c) {
                                        }
                                        i6 = i4;
                                    } else {
                                        return;
                                    }
                                    e = 0;
                                    i7 = 0;
                                    i8 = 0;
                                    r0.n.a(bVar, c, d, c2, d2, z, z2, b, z3, i5, i6, e, i7, i8);
                                    r0.k = false;
                                }
                            }
                        }
                    }
                }
            }
        }

        public void a(long j, int i) {
            int i2 = 0;
            if (this.i == 9 || (this.c && this.n.a(this.m))) {
                if (this.o) {
                    a(i + ((int) (j - this.j)));
                }
                this.p = this.j;
                this.q = this.l;
                this.r = false;
                this.o = true;
            }
            j = this.r;
            if (this.i == 5 || (this.b && this.i == 1 && this.n.b())) {
                i2 = 1;
            }
            this.r = j | i2;
        }

        private void a(int i) {
            this.a.a(this.q, this.r, (int) (this.j - this.p), i, null);
        }
    }

    public dw(ck ckVar, ed edVar, boolean z, boolean z2) {
        super(ckVar);
        this.c = edVar;
        this.e = new a(ckVar, z, z2);
        this.f = new ea(true, 128);
        this.g = new ea(true, 128);
        this.h = new ea(true, 128);
        this.k = new fp();
    }

    public void b() {
    }

    public void a() {
        fn.a(this.d);
        this.f.a();
        this.g.a();
        this.h.a();
        this.e.b();
        this.i = 0;
    }

    public void a(long j, boolean z) {
        this.j = j;
    }

    public void a(fp fpVar) {
        if (fpVar.b() > 0) {
            int d = fpVar.d();
            int c = fpVar.c();
            byte[] bArr = fpVar.a;
            this.i += (long) fpVar.b();
            this.a.a(fpVar, fpVar.b());
            while (true) {
                int a = fn.a(bArr, d, c, this.d);
                if (a == c) {
                    a(bArr, d, c);
                    return;
                }
                int b = fn.b(bArr, a);
                int i = a - d;
                if (i > 0) {
                    a(bArr, d, a);
                }
                int i2 = c - a;
                long j = this.i - ((long) i2);
                a(j, i2, i < 0 ? -i : 0, this.j);
                a(j, b, this.j);
                d = a + 3;
            }
        }
    }

    private void a(long j, int i, long j2) {
        if (!this.b || this.e.a()) {
            this.f.a(i);
            this.g.a(i);
        }
        this.h.a(i);
        this.e.a(j, i, j2);
    }

    private void a(byte[] bArr, int i, int i2) {
        if (!this.b || this.e.a()) {
            this.f.a(bArr, i, i2);
            this.g.a(bArr, i, i2);
        }
        this.h.a(bArr, i, i2);
        this.e.a(bArr, i, i2);
    }

    private void a(long j, int i, int i2, long j2) {
        int i3 = i2;
        if (!this.b || r0.e.a()) {
            r0.f.b(i3);
            r0.g.b(i3);
            if (r0.b) {
                if (r0.f.b()) {
                    r0.e.a(fn.a(a(r0.f)));
                    r0.f.a();
                } else if (r0.g.b()) {
                    r0.e.a(fn.b(a(r0.g)));
                    r0.g.a();
                }
            } else if (r0.f.b() && r0.g.b()) {
                List arrayList = new ArrayList();
                arrayList.add(Arrays.copyOf(r0.f.a, r0.f.b));
                arrayList.add(Arrays.copyOf(r0.g.a, r0.g.b));
                b a = fn.a(a(r0.f));
                com.google.ads.interactivemedia.v3.internal.fn.a b = fn.b(a(r0.g));
                r0.a.a(bj.a(null, MimeTypes.VIDEO_H264, -1, -1, -1, a.b, a.c, arrayList, -1, a.d));
                r0.b = true;
                r0.e.a(a);
                r0.e.a(b);
                r0.f.a();
                r0.g.a();
            }
        }
        if (r0.h.b(i3)) {
            r0.k.a(r0.h.a, fn.a(r0.h.a, r0.h.b));
            r0.k.c(4);
            r0.c.a(j2, r0.k);
        }
        r0.e.a(j, i);
    }

    private static fo a(ea eaVar) {
        fo foVar = new fo(eaVar.a, fn.a(eaVar.a, eaVar.b));
        foVar.b(32);
        return foVar;
    }
}
