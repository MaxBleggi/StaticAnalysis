package com.google.android.gms.internal.measurement;

final class zzus extends zzux {
    private final int zzbuy;
    private final int zzbuz;

    zzus(byte[] bArr, int i, int i2) {
        super(bArr);
        zzun.zzb(i, i + i2, (int) bArr.length);
        this.zzbuy = i;
        this.zzbuz = i2;
    }

    public final byte zzal(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzbvb[this.zzbuy + i];
        }
        if (i < 0) {
            StringBuilder stringBuilder = new StringBuilder(22);
            stringBuilder.append("Index < 0: ");
            stringBuilder.append(i);
            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder(40);
        stringBuilder2.append("Index > length: ");
        stringBuilder2.append(i);
        stringBuilder2.append(", ");
        stringBuilder2.append(size);
        throw new ArrayIndexOutOfBoundsException(stringBuilder2.toString());
    }

    final byte zzam(int i) {
        return this.zzbvb[this.zzbuy + i];
    }

    public final int size() {
        return this.zzbuz;
    }

    protected final int zzun() {
        return this.zzbuy;
    }
}
