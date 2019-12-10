package com.google.android.gms.internal.cast;

import android.graphics.Bitmap;

final class zzas implements zzy {
    private final /* synthetic */ zzar zzro;

    zzas(zzar com_google_android_gms_internal_cast_zzar) {
        this.zzro = com_google_android_gms_internal_cast_zzar;
    }

    public final void zza(Bitmap bitmap) {
        if (bitmap != null) {
            if (this.zzro.zzrn != null) {
                this.zzro.zzrn.setVisibility(4);
            }
            this.zzro.zzrj.setVisibility(0);
            this.zzro.zzrj.setImageBitmap(bitmap);
        }
    }
}
