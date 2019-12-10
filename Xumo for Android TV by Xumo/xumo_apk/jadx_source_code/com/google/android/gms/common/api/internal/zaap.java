package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks;
import javax.annotation.concurrent.GuardedBy;

final class zaap extends zabf {
    private final /* synthetic */ ConnectionProgressReportCallbacks zagn;

    zaap(zaan com_google_android_gms_common_api_internal_zaan, zabd com_google_android_gms_common_api_internal_zabd, ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.zagn = connectionProgressReportCallbacks;
        super(com_google_android_gms_common_api_internal_zabd);
    }

    @GuardedBy("mLock")
    public final void zaan() {
        this.zagn.onReportServiceBinding(new ConnectionResult(16, null));
    }
}
