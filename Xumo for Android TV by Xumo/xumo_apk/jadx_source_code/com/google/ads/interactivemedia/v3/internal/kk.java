package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;

/* compiled from: IMASDK */
class kk implements ki {
    private lv a;
    private byte[] b;
    private final int c;

    public kk(int i) {
        this.c = i;
        a();
    }

    public void a() {
        this.b = new byte[this.c];
        this.a = lv.a(this.b);
    }

    public void a(int i, long j) throws IOException {
        this.a.a(i, j);
    }

    public void a(int i, String str) throws IOException {
        this.a.a(i, str);
    }

    public byte[] b() throws IOException {
        int a = this.a.a();
        if (a < 0) {
            throw new IOException();
        } else if (a == 0) {
            return this.b;
        } else {
            Object obj = new byte[(this.b.length - a)];
            System.arraycopy(this.b, 0, obj, 0, obj.length);
            return obj;
        }
    }
}
