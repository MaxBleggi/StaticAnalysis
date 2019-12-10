package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.concurrent.Executor;

final class zze<TResult, TContinuationResult> implements OnCanceledListener, OnFailureListener, OnSuccessListener<TContinuationResult>, zzq<TResult> {
    private final Executor zzd;
    private final Continuation<TResult, Task<TContinuationResult>> zze;
    private final zzu<TContinuationResult> zzf;

    public zze(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation, @NonNull zzu<TContinuationResult> com_google_android_gms_tasks_zzu_TContinuationResult) {
        this.zzd = executor;
        this.zze = continuation;
        this.zzf = com_google_android_gms_tasks_zzu_TContinuationResult;
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzd.execute(new zzf(this, task));
    }

    public final void onSuccess(TContinuationResult tContinuationResult) {
        this.zzf.setResult(tContinuationResult);
    }

    public final void onFailure(@NonNull Exception exception) {
        this.zzf.setException(exception);
    }

    public final void onCanceled() {
        this.zzf.zza();
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }
}
