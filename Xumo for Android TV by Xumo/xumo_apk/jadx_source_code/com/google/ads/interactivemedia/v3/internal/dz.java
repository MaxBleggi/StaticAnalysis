package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class dz extends du {
    private final fp b = new fp(4);
    private final fm c;
    private int d = 0;
    private int e;
    private boolean f;
    private boolean g;
    private long h;
    private int i;
    private long j;

    public dz(ck ckVar) {
        super(ckVar);
        this.b.a[0] = (byte) -1;
        this.c = new fm();
    }

    public void b() {
    }

    public void a() {
        this.d = 0;
        this.e = 0;
        this.g = false;
    }

    public void a(long j, boolean z) {
        this.j = j;
    }

    public void a(fp fpVar) {
        while (fpVar.b() > 0) {
            switch (this.d) {
                case 0:
                    b(fpVar);
                    break;
                case 1:
                    c(fpVar);
                    break;
                case 2:
                    d(fpVar);
                    break;
                default:
                    break;
            }
        }
    }

    private void b(fp fpVar) {
        byte[] bArr = fpVar.a;
        int c = fpVar.c();
        for (int d = fpVar.d(); d < c; d++) {
            boolean z = (bArr[d] & 255) == 255;
            Object obj = (this.g && (bArr[d] & 224) == 224) ? 1 : null;
            this.g = z;
            if (obj != null) {
                fpVar.c(d + 1);
                this.g = false;
                this.b.a[1] = bArr[d];
                this.e = 2;
                this.d = 1;
                return;
            }
        }
        fpVar.c(c);
    }

    private void c(fp fpVar) {
        int min = Math.min(fpVar.b(), 4 - this.e);
        fpVar.a(this.b.a, this.e, min);
        this.e += min;
        if (this.e >= 4) {
            r0.b.c(0);
            if (fm.a(r0.b.m(), r0.c)) {
                r0.i = r0.c.c;
                if (!r0.f) {
                    r0.h = (((long) r0.c.g) * 1000000) / ((long) r0.c.d);
                    r0.a.a(bj.a(null, r0.c.b, -1, 4096, -1, r0.c.e, r0.c.d, null, null));
                    r0.f = true;
                }
                r0.b.c(0);
                r0.a.a(r0.b, 4);
                r0.d = 2;
                return;
            }
            r0.e = 0;
            r0.d = 1;
        }
    }

    private void d(fp fpVar) {
        int min = Math.min(fpVar.b(), this.i - this.e);
        this.a.a(fpVar, min);
        this.e += min;
        if (this.e >= this.i) {
            this.a.a(this.j, 1, this.i, 0, null);
            this.j += this.h;
            this.e = 0;
            this.d = 0;
        }
    }
}
