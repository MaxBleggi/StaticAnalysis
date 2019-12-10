package com.google.ads.interactivemedia.v3.internal;

import java.nio.charset.Charset;

/* compiled from: IMASDK */
public final class fp {
    public byte[] a;
    private int b;
    private int c;

    public fp(int i) {
        this.a = new byte[i];
        this.c = this.a.length;
    }

    public fp(byte[] bArr) {
        this.a = bArr;
        this.c = bArr.length;
    }

    public fp(byte[] bArr, int i) {
        this.a = bArr;
        this.c = i;
    }

    public void a(int i) {
        a(e() < i ? new byte[i] : this.a, i);
    }

    public void a(byte[] bArr, int i) {
        this.a = bArr;
        this.c = i;
        this.b = null;
    }

    public void a() {
        this.b = 0;
        this.c = 0;
    }

    public int b() {
        return this.c - this.b;
    }

    public int c() {
        return this.c;
    }

    public void b(int i) {
        boolean z = i >= 0 && i <= this.a.length;
        fe.a(z);
        this.c = i;
    }

    public int d() {
        return this.b;
    }

    public int e() {
        return this.a == null ? 0 : this.a.length;
    }

    public void c(int i) {
        boolean z = i >= 0 && i <= this.c;
        fe.a(z);
        this.b = i;
    }

    public void d(int i) {
        c(this.b + i);
    }

    public void a(fo foVar, int i) {
        a(foVar.a, 0, i);
        foVar.a(0);
    }

    public void a(byte[] bArr, int i, int i2) {
        System.arraycopy(this.a, this.b, bArr, i, i2);
        this.b += i2;
    }

