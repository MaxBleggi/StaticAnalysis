package com.google.android.gms.common.api.internal;

import androidx.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

final class zan implements Runnable {
    private final zam zadi;
    final /* synthetic */ zal zadj;

    zan(zal com_google_android_gms_common_api_internal_zal, zam com_google_android_gms_common_api_internal_zam) {
        this.zadj = com_google_android_gms_common_api_internal_zal;
        this.zadi = com_google_android_gms_common_api_internal_zam;
    }

    @MainThread
    public final void run() {
        if (this.zadj.mStarted) {
            ConnectionResult connectionResult = this.zadi.getConnectionResult();
            if (connectionResult.hasResolution()) {
                this.zadj.mLifecycleFragment.startActivityForResult(GoogleApiActivity.zaa(this.zadj.getActivity(), connectionResult.getResolution(), this.zadi.zar(), false), 1);
            } else if (this.zadj.zacc.isUserResolvableError(connectionResult.getErrorCode())) {
                this.zadj.zacc.zaa(this.zadj.getActivity(), this.zadj.mLifecycleFragment, connectionResult.getErrorCode(), 2, this.zadj);
            } else if (connectionResult.getErrorCode() == 18) {
                this.zadj.zacc.zaa(this.zadj.getActivity().getApplicationContext(), new zao(this, GoogleApiAvailability.zaa(this.zadj.getActivity(), this.zadj)));
            } else {
                this.zadj.zaa(connectionResult, this.zadi.zar());
            }
        }
    }
}
