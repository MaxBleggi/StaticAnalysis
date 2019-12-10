package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zad<T> extends zac {
    protected final TaskCompletionSource<T> zacm;

    public zad(int i, TaskCompletionSource<T> taskCompletionSource) {
        super(i);
        this.zacm = taskCompletionSource;
    }

    public void zaa(@NonNull zaab com_google_android_gms_common_api_internal_zaab, boolean z) {
    }

    protected abstract void zad(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) throws RemoteException;

    public void zaa(@NonNull Status status) {
        this.zacm.trySetException(new ApiException(status));
    }

    public void zaa(@NonNull RuntimeException runtimeException) {
        this.zacm.trySetException(runtimeException);
    }

    public final void zaa(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) throws DeadObjectException {
        try {
            zad(com_google_android_gms_common_api_internal_GoogleApiManager_zaa_);
        } catch (zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_2) {
            zaa(zab.zaa((RemoteException) com_google_android_gms_common_api_internal_GoogleApiManager_zaa_2));
            throw com_google_android_gms_common_api_internal_GoogleApiManager_zaa_2;
        } catch (zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_22) {
            zaa(zab.zaa((RemoteException) com_google_android_gms_common_api_internal_GoogleApiManager_zaa_22));
        } catch (RuntimeException e) {
            zaa(e);
        }
    }
}
