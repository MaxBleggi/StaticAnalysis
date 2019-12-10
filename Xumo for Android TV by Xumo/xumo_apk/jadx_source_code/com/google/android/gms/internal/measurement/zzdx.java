package com.google.android.gms.internal.measurement;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.util.Log;
import androidx.annotation.Nullable;
import java.lang.reflect.Method;

@TargetApi(24)
public final class zzdx {
    @Nullable
    private static final Method zzadk = zzfu();
    @Nullable
    private static final Method zzadl = zzfv();
    private static volatile zzdz zzadm = zzdy.zzadn;
    private final JobScheduler zzadj;

    @androidx.annotation.Nullable
    private static java.lang.reflect.Method zzfu() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.gms.internal.measurement.zzdx.zzfu():java.lang.reflect.Method
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 5 more
*/
        /*
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 24;
        if (r0 < r1) goto L_0x0037;
        r0 = android.app.job.JobScheduler.class;
        r1 = "scheduleAsPackage";
        r2 = 4;
        r2 = new java.lang.Class[r2];
        r3 = 0;
        r4 = android.app.job.JobInfo.class;
        r2[r3] = r4;
        r3 = 1;
        r4 = java.lang.String.class;
        r2[r3] = r4;
        r3 = 2;
        r4 = java.lang.Integer.TYPE;
        r2[r3] = r4;
        r3 = 3;
        r4 = java.lang.String.class;
        r2[r3] = r4;
        r0 = r0.getDeclaredMethod(r1, r2);
        return r0;
        r0 = "JobSchedulerCompat";
        r1 = 6;
        r0 = android.util.Log.isLoggable(r0, r1);
        if (r0 == 0) goto L_0x0037;
        r0 = "JobSchedulerCompat";
        r1 = "No scheduleAsPackage method available, falling back to schedule";
        android.util.Log.e(r0, r1);
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzdx.zzfu():java.lang.reflect.Method");
    }

    @androidx.annotation.Nullable
    private static java.lang.reflect.Method zzfv() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.gms.internal.measurement.zzdx.zzfv():java.lang.reflect.Method
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 5 more
*/
        /*
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 0;
        r2 = 24;
        if (r0 < r2) goto L_0x0021;
        r0 = android.os.UserHandle.class;
        r2 = "myUserId";
        r0 = r0.getDeclaredMethod(r2, r1);
        return r0;
        r0 = "JobSchedulerCompat";
        r2 = 6;
        r0 = android.util.Log.isLoggable(r0, r2);
        if (r0 == 0) goto L_0x0021;
        r0 = "JobSchedulerCompat";
        r2 = "No myUserId method available";
        android.util.Log.e(r0, r2);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzdx.zzfv():java.lang.reflect.Method");
    }

    private static int zzfw() {
        if (zzadl != null) {
            try {
                return ((Integer) zzadl.invoke(null, new Object[0])).intValue();
            } catch (Throwable e) {
                if (Log.isLoggable("JobSchedulerCompat", 6)) {
                    Log.e("JobSchedulerCompat", "myUserId invocation illegal", e);
                }
            }
        }
        return 0;
    }

    private zzdx(JobScheduler jobScheduler) {
        this.zzadj = jobScheduler;
    }

    private final int zza(JobInfo jobInfo, String str, int i, String str2) {
        if (zzadk != null) {
            try {
                return ((Integer) zzadk.invoke(this.zzadj, new Object[]{jobInfo, str, Integer.valueOf(i), str2})).intValue();
            } catch (String str3) {
                Log.e(str2, "error calling scheduleAsPackage", str3);
            }
        }
        return this.zzadj.schedule(jobInfo);
    }

    public static int zza(Context context, JobInfo jobInfo, String str, String str2) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (zzadk != null && zzadm.zzfy()) {
            if (context.checkSelfPermission("android.permission.UPDATE_DEVICE_STATS") == null) {
                return new zzdx(jobScheduler).zza(jobInfo, str, zzfw(), str2);
            }
        }
        return jobScheduler.schedule(jobInfo);
    }
}
