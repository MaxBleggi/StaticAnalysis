package com.google.android.gms.stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.providers.PooledExecutorsProvider;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.common.util.WorkSourceUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.ThreadSafe;

@ShowFirstParty
@ThreadSafe
@KeepForSdk
public class WakeLock {
    private static ScheduledExecutorService zzn;
    private static volatile zza zzo = new zza();
    private final Object zza;
    private final android.os.PowerManager.WakeLock zzb;
    private WorkSource zzc;
    private final int zzd;
    private final String zze;
    private final String zzf;
    private final String zzg;
    private final Context zzh;
    private boolean zzi;
    private final Map<String, Integer[]> zzj;
    private final Set<Future<?>> zzk;
    private int zzl;
    private AtomicInteger zzm;

    public interface zza {
    }

    @KeepForSdk
    public WakeLock(@NonNull Context context, int i, @NonNull String str) {
        this(context, i, str, null, context == null ? null : context.getPackageName());
    }

    private WakeLock(@NonNull Context context, int i, @NonNull String str, @Nullable String str2, @NonNull String str3) {
        this(context, i, str, null, str3, null);
    }

    @SuppressLint({"UnwrappedWakeLock"})
    private WakeLock(@NonNull Context context, int i, @NonNull String str, @Nullable String str2, @NonNull String str3, @Nullable String str4) {
        this.zza = this;
        this.zzi = true;
        this.zzj = new HashMap();
        this.zzk = Collections.synchronizedSet(new HashSet());
        this.zzm = new AtomicInteger(null);
        Preconditions.checkNotNull(context, "WakeLock: context must not be null");
        Preconditions.checkNotEmpty(str, "WakeLock: wakeLockName must not be empty");
        this.zzd = i;
        this.zzf = null;
        this.zzg = null;
        this.zzh = context.getApplicationContext();
        if ("com.google.android.gms".equals(context.getPackageName()) == null) {
            str2 = String.valueOf("*gcore*:");
            str4 = String.valueOf(str);
            this.zze = str4.length() != 0 ? str2.concat(str4) : new String(str2);
        } else {
            this.zze = str;
        }
        this.zzb = ((PowerManager) context.getSystemService("power")).newWakeLock(i, str);
        if (WorkSourceUtil.hasWorkSourcePermission(context) != 0) {
            if (Strings.isEmptyOrWhitespace(str3) != 0) {
                str3 = context.getPackageName();
            }
            this.zzc = WorkSourceUtil.fromPackage(context, str3);
            context = this.zzc;
            if (!(context == null || WorkSourceUtil.hasWorkSourcePermission(this.zzh) == 0)) {
                if (this.zzc != 0) {
                    this.zzc.add(context);
                } else {
                    this.zzc = context;
                }
                try {
                    this.zzb.setWorkSource(this.zzc);
                } catch (Context context2) {
                    Log.wtf("WakeLock", context2.toString());
                }
            }
        }
        if (zzn == null) {
            zzn = PooledExecutorsProvider.getInstance().newSingleThreadScheduledExecutor();
        }
    }

