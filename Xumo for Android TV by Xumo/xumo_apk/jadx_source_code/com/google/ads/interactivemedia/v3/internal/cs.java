package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;

/* compiled from: IMASDK */
public final class cs implements cc {
    private static final int a = ft.c("Xing");
    private static final int b = ft.c("Info");
    private static final int c = ft.c("VBRI");
    private final long d;
    private final fp e;
    private final fm f;
    private ce g;
    private ck h;
    private int i;
    private cg j;
    private a k;
    private long l;
    private long m;
    private int n;

    /* compiled from: IMASDK */
    interface a extends cj {
        long a(long j);

        long b();
    }

    public cs() {
        this(-1);
    }

    public void c() {
    }

    public cs(long j) {
        this.d = j;
        this.e = new fp(4);
        this.f = new fm();
        this.l = -1;
    }

    public boolean a(cd cdVar) throws IOException, InterruptedException {
        return a(cdVar, true);
    }

    public void a(ce ceVar) {
        this.g = ceVar;
        this.h = ceVar.d(0);
        ceVar.f();
    }

    public void b() {
        this.i = 0;
        this.m = 0;
        this.l = -1;
        this.n = 0;
    }

    public int a(cd cdVar, ch chVar) throws IOException, InterruptedException {
        if (this.i == null && d(cdVar) == null) {
            return -1;
        }
        if (this.k == null) {
            e(cdVar);
            this.g.a(this.k);
            chVar = bj.a(null, this.f.b, -1, 4096, this.k.b(), this.f.e, this.f.d, null, null);
            if (this.j != null) {
                chVar = chVar.a(this.j.a, this.j.b);
            }
            this.h.a(chVar);
        }
        return b(cdVar);
    }

    private int b(cd cdVar) throws IOException, InterruptedException {
        if (this.n == 0) {
            if (!c(cdVar)) {
                return -1;
            }
            if (this.l == -1) {
                this.l = this.k.a(cdVar.c());
                if (this.d != -1) {
                    this.l += this.d - this.k.a(0);
                }
            }
            this.n = this.f.c;
        }
        cdVar = this.h.a(cdVar, this.n, true);
        if (cdVar == -1) {
            return -1;
        }
        this.n -= cdVar;
        if (this.n > null) {
            return 0;
        }
        this.h.a(this.l + ((this.m * 1000000) / ((long) this.f.d)), 1, this.f.c, 0, null);
        this.m += (long) this.f.g;
        this.n = 0;
        return 0;
    }

    private boolean c(cd cdVar) throws IOException, InterruptedException {
        cdVar.a();
        if (!cdVar.b(this.e.a, 0, 4, true)) {
            return false;
        }
        this.e.c(0);
        int m = this.e.m();
        if ((m & -128000) != (-128000 & this.i) || fm.a(m) == -1) {
            this.i = 0;
            cdVar.b(1);
            return d(cdVar);
        }
        fm.a(m, this.f);
        return true;
    }

    private boolean d(com.google.ads.interactivemedia.v3.internal.cd r2) throws java.io.IOException, java.lang.InterruptedException {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r1 = this;
        r0 = 0;
        r2 = r1.a(r2, r0);	 Catch:{ EOFException -> 0x0006 }
        return r2;
    L_0x0006:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.cs.d(com.google.ads.interactivemedia.v3.internal.cd):boolean");
    }

