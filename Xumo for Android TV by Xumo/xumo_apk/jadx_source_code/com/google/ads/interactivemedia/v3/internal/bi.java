package com.google.ads.interactivemedia.v3.internal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Surface;
import com.google.ads.interactivemedia.v3.internal.bg.b;
import com.google.android.exoplayer2.util.MimeTypes;

@TargetApi(16)
/* compiled from: IMASDK */
public class bi extends bg {
    private final br c;
    private final a d;
    private final long e;
    private final int f;
    private final int g;
    private Surface h;
    private boolean i;
    private boolean j;
    private long k;
    private long l;
    private int m;
    private int n;
    private int o;
    private float p;
    private int q;
    private int r;
    private int s;
    private float t;
    private int u;
    private int v;
    private int w;
    private float x;

    /* compiled from: IMASDK */
    public interface a extends b {
        void a(int i, int i2, int i3, float f);

        void a(int i, long j);

        void a(Surface surface);
    }

    public bi(Context context, bn bnVar, bf bfVar, int i, long j, Handler handler, a aVar, int i2) {
        this(context, bnVar, bfVar, i, j, null, false, handler, aVar, i2);
    }

    protected boolean a(long j, long j2) {
        return j < -30000;
    }

    public bi(Context context, bn bnVar, bf bfVar, int i, long j, bv<bx> bvVar, boolean z, Handler handler, a aVar, int i2) {
        super(bnVar, bfVar, (bv) bvVar, z, handler, (b) aVar);
        Context context2 = context;
        this.c = new br(context);
        this.f = i;
        this.e = 1000 * j;
        this.d = aVar;
        this.g = i2;
        this.k = -1;
        this.q = -1;
        this.r = -1;
        this.t = -1.0f;
        this.p = -1.0f;
        this.u = -1;
        this.v = -1;
        this.x = -1.0f;
    }

    protected boolean a(bf bfVar, bj bjVar) throws bh.b {
        bjVar = bjVar.b;
        if (!fl.b(bjVar)) {
            return false;
        }
        if (MimeTypes.VIDEO_UNKNOWN.equals(bjVar) || bfVar.a(bjVar, false) != null) {
            return true;
        }
        return false;
    }

    protected void a(int i, long j, boolean z) throws az {
        super.a(i, j, z);
        if (z && this.e > 0) {
            this.k = (SystemClock.elapsedRealtime() * 1000) + this.e;
        }
        this.c.a();
    }

    protected void a(long j) throws az {
        super.a(j);
        this.j = false;
        this.n = 0;
        this.k = -1;
    }

    protected boolean f() {
        if (super.f() && (this.j || !l() || o() == 2)) {
            this.k = -1;
            return true;
        } else if (this.k == -1) {
            return false;
        } else {
            if (SystemClock.elapsedRealtime() * 1000 < this.k) {
                return true;
            }
            this.k = -1;
            return false;
        }
    }

    protected void c() {
        super.c();
        this.m = 0;
        this.l = SystemClock.elapsedRealtime();
    }

    protected void d() {
        this.k = -1;
        A();
        super.d();
    }

    protected void g() throws az {
        this.q = -1;
        this.r = -1;
        this.t = -1.0f;
        this.p = -1.0f;
        this.u = -1;
        this.v = -1;
        this.x = -1.0f;
        this.c.b();
        super.g();
    }

    public void a(int i, Object obj) throws az {
        if (i == 1) {
            a((Surface) obj);
        } else {
            super.a(i, obj);
        }
    }

    private void a(Surface surface) throws az {
        if (this.h != surface) {
            this.h = surface;
            this.i = null;
            surface = v();
            if (surface == 2 || surface == 3) {
                m();
                j();
            }
        }
    }

    protected boolean k() {
        return super.k() && this.h != null && this.h.isValid();
    }

    protected void a(MediaCodec mediaCodec, boolean z, MediaFormat mediaFormat, MediaCrypto mediaCrypto) {
        a(mediaFormat, z);
        mediaCodec.configure(mediaFormat, this.h, mediaCrypto, 0);
    }

    protected void a(bk bkVar) throws az {
        float f;
        super.a(bkVar);
        if (bkVar.a.m == -1.0f) {
            f = 1.0f;
        } else {
            f = bkVar.a.m;
        }
        this.p = f;
        if (bkVar.a.l == -1) {
            bkVar = null;
        } else {
            bkVar = bkVar.a.l;
        }
        this.o = bkVar;
    }

