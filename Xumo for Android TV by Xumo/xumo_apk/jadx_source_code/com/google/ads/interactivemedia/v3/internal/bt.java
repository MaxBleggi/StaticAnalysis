package com.google.ads.interactivemedia.v3.internal;

import android.annotation.TargetApi;
import android.media.AudioTimestamp;
import android.media.AudioTrack;
import android.media.PlaybackParams;
import android.os.ConditionVariable;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.util.MimeTypes;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/* compiled from: IMASDK */
public final class bt {
    public static boolean a = false;
    public static boolean b = false;
    private int A;
    private int B;
    private long C;
    private long D;
    private long E;
    private float F;
    private byte[] G;
    private int H;
    private int I;
    private ByteBuffer J;
    private boolean K;
    private final bs c;
    private final ConditionVariable d;
    private final long[] e;
    private final a f;
    private AudioTrack g;
    private AudioTrack h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private boolean n;
    private int o;
    private int p;
    private long q;
    private int r;
    private int s;
    private long t;
    private long u;
    private boolean v;
    private long w;
    private Method x;
    private long y;
    private long z;

    /* compiled from: IMASDK */
    private static class a {
        protected AudioTrack a;
        private boolean b;
        private int c;
        private long d;
        private long e;
        private long f;
        private long g;
        private long h;
        private long i;
        private long j;

        private a() {
        }

        public boolean d() {
            return false;
        }

        public float g() {
            return 1.0f;
        }

        public void a(AudioTrack audioTrack, boolean z) {
            this.a = audioTrack;
            this.b = z;
            this.g = -1;
            this.h = -1;
            this.d = 0;
            this.e = 0;
            this.f = 0;
            if (audioTrack != null) {
                this.c = audioTrack.getSampleRate();
            }
        }

        public void a(long j) {
            this.i = b();
            this.g = SystemClock.elapsedRealtime() * 1000;
            this.j = j;
            this.a.stop();
        }

        public void a() {
            if (this.g == -1) {
                this.a.pause();
            }
        }

        public boolean b(long j) {
            return (this.h == -1 || j <= 0 || SystemClock.elapsedRealtime() - this.h < 200) ? 0 : 1;
        }

        public long b() {
            if (this.g != -1) {
                return Math.min(this.j, this.i + ((((SystemClock.elapsedRealtime() * 1000) - this.g) * ((long) this.c)) / 1000000));
            }
            int playState = this.a.getPlayState();
            if (playState == 1) {
                return 0;
            }
            long playbackHeadPosition = 4294967295L & ((long) this.a.getPlaybackHeadPosition());
            if (this.b) {
                if (playState == 2 && playbackHeadPosition == 0) {
                    this.f = this.d;
                }
                playbackHeadPosition += this.f;
            }
            if (ft.a <= 26) {
                if (playbackHeadPosition == 0 && this.d > 0 && playState == 3) {
                    if (this.h == -1) {
                        this.h = SystemClock.elapsedRealtime();
                    }
                    return this.d;
                }
                this.h = -1;
            }
            if (this.d > playbackHeadPosition) {
                this.e++;
            }
            this.d = playbackHeadPosition;
            return playbackHeadPosition + (this.e << 32);
        }

        public long c() {
            return (b() * 1000000) / ((long) this.c);
        }

        public long e() {
            throw new UnsupportedOperationException();
        }

        public long f() {
            throw new UnsupportedOperationException();
        }

