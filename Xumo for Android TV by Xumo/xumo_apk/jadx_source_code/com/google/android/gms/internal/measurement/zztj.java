package com.google.android.gms.internal.measurement;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

final class zztj extends WeakReference<Throwable> {
    private final int zzbst;

    public zztj(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, null);
        if (th != null) {
            this.zzbst = System.identityHashCode(th);
            return;
        }
        throw new NullPointerException("The referent cannot be null");
    }

    public final int hashCode() {
        return this.zzbst;
    }

    public final boolean equals(Object obj) {
        if (obj != null) {
            if (obj.getClass() == getClass()) {
                if (this == obj) {
                    return true;
                }
                zztj com_google_android_gms_internal_measurement_zztj = (zztj) obj;
                return this.zzbst == com_google_android_gms_internal_measurement_zztj.zzbst && get() == com_google_android_gms_internal_measurement_zztj.get();
            }
        }
        return false;
    }
}