    protected void a(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        int integer;
        int integer2;
        Object obj = (mediaFormat.containsKey("crop-right") && mediaFormat.containsKey("crop-left") && mediaFormat.containsKey("crop-bottom") && mediaFormat.containsKey("crop-top")) ? 1 : null;
        if (obj != null) {
            integer = (mediaFormat.getInteger("crop-right") - mediaFormat.getInteger("crop-left")) + 1;
        } else {
            integer = mediaFormat.getInteger("width");
        }
        this.q = integer;
        if (obj != null) {
            integer2 = (mediaFormat.getInteger("crop-bottom") - mediaFormat.getInteger("crop-top")) + 1;
        } else {
            integer2 = mediaFormat.getInteger("height");
        }
        this.r = integer2;
        this.t = this.p;
        if (ft.a < 21) {
            this.s = this.o;
        } else if (this.o == 90 || this.o == 270) {
            mediaFormat = this.q;
            this.q = this.r;
            this.r = mediaFormat;
            this.t = 1065353216 / this.t;
        }
        mediaCodec.setVideoScalingMode(this.f);
    }

    protected boolean a(MediaCodec mediaCodec, boolean z, bj bjVar, bj bjVar2) {
        return (bjVar2.b.equals(bjVar.b) == null || !(z || (bjVar.h == bjVar2.h && bjVar.i == bjVar2.i))) ? null : true;
    }

