package com.google.android.gms.internal.cast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class zzez {
    @NonNull
    public static <T> T checkNotNull(@Nullable T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }
}
