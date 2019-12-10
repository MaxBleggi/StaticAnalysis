package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class ed {
    private final ck a;

    public ed(ck ckVar) {
        this.a = ckVar;
        ckVar.a(bj.a(null, "application/eia-608", -1, -1, null));
    }

    public void a(long j, fp fpVar) {
        while (fpVar.b() > 1) {
            int f;
            int i = 0;
            int i2 = 0;
            do {
                f = fpVar.f();
                i2 += f;
            } while (f == 255);
            while (true) {
                f = fpVar.f();
                int i3 = i + f;
                if (f != 255) {
                    break;
                }
                i = i3;
            }
            if (eo.a(i2, i3, fpVar)) {
                this.a.a(fpVar, i3);
                this.a.a(j, 1, i3, 0, null);
            } else {
                fpVar.d(i3);
            }
        }
    }
}
