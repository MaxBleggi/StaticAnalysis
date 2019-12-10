package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class zzwm extends zzuj<String> implements zzwn, RandomAccess {
    private static final zzwm zzcbb;
    private static final zzwn zzcbc = zzcbb;
    private final List<Object> zzcbd;

    public zzwm() {
        this(10);
    }

    public zzwm(int i) {
        this(new ArrayList(i));
    }

    private zzwm(ArrayList<Object> arrayList) {
        this.zzcbd = arrayList;
    }

    public final int size() {
        return this.zzcbd.size();
    }

    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzuh();
        if (collection instanceof zzwn) {
            collection = ((zzwn) collection).zzxi();
        }
        i = this.zzcbd.addAll(i, collection);
        this.modCount++;
        return i;
    }

    public final void clear() {
        zzuh();
        this.zzcbd.clear();
        this.modCount++;
    }

    public final void zzc(zzun com_google_android_gms_internal_measurement_zzun) {
        zzuh();
        this.zzcbd.add(com_google_android_gms_internal_measurement_zzun);
        this.modCount++;
    }

    public final Object zzbo(int i) {
        return this.zzcbd.get(i);
    }

    private static String zzaa(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzun) {
            return ((zzun) obj).zzuk();
        }
        return zzvz.zzm((byte[]) obj);
    }

    public final List<?> zzxi() {
        return Collections.unmodifiableList(this.zzcbd);
    }

    public final zzwn zzxj() {
        return zzug() ? new zzyp(this) : this;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        String str = (String) obj;
        zzuh();
        return zzaa(this.zzcbd.set(i, str));
    }

    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    public final /* synthetic */ Object remove(int i) {
        zzuh();
        i = this.zzcbd.remove(i);
        this.modCount++;
        return zzaa(i);
    }

    public final /* bridge */ /* synthetic */ boolean zzug() {
        return super.zzug();
    }

    public final /* synthetic */ void add(int i, Object obj) {
        String str = (String) obj;
        zzuh();
        this.zzcbd.add(i, str);
        this.modCount++;
    }

    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final /* synthetic */ zzwd zzak(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzcbd);
            return new zzwm(arrayList);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzcbd.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        String zzuk;
        if (obj instanceof zzun) {
            zzun com_google_android_gms_internal_measurement_zzun = (zzun) obj;
            zzuk = com_google_android_gms_internal_measurement_zzun.zzuk();
            if (com_google_android_gms_internal_measurement_zzun.zzul()) {
                this.zzcbd.set(i, zzuk);
            }
            return zzuk;
        }
        byte[] bArr = (byte[]) obj;
        zzuk = zzvz.zzm(bArr);
        if (zzvz.zzl(bArr)) {
            this.zzcbd.set(i, zzuk);
        }
        return zzuk;
    }

    static {
        zzuj com_google_android_gms_internal_measurement_zzwm = new zzwm();
        zzcbb = com_google_android_gms_internal_measurement_zzwm;
        com_google_android_gms_internal_measurement_zzwm.zzsw();
    }
}
