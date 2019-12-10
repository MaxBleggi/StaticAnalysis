package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgk extends zzzl<zzgk> {
    public zzgl[] zzaxr;

    public zzgk() {
        this.zzaxr = zzgl.zznb();
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgk)) {
            return false;
        }
        zzgk com_google_android_gms_internal_measurement_zzgk = (zzgk) obj;
        if (!zzzp.equals(this.zzaxr, com_google_android_gms_internal_measurement_zzgk.zzaxr)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgk.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgk.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgk.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2 = (((getClass().getName().hashCode() + 527) * 31) + zzzp.hashCode(this.zzaxr)) * 31;
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
        if (this.zzaxr != null && this.zzaxr.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzaxr) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(1, com_google_android_gms_internal_measurement_zzzr);
                }
            }
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzaxr != null && this.zzaxr.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzaxr) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    zzf += zzzj.zzb(1, com_google_android_gms_internal_measurement_zzzr);
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
            if (zzuq == 10) {
                zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 10);
                int length = this.zzaxr == null ? 0 : this.zzaxr.length;
                Object obj = new zzgl[(zzuq + length)];
                if (length != 0) {
                    System.arraycopy(this.zzaxr, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = new zzgl();
                    com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                    com_google_android_gms_internal_measurement_zzzi.zzuq();
                    length++;
                }
                obj[length] = new zzgl();
                com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                this.zzaxr = obj;
            } else if (!super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                return this;
            }
        }
    }
}
