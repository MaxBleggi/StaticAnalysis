package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;
import android.util.Pair;
import androidx.core.internal.view.SupportMenu;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: IMASDK */
final class cw {
    private static final int a = ft.c(C.CENC_TYPE_cenc);

    /* compiled from: IMASDK */
    private static final class a {
        public final List<byte[]> a;
        public final int b;
        public final float c;

        public a(List<byte[]> list, int i, float f) {
            this.a = list;
            this.b = i;
            this.c = f;
        }
    }

    /* compiled from: IMASDK */
    private static final class b {
        public final int a;
        public int b;
        public int c;
        public long d;
        private final boolean e;
        private final fp f;
        private final fp g;
        private int h;
        private int i;

        public b(fp fpVar, fp fpVar2, boolean z) {
            this.g = fpVar;
            this.f = fpVar2;
            this.e = z;
            fpVar2.c(12);
            this.a = fpVar2.s();
            fpVar.c(12);
            this.i = fpVar.s();
            fpVar2 = true;
            if (fpVar.m() != 1) {
                fpVar2 = null;
            }
            fe.b(fpVar2, "first_chunk must be 1");
            this.b = -1;
        }

        public boolean a() {
            int i = this.b + 1;
            this.b = i;
            if (i == this.a) {
                return false;
            }
            long u;
            if (this.e) {
                u = this.f.u();
            } else {
                u = this.f.k();
            }
            this.d = u;
            if (this.b == this.h) {
                this.c = this.g.s();
                this.g.d(4);
                i = this.i - 1;
                this.i = i;
                this.h = i > 0 ? this.g.s() - 1 : -1;
            }
            return true;
        }
    }

    /* compiled from: IMASDK */
    private interface c {
        int a();

        int b();

        boolean c();
    }

    /* compiled from: IMASDK */
    private static final class d {
        public final de[] a;
        public bj b;
        public int c = -1;

        public d(int i) {
            this.a = new de[i];
        }
    }

    /* compiled from: IMASDK */
    private static final class g {
        private final int a;
        private final long b;
        private final int c;

        public g(int i, long j, int i2) {
            this.a = i;
            this.b = j;
            this.c = i2;
        }
    }

    /* compiled from: IMASDK */
    static final class e implements c {
        private final int a = this.c.s();
        private final int b = this.c.s();
        private final fp c;

        public e(b bVar) {
            this.c = bVar.aP;
            this.c.c(12);
        }

        public int a() {
            return this.b;
        }

        public int b() {
            return this.a == 0 ? this.c.s() : this.a;
        }

        public boolean c() {
            return this.a != 0;
        }
    }

    /* compiled from: IMASDK */
    static final class f implements c {
        private final fp a;
        private final int b = this.a.s();
        private final int c = (this.a.s() & 255);
        private int d;
        private int e;

        public f(b bVar) {
            this.a = bVar.aP;
            this.a.c(12);
        }

        public boolean c() {
            return false;
        }

        public int a() {
            return this.b;
        }

        public int b() {
            if (this.c == 8) {
                return this.a.f();
            }
            if (this.c == 16) {
                return this.a.g();
            }
            int i = this.d;
            this.d = i + 1;
            if (i % 2 != 0) {
                return this.e & 15;
            }
            this.e = this.a.f();
            return (this.e & PsExtractor.VIDEO_STREAM_MASK) >> 4;
        }
    }

    public static dd a(a aVar, b bVar, long j, boolean z) {
        a aVar2 = aVar;
        a e = aVar2.e(cv.E);
        int e2 = e(e.d(cv.S).aP);
        if (e2 != dd.b && e2 != dd.a && e2 != dd.c && e2 != dd.d && e2 != dd.e && e2 != dd.f) {
            return null;
        }
        long a;
        b bVar2;
        dd ddVar;
        g d = d(aVar2.d(cv.O).aP);
        long j2 = -1;
        if (j == -1) {
            a = d.b;
            bVar2 = bVar;
        } else {
            bVar2 = bVar;
            a = j;
        }
        long c = c(bVar2.aP);
        if (a != -1) {
            j2 = ft.a(a, 1000000, c);
        }
        long j3 = j2;
        a e3 = e.e(cv.F).e(cv.G);
        Pair f = f(e.d(cv.R).aP);
        d a2 = a(e3.d(cv.T).aP, d.a, j3, d.c, (String) f.second, z);
        Pair a3 = a(aVar2.e(cv.P));
        if (a2.b == null) {
            ddVar = null;
        } else {
            dd ddVar2 = new dd(d.a, e2, ((Long) f.first).longValue(), c, j3, a2.b, a2.a, a2.c, (long[]) a3.first, (long[]) a3.second);
        }
        return ddVar;
    }

