package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.GmsClientEventManager.GmsClientEventState;

final class zaax implements GmsClientEventState {
    private final /* synthetic */ zaaw zahg;

    zaax(zaaw com_google_android_gms_common_api_internal_zaaw) {
        this.zahg = com_google_android_gms_common_api_internal_zaaw;
    }

    public final Bundle getConnectionHint() {
        return null;
    }

    public final boolean isConnected() {
        return this.zahg.isConnected();
    }
}
