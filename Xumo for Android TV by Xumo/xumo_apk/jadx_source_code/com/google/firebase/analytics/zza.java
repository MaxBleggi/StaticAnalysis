package com.google.firebase.analytics;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

final class zza implements Callable<String> {
    private final /* synthetic */ FirebaseAnalytics zzbsz;

    zza(FirebaseAnalytics firebaseAnalytics) {
        this.zzbsz = firebaseAnalytics;
    }

    public final /* synthetic */ Object call() throws Exception {
        String zza = this.zzbsz.zzgc();
        if (zza != null) {
            return zza;
        }
        zza = this.zzbsz.zzadp.zzgj().zzak(120000);
        if (zza != null) {
            this.zzbsz.zzcr(zza);
            return zza;
        }
        throw new TimeoutException();
    }
}