        public void a(PlaybackParams playbackParams) {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: IMASDK */
    public static final class d extends Exception {
        public final int a;

        public d(int i, int i2, int i3, int i4) {
            StringBuilder stringBuilder = new StringBuilder(82);
            stringBuilder.append("AudioTrack init failed: ");
            stringBuilder.append(i);
            stringBuilder.append(", Config(");
            stringBuilder.append(i2);
            stringBuilder.append(", ");
            stringBuilder.append(i3);
            stringBuilder.append(", ");
            stringBuilder.append(i4);
            stringBuilder.append(")");
            super(stringBuilder.toString());
            this.a = i;
        }
    }

    /* compiled from: IMASDK */
    public static final class e extends RuntimeException {
        public e(String str) {
            super(str);
        }
    }

    /* compiled from: IMASDK */
    public static final class f extends Exception {
        public final int a;

        public f(int i) {
            StringBuilder stringBuilder = new StringBuilder(36);
            stringBuilder.append("AudioTrack write failed: ");
            stringBuilder.append(i);
            super(stringBuilder.toString());
            this.a = i;
        }
    }

    @TargetApi(19)
    /* compiled from: IMASDK */
    private static class b extends a {
        private final AudioTimestamp b = new AudioTimestamp();
        private long c;
        private long d;
        private long e;

        public b() {
            super();
        }

        public void a(AudioTrack audioTrack, boolean z) {
            super.a(audioTrack, z);
            this.c = 0;
            this.d = 0;
            this.e = 0;
        }

        public boolean d() {
            boolean timestamp = this.a.getTimestamp(this.b);
            if (timestamp) {
                long j = this.b.framePosition;
                if (this.d > j) {
                    this.c++;
                }
                this.d = j;
                this.e = j + (this.c << 32);
            }
            return timestamp;
        }

        public long e() {
            return this.b.nanoTime;
        }

        public long f() {
            return this.e;
        }
    }

    @TargetApi(23)
    /* compiled from: IMASDK */
    private static class c extends b {
        private PlaybackParams b;
        private float c = 1.0f;

        public void a(AudioTrack audioTrack, boolean z) {
            super.a(audioTrack, z);
            h();
        }

        public void a(PlaybackParams playbackParams) {
            if (playbackParams == null) {
                playbackParams = new PlaybackParams();
            }
            playbackParams = playbackParams.allowDefaults();
            this.b = playbackParams;
            this.c = playbackParams.getSpeed();
            h();
        }

        public float g() {
            return this.c;
        }

        private void h() {
            if (this.a != null && this.b != null) {
                this.a.setPlaybackParams(this.b);
            }
        }
    }

    public bt() {
        this(null, 3);
    }

    public bt(com.google.ads.interactivemedia.v3.internal.bs r1, int r2) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.ads.interactivemedia.v3.internal.bt.<init>(com.google.ads.interactivemedia.v3.internal.bs, int):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 5 more
*/
        /*
        r0 = this;
        r3.<init>();
        r3.c = r4;
        r4 = new android.os.ConditionVariable;
        r0 = 1;
        r4.<init>(r0);
        r3.d = r4;
        r4 = com.google.ads.interactivemedia.v3.internal.ft.a;
        r0 = 0;
        r1 = 18;
        if (r4 < r1) goto L_0x0023;
        r4 = android.media.AudioTrack.class;
        r1 = "getLatency";
        r2 = r0;
        r2 = (java.lang.Class[]) r2;
        r4 = r4.getMethod(r1, r2);
        r3.x = r4;
        goto L_0x0023;
        r4 = com.google.ads.interactivemedia.v3.internal.ft.a;
        r1 = 23;
        if (r4 < r1) goto L_0x0031;
        r4 = new com.google.ads.interactivemedia.v3.internal.bt$c;
        r4.<init>();
        r3.f = r4;
        goto L_0x0046;
        r4 = com.google.ads.interactivemedia.v3.internal.ft.a;
        r1 = 19;
        if (r4 < r1) goto L_0x003f;
        r4 = new com.google.ads.interactivemedia.v3.internal.bt$b;
        r4.<init>();
        r3.f = r4;
        goto L_0x0046;
        r4 = new com.google.ads.interactivemedia.v3.internal.bt$a;
        r4.<init>();
        r3.f = r4;
        r4 = 10;
        r4 = new long[r4];
        r3.e = r4;
        r3.k = r5;
        r4 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r3.F = r4;
        r4 = 0;
        r3.B = r4;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.bt.<init>(com.google.ads.interactivemedia.v3.internal.bs, int):void");
    }

    public boolean a(String str) {
        return (this.c == null || this.c.a(b(str)) == null) ? null : true;
    }

    public boolean a() {
        return this.h != null;
    }

    public long a(boolean z) {
        if (!n()) {
            return Long.MIN_VALUE;
        }
        if (this.h.getPlayState() == 3) {
            o();
        }
        long nanoTime = System.nanoTime() / 1000;
        if (this.v) {
            nanoTime = b(this.f.f() + c((long) (((float) (nanoTime - (this.f.e() / 1000))) * this.f.g()))) + this.C;
        } else {
            if (this.s == 0) {
                nanoTime = this.f.c() + this.C;
            } else {
                nanoTime = (nanoTime + this.t) + this.C;
            }
            if (!z) {
                nanoTime -= this.E;
            }
        }
        return nanoTime;
    }

    public void a(String str, int i, int i2, int i3) {
        a(str, i, i2, i3, 0);
    }

    public void a(String str, int i, int i2, int i3, int i4) {
        int i5;
        boolean z;
        boolean equals;
        int i6 = 252;
        switch (i) {
            case 1:
                i5 = 4;
                break;
            case 2:
                i5 = 12;
                break;
            case 3:
                i5 = 28;
                break;
            case 4:
                i5 = 204;
                break;
            case 5:
                i5 = 220;
                break;
            case 6:
                i5 = 252;
                break;
            case 7:
                i5 = 1276;
                break;
            case 8:
                i5 = au.a;
                break;
            default:
                i3 = new StringBuilder(38);
                i3.append("Unsupported channel count: ");
                i3.append(i);
                throw new IllegalArgumentException(i3.toString());
        }
        if (ft.a <= 23 && "foster".equals(ft.b) && "NVIDIA".equals(ft.c)) {
            if (!(i == 3 || i == 5)) {
                if (i == 7) {
                    i6 = au.a;
                }
            }
            z = true;
            equals = MimeTypes.AUDIO_RAW.equals(str) ^ true;
            if (ft.a <= 25 && "fugu".equals(ft.b) && equals && i == 1) {
                i6 = 12;
            }
            if (equals) {
                i3 = b(str);
            } else if (!(i3 == 3 || i3 == 2 || i3 == Integer.MIN_VALUE)) {
                if (i3 == 1073741824) {
                    i2 = new StringBuilder(37);
                    i2.append("Unsupported PCM encoding: ");
                    i2.append(i3);
                    throw new IllegalArgumentException(i2.toString());
                }
            }
            if (a() != null || this.l != i3 || this.i != i2 || this.j != i6) {
                j();
                this.l = i3;
                this.n = equals;
                this.i = i2;
                this.j = i6;
                if (equals) {
                    i3 = 2;
                }
                this.m = i3;
                this.o = i * 2;
                if (i4 != 0) {
                    this.p = i4;
                } else if (equals) {
                    str = AudioTrack.getMinBufferSize(i2, i6, this.m);
                    if (str != -2) {
                        z = false;
                    }
                    fe.b(z);
                    i = str * 4;
                    i2 = ((int) c(250000)) * this.o;
                    str = (int) Math.max((long) str, c(750000) * ((long) this.o));
                    if (i < i2) {
                        str = i2;
                    } else if (i > str) {
                        str = i;
                    }
                    this.p = str;
                } else {
                    if (this.m != 5) {
                        if (this.m == 6) {
                            this.p = 49152;
                        }
                    }
                    this.p = CacheDataSink.DEFAULT_BUFFER_SIZE;
                }
                if (equals) {
                    str = b(a((long) this.p));
                } else {
                    str = -1;
                }
                this.q = str;
            }
            return;
        }
        i6 = i5;
        z = true;
        equals = MimeTypes.AUDIO_RAW.equals(str) ^ true;
        i6 = 12;
        if (equals) {
            i3 = b(str);
        } else if (i3 == 1073741824) {
            i2 = new StringBuilder(37);
            i2.append("Unsupported PCM encoding: ");
            i2.append(i3);
            throw new IllegalArgumentException(i2.toString());
        }
        if (a() != null) {
        }
        j();
        this.l = i3;
        this.n = equals;
        this.i = i2;
        this.j = i6;
        if (equals) {
            i3 = 2;
        }
        this.m = i3;
        this.o = i * 2;
        if (i4 != 0) {
            this.p = i4;
        } else if (equals) {
            str = AudioTrack.getMinBufferSize(i2, i6, this.m);
            if (str != -2) {
                z = false;
            }
            fe.b(z);
            i = str * 4;
            i2 = ((int) c(250000)) * this.o;
            str = (int) Math.max((long) str, c(750000) * ((long) this.o));
            if (i < i2) {
                str = i2;
            } else if (i > str) {
                str = i;
            }
            this.p = str;
        } else {
            if (this.m != 5) {
                if (this.m == 6) {
                    this.p = 49152;
                }
            }
            this.p = CacheDataSink.DEFAULT_BUFFER_SIZE;
        }
        if (equals) {
            str = b(a((long) this.p));
        } else {
            str = -1;
        }
        this.q = str;
    }

    public int b() throws d {
        return a(0);
    }

    public int a(int i) throws d {
        this.d.block();
        if (i == 0) {
            this.h = new AudioTrack(this.k, this.i, this.j, this.m, this.p, 1);
        } else {
            this.h = new AudioTrack(this.k, this.i, this.j, this.m, this.p, 1, i);
        }
        p();
        int audioSessionId = this.h.getAudioSessionId();
        if (a && ft.a < 21) {
            if (!(this.g == null || audioSessionId == this.g.getAudioSessionId())) {
                m();
            }
            if (this.g == null) {
                this.g = new AudioTrack(this.k, 4000, 4, 2, 2, 0, audioSessionId);
            }
        }
        this.f.a(this.h, s());
        l();
        return audioSessionId;
    }

    public int c() {
        return this.p;
    }

    public long d() {
        return this.q;
    }

    public void e() {
        if (a()) {
            this.D = System.nanoTime() / 1000;
            this.h.play();
        }
    }

    public void f() {
        if (this.B == 1) {
            this.B = 2;
        }
    }

    public int a(ByteBuffer byteBuffer, int i, int i2, long j) throws f {
        ByteBuffer byteBuffer2;
        bt btVar = this;
        int i3 = i2;
        long j2 = j;
        int i4 = 1;
        int i5 = 0;
        if (s()) {
            if (btVar.h.getPlayState() == 2) {
                return 0;
            }
            if (btVar.h.getPlayState() == 1 && btVar.h.getPlaybackHeadPosition() != 0) {
                return 0;
            }
        }
        if (btVar.I != 0) {
            byteBuffer2 = byteBuffer;
            i4 = 0;
        } else if (i3 == 0) {
            return 2;
        } else {
            int position;
            btVar.K = btVar.m != btVar.l;
            if (btVar.K) {
                fe.b(btVar.m == 2);
                btVar.J = a(byteBuffer, i, i3, btVar.l, btVar.J);
                byteBuffer2 = btVar.J;
                position = btVar.J.position();
                i3 = btVar.J.limit();
            } else {
                byteBuffer2 = byteBuffer;
                position = i;
            }
            btVar.I = i3;
            byteBuffer2.position(position);
            if (btVar.n && btVar.A == 0) {
                btVar.A = a(btVar.m, byteBuffer2);
            }
            if (btVar.B == 0) {
                btVar.C = Math.max(0, j2);
                btVar.B = 1;
            } else {
                long b = btVar.C + b(q());
                if (btVar.B == 1 && Math.abs(b - j2) > 200000) {
                    StringBuilder stringBuilder = new StringBuilder(80);
                    stringBuilder.append("Discontinuity detected [expected ");
                    stringBuilder.append(b);
                    stringBuilder.append(", got ");
                    stringBuilder.append(j2);
                    stringBuilder.append("]");
                    Log.e("AudioTrack", stringBuilder.toString());
                    btVar.B = 2;
                }
                if (btVar.B == 2) {
                    btVar.C += j2 - b;
                    btVar.B = 1;
                    if (ft.a < 21) {
                        if (btVar.G == null || btVar.G.length < i3) {
                            btVar.G = new byte[i3];
                        }
                        byteBuffer2.get(btVar.G, 0, i3);
                        btVar.H = 0;
                    }
                }
            }
            i4 = 0;
            if (ft.a < 21) {
                btVar.G = new byte[i3];
                byteBuffer2.get(btVar.G, 0, i3);
                btVar.H = 0;
            }
        }
        if (ft.a < 21) {
            int b2 = btVar.p - ((int) (btVar.y - (btVar.f.b() * ((long) btVar.o))));
            if (b2 > 0) {
                i5 = btVar.h.write(btVar.G, btVar.H, Math.min(btVar.I, b2));
                if (i5 >= 0) {
                    btVar.H += i5;
                }
            }
        } else {
            if (btVar.K) {
                byteBuffer2 = btVar.J;
            }
            i5 = a(btVar.h, byteBuffer2, btVar.I);
        }
        if (i5 >= 0) {
            btVar.I -= i5;
            if (!btVar.n) {
                btVar.y += (long) i5;
            }
            if (btVar.I == 0) {
                if (btVar.n) {
                    btVar.z += (long) btVar.A;
                }
                i4 |= 2;
            }
            if (btVar.f.b(q())) {
                Log.w("AudioTrack", "Resetting stalled audio track");
                j();
                i4 |= 2;
            }
            return i4;
        }
        throw new f(i5);
    }

    public void g() {
        if (a()) {
            this.f.a(q());
        }
    }

    public boolean h() {
        return a() && (q() > this.f.b() || t());
    }

    public void a(PlaybackParams playbackParams) {
        this.f.a(playbackParams);
    }

    public boolean b(int i) {
        if (this.k == i) {
            return false;
        }
        this.k = i;
        j();
        return true;
    }

    public void a(float f) {
        if (this.F != f) {
            this.F = f;
            l();
        }
    }

    private void l() {
        if (!a()) {
            return;
        }
        if (ft.a >= 21) {
            a(this.h, this.F);
        } else {
            b(this.h, this.F);
        }
    }

    public void i() {
        if (a()) {
            r();
            this.f.a();
        }
    }

    public void j() {
        if (a()) {
            this.y = 0;
            this.z = 0;
            this.A = 0;
            this.I = 0;
            this.B = 0;
            this.E = 0;
            r();
            if (this.h.getPlayState() == 3) {
                this.h.pause();
            }
            final AudioTrack audioTrack = this.h;
            this.h = null;
            this.f.a(null, false);
            this.d.close();
            new Thread(this) {
                final /* synthetic */ bt b;

                public void run() {
                    try {
                        audioTrack.flush();
                        audioTrack.release();
                    } finally {
                        this.b.d.open();
                    }
                }
            }.start();
        }
    }

    public void k() {
        j();
        m();
    }

    private void m() {
        if (this.g != null) {
            final AudioTrack audioTrack = this.g;
            this.g = null;
            new Thread(this) {
                public void run() {
                    audioTrack.release();
                }
            }.start();
        }
    }

    private boolean n() {
        return a() && this.B != 0;
    }

    private void o() {
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
        r17 = this;
        r0 = r17;
        r1 = r0.f;
        r1 = r1.c();
        r3 = 0;
        r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
        if (r5 != 0) goto L_0x000f;
    L_0x000e:
        return;
    L_0x000f:
        r5 = java.lang.System.nanoTime();
        r7 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r5 = r5 / r7;
        r9 = r0.u;
        r9 = r5 - r9;
        r11 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r13 = 0;
        r14 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1));
        if (r14 < 0) goto L_0x0057;
    L_0x0021:
        r9 = r0.e;
        r10 = r0.r;
        r11 = r1 - r5;
        r9[r10] = r11;
        r9 = r0.r;
        r9 = r9 + 1;
        r10 = 10;
        r9 = r9 % r10;
        r0.r = r9;
        r9 = r0.s;
        if (r9 >= r10) goto L_0x003c;
    L_0x0036:
        r9 = r0.s;
        r9 = r9 + 1;
        r0.s = r9;
    L_0x003c:
        r0.u = r5;
        r0.t = r3;
        r9 = 0;
    L_0x0041:
        r10 = r0.s;
        if (r9 >= r10) goto L_0x0057;
    L_0x0045:
        r10 = r0.t;
        r12 = r0.e;
        r14 = r12[r9];
        r12 = r0.s;
        r3 = (long) r12;
        r14 = r14 / r3;
        r10 = r10 + r14;
        r0.t = r10;
        r9 = r9 + 1;
        r3 = 0;
        goto L_0x0041;
    L_0x0057:
        r3 = r17.s();
        if (r3 == 0) goto L_0x005e;
    L_0x005d:
        return;
    L_0x005e:
        r3 = r0.w;
        r3 = r5 - r3;
        r9 = 500000; // 0x7a120 float:7.00649E-40 double:2.47033E-318;
        r11 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1));
        if (r11 < 0) goto L_0x0177;
    L_0x0069:
        r3 = r0.f;
        r3 = r3.d();
        r0.v = r3;
        r3 = r0.v;
        r9 = 5000000; // 0x4c4b40 float:7.006492E-39 double:2.470328E-317;
        if (r3 == 0) goto L_0x0121;
    L_0x0078:
        r3 = r0.f;
        r3 = r3.e();
        r3 = r3 / r7;
        r11 = r0.f;
        r11 = r11.f();
        r14 = r0.D;
        r16 = (r3 > r14 ? 1 : (r3 == r14 ? 0 : -1));
        if (r16 >= 0) goto L_0x008f;
    L_0x008b:
        r0.v = r13;
        goto L_0x0121;
    L_0x008f:
        r14 = 0;
        r14 = r3 - r5;
        r14 = java.lang.Math.abs(r14);
        r16 = (r14 > r9 ? 1 : (r14 == r9 ? 0 : -1));
        if (r16 <= 0) goto L_0x00d7;
    L_0x009a:
        r14 = 136; // 0x88 float:1.9E-43 double:6.7E-322;
        r15 = new java.lang.StringBuilder;
        r15.<init>(r14);
        r14 = "Spurious audio timestamp (system clock mismatch): ";
        r15.append(r14);
        r15.append(r11);
        r11 = ", ";
        r15.append(r11);
        r15.append(r3);
        r3 = ", ";
        r15.append(r3);
        r15.append(r5);
        r3 = ", ";
        r15.append(r3);
        r15.append(r1);
        r1 = r15.toString();
        r2 = b;
        if (r2 != 0) goto L_0x00d1;
    L_0x00c9:
        r2 = "AudioTrack";
        android.util.Log.w(r2, r1);
        r0.v = r13;
        goto L_0x0121;
    L_0x00d1:
        r2 = new com.google.ads.interactivemedia.v3.internal.bt$e;
        r2.<init>(r1);
        throw r2;
    L_0x00d7:
        r14 = r0.b(r11);
        r14 = r14 - r1;
        r14 = java.lang.Math.abs(r14);
        r16 = (r14 > r9 ? 1 : (r14 == r9 ? 0 : -1));
        if (r16 <= 0) goto L_0x0121;
    L_0x00e4:
        r14 = 138; // 0x8a float:1.93E-43 double:6.8E-322;
        r15 = new java.lang.StringBuilder;
        r15.<init>(r14);
        r14 = "Spurious audio timestamp (frame position mismatch): ";
        r15.append(r14);
        r15.append(r11);
        r11 = ", ";
        r15.append(r11);
        r15.append(r3);
        r3 = ", ";
        r15.append(r3);
        r15.append(r5);
        r3 = ", ";
        r15.append(r3);
        r15.append(r1);
        r1 = r15.toString();
        r2 = b;
        if (r2 != 0) goto L_0x011b;
    L_0x0113:
        r2 = "AudioTrack";
        android.util.Log.w(r2, r1);
        r0.v = r13;
        goto L_0x0121;
    L_0x011b:
        r2 = new com.google.ads.interactivemedia.v3.internal.bt$e;
        r2.<init>(r1);
        throw r2;
    L_0x0121:
        r1 = r0.x;
        if (r1 == 0) goto L_0x0175;
    L_0x0125:
        r1 = r0.n;
        if (r1 != 0) goto L_0x0175;
    L_0x0129:
        r1 = 0;
        r2 = r0.x;	 Catch:{ Exception -> 0x0173 }
        r3 = r0.h;	 Catch:{ Exception -> 0x0173 }
        r4 = r1;	 Catch:{ Exception -> 0x0173 }
        r4 = (java.lang.Object[]) r4;	 Catch:{ Exception -> 0x0173 }
        r2 = r2.invoke(r3, r4);	 Catch:{ Exception -> 0x0173 }
        r2 = (java.lang.Integer) r2;	 Catch:{ Exception -> 0x0173 }
        r2 = r2.intValue();	 Catch:{ Exception -> 0x0173 }
        r2 = (long) r2;	 Catch:{ Exception -> 0x0173 }
        r2 = r2 * r7;	 Catch:{ Exception -> 0x0173 }
        r7 = r0.q;	 Catch:{ Exception -> 0x0173 }
        r4 = 0;	 Catch:{ Exception -> 0x0173 }
        r2 = r2 - r7;	 Catch:{ Exception -> 0x0173 }
        r0.E = r2;	 Catch:{ Exception -> 0x0173 }
        r2 = r0.E;	 Catch:{ Exception -> 0x0173 }
        r7 = 0;	 Catch:{ Exception -> 0x0173 }
        r2 = java.lang.Math.max(r2, r7);	 Catch:{ Exception -> 0x0173 }
        r0.E = r2;	 Catch:{ Exception -> 0x0173 }
        r2 = r0.E;	 Catch:{ Exception -> 0x0173 }
        r4 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1));	 Catch:{ Exception -> 0x0173 }
        if (r4 <= 0) goto L_0x0175;	 Catch:{ Exception -> 0x0173 }
    L_0x0154:
        r2 = "AudioTrack";	 Catch:{ Exception -> 0x0173 }
        r3 = r0.E;	 Catch:{ Exception -> 0x0173 }
        r7 = 61;	 Catch:{ Exception -> 0x0173 }
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0173 }
        r8.<init>(r7);	 Catch:{ Exception -> 0x0173 }
        r7 = "Ignoring impossibly large audio latency: ";	 Catch:{ Exception -> 0x0173 }
        r8.append(r7);	 Catch:{ Exception -> 0x0173 }
        r8.append(r3);	 Catch:{ Exception -> 0x0173 }
        r3 = r8.toString();	 Catch:{ Exception -> 0x0173 }
        android.util.Log.w(r2, r3);	 Catch:{ Exception -> 0x0173 }
        r2 = 0;	 Catch:{ Exception -> 0x0173 }
        r0.E = r2;	 Catch:{ Exception -> 0x0173 }
        goto L_0x0175;
    L_0x0173:
        r0.x = r1;
    L_0x0175:
        r0.w = r5;
    L_0x0177:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.bt.o():void");
    }

    private void p() throws com.google.ads.interactivemedia.v3.internal.bt.d {
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
        r5 = this;
        r0 = r5.h;
        r0 = r0.getState();
        r1 = 1;
        if (r0 != r1) goto L_0x000a;
    L_0x0009:
        return;
    L_0x000a:
        r1 = 0;
        r2 = r5.h;	 Catch:{ Exception -> 0x0017, all -> 0x0013 }
        r2.release();	 Catch:{ Exception -> 0x0017, all -> 0x0013 }
        r5.h = r1;
        goto L_0x0019;
    L_0x0013:
        r0 = move-exception;
        r5.h = r1;
        throw r0;
    L_0x0017:
        r5.h = r1;
    L_0x0019:
        r1 = new com.google.ads.interactivemedia.v3.internal.bt$d;
        r2 = r5.i;
        r3 = r5.j;
        r4 = r5.p;
        r1.<init>(r0, r2, r3, r4);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.bt.p():void");
    }

    private long a(long j) {
        return j / ((long) this.o);
    }

    private long b(long j) {
        return (j * 1000000) / ((long) this.i);
    }

    private long c(long j) {
        return (j * ((long) this.i)) / 1000000;
    }

    private long q() {
        return this.n ? this.z : a(this.y);
    }

    private void r() {
        this.t = 0;
        this.s = 0;
        this.r = 0;
        this.u = 0;
        this.v = false;
        this.w = 0;
    }

    private boolean s() {
        return ft.a < 23 && (this.m == 5 || this.m == 6);
    }

    private boolean t() {
        return s() && this.h.getPlayState() == 2 && this.h.getPlaybackHeadPosition() == 0;
    }

    private static ByteBuffer a(ByteBuffer byteBuffer, int i, int i2, int i3, ByteBuffer byteBuffer2) {
        int i4;
        if (i3 == Integer.MIN_VALUE) {
            i4 = (i2 / 3) * 2;
        } else if (i3 == 3) {
            i4 = i2 * 2;
        } else if (i3 == 1073741824) {
            i4 = i2 / 2;
        } else {
            throw new IllegalStateException();
        }
        if (byteBuffer2 == null || byteBuffer2.capacity() < i4) {
            byteBuffer2 = ByteBuffer.allocateDirect(i4);
        }
        byteBuffer2.position(0);
        byteBuffer2.limit(i4);
        i2 += i;
        if (i3 == Integer.MIN_VALUE) {
            while (i < i2) {
                byteBuffer2.put(byteBuffer.get(i + 1));
                byteBuffer2.put(byteBuffer.get(i + 2));
                i += 3;
            }
        } else if (i3 == 3) {
            while (i < i2) {
                byteBuffer2.put((byte) 0);
                byteBuffer2.put((byte) ((byteBuffer.get(i) & 255) - 128));
                i++;
            }
        } else if (i3 == 1073741824) {
            while (i < i2) {
                byteBuffer2.put(byteBuffer.get(i + 2));
                byteBuffer2.put(byteBuffer.get(i + 3));
                i += 4;
            }
        } else {
            throw new IllegalStateException();
        }
        byteBuffer2.position(0);
        return byteBuffer2;
    }

    private static int b(String str) {
        int hashCode = str.hashCode();
        if (hashCode != -1095064472) {
            if (hashCode != 187078296) {
                if (hashCode != 1504578661) {
                    if (hashCode == 1505942594) {
                        if (str.equals(MimeTypes.AUDIO_DTS_HD) != null) {
                            str = 3;
                            switch (str) {
                                case null:
                                    return 5;
                                case 1:
                                    return 6;
                                case 2:
                                    return 7;
                                case 3:
                                    return 8;
                                default:
                                    return 0;
                            }
                        }
                    }
                } else if (str.equals(MimeTypes.AUDIO_E_AC3) != null) {
                    str = true;
                    switch (str) {
                        case null:
                            return 5;
                        case 1:
                            return 6;
                        case 2:
                            return 7;
                        case 3:
                            return 8;
                        default:
                            return 0;
                    }
                }
            } else if (str.equals(MimeTypes.AUDIO_AC3) != null) {
                str = null;
                switch (str) {
                    case null:
                        return 5;
                    case 1:
                        return 6;
                    case 2:
                        return 7;
                    case 3:
                        return 8;
                    default:
                        return 0;
                }
            }
        } else if (str.equals(MimeTypes.AUDIO_DTS) != null) {
            str = 2;
            switch (str) {
                case null:
                    return 5;
                case 1:
                    return 6;
                case 2:
                    return 7;
                case 3:
                    return 8;
                default:
                    return 0;
            }
        }
        str = -1;
        switch (str) {
            case null:
                return 5;
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 8;
            default:
                return 0;
        }
    }

    private static int a(int i, ByteBuffer byteBuffer) {
        if (i != 7) {
            if (i != 8) {
                if (i == 5) {
                    return fd.a();
                }
                if (i == 6) {
                    return fd.a(byteBuffer);
                }
                StringBuilder stringBuilder = new StringBuilder(38);
                stringBuilder.append("Unexpected audio encoding: ");
                stringBuilder.append(i);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        return fg.a(byteBuffer);
    }

    @TargetApi(21)
    private static int a(AudioTrack audioTrack, ByteBuffer byteBuffer, int i) {
        return audioTrack.write(byteBuffer, i, 1);
    }

    @TargetApi(21)
    private static void a(AudioTrack audioTrack, float f) {
        audioTrack.setVolume(f);
    }

    private static void b(AudioTrack audioTrack, float f) {
        audioTrack.setStereoVolume(f, f);
    }
}
