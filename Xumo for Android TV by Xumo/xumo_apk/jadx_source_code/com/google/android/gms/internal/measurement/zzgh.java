package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgh extends zzzl<zzgh> {
    private static volatile zzgh[] zzaxi;
    public Integer zzaxj;
    public Long zzaxk;

    public static zzgh[] zzmy() {
        if (zzaxi == null) {
            synchronized (zzzp.zzcgg) {
                if (zzaxi == null) {
                    zzaxi = new zzgh[0];
                }
            }
        }
        return zzaxi;
    }

    public zzgh() {
        this.zzaxj = null;
        this.zzaxk = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgh)) {
            return false;
        }
        zzgh com_google_android_gms_internal_measurement_zzgh = (zzgh) obj;
        if (this.zzaxj == null) {
            if (com_google_android_gms_internal_measurement_zzgh.zzaxj != null) {
                return false;
            }
        } else if (!this.zzaxj.equals(com_google_android_gms_internal_measurement_zzgh.zzaxj)) {
            return false;
        }
        if (this.zzaxk == null) {
            if (com_google_android_gms_internal_measurement_zzgh.zzaxk != null) {
                return false;
            }
        } else if (!this.zzaxk.equals(com_google_android_gms_internal_measurement_zzgh.zzaxk)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgh.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgh.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgh.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + (this.zzaxj == null ? 0 : this.zzaxj.hashCode())) * 31) + (this.zzaxk == null ? 0 : this.zzaxk.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i = this.zzcfx.hashCode();
            }
        }
        return hashCode + i;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzaxj != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(1, this.zzaxj.intValue());
        }
        if (this.zzaxk != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(2, this.zzaxk.longValue());
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzaxj != null) {
            zzf += zzzj.zzh(1, this.zzaxj.intValue());
        }
        return this.zzaxk != null ? zzf + zzzj.zzd(2, this.zzaxk.longValue()) : zzf;
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            if (zzuq == 8) {
                this.zzaxj = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
            } else if (zzuq == 16) {
                this.zzaxk = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
