package com.google.android.gms.cast.framework.media.uicontroller;

import android.view.View;
import android.view.View.OnClickListener;

final class zze implements OnClickListener {
    private final /* synthetic */ UIMediaController zzrg;
    private final /* synthetic */ long zzrh;

    zze(UIMediaController uIMediaController, long j) {
        this.zzrg = uIMediaController;
        this.zzrh = j;
    }

    public final void onClick(View view) {
        this.zzrg.onRewindClicked(view, this.zzrh);
    }
}
