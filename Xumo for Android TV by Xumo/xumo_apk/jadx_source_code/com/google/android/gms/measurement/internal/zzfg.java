package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzdx;

public final class zzfg extends zzfj {
    private final zzw zzatr;
    private final AlarmManager zzyt = ((AlarmManager) getContext().getSystemService(NotificationCompat.CATEGORY_ALARM));
    private Integer zzyu;

    protected zzfg(zzfk com_google_android_gms_measurement_internal_zzfk) {
        super(com_google_android_gms_measurement_internal_zzfk);
        this.zzatr = new zzfh(this, com_google_android_gms_measurement_internal_zzfk.zzmh(), com_google_android_gms_measurement_internal_zzfk);
    }

    protected final boolean zzgy() {
        this.zzyt.cancel(zzeo());
        if (VERSION.SDK_INT >= 24) {
            zzlr();
        }
        return false;
    }

    @TargetApi(24)
    private final void zzlr() {
        JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService("jobscheduler");
        int jobId = getJobId();
        zzgt().zzjo().zzg("Cancelling job. JobID", Integer.valueOf(jobId));
        jobScheduler.cancel(jobId);
    }

    public final void zzh(long j) {
        zzcl();
        zzgw();
        Context context = getContext();
        if (!zzbk.zza(context)) {
            zzgt().zzjn().zzca("Receiver not registered/enabled");
        }
        if (!zzfu.zza(context, false)) {
            zzgt().zzjn().zzca("Service not registered/enabled");
        }
        cancel();
        long elapsedRealtime = zzbx().elapsedRealtime() + j;
        if (j < Math.max(0, ((Long) zzag.zzakd.get()).longValue()) && !this.zzatr.zzej()) {
            zzgt().zzjo().zzca("Scheduling upload with DelayedRunnable");
            this.zzatr.zzh(j);
        }
        zzgw();
        if (VERSION.SDK_INT >= 24) {
            zzgt().zzjo().zzca("Scheduling upload with JobScheduler");
            context = getContext();
            ComponentName componentName = new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementJobService");
            int jobId = getJobId();
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putString("action", "com.google.android.gms.measurement.UPLOAD");
            JobInfo build = new Builder(jobId, componentName).setMinimumLatency(j).setOverrideDeadline(j << 1).setExtras(persistableBundle).build();
            zzgt().zzjo().zzg("Scheduling job. JobID", Integer.valueOf(jobId));
            zzdx.zza(context, build, "com.google.android.gms", "UploadAlarm");
            return;
        }
        zzgt().zzjo().zzca("Scheduling upload with AlarmManager");
        this.zzyt.setInexactRepeating(2, elapsedRealtime, Math.max(((Long) zzag.zzajy.get()).longValue(), j), zzeo());
    }

    private final int getJobId() {
        if (this.zzyu == null) {
            String str = "measurement";
            String valueOf = String.valueOf(getContext().getPackageName());
            this.zzyu = Integer.valueOf((valueOf.length() != 0 ? str.concat(valueOf) : new String(str)).hashCode());
        }
        return this.zzyu.intValue();
    }

    public final void cancel() {
        zzcl();
        this.zzyt.cancel(zzeo());
        this.zzatr.cancel();
        if (VERSION.SDK_INT >= 24) {
            zzlr();
        }
    }

    private final PendingIntent zzeo() {
        Context context = getContext();
        return PendingIntent.getBroadcast(context, 0, new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementReceiver").setAction("com.google.android.gms.measurement.UPLOAD"), 0);
    }

    public final /* bridge */ /* synthetic */ zzfq zzjr() {
        return super.zzjr();
    }

    public final /* bridge */ /* synthetic */ zzk zzjs() {
        return super.zzjs();
    }

    public final /* bridge */ /* synthetic */ zzr zzjt() {
        return super.zzjt();
    }

    public final /* bridge */ /* synthetic */ void zzgf() {
        super.zzgf();
    }

    public final /* bridge */ /* synthetic */ void zzgg() {
        super.zzgg();
    }

    public final /* bridge */ /* synthetic */ void zzgh() {
        super.zzgh();
    }

    public final /* bridge */ /* synthetic */ void zzaf() {
        super.zzaf();
    }

    public final /* bridge */ /* synthetic */ zzy zzgp() {
        return super.zzgp();
    }

    public final /* bridge */ /* synthetic */ Clock zzbx() {
        return super.zzbx();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ zzao zzgq() {
        return super.zzgq();
    }

    public final /* bridge */ /* synthetic */ zzfu zzgr() {
        return super.zzgr();
    }

    public final /* bridge */ /* synthetic */ zzbp zzgs() {
        return super.zzgs();
    }

    public final /* bridge */ /* synthetic */ zzaq zzgt() {
        return super.zzgt();
    }

    public final /* bridge */ /* synthetic */ zzbb zzgu() {
        return super.zzgu();
    }

    public final /* bridge */ /* synthetic */ zzo zzgv() {
        return super.zzgv();
    }

    public final /* bridge */ /* synthetic */ zzl zzgw() {
        return super.zzgw();
    }
}
