package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;

final class zabl implements Runnable {
    private final /* synthetic */ zaa zaix;
    private final /* synthetic */ ConnectionResult zaiy;

    zabl(zaa com_google_android_gms_common_api_internal_GoogleApiManager_zaa, ConnectionResult connectionResult) {
        this.zaix = com_google_android_gms_common_api_internal_GoogleApiManager_zaa;
        this.zaiy = connectionResult;
    }

    public final void run() {
        this.zaix.onConnectionFailed(this.zaiy);
    }
}
