package com.google.android.exoplayer2.scheduler;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Requirements {
    private static final int DEVICE_CHARGING = 16;
    private static final int DEVICE_IDLE = 8;
    public static final int NETWORK_TYPE_ANY = 1;
    private static final int NETWORK_TYPE_MASK = 7;
    public static final int NETWORK_TYPE_METERED = 4;
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_NOT_ROAMING = 3;
    private static final String[] NETWORK_TYPE_STRINGS = null;
    public static final int NETWORK_TYPE_UNMETERED = 2;
    private static final String TAG = "Requirements";
    private final int requirements;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkType {
    }

    private static void logd(String str) {
    }

    public Requirements(int i, boolean z, boolean z2) {
        int i2 = 0;
        i |= z ? true : false;
        if (z2) {
            i2 = 8;
        }
        this(i | i2);
    }

    public Requirements(int i) {
        this.requirements = i;
    }

    public int getRequiredNetworkType() {
        return this.requirements & 7;
    }

    public boolean isChargingRequired() {
        return (this.requirements & 16) != 0;
    }

    public boolean isIdleRequired() {
        return (this.requirements & 8) != 0;
    }

    public boolean checkRequirements(Context context) {
        return (checkNetworkRequirements(context) && checkChargingRequirement(context) && checkIdleRequirement(context) != null) ? true : null;
    }

    public int getRequirementsData() {
        return this.requirements;
    }

    private boolean checkNetworkRequirements(Context context) {
        int requiredNetworkType = getRequiredNetworkType();
        if (requiredNetworkType == 0) {
            return true;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.isConnected()) {
                if (!checkInternetConnectivity(connectivityManager)) {
                    return false;
                }
                if (requiredNetworkType == 1) {
                    return true;
                }
                if (requiredNetworkType == 3) {
                    context = activeNetworkInfo.isRoaming();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Roaming: ");
                    stringBuilder.append(context);
                    logd(stringBuilder.toString());
                    return context ^ 1;
                }
                context = isActiveNetworkMetered(connectivityManager, activeNetworkInfo);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Metered network: ");
                stringBuilder2.append(context);
                logd(stringBuilder2.toString());
                if (requiredNetworkType == 2) {
                    return context ^ 1;
                }
                if (requiredNetworkType == 4) {
                    return context;
                }
                throw new IllegalStateException();
            }
        }
        logd("No network info or no connection.");
        return false;
    }

    private boolean checkChargingRequirement(Context context) {
        if (!isChargingRequired()) {
            return true;
        }
        context = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        boolean z = false;
        if (context == null) {
            return false;
        }
        context = context.getIntExtra("status", -1);
        if (context == 2 || context == 5) {
            z = true;
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean checkIdleRequirement(android.content.Context r5) {
        /*
        r4 = this;
        r0 = r4.isIdleRequired();
        r1 = 1;
        if (r0 != 0) goto L_0x0008;
    L_0x0007:
        return r1;
    L_0x0008:
        r0 = "power";
        r5 = r5.getSystemService(r0);
        r5 = (android.os.PowerManager) r5;
        r0 = com.google.android.exoplayer2.util.Util.SDK_INT;
        r2 = 23;
        r3 = 0;
        if (r0 < r2) goto L_0x0020;
    L_0x0017:
        r5 = r5.isDeviceIdleMode();
        if (r5 != 0) goto L_0x001e;
    L_0x001d:
        goto L_0x0033;
    L_0x001e:
        r1 = 0;
        goto L_0x0033;
    L_0x0020:
        r0 = com.google.android.exoplayer2.util.Util.SDK_INT;
        r2 = 20;
        if (r0 < r2) goto L_0x002d;
    L_0x0026:
        r5 = r5.isInteractive();
        if (r5 != 0) goto L_0x001e;
    L_0x002c:
        goto L_0x0033;
    L_0x002d:
        r5 = r5.isScreenOn();
        if (r5 != 0) goto L_0x001e;
    L_0x0033:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.scheduler.Requirements.checkIdleRequirement(android.content.Context):boolean");
    }

    private static boolean checkInternetConnectivity(ConnectivityManager connectivityManager) {
        if (Util.SDK_INT < 23) {
            return true;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        boolean z = false;
        if (activeNetwork == null) {
            logd("No active network.");
            return false;
        }
        connectivityManager = connectivityManager.getNetworkCapabilities(activeNetwork);
        if (connectivityManager == null || connectivityManager.hasCapability(16) == null) {
            z = true;
        }
        connectivityManager = new StringBuilder();
        connectivityManager.append("Network capability validated: ");
        connectivityManager.append(z);
        logd(connectivityManager.toString());
        return z ^ 1;
    }

    private static boolean isActiveNetworkMetered(ConnectivityManager connectivityManager, NetworkInfo networkInfo) {
        if (Util.SDK_INT >= 16) {
            return connectivityManager.isActiveNetworkMetered();
        }
        connectivityManager = networkInfo.getType();
        networkInfo = true;
        if (connectivityManager == 1 || connectivityManager == 7 || connectivityManager == 9) {
            networkInfo = null;
        }
        return networkInfo;
    }

    public String toString() {
        return super.toString();
    }
}