    public static dg a(dd ddVar, a aVar) throws bl {
        c eVar;
        dd ddVar2 = ddVar;
        a aVar2 = aVar;
        b d = aVar2.d(cv.aq);
        if (d != null) {
            eVar = new e(d);
        } else {
            d = aVar2.d(cv.ar);
            if (d != null) {
                eVar = new f(d);
            } else {
                throw new bl("Track has no sample table size information");
            }
        }
        int a = eVar.a();
        if (a == 0) {
            return new dg(new long[0], new int[0], 0, new long[0], new int[0]);
        }
        boolean z;
        int s;
        int s2;
        Object obj;
        long[] jArr;
        Object obj2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        b d2 = aVar2.d(cv.as);
        if (d2 == null) {
            d2 = aVar2.d(cv.at);
            z = true;
        } else {
            z = false;
        }
        fp fpVar = d2.aP;
        fp fpVar2 = aVar2.d(cv.ap).aP;
        fp fpVar3 = aVar2.d(cv.am).aP;
        b d3 = aVar2.d(cv.an);
        fp fpVar4 = d3 != null ? d3.aP : null;
        b d4 = aVar2.d(cv.ao);
        fp fpVar5 = d4 != null ? d4.aP : null;
        b bVar = new b(fpVar2, fpVar, z);
        fpVar3.c(12);
        int s3 = fpVar3.s() - 1;
        int s4 = fpVar3.s();
        int s5 = fpVar3.s();
        if (fpVar5 != null) {
            fpVar5.c(12);
            s = fpVar5.s();
        } else {
            s = 0;
        }
        int i6 = -1;
        if (fpVar4 != null) {
            fpVar4.c(12);
            s2 = fpVar4.s();
            if (s2 > 0) {
                i6 = fpVar4.s() - 1;
            } else {
                fpVar4 = null;
            }
        } else {
            s2 = 0;
        }
        Object obj3 = (eVar.c() && MimeTypes.AUDIO_RAW.equals(ddVar2.l.b) && s3 == 0 && s == 0 && s2 == 0) ? 1 : null;
        long j = 0;
        c cVar;
        if (obj3 == null) {
            int i7;
            obj3 = new long[a];
            obj = new int[a];
            jArr = new long[a];
            int i8 = s2;
            obj2 = new int[a];
            fp fpVar6 = fpVar3;
            int i9 = s;
            long j2 = 0;
            i = i8;
            s = 0;
            int i10 = 0;
            int i11 = 0;
            i2 = 0;
            i3 = s3;
            long j3 = j2;
            s3 = 0;
            int i12 = s5;
            s5 = s4;
            s4 = i12;
            while (s3 < a) {
                int i13;
                int i14;
                while (i11 == 0) {
                    fe.b(bVar.a());
                    i13 = s4;
                    i7 = i3;
                    long j4 = bVar.d;
                    i11 = bVar.c;
                    s4 = i13;
                    i3 = i7;
                    j3 = j4;
                }
                i13 = s4;
                i7 = i3;
                if (fpVar5 != null) {
                    while (i2 == 0 && i9 > 0) {
                        i2 = fpVar5.s();
                        i10 = fpVar5.m();
                        i9--;
                    }
                    i2--;
                }
                s4 = i10;
                obj3[s3] = j3;
                obj[s3] = eVar.b();
                if (obj[s3] > s) {
                    i4 = a;
                    cVar = eVar;
                    s = obj[s3];
                } else {
                    i4 = a;
                    cVar = eVar;
                }
                jArr[s3] = j2 + ((long) s4);
                obj2[s3] = fpVar4 == null ? 1 : 0;
                if (s3 == i6) {
                    obj2[s3] = 1;
                    i--;
                    if (i > 0) {
                        i14 = i;
                        i6 = fpVar4.s() - 1;
                        i = i13;
                        j2 += (long) i;
                        s5--;
                        if (s5 == 0 || i7 <= 0) {
                            i3 = i7;
                        } else {
                            i3 = i7 - 1;
                            s5 = fpVar6.s();
                            i = fpVar6.s();
                        }
                        j3 += (long) obj[s3];
                        i11--;
                        s3++;
                        i10 = s4;
                        eVar = cVar;
                        a = i4;
                        s4 = i;
                        i = i14;
                    }
                }
                i14 = i;
                i = i13;
                j2 += (long) i;
                s5--;
                if (s5 == 0) {
                }
                i3 = i7;
                j3 += (long) obj[s3];
                i11--;
                s3++;
                i10 = s4;
                eVar = cVar;
                a = i4;
                s4 = i;
                i = i14;
            }
            i4 = a;
            i7 = i3;
            fe.a(i2 == 0);
            while (i9 > 0) {
                fe.a(fpVar5.s() == 0);
                fpVar5.m();
                i9--;
            }
            if (i == 0 && s5 == 0 && i11 == 0) {
                if (i7 == 0) {
                    ddVar2 = ddVar;
                    i5 = s;
                }
            }
            a = i;
            ddVar2 = ddVar;
            int i15 = ddVar2.g;
            StringBuilder stringBuilder = new StringBuilder(215);
            stringBuilder.append("Inconsistent stbl box for track ");
            stringBuilder.append(i15);
            stringBuilder.append(": remainingSynchronizationSamples ");
            stringBuilder.append(a);
            stringBuilder.append(", remainingSamplesAtTimestampDelta ");
            stringBuilder.append(s5);
            stringBuilder.append(", remainingSamplesInChunk ");
            stringBuilder.append(i11);
            stringBuilder.append(", remainingTimestampDeltaChanges ");
            stringBuilder.append(i7);
            Log.w("AtomParsers", stringBuilder.toString());
            i5 = s;
        } else {
            i4 = a;
            cVar = eVar;
            long[] jArr2 = new long[bVar.a];
            int[] iArr = new int[bVar.a];
            while (bVar.a()) {
                jArr2[bVar.b] = bVar.d;
                iArr[bVar.b] = bVar.c;
            }
            com.google.ads.interactivemedia.v3.internal.cy.a a2 = cy.a(cVar.b(), jArr2, iArr, (long) s5);
            obj3 = a2.a;
            obj = a2.b;
            a = a2.c;
            jArr = a2.d;
            obj2 = a2.e;
            i5 = a;
        }
        if (ddVar2.n == null) {
            ft.a(jArr, 1000000, ddVar2.i);
            return new dg(obj3, obj, i5, jArr, obj2);
        }
        int i16;
        Object obj4;
        Object obj5;
        if (ddVar2.n.length == 1) {
            int i17 = 0;
            if (ddVar2.n[0] == 0) {
                i16 = 0;
                while (i16 < jArr.length) {
                    jArr[i16] = ft.a(jArr[i16] - ddVar2.o[i17], 1000000, ddVar2.i);
                    i16++;
                    i17 = 0;
                }
                return new dg(obj3, obj, i5, jArr, obj2);
            }
        }
        a = 0;
        i15 = 0;
        s3 = 0;
        for (i16 = 0; i16 < ddVar2.n.length; i16++) {
            long j5 = ddVar2.o[i16];
            if (j5 != -1) {
                long a3 = ft.a(ddVar2.n[i16], ddVar2.i, ddVar2.j);
                s = ft.b(jArr, j5, true, true);
                i3 = ft.b(jArr, j5 + a3, true, false);
                i15 += i3 - s;
                a |= s3 != s ? 1 : 0;
                s3 = i3;
            }
        }
        i16 = (i15 != i4 ? 1 : 0) | a;
        Object obj6 = i16 != 0 ? new long[i15] : obj3;
        Object obj7 = i16 != 0 ? new int[i15] : obj;
        if (i16 != 0) {
            i5 = 0;
        }
        Object obj8 = i16 != 0 ? new int[i15] : obj2;
        long[] jArr3 = new long[i15];
        i2 = i5;
        int i18 = 0;
        s5 = 0;
        while (i18 < ddVar2.n.length) {
            int i19;
            Object obj9;
            Object obj10;
            long[] jArr4;
            long j6 = ddVar2.o[i18];
            long j7 = ddVar2.n[i18];
            if (j6 != -1) {
                Object obj11 = obj7;
                Object obj12 = obj8;
                long a4 = ft.a(j7, ddVar2.i, ddVar2.j) + j6;
                i6 = ft.b(jArr, j6, true, true);
                i19 = i18;
                s3 = ft.b(jArr, a4, true, false);
                if (i16 != 0) {
                    s4 = s3 - i6;
                    System.arraycopy(obj3, i6, obj6, s5, s4);
                    obj4 = obj11;
                    System.arraycopy(obj, i6, obj4, s5, s4);
                    obj5 = obj12;
                    System.arraycopy(obj2, i6, obj5, s5, s4);
                } else {
                    obj4 = obj11;
                    obj5 = obj12;
                }
                s4 = i2;
                while (i6 < s3) {
                    obj9 = obj3;
                    obj10 = obj2;
                    jArr4 = jArr;
                    int i20 = s3;
                    jArr3[s5] = ft.a(j, 1000000, ddVar2.j) + ft.a(jArr[i6] - j6, 1000000, ddVar2.i);
                    if (i16 != 0 && r14[s5] > s4) {
                        s4 = obj[i6];
                    }
                    s5++;
                    i6++;
                    obj3 = obj9;
                    obj2 = obj10;
                    jArr = jArr4;
                    s3 = i20;
                }
                obj9 = obj3;
                obj10 = obj2;
                jArr4 = jArr;
                i2 = s4;
            } else {
                obj9 = obj3;
                obj10 = obj2;
                jArr4 = jArr;
                obj4 = obj7;
                i19 = i18;
                obj5 = obj8;
            }
            j += j7;
            obj8 = obj5;
            obj7 = obj4;
            obj2 = obj10;
            jArr = jArr4;
            i18 = i19 + 1;
            obj3 = obj9;
        }
        obj4 = obj7;
        obj5 = obj8;
        i16 = 0;
        for (i = 0; i < obj5.length && i16 == 0; i++) {
            i16 |= (obj5[i] & 1) != 0 ? 1 : 0;
        }
        if (i16 != 0) {
            return new dg(obj6, obj4, i2, jArr3, obj5);
        }
        throw new bl("The edited sample sequence does not contain a sync sample.");
    }

