package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzul extends zzuj<Boolean> implements zzwd<Boolean>, zzxp, RandomAccess {
    private static final zzul zzbus;
    private int size;
    private boolean[] zzbut;

    zzul() {
        this(new boolean[10], 0);
    }

    private zzul(boolean[] zArr, int i) {
        this.zzbut = zArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzuh();
        if (i2 >= i) {
            System.arraycopy(this.zzbut, i2, this.zzbut, i, this.size - i2);
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
        if (!(obj instanceof zzul)) {
            return super.equals(obj);
        }
        zzul com_google_android_gms_internal_measurement_zzul = (zzul) obj;
        if (this.size != com_google_android_gms_internal_measurement_zzul.size) {
            return false;
        }
        obj = com_google_android_gms_internal_measurement_zzul.zzbut;
        for (int i = 0; i < this.size; i++) {
            if (this.zzbut[i] != obj[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzvz.zzu(this.zzbut[i2]);
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void addBoolean(boolean z) {
        zza(this.size, z);
    }

    private final void zza(int i, boolean z) {
        zzuh();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzaj(i));
        }
        if (this.size < this.zzbut.length) {
            System.arraycopy(this.zzbut, i, this.zzbut, i + 1, this.size - i);
        } else {
            Object obj = new boolean[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzbut, 0, obj, 0, i);
            System.arraycopy(this.zzbut, i, obj, i + 1, this.size - i);
            this.zzbut = obj;
        }
        this.zzbut[i] = z;
        this.size++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzuh();
        zzvz.checkNotNull(collection);
        if (!(collection instanceof zzul)) {
            return super.addAll(collection);
        }
        zzul com_google_android_gms_internal_measurement_zzul = (zzul) collection;
        if (com_google_android_gms_internal_measurement_zzul.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size >= com_google_android_gms_internal_measurement_zzul.size) {
            int i = this.size + com_google_android_gms_internal_measurement_zzul.size;
            if (i > this.zzbut.length) {
                this.zzbut = Arrays.copyOf(this.zzbut, i);
            }
            System.arraycopy(com_google_android_gms_internal_measurement_zzul.zzbut, 0, this.zzbut, this.size, com_google_android_gms_internal_measurement_zzul.size);
            this.size = i;
            this.modCount += 1;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzuh();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Boolean.valueOf(this.zzbut[i]))) {
                System.arraycopy(this.zzbut, i + 1, this.zzbut, i, this.size - i);
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
        obj = ((Boolean) obj).booleanValue();
        zzuh();
        zzai(i);
        boolean z = this.zzbut[i];
        this.zzbut[i] = obj;
        return Boolean.valueOf(z);
    }

    public final /* synthetic */ Object remove(int i) {
        zzuh();
        zzai(i);
        boolean z = this.zzbut[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzbut, i + 1, this.zzbut, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Boolean.valueOf(z);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Boolean) obj).booleanValue());
    }

    public final /* synthetic */ zzwd zzak(int i) {
        if (i >= this.size) {
            return new zzul(Arrays.copyOf(this.zzbut, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzai(i);
        return Boolean.valueOf(this.zzbut[i]);
    }

    static {
        zzuj com_google_android_gms_internal_measurement_zzul = new zzul();
        zzbus = com_google_android_gms_internal_measurement_zzul;
        com_google_android_gms_internal_measurement_zzul.zzsw();
    }
}
