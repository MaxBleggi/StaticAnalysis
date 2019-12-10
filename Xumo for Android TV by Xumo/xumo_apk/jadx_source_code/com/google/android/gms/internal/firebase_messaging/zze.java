package com.google.android.gms.internal.firebase_messaging;

import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

final class zze {
    private final ConcurrentHashMap<zzf, List<Throwable>> zze = new ConcurrentHashMap(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzf = new ReferenceQueue();

    zze() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        z = this.zzf.poll();
        while (z) {
            this.zze.remove(z);
            z = this.zzf.poll();
        }
        List list = (List) this.zze.get(new zzf(th, null));
        if (list != null) {
            return list;
        }
        z = new Vector(2);
        List list2 = (List) this.zze.putIfAbsent(new zzf(th, this.zzf), z);
        return list2 == null ? z : list2;
    }
}
