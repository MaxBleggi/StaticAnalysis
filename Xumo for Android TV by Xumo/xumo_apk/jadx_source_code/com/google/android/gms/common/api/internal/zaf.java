package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zaf extends zad<Void> {
    private final RegisterListenerMethod<AnyClient, ?> zaco;
    private final UnregisterListenerMethod<AnyClient, ?> zacp;

    public zaf(zabw com_google_android_gms_common_api_internal_zabw, TaskCompletionSource<Void> taskCompletionSource) {
        super(3, taskCompletionSource);
        this.zaco = com_google_android_gms_common_api_internal_zabw.zajw;
        this.zacp = com_google_android_gms_common_api_internal_zabw.zajx;
    }

    public final /* bridge */ /* synthetic */ void zaa(@NonNull zaab com_google_android_gms_common_api_internal_zaab, boolean z) {
    }

    public final void zad(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) throws RemoteException {
        this.zaco.registerListener(com_google_android_gms_common_api_internal_GoogleApiManager_zaa_.zaab(), this.zacm);
        if (this.zaco.getListenerKey() != null) {
            com_google_android_gms_common_api_internal_GoogleApiManager_zaa_.zabk().put(this.zaco.getListenerKey(), new zabw(this.zaco, this.zacp));
        }
    }

    @Nullable
    public final Feature[] zab(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) {
        return this.zaco.getRequiredFeatures();
    }

    public final boolean zac(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) {
        return this.zaco.shouldAutoResolveMissingFeatures();
    }
}
