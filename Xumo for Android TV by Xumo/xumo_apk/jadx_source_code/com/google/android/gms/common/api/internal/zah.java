package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zah extends zad<Boolean> {
    private final ListenerKey<?> zacs;

    public zah(ListenerKey<?> listenerKey, TaskCompletionSource<Boolean> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zacs = listenerKey;
    }

    public final /* bridge */ /* synthetic */ void zaa(@NonNull zaab com_google_android_gms_common_api_internal_zaab, boolean z) {
    }

    public final void zad(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) throws RemoteException {
        zabw com_google_android_gms_common_api_internal_zabw = (zabw) com_google_android_gms_common_api_internal_GoogleApiManager_zaa_.zabk().remove(this.zacs);
        if (com_google_android_gms_common_api_internal_zabw != null) {
            com_google_android_gms_common_api_internal_zabw.zajx.unregisterListener(com_google_android_gms_common_api_internal_GoogleApiManager_zaa_.zaab(), this.zacm);
            com_google_android_gms_common_api_internal_zabw.zajw.clearListener();
            return;
        }
        this.zacm.trySetResult(Boolean.valueOf(false));
    }

    @Nullable
    public final Feature[] zab(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) {
        zabw com_google_android_gms_common_api_internal_zabw = (zabw) com_google_android_gms_common_api_internal_GoogleApiManager_zaa_.zabk().get(this.zacs);
        if (com_google_android_gms_common_api_internal_zabw == null) {
            return null;
        }
        return com_google_android_gms_common_api_internal_zabw.zajw.getRequiredFeatures();
    }

    public final boolean zac(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) {
        zabw com_google_android_gms_common_api_internal_zabw = (zabw) com_google_android_gms_common_api_internal_GoogleApiManager_zaa_.zabk().get(this.zacs);
        return (com_google_android_gms_common_api_internal_zabw == null || com_google_android_gms_common_api_internal_zabw.zajw.shouldAutoResolveMissingFeatures() == null) ? null : true;
    }
}
