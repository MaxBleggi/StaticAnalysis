package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzfy extends zzzl<zzfy> {
    private static volatile zzfy[] zzavw;
    public Boolean zzavu;
    public Boolean zzavv;
    public Integer zzavx;
    public String zzavy;
    public zzfz[] zzavz;
    private Boolean zzawa;
    public zzga zzawb;

    public static zzfy[] zzms() {
        if (zzavw == null) {
            synchronized (zzzp.zzcgg) {
                if (zzavw == null) {
                    zzavw = new zzfy[0];
                }
            }
        }
        return zzavw;
    }

    public zzfy() {
        this.zzavx = null;
        this.zzavy = null;
        this.zzavz = zzfz.zzmt();
        this.zzawa = null;
        this.zzawb = null;
        this.zzavu = null;
        this.zzavv = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfy)) {
            return false;
        }
        zzfy com_google_android_gms_internal_measurement_zzfy = (zzfy) obj;
        if (this.zzavx == null) {
            if (com_google_android_gms_internal_measurement_zzfy.zzavx != null) {
                return false;
            }
        } else if (!this.zzavx.equals(com_google_android_gms_internal_measurement_zzfy.zzavx)) {
            return false;
        }
        if (this.zzavy == null) {
            if (com_google_android_gms_internal_measurement_zzfy.zzavy != null) {
                return false;
            }
        } else if (!this.zzavy.equals(com_google_android_gms_internal_measurement_zzfy.zzavy)) {
            return false;
        }
        if (!zzzp.equals(this.zzavz, com_google_android_gms_internal_measurement_zzfy.zzavz)) {
            return false;
        }
        if (this.zzawa == null) {
            if (com_google_android_gms_internal_measurement_zzfy.zzawa != null) {
                return false;
            }
        } else if (!this.zzawa.equals(com_google_android_gms_internal_measurement_zzfy.zzawa)) {
            return false;
        }
        if (this.zzawb == null) {
            if (com_google_android_gms_internal_measurement_zzfy.zzawb != null) {
                return false;
            }
        } else if (!this.zzawb.equals(com_google_android_gms_internal_measurement_zzfy.zzawb)) {
            return false;
        }
        if (this.zzavu == null) {
            if (com_google_android_gms_internal_measurement_zzfy.zzavu != null) {
                return false;
            }
        } else if (!this.zzavu.equals(com_google_android_gms_internal_measurement_zzfy.zzavu)) {
            return false;
        }
        if (this.zzavv == null) {
            if (com_google_android_gms_internal_measurement_zzfy.zzavv != null) {
                return false;
            }
        } else if (!this.zzavv.equals(com_google_android_gms_internal_measurement_zzfy.zzavv)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzfy.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzfy.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzfy.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzavx == null ? 0 : this.zzavx.hashCode())) * 31) + (this.zzavy == null ? 0 : this.zzavy.hashCode())) * 31) + zzzp.hashCode(this.zzavz)) * 31) + (this.zzawa == null ? 0 : this.zzawa.hashCode());
        zzga com_google_android_gms_internal_measurement_zzga = this.zzawb;
        hashCode *= 31;
        if (com_google_android_gms_internal_measurement_zzga == null) {
            i = 0;
        } else {
            i = com_google_android_gms_internal_measurement_zzga.hashCode();
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
        if (this.zzavy != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(2, this.zzavy);
        }
        if (this.zzavz != null && this.zzavz.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzavz) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(3, com_google_android_gms_internal_measurement_zzzr);
                }
            }
        }
        if (this.zzawa != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(4, this.zzawa.booleanValue());
        }
        if (this.zzawb != null) {
            com_google_android_gms_internal_measurement_zzzj.zza(5, this.zzawb);
        }
        if (this.zzavu != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(6, this.zzavu.booleanValue());
        }
        if (this.zzavv != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(7, this.zzavv.booleanValue());
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzavx != null) {
            zzf += zzzj.zzh(1, this.zzavx.intValue());
        }
        if (this.zzavy != null) {
            zzf += zzzj.zzc(2, this.zzavy);
        }
        if (this.zzavz != null && this.zzavz.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzavz) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    zzf += zzzj.zzb(3, com_google_android_gms_internal_measurement_zzzr);
                }
            }
        }
        if (this.zzawa != null) {
            this.zzawa.booleanValue();
            zzf += zzzj.zzbc(4) + 1;
        }
        if (this.zzawb != null) {
            zzf += zzzj.zzb(5, this.zzawb);
        }
        if (this.zzavu != null) {
            this.zzavu.booleanValue();
            zzf += zzzj.zzbc(6) + 1;
        }
        if (this.zzavv == null) {
            return zzf;
        }
        this.zzavv.booleanValue();
        return zzf + (zzzj.zzbc(7) + 1);
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
                this.zzavy = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (zzuq == 26) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 26);
                int length = this.zzavz == null ? 0 : this.zzavz.length;
                Object obj = new zzfz[(zzuq + length)];
                if (length != 0) {
                    System.arraycopy(this.zzavz, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzfz();
                    com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    length++;
                }
                obj[length] = new zzfz();
                com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                this.zzavz = obj;
            } else if (zzuq == 32) {
                this.zzawa = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
            } else if (zzuq == 42) {
                if (this.zzawb == null) {
                    this.zzawb = new zzga();
                }
                com_google_android_gms_internal_measurement_zzzi.zza(this.zzawb);
            } else if (zzuq == 48) {
                this.zzavu = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
            } else if (zzuq == 56) {
                this.zzavv = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