    protected boolean a(long r17, long r19, android.media.MediaCodec r21, java.nio.ByteBuffer r22, android.media.MediaCodec.BufferInfo r23, int r24, boolean r25) {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r16 = this;
        r0 = r16;
        r1 = r19;
        r3 = r21;
        r4 = r23;
        r5 = r24;
        r6 = 1;
        r7 = 0;
        if (r25 == 0) goto L_0x0014;
    L_0x000e:
        r0.a(r3, r5);
        r0.n = r7;
        return r6;
    L_0x0014:
        r8 = r0.j;
        r9 = 21;
        if (r8 != 0) goto L_0x002c;
    L_0x001a:
        r1 = com.google.ads.interactivemedia.v3.internal.ft.a;
        if (r1 < r9) goto L_0x0026;
    L_0x001e:
        r1 = java.lang.System.nanoTime();
        r0.a(r3, r5, r1);
        goto L_0x0029;
    L_0x0026:
        r0.c(r3, r5);
    L_0x0029:
        r0.n = r7;
        return r6;
    L_0x002c:
        r8 = r16.v();
        r10 = 3;
        if (r8 == r10) goto L_0x0034;
    L_0x0033:
        return r7;
    L_0x0034:
        r10 = android.os.SystemClock.elapsedRealtime();
        r12 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r10 = r10 * r12;
        r10 = r10 - r1;
        r14 = r4.presentationTimeUs;
        r14 = r14 - r17;
        r14 = r14 - r10;
        r10 = java.lang.System.nanoTime();
        r14 = r14 * r12;
        r14 = r14 + r10;
        r8 = r0.c;
        r6 = r4.presentationTimeUs;
        r6 = r8.a(r6, r14);
        r10 = r6 - r10;
        r10 = r10 / r12;
        r1 = r0.a(r10, r1);
        if (r1 == 0) goto L_0x005f;
    L_0x005a:
        r0.b(r3, r5);
        r1 = 1;
        return r1;
    L_0x005f:
        r1 = 1;
        r2 = com.google.ads.interactivemedia.v3.internal.ft.a;
        if (r2 < r9) goto L_0x0074;
    L_0x0064:
        r8 = 50000; // 0xc350 float:7.0065E-41 double:2.47033E-319;
        r2 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1));
        if (r2 >= 0) goto L_0x0072;
    L_0x006b:
        r0.a(r3, r5, r6);
        r2 = 0;
        r0.n = r2;
        return r1;
    L_0x0072:
        r1 = 0;
        goto L_0x0097;
    L_0x0074:
        r1 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r4 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1));
        if (r4 >= 0) goto L_0x0072;
    L_0x007a:
        r1 = 11000; // 0x2af8 float:1.5414E-41 double:5.4347E-320;
        r4 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1));
        if (r4 <= 0) goto L_0x008f;
    L_0x0080:
        r1 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r10 = r10 - r1;
        r10 = r10 / r12;	 Catch:{ InterruptedException -> 0x0088 }
        java.lang.Thread.sleep(r10);	 Catch:{ InterruptedException -> 0x0088 }
        goto L_0x008f;
    L_0x0088:
        r1 = java.lang.Thread.currentThread();
        r1.interrupt();
    L_0x008f:
        r0.c(r3, r5);
        r1 = 0;
        r0.n = r1;
        r1 = 1;
        return r1;
    L_0x0097:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.bi.a(long, long, android.media.MediaCodec, java.nio.ByteBuffer, android.media.MediaCodec$BufferInfo, int, boolean):boolean");
    }

    protected void a(MediaCodec mediaCodec, int i) {
        fs.a("skipVideoBuffer");
        mediaCodec.releaseOutputBuffer(i, false);
        fs.a();
        mediaCodec = this.a;
        mediaCodec.g++;
    }

    protected void b(MediaCodec mediaCodec, int i) {
        fs.a("dropVideoBuffer");
        mediaCodec.releaseOutputBuffer(i, false);
        fs.a();
        mediaCodec = this.a;
        mediaCodec.h++;
        this.m++;
        this.n++;
        this.a.i = Math.max(this.n, this.a.i);
        if (this.m == this.g) {
            A();
        }
    }

    protected void c(MediaCodec mediaCodec, int i) {
        a();
        fs.a("releaseOutputBuffer");
        mediaCodec.releaseOutputBuffer(i, true);
        fs.a();
        mediaCodec = this.a;
        mediaCodec.f++;
        this.j = true;
        i();
    }

    @TargetApi(21)
    protected void a(MediaCodec mediaCodec, int i, long j) {
        a();
        fs.a("releaseOutputBuffer");
        mediaCodec.releaseOutputBuffer(i, j);
        fs.a();
        mediaCodec = this.a;
        mediaCodec.f++;
        this.j = true;
        i();
    }

    @SuppressLint({"InlinedApi"})
    private void a(MediaFormat mediaFormat, boolean z) {
        if (!mediaFormat.containsKey("max-input-size")) {
            int integer = mediaFormat.getInteger("height");
            if (z && mediaFormat.containsKey("max-height")) {
                integer = Math.max(integer, mediaFormat.getInteger("max-height"));
            }
            int integer2 = mediaFormat.getInteger("width");
            if (z && mediaFormat.containsKey("max-width")) {
                integer2 = Math.max(integer, mediaFormat.getInteger("max-width"));
            }
            z = mediaFormat.getString("mime");
            Object obj = -1;
            int i = 4;
            switch (z.hashCode()) {
                case -1664118616:
                    if (z.equals(MimeTypes.VIDEO_H263)) {
                        obj = null;
                        break;
                    }
                    break;
                case -1662541442:
                    if (z.equals(MimeTypes.VIDEO_H265)) {
                        obj = 4;
                        break;
                    }
                    break;
                case 1187890754:
                    if (z.equals(MimeTypes.VIDEO_MP4V)) {
                        obj = 1;
                        break;
                    }
                    break;
                case 1331836730:
                    if (z.equals(MimeTypes.VIDEO_H264)) {
                        obj = 2;
                        break;
                    }
                    break;
                case 1599127256:
                    if (z.equals(MimeTypes.VIDEO_VP8)) {
                        obj = 3;
                        break;
                    }
                    break;
                case 1599127257:
                    if (z.equals(MimeTypes.VIDEO_VP9)) {
                        obj = 5;
                        break;
                    }
                    break;
                default:
                    break;
            }
            switch (obj) {
                case null:
                case 1:
                    integer2 *= integer;
                    break;
                case 2:
                    if (!"BRAVIA 4K 2015".equals(ft.d)) {
                        integer2 = ((((integer2 + 15) / 16) * ((integer + 15) / 16)) * 16) * 16;
                        break;
                    }
                    return;
                case 3:
                    integer2 *= integer;
                    break;
                case 4:
                case 5:
                    integer2 *= integer;
                    break;
                default:
                    return;
            }
            i = 2;
            mediaFormat.setInteger("max-input-size", (integer2 * 3) / (i * 2));
        }
    }

    private void a() {
        if (!(this.b == null || this.d == null)) {
            if (this.u != this.q || this.v != this.r || this.w != this.s || this.x != this.t) {
                int i = this.q;
                int i2 = this.r;
                int i3 = this.s;
                float f = this.t;
                final int i4 = i;
                final int i5 = i2;
                final int i6 = i3;
                final float f2 = f;
                this.b.post(new Runnable(this) {
                    final /* synthetic */ bi e;

                    public void run() {
                        this.e.d.a(i4, i5, i6, f2);
                    }
                });
                this.u = i;
                this.v = i2;
                this.w = i3;
                this.x = f;
            }
        }
    }

    private void i() {
        if (!(this.b == null || this.d == null)) {
            if (!this.i) {
                final Surface surface = this.h;
                this.b.post(new Runnable(this) {
                    final /* synthetic */ bi b;

                    public void run() {
                        this.b.d.a(surface);
                    }
                });
                this.i = true;
            }
        }
    }

    private void A() {
        if (!(this.b == null || this.d == null)) {
            if (this.m != 0) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                final int i = this.m;
                final long j = elapsedRealtime - this.l;
                this.b.post(new Runnable(this) {
                    final /* synthetic */ bi c;

                    public void run() {
                        this.c.d.a(i, j);
                    }
                });
                this.m = 0;
                this.l = elapsedRealtime;
            }
        }
    }
}
