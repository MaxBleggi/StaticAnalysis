package com.google.ads.interactivemedia.v3.internal;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodec.CodecException;
import android.media.MediaCodec.CryptoException;
import android.media.MediaCodec.CryptoInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.SystemClock;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@TargetApi(16)
/* compiled from: IMASDK */
public abstract class bg extends bo {
    private static final byte[] c = ft.d("0000016742C00BDA259000000168CE0F13200000016588840DCE7118A0002FBF1C31C3275D78");
    private ByteBuffer[] A;
    private long B;
    private int C;
    private int D;
    private boolean E;
    private boolean F;
    private int G;
    private int H;
    private boolean I;
    private boolean J;
    private int K;
    private boolean L;
    private boolean M;
    private boolean N;
    private boolean O;
    public final av a;
    protected final Handler b;
    private final bf d;
    private final bv<bx> e;
    private final boolean f;
    private final bm g;
    private final bk h;
    private final List<Long> i;
    private final BufferInfo j;
    private final b k;
    private final boolean l;
    private bj m;
    private bu n;
    private MediaCodec o;
    private boolean p;
    private boolean q;
    private boolean r;
    private boolean s;
    private boolean t;
    private boolean u;
    private boolean v;
    private boolean w;
    private boolean x;
    private boolean y;
    private ByteBuffer[] z;

    /* compiled from: IMASDK */
    public static class a extends Exception {
        public final String a;
        public final boolean b;
        public final String c;
        public final String d;

        public a(bj bjVar, Throwable th, boolean z, int i) {
            String valueOf = String.valueOf(bjVar);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 36);
            stringBuilder.append("Decoder init failed: [");
            stringBuilder.append(i);
            stringBuilder.append("], ");
            stringBuilder.append(valueOf);
            super(stringBuilder.toString(), th);
            this.a = bjVar.b;
            this.b = z;
            this.c = null;
            this.d = a(i);
        }

