package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/* compiled from: IMASDK */
final class dh extends dm {
    private fi e;
    private fh f;
    private boolean g;

    dh() {
    }

    static boolean a(fp fpVar) {
        return (fpVar.f() == 127 && fpVar.k() == 1179402563) ? true : null;
    }

    public int a(cd cdVar, ch chVar) throws IOException, InterruptedException {
        long c = cdVar.c();
        if (!this.b.a(cdVar, this.a)) {
            return -1;
        }
        byte[] bArr = r0.a.a;
        if (r0.e == null) {
            r0.e = new fi(bArr, 17);
            Object copyOfRange = Arrays.copyOfRange(bArr, 9, r0.a.c());
            copyOfRange[4] = Byte.MIN_VALUE;
            r0.c.a(bj.a(null, "audio/x-flac", r0.e.a(), -1, r0.e.b(), r0.e.f, r0.e.e, Collections.singletonList(copyOfRange), null));
        } else if (bArr[0] == (byte) -1) {
            if (!r0.g) {
                if (r0.f != null) {
                    r0.d.a(r0.f.a(c, (long) r0.e.e));
                    r0.f = null;
                } else {
                    r0.d.a(cj.f);
                }
                r0.g = true;
            }
            r0.c.a(r0.a, r0.a.c());
            r0.a.c(0);
            r0.c.a(fj.a(r0.e, r0.a), 1, r0.a.c(), 0, null);
        } else if ((bArr[0] & 127) == 3 && r0.f == null) {
            r0.f = fh.a(r0.a);
        }
        r0.a.a();
        return 0;
    }
}
