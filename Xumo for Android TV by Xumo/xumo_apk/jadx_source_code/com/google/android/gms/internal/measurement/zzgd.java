package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgd extends zzzl<zzgd> {
    private static volatile zzgd[] zzawt;
    public String name;
    public Boolean zzawu;
    public Boolean zzawv;
    public Integer zzaww;

    public static zzgd[] zzmv() {
        if (zzawt == null) {
            synchronized (zzzp.zzcgg) {
                if (zzawt == null) {
                    zzawt = new zzgd[0];
                }
            }
        }
        return zzawt;
    }

    public zzgd() {
        this.name = null;
        this.zzawu = null;
        this.zzawv = null;
        this.zzaww = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgd)) {
            return false;
        }
        zzgd com_google_android_gms_internal_measurement_zzgd = (zzgd) obj;
        if (this.name == null) {
            if (com_google_android_gms_internal_measurement_zzgd.name != null) {
                return false;
            }
        } else if (!this.name.equals(com_google_android_gms_internal_measurement_zzgd.name)) {
            return false;
        }
        if (this.zzawu == null) {
            if (com_google_android_gms_internal_measurement_zzgd.zzawu != null) {
                return false;
            }
        } else if (!this.zzawu.equals(com_google_android_gms_internal_measurement_zzgd.zzawu)) {
            return false;
        }
        if (this.zzawv == null) {
            if (com_google_android_gms_internal_measurement_zzgd.zzawv != null) {
                return false;
            }
        } else if (!this.zzawv.equals(com_google_android_gms_internal_measurement_zzgd.zzawv)) {
            return false;
        }
        if (this.zzaww == null) {
            if (com_google_android_gms_internal_measurement_zzgd.zzaww != null) {
                return false;
            }
        } else if (!this.zzaww.equals(com_google_android_gms_internal_measurement_zzgd.zzaww)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgd.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgd.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgd.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((getClass().getName().hashCode() + 527) * 31) + (this.name == null ? 0 : this.name.hashCode())) * 31) + (this.zzawu == null ? 0 : this.zzawu.hashCode())) * 31) + (this.zzawv == null ? 0 : this.zzawv.hashCode())) * 31) + (this.zzaww == null ? 0 : this.zzaww.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i = this.zzcfx.hashCode();
            }
        }
        return hashCode + i;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.name != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(1, this.name);
        }
        if (this.zzawu != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(2, this.zzawu.booleanValue());
        }
        if (this.zzawv != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(3, this.zzawv.booleanValue());
        }
        if (this.zzaww != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(4, this.zzaww.intValue());
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.name != null) {
            zzf += zzzj.zzc(1, this.name);
        }
        if (this.zzawu != null) {
            this.zzawu.booleanValue();
            zzf += zzzj.zzbc(2) + 1;
        }
        if (this.zzawv != null) {
            this.zzawv.booleanValue();
            zzf += zzzj.zzbc(3) + 1;
        }
        return this.zzaww != null ? zzf + zzzj.zzh(4, this.zzaww.intValue()) : zzf;
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            if (zzuq == 10) {
                this.name = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (zzuq == 16) {
                this.zzawu = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
            } else if (zzuq == 24) {
                this.zzawv = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
            } else if (zzuq == 32) {
                this.zzaww = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
