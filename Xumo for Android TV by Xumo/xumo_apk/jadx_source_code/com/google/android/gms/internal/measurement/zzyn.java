package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzvx.zze;
import java.io.IOException;
import java.util.Arrays;

public final class zzyn {
    private static final zzyn zzcdj = new zzyn(0, new int[0], new Object[0], false);
    private int count;
    private boolean zzbup;
    private int zzbzi;
    private Object[] zzcbv;
    private int[] zzcdk;

    public static zzyn zzys() {
        return zzcdj;
    }

    static zzyn zzyt() {
        return new zzyn();
    }

    static zzyn zza(zzyn com_google_android_gms_internal_measurement_zzyn, zzyn com_google_android_gms_internal_measurement_zzyn2) {
        int i = com_google_android_gms_internal_measurement_zzyn.count + com_google_android_gms_internal_measurement_zzyn2.count;
        Object copyOf = Arrays.copyOf(com_google_android_gms_internal_measurement_zzyn.zzcdk, i);
        System.arraycopy(com_google_android_gms_internal_measurement_zzyn2.zzcdk, 0, copyOf, com_google_android_gms_internal_measurement_zzyn.count, com_google_android_gms_internal_measurement_zzyn2.count);
        Object copyOf2 = Arrays.copyOf(com_google_android_gms_internal_measurement_zzyn.zzcbv, i);
        System.arraycopy(com_google_android_gms_internal_measurement_zzyn2.zzcbv, 0, copyOf2, com_google_android_gms_internal_measurement_zzyn.count, com_google_android_gms_internal_measurement_zzyn2.count);
        return new zzyn(i, copyOf, copyOf2, true);
    }

    private zzyn() {
        this(0, new int[8], new Object[8], true);
    }

    private zzyn(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzbzi = -1;
        this.count = i;
        this.zzcdk = iArr;
        this.zzcbv = objArr;
        this.zzbup = z;
    }

    public final void zzsw() {
        this.zzbup = false;
    }

    final void zza(zzzh com_google_android_gms_internal_measurement_zzzh) throws IOException {
        int i;
        if (com_google_android_gms_internal_measurement_zzzh.zzvt() == zze.zzcaa) {
            for (i = this.count - 1; i >= 0; i--) {
                com_google_android_gms_internal_measurement_zzzh.zza(this.zzcdk[i] >>> 3, this.zzcbv[i]);
            }
            return;
        }
        for (i = 0; i < this.count; i++) {
            com_google_android_gms_internal_measurement_zzzh.zza(this.zzcdk[i] >>> 3, this.zzcbv[i]);
        }
    }

    public final void zzb(zzzh com_google_android_gms_internal_measurement_zzzh) throws IOException {
        if (this.count != 0) {
            int i;
            if (com_google_android_gms_internal_measurement_zzzh.zzvt() == zze.zzbzz) {
                for (i = 0; i < this.count; i++) {
                    zzb(this.zzcdk[i], this.zzcbv[i], com_google_android_gms_internal_measurement_zzzh);
                }
                return;
            }
            for (i = this.count - 1; i >= 0; i--) {
                zzb(this.zzcdk[i], this.zzcbv[i], com_google_android_gms_internal_measurement_zzzh);
            }
        }
    }

    private static void zzb(int i, Object obj, zzzh com_google_android_gms_internal_measurement_zzzh) throws IOException {
        int i2 = i >>> 3;
        i &= 7;
        if (i != 5) {
            switch (i) {
                case 0:
                    com_google_android_gms_internal_measurement_zzzh.zzi(i2, ((Long) obj).longValue());
                    return;
                case 1:
                    com_google_android_gms_internal_measurement_zzzh.zzc(i2, ((Long) obj).longValue());
                    return;
                case 2:
                    com_google_android_gms_internal_measurement_zzzh.zza(i2, (zzun) obj);
                    return;
                case 3:
                    if (com_google_android_gms_internal_measurement_zzzh.zzvt() == zze.zzbzz) {
                        com_google_android_gms_internal_measurement_zzzh.zzbl(i2);
                        ((zzyn) obj).zzb(com_google_android_gms_internal_measurement_zzzh);
                        com_google_android_gms_internal_measurement_zzzh.zzbm(i2);
                        return;
                    }
                    com_google_android_gms_internal_measurement_zzzh.zzbm(i2);
                    ((zzyn) obj).zzb(com_google_android_gms_internal_measurement_zzzh);
                    com_google_android_gms_internal_measurement_zzzh.zzbl(i2);
                    return;
                default:
                    throw new RuntimeException(zzwe.zzxb());
            }
        }
        com_google_android_gms_internal_measurement_zzzh.zzg(i2, ((Integer) obj).intValue());
    }

