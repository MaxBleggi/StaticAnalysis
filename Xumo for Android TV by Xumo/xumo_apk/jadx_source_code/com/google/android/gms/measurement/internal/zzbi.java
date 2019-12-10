package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.annotation.MainThread;
import com.google.android.gms.internal.measurement.zzv;

public final class zzbi implements ServiceConnection {
    private final String packageName;
    final /* synthetic */ zzbh zzaok;

    zzbi(zzbh com_google_android_gms_measurement_internal_zzbh, String str) {
        this.zzaok = com_google_android_gms_measurement_internal_zzbh;
        this.packageName = str;
    }

    @MainThread
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) {
            this.zzaok.zzadp.zzgt().zzjj().zzca("Install Referrer connection returned with null binder");
            return;
        }
        try {
            componentName = zzv.zza(iBinder);
            if (componentName == null) {
                this.zzaok.zzadp.zzgt().zzjj().zzca("Install Referrer Service implementation was not found");
                return;
            }
            this.zzaok.zzadp.zzgt().zzjm().zzca("Install Referrer Service connected");
            this.zzaok.zzadp.zzgs().zzc(new zzbj(this, componentName, this));
        } catch (ComponentName componentName2) {
            this.zzaok.zzadp.zzgt().zzjj().zzg("Exception occurred while calling Install Referrer API", componentName2);
        }
    }

    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        this.zzaok.zzadp.zzgt().zzjm().zzca("Install Referrer Service disconnected");
    }
}
