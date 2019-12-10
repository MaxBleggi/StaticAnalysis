package com.google.android.gms.internal.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzdv extends zzdx {
    zzdv(zzds com_google_android_gms_internal_cast_zzds, GoogleApiClient googleApiClient) {
        super(com_google_android_gms_internal_cast_zzds, googleApiClient);
    }

    public final void zza(zzec com_google_android_gms_internal_cast_zzec) throws RemoteException {
        com_google_android_gms_internal_cast_zzec.zza(new zzdz(this));
    }

    public final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzec) anyClient);
    }
}
