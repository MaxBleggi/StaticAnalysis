package com.google.android.gms.cast;

import com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings;

final class zzy implements Runnable {
    private final /* synthetic */ CastRemoteDisplayLocalService zzch;
    private final /* synthetic */ NotificationSettings zzck;

    zzy(CastRemoteDisplayLocalService castRemoteDisplayLocalService, NotificationSettings notificationSettings) {
        this.zzch = castRemoteDisplayLocalService;
        this.zzck = notificationSettings;
    }

    public final void run() {
        this.zzch.zza(this.zzck);
    }
}
