package com.google.android.gms.measurement.internal;

final class zzdp implements Runnable {
    private final /* synthetic */ boolean zzaes;
    private final /* synthetic */ zzcy zzarr;

    zzdp(zzcy com_google_android_gms_measurement_internal_zzcy, boolean z) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
        this.zzaes = z;
    }

    public final void run() {
        boolean isEnabled = this.zzarr.zzadp.isEnabled();
        boolean zzks = this.zzarr.zzadp.zzks();
        this.zzarr.zzadp.zzd(this.zzaes);
        if (zzks == this.zzaes) {
            this.zzarr.zzadp.zzgt().zzjo().zzg("Default data collection state already set to", Boolean.valueOf(this.zzaes));
        }
        if (this.zzarr.zzadp.isEnabled() == isEnabled || this.zzarr.zzadp.isEnabled() != this.zzarr.zzadp.zzks()) {
            this.zzarr.zzadp.zzgt().zzjl().zze("Default data collection is different than actual status", Boolean.valueOf(this.zzaes), Boolean.valueOf(isEnabled));
        }
        this.zzarr.zzlc();
    }
}
