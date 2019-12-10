package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class dn {
    public final byte[] a;
    private int b;
    private int c;
    private int d;

    public dn(byte[] bArr) {
        this(bArr, bArr.length);
    }

    public dn(byte[] bArr, int i) {
        this.a = bArr;
        this.b = i * 8;
    }

    public boolean a() {
        return a(1) == 1;
    }

    public int a(int i) {
        int i2 = 0;
        fe.b(b() + i <= this.b);
        if (i == 0) {
            return 0;
        }
        int min;
        int i3;
        if (this.d != 0) {
            min = Math.min(i, 8 - this.d);
            i3 = (255 >>> (8 - min)) & (this.a[this.c] >>> this.d);
            this.d += min;
            if (this.d == 8) {
                this.c++;
                this.d = 0;
            }
        } else {
            min = 0;
            i3 = 0;
        }
        int i4 = i - min;
        if (i4 > 7) {
            i4 /= 8;
            while (i2 < i4) {
                long j = (long) i3;
                byte[] bArr = this.a;
                int i5 = this.c;
                this.c = i5 + 1;
                i3 = (int) (j | ((((long) bArr[i5]) & 255) << min));
                min += 8;
                i2++;
            }
        }
        if (i > min) {
            i -= min;
            i3 |= ((255 >>> (8 - i)) & this.a[this.c]) << min;
            this.d += i;
        }
        return i3;
    }

    public void b(int i) {
        fe.b(b() + i <= this.b);
        this.c += i / 8;
        this.d += i % 8;
        if (this.d > 7) {
            this.c++;
            this.d -= 8;
        }
    }

    public int b() {
        return (this.c * 8) + this.d;
    }
}
