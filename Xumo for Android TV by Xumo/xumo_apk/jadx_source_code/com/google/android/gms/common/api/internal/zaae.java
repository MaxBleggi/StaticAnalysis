package com.google.android.gms.common.api.internal;

import android.app.Activity;
import androidx.collection.ArraySet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Preconditions;

public class zaae extends zal {
    private GoogleApiManager zabm;
    private final ArraySet<zai<?>> zafo = new ArraySet();

    public static void zaa(Activity activity, GoogleApiManager googleApiManager, zai<?> com_google_android_gms_common_api_internal_zai_) {
        activity = LifecycleCallback.getFragment(activity);
        zaae com_google_android_gms_common_api_internal_zaae = (zaae) activity.getCallbackOrNull("ConnectionlessLifecycleHelper", zaae.class);
        if (com_google_android_gms_common_api_internal_zaae == null) {
            com_google_android_gms_common_api_internal_zaae = new zaae(activity);
        }
        com_google_android_gms_common_api_internal_zaae.zabm = googleApiManager;
        Preconditions.checkNotNull(com_google_android_gms_common_api_internal_zai_, "ApiKey cannot be null");
        com_google_android_gms_common_api_internal_zaae.zafo.add(com_google_android_gms_common_api_internal_zai_);
        googleApiManager.zaa(com_google_android_gms_common_api_internal_zaae);
    }

    private zaae(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.mLifecycleFragment.addCallback("ConnectionlessLifecycleHelper", this);
    }

    public void onStart() {
        super.onStart();
        zaak();
    }

    public void onResume() {
        super.onResume();
        zaak();
    }

    public void onStop() {
        super.onStop();
        this.zabm.zab(this);
    }

    protected final void zaa(ConnectionResult connectionResult, int i) {
        this.zabm.zaa(connectionResult, i);
    }

    protected final void zao() {
        this.zabm.zao();
    }

    final ArraySet<zai<?>> zaaj() {
        return this.zafo;
    }

    private final void zaak() {
        if (!this.zafo.isEmpty()) {
            this.zabm.zaa(this);
        }
    }
}
