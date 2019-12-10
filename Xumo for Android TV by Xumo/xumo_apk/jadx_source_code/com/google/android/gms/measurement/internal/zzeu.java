package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.MainThread;
import com.google.android.gms.common.internal.Preconditions;

public final class zzeu<T extends Context & zzey> {
    private final T zzaby;

    public zzeu(T t) {
        Preconditions.checkNotNull(t);
        this.zzaby = t;
    }

    @MainThread
    public final void onCreate() {
        zzbu zza = zzbu.zza(this.zzaby, null);
        zzaq zzgt = zza.zzgt();
        zza.zzgw();
        zzgt.zzjo().zzca("Local AppMeasurementService is starting up");
    }

    @MainThread
    public final void onDestroy() {
        zzbu zza = zzbu.zza(this.zzaby, null);
        zzaq zzgt = zza.zzgt();
        zza.zzgw();
        zzgt.zzjo().zzca("Local AppMeasurementService is shutting down");
    }

    @MainThread
    public final int onStartCommand(Intent intent, int i, int i2) {
        i = zzbu.zza(this.zzaby, null);
        zzaq zzgt = i.zzgt();
        if (intent == null) {
            zzgt.zzjj().zzca("AppMeasurementService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        i.zzgw();
        zzgt.zzjo().zze("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action) != 0) {
            zzb(new zzev(this, i2, zzgt, intent));
        }
        return 2;
    }

    private final void zzb(Runnable runnable) {
        zzfk zzn = zzfk.zzn(this.zzaby);
        zzn.zzgs().zzc(new zzex(this, zzn, runnable));
    }

    @MainThread
    public final IBinder onBind(Intent intent) {
        if (intent == null) {
            zzgt().zzjg().zzca("onBind called with null intent");
            return null;
        }
        intent = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(intent)) {
            return new zzbw(zzfk.zzn(this.zzaby));
        }
        zzgt().zzjj().zzg("onBind received unknown action", intent);
        return null;
    }

    @MainThread
    public final boolean onUnbind(Intent intent) {
        if (intent == null) {
            zzgt().zzjg().zzca("onUnbind called with null intent");
            return true;
        }
        zzgt().zzjo().zzg("onUnbind called for intent. action", intent.getAction());
        return true;
    }

    @TargetApi(24)
    @MainThread
    public final boolean onStartJob(JobParameters jobParameters) {
        zzbu zza = zzbu.zza(this.zzaby, null);
        zzaq zzgt = zza.zzgt();
        String string = jobParameters.getExtras().getString("action");
        zza.zzgw();
        zzgt.zzjo().zzg("Local AppMeasurementJobService called. action", string);
        if ("com.google.android.gms.measurement.UPLOAD".equals(string)) {
            zzb(new zzew(this, zzgt, jobParameters));
        }
        return true;
    }

    @MainThread
    public final void onRebind(Intent intent) {
        if (intent == null) {
            zzgt().zzjg().zzca("onRebind called with null intent");
            return;
        }
        zzgt().zzjo().zzg("onRebind called. action", intent.getAction());
    }

    private final zzaq zzgt() {
        return zzbu.zza(this.zzaby, null).zzgt();
    }

    final /* synthetic */ void zza(zzaq com_google_android_gms_measurement_internal_zzaq, JobParameters jobParameters) {
        com_google_android_gms_measurement_internal_zzaq.zzjo().zzca("AppMeasurementJobService processed last upload request.");
        ((zzey) this.zzaby).zza(jobParameters, false);
    }

    final /* synthetic */ void zza(int i, zzaq com_google_android_gms_measurement_internal_zzaq, Intent intent) {
        if (((zzey) this.zzaby).callServiceStopSelfResult(i)) {
            com_google_android_gms_measurement_internal_zzaq.zzjo().zzg("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzgt().zzjo().zzca("Completed wakeful intent.");
            ((zzey) this.zzaby).zzb(intent);
        }
    }
}
