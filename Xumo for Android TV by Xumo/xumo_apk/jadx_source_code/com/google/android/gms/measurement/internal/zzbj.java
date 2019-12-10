package com.google.android.gms.measurement.internal;

import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.internal.measurement.zzu;

final class zzbj implements Runnable {
    private final /* synthetic */ zzu zzaol;
    private final /* synthetic */ ServiceConnection zzaom;
    private final /* synthetic */ zzbi zzaon;

    zzbj(zzbi com_google_android_gms_measurement_internal_zzbi, zzu com_google_android_gms_internal_measurement_zzu, ServiceConnection serviceConnection) {
        this.zzaon = com_google_android_gms_measurement_internal_zzbi;
        this.zzaol = com_google_android_gms_internal_measurement_zzu;
        this.zzaom = serviceConnection;
    }

    public final void run() {
        zzbh com_google_android_gms_measurement_internal_zzbh = this.zzaon.zzaok;
        String zza = this.zzaon.packageName;
        zzu com_google_android_gms_internal_measurement_zzu = this.zzaol;
        ServiceConnection serviceConnection = this.zzaom;
        Bundle zza2 = com_google_android_gms_measurement_internal_zzbh.zza(zza, com_google_android_gms_internal_measurement_zzu);
        com_google_android_gms_measurement_internal_zzbh.zzadp.zzgs().zzaf();
        if (zza2 != null) {
            long j = zza2.getLong("install_begin_timestamp_seconds", 0) * 1000;
            if (j == 0) {
                com_google_android_gms_measurement_internal_zzbh.zzadp.zzgt().zzjg().zzca("Service response is missing Install Referrer install timestamp");
            } else {
                String string = zza2.getString("install_referrer");
                if (string != null) {
                    if (!string.isEmpty()) {
                        com_google_android_gms_measurement_internal_zzbh.zzadp.zzgt().zzjo().zzg("InstallReferrer API result", string);
                        zzfu zzgr = com_google_android_gms_measurement_internal_zzbh.zzadp.zzgr();
                        String str = "?";
                        string = String.valueOf(string);
                        Bundle zza3 = zzgr.zza(Uri.parse(string.length() != 0 ? str.concat(string) : new String(str)));
                        if (zza3 == null) {
                            com_google_android_gms_measurement_internal_zzbh.zzadp.zzgt().zzjg().zzca("No campaign params defined in install referrer result");
                        } else {
                            String string2 = zza3.getString("medium");
                            Object obj = (string2 == null || "(not set)".equalsIgnoreCase(string2) || "organic".equalsIgnoreCase(string2)) ? null : 1;
                            if (obj != null) {
                                long j2 = zza2.getLong("referrer_click_timestamp_seconds", 0) * 1000;
                                if (j2 == 0) {
                                    com_google_android_gms_measurement_internal_zzbh.zzadp.zzgt().zzjg().zzca("Install Referrer is missing click timestamp for ad campaign");
                                } else {
                                    zza3.putLong("click_timestamp", j2);
                                }
                            }
                            if (j == com_google_android_gms_measurement_internal_zzbh.zzadp.zzgu().zzanr.get()) {
                                com_google_android_gms_measurement_internal_zzbh.zzadp.zzgw();
                                com_google_android_gms_measurement_internal_zzbh.zzadp.zzgt().zzjo().zzca("Campaign has already been logged");
                            } else {
                                com_google_android_gms_measurement_internal_zzbh.zzadp.zzgu().zzanr.set(j);
                                com_google_android_gms_measurement_internal_zzbh.zzadp.zzgw();
                                com_google_android_gms_measurement_internal_zzbh.zzadp.zzgt().zzjo().zzg("Logging Install Referrer campaign from sdk with ", "referrer API");
                                zza3.putString("_cis", "referrer API");
                                com_google_android_gms_measurement_internal_zzbh.zzadp.zzgj().logEvent("auto", "_cmp", zza3);
                            }
                        }
                    }
                }
                com_google_android_gms_measurement_internal_zzbh.zzadp.zzgt().zzjg().zzca("No referrer defined in install referrer response");
            }
        }
        if (serviceConnection != null) {
            ConnectionTracker.getInstance().unbindService(com_google_android_gms_measurement_internal_zzbh.zzadp.getContext(), serviceConnection);
        }
    }
}
