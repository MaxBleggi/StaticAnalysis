package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgo extends zzzl<zzgo> {
    private static volatile zzgo[] zzazf;
    public String name;
    public String zzamw;
    private Float zzauz;
    public Double zzava;
    public Long zzaxq;
    public Long zzazg;

    public static zzgo[] zznd() {
        if (zzazf == null) {
            synchronized (zzzp.zzcgg) {
                if (zzazf == null) {
                    zzazf = new zzgo[0];
                }
            }
        }
        return zzazf;
    }

    public zzgo() {
        this.zzazg = null;
        this.name = null;
        this.zzamw = null;
        this.zzaxq = null;
        this.zzauz = null;
        this.zzava = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgo)) {
            return false;
        }
        zzgo com_google_android_gms_internal_measurement_zzgo = (zzgo) obj;
        if (this.zzazg == null) {
            if (com_google_android_gms_internal_measurement_zzgo.zzazg != null) {
                return false;
            }
        } else if (!this.zzazg.equals(com_google_android_gms_internal_measurement_zzgo.zzazg)) {
            return false;
        }
        if (this.name == null) {
            if (com_google_android_gms_internal_measurement_zzgo.name != null) {
                return false;
            }
        } else if (!this.name.equals(com_google_android_gms_internal_measurement_zzgo.name)) {
            return false;
        }
        if (this.zzamw == null) {
            if (com_google_android_gms_internal_measurement_zzgo.zzamw != null) {
                return false;
            }
        } else if (!this.zzamw.equals(com_google_android_gms_internal_measurement_zzgo.zzamw)) {
            return false;
        }
        if (this.zzaxq == null) {
            if (com_google_android_gms_internal_measurement_zzgo.zzaxq != null) {
                return false;
            }
        } else if (!this.zzaxq.equals(com_google_android_gms_internal_measurement_zzgo.zzaxq)) {
            return false;
        }
        if (this.zzauz == null) {
            if (com_google_android_gms_internal_measurement_zzgo.zzauz != null) {
                return false;
            }
        } else if (!this.zzauz.equals(com_google_android_gms_internal_measurement_zzgo.zzauz)) {
            return false;
        }
        if (this.zzava == null) {
            if (com_google_android_gms_internal_measurement_zzgo.zzava != null) {
                return false;
            }
        } else if (!this.zzava.equals(com_google_android_gms_internal_measurement_zzgo.zzava)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgo.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgo.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgo.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzazg == null ? 0 : this.zzazg.hashCode())) * 31) + (this.name == null ? 0 : this.name.hashCode())) * 31) + (this.zzamw == null ? 0 : this.zzamw.hashCode())) * 31) + (this.zzaxq == null ? 0 : this.zzaxq.hashCode())) * 31) + (this.zzauz == null ? 0 : this.zzauz.hashCode())) * 31) + (this.zzava == null ? 0 : this.zzava.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i = this.zzcfx.hashCode();
            }
        }
        return hashCode + i;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzazg != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(1, this.zzazg.longValue());
        }
        if (this.name != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(2, this.name);
        }
        if (this.zzamw != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(3, this.zzamw);
        }
        if (this.zzaxq != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(4, this.zzaxq.longValue());
        }
        if (this.zzauz != null) {
            com_google_android_gms_internal_measurement_zzzj.zza(5, this.zzauz.floatValue());
        }
        if (this.zzava != null) {
            com_google_android_gms_internal_measurement_zzzj.zza(6, this.zzava.doubleValue());
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzazg != null) {
            zzf += zzzj.zzd(1, this.zzazg.longValue());
        }
        if (this.name != null) {
            zzf += zzzj.zzc(2, this.name);
        }
        if (this.zzamw != null) {
            zzf += zzzj.zzc(3, this.zzamw);
        }
        if (this.zzaxq != null) {
            zzf += zzzj.zzd(4, this.zzaxq.longValue());
        }
        if (this.zzauz != null) {
            this.zzauz.floatValue();
            zzf += zzzj.zzbc(5) + 4;
        }
        if (this.zzava == null) {
            return zzf;
        }
        this.zzava.doubleValue();
        return zzf + (zzzj.zzbc(6) + 8);
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            if (zzuq == 8) {
                this.zzazg = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
            } else if (zzuq == 18) {
                this.name = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (zzuq == 26) {
                this.zzamw = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (zzuq == 32) {
                this.zzaxq = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
            } else if (zzuq == 45) {
                this.zzauz = Float.valueOf(Float.intBitsToFloat(com_google_android_gms_internal_measurement_zzzi.zzvk()));
            } else if (zzuq == 49) {
                this.zzava = Double.valueOf(Double.longBitsToDouble(com_google_android_gms_internal_measurement_zzzi.zzvl()));
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
