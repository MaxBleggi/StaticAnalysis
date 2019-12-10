package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgi extends zzzl<zzgi> {
    private static volatile zzgi[] zzaxl;
    public Integer count;
    public String name;
    public zzgj[] zzaxm;
    public Long zzaxn;
    public Long zzaxo;

    public static zzgi[] zzmz() {
        if (zzaxl == null) {
            synchronized (zzzp.zzcgg) {
                if (zzaxl == null) {
                    zzaxl = new zzgi[0];
                }
            }
        }
        return zzaxl;
    }

    public zzgi() {
        this.zzaxm = zzgj.zzna();
        this.name = null;
        this.zzaxn = null;
        this.zzaxo = null;
        this.count = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgi)) {
            return false;
        }
        zzgi com_google_android_gms_internal_measurement_zzgi = (zzgi) obj;
        if (!zzzp.equals(this.zzaxm, com_google_android_gms_internal_measurement_zzgi.zzaxm)) {
            return false;
        }
        if (this.name == null) {
            if (com_google_android_gms_internal_measurement_zzgi.name != null) {
                return false;
            }
        } else if (!this.name.equals(com_google_android_gms_internal_measurement_zzgi.name)) {
            return false;
        }
        if (this.zzaxn == null) {
            if (com_google_android_gms_internal_measurement_zzgi.zzaxn != null) {
                return false;
            }
        } else if (!this.zzaxn.equals(com_google_android_gms_internal_measurement_zzgi.zzaxn)) {
            return false;
        }
        if (this.zzaxo == null) {
            if (com_google_android_gms_internal_measurement_zzgi.zzaxo != null) {
                return false;
            }
        } else if (!this.zzaxo.equals(com_google_android_gms_internal_measurement_zzgi.zzaxo)) {
            return false;
        }
        if (this.count == null) {
            if (com_google_android_gms_internal_measurement_zzgi.count != null) {
                return false;
            }
        } else if (!this.count.equals(com_google_android_gms_internal_measurement_zzgi.count)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgi.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgi.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgi.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + zzzp.hashCode(this.zzaxm)) * 31) + (this.name == null ? 0 : this.name.hashCode())) * 31) + (this.zzaxn == null ? 0 : this.zzaxn.hashCode())) * 31) + (this.zzaxo == null ? 0 : this.zzaxo.hashCode())) * 31) + (this.count == null ? 0 : this.count.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i = this.zzcfx.hashCode();
            }
        }
        return hashCode + i;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzaxm != null && this.zzaxm.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzaxm) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(1, com_google_android_gms_internal_measurement_zzzr);
                }
            }
        }
        if (this.name != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(2, this.name);
        }
        if (this.zzaxn != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(3, this.zzaxn.longValue());
        }
        if (this.zzaxo != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(4, this.zzaxo.longValue());
        }
        if (this.count != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(5, this.count.intValue());
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzaxm != null && this.zzaxm.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzaxm) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    zzf += zzzj.zzb(1, com_google_android_gms_internal_measurement_zzzr);
                }
            }
        }
        if (this.name != null) {
            zzf += zzzj.zzc(2, this.name);
        }
        if (this.zzaxn != null) {
            zzf += zzzj.zzd(3, this.zzaxn.longValue());
        }
        if (this.zzaxo != null) {
            zzf += zzzj.zzd(4, this.zzaxo.longValue());
        }
        return this.count != null ? zzf + zzzj.zzh(5, this.count.intValue()) : zzf;
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            if (zzuq == 10) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 10);
                int length = this.zzaxm == null ? 0 : this.zzaxm.length;
                Object obj = new zzgj[(zzuq + length)];
                if (length != 0) {
                    System.arraycopy(this.zzaxm, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzgj();
                    com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    length++;
                }
                obj[length] = new zzgj();
                com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                this.zzaxm = obj;
            } else if (zzuq == 18) {
                this.name = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (zzuq == 24) {
                this.zzaxn = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
            } else if (zzuq == 32) {
                this.zzaxo = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
            } else if (zzuq == 40) {
                this.count = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
