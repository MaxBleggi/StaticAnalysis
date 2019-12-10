package com.google.android.gms.internal.firebase_messaging;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

final class zzf extends WeakReference<Throwable> {
    private final int zzg;

    public zzf(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, referenceQueue);
        if (th != null) {
            this.zzg = System.identityHashCode(th);
            return;
        }
        throw new NullPointerException("The referent cannot be null");
    }

    public final int hashCode() {
        return this.zzg;
    }

    public final boolean equals(Object obj) {
        if (obj != null) {
            if (obj.getClass() == getClass()) {
                if (this == obj) {
                    return true;
                }
                zzf com_google_android_gms_internal_firebase_messaging_zzf = (zzf) obj;
                return this.zzg == com_google_android_gms_internal_firebase_messaging_zzf.zzg && get() == com_google_android_gms_internal_firebase_messaging_zzf.get();
            }
        }
        return false;
    }
}
