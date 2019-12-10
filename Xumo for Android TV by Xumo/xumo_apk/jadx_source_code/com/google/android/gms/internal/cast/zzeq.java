package com.google.android.gms.internal.cast;

import android.os.Build.VERSION;
import android.os.Looper;

final class zzeq extends ThreadLocal<zzep> {
    zzeq() {
    }

    protected final /* synthetic */ Object initialValue() {
        if (VERSION.SDK_INT >= 16) {
            return new zzev();
        }
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            return new zzeu(myLooper);
        }
        throw new IllegalStateException("The current thread must have a looper!");
    }
}
