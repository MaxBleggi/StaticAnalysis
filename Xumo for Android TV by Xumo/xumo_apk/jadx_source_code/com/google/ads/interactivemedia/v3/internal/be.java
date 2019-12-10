package com.google.ads.interactivemedia.v3.internal;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.PlaybackParams;
import android.os.Handler;
import android.os.SystemClock;
import com.google.ads.interactivemedia.v3.internal.bg.b;
import com.google.ads.interactivemedia.v3.internal.bt.d;
import com.google.ads.interactivemedia.v3.internal.bt.f;
import com.google.android.exoplayer2.util.MimeTypes;
import java.nio.ByteBuffer;

@TargetApi(16)
/* compiled from: IMASDK */
public class be extends bg implements bd {
    private final a c;
    private final bt d;
    private boolean e;
    private MediaFormat f;
    private int g;
    private int h;
    private long i;
    private boolean j;
    private boolean k;
    private long l;

    /* compiled from: IMASDK */
    public interface a extends b {
        void a(int i, long j, long j2);

        void a(d dVar);

        void a(f fVar);
    }

    public be(bn bnVar, bf bfVar, bv bvVar, boolean z, Handler handler, a aVar, bs bsVar, int i) {
        this(new bn[]{bnVar}, bfVar, bvVar, z, handler, aVar, bsVar, i);
    }

    protected void a(int i) {
    }

    protected bd b() {
        return this;
    }

    protected void i() {
    }

    public be(bn[] bnVarArr, bf bfVar, bv bvVar, boolean z, Handler handler, a aVar, bs bsVar, int i) {
        super(bnVarArr, bfVar, bvVar, z, handler, (b) aVar);
        this.c = aVar;
        this.h = null;
        this.d = new bt(bsVar, i);
    }

    protected boolean a(bf bfVar, bj bjVar) throws bh.b {
        String str = bjVar.b;
        if (!fl.a(str)) {
            return false;
        }
        if (MimeTypes.AUDIO_UNKNOWN.equals(str) || ((a(str) && bfVar.a() != null) || bfVar.a(str, false) != null)) {
            return true;
        }
        return false;
    }

    protected ay a(bf bfVar, String str, boolean z) throws bh.b {
        if (a(str)) {
            ay a = bfVar.a();
            if (a != null) {
                this.e = true;
                return a;
            }
        }
        this.e = false;
        return super.a(bfVar, str, z);
    }

    protected boolean a(String str) {
        return this.d.a(str);
    }

    protected void a(MediaCodec mediaCodec, boolean z, MediaFormat mediaFormat, MediaCrypto mediaCrypto) {
        z = mediaFormat.getString("mime");
        if (this.e) {
            mediaFormat.setString("mime", MimeTypes.AUDIO_RAW);
            mediaCodec.configure(mediaFormat, null, mediaCrypto, 0);
            mediaFormat.setString("mime", z);
            this.f = mediaFormat;
            return;
        }
        mediaCodec.configure(mediaFormat, null, mediaCrypto, 0);
        this.f = null;
    }

    protected void a(bk bkVar) throws az {
        super.a(bkVar);
        this.g = MimeTypes.AUDIO_RAW.equals(bkVar.a.b) ? bkVar.a.s : 2;
    }

    protected void a(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        mediaCodec = this.f != null ? true : null;
        String string = mediaCodec != null ? this.f.getString("mime") : MimeTypes.AUDIO_RAW;
        if (mediaCodec != null) {
            mediaFormat = this.f;
        }
        this.d.a(string, mediaFormat.getInteger("channel-count"), mediaFormat.getInteger("sample-rate"), this.g);
    }

    protected void c() {
        super.c();
        this.d.e();
    }

    protected void d() {
        this.d.i();
        super.d();
    }

    protected boolean e() {
        return super.e() && !this.d.h();
    }

    protected boolean f() {
        if (!this.d.h()) {
            if (!super.f()) {
                return false;
            }
        }
        return true;
    }

