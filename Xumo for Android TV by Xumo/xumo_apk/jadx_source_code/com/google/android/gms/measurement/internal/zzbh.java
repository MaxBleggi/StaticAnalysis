package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzu;
import java.util.List;

public final class zzbh {
    final zzbu zzadp;

    zzbh(zzbu com_google_android_gms_measurement_internal_zzbu) {
        this.zzadp = com_google_android_gms_measurement_internal_zzbu;
    }

    @WorkerThread
    protected final void zzcg(String str) {
        if (str != null) {
            if (!str.isEmpty()) {
                this.zzadp.zzgs().zzaf();
                if (zzke()) {
                    this.zzadp.zzgt().zzjm().zzca("Install Referrer Reporter is initializing");
                    ServiceConnection com_google_android_gms_measurement_internal_zzbi = new zzbi(this, str);
                    this.zzadp.zzgs().zzaf();
                    str = new Intent("com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE");
                    str.setComponent(new ComponentName("com.android.vending", "com.google.android.finsky.externalreferrer.GetInstallReferrerService"));
                    PackageManager packageManager = this.zzadp.getContext().getPackageManager();
                    if (packageManager == null) {
                        this.zzadp.zzgt().zzjj().zzca("Failed to obtain Package Manager to verify binding conditions");
                        return;
                    }
                    List queryIntentServices = packageManager.queryIntentServices(str, 0);
                    if (queryIntentServices == null || queryIntentServices.isEmpty()) {
                        this.zzadp.zzgt().zzjm().zzca("Play Service for fetching Install Referrer is unavailable on device");
                        return;
                    }
                    ResolveInfo resolveInfo = (ResolveInfo) queryIntentServices.get(0);
                    if (resolveInfo.serviceInfo != null) {
                        String str2 = resolveInfo.serviceInfo.packageName;
                        if (resolveInfo.serviceInfo.name != null && "com.android.vending".equals(str2) && zzke()) {
                            try {
                                this.zzadp.zzgt().zzjm().zzg("Install Referrer Service is", ConnectionTracker.getInstance().bindService(this.zzadp.getContext(), new Intent(str), com_google_android_gms_measurement_internal_zzbi, 1) != null ? "available" : "not available");
                                return;
                            } catch (String str3) {
                                this.zzadp.zzgt().zzjg().zzg("Exception occurred while binding to Install Referrer Service", str3.getMessage());
                                return;
                            }
                        }
                        this.zzadp.zzgt().zzjm().zzca("Play Store missing or incompatible. Version 8.3.73 or later required");
                    }
                    return;
                }
                this.zzadp.zzgt().zzjm().zzca("Install Referrer Reporter is not available");
                return;
            }
        }
        this.zzadp.zzgt().zzjm().zzca("Install Referrer Reporter was called with invalid app package name");
    }

    @VisibleForTesting
    private final boolean zzke() {
        boolean z = false;
        try {
            PackageManagerWrapper packageManager = Wrappers.packageManager(this.zzadp.getContext());
            if (packageManager == null) {
                this.zzadp.zzgt().zzjm().zzca("Failed to retrieve Package Manager to check Play Store compatibility");
                return false;
            }
            if (packageManager.getPackageInfo("com.android.vending", 128).versionCode >= 80837300) {
                z = true;
            }
            return z;
        } catch (Exception e) {
            this.zzadp.zzgt().zzjm().zzg("Failed to retrieve Play Store version", e);
            return false;
        }
    }

    @WorkerThread
    @Nullable
    @VisibleForTesting
    final Bundle zza(String str, zzu com_google_android_gms_internal_measurement_zzu) {
        this.zzadp.zzgs().zzaf();
        if (com_google_android_gms_internal_measurement_zzu == null) {
            this.zzadp.zzgt().zzjj().zzca("Attempting to use Install Referrer Service while it is not initialized");
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("package_name", str);
        try {
            str = com_google_android_gms_internal_measurement_zzu.zza(bundle);
            if (str != null) {
                return str;
            }
            this.zzadp.zzgt().zzjg().zzca("Install Referrer Service returned a null response");
            return null;
        } catch (String str2) {
            this.zzadp.zzgt().zzjg().zzg("Exception occurred while retrieving the Install Referrer", str2.getMessage());
            return null;
        }
    }
}
