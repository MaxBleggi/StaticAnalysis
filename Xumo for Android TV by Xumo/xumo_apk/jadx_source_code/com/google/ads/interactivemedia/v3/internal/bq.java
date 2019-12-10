package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.ba.a;

/* compiled from: IMASDK */
public abstract class bq implements a {
    private int a;

    protected void a(int i, long j, boolean z) throws az {
    }

    public void a(int i, Object obj) throws az {
    }

    protected bd b() {
        return null;
    }

    protected abstract bj b(int i);

    protected abstract void b(long j, long j2) throws az;

    protected void c() throws az {
    }

    protected abstract boolean c(long j) throws az;

    protected void d() throws az {
    }

    protected abstract void d(long j) throws az;

    protected abstract boolean e();

    protected abstract boolean f();

    protected void g() throws az {
    }

    protected abstract long q();

    protected abstract long r();

    protected abstract void s() throws az;

    protected void t() throws az {
    }

    protected abstract int u();

    protected final int v() {
        return this.a;
    }

    final int f(long j) throws az {
        fe.b(this.a == 0);
        this.a = c(j);
        return this.a;
    }

    final void b(int i, long j, boolean z) throws az {
        boolean z2 = true;
        if (this.a != 1) {
            z2 = false;
        }
        fe.b(z2);
        this.a = 2;
        a(i, j, z);
    }

    final void w() throws az {
        fe.b(this.a == 2);
        this.a = 3;
        c();
    }

    final void x() throws az {
        fe.b(this.a == 3);
        this.a = 2;
        d();
    }

    final void y() throws az {
        fe.b(this.a == 2);
        this.a = 1;
        g();
    }

    final void z() throws az {
        boolean z = (this.a == 2 || this.a == 3 || this.a == -1) ? false : true;
        fe.b(z);
        this.a = -1;
        t();
    }
}
