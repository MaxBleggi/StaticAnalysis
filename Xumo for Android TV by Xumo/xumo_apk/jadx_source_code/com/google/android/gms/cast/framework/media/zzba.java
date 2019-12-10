package com.google.android.gms.cast.framework.media;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

final class zzba implements OnClickListener {
    private final /* synthetic */ TracksChooserDialogFragment zzqc;
    private final /* synthetic */ zzbb zzqd;
    private final /* synthetic */ zzbb zzqe;

    zzba(TracksChooserDialogFragment tracksChooserDialogFragment, zzbb com_google_android_gms_cast_framework_media_zzbb, zzbb com_google_android_gms_cast_framework_media_zzbb2) {
        this.zzqc = tracksChooserDialogFragment;
        this.zzqd = com_google_android_gms_cast_framework_media_zzbb;
        this.zzqe = com_google_android_gms_cast_framework_media_zzbb2;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.zzqc.zza(this.zzqd, this.zzqe);
    }
}
