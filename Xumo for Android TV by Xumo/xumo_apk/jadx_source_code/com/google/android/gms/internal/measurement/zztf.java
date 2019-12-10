package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

final /* synthetic */ class zztf implements OnSharedPreferenceChangeListener {
    private final zzte zzbsn;

    zztf(zzte com_google_android_gms_internal_measurement_zzte) {
        this.zzbsn = com_google_android_gms_internal_measurement_zzte;
    }

    public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        this.zzbsn.zza(sharedPreferences, str);
    }
}
