package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzvh extends zzuj<Double> implements zzwd<Double>, zzxp, RandomAccess {
    private static final zzvh zzbwb;
    private int size;
    private double[] zzbwc;

    zzvh() {
        this(new double[10], 0);
    }

    private zzvh(double[] dArr, int i) {
        this.zzbwc = dArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzuh();
        if (i2 >= i) {
            System.arraycopy(this.zzbwc, i2, this.zzbwc, i, this.size - i2);
            this.size -= i2 - i;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzvh)) {
            return super.equals(obj);
        }
        zzvh com_google_android_gms_internal_measurement_zzvh = (zzvh) obj;
        if (this.size != com_google_android_gms_internal_measurement_zzvh.size) {
            return false;
        }
        obj = com_google_android_gms_internal_measurement_zzvh.zzbwc;
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(this.zzbwc[i]) != Double.doubleToLongBits(obj[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzvz.zzbi(Double.doubleToLongBits(this.zzbwc[i2]));
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void zzd(double d) {
        zzc(this.size, d);
    }

    private final void zzc(int i, double d) {
        zzuh();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzaj(i));
        }
        if (this.size < this.zzbwc.length) {
            System.arraycopy(this.zzbwc, i, this.zzbwc, i + 1, this.size - i);
        } else {
            Object obj = new double[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzbwc, 0, obj, 0, i);
            System.arraycopy(this.zzbwc, i, obj, i + 1, this.size - i);
            this.zzbwc = obj;
        }
        this.zzbwc[i] = d;
        this.size++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Double> collection) {
        zzuh();
        zzvz.checkNotNull(collection);
        if (!(collection instanceof zzvh)) {
            return super.addAll(collection);
        }
        zzvh com_google_android_gms_internal_measurement_zzvh = (zzvh) collection;
        if (com_google_android_gms_internal_measurement_zzvh.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size >= com_google_android_gms_internal_measurement_zzvh.size) {
            int i = this.size + com_google_android_gms_internal_measurement_zzvh.size;
            if (i > this.zzbwc.length) {
                this.zzbwc = Arrays.copyOf(this.zzbwc, i);
            }
            System.arraycopy(com_google_android_gms_internal_measurement_zzvh.zzbwc, 0, this.zzbwc, this.size, com_google_android_gms_internal_measurement_zzvh.size);
            this.size = i;
            this.modCount += 1;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzuh();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zzbwc[i]))) {
                System.arraycopy(this.zzbwc, i + 1, this.zzbwc, i, this.size - i);
                this.size -= 1;
                this.modCount += 1;
                return true;
            }
        }
        return false;
    }

    private final void zzai(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzaj(i));
        }
    }

    private final String zzaj(int i) {
        int i2 = this.size;
        StringBuilder stringBuilder = new StringBuilder(35);
        stringBuilder.append("Index:");
        stringBuilder.append(i);
        stringBuilder.append(", Size:");
        stringBuilder.append(i2);
        return stringBuilder.toString();
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        double doubleValue = ((Double) obj).doubleValue();
        zzuh();
        zzai(i);
        double d = this.zzbwc[i];
        this.zzbwc[i] = doubleValue;
        return Double.valueOf(d);
    }

    public final /* synthetic */ Object remove(int i) {
        zzuh();
        zzai(i);
        double d = this.zzbwc[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzbwc, i + 1, this.zzbwc, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Double.valueOf(d);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Double) obj).doubleValue());
    }

    public final /* synthetic */ zzwd zzak(int i) {
        if (i >= this.size) {
            return new zzvh(Arrays.copyOf(this.zzbwc, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzai(i);
        return Double.valueOf(this.zzbwc[i]);
    }

    static {
        zzuj com_google_android_gms_internal_measurement_zzvh = new zzvh();
        zzbwb = com_google_android_gms_internal_measurement_zzvh;
        com_google_android_gms_internal_measurement_zzvh.zzsw();
    }
}
