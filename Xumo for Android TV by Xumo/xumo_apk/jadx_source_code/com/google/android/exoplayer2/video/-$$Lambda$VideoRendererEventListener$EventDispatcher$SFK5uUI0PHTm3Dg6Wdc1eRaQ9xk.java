package com.google.android.exoplayer2.video;

import android.view.Surface;
import com.google.android.exoplayer2.video.VideoRendererEventListener.EventDispatcher;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$VideoRendererEventListener$EventDispatcher$SFK5uUI0PHTm3Dg6Wdc1eRaQ9xk implements Runnable {
    private final /* synthetic */ EventDispatcher f$0;
    private final /* synthetic */ Surface f$1;

    public /* synthetic */ -$$Lambda$VideoRendererEventListener$EventDispatcher$SFK5uUI0PHTm3Dg6Wdc1eRaQ9xk(EventDispatcher eventDispatcher, Surface surface) {
        this.f$0 = eventDispatcher;
        this.f$1 = surface;
    }

    public final void run() {
        this.f$0.listener.onRenderedFirstFrame(this.f$1);
    }
}
