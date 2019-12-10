package com.xumo.xumo.util;

import com.google.android.exoplayer2.offline.DownloadAction;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$DownloadTracker$bzokEdj9PSB55m-U0EMSfNRGwtE implements Runnable {
    private final /* synthetic */ DownloadTracker f$0;
    private final /* synthetic */ DownloadAction[] f$1;

    public /* synthetic */ -$$Lambda$DownloadTracker$bzokEdj9PSB55m-U0EMSfNRGwtE(DownloadTracker downloadTracker, DownloadAction[] downloadActionArr) {
        this.f$0 = downloadTracker;
        this.f$1 = downloadActionArr;
    }

    public final void run() {
        DownloadTracker.lambda$handleTrackedDownloadStatesChanged$0(this.f$0, this.f$1);
    }
}
