package com.google.android.gms.measurement;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import androidx.annotation.MainThread;
import com.google.android.gms.measurement.internal.zzeu;
import com.google.android.gms.measurement.internal.zzey;

@TargetApi(24)
public final class AppMeasurementJobService extends JobService implements zzey {
    private zzeu<AppMeasurementJobService> zzadr;

    private final zzeu<AppMeasurementJobService> zzfz() {
        if (this.zzadr == null) {
            this.zzadr = new zzeu(this);
        }
        return this.zzadr;
    }

    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public final void zzb(Intent intent) {
    }

    @MainThread
    public final void onCreate() {
        super.onCreate();
        zzfz().onCreate();
    }

    @MainThread
    public final void onDestroy() {
        zzfz().onDestroy();
        super.onDestroy();
    }

    public final boolean onStartJob(JobParameters jobParameters) {
        return zzfz().onStartJob(jobParameters);
    }

    @MainThread
    public final boolean onUnbind(Intent intent) {
        return zzfz().onUnbind(intent);
    }

    @MainThread
    public final void onRebind(Intent intent) {
        zzfz().onRebind(intent);
    }

    public final boolean callServiceStopSelfResult(int i) {
        throw new UnsupportedOperationException();
    }

    @TargetApi(24)
    public final void zza(JobParameters jobParameters, boolean z) {
        jobFinished(jobParameters, false);
    }
}
