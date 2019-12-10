package com.google.ads.interactivemedia.v3.internal;

import java.util.Arrays;

/* compiled from: IMASDK */
public final class ev implements eq {
    private final int a;
    private final byte[] b;
    private int c;
    private int d;
    private ep[] e;

    public ev(int i) {
        this(i, 0);
    }

    public ev(int i, int i2) {
        boolean z = true;
        fe.a(i > 0);
        if (i2 < 0) {
            z = false;
        }
        fe.a(z);
        this.a = i;
        this.d = i2;
        this.e = new ep[(i2 + 100)];
        if (i2 > 0) {
            this.b = new byte[(i2 * i)];
            for (int i3 = 0; i3 < i2; i3++) {
                this.e[i3] = new ep(this.b, i3 * i);
            }
            return;
        }
        this.b = 0;
    }

    public synchronized ep a() {
        ep epVar;
        this.c++;
        if (this.d > 0) {
            ep[] epVarArr = this.e;
            int i = this.d - 1;
            this.d = i;
            epVar = epVarArr[i];
            this.e[this.d] = null;
        } else {
            epVar = new ep(new byte[this.a], 0);
        }
        return epVar;
    }

    public synchronized void a(ep epVar) {
        boolean z;
        ep[] epVarArr;
        int i;
        if (epVar.a != this.b) {
            if (epVar.a.length != this.a) {
                z = false;
                fe.a(z);
                this.c--;
                if (this.d == this.e.length) {
                    this.e = (ep[]) Arrays.copyOf(this.e, this.e.length * 2);
                }
                epVarArr = this.e;
                i = this.d;
                this.d = i + 1;
                epVarArr[i] = epVar;
                notifyAll();
            }
        }
        z = true;
        fe.a(z);
        this.c--;
        if (this.d == this.e.length) {
            this.e = (ep[]) Arrays.copyOf(this.e, this.e.length * 2);
        }
        epVarArr = this.e;
        i = this.d;
        this.d = i + 1;
        epVarArr[i] = epVar;
        notifyAll();
    }

    public synchronized void a(ep[] epVarArr) {
        if (this.d + epVarArr.length >= this.e.length) {
            this.e = (ep[]) Arrays.copyOf(this.e, Math.max(this.e.length * 2, this.d + epVarArr.length));
        }
        for (ep epVar : epVarArr) {
            boolean z;
            ep[] epVarArr2;
            int i;
            if (epVar.a != this.b) {
                if (epVar.a.length != this.a) {
                    z = false;
                    fe.a(z);
                    epVarArr2 = this.e;
                    i = this.d;
                    this.d = i + 1;
                    epVarArr2[i] = epVar;
                }
            }
            z = true;
            fe.a(z);
            epVarArr2 = this.e;
            i = this.d;
            this.d = i + 1;
            epVarArr2[i] = epVar;
        }
        this.c -= epVarArr.length;
        notifyAll();
    }

    public synchronized void a(int i) {
        int i2 = 0;
        i = Math.max(0, ft.a(i, this.a) - this.c);
        if (i < this.d) {
            if (this.b != null) {
                int i3 = this.d - 1;
                while (i2 <= i3) {
                    ep epVar = this.e[i2];
                    if (epVar.a == this.b) {
                        i2++;
                    } else {
                        ep epVar2 = this.e[i3];
                        if (epVar2.a != this.b) {
                            i3--;
                        } else {
                            int i4 = i2 + 1;
                            this.e[i2] = epVar2;
                            int i5 = i3 - 1;
                            this.e[i3] = epVar;
                            i3 = i5;
                            i2 = i4;
                        }
                    }
                }
                i = Math.max(i, i2);
                if (i >= this.d) {
                    return;
                }
            }
            Arrays.fill(this.e, i, this.d, null);
            this.d = i;
        }
    }

    public synchronized int c() {
        return this.c * this.a;
    }

    public synchronized void b(int i) throws InterruptedException {
        while (c() > i) {
            wait();
        }
    }

    public int b() {
        return this.a;
    }
}
