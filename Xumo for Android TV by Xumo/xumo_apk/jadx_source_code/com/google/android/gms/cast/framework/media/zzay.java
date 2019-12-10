package com.google.android.gms.cast.framework.media;

import java.util.TimerTask;

final class zzay extends TimerTask {
    private final /* synthetic */ RemoteMediaClient zzpp;
    private final /* synthetic */ zze zzpv;

    zzay(zze com_google_android_gms_cast_framework_media_RemoteMediaClient_zze, RemoteMediaClient remoteMediaClient) {
        this.zzpv = com_google_android_gms_cast_framework_media_RemoteMediaClient_zze;
        this.zzpp = remoteMediaClient;
    }

    public final void run() {
        this.zzpv.zzpe.zza(this.zzpv.zzpr);
        this.zzpv.zzpe.handler.postDelayed(this, this.zzpv.zzps);
    }
}
