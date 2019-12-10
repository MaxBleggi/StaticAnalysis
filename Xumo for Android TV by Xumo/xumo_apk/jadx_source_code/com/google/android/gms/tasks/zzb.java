package com.google.android.gms.tasks;

final class zzb implements OnSuccessListener<Void> {
    private final /* synthetic */ OnTokenCanceledListener zzb;

    zzb(zza com_google_android_gms_tasks_zza, OnTokenCanceledListener onTokenCanceledListener) {
        this.zzb = onTokenCanceledListener;
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzb.onCanceled();
    }
}
