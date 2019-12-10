package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.games.GameManagerClient.GameManagerResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.VisibleForTesting;

@VisibleForTesting
public abstract class zzbs extends zzbu<GameManagerResult> {
    final /* synthetic */ zzbm zzut;

    public zzbs(zzbm com_google_android_gms_internal_cast_zzbm) {
        this.zzut = com_google_android_gms_internal_cast_zzbm;
        super(com_google_android_gms_internal_cast_zzbm);
        this.zzva = new zzbt(this, com_google_android_gms_internal_cast_zzbm);
    }

    public static GameManagerResult a_(Status status) {
        return new zzby(status, null, -1, null);
    }

    public /* synthetic */ Result createFailedResult(Status status) {
        return a_(status);
    }
}
