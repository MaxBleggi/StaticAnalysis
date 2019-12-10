package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

public final class zza {
    private static final IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static long zzgt;
    private static float zzgu = Float.NaN;

    @TargetApi(20)
    public static int zzg(Context context) {
        if (context != null) {
            if (context.getApplicationContext() != null) {
                int i;
                Intent registerReceiver = context.getApplicationContext().registerReceiver(null, filter);
                int i2 = 0;
                if (registerReceiver == null) {
                    i = 0;
                } else {
                    i = registerReceiver.getIntExtra("plugged", 0);
                }
                i = (i & 7) != 0 ? 1 : 0;
                PowerManager powerManager = (PowerManager) context.getSystemService("power");
                if (powerManager == null) {
                    return -1;
                }
                if (PlatformVersion.isAtLeastKitKatWatch()) {
                    context = powerManager.isInteractive();
                } else {
                    context = powerManager.isScreenOn();
                }
                if (context != null) {
                    i2 = 1;
                }
                return (i2 << 1) | i;
            }
        }
        return -1;
    }

    public static synchronized float zzh(Context context) {
        synchronized (zza.class) {
            if (SystemClock.elapsedRealtime() - zzgt >= DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS || Float.isNaN(zzgu)) {
                context = context.getApplicationContext().registerReceiver(null, filter);
                if (context != null) {
                    zzgu = ((float) context.getIntExtra(Param.LEVEL, -1)) / ((float) context.getIntExtra("scale", -1));
                }
                zzgt = SystemClock.elapsedRealtime();
                context = zzgu;
                return context;
            }
            context = zzgu;
            return context;
        }
    }
}
