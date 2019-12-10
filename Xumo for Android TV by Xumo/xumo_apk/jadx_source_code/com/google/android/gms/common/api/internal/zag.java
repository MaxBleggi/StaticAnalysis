package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zag<ResultT> extends zac {
    private final TaskCompletionSource<ResultT> zacm;
    private final TaskApiCall<AnyClient, ResultT> zacq;
    private final StatusExceptionMapper zacr;

    public zag(int i, TaskApiCall<AnyClient, ResultT> taskApiCall, TaskCompletionSource<ResultT> taskCompletionSource, StatusExceptionMapper statusExceptionMapper) {
        super(i);
        this.zacm = taskCompletionSource;
        this.zacq = taskApiCall;
        this.zacr = statusExceptionMapper;
    }

    public final void zaa(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) throws DeadObjectException {
        try {
            this.zacq.doExecute(com_google_android_gms_common_api_internal_GoogleApiManager_zaa_.zaab(), this.zacm);
        } catch (zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_2) {
            throw com_google_android_gms_common_api_internal_GoogleApiManager_zaa_2;
        } catch (zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_22) {
            zaa(zab.zaa((RemoteException) com_google_android_gms_common_api_internal_GoogleApiManager_zaa_22));
        } catch (RuntimeException e) {
            zaa(e);
        }
    }

    public final void zaa(@NonNull Status status) {
        this.zacm.trySetException(this.zacr.getException(status));
    }

    public final void zaa(@NonNull RuntimeException runtimeException) {
        this.zacm.trySetException(runtimeException);
    }

    public final void zaa(@NonNull zaab com_google_android_gms_common_api_internal_zaab, boolean z) {
        com_google_android_gms_common_api_internal_zaab.zaa(this.zacm, z);
    }

    @Nullable
    public final Feature[] zab(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) {
        return this.zacq.zabt();
    }

    public final boolean zac(zaa<?> com_google_android_gms_common_api_internal_GoogleApiManager_zaa_) {
        return this.zacq.shouldAutoResolveMissingFeatures();
    }
}
