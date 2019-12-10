package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement.OnEventListener;

final class zze implements OnEventListener {
    private final /* synthetic */ zzd zzbtq;

    public zze(zzd com_google_firebase_analytics_connector_internal_zzd) {
        this.zzbtq = com_google_firebase_analytics_connector_internal_zzd;
    }

    public final void onEvent(String str, String str2, Bundle bundle, long j) {
        if (this.zzbtq.zzbtn.contains(str2) != null) {
            str = new Bundle();
            str.putString("events", zzc.zzfz(str2));
            this.zzbtq.zzbto.onMessageTriggered(2, str);
        }
    }
}
