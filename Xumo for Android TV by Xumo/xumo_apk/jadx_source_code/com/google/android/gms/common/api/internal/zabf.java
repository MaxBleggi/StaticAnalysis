package com.google.android.gms.common.api.internal;

abstract class zabf {
    private final zabd zaht;

    protected zabf(zabd com_google_android_gms_common_api_internal_zabd) {
        this.zaht = com_google_android_gms_common_api_internal_zabd;
    }

    protected abstract void zaan();

    public final void zac(zabe com_google_android_gms_common_api_internal_zabe) {
        com_google_android_gms_common_api_internal_zabe.zaen.lock();
        try {
            if (com_google_android_gms_common_api_internal_zabe.zahp == this.zaht) {
                zaan();
                com_google_android_gms_common_api_internal_zabe.zaen.unlock();
            }
        } finally {
            com_google_android_gms_common_api_internal_zabe.zaen.unlock();
        }
    }
}
