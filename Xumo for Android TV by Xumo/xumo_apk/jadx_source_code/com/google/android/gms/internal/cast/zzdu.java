package com.google.android.gms.internal.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzdu extends zzdx {
    private final /* synthetic */ zzds zzzg;
    private final /* synthetic */ String zzzh;

    zzdu(zzds com_google_android_gms_internal_cast_zzds, GoogleApiClient googleApiClient, String str) {
        this.zzzg = com_google_android_gms_internal_cast_zzds;
        this.zzzh = str;
        super(com_google_android_gms_internal_cast_zzds, googleApiClient);
    }

    public final void zza(zzec com_google_android_gms_internal_cast_zzec) throws RemoteException {
        com_google_android_gms_internal_cast_zzec.zza(new zzdy(this, com_google_android_gms_internal_cast_zzec), this.zzzg.zzzf, this.zzzh);
    }

    public final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzec) anyClient);
    }
}
