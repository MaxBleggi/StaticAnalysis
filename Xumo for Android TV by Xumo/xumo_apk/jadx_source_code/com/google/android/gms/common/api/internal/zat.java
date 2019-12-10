package com.google.android.gms.common.api.internal;

final class zat implements Runnable {
    private final /* synthetic */ zas zaep;

    zat(zas com_google_android_gms_common_api_internal_zas) {
        this.zaep = com_google_android_gms_common_api_internal_zas;
    }

    public final void run() {
        this.zaep.zaen.lock();
        try {
            this.zaep.zax();
        } finally {
            this.zaep.zaen.unlock();
        }
    }
}
