package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.nio.charset.Charset;

class zzux extends zzuw {
    protected final byte[] zzbvb;

    zzux(byte[] bArr) {
        if (bArr != null) {
            this.zzbvb = bArr;
            return;
        }
        throw new NullPointerException();
    }

    protected int zzun() {
        return 0;
    }

    public byte zzal(int i) {
        return this.zzbvb[i];
    }

    byte zzam(int i) {
        return this.zzbvb[i];
    }

    public int size() {
        return this.zzbvb.length;
    }

    public final zzun zzb(int i, int i2) {
        i = zzun.zzb(0, i2, size());
        if (i == 0) {
            return zzun.zzbuu;
        }
        return new zzus(this.zzbvb, zzun(), i);
    }

    final void zza(zzum com_google_android_gms_internal_measurement_zzum) throws IOException {
        com_google_android_gms_internal_measurement_zzum.zza(this.zzbvb, zzun(), size());
    }

    protected final String zza(Charset charset) {
        return new String(this.zzbvb, zzun(), size(), charset);
    }

    public final boolean zzul() {
        int zzun = zzun();
        return zzyu.zzf(this.zzbvb, zzun, size() + zzun);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzun) || size() != ((zzun) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzux)) {
            return obj.equals(this);
        }
        zzux com_google_android_gms_internal_measurement_zzux = (zzux) obj;
        int zzum = zzum();
        int zzum2 = com_google_android_gms_internal_measurement_zzux.zzum();
        if (zzum == 0 || zzum2 == 0 || zzum == zzum2) {
            return zza(com_google_android_gms_internal_measurement_zzux, 0, size());
        }
        return false;
    }

    final boolean zza(zzun com_google_android_gms_internal_measurement_zzun, int i, int i2) {
        StringBuilder stringBuilder;
        if (i2 > com_google_android_gms_internal_measurement_zzun.size()) {
            i = size();
            stringBuilder = new StringBuilder(40);
            stringBuilder.append("Length too large: ");
            stringBuilder.append(i2);
            stringBuilder.append(i);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (i2 > com_google_android_gms_internal_measurement_zzun.size()) {
            com_google_android_gms_internal_measurement_zzun = com_google_android_gms_internal_measurement_zzun.size();
            stringBuilder = new StringBuilder(59);
            stringBuilder.append("Ran off end of other: 0, ");
            stringBuilder.append(i2);
            stringBuilder.append(", ");
            stringBuilder.append(com_google_android_gms_internal_measurement_zzun);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if ((com_google_android_gms_internal_measurement_zzun instanceof zzux) == 0) {
            return com_google_android_gms_internal_measurement_zzun.zzb(0, i2).equals(zzb(0, i2));
        } else {
            zzux com_google_android_gms_internal_measurement_zzux = (zzux) com_google_android_gms_internal_measurement_zzun;
            i = this.zzbvb;
            byte[] bArr = com_google_android_gms_internal_measurement_zzux.zzbvb;
            int zzun = zzun() + i2;
            i2 = zzun();
            com_google_android_gms_internal_measurement_zzun = com_google_android_gms_internal_measurement_zzux.zzun();
            while (i2 < zzun) {
                if (i[i2] != bArr[com_google_android_gms_internal_measurement_zzun]) {
                    return false;
                }
                i2++;
                com_google_android_gms_internal_measurement_zzun++;
            }
            return true;
        }
    }

    protected final int zza(int i, int i2, int i3) {
        return zzvz.zza(i, this.zzbvb, zzun(), i3);
    }
}
