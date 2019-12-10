package com.google.android.gms.measurement;

import android.content.BroadcastReceiver;
import android.content.BroadcastReceiver.PendingResult;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.MainThread;
import com.google.android.gms.measurement.internal.zzbk;
import com.google.android.gms.measurement.internal.zzbn;

public final class AppMeasurementInstallReferrerReceiver extends BroadcastReceiver implements zzbn {
    private zzbk zzadq;

    public final void doStartService(Context context, Intent intent) {
    }

    @MainThread
    public final void onReceive(Context context, Intent intent) {
        if (this.zzadq == null) {
            this.zzadq = new zzbk(this);
        }
        this.zzadq.onReceive(context, intent);
    }

    public final PendingResult doGoAsync() {
        return goAsync();
    }
}
