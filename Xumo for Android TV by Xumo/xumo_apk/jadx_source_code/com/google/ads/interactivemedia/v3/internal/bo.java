package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.bh.b;
import com.google.ads.interactivemedia.v3.internal.bn.a;
import java.util.Arrays;

/* compiled from: IMASDK */
public abstract class bo extends bq {
    private final a[] a;
    private int[] b;
    private int[] c;
    private a d;
    private int e;
    private long f;

    public bo(bn... bnVarArr) {
        this.a = new a[bnVarArr.length];
        for (int i = 0; i < bnVarArr.length; i++) {
            this.a[i] = bnVarArr[i].a();
        }
    }

    protected abstract void a(long j) throws az;

    protected abstract void a(long j, long j2, boolean z) throws az;

    protected abstract boolean a(bj bjVar) throws b;

    protected long e(long j) {
        return j;
    }

    protected final boolean c(long j) throws az {
        int i = 1;
        for (int i2 = 0; i2 < this.a.length; i2++) {
            i &= r1.a[i2].a(j);
        }
        if (i == 0) {
            return false;
        }
        int[] iArr;
        i = 0;
        for (a c : r1.a) {
            i += c.c();
        }
        int[] iArr2 = new int[i];
        int[] iArr3 = new int[i];
        int length = r1.a.length;
        long j2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < length) {
            a aVar = r1.a[i3];
            int c2 = aVar.c();
            long j3 = j2;
            int i5 = i4;
            i4 = 0;
            while (i4 < c2) {
                bj a = aVar.a(i4);
                try {
                    if (a(a)) {
                        iArr2[i5] = i3;
                        iArr3[i5] = i4;
                        i5++;
                        if (j3 != -1) {
                            iArr = iArr2;
                            long j4 = a.e;
                            if (j4 == -1) {
                                j3 = -1;
                            } else if (j4 != -2) {
                                j3 = Math.max(j3, j4);
                            }
                            i4++;
                            iArr2 = iArr;
                        }
                    }
                    iArr = iArr2;
                    i4++;
                    iArr2 = iArr;
                } catch (Throwable e) {
                    throw new az(e);
                }
            }
            iArr = iArr2;
            i3++;
            i4 = i5;
            j2 = j3;
        }
        iArr = iArr2;
        r1.f = j2;
        r1.b = Arrays.copyOf(iArr, i4);
        r1.c = Arrays.copyOf(iArr3, i4);
        return true;
    }

    protected void a(int i, long j, boolean z) throws az {
        j = e(j);
        this.d = this.a[this.b[i]];
        this.e = this.c[i];
        this.d.a(this.e, j);
        a(j);
    }

    protected void d(long j) throws az {
        j = e(j);
        this.d.b(j);
        b(j);
    }

    protected final void b(long j, long j2) throws az {
        j = e(j);
        a(b(j), j2, this.d.b(this.e, j));
    }

    protected long q() {
        return this.d.d();
    }

    protected long r() {
        return this.f;
    }

    protected void s() throws az {
        if (this.d != null) {
            a(this.d);
            return;
        }
        for (a a : this.a) {
            a(a);
        }
    }

    protected void g() throws az {
        this.d.c(this.e);
        this.d = null;
    }

    protected void t() throws az {
        for (a e : this.a) {
            e.e();
        }
    }

    protected final int u() {
        return this.c.length;
    }

    protected final bj b(int i) {
        return this.a[this.b[i]].a(this.c[i]);
    }

    protected final int a(long j, bk bkVar, bm bmVar) {
        return this.d.a(this.e, j, bkVar, bmVar);
    }

    private long b(long j) throws az {
        long b = this.d.b(this.e);
        if (b == Long.MIN_VALUE) {
            return j;
        }
        a(b);
        return b;
    }

    private void a(a aVar) throws az {
        try {
            aVar.b();
        } catch (Throwable e) {
            throw new az(e);
        }
    }
}
