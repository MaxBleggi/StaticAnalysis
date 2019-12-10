package com.google.android.gms.tasks;

final class zzd implements Runnable {
    private final /* synthetic */ Task zzg;
    private final /* synthetic */ zzc zzh;

    zzd(zzc com_google_android_gms_tasks_zzc, Task task) {
        this.zzh = com_google_android_gms_tasks_zzc;
        this.zzg = task;
    }

    public final void run() {
        if (this.zzg.isCanceled()) {
            this.zzh.zzf.zza();
            return;
        }
        try {
            this.zzh.zzf.setResult(this.zzh.zze.then(this.zzg));
        } catch (Exception e) {
            if (e.getCause() instanceof Exception) {
                this.zzh.zzf.setException((Exception) e.getCause());
            } else {
                this.zzh.zzf.setException(e);
            }
        } catch (Exception e2) {
            this.zzh.zzf.setException(e2);
        }
    }
}
