package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

final class zztu extends zztt {
    private final TaskCompletionSource<ShortDynamicLink> zzbuh;

    zztu(TaskCompletionSource<ShortDynamicLink> taskCompletionSource) {
        this.zzbuh = taskCompletionSource;
    }

    public final void zza(Status status, zzuc com_google_android_gms_internal_measurement_zzuc) {
        TaskUtil.setResultOrApiException(status, com_google_android_gms_internal_measurement_zzuc, this.zzbuh);
    }
}