    private final List<String> zza() {
        return WorkSourceUtil.getNames(this.zzc);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @com.google.android.gms.common.annotation.KeepForSdk
    public void acquire(long r14) {
        /*
        r13 = this;
        r0 = r13.zzm;
        r0.incrementAndGet();
        r0 = 0;
        r6 = r13.zza(r0);
        r0 = r13.zza;
        monitor-enter(r0);
        r1 = r13.zzj;	 Catch:{ all -> 0x0096 }
        r1 = r1.isEmpty();	 Catch:{ all -> 0x0096 }
        r2 = 0;
        if (r1 == 0) goto L_0x001a;
    L_0x0016:
        r1 = r13.zzl;	 Catch:{ all -> 0x0096 }
        if (r1 <= 0) goto L_0x0029;
    L_0x001a:
        r1 = r13.zzb;	 Catch:{ all -> 0x0096 }
        r1 = r1.isHeld();	 Catch:{ all -> 0x0096 }
        if (r1 != 0) goto L_0x0029;
    L_0x0022:
        r1 = r13.zzj;	 Catch:{ all -> 0x0096 }
        r1.clear();	 Catch:{ all -> 0x0096 }
        r13.zzl = r2;	 Catch:{ all -> 0x0096 }
    L_0x0029:
        r1 = r13.zzi;	 Catch:{ all -> 0x0096 }
        r12 = 1;
        if (r1 == 0) goto L_0x0056;
    L_0x002e:
        r1 = r13.zzj;	 Catch:{ all -> 0x0096 }
        r1 = r1.get(r6);	 Catch:{ all -> 0x0096 }
        r1 = (java.lang.Integer[]) r1;	 Catch:{ all -> 0x0096 }
        if (r1 != 0) goto L_0x0047;
    L_0x0038:
        r1 = r13.zzj;	 Catch:{ all -> 0x0096 }
        r3 = new java.lang.Integer[r12];	 Catch:{ all -> 0x0096 }
        r4 = java.lang.Integer.valueOf(r12);	 Catch:{ all -> 0x0096 }
        r3[r2] = r4;	 Catch:{ all -> 0x0096 }
        r1.put(r6, r3);	 Catch:{ all -> 0x0096 }
        r2 = 1;
        goto L_0x0054;
    L_0x0047:
        r3 = r1[r2];	 Catch:{ all -> 0x0096 }
        r3 = r3.intValue();	 Catch:{ all -> 0x0096 }
        r3 = r3 + r12;
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x0096 }
        r1[r2] = r3;	 Catch:{ all -> 0x0096 }
    L_0x0054:
        if (r2 != 0) goto L_0x005e;
    L_0x0056:
        r1 = r13.zzi;	 Catch:{ all -> 0x0096 }
        if (r1 != 0) goto L_0x007d;
    L_0x005a:
        r1 = r13.zzl;	 Catch:{ all -> 0x0096 }
        if (r1 != 0) goto L_0x007d;
    L_0x005e:
        r1 = com.google.android.gms.common.stats.WakeLockTracker.getInstance();	 Catch:{ all -> 0x0096 }
        r2 = r13.zzh;	 Catch:{ all -> 0x0096 }
        r3 = r13.zzb;	 Catch:{ all -> 0x0096 }
        r3 = com.google.android.gms.common.stats.StatsUtils.getEventKey(r3, r6);	 Catch:{ all -> 0x0096 }
        r4 = 7;
        r5 = r13.zze;	 Catch:{ all -> 0x0096 }
        r7 = 0;
        r8 = r13.zzd;	 Catch:{ all -> 0x0096 }
        r9 = r13.zza();	 Catch:{ all -> 0x0096 }
        r10 = r14;
        r1.registerEvent(r2, r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ all -> 0x0096 }
        r1 = r13.zzl;	 Catch:{ all -> 0x0096 }
        r1 = r1 + r12;
        r13.zzl = r1;	 Catch:{ all -> 0x0096 }
    L_0x007d:
        monitor-exit(r0);	 Catch:{ all -> 0x0096 }
        r0 = r13.zzb;
        r0.acquire();
        r0 = 0;
        r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1));
        if (r2 <= 0) goto L_0x0095;
    L_0x0089:
        r0 = zzn;
        r1 = new com.google.android.gms.stats.zzb;
        r1.<init>(r13);
        r2 = java.util.concurrent.TimeUnit.MILLISECONDS;
        r0.schedule(r1, r14, r2);
    L_0x0095:
        return;
    L_0x0096:
        r14 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0096 }
        throw r14;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.stats.WakeLock.acquire(long):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @com.google.android.gms.common.annotation.KeepForSdk
    public void release() {
        /*
        r12 = this;
        r0 = r12.zzm;
        r0 = r0.decrementAndGet();
        if (r0 >= 0) goto L_0x0019;
    L_0x0008:
        r0 = "WakeLock";
        r1 = r12.zze;
        r1 = java.lang.String.valueOf(r1);
        r2 = " release without a matched acquire!";
        r1 = r1.concat(r2);
        android.util.Log.e(r0, r1);
    L_0x0019:
        r0 = 0;
        r6 = r12.zza(r0);
        r0 = r12.zza;
        monitor-enter(r0);
        r1 = r12.zzi;	 Catch:{ all -> 0x007e }
        r10 = 1;
        r11 = 0;
        if (r1 == 0) goto L_0x0052;
    L_0x0027:
        r1 = r12.zzj;	 Catch:{ all -> 0x007e }
        r1 = r1.get(r6);	 Catch:{ all -> 0x007e }
        r1 = (java.lang.Integer[]) r1;	 Catch:{ all -> 0x007e }
        if (r1 != 0) goto L_0x0033;
    L_0x0031:
        r1 = 0;
        goto L_0x0050;
    L_0x0033:
        r2 = r1[r11];	 Catch:{ all -> 0x007e }
        r2 = r2.intValue();	 Catch:{ all -> 0x007e }
        if (r2 != r10) goto L_0x0042;
    L_0x003b:
        r1 = r12.zzj;	 Catch:{ all -> 0x007e }
        r1.remove(r6);	 Catch:{ all -> 0x007e }
        r1 = 1;
        goto L_0x0050;
    L_0x0042:
        r2 = r1[r11];	 Catch:{ all -> 0x007e }
        r2 = r2.intValue();	 Catch:{ all -> 0x007e }
        r2 = r2 - r10;
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ all -> 0x007e }
        r1[r11] = r2;	 Catch:{ all -> 0x007e }
        goto L_0x0031;
    L_0x0050:
        if (r1 != 0) goto L_0x005a;
    L_0x0052:
        r1 = r12.zzi;	 Catch:{ all -> 0x007e }
        if (r1 != 0) goto L_0x0079;
    L_0x0056:
        r1 = r12.zzl;	 Catch:{ all -> 0x007e }
        if (r1 != r10) goto L_0x0079;
    L_0x005a:
        r1 = com.google.android.gms.common.stats.WakeLockTracker.getInstance();	 Catch:{ all -> 0x007e }
        r2 = r12.zzh;	 Catch:{ all -> 0x007e }
        r3 = r12.zzb;	 Catch:{ all -> 0x007e }
        r3 = com.google.android.gms.common.stats.StatsUtils.getEventKey(r3, r6);	 Catch:{ all -> 0x007e }
        r4 = 8;
        r5 = r12.zze;	 Catch:{ all -> 0x007e }
        r7 = 0;
        r8 = r12.zzd;	 Catch:{ all -> 0x007e }
        r9 = r12.zza();	 Catch:{ all -> 0x007e }
        r1.registerEvent(r2, r3, r4, r5, r6, r7, r8, r9);	 Catch:{ all -> 0x007e }
        r1 = r12.zzl;	 Catch:{ all -> 0x007e }
        r1 = r1 - r10;
        r12.zzl = r1;	 Catch:{ all -> 0x007e }
    L_0x0079:
        monitor-exit(r0);	 Catch:{ all -> 0x007e }
        r12.zza(r11);
        return;
    L_0x007e:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x007e }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.stats.WakeLock.release():void");
    }

    private final void zza(int i) {
        if (this.zzb.isHeld() != 0) {
            try {
                this.zzb.release();
            } catch (int i2) {
                if (i2.getClass().equals(RuntimeException.class)) {
                    Log.e("WakeLock", String.valueOf(this.zze).concat(" was already released!"), i2);
                } else {
                    throw i2;
                }
            }
            this.zzb.isHeld();
        }
    }

    private final String zza(String str) {
        if (this.zzi) {
            return !TextUtils.isEmpty(str) ? str : this.zzf;
        } else {
            return this.zzf;
        }
    }

    @KeepForSdk
    public void setReferenceCounted(boolean z) {
        this.zzb.setReferenceCounted(z);
        this.zzi = z;
    }

    @KeepForSdk
    public boolean isHeld() {
        return this.zzb.isHeld();
    }
}
