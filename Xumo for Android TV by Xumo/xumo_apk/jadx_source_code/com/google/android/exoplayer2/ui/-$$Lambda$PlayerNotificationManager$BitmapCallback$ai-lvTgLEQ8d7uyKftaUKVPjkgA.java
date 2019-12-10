package com.google.android.exoplayer2.ui;

import android.graphics.Bitmap;
import com.google.android.exoplayer2.ui.PlayerNotificationManager.BitmapCallback;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$PlayerNotificationManager$BitmapCallback$ai-lvTgLEQ8d7uyKftaUKVPjkgA implements Runnable {
    private final /* synthetic */ BitmapCallback f$0;
    private final /* synthetic */ Bitmap f$1;

    public /* synthetic */ -$$Lambda$PlayerNotificationManager$BitmapCallback$ai-lvTgLEQ8d7uyKftaUKVPjkgA(BitmapCallback bitmapCallback, Bitmap bitmap) {
        this.f$0 = bitmapCallback;
        this.f$1 = bitmap;
    }

    public final void run() {
        BitmapCallback.lambda$onBitmap$0(this.f$0, this.f$1);
    }
}
