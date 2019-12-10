package com.google.ads.interactivemedia.v3.internal;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;
import java.nio.ByteBuffer;

/* compiled from: IMASDK */
public final class fd {
    private static final int[] a = new int[]{1, 2, 3, 6};
    private static final int[] b = new int[]{48000, 44100, 32000};
    private static final int[] c = new int[]{24000, 22050, 16000};
    private static final int[] d = new int[]{2, 1, 2, 3, 3, 4, 4, 5};
    private static final int[] e = new int[]{32, 40, 48, 56, 64, 80, 96, 112, 128, 160, PsExtractor.AUDIO_STREAM, 224, 256, 320, 384, 448, 512, 576, 640};
    private static final int[] f = new int[]{69, 87, 104, 121, 139, 174, 208, 243, 278, 348, 417, 487, 557, 696, 835, 975, 1114, 1253, 1393};

    public static int a() {
        return 1536;
    }

    public static bj a(fp fpVar, String str, long j, String str2) {
        int i = b[(fpVar.f() & PsExtractor.AUDIO_STREAM) >> 6];
        int f = fpVar.f();
        int i2 = d[(f & 56) >> 3];
        if ((f & 4) != 0) {
            i2++;
        }
        return bj.a(str, MimeTypes.AUDIO_AC3, -1, -1, j, i2, i, null, str2);
    }

    public static bj b(fp fpVar, String str, long j, String str2) {
        fp fpVar2 = fpVar;
        fpVar.d(2);
        int i = b[(fpVar.f() & PsExtractor.AUDIO_STREAM) >> 6];
        int f = fpVar.f();
        int i2 = d[(f & 14) >> 1];
        if ((f & 1) != 0) {
            i2++;
        }
        return bj.a(str, MimeTypes.AUDIO_E_AC3, -1, -1, j, i2, i, null, str2);
    }

    public static bj a(fo foVar, String str, long j, String str2) {
        fo foVar2 = foVar;
        foVar.b(32);
        int c = foVar.c(2);
        foVar.b(14);
        int c2 = foVar.c(3);
        if (!((c2 & 1) == 0 || c2 == 1)) {
            foVar.b(2);
        }
        if ((c2 & 4) != 0) {
            foVar.b(2);
        }
        if (c2 == 2) {
            foVar.b(2);
        }
        return bj.a(str, MimeTypes.AUDIO_AC3, -1, -1, j, d[c2] + foVar.b(), b[c], null, str2);
    }

    public static bj b(fo foVar, String str, long j, String str2) {
        int i;
        fo foVar2 = foVar;
        foVar.b(32);
        int c = foVar.c(2);
        if (c == 3) {
            i = c[foVar.c(2)];
        } else {
            foVar.b(2);
            i = b[c];
        }
        int i2 = i;
        i = foVar.c(3);
        return bj.a(str, MimeTypes.AUDIO_E_AC3, -1, -1, j, d[i] + foVar.b(), i2, null, str2);
    }

    public static int a(byte[] bArr) {
        return a((bArr[4] & PsExtractor.AUDIO_STREAM) >> 6, bArr[4] & 63);
    }

    public static int b(byte[] bArr) {
        return ((((bArr[2] & 7) << 8) + (bArr[3] & 255)) + 1) * 2;
    }

    public static int c(byte[] bArr) {
        int i = 6;
        if (((bArr[4] & PsExtractor.AUDIO_STREAM) >> 6) != 3) {
            i = a[(bArr[4] & 48) >> 4];
        }
        return i * 256;
    }

    public static int a(ByteBuffer byteBuffer) {
        int i = 6;
        if (((byteBuffer.get(byteBuffer.position() + 4) & PsExtractor.AUDIO_STREAM) >> 6) != 3) {
            i = a[(byteBuffer.get(byteBuffer.position() + 4) & 48) >> 4];
        }
        return i * 256;
    }

    private static int a(int i, int i2) {
        i = b[i];
        if (i == 44100) {
            return (f[i2 / 2] + (i2 % 2)) * 2;
        }
        i2 = e[i2 / 2];
        return i == 32000 ? i2 * 6 : i2 * 4;
    }
}
