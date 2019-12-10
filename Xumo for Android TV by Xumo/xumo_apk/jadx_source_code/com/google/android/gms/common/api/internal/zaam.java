package com.google.android.gms.common.api.internal;

import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;

final class zaam implements ConnectionProgressReportCallbacks {
    private final Api<?> mApi;
    private final boolean zaeb;
    private final WeakReference<zaak> zagj;

    public zaam(zaak com_google_android_gms_common_api_internal_zaak, Api<?> api, boolean z) {
        this.zagj = new WeakReference(com_google_android_gms_common_api_internal_zaak);
        this.mApi = api;
        this.zaeb = z;
    }

    public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
        zaak com_google_android_gms_common_api_internal_zaak = (zaak) this.zagj.get();
        if (com_google_android_gms_common_api_internal_zaak != null) {
            Preconditions.checkState(Looper.myLooper() == com_google_android_gms_common_api_internal_zaak.zafs.zaed.getLooper(), "onReportServiceBinding must be called on the GoogleApiClient handler thread");
            com_google_android_gms_common_api_internal_zaak.zaen.lock();
            try {
                if (com_google_android_gms_common_api_internal_zaak.zac(0)) {
                    if (!connectionResult.isSuccess()) {
                        com_google_android_gms_common_api_internal_zaak.zab(connectionResult, this.mApi, this.zaeb);
                    }
                    if (com_google_android_gms_common_api_internal_zaak.zaao() != null) {
                        com_google_android_gms_common_api_internal_zaak.zaap();
                    }
                    com_google_android_gms_common_api_internal_zaak.zaen.unlock();
                }
            } finally {
                com_google_android_gms_common_api_internal_zaak.zaen.unlock();
            }
        }
    }
}
