package com.google.android.gms.tasks;

final class zzl implements Runnable {
    private final /* synthetic */ Task zzg;
    private final /* synthetic */ zzk zzo;

    zzl(zzk com_google_android_gms_tasks_zzk, Task task) {
        this.zzo = com_google_android_gms_tasks_zzk;
        this.zzg = task;
    }

    public final void run() {
        synchronized (this.zzo.mLock) {
            if (this.zzo.zzn != null) {
                this.zzo.zzn.onFailure(this.zzg.getException());
            }
        }
    }
}
