package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.offline.DownloadHelper.AnonymousClass1;
import com.google.android.exoplayer2.offline.DownloadHelper.Callback;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$DownloadHelper$1$NJSYwfqmwQZzGoahNhNZN-HV0Ik implements Runnable {
    private final /* synthetic */ AnonymousClass1 f$0;
    private final /* synthetic */ Callback f$1;

    public /* synthetic */ -$$Lambda$DownloadHelper$1$NJSYwfqmwQZzGoahNhNZN-HV0Ik(AnonymousClass1 anonymousClass1, Callback callback) {
        this.f$0 = anonymousClass1;
        this.f$1 = callback;
    }

    public final void run() {
        this.f$1.onPrepared(this.f$0.this$0);
    }
}
