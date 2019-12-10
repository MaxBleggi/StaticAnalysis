package com.xumo.xumo.player;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$XumoExoPlayer$BeaconTimerTask$jERq269lRwTq3WmcfPPxp9cAP8M implements Runnable {
    private final /* synthetic */ BeaconTimerTask f$0;

    public /* synthetic */ -$$Lambda$XumoExoPlayer$BeaconTimerTask$jERq269lRwTq3WmcfPPxp9cAP8M(BeaconTimerTask beaconTimerTask) {
        this.f$0 = beaconTimerTask;
    }

    public final void run() {
        this.f$0.this$0.requestNextAd(this.f$0.this$0.getNextAdUri());
    }
}
