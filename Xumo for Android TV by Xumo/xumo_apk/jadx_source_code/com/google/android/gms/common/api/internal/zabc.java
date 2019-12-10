package com.google.android.gms.common.api.internal;

import java.lang.ref.WeakReference;

final class zabc extends zabr {
    private WeakReference<zaaw> zahl;

    zabc(zaaw com_google_android_gms_common_api_internal_zaaw) {
        this.zahl = new WeakReference(com_google_android_gms_common_api_internal_zaaw);
    }

    public final void zas() {
        zaaw com_google_android_gms_common_api_internal_zaaw = (zaaw) this.zahl.get();
        if (com_google_android_gms_common_api_internal_zaaw != null) {
            com_google_android_gms_common_api_internal_zaaw.resume();
        }
    }
}