    public long a() {
        long a = this.d.a(e());
        if (a != Long.MIN_VALUE) {
            if (!this.j) {
                a = Math.max(this.i, a);
            }
            this.i = a;
            this.j = false;
        }
        return this.i;
    }

    protected void g() throws az {
        this.h = 0;
        try {
            this.d.k();
        } finally {
            super.g();
        }
    }

    protected void a(long j) throws az {
        super.a(j);
        this.d.j();
        this.i = j;
        this.j = 1;
    }

    protected boolean a(long j, long j2, MediaCodec mediaCodec, ByteBuffer byteBuffer, BufferInfo bufferInfo, int i, boolean z) throws az {
        MediaCodec mediaCodec2 = mediaCodec;
        BufferInfo bufferInfo2 = bufferInfo;
        int i2 = i;
        if (this.e && (bufferInfo2.flags & 2) != 0) {
            mediaCodec2.releaseOutputBuffer(i2, false);
            return true;
        } else if (z) {
            mediaCodec2.releaseOutputBuffer(i2, false);
            r0 = r7.a;
            r0.g++;
            r7.d.f();
            return true;
        } else {
            if (r7.d.a()) {
                boolean z2 = r7.k;
                r7.k = r7.d.h();
                if (z2 && !r7.k && v() == 3) {
                    long elapsedRealtime = SystemClock.elapsedRealtime() - r7.l;
                    long d = r7.d.d();
                    long j3 = -1;
                    if (d != -1) {
                        j3 = d / 1000;
                    }
                    a(r7.d.c(), j3, elapsedRealtime);
                }
            } else {
                try {
                    if (r7.h != 0) {
                        r7.d.a(r7.h);
                    } else {
                        r7.h = r7.d.b();
                        a(r7.h);
                    }
                    r7.k = false;
                    if (v() == 3) {
                        r7.d.e();
                    }
                } catch (Throwable e) {
                    a((d) e);
                    throw new az(e);
                }
            }
            try {
                int a = r7.d.a(byteBuffer, bufferInfo2.offset, bufferInfo2.size, bufferInfo2.presentationTimeUs);
                r7.l = SystemClock.elapsedRealtime();
                if ((a & 1) != 0) {
                    i();
                    r7.j = true;
                }
                if ((a & 2) == 0) {
                    return false;
                }
                mediaCodec2.releaseOutputBuffer(i2, false);
                r0 = r7.a;
                r0.f++;
                return true;
            } catch (Throwable e2) {
                a((f) e2);
                throw new az(e2);
            }
        }
    }

    protected void h() {
        this.d.g();
    }

    public void a(int i, Object obj) throws az {
        switch (i) {
            case 1:
                this.d.a(((Float) obj).floatValue());
                return;
            case 2:
                this.d.a((PlaybackParams) obj);
                return;
            case 3:
                if (this.d.b(((Integer) obj).intValue()) != 0) {
                    this.h = 0;
                    return;
                }
                return;
            default:
                super.a(i, obj);
                return;
        }
    }

    private void a(final d dVar) {
        if (this.b != null && this.c != null) {
            this.b.post(new Runnable(this) {
                final /* synthetic */ be b;

                public void run() {
                    this.b.c.a(dVar);
                }
            });
        }
    }

    private void a(final f fVar) {
        if (this.b != null && this.c != null) {
            this.b.post(new Runnable(this) {
                final /* synthetic */ be b;

                public void run() {
                    this.b.c.a(fVar);
                }
            });
        }
    }

    private void a(int i, long j, long j2) {
        if (this.b != null && this.c != null) {
            final int i2 = i;
            final long j3 = j;
            final long j4 = j2;
            this.b.post(new Runnable(this) {
                final /* synthetic */ be d;

                public void run() {
                    this.d.c.a(i2, j3, j4);
                }
            });
        }
    }
}
