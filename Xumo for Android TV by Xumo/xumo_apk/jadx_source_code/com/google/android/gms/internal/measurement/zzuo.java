package com.google.android.gms.internal.measurement;

import java.util.NoSuchElementException;

final class zzuo extends zzuq {
    private final int limit = this.zzbux.size();
    private int position = null;
    private final /* synthetic */ zzun zzbux;

    zzuo(zzun com_google_android_gms_internal_measurement_zzun) {
        this.zzbux = com_google_android_gms_internal_measurement_zzun;
    }

    public final boolean hasNext() {
        return this.position < this.limit;
    }

    public final byte nextByte() {
        int i = this.position;
        if (i < this.limit) {
            this.position = i + 1;
            return this.zzbux.zzam(i);
        }
        throw new NoSuchElementException();
    }
}
