package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;

/* compiled from: IMASDK */
final class em {
    private static final long[] a = new long[]{128, 64, 32, 16, 8, 4, 2, 1};
    private final byte[] b = new byte[8];
    private int c;
    private int d;

    public void a() {
        this.c = 0;
        this.d = 0;
    }

    public long a(cd cdVar, boolean z, boolean z2, int i) throws IOException, InterruptedException {
        if (this.c == 0) {
            if (!cdVar.a(this.b, 0, 1, z)) {
                return -1;
            }
            this.d = a(this.b[0] & 255);
            if (!this.d) {
                this.c = 1;
            } else {
                throw new IllegalStateException("No valid varint length mask found");
            }
        }
        if (this.d > i) {
            this.c = 0;
            return -2;
        }
        if (!this.d) {
            cdVar.b(this.b, 1, this.d - 1);
        }
        this.c = 0;
        return a(this.b, this.d, z2);
    }

    public int b() {
        return this.d;
    }

    public static int a(int i) {
        for (int i2 = 0; i2 < a.length; i2++) {
            if ((a[i2] & ((long) i)) != 0) {
                return i2 + 1;
            }
        }
        return -1;
    }

    public static long a(byte[] bArr, int i, boolean z) {
        long j = ((long) bArr[0]) & 255;
        if (z) {
            j &= a[i - 1] ^ -1;
        }
        for (z = true; z < i; z++) {
            j = (j << 8) | (((long) bArr[z]) & 255);
        }
        return j;
    }
}
