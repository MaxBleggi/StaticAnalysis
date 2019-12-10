package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;

/* compiled from: IMASDK */
public class ca implements ck {
    private final ci a;
    private final bm b = new bm(0);
    private boolean c = true;
    private long d = Long.MIN_VALUE;
    private long e = Long.MIN_VALUE;
    private volatile long f = Long.MIN_VALUE;
    private volatile bj g;

    public ca(eq eqVar) {
        this.a = new ci(eqVar);
    }

    public void a() {
        this.a.a();
        this.c = true;
        this.d = Long.MIN_VALUE;
        this.e = Long.MIN_VALUE;
        this.f = Long.MIN_VALUE;
    }

    public boolean b() {
        return this.g != null;
    }

    public bj c() {
        return this.g;
    }

    public long d() {
        return this.f;
    }

    public boolean e() {
        return f() ^ 1;
    }

    public boolean a(bm bmVar) {
        if (!f()) {
            return false;
        }
        this.a.b(bmVar);
        this.c = false;
        this.d = bmVar.e;
        return true;
    }

    public void a(long j) {
        while (this.a.a(this.b) && this.b.e < j) {
            this.a.b();
            this.c = true;
        }
        this.d = Long.MIN_VALUE;
    }

    public boolean b(long j) {
        return this.a.a(j);
    }

    private boolean f() {
        boolean a = this.a.a(this.b);
        if (this.c) {
            while (a && !this.b.c()) {
                this.a.b();
                a = this.a.a(this.b);
            }
        }
        if (!a) {
            return false;
        }
        if (this.e == Long.MIN_VALUE || this.b.e < this.e) {
            return true;
        }
        return false;
    }

    public void a(bj bjVar) {
        this.g = bjVar;
    }

    public int a(cd cdVar, int i, boolean z) throws IOException, InterruptedException {
        return this.a.a(cdVar, i, z);
    }

    public void a(fp fpVar, int i) {
        this.a.a(fpVar, i);
    }

    public void a(long j, int i, int i2, int i3, byte[] bArr) {
        long j2 = j;
        this.f = Math.max(this.f, j);
        int i4 = i2;
        this.a.a(j2, i, (this.a.c() - ((long) i4)) - ((long) i3), i4, bArr);
    }
}
