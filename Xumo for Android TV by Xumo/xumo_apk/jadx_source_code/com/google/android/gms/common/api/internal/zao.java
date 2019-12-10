package com.google.android.gms.common.api.internal;

import android.app.Dialog;

final class zao extends zabr {
    private final /* synthetic */ Dialog zadk;
    private final /* synthetic */ zan zadl;

    zao(zan com_google_android_gms_common_api_internal_zan, Dialog dialog) {
        this.zadl = com_google_android_gms_common_api_internal_zan;
        this.zadk = dialog;
    }

    public final void zas() {
        this.zadl.zadj.zaq();
        if (this.zadk.isShowing()) {
            this.zadk.dismiss();
        }
    }
}