    public static cg a(b bVar, boolean z) {
        if (z) {
            return null;
        }
        fp fpVar = bVar.aP;
        fpVar.c(8);
        while (fpVar.b() >= 8) {
            int m = fpVar.m();
            if (fpVar.m() == cv.aA) {
                fpVar.c(fpVar.d() - 8);
                fpVar.b(fpVar.d() + m);
                return a(fpVar);
            }
            fpVar.d(m - 8);
        }
        return null;
    }

    private static cg a(fp fpVar) {
        fpVar.d(12);
        fp fpVar2 = new fp();
        while (fpVar.b() >= 8) {
            int m = fpVar.m() - 8;
            if (fpVar.m() == cv.aB) {
                fpVar2.a(fpVar.a, fpVar.d() + m);
                fpVar2.c(fpVar.d());
                cg b = b(fpVar2);
                if (b != null) {
                    return b;
                }
            }
            fpVar.d(m);
        }
        return null;
    }

    private static cg b(fp fpVar) {
        while (true) {
            String str = null;
            if (fpVar.b() <= 0) {
                return null;
            }
            int d = fpVar.d() + fpVar.m();
            if (fpVar.m() == cv.aN) {
                String str2 = null;
                Object obj = str2;
                while (fpVar.d() < d) {
                    int m = fpVar.m() - 12;
                    int m2 = fpVar.m();
                    fpVar.d(4);
                    if (m2 == cv.aC) {
                        obj = fpVar.e(m);
                    } else if (m2 == cv.aD) {
                        str = fpVar.e(m);
                    } else if (m2 == cv.aE) {
                        fpVar.d(4);
                        str2 = fpVar.e(m - 4);
                    } else {
                        fpVar.d(m);
                    }
                }
                if (!(str == null || str2 == null || !"com.apple.iTunes".equals(r3))) {
                    return cg.a(str, str2);
                }
            }
            fpVar.c(d);
        }
    }

