package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgb extends zzzl<zzgb> {
    private static volatile zzgb[] zzawm;
    public Boolean zzavu;
    public Boolean zzavv;
    public Integer zzavx;
    public String zzawn;
    public zzfz zzawo;

    public static zzgb[] zzmu() {
        if (zzawm == null) {
            synchronized (zzzp.zzcgg) {
                if (zzawm == null) {
                    zzawm = new zzgb[0];
                }
            }
        }
        return zzawm;
    }

    public zzgb() {
        this.zzavx = null;
        this.zzawn = null;
        this.zzawo = null;
        this.zzavu = null;
        this.zzavv = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgb)) {
            return false;
        }
        zzgb com_google_android_gms_internal_measurement_zzgb = (zzgb) obj;
        if (this.zzavx == null) {
            if (com_google_android_gms_internal_measurement_zzgb.zzavx != null) {
                return false;
            }
        } else if (!this.zzavx.equals(com_google_android_gms_internal_measurement_zzgb.zzavx)) {
            return false;
        }
        if (this.zzawn == null) {
            if (com_google_android_gms_internal_measurement_zzgb.zzawn != null) {
                return false;
            }
        } else if (!this.zzawn.equals(com_google_android_gms_internal_measurement_zzgb.zzawn)) {
            return false;
        }
        if (this.zzawo == null) {
            if (com_google_android_gms_internal_measurement_zzgb.zzawo != null) {
                return false;
            }
        } else if (!this.zzawo.equals(com_google_android_gms_internal_measurement_zzgb.zzawo)) {
            return false;
        }
        if (this.zzavu == null) {
            if (com_google_android_gms_internal_measurement_zzgb.zzavu != null) {
                return false;
            }
        } else if (!this.zzavu.equals(com_google_android_gms_internal_measurement_zzgb.zzavu)) {
            return false;
        }
        if (this.zzavv == null) {
            if (com_google_android_gms_internal_measurement_zzgb.zzavv != null) {
                return false;
            }
        } else if (!this.zzavv.equals(com_google_android_gms_internal_measurement_zzgb.zzavv)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgb.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgb.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgb.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((((getClass().getName().hashCode() + 527) * 31) + (this.zzavx == null ? 0 : this.zzavx.hashCode())) * 31) + (this.zzawn == null ? 0 : this.zzawn.hashCode());
        zzfz com_google_android_gms_internal_measurement_zzfz = this.zzawo;
        hashCode *= 31;
        if (com_google_android_gms_internal_measurement_zzfz == null) {
            i = 0;
        } else {
            i = com_google_android_gms_internal_measurement_zzfz.hashCode();
        }
        hashCode = (((((hashCode + i) * 31) + (this.zzavu == null ? 0 : this.zzavu.hashCode())) * 31) + (this.zzavv == null ? 0 : this.zzavv.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i2 = this.zzcfx.hashCode();
            }
        }
        return hashCode + i2;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzavx != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(1, this.zzavx.intValue());
        }
        if (this.zzawn != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(2, this.zzawn);
        }
        if (this.zzawo != null) {
            com_google_android_gms_internal_measurement_zzzj.zza(3, this.zzawo);
        }
        if (this.zzavu != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(4, this.zzavu.booleanValue());
        }
        if (this.zzavv != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(5, this.zzavv.booleanValue());
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzavx != null) {
            zzf += zzzj.zzh(1, this.zzavx.intValue());
        }
        if (this.zzawn != null) {
            zzf += zzzj.zzc(2, this.zzawn);
        }
        if (this.zzawo != null) {
            zzf += zzzj.zzb(3, this.zzawo);
        }
        if (this.zzavu != null) {
            this.zzavu.booleanValue();
            zzf += zzzj.zzbc(4) + 1;
        }
        if (this.zzavv == null) {
            return zzf;
        }
        this.zzavv.booleanValue();
        return zzf + (zzzj.zzbc(5) + 1);
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            if (zzuq == 8) {
                this.zzavx = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
            } else if (zzuq == 18) {
                this.zzawn = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (zzuq == 26) {
                if (this.zzawo == null) {
                    this.zzawo = new zzfz();
                }
                com_google_android_gms_internal_measurement_zzzi.zza(this.zzawo);
            } else if (zzuq == 32) {
                this.zzavu = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
            } else if (zzuq == 40) {
                this.zzavv = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
