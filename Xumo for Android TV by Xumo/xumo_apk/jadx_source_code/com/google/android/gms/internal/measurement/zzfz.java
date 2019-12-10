package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzfz extends zzzl<zzfz> {
    private static volatile zzfz[] zzawc;
    public zzgc zzawd;
    public zzga zzawe;
    public Boolean zzawf;
    public String zzawg;

    public static zzfz[] zzmt() {
        if (zzawc == null) {
            synchronized (zzzp.zzcgg) {
                if (zzawc == null) {
                    zzawc = new zzfz[0];
                }
            }
        }
        return zzawc;
    }

    public zzfz() {
        this.zzawd = null;
        this.zzawe = null;
        this.zzawf = null;
        this.zzawg = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfz)) {
            return false;
        }
        zzfz com_google_android_gms_internal_measurement_zzfz = (zzfz) obj;
        if (this.zzawd == null) {
            if (com_google_android_gms_internal_measurement_zzfz.zzawd != null) {
                return false;
            }
        } else if (!this.zzawd.equals(com_google_android_gms_internal_measurement_zzfz.zzawd)) {
            return false;
        }
        if (this.zzawe == null) {
            if (com_google_android_gms_internal_measurement_zzfz.zzawe != null) {
                return false;
            }
        } else if (!this.zzawe.equals(com_google_android_gms_internal_measurement_zzfz.zzawe)) {
            return false;
        }
        if (this.zzawf == null) {
            if (com_google_android_gms_internal_measurement_zzfz.zzawf != null) {
                return false;
            }
        } else if (!this.zzawf.equals(com_google_android_gms_internal_measurement_zzfz.zzawf)) {
            return false;
        }
        if (this.zzawg == null) {
            if (com_google_android_gms_internal_measurement_zzfz.zzawg != null) {
                return false;
            }
        } else if (!this.zzawg.equals(com_google_android_gms_internal_measurement_zzfz.zzawg)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzfz.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzfz.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzfz.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int hashCode = getClass().getName().hashCode() + 527;
        zzgc com_google_android_gms_internal_measurement_zzgc = this.zzawd;
        hashCode *= 31;
        int i2 = 0;
        if (com_google_android_gms_internal_measurement_zzgc == null) {
            i = 0;
        } else {
            i = com_google_android_gms_internal_measurement_zzgc.hashCode();
        }
        hashCode += i;
        zzga com_google_android_gms_internal_measurement_zzga = this.zzawe;
        hashCode *= 31;
        if (com_google_android_gms_internal_measurement_zzga == null) {
            i = 0;
        } else {
            i = com_google_android_gms_internal_measurement_zzga.hashCode();
        }
        hashCode = (((((hashCode + i) * 31) + (this.zzawf == null ? 0 : this.zzawf.hashCode())) * 31) + (this.zzawg == null ? 0 : this.zzawg.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i2 = this.zzcfx.hashCode();
            }
        }
        return hashCode + i2;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzawd != null) {
            com_google_android_gms_internal_measurement_zzzj.zza(1, this.zzawd);
        }
        if (this.zzawe != null) {
            com_google_android_gms_internal_measurement_zzzj.zza(2, this.zzawe);
        }
        if (this.zzawf != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(3, this.zzawf.booleanValue());
        }
        if (this.zzawg != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(4, this.zzawg);
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzawd != null) {
            zzf += zzzj.zzb(1, this.zzawd);
        }
        if (this.zzawe != null) {
            zzf += zzzj.zzb(2, this.zzawe);
        }
        if (this.zzawf != null) {
            this.zzawf.booleanValue();
            zzf += zzzj.zzbc(3) + 1;
        }
        return this.zzawg != null ? zzf + zzzj.zzc(4, this.zzawg) : zzf;
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            if (zzuq == 10) {
                if (this.zzawd == null) {
                    this.zzawd = new zzgc();
                }
                com_google_android_gms_internal_measurement_zzzi.zza(this.zzawd);
            } else if (zzuq == 18) {
                if (this.zzawe == null) {
                    this.zzawe = new zzga();
                }
                com_google_android_gms_internal_measurement_zzzi.zza(this.zzawe);
            } else if (zzuq == 24) {
                this.zzawf = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
            } else if (zzuq == 34) {
                this.zzawg = com_google_android_gms_internal_measurement_zzzi.readString();
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
