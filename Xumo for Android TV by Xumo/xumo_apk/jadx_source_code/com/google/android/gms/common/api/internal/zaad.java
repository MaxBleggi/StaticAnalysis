package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zaad implements OnCompleteListener<TResult> {
    private final /* synthetic */ zaab zafm;
    private final /* synthetic */ TaskCompletionSource zafn;

    zaad(zaab com_google_android_gms_common_api_internal_zaab, TaskCompletionSource taskCompletionSource) {
        this.zafm = com_google_android_gms_common_api_internal_zaab;
        this.zafn = taskCompletionSource;
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zafm.zafk.remove(this.zafn);
    }
}
