package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences.Editor;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

public final class zzbd {
    private boolean value;
    private final boolean zzaoc = true;
    private boolean zzaod;
    private final /* synthetic */ zzbb zzaoe;
    private final String zzoj;

    public zzbd(zzbb com_google_android_gms_measurement_internal_zzbb, String str, boolean z) {
        this.zzaoe = com_google_android_gms_measurement_internal_zzbb;
        Preconditions.checkNotEmpty(str);
        this.zzoj = str;
    }

    @WorkerThread
    public final boolean get() {
        if (!this.zzaod) {
            this.zzaod = true;
            this.value = this.zzaoe.zzju().getBoolean(this.zzoj, this.zzaoc);
        }
        return this.value;
    }

    @WorkerThread
    public final void set(boolean z) {
        Editor edit = this.zzaoe.zzju().edit();
        edit.putBoolean(this.zzoj, z);
        edit.apply();
        this.value = z;
    }
}
