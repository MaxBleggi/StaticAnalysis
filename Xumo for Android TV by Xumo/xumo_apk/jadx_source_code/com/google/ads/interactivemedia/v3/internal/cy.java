package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class cy {

    /* compiled from: IMASDK */
    public static final class a {
        public final long[] a;
        public final int[] b;
        public final int c;
        public final long[] d;
        public final int[] e;

        public a(long[] jArr, int[] iArr, int i, long[] jArr2, int[] iArr2) {
            this.a = jArr;
            this.b = iArr;
            this.c = i;
            this.d = jArr2;
            this.e = iArr2;
        }
    }

    public static a a(int i, long[] jArr, int[] iArr, long j) {
        int[] iArr2 = iArr;
        int i2 = 8192 / i;
        int i3 = 0;
        int i4 = 0;
        for (int a : iArr2) {
            i4 += ft.a(a, i2);
        }
        long[] jArr2 = new long[i4];
        int[] iArr3 = new int[i4];
        long[] jArr3 = new long[i4];
        int[] iArr4 = new int[i4];
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i3 < iArr2.length) {
            i4 = iArr2[i3];
            long j2 = jArr[i3];
            while (i4 > 0) {
                int min = Math.min(i2, i4);
                jArr2[i5] = j2;
                iArr3[i5] = i * min;
                i7 = Math.max(i7, iArr3[i5]);
                jArr3[i5] = ((long) i6) * j;
                iArr4[i5] = 1;
                j2 += (long) iArr3[i5];
                i6 += min;
                i4 -= min;
                i5++;
                iArr2 = iArr;
            }
            i3++;
            iArr2 = iArr;
        }
        return new a(jArr2, iArr3, i7, jArr3, iArr4);
    }
}
