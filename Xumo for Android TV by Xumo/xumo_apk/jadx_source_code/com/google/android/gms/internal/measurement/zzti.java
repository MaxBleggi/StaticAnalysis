package com.google.android.gms.internal.measurement;

import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

final class zzti {
    private final ConcurrentHashMap<zztj, List<Throwable>> zzbsr = new ConcurrentHashMap(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzbss = new ReferenceQueue();

    zzti() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        z = this.zzbss.poll();
        while (z) {
            this.zzbsr.remove(z);
            z = this.zzbss.poll();
        }
        return (List) this.zzbsr.get(new zztj(th, null));
    }
}
