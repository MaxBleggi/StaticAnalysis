package com.google.android.gms.tasks;

final class zzh implements Runnable {
    private final /* synthetic */ zzg zzk;

    zzh(zzg com_google_android_gms_tasks_zzg) {
        this.zzk = com_google_android_gms_tasks_zzg;
    }

    public final void run() {
        synchronized (this.zzk.mLock) {
            if (this.zzk.zzj != null) {
                this.zzk.zzj.onCanceled();
            }
        }
    }
}
