package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.util.EventDispatcher.Event;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$EventDispatcher$N5z9NZy9yo_9QWPNCHLnel_5AjM implements Runnable {
    private final /* synthetic */ Event f$0;
    private final /* synthetic */ Object f$1;

    public /* synthetic */ -$$Lambda$EventDispatcher$N5z9NZy9yo_9QWPNCHLnel_5AjM(Event event, Object obj) {
        this.f$0 = event;
        this.f$1 = obj;
    }

    public final void run() {
        this.f$0.sendTo(this.f$1);
    }
}
