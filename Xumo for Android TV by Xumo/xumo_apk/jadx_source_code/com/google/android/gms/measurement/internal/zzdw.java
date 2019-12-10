package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Map;

public final class zzdw extends zzf {
    @VisibleForTesting
    protected zzdv zzasd;
    private volatile zzdv zzase;
    private zzdv zzasf;
    private final Map<Activity, zzdv> zzasg = new ArrayMap();
    private zzdv zzash;
    private String zzasi;

    public zzdw(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
    }

    protected final boolean zzgy() {
        return false;
    }

    @WorkerThread
    public final zzdv zzle() {
        zzcl();
        zzaf();
        return this.zzasd;
    }

    public final void setCurrentScreen(@NonNull Activity activity, @Size(max = 36, min = 1) @Nullable String str, @Size(max = 36, min = 1) @Nullable String str2) {
        if (this.zzase == null) {
            zzgt().zzjj().zzca("setCurrentScreen cannot be called while no activity active");
        } else if (this.zzasg.get(activity) == null) {
            zzgt().zzjj().zzca("setCurrentScreen must be called with an activity in the activity lifecycle");
        } else {
            if (str2 == null) {
                str2 = zzcs(activity.getClass().getCanonicalName());
            }
            boolean equals = this.zzase.zzasa.equals(str2);
            boolean zzv = zzfu.zzv(this.zzase.zzuw, str);
            if (equals && zzv) {
                zzgt().zzjl().zzca("setCurrentScreen cannot be called with the same class and name");
            } else if (str != null && (str.length() <= 0 || str.length() > 100)) {
                zzgt().zzjj().zzg("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            } else if (str2 == null || (str2.length() > 0 && str2.length() <= 100)) {
                zzgt().zzjo().zze("Setting current screen to name, class", str == null ? "null" : str, str2);
                zzdv com_google_android_gms_measurement_internal_zzdv = new zzdv(str, str2, zzgr().zzmj());
                this.zzasg.put(activity, com_google_android_gms_measurement_internal_zzdv);
                zza(activity, com_google_android_gms_measurement_internal_zzdv, true);
            } else {
                zzgt().zzjj().zzg("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            }
        }
    }

    public final zzdv zzlf() {
        zzgg();
        return this.zzase;
    }

    @MainThread
    private final void zza(Activity activity, zzdv com_google_android_gms_measurement_internal_zzdv, boolean z) {
        zzdv com_google_android_gms_measurement_internal_zzdv2 = this.zzase == null ? this.zzasf : this.zzase;
        if (com_google_android_gms_measurement_internal_zzdv.zzasa == null) {
            com_google_android_gms_measurement_internal_zzdv = new zzdv(com_google_android_gms_measurement_internal_zzdv.zzuw, zzcs(activity.getClass().getCanonicalName()), com_google_android_gms_measurement_internal_zzdv.zzasb);
        }
        this.zzasf = this.zzase;
        this.zzase = com_google_android_gms_measurement_internal_zzdv;
        zzgs().zzc(new zzdx(this, z, com_google_android_gms_measurement_internal_zzdv2, com_google_android_gms_measurement_internal_zzdv));
    }

    @WorkerThread
    private final void zza(@NonNull zzdv com_google_android_gms_measurement_internal_zzdv, boolean z) {
        zzgi().zzq(zzbx().elapsedRealtime());
        if (zzgo().zza(com_google_android_gms_measurement_internal_zzdv.zzasc, z)) {
            com_google_android_gms_measurement_internal_zzdv.zzasc = false;
        }
    }

    public static void zza(zzdv com_google_android_gms_measurement_internal_zzdv, Bundle bundle, boolean z) {
        if (bundle == null || com_google_android_gms_measurement_internal_zzdv == null || (bundle.containsKey("_sc") && !z)) {
            if (bundle != null && com_google_android_gms_measurement_internal_zzdv == null && z) {
                bundle.remove("_sn");
                bundle.remove("_sc");
                bundle.remove("_si");
            }
            return;
        }
        if (com_google_android_gms_measurement_internal_zzdv.zzuw) {
            bundle.putString("_sn", com_google_android_gms_measurement_internal_zzdv.zzuw);
        } else {
            bundle.remove("_sn");
        }
        bundle.putString("_sc", com_google_android_gms_measurement_internal_zzdv.zzasa);
        bundle.putLong("_si", com_google_android_gms_measurement_internal_zzdv.zzasb);
    }

    @WorkerThread
    public final void zza(String str, zzdv com_google_android_gms_measurement_internal_zzdv) {
        zzaf();
        synchronized (this) {
            if (this.zzasi == null || this.zzasi.equals(str) || com_google_android_gms_measurement_internal_zzdv != null) {
                this.zzasi = str;
                this.zzash = com_google_android_gms_measurement_internal_zzdv;
            }
        }
    }

    @VisibleForTesting
    private static String zzcs(String str) {
        str = str.split("\\.");
        str = str.length > 0 ? str[str.length - 1] : "";
        return str.length() > 100 ? str.substring(0, 100) : str;
    }

    @MainThread
    private final zzdv zze(@NonNull Activity activity) {
        Preconditions.checkNotNull(activity);
        zzdv com_google_android_gms_measurement_internal_zzdv = (zzdv) this.zzasg.get(activity);
        if (com_google_android_gms_measurement_internal_zzdv != null) {
            return com_google_android_gms_measurement_internal_zzdv;
        }
        zzdv com_google_android_gms_measurement_internal_zzdv2 = new zzdv(null, zzcs(activity.getClass().getCanonicalName()), zzgr().zzmj());
        this.zzasg.put(activity, com_google_android_gms_measurement_internal_zzdv2);
        return com_google_android_gms_measurement_internal_zzdv2;
    }

    @MainThread
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        if (bundle != null) {
            bundle = bundle.getBundle("com.google.app_measurement.screen_service");
            if (bundle != null) {
                this.zzasg.put(activity, new zzdv(bundle.getString(JsonKey.NAME), bundle.getString("referrer_name"), bundle.getLong("id")));
            }
        }
    }

    @MainThread
    public final void onActivityResumed(Activity activity) {
        zza(activity, zze(activity), false);
        activity = zzgi();
        activity.zzgs().zzc(new zzd(activity, activity.zzbx().elapsedRealtime()));
    }

    @MainThread
    public final void onActivityPaused(Activity activity) {
        activity = zze(activity);
        this.zzasf = this.zzase;
        this.zzase = null;
        zzgs().zzc(new zzdy(this, activity));
    }

    @MainThread
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        if (bundle != null) {
            zzdv com_google_android_gms_measurement_internal_zzdv = (zzdv) this.zzasg.get(activity);
            if (com_google_android_gms_measurement_internal_zzdv != null) {
                Bundle bundle2 = new Bundle();
                bundle2.putLong("id", com_google_android_gms_measurement_internal_zzdv.zzasb);
                bundle2.putString(JsonKey.NAME, com_google_android_gms_measurement_internal_zzdv.zzuw);
                bundle2.putString("referrer_name", com_google_android_gms_measurement_internal_zzdv.zzasa);
                bundle.putBundle("com.google.app_measurement.screen_service", bundle2);
            }
        }
    }

    @MainThread
    public final void onActivityDestroyed(Activity activity) {
        this.zzasg.remove(activity);
    }

    public final /* bridge */ /* synthetic */ void zzgf() {
        super.zzgf();
    }

    public final /* bridge */ /* synthetic */ void zzgg() {
        super.zzgg();
    }

    public final /* bridge */ /* synthetic */ void zzgh() {
        super.zzgh();
    }

    public final /* bridge */ /* synthetic */ void zzaf() {
        super.zzaf();
    }

    public final /* bridge */ /* synthetic */ zza zzgi() {
        return super.zzgi();
    }

    public final /* bridge */ /* synthetic */ zzcy zzgj() {
        return super.zzgj();
    }

    public final /* bridge */ /* synthetic */ zzak zzgk() {
        return super.zzgk();
    }

    public final /* bridge */ /* synthetic */ zzdz zzgl() {
        return super.zzgl();
    }

    public final /* bridge */ /* synthetic */ zzdw zzgm() {
        return super.zzgm();
    }

    public final /* bridge */ /* synthetic */ zzam zzgn() {
        return super.zzgn();
    }

    public final /* bridge */ /* synthetic */ zzez zzgo() {
        return super.zzgo();
    }

    public final /* bridge */ /* synthetic */ zzy zzgp() {
        return super.zzgp();
    }

    public final /* bridge */ /* synthetic */ Clock zzbx() {
        return super.zzbx();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ zzao zzgq() {
        return super.zzgq();
    }

    public final /* bridge */ /* synthetic */ zzfu zzgr() {
        return super.zzgr();
    }

    public final /* bridge */ /* synthetic */ zzbp zzgs() {
        return super.zzgs();
    }

    public final /* bridge */ /* synthetic */ zzaq zzgt() {
        return super.zzgt();
    }

    public final /* bridge */ /* synthetic */ zzbb zzgu() {
        return super.zzgu();
    }

    public final /* bridge */ /* synthetic */ zzo zzgv() {
        return super.zzgv();
    }

    public final /* bridge */ /* synthetic */ zzl zzgw() {
        return super.zzgw();
    }
}
