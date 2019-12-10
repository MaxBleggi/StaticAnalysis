package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgn extends zzzl<zzgn> {
    private static volatile zzgn[] zzazd;
    public Integer zzaxj;
    public long[] zzaze;

    public static zzgn[] zznc() {
        if (zzazd == null) {
            synchronized (zzzp.zzcgg) {
                if (zzazd == null) {
                    zzazd = new zzgn[0];
                }
            }
        }
        return zzazd;
    }

    public zzgn() {
        this.zzaxj = null;
        this.zzaze = zzzu.zzcgm;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgn)) {
            return false;
        }
        zzgn com_google_android_gms_internal_measurement_zzgn = (zzgn) obj;
        if (this.zzaxj == null) {
            if (com_google_android_gms_internal_measurement_zzgn.zzaxj != null) {
                return false;
            }
        } else if (!this.zzaxj.equals(com_google_android_gms_internal_measurement_zzgn.zzaxj)) {
            return false;
        }
        if (!zzzp.equals(this.zzaze, com_google_android_gms_internal_measurement_zzgn.zzaze)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgn.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgn.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgn.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + (this.zzaxj == null ? 0 : this.zzaxj.hashCode())) * 31) + zzzp.hashCode(this.zzaze)) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i = this.zzcfx.hashCode();
            }
        }
        return hashCode + i;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzaxj != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(1, this.zzaxj.intValue());
        }
        if (this.zzaze != null && this.zzaze.length > 0) {
            for (long zzi : this.zzaze) {
                com_google_android_gms_internal_measurement_zzzj.zzi(2, zzi);
            }
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzaxj != null) {
            zzf += zzzj.zzh(1, this.zzaxj.intValue());
        }
        if (this.zzaze == null || this.zzaze.length <= 0) {
            return zzf;
        }
        int i = 0;
        for (long zzbl : this.zzaze) {
            i += zzzj.zzbl(zzbl);
        }
        return (zzf + i) + (this.zzaze.length * 1);
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            if (zzuq == 8) {
                this.zzaxj = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
            } else if (zzuq == 16) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 16);
                r1 = this.zzaze == null ? 0 : this.zzaze.length;
                Object obj = new long[(zzuq + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzaze, 0, obj, 0, r1);
                }
                while (r1 < obj.length - 1) {
                    obj[r1] = com_google_android_gms_internal_measurement_zzzi.zzvj();
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    r1++;
                }
                obj[r1] = com_google_android_gms_internal_measurement_zzzi.zzvj();
                this.zzaze = obj;
            } else if (zzuq == 18) {
                zzuq = com_google_android_gms_internal_measurement_zzzi.zzar(com_google_android_gms_internal_measurement_zzzi.zzvi());
                r1 = com_google_android_gms_internal_measurement_zzzi.getPosition();
                int i = 0;
                while (com_google_android_gms_internal_measurement_zzzi.zzzf() > 0) {
                    com_google_android_gms_internal_measurement_zzzi.zzvj();
                    i++;
                }
                com_google_android_gms_internal_measurement_zzzi.zzca(r1);
                r1 = this.zzaze == null ? 0 : this.zzaze.length;
                Object obj2 = new long[(i + r1)];
                if (r1 != 0) {
                    System.arraycopy(this.zzaze, 0, obj2, 0, r1);
                }
                while (r1 < obj2.length) {
                    obj2[r1] = com_google_android_gms_internal_measurement_zzzi.zzvj();
                    r1++;
                }
                this.zzaze = obj2;
                com_google_android_gms_internal_measurement_zzzi.zzas(zzuq);
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
