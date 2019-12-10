package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzsx;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class zzbu implements zzcr {
    private static volatile zzbu zzapr;
    private final boolean zzadv;
    private final String zzadx;
    private final long zzaha;
    private final zzl zzait;
    private final String zzaps;
    private final String zzapt;
    private final zzo zzapu;
    private final zzbb zzapv;
    private final zzaq zzapw;
    private final zzbp zzapx;
    private final zzez zzapy;
    private final AppMeasurement zzapz;
    private final zzfu zzaqa;
    private final zzao zzaqb;
    private final zzdw zzaqc;
    private final zzcy zzaqd;
    private final zza zzaqe;
    private zzam zzaqf;
    private zzdz zzaqg;
    private zzy zzaqh;
    private zzak zzaqi;
    private zzbh zzaqj;
    private Boolean zzaqk;
    private long zzaql;
    private volatile Boolean zzaqm;
    @VisibleForTesting
    private Boolean zzaqn;
    @VisibleForTesting
    private Boolean zzaqo;
    private int zzaqp;
    private AtomicInteger zzaqq = new AtomicInteger(0);
    private final Context zzri;
    private final Clock zzrz;
    private boolean zzvz = false;

    private zzbu(zzcx com_google_android_gms_measurement_internal_zzcx) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzcx);
        this.zzait = new zzl(com_google_android_gms_measurement_internal_zzcx.zzri);
        zzag.zza(this.zzait);
        this.zzri = com_google_android_gms_measurement_internal_zzcx.zzri;
        this.zzadx = com_google_android_gms_measurement_internal_zzcx.zzadx;
        this.zzaps = com_google_android_gms_measurement_internal_zzcx.zzaps;
        this.zzapt = com_google_android_gms_measurement_internal_zzcx.zzapt;
        this.zzadv = com_google_android_gms_measurement_internal_zzcx.zzadv;
        this.zzaqm = com_google_android_gms_measurement_internal_zzcx.zzaqm;
        zzal com_google_android_gms_measurement_internal_zzal = com_google_android_gms_measurement_internal_zzcx.zzarj;
        if (!(com_google_android_gms_measurement_internal_zzal == null || com_google_android_gms_measurement_internal_zzal.zzady == null)) {
            Object obj = com_google_android_gms_measurement_internal_zzal.zzady.get("measurementEnabled");
            if (obj instanceof Boolean) {
                this.zzaqn = (Boolean) obj;
            }
            Object obj2 = com_google_android_gms_measurement_internal_zzal.zzady.get("measurementDeactivated");
            if (obj2 instanceof Boolean) {
                this.zzaqo = (Boolean) obj2;
            }
        }
        zzsx.zzae(this.zzri);
        this.zzrz = DefaultClock.getInstance();
        this.zzaha = this.zzrz.currentTimeMillis();
        this.zzapu = new zzo(this);
        zzcq com_google_android_gms_measurement_internal_zzbb = new zzbb(this);
        com_google_android_gms_measurement_internal_zzbb.zzq();
        this.zzapv = com_google_android_gms_measurement_internal_zzbb;
        com_google_android_gms_measurement_internal_zzbb = new zzaq(this);
        com_google_android_gms_measurement_internal_zzbb.zzq();
        this.zzapw = com_google_android_gms_measurement_internal_zzbb;
        com_google_android_gms_measurement_internal_zzbb = new zzfu(this);
        com_google_android_gms_measurement_internal_zzbb.zzq();
        this.zzaqa = com_google_android_gms_measurement_internal_zzbb;
        com_google_android_gms_measurement_internal_zzbb = new zzao(this);
        com_google_android_gms_measurement_internal_zzbb.zzq();
        this.zzaqb = com_google_android_gms_measurement_internal_zzbb;
        this.zzaqe = new zza(this);
        zzf com_google_android_gms_measurement_internal_zzdw = new zzdw(this);
        com_google_android_gms_measurement_internal_zzdw.zzq();
        this.zzaqc = com_google_android_gms_measurement_internal_zzdw;
        com_google_android_gms_measurement_internal_zzdw = new zzcy(this);
        com_google_android_gms_measurement_internal_zzdw.zzq();
        this.zzaqd = com_google_android_gms_measurement_internal_zzdw;
        this.zzapz = new AppMeasurement(this);
        com_google_android_gms_measurement_internal_zzdw = new zzez(this);
        com_google_android_gms_measurement_internal_zzdw.zzq();
        this.zzapy = com_google_android_gms_measurement_internal_zzdw;
        com_google_android_gms_measurement_internal_zzbb = new zzbp(this);
        com_google_android_gms_measurement_internal_zzbb.zzq();
        this.zzapx = com_google_android_gms_measurement_internal_zzbb;
        zzl com_google_android_gms_measurement_internal_zzl = this.zzait;
        if (this.zzri.getApplicationContext() instanceof Application) {
            zzcp zzgj = zzgj();
            if (zzgj.getContext().getApplicationContext() instanceof Application) {
                Application application = (Application) zzgj.getContext().getApplicationContext();
                if (zzgj.zzark == null) {
                    zzgj.zzark = new zzds(zzgj);
                }
                application.unregisterActivityLifecycleCallbacks(zzgj.zzark);
                application.registerActivityLifecycleCallbacks(zzgj.zzark);
                zzgj.zzgt().zzjo().zzca("Registered activity lifecycle callback");
            }
        } else {
            zzgt().zzjj().zzca("Application context is not an Application");
        }
        this.zzapx.zzc(new zzbv(this, com_google_android_gms_measurement_internal_zzcx));
    }

    @WorkerThread
    private final void zza(zzcx com_google_android_gms_measurement_internal_zzcx) {
        zzgs().zzaf();
        zzo.zzhy();
        com_google_android_gms_measurement_internal_zzcx = new zzy(this);
        com_google_android_gms_measurement_internal_zzcx.zzq();
        this.zzaqh = com_google_android_gms_measurement_internal_zzcx;
        com_google_android_gms_measurement_internal_zzcx = new zzak(this);
        com_google_android_gms_measurement_internal_zzcx.zzq();
        this.zzaqi = com_google_android_gms_measurement_internal_zzcx;
        zzf com_google_android_gms_measurement_internal_zzam = new zzam(this);
        com_google_android_gms_measurement_internal_zzam.zzq();
        this.zzaqf = com_google_android_gms_measurement_internal_zzam;
        com_google_android_gms_measurement_internal_zzam = new zzdz(this);
        com_google_android_gms_measurement_internal_zzam.zzq();
        this.zzaqg = com_google_android_gms_measurement_internal_zzam;
        this.zzaqa.zzgx();
        this.zzapv.zzgx();
        this.zzaqj = new zzbh(this);
        this.zzaqi.zzgx();
        zzgt().zzjm().zzg("App measurement is starting up, version", Long.valueOf(this.zzapu.zzhh()));
        zzl com_google_android_gms_measurement_internal_zzl = this.zzait;
        zzgt().zzjm().zzca("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        com_google_android_gms_measurement_internal_zzl = this.zzait;
        com_google_android_gms_measurement_internal_zzcx = com_google_android_gms_measurement_internal_zzcx.zzal();
        if (TextUtils.isEmpty(this.zzadx)) {
            zzas zzjm;
            if (zzgr().zzdb(com_google_android_gms_measurement_internal_zzcx)) {
                zzjm = zzgt().zzjm();
                com_google_android_gms_measurement_internal_zzcx = "Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.";
            } else {
                zzjm = zzgt().zzjm();
                String str = "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ";
                com_google_android_gms_measurement_internal_zzcx = String.valueOf(com_google_android_gms_measurement_internal_zzcx);
                com_google_android_gms_measurement_internal_zzcx = com_google_android_gms_measurement_internal_zzcx.length() != 0 ? str.concat(com_google_android_gms_measurement_internal_zzcx) : new String(str);
            }
            zzjm.zzca(com_google_android_gms_measurement_internal_zzcx);
        }
        zzgt().zzjn().zzca("Debug-level message logging enabled");
        if (this.zzaqp != this.zzaqq.get()) {
            zzgt().zzjg().zze("Not all components initialized", Integer.valueOf(this.zzaqp), Integer.valueOf(this.zzaqq.get()));
        }
        this.zzvz = true;
    }

    @WorkerThread
    protected final void start() {
        zzgs().zzaf();
        if (zzgu().zzanl.get() == 0) {
            zzgu().zzanl.set(this.zzrz.currentTimeMillis());
        }
        if (Long.valueOf(zzgu().zzanq.get()).longValue() == 0) {
            zzgt().zzjo().zzg("Persisting first open", Long.valueOf(this.zzaha));
            zzgu().zzanq.set(this.zzaha);
        }
        zzl com_google_android_gms_measurement_internal_zzl;
        if (zzkv()) {
            com_google_android_gms_measurement_internal_zzl = this.zzait;
            if (!(TextUtils.isEmpty(zzgk().getGmpAppId()) && TextUtils.isEmpty(zzgk().zzhb()))) {
                zzgr();
                if (zzfu.zza(zzgk().getGmpAppId(), zzgu().zzjv(), zzgk().zzhb(), zzgu().zzjw())) {
                    zzgt().zzjm().zzca("Rechecking which service to use due to a GMP App Id change");
                    zzgu().zzjy();
                    zzgn().resetAnalyticsData();
                    this.zzaqg.disconnect();
                    this.zzaqg.zzdj();
                    zzgu().zzanq.set(this.zzaha);
                    zzgu().zzans.zzcf(null);
                }
                zzgu().zzcd(zzgk().getGmpAppId());
                zzgu().zzce(zzgk().zzhb());
                if (this.zzapu.zzbk(zzgk().zzal())) {
                    this.zzapy.zzan(this.zzaha);
                }
            }
            zzgj().zzcr(zzgu().zzans.zzkd());
            com_google_android_gms_measurement_internal_zzl = this.zzait;
            if (!(TextUtils.isEmpty(zzgk().getGmpAppId()) && TextUtils.isEmpty(zzgk().zzhb()))) {
                boolean isEnabled = isEnabled();
                if (!(zzgu().zzkc() || this.zzapu.zzhz())) {
                    zzgu().zzi(isEnabled ^ 1);
                }
                if (!this.zzapu.zzbc(zzgk().zzal()) || isEnabled) {
                    zzgj().zzld();
                }
                zzgl().zza(new AtomicReference());
            }
        } else if (isEnabled()) {
            if (!zzgr().zzx("android.permission.INTERNET")) {
                zzgt().zzjg().zzca("App is missing INTERNET permission");
            }
            if (!zzgr().zzx("android.permission.ACCESS_NETWORK_STATE")) {
                zzgt().zzjg().zzca("App is missing ACCESS_NETWORK_STATE permission");
            }
            com_google_android_gms_measurement_internal_zzl = this.zzait;
            if (!(Wrappers.packageManager(this.zzri).isCallerInstantApp() || this.zzapu.zzif())) {
                if (!zzbk.zza(this.zzri)) {
                    zzgt().zzjg().zzca("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzfu.zza(this.zzri, false)) {
                    zzgt().zzjg().zzca("AppMeasurementService not registered/enabled");
                }
            }
            zzgt().zzjg().zzca("Uploading is not possible. App measurement disabled");
        }
    }

    public final zzl zzgw() {
        return this.zzait;
    }

    public final zzo zzgv() {
        return this.zzapu;
    }

    public final zzbb zzgu() {
        zza(this.zzapv);
        return this.zzapv;
    }

    public final zzaq zzgt() {
        zza(this.zzapw);
        return this.zzapw;
    }

    public final zzaq zzkj() {
        return (this.zzapw == null || !this.zzapw.isInitialized()) ? null : this.zzapw;
    }

    public final zzbp zzgs() {
        zza(this.zzapx);
        return this.zzapx;
    }

    public final zzez zzgo() {
        zza(this.zzapy);
        return this.zzapy;
    }

    public final zzbh zzkk() {
        return this.zzaqj;
    }

    final zzbp zzkl() {
        return this.zzapx;
    }

    public final zzcy zzgj() {
        zza(this.zzaqd);
        return this.zzaqd;
    }

    public final AppMeasurement zzkm() {
        return this.zzapz;
    }

    public final zzfu zzgr() {
        zza(this.zzaqa);
        return this.zzaqa;
    }

    public final zzao zzgq() {
        zza(this.zzaqb);
        return this.zzaqb;
    }

    public final zzam zzgn() {
        zza(this.zzaqf);
        return this.zzaqf;
    }

    public final Context getContext() {
        return this.zzri;
    }

    public final boolean zzkn() {
        return TextUtils.isEmpty(this.zzadx);
    }

    public final String zzko() {
        return this.zzadx;
    }

    public final String zzkp() {
        return this.zzaps;
    }

    public final String zzkq() {
        return this.zzapt;
    }

    public final boolean zzkr() {
        return this.zzadv;
    }

    public final Clock zzbx() {
        return this.zzrz;
    }

    public final zzdw zzgm() {
        zza(this.zzaqc);
        return this.zzaqc;
    }

    public final zzdz zzgl() {
        zza(this.zzaqg);
        return this.zzaqg;
    }

    public final zzy zzgp() {
        zza(this.zzaqh);
        return this.zzaqh;
    }

    public final zzak zzgk() {
        zza(this.zzaqi);
        return this.zzaqi;
    }

    public final zza zzgi() {
        if (this.zzaqe != null) {
            return this.zzaqe;
        }
        throw new IllegalStateException("Component not created");
    }

    public static zzbu zza(Context context, zzal com_google_android_gms_measurement_internal_zzal) {
        if (com_google_android_gms_measurement_internal_zzal != null && (com_google_android_gms_measurement_internal_zzal.origin == null || com_google_android_gms_measurement_internal_zzal.zzadx == null)) {
            com_google_android_gms_measurement_internal_zzal = new zzal(com_google_android_gms_measurement_internal_zzal.zzadt, com_google_android_gms_measurement_internal_zzal.zzadu, com_google_android_gms_measurement_internal_zzal.zzadv, com_google_android_gms_measurement_internal_zzal.zzadw, null, null, com_google_android_gms_measurement_internal_zzal.zzady);
        }
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzapr == null) {
            synchronized (zzbu.class) {
                if (zzapr == null) {
                    zzapr = new zzbu(new zzcx(context, com_google_android_gms_measurement_internal_zzal));
                }
            }
        } else if (!(com_google_android_gms_measurement_internal_zzal == null || com_google_android_gms_measurement_internal_zzal.zzady == null || com_google_android_gms_measurement_internal_zzal.zzady.containsKey("dataCollectionDefaultEnabled") == null)) {
            zzapr.zzd(com_google_android_gms_measurement_internal_zzal.zzady.getBoolean("dataCollectionDefaultEnabled"));
        }
        return zzapr;
    }

    private final void zzcl() {
        if (!this.zzvz) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
    }

    private static void zza(zzcq com_google_android_gms_measurement_internal_zzcq) {
        if (com_google_android_gms_measurement_internal_zzcq == null) {
            throw new IllegalStateException("Component not created");
        } else if (!com_google_android_gms_measurement_internal_zzcq.isInitialized()) {
            com_google_android_gms_measurement_internal_zzcq = String.valueOf(com_google_android_gms_measurement_internal_zzcq.getClass());
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(com_google_android_gms_measurement_internal_zzcq).length() + 27);
            stringBuilder.append("Component not initialized: ");
            stringBuilder.append(com_google_android_gms_measurement_internal_zzcq);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    private static void zza(zzf com_google_android_gms_measurement_internal_zzf) {
        if (com_google_android_gms_measurement_internal_zzf == null) {
            throw new IllegalStateException("Component not created");
        } else if (!com_google_android_gms_measurement_internal_zzf.isInitialized()) {
            com_google_android_gms_measurement_internal_zzf = String.valueOf(com_google_android_gms_measurement_internal_zzf.getClass());
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(com_google_android_gms_measurement_internal_zzf).length() + 27);
            stringBuilder.append("Component not initialized: ");
            stringBuilder.append(com_google_android_gms_measurement_internal_zzf);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    private static void zza(zzcp com_google_android_gms_measurement_internal_zzcp) {
        if (com_google_android_gms_measurement_internal_zzcp == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    @WorkerThread
    final void zzd(boolean z) {
        this.zzaqm = Boolean.valueOf(z);
    }

    @WorkerThread
    public final boolean zzks() {
        return this.zzaqm != null && this.zzaqm.booleanValue();
    }

    @WorkerThread
    public final boolean isEnabled() {
        zzgs().zzaf();
        zzcl();
        Boolean zzjz;
        if (this.zzapu.zza(zzag.zzalo)) {
            if (this.zzapu.zzhz()) {
                return false;
            }
            if (this.zzaqo != null && this.zzaqo.booleanValue()) {
                return false;
            }
            zzjz = zzgu().zzjz();
            if (zzjz != null) {
                return zzjz.booleanValue();
            }
            zzjz = this.zzapu.zzia();
            if (zzjz != null) {
                return zzjz.booleanValue();
            }
            if (this.zzaqn != null) {
                return this.zzaqn.booleanValue();
            }
            if (GoogleServices.isMeasurementExplicitlyDisabled()) {
                return false;
            }
            if (!this.zzapu.zza(zzag.zzalk) || this.zzaqm == null) {
                return true;
            }
            return this.zzaqm.booleanValue();
        } else if (this.zzapu.zzhz()) {
            return false;
        } else {
            boolean booleanValue;
            zzjz = this.zzapu.zzia();
            if (zzjz != null) {
                booleanValue = zzjz.booleanValue();
            } else {
                booleanValue = GoogleServices.isMeasurementExplicitlyDisabled() ^ true;
                if (booleanValue && this.zzaqm != null && ((Boolean) zzag.zzalk.get()).booleanValue()) {
                    booleanValue = this.zzaqm.booleanValue();
                }
            }
            return zzgu().zzh(booleanValue);
        }
    }

    final long zzkt() {
        Long valueOf = Long.valueOf(zzgu().zzanq.get());
        if (valueOf.longValue() == 0) {
            return this.zzaha;
        }
        return Math.min(this.zzaha, valueOf.longValue());
    }

    final void zzgg() {
        zzl com_google_android_gms_measurement_internal_zzl = this.zzait;
    }

    final void zzgf() {
        zzl com_google_android_gms_measurement_internal_zzl = this.zzait;
        throw new IllegalStateException("Unexpected call on client side");
    }

    final void zzb(zzcq com_google_android_gms_measurement_internal_zzcq) {
        this.zzaqp++;
    }

    final void zzb(zzf com_google_android_gms_measurement_internal_zzf) {
        this.zzaqp++;
    }

    final void zzku() {
        this.zzaqq.incrementAndGet();
    }

    @WorkerThread
    protected final boolean zzkv() {
        zzcl();
        zzgs().zzaf();
        if (this.zzaqk == null || this.zzaql == 0 || !(this.zzaqk == null || this.zzaqk.booleanValue() || Math.abs(this.zzrz.elapsedRealtime() - this.zzaql) <= 1000)) {
            this.zzaql = this.zzrz.elapsedRealtime();
            zzl com_google_android_gms_measurement_internal_zzl = this.zzait;
            boolean z = true;
            boolean z2 = zzgr().zzx("android.permission.INTERNET") && zzgr().zzx("android.permission.ACCESS_NETWORK_STATE") && (Wrappers.packageManager(this.zzri).isCallerInstantApp() || this.zzapu.zzif() || (zzbk.zza(this.zzri) && zzfu.zza(this.zzri, false)));
            this.zzaqk = Boolean.valueOf(z2);
            if (this.zzaqk.booleanValue()) {
                if (!zzgr().zzu(zzgk().getGmpAppId(), zzgk().zzhb())) {
                    if (TextUtils.isEmpty(zzgk().zzhb())) {
                        z = false;
                    }
                }
                this.zzaqk = Boolean.valueOf(z);
            }
        }
        return this.zzaqk.booleanValue();
    }
}
