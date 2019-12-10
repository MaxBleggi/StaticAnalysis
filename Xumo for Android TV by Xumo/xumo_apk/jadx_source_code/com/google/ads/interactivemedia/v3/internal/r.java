package com.google.ads.interactivemedia.v3.internal;

import android.annotation.SuppressLint;
import android.content.Context;

/* compiled from: IMASDK */
public class r {
    @SuppressLint({"StaticFieldLeak"})
    private static r a = new r();
    private Context b;

    public static r a() {
        return a;
    }

    private r() {
    }

    public Context b() {
        return this.b;
    }

    public void a(Context context) {
        this.b = context != null ? context.getApplicationContext() : null;
    }
}
