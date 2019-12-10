package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzfx extends zzzl<zzfx> {
    private static volatile zzfx[] zzavq;
    public Integer zzavr;
    public zzgb[] zzavs;
    public zzfy[] zzavt;
    private Boolean zzavu;
    private Boolean zzavv;

    public static zzfx[] zzmr() {
        if (zzavq == null) {
            synchronized (zzzp.zzcgg) {
                if (zzavq == null) {
                    zzavq = new zzfx[0];
                }
            }
        }
        return zzavq;
    }

    public zzfx() {
        this.zzavr = null;
        this.zzavs = zzgb.zzmu();
        this.zzavt = zzfy.zzms();
        this.zzavu = null;
        this.zzavv = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfx)) {
            return false;
        }
        zzfx com_google_android_gms_internal_measurement_zzfx = (zzfx) obj;
        if (this.zzavr == null) {
            if (com_google_android_gms_internal_measurement_zzfx.zzavr != null) {
                return false;
            }
        } else if (!this.zzavr.equals(com_google_android_gms_internal_measurement_zzfx.zzavr)) {
            return false;
        }
        if (!zzzp.equals(this.zzavs, com_google_android_gms_internal_measurement_zzfx.zzavs) || !zzzp.equals(this.zzavt, com_google_android_gms_internal_measurement_zzfx.zzavt)) {
            return false;
        }
        if (this.zzavu == null) {
            if (com_google_android_gms_internal_measurement_zzfx.zzavu != null) {
                return false;
            }
        } else if (!this.zzavu.equals(com_google_android_gms_internal_measurement_zzfx.zzavu)) {
            return false;
        }
        if (this.zzavv == null) {
            if (com_google_android_gms_internal_measurement_zzfx.zzavv != null) {
                return false;
            }
        } else if (!this.zzavv.equals(com_google_android_gms_internal_measurement_zzfx.zzavv)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzfx.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzfx.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzfx.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzavr == null ? 0 : this.zzavr.hashCode())) * 31) + zzzp.hashCode(this.zzavs)) * 31) + zzzp.hashCode(this.zzavt)) * 31) + (this.zzavu == null ? 0 : this.zzavu.hashCode())) * 31) + (this.zzavv == null ? 0 : this.zzavv.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i = this.zzcfx.hashCode();
            }
        }
        return hashCode + i;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzavr != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(1, this.zzavr.intValue());
        }
        if (this.zzavs != null && this.zzavs.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzavs) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(2, com_google_android_gms_internal_measurement_zzzr);
                }
            }
        }
        if (this.zzavt != null && this.zzavt.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr2 : this.zzavt) {
                if (com_google_android_gms_internal_measurement_zzzr2 != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(3, com_google_android_gms_internal_measurement_zzzr2);
                }
            }
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
        if (this.zzavr != null) {
            zzf += zzzj.zzh(1, this.zzavr.intValue());
        }
        if (this.zzavs != null && this.zzavs.length > 0) {
            int i = zzf;
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzavs) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    i += zzzj.zzb(2, com_google_android_gms_internal_measurement_zzzr);
                }
            }
            zzf = i;
        }
        if (this.zzavt != null && this.zzavt.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr2 : this.zzavt) {
                if (com_google_android_gms_internal_measurement_zzzr2 != null) {
                    zzf += zzzj.zzb(3, com_google_android_gms_internal_measurement_zzzr2);
                }
            }
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
                this.zzavr = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
            } else if (zzuq == 18) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 18);
                r1 = this.zzavs == null ? 0 : this.zzavs.length;
                r0 = new zzgb[(zzuq + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzavs, 0, r0, 0, r1);
                }
                while (r1 < r0.length - 1) {
                    r0[r1] = new zzgb();
                    com_google_android_gms_internal_measurement_zzzi.zza(r0[r1]);
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    r1++;
                }
                r0[r1] = new zzgb();
                com_google_android_gms_internal_measurement_zzzi.zza(r0[r1]);
                this.zzavs = r0;
            } else if (zzuq == 26) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 26);
                r1 = this.zzavt == null ? 0 : this.zzavt.length;
                r0 = new zzfy[(zzuq + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzavt, 0, r0, 0, r1);
                }
                while (r1 < r0.length - 1) {
                    r0[r1] = new zzfy();
                    com_google_android_gms_internal_measurement_zzzi.zza(r0[r1]);
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    r1++;
                }
                r0[r1] = new zzfy();
                com_google_android_gms_internal_measurement_zzzi.zza(r0[r1]);
                this.zzavt = r0;
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
