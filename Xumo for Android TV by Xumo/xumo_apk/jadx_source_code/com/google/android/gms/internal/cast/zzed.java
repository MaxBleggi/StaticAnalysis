package com.google.android.gms.internal.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

final class zzed extends zzej {
    private final /* synthetic */ zzei zzzm;
    private final /* synthetic */ zzec zzzn;

    zzed(zzec com_google_android_gms_internal_cast_zzec, zzei com_google_android_gms_internal_cast_zzei) {
        this.zzzn = com_google_android_gms_internal_cast_zzec;
        this.zzzm = com_google_android_gms_internal_cast_zzei;
    }

    public final void zzr(int i) throws RemoteException {
        zzec.zzbe.d("onRemoteDisplayEnded", new Object[0]);
        if (this.zzzm != null) {
            this.zzzm.zzr(i);
        }
        if (this.zzzn.zzzk != null) {
            this.zzzn.zzzk.onRemoteDisplayEnded(new Status(i));
        }
    }
}
