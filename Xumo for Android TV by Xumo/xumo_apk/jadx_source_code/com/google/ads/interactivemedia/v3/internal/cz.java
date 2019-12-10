package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.google.ads.interactivemedia.v3.internal.bu.b;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/* compiled from: IMASDK */
public class cz implements cc {
    private static final int a = ft.c("seig");
    private static final byte[] b = new byte[]{(byte) -94, (byte) 57, (byte) 79, (byte) 82, (byte) 90, (byte) -101, (byte) 79, (byte) 20, (byte) -94, (byte) 68, (byte) 108, (byte) 66, (byte) 124, (byte) 100, (byte) -115, (byte) -12};
    private final int c;
    private final dd d;
    private final SparseArray<a> e;
    private final fp f;
    private final fp g;
    private final fp h;
    private final fp i;
    private final byte[] j;
    private final Stack<a> k;
    private int l;
    private int m;
    private long n;
    private int o;
    private fp p;
    private long q;
    private a r;
    private int s;
    private int t;
    private int u;
    private ce v;
    private boolean w;

    /* compiled from: IMASDK */
    private static final class a {
        public final df a = new df();
        public final ck b;
        public dd c;
        public cx d;
        public int e;

        public a(ck ckVar) {
            this.b = ckVar;
        }

        public void a(dd ddVar, cx cxVar) {
            this.c = (dd) fe.a((Object) ddVar);
            this.d = (cx) fe.a((Object) cxVar);
            this.b.a(ddVar.l);
            a();
        }

        public void a() {
            this.a.a();
            this.e = 0;
        }
    }

    public cz() {
        this(0);
    }

    protected void a(fp fpVar, long j) throws bl {
    }

    public final void c() {
    }

    public cz(int i) {
        this(i, null);
    }

    public cz(int i, dd ddVar) {
        this.d = ddVar;
        this.c = i | (ddVar != null ? 4 : null);
        this.i = new fp(16);
        this.f = new fp(fn.a);
        this.g = new fp(4);
        this.h = new fp(1);
        this.j = new byte[16];
        this.k = new Stack();
        this.e = new SparseArray();
        a();
    }

    public final boolean a(cd cdVar) throws IOException, InterruptedException {
        return dc.a(cdVar);
    }

    public final void a(ce ceVar) {
        this.v = ceVar;
        if (this.d != null) {
            a aVar = new a(ceVar.d(0));
            aVar.a(this.d, new cx(0, 0, 0, 0));
            this.e.put(0, aVar);
            this.v.f();
        }
    }

