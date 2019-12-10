package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerClient.GameManagerInstanceResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.VisibleForTesting;

@VisibleForTesting
public abstract class zzbv extends zzbu<GameManagerInstanceResult> {
    final /* synthetic */ zzbm zzut;
    private GameManagerClient zzvb;

    public zzbv(zzbm com_google_android_gms_internal_cast_zzbm, GameManagerClient gameManagerClient) {
        this.zzut = com_google_android_gms_internal_cast_zzbm;
        super(com_google_android_gms_internal_cast_zzbm);
        this.zzvb = gameManagerClient;
        this.zzva = new zzbw(this, com_google_android_gms_internal_cast_zzbm);
    }

    public static GameManagerInstanceResult zzc(Status status) {
        return new zzbx(status, null);
    }

    public /* synthetic */ Result createFailedResult(Status status) {
        return zzc(status);
    }
}
