package com.google.android.gms.cast.framework.media;

import android.graphics.Bitmap;
import com.google.android.gms.internal.cast.zzy;

final class zzj implements zzy {
    private final /* synthetic */ MediaNotificationService zzmg;
    private final /* synthetic */ zzb zzmh;

    zzj(MediaNotificationService mediaNotificationService, zzb com_google_android_gms_cast_framework_media_MediaNotificationService_zzb) {
        this.zzmg = mediaNotificationService;
        this.zzmh = com_google_android_gms_cast_framework_media_MediaNotificationService_zzb;
    }

    public final void zza(Bitmap bitmap) {
        this.zzmh.zzmo = bitmap;
        this.zzmg.zzmd = this.zzmh;
        this.zzmg.zzaz();
    }
}
