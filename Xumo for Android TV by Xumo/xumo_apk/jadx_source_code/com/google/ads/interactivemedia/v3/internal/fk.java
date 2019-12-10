package com.google.ads.interactivemedia.v3.internal;

import java.util.Arrays;

/* compiled from: IMASDK */
public final class fk {
    private int a;
    private long[] b;

    public fk() {
        this(32);
    }

    public fk(int i) {
        this.b = new long[i];
    }

    public void a(long j) {
        if (this.a == this.b.length) {
            this.b = Arrays.copyOf(this.b, this.a * 2);
        }
        long[] jArr = this.b;
        int i = this.a;
        this.a = i + 1;
        jArr[i] = j;
    }

    public long a(int i) {
        if (i >= 0 && i < this.a) {
            return this.b[i];
        }
        int i2 = this.a;
        StringBuilder stringBuilder = new StringBuilder(45);
        stringBuilder.append("Invalid size ");
        stringBuilder.append(i);
        stringBuilder.append(", size is ");
        stringBuilder.append(i2);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int a() {
        return this.a;
    }
}
