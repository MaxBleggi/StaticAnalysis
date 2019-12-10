package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;

class zzaz extends BroadcastReceiver {
    @VisibleForTesting
    private static final String zzabi = "com.google.android.gms.measurement.internal.zzaz";
    private boolean zzabj;
    private boolean zzabk;
    private final zzfk zzang;

    zzaz(zzfk com_google_android_gms_measurement_internal_zzfk) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzfk);
        this.zzang = com_google_android_gms_measurement_internal_zzfk;
    }

    @MainThread
    public void onReceive(Context context, Intent intent) {
        this.zzang.zzlx();
        context = intent.getAction();
        this.zzang.zzgt().zzjo().zzg("NetworkBroadcastReceiver received action", context);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(context) != null) {
            Intent zzfb = this.zzang.zzlt().zzfb();
            if (this.zzabk != zzfb) {
                this.zzabk = zzfb;
                this.zzang.zzgs().zzc(new zzba(this, zzfb));
            }
            return;
        }
        this.zzang.zzgt().zzjj().zzg("NetworkBroadcastReceiver received unknown action", context);
    }

    @WorkerThread
    public final void zzey() {
        this.zzang.zzlx();
        this.zzang.zzgs().zzaf();
        if (!this.zzabj) {
            this.zzang.getContext().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.zzabk = this.zzang.zzlt().zzfb();
            this.zzang.zzgt().zzjo().zzg("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzabk));
            this.zzabj = true;
        }
    }

    @WorkerThread
    public final void unregister() {
        this.zzang.zzlx();
        this.zzang.zzgs().zzaf();
        this.zzang.zzgs().zzaf();
        if (this.zzabj) {
            this.zzang.zzgt().zzjo().zzca("Unregistering connectivity change receiver");
            this.zzabj = false;
            this.zzabk = false;
            try {
                this.zzang.getContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzang.zzgt().zzjg().zzg("Failed to unregister the network broadcast receiver", e);
            }
        }
    }
}
