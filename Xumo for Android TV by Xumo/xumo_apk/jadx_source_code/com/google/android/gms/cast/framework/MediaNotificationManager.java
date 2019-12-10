package com.google.android.gms.cast.framework;

import androidx.annotation.NonNull;

public class MediaNotificationManager {
    private final SessionManager zzhj;

    public MediaNotificationManager(@NonNull SessionManager sessionManager) {
        this.zzhj = sessionManager;
    }

    public void updateNotification() {
        CastSession currentCastSession = this.zzhj.getCurrentCastSession();
        if (currentCastSession != null) {
            currentCastSession.zzs().zzg(true);
        }
    }
}