    private static long c(fp fpVar) {
        int i = 8;
        fpVar.c(8);
        if (cv.a(fpVar.m()) != 0) {
            i = 16;
        }
        fpVar.d(i);
        return fpVar.k();
    }

    private static g d(fp fpVar) {
        Object obj;
        int i = 8;
        fpVar.c(8);
        int a = cv.a(fpVar.m());
        fpVar.d(a == 0 ? 8 : 16);
        int m = fpVar.m();
        fpVar.d(4);
        int d = fpVar.d();
        if (a == 0) {
            i = 4;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (fpVar.a[d + i3] != (byte) -1) {
                obj = null;
                break;
            }
        }
        obj = 1;
        long j = -1;
        if (obj != null) {
            fpVar.d(i);
        } else {
            long k = a == 0 ? fpVar.k() : fpVar.u();
            if (k != 0) {
                j = k;
            }
        }
        fpVar.d(16);
        i = fpVar.m();
        a = fpVar.m();
        fpVar.d(4);
        int m2 = fpVar.m();
        fpVar = fpVar.m();
        if (i == 0 && a == 65536 && m2 == SupportMenu.CATEGORY_MASK && fpVar == null) {
            i2 = 90;
        } else if (i == 0 && a == SupportMenu.CATEGORY_MASK && m2 == 65536 && fpVar == null) {
            i2 = 270;
        } else if (i == SupportMenu.CATEGORY_MASK && a == 0 && m2 == 0 && fpVar == -65536) {
            i2 = 180;
        }
        return new g(m, j, i2);
    }

    private static int e(fp fpVar) {
        fpVar.c(16);
        return fpVar.m();
    }

    private static Pair<Long, String> f(fp fpVar) {
        int i = 8;
        fpVar.c(8);
        int a = cv.a(fpVar.m());
        fpVar.d(a == 0 ? 8 : 16);
        long k = fpVar.k();
        if (a == 0) {
            i = 4;
        }
        fpVar.d(i);
        fpVar = fpVar.g();
        char c = (char) (((fpVar >> 10) & 31) + 96);
        char c2 = (char) (((fpVar >> 5) & 31) + 96);
        fpVar = (char) ((fpVar & 31) + 96);
        StringBuilder stringBuilder = new StringBuilder(3);
        stringBuilder.append(c);
        stringBuilder.append(c2);
        stringBuilder.append(fpVar);
        return Pair.create(Long.valueOf(k), stringBuilder.toString());
    }

    private static d a(fp fpVar, int i, long j, int i2, String str, boolean z) {
        fp fpVar2 = fpVar;
        fpVar2.c(12);
        int m = fpVar.m();
        d dVar = new d(m);
        for (int i3 = 0; i3 < m; i3++) {
            int d = fpVar.d();
            int m2 = fpVar.m();
            fe.a(m2 > 0, "childAtomSize should be positive");
            int m3 = fpVar.m();
            if (!(m3 == cv.b || m3 == cv.c || m3 == cv.Z || m3 == cv.al || m3 == cv.d || m3 == cv.e || m3 == cv.f || m3 == cv.aJ)) {
                if (m3 != cv.aK) {
                    long j2;
                    if (!(m3 == cv.i || m3 == cv.aa || m3 == cv.n || m3 == cv.p || m3 == cv.r || m3 == cv.u || m3 == cv.s || m3 == cv.t || m3 == cv.ax || m3 == cv.ay || m3 == cv.l || m3 == cv.m)) {
                        if (m3 != cv.j) {
                            if (m3 == cv.aj) {
                                dVar.b = bj.a(Integer.toString(i), MimeTypes.APPLICATION_TTML, -1, j, str);
                            } else if (m3 == cv.au) {
                                dVar.b = bj.a(Integer.toString(i), MimeTypes.APPLICATION_TX3G, -1, j, str);
                            } else if (m3 == cv.av) {
                                dVar.b = bj.a(Integer.toString(i), "application/x-mp4vtt", -1, j, str);
                            } else if (m3 == cv.aw) {
                                dVar.b = bj.a(Integer.toString(i), MimeTypes.APPLICATION_TTML, -1, j, str, 0);
                            } else if (m3 == cv.aM) {
                                dVar.b = bj.a(Integer.toString(i), MimeTypes.APPLICATION_CAMERA_MOTION, -1, j);
                            } else {
                                j2 = j;
                            }
                            fpVar2.c(d + m2);
                        }
                    }
                    j2 = j;
                    a(fpVar, m3, d, m2, i, j, str, z, dVar, i3);
                    fpVar2.c(d + m2);
                }
            }
            a(fpVar, m3, d, m2, i, j, i2, dVar, i3);
            fpVar2.c(d + m2);
        }
        return dVar;
    }

