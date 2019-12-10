package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences.Editor;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

public final class zzbg {
    private String value;
    private boolean zzaod;
    private final /* synthetic */ zzbb zzaoe;
    private final String zzaoj = null;
    private final String zzoj;

    public zzbg(zzbb com_google_android_gms_measurement_internal_zzbb, String str, String str2) {
        this.zzaoe = com_google_android_gms_measurement_internal_zzbb;
        Preconditions.checkNotEmpty(str);
        this.zzoj = str;
    }

    @WorkerThread
    public final String zzkd() {
        if (!this.zzaod) {
            this.zzaod = true;
            this.value = this.zzaoe.zzju().getString(this.zzoj, null);
        }
        return this.value;
    }

    @WorkerThread
    public final void zzcf(String str) {
        if (!zzfu.zzv(str, this.value)) {
            Editor edit = this.zzaoe.zzju().edit();
            edit.putString(this.zzoj, str);
            edit.apply();
            this.value = str;
        }
    }
}