    public final void b() {
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            ((a) this.e.valueAt(i)).a();
        }
        this.k.clear();
        a();
    }

    public final int a(cd cdVar, ch chVar) throws IOException, InterruptedException {
        while (true) {
            switch (this.l) {
                case null:
                    if (b(cdVar) != null) {
                        break;
                    }
                    return -1;
                case 1:
                    c(cdVar);
                    break;
                case 2:
                    d(cdVar);
                    break;
                default:
                    if (e(cdVar) == null) {
                        break;
                    }
                    return null;
            }
        }
    }

    private void a() {
        this.l = 0;
        this.o = 0;
    }

    private boolean b(cd cdVar) throws IOException, InterruptedException {
        if (this.o == 0) {
            if (!cdVar.a(this.i.a, 0, 8, true)) {
                return false;
            }
            this.o = 8;
            this.i.c(0);
            this.n = this.i.k();
            this.m = this.i.m();
        }
        if (this.n == 1) {
            cdVar.b(this.i.a, 8, 8);
            this.o += 8;
            this.n = this.i.u();
        }
        if (this.n >= ((long) this.o)) {
            long c = cdVar.c() - ((long) this.o);
            if (this.m == cv.K) {
                int size = this.e.size();
                for (int i = 0; i < size; i++) {
                    df dfVar = ((a) this.e.valueAt(i)).a;
                    dfVar.c = c;
                    dfVar.b = c;
                }
            }
            if (this.m == cv.h) {
                this.r = null;
                this.q = c + this.n;
                if (this.w == null) {
                    this.v.a(cj.f);
                    this.w = true;
                }
                this.l = 2;
                return true;
            }
            if (b(this.m)) {
                long c2 = (cdVar.c() + this.n) - 8;
                this.k.add(new a(this.m, c2));
                if (this.n == ((long) this.o)) {
                    a(c2);
                } else {
                    a();
                }
            } else if (a(this.m) != null) {
                if (this.o != 8) {
                    throw new bl("Leaf atom defines extended atom size (unsupported).");
                } else if (this.n <= 2147483647L) {
                    this.p = new fp((int) this.n);
                    System.arraycopy(this.i.a, 0, this.p.a, 0, 8);
                    this.l = 1;
                } else {
                    throw new bl("Leaf atom with length > 2147483647 (unsupported).");
                }
            } else if (this.n <= 2147483647L) {
                this.p = null;
                this.l = 1;
            } else {
                throw new bl("Skipping atom with length > 2147483647 (unsupported).");
            }
            return true;
        }
        throw new bl("Atom size less than header length (unsupported).");
    }

    private void c(cd cdVar) throws IOException, InterruptedException {
        int i = ((int) this.n) - this.o;
        if (this.p != null) {
            cdVar.b(this.p.a, 8, i);
            a(new b(this.m, this.p), cdVar.c());
        } else {
            cdVar.b(i);
        }
        a(cdVar.c());
    }

    private void a(long j) throws bl {
        while (!this.k.isEmpty() && ((a) this.k.peek()).aP == j) {
            a((a) this.k.pop());
        }
        a();
    }

    private void a(b bVar, long j) throws bl {
        if (!this.k.isEmpty()) {
            ((a) this.k.peek()).a(bVar);
        } else if (bVar.aO == cv.A) {
            this.v.a(b(bVar.aP, j));
            this.w = true;
        } else if (bVar.aO == cv.aF) {
            a(bVar.aP, j);
        }
    }

    private void a(a aVar) throws bl {
        if (aVar.aO == cv.B) {
            b(aVar);
        } else if (aVar.aO == cv.K) {
            c(aVar);
        } else if (!this.k.isEmpty()) {
            ((a) this.k.peek()).a(aVar);
        }
    }

    private void b(a aVar) {
        int i;
        boolean z = true;
        int i2 = 0;
        fe.b(this.d == null, "Unexpected moov box.");
        bu a = a(aVar.aQ);
        if (a != null) {
            this.v.a(a);
        }
        a e = aVar.e(cv.M);
        SparseArray sparseArray = new SparseArray();
        int size = e.aQ.size();
        long j = -1;
        for (i = 0; i < size; i++) {
            b bVar = (b) e.aQ.get(i);
            if (bVar.aO == cv.y) {
                Pair a2 = a(bVar.aP);
                sparseArray.put(((Integer) a2.first).intValue(), (cx) a2.second);
            } else if (bVar.aO == cv.N) {
                j = b(bVar.aP);
            }
        }
        SparseArray sparseArray2 = new SparseArray();
        i = aVar.aR.size();
        for (int i3 = 0; i3 < i; i3++) {
            a aVar2 = (a) aVar.aR.get(i3);
            if (aVar2.aO == cv.D) {
                dd a3 = cw.a(aVar2, aVar.d(cv.C), j, false);
                if (a3 != null) {
                    sparseArray2.put(a3.g, a3);
                }
            }
        }
        aVar = sparseArray2.size();
        if (this.e.size() == 0) {
            for (int i4 = 0; i4 < aVar; i4++) {
                this.e.put(((dd) sparseArray2.valueAt(i4)).g, new a(this.v.d(i4)));
            }
            this.v.f();
        } else {
            if (this.e.size() != aVar) {
                z = false;
            }
            fe.b(z);
        }
        while (i2 < aVar) {
            dd ddVar = (dd) sparseArray2.valueAt(i2);
            ((a) this.e.get(ddVar.g)).a(ddVar, (cx) sparseArray.get(ddVar.g));
            i2++;
        }
    }

    private void c(a aVar) throws bl {
        a(aVar, this.e, this.c, this.j);
        bu a = a(aVar.aQ);
        if (a != null) {
            this.v.a(a);
        }
    }

    private static Pair<Integer, cx> a(fp fpVar) {
        fpVar.c(12);
        return Pair.create(Integer.valueOf(fpVar.m()), new cx(fpVar.s() - 1, fpVar.s(), fpVar.s(), fpVar.m()));
    }

    private static long b(fp fpVar) {
        fpVar.c(8);
        return cv.a(fpVar.m()) == 0 ? fpVar.k() : fpVar.u();
    }

    private static void a(a aVar, SparseArray<a> sparseArray, int i, byte[] bArr) throws bl {
        int size = aVar.aR.size();
        for (int i2 = 0; i2 < size; i2++) {
            a aVar2 = (a) aVar.aR.get(i2);
            if (aVar2.aO == cv.L) {
                b(aVar2, sparseArray, i, bArr);
            }
        }
    }

    private static void b(a aVar, SparseArray<a> sparseArray, int i, byte[] bArr) throws bl {
        if (aVar.f(cv.z) == 1) {
            a a = a(aVar.d(cv.x).aP, (SparseArray) sparseArray, i);
            if (a != null) {
                df dfVar = a.a;
                long j = dfVar.o;
                a.a();
                if (aVar.d(cv.w) != null && (i & 2) == 0) {
                    j = c(aVar.d(cv.w).aP);
                }
                a(a, j, i, aVar.d(cv.z).aP);
                i = aVar.d(cv.ac);
                if (i != 0) {
                    a(a.c.m[dfVar.a.a], i.aP, dfVar);
                }
                sparseArray = aVar.d(cv.ad);
                if (sparseArray != null) {
                    a(sparseArray.aP, dfVar);
                }
                sparseArray = aVar.d(cv.ah);
                if (sparseArray != null) {
                    b(sparseArray.aP, dfVar);
                }
                sparseArray = aVar.d(cv.ae);
                i = aVar.d(cv.af);
                if (!(sparseArray == null || i == 0)) {
                    a(sparseArray.aP, i.aP, dfVar);
                }
                sparseArray = aVar.aQ.size();
                for (i = 0; i < sparseArray; i++) {
                    b bVar = (b) aVar.aQ.get(i);
                    if (bVar.aO == cv.ag) {
                        a(bVar.aP, dfVar, bArr);
                    }
                }
                return;
            }
            return;
        }
        throw new bl("Trun count in traf != 1 (unsupported).");
    }

    private static void a(de deVar, fp fpVar, df dfVar) throws bl {
        deVar = deVar.b;
        fpVar.c(8);
        boolean z = true;
        if ((cv.b(fpVar.m()) & 1) == 1) {
            fpVar.d(8);
        }
        int f = fpVar.f();
        int s = fpVar.s();
        if (s == dfVar.d) {
            int i;
            if (f == 0) {
                boolean[] zArr = dfVar.j;
                i = 0;
                for (int i2 = 0; i2 < s; i2++) {
                    int f2 = fpVar.f();
                    i += f2;
                    zArr[i2] = f2 > deVar;
                }
            } else {
                if (f <= deVar) {
                    z = false;
                }
                i = (f * s) + 0;
                Arrays.fill(dfVar.j, 0, s, z);
            }
            dfVar.b(i);
            return;
        }
        fpVar = dfVar.d;
        StringBuilder stringBuilder = new StringBuilder(41);
        stringBuilder.append("Length mismatch: ");
        stringBuilder.append(s);
        stringBuilder.append(", ");
        stringBuilder.append(fpVar);
        throw new bl(stringBuilder.toString());
    }

    private static void a(fp fpVar, df dfVar) throws bl {
        fpVar.c(8);
        int m = fpVar.m();
        if ((cv.b(m) & 1) == 1) {
            fpVar.d(8);
        }
        int s = fpVar.s();
        if (s == 1) {
            dfVar.c += cv.a(m) == 0 ? fpVar.k() : fpVar.u();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(40);
        stringBuilder.append("Unexpected saio entry count: ");
        stringBuilder.append(s);
        throw new bl(stringBuilder.toString());
    }

    private static a a(fp fpVar, SparseArray<a> sparseArray, int i) {
        fpVar.c(8);
        int b = cv.b(fpVar.m());
        int m = fpVar.m();
        if ((i & 4) != 0) {
            m = 0;
        }
        a aVar = (a) sparseArray.get(m);
        if (aVar == null) {
            return null;
        }
        if ((b & 1) != 0) {
            long u = fpVar.u();
            aVar.a.b = u;
            aVar.a.c = u;
        }
        i = aVar.d;
        aVar.a.a = new cx((b & 2) != 0 ? fpVar.s() - 1 : i.a, (b & 8) != 0 ? fpVar.s() : i.b, (b & 16) != 0 ? fpVar.s() : i.c, (b & 32) != 0 ? fpVar.s() : i.d);
        return aVar;
    }

    private static long c(fp fpVar) {
        fpVar.c(8);
        return cv.a(fpVar.m()) == 1 ? fpVar.u() : fpVar.k();
    }

    private static void a(a aVar, long j, int i, fp fpVar) {
        a aVar2 = aVar;
        fpVar.c(8);
        int b = cv.b(fpVar.m());
        dd ddVar = aVar2.c;
        df dfVar = aVar2.a;
        cx cxVar = dfVar.a;
        int s = fpVar.s();
        if ((b & 1) != 0) {
            dfVar.b += (long) fpVar.m();
        }
        Object obj = (b & 4) != 0 ? 1 : null;
        int i2 = cxVar.d;
        if (obj != null) {
            i2 = fpVar.s();
        }
        Object obj2 = (b & 256) != 0 ? 1 : null;
        Object obj3 = (b & 512) != 0 ? 1 : null;
        Object obj4 = (b & 1024) != 0 ? 1 : null;
        Object obj5 = (b & 2048) != 0 ? 1 : null;
        long j2 = 0;
        if (ddVar.n != null && ddVar.n.length == 1 && ddVar.n[0] == 0) {
            j2 = ft.a(ddVar.o[0], 1000, ddVar.i);
        }
        dfVar.a(s);
        int[] iArr = dfVar.e;
        int[] iArr2 = dfVar.f;
        long[] jArr = dfVar.g;
        boolean[] zArr = dfVar.h;
        long[] jArr2 = jArr;
        long j3 = j2;
        long j4 = ddVar.i;
        Object obj6 = (ddVar.h != dd.a || (i & 1) == 0) ? null : 1;
        long j5 = j;
        int i3 = 0;
        while (i3 < s) {
            int i4;
            int s2;
            int i5;
            Object obj7;
            Object obj8;
            Object obj9;
            if (obj2 != null) {
                i4 = s;
                s = fpVar.s();
            } else {
                i4 = s;
                s = cxVar.b;
            }
            if (obj3 != null) {
                s2 = fpVar.s();
                i5 = i2;
            } else {
                i5 = i2;
                s2 = cxVar.c;
            }
            i2 = (i3 != 0 || obj == null) ? obj4 != null ? fpVar.m() : cxVar.d : i5;
            if (obj5 != null) {
                obj7 = obj5;
                obj8 = obj2;
                obj9 = obj3;
                iArr2[i3] = (int) (((long) (fpVar.m() * 1000)) / j4);
            } else {
                obj7 = obj5;
                obj8 = obj2;
                obj9 = obj3;
                iArr2[i3] = 0;
            }
            jArr2[i3] = ft.a(j5, 1000, j4) - j3;
            iArr[i3] = s2;
            boolean z = ((i2 >> 16) & 1) == 0 && (obj6 == null || i3 == 0);
            zArr[i3] = z;
            j5 += (long) s;
            i3++;
            s = i4;
            i2 = i5;
            obj5 = obj7;
            obj2 = obj8;
            obj3 = obj9;
            zArr = zArr;
        }
        dfVar.o = j5;
    }

    private static void a(fp fpVar, df dfVar, byte[] bArr) throws bl {
        fpVar.c(8);
        fpVar.a(bArr, 0, 16);
        if (Arrays.equals(bArr, b) != null) {
            a(fpVar, 16, dfVar);
        }
    }

    private static void b(fp fpVar, df dfVar) throws bl {
        a(fpVar, 0, dfVar);
    }

    private static void a(fp fpVar, int i, df dfVar) throws bl {
        fpVar.c(i + 8);
        i = cv.b(fpVar.m());
        if ((i & 1) == 0) {
            i = (i & 2) != 0 ? 1 : 0;
            int s = fpVar.s();
            if (s == dfVar.d) {
                Arrays.fill(dfVar.j, 0, s, i);
                dfVar.b(fpVar.b());
                dfVar.a(fpVar);
                return;
            }
            i = dfVar.d;
            StringBuilder stringBuilder = new StringBuilder(41);
            stringBuilder.append("Length mismatch: ");
            stringBuilder.append(s);
            stringBuilder.append(", ");
            stringBuilder.append(i);
            throw new bl(stringBuilder.toString());
        }
        throw new bl("Overriding TrackEncryptionBox parameters is unsupported.");
    }

    private static void a(fp fpVar, fp fpVar2, df dfVar) throws bl {
        fpVar.c(8);
        int m = fpVar.m();
        if (fpVar.m() == a) {
            if (cv.a(m) == 1) {
                fpVar.d(4);
            }
            if (fpVar.m() == 1) {
                fpVar2.c(8);
                fpVar = fpVar2.m();
                if (fpVar2.m() == a) {
                    fpVar = cv.a(fpVar);
                    if (fpVar == 1) {
                        if (fpVar2.k() == 0) {
                            throw new bl("Variable length decription in sgpd found (unsupported)");
                        }
                    } else if (fpVar >= 2) {
                        fpVar2.d(4);
                    }
                    if (fpVar2.k() == 1) {
                        fpVar2.d(2);
                        fpVar = fpVar2.f() == 1 ? true : null;
                        if (fpVar != null) {
                            m = fpVar2.f();
                            byte[] bArr = new byte[16];
                            fpVar2.a(bArr, 0, bArr.length);
                            dfVar.i = true;
                            dfVar.n = new de(fpVar, m, bArr);
                            return;
                        }
                        return;
                    }
                    throw new bl("Entry count in sgpd != 1 (unsupported).");
                }
                return;
            }
            throw new bl("Entry count in sbgp != 1 (unsupported).");
        }
    }

    private static by b(fp fpVar, long j) throws bl {
        long k;
        long k2;
        fp fpVar2 = fpVar;
        fpVar2.c(8);
        int a = cv.a(fpVar.m());
        fpVar2.d(4);
        long k3 = fpVar.k();
        if (a == 0) {
            k = fpVar.k();
            k2 = j + fpVar.k();
        } else {
            k = fpVar.u();
            k2 = j + fpVar.u();
        }
        long j2 = k2;
        k2 = k;
        fpVar2.d(2);
        a = fpVar.g();
        int[] iArr = new int[a];
        long[] jArr = new long[a];
        long j3 = k2;
        long[] jArr2 = new long[a];
        long[] jArr3 = new long[a];
        k = ft.a(k2, 1000000, k3);
        long j4 = j2;
        int i = 0;
        while (i < a) {
            int m = fpVar.m();
            if ((Integer.MIN_VALUE & m) == 0) {
                long k4 = fpVar.k();
                iArr[i] = m & Integer.MAX_VALUE;
                jArr2[i] = j4;
                jArr[i] = k;
                j3 += k4;
                k = ft.a(j3, 1000000, k3);
                jArr3[i] = k - jArr[i];
                fpVar2.d(4);
                j4 += (long) iArr[i];
                i++;
            } else {
                throw new bl("Unhandled indirect reference");
            }
        }
        return new by(iArr, jArr2, jArr3, jArr);
    }

    private void d(cd cdVar) throws IOException, InterruptedException {
        int size = this.e.size();
        a aVar = null;
        long j = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            df dfVar = ((a) this.e.valueAt(i)).a;
            if (dfVar.m && dfVar.c < j) {
                long j2 = dfVar.c;
                aVar = (a) this.e.valueAt(i);
                j = j2;
            }
        }
        if (aVar == null) {
            this.l = 3;
            return;
        }
        size = (int) (j - cdVar.c());
        if (size >= 0) {
            cdVar.b(size);
            aVar.a.a(cdVar);
            return;
        }
        throw new bl("Offset to encryption data was negative.");
    }

    private boolean e(cd cdVar) throws IOException, InterruptedException {
        byte[] bArr;
        int i = 0;
        if (this.l == 3) {
            if (this.r == null) {
                this.r = a(this.e);
                int c;
                if (this.r == null) {
                    c = (int) (this.q - cdVar.c());
                    if (c >= 0) {
                        cdVar.b(c);
                        a();
                        return false;
                    }
                    throw new bl("Offset to end of mdat was negative.");
                }
                c = (int) (this.r.a.b - cdVar.c());
                if (c < 0) {
                    Log.w("FragmentedMp4Extractor", "Ignoring negative offset to sample data.");
                    c = 0;
                }
                cdVar.b(c);
            }
            this.s = this.r.a.e[this.r.e];
            if (this.r.a.i) {
                this.t = a(this.r);
                this.s += this.t;
            } else {
                this.t = 0;
            }
            this.l = 4;
            this.u = 0;
        }
        df dfVar = this.r.a;
        dd ddVar = this.r.c;
        ck ckVar = this.r.b;
        int i2 = this.r.e;
        if (ddVar.p != -1) {
            byte[] bArr2 = this.g.a;
            bArr2[0] = (byte) 0;
            bArr2[1] = (byte) 0;
            bArr2[2] = (byte) 0;
            int i3 = ddVar.p;
            int i4 = 4 - ddVar.p;
            while (this.t < this.s) {
                if (this.u == 0) {
                    cdVar.b(this.g.a, i4, i3);
                    this.g.c(0);
                    this.u = this.g.s();
                    this.f.c(0);
                    ckVar.a(this.f, 4);
                    this.t += 4;
                    this.s += i4;
                } else {
                    int a = ckVar.a(cdVar, this.u, false);
                    this.t += a;
                    this.u -= a;
                }
            }
        } else {
            while (this.t < this.s) {
                this.t += ckVar.a(cdVar, this.s - this.t, false);
            }
        }
        long c2 = dfVar.c(i2) * 1000;
        if (dfVar.i != null) {
            i = 2;
        }
        int i5 = i | dfVar.h[i2];
        cdVar = dfVar.a.a;
        if (dfVar.i) {
            if (dfVar.n != null) {
                cdVar = dfVar.n.c;
            } else {
                cdVar = ddVar.m[cdVar].c;
            }
            bArr = cdVar;
        } else {
            bArr = null;
        }
        ckVar.a(c2, i5, this.s, 0, bArr);
        cdVar = this.r;
        cdVar.e++;
        if (this.r.e == dfVar.d) {
            this.r = null;
        }
        this.l = 3;
        return true;
    }

    private static a a(SparseArray<a> sparseArray) {
        int size = sparseArray.size();
        a aVar = null;
        long j = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            a aVar2 = (a) sparseArray.valueAt(i);
            if (aVar2.e != aVar2.a.d) {
                long j2 = aVar2.a.b;
                if (j2 < j) {
                    aVar = aVar2;
                    j = j2;
                }
            }
        }
        return aVar;
    }

    private int a(a aVar) {
        de deVar;
        df dfVar = aVar.a;
        fp fpVar = dfVar.l;
        int i = dfVar.a.a;
        if (dfVar.n != null) {
            deVar = dfVar.n;
        } else {
            deVar = aVar.c.m[i];
        }
        i = deVar.b;
        boolean z = dfVar.j[aVar.e];
        this.h.a[0] = (byte) ((z ? 128 : 0) | i);
        this.h.c(0);
        aVar = aVar.b;
        aVar.a(this.h, 1);
        aVar.a(fpVar, i);
        if (!z) {
            return i + 1;
        }
        int g = fpVar.g();
        fpVar.d(-2);
        g = (g * 6) + 2;
        aVar.a(fpVar, g);
        return (i + 1) + g;
    }

    private static com.google.ads.interactivemedia.v3.internal.bu.a a(List<b> list) {
        int size = list.size();
        com.google.ads.interactivemedia.v3.internal.bu.a aVar = null;
        for (int i = 0; i < size; i++) {
            b bVar = (b) list.get(i);
            if (bVar.aO == cv.U) {
                if (aVar == null) {
                    aVar = new com.google.ads.interactivemedia.v3.internal.bu.a();
                }
                byte[] bArr = bVar.aP.a;
                if (db.a(bArr) == null) {
                    Log.w("FragmentedMp4Extractor", "Skipped pssh atom (failed to extract uuid)");
                } else {
                    aVar.a(db.a(bArr), new b(MimeTypes.VIDEO_MP4, bArr));
                }
            }
        }
        return aVar;
    }

    private static boolean a(int i) {
        if (!(i == cv.S || i == cv.R || i == cv.C || i == cv.A || i == cv.T || i == cv.w || i == cv.x || i == cv.O || i == cv.y || i == cv.z || i == cv.U || i == cv.ac || i == cv.ad || i == cv.ah || i == cv.ae || i == cv.af || i == cv.ag || i == cv.Q || i == cv.N)) {
            if (i != cv.aF) {
                return false;
            }
        }
        return true;
    }

    private static boolean b(int i) {
        if (!(i == cv.B || i == cv.D || i == cv.E || i == cv.F || i == cv.G || i == cv.K || i == cv.L || i == cv.M)) {
            if (i != cv.P) {
                return false;
            }
        }
        return true;
    }
}
