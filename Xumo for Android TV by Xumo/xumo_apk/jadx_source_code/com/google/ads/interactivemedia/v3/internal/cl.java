package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.co.a;
import com.google.android.exoplayer2.util.MimeTypes;
import java.util.Collections;

/* compiled from: IMASDK */
final class cl extends co {
    private static final int[] b = new int[]{5500, 11000, 22000, 44000};
    private boolean c;
    private boolean d;

    public cl(ck ckVar) {
        super(ckVar);
    }

    protected boolean a(fp fpVar) throws a {
        if (this.c) {
            fpVar.d(1);
        } else {
            fpVar = fpVar.f();
            int i = (fpVar >> 4) & 15;
            fpVar = (fpVar >> 2) & 3;
            StringBuilder stringBuilder;
            if (fpVar < null || fpVar >= b.length) {
                stringBuilder = new StringBuilder(38);
                stringBuilder.append("Invalid sample rate index: ");
                stringBuilder.append(fpVar);
                throw new a(stringBuilder.toString());
            } else if (i == 10) {
                this.c = true;
            } else {
                stringBuilder = new StringBuilder(39);
                stringBuilder.append("Audio format not supported: ");
                stringBuilder.append(i);
                throw new a(stringBuilder.toString());
            }
        }
        return true;
    }

    protected void a(fp fpVar, long j) {
        int f = fpVar.f();
        if (f == 0 && !this.d) {
            j = new byte[fpVar.b()];
            fpVar.a(j, 0, j.length);
            fpVar = ff.a(j);
            this.a.a(bj.a(null, MimeTypes.AUDIO_AAC, -1, -1, a(), ((Integer) fpVar.second).intValue(), ((Integer) fpVar.first).intValue(), Collections.singletonList(j), null));
            this.d = true;
        } else if (f == 1) {
            int b = fpVar.b();
            this.a.a(fpVar, b);
            this.a.a(j, 1, b, 0, null);
        }
    }
}
