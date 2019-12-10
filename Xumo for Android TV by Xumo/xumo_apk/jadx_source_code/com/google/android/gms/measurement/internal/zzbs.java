package com.google.android.gms.measurement.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

final class zzbs<V> extends FutureTask<V> implements Comparable<zzbs> {
    private final String zzapl;
    private final /* synthetic */ zzbp zzapm;
    private final long zzapn = zzbp.zzapk.getAndIncrement();
    final boolean zzapo;

    zzbs(zzbp com_google_android_gms_measurement_internal_zzbp, Callable<V> callable, boolean z, String str) {
        this.zzapm = com_google_android_gms_measurement_internal_zzbp;
        super(callable);
        Preconditions.checkNotNull(str);
        this.zzapl = str;
        this.zzapo = z;
        if (this.zzapn == Long.MAX_VALUE) {
            com_google_android_gms_measurement_internal_zzbp.zzgt().zzjg().zzca("Tasks index overflow");
        }
    }

    zzbs(zzbp com_google_android_gms_measurement_internal_zzbp, Runnable runnable, boolean z, String str) {
        this.zzapm = com_google_android_gms_measurement_internal_zzbp;
        super(runnable, false);
        Preconditions.checkNotNull(str);
        this.zzapl = str;
        this.zzapo = null;
        if (this.zzapn == Long.MAX_VALUE) {
            com_google_android_gms_measurement_internal_zzbp.zzgt().zzjg().zzca("Tasks index overflow");
        }
    }

    protected final void setException(Throwable th) {
        this.zzapm.zzgt().zzjg().zzg(this.zzapl, th);
        if (th instanceof zzbq) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
        }
        super.setException(th);
    }

    public final /* synthetic */ int compareTo(@NonNull Object obj) {
        zzbs com_google_android_gms_measurement_internal_zzbs = (zzbs) obj;
        if (this.zzapo != com_google_android_gms_measurement_internal_zzbs.zzapo) {
            return this.zzapo != null ? -1 : 1;
        } else {
            if (this.zzapn < com_google_android_gms_measurement_internal_zzbs.zzapn) {
                return -1;
            }
            if (this.zzapn > com_google_android_gms_measurement_internal_zzbs.zzapn) {
                return 1;
            }
            this.zzapm.zzgt().zzjh().zzg("Two tasks share the same index. index", Long.valueOf(this.zzapn));
            return null;
        }
    }
}
