package com.google.android.gms.cast.framework.media;

import java.util.TimerTask;

final class zzk extends TimerTask {
    private final /* synthetic */ MediaQueue zznf;

    zzk(MediaQueue mediaQueue) {
        this.zznf = mediaQueue;
    }

    public final void run() {
        this.zznf.zzbg();
    }
}
