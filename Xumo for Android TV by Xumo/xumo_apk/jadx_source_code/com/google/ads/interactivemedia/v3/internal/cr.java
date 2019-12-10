package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: IMASDK */
final class cr {
    private static final int a = ft.c("ID3");
    private static final Charset[] b = new Charset[]{Charset.forName("ISO-8859-1"), Charset.forName("UTF-16LE"), Charset.forName("UTF-16BE"), Charset.forName("UTF-8")};

    public static cg a(cd cdVar) throws IOException, InterruptedException {
        fp fpVar = new fp(10);
        cg cgVar = null;
        int i = 0;
        while (true) {
            cdVar.c(fpVar.a, 0, 10);
            fpVar.c(0);
            if (fpVar.j() != a) {
                cdVar.a();
                cdVar.c(i);
                return cgVar;
            }
            int f = fpVar.f();
            int f2 = fpVar.f();
            int f3 = fpVar.f();
            int r = fpVar.r();
            if (cgVar == null && a(f, f2, f3, r)) {
                byte[] bArr = new byte[r];
                cdVar.c(bArr, 0, r);
                cgVar = a(new fp(bArr), f, f3);
            } else {
                cdVar.c(r);
            }
            i += r + 10;
        }
    }

    private static boolean a(int i, int i2, int i3, int i4) {
        return i2 != 255 && i >= 2 && i <= 4 && i4 <= 3145728 && ((i != 2 || ((i3 & 63) == 0 && (i3 & 64) == 0)) && ((i != 3 || (i3 & 31) == 0) && (i != 4 || (i3 & 15) == 0)));
    }

