package com.google.android.gms.measurement.internal;

abstract class zzfj extends zzfi {
    private boolean zzvz;

    zzfj(zzfk com_google_android_gms_measurement_internal_zzfk) {
        super(com_google_android_gms_measurement_internal_zzfk);
        this.zzang.zzb(this);
    }

    protected abstract boolean zzgy();

    final boolean isInitialized() {
        return this.zzvz;
    }

    protected final void zzcl() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzq() {
        if (this.zzvz) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzgy();
        this.zzang.zzmg();
        this.zzvz = true;
    }
}
