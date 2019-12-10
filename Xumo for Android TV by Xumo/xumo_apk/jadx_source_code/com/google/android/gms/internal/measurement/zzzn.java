package com.google.android.gms.internal.measurement;

public final class zzzn implements Cloneable {
    private static final zzzo zzcga = new zzzo();
    private int mSize;
    private boolean zzcgb;
    private int[] zzcgc;
    private zzzo[] zzcgd;

    zzzn() {
        this(10);
    }

    private zzzn(int i) {
        this.zzcgb = false;
        i = idealIntArraySize(i);
        this.zzcgc = new int[i];
        this.zzcgd = new zzzo[i];
        this.mSize = 0;
    }

    final zzzo zzcd(int i) {
        i = zzcf(i);
        if (i >= 0) {
            if (this.zzcgd[i] != zzcga) {
                return this.zzcgd[i];
            }
        }
        return 0;
    }

    final void zza(int i, zzzo com_google_android_gms_internal_measurement_zzzo) {
        int zzcf = zzcf(i);
        if (zzcf >= 0) {
            this.zzcgd[zzcf] = com_google_android_gms_internal_measurement_zzzo;
            return;
        }
        zzcf ^= -1;
        if (zzcf >= this.mSize || this.zzcgd[zzcf] != zzcga) {
            if (this.mSize >= this.zzcgc.length) {
                int idealIntArraySize = idealIntArraySize(this.mSize + 1);
                Object obj = new int[idealIntArraySize];
                Object obj2 = new zzzo[idealIntArraySize];
                System.arraycopy(this.zzcgc, 0, obj, 0, this.zzcgc.length);
                System.arraycopy(this.zzcgd, 0, obj2, 0, this.zzcgd.length);
                this.zzcgc = obj;
                this.zzcgd = obj2;
            }
            if (this.mSize - zzcf != 0) {
                int i2 = zzcf + 1;
                System.arraycopy(this.zzcgc, zzcf, this.zzcgc, i2, this.mSize - zzcf);
                System.arraycopy(this.zzcgd, zzcf, this.zzcgd, i2, this.mSize - zzcf);
            }
            this.zzcgc[zzcf] = i;
            this.zzcgd[zzcf] = com_google_android_gms_internal_measurement_zzzo;
            this.mSize++;
            return;
        }
        this.zzcgc[zzcf] = i;
        this.zzcgd[zzcf] = com_google_android_gms_internal_measurement_zzzo;
    }

    final int size() {
        return this.mSize;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final zzzo zzce(int i) {
        return this.zzcgd[i];
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzzn)) {
            return false;
        }
        zzzn com_google_android_gms_internal_measurement_zzzn = (zzzn) obj;
        if (this.mSize != com_google_android_gms_internal_measurement_zzzn.mSize) {
            return false;
        }
        Object obj2;
        int[] iArr = this.zzcgc;
        int[] iArr2 = com_google_android_gms_internal_measurement_zzzn.zzcgc;
        int i = this.mSize;
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                obj2 = null;
                break;
            }
        }
        obj2 = 1;
        if (obj2 != null) {
            zzzo[] com_google_android_gms_internal_measurement_zzzoArr = this.zzcgd;
            obj = com_google_android_gms_internal_measurement_zzzn.zzcgd;
            int i3 = this.mSize;
            for (i = 0; i < i3; i++) {
                if (!com_google_android_gms_internal_measurement_zzzoArr[i].equals(obj[i])) {
                    obj = null;
                    break;
                }
            }
            obj = true;
            if (obj != null) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzcgc[i2]) * 31) + this.zzcgd[i2].hashCode();
        }
        return i;
    }

    private static int idealIntArraySize(int i) {
        i <<= 2;
        for (int i2 = 4; i2 < 32; i2++) {
            int i3 = (1 << i2) - 12;
            if (i <= i3) {
                i = i3;
                break;
            }
        }
        return i / 4;
    }

    private final int zzcf(int i) {
        int i2 = this.mSize - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzcgc[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i2 = i4 - 1;
            }
        }
        return i3 ^ -1;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        zzzn com_google_android_gms_internal_measurement_zzzn = new zzzn(i);
        int i2 = 0;
        System.arraycopy(this.zzcgc, 0, com_google_android_gms_internal_measurement_zzzn.zzcgc, 0, i);
        while (i2 < i) {
            if (this.zzcgd[i2] != null) {
                com_google_android_gms_internal_measurement_zzzn.zzcgd[i2] = (zzzo) this.zzcgd[i2].clone();
            }
            i2++;
        }
        com_google_android_gms_internal_measurement_zzzn.mSize = i;
        return com_google_android_gms_internal_measurement_zzzn;
    }
}
