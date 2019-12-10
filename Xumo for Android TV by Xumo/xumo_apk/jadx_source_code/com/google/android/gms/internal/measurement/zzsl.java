package com.google.android.gms.internal.measurement;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Process;
import android.os.UserManager;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;

public class zzsl {
    private static volatile UserManager zzbrk;
    private static volatile boolean zzbrl = (zztj() ^ 1);

    private zzsl() {
    }

    public static boolean zztj() {
        return VERSION.SDK_INT >= 24;
    }

    public static boolean isUserUnlocked(Context context) {
        if (zztj()) {
            if (zzab(context) == null) {
                return null;
            }
        }
        return true;
    }

    @RequiresApi(24)
    @TargetApi(24)
    private static boolean zzab(Context context) {
        boolean z = zzbrl;
        if (!z) {
            boolean z2 = z;
            int i = 1;
            while (i <= 2) {
                UserManager zzac = zzac(context);
                if (zzac == null) {
                    zzbrl = true;
                    return true;
                }
                try {
                    if (!zzac.isUserUnlocked()) {
                        if (zzac.isUserRunning(Process.myUserHandle())) {
                            z2 = false;
                            zzbrl = z2;
                            z = z2;
                            if (z) {
                                zzbrk = null;
                            }
                        }
                    }
                    z2 = true;
                    zzbrl = z2;
                    z = z2;
                    if (z) {
                        zzbrk = null;
                    }
                } catch (Throwable e) {
                    Log.w("DirectBootUtils", "Failed to check if user is unlocked", e);
                    zzbrk = null;
                    i++;
                }
            }
            z = z2;
            if (z) {
                zzbrk = null;
            }
        }
        return z;
    }

    @RequiresApi(24)
    @VisibleForTesting
    @TargetApi(24)
    private static UserManager zzac(Context context) {
        UserManager userManager = zzbrk;
        if (userManager == null) {
            synchronized (zzsl.class) {
                userManager = zzbrk;
                if (userManager == null) {
                    UserManager userManager2 = (UserManager) context.getSystemService(UserManager.class);
                    zzbrk = userManager2;
                    userManager = userManager2;
                }
            }
        }
        return userManager;
    }
}
