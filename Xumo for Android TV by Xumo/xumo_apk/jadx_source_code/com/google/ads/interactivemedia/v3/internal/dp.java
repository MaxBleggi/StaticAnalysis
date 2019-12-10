package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.gms.cast.MediaLoadOptions;
import java.util.Arrays;

/* compiled from: IMASDK */
final class dp {

    /* compiled from: IMASDK */
    public static final class a {
        public final int a;
        public final int b;
        public final long[] c;
        public final int d;
        public final boolean e;

        public a(int i, int i2, long[] jArr, int i3, boolean z) {
            this.a = i;
            this.b = i2;
            this.c = jArr;
            this.d = i3;
            this.e = z;
        }
    }

    /* compiled from: IMASDK */
    public static final class b {
        public final String a;
        public final String[] b;
        public final int c;

        public b(String str, String[] strArr, int i) {
            this.a = str;
            this.b = strArr;
            this.c = i;
        }
    }

    /* compiled from: IMASDK */
    public static final class c {
        public final boolean a;
        public final int b;
        public final int c;
        public final int d;

        public c(boolean z, int i, int i2, int i3) {
            this.a = z;
            this.b = i;
            this.c = i2;
            this.d = i3;
        }
    }

    /* compiled from: IMASDK */
    public static final class d {
        public final long a;
        public final int b;
        public final long c;
        public final int d;
        public final int e;
        public final int f;
        public final int g;
        public final int h;
        public final boolean i;
        public final byte[] j;

        public d(long j, int i, long j2, int i2, int i3, int i4, int i5, int i6, boolean z, byte[] bArr) {
            this.a = j;
            this.b = i;
            this.c = j2;
            this.d = i2;
            this.e = i3;
            this.f = i4;
            this.g = i5;
            this.h = i6;
            this.i = z;
            this.j = bArr;
        }
    }

    public static int a(int i) {
        int i2 = 0;
        while (i > 0) {
            i2++;
            i >>>= 1;
        }
        return i2;
    }

    public static d a(fp fpVar) throws bl {
        fp fpVar2 = fpVar;
        a(1, fpVar2, false);
        long l = fpVar.l();
        int f = fpVar.f();
        long l2 = fpVar.l();
        int n = fpVar.n();
        int n2 = fpVar.n();
        int n3 = fpVar.n();
        int f2 = fpVar.f();
        return new d(l, f, l2, n, n2, n3, (int) Math.pow(MediaLoadOptions.PLAYBACK_RATE_MAX, (double) (f2 & 15)), (int) Math.pow(MediaLoadOptions.PLAYBACK_RATE_MAX, (double) ((f2 & PsExtractor.VIDEO_STREAM_MASK) >> 4)), (fpVar.f() & 1) > 0, Arrays.copyOf(fpVar2.a, fpVar.c()));
    }

    public static b b(fp fpVar) throws bl {
        int i = 0;
        a(3, fpVar, false);
        String e = fpVar.e((int) fpVar.l());
        int length = 11 + e.length();
        long l = fpVar.l();
        String[] strArr = new String[((int) l)];
        length += 4;
        while (((long) i) < l) {
            length += 4;
            strArr[i] = fpVar.e((int) fpVar.l());
            length += strArr[i].length();
            i++;
        }
        if ((fpVar.f() & 1) != null) {
            return new b(e, strArr, length + 1);
        }
        throw new bl("framing bit expected to be set");
    }

    public static boolean a(int i, fp fpVar, boolean z) throws bl {
        if (fpVar.f() == i) {
            if (fpVar.f() == 118 && fpVar.f() == 111 && fpVar.f() == 114 && fpVar.f() == 98 && fpVar.f() == 105) {
                if (fpVar.f() == 115) {
                    return true;
                }
            }
            if (z) {
                return false;
            }
            throw new bl("expected characters 'vorbis'");
        } else if (z) {
            return false;
        } else {
            z = "expected header type ";
            i = String.valueOf(Integer.toHexString(i));
            throw new bl(i.length() != 0 ? z.concat(i) : new String(z));
        }
    }

