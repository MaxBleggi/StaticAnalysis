package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.concurrent.GuardedBy;

public final class zzab {
    @GuardedBy("MessengerIpcClient.class")
    private static zzab zzbt;
    private final ScheduledExecutorService zzbu;
    @GuardedBy("this")
    private zzad zzbv = new zzad();
    @GuardedBy("this")
    private int zzbw = 1;
    private final Context zzx;

    public static synchronized zzab zzc(Context context) {
        synchronized (zzab.class) {
            if (zzbt == null) {
                zzbt = new zzab(context, Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("MessengerIpcClient")));
            }
            context = zzbt;
        }
        return context;
    }

    @VisibleForTesting
    private zzab(Context context, ScheduledExecutorService scheduledExecutorService) {
        this.zzbu = scheduledExecutorService;
        this.zzx = context.getApplicationContext();
    }

    public final Task<Void> zza(int i, Bundle bundle) {
        return zza(new zzaj(zzx(), 2, bundle));
    }

    public final Task<Bundle> zzb(int i, Bundle bundle) {
        return zza(new zzam(zzx(), 1, bundle));
    }

    private final synchronized <T> Task<T> zza(zzak<T> com_google_firebase_iid_zzak_T) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(com_google_firebase_iid_zzak_T);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 9);
            stringBuilder.append("Queueing ");
            stringBuilder.append(valueOf);
            Log.d("MessengerIpcClient", stringBuilder.toString());
        }
        if (!this.zzbv.zzb(com_google_firebase_iid_zzak_T)) {
            this.zzbv = new zzad();
            this.zzbv.zzb(com_google_firebase_iid_zzak_T);
        }
        return com_google_firebase_iid_zzak_T.zzcg.getTask();
    }

    private final synchronized int zzx() {
        int i;
        i = this.zzbw;
        this.zzbw = i + 1;
        return i;
    }
}
