package com.google.ads.interactivemedia.v3.internal;

import android.os.Build.VERSION;
import android.view.View;

/* compiled from: IMASDK */
public final class ag {
    public static float a(View view) {
        return VERSION.SDK_INT >= 21 ? view.getZ() : null;
    }

    public static View b(View view) {
        view = view.getParent();
        return view instanceof View ? view : null;
    }

    public static boolean c(View view) {
        if ((VERSION.SDK_INT >= 19 && !view.isAttachedToWindow()) || !view.isShown()) {
            return false;
        }
        while (view != null) {
            if (view.getAlpha() == 0.0f) {
                return false;
            }
            view = b(view);
        }
        return true;
    }

    public static boolean d(View view) {
        if ((VERSION.SDK_INT < 19 || view.isAttachedToWindow()) && view.getVisibility() == 0 && view.getAlpha() != 0.0f) {
            return true;
        }
        return false;
    }
}