    public static c[] a(fp fpVar, int i) throws bl {
        int i2 = 0;
        a(5, fpVar, false);
        int f = fpVar.f() + 1;
        dn dnVar = new dn(fpVar.a);
        dnVar.b(fpVar.d() * 8);
        for (fpVar = null; fpVar < f; fpVar++) {
            d(dnVar);
        }
        fpVar = dnVar.a(6) + 1;
        while (i2 < fpVar) {
            if (dnVar.a(16) == 0) {
                i2++;
            } else {
                throw new bl("placeholder of time domain transforms not zeroed out");
            }
        }
        c(dnVar);
        b(dnVar);
        a(i, dnVar);
        fpVar = a(dnVar);
        if (dnVar.a() != 0) {
            return fpVar;
        }
        throw new bl("framing bit after modes not set as expected");
    }

    private static c[] a(dn dnVar) {
        int a = dnVar.a(6) + 1;
        c[] cVarArr = new c[a];
        for (int i = 0; i < a; i++) {
            cVarArr[i] = new c(dnVar.a(), dnVar.a(16), dnVar.a(16), dnVar.a(8));
        }
        return cVarArr;
    }

    private static void a(int i, dn dnVar) throws bl {
        int a = dnVar.a(6) + 1;
        for (int i2 = 0; i2 < a; i2++) {
            int a2 = dnVar.a(16);
            if (a2 != 0) {
                StringBuilder stringBuilder = new StringBuilder(52);
                stringBuilder.append("mapping type other than 0 not supported: ");
                stringBuilder.append(a2);
                Log.e("VorbisUtil", stringBuilder.toString());
            } else {
                int a3;
                a2 = dnVar.a() ? dnVar.a(4) + 1 : 1;
                if (dnVar.a()) {
                    a3 = dnVar.a(8) + 1;
                    for (int i3 = 0; i3 < a3; i3++) {
                        int i4 = i - 1;
                        dnVar.b(a(i4));
                        dnVar.b(a(i4));
                    }
                }
                if (dnVar.a(2) == 0) {
                    if (a2 > 1) {
                        for (a3 = 0; a3 < i; a3++) {
                            dnVar.b(4);
                        }
                    }
                    for (int i5 = 0; i5 < a2; i5++) {
                        dnVar.b(8);
                        dnVar.b(8);
                        dnVar.b(8);
                    }
                } else {
                    throw new bl("to reserved bits must be zero after mapping coupling steps");
                }
            }
        }
    }

    private static void b(dn dnVar) throws bl {
        int a = dnVar.a(6) + 1;
        int i = 0;
        while (i < a) {
            if (dnVar.a(16) <= 2) {
                int i2;
                dnVar.b(24);
                dnVar.b(24);
                dnVar.b(24);
                int a2 = dnVar.a(6) + 1;
                dnVar.b(8);
                int[] iArr = new int[a2];
                for (i2 = 0; i2 < a2; i2++) {
                    iArr[i2] = ((dnVar.a() ? dnVar.a(5) : 0) * 8) + dnVar.a(3);
                }
                for (i2 = 0; i2 < a2; i2++) {
                    for (int i3 = 0; i3 < 8; i3++) {
                        if ((iArr[i2] & (1 << i3)) != 0) {
                            dnVar.b(8);
                        }
                    }
                }
                i++;
            } else {
                throw new bl("residueType greater than 2 is not decodable");
            }
        }
    }

