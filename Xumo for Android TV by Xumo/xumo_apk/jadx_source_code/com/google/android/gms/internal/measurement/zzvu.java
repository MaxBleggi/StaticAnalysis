package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzvu extends zzuj<Float> implements zzwd<Float>, zzxp, RandomAccess {
    private static final zzvu zzbzd;
    private int size;
    private float[] zzbze;

    zzvu() {
        this(new float[10], 0);
    }

    private zzvu(float[] fArr, int i) {
        this.zzbze = fArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzuh();
        if (i2 >= i) {
            System.arraycopy(this.zzbze, i2, this.zzbze, i, this.size - i2);
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
        if (!(obj instanceof zzvu)) {
            return super.equals(obj);
        }
        zzvu com_google_android_gms_internal_measurement_zzvu = (zzvu) obj;
        if (this.size != com_google_android_gms_internal_measurement_zzvu.size) {
            return false;
        }
        obj = com_google_android_gms_internal_measurement_zzvu.zzbze;
        for (int i = 0; i < this.size; i++) {
            if (Float.floatToIntBits(this.zzbze[i]) != Float.floatToIntBits(obj[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + Float.floatToIntBits(this.zzbze[i2]);
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void zzc(float f) {
        zzc(this.size, f);
    }

    private final void zzc(int i, float f) {
        zzuh();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzaj(i));
        }
        if (this.size < this.zzbze.length) {
            System.arraycopy(this.zzbze, i, this.zzbze, i + 1, this.size - i);
        } else {
            Object obj = new float[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzbze, 0, obj, 0, i);
            System.arraycopy(this.zzbze, i, obj, i + 1, this.size - i);
            this.zzbze = obj;
        }
        this.zzbze[i] = f;
        this.size++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Float> collection) {
        zzuh();
        zzvz.checkNotNull(collection);
        if (!(collection instanceof zzvu)) {
            return super.addAll(collection);
        }
        zzvu com_google_android_gms_internal_measurement_zzvu = (zzvu) collection;
        if (com_google_android_gms_internal_measurement_zzvu.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size >= com_google_android_gms_internal_measurement_zzvu.size) {
            int i = this.size + com_google_android_gms_internal_measurement_zzvu.size;
            if (i > this.zzbze.length) {
                this.zzbze = Arrays.copyOf(this.zzbze, i);
            }
            System.arraycopy(com_google_android_gms_internal_measurement_zzvu.zzbze, 0, this.zzbze, this.size, com_google_android_gms_internal_measurement_zzvu.size);
            this.size = i;
            this.modCount += 1;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzuh();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Float.valueOf(this.zzbze[i]))) {
                System.arraycopy(this.zzbze, i + 1, this.zzbze, i, this.size - i);
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
        obj = ((Float) obj).floatValue();
        zzuh();
        zzai(i);
        float f = this.zzbze[i];
        this.zzbze[i] = obj;
        return Float.valueOf(f);
    }

    public final /* synthetic */ Object remove(int i) {
        zzuh();
        zzai(i);
        float f = this.zzbze[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzbze, i + 1, this.zzbze, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Float.valueOf(f);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Float) obj).floatValue());
    }

    public final /* synthetic */ zzwd zzak(int i) {
        if (i >= this.size) {
            return new zzvu(Arrays.copyOf(this.zzbze, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzai(i);
        return Float.valueOf(this.zzbze[i]);
    }

    static {
        zzuj com_google_android_gms_internal_measurement_zzvu = new zzvu();
        zzbzd = com_google_android_gms_internal_measurement_zzvu;
        com_google_android_gms_internal_measurement_zzvu.zzsw();
    }
}
