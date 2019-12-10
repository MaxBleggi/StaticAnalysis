package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;

final /* synthetic */ class zze implements Runnable {
    private final zzd zzs;
    private final Intent zzt;

    zze(zzd com_google_firebase_iid_zzd, Intent intent) {
        this.zzs = com_google_firebase_iid_zzd;
        this.zzt = intent;
    }

    public final void run() {
        zzd com_google_firebase_iid_zzd = this.zzs;
        String action = this.zzt.getAction();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(action).length() + 61);
        stringBuilder.append("Service took too long to process intent: ");
        stringBuilder.append(action);
        stringBuilder.append(" App may get closed.");
        Log.w("EnhancedIntentService", stringBuilder.toString());
        com_google_firebase_iid_zzd.finish();
    }
}
