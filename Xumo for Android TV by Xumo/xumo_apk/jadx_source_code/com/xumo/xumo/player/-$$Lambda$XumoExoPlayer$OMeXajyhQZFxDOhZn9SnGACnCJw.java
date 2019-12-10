package com.xumo.xumo.player;

import android.os.Handler;
import android.os.Looper;
import com.xumo.xumo.player.WatchdogTimer.OnWatchdogStopListener;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$XumoExoPlayer$OMeXajyhQZFxDOhZn9SnGACnCJw implements OnWatchdogStopListener {
    private final /* synthetic */ XumoExoPlayer f$0;

    public /* synthetic */ -$$Lambda$XumoExoPlayer$OMeXajyhQZFxDOhZn9SnGACnCJw(XumoExoPlayer xumoExoPlayer) {
        this.f$0 = xumoExoPlayer;
    }

    public final void stop() {
        new Handler(Looper.getMainLooper()).post(new -$$Lambda$XumoExoPlayer$5tK-J1EgseRIVPWN6gT03JhzesQ(this.f$0));
    }
}
