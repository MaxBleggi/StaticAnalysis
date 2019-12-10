package com.google.android.gms.measurement.internal;

final class zzx implements Runnable {
    private final /* synthetic */ zzcr zzaia;
    private final /* synthetic */ zzw zzaib;

    zzx(zzw com_google_android_gms_measurement_internal_zzw, zzcr com_google_android_gms_measurement_internal_zzcr) {
        this.zzaib = com_google_android_gms_measurement_internal_zzw;
        this.zzaia = com_google_android_gms_measurement_internal_zzcr;
    }

    public final void run() {
        this.zzaia.zzgw();
        if (zzl.isMainThread()) {
            this.zzaia.zzgs().zzc((Runnable) this);
            return;
        }
        boolean zzej = this.zzaib.zzej();
        this.zzaib.zzyp = 0;
        if (zzej) {
            this.zzaib.run();
        }
    }
}
