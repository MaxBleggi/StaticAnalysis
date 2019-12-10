package com.google.android.gms.cast.framework;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.internal.cast.zzdh;
import com.google.android.gms.internal.cast.zze;

public class ReconnectionService extends Service {
    private static final zzdh zzbe = new zzdh("ReconnectionService");
    private zzr zziv;

    public void onCreate() {
        CastContext sharedInstance = CastContext.getSharedInstance(this);
        this.zziv = zze.zza(this, sharedInstance.getSessionManager().zzu(), sharedInstance.zzr().zzu());
        try {
            this.zziv.onCreate();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "onCreate", zzr.class.getSimpleName());
        }
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            return this.zziv.onStartCommand(intent, i, i2);
        } catch (Intent intent2) {
            zzbe.zza(intent2, "Unable to call %s on %s.", "onStartCommand", zzr.class.getSimpleName());
            return 1;
        }
    }

    public IBinder onBind(Intent intent) {
        try {
            return this.zziv.onBind(intent);
        } catch (Intent intent2) {
            zzbe.zza(intent2, "Unable to call %s on %s.", "onBind", zzr.class.getSimpleName());
            return null;
        }
    }

    public void onDestroy() {
        try {
            this.zziv.onDestroy();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "onDestroy", zzr.class.getSimpleName());
        }
        super.onDestroy();
    }
}
