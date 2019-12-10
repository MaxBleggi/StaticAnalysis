package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import javax.annotation.Nullable;

@VisibleForTesting
final class zzaz extends BroadcastReceiver {
    @Nullable
    private zzay zzdk;

    public zzaz(zzay com_google_firebase_iid_zzay) {
        this.zzdk = com_google_firebase_iid_zzay;
    }

    public final void zzap() {
        if (FirebaseInstanceId.zzl()) {
            Log.d("FirebaseInstanceId", "Connectivity change received registered");
        }
        this.zzdk.getContext().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public final void onReceive(Context context, Intent intent) {
        if (this.zzdk != null && this.zzdk.zzao() != null) {
            if (FirebaseInstanceId.zzl() != null) {
                Log.d("FirebaseInstanceId", "Connectivity changed. Starting background sync.");
            }
            FirebaseInstanceId.zza(this.zzdk, 0);
            this.zzdk.getContext().unregisterReceiver(this);
            this.zzdk = null;
        }
    }
}
