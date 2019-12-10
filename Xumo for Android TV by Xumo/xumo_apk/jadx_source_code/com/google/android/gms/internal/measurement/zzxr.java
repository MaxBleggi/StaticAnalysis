package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.List;

final class zzxr<E> extends zzuj<E> {
    private static final zzxr<Object> zzccq;
    private final List<E> zzcbd;

    public static <E> zzxr<E> zzyb() {
        return zzccq;
    }

    zzxr() {
        this(new ArrayList(10));
    }

    private zzxr(List<E> list) {
        this.zzcbd = list;
    }

    public final void add(int i, E e) {
        zzuh();
        this.zzcbd.add(i, e);
        this.modCount++;
    }

    public final E get(int i) {
        return this.zzcbd.get(i);
    }

    public final E remove(int i) {
        zzuh();
        i = this.zzcbd.remove(i);
        this.modCount++;
        return i;
    }

    public final E set(int i, E e) {
        zzuh();
        i = this.zzcbd.set(i, e);
        this.modCount++;
        return i;
    }

    public final int size() {
        return this.zzcbd.size();
    }

    public final /* synthetic */ zzwd zzak(int i) {
        if (i >= size()) {
            List arrayList = new ArrayList(i);
            arrayList.addAll(this.zzcbd);
            return new zzxr(arrayList);
        }
        throw new IllegalArgumentException();
    }

    static {
        zzuj com_google_android_gms_internal_measurement_zzxr = new zzxr();
        zzccq = com_google_android_gms_internal_measurement_zzxr;
        com_google_android_gms_internal_measurement_zzxr.zzsw();
    }
}
