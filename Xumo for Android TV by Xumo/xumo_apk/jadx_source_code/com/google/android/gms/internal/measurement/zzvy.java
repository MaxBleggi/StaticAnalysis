package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzvy extends zzuj<Integer> implements zzwd<Integer>, zzxp, RandomAccess {
    private static final zzvy zzcac;
    private int size;
    private int[] zzcad;

    zzvy() {
        this(new int[10], 0);
    }

    private zzvy(int[] iArr, int i) {
        this.zzcad = iArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzuh();
        if (i2 >= i) {
            System.arraycopy(this.zzcad, i2, this.zzcad, i, this.size - i2);
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
        if (!(obj instanceof zzvy)) {
            return super.equals(obj);
        }
        zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) obj;
        if (this.size != com_google_android_gms_internal_measurement_zzvy.size) {
            return false;
        }
        obj = com_google_android_gms_internal_measurement_zzvy.zzcad;
        for (int i = 0; i < this.size; i++) {
            if (this.zzcad[i] != obj[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.zzcad[i2];
        }
        return i;
    }

    public final int getInt(int i) {
        zzai(i);
        return this.zzcad[i];
    }

    public final int size() {
        return this.size;
    }

    public final void zzbn(int i) {
        zzp(this.size, i);
    }

    private final void zzp(int i, int i2) {
        zzuh();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzaj(i));
        }
        if (this.size < this.zzcad.length) {
            System.arraycopy(this.zzcad, i, this.zzcad, i + 1, this.size - i);
        } else {
            Object obj = new int[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzcad, 0, obj, 0, i);
            System.arraycopy(this.zzcad, i, obj, i + 1, this.size - i);
            this.zzcad = obj;
        }
        this.zzcad[i] = i2;
        this.size++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Integer> collection) {
        zzuh();
        zzvz.checkNotNull(collection);
        if (!(collection instanceof zzvy)) {
            return super.addAll(collection);
        }
        zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) collection;
        if (com_google_android_gms_internal_measurement_zzvy.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size >= com_google_android_gms_internal_measurement_zzvy.size) {
            int i = this.size + com_google_android_gms_internal_measurement_zzvy.size;
            if (i > this.zzcad.length) {
                this.zzcad = Arrays.copyOf(this.zzcad, i);
            }
            System.arraycopy(com_google_android_gms_internal_measurement_zzvy.zzcad, 0, this.zzcad, this.size, com_google_android_gms_internal_measurement_zzvy.size);
            this.size = i;
            this.modCount += 1;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzuh();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Integer.valueOf(this.zzcad[i]))) {
                System.arraycopy(this.zzcad, i + 1, this.zzcad, i, this.size - i);
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
        obj = ((Integer) obj).intValue();
        zzuh();
        zzai(i);
        int i2 = this.zzcad[i];
        this.zzcad[i] = obj;
        return Integer.valueOf(i2);
    }

    public final /* synthetic */ Object remove(int i) {
        zzuh();
        zzai(i);
        int i2 = this.zzcad[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzcad, i + 1, this.zzcad, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzp(i, ((Integer) obj).intValue());
    }

    public final /* synthetic */ zzwd zzak(int i) {
        if (i >= this.size) {
            return new zzvy(Arrays.copyOf(this.zzcad, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    static {
        zzuj com_google_android_gms_internal_measurement_zzvy = new zzvy();
        zzcac = com_google_android_gms_internal_measurement_zzvy;
        com_google_android_gms_internal_measurement_zzvy.zzsw();
    }
}
