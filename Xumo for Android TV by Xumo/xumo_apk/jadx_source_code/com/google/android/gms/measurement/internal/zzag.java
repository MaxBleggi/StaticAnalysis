package com.google.android.gms.measurement.internal;

import android.content.Context;
import androidx.annotation.WorkerThread;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzsm;
import com.google.android.gms.internal.measurement.zzsw;
import com.google.android.gms.internal.measurement.zzsx;
import com.google.android.gms.internal.measurement.zztd;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@VisibleForTesting
public final class zzag {
    private static volatile zzbu zzadp;
    static zzl zzait;
    static List<zza<Integer>> zzaiu = new ArrayList();
    static List<zza<Long>> zzaiv = new ArrayList();
    static List<zza<Boolean>> zzaiw = new ArrayList();
    static List<zza<String>> zzaix = new ArrayList();
    static List<zza<Double>> zzaiy = new ArrayList();
    private static final zztd zzaiz = new zztd(zzsw.zzfs("com.google.android.gms.measurement"));
    @VisibleForTesting
    private static Boolean zzaja;
    private static zza<Boolean> zzajb = zza.zzb("measurement.log_third_party_store_events_enabled", false, false);
    private static zza<Boolean> zzajc = zza.zzb("measurement.log_installs_enabled", false, false);
    private static zza<Boolean> zzajd = zza.zzb("measurement.log_upgrades_enabled", false, false);
    public static zza<Boolean> zzaje = zza.zzb("measurement.log_androidId_enabled", false, false);
    public static zza<Boolean> zzajf = zza.zzb("measurement.upload_dsid_enabled", false, false);
    public static zza<String> zzajg = zza.zzd("measurement.log_tag", "FA", "FA-SVC");
    public static zza<Long> zzajh = zza.zzb("measurement.ad_id_cache_time", (long) NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS, (long) NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
    public static zza<Long> zzaji = zza.zzb("measurement.monitoring.sample_period_millis", 86400000, 86400000);
    public static zza<Long> zzajj = zza.zzb("measurement.config.cache_time", 86400000, 3600000);
    public static zza<String> zzajk;
    public static zza<String> zzajl;
    public static zza<Integer> zzajm = zza.zzc("measurement.upload.max_bundles", 100, 100);
    public static zza<Integer> zzajn = zza.zzc("measurement.upload.max_batch_size", 65536, 65536);
    public static zza<Integer> zzajo = zza.zzc("measurement.upload.max_bundle_size", 65536, 65536);
    public static zza<Integer> zzajp = zza.zzc("measurement.upload.max_events_per_bundle", 1000, 1000);
    public static zza<Integer> zzajq = zza.zzc("measurement.upload.max_events_per_day", DefaultOggSeeker.MATCH_BYTE_RANGE, DefaultOggSeeker.MATCH_BYTE_RANGE);
    public static zza<Integer> zzajr = zza.zzc("measurement.upload.max_error_events_per_day", 1000, 1000);
    public static zza<Integer> zzajs = zza.zzc("measurement.upload.max_public_events_per_day", DefaultLoadControl.DEFAULT_MAX_BUFFER_MS, DefaultLoadControl.DEFAULT_MAX_BUFFER_MS);
    public static zza<Integer> zzajt = zza.zzc("measurement.upload.max_conversions_per_day", 500, 500);
    public static zza<Integer> zzaju = zza.zzc("measurement.upload.max_realtime_events_per_day", 10, 10);
    public static zza<Integer> zzajv = zza.zzc("measurement.store.max_stored_events_per_app", DefaultOggSeeker.MATCH_BYTE_RANGE, DefaultOggSeeker.MATCH_BYTE_RANGE);
    public static zza<String> zzajw;
    public static zza<Long> zzajx = zza.zzb("measurement.upload.backoff_period", 43200000, 43200000);
    public static zza<Long> zzajy = zza.zzb("measurement.upload.window_interval", 3600000, 3600000);
    public static zza<Long> zzajz = zza.zzb("measurement.upload.interval", 3600000, 3600000);
    public static zza<Long> zzaka = zza.zzb("measurement.upload.realtime_upload_interval", (long) NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS, (long) NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
    public static zza<Long> zzakb = zza.zzb("measurement.upload.debug_upload_interval", 1000, 1000);
    public static zza<Long> zzakc = zza.zzb("measurement.upload.minimum_delay", 500, 500);
    public static zza<Long> zzakd = zza.zzb("measurement.alarm_manager.minimum_interval", (long) DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS, (long) DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS);
    public static zza<Long> zzake = zza.zzb("measurement.upload.stale_data_deletion_interval", 86400000, 86400000);
    public static zza<Long> zzakf = zza.zzb("measurement.upload.refresh_blacklisted_config_interval", 604800000, 604800000);
    public static zza<Long> zzakg = zza.zzb("measurement.upload.initial_upload_delay_time", 15000, 15000);
    public static zza<Long> zzakh = zza.zzb("measurement.upload.retry_time", 1800000, 1800000);
    public static zza<Integer> zzaki = zza.zzc("measurement.upload.retry_count", 6, 6);
    public static zza<Long> zzakj = zza.zzb("measurement.upload.max_queue_time", 2419200000L, 2419200000L);
    public static zza<Integer> zzakk = zza.zzc("measurement.lifetimevalue.max_currency_tracked", 4, 4);
    public static zza<Integer> zzakl = zza.zzc("measurement.audience.filter_result_max_count", Callback.DEFAULT_DRAG_ANIMATION_DURATION, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
    public static zza<Long> zzakm = zza.zzb("measurement.service_client.idle_disconnect_millis", 5000, 5000);
    public static zza<Boolean> zzakn = zza.zzb("measurement.test.boolean_flag", false, false);
    public static zza<String> zzako;
    public static zza<Long> zzakp = zza.zzb("measurement.test.long_flag", -1, -1);
    public static zza<Integer> zzakq = zza.zzc("measurement.test.int_flag", -2, -2);
    public static zza<Double> zzakr = zza.zza("measurement.test.double_flag", -3.0d, -3.0d);
    public static zza<Integer> zzaks = zza.zzc("measurement.experiment.max_ids", 50, 50);
    public static zza<Boolean> zzakt = zza.zzb("measurement.lifetimevalue.user_engagement_tracking_enabled", true, true);
    public static zza<Boolean> zzaku = zza.zzb("measurement.audience.complex_param_evaluation", true, true);
    public static zza<Boolean> zzakv = zza.zzb("measurement.validation.internal_limits_internal_event_params", false, false);
    public static zza<Boolean> zzakw = zza.zzb("measurement.quality.unsuccessful_update_retry_counter", true, true);
    public static zza<Boolean> zzakx = zza.zzb("measurement.iid.disable_on_collection_disabled", true, true);
    public static zza<Boolean> zzaky = zza.zzb("measurement.app_launch.call_only_when_enabled", true, true);
    public static zza<Boolean> zzakz = zza.zzb("measurement.run_on_worker_inline", true, false);
    public static zza<Boolean> zzala = zza.zzb("measurement.audience.dynamic_filters", false, false);
    public static zza<Boolean> zzalb = zza.zzb("measurement.reset_analytics.persist_time", false, false);
    public static zza<Boolean> zzalc = zza.zzb("measurement.validation.value_and_currency_params", false, false);
    public static zza<Boolean> zzald = zza.zzb("measurement.sampling.time_zone_offset_enabled", false, false);
    public static zza<Boolean> zzale = zza.zzb("measurement.referrer.enable_logging_install_referrer_cmp_from_apk", false, false);
    public static zza<Boolean> zzalf = zza.zzb("measurement.fetch_config_with_admob_app_id", false, false);
    public static zza<Boolean> zzalg = zza.zzb("measurement.sessions.session_id_enabled", false, false);
    public static zza<Boolean> zzalh = zza.zzb("measurement.sessions.session_number_enabled", false, false);
    public static zza<Boolean> zzali = zza.zzb("measurement.sessions.immediate_start_enabled", false, false);
    public static zza<Boolean> zzalj = zza.zzb("measurement.sessions.background_sessions_enabled", false, false);
    public static zza<Boolean> zzalk = zza.zzb("measurement.collection.firebase_global_collection_flag_enabled", true, true);
    public static zza<Boolean> zzall = zza.zzb("measurement.collection.efficient_engagement_reporting_enabled", false, false);
    public static zza<Boolean> zzalm = zza.zzb("measurement.collection.redundant_engagement_removal_enabled", false, false);
    public static zza<Boolean> zzaln = zza.zzb("measurement.remove_app_instance_id_cache_enabled", true, true);
    public static zza<Boolean> zzalo = zza.zzb("measurement.collection.init_params_control_enabled", true, true);
    public static zza<Boolean> zzalp = zza.zzb("measurement.upload.disable_is_uploader", false, false);
    public static zza<Boolean> zzalq = zza.zzb("measurement.experiment.enable_experiment_reporting", false, false);
    public static zza<Boolean> zzalr = zza.zzb("measurement.collection.log_event_and_bundle_v2", true, true);

    @VisibleForTesting
    public static final class zza<V> {
        private final V zzaan;
        private zzsx<V> zzals;
        private final V zzalt;
        private volatile V zzalu;
        private final String zzoj;

        private zza(String str, V v, V v2) {
            this.zzoj = str;
            this.zzaan = v;
            this.zzalt = v2;
        }

        static zza<Boolean> zzb(String str, boolean z, boolean z2) {
            zza<Boolean> com_google_android_gms_measurement_internal_zzag_zza = new zza(str, Boolean.valueOf(z), Boolean.valueOf(z2));
            zzag.zzaiw.add(com_google_android_gms_measurement_internal_zzag_zza);
            return com_google_android_gms_measurement_internal_zzag_zza;
        }

        static zza<String> zzd(String str, String str2, String str3) {
            zza<String> com_google_android_gms_measurement_internal_zzag_zza = new zza(str, str2, str3);
            zzag.zzaix.add(com_google_android_gms_measurement_internal_zzag_zza);
            return com_google_android_gms_measurement_internal_zzag_zza;
        }

        static zza<Long> zzb(String str, long j, long j2) {
            zza<Long> com_google_android_gms_measurement_internal_zzag_zza = new zza(str, Long.valueOf(j), Long.valueOf(j2));
            zzag.zzaiv.add(com_google_android_gms_measurement_internal_zzag_zza);
            return com_google_android_gms_measurement_internal_zzag_zza;
        }

        static zza<Integer> zzc(String str, int i, int i2) {
            zza<Integer> com_google_android_gms_measurement_internal_zzag_zza = new zza(str, Integer.valueOf(i), Integer.valueOf(i2));
            zzag.zzaiu.add(com_google_android_gms_measurement_internal_zzag_zza);
            return com_google_android_gms_measurement_internal_zzag_zza;
        }

        static zza<Double> zza(String str, double d, double d2) {
            d = new zza(str, Double.valueOf(-3.0d), Double.valueOf(-3.0d));
            zzag.zzaiy.add(d);
            return d;
        }

        public final String getKey() {
            return this.zzoj;
        }

        private static void zzq() {
            synchronized (zza.class) {
                for (zza com_google_android_gms_measurement_internal_zzag_zza : zzag.zzaiw) {
                    zztd zziz = zzag.zzaiz;
                    String str = com_google_android_gms_measurement_internal_zzag_zza.zzoj;
                    zzl com_google_android_gms_measurement_internal_zzl = zzag.zzait;
                    com_google_android_gms_measurement_internal_zzag_zza.zzals = zziz.zzd(str, ((Boolean) com_google_android_gms_measurement_internal_zzag_zza.zzaan).booleanValue());
                }
                for (zza com_google_android_gms_measurement_internal_zzag_zza2 : zzag.zzaix) {
                    zziz = zzag.zzaiz;
                    str = com_google_android_gms_measurement_internal_zzag_zza2.zzoj;
                    com_google_android_gms_measurement_internal_zzl = zzag.zzait;
                    com_google_android_gms_measurement_internal_zzag_zza2.zzals = zziz.zzy(str, (String) com_google_android_gms_measurement_internal_zzag_zza2.zzaan);
                }
                for (zza com_google_android_gms_measurement_internal_zzag_zza22 : zzag.zzaiv) {
                    zziz = zzag.zzaiz;
                    str = com_google_android_gms_measurement_internal_zzag_zza22.zzoj;
                    com_google_android_gms_measurement_internal_zzl = zzag.zzait;
                    com_google_android_gms_measurement_internal_zzag_zza22.zzals = zziz.zze(str, ((Long) com_google_android_gms_measurement_internal_zzag_zza22.zzaan).longValue());
                }
                for (zza com_google_android_gms_measurement_internal_zzag_zza222 : zzag.zzaiu) {
                    zziz = zzag.zzaiz;
                    str = com_google_android_gms_measurement_internal_zzag_zza222.zzoj;
                    com_google_android_gms_measurement_internal_zzl = zzag.zzait;
                    com_google_android_gms_measurement_internal_zzag_zza222.zzals = zziz.zzd(str, ((Integer) com_google_android_gms_measurement_internal_zzag_zza222.zzaan).intValue());
                }
                for (zza com_google_android_gms_measurement_internal_zzag_zza2222 : zzag.zzaiy) {
                    zziz = zzag.zzaiz;
                    str = com_google_android_gms_measurement_internal_zzag_zza2222.zzoj;
                    com_google_android_gms_measurement_internal_zzl = zzag.zzait;
                    com_google_android_gms_measurement_internal_zzag_zza2222.zzals = zziz.zzb(str, ((Double) com_google_android_gms_measurement_internal_zzag_zza2222.zzaan).doubleValue());
                }
            }
        }

        @WorkerThread
        private static void zzja() {
            synchronized (zza.class) {
                if (zzl.isMainThread()) {
                    throw new IllegalStateException("Tried to refresh flag cache on main thread or on package side.");
                }
                zzl com_google_android_gms_measurement_internal_zzl = zzag.zzait;
                try {
                    for (zza com_google_android_gms_measurement_internal_zzag_zza : zzag.zzaiw) {
                        com_google_android_gms_measurement_internal_zzag_zza.zzalu = com_google_android_gms_measurement_internal_zzag_zza.zzals.get();
                    }
                    for (zza com_google_android_gms_measurement_internal_zzag_zza2 : zzag.zzaix) {
                        com_google_android_gms_measurement_internal_zzag_zza2.zzalu = com_google_android_gms_measurement_internal_zzag_zza2.zzals.get();
                    }
                    for (zza com_google_android_gms_measurement_internal_zzag_zza22 : zzag.zzaiv) {
                        com_google_android_gms_measurement_internal_zzag_zza22.zzalu = com_google_android_gms_measurement_internal_zzag_zza22.zzals.get();
                    }
                    for (zza com_google_android_gms_measurement_internal_zzag_zza222 : zzag.zzaiu) {
                        com_google_android_gms_measurement_internal_zzag_zza222.zzalu = com_google_android_gms_measurement_internal_zzag_zza222.zzals.get();
                    }
                    for (zza com_google_android_gms_measurement_internal_zzag_zza2222 : zzag.zzaiy) {
                        com_google_android_gms_measurement_internal_zzag_zza2222.zzalu = com_google_android_gms_measurement_internal_zzag_zza2222.zzals.get();
                    }
                } catch (Exception e) {
                    zzag.zza(e);
                }
            }
        }

        public final V get() {
            if (zzag.zzait == null) {
                return this.zzaan;
            }
            zzl com_google_android_gms_measurement_internal_zzl = zzag.zzait;
            if (zzl.isMainThread()) {
                return this.zzalu == null ? this.zzaan : this.zzalu;
            } else {
                zzja();
                try {
                    return this.zzals.get();
                } catch (Exception e) {
                    zzag.zza(e);
                    return this.zzals.getDefaultValue();
                }
            }
        }

        public final V get(V v) {
            if (v != null) {
                return v;
            }
            if (zzag.zzait == null) {
                return this.zzaan;
            }
            v = zzag.zzait;
            if (zzl.isMainThread() != null) {
                return this.zzalu == null ? this.zzaan : this.zzalu;
            } else {
                zzja();
                try {
                    return this.zzals.get();
                } catch (Exception e) {
                    zzag.zza(e);
                    return this.zzals.getDefaultValue();
                }
            }
        }
    }

    public static Map<String, String> zzm(Context context) {
        return zzsm.zza(context.getContentResolver(), zzsw.zzfs("com.google.android.gms.measurement")).zztk();
    }

    static void zza(zzbu com_google_android_gms_measurement_internal_zzbu) {
        zzadp = com_google_android_gms_measurement_internal_zzbu;
    }

    @VisibleForTesting
    static void zza(Exception exception) {
        if (zzadp != null) {
            Context context = zzadp.getContext();
            if (zzaja == null) {
                zzaja = Boolean.valueOf(GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context, GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE) == 0);
            }
            if (zzaja.booleanValue()) {
                zzadp.zzgt().zzjg().zzg("Got Exception on PhenotypeFlag.get on Play device", exception);
            }
        }
    }

    static void zza(zzl com_google_android_gms_measurement_internal_zzl) {
        zzait = com_google_android_gms_measurement_internal_zzl;
        zza.zzq();
    }

    static {
        String str = "https";
        zzajk = zza.zzd("measurement.config.url_scheme", str, str);
        str = "app-measurement.com";
        zzajl = zza.zzd("measurement.config.url_authority", str, str);
        str = "https://app-measurement.com/a";
        zzajw = zza.zzd("measurement.upload.url", str, str);
        String str2 = "---";
        zzako = zza.zzd("measurement.test.string_flag", str2, str2);
    }
}