    private static void a(fp fpVar, int i, int i2, int i3, int i4, long j, int i5, d dVar, int i6) {
        fp fpVar2 = fpVar;
        int i7 = i;
        int i8 = i2;
        int i9 = i3;
        d dVar2 = dVar;
        fpVar2.c(i8 + 8);
        fpVar2.d(24);
        int g = fpVar.g();
        int g2 = fpVar.g();
        fpVar2.d(50);
        int d = fpVar.d();
        if (i7 == cv.Z) {
            a(fpVar2, i8, i9, dVar2, i6);
            fpVar2.c(d);
        }
        String str = null;
        List list = null;
        byte[] bArr = list;
        Object obj = null;
        float f = 1.0f;
        int i10 = -1;
        while (d - i8 < i9) {
            fpVar2.c(d);
            int d2 = fpVar.d();
            int m = fpVar.m();
            if (m != 0 || fpVar.d() - i8 != i9) {
                fe.a(m > 0, "childAtomSize should be positive");
                int m2 = fpVar.m();
                if (m2 == cv.H) {
                    fe.b(str == null);
                    str = MimeTypes.VIDEO_H264;
                    a a = a(fpVar2, d2);
                    list = a.a;
                    dVar2.c = a.b;
                    if (obj == null) {
                        f = a.c;
                    }
                } else if (m2 == cv.I) {
                    fe.b(str == null);
                    str = MimeTypes.VIDEO_H265;
                    Pair b = b(fpVar2, d2);
                    list = (List) b.first;
                    dVar2.c = ((Integer) b.second).intValue();
                } else if (m2 == cv.g) {
                    fe.b(str == null);
                    str = MimeTypes.VIDEO_H263;
                } else if (m2 == cv.J) {
                    fe.b(str == null);
                    Pair d3 = d(fpVar2, d2);
                    String str2 = (String) d3.first;
                    list = Collections.singletonList((byte[]) d3.second);
                    str = str2;
                } else if (m2 == cv.ai) {
                    f = c(fpVar2, d2);
                    obj = 1;
                } else if (m2 == cv.aL) {
                    fe.b(str == null);
                    str = i7 == cv.aJ ? MimeTypes.VIDEO_VP8 : MimeTypes.VIDEO_VP9;
                } else if (m2 == cv.aH) {
                    bArr = d(fpVar2, d2, m);
                } else if (m2 == cv.aG) {
                    m2 = fpVar.f();
                    fpVar2.d(3);
                    if (m2 == 0) {
                        switch (fpVar.f()) {
                            case 0:
                                i10 = 0;
                                break;
                            case 1:
                                i10 = 1;
                                break;
                            case 2:
                                i10 = 2;
                                break;
                            case 3:
                                i10 = 3;
                                break;
                            default:
                                break;
                        }
                    }
                }
                d += m;
            } else if (str == null) {
                dVar2.b = bj.a(Integer.toString(i4), str, -1, -1, j, g, g2, list, i5, f, bArr, i10, null);
            }
        }
        if (str == null) {
            dVar2.b = bj.a(Integer.toString(i4), str, -1, -1, j, g, g2, list, i5, f, bArr, i10, null);
        }
    }

    private static a a(fp fpVar, int i) {
        fpVar.c((i + 8) + 4);
        i = (fpVar.f() & 3) + 1;
        if (i != 3) {
            int i2;
            List arrayList = new ArrayList();
            float f = 1.0f;
            int f2 = fpVar.f() & 31;
            for (i2 = 0; i2 < f2; i2++) {
                arrayList.add(fn.a(fpVar));
            }
            i2 = fpVar.f();
            for (int i3 = 0; i3 < i2; i3++) {
                arrayList.add(fn.a(fpVar));
            }
            if (f2 > 0) {
                fo foVar = new fo((byte[]) arrayList.get(0));
                foVar.a((i + 1) * 8);
                f = fn.a(foVar).d;
            }
            return new a(arrayList, i, f);
        }
        throw new IllegalStateException();
    }

