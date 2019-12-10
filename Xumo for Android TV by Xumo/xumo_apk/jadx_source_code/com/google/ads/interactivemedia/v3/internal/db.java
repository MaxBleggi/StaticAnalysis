package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;
import android.util.Pair;
import java.util.UUID;

/* compiled from: IMASDK */
public final class db {
    public static UUID a(byte[] bArr) {
        bArr = b(bArr);
        if (bArr == null) {
            return null;
        }
        return (UUID) bArr.first;
    }

    private static Pair<UUID, byte[]> b(byte[] bArr) {
        fp fpVar = new fp(bArr);
        if (fpVar.c() < 32) {
            return null;
        }
        fpVar.c(0);
        if (fpVar.m() != fpVar.b() + 4 || fpVar.m() != cv.U) {
            return null;
        }
        int a = cv.a(fpVar.m());
        if (a > 1) {
            StringBuilder stringBuilder = new StringBuilder(37);
            stringBuilder.append("Unsupported pssh version: ");
            stringBuilder.append(a);
            Log.w("PsshAtomUtil", stringBuilder.toString());
            return null;
        }
        UUID uuid = new UUID(fpVar.o(), fpVar.o());
        if (a == 1) {
            fpVar.d(fpVar.s() * 16);
        }
        a = fpVar.s();
        if (a != fpVar.b()) {
            return null;
        }
        Object obj = new byte[a];
        fpVar.a(obj, 0, a);
        return Pair.create(uuid, obj);
    }
}
