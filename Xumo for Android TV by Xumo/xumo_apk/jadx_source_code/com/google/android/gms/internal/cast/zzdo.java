package com.google.android.gms.internal.cast;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Locale;

public final class zzdo {
    private static final zzdh zzbe = new zzdh("RequestTracker");
    private static final Object zzza = new Object();
    private final Handler handler = new zzek(Looper.getMainLooper());
    @VisibleForTesting
    private Runnable zzpt;
    @VisibleForTesting
    private long zzve = -1;
    private long zzyy;
    @VisibleForTesting
    private zzdn zzyz;

    public zzdo(long j) {
        this.zzyy = j;
    }

    public final void zza(long j, zzdn com_google_android_gms_internal_cast_zzdn) {
        synchronized (zzza) {
            zzdn com_google_android_gms_internal_cast_zzdn2 = this.zzyz;
            long j2 = this.zzve;
            this.zzve = j;
            this.zzyz = com_google_android_gms_internal_cast_zzdn;
        }
        if (com_google_android_gms_internal_cast_zzdn2 != null) {
            com_google_android_gms_internal_cast_zzdn2.zzb(j2);
        }
        synchronized (zzza) {
            if (this.zzpt != null) {
                this.handler.removeCallbacks(this.zzpt);
            }
            this.zzpt = new zzdp(this);
            this.handler.postDelayed(this.zzpt, this.zzyy);
        }
    }

    public final boolean zzdq() {
        boolean z;
        synchronized (zzza) {
            z = this.zzve != -1;
        }
        return z;
    }

    public final boolean test(long j) {
        synchronized (zzza) {
            j = (this.zzve == -1 || this.zzve != j) ? null : 1;
        }
        return j;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zzc(long r8, int r10, java.lang.Object r11) {
        /*
        r7 = this;
        r0 = zzza;
        monitor-enter(r0);
        r1 = r7.zzve;	 Catch:{ all -> 0x002a }
        r3 = -1;
        r5 = 0;
        r6 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
        if (r6 == 0) goto L_0x0028;
    L_0x000c:
        r1 = r7.zzve;	 Catch:{ all -> 0x002a }
        r3 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1));
        if (r3 != 0) goto L_0x0028;
    L_0x0012:
        r1 = java.util.Locale.ROOT;	 Catch:{ all -> 0x002a }
        r2 = "request %d completed";
        r3 = 1;
        r4 = new java.lang.Object[r3];	 Catch:{ all -> 0x002a }
        r8 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x002a }
        r4[r5] = r8;	 Catch:{ all -> 0x002a }
        r8 = java.lang.String.format(r1, r2, r4);	 Catch:{ all -> 0x002a }
        r7.zza(r10, r11, r8);	 Catch:{ all -> 0x002a }
        monitor-exit(r0);	 Catch:{ all -> 0x002a }
        return r3;
    L_0x0028:
        monitor-exit(r0);	 Catch:{ all -> 0x002a }
        return r5;
    L_0x002a:
        r8 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x002a }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdo.zzc(long, int, java.lang.Object):boolean");
    }

    public final boolean zzq(int i) {
        return zza((int) CastStatusCodes.CANCELED, null);
    }

    private final boolean zza(int i, Object obj) {
        synchronized (zzza) {
            if (this.zzve != -1) {
                zza(i, null, String.format(Locale.ROOT, "clearing request %d", new Object[]{Long.valueOf(this.zzve)}));
                return true;
            }
            return false;
        }
    }

    private final void zza(int i, Object obj, String str) {
        zzbe.d(str, new Object[0]);
        synchronized (zzza) {
            if (this.zzyz != null) {
                this.zzyz.zza(this.zzve, i, obj);
            }
            this.zzve = -1;
            this.zzyz = null;
            synchronized (zzza) {
                if (this.zzpt == null) {
                } else {
                    this.handler.removeCallbacks(this.zzpt);
                    this.zzpt = null;
                }
            }
        }
    }

    final /* synthetic */ void zzdr() {
        synchronized (zzza) {
            if (this.zzve == -1) {
                return;
            }
            zza(15, null);
        }
    }
}
