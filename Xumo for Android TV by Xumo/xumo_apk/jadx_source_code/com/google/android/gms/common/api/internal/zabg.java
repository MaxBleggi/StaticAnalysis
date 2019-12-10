package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.internal.base.zal;

final class zabg extends zal {
    private final /* synthetic */ zabe zahu;

    zabg(zabe com_google_android_gms_common_api_internal_zabe, Looper looper) {
        this.zahu = com_google_android_gms_common_api_internal_zabe;
        super(looper);
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                ((zabf) message.obj).zac(this.zahu);
                return;
            case 2:
                throw ((RuntimeException) message.obj);
            default:
                message = message.what;
                StringBuilder stringBuilder = new StringBuilder(31);
                stringBuilder.append("Unknown message id: ");
                stringBuilder.append(message);
                Log.w("GACStateManager", stringBuilder.toString());
                return;
        }
    }
}
