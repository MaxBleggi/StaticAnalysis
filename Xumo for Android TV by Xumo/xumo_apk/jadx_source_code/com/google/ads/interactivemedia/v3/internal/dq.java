package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class dq extends du {
    private final boolean b;
    private final fo c = new fo(new byte[true]);
    private final fp d = new fp(this.c.a);
    private int e = null;
    private int f;
    private boolean g;
    private long h;
    private bj i;
    private int j;
    private long k;

    public dq(ck ckVar, boolean z) {
        super(ckVar);
        this.b = z;
    }

    public void b() {
    }

    public void a() {
        this.e = 0;
        this.f = 0;
        this.g = false;
    }

    public void a(long j, boolean z) {
        this.k = j;
    }

    public void a(fp fpVar) {
        while (fpVar.b() > 0) {
            switch (this.e) {
                case 0:
                    if (!b(fpVar)) {
                        break;
                    }
                    this.e = 1;
                    this.d.a[0] = (byte) 11;
                    this.d.a[1] = (byte) 119;
                    this.f = 2;
                    break;
                case 1:
                    if (!a(fpVar, this.d.a, 8)) {
                        break;
                    }
                    c();
                    this.d.c(0);
                    this.a.a(this.d, 8);
                    this.e = 2;
                    break;
                case 2:
                    int min = Math.min(fpVar.b(), this.j - this.f);
                    this.a.a(fpVar, min);
                    this.f += min;
                    if (this.f != this.j) {
                        break;
                    }
                    this.a.a(this.k, 1, this.j, 0, null);
                    this.k += this.h;
                    this.e = 0;
                    break;
                default:
                    break;
            }
        }
    }

    private boolean a(fp fpVar, byte[] bArr, int i) {
        int min = Math.min(fpVar.b(), i - this.f);
        fpVar.a(bArr, this.f, min);
        this.f += min;
        return this.f == i ? true : null;
    }

    private boolean b(fp fpVar) {
        while (true) {
            boolean z = false;
            if (fpVar.b() <= 0) {
                return false;
            }
            if (this.g) {
                int f = fpVar.f();
                if (f == 119) {
                    this.g = false;
                    return true;
                }
                if (f == 11) {
                    z = true;
                }
                this.g = z;
            } else {
                if (fpVar.f() == 11) {
                    z = true;
                }
                this.g = z;
            }
        }
    }

    private void c() {
        int b;
        if (this.i == null) {
            bj b2;
            if (this.b) {
                b2 = fd.b(this.c, null, -1, null);
            } else {
                b2 = fd.a(this.c, null, -1, null);
            }
            this.i = b2;
            this.a.a(this.i);
        }
        if (this.b) {
            b = fd.b(this.c.a);
        } else {
            b = fd.a(this.c.a);
        }
        this.j = b;
        if (this.b) {
            b = fd.c(this.c.a);
        } else {
            b = fd.a();
        }
        this.h = (long) ((int) ((((long) b) * 1000000) / ((long) this.i.r)));
    }
}
