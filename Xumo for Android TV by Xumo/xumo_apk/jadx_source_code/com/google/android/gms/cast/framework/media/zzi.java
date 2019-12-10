package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.framework.AppVisibilityListener;

final class zzi implements AppVisibilityListener {
    private final /* synthetic */ MediaNotificationService zzmg;

    zzi(MediaNotificationService mediaNotificationService) {
        this.zzmg = mediaNotificationService;
    }

    public final void onAppEnteredForeground() {
        this.zzmg.stopForeground(true);
    }

    public final void onAppEnteredBackground() {
        if (this.zzmg.zzbu != null) {
            this.zzmg.startForeground(1, this.zzmg.zzbu);
        } else {
            this.zzmg.stopForeground(true);
        }
    }
}
