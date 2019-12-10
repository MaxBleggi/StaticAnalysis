package com.google.android.gms.measurement.internal;

import java.util.Iterator;

final class zzac implements Iterator<String> {
    private Iterator<String> zzaiq = this.zzair.zzaip.keySet().iterator();
    private final /* synthetic */ zzab zzair;

    zzac(zzab com_google_android_gms_measurement_internal_zzab) {
        this.zzair = com_google_android_gms_measurement_internal_zzab;
    }

    public final boolean hasNext() {
        return this.zzaiq.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzaiq.next();
    }
}
