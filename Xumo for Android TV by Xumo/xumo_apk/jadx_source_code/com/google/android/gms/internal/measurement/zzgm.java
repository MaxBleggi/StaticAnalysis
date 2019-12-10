package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgm extends zzzl<zzgm> {
    public long[] zzayz;
    public long[] zzaza;
    public zzgh[] zzazb;
    public zzgn[] zzazc;

    public zzgm() {
        this.zzayz = zzzu.zzcgm;
        this.zzaza = zzzu.zzcgm;
        this.zzazb = zzgh.zzmy();
        this.zzazc = zzgn.zznc();
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgm)) {
            return false;
        }
        zzgm com_google_android_gms_internal_measurement_zzgm = (zzgm) obj;
        if (!zzzp.equals(this.zzayz, com_google_android_gms_internal_measurement_zzgm.zzayz) || !zzzp.equals(this.zzaza, com_google_android_gms_internal_measurement_zzgm.zzaza) || !zzzp.equals(this.zzazb, com_google_android_gms_internal_measurement_zzgm.zzazb) || !zzzp.equals(this.zzazc, com_google_android_gms_internal_measurement_zzgm.zzazc)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgm.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgm.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgm.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2 = (((((((((getClass().getName().hashCode() + 527) * 31) + zzzp.hashCode(this.zzayz)) * 31) + zzzp.hashCode(this.zzaza)) * 31) + zzzp.hashCode(this.zzazb)) * 31) + zzzp.hashCode(this.zzazc)) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                hashCode = this.zzcfx.hashCode();
                return hashCode2 + hashCode;
            }
        }
        hashCode = 0;
        return hashCode2 + hashCode;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzayz != null && this.zzayz.length > 0) {
            for (long zza : this.zzayz) {
                com_google_android_gms_internal_measurement_zzzj.zza(1, zza);
            }
        }
        if (this.zzaza != null && this.zzaza.length > 0) {
            for (long zza2 : this.zzaza) {
                com_google_android_gms_internal_measurement_zzzj.zza(2, zza2);
            }
        }
        if (this.zzazb != null && this.zzazb.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzazb) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(3, com_google_android_gms_internal_measurement_zzzr);
                }
            }
        }
        if (this.zzazc != null && this.zzazc.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr2 : this.zzazc) {
                if (com_google_android_gms_internal_measurement_zzzr2 != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(4, com_google_android_gms_internal_measurement_zzzr2);
                }
            }
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int i;
        int i2;
        int zzf = super.zzf();
        if (this.zzayz != null && this.zzayz.length > 0) {
            i = 0;
            for (long zzbl : this.zzayz) {
                i += zzzj.zzbl(zzbl);
            }
            zzf = (zzf + i) + (this.zzayz.length * 1);
        }
        if (this.zzaza != null && this.zzaza.length > 0) {
            i = 0;
            for (long zzbl2 : this.zzaza) {
                i += zzzj.zzbl(zzbl2);
            }
            zzf = (zzf + i) + (this.zzaza.length * 1);
        }
        if (this.zzazb != null && this.zzazb.length > 0) {
            i2 = zzf;
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzazb) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    i2 += zzzj.zzb(3, com_google_android_gms_internal_measurement_zzzr);
                }
            }
            zzf = i2;
        }
        if (this.zzazc != null && this.zzazc.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr2 : this.zzazc) {
                if (com_google_android_gms_internal_measurement_zzzr2 != null) {
                    zzf += zzzj.zzb(4, com_google_android_gms_internal_measurement_zzzr2);
                }
            }
        }
        return zzf;
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            if (zzuq == 0) {
                return this;
            }
            int length;
            Object obj;
            if (zzuq == 8) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 8);
                length = this.zzayz == null ? 0 : this.zzayz.length;
                obj = new long[(zzuq + length)];
                if (length != 0) {
                    System.arraycopy(this.zzayz, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = com_google_android_gms_internal_measurement_zzzi.zzvj();
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    length++;
                }
                obj[length] = com_google_android_gms_internal_measurement_zzzi.zzvj();
                this.zzayz = obj;
            } else if (zzuq == 10) {
                zzuq = com_google_android_gms_internal_measurement_zzzi.zzar(com_google_android_gms_internal_measurement_zzzi.zzvi());
                length = com_google_android_gms_internal_measurement_zzzi.getPosition();
                r3 = 0;
                while (com_google_android_gms_internal_measurement_zzzi.zzzf() > 0) {
                    com_google_android_gms_internal_measurement_zzzi.zzvj();
                    r3++;
                }
                com_google_android_gms_internal_measurement_zzzi.zzca(length);
                length = this.zzayz == null ? 0 : this.zzayz.length;
                r3 = new long[(r3 + length)];
                if (length != 0) {
                    System.arraycopy(this.zzayz, 0, r3, 0, length);
                }
                while (length < r3.length) {
                    r3[length] = com_google_android_gms_internal_measurement_zzzi.zzvj();
                    length++;
                }
                this.zzayz = r3;
                com_google_android_gms_internal_measurement_zzzi.zzas(zzuq);
            } else if (zzuq == 16) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 16);
                length = this.zzaza == null ? 0 : this.zzaza.length;
                obj = new long[(zzuq + length)];
                if (length != 0) {
                    System.arraycopy(this.zzaza, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = com_google_android_gms_internal_measurement_zzzi.zzvj();
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    length++;
                }
                obj[length] = com_google_android_gms_internal_measurement_zzzi.zzvj();
                this.zzaza = obj;
            } else if (zzuq == 18) {
                zzuq = com_google_android_gms_internal_measurement_zzzi.zzar(com_google_android_gms_internal_measurement_zzzi.zzvi());
                length = com_google_android_gms_internal_measurement_zzzi.getPosition();
                r3 = 0;
                while (com_google_android_gms_internal_measurement_zzzi.zzzf() > 0) {
                    com_google_android_gms_internal_measurement_zzzi.zzvj();
                    r3++;
                }
                com_google_android_gms_internal_measurement_zzzi.zzca(length);
                length = this.zzaza == null ? 0 : this.zzaza.length;
                r3 = new long[(r3 + length)];
                if (length != 0) {
                    System.arraycopy(this.zzaza, 0, r3, 0, length);
                }
                while (length < r3.length) {
                    r3[length] = com_google_android_gms_internal_measurement_zzzi.zzvj();
                    length++;
                }
                this.zzaza = r3;
                com_google_android_gms_internal_measurement_zzzi.zzas(zzuq);
            } else if (zzuq == 26) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 26);
                length = this.zzazb == null ? 0 : this.zzazb.length;
                obj = new zzgh[(zzuq + length)];
                if (length != 0) {
                    System.arraycopy(this.zzazb, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzgh();
                    com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    length++;
                }
                obj[length] = new zzgh();
                com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                this.zzazb = obj;
            } else if (zzuq == 34) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 34);
                length = this.zzazc == null ? 0 : this.zzazc.length;
                obj = new zzgn[(zzuq + length)];
                if (length != 0) {
                    System.arraycopy(this.zzazc, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzgn();
                    com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    length++;
                }
                obj[length] = new zzgn();
                com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                this.zzazc = obj;
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
