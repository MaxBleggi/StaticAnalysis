package com.google.android.gms.internal.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public final class zzdz extends zzdw {
    private final /* synthetic */ zzdx zzzj;

    protected zzdz(zzdx com_google_android_gms_internal_cast_zzdx) {
        this.zzzj = com_google_android_gms_internal_cast_zzdx;
    }

    public final void onDisconnected() throws RemoteException {
        zzds.zzbe.d("onDisconnected", new Object[0]);
        this.zzzj.zzzg.zzb();
        this.zzzj.setResult(new zzea(Status.RESULT_SUCCESS));
    }

    public final void onError(int i) throws RemoteException {
        zzds.zzbe.d("onError: %d", Integer.valueOf(i));
        this.zzzj.zzzg.zzb();
        this.zzzj.setResult(new zzea(Status.RESULT_INTERNAL_ERROR));
    }
}
