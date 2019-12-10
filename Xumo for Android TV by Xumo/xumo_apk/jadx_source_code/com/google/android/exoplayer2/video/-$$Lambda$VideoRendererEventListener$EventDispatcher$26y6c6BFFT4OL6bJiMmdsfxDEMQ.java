package com.google.android.exoplayer2.video;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.video.VideoRendererEventListener.EventDispatcher;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$VideoRendererEventListener$EventDispatcher$26y6c6BFFT4OL6bJiMmdsfxDEMQ implements Runnable {
    private final /* synthetic */ EventDispatcher f$0;
    private final /* synthetic */ Format f$1;

    public /* synthetic */ -$$Lambda$VideoRendererEventListener$EventDispatcher$26y6c6BFFT4OL6bJiMmdsfxDEMQ(EventDispatcher eventDispatcher, Format format) {
        this.f$0 = eventDispatcher;
        this.f$1 = format;
    }

    public final void run() {
        this.f$0.listener.onVideoInputFormatChanged(this.f$1);
    }
}
