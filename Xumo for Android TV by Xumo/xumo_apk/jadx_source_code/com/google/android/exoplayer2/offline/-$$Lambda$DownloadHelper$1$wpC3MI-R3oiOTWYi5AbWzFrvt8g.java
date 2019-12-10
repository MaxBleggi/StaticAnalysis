package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.offline.DownloadHelper.AnonymousClass1;
import com.google.android.exoplayer2.offline.DownloadHelper.Callback;
import java.io.IOException;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$DownloadHelper$1$wpC3MI-R3oiOTWYi5AbWzFrvt8g implements Runnable {
    private final /* synthetic */ AnonymousClass1 f$0;
    private final /* synthetic */ Callback f$1;
    private final /* synthetic */ IOException f$2;

    public /* synthetic */ -$$Lambda$DownloadHelper$1$wpC3MI-R3oiOTWYi5AbWzFrvt8g(AnonymousClass1 anonymousClass1, Callback callback, IOException iOException) {
        this.f$0 = anonymousClass1;
        this.f$1 = callback;
        this.f$2 = iOException;
    }

    public final void run() {
        this.f$1.onPrepareError(this.f$0.this$0, this.f$2);
    }
}
