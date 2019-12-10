package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgg extends zzzl<zzgg> {
    private static volatile zzgg[] zzaxe;
    public Integer zzavr;
    public zzgm zzaxf;
    public zzgm zzaxg;
    public Boolean zzaxh;

    public static zzgg[] zzmx() {
        if (zzaxe == null) {
            synchronized (zzzp.zzcgg) {
                if (zzaxe == null) {
                    zzaxe = new zzgg[0];
                }
            }
        }
        return zzaxe;
    }

    public zzgg() {
        this.zzavr = null;
        this.zzaxf = null;
        this.zzaxg = null;
        this.zzaxh = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgg)) {
            return false;
        }
        zzgg com_google_android_gms_internal_measurement_zzgg = (zzgg) obj;
        if (this.zzavr == null) {
            if (com_google_android_gms_internal_measurement_zzgg.zzavr != null) {
                return false;
            }
        } else if (!this.zzavr.equals(com_google_android_gms_internal_measurement_zzgg.zzavr)) {
            return false;
        }
        if (this.zzaxf == null) {
            if (com_google_android_gms_internal_measurement_zzgg.zzaxf != null) {
                return false;
            }
        } else if (!this.zzaxf.equals(com_google_android_gms_internal_measurement_zzgg.zzaxf)) {
            return false;
        }
        if (this.zzaxg == null) {
            if (com_google_android_gms_internal_measurement_zzgg.zzaxg != null) {
                return false;
            }
        } else if (!this.zzaxg.equals(com_google_android_gms_internal_measurement_zzgg.zzaxg)) {
            return false;
        }
        if (this.zzaxh == null) {
            if (com_google_android_gms_internal_measurement_zzgg.zzaxh != null) {
                return false;
            }
        } else if (!this.zzaxh.equals(com_google_android_gms_internal_measurement_zzgg.zzaxh)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgg.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgg.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgg.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((getClass().getName().hashCode() + 527) * 31) + (this.zzavr == null ? 0 : this.zzavr.hashCode());
        zzgm com_google_android_gms_internal_measurement_zzgm = this.zzaxf;
        hashCode *= 31;
        if (com_google_android_gms_internal_measurement_zzgm == null) {
            i = 0;
        } else {
            i = com_google_android_gms_internal_measurement_zzgm.hashCode();
        }
        hashCode += i;
        com_google_android_gms_internal_measurement_zzgm = this.zzaxg;
        hashCode *= 31;
        if (com_google_android_gms_internal_measurement_zzgm == null) {
            i = 0;
        } else {
            i = com_google_android_gms_internal_measurement_zzgm.hashCode();
        }
        hashCode = (((hashCode + i) * 31) + (this.zzaxh == null ? 0 : this.zzaxh.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i2 = this.zzcfx.hashCode();
            }
        }
        return hashCode + i2;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzavr != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(1, this.zzavr.intValue());
        }
        if (this.zzaxf != null) {
            com_google_android_gms_internal_measurement_zzzj.zza(2, this.zzaxf);
        }
        if (this.zzaxg != null) {
            com_google_android_gms_internal_measurement_zzzj.zza(3, this.zzaxg);
        }
        if (this.zzaxh != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(4, this.zzaxh.booleanValue());
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzavr != null) {
            zzf += zzzj.zzh(1, this.zzavr.intValue());
        }
        if (this.zzaxf != null) {
            zzf += zzzj.zzb(2, this.zzaxf);
        }
        if (this.zzaxg != null) {
            zzf += zzzj.zzb(3, this.zzaxg);
        }
        if (this.zzaxh == null) {
            return zzf;
        }
        this.zzaxh.booleanValue();
        return zzf + (zzzj.zzbc(4) + 1);
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            if (zzuq == 8) {
                this.zzavr = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
            } else if (zzuq == 18) {
                if (this.zzaxf == null) {
                    this.zzaxf = new zzgm();
                }
                com_google_android_gms_internal_measurement_zzzi.zza(this.zzaxf);
            } else if (zzuq == 26) {
                if (this.zzaxg == null) {
                    this.zzaxg = new zzgm();
                }
                com_google_android_gms_internal_measurement_zzzi.zza(this.zzaxg);
            } else if (zzuq == 32) {
                this.zzaxh = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