    public final int zzyu() {
        int i = this.zzbzi;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (i = 0; i < this.count; i++) {
            i2 += zzve.zzd(this.zzcdk[i] >>> 3, (zzun) this.zzcbv[i]);
        }
        this.zzbzi = i2;
        return i2;
    }

    public final int zzwe() {
        int i = this.zzbzi;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (i = 0; i < this.count; i++) {
            int i3 = this.zzcdk[i];
            int i4 = i3 >>> 3;
            i3 &= 7;
            if (i3 != 5) {
                switch (i3) {
                    case 0:
                        i2 += zzve.zze(i4, ((Long) this.zzcbv[i]).longValue());
                        break;
                    case 1:
                        i2 += zzve.zzg(i4, ((Long) this.zzcbv[i]).longValue());
                        break;
                    case 2:
                        i2 += zzve.zzc(i4, (zzun) this.zzcbv[i]);
                        break;
                    case 3:
                        i2 += (zzve.zzbc(i4) << 1) + ((zzyn) this.zzcbv[i]).zzwe();
                        break;
                    default:
                        throw new IllegalStateException(zzwe.zzxb());
                }
            }
            i2 += zzve.zzk(i4, ((Integer) this.zzcbv[i]).intValue());
        }
        this.zzbzi = i2;
        return i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzyn)) {
            return false;
        }
        zzyn com_google_android_gms_internal_measurement_zzyn = (zzyn) obj;
        if (this.count == com_google_android_gms_internal_measurement_zzyn.count) {
            Object obj2;
            int[] iArr = this.zzcdk;
            int[] iArr2 = com_google_android_gms_internal_measurement_zzyn.zzcdk;
            int i = this.count;
            for (int i2 = 0; i2 < i; i2++) {
                if (iArr[i2] != iArr2[i2]) {
                    obj2 = null;
                    break;
                }
            }
            obj2 = 1;
            if (obj2 != null) {
                Object[] objArr = this.zzcbv;
                obj = com_google_android_gms_internal_measurement_zzyn.zzcbv;
                int i3 = this.count;
                for (i = 0; i < i3; i++) {
                    if (!objArr[i].equals(obj[i])) {
                        obj = null;
                        break;
                    }
                }
                obj = true;
                if (obj != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = (this.count + 527) * 31;
        int[] iArr = this.zzcdk;
        int i2 = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < this.count; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        i = (i + i3) * 31;
        Object[] objArr = this.zzcbv;
        for (int i5 = 0; i5 < this.count; i5++) {
            i2 = (i2 * 31) + objArr[i5].hashCode();
        }
        return i + i2;
    }

    final void zzb(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            zzxh.zzb(stringBuilder, i, String.valueOf(this.zzcdk[i2] >>> 3), this.zzcbv[i2]);
        }
    }

    final void zzb(int i, Object obj) {
        if (this.zzbup) {
            if (this.count == this.zzcdk.length) {
                int i2 = this.count + (this.count < 4 ? 8 : this.count >> 1);
                this.zzcdk = Arrays.copyOf(this.zzcdk, i2);
                this.zzcbv = Arrays.copyOf(this.zzcbv, i2);
            }
            this.zzcdk[this.count] = i;
            this.zzcbv[this.count] = obj;
            this.count++;
            return;
        }
        throw new UnsupportedOperationException();
    }
}
