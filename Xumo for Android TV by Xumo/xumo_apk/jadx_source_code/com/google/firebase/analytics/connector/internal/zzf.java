package com.google.firebase.analytics.connector.internal;

import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.connector.AnalyticsConnector.AnalyticsConnectorListener;
import java.util.Set;

public final class zzf implements zza {
    private AppMeasurement zzbtb;
    private AnalyticsConnectorListener zzbto;
    private zzg zzbtr = new zzg(this);

    public zzf(AppMeasurement appMeasurement, AnalyticsConnectorListener analyticsConnectorListener) {
        this.zzbto = analyticsConnectorListener;
        this.zzbtb = appMeasurement;
        this.zzbtb.registerOnMeasurementEventListener(this.zzbtr);
    }

    public final void registerEventNames(Set<String> set) {
    }

    public final void unregisterEventNames() {
    }

    public final AnalyticsConnectorListener zztv() {
        return this.zzbto;
    }
}
