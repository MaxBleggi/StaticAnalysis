package com.google.android.gms.internal.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

final class zzck extends TaskApiCall<zzcm, Void> {
    private final /* synthetic */ List zzpl;
    private final /* synthetic */ String[] zzwe;
    private final /* synthetic */ String zzwf;

    zzck(zzci com_google_android_gms_internal_cast_zzci, String[] strArr, String str, List list) {
        this.zzwe = strArr;
        this.zzwf = str;
        this.zzpl = list;
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzcm com_google_android_gms_internal_cast_zzcm = (zzcm) anyClient;
        ((zzde) com_google_android_gms_internal_cast_zzcm.getService()).zza(new zzcl(this, taskCompletionSource), this.zzwe, this.zzwf, this.zzpl);
    }
}
