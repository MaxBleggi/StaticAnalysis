package com.google.android.gms.internal.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplaySessionResult;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.util.VisibleForTesting;

@VisibleForTesting
class zzdx extends ApiMethodImpl<CastRemoteDisplaySessionResult, zzec> {
    final /* synthetic */ zzds zzzg;

    public zzdx(zzds com_google_android_gms_internal_cast_zzds, GoogleApiClient googleApiClient) {
        this.zzzg = com_google_android_gms_internal_cast_zzds;
        super(com_google_android_gms_internal_cast_zzds.zzze, googleApiClient);
    }

    @VisibleForTesting
    public void zza(zzec com_google_android_gms_internal_cast_zzec) throws RemoteException {
    }

    @VisibleForTesting
    public /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzec) anyClient);
    }

    protected /* synthetic */ Result createFailedResult(Status status) {
        return new zzea(status);
    }
}
