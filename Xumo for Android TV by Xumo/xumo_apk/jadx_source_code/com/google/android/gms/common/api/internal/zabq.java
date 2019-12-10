package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public final class zabq extends BroadcastReceiver {
    private Context mContext;
    private final zabr zajh;

    public zabq(zabr com_google_android_gms_common_api_internal_zabr) {
        this.zajh = com_google_android_gms_common_api_internal_zabr;
    }

    public final void zac(Context context) {
        this.mContext = context;
    }

    public final synchronized void unregister() {
        if (this.mContext != null) {
            this.mContext.unregisterReceiver(this);
        }
        this.mContext = null;
    }

    public final void onReceive(Context context, Intent intent) {
        context = intent.getData();
        if ("com.google.android.gms".equals(context != null ? context.getSchemeSpecificPart() : null) != null) {
            this.zajh.zas();
            unregister();
        }
    }
}