    private static void c(dn dnVar) throws bl {
        int a = dnVar.a(6) + 1;
        for (int i = 0; i < a; i++) {
            int a2 = dnVar.a(16);
            int a3;
            switch (a2) {
                case 0:
                    dnVar.b(8);
                    dnVar.b(16);
                    dnVar.b(16);
                    dnVar.b(6);
                    dnVar.b(8);
                    a3 = dnVar.a(4) + 1;
                    for (a2 = 0; a2 < a3; a2++) {
                        dnVar.b(8);
                    }
                    break;
                case 1:
                    int a4;
                    a3 = dnVar.a(5);
                    int[] iArr = new int[a3];
                    int i2 = -1;
                    for (a2 = 0; a2 < a3; a2++) {
                        iArr[a2] = dnVar.a(4);
                        if (iArr[a2] > i2) {
                            i2 = iArr[a2];
                        }
                    }
                    int[] iArr2 = new int[(i2 + 1)];
                    for (i2 = 0; i2 < iArr2.length; i2++) {
                        iArr2[i2] = dnVar.a(3) + 1;
                        a4 = dnVar.a(2);
                        if (a4 > 0) {
                            dnVar.b(8);
                        }
                        for (int i3 = 0; i3 < (1 << a4); i3++) {
                            dnVar.b(8);
                        }
                    }
                    dnVar.b(2);
                    int a5 = dnVar.a(4);
                    i2 = 0;
                    a4 = 0;
                    for (int i4 = 0; i4 < a3; i4++) {
                        i2 += iArr2[iArr[i4]];
                        while (a4 < i2) {
                            dnVar.b(a5);
                            a4++;
                        }
                    }
                    break;
                default:
                    StringBuilder stringBuilder = new StringBuilder(52);
                    stringBuilder.append("floor type greater than 1 not decodable: ");
                    stringBuilder.append(a2);
                    throw new bl(stringBuilder.toString());
            }
        }
    }

    private static a d(dn dnVar) throws bl {
        if (dnVar.a(24) == 5653314) {
            int i;
            int i2;
            int a = dnVar.a(16);
            int a2 = dnVar.a(24);
            long[] jArr = new long[a2];
            boolean a3 = dnVar.a();
            long j = 0;
            if (a3) {
                int a4 = dnVar.a(5) + 1;
                i = 0;
                while (i < jArr.length) {
                    int a5 = dnVar.a(a(a2 - i));
                    int i3 = i;
                    for (i = 0; i < a5 && i3 < jArr.length; i++) {
                        jArr[i3] = (long) a4;
                        i3++;
                    }
                    a4++;
                    i = i3;
                }
            } else {
                boolean a6 = dnVar.a();
                for (i2 = 0; i2 < jArr.length; i2++) {
                    if (!a6) {
                        jArr[i2] = (long) (dnVar.a(5) + 1);
                    } else if (dnVar.a()) {
                        jArr[i2] = (long) (dnVar.a(5) + 1);
                    } else {
                        jArr[i2] = 0;
                    }
                }
            }
            i2 = dnVar.a(4);
            if (i2 <= 2) {
                if (i2 == 1 || i2 == 2) {
                    dnVar.b(32);
                    dnVar.b(32);
                    i = dnVar.a(4) + 1;
                    dnVar.b(1);
                    if (i2 != 1) {
                        j = (long) (a2 * a);
                    } else if (a != 0) {
                        j = a((long) a2, (long) a);
                    }
                    dnVar.b((int) (j * ((long) i)));
                }
                return new a(a, a2, jArr, i2, a3);
            }
            StringBuilder stringBuilder = new StringBuilder(53);
            stringBuilder.append("lookup type greater than 2 not decodable: ");
            stringBuilder.append(i2);
            throw new bl(stringBuilder.toString());
        }
        dnVar = dnVar.b();
        StringBuilder stringBuilder2 = new StringBuilder(66);
        stringBuilder2.append("expected code book to start with [0x56, 0x43, 0x42] at ");
        stringBuilder2.append(dnVar);
        throw new bl(stringBuilder2.toString());
    }

    private static long a(long j, long j2) {
        j = (double) j;
        j2 = (double) j2;
        Double.isNaN(j2);
        return (long) Math.floor(Math.pow(j, 1.0d / j2));
    }
}