        public a(bj bjVar, Throwable th, boolean z, String str) {
            String valueOf = String.valueOf(bjVar);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 23) + String.valueOf(valueOf).length());
            stringBuilder.append("Decoder init failed: ");
            stringBuilder.append(str);
            stringBuilder.append(", ");
            stringBuilder.append(valueOf);
            super(stringBuilder.toString(), th);
            this.a = bjVar.b;
            this.b = z;
            this.c = str;
            this.d = ft.a >= true ? a(th) : null;
        }

        @TargetApi(21)
        private static String a(Throwable th) {
            return th instanceof CodecException ? ((CodecException) th).getDiagnosticInfo() : null;
        }

        private static String a(int i) {
            String str = i < 0 ? "neg_" : "";
            i = Math.abs(i);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 64);
            stringBuilder.append("com.google.ads.interactivemedia.v3.exoplayer.MediaCodecTrackRenderer_");
            stringBuilder.append(str);
            stringBuilder.append(i);
            return stringBuilder.toString();
        }
    }

    /* compiled from: IMASDK */
    public interface b {
        void a(CryptoException cryptoException);

        void a(a aVar);

        void a(String str, long j, long j2);
    }

    public bg(bn bnVar, bf bfVar, bv<bx> bvVar, boolean z, Handler handler, b bVar) {
        this(new bn[]{bnVar}, bfVar, (bv) bvVar, z, handler, bVar);
    }

    private boolean a(long r1, long r3) throws com.google.ads.interactivemedia.v3.internal.az {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.ads.interactivemedia.v3.internal.bg.a(long, long):boolean
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
*/
        /*
        r0 = this;
        r10 = r15;
        r0 = r10.M;
        r11 = 0;
        if (r0 == 0) goto L_0x0007;
        return r11;
        r0 = r10.D;
        if (r0 >= 0) goto L_0x003c;
        r0 = r10.v;
        if (r0 == 0) goto L_0x002e;
        r0 = r10.J;
        if (r0 == 0) goto L_0x002e;
        r0 = r10.o;
        r1 = r10.j;
        r2 = r15.p();
        r0 = r0.dequeueOutputBuffer(r1, r2);
        r10.D = r0;
        goto L_0x003c;
        r15.A();
        r0 = r10.M;
        if (r0 == 0) goto L_0x002d;
        r15.m();
        return r11;
        r0 = r10.o;
        r1 = r10.j;
        r2 = r15.p();
        r0 = r0.dequeueOutputBuffer(r1, r2);
        r10.D = r0;
        r0 = r10.D;
        r1 = -2;
        r12 = 1;
        if (r0 != r1) goto L_0x0046;
        r15.i();
        return r12;
        r0 = r10.D;
        r1 = -3;
        if (r0 != r1) goto L_0x005b;
        r0 = r10.o;
        r0 = r0.getOutputBuffers();
        r10.A = r0;
        r0 = r10.a;
        r1 = r0.e;
        r1 = r1 + r12;
        r0.e = r1;
        return r12;
        r0 = r10.D;
        if (r0 >= 0) goto L_0x0071;
        r0 = r10.t;
        if (r0 == 0) goto L_0x0070;
        r0 = r10.L;
        if (r0 != 0) goto L_0x006c;
        r0 = r10.H;
        r1 = 2;
        if (r0 != r1) goto L_0x0070;
        r15.A();
        return r12;
        return r11;
        r0 = r10.y;
        r13 = -1;
        if (r0 == 0) goto L_0x0082;
        r10.y = r11;
        r0 = r10.o;
        r1 = r10.D;
        r0.releaseOutputBuffer(r1, r11);
        r10.D = r13;
        return r12;
        r0 = r10.j;
        r0 = r0.flags;
        r0 = r0 & 4;
        if (r0 == 0) goto L_0x008e;
        r15.A();
        return r11;
        r0 = r10.j;
        r0 = r0.presentationTimeUs;
        r14 = r15.h(r0);
        r0 = r10.v;
        if (r0 == 0) goto L_0x00c5;
        r0 = r10.J;
        if (r0 == 0) goto L_0x00c5;
        r5 = r10.o;
        r0 = r10.A;
        r1 = r10.D;
        r6 = r0[r1];
        r7 = r10.j;
        r8 = r10.D;
        if (r14 == r13) goto L_0x00ae;
        r9 = 1;
        goto L_0x00af;
        r9 = 0;
        r0 = r15;
        r1 = r16;
        r3 = r18;
        r0 = r0.a(r1, r3, r5, r6, r7, r8, r9);
        goto L_0x00df;
        r15.A();
        r0 = r10.M;
        if (r0 == 0) goto L_0x00c4;
        r15.m();
        return r11;
        r5 = r10.o;
        r0 = r10.A;
        r1 = r10.D;
        r6 = r0[r1];
        r7 = r10.j;
        r8 = r10.D;
        if (r14 == r13) goto L_0x00d5;
        r9 = 1;
        goto L_0x00d6;
        r9 = 0;
        r0 = r15;
        r1 = r16;
        r3 = r18;
        r0 = r0.a(r1, r3, r5, r6, r7, r8, r9);
        if (r0 == 0) goto L_0x00f2;
        r0 = r10.j;
        r0 = r0.presentationTimeUs;
        r15.b(r0);
        if (r14 == r13) goto L_0x00ef;
        r0 = r10.i;
        r0.remove(r14);
        r10.D = r13;
        return r12;
        return r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.bg.a(long, long):boolean");
    }

    protected void a(long j, ByteBuffer byteBuffer, int i, boolean z) {
    }

    protected void a(MediaCodec mediaCodec, MediaFormat mediaFormat) throws az {
    }

    protected abstract void a(MediaCodec mediaCodec, boolean z, MediaFormat mediaFormat, MediaCrypto mediaCrypto);

    protected abstract boolean a(long j, long j2, MediaCodec mediaCodec, ByteBuffer byteBuffer, BufferInfo bufferInfo, int i, boolean z) throws az;

    protected boolean a(MediaCodec mediaCodec, boolean z, bj bjVar, bj bjVar2) {
        return false;
    }

    protected abstract boolean a(bf bfVar, bj bjVar) throws com.google.ads.interactivemedia.v3.internal.bh.b;

    protected void b(long j) {
    }

    protected void c() {
    }

    protected void d() {
    }

    protected void h() {
    }

    protected long p() {
        return 0;
    }

    public bg(bn[] bnVarArr, bf bfVar, bv<bx> bvVar, boolean z, Handler handler, b bVar) {
        super(bnVarArr);
        fe.b(ft.a >= 16 ? 1 : null);
        this.d = (bf) fe.a((Object) bfVar);
        this.e = bvVar;
        this.f = z;
        this.b = handler;
        this.k = bVar;
        this.l = B();
        this.a = new av();
        this.g = new bm(0);
        this.h = new bk();
        this.i = new ArrayList();
        this.j = new BufferInfo();
        this.G = 0;
        this.H = 0;
    }

    protected final boolean a(bj bjVar) throws com.google.ads.interactivemedia.v3.internal.bh.b {
        return a(this.d, bjVar);
    }

    protected ay a(bf bfVar, String str, boolean z) throws com.google.ads.interactivemedia.v3.internal.bh.b {
        return bfVar.a(str, z);
    }

    protected final void j() throws az {
        if (k()) {
            MediaCrypto mediaCrypto;
            boolean z;
            ay a;
            String str = this.m.b;
            boolean z2 = false;
            if (this.n == null) {
                mediaCrypto = null;
                z = false;
            } else if (this.e != null) {
                if (!this.E) {
                    this.e.a(this.n);
                    this.E = true;
                }
                int b = this.e.b();
                if (b != 0) {
                    if (b != 3) {
                        if (b != 4) {
                            return;
                        }
                    }
                    mediaCrypto = ((bx) this.e.c()).a();
                    z = this.e.a(str);
                } else {
                    throw new az(this.e.d());
                }
            } else {
                throw new az("Media requires a DrmSessionManager");
            }
            try {
                a = a(this.d, str, z);
            } catch (Throwable e) {
                a(new a(this.m, e, z, -49998));
                a = null;
            }
            if (a == null) {
                a(new a(this.m, null, z, -49999));
            }
            String str2 = a.a;
            if (a.c && !f(str2)) {
                z2 = true;
            }
            this.p = z2;
            this.q = a(str2, this.m);
            this.r = a(str2);
            this.s = b(str2);
            this.t = c(str2);
            this.u = d(str2);
            this.v = e(str2);
            this.w = b(str2, this.m);
            try {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 19);
                stringBuilder.append("createByCodecName(");
                stringBuilder.append(str2);
                stringBuilder.append(")");
                fs.a(stringBuilder.toString());
                this.o = MediaCodec.createByCodecName(str2);
                fs.a();
                fs.a("configureCodec");
                a(this.o, a.c, b(this.m), mediaCrypto);
                fs.a();
                fs.a("codec.start()");
                this.o.start();
                fs.a();
                long elapsedRealtime2 = SystemClock.elapsedRealtime();
                a(str2, elapsedRealtime2, elapsedRealtime2 - elapsedRealtime);
                this.z = this.o.getInputBuffers();
                this.A = this.o.getOutputBuffers();
            } catch (Throwable e2) {
                a(new a(this.m, e2, z, str2));
            }
            this.B = v() == 3 ? SystemClock.elapsedRealtime() : -1;
            this.C = -1;
            this.D = -1;
            this.O = true;
            av avVar = this.a;
            avVar.a++;
        }
    }

    private void a(a aVar) throws az {
        b(aVar);
        throw new az((Throwable) aVar);
    }

    protected boolean k() {
        return this.o == null && this.m != null;
    }

    protected final boolean l() {
        return this.o != null;
    }

    protected void g() throws az {
        this.m = null;
        this.n = null;
        try {
            m();
            try {
                if (this.E) {
                    this.e.a();
                    this.E = false;
                }
                super.g();
            } catch (Throwable th) {
                super.g();
            }
        } catch (Throwable th2) {
            super.g();
        }
    }

    protected void m() {
        if (this.o != null) {
            this.B = -1;
            this.C = -1;
            this.D = -1;
            this.N = false;
            this.i.clear();
            this.z = null;
            this.A = null;
            this.F = false;
            this.I = false;
            this.p = false;
            this.q = false;
            this.r = false;
            this.s = false;
            this.t = false;
            this.u = false;
            this.w = false;
            this.x = false;
            this.y = false;
            this.J = false;
            this.G = 0;
            this.H = 0;
            av avVar = this.a;
            avVar.b++;
            try {
                this.o.stop();
                try {
                    this.o.release();
                } finally {
                    this.o = null;
                }
            } catch (Throwable th) {
                this.o.release();
            } finally {
                this.o = null;
            }
        }
    }

    protected void a(long j) throws az {
        this.K = 0;
        this.L = false;
        this.M = false;
        if (this.o != null) {
            n();
        }
    }

    protected void a(long j, long j2, boolean z) throws az {
        z = z ? !this.K ? true : this.K : false;
        this.K = z;
        if (!this.m) {
            g(j);
        }
        j();
        if (this.o) {
            fs.a("drainAndFeed");
            while (a(j, j2)) {
            }
            if (a(j, true) != null) {
                while (a(j, false) != null) {
                }
            }
            fs.a();
        }
        this.a.a();
    }

    private void g(long j) throws az {
        if (a(j, this.h, null) == -4) {
            a(this.h);
        }
    }

    protected void n() throws az {
        this.B = -1;
        this.C = -1;
        this.D = -1;
        this.O = true;
        this.N = false;
        this.i.clear();
        this.x = false;
        this.y = false;
        if (!this.r) {
            if (!this.u || !this.J) {
                if (this.H != 0) {
                    m();
                    j();
                } else {
                    this.o.flush();
                    this.I = false;
                }
                if (this.F && this.m != null) {
                    this.G = 1;
                    return;
                }
            }
        }
        m();
        j();
        if (this.F) {
        }
    }

    private boolean a(long j, boolean z) throws az {
        if (!this.L) {
            if (r7.H != 2) {
                if (r7.C < 0) {
                    r7.C = r7.o.dequeueInputBuffer(0);
                    if (r7.C < 0) {
                        return false;
                    }
                    r7.g.b = r7.z[r7.C];
                    r7.g.d();
                }
                if (r7.H == 1) {
                    if (!r7.t) {
                        r7.J = true;
                        r7.o.queueInputBuffer(r7.C, 0, 0, 0, 4);
                        r7.C = -1;
                    }
                    r7.H = 2;
                    return false;
                } else if (r7.x) {
                    r7.x = false;
                    r7.g.b.put(c);
                    r7.o.queueInputBuffer(r7.C, 0, c.length, 0, 0);
                    r7.C = -1;
                    r7.I = true;
                    return true;
                } else {
                    int i;
                    if (r7.N) {
                        i = -3;
                    } else {
                        if (r7.G == 1) {
                            for (i = 0; i < r7.m.f.size(); i++) {
                                r7.g.b.put((byte[]) r7.m.f.get(i));
                            }
                            r7.G = 2;
                        }
                        i = a(j, r7.h, r7.g);
                        if (z && r7.K == 1 && i == -2) {
                            r7.K = 2;
                        }
                    }
                    if (i == -2) {
                        return false;
                    }
                    if (i == -4) {
                        if (r7.G == 2) {
                            r7.g.d();
                            r7.G = 1;
                        }
                        a(r7.h);
                        return true;
                    } else if (i == -1) {
                        if (r7.G == 2) {
                            r7.g.d();
                            r7.G = 1;
                        }
                        r7.L = true;
                        if (r7.I) {
                            try {
                                if (!r7.t) {
                                    r7.J = true;
                                    r7.o.queueInputBuffer(r7.C, 0, 0, 0, 4);
                                    r7.C = -1;
                                }
                                return false;
                            } catch (Throwable e) {
                                a((CryptoException) e);
                                throw new az(e);
                            }
                        }
                        A();
                        return false;
                    } else {
                        if (r7.O) {
                            if (r7.g.c()) {
                                r7.O = false;
                            } else {
                                r7.g.d();
                                if (r7.G == 2) {
                                    r7.G = 1;
                                }
                                return true;
                            }
                        }
                        boolean a = r7.g.a();
                        r7.N = a(a);
                        if (r7.N) {
                            return false;
                        }
                        if (r7.q && !a) {
                            fn.a(r7.g.b);
                            if (r7.g.b.position() == 0) {
                                return true;
                            }
                            r7.q = false;
                        }
                        try {
                            int position = r7.g.b.position();
                            int i2 = position - r7.g.c;
                            long j2 = r7.g.e;
                            if (r7.g.b()) {
                                r7.i.add(Long.valueOf(j2));
                            }
                            a(j2, r7.g.b, position, a);
                            if (a) {
                                r7.o.queueSecureInputBuffer(r7.C, 0, a(r7.g, i2), j2, 0);
                            } else {
                                r7.o.queueInputBuffer(r7.C, 0, position, j2, 0);
                            }
                            r7.C = -1;
                            r7.I = true;
                            r7.G = 0;
                            av avVar = r7.a;
                            avVar.c++;
                            return true;
                        } catch (Throwable e2) {
                            a((CryptoException) e2);
                            throw new az(e2);
                        }
                    }
                }
            }
        }
        return false;
    }

    private static CryptoInfo a(bm bmVar, int i) {
        bmVar = bmVar.a.a();
        if (i == 0) {
            return bmVar;
        }
        if (bmVar.numBytesOfClearData == null) {
            bmVar.numBytesOfClearData = new int[1];
        }
        int[] iArr = bmVar.numBytesOfClearData;
        iArr[0] = iArr[0] + i;
        return bmVar;
    }

    private MediaFormat b(bj bjVar) {
        bjVar = bjVar.b();
        if (this.l) {
            bjVar.setInteger("auto-frc", 0);
        }
        return bjVar;
    }

    private boolean a(boolean z) throws az {
        if (!this.E) {
            return false;
        }
        int b = this.e.b();
        if (b == 0) {
            throw new az(this.e.d());
        } else if (b == 4 || (!z && this.f)) {
            return false;
        } else {
            return true;
        }
    }

    protected void a(bk bkVar) throws az {
        bj bjVar = this.m;
        this.m = bkVar.a;
        this.n = bkVar.b;
        boolean z = false;
        bkVar = (this.n == null || this.E != null) ? null : true;
        if (!ft.a(this.m, (Object) bjVar) || bkVar != null) {
            if (this.o != null && bkVar == null && a(this.o, this.p, bjVar, this.m) != null) {
                this.F = true;
                this.G = 1;
                if (this.s != null && this.m.h == bjVar.h && this.m.i == bjVar.i) {
                    z = true;
                }
                this.x = z;
            } else if (this.I != null) {
                this.H = 1;
            } else {
                m();
                j();
            }
        }
    }

    protected boolean e() {
        return this.M;
    }

    protected boolean f() {
        return (this.m == null || this.N || (this.K == 0 && this.D < 0 && !a())) ? false : true;
    }

    protected final int o() {
        return this.K;
    }

    private boolean a() {
        return SystemClock.elapsedRealtime() < this.B + 1000;
    }

    private void i() throws az {
        MediaFormat outputFormat = this.o.getOutputFormat();
        if (this.s && outputFormat.getInteger("width") == 32 && outputFormat.getInteger("height") == 32) {
            this.y = true;
            return;
        }
        if (this.w) {
            outputFormat.setInteger("channel-count", 1);
        }
        a(this.o, outputFormat);
        av avVar = this.a;
        avVar.d++;
    }

    private void A() throws az {
        if (this.H == 2) {
            m();
            j();
            return;
        }
        this.M = true;
        h();
    }

    private void b(final a aVar) {
        if (this.b != null && this.k != null) {
            this.b.post(new Runnable(this) {
                final /* synthetic */ bg b;

                public void run() {
                    this.b.k.a(aVar);
                }
            });
        }
    }

    private void a(final CryptoException cryptoException) {
        if (this.b != null && this.k != null) {
            this.b.post(new Runnable(this) {
                final /* synthetic */ bg b;

                public void run() {
                    this.b.k.a(cryptoException);
                }
            });
        }
    }

    private void a(String str, long j, long j2) {
        if (this.b != null && this.k != null) {
            final String str2 = str;
            final long j3 = j;
            final long j4 = j2;
            this.b.post(new Runnable(this) {
                final /* synthetic */ bg d;

                public void run() {
                    this.d.k.a(str2, j3, j4);
                }
            });
        }
    }

    private int h(long j) {
        int size = this.i.size();
        for (int i = 0; i < size; i++) {
            if (((Long) this.i.get(i)).longValue() == j) {
                return i;
            }
        }
        return -1;
    }

    private static boolean a(String str) {
        if (ft.a >= 18 && !(ft.a == 18 && ("OMX.SEC.avc.dec".equals(str) || "OMX.SEC.avc.dec.secure".equals(str)))) {
            if (ft.a == 19 && ft.d.startsWith("SM-G800")) {
                if (!"OMX.Exynos.avc.dec".equals(str)) {
                    if ("OMX.Exynos.avc.dec.secure".equals(str) != null) {
                    }
                }
            }
            return null;
        }
        return true;
    }

    private static boolean b(String str) {
        return (ft.a >= 24 || ((!"OMX.Nvidia.h264.decode".equals(str) && "OMX.Nvidia.h264.decode.secure".equals(str) == null) || (ft.b.equals("flounder") == null && ft.b.equals("flounder_lte") == null && ft.b.equals("grouper") == null && ft.b.equals("tilapia") == null))) ? null : true;
    }

    private static boolean a(String str, bj bjVar) {
        return (ft.a >= 21 || bjVar.f.isEmpty() == null || "OMX.MTK.VIDEO.DECODER.AVC".equals(str) == null) ? null : true;
    }

    private static boolean c(String str) {
        return (ft.a > 17 || (!"OMX.rk.video_decoder.avc".equals(str) && "OMX.allwinner.video.decoder.avc".equals(str) == null)) ? null : true;
    }

    private static boolean d(String str) {
        return ((ft.a > 23 || !"OMX.google.vorbis.decoder".equals(str)) && (ft.a > 19 || !"hb2000".equals(ft.b) || (!"OMX.amlogic.avc.decoder.awesome".equals(str) && "OMX.amlogic.avc.decoder.awesome.secure".equals(str) == null))) ? null : true;
    }

    private static boolean e(String str) {
        return (ft.a != 21 || "OMX.google.aac.decoder".equals(str) == null) ? null : true;
    }

    private static boolean b(String str, bj bjVar) {
        if (ft.a > 18 || bjVar.q != 1 || "OMX.MTK.AUDIO.DECODER.MP3".equals(str) == null) {
            return false;
        }
        return true;
    }

    private static boolean f(String str) {
        return (ft.a > 19 || !ft.d.equals("ODROID-XU3") || (!"OMX.Exynos.AVC.Decoder".equals(str) && "OMX.Exynos.AVC.Decoder.secure".equals(str) == null)) ? null : true;
    }

    private static boolean B() {
        return ft.a <= 22 && "foster".equals(ft.b) && "NVIDIA".equals(ft.c);
    }
}
