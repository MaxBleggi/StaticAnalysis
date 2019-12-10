package com.xumo.xumo.player;

import android.os.Handler;
import android.os.Looper;
import com.xumo.xumo.player.WatchdogTimer.OnWatchdogStopListener;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$XumoExoPlayer$YzXgk5FGNUqWXR_D-SBvtFMFPuU implements OnWatchdogStopListener {
    private final /* synthetic */ XumoExoPlayer f$0;

    public /* synthetic */ -$$Lambda$XumoExoPlayer$YzXgk5FGNUqWXR_D-SBvtFMFPuU(XumoExoPlayer xumoExoPlayer) {
        this.f$0 = xumoExoPlayer;
    }

    public final void stop() {
        new Handler(Looper.getMainLooper()).post(new -$$Lambda$XumoExoPlayer$knSCe92jYCxj5uI6XjkBzIlC5Ng(this.f$0));
    }
}
