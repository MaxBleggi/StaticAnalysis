package com.google.ads.interactivemedia.v3.internal;

import android.util.Pair;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* compiled from: IMASDK */
public final class ff {
    private static final byte[] a = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 1};
    private static final int[] b = new int[]{96000, 88200, SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_DEFAULT, 48000, 44100, 32000, 24000, 22050, 16000, 12000, 11025, 8000, 7350};
    private static final int[] c = new int[]{0, 1, 2, 3, 4, 5, 6, 8, -1, -1, -1, 7, 8, -1, 8, -1};

    public static Pair<Integer, Integer> a(byte[] bArr) {
        fo foVar = new fo(bArr);
        int c = foVar.c(5);
        int c2 = foVar.c(4);
        boolean z = false;
        if (c2 == 15) {
            c2 = foVar.c(24);
        } else {
            fe.a(c2 < 13);
            c2 = b[c2];
        }
        int c3 = foVar.c(4);
        if (c == 5 || c == 29) {
            c = foVar.c(4);
            if (c == 15) {
                c = foVar.c(24);
            } else {
                fe.a(c < 13);
                c = b[c];
            }
            c2 = c;
            if (foVar.c(5) == 22) {
                c3 = foVar.c(4);
            }
        }
        bArr = c[c3];
        if (bArr != -1) {
            z = true;
        }
        fe.a(z);
        return Pair.create(Integer.valueOf(c2), Integer.valueOf(bArr));
    }

    public static byte[] a(int i, int i2, int i3) {
        return new byte[]{(byte) (((i << 3) & 248) | ((i2 >> 1) & 7)), (byte) (((i2 << 7) & 128) | ((i3 << 3) & 120))};
    }

    public static byte[] a(byte[] bArr, int i, int i2) {
        Object obj = new byte[(a.length + i2)];
        System.arraycopy(a, 0, obj, 0, a.length);
        System.arraycopy(bArr, i, obj, a.length, i2);
        return obj;
    }
}
