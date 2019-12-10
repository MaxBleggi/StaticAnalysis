package com.google.ads.interactivemedia.v3.internal;

import android.os.HandlerThread;
import android.os.Process;

/* compiled from: IMASDK */
public final class fr extends HandlerThread {
    private final int a;

    public fr(String str, int i) {
        super(str);
        this.a = i;
    }

    public void run() {
        Process.setThreadPriority(this.a);
        super.run();
    }
}
