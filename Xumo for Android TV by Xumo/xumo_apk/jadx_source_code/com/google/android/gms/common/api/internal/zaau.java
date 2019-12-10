package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;

abstract class zaau implements Runnable {
    private final /* synthetic */ zaak zagi;

    private zaau(zaak com_google_android_gms_common_api_internal_zaak) {
        this.zagi = com_google_android_gms_common_api_internal_zaak;
    }

    @WorkerThread
    protected abstract void zaan();

    @WorkerThread
    public void run() {
        this.zagi.zaen.lock();
        try {
            if (!Thread.interrupted()) {
                zaan();
                this.zagi.zaen.unlock();
            }
        } catch (RuntimeException e) {
            this.zagi.zafs.zab(e);
        } finally {
            this.zagi.zaen.unlock();
        }
    }
}
