package com.google.android.gms.cast.framework.media.uicontroller;

import android.view.View;
import android.view.View.OnClickListener;

final class zzd implements OnClickListener {
    private final /* synthetic */ UIMediaController zzrg;
    private final /* synthetic */ long zzrh;

    zzd(UIMediaController uIMediaController, long j) {
        this.zzrg = uIMediaController;
        this.zzrh = j;
    }

    public final void onClick(View view) {
        this.zzrg.onForwardClicked(view, this.zzrh);
    }
}