    private static Pair<List<byte[]>, Integer> b(fp fpVar, int i) {
        int i2;
        fpVar.c((i + 8) + 21);
        i = fpVar.f() & 3;
        int f = fpVar.f();
        int d = fpVar.d();
        int i3 = 0;
        int i4 = 0;
        while (i3 < f) {
            fpVar.d(1);
            int g = fpVar.g();
            i2 = i4;
            for (i4 = 0; i4 < g; i4++) {
                int g2 = fpVar.g();
                i2 += g2 + 4;
                fpVar.d(g2);
            }
            i3++;
            i4 = i2;
        }
        fpVar.c(d);
        Object obj = new byte[i4];
        i3 = 0;
        i2 = 0;
        while (i3 < f) {
            fpVar.d(1);
            g2 = fpVar.g();
            int i5 = i2;
            for (i2 = 0; i2 < g2; i2++) {
                int g3 = fpVar.g();
                System.arraycopy(fn.a, 0, obj, i5, fn.a.length);
                i5 += fn.a.length;
                System.arraycopy(fpVar.a, fpVar.d(), obj, i5, g3);
                i5 += g3;
                fpVar.d(g3);
            }
            i3++;
            i2 = i5;
        }
        if (i4 == 0) {
            fpVar = null;
        } else {
            fpVar = Collections.singletonList(obj);
        }
        return Pair.create(fpVar, Integer.valueOf(i + 1));
    }

    private static Pair<long[], long[]> a(a aVar) {
        if (aVar != null) {
            aVar = aVar.d(cv.Q);
            if (aVar != null) {
                aVar = aVar.aP;
                aVar.c(8);
                int a = cv.a(aVar.m());
                int s = aVar.s();
                Object obj = new long[s];
                Object obj2 = new long[s];
                int i = 0;
                while (i < s) {
                    obj[i] = a == 1 ? aVar.u() : aVar.k();
                    obj2[i] = a == 1 ? aVar.o() : (long) aVar.m();
                    if (aVar.i() == (short) 1) {
                        aVar.d(2);
                        i++;
                    } else {
                        throw new IllegalArgumentException("Unsupported media rate.");
                    }
                }
                return Pair.create(obj, obj2);
            }
        }
        return Pair.create(null, null);
    }

    private static float c(fp fpVar, int i) {
        fpVar.c(i + 8);
        return ((float) fpVar.s()) / ((float) fpVar.s());
    }

