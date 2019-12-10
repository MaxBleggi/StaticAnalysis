package com.google.android.gms.cast;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Display;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.internal.cast.zzeb;
import com.google.android.gms.internal.cast.zzeg;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzq extends TaskApiCall<zzeb, Display> {
    private final /* synthetic */ String zzaf;
    private final /* synthetic */ int zzbg;
    private final /* synthetic */ PendingIntent zzbh;
    private final /* synthetic */ CastDevice zzbi;
    final /* synthetic */ CastRemoteDisplayClient zzbj;

    zzq(CastRemoteDisplayClient castRemoteDisplayClient, int i, PendingIntent pendingIntent, CastDevice castDevice, String str) {
        this.zzbj = castRemoteDisplayClient;
        this.zzbg = i;
        this.zzbh = pendingIntent;
        this.zzbi = castDevice;
        this.zzaf = str;
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzeb com_google_android_gms_internal_cast_zzeb = (zzeb) anyClient;
        Bundle bundle = new Bundle();
        bundle.putInt("configuration", this.zzbg);
        ((zzeg) com_google_android_gms_internal_cast_zzeb.getService()).zza(new zzr(this, taskCompletionSource, com_google_android_gms_internal_cast_zzeb), this.zzbh, this.zzbi.getDeviceId(), this.zzaf, bundle);
    }
}
