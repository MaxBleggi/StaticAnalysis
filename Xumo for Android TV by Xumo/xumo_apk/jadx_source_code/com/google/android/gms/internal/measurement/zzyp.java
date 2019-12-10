package com.google.android.gms.internal.measurement;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public final class zzyp extends AbstractList<String> implements zzwn, RandomAccess {
    private final zzwn zzcdl;

    public zzyp(zzwn com_google_android_gms_internal_measurement_zzwn) {
        this.zzcdl = com_google_android_gms_internal_measurement_zzwn;
    }

    public final zzwn zzxj() {
        return this;
    }

    public final Object zzbo(int i) {
        return this.zzcdl.zzbo(i);
    }

    public final int size() {
        return this.zzcdl.size();
    }

    public final void zzc(zzun com_google_android_gms_internal_measurement_zzun) {
        throw new UnsupportedOperationException();
    }

    public final ListIterator<String> listIterator(int i) {
        return new zzyq(this, i);
    }

    public final Iterator<String> iterator() {
        return new zzyr(this);
    }

    public final List<?> zzxi() {
        return this.zzcdl.zzxi();
    }

    public final /* synthetic */ Object get(int i) {
        return (String) this.zzcdl.get(i);
    }
}