    private static void a(fp fpVar, int i, int i2, int i3, int i4, long j, String str, boolean z, d dVar, int i5) {
        int g;
        int round;
        int s;
        int i6;
        int i7;
        String str2;
        int i8;
        int i9;
        String str3;
        int i10;
        Object obj;
        int m;
        String str4;
        int i11;
        int i12;
        d dVar2;
        Pair d;
        List list;
        fp fpVar2 = fpVar;
        int i13 = i2;
        int i14 = i3;
        long j2 = j;
        String str5 = str;
        d dVar3 = dVar;
        fpVar2.c(i13 + 8);
        if (z) {
            fpVar2.d(8);
            g = fpVar.g();
            fpVar2.d(6);
        } else {
            fpVar2.d(16);
            g = 0;
        }
        if (g != 0) {
            if (g != 1) {
                if (g == 2) {
                    fpVar2.d(16);
                    round = (int) Math.round(fpVar.v());
                    s = fpVar.s();
                    fpVar2.d(20);
                    i6 = s;
                    s = fpVar.d();
                    i7 = i;
                    if (i7 != cv.aa) {
                        g = a(fpVar2, i13, i14, dVar3, i5);
                        fpVar2.c(s);
                    } else {
                        g = i7;
                    }
                    if (g == cv.n) {
                        str2 = MimeTypes.AUDIO_AC3;
                    } else if (g == cv.p) {
                        str2 = MimeTypes.AUDIO_E_AC3;
                    } else if (g != cv.r) {
                        str2 = MimeTypes.AUDIO_DTS;
                    } else {
                        if (g != cv.s) {
                            if (g == cv.t) {
                                if (g == cv.u) {
                                    str2 = MimeTypes.AUDIO_DTS_EXPRESS;
                                } else if (g == cv.ax) {
                                    str2 = MimeTypes.AUDIO_AMR_NB;
                                } else if (g != cv.ay) {
                                    str2 = MimeTypes.AUDIO_AMR_WB;
                                } else {
                                    if (g != cv.l) {
                                        if (g == cv.m) {
                                            str2 = g != cv.j ? MimeTypes.AUDIO_MPEG : null;
                                        }
                                    }
                                    str2 = MimeTypes.AUDIO_RAW;
                                }
                            }
                        }
                        str2 = MimeTypes.AUDIO_DTS_HD;
                    }
                    i8 = round;
                    i9 = s;
                    str3 = str2;
                    i10 = i6;
                    obj = null;
                    while (i9 - i13 < i14) {
                        fpVar2.c(i9);
                        m = fpVar.m();
                        fe.a(m <= 0, "childAtomSize should be positive");
                        round = fpVar.m();
                        if (round != cv.J) {
                            if (z || round != cv.k) {
                                if (round == cv.o) {
                                    fpVar2.c(i9 + 8);
                                    dVar3.b = fd.a(fpVar2, Integer.toString(i4), j2, str5);
                                } else if (round == cv.q) {
                                    fpVar2.c(i9 + 8);
                                    dVar3.b = fd.b(fpVar2, Integer.toString(i4), j2, str5);
                                } else if (round == cv.v) {
                                    str4 = str3;
                                    i11 = m;
                                    i12 = i9;
                                    dVar2 = dVar3;
                                    dVar2.b = bj.a(Integer.toString(i4), str3, -1, -1, j, i10, i8, null, str);
                                    g = i11;
                                    str3 = str4;
                                    round = i12;
                                    i9 = round + g;
                                    dVar3 = dVar2;
                                    i14 = i3;
                                }
                                str4 = str3;
                                i11 = m;
                                i12 = i9;
                                dVar2 = dVar3;
                                g = i11;
                                str3 = str4;
                                round = i12;
                                i9 = round + g;
                                dVar3 = dVar2;
                                i14 = i3;
                            }
                        }
                        str4 = str3;
                        i11 = m;
                        i12 = i9;
                        dVar2 = dVar3;
                        if (round != cv.J) {
                            g = i11;
                            round = i12;
                            i9 = round;
                        } else {
                            g = i11;
                            round = i12;
                            i9 = a(fpVar2, round, g);
                        }
                        if (i9 == -1) {
                            String str6;
                            d = d(fpVar2, i9);
                            str6 = (String) d.first;
                            obj = (byte[]) d.second;
                            if (MimeTypes.AUDIO_AAC.equals(str6)) {
                                d = ff.a(obj);
                                i7 = ((Integer) d.first).intValue();
                                i10 = ((Integer) d.second).intValue();
                                i8 = i7;
                            }
                            str3 = str6;
                        } else {
                            str3 = str4;
                        }
                        i9 = round + g;
                        dVar3 = dVar2;
                        i14 = i3;
                    }
                    str4 = str3;
                    dVar2 = dVar3;
                    if (dVar2.b == null) {
                        str2 = str4;
                        if (str2 != null) {
                            int i15 = MimeTypes.AUDIO_RAW.equals(str2) ? 2 : -1;
                            String num = Integer.toString(i4);
                            if (obj != null) {
                                list = null;
                            } else {
                                list = Collections.singletonList(obj);
                            }
                            dVar2.b = bj.a(num, str2, -1, -1, j, i10, i8, list, str, i15);
                        }
                    }
                }
                return;
            }
        }
        i6 = fpVar.g();
        fpVar2.d(6);
        round = fpVar.q();
        if (g == 1) {
            fpVar2.d(16);
        }
        s = fpVar.d();
        i7 = i;
        if (i7 != cv.aa) {
            g = i7;
        } else {
            g = a(fpVar2, i13, i14, dVar3, i5);
            fpVar2.c(s);
        }
        if (g == cv.n) {
            str2 = MimeTypes.AUDIO_AC3;
        } else if (g == cv.p) {
            str2 = MimeTypes.AUDIO_E_AC3;
        } else if (g != cv.r) {
            if (g != cv.s) {
                if (g == cv.t) {
                    if (g == cv.u) {
                        str2 = MimeTypes.AUDIO_DTS_EXPRESS;
                    } else if (g == cv.ax) {
                        str2 = MimeTypes.AUDIO_AMR_NB;
                    } else if (g != cv.ay) {
                        if (g != cv.l) {
                            if (g == cv.m) {
                                if (g != cv.j) {
                                }
                            }
                        }
                        str2 = MimeTypes.AUDIO_RAW;
                    } else {
                        str2 = MimeTypes.AUDIO_AMR_WB;
                    }
                }
            }
            str2 = MimeTypes.AUDIO_DTS_HD;
        } else {
            str2 = MimeTypes.AUDIO_DTS;
        }
        i8 = round;
        i9 = s;
        str3 = str2;
        i10 = i6;
        obj = null;
        while (i9 - i13 < i14) {
            fpVar2.c(i9);
            m = fpVar.m();
            if (m <= 0) {
            }
            fe.a(m <= 0, "childAtomSize should be positive");
            round = fpVar.m();
            if (round != cv.J) {
                if (z) {
                }
                if (round == cv.o) {
                    fpVar2.c(i9 + 8);
                    dVar3.b = fd.a(fpVar2, Integer.toString(i4), j2, str5);
                } else if (round == cv.q) {
                    fpVar2.c(i9 + 8);
                    dVar3.b = fd.b(fpVar2, Integer.toString(i4), j2, str5);
                } else if (round == cv.v) {
                    str4 = str3;
                    i11 = m;
                    i12 = i9;
                    dVar2 = dVar3;
                    dVar2.b = bj.a(Integer.toString(i4), str3, -1, -1, j, i10, i8, null, str);
                    g = i11;
                    str3 = str4;
                    round = i12;
                    i9 = round + g;
                    dVar3 = dVar2;
                    i14 = i3;
                }
                str4 = str3;
                i11 = m;
                i12 = i9;
                dVar2 = dVar3;
                g = i11;
                str3 = str4;
                round = i12;
                i9 = round + g;
                dVar3 = dVar2;
                i14 = i3;
            }
            str4 = str3;
            i11 = m;
            i12 = i9;
            dVar2 = dVar3;
            if (round != cv.J) {
                g = i11;
                round = i12;
                i9 = a(fpVar2, round, g);
            } else {
                g = i11;
                round = i12;
                i9 = round;
            }
            if (i9 == -1) {
                str3 = str4;
            } else {
                d = d(fpVar2, i9);
                str6 = (String) d.first;
                obj = (byte[]) d.second;
                if (MimeTypes.AUDIO_AAC.equals(str6)) {
                    d = ff.a(obj);
                    i7 = ((Integer) d.first).intValue();
                    i10 = ((Integer) d.second).intValue();
                    i8 = i7;
                }
                str3 = str6;
            }
            i9 = round + g;
            dVar3 = dVar2;
            i14 = i3;
        }
        str4 = str3;
        dVar2 = dVar3;
        if (dVar2.b == null) {
            str2 = str4;
            if (str2 != null) {
                if (MimeTypes.AUDIO_RAW.equals(str2)) {
                }
                String num2 = Integer.toString(i4);
                if (obj != null) {
                    list = Collections.singletonList(obj);
                } else {
                    list = null;
                }
                dVar2.b = bj.a(num2, str2, -1, -1, j, i10, i8, list, str, i15);
            }
        }
    }

