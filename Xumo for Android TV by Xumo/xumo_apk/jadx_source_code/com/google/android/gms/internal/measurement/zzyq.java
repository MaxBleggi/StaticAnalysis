package com.google.android.gms.internal.measurement;

import java.util.ListIterator;

final class zzyq implements ListIterator<String> {
    private ListIterator<String> zzcdm = this.zzcdo.zzcdl.listIterator(this.zzcdn);
    private final /* synthetic */ int zzcdn;
    private final /* synthetic */ zzyp zzcdo;

    zzyq(zzyp com_google_android_gms_internal_measurement_zzyp, int i) {
        this.zzcdo = com_google_android_gms_internal_measurement_zzyp;
        this.zzcdn = i;
    }

    public final boolean hasNext() {
        return this.zzcdm.hasNext();
    }

    public final boolean hasPrevious() {
        return this.zzcdm.hasPrevious();
    }

    public final int nextIndex() {
        return this.zzcdm.nextIndex();
    }

    public final int previousIndex() {
        return this.zzcdm.previousIndex();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void set(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object previous() {
        return (String) this.zzcdm.previous();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzcdm.next();
    }
}
