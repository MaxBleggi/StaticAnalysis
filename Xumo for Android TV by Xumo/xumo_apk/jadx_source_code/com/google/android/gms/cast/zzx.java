package com.google.android.gms.cast;

final class zzx implements Runnable {
    private final /* synthetic */ CastRemoteDisplayLocalService zzch;
    private final /* synthetic */ boolean zzcn;

    zzx(CastRemoteDisplayLocalService castRemoteDisplayLocalService, boolean z) {
        this.zzch = castRemoteDisplayLocalService;
        this.zzcn = z;
    }

    public final void run() {
        this.zzch.zza(this.zzcn);
    }
}
