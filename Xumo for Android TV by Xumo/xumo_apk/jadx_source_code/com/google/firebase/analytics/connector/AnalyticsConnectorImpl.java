package com.google.firebase.analytics.connector;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.internal.zzal;
import com.google.android.gms.measurement.internal.zzbu;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector.AnalyticsConnectorHandle;
import com.google.firebase.analytics.connector.AnalyticsConnector.AnalyticsConnectorListener;
import com.google.firebase.analytics.connector.AnalyticsConnector.ConditionalUserProperty;
import com.google.firebase.analytics.connector.internal.zza;
import com.google.firebase.analytics.connector.internal.zzc;
import com.google.firebase.analytics.connector.internal.zzd;
import com.google.firebase.analytics.connector.internal.zzf;
import com.google.firebase.events.Event;
import com.google.firebase.events.Subscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AnalyticsConnectorImpl implements AnalyticsConnector {
    private static volatile AnalyticsConnector zzbta;
    @VisibleForTesting
    private final AppMeasurement zzbtb;
    @VisibleForTesting
    final Map<String, zza> zzbtc = new ConcurrentHashMap();

    private AnalyticsConnectorImpl(AppMeasurement appMeasurement) {
        Preconditions.checkNotNull(appMeasurement);
        this.zzbtb = appMeasurement;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    @KeepForSdk
    public static AnalyticsConnector getInstance(FirebaseApp firebaseApp, Context context, Subscriber subscriber) {
        Preconditions.checkNotNull(firebaseApp);
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(subscriber);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzbta == null) {
            synchronized (AnalyticsConnectorImpl.class) {
                if (zzbta == null) {
                    Bundle bundle = new Bundle(1);
                    if (firebaseApp.isDefaultApp()) {
                        subscriber.subscribe(DataCollectionDefaultChange.class, zza.zzbtd, zzb.zzbte);
                        bundle.putBoolean("dataCollectionDefaultEnabled", firebaseApp.isDataCollectionDefaultEnabled());
                    }
                    zzbta = new AnalyticsConnectorImpl(zzbu.zza(context, zzal.zzc(bundle)).zzkm());
                }
            }
        }
        return zzbta;
    }

    @KeepForSdk
    public static AnalyticsConnector getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @KeepForSdk
    public static AnalyticsConnector getInstance(FirebaseApp firebaseApp) {
        return (AnalyticsConnector) firebaseApp.get(AnalyticsConnector.class);
    }

    @KeepForSdk
    public void logEvent(@NonNull String str, @NonNull String str2, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (zzc.zzfv(str) && zzc.zza(str2, bundle) && zzc.zzb(str, str2, bundle)) {
            this.zzbtb.logEventInternal(str, str2, bundle);
        }
    }

    @KeepForSdk
    public void setUserProperty(@NonNull String str, @NonNull String str2, Object obj) {
        if (zzc.zzfv(str) && zzc.zzz(str, str2)) {
            this.zzbtb.setUserPropertyInternal(str, str2, obj);
        }
    }

    @WorkerThread
    @KeepForSdk
    public Map<String, Object> getUserProperties(boolean z) {
        return this.zzbtb.getUserProperties(z);
    }

    @WorkerThread
    @KeepForSdk
    public AnalyticsConnectorHandle registerAnalyticsConnectorListener(@NonNull final String str, AnalyticsConnectorListener analyticsConnectorListener) {
        Preconditions.checkNotNull(analyticsConnectorListener);
        if (!zzc.zzfv(str) || zzfu(str)) {
            return null;
        }
        AppMeasurement appMeasurement = this.zzbtb;
        Object com_google_firebase_analytics_connector_internal_zzd = AppMeasurement.FIAM_ORIGIN.equals(str) ? new zzd(appMeasurement, analyticsConnectorListener) : AppMeasurement.CRASH_ORIGIN.equals(str) ? new zzf(appMeasurement, analyticsConnectorListener) : null;
        if (com_google_firebase_analytics_connector_internal_zzd == null) {
            return null;
        }
        this.zzbtc.put(str, com_google_firebase_analytics_connector_internal_zzd);
        return new AnalyticsConnectorHandle(this) {
            private final /* synthetic */ AnalyticsConnectorImpl zzbtf;

            public void unregister() {
                if (this.zzbtf.zzfu(str)) {
                    AnalyticsConnectorListener zztv = ((zza) this.zzbtf.zzbtc.get(str)).zztv();
                    if (zztv != null) {
                        zztv.onMessageTriggered(0, null);
                    }
                    this.zzbtf.zzbtc.remove(str);
                }
            }

            @KeepForSdk
            public void registerEventNames(Set<String> set) {
                if (this.zzbtf.zzfu(str) && str.equals(AppMeasurement.FIAM_ORIGIN) && set != null) {
                    if (!set.isEmpty()) {
                        ((zza) this.zzbtf.zzbtc.get(str)).registerEventNames(set);
                    }
                }
            }

            @KeepForSdk
            public void unregisterEventNames() {
                if (this.zzbtf.zzfu(str)) {
                    if (str.equals(AppMeasurement.FIAM_ORIGIN)) {
                        ((zza) this.zzbtf.zzbtc.get(str)).unregisterEventNames();
                    }
                }
            }
        };
    }

    @KeepForSdk
    public void setConditionalUserProperty(@NonNull ConditionalUserProperty conditionalUserProperty) {
        if (zzc.zza(conditionalUserProperty)) {
            this.zzbtb.setConditionalUserProperty(zzc.zzb(conditionalUserProperty));
        }
    }

    @KeepForSdk
    public void clearConditionalUserProperty(@Size(max = 24, min = 1) @NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        if (str2 == null || zzc.zza(str2, bundle)) {
            this.zzbtb.clearConditionalUserProperty(str, str2, bundle);
        }
    }

    @WorkerThread
    @KeepForSdk
    public List<ConditionalUserProperty> getConditionalUserProperties(@NonNull String str, @Size(max = 23, min = 1) @Nullable String str2) {
        List<ConditionalUserProperty> arrayList = new ArrayList();
        for (AppMeasurement.ConditionalUserProperty zzd : this.zzbtb.getConditionalUserProperties(str, str2)) {
            arrayList.add(zzc.zzd(zzd));
        }
        return arrayList;
    }

    @WorkerThread
    @KeepForSdk
    public int getMaxUserProperties(@Size(min = 1) @NonNull String str) {
        return this.zzbtb.getMaxUserProperties(str);
    }

    private final boolean zzfu(@NonNull String str) {
        return (str.isEmpty() || !this.zzbtc.containsKey(str) || this.zzbtc.get(str) == null) ? null : true;
    }

    static final /* synthetic */ void zza(Event event) {
        event = ((DataCollectionDefaultChange) event.getPayload()).enabled;
        synchronized (AnalyticsConnectorImpl.class) {
            ((AnalyticsConnectorImpl) zzbta).zzbtb.zzd(event);
        }
    }
}
