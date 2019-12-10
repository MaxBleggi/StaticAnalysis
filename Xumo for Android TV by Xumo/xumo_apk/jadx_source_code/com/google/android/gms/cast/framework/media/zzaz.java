package com.google.android.gms.cast.framework.media;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

final class zzaz implements OnClickListener {
    private final /* synthetic */ TracksChooserDialogFragment zzqc;

    zzaz(TracksChooserDialogFragment tracksChooserDialogFragment) {
        this.zzqc = tracksChooserDialogFragment;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        if (this.zzqc.zzpz != null) {
            this.zzqc.zzpz.cancel();
            this.zzqc.zzpz = null;
        }
    }
}
