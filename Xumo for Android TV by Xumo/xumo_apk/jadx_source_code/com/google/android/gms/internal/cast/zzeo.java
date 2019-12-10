package com.google.android.gms.internal.cast;

final class zzeo extends zzer {
    private final /* synthetic */ zzen zzzu;

    zzeo(zzen com_google_android_gms_internal_cast_zzen) {
        this.zzzu = com_google_android_gms_internal_cast_zzen;
    }

    public final void doFrame(long j) {
        this.zzzu.zzzs = this.zzzu.zzzs + 1;
        if (this.zzzu.zzb(this.zzzu.animator) == null && this.zzzu.animator.isStarted() == null && this.zzzu.zzdt() == null) {
            if (this.zzzu.zzzq != null) {
                this.zzzu.zzzq.run();
            }
            this.zzzu.animator.start();
        }
    }
}
