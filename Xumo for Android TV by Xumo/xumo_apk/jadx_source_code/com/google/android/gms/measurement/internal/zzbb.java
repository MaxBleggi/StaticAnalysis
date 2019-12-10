package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.math.BigInteger;
import java.util.Locale;

final class zzbb extends zzcq {
    @VisibleForTesting
    static final Pair<String, Long> zzanj = new Pair("", Long.valueOf(0));
    private SharedPreferences zzabr;
    public zzbf zzank;
    public final zzbe zzanl = new zzbe(this, "last_upload", 0);
    public final zzbe zzanm = new zzbe(this, "last_upload_attempt", 0);
    public final zzbe zzann = new zzbe(this, "backoff", 0);
    public final zzbe zzano = new zzbe(this, "last_delete_stale", 0);
    public final zzbe zzanp = new zzbe(this, "midnight_offset", 0);
    public final zzbe zzanq = new zzbe(this, "first_open_time", 0);
    public final zzbe zzanr = new zzbe(this, "app_install_time", 0);
    public final zzbg zzans = new zzbg(this, "app_instance_id", null);
    private String zzant;
    private boolean zzanu;
    private long zzanv;
    public final zzbe zzanw = new zzbe(this, "time_before_start", NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
    public final zzbe zzanx = new zzbe(this, "session_timeout", 1800000);
    public final zzbd zzany = new zzbd(this, "start_new_session", true);
    public final zzbe zzanz = new zzbe(this, "last_pause_time", 0);
    public final zzbe zzaoa = new zzbe(this, "time_active", 0);
    public boolean zzaob;

    @WorkerThread
    @NonNull
    final Pair<String, Boolean> zzcb(String str) {
        zzaf();
        long elapsedRealtime = zzbx().elapsedRealtime();
        if (this.zzant != null && elapsedRealtime < this.zzanv) {
            return new Pair(this.zzant, Boolean.valueOf(this.zzanu));
        }
        this.zzanv = elapsedRealtime + zzgv().zza(str, zzag.zzajh);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            str = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
            if (str != null) {
                this.zzant = str.getId();
                this.zzanu = str.isLimitAdTrackingEnabled();
            }
            if (this.zzant == null) {
                this.zzant = "";
            }
        } catch (String str2) {
            zzgt().zzjn().zzg("Unable to get advertising id", str2);
            this.zzant = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(null);
        return new Pair(this.zzant, Boolean.valueOf(this.zzanu));
    }

    protected final boolean zzgy() {
        return true;
    }

    @WorkerThread
    final String zzcc(String str) {
        zzaf();
        str = zzcb(str).first;
        if (zzfu.getMessageDigest() == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzfu.getMessageDigest().digest(str.getBytes()))});
    }

    zzbb(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
    }

    @WorkerThread
    protected final void zzgz() {
        this.zzabr = getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzaob = this.zzabr.getBoolean("has_been_opened", false);
        if (!this.zzaob) {
            Editor edit = this.zzabr.edit();
            edit.putBoolean("has_been_opened", true);
            edit.apply();
        }
        this.zzank = new zzbf(this, "health_monitor", Math.max(0, ((Long) zzag.zzaji.get()).longValue()));
    }

    @WorkerThread
    private final SharedPreferences zzju() {
        zzaf();
        zzcl();
        return this.zzabr;
    }

    @WorkerThread
    final void zzcd(String str) {
        zzaf();
        Editor edit = zzju().edit();
        edit.putString("gmp_app_id", str);
        edit.apply();
    }

    @WorkerThread
    final String zzjv() {
        zzaf();
        return zzju().getString("gmp_app_id", null);
    }

    @WorkerThread
    final void zzce(String str) {
        zzaf();
        Editor edit = zzju().edit();
        edit.putString("admob_app_id", str);
        edit.apply();
    }

    @WorkerThread
    final String zzjw() {
        zzaf();
        return zzju().getString("admob_app_id", null);
    }

    @WorkerThread
    final Boolean zzjx() {
        zzaf();
        if (zzju().contains("use_service")) {
            return Boolean.valueOf(zzju().getBoolean("use_service", false));
        }
        return null;
    }

    @WorkerThread
    final void zzg(boolean z) {
        zzaf();
        zzgt().zzjo().zzg("Setting useService", Boolean.valueOf(z));
        Editor edit = zzju().edit();
        edit.putBoolean("use_service", z);
        edit.apply();
    }

    @WorkerThread
    final void zzjy() {
        zzaf();
        zzgt().zzjo().zzca("Clearing collection preferences.");
        if (zzgv().zza(zzag.zzalo)) {
            Boolean zzjz = zzjz();
            Editor edit = zzju().edit();
            edit.clear();
            edit.apply();
            if (zzjz != null) {
                setMeasurementEnabled(zzjz.booleanValue());
            }
            return;
        }
        boolean contains = zzju().contains("measurement_enabled");
        boolean z = true;
        if (contains) {
            z = zzh(true);
        }
        Editor edit2 = zzju().edit();
        edit2.clear();
        edit2.apply();
        if (contains) {
            setMeasurementEnabled(z);
        }
    }

    @WorkerThread
    final void setMeasurementEnabled(boolean z) {
        zzaf();
        zzgt().zzjo().zzg("Setting measurementEnabled", Boolean.valueOf(z));
        Editor edit = zzju().edit();
        edit.putBoolean("measurement_enabled", z);
        edit.apply();
    }

    @WorkerThread
    final boolean zzh(boolean z) {
        zzaf();
        return zzju().getBoolean("measurement_enabled", z);
    }

    @WorkerThread
    final Boolean zzjz() {
        zzaf();
        return zzju().contains("measurement_enabled") ? Boolean.valueOf(zzju().getBoolean("measurement_enabled", true)) : null;
    }

    @WorkerThread
    protected final String zzka() {
        zzaf();
        String string = zzju().getString("previous_os_version", null);
        zzgp().zzcl();
        String str = VERSION.RELEASE;
        if (!(TextUtils.isEmpty(str) || str.equals(string))) {
            Editor edit = zzju().edit();
            edit.putString("previous_os_version", str);
            edit.apply();
        }
        return string;
    }

    @WorkerThread
    final void zzi(boolean z) {
        zzaf();
        zzgt().zzjo().zzg("Updating deferred analytics collection", Boolean.valueOf(z));
        Editor edit = zzju().edit();
        edit.putBoolean("deferred_analytics_collection", z);
        edit.apply();
    }

    @WorkerThread
    final boolean zzkb() {
        zzaf();
        return zzju().getBoolean("deferred_analytics_collection", false);
    }

    @WorkerThread
    final boolean zzkc() {
        return this.zzabr.contains("deferred_analytics_collection");
    }

    final boolean zzaj(long j) {
        return j - this.zzanx.get() > this.zzanz.get() ? 1 : 0;
    }
}
