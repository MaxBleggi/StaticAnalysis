package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;

public abstract class zzun implements Serializable, Iterable<Byte> {
    public static final zzun zzbuu = new zzux(zzvz.zzcae);
    private static final zzut zzbuv = (zzuk.zzui() ? new zzuy() : new zzur());
    private static final Comparator<zzun> zzbuw = new zzup();
    private int zzbst = 0;

    zzun() {
    }

    private static int zza(byte b) {
        return b & 255;
    }

    public abstract boolean equals(Object obj);

    public abstract int size();

    protected abstract int zza(int i, int i2, int i3);

    protected abstract String zza(Charset charset);

    abstract void zza(zzum com_google_android_gms_internal_measurement_zzum) throws IOException;

    public abstract byte zzal(int i);

    abstract byte zzam(int i);

    public abstract zzun zzb(int i, int i2);

    public abstract boolean zzul();

    public static zzun zzb(byte[] bArr, int i, int i2) {
        zzb(i, i + i2, bArr.length);
        return new zzux(zzbuv.zzc(bArr, i, i2));
    }

    static zzun zzi(byte[] bArr) {
        return new zzux(bArr);
    }

    public static zzun zzgc(String str) {
        return new zzux(str.getBytes(zzvz.UTF_8));
    }

    public final String zzuk() {
        return size() == 0 ? "" : zza(zzvz.UTF_8);
    }

    public final int hashCode() {
        int i = this.zzbst;
        if (i == 0) {
            i = size();
            i = zza(i, 0, i);
            if (i == 0) {
                i = 1;
            }
            this.zzbst = i;
        }
        return i;
    }

    static zzuv zzan(int i) {
        return new zzuv(i);
    }

    protected final int zzum() {
        return this.zzbst;
    }

    static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((((i | i2) | i4) | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder stringBuilder = new StringBuilder(32);
            stringBuilder.append("Beginning index: ");
            stringBuilder.append(i);
            stringBuilder.append(" < 0");
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else if (i2 < i) {
            r1 = new StringBuilder(66);
            r1.append("Beginning index larger than ending index: ");
            r1.append(i);
            r1.append(", ");
            r1.append(i2);
            throw new IndexOutOfBoundsException(r1.toString());
        } else {
            r1 = new StringBuilder(37);
            r1.append("End index: ");
            r1.append(i2);
            r1.append(" >= ");
            r1.append(i3);
            throw new IndexOutOfBoundsException(r1.toString());
        }
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }

    public /* synthetic */ Iterator iterator() {
        return new zzuo(this);
    }
}
