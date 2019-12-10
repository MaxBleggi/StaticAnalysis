package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import androidx.core.os.EnvironmentCompat;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.HashMap;
import javax.annotation.concurrent.GuardedBy;

final class zze extends GmsClientSupervisor implements Callback {
    private final Handler mHandler;
    @GuardedBy("mConnectionStatus")
    private final HashMap<zza, zzf> zzdu = new HashMap();
    private final Context zzdv;
    private final ConnectionTracker zzdw;
    private final long zzdx;
    private final long zzdy;

    zze(Context context) {
        this.zzdv = context.getApplicationContext();
        this.mHandler = new com.google.android.gms.internal.common.zze(context.getMainLooper(), this);
        this.zzdw = ConnectionTracker.getInstance();
        this.zzdx = 5000;
        this.zzdy = 300000;
    }

    protected final boolean zza(zza com_google_android_gms_common_internal_GmsClientSupervisor_zza, ServiceConnection serviceConnection, String str) {
        Preconditions.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zzdu) {
            zzf com_google_android_gms_common_internal_zzf = (zzf) this.zzdu.get(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
            if (com_google_android_gms_common_internal_zzf == null) {
                com_google_android_gms_common_internal_zzf = new zzf(this, com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                com_google_android_gms_common_internal_zzf.zza(serviceConnection, str);
                com_google_android_gms_common_internal_zzf.zze(str);
                this.zzdu.put(com_google_android_gms_common_internal_GmsClientSupervisor_zza, com_google_android_gms_common_internal_zzf);
            } else {
                this.mHandler.removeMessages(0, com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                if (!com_google_android_gms_common_internal_zzf.zza(serviceConnection)) {
                    com_google_android_gms_common_internal_zzf.zza(serviceConnection, str);
                    switch (com_google_android_gms_common_internal_zzf.getState()) {
                        case 1:
                            serviceConnection.onServiceConnected(com_google_android_gms_common_internal_zzf.getComponentName(), com_google_android_gms_common_internal_zzf.getBinder());
                            break;
                        case 2:
                            com_google_android_gms_common_internal_zzf.zze(str);
                            break;
                        default:
                            break;
                    }
                }
                com_google_android_gms_common_internal_GmsClientSupervisor_zza = String.valueOf(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(com_google_android_gms_common_internal_GmsClientSupervisor_zza).length() + 81);
                stringBuilder.append("Trying to bind a GmsServiceConnection that was already connected before.  config=");
                stringBuilder.append(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                throw new IllegalStateException(stringBuilder.toString());
            }
            com_google_android_gms_common_internal_GmsClientSupervisor_zza = com_google_android_gms_common_internal_zzf.isBound();
        }
        return com_google_android_gms_common_internal_GmsClientSupervisor_zza;
    }

    protected final void zzb(zza com_google_android_gms_common_internal_GmsClientSupervisor_zza, ServiceConnection serviceConnection, String str) {
        Preconditions.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zzdu) {
            zzf com_google_android_gms_common_internal_zzf = (zzf) this.zzdu.get(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
            StringBuilder stringBuilder;
            if (com_google_android_gms_common_internal_zzf == null) {
                com_google_android_gms_common_internal_GmsClientSupervisor_zza = String.valueOf(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                stringBuilder = new StringBuilder(String.valueOf(com_google_android_gms_common_internal_GmsClientSupervisor_zza).length() + 50);
                stringBuilder.append("Nonexistent connection status for service config: ");
                stringBuilder.append(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                throw new IllegalStateException(stringBuilder.toString());
            } else if (com_google_android_gms_common_internal_zzf.zza(serviceConnection)) {
                com_google_android_gms_common_internal_zzf.zzb(serviceConnection, str);
                if (com_google_android_gms_common_internal_zzf.zzr() != null) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(null, com_google_android_gms_common_internal_GmsClientSupervisor_zza), this.zzdx);
                }
            } else {
                com_google_android_gms_common_internal_GmsClientSupervisor_zza = String.valueOf(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                stringBuilder = new StringBuilder(String.valueOf(com_google_android_gms_common_internal_GmsClientSupervisor_zza).length() + 76);
                stringBuilder.append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=");
                stringBuilder.append(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }

    public final boolean handleMessage(Message message) {
        zza com_google_android_gms_common_internal_GmsClientSupervisor_zza;
        zzf com_google_android_gms_common_internal_zzf;
        switch (message.what) {
            case 0:
                synchronized (this.zzdu) {
                    com_google_android_gms_common_internal_GmsClientSupervisor_zza = (zza) message.obj;
                    com_google_android_gms_common_internal_zzf = (zzf) this.zzdu.get(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                    if (com_google_android_gms_common_internal_zzf != null && com_google_android_gms_common_internal_zzf.zzr()) {
                        if (com_google_android_gms_common_internal_zzf.isBound()) {
                            com_google_android_gms_common_internal_zzf.zzf("GmsClientSupervisor");
                        }
                        this.zzdu.remove(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                    }
                }
                return true;
            case 1:
                synchronized (this.zzdu) {
                    com_google_android_gms_common_internal_GmsClientSupervisor_zza = (zza) message.obj;
                    com_google_android_gms_common_internal_zzf = (zzf) this.zzdu.get(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                    if (com_google_android_gms_common_internal_zzf != null && com_google_android_gms_common_internal_zzf.getState() == 3) {
                        String valueOf = String.valueOf(com_google_android_gms_common_internal_GmsClientSupervisor_zza);
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 47);
                        stringBuilder.append("Timeout waiting for ServiceConnection callback ");
                        stringBuilder.append(valueOf);
                        Log.wtf("GmsClientSupervisor", stringBuilder.toString(), new Exception());
                        ComponentName componentName = com_google_android_gms_common_internal_zzf.getComponentName();
                        if (componentName == null) {
                            componentName = com_google_android_gms_common_internal_GmsClientSupervisor_zza.getComponentName();
                        }
                        if (componentName == null) {
                            componentName = new ComponentName(com_google_android_gms_common_internal_GmsClientSupervisor_zza.getPackage(), EnvironmentCompat.MEDIA_UNKNOWN);
                        }
                        com_google_android_gms_common_internal_zzf.onServiceDisconnected(componentName);
                    }
                }
                return true;
            default:
                return null;
        }
    }
}
