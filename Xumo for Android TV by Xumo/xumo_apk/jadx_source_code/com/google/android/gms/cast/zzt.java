package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzt extends zza {
    private final /* synthetic */ TaskCompletionSource zzbk;
    private final /* synthetic */ zzs zzbn;

    zzt(zzs com_google_android_gms_cast_zzs, TaskCompletionSource taskCompletionSource) {
        this.zzbn = com_google_android_gms_cast_zzs;
        this.zzbk = taskCompletionSource;
        super();
    }

    public final void onDisconnected() throws RemoteException {
        this.zzbn.zzbj.zzbe.d("onDisconnected", new Object[0]);
        this.zzbn.zzbj.zzb();
        TaskUtil.setResultOrApiException(Status.RESULT_SUCCESS, this.zzbk);
    }

    public final void onError(int i) throws RemoteException {
        this.zzbn.zzbj.zzbe.d("onError: %d", Integer.valueOf(i));
        this.zzbn.zzbj.zzb();
        TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, this.zzbk);
    }
}
