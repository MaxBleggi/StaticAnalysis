package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgf extends zzzl<zzgf> {
    private static volatile zzgf[] zzaxd;
    public String value;
    public String zzoj;

    public static zzgf[] zzmw() {
        if (zzaxd == null) {
            synchronized (zzzp.zzcgg) {
                if (zzaxd == null) {
                    zzaxd = new zzgf[0];
                }
            }
        }
        return zzaxd;
    }

    public zzgf() {
        this.zzoj = null;
        this.value = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgf)) {
            return false;
        }
        zzgf com_google_android_gms_internal_measurement_zzgf = (zzgf) obj;
        if (this.zzoj == null) {
            if (com_google_android_gms_internal_measurement_zzgf.zzoj != null) {
                return false;
            }
        } else if (!this.zzoj.equals(com_google_android_gms_internal_measurement_zzgf.zzoj)) {
            return false;
        }
        if (this.value == null) {
            if (com_google_android_gms_internal_measurement_zzgf.value != null) {
                return false;
            }
        } else if (!this.value.equals(com_google_android_gms_internal_measurement_zzgf.value)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgf.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgf.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgf.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + (this.zzoj == null ? 0 : this.zzoj.hashCode())) * 31) + (this.value == null ? 0 : this.value.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i = this.zzcfx.hashCode();
            }
        }
        return hashCode + i;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzoj != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(1, this.zzoj);
        }
        if (this.value != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(2, this.value);
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzoj != null) {
            zzf += zzzj.zzc(1, this.zzoj);
        }
        return this.value != null ? zzf + zzzj.zzc(2, this.value) : zzf;
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            if (zzuq == 10) {
                this.zzoj = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (zzuq == 18) {
                this.value = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
