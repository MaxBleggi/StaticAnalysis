package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver.PendingResult;
import android.content.Context;
import android.os.Bundle;

final class zzbm implements Runnable {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzbu zzaop;
    private final /* synthetic */ zzaq zzaoq;
    private final /* synthetic */ long zzaor;
    private final /* synthetic */ Bundle zzaos;
    private final /* synthetic */ PendingResult zzrf;

    zzbm(zzbk com_google_android_gms_measurement_internal_zzbk, zzbu com_google_android_gms_measurement_internal_zzbu, long j, Bundle bundle, Context context, zzaq com_google_android_gms_measurement_internal_zzaq, PendingResult pendingResult) {
        this.zzaop = com_google_android_gms_measurement_internal_zzbu;
        this.zzaor = j;
        this.zzaos = bundle;
        this.val$context = context;
        this.zzaoq = com_google_android_gms_measurement_internal_zzaq;
        this.zzrf = pendingResult;
    }

    public final void run() {
        long j = this.zzaop.zzgu().zzanq.get();
        long j2 = this.zzaor;
        if (j > 0 && (j2 >= j || j2 <= 0)) {
            j2 = j - 1;
        }
        if (j2 > 0) {
            this.zzaos.putLong("click_timestamp", j2);
        }
        this.zzaos.putString("_cis", "referrer broadcast");
        zzbu.zza(this.val$context, null).zzgj().logEvent("auto", "_cmp", this.zzaos);
        this.zzaoq.zzjo().zzca("Install campaign recorded");
        if (this.zzrf != null) {
            this.zzrf.finish();
        }
    }
}