    private boolean a(com.google.ads.interactivemedia.v3.internal.cd r11, boolean r12) throws java.io.IOException, java.lang.InterruptedException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r10 = this;
        r11.a();
        r0 = r11.c();
        r2 = 0;
        r3 = 0;
        r5 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1));
        if (r5 != 0) goto L_0x0023;
    L_0x000e:
        r0 = com.google.ads.interactivemedia.v3.internal.cr.a(r11);
        r10.j = r0;
        r0 = r11.b();
        r0 = (int) r0;
        if (r12 != 0) goto L_0x001e;
    L_0x001b:
        r11.b(r0);
    L_0x001e:
        r4 = r0;
        r0 = 0;
    L_0x0020:
        r1 = 0;
        r3 = 0;
        goto L_0x0027;
    L_0x0023:
        r0 = 0;
        r1 = 0;
        r3 = 0;
        r4 = 0;
    L_0x0027:
        if (r12 == 0) goto L_0x002e;
    L_0x0029:
        r5 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        if (r0 != r5) goto L_0x002e;
    L_0x002d:
        return r2;
    L_0x002e:
        if (r12 != 0) goto L_0x003d;
    L_0x0030:
        r5 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        if (r0 == r5) goto L_0x0035;
    L_0x0034:
        goto L_0x003d;
    L_0x0035:
        r11 = new com.google.ads.interactivemedia.v3.internal.bl;
        r12 = "Searched too many bytes.";
        r11.<init>(r12);
        throw r11;
    L_0x003d:
        r5 = r10.e;
        r5 = r5.a;
        r6 = 4;
        r7 = 1;
        r5 = r11.b(r5, r2, r6, r7);
        if (r5 != 0) goto L_0x004a;
    L_0x0049:
        return r2;
    L_0x004a:
        r5 = r10.e;
        r5.c(r2);
        r5 = r10.e;
        r5 = r5.m();
        if (r1 == 0) goto L_0x005f;
    L_0x0057:
        r8 = -128000; // 0xfffffffffffe0c00 float:NaN double:NaN;
        r9 = r5 & r8;
        r8 = r8 & r1;
        if (r9 != r8) goto L_0x0066;
    L_0x005f:
        r8 = com.google.ads.interactivemedia.v3.internal.fm.a(r5);
        r9 = -1;
        if (r8 != r9) goto L_0x0077;
    L_0x0066:
        r0 = r0 + 1;
        if (r12 == 0) goto L_0x0073;
    L_0x006a:
        r11.a();
        r1 = r4 + r0;
        r11.c(r1);
        goto L_0x0020;
    L_0x0073:
        r11.b(r7);
        goto L_0x0020;
    L_0x0077:
        r3 = r3 + r7;
        if (r3 != r7) goto L_0x0081;
    L_0x007a:
        r1 = r10.f;
        com.google.ads.interactivemedia.v3.internal.fm.a(r5, r1);
        r1 = r5;
        goto L_0x0090;
    L_0x0081:
        if (r3 != r6) goto L_0x0090;
    L_0x0083:
        if (r12 == 0) goto L_0x008a;
    L_0x0085:
        r4 = r4 + r0;
        r11.b(r4);
        goto L_0x008d;
    L_0x008a:
        r11.a();
    L_0x008d:
        r10.i = r1;
        return r7;
    L_0x0090:
        r8 = r8 + -4;
        r11.c(r8);
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.cs.a(com.google.ads.interactivemedia.v3.internal.cd, boolean):boolean");
    }

    private void e(cd cdVar) throws IOException, InterruptedException {
        int i;
        int m;
        fp fpVar = new fp(this.f.c);
        cdVar.c(fpVar.a, 0, this.f.c);
        long c = cdVar.c();
        long d = cdVar.d();
        if ((this.f.a & 1) != 0) {
            if (this.f.e != 1) {
                i = 36;
                if (fpVar.c() >= i + 4) {
                    fpVar.c(i);
                    m = fpVar.m();
                } else {
                    m = 0;
                }
                if (m != a) {
                    if (m == b) {
                        if (fpVar.c() >= 40) {
                            fpVar.c(36);
                            if (fpVar.m() == c) {
                                this.k = ct.a(this.f, fpVar, c, d);
                                cdVar.b(this.f.c);
                            }
                        }
                        if (this.k == null) {
                            cdVar.a();
                            cdVar.c(this.e.a, 0, 4);
                            this.e.c(0);
                            fm.a(this.e.m(), this.f);
                            this.k = new cq(cdVar.c(), this.f.f, d);
                        }
                    }
                }
                this.k = cu.a(this.f, fpVar, c, d);
                if (this.k != null && this.j == null) {
                    cdVar.a();
                    cdVar.c(i + 141);
                    cdVar.c(this.e.a, 0, 3);
                    this.e.c(0);
                    this.j = cg.a(this.e.j());
                }
                cdVar.b(this.f.c);
                if (this.k == null) {
                    cdVar.a();
                    cdVar.c(this.e.a, 0, 4);
                    this.e.c(0);
                    fm.a(this.e.m(), this.f);
                    this.k = new cq(cdVar.c(), this.f.f, d);
                }
            }
        } else if (this.f.e == 1) {
            i = 13;
            if (fpVar.c() >= i + 4) {
                m = 0;
            } else {
                fpVar.c(i);
                m = fpVar.m();
            }
            if (m != a) {
                if (m == b) {
                    if (fpVar.c() >= 40) {
                        fpVar.c(36);
                        if (fpVar.m() == c) {
                            this.k = ct.a(this.f, fpVar, c, d);
                            cdVar.b(this.f.c);
                        }
                    }
                    if (this.k == null) {
                        cdVar.a();
                        cdVar.c(this.e.a, 0, 4);
                        this.e.c(0);
                        fm.a(this.e.m(), this.f);
                        this.k = new cq(cdVar.c(), this.f.f, d);
                    }
                }
            }
            this.k = cu.a(this.f, fpVar, c, d);
            cdVar.a();
            cdVar.c(i + 141);
            cdVar.c(this.e.a, 0, 3);
            this.e.c(0);
            this.j = cg.a(this.e.j());
            cdVar.b(this.f.c);
            if (this.k == null) {
                cdVar.a();
                cdVar.c(this.e.a, 0, 4);
                this.e.c(0);
                fm.a(this.e.m(), this.f);
                this.k = new cq(cdVar.c(), this.f.f, d);
            }
        }
        i = 21;
        if (fpVar.c() >= i + 4) {
            fpVar.c(i);
            m = fpVar.m();
        } else {
            m = 0;
        }
        if (m != a) {
            if (m == b) {
                if (fpVar.c() >= 40) {
                    fpVar.c(36);
                    if (fpVar.m() == c) {
                        this.k = ct.a(this.f, fpVar, c, d);
                        cdVar.b(this.f.c);
                    }
                }
                if (this.k == null) {
                    cdVar.a();
                    cdVar.c(this.e.a, 0, 4);
                    this.e.c(0);
                    fm.a(this.e.m(), this.f);
                    this.k = new cq(cdVar.c(), this.f.f, d);
                }
            }
        }
        this.k = cu.a(this.f, fpVar, c, d);
        cdVar.a();
        cdVar.c(i + 141);
        cdVar.c(this.e.a, 0, 3);
        this.e.c(0);
        this.j = cg.a(this.e.j());
        cdVar.b(this.f.c);
        if (this.k == null) {
            cdVar.a();
            cdVar.c(this.e.a, 0, 4);
            this.e.c(0);
            fm.a(this.e.m(), this.f);
            this.k = new cq(cdVar.c(), this.f.f, d);
        }
    }
}
