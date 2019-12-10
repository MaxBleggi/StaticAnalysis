package com.google.ads.interactivemedia.v3.internal;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;
import java.nio.ByteBuffer;

/* compiled from: IMASDK */
public final class fg {
    private static final int[] a = new int[]{1, 2, 2, 2, 2, 3, 3, 4, 4, 5, 6, 6, 6, 7, 8, 8};
    private static final int[] b = new int[]{-1, 8000, 16000, 32000, -1, -1, 11025, 22050, 44100, -1, -1, 12000, 24000, 48000, -1, -1};
    private static final int[] c = new int[]{64, 112, 128, PsExtractor.AUDIO_STREAM, 224, 256, 384, 448, 512, 640, 768, MediaRouterJellybean.DEVICE_OUT_BLUETOOTH, 1024, 1152, 1280, 1536, 1920, 2048, 2304, 2560, 2688, 2816, 2823, 2944, 3072, 3840, 4096, 6144, 7680};
    private static final fo d = new fo();

    public static bj a(byte[] bArr, String str, long j, String str2) {
        int i;
        fo foVar = d;
        byte[] bArr2 = bArr;
        foVar.a(bArr);
        foVar.b(60);
        int i2 = a[foVar.c(6)];
        int i3 = b[foVar.c(4)];
        int c = foVar.c(5);
        if (c >= c.length) {
            i = -1;
        } else {
            i = (c[c] * 1000) / 2;
        }
        foVar.b(10);
        return bj.a(str, MimeTypes.AUDIO_DTS, i, -1, j, i2 + (foVar.c(2) > 0 ? 1 : 0), i3, null, str2);
    }

    public static int a(byte[] bArr) {
        return ((((bArr[5] & 252) >> 2) | ((bArr[4] & 1) << 6)) + 1) * 32;
    }

    public static int a(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        return ((((byteBuffer.get(position + 5) & 252) >> 2) | ((byteBuffer.get(position + 4) & 1) << 6)) + 1) * 32;
    }

    public static int b(byte[] bArr) {
        return (((bArr[7] & PsExtractor.VIDEO_STREAM_MASK) >> 4) | (((bArr[5] & 2) << 12) | ((bArr[6] & 255) << 4))) + 1;
    }
}
