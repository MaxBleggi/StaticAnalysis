package com.google.android.gms.cast;

final class zzv implements Runnable {
    private final /* synthetic */ CastRemoteDisplayLocalService zzch;

    zzv(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zzch = castRemoteDisplayLocalService;
    }

    public final void run() {
        CastRemoteDisplayLocalService castRemoteDisplayLocalService = this.zzch;
        boolean zzb = this.zzch.zzcc;
        StringBuilder stringBuilder = new StringBuilder(59);
        stringBuilder.append("onCreate after delay. The local service been started: ");
        stringBuilder.append(zzb);
        castRemoteDisplayLocalService.zzb(stringBuilder.toString());
        if (!this.zzch.zzcc) {
            this.zzch.zzc("The local service has not been been started, stopping it");
            this.zzch.stopSelf();
        }
    }
}
