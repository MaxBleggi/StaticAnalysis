package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzge extends zzzl<zzge> {
    public String zzafx;
    public Long zzawx;
    private Integer zzawy;
    public zzgf[] zzawz;
    public zzgd[] zzaxa;
    public zzfx[] zzaxb;
    private String zzaxc;

    public zzge() {
        this.zzawx = null;
        this.zzafx = null;
        this.zzawy = null;
        this.zzawz = zzgf.zzmw();
        this.zzaxa = zzgd.zzmv();
        this.zzaxb = zzfx.zzmr();
        this.zzaxc = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzge)) {
            return false;
        }
        zzge com_google_android_gms_internal_measurement_zzge = (zzge) obj;
        if (this.zzawx == null) {
            if (com_google_android_gms_internal_measurement_zzge.zzawx != null) {
                return false;
            }
        } else if (!this.zzawx.equals(com_google_android_gms_internal_measurement_zzge.zzawx)) {
            return false;
        }
        if (this.zzafx == null) {
            if (com_google_android_gms_internal_measurement_zzge.zzafx != null) {
                return false;
            }
        } else if (!this.zzafx.equals(com_google_android_gms_internal_measurement_zzge.zzafx)) {
            return false;
        }
        if (this.zzawy == null) {
            if (com_google_android_gms_internal_measurement_zzge.zzawy != null) {
                return false;
            }
        } else if (!this.zzawy.equals(com_google_android_gms_internal_measurement_zzge.zzawy)) {
            return false;
        }
        if (!zzzp.equals(this.zzawz, com_google_android_gms_internal_measurement_zzge.zzawz) || !zzzp.equals(this.zzaxa, com_google_android_gms_internal_measurement_zzge.zzaxa) || !zzzp.equals(this.zzaxb, com_google_android_gms_internal_measurement_zzge.zzaxb)) {
            return false;
        }
        if (this.zzaxc == null) {
            if (com_google_android_gms_internal_measurement_zzge.zzaxc != null) {
                return false;
            }
        } else if (!this.zzaxc.equals(com_google_android_gms_internal_measurement_zzge.zzaxc)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzge.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzge.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzge.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzawx == null ? 0 : this.zzawx.hashCode())) * 31) + (this.zzafx == null ? 0 : this.zzafx.hashCode())) * 31) + (this.zzawy == null ? 0 : this.zzawy.hashCode())) * 31) + zzzp.hashCode(this.zzawz)) * 31) + zzzp.hashCode(this.zzaxa)) * 31) + zzzp.hashCode(this.zzaxb)) * 31) + (this.zzaxc == null ? 0 : this.zzaxc.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i = this.zzcfx.hashCode();
            }
        }
        return hashCode + i;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzawx != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(1, this.zzawx.longValue());
        }
        if (this.zzafx != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(2, this.zzafx);
        }
        if (this.zzawy != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(3, this.zzawy.intValue());
        }
        if (this.zzawz != null && this.zzawz.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzawz) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(4, com_google_android_gms_internal_measurement_zzzr);
                }
            }
        }
        if (this.zzaxa != null && this.zzaxa.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr2 : this.zzaxa) {
                if (com_google_android_gms_internal_measurement_zzzr2 != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(5, com_google_android_gms_internal_measurement_zzzr2);
                }
            }
        }
        if (this.zzaxb != null && this.zzaxb.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr3 : this.zzaxb) {
                if (com_google_android_gms_internal_measurement_zzzr3 != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(6, com_google_android_gms_internal_measurement_zzzr3);
                }
            }
        }
        if (this.zzaxc != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(7, this.zzaxc);
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int i;
        int zzf = super.zzf();
        if (this.zzawx != null) {
            zzf += zzzj.zzd(1, this.zzawx.longValue());
        }
        if (this.zzafx != null) {
            zzf += zzzj.zzc(2, this.zzafx);
        }
        if (this.zzawy != null) {
            zzf += zzzj.zzh(3, this.zzawy.intValue());
        }
        if (this.zzawz != null && this.zzawz.length > 0) {
            i = zzf;
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzawz) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    i += zzzj.zzb(4, com_google_android_gms_internal_measurement_zzzr);
                }
            }
            zzf = i;
        }
        if (this.zzaxa != null && this.zzaxa.length > 0) {
            i = zzf;
            for (zzzr com_google_android_gms_internal_measurement_zzzr2 : this.zzaxa) {
                if (com_google_android_gms_internal_measurement_zzzr2 != null) {
                    i += zzzj.zzb(5, com_google_android_gms_internal_measurement_zzzr2);
                }
            }
            zzf = i;
        }
        if (this.zzaxb != null && this.zzaxb.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr3 : this.zzaxb) {
                if (com_google_android_gms_internal_measurement_zzzr3 != null) {
                    zzf += zzzj.zzb(6, com_google_android_gms_internal_measurement_zzzr3);
                }
            }
        }
        return this.zzaxc != null ? zzf + zzzj.zzc(7, this.zzaxc) : zzf;
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            if (zzuq == 8) {
                this.zzawx = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
            } else if (zzuq == 18) {
                this.zzafx = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (zzuq == 24) {
                this.zzawy = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
            } else if (zzuq == 34) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 34);
                r1 = this.zzawz == null ? 0 : this.zzawz.length;
                r0 = new zzgf[(zzuq + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzawz, 0, r0, 0, r1);
                }
                while (r1 < r0.length - 1) {
                    r0[r1] = new zzgf();
                    com_google_android_gms_internal_measurement_zzzi.zza(r0[r1]);
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    r1++;
                }
                r0[r1] = new zzgf();
                com_google_android_gms_internal_measurement_zzzi.zza(r0[r1]);
                this.zzawz = r0;
            } else if (zzuq == 42) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 42);
                r1 = this.zzaxa == null ? 0 : this.zzaxa.length;
                r0 = new zzgd[(zzuq + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzaxa, 0, r0, 0, r1);
                }
                while (r1 < r0.length - 1) {
                    r0[r1] = new zzgd();
                    com_google_android_gms_internal_measurement_zzzi.zza(r0[r1]);
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    r1++;
                }
                r0[r1] = new zzgd();
                com_google_android_gms_internal_measurement_zzzi.zza(r0[r1]);
                this.zzaxa = r0;
            } else if (zzuq == 50) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 50);
                r1 = this.zzaxb == null ? 0 : this.zzaxb.length;
                r0 = new zzfx[(zzuq + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzaxb, 0, r0, 0, r1);
                }
                while (r1 < r0.length - 1) {
                    r0[r1] = new zzfx();
                    com_google_android_gms_internal_measurement_zzzi.zza(r0[r1]);
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    r1++;
                }
                r0[r1] = new zzfx();
                com_google_android_gms_internal_measurement_zzzi.zza(r0[r1]);
                this.zzaxb = r0;
            } else if (zzuq == 58) {
                this.zzaxc = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
