package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.framework.IntroductoryOverlay.zza;
import com.google.android.gms.cast.framework.internal.featurehighlight.zzh;

final class zzo implements zzh {
    final /* synthetic */ zzn zzjk;

    zzo(zzn com_google_android_gms_internal_cast_zzn) {
        this.zzjk = com_google_android_gms_internal_cast_zzn;
    }

    public final void zzao() {
        if (this.zzjk.zzjj) {
            zza.zzd(this.zzjk.zzim);
            this.zzjk.zzji.zzc(new zzp(this));
        }
    }

    public final void dismiss() {
        if (this.zzjk.zzjj) {
            zza.zzd(this.zzjk.zzim);
            this.zzjk.zzji.zzb(new zzq(this));
        }
    }
}
