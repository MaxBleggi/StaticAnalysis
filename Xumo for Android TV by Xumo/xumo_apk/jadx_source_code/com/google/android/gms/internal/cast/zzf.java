package com.google.android.gms.internal.cast;

import android.content.Context;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionProvider;

public final class zzf extends SessionProvider {
    private final CastOptions zzhn;
    private final zzw zzjg;

    public zzf(Context context, CastOptions castOptions, zzw com_google_android_gms_internal_cast_zzw) {
        String categoryForCast;
        if (castOptions.getSupportedNamespaces().isEmpty()) {
            categoryForCast = CastMediaControlIntent.categoryForCast(castOptions.getReceiverApplicationId());
        } else {
            categoryForCast = CastMediaControlIntent.categoryForCast(castOptions.getReceiverApplicationId(), castOptions.getSupportedNamespaces());
        }
        super(context, categoryForCast);
        this.zzhn = castOptions;
        this.zzjg = com_google_android_gms_internal_cast_zzw;
    }

    public final Session createSession(String str) {
        return new CastSession(getContext(), getCategory(), str, this.zzhn, Cast.CastApi, new zzg(), new zzai(getContext(), this.zzhn, this.zzjg));
    }

    public final boolean isSessionRecoverable() {
        return this.zzhn.getResumeSavedSession();
    }
}
