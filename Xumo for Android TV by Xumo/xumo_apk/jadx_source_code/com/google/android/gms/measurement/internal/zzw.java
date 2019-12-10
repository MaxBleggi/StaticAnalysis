package com.google.android.gms.measurement.internal;

import android.os.Handler;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzea;

abstract class zzw {
    private static volatile Handler handler;
    private final zzcr zzahz;
    private final Runnable zzyo;
    private volatile long zzyp;

    zzw(zzcr com_google_android_gms_measurement_internal_zzcr) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzcr);
        this.zzahz = com_google_android_gms_measurement_internal_zzcr;
        this.zzyo = new zzx(this, com_google_android_gms_measurement_internal_zzcr);
    }

    public abstract void run();

    public final void zzh(long j) {
        cancel();
        if (j >= 0) {
            this.zzyp = this.zzahz.zzbx().currentTimeMillis();
            if (!getHandler().postDelayed(this.zzyo, j)) {
                this.zzahz.zzgt().zzjg().zzg("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }

    public final boolean zzej() {
        return this.zzyp != 0;
    }

    final void cancel() {
        this.zzyp = 0;
        getHandler().removeCallbacks(this.zzyo);
    }

    private final Handler getHandler() {
        if (handler != null) {
            return handler;
        }
        Handler handler;
        synchronized (zzw.class) {
            if (handler == null) {
                handler = new zzea(this.zzahz.getContext().getMainLooper());
            }
            handler = handler;
        }
        return handler;
    }
}
