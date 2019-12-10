package com.google.android.gms.cast.framework.media;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

final class zzau implements ResultCallback<Status> {
    private final long zzgp;
    private final /* synthetic */ zza zzpn;

    zzau(zza com_google_android_gms_cast_framework_media_RemoteMediaClient_zza, long j) {
        this.zzpn = com_google_android_gms_cast_framework_media_RemoteMediaClient_zza;
        this.zzgp = j;
    }

    public final /* synthetic */ void onResult(@NonNull Result result) {
        Status status = (Status) result;
        if (!status.isSuccess()) {
            this.zzpn.zzpe.zzfl.zza(this.zzgp, status.getStatusCode());
        }
    }
}