    public int f() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        return bArr[i] & 255;
    }

    public int g() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = (bArr[i] & 255) << 8;
        byte[] bArr2 = this.a;
        int i3 = this.b;
        this.b = i3 + 1;
        return i2 | (bArr2[i3] & 255);
    }

    public int h() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = bArr[i] & 255;
        byte[] bArr2 = this.a;
        int i3 = this.b;
        this.b = i3 + 1;
        return i2 | ((bArr2[i3] & 255) << 8);
    }

    public short i() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = (bArr[i] & 255) << 8;
        byte[] bArr2 = this.a;
        int i3 = this.b;
        this.b = i3 + 1;
        return (short) (i2 | (bArr2[i3] & 255));
    }

    public int j() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = (bArr[i] & 255) << 16;
        byte[] bArr2 = this.a;
        int i3 = this.b;
        this.b = i3 + 1;
        i2 |= (bArr2[i3] & 255) << 8;
        bArr2 = this.a;
        i3 = this.b;
        this.b = i3 + 1;
        return i2 | (bArr2[i3] & 255);
    }

    public long k() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        long j = (((long) bArr[i]) & 255) << 24;
        byte[] bArr2 = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 16;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 8;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        return j | (255 & ((long) bArr2[i2]));
    }

    public long l() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        long j = ((long) bArr[i]) & 255;
        byte[] bArr2 = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 8;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 16;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        return j | ((255 & ((long) bArr2[i2])) << 24);
    }

    public int m() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = (bArr[i] & 255) << 24;
        byte[] bArr2 = this.a;
        int i3 = this.b;
        this.b = i3 + 1;
        i2 |= (bArr2[i3] & 255) << 16;
        bArr2 = this.a;
        i3 = this.b;
        this.b = i3 + 1;
        i2 |= (bArr2[i3] & 255) << 8;
        bArr2 = this.a;
        i3 = this.b;
        this.b = i3 + 1;
        return i2 | (bArr2[i3] & 255);
    }

    public int n() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = bArr[i] & 255;
        byte[] bArr2 = this.a;
        int i3 = this.b;
        this.b = i3 + 1;
        i2 |= (bArr2[i3] & 255) << 8;
        bArr2 = this.a;
        i3 = this.b;
        this.b = i3 + 1;
        i2 |= (bArr2[i3] & 255) << 16;
        bArr2 = this.a;
        i3 = this.b;
        this.b = i3 + 1;
        return i2 | ((bArr2[i3] & 255) << 24);
    }

    public long o() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        long j = (((long) bArr[i]) & 255) << 56;
        byte[] bArr2 = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 48;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 40;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 32;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 24;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 16;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 8;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        return j | (255 & ((long) bArr2[i2]));
    }

    public long p() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        long j = ((long) bArr[i]) & 255;
        byte[] bArr2 = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 8;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 16;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 24;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 32;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 40;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        j |= (((long) bArr2[i2]) & 255) << 48;
        bArr2 = this.a;
        i2 = this.b;
        this.b = i2 + 1;
        return j | ((255 & ((long) bArr2[i2])) << 56);
    }

    public int q() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = (bArr[i] & 255) << 8;
        byte[] bArr2 = this.a;
        int i3 = this.b;
        this.b = i3 + 1;
        i2 |= bArr2[i3] & 255;
        this.b += 2;
        return i2;
    }

    public int r() {
        return (((f() << 21) | (f() << 14)) | (f() << 7)) | f();
    }

    public int s() {
        int m = m();
        if (m >= 0) {
            return m;
        }
        StringBuilder stringBuilder = new StringBuilder(29);
        stringBuilder.append("Top bit not zero: ");
        stringBuilder.append(m);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int t() {
        int n = n();
        if (n >= 0) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder(29);
        stringBuilder.append("Top bit not zero: ");
        stringBuilder.append(n);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public long u() {
        long o = o();
        if (o >= 0) {
            return o;
        }
        StringBuilder stringBuilder = new StringBuilder(38);
        stringBuilder.append("Top bit not zero: ");
        stringBuilder.append(o);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public double v() {
        return Double.longBitsToDouble(o());
    }

    public String e(int i) {
        return a(i, Charset.defaultCharset());
    }

    public String a(int i, Charset charset) {
        String str = new String(this.a, this.b, i, charset);
        this.b += i;
        return str;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long w() {
        /*
        r12 = this;
        r0 = r12.a;
        r1 = r12.b;
        r0 = r0[r1];
        r0 = (long) r0;
        r2 = 7;
        r3 = 7;
    L_0x0009:
        r4 = 6;
        r5 = 1;
        if (r3 < 0) goto L_0x0025;
    L_0x000d:
        r6 = r5 << r3;
        r7 = (long) r6;
        r7 = r7 & r0;
        r9 = 0;
        r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1));
        if (r11 != 0) goto L_0x0022;
    L_0x0017:
        if (r3 >= r4) goto L_0x001e;
    L_0x0019:
        r6 = r6 - r5;
        r6 = (long) r6;
        r0 = r0 & r6;
        r2 = r2 - r3;
        goto L_0x0026;
    L_0x001e:
        if (r3 != r2) goto L_0x0025;
    L_0x0020:
        r2 = 1;
        goto L_0x0026;
    L_0x0022:
        r3 = r3 + -1;
        goto L_0x0009;
    L_0x0025:
        r2 = 0;
    L_0x0026:
        if (r2 == 0) goto L_0x005e;
    L_0x0028:
        if (r5 >= r2) goto L_0x0058;
    L_0x002a:
        r3 = r12.a;
        r6 = r12.b;
        r6 = r6 + r5;
        r3 = r3[r6];
        r6 = r3 & 192;
        r7 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        if (r6 != r7) goto L_0x003f;
    L_0x0037:
        r0 = r0 << r4;
        r3 = r3 & 63;
        r6 = (long) r3;
        r0 = r0 | r6;
        r5 = r5 + 1;
        goto L_0x0028;
    L_0x003f:
        r2 = new java.lang.NumberFormatException;
        r3 = 62;
        r4 = new java.lang.StringBuilder;
        r4.<init>(r3);
        r3 = "Invalid UTF-8 sequence continuation byte: ";
        r4.append(r3);
        r4.append(r0);
        r0 = r4.toString();
        r2.<init>(r0);
        throw r2;
    L_0x0058:
        r3 = r12.b;
        r3 = r3 + r2;
        r12.b = r3;
        return r0;
    L_0x005e:
        r2 = new java.lang.NumberFormatException;
        r3 = 55;
        r4 = new java.lang.StringBuilder;
        r4.<init>(r3);
        r3 = "Invalid UTF-8 sequence first byte: ";
        r4.append(r3);
        r4.append(r0);
        r0 = r4.toString();
        r2.<init>(r0);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.fp.w():long");
    }
}
