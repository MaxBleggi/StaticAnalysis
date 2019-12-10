package com.google.ads.interactivemedia.v3.internal;

import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import java.io.IOException;

/* compiled from: IMASDK */
public final class dr implements cc {
    private static final int a = ft.c("ID3");
    private final long b;
    private final fp c;
    private ds d;
    private boolean e;

    public dr() {
        this(0);
    }

    public void c() {
    }

    public dr(long j) {
        this.b = j;
        this.c = new fp((int) Callback.DEFAULT_DRAG_ANIMATION_DURATION);
    }

    public boolean a(cd cdVar) throws IOException, InterruptedException {
        fp fpVar = new fp(10);
        fo foVar = new fo(fpVar.a);
        int i = 0;
        while (true) {
            cdVar.c(fpVar.a, 0, 10);
            fpVar.c(0);
            if (fpVar.j() != a) {
                break;
            }
            int i2 = ((((fpVar.a[6] & 127) << 21) | ((fpVar.a[7] & 127) << 14)) | ((fpVar.a[8] & 127) << 7)) | (fpVar.a[9] & 127);
            i += i2 + 10;
            cdVar.c(i2);
        }
        cdVar.a();
        cdVar.c(i);
        int i3 = i;
        while (true) {
            i2 = 0;
            int i4 = 0;
            while (true) {
                cdVar.c(fpVar.a, 0, 2);
                fpVar.c(0);
                if ((fpVar.g() & 65526) != 65520) {
                    break;
                }
                i2++;
                if (i2 >= 4 && i4 > TsExtractor.TS_PACKET_SIZE) {
                    return true;
                }
                cdVar.c(fpVar.a, 0, 4);
                foVar.a(14);
                int c = foVar.c(13);
                if (c <= 6) {
                    return false;
                }
                cdVar.c(c - 6);
                i4 += c;
            }
            cdVar.a();
            i3++;
            if (i3 - i >= 8192) {
                return false;
            }
            cdVar.c(i3);
        }
    }

    public void a(ce ceVar) {
        this.d = new ds(ceVar.d(0), ceVar.d(1));
        ceVar.f();
        ceVar.a(cj.f);
    }

    public void b() {
        this.e = false;
        this.d.a();
    }

    public int a(cd cdVar, ch chVar) throws IOException, InterruptedException {
        cdVar = cdVar.a(this.c.a, 0, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        if (cdVar == -1) {
            return -1;
        }
        this.c.c(0);
        this.c.b(cdVar);
        if (this.e == null) {
            this.d.a(this.b, true);
            this.e = true;
        }
        this.d.a(this.c);
        return 0;
    }
}
