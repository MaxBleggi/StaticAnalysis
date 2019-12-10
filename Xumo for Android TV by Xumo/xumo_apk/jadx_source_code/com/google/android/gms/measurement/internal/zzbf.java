package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences.Editor;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;

public final class zzbf {
    private final long zzabv;
    private final /* synthetic */ zzbb zzaoe;
    @VisibleForTesting
    private final String zzaog;
    private final String zzaoh;
    private final String zzaoi;

    private zzbf(zzbb com_google_android_gms_measurement_internal_zzbb, String str, long j) {
        this.zzaoe = com_google_android_gms_measurement_internal_zzbb;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkArgument(j > 0 ? true : null);
        this.zzaog = String.valueOf(str).concat(":start");
        this.zzaoh = String.valueOf(str).concat(":count");
        this.zzaoi = String.valueOf(str).concat(":value");
        this.zzabv = j;
    }

    @WorkerThread
    private final void zzfl() {
        this.zzaoe.zzaf();
        long currentTimeMillis = this.zzaoe.zzbx().currentTimeMillis();
        Editor edit = this.zzaoe.zzju().edit();
        edit.remove(this.zzaoh);
        edit.remove(this.zzaoi);
        edit.putLong(this.zzaog, currentTimeMillis);
        edit.apply();
    }

    @WorkerThread
    public final void zzc(String str, long j) {
        this.zzaoe.zzaf();
        if (zzfn() == 0) {
            zzfl();
        }
        if (str == null) {
            str = "";
        }
        j = this.zzaoe.zzju().getLong(this.zzaoh, 0);
        if (j <= 0) {
            j = this.zzaoe.zzju().edit();
            j.putString(this.zzaoi, str);
            j.putLong(this.zzaoh, 1);
            j.apply();
            return;
        }
        j++;
        Object obj = (this.zzaoe.zzgr().zzmk().nextLong() & Long.MAX_VALUE) < Long.MAX_VALUE / j ? 1 : null;
        Editor edit = this.zzaoe.zzju().edit();
        if (obj != null) {
            edit.putString(this.zzaoi, str);
        }
        edit.putLong(this.zzaoh, j);
        edit.apply();
    }

    @WorkerThread
    public final Pair<String, Long> zzfm() {
        this.zzaoe.zzaf();
        this.zzaoe.zzaf();
        long zzfn = zzfn();
        if (zzfn == 0) {
            zzfl();
            zzfn = 0;
        } else {
            zzfn = Math.abs(zzfn - this.zzaoe.zzbx().currentTimeMillis());
        }
        if (zzfn < this.zzabv) {
            return null;
        }
        if (zzfn > (this.zzabv << 1)) {
            zzfl();
            return null;
        }
        String string = this.zzaoe.zzju().getString(this.zzaoi, null);
        long j = this.zzaoe.zzju().getLong(this.zzaoh, 0);
        zzfl();
        if (string != null) {
            if (j > 0) {
                return new Pair(string, Long.valueOf(j));
            }
        }
        return zzbb.zzanj;
    }

    @WorkerThread
    private final long zzfn() {
        return this.zzaoe.zzju().getLong(this.zzaog, 0);
    }
}
