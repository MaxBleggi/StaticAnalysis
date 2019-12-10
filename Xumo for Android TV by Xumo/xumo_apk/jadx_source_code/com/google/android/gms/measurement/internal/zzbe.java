package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences.Editor;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

public final class zzbe {
    private long value;
    private boolean zzaod;
    private final /* synthetic */ zzbb zzaoe;
    private final long zzaof;
    private final String zzoj;

    public zzbe(zzbb com_google_android_gms_measurement_internal_zzbb, String str, long j) {
        this.zzaoe = com_google_android_gms_measurement_internal_zzbb;
        Preconditions.checkNotEmpty(str);
        this.zzoj = str;
        this.zzaof = j;
    }

    @WorkerThread
    public final long get() {
        if (!this.zzaod) {
            this.zzaod = true;
            this.value = this.zzaoe.zzju().getLong(this.zzoj, this.zzaof);
        }
        return this.value;
    }

    @WorkerThread
    public final void set(long j) {
        Editor edit = this.zzaoe.zzju().edit();
        edit.putLong(this.zzoj, j);
        edit.apply();
        this.value = j;
    }
}
