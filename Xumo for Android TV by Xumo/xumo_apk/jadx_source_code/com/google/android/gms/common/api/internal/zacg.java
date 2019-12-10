package com.google.android.gms.common.api.internal;

import com.google.android.gms.signin.internal.zaj;

final class zacg implements Runnable {
    private final /* synthetic */ zaj zagq;
    private final /* synthetic */ zace zakj;

    zacg(zace com_google_android_gms_common_api_internal_zace, zaj com_google_android_gms_signin_internal_zaj) {
        this.zakj = com_google_android_gms_common_api_internal_zace;
        this.zagq = com_google_android_gms_signin_internal_zaj;
    }

    public final void run() {
        this.zakj.zac(this.zagq);
    }
}
