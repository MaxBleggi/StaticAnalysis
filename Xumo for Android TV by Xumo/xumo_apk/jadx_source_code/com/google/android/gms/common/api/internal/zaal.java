package com.google.android.gms.common.api.internal;

final class zaal implements Runnable {
    private final /* synthetic */ zaak zagi;

    zaal(zaak com_google_android_gms_common_api_internal_zaak) {
        this.zagi = com_google_android_gms_common_api_internal_zaak;
    }

    public final void run() {
        this.zagi.zaex.cancelAvailabilityErrorNotifications(this.zagi.mContext);
    }
}
