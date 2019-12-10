package com.google.android.gms.cast.framework.media.uicontroller;

import android.view.View;
import android.view.View.OnClickListener;

final class zzh implements OnClickListener {
    private final /* synthetic */ UIMediaController zzrg;

    zzh(UIMediaController uIMediaController) {
        this.zzrg = uIMediaController;
    }

    public final void onClick(View view) {
        this.zzrg.onClosedCaptionClicked(view);
    }
}
