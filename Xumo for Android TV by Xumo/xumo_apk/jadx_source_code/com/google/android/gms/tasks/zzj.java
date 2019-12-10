package com.google.android.gms.tasks;

final class zzj implements Runnable {
    private final /* synthetic */ Task zzg;
    private final /* synthetic */ zzi zzm;

    zzj(zzi com_google_android_gms_tasks_zzi, Task task) {
        this.zzm = com_google_android_gms_tasks_zzi;
        this.zzg = task;
    }

    public final void run() {
        synchronized (this.zzm.mLock) {
            if (this.zzm.zzl != null) {
                this.zzm.zzl.onComplete(this.zzg);
            }
        }
    }
}
