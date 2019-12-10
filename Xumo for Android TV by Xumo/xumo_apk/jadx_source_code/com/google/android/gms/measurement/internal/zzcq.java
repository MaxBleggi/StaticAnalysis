package com.google.android.gms.measurement.internal;

abstract class zzcq extends zzcp {
    private boolean zzvz;

    zzcq(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
        this.zzadp.zzb(this);
    }

    protected abstract boolean zzgy();

    protected void zzgz() {
    }

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
        } else if (!zzgy()) {
            this.zzadp.zzku();
            this.zzvz = true;
        }
    }

    public final void zzgx() {
        if (this.zzvz) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzgz();
        this.zzadp.zzku();
        this.zzvz = true;
    }
}