    private static cg a(fp fpVar, int i, int i2) {
        b(fpVar, i, i2);
        fpVar.c(0);
        if (i != 3 || (i2 & 64) == 0) {
            if (i == 4 && (i2 & 64) != 0) {
                if (fpVar.b() < 4) {
                    return null;
                }
                i2 = fpVar.r();
                if (i2 >= 6) {
                    if (i2 <= fpVar.b() + 4) {
                        fpVar.c(i2);
                    }
                }
                return null;
            }
        } else if (fpVar.b() < 4) {
            return null;
        } else {
            i2 = fpVar.s();
            if (i2 > fpVar.b()) {
                return null;
            }
            if (i2 >= 6) {
                fpVar.d(2);
                int s = fpVar.s();
                fpVar.c(4);
                fpVar.b(fpVar.c() - s);
                if (fpVar.b() < i2) {
                    return null;
                }
            }
            fpVar.d(i2);
        }
        while (true) {
            i2 = a(i, fpVar);
            if (i2 == 0) {
                return null;
            }
            if (((String) i2.first).length() > 3) {
                i2 = cg.a(((String) i2.first).substring(3), (String) i2.second);
                if (i2 != 0) {
                    return i2;
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.util.Pair<java.lang.String, java.lang.String> a(int r9, com.google.ads.interactivemedia.v3.internal.fp r10) {
        /*
    L_0x0000:
        r0 = 0;
        r1 = 3;
        r2 = 1;
        r3 = 2;
        r4 = 0;
        if (r9 != r3) goto L_0x0039;
    L_0x0007:
        r5 = r10.b();
        r6 = 6;
        if (r5 >= r6) goto L_0x000f;
    L_0x000e:
        return r4;
    L_0x000f:
        r5 = "US-ASCII";
        r5 = java.nio.charset.Charset.forName(r5);
        r1 = r10.a(r1, r5);
        r5 = "\u0000\u0000\u0000";
        r5 = r1.equals(r5);
        if (r5 == 0) goto L_0x0022;
    L_0x0021:
        return r4;
    L_0x0022:
        r5 = r10.j();
        if (r5 == 0) goto L_0x0038;
    L_0x0028:
        r6 = r10.b();
        if (r5 <= r6) goto L_0x002f;
    L_0x002e:
        goto L_0x0038;
    L_0x002f:
        r6 = "COM";
        r1 = r1.equals(r6);
        if (r1 == 0) goto L_0x00b2;
    L_0x0037:
        goto L_0x0089;
    L_0x0038:
        return r4;
    L_0x0039:
        r5 = r10.b();
        r6 = 10;
        if (r5 >= r6) goto L_0x0042;
    L_0x0041:
        return r4;
    L_0x0042:
        r5 = "US-ASCII";
        r5 = java.nio.charset.Charset.forName(r5);
        r6 = 4;
        r5 = r10.a(r6, r5);
        r7 = "\u0000\u0000\u0000\u0000";
        r7 = r5.equals(r7);
        if (r7 == 0) goto L_0x0056;
    L_0x0055:
        return r4;
    L_0x0056:
        if (r9 != r6) goto L_0x005d;
    L_0x0058:
        r7 = r10.r();
        goto L_0x0061;
    L_0x005d:
        r7 = r10.s();
    L_0x0061:
        if (r7 == 0) goto L_0x00b7;
    L_0x0063:
        r8 = r10.b();
        r8 = r8 - r3;
        if (r7 <= r8) goto L_0x006b;
    L_0x006a:
        goto L_0x00b7;
    L_0x006b:
        r8 = r10.g();
        if (r9 != r6) goto L_0x0075;
    L_0x0071:
        r6 = r8 & 12;
        if (r6 != 0) goto L_0x007b;
    L_0x0075:
        if (r9 != r1) goto L_0x007d;
    L_0x0077:
        r1 = r8 & 192;
        if (r1 == 0) goto L_0x007d;
    L_0x007b:
        r1 = 1;
        goto L_0x007e;
    L_0x007d:
        r1 = 0;
    L_0x007e:
        if (r1 != 0) goto L_0x00b1;
    L_0x0080:
        r1 = "COMM";
        r1 = r5.equals(r1);
        if (r1 == 0) goto L_0x00b1;
    L_0x0088:
        r5 = r7;
    L_0x0089:
        r9 = r10.f();
        if (r9 < 0) goto L_0x00b0;
    L_0x008f:
        r1 = b;
        r1 = r1.length;
        if (r9 < r1) goto L_0x0095;
    L_0x0094:
        goto L_0x00b0;
    L_0x0095:
        r1 = b;
        r9 = r1[r9];
        r5 = r5 - r2;
        r9 = r10.a(r5, r9);
        r10 = "\u0000";
        r9 = r9.split(r10);
        r10 = r9.length;
        if (r10 != r3) goto L_0x00af;
    L_0x00a7:
        r10 = r9[r0];
        r9 = r9[r2];
        r4 = android.util.Pair.create(r10, r9);
    L_0x00af:
        return r4;
    L_0x00b0:
        return r4;
    L_0x00b1:
        r5 = r7;
    L_0x00b2:
        r10.d(r5);
        goto L_0x0000;
    L_0x00b7:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.cr.a(int, com.google.ads.interactivemedia.v3.internal.fp):android.util.Pair<java.lang.String, java.lang.String>");
    }

    private static boolean b(fp fpVar, int i, int i2) {
        int i3 = 0;
        if (i != 4) {
            if ((i2 & 128) != 0) {
                i = fpVar.a;
                i2 = i.length;
                while (true) {
                    int i4 = i3 + 1;
                    if (i4 >= i2) {
                        break;
                    }
                    if ((i[i3] & 255) == 255 && i[i4] == (byte) 0) {
                        System.arraycopy(i, i3 + 2, i, i4, (i2 - i3) - 2);
                        i2--;
                    }
                    i3 = i4;
                }
                fpVar.b(i2);
            }
        } else if (a(fpVar, false) != 0) {
            b(fpVar, false);
        } else if (a(fpVar, true) == 0) {
            return false;
        } else {
            b(fpVar, true);
        }
        return true;
    }

    private static boolean a(fp fpVar, boolean z) {
        fpVar.c(0);
        while (fpVar.b() >= 10 && fpVar.m() != 0) {
            long k = fpVar.k();
            if (!z) {
                if ((8421504 & k) != 0) {
                    return false;
                }
                k = (((k >> 24) & 127) << 21) | (((k & 127) | (((k >> 8) & 127) << 7)) | (((k >> 16) & 127) << 14));
            }
            if (k > ((long) (fpVar.b() - 2))) {
                return false;
            }
            if ((1 & fpVar.g()) != 0 && fpVar.b() < 4) {
                return false;
            }
            fpVar.d((int) k);
        }
        return true;
    }

    private static void b(fp fpVar, boolean z) {
        fpVar.c(0);
        byte[] bArr = fpVar.a;
        while (fpVar.b() >= 10 && fpVar.m() != 0) {
            int d;
            int s = z ? fpVar.s() : fpVar.r();
            int g = fpVar.g();
            if ((g & 1) != 0) {
                d = fpVar.d();
                System.arraycopy(bArr, d + 4, bArr, d, fpVar.b() - 4);
                s -= 4;
                d = g & -2;
                fpVar.b(fpVar.c() - 4);
            } else {
                d = g;
            }
            if ((d & 2) != 0) {
                int d2 = fpVar.d() + 1;
                int i = d2;
                int i2 = s;
                s = 0;
                while (true) {
                    s++;
                    if (s >= i2) {
                        break;
                    }
                    if ((bArr[d2 - 1] & 255) == 255 && bArr[d2] == (byte) 0) {
                        d2++;
                        i2--;
                    }
                    int i3 = i + 1;
                    int i4 = d2 + 1;
                    bArr[i] = bArr[d2];
                    i = i3;
                    d2 = i4;
                }
                fpVar.b(fpVar.c() - (d2 - i));
                System.arraycopy(bArr, d2, bArr, i, fpVar.b() - d2);
                d &= -3;
                s = i2;
            }
            if (d != g || z) {
                g = fpVar.d() - 6;
                a(bArr, g, s);
                bArr[g + 4] = (byte) (d >> 8);
                bArr[g + 5] = (byte) (d & 255);
            }
            fpVar.d(s);
        }
    }

    private static void a(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) ((i2 >> 21) & 127);
        bArr[i + 1] = (byte) ((i2 >> 14) & 127);
        bArr[i + 2] = (byte) ((i2 >> 7) & 127);
        bArr[i + 3] = (byte) (i2 & 127);
    }
}
