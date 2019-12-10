package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult.StatusListener;
import com.google.android.gms.common.api.Status;

final class zaac implements StatusListener {
    private final /* synthetic */ BasePendingResult zafl;
    private final /* synthetic */ zaab zafm;

    zaac(zaab com_google_android_gms_common_api_internal_zaab, BasePendingResult basePendingResult) {
        this.zafm = com_google_android_gms_common_api_internal_zaab;
        this.zafl = basePendingResult;
    }

    public final void onComplete(Status status) {
        this.zafm.zafj.remove(this.zafl);
    }
}
