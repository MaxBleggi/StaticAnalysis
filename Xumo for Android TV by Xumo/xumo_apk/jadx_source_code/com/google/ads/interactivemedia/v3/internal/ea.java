package com.google.ads.interactivemedia.v3.internal;

import java.util.Arrays;

/* compiled from: IMASDK */
final class ea {
    public byte[] a;
    public int b;
    private final int c;
    private boolean d;
    private boolean e;

    public ea(int i, int i2) {
        this.c = i;
        this.a = new byte[(i2 + 3)];
        this.a[2] = (byte) 1;
    }

    public void a() {
        this.d = false;
        this.e = false;
    }

    public boolean b() {
        return this.e;
    }

    public void a(int i) {
        boolean z = true;
        fe.b(this.d ^ true);
        if (i != this.c) {
            z = false;
        }
        this.d = z;
        if (this.d != 0) {
            this.b = 3;
            this.e = false;
        }
    }

    public void a(byte[] bArr, int i, int i2) {
        if (this.d) {
            i2 -= i;
            if (this.a.length < this.b + i2) {
                this.a = Arrays.copyOf(this.a, (this.b + i2) * 2);
            }
            System.arraycopy(bArr, i, this.a, this.b, i2);
            this.b += i2;
        }
    }

    public boolean b(int i) {
        if (!this.d) {
            return false;
        }
        this.b -= i;
        this.d = false;
        this.e = true;
        return true;
    }
}
