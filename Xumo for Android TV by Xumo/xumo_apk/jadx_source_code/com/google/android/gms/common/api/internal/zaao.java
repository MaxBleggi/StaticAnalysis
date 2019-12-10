package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import javax.annotation.concurrent.GuardedBy;

final class zaao extends zabf {
    private final /* synthetic */ ConnectionResult zagl;
    private final /* synthetic */ zaan zagm;

    zaao(zaan com_google_android_gms_common_api_internal_zaan, zabd com_google_android_gms_common_api_internal_zabd, ConnectionResult connectionResult) {
        this.zagm = com_google_android_gms_common_api_internal_zaan;
        this.zagl = connectionResult;
        super(com_google_android_gms_common_api_internal_zabd);
    }

    @GuardedBy("mLock")
    public final void zaan() {
        this.zagm.zagi.zae(this.zagl);
    }
}
