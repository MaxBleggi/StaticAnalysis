package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzc<TResult, TContinuationResult> implements zzq<TResult> {
    private final Executor zzd;
    private final Continuation<TResult, TContinuationResult> zze;
    private final zzu<TContinuationResult> zzf;

    public zzc(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzu<TContinuationResult> com_google_android_gms_tasks_zzu_TContinuationResult) {
        this.zzd = executor;
        this.zze = continuation;
        this.zzf = com_google_android_gms_tasks_zzu_TContinuationResult;
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzd.execute(new zzd(this, task));
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }
}
