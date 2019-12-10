package com.google.ads.interactivemedia.v3.internal;

import android.annotation.TargetApi;
import android.os.Trace;

/* compiled from: IMASDK */
public final class fs {
    public static void a(String str) {
        if (ft.a >= 18) {
            b(str);
        }
    }

    public static void a() {
        if (ft.a >= 18) {
            b();
        }
    }

    @TargetApi(18)
    private static void b(String str) {
        Trace.beginSection(str);
    }

    @TargetApi(18)
    private static void b() {
        Trace.endSection();
    }
}
