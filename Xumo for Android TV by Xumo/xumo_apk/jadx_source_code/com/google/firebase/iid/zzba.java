package com.google.firebase.iid;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;

final class zzba {
    @GuardedBy("itself")
    private final zzaw zzaj;
    @GuardedBy("this")
    private int zzdl = 0;
    @GuardedBy("this")
    private final Map<Integer, TaskCompletionSource<Void>> zzdm = new ArrayMap();

    zzba(zzaw com_google_firebase_iid_zzaw) {
        this.zzaj = com_google_firebase_iid_zzaw;
    }

    final synchronized Task<Void> zza(String str) {
        int i;
        synchronized (this.zzaj) {
            Object zzak = this.zzaj.zzak();
            zzaw com_google_firebase_iid_zzaw = this.zzaj;
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzak).length() + 1) + String.valueOf(str).length());
            stringBuilder.append(zzak);
            stringBuilder.append(",");
            stringBuilder.append(str);
            com_google_firebase_iid_zzaw.zzf(stringBuilder.toString());
        }
        str = new TaskCompletionSource();
        Map map = this.zzdm;
        if (TextUtils.isEmpty(zzak)) {
            i = 0;
        } else {
            i = zzak.split(",").length - 1;
        }
        map.put(Integer.valueOf(this.zzdl + i), str);
        return str.getTask();
    }

    final synchronized boolean zzaq() {
        return zzar() != null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @androidx.annotation.WorkerThread
    final boolean zzc(com.google.firebase.iid.FirebaseInstanceId r5) {
        /*
        r4 = this;
    L_0x0000:
        monitor-enter(r4);
        r0 = r4.zzar();	 Catch:{ all -> 0x0042 }
        r1 = 1;
        if (r0 != 0) goto L_0x0017;
    L_0x0008:
        r5 = com.google.firebase.iid.FirebaseInstanceId.zzl();	 Catch:{ all -> 0x0042 }
        if (r5 == 0) goto L_0x0015;
    L_0x000e:
        r5 = "FirebaseInstanceId";
        r0 = "topic sync succeeded";
        android.util.Log.d(r5, r0);	 Catch:{ all -> 0x0042 }
    L_0x0015:
        monitor-exit(r4);	 Catch:{ all -> 0x0042 }
        return r1;
    L_0x0017:
        monitor-exit(r4);	 Catch:{ all -> 0x0042 }
        r2 = zza(r5, r0);
        if (r2 != 0) goto L_0x0020;
    L_0x001e:
        r5 = 0;
        return r5;
    L_0x0020:
        monitor-enter(r4);
        r2 = r4.zzdm;	 Catch:{ all -> 0x003f }
        r3 = r4.zzdl;	 Catch:{ all -> 0x003f }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x003f }
        r2 = r2.remove(r3);	 Catch:{ all -> 0x003f }
        r2 = (com.google.android.gms.tasks.TaskCompletionSource) r2;	 Catch:{ all -> 0x003f }
        r4.zzk(r0);	 Catch:{ all -> 0x003f }
        r0 = r4.zzdl;	 Catch:{ all -> 0x003f }
        r0 = r0 + r1;
        r4.zzdl = r0;	 Catch:{ all -> 0x003f }
        monitor-exit(r4);	 Catch:{ all -> 0x003f }
        if (r2 == 0) goto L_0x0000;
    L_0x003a:
        r0 = 0;
        r2.setResult(r0);
        goto L_0x0000;
    L_0x003f:
        r5 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x003f }
        throw r5;
    L_0x0042:
        r5 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0042 }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzba.zzc(com.google.firebase.iid.FirebaseInstanceId):boolean");
    }

    @GuardedBy("this")
    @Nullable
    private final String zzar() {
        synchronized (this.zzaj) {
            Object zzak = this.zzaj.zzak();
        }
        if (!TextUtils.isEmpty(zzak)) {
            String[] split = zzak.split(",");
            if (split.length > 1 && !TextUtils.isEmpty(split[1])) {
                return split[1];
            }
        }
        return null;
    }

    private final synchronized boolean zzk(String str) {
        synchronized (this.zzaj) {
            String zzak = this.zzaj.zzak();
            String valueOf = String.valueOf(",");
            String valueOf2 = String.valueOf(str);
            if (zzak.startsWith(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf))) {
                valueOf = String.valueOf(",");
                str = String.valueOf(str);
                this.zzaj.zzf(zzak.substring((str.length() != 0 ? valueOf.concat(str) : new String(valueOf)).length()));
                return true;
            }
            return null;
        }
    }

    @WorkerThread
    private static boolean zza(FirebaseInstanceId firebaseInstanceId, String str) {
        str = str.split("!");
        if (str.length == 2) {
            String str2 = str[0];
            str = str[1];
            Object obj = -1;
            try {
                int hashCode = str2.hashCode();
                if (hashCode != 83) {
                    if (hashCode == 85) {
                        if (str2.equals("U")) {
                            obj = 1;
                        }
                    }
                } else if (str2.equals("S")) {
                    obj = null;
                }
                switch (obj) {
                    case null:
                        firebaseInstanceId.zzb(str);
                        if (FirebaseInstanceId.zzl() != null) {
                            Log.d("FirebaseInstanceId", "subscribe operation succeeded");
                            break;
                        }
                        break;
                    case 1:
                        firebaseInstanceId.zzc(str);
                        if (FirebaseInstanceId.zzl() != null) {
                            Log.d("FirebaseInstanceId", "unsubscribe operation succeeded");
                            break;
                        }
                        break;
                    default:
                        break;
                }
            } catch (FirebaseInstanceId firebaseInstanceId2) {
                str = "FirebaseInstanceId";
                String str3 = "Topic sync failed: ";
                firebaseInstanceId2 = String.valueOf(firebaseInstanceId2.getMessage());
                Log.e(str, firebaseInstanceId2.length() != 0 ? str3.concat(firebaseInstanceId2) : new String(str3));
                return false;
            }
        }
        return true;
    }
}
