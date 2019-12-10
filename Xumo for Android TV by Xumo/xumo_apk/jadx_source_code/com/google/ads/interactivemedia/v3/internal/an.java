package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.am.a;
import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: IMASDK */
public class an implements a {
    private final BlockingQueue<Runnable> a = new LinkedBlockingQueue();
    private final ThreadPoolExecutor b = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, this.a);
    private final ArrayDeque<am> c = new ArrayDeque();
    private am d = null;

    public void b(am amVar) {
        amVar.a((a) this);
        this.c.add(amVar);
        if (this.d == null) {
            a();
        }
    }

    private void a() {
        this.d = (am) this.c.poll();
        if (this.d != null) {
            this.d.a(this.b);
        }
    }

    public void a(am amVar) {
        this.d = null;
        a();
    }
}
