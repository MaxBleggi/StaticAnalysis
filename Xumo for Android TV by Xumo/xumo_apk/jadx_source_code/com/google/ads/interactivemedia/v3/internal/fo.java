package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
public final class fo {
    public byte[] a;
    private int b;
    private int c;
    private int d;

    public fo(byte[] bArr) {
        this(bArr, bArr.length);
    }

    public fo(byte[] bArr, int i) {
        this.a = bArr;
        this.d = i;
    }

    public void a(byte[] bArr) {
        a(bArr, bArr.length);
    }

    public void a(byte[] bArr, int i) {
        this.a = bArr;
        this.b = 0;
        this.c = 0;
        this.d = i;
    }

    public int a() {
        return ((this.d - this.b) * 8) - this.c;
    }

    public void a(int i) {
        this.b = i / 8;
        this.c = i - (this.b * 8);
        g();
    }

    public void b(int i) {
        this.b += i / 8;
        this.c += i % 8;
        if (this.c > 7) {
            this.b++;
            this.c -= 8;
        }
        g();
    }

    public boolean b() {
        return c(1) == 1;
    }

    public int c(int i) {
        int i2 = 0;
        if (i == 0) {
            return 0;
        }
        int i3 = i / 8;
        int i4 = 0;
        while (i2 < i3) {
            int i5;
            if (this.c != 0) {
                i5 = ((this.a[this.b + 1] & 255) >>> (8 - this.c)) | ((this.a[this.b] & 255) << this.c);
            } else {
                i5 = this.a[this.b];
            }
            i -= 8;
            i4 |= (255 & i5) << i;
            this.b++;
            i2++;
        }
        if (i > 0) {
            i2 = this.c + i;
            i = (byte) (255 >> (8 - i));
            if (i2 > 8) {
                i = (i & (((this.a[this.b] & 255) << (i2 - 8)) | ((255 & this.a[this.b + 1]) >> (16 - i2)))) | i4;
                this.b++;
            } else {
                i = (i & ((this.a[this.b] & 255) >> (8 - i2))) | i4;
                if (i2 == 8) {
                    this.b++;
                }
            }
            i4 = i;
            this.c = i2 % 8;
        }
        g();
        return i4;
    }

    public boolean c() {
        int i = this.b;
        int i2 = this.c;
        int i3 = 0;
        while (this.b < this.d && !b()) {
            i3++;
        }
        Object obj = this.b == this.d ? 1 : null;
        this.b = i;
        this.c = i2;
        if (obj != null || a() < (i3 * 2) + 1) {
            return false;
        }
        return true;
    }

    public int d() {
        return f();
    }

    public int e() {
        int f = f();
        return (f % 2 == 0 ? -1 : 1) * ((f + 1) / 2);
    }

    private int f() {
        int i = 0;
        int i2 = 0;
        while (!b()) {
            i2++;
        }
        int i3 = (1 << i2) - 1;
        if (i2 > 0) {
            i = c(i2);
        }
        return i3 + i;
    }

    private void g() {
        boolean z = this.b >= 0 && this.c >= 0 && this.c < 8 && (this.b < this.d || (this.b == this.d && this.c == 0));
        fe.b(z);
    }
}
