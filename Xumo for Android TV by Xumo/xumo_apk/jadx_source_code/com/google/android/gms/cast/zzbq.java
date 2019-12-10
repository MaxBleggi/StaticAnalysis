package com.google.android.gms.cast;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

final class zzbq implements ResultCallback<Status> {
    private final long zzgp;
    private final /* synthetic */ zza zzgq;

    zzbq(zza com_google_android_gms_cast_RemoteMediaPlayer_zza, long j) {
        this.zzgq = com_google_android_gms_cast_RemoteMediaPlayer_zza;
        this.zzgp = j;
    }

    public final /* synthetic */ void onResult(@NonNull Result result) {
        Status status = (Status) result;
        if (!status.isSuccess()) {
            this.zzgq.zzfr.zzfl.zza(this.zzgp, status.getStatusCode());
        }
    }
}
