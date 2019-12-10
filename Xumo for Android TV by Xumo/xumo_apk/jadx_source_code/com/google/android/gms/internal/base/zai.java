package com.google.android.gms.internal.base;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;

final class zai extends ConstantState {
    int mChangingConfigurations;
    int zanv;

    zai(zai com_google_android_gms_internal_base_zai) {
        if (com_google_android_gms_internal_base_zai != null) {
            this.mChangingConfigurations = com_google_android_gms_internal_base_zai.mChangingConfigurations;
            this.zanv = com_google_android_gms_internal_base_zai.zanv;
        }
    }

    public final Drawable newDrawable() {
        return new zae(this);
    }

    public final int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }
}
