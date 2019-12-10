package com.google.android.gms.internal.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.util.VisibleForTesting;

@VisibleForTesting
public abstract class zzbu<R extends Result> extends zzcg<R> {
    protected zzdn zzva;

    public zzbu(zzbm com_google_android_gms_internal_cast_zzbm) {
        super(com_google_android_gms_internal_cast_zzbm.zzoy);
    }

    public abstract void execute();

    protected /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        execute();
    }
}
