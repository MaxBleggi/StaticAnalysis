package com.google.ads.interactivemedia.v3.internal;

import android.os.SystemClock;

/* compiled from: IMASDK */
final class bp implements bd {
    private boolean a;
    private long b;
    private long c;

    bp() {
    }

    public void b() {
        if (!this.a) {
            this.a = true;
            this.c = b(this.b);
        }
    }

    public void c() {
        if (this.a) {
            this.b = b(this.c);
            this.a = false;
        }
    }

    public void a(long j) {
        this.b = j;
        this.c = b(j);
    }

    public long a() {
        return this.a ? b(this.c) : this.b;
    }

    private long b(long j) {
        return (SystemClock.elapsedRealtime() * 1000) - j;
    }
}
