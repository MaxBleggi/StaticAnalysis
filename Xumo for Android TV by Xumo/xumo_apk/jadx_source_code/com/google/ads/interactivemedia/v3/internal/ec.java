package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
public final class ec {
    private final long a;
    private long b;
    private volatile long c = Long.MIN_VALUE;

    public ec(long j) {
        this.a = j;
    }

    public void a() {
        this.c = Long.MIN_VALUE;
    }

    public long a(long j) {
        long j2;
        if (this.c != Long.MIN_VALUE) {
            j2 = (this.c + 4294967296L) / 8589934592L;
            long j3 = ((j2 - 1) * 8589934592L) + j;
            j += j2 * 8589934592L;
            if (Math.abs(j3 - this.c) < Math.abs(j - this.c)) {
                j = j3;
            }
        }
        j2 = b(j);
        if (this.a != Long.MAX_VALUE && this.c == Long.MIN_VALUE) {
            this.b = this.a - j2;
        }
        this.c = j;
        return j2 + this.b;
    }

    public static long b(long j) {
        return (j * 1000000) / 90000;
    }
}
