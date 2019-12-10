package com.google.android.gms.measurement;

import android.content.BroadcastReceiver.PendingResult;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.MainThread;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.google.android.gms.measurement.internal.zzbk;
import com.google.android.gms.measurement.internal.zzbn;

public final class AppMeasurementReceiver extends WakefulBroadcastReceiver implements zzbn {
    private zzbk zzadq;

    @MainThread
    public final void onReceive(Context context, Intent intent) {
        if (this.zzadq == null) {
            this.zzadq = new zzbk(this);
        }
        this.zzadq.onReceive(context, intent);
    }

    @MainThread
    public final void doStartService(Context context, Intent intent) {
        WakefulBroadcastReceiver.startWakefulService(context, intent);
    }

    public final PendingResult doGoAsync() {
        return goAsync();
    }
}
