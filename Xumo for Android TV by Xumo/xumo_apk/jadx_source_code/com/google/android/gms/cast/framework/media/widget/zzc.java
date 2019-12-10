package com.google.android.gms.cast.framework.media.widget;

import android.os.Looper;
import com.google.android.gms.cast.AdBreakClipInfo;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.internal.cast.zzek;
import java.util.TimerTask;

final class zzc extends TimerTask {
    final /* synthetic */ ExpandedControllerActivity zztr;
    final /* synthetic */ AdBreakClipInfo zzts;
    final /* synthetic */ RemoteMediaClient zztt;

    zzc(ExpandedControllerActivity expandedControllerActivity, AdBreakClipInfo adBreakClipInfo, RemoteMediaClient remoteMediaClient) {
        this.zztr = expandedControllerActivity;
        this.zzts = adBreakClipInfo;
        this.zztt = remoteMediaClient;
    }

    public final void run() {
        new zzek(Looper.getMainLooper()).post(new zzd(this));
    }
}
