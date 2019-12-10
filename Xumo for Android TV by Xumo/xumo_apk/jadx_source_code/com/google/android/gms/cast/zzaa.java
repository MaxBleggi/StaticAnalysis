package com.google.android.gms.cast;

import androidx.annotation.NonNull;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

final class zzaa implements OnCompleteListener<Void> {
    private final /* synthetic */ CastRemoteDisplayLocalService zzch;

    zzaa(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zzch = castRemoteDisplayLocalService;
    }

    public final void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful() == null) {
            this.zzch.zzb("Unable to stop the remote display, result unsuccessful");
            if (this.zzch.zzbr.get() != null) {
                ((Callbacks) this.zzch.zzbr.get()).onRemoteDisplaySessionError(new Status(CastStatusCodes.ERROR_STOPPING_SERVICE_FAILED));
            }
        } else {
            this.zzch.zzb("remote display stopped");
        }
        this.zzch.zzby = null;
    }
}
