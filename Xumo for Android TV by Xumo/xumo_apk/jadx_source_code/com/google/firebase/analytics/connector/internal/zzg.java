package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.AppMeasurement.OnEventListener;

final class zzg implements OnEventListener {
    private final /* synthetic */ zzf zzbts;

    public zzg(zzf com_google_firebase_analytics_connector_internal_zzf) {
        this.zzbts = com_google_firebase_analytics_connector_internal_zzf;
    }

    public final void onEvent(String str, String str2, Bundle bundle, long j) {
        if (str != null && str.equals(AppMeasurement.CRASH_ORIGIN) == null && zzc.zzfw(str2) != null) {
            str = new Bundle();
            str.putString(JsonKey.NAME, str2);
            str.putLong("timestampInMillis", j);
            str.putBundle("params", bundle);
            this.zzbts.zzbto.onMessageTriggered(3, str);
        }
    }
}
