package com.google.android.gms.common.api.internal;

import androidx.annotation.BinderThread;
import com.google.android.gms.signin.internal.zac;
import com.google.android.gms.signin.internal.zaj;
import java.lang.ref.WeakReference;

final class zaar extends zac {
    private final WeakReference<zaak> zagj;

    zaar(zaak com_google_android_gms_common_api_internal_zaak) {
        this.zagj = new WeakReference(com_google_android_gms_common_api_internal_zaak);
    }

    @BinderThread
    public final void zab(zaj com_google_android_gms_signin_internal_zaj) {
        zaak com_google_android_gms_common_api_internal_zaak = (zaak) this.zagj.get();
        if (com_google_android_gms_common_api_internal_zaak != null) {
            com_google_android_gms_common_api_internal_zaak.zafs.zaa(new zaas(this, com_google_android_gms_common_api_internal_zaak, com_google_android_gms_common_api_internal_zaak, com_google_android_gms_signin_internal_zaj));
        }
    }
}
