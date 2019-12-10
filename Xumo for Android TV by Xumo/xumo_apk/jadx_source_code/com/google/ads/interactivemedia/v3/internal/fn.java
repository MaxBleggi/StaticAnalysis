package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* compiled from: IMASDK */
public final class fn {
    public static final byte[] a = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 1};
    public static final float[] b = new float[]{1.0f, 1.0f, 1.0909091f, 0.90909094f, 1.4545455f, 1.2121212f, 2.1818182f, 1.8181819f, 2.909091f, 2.4242425f, 1.6363636f, 1.3636364f, 1.939394f, 1.6161616f, 1.3333334f, 1.5f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT};
    private static final Object c = new Object();
    private static int[] d = new int[10];

    /* compiled from: IMASDK */
    public static final class a {
        public final int a;
        public final int b;
        public final boolean c;

        public a(int i, int i2, boolean z) {
            this.a = i;
            this.b = i2;
            this.c = z;
        }
    }

    /* compiled from: IMASDK */
    public static final class b {
        public final int a;
        public final int b;
        public final int c;
        public final float d;
        public final boolean e;
        public final boolean f;
        public final int g;
        public final int h;
        public final int i;
        public final boolean j;

        public b(int i, int i2, int i3, float f, boolean z, boolean z2, int i4, int i5, int i6, boolean z3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = f;
            this.e = z;
            this.f = z2;
            this.g = i4;
            this.h = i5;
            this.i = i6;
            this.j = z3;
        }
    }

    public static int a(byte[] bArr, int i) {
        synchronized (c) {
            int i2;
            int i3 = 0;
            int i4 = 0;
            while (i3 < i) {
                i3 = a(bArr, i3, i);
                if (i3 < i) {
                    if (d.length <= i4) {
                        d = Arrays.copyOf(d, d.length * 2);
                    }
                    i2 = i4 + 1;
                    d[i4] = i3;
                    i3 += 3;
                    i4 = i2;
                }
            }
            i -= i4;
            int i5 = 0;
            i2 = 0;
            for (i3 = 0; i3 < i4; i3++) {
                int i6 = d[i3] - i2;
                System.arraycopy(bArr, i2, bArr, i5, i6);
                i5 += i6;
                int i7 = i5 + 1;
                bArr[i5] = (byte) 0;
                i5 = i7 + 1;
                bArr[i7] = (byte) 0;
                i2 += i6 + 3;
            }
            System.arraycopy(bArr, i2, bArr, i5, i - i5);
        }
        return i;
    }

    public static void a(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i + 1;
            if (i3 < position) {
                int i4 = byteBuffer.get(i) & 255;
                if (i2 == 3) {
                    if (i4 == 1 && (byteBuffer.get(i3) & 31) == 7) {
                        ByteBuffer duplicate = byteBuffer.duplicate();
                        duplicate.position(i - 3);
                        duplicate.limit(position);
                        byteBuffer.position(0);
                        byteBuffer.put(duplicate);
                        return;
                    }
                } else if (i4 == 0) {
                    i2++;
                }
                if (i4 != 0) {
                    i2 = 0;
                }
                i = i3;
            } else {
                byteBuffer.clear();
                return;
            }
        }
    }

    public static byte[] a(fp fpVar) {
        int g = fpVar.g();
        int d = fpVar.d();
        fpVar.d(g);
        return ff.a(fpVar.a, d, g);
    }

    public static int b(byte[] bArr, int i) {
        return bArr[i + 3] & 31;
    }

    public static int c(byte[] bArr, int i) {
        return (bArr[i + 3] & 126) >> 1;
    }

    public static b a(fo foVar) {
        boolean z;
        int d;
        int d2;
        int i;
        int d3;
        boolean b;
        long d4;
        int i2;
        boolean z2;
        int d5;
        boolean b2;
        int i3;
        int d6;
        int d7;
        int d8;
        int i4;
        int i5;
        float f;
        int c;
        int c2;
        float f2;
        StringBuilder stringBuilder;
        fo foVar2 = foVar;
        int c3 = foVar2.c(8);
        foVar2.b(16);
        int d9 = foVar.d();
        int i6 = 1;
        if (!(c3 == 100 || c3 == 110 || c3 == 122 || c3 == 244 || c3 == 44 || c3 == 83 || c3 == 86 || c3 == 118 || c3 == 128)) {
            if (c3 != TsExtractor.TS_STREAM_TYPE_DTS) {
                c3 = 1;
                z = false;
                d = foVar.d() + 4;
                d2 = foVar.d();
                if (d2 == 0) {
                    i = d9;
                    d3 = foVar.d() + 4;
                } else if (d2 != 1) {
                    b = foVar.b();
                    foVar.e();
                    foVar.e();
                    d4 = (long) foVar.d();
                    i = d9;
                    for (i2 = 0; ((long) i2) < d4; i2++) {
                        foVar.d();
                    }
                    z2 = b;
                    d3 = 0;
                    foVar.d();
                    foVar2.b(1);
                    d5 = foVar.d() + 1;
                    d9 = foVar.d() + 1;
                    b2 = foVar.b();
                    i3 = (2 - b2) * d9;
                    if (!b2) {
                        foVar2.b(1);
                    }
                    foVar2.b(1);
                    d5 *= 16;
                    i3 *= 16;
                    if (foVar.b()) {
                        d9 = foVar.d();
                        d6 = foVar.d();
                        d7 = foVar.d();
                        d8 = foVar.d();
                        if (c3 != 0) {
                            i4 = 2 - b2;
                            i5 = 1;
                        } else {
                            i5 = c3 != 3 ? 1 : 2;
                            if (c3 == 1) {
                                i6 = 2;
                            }
                            i4 = (2 - b2) * i6;
                        }
                        d5 -= (d9 + d6) * i5;
                        i3 -= (d7 + d8) * i4;
                    }
                    i4 = d5;
                    i6 = i3;
                    f = 1.0f;
                    if (foVar.b() && foVar.b()) {
                        c = foVar2.c(8);
                        if (c != 255) {
                            c = foVar2.c(16);
                            c2 = foVar2.c(16);
                            if (!(c == 0 || c2 == 0)) {
                                f = ((float) c) / ((float) c2);
                            }
                            f2 = f;
                        } else if (c >= b.length) {
                            f2 = b[c];
                        } else {
                            stringBuilder = new StringBuilder(46);
                            stringBuilder.append("Unexpected aspect_ratio_idc value: ");
                            stringBuilder.append(c);
                            Log.w("NalUnitUtil", stringBuilder.toString());
                        }
                        return new b(i, i4, i6, f2, z, b2, d, d2, d3, z2);
                    }
                    f2 = 1.0f;
                    return new b(i, i4, i6, f2, z, b2, d, d2, d3, z2);
                } else {
                    i = d9;
                    d3 = 0;
                }
                z2 = false;
                foVar.d();
                foVar2.b(1);
                d5 = foVar.d() + 1;
                d9 = foVar.d() + 1;
                b2 = foVar.b();
                i3 = (2 - b2) * d9;
                if (b2) {
                    foVar2.b(1);
                }
                foVar2.b(1);
                d5 *= 16;
                i3 *= 16;
                if (foVar.b()) {
                    d9 = foVar.d();
                    d6 = foVar.d();
                    d7 = foVar.d();
                    d8 = foVar.d();
                    if (c3 != 0) {
                        if (c3 != 3) {
                        }
                        if (c3 == 1) {
                            i6 = 2;
                        }
                        i4 = (2 - b2) * i6;
                    } else {
                        i4 = 2 - b2;
                        i5 = 1;
                    }
                    d5 -= (d9 + d6) * i5;
                    i3 -= (d7 + d8) * i4;
                }
                i4 = d5;
                i6 = i3;
                f = 1.0f;
                c = foVar2.c(8);
                if (c != 255) {
                    c = foVar2.c(16);
                    c2 = foVar2.c(16);
                    f = ((float) c) / ((float) c2);
                    f2 = f;
                } else if (c >= b.length) {
                    stringBuilder = new StringBuilder(46);
                    stringBuilder.append("Unexpected aspect_ratio_idc value: ");
                    stringBuilder.append(c);
                    Log.w("NalUnitUtil", stringBuilder.toString());
                    f2 = 1.0f;
                } else {
                    f2 = b[c];
                }
                return new b(i, i4, i6, f2, z, b2, d, d2, d3, z2);
            }
        }
        c3 = foVar.d();
        b = c3 == 3 ? foVar.b() : false;
        foVar.d();
        foVar.d();
        foVar2.b(1);
        if (foVar.b()) {
            int i7 = c3 != 3 ? 8 : 12;
            i2 = 0;
            while (i2 < i7) {
                if (foVar.b()) {
                    a(foVar2, i2 < 6 ? 16 : 64);
                }
                i2++;
            }
        }
        z = b;
        d = foVar.d() + 4;
        d2 = foVar.d();
        if (d2 == 0) {
            i = d9;
            d3 = foVar.d() + 4;
        } else if (d2 != 1) {
            i = d9;
            d3 = 0;
        } else {
            b = foVar.b();
            foVar.e();
            foVar.e();
            d4 = (long) foVar.d();
            i = d9;
            for (i2 = 0; ((long) i2) < d4; i2++) {
                foVar.d();
            }
            z2 = b;
            d3 = 0;
            foVar.d();
            foVar2.b(1);
            d5 = foVar.d() + 1;
            d9 = foVar.d() + 1;
            b2 = foVar.b();
            i3 = (2 - b2) * d9;
            if (b2) {
                foVar2.b(1);
            }
            foVar2.b(1);
            d5 *= 16;
            i3 *= 16;
            if (foVar.b()) {
                d9 = foVar.d();
                d6 = foVar.d();
                d7 = foVar.d();
                d8 = foVar.d();
                if (c3 != 0) {
                    i4 = 2 - b2;
                    i5 = 1;
                } else {
                    if (c3 != 3) {
                    }
                    if (c3 == 1) {
                        i6 = 2;
                    }
                    i4 = (2 - b2) * i6;
                }
                d5 -= (d9 + d6) * i5;
                i3 -= (d7 + d8) * i4;
            }
            i4 = d5;
            i6 = i3;
            f = 1.0f;
            c = foVar2.c(8);
            if (c != 255) {
                c = foVar2.c(16);
                c2 = foVar2.c(16);
                f = ((float) c) / ((float) c2);
                f2 = f;
            } else if (c >= b.length) {
                f2 = b[c];
            } else {
                stringBuilder = new StringBuilder(46);
                stringBuilder.append("Unexpected aspect_ratio_idc value: ");
                stringBuilder.append(c);
                Log.w("NalUnitUtil", stringBuilder.toString());
                f2 = 1.0f;
            }
            return new b(i, i4, i6, f2, z, b2, d, d2, d3, z2);
        }
        z2 = false;
        foVar.d();
        foVar2.b(1);
        d5 = foVar.d() + 1;
        d9 = foVar.d() + 1;
        b2 = foVar.b();
        i3 = (2 - b2) * d9;
        if (b2) {
            foVar2.b(1);
        }
        foVar2.b(1);
        d5 *= 16;
        i3 *= 16;
        if (foVar.b()) {
            d9 = foVar.d();
            d6 = foVar.d();
            d7 = foVar.d();
            d8 = foVar.d();
            if (c3 != 0) {
                if (c3 != 3) {
                }
                if (c3 == 1) {
                    i6 = 2;
                }
                i4 = (2 - b2) * i6;
            } else {
                i4 = 2 - b2;
                i5 = 1;
            }
            d5 -= (d9 + d6) * i5;
            i3 -= (d7 + d8) * i4;
        }
        i4 = d5;
        i6 = i3;
        f = 1.0f;
        c = foVar2.c(8);
        if (c != 255) {
            c = foVar2.c(16);
            c2 = foVar2.c(16);
            f = ((float) c) / ((float) c2);
            f2 = f;
        } else if (c >= b.length) {
            stringBuilder = new StringBuilder(46);
            stringBuilder.append("Unexpected aspect_ratio_idc value: ");
            stringBuilder.append(c);
            Log.w("NalUnitUtil", stringBuilder.toString());
            f2 = 1.0f;
        } else {
            f2 = b[c];
        }
        return new b(i, i4, i6, f2, z, b2, d, d2, d3, z2);
    }

    public static a b(fo foVar) {
        int d = foVar.d();
        int d2 = foVar.d();
        foVar.b(1);
        return new a(d, d2, foVar.b());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(byte[] r7, int r8, int r9, boolean[] r10) {
        /*
        r0 = r9 - r8;
        r1 = 0;
        r2 = 1;
        if (r0 < 0) goto L_0x0008;
    L_0x0006:
        r3 = 1;
        goto L_0x0009;
    L_0x0008:
        r3 = 0;
    L_0x0009:
        com.google.ads.interactivemedia.v3.internal.fe.b(r3);
        if (r0 != 0) goto L_0x000f;
    L_0x000e:
        return r9;
    L_0x000f:
        r3 = 2;
        if (r10 == 0) goto L_0x0040;
    L_0x0012:
        r4 = r10[r1];
        if (r4 == 0) goto L_0x001c;
    L_0x0016:
        a(r10);
        r8 = r8 + -3;
        return r8;
    L_0x001c:
        if (r0 <= r2) goto L_0x002b;
    L_0x001e:
        r4 = r10[r2];
        if (r4 == 0) goto L_0x002b;
    L_0x0022:
        r4 = r7[r8];
        if (r4 != r2) goto L_0x002b;
    L_0x0026:
        a(r10);
        r8 = r8 - r3;
        return r8;
    L_0x002b:
        if (r0 <= r3) goto L_0x0040;
    L_0x002d:
        r4 = r10[r3];
        if (r4 == 0) goto L_0x0040;
    L_0x0031:
        r4 = r7[r8];
        if (r4 != 0) goto L_0x0040;
    L_0x0035:
        r4 = r8 + 1;
        r4 = r7[r4];
        if (r4 != r2) goto L_0x0040;
    L_0x003b:
        a(r10);
        r8 = r8 - r2;
        return r8;
    L_0x0040:
        r4 = r9 + -1;
        r8 = r8 + r3;
    L_0x0043:
        if (r8 >= r4) goto L_0x0067;
    L_0x0045:
        r5 = r7[r8];
        r5 = r5 & 254;
        if (r5 == 0) goto L_0x004c;
    L_0x004b:
        goto L_0x0064;
    L_0x004c:
        r5 = r8 + -2;
        r6 = r7[r5];
        if (r6 != 0) goto L_0x0062;
    L_0x0052:
        r6 = r8 + -1;
        r6 = r7[r6];
        if (r6 != 0) goto L_0x0062;
    L_0x0058:
        r6 = r7[r8];
        if (r6 != r2) goto L_0x0062;
    L_0x005c:
        if (r10 == 0) goto L_0x0061;
    L_0x005e:
        a(r10);
    L_0x0061:
        return r5;
    L_0x0062:
        r8 = r8 + -2;
    L_0x0064:
        r8 = r8 + 3;
        goto L_0x0043;
    L_0x0067:
        if (r10 == 0) goto L_0x00bd;
    L_0x0069:
        if (r0 <= r3) goto L_0x007f;
    L_0x006b:
        r8 = r9 + -3;
        r8 = r7[r8];
        if (r8 != 0) goto L_0x007d;
    L_0x0071:
        r8 = r9 + -2;
        r8 = r7[r8];
        if (r8 != 0) goto L_0x007d;
    L_0x0077:
        r8 = r7[r4];
        if (r8 != r2) goto L_0x007d;
    L_0x007b:
        r8 = 1;
        goto L_0x0099;
    L_0x007d:
        r8 = 0;
        goto L_0x0099;
    L_0x007f:
        if (r0 != r3) goto L_0x0090;
    L_0x0081:
        r8 = r10[r3];
        if (r8 == 0) goto L_0x007d;
    L_0x0085:
        r8 = r9 + -2;
        r8 = r7[r8];
        if (r8 != 0) goto L_0x007d;
    L_0x008b:
        r8 = r7[r4];
        if (r8 != r2) goto L_0x007d;
    L_0x008f:
        goto L_0x007b;
    L_0x0090:
        r8 = r10[r2];
        if (r8 == 0) goto L_0x007d;
    L_0x0094:
        r8 = r7[r4];
        if (r8 != r2) goto L_0x007d;
    L_0x0098:
        goto L_0x007b;
    L_0x0099:
        r10[r1] = r8;
        if (r0 <= r2) goto L_0x00ab;
    L_0x009d:
        r8 = r9 + -2;
        r8 = r7[r8];
        if (r8 != 0) goto L_0x00a9;
    L_0x00a3:
        r8 = r7[r4];
        if (r8 != 0) goto L_0x00a9;
    L_0x00a7:
        r8 = 1;
        goto L_0x00b4;
    L_0x00a9:
        r8 = 0;
        goto L_0x00b4;
    L_0x00ab:
        r8 = r10[r3];
        if (r8 == 0) goto L_0x00a9;
    L_0x00af:
        r8 = r7[r4];
        if (r8 != 0) goto L_0x00a9;
    L_0x00b3:
        goto L_0x00a7;
    L_0x00b4:
        r10[r2] = r8;
        r7 = r7[r4];
        if (r7 != 0) goto L_0x00bb;
    L_0x00ba:
        r1 = 1;
    L_0x00bb:
        r10[r3] = r1;
    L_0x00bd:
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.fn.a(byte[], int, int, boolean[]):int");
    }

    public static void a(boolean[] zArr) {
        zArr[0] = false;
        zArr[1] = false;
        zArr[2] = false;
    }

    private static int a(byte[] bArr, int i, int i2) {
        while (i < i2 - 2) {
            if (bArr[i] == (byte) 0 && bArr[i + 1] == (byte) 0 && bArr[i + 2] == (byte) 3) {
                return i;
            }
            i++;
        }
        return i2;
    }

    private static void a(fo foVar, int i) {
        int i2 = 8;
        int i3 = 8;
        for (int i4 = 0; i4 < i; i4++) {
            if (i2 != 0) {
                i2 = ((foVar.e() + i3) + 256) % 256;
            }
            if (i2 != 0) {
                i3 = i2;
            }
        }
    }
}
