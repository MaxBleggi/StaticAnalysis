package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;
import android.util.Pair;
import androidx.core.app.FrameMetricsAggregator;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;
import java.util.Arrays;
import java.util.Collections;

/* compiled from: IMASDK */
final class ds extends du {
    private static final byte[] b = new byte[]{(byte) 73, (byte) 68, (byte) 51};
    private final fo c = new fo(new byte[7]);
    private final fp d = new fp(Arrays.copyOf(b, 10));
    private final ck e;
    private int f;
    private int g;
    private int h;
    private boolean i;
    private boolean j;
    private long k;
    private int l;
    private long m;
    private ck n;
    private long o;

    public ds(ck ckVar, ck ckVar2) {
        super(ckVar);
        this.e = ckVar2;
        ckVar2.a(bj.a());
        c();
    }

    public void b() {
    }

    public void a() {
        c();
    }

    public void a(long j, boolean z) {
        this.m = j;
    }

    public void a(fp fpVar) {
        while (fpVar.b() > 0) {
            switch (this.f) {
                case 0:
                    b(fpVar);
                    break;
                case 1:
                    if (!a(fpVar, this.d.a, 10)) {
                        break;
                    }
                    f();
                    break;
                case 2:
                    if (!a(fpVar, this.c.a, this.i ? 7 : 5)) {
                        break;
                    }
                    g();
                    break;
                case 3:
                    c(fpVar);
                    break;
                default:
                    break;
            }
        }
    }

    private boolean a(fp fpVar, byte[] bArr, int i) {
        int min = Math.min(fpVar.b(), i - this.g);
        fpVar.a(bArr, this.g, min);
        this.g += min;
        return this.g == i ? true : null;
    }

    private void c() {
        this.f = 0;
        this.g = 0;
        this.h = 256;
    }

    private void d() {
        this.f = 1;
        this.g = b.length;
        this.l = 0;
        this.d.c(0);
    }

    private void a(ck ckVar, long j, int i, int i2) {
        this.f = 3;
        this.g = i;
        this.n = ckVar;
        this.o = j;
        this.l = i2;
    }

    private void e() {
        this.f = 2;
        this.g = 0;
    }

    private void b(fp fpVar) {
        byte[] bArr = fpVar.a;
        int d = fpVar.d();
        int c = fpVar.c();
        while (d < c) {
            int i = d + 1;
            d = bArr[d] & 255;
            if (this.h != 512 || d < PsExtractor.VIDEO_STREAM_MASK || d == 255) {
                d |= this.h;
                if (d == 329) {
                    this.h = 768;
                } else if (d == FrameMetricsAggregator.EVERY_DURATION) {
                    this.h = 512;
                } else if (d == 836) {
                    this.h = 1024;
                } else if (d == 1075) {
                    d();
                    fpVar.c(i);
                    return;
                } else if (this.h != 256) {
                    this.h = 256;
                    i--;
                }
                d = i;
            } else {
                boolean z = true;
                if ((d & 1) != 0) {
                    z = false;
                }
                this.i = z;
                e();
                fpVar.c(i);
                return;
            }
        }
        fpVar.c(d);
    }

    private void f() {
        this.e.a(this.d, 10);
        this.d.c(6);
        a(this.e, 0, 10, this.d.r() + 10);
    }

    private void g() {
        int c;
        this.c.a(0);
        if (this.j) {
            this.c.b(10);
        } else {
            c = this.c.c(2) + 1;
            if (c != 2) {
                StringBuilder stringBuilder = new StringBuilder(61);
                stringBuilder.append("Detected audio object type: ");
                stringBuilder.append(c);
                stringBuilder.append(", but assuming AAC LC.");
                Log.w("AdtsReader", stringBuilder.toString());
                c = 2;
            }
            int c2 = this.c.c(4);
            this.c.b(1);
            Object a = ff.a(c, c2, this.c.c(3));
            Pair a2 = ff.a(a);
            bj a3 = bj.a(null, MimeTypes.AUDIO_AAC, -1, -1, -1, ((Integer) a2.second).intValue(), ((Integer) a2.first).intValue(), Collections.singletonList(a), null);
            this.k = 1024000000 / ((long) a3.r);
            this.a.a(a3);
            this.j = true;
        }
        this.c.b(4);
        c = (this.c.c(13) - 2) - 5;
        if (this.i) {
            c -= 2;
        }
        a(this.a, this.k, 0, c);
    }

    private void c(fp fpVar) {
        int min = Math.min(fpVar.b(), this.l - this.g);
        this.n.a(fpVar, min);
        this.g += min;
        if (this.g == this.l) {
            this.n.a(this.m, 1, this.l, 0, null);
            this.m += this.o;
            c();
        }
    }
}
