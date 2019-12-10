package com.google.android.gms.internal.cast;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

final class zzbr implements ResultCallback<Status> {
    private final /* synthetic */ zzbm zzut;
    private final /* synthetic */ long zzux;

    zzbr(zzbm com_google_android_gms_internal_cast_zzbm, long j) {
        this.zzut = com_google_android_gms_internal_cast_zzbm;
        this.zzux = j;
    }

    public final /* synthetic */ void onResult(Result result) {
        Status status = (Status) result;
        if (!status.isSuccess()) {
            this.zzut.zza(this.zzux, status.getStatusCode());
        }
    }
}
