package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class dt extends du {
    private final fp b = new fp(new byte[15]);
    private int c;
    private int d;
    private int e;
    private long f;
    private bj g;
    private int h;
    private long i;

    public dt(ck ckVar) {
        super(ckVar);
        this.b.a[0] = Byte.MAX_VALUE;
        this.b.a[1] = (byte) -2;
        this.b.a[2] = Byte.MIN_VALUE;
        this.b.a[3] = 1;
        this.c = 0;
    }

    public void b() {
    }

    public void a() {
        this.c = 0;
        this.d = 0;
        this.e = 0;
    }

    public void a(long j, boolean z) {
        this.i = j;
    }

    public void a(fp fpVar) {
        while (fpVar.b() > 0) {
            switch (this.c) {
                case 0:
                    if (!b(fpVar)) {
                        break;
                    }
                    this.d = 4;
                    this.c = 1;
                    break;
                case 1:
                    if (!a(fpVar, this.b.a, 15)) {
                        break;
                    }
                    c();
                    this.b.c(0);
                    this.a.a(this.b, 15);
                    this.c = 2;
                    break;
                case 2:
                    int min = Math.min(fpVar.b(), this.h - this.d);
                    this.a.a(fpVar, min);
                    this.d += min;
                    if (this.d != this.h) {
                        break;
                    }
                    this.a.a(this.i, 1, this.h, 0, null);
                    this.i += this.f;
                    this.c = 0;
                    break;
                default:
                    break;
            }
        }
    }

    private boolean a(fp fpVar, byte[] bArr, int i) {
        int min = Math.min(fpVar.b(), i - this.d);
        fpVar.a(bArr, this.d, min);
        this.d += min;
        return this.d == i ? true : null;
    }

    private boolean b(fp fpVar) {
        while (fpVar.b() > 0) {
            this.e <<= 8;
            this.e |= fpVar.f();
            if (this.e == 2147385345) {
                this.e = 0;
                return true;
            }
        }
        return false;
    }

    private void c() {
        byte[] bArr = this.b.a;
        if (this.g == null) {
            this.g = fg.a(bArr, null, -1, null);
            this.a.a(this.g);
        }
        this.h = fg.b(bArr);
        this.f = (long) ((int) ((((long) fg.a(bArr)) * 1000000) / ((long) this.g.r)));
    }
}