    private static int a(fp fpVar, int i, int i2) {
        int d = fpVar.d();
        while (d - i < i2) {
            fpVar.c(d);
            int m = fpVar.m();
            fe.a(m > 0, "childAtomSize should be positive");
            if (fpVar.m() == cv.J) {
                return d;
            }
            d += m;
        }
        return -1;
    }

    private static Pair<String, byte[]> d(fp fpVar, int i) {
        fpVar.c((i + 8) + 4);
        fpVar.d(1);
        g(fpVar);
        fpVar.d(2);
        int f = fpVar.f();
        if ((f & 128) != 0) {
            fpVar.d(2);
        }
        if ((f & 64) != 0) {
            fpVar.d(fpVar.g());
        }
        if ((f & 32) != 0) {
            fpVar.d(2);
        }
        fpVar.d(1);
        g(fpVar);
        Object obj = null;
        switch (fpVar.f()) {
            case 32:
                obj = MimeTypes.VIDEO_MP4V;
                break;
            case 33:
                obj = MimeTypes.VIDEO_H264;
                break;
            case 35:
                obj = MimeTypes.VIDEO_H265;
                break;
            case 64:
            case 102:
            case 103:
            case 104:
                obj = MimeTypes.AUDIO_AAC;
                break;
            case 107:
                return Pair.create(MimeTypes.AUDIO_MPEG, null);
            case 165:
                obj = MimeTypes.AUDIO_AC3;
                break;
            case 166:
                obj = MimeTypes.AUDIO_E_AC3;
                break;
            case 169:
            case 172:
                return Pair.create(MimeTypes.AUDIO_DTS, null);
            case 170:
            case 171:
                return Pair.create(MimeTypes.AUDIO_DTS_HD, null);
            default:
                break;
        }
        fpVar.d(12);
        fpVar.d(1);
        i = g(fpVar);
        Object obj2 = new byte[i];
        fpVar.a(obj2, 0, i);
        return Pair.create(obj, obj2);
    }

    private static int a(fp fpVar, int i, int i2, d dVar, int i3) {
        int d = fpVar.d();
        while (true) {
            boolean z = false;
            if (d - i >= i2) {
                return 0;
            }
            fpVar.c(d);
            int m = fpVar.m();
            if (m > 0) {
                z = true;
            }
            fe.a(z, "childAtomSize should be positive");
            if (fpVar.m() == cv.V) {
                Pair b = b(fpVar, d, m);
                if (b != null) {
                    dVar.a[i3] = (de) b.second;
                    return ((Integer) b.first).intValue();
                }
            }
            d += m;
        }
    }

    private static Pair<Integer, de> b(fp fpVar, int i, int i2) {
        int i3 = i + 8;
        boolean z = false;
        Object obj = null;
        Object obj2 = obj;
        Object obj3 = null;
        while (true) {
            Object obj4 = 1;
            if (i3 - i >= i2) {
                break;
            }
            fpVar.c(i3);
            int m = fpVar.m();
            int m2 = fpVar.m();
            if (m2 == cv.ab) {
                obj = Integer.valueOf(fpVar.m());
            } else if (m2 == cv.W) {
                fpVar.d(4);
                if (fpVar.m() != a) {
                    obj4 = null;
                }
                obj3 = obj4;
            } else if (m2 == cv.X) {
                obj2 = c(fpVar, i3, m);
            }
            i3 += m;
        }
        if (obj3 == null) {
            return null;
        }
        fe.a(obj != null ? true : null, "frma atom is mandatory");
        if (obj2 != null) {
            z = true;
        }
        fe.a(z, "schi->tenc atom is mandatory");
        return Pair.create(obj, obj2);
    }

    private static de c(fp fpVar, int i, int i2) {
        int i3 = i + 8;
        while (i3 - i < i2) {
            fpVar.c(i3);
            int m = fpVar.m();
            if (fpVar.m() == cv.Y) {
                fpVar.d(6);
                boolean z = true;
                if (fpVar.f() != 1) {
                    z = false;
                }
                i = fpVar.f();
                byte[] bArr = new byte[16];
                fpVar.a(bArr, 0, bArr.length);
                return new de(z, i, bArr);
            }
            i3 += m;
        }
        return null;
    }

    private static byte[] d(fp fpVar, int i, int i2) {
        int i3 = i + 8;
        while (i3 - i < i2) {
            fpVar.c(i3);
            int m = fpVar.m();
            if (fpVar.m() == cv.aI) {
                return Arrays.copyOfRange(fpVar.a, i3, m + i3);
            }
            i3 += m;
        }
        return null;
    }

    private static int g(fp fpVar) {
        int f = fpVar.f();
        int i = f & 127;
        while ((f & 128) == 128) {
            f = fpVar.f();
            i = (i << 7) | (f & 127);
        }
        return i;
    }
}
