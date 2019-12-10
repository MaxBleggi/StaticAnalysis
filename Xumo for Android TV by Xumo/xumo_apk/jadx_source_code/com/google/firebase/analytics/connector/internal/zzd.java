package com.google.firebase.analytics.connector.internal;

import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.connector.AnalyticsConnector.AnalyticsConnectorListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class zzd implements zza {
    private AppMeasurement zzbtb;
    Set<String> zzbtn;
    private AnalyticsConnectorListener zzbto;
    private zze zzbtp = new zze(this);

    public zzd(AppMeasurement appMeasurement, AnalyticsConnectorListener analyticsConnectorListener) {
        this.zzbto = analyticsConnectorListener;
        this.zzbtb = appMeasurement;
        this.zzbtb.registerOnMeasurementEventListener(this.zzbtp);
        this.zzbtn = new HashSet();
    }

    public final AnalyticsConnectorListener zztv() {
        return this.zzbto;
    }

    public final void registerEventNames(Set<String> set) {
        this.zzbtn.clear();
        Set set2 = this.zzbtn;
        Collection hashSet = new HashSet();
        for (String str : set) {
            if (hashSet.size() >= 50) {
                break;
            } else if (zzc.zzfy(str) && zzc.zzfx(str)) {
                hashSet.add(zzc.zzga(str));
            }
        }
        set2.addAll(hashSet);
    }

    public final void unregisterEventNames() {
        this.zzbtn.clear();
    }
}
