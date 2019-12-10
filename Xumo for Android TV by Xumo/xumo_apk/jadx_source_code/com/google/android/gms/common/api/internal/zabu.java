package com.google.android.gms.common.api.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

public class zabu extends zal {
    private TaskCompletionSource<Void> zajo = new TaskCompletionSource();

    public static zabu zac(Activity activity) {
        activity = LifecycleCallback.getFragment(activity);
        zabu com_google_android_gms_common_api_internal_zabu = (zabu) activity.getCallbackOrNull("GmsAvailabilityHelper", zabu.class);
        if (com_google_android_gms_common_api_internal_zabu == null) {
            return new zabu(activity);
        }
        if (com_google_android_gms_common_api_internal_zabu.zajo.getTask().isComplete() != null) {
            com_google_android_gms_common_api_internal_zabu.zajo = new TaskCompletionSource();
        }
        return com_google_android_gms_common_api_internal_zabu;
    }

    private zabu(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.mLifecycleFragment.addCallback("GmsAvailabilityHelper", this);
    }

    protected final void zaa(ConnectionResult connectionResult, int i) {
        this.zajo.setException(ApiExceptionUtil.fromStatus(new Status(connectionResult.getErrorCode(), connectionResult.getErrorMessage(), connectionResult.getResolution())));
    }

    protected final void zao() {
        int isGooglePlayServicesAvailable = this.zacc.isGooglePlayServicesAvailable(this.mLifecycleFragment.getLifecycleActivity());
        if (isGooglePlayServicesAvailable == 0) {
            this.zajo.setResult(null);
            return;
        }
        if (!this.zajo.getTask().isComplete()) {
            zab(new ConnectionResult(isGooglePlayServicesAvailable, null), 0);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.zajo.trySetException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }

    public final Task<Void> getTask() {
        return this.zajo.getTask();
    }
}
