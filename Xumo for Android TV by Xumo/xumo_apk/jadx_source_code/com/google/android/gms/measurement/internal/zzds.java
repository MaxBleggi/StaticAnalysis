package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.MainThread;

@TargetApi(14)
@MainThread
final class zzds implements ActivityLifecycleCallbacks {
    private final /* synthetic */ zzcy zzarr;

    private zzds(zzcy com_google_android_gms_measurement_internal_zzcy) {
        this.zzarr = com_google_android_gms_measurement_internal_zzcy;
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        try {
            this.zzarr.zzgt().zzjo().zzca("onActivityCreated");
            Intent intent = activity.getIntent();
            if (intent != null) {
                Uri data = intent.getData();
                if (data != null && data.isHierarchical()) {
                    if (bundle == null) {
                        Bundle zza = this.zzarr.zzgr().zza(data);
                        this.zzarr.zzgr();
                        String str = zzfu.zzd(intent) ? "gs" : "auto";
                        if (zza != null) {
                            this.zzarr.logEvent(str, "_cmp", zza);
                        }
                    }
                    Object queryParameter = data.getQueryParameter("referrer");
                    if (!TextUtils.isEmpty(queryParameter)) {
                        Object obj = (queryParameter.contains("gclid") && (queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium") || queryParameter.contains("utm_term") || queryParameter.contains("utm_content"))) ? 1 : null;
                        if (obj == null) {
                            this.zzarr.zzgt().zzjn().zzca("Activity created with data 'referrer' param without gclid and at least one utm field");
                            return;
                        }
                        this.zzarr.zzgt().zzjn().zzg("Activity created with referrer", queryParameter);
                        if (!TextUtils.isEmpty(queryParameter)) {
                            this.zzarr.zzb("auto", "_ldl", queryParameter, true);
                        }
                    } else {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            this.zzarr.zzgt().zzjg().zzg("Throwable caught in onActivityCreated", e);
        }
        this.zzarr.zzgm().onActivityCreated(activity, bundle);
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zzarr.zzgm().onActivityDestroyed(activity);
    }

    @MainThread
    public final void onActivityPaused(Activity activity) {
        this.zzarr.zzgm().onActivityPaused(activity);
        activity = this.zzarr.zzgo();
        activity.zzgs().zzc(new zzfe(activity, activity.zzbx().elapsedRealtime()));
    }

    @MainThread
    public final void onActivityResumed(Activity activity) {
        this.zzarr.zzgm().onActivityResumed(activity);
        activity = this.zzarr.zzgo();
        activity.zzgs().zzc(new zzfd(activity, activity.zzbx().elapsedRealtime()));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zzarr.zzgm().onActivitySaveInstanceState(activity, bundle);
    }
}
