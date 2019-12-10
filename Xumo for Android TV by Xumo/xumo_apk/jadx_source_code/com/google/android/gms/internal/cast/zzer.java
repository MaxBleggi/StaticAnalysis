package com.google.android.gms.internal.cast;

import android.annotation.TargetApi;
import android.view.Choreographer.FrameCallback;

public abstract class zzer {
    private Runnable zzzw;
    private FrameCallback zzzx;

    public abstract void doFrame(long j);

    @TargetApi(16)
    final FrameCallback zzdv() {
        if (this.zzzx == null) {
            this.zzzx = new zzes(this);
        }
        return this.zzzx;
    }

    final Runnable zzdw() {
        if (this.zzzw == null) {
            this.zzzw = new zzet(this);
        }
        return this.zzzw;
    }
}
