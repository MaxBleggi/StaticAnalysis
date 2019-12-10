package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;

/* compiled from: IMASDK */
abstract class dm {
    protected final fp a = new fp(new byte[OggPageHeader.MAX_PAGE_PAYLOAD], 0);
    protected final dj b = new dj();
    protected ck c;
    protected ce d;

    dm() {
    }

    abstract int a(cd cdVar, ch chVar) throws IOException, InterruptedException;

    void a(ce ceVar, ck ckVar) {
        this.d = ceVar;
        this.c = ckVar;
    }

    void b() {
        this.b.a();
        this.a.a();
    }
}
