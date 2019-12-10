package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.dp.b;
import com.google.ads.interactivemedia.v3.internal.dp.c;
import com.google.ads.interactivemedia.v3.internal.dp.d;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.util.ArrayList;

/* compiled from: IMASDK */
final class do extends dm implements cj {
    private a e;
    private int g;
    private long h;
    private boolean i;
    private final dk j = new dk();
    private long k = -1;
    private d l;
    private b m;
    private long n;
    private long o;
    private long p;
    private long q;

    /* compiled from: IMASDK */
    static final class a {
        public final d a;
        public final b b;
        public final byte[] c;
        public final c[] d;
        public final int e;

        public a(d dVar, b bVar, byte[] bArr, c[] cVarArr, int i) {
            this.a = dVar;
            this.b = bVar;
            this.c = bArr;
            this.d = cVarArr;
            this.e = i;
        }
    }

    do() {
    }

    static boolean a(com.google.ads.interactivemedia.v3.internal.fp r1) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = 1;
        r1 = com.google.ads.interactivemedia.v3.internal.dp.a(r0, r1, r0);	 Catch:{ bl -> 0x0006 }
        return r1;
    L_0x0006:
        r1 = 0;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.do.a(com.google.ads.interactivemedia.v3.internal.fp):boolean");
    }

    public void b() {
        super.b();
        this.g = 0;
        this.h = 0;
        this.i = false;
    }

    public int a(cd cdVar, ch chVar) throws IOException, InterruptedException {
        long j;
        cd cdVar2 = cdVar;
        ch chVar2 = chVar;
        if (this.p == 0) {
            long j2;
            if (r0.e == null) {
                r0.n = cdVar.d();
                r0.e = a(cdVar2, r0.a);
                r0.o = cdVar.c();
                r0.d.a(r0);
                if (r0.n != -1) {
                    chVar2.a = Math.max(0, cdVar.d() - 8000);
                    return 1;
                }
            }
            if (r0.n == -1) {
                j = -1;
            } else {
                j = r0.b.a(cdVar2);
            }
            r0.p = j;
            ArrayList arrayList = new ArrayList();
            arrayList.add(r0.e.a.j);
            arrayList.add(r0.e.c);
            if (r0.n == -1) {
                j2 = -1;
            } else {
                j2 = (r0.p * 1000000) / r0.e.a.c;
            }
            r0.q = j2;
            r0.c.a(bj.a(null, MimeTypes.AUDIO_VORBIS, r0.e.a.e, OggPageHeader.MAX_PAGE_PAYLOAD, r0.q, r0.e.a.b, (int) r0.e.a.c, arrayList, null));
            if (r0.n != -1) {
                r0.j.a(r0.n - r0.o, r0.p);
                chVar2.a = r0.o;
                return 1;
            }
        }
        if (!r0.i && r0.k > -1) {
            dl.a(cdVar);
            j = r0.j.a(r0.k, cdVar2);
            if (j != -1) {
                chVar2.a = j;
                return 1;
            }
            r0.h = r0.b.a(cdVar2, r0.k);
            r0.g = r0.l.g;
            r0.i = true;
        }
        if (!r0.b.a(cdVar2, r0.a)) {
            return -1;
        }
        if ((r0.a.a[0] & 1) != 1) {
            int a = a(r0.a.a[0], r0.e);
            long j3 = (long) (r0.i ? (r0.g + a) / 4 : 0);
            if (r0.h + j3 >= r0.k) {
                a(r0.a, j3);
                long j4 = (r0.h * 1000000) / r0.e.a.c;
                r0.c.a(r0.a, r0.a.c());
                r0.c.a(j4, 1, r0.a.c(), 0, null);
                r0.k = -1;
            }
            r0.i = true;
            r0.h += j3;
            r0.g = a;
        }
        r0.a.a();
        return 0;
    }

    a a(cd cdVar, fp fpVar) throws IOException, InterruptedException {
        if (this.l == null) {
            this.b.a(cdVar, fpVar);
            this.l = dp.a(fpVar);
            fpVar.a();
        }
        if (this.m == null) {
            this.b.a(cdVar, fpVar);
            this.m = dp.b(fpVar);
            fpVar.a();
        }
        this.b.a(cdVar, fpVar);
        Object obj = new byte[fpVar.c()];
        System.arraycopy(fpVar.a, 0, obj, 0, fpVar.c());
        c[] a = dp.a(fpVar, this.l.b);
        int a2 = dp.a(a.length - 1);
        fpVar.a();
        return new a(this.l, this.m, obj, a, a2);
    }

    static void a(fp fpVar, long j) {
        fpVar.b(fpVar.c() + 4);
        fpVar.a[fpVar.c() - 4] = (byte) ((int) (j & 255));
        fpVar.a[fpVar.c() - 3] = (byte) ((int) ((j >>> 8) & 255));
        fpVar.a[fpVar.c() - 2] = (byte) ((int) ((j >>> 16) & 255));
        fpVar.a[fpVar.c() - 1] = (byte) ((int) ((j >>> 24) & 255));
    }

    private static int a(byte b, a aVar) {
        if (aVar.d[dl.a(b, aVar.e, 1)].a == (byte) 0) {
            return aVar.a.g;
        }
        return aVar.a.h;
    }

    public boolean a() {
        return (this.e == null || this.n == -1) ? false : true;
    }

    public long b(long j) {
        if (j == 0) {
            this.k = -1;
            return this.o;
        }
        this.k = (this.e.a.c * j) / 1000000;
        return Math.max(this.o, (((this.n - this.o) * j) / this.q) - 4000);
    }
}
