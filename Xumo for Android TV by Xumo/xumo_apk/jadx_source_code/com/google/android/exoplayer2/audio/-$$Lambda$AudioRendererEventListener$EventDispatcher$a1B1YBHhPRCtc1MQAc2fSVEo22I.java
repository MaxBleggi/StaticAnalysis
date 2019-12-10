package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioRendererEventListener.EventDispatcher;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$AudioRendererEventListener$EventDispatcher$a1B1YBHhPRCtc1MQAc2fSVEo22I implements Runnable {
    private final /* synthetic */ EventDispatcher f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ -$$Lambda$AudioRendererEventListener$EventDispatcher$a1B1YBHhPRCtc1MQAc2fSVEo22I(EventDispatcher eventDispatcher, int i) {
        this.f$0 = eventDispatcher;
        this.f$1 = i;
    }

    public final void run() {
        this.f$0.listener.onAudioSessionId(this.f$1);
    }
}
