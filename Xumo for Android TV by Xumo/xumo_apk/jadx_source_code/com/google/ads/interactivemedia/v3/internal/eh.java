package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;
import com.google.android.exoplayer2.upstream.DataSchemeDataSource;
import java.io.IOException;

/* compiled from: IMASDK */
final class eh {

    /* compiled from: IMASDK */
    private static final class a {
        public final int a;
        public final long b;

        private a(int i, long j) {
            this.a = i;
            this.b = j;
        }

        public static a a(cd cdVar, fp fpVar) throws IOException, InterruptedException {
            cdVar.c(fpVar.a, 0, 8);
            fpVar.c(0);
            return new a(fpVar.m(), fpVar.l());
        }
    }

    public static eg a(cd cdVar) throws IOException, InterruptedException, bl {
        fe.a((Object) cdVar);
        fp fpVar = new fp(16);
        if (a.a(cdVar, fpVar).a != ft.c("RIFF")) {
            return null;
        }
        cdVar.c(fpVar.a, 0, 4);
        fpVar.c(0);
        int m = fpVar.m();
        if (m != ft.c("WAVE")) {
            StringBuilder stringBuilder = new StringBuilder(36);
            stringBuilder.append("Unsupported RIFF format: ");
            stringBuilder.append(m);
            Log.e("WavHeaderReader", stringBuilder.toString());
            return null;
        }
        a a = a.a(cdVar, fpVar);
        while (a.a != ft.c("fmt ")) {
            cdVar.c((int) a.b);
            a = a.a(cdVar, fpVar);
        }
        fe.b(a.b >= 16);
        cdVar.c(fpVar.a, 0, 16);
        fpVar.c(0);
        int h = fpVar.h();
        int h2 = fpVar.h();
        int t = fpVar.t();
        int t2 = fpVar.t();
        int h3 = fpVar.h();
        int h4 = fpVar.h();
        int i = (h2 * h4) / 8;
        if (h3 == i) {
            int a2 = ft.a(h4);
            if (a2 == 0) {
                stringBuilder = new StringBuilder(38);
                stringBuilder.append("Unsupported WAV bit depth: ");
                stringBuilder.append(h4);
                Log.e("WavHeaderReader", stringBuilder.toString());
                return null;
            } else if (h == 1 || h == 65534) {
                cdVar.c(((int) a.b) - 16);
                return new eg(h2, t, t2, h3, h4, a2);
            } else {
                stringBuilder = new StringBuilder(40);
                stringBuilder.append("Unsupported WAV format type: ");
                stringBuilder.append(h);
                Log.e("WavHeaderReader", stringBuilder.toString());
                return null;
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder(55);
        stringBuilder2.append("Expected block alignment: ");
        stringBuilder2.append(i);
        stringBuilder2.append("; got: ");
        stringBuilder2.append(h3);
        throw new bl(stringBuilder2.toString());
    }

    public static void a(cd cdVar, eg egVar) throws IOException, InterruptedException, bl {
        fe.a((Object) cdVar);
        fe.a((Object) egVar);
        cdVar.a();
        fp fpVar = new fp(8);
        a a = a.a(cdVar, fpVar);
        while (a.a != ft.c(DataSchemeDataSource.SCHEME_DATA)) {
            int i = a.a;
            StringBuilder stringBuilder = new StringBuilder(39);
            stringBuilder.append("Ignoring unknown WAV chunk: ");
            stringBuilder.append(i);
            Log.w("WavHeaderReader", stringBuilder.toString());
            long j = a.b + 8;
            if (a.a == ft.c("RIFF")) {
                j = 12;
            }
            if (j <= 2147483647L) {
                cdVar.b((int) j);
                a = a.a(cdVar, fpVar);
            } else {
                egVar = a.a;
                StringBuilder stringBuilder2 = new StringBuilder(51);
                stringBuilder2.append("Chunk is too large (~2GB+) to skip; id: ");
                stringBuilder2.append(egVar);
                throw new bl(stringBuilder2.toString());
            }
        }
        cdVar.b(8);
        egVar.a(cdVar.c(), a.b);
    }
}
