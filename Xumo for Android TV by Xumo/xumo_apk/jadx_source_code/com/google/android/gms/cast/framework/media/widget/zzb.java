package com.google.android.gms.cast.framework.media.widget;

import android.view.View;
import android.view.View.OnClickListener;

final class zzb implements OnClickListener {
    private final /* synthetic */ ExpandedControllerActivity zztr;

    zzb(ExpandedControllerActivity expandedControllerActivity) {
        this.zztr = expandedControllerActivity;
    }

    public final void onClick(View view) {
        if (this.zztr.zztk.isClickable() != null) {
            this.zztr.getRemoteMediaClient().skipAd();
        }
    }
}
