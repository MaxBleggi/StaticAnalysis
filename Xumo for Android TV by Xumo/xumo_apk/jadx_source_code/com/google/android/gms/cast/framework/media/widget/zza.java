package com.google.android.gms.cast.framework.media.widget;

import android.graphics.Bitmap;
import com.google.android.gms.internal.cast.zzy;

final class zza implements zzy {
    private final /* synthetic */ ExpandedControllerActivity zztr;

    zza(ExpandedControllerActivity expandedControllerActivity) {
        this.zztr = expandedControllerActivity;
    }

    public final void zza(Bitmap bitmap) {
        if (bitmap != null) {
            if (this.zztr.zzti != null) {
                this.zztr.zzti.setVisibility(8);
            }
            if (this.zztr.zzth != null) {
                this.zztr.zzth.setVisibility(0);
                this.zztr.zzth.setImageBitmap(bitmap);
            }
        }
    }
}
