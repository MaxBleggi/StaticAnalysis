package com.google.ads.interactivemedia.v3.internal;

import android.net.Uri;
import java.util.Arrays;

/* compiled from: IMASDK */
public final class eu {
    public final Uri a;
    public final byte[] b;
    public final long c;
    public final long d;
    public final long e;
    public final String f;
    public final int g;

    public eu(Uri uri, long j, long j2, String str) {
        this(uri, j, j, j2, str, 0);
    }

    public eu(Uri uri, long j, long j2, long j3, String str, int i) {
        this(uri, null, j, j2, j3, str, i);
    }

    public eu(Uri uri, byte[] bArr, long j, long j2, long j3, String str, int i) {
        boolean z = false;
        fe.a(j >= 0);
        fe.a(j2 >= 0);
        if (j3 > 0 || j3 == -1) {
            z = true;
        }
        fe.a(z);
        this.a = uri;
        this.b = bArr;
        this.c = j;
        this.d = j2;
        this.e = j3;
        this.f = str;
        this.g = i;
    }

    public String toString() {
        String valueOf = String.valueOf(this.a);
        String arrays = Arrays.toString(this.b);
        long j = this.c;
        long j2 = this.d;
        long j3 = this.e;
        String str = this.f;
        int i = this.g;
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(valueOf).length() + 93) + String.valueOf(arrays).length()) + String.valueOf(str).length());
        stringBuilder.append("DataSpec[");
        stringBuilder.append(valueOf);
        stringBuilder.append(", ");
        stringBuilder.append(arrays);
        stringBuilder.append(", ");
        stringBuilder.append(j);
        stringBuilder.append(", ");
        stringBuilder.append(j2);
        stringBuilder.append(", ");
        stringBuilder.append(j3);
        stringBuilder.append(", ");
        stringBuilder.append(str);
        stringBuilder.append(", ");
        stringBuilder.append(i);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
