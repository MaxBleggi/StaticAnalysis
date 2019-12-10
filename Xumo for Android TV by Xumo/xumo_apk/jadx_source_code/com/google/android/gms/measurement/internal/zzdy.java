package com.google.android.gms.measurement.internal;

final class zzdy implements Runnable {
    private final /* synthetic */ zzdw zzasm;
    private final /* synthetic */ zzdv zzasn;

    zzdy(zzdw com_google_android_gms_measurement_internal_zzdw, zzdv com_google_android_gms_measurement_internal_zzdv) {
        this.zzasm = com_google_android_gms_measurement_internal_zzdw;
        this.zzasn = com_google_android_gms_measurement_internal_zzdv;
    }

    public final void run() {
        this.zzasm.zza(this.zzasn, false);
        this.zzasm.zzasd = null;
        this.zzasm.zzgl().zza(null);
    }
}
