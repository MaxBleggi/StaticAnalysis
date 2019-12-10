package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.dl.a;
import com.google.ads.interactivemedia.v3.internal.dl.b;
import java.io.IOException;

/* compiled from: IMASDK */
final class dj {
    private final b a = new b();
    private final fp b = new fp(282);
    private final a c = new a();
    private int d = -1;
    private long e;

    dj() {
    }

    public void a() {
        this.a.a();
        this.b.a();
        this.d = -1;
    }

    public boolean a(cd cdVar, fp fpVar) throws IOException, InterruptedException {
        boolean z = (cdVar == null || fpVar == null) ? false : true;
        fe.b(z);
        Object obj = null;
        while (obj == null) {
            int i;
            if (this.d < 0) {
                if (!dl.a(cdVar, this.a, this.b, true)) {
                    return false;
                }
                int i2;
                i = this.a.h;
                if ((this.a.b & 1) == 1 && fpVar.c() == 0) {
                    dl.a(this.a, 0, this.c);
                    i2 = this.c.b + 0;
                    i += this.c.a;
                } else {
                    i2 = 0;
                }
                cdVar.b(i);
                this.d = i2;
            }
            dl.a(this.a, this.d, this.c);
            i = this.d + this.c.b;
            if (this.c.a > 0) {
                cdVar.b(fpVar.a, fpVar.c(), this.c.a);
                fpVar.b(fpVar.c() + this.c.a);
                obj = this.a.j[i + -1] != 255 ? 1 : null;
            }
            if (i == this.a.g) {
                i = -1;
            }
            this.d = i;
        }
        return true;
    }

    public long a(cd cdVar) throws IOException, InterruptedException {
        fe.a(cdVar.d() != -1);
        dl.a(cdVar);
        this.a.a();
        while ((this.a.b & 4) != 4 && cdVar.c() < cdVar.d()) {
            dl.a(cdVar, this.a, this.b, false);
            cdVar.b(this.a.h + this.a.i);
        }
        return this.a.c;
    }

    public long a(cd cdVar, long j) throws IOException, InterruptedException {
        dl.a(cdVar);
        dl.a(cdVar, this.a, this.b, false);
        while (this.a.c < j) {
            cdVar.b(this.a.h + this.a.i);
            this.e = this.a.c;
            dl.a(cdVar, this.a, this.b, false);
        }
        if (this.e != 0) {
            cdVar.a();
            cdVar = this.e;
            this.e = 0;
            this.d = -1;
            return cdVar;
        }
        throw new bl();
    }
}
