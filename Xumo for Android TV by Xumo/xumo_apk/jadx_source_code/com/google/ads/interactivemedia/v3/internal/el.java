package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;

/* compiled from: IMASDK */
final class el {
    private final fp a = new fp(8);
    private int b;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(com.google.ads.interactivemedia.v3.internal.cd r19) throws java.io.IOException, java.lang.InterruptedException {
        /*
        r18 = this;
        r0 = r18;
        r1 = r19;
        r2 = r19.d();
        r4 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r6 = -1;
        r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x0016;
    L_0x0010:
        r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r8 <= 0) goto L_0x0015;
    L_0x0014:
        goto L_0x0016;
    L_0x0015:
        r4 = r2;
    L_0x0016:
        r4 = (int) r4;
        r5 = r0.a;
        r5 = r5.a;
        r8 = 4;
        r9 = 0;
        r1.c(r5, r9, r8);
        r5 = r0.a;
        r10 = r5.k();
        r0.b = r8;
    L_0x0028:
        r12 = 440786851; // 0x1a45dfa3 float:4.0919297E-23 double:2.1777764E-315;
        r5 = 1;
        r8 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
        if (r8 == 0) goto L_0x0050;
    L_0x0030:
        r8 = r0.b;
        r8 = r8 + r5;
        r0.b = r8;
        if (r8 != r4) goto L_0x0038;
    L_0x0037:
        return r9;
    L_0x0038:
        r8 = r0.a;
        r8 = r8.a;
        r1.c(r8, r9, r5);
        r5 = 8;
        r10 = r10 << r5;
        r12 = -256; // 0xffffffffffffff00 float:NaN double:NaN;
        r10 = r10 & r12;
        r5 = r0.a;
        r5 = r5.a;
        r5 = r5[r9];
        r5 = r5 & 255;
        r12 = (long) r5;
        r10 = r10 | r12;
        goto L_0x0028;
    L_0x0050:
        r10 = r18.b(r19);
        r4 = r0.b;
        r12 = (long) r4;
        r14 = -9223372036854775808;
        r4 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1));
        if (r4 == 0) goto L_0x00a7;
    L_0x005d:
        r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x0068;
    L_0x0061:
        r6 = r12 + r10;
        r4 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
        if (r4 < 0) goto L_0x0068;
    L_0x0067:
        goto L_0x00a7;
    L_0x0068:
        r2 = r0.b;
        r2 = (long) r2;
        r6 = r12 + r10;
        r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r4 >= 0) goto L_0x009d;
    L_0x0071:
        r2 = r18.b(r19);
        r4 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1));
        if (r4 != 0) goto L_0x007a;
    L_0x0079:
        return r9;
    L_0x007a:
        r2 = r18.b(r19);
        r6 = 0;
        r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r4 < 0) goto L_0x009c;
    L_0x0084:
        r16 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r4 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1));
        if (r4 <= 0) goto L_0x008c;
    L_0x008b:
        goto L_0x009c;
    L_0x008c:
        r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x0068;
    L_0x0090:
        r4 = (int) r2;
        r1.c(r4);
        r4 = r0.b;
        r6 = (long) r4;
        r6 = r6 + r2;
        r2 = (int) r6;
        r0.b = r2;
        goto L_0x0068;
    L_0x009c:
        return r9;
    L_0x009d:
        r1 = r0.b;
        r1 = (long) r1;
        r3 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
        if (r3 != 0) goto L_0x00a5;
    L_0x00a4:
        goto L_0x00a6;
    L_0x00a5:
        r5 = 0;
    L_0x00a6:
        return r5;
    L_0x00a7:
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.el.a(com.google.ads.interactivemedia.v3.internal.cd):boolean");
    }

    private long b(cd cdVar) throws IOException, InterruptedException {
        int i = 0;
        cdVar.c(this.a.a, 0, 1);
        int i2 = this.a.a[0] & 255;
        if (i2 == 0) {
            return Long.MIN_VALUE;
        }
        int i3 = 128;
        int i4 = 0;
        while ((i2 & i3) == 0) {
            i3 >>= 1;
            i4++;
        }
        i2 &= i3 ^ -1;
        cdVar.c(this.a.a, 1, i4);
        while (i < i4) {
            i++;
            i2 = (this.a.a[i] & 255) + (i2 << 8);
        }
        this.b += i4 + 1;
        return (long) i2;
    }
}
