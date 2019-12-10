package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;

/* compiled from: IMASDK */
final class dy extends du {
    private final fp b = new fp(10);
    private boolean c;
    private long d;
    private int e;
    private int f;

    public dy(ck ckVar) {
        super(ckVar);
        ckVar.a(bj.a());
    }

    public void a() {
        this.c = false;
    }

    public void a(long j, boolean z) {
        if (z) {
            this.c = true;
            this.d = j;
            this.e = 0;
            this.f = 0;
        }
    }

    public void a(fp fpVar) {
        if (this.c) {
            int b = fpVar.b();
            if (this.f < 10) {
                int min = Math.min(b, 10 - this.f);
                System.arraycopy(fpVar.a, fpVar.d(), this.b.a, this.f, min);
                if (this.f + min == 10) {
                    this.b.c(0);
                    if (73 == this.b.f() && 68 == this.b.f()) {
                        if (51 == this.b.f()) {
                            this.b.d(3);
                            this.e = this.b.r() + 10;
                        }
                    }
                    Log.w("Id3Reader", "Discarding invalid ID3 tag");
                    this.c = false;
                    return;
                }
            }
            b = Math.min(b, this.e - this.f);
            this.a.a(fpVar, b);
            this.f += b;
        }
    }

    public void b() {
        if (this.c && this.e != 0) {
            if (this.f == this.e) {
                this.a.a(this.d, 1, this.e, 0, null);
                this.c = false;
            }
        }
    }
}
