package com.google.android.exoplayer2.scheduler;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import androidx.annotation.RequiresPermission;
import com.google.android.exoplayer2.util.Util;

@TargetApi(21)
public final class PlatformScheduler implements Scheduler {
    private static final String KEY_REQUIREMENTS = "requirements";
    private static final String KEY_SERVICE_ACTION = "service_action";
    private static final String KEY_SERVICE_PACKAGE = "service_package";
    private static final String TAG = "PlatformScheduler";
    private final int jobId;
    private final JobScheduler jobScheduler;
    private final ComponentName jobServiceComponentName;

    public static final class PlatformSchedulerService extends JobService {
        public boolean onStopJob(JobParameters jobParameters) {
            return false;
        }

        public boolean onStartJob(JobParameters jobParameters) {
            PlatformScheduler.logd("PlatformSchedulerService started");
            PersistableBundle extras = jobParameters.getExtras();
            if (new Requirements(extras.getInt(PlatformScheduler.KEY_REQUIREMENTS)).checkRequirements(this)) {
                PlatformScheduler.logd("Requirements are met");
                jobParameters = extras.getString(PlatformScheduler.KEY_SERVICE_ACTION);
                String string = extras.getString(PlatformScheduler.KEY_SERVICE_PACKAGE);
                Intent intent = new Intent(jobParameters).setPackage(string);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Starting service action: ");
                stringBuilder.append(jobParameters);
                stringBuilder.append(" package: ");
                stringBuilder.append(string);
                PlatformScheduler.logd(stringBuilder.toString());
                Util.startForegroundService(this, intent);
            } else {
                PlatformScheduler.logd("Requirements are not met");
                jobFinished(jobParameters, true);
            }
            return null;
        }
    }

    private static void logd(String str) {
    }

    @RequiresPermission("android.permission.RECEIVE_BOOT_COMPLETED")
    public PlatformScheduler(Context context, int i) {
        this.jobId = i;
        this.jobServiceComponentName = new ComponentName(context, PlatformSchedulerService.class);
        this.jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
    }

    public boolean schedule(Requirements requirements, String str, String str2) {
        requirements = this.jobScheduler.schedule(buildJobInfo(this.jobId, this.jobServiceComponentName, requirements, str2, str));
        str = new StringBuilder();
        str.append("Scheduling job: ");
        str.append(this.jobId);
        str.append(" result: ");
        str.append(requirements);
        logd(str.toString());
        return requirements == 1 ? true : null;
    }

    public boolean cancel() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Canceling job: ");
        stringBuilder.append(this.jobId);
        logd(stringBuilder.toString());
        this.jobScheduler.cancel(this.jobId);
        return true;
    }

    private static JobInfo buildJobInfo(int i, ComponentName componentName, Requirements requirements, String str, String str2) {
        Builder builder = new Builder(i, componentName);
        switch (requirements.getRequiredNetworkType()) {
            case 0:
                i = 0;
                break;
            case 1:
                i = 1;
                break;
            case 2:
                i = 2;
                break;
            case 3:
                if (Util.SDK_INT >= 24) {
                    i = 3;
                    break;
                }
                throw new UnsupportedOperationException();
            case 4:
                if (Util.SDK_INT >= 26) {
                    i = 4;
                    break;
                }
                throw new UnsupportedOperationException();
            default:
                throw new UnsupportedOperationException();
        }
        builder.setRequiredNetworkType(i);
        builder.setRequiresDeviceIdle(requirements.isIdleRequired());
        builder.setRequiresCharging(requirements.isChargingRequired());
        builder.setPersisted(true);
        i = new PersistableBundle();
        i.putString(KEY_SERVICE_ACTION, str);
        i.putString(KEY_SERVICE_PACKAGE, str2);
        i.putInt(KEY_REQUIREMENTS, requirements.getRequirementsData());
        builder.setExtras(i);
        return builder.build();
    }
}
