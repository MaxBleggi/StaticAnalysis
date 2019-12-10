package com.google.android.exoplayer2.source.ads;

import java.io.IOException;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$AdsMediaSource$AdPrepareErrorListener$JESn0be9jt8rlP-1WMBP87BIkQ8 implements Runnable {
    private final /* synthetic */ AdPrepareErrorListener f$0;
    private final /* synthetic */ IOException f$1;

    public /* synthetic */ -$$Lambda$AdsMediaSource$AdPrepareErrorListener$JESn0be9jt8rlP-1WMBP87BIkQ8(AdPrepareErrorListener adPrepareErrorListener, IOException iOException) {
        this.f$0 = adPrepareErrorListener;
        this.f$1 = iOException;
    }

    public final void run() {
        this.f$0.this$0.adsLoader.handlePrepareError(this.f$0.adGroupIndex, this.f$0.adIndexInAdGroup, this.f$1);
    }
}
