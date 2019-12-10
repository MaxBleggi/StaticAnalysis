package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.internal.cast.zzeb;
import com.google.android.gms.internal.cast.zzeg;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzs extends TaskApiCall<zzeb, Void> {
    final /* synthetic */ CastRemoteDisplayClient zzbj;

    zzs(CastRemoteDisplayClient castRemoteDisplayClient) {
        this.zzbj = castRemoteDisplayClient;
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzeb com_google_android_gms_internal_cast_zzeb = (zzeb) anyClient;
        ((zzeg) com_google_android_gms_internal_cast_zzeb.getService()).zza(new zzt(this, taskCompletionSource));
    }
}
