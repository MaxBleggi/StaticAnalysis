package com.google.firebase.iid;

import android.util.Pair;
import androidx.collection.ArrayMap;
import com.google.android.gms.tasks.Task;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

final class zzaq {
    private final Executor zzbj;
    @GuardedBy("this")
    private final Map<Pair<String, String>, Task<String>> zzco = new ArrayMap();

    zzaq(Executor executor) {
        this.zzbj = executor;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final synchronized com.google.android.gms.tasks.Task<java.lang.String> zza(java.lang.String r4, java.lang.String r5, com.google.firebase.iid.zzas r6) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = new android.util.Pair;	 Catch:{ all -> 0x0081 }
        r0.<init>(r4, r5);	 Catch:{ all -> 0x0081 }
        r4 = r3.zzco;	 Catch:{ all -> 0x0081 }
        r4 = r4.get(r0);	 Catch:{ all -> 0x0081 }
        r4 = (com.google.android.gms.tasks.Task) r4;	 Catch:{ all -> 0x0081 }
        r5 = 3;
        if (r4 == 0) goto L_0x003f;
    L_0x0011:
        r6 = "FirebaseInstanceId";
        r5 = android.util.Log.isLoggable(r6, r5);	 Catch:{ all -> 0x0081 }
        if (r5 == 0) goto L_0x003d;
    L_0x0019:
        r5 = "FirebaseInstanceId";
        r6 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x0081 }
        r0 = java.lang.String.valueOf(r6);	 Catch:{ all -> 0x0081 }
        r0 = r0.length();	 Catch:{ all -> 0x0081 }
        r0 = r0 + 29;
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0081 }
        r1.<init>(r0);	 Catch:{ all -> 0x0081 }
        r0 = "Joining ongoing request for: ";
        r1.append(r0);	 Catch:{ all -> 0x0081 }
        r1.append(r6);	 Catch:{ all -> 0x0081 }
        r6 = r1.toString();	 Catch:{ all -> 0x0081 }
        android.util.Log.d(r5, r6);	 Catch:{ all -> 0x0081 }
    L_0x003d:
        monitor-exit(r3);
        return r4;
    L_0x003f:
        r4 = "FirebaseInstanceId";
        r4 = android.util.Log.isLoggable(r4, r5);	 Catch:{ all -> 0x0081 }
        if (r4 == 0) goto L_0x006b;
    L_0x0047:
        r4 = "FirebaseInstanceId";
        r5 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x0081 }
        r1 = java.lang.String.valueOf(r5);	 Catch:{ all -> 0x0081 }
        r1 = r1.length();	 Catch:{ all -> 0x0081 }
        r1 = r1 + 24;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0081 }
        r2.<init>(r1);	 Catch:{ all -> 0x0081 }
        r1 = "Making new request for: ";
        r2.append(r1);	 Catch:{ all -> 0x0081 }
        r2.append(r5);	 Catch:{ all -> 0x0081 }
        r5 = r2.toString();	 Catch:{ all -> 0x0081 }
        android.util.Log.d(r4, r5);	 Catch:{ all -> 0x0081 }
    L_0x006b:
        r4 = r6.zzs();	 Catch:{ all -> 0x0081 }
        r5 = r3.zzbj;	 Catch:{ all -> 0x0081 }
        r6 = new com.google.firebase.iid.zzar;	 Catch:{ all -> 0x0081 }
        r6.<init>(r3, r0);	 Catch:{ all -> 0x0081 }
        r4 = r4.continueWithTask(r5, r6);	 Catch:{ all -> 0x0081 }
        r5 = r3.zzco;	 Catch:{ all -> 0x0081 }
        r5.put(r0, r4);	 Catch:{ all -> 0x0081 }
        monitor-exit(r3);
        return r4;
    L_0x0081:
        r4 = move-exception;
        monitor-exit(r3);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzaq.zza(java.lang.String, java.lang.String, com.google.firebase.iid.zzas):com.google.android.gms.tasks.Task<java.lang.String>");
    }

    final /* synthetic */ Task zza(Pair pair, Task task) throws Exception {
        synchronized (this) {
            this.zzco.remove(pair);
        }
        return task;
    }
}
