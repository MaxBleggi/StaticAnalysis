package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzgi;
import com.google.android.gms.internal.measurement.zzgj;
import com.google.android.gms.internal.measurement.zzgl;
import com.google.android.gms.internal.measurement.zzgo;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class zzfk implements zzcr {
    private static volatile zzfk zzatt;
    private final zzbu zzadp;
    private zzbo zzatu;
    private zzau zzatv;
    private zzr zzatw;
    private zzaz zzatx;
    private zzfg zzaty;
    private zzk zzatz;
    private final zzfq zzaua;
    private zzdt zzaub;
    private boolean zzauc;
    private boolean zzaud;
    @VisibleForTesting
    private long zzaue;
    private List<Runnable> zzauf;
    private int zzaug;
    private int zzauh;
    private boolean zzaui;
    private boolean zzauj;
    private boolean zzauk;
    private FileLock zzaul;
    private FileChannel zzaum;
    private List<Long> zzaun;
    private List<Long> zzauo;
    private long zzaup;
    private boolean zzvz;

    class zza implements zzt {
        private final /* synthetic */ zzfk zzaur;
        zzgl zzaut;
        List<Long> zzauu;
        List<zzgi> zzauv;
        private long zzauw;

        private zza(zzfk com_google_android_gms_measurement_internal_zzfk) {
            this.zzaur = com_google_android_gms_measurement_internal_zzfk;
        }

        public final void zzb(zzgl com_google_android_gms_internal_measurement_zzgl) {
            Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzgl);
            this.zzaut = com_google_android_gms_internal_measurement_zzgl;
        }

        public final boolean zza(long j, zzgi com_google_android_gms_internal_measurement_zzgi) {
            Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzgi);
            if (this.zzauv == null) {
                this.zzauv = new ArrayList();
            }
            if (this.zzauu == null) {
                this.zzauu = new ArrayList();
            }
            if (this.zzauv.size() > 0 && zza((zzgi) this.zzauv.get(0)) != zza(com_google_android_gms_internal_measurement_zzgi)) {
                return false;
            }
            long zzwe = this.zzauw + ((long) com_google_android_gms_internal_measurement_zzgi.zzwe());
            if (zzwe >= ((long) Math.max(0, ((Integer) zzag.zzajo.get()).intValue()))) {
                return false;
            }
            this.zzauw = zzwe;
            this.zzauv.add(com_google_android_gms_internal_measurement_zzgi);
            this.zzauu.add(Long.valueOf(j));
            if (this.zzauv.size() >= Math.max(1, ((Integer) zzag.zzajp.get()).intValue())) {
                return false;
            }
            return true;
        }

        private static long zza(zzgi com_google_android_gms_internal_measurement_zzgi) {
            return ((com_google_android_gms_internal_measurement_zzgi.zzaxn.longValue() / 1000) / 60) / 60;
        }
    }

    public static zzfk zzn(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzatt == null) {
            synchronized (zzfk.class) {
                if (zzatt == null) {
                    zzatt = new zzfk(new zzfp(context));
                }
            }
        }
        return zzatt;
    }

    private zzfk(zzfp com_google_android_gms_measurement_internal_zzfp) {
        this(com_google_android_gms_measurement_internal_zzfp, null);
    }

    private zzfk(zzfp com_google_android_gms_measurement_internal_zzfp, zzbu com_google_android_gms_measurement_internal_zzbu) {
        this.zzvz = null;
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzfp);
        this.zzadp = zzbu.zza(com_google_android_gms_measurement_internal_zzfp.zzri, null);
        this.zzaup = -1;
        com_google_android_gms_measurement_internal_zzbu = new zzfq(this);
        com_google_android_gms_measurement_internal_zzbu.zzq();
        this.zzaua = com_google_android_gms_measurement_internal_zzbu;
        com_google_android_gms_measurement_internal_zzbu = new zzau(this);
        com_google_android_gms_measurement_internal_zzbu.zzq();
        this.zzatv = com_google_android_gms_measurement_internal_zzbu;
        com_google_android_gms_measurement_internal_zzbu = new zzbo(this);
        com_google_android_gms_measurement_internal_zzbu.zzq();
        this.zzatu = com_google_android_gms_measurement_internal_zzbu;
        this.zzadp.zzgs().zzc(new zzfl(this, com_google_android_gms_measurement_internal_zzfp));
    }

    @WorkerThread
    private final void zza(zzfp com_google_android_gms_measurement_internal_zzfp) {
        this.zzadp.zzgs().zzaf();
        com_google_android_gms_measurement_internal_zzfp = new zzr(this);
        com_google_android_gms_measurement_internal_zzfp.zzq();
        this.zzatw = com_google_android_gms_measurement_internal_zzfp;
        this.zzadp.zzgv().zza(this.zzatu);
        com_google_android_gms_measurement_internal_zzfp = new zzk(this);
        com_google_android_gms_measurement_internal_zzfp.zzq();
        this.zzatz = com_google_android_gms_measurement_internal_zzfp;
        com_google_android_gms_measurement_internal_zzfp = new zzdt(this);
        com_google_android_gms_measurement_internal_zzfp.zzq();
        this.zzaub = com_google_android_gms_measurement_internal_zzfp;
        com_google_android_gms_measurement_internal_zzfp = new zzfg(this);
        com_google_android_gms_measurement_internal_zzfp.zzq();
        this.zzaty = com_google_android_gms_measurement_internal_zzfp;
        this.zzatx = new zzaz(this);
        if (this.zzaug != this.zzauh) {
            this.zzadp.zzgt().zzjg().zze("Not all upload components initialized", Integer.valueOf(this.zzaug), Integer.valueOf(this.zzauh));
        }
        this.zzvz = true;
    }

    @WorkerThread
    protected final void start() {
        this.zzadp.zzgs().zzaf();
        zzjt().zzij();
        if (this.zzadp.zzgu().zzanl.get() == 0) {
            this.zzadp.zzgu().zzanl.set(this.zzadp.zzbx().currentTimeMillis());
        }
        zzmb();
    }

    public final zzl zzgw() {
        return this.zzadp.zzgw();
    }

    public final zzo zzgv() {
        return this.zzadp.zzgv();
    }

    public final zzaq zzgt() {
        return this.zzadp.zzgt();
    }

    public final zzbp zzgs() {
        return this.zzadp.zzgs();
    }

    private final zzbo zzls() {
        zza(this.zzatu);
        return this.zzatu;
    }

    public final zzau zzlt() {
        zza(this.zzatv);
        return this.zzatv;
    }

    public final zzr zzjt() {
        zza(this.zzatw);
        return this.zzatw;
    }

    private final zzaz zzlu() {
        if (this.zzatx != null) {
            return this.zzatx;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzfg zzlv() {
        zza(this.zzaty);
        return this.zzaty;
    }

    public final zzk zzjs() {
        zza(this.zzatz);
        return this.zzatz;
    }

    public final zzdt zzlw() {
        zza(this.zzaub);
        return this.zzaub;
    }

    public final zzfq zzjr() {
        zza(this.zzaua);
        return this.zzaua;
    }

    public final zzao zzgq() {
        return this.zzadp.zzgq();
    }

    public final Context getContext() {
        return this.zzadp.getContext();
    }

    public final Clock zzbx() {
        return this.zzadp.zzbx();
    }

    public final zzfu zzgr() {
        return this.zzadp.zzgr();
    }

    @WorkerThread
    private final void zzaf() {
        this.zzadp.zzgs().zzaf();
    }

    final void zzlx() {
        if (!this.zzvz) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    private static void zza(zzfj com_google_android_gms_measurement_internal_zzfj) {
        if (com_google_android_gms_measurement_internal_zzfj == null) {
            throw new IllegalStateException("Upload Component not created");
        } else if (!com_google_android_gms_measurement_internal_zzfj.isInitialized()) {
            com_google_android_gms_measurement_internal_zzfj = String.valueOf(com_google_android_gms_measurement_internal_zzfj.getClass());
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(com_google_android_gms_measurement_internal_zzfj).length() + 27);
            stringBuilder.append("Component not initialized: ");
            stringBuilder.append(com_google_android_gms_measurement_internal_zzfj);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    final void zze(zzi com_google_android_gms_measurement_internal_zzi) {
        zzaf();
        zzlx();
        Preconditions.checkNotEmpty(com_google_android_gms_measurement_internal_zzi.packageName);
        zzg(com_google_android_gms_measurement_internal_zzi);
    }

    private final long zzly() {
        long currentTimeMillis = this.zzadp.zzbx().currentTimeMillis();
        zzcp zzgu = this.zzadp.zzgu();
        zzgu.zzcl();
        zzgu.zzaf();
        long j = zzgu.zzanp.get();
        if (j == 0) {
            j = 1 + ((long) zzgu.zzgr().zzmk().nextInt(86400000));
            zzgu.zzanp.set(j);
        }
        return ((((currentTimeMillis + j) / 1000) / 60) / 60) / 24;
    }

    @WorkerThread
    final void zzd(zzae com_google_android_gms_measurement_internal_zzae, String str) {
        zzfk com_google_android_gms_measurement_internal_zzfk = this;
        zzae com_google_android_gms_measurement_internal_zzae2 = com_google_android_gms_measurement_internal_zzae;
        String str2 = str;
        zzg zzbo = zzjt().zzbo(str2);
        if (zzbo != null) {
            if (!TextUtils.isEmpty(zzbo.zzak())) {
                Boolean zzc = zzc(zzbo);
                if (zzc == null) {
                    if (!"_ui".equals(com_google_android_gms_measurement_internal_zzae2.name)) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjj().zzg("Could not find package. appId", zzaq.zzby(str));
                    }
                } else if (!zzc.booleanValue()) {
                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zzg("App version does not match; dropping event. appId", zzaq.zzby(str));
                    return;
                }
                zzi com_google_android_gms_measurement_internal_zzi = r2;
                zzg com_google_android_gms_measurement_internal_zzg = zzbo;
                zzi com_google_android_gms_measurement_internal_zzi2 = new zzi(str, zzbo.getGmpAppId(), zzbo.zzak(), zzbo.zzhf(), zzbo.zzhg(), zzbo.zzhh(), zzbo.zzhi(), null, zzbo.isMeasurementEnabled(), false, com_google_android_gms_measurement_internal_zzg.getFirebaseInstanceId(), com_google_android_gms_measurement_internal_zzg.zzhv(), 0, 0, com_google_android_gms_measurement_internal_zzg.zzhw(), com_google_android_gms_measurement_internal_zzg.zzhx(), false, com_google_android_gms_measurement_internal_zzg.zzhb());
                zzc(com_google_android_gms_measurement_internal_zzae2, com_google_android_gms_measurement_internal_zzi);
                return;
            }
        }
        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjn().zzg("No app data available; dropping event", str2);
    }

    @WorkerThread
    final void zzc(zzae com_google_android_gms_measurement_internal_zzae, zzi com_google_android_gms_measurement_internal_zzi) {
        zzfk com_google_android_gms_measurement_internal_zzfk = this;
        zzae com_google_android_gms_measurement_internal_zzae2 = com_google_android_gms_measurement_internal_zzae;
        zzi com_google_android_gms_measurement_internal_zzi2 = com_google_android_gms_measurement_internal_zzi;
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzi);
        Preconditions.checkNotEmpty(com_google_android_gms_measurement_internal_zzi2.packageName);
        zzaf();
        zzlx();
        String str = com_google_android_gms_measurement_internal_zzi2.packageName;
        long j = com_google_android_gms_measurement_internal_zzae2.zzais;
        if (!zzjr().zze(com_google_android_gms_measurement_internal_zzae2, com_google_android_gms_measurement_internal_zzi2)) {
            return;
        }
        if (com_google_android_gms_measurement_internal_zzi2.zzagg) {
            zzjt().beginTransaction();
            try {
                List emptyList;
                zzcp zzjt = zzjt();
                Preconditions.checkNotEmpty(str);
                zzjt.zzaf();
                zzjt.zzcl();
                if (j < 0) {
                    zzjt.zzgt().zzjj().zze("Invalid time querying timed out conditional properties", zzaq.zzby(str), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzjt.zzb("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str, String.valueOf(j)});
                }
                for (zzm com_google_android_gms_measurement_internal_zzm : r4) {
                    if (com_google_android_gms_measurement_internal_zzm != null) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjn().zzd("User property timed out", com_google_android_gms_measurement_internal_zzm.packageName, com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzm.zzahe.name), com_google_android_gms_measurement_internal_zzm.zzahe.getValue());
                        if (com_google_android_gms_measurement_internal_zzm.zzahf != null) {
                            zzd(new zzae(com_google_android_gms_measurement_internal_zzm.zzahf, j), com_google_android_gms_measurement_internal_zzi2);
                        }
                        zzjt().zzk(str, com_google_android_gms_measurement_internal_zzm.zzahe.name);
                    }
                }
                zzjt = zzjt();
                Preconditions.checkNotEmpty(str);
                zzjt.zzaf();
                zzjt.zzcl();
                if (j < 0) {
                    zzjt.zzgt().zzjj().zze("Invalid time querying expired conditional properties", zzaq.zzby(str), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzjt.zzb("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str, String.valueOf(j)});
                }
                List arrayList = new ArrayList(r4.size());
                for (zzm com_google_android_gms_measurement_internal_zzm2 : r4) {
                    if (com_google_android_gms_measurement_internal_zzm2 != null) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjn().zzd("User property expired", com_google_android_gms_measurement_internal_zzm2.packageName, com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzm2.zzahe.name), com_google_android_gms_measurement_internal_zzm2.zzahe.getValue());
                        zzjt().zzh(str, com_google_android_gms_measurement_internal_zzm2.zzahe.name);
                        if (com_google_android_gms_measurement_internal_zzm2.zzahh != null) {
                            arrayList.add(com_google_android_gms_measurement_internal_zzm2.zzahh);
                        }
                        zzjt().zzk(str, com_google_android_gms_measurement_internal_zzm2.zzahe.name);
                    }
                }
                ArrayList arrayList2 = (ArrayList) arrayList;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    zzd(new zzae((zzae) obj, j), com_google_android_gms_measurement_internal_zzi2);
                }
                zzjt = zzjt();
                String str2 = com_google_android_gms_measurement_internal_zzae2.name;
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotEmpty(str2);
                zzjt.zzaf();
                zzjt.zzcl();
                List emptyList2;
                if (j < 0) {
                    zzjt.zzgt().zzjj().zzd("Invalid time querying triggered conditional properties", zzaq.zzby(str), zzjt.zzgq().zzbv(str2), Long.valueOf(j));
                    emptyList2 = Collections.emptyList();
                } else {
                    emptyList2 = zzjt.zzb("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str, str2, String.valueOf(j)});
                }
                List arrayList3 = new ArrayList(r3.size());
                for (zzm com_google_android_gms_measurement_internal_zzm3 : r3) {
                    if (com_google_android_gms_measurement_internal_zzm3 != null) {
                        zzfr com_google_android_gms_measurement_internal_zzfr = com_google_android_gms_measurement_internal_zzm3.zzahe;
                        zzft com_google_android_gms_measurement_internal_zzft = r4;
                        zzft com_google_android_gms_measurement_internal_zzft2 = new zzft(com_google_android_gms_measurement_internal_zzm3.packageName, com_google_android_gms_measurement_internal_zzm3.origin, com_google_android_gms_measurement_internal_zzfr.name, j, com_google_android_gms_measurement_internal_zzfr.getValue());
                        if (zzjt().zza(com_google_android_gms_measurement_internal_zzft)) {
                            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjn().zzd("User property triggered", com_google_android_gms_measurement_internal_zzm3.packageName, com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzft.name), com_google_android_gms_measurement_internal_zzft.value);
                        } else {
                            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zzd("Too many active user properties, ignoring", zzaq.zzby(com_google_android_gms_measurement_internal_zzm3.packageName), com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzft.name), com_google_android_gms_measurement_internal_zzft.value);
                        }
                        if (com_google_android_gms_measurement_internal_zzm3.zzahg != null) {
                            arrayList3.add(com_google_android_gms_measurement_internal_zzm3.zzahg);
                        }
                        com_google_android_gms_measurement_internal_zzm3.zzahe = new zzfr(com_google_android_gms_measurement_internal_zzft);
                        com_google_android_gms_measurement_internal_zzm3.active = true;
                        zzjt().zza(com_google_android_gms_measurement_internal_zzm3);
                    }
                }
                zzd(com_google_android_gms_measurement_internal_zzae, com_google_android_gms_measurement_internal_zzi);
                ArrayList arrayList4 = (ArrayList) arrayList3;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList4.get(i2);
                    i2++;
                    zzd(new zzae((zzae) obj2, j), com_google_android_gms_measurement_internal_zzi2);
                }
                zzjt().setTransactionSuccessful();
            } finally {
                zzjt().endTransaction();
            }
        } else {
            zzg(com_google_android_gms_measurement_internal_zzi2);
        }
    }

    @WorkerThread
    private final void zzd(zzae com_google_android_gms_measurement_internal_zzae, zzi com_google_android_gms_measurement_internal_zzi) {
        zzfk com_google_android_gms_measurement_internal_zzfk = this;
        zzae com_google_android_gms_measurement_internal_zzae2 = com_google_android_gms_measurement_internal_zzae;
        zzi com_google_android_gms_measurement_internal_zzi2 = com_google_android_gms_measurement_internal_zzi;
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzi);
        Preconditions.checkNotEmpty(com_google_android_gms_measurement_internal_zzi2.packageName);
        long nanoTime = System.nanoTime();
        zzaf();
        zzlx();
        String str = com_google_android_gms_measurement_internal_zzi2.packageName;
        if (!zzjr().zze(com_google_android_gms_measurement_internal_zzae2, com_google_android_gms_measurement_internal_zzi2)) {
            return;
        }
        if (com_google_android_gms_measurement_internal_zzi2.zzagg) {
            int i = 0;
            zzg zzbo;
            if (zzls().zzo(str, com_google_android_gms_measurement_internal_zzae2.name)) {
                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjj().zze("Dropping blacklisted event. appId", zzaq.zzby(str), com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zzbv(com_google_android_gms_measurement_internal_zzae2.name));
                if (zzls().zzcn(str) || zzls().zzco(str)) {
                    i = 1;
                }
                if (i == 0 && !"_err".equals(com_google_android_gms_measurement_internal_zzae2.name)) {
                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zza(str, 11, "_ev", com_google_android_gms_measurement_internal_zzae2.name, 0);
                }
                if (i != 0) {
                    zzbo = zzjt().zzbo(str);
                    if (zzbo != null) {
                        if (Math.abs(com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis() - Math.max(zzbo.zzhl(), zzbo.zzhk())) > ((Long) zzag.zzakf.get()).longValue()) {
                            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjn().zzca("Fetching config for blacklisted app");
                            zzb(zzbo);
                        }
                    }
                }
                return;
            }
            long round;
            if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().isLoggable(2)) {
                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzg("Logging event", com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zzb(com_google_android_gms_measurement_internal_zzae2));
            }
            zzjt().beginTransaction();
            zzg(com_google_android_gms_measurement_internal_zzi2);
            if ("_iap".equals(com_google_android_gms_measurement_internal_zzae2.name) || Event.ECOMMERCE_PURCHASE.equals(com_google_android_gms_measurement_internal_zzae2.name)) {
                Object string = com_google_android_gms_measurement_internal_zzae2.zzaig.getString(Param.CURRENCY);
                if (Event.ECOMMERCE_PURCHASE.equals(com_google_android_gms_measurement_internal_zzae2.name)) {
                    double doubleValue = com_google_android_gms_measurement_internal_zzae2.zzaig.zzbt("value").doubleValue() * 1000000.0d;
                    if (doubleValue == 0.0d) {
                        doubleValue = (double) com_google_android_gms_measurement_internal_zzae2.zzaig.getLong("value").longValue();
                        Double.isNaN(doubleValue);
                        doubleValue *= 1000000.0d;
                    }
                    if (doubleValue > 9.223372036854776E18d || doubleValue < -9.223372036854776E18d) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjj().zze("Data lost. Currency value is too big. appId", zzaq.zzby(str), Double.valueOf(doubleValue));
                        string = null;
                        if (string == null) {
                            zzjt().setTransactionSuccessful();
                            zzjt().endTransaction();
                            return;
                        }
                    }
                    round = Math.round(doubleValue);
                } else {
                    round = com_google_android_gms_measurement_internal_zzae2.zzaig.getLong("value").longValue();
                }
                if (!TextUtils.isEmpty(string)) {
                    String toUpperCase = string.toUpperCase(Locale.US);
                    if (toUpperCase.matches("[A-Z]{3}")) {
                        zzft com_google_android_gms_measurement_internal_zzft;
                        String valueOf = String.valueOf("_ltv_");
                        toUpperCase = String.valueOf(toUpperCase);
                        String concat = toUpperCase.length() != 0 ? valueOf.concat(toUpperCase) : new String(valueOf);
                        zzft zzi = zzjt().zzi(str, concat);
                        if (zzi != null) {
                            if (zzi.value instanceof Long) {
                                com_google_android_gms_measurement_internal_zzft = new zzft(str, com_google_android_gms_measurement_internal_zzae2.origin, concat, com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis(), Long.valueOf(((Long) zzi.value).longValue() + round));
                                if (!zzjt().zza(zzi)) {
                                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zzd("Too many unique user properties are set. Ignoring user property. appId", zzaq.zzby(str), com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zzbx(zzi.name), zzi.value);
                                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zza(str, 9, null, null, 0);
                                }
                            }
                        }
                        zzcp zzjt = zzjt();
                        int zzb = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzb(str, zzag.zzakk) - 1;
                        Preconditions.checkNotEmpty(str);
                        zzjt.zzaf();
                        zzjt.zzcl();
                        try {
                            zzjt.getWritableDatabase().execSQL("delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);", new String[]{str, str, String.valueOf(zzb)});
                        } catch (SQLiteException e) {
                            zzjt.zzgt().zzjg().zze("Error pruning currencies. appId", zzaq.zzby(str), e);
                        } catch (Throwable th) {
                            zzjt().endTransaction();
                        }
                        com_google_android_gms_measurement_internal_zzft = new zzft(str, com_google_android_gms_measurement_internal_zzae2.origin, concat, com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis(), Long.valueOf(round));
                        if (zzjt().zza(zzi)) {
                            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zzd("Too many unique user properties are set. Ignoring user property. appId", zzaq.zzby(str), com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zzbx(zzi.name), zzi.value);
                            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zza(str, 9, null, null, 0);
                        }
                    }
                }
                string = 1;
                if (string == null) {
                    zzjt().setTransactionSuccessful();
                    zzjt().endTransaction();
                    return;
                }
            }
            boolean zzcv = zzfu.zzcv(com_google_android_gms_measurement_internal_zzae2.name);
            boolean equals = "_err".equals(com_google_android_gms_measurement_internal_zzae2.name);
            long j = nanoTime;
            zzs zza = zzjt().zza(zzly(), str, true, zzcv, false, equals, false);
            round = zza.zzahu - ((long) ((Integer) zzag.zzajq.get()).intValue());
            if (round > 0) {
                if (round % 1000 == 1) {
                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zze("Data loss. Too many events logged. appId, count", zzaq.zzby(str), Long.valueOf(zza.zzahu));
                }
                zzjt().setTransactionSuccessful();
                zzjt().endTransaction();
                return;
            }
            zzz zza2;
            zzaa zzai;
            if (zzcv) {
                zzs com_google_android_gms_measurement_internal_zzs = zza;
                round = zza.zzaht - ((long) ((Integer) zzag.zzajs.get()).intValue());
                if (round > 0) {
                    if (round % 1000 == 1) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zze("Data loss. Too many public events logged. appId, count", zzaq.zzby(str), Long.valueOf(com_google_android_gms_measurement_internal_zzs.zzaht));
                    }
                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zza(str, 16, "_ev", com_google_android_gms_measurement_internal_zzae2.name, 0);
                    zzjt().setTransactionSuccessful();
                    zzjt().endTransaction();
                    return;
                }
                zza = com_google_android_gms_measurement_internal_zzs;
            }
            if (equals) {
                round = zza.zzahw - ((long) Math.max(0, Math.min(1000000, com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzb(com_google_android_gms_measurement_internal_zzi2.packageName, zzag.zzajr))));
                if (round > 0) {
                    if (round == 1) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zze("Too many error events logged. appId, count", zzaq.zzby(str), Long.valueOf(zza.zzahw));
                    }
                    zzjt().setTransactionSuccessful();
                    zzjt().endTransaction();
                    return;
                }
            }
            Bundle zziy = com_google_android_gms_measurement_internal_zzae2.zzaig.zziy();
            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zza(zziy, "_o", com_google_android_gms_measurement_internal_zzae2.origin);
            if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zzdb(str)) {
                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zza(zziy, "_dbg", Long.valueOf(1));
                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zza(zziy, "_r", Long.valueOf(1));
            }
            if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzbj(com_google_android_gms_measurement_internal_zzi2.packageName) && "_s".equals(com_google_android_gms_measurement_internal_zzae2.name)) {
                zzft zzi2 = zzjt().zzi(com_google_android_gms_measurement_internal_zzi2.packageName, "_sno");
                if (zzi2 != null && (zzi2.value instanceof Long)) {
                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zza(zziy, "_sno", zzi2.value);
                }
            }
            long zzbp = zzjt().zzbp(str);
            if (zzbp > 0) {
                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjj().zze("Data lost. Too many events stored on disk, deleted. appId", zzaq.zzby(str), Long.valueOf(zzbp));
            }
            String str2 = str;
            zzz com_google_android_gms_measurement_internal_zzz = new zzz(com_google_android_gms_measurement_internal_zzfk.zzadp, com_google_android_gms_measurement_internal_zzae2.origin, str, com_google_android_gms_measurement_internal_zzae2.name, com_google_android_gms_measurement_internal_zzae2.zzais, 0, zziy);
            zzaa zzg = zzjt().zzg(str2, com_google_android_gms_measurement_internal_zzz.name);
            if (zzg != null) {
                zza2 = com_google_android_gms_measurement_internal_zzz.zza(com_google_android_gms_measurement_internal_zzfk.zzadp, zzg.zzaij);
                zzai = zzg.zzai(zza2.timestamp);
            } else if (zzjt().zzbs(str2) < 500 || !zzcv) {
                zzaa com_google_android_gms_measurement_internal_zzaa = new zzaa(str2, com_google_android_gms_measurement_internal_zzz.name, 0, 0, com_google_android_gms_measurement_internal_zzz.timestamp, 0, null, null, null, null);
            } else {
                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zzd("Too many event names used, ignoring event. appId, name, supported count", zzaq.zzby(str2), com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zzbv(com_google_android_gms_measurement_internal_zzz.name), Integer.valueOf(500));
                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zza(str2, 8, null, null, 0);
                zzjt().endTransaction();
                return;
            }
            zzjt().zza(zzai);
            zzaf();
            zzlx();
            Preconditions.checkNotNull(zza2);
            Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzi);
            Preconditions.checkNotEmpty(zza2.zztt);
            Preconditions.checkArgument(zza2.zztt.equals(com_google_android_gms_measurement_internal_zzi2.packageName));
            zzgl com_google_android_gms_internal_measurement_zzgl = new zzgl();
            com_google_android_gms_internal_measurement_zzgl.zzaxt = Integer.valueOf(1);
            com_google_android_gms_internal_measurement_zzgl.zzayb = "android";
            com_google_android_gms_internal_measurement_zzgl.zztt = com_google_android_gms_measurement_internal_zzi2.packageName;
            com_google_android_gms_internal_measurement_zzgl.zzage = com_google_android_gms_measurement_internal_zzi2.zzage;
            com_google_android_gms_internal_measurement_zzgl.zzts = com_google_android_gms_measurement_internal_zzi2.zzts;
            com_google_android_gms_internal_measurement_zzgl.zzayn = com_google_android_gms_measurement_internal_zzi2.zzagd == -2147483648L ? null : Integer.valueOf((int) com_google_android_gms_measurement_internal_zzi2.zzagd);
            com_google_android_gms_internal_measurement_zzgl.zzayf = Long.valueOf(com_google_android_gms_measurement_internal_zzi2.zzadt);
            com_google_android_gms_internal_measurement_zzgl.zzafx = com_google_android_gms_measurement_internal_zzi2.zzafx;
            com_google_android_gms_internal_measurement_zzgl.zzaxc = com_google_android_gms_measurement_internal_zzi2.zzagk;
            com_google_android_gms_internal_measurement_zzgl.zzayj = com_google_android_gms_measurement_internal_zzi2.zzagf == 0 ? null : Long.valueOf(com_google_android_gms_measurement_internal_zzi2.zzagf);
            if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zze(com_google_android_gms_measurement_internal_zzi2.packageName, zzag.zzalq)) {
                com_google_android_gms_internal_measurement_zzgl.zzayx = zzjr().zzmi();
            }
            Pair zzcb = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzcb(com_google_android_gms_measurement_internal_zzi2.packageName);
            if (zzcb == null || TextUtils.isEmpty((CharSequence) zzcb.first)) {
                if (!com_google_android_gms_measurement_internal_zzfk.zzadp.zzgp().zzl(com_google_android_gms_measurement_internal_zzfk.zzadp.getContext()) && com_google_android_gms_measurement_internal_zzi2.zzagj) {
                    String string2 = Secure.getString(com_google_android_gms_measurement_internal_zzfk.zzadp.getContext().getContentResolver(), "android_id");
                    if (string2 == null) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjj().zzg("null secure ID. appId", zzaq.zzby(com_google_android_gms_internal_measurement_zzgl.zztt));
                        string2 = "null";
                    } else if (string2.isEmpty()) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjj().zzg("empty secure ID. appId", zzaq.zzby(com_google_android_gms_internal_measurement_zzgl.zztt));
                    }
                    com_google_android_gms_internal_measurement_zzgl.zzayq = string2;
                }
            } else if (com_google_android_gms_measurement_internal_zzi2.zzagi) {
                com_google_android_gms_internal_measurement_zzgl.zzayh = (String) zzcb.first;
                com_google_android_gms_internal_measurement_zzgl.zzayi = (Boolean) zzcb.second;
            }
            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgp().zzcl();
            com_google_android_gms_internal_measurement_zzgl.zzayd = Build.MODEL;
            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgp().zzcl();
            com_google_android_gms_internal_measurement_zzgl.zzayc = VERSION.RELEASE;
            com_google_android_gms_internal_measurement_zzgl.zzaye = Integer.valueOf((int) com_google_android_gms_measurement_internal_zzfk.zzadp.zzgp().zziw());
            com_google_android_gms_internal_measurement_zzgl.zzaid = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgp().zzix();
            com_google_android_gms_internal_measurement_zzgl.zzayg = null;
            com_google_android_gms_internal_measurement_zzgl.zzaxw = null;
            com_google_android_gms_internal_measurement_zzgl.zzaxx = null;
            com_google_android_gms_internal_measurement_zzgl.zzaxy = null;
            com_google_android_gms_internal_measurement_zzgl.zzays = Long.valueOf(com_google_android_gms_measurement_internal_zzi2.zzagh);
            if (com_google_android_gms_measurement_internal_zzfk.zzadp.isEnabled() && zzo.zzie()) {
                com_google_android_gms_internal_measurement_zzgl.zzayt = null;
            }
            zzbo = zzjt().zzbo(com_google_android_gms_measurement_internal_zzi2.packageName);
            if (zzbo == null) {
                zzbo = new zzg(com_google_android_gms_measurement_internal_zzfk.zzadp, com_google_android_gms_measurement_internal_zzi2.packageName);
                zzbo.zzal(com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr().zzmm());
                zzbo.zzap(com_google_android_gms_measurement_internal_zzi2.zzafz);
                zzbo.zzam(com_google_android_gms_measurement_internal_zzi2.zzafx);
                zzbo.zzao(com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzcc(com_google_android_gms_measurement_internal_zzi2.packageName));
                zzbo.zzx(0);
                zzbo.zzs(0);
                zzbo.zzt(0);
                zzbo.setAppVersion(com_google_android_gms_measurement_internal_zzi2.zzts);
                zzbo.zzu(com_google_android_gms_measurement_internal_zzi2.zzagd);
                zzbo.zzaq(com_google_android_gms_measurement_internal_zzi2.zzage);
                zzbo.zzv(com_google_android_gms_measurement_internal_zzi2.zzadt);
                zzbo.zzw(com_google_android_gms_measurement_internal_zzi2.zzagf);
                zzbo.setMeasurementEnabled(com_google_android_gms_measurement_internal_zzi2.zzagg);
                zzbo.zzag(com_google_android_gms_measurement_internal_zzi2.zzagh);
                zzjt().zza(zzbo);
            }
            com_google_android_gms_internal_measurement_zzgl.zzafw = zzbo.getAppInstanceId();
            com_google_android_gms_internal_measurement_zzgl.zzafz = zzbo.getFirebaseInstanceId();
            List zzbn = zzjt().zzbn(com_google_android_gms_measurement_internal_zzi2.packageName);
            com_google_android_gms_internal_measurement_zzgl.zzaxv = new zzgo[zzbn.size()];
            for (int i2 = 0; i2 < zzbn.size(); i2++) {
                zzgo com_google_android_gms_internal_measurement_zzgo = new zzgo();
                com_google_android_gms_internal_measurement_zzgl.zzaxv[i2] = com_google_android_gms_internal_measurement_zzgo;
                com_google_android_gms_internal_measurement_zzgo.name = ((zzft) zzbn.get(i2)).name;
                com_google_android_gms_internal_measurement_zzgo.zzazg = Long.valueOf(((zzft) zzbn.get(i2)).zzaux);
                zzjr().zza(com_google_android_gms_internal_measurement_zzgo, ((zzft) zzbn.get(i2)).value);
            }
            try {
                boolean zzp;
                long zza3 = zzjt().zza(com_google_android_gms_internal_measurement_zzgl);
                zzr zzjt2 = zzjt();
                if (zza2.zzaig != null) {
                    Iterator it = zza2.zzaig.iterator();
                    while (it.hasNext()) {
                        if ("_r".equals((String) it.next())) {
                            break;
                        }
                    }
                    zzp = zzls().zzp(zza2.zztt, zza2.name);
                    zzs zza4 = zzjt().zza(zzly(), zza2.zztt, false, false, false, false, false);
                    if (zzp && zza4.zzahx < ((long) com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzas(zza2.zztt))) {
                        zzp = true;
                        if (zzjt2.zza(zza2, zza3, zzp)) {
                            com_google_android_gms_measurement_internal_zzfk.zzaue = 0;
                        }
                        zzjt().setTransactionSuccessful();
                        if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().isLoggable(2)) {
                            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzg("Event recorded", com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zza(zza2));
                        }
                        zzjt().endTransaction();
                        zzmb();
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzg("Background event processing time, ms", Long.valueOf(((System.nanoTime() - j) + 500000) / 1000000));
                        return;
                    }
                }
                zzp = false;
                if (zzjt2.zza(zza2, zza3, zzp)) {
                    com_google_android_gms_measurement_internal_zzfk.zzaue = 0;
                }
            } catch (IOException e2) {
                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zze("Data loss. Failed to insert raw event metadata. appId", zzaq.zzby(com_google_android_gms_internal_measurement_zzgl.zztt), e2);
            }
            zzjt().setTransactionSuccessful();
            if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().isLoggable(2)) {
                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzg("Event recorded", com_google_android_gms_measurement_internal_zzfk.zzadp.zzgq().zza(zza2));
            }
            zzjt().endTransaction();
            zzmb();
            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzg("Background event processing time, ms", Long.valueOf(((System.nanoTime() - j) + 500000) / 1000000));
            return;
        }
        zzg(com_google_android_gms_measurement_internal_zzi2);
    }

    @androidx.annotation.WorkerThread
    final void zzlz() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r17 = this;
        r1 = r17;
        r17.zzaf();
        r17.zzlx();
        r0 = 1;
        r1.zzauk = r0;
        r2 = 0;
        r3 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r3.zzgw();	 Catch:{ all -> 0x02db }
        r3 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r3 = r3.zzgl();	 Catch:{ all -> 0x02db }
        r3 = r3.zzli();	 Catch:{ all -> 0x02db }
        if (r3 != 0) goto L_0x0032;	 Catch:{ all -> 0x02db }
    L_0x001d:
        r0 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r0 = r0.zzgt();	 Catch:{ all -> 0x02db }
        r0 = r0.zzjj();	 Catch:{ all -> 0x02db }
        r3 = "Upload data called on the client side before use of service was decided";	 Catch:{ all -> 0x02db }
        r0.zzca(r3);	 Catch:{ all -> 0x02db }
        r1.zzauk = r2;
        r17.zzmc();
        return;
    L_0x0032:
        r3 = r3.booleanValue();	 Catch:{ all -> 0x02db }
        if (r3 == 0) goto L_0x004d;	 Catch:{ all -> 0x02db }
    L_0x0038:
        r0 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r0 = r0.zzgt();	 Catch:{ all -> 0x02db }
        r0 = r0.zzjg();	 Catch:{ all -> 0x02db }
        r3 = "Upload called in the client side when service should be used";	 Catch:{ all -> 0x02db }
        r0.zzca(r3);	 Catch:{ all -> 0x02db }
        r1.zzauk = r2;
        r17.zzmc();
        return;
    L_0x004d:
        r3 = r1.zzaue;	 Catch:{ all -> 0x02db }
        r5 = 0;	 Catch:{ all -> 0x02db }
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));	 Catch:{ all -> 0x02db }
        if (r7 <= 0) goto L_0x005e;	 Catch:{ all -> 0x02db }
    L_0x0055:
        r17.zzmb();	 Catch:{ all -> 0x02db }
        r1.zzauk = r2;
        r17.zzmc();
        return;
    L_0x005e:
        r17.zzaf();	 Catch:{ all -> 0x02db }
        r3 = r1.zzaun;	 Catch:{ all -> 0x02db }
        if (r3 == 0) goto L_0x0067;	 Catch:{ all -> 0x02db }
    L_0x0065:
        r3 = 1;	 Catch:{ all -> 0x02db }
        goto L_0x0068;	 Catch:{ all -> 0x02db }
    L_0x0067:
        r3 = 0;	 Catch:{ all -> 0x02db }
    L_0x0068:
        if (r3 == 0) goto L_0x007f;	 Catch:{ all -> 0x02db }
    L_0x006a:
        r0 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r0 = r0.zzgt();	 Catch:{ all -> 0x02db }
        r0 = r0.zzjo();	 Catch:{ all -> 0x02db }
        r3 = "Uploading requested multiple times";	 Catch:{ all -> 0x02db }
        r0.zzca(r3);	 Catch:{ all -> 0x02db }
        r1.zzauk = r2;
        r17.zzmc();
        return;
    L_0x007f:
        r3 = r17.zzlt();	 Catch:{ all -> 0x02db }
        r3 = r3.zzfb();	 Catch:{ all -> 0x02db }
        if (r3 != 0) goto L_0x00a1;	 Catch:{ all -> 0x02db }
    L_0x0089:
        r0 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r0 = r0.zzgt();	 Catch:{ all -> 0x02db }
        r0 = r0.zzjo();	 Catch:{ all -> 0x02db }
        r3 = "Network not connected, ignoring upload request";	 Catch:{ all -> 0x02db }
        r0.zzca(r3);	 Catch:{ all -> 0x02db }
        r17.zzmb();	 Catch:{ all -> 0x02db }
        r1.zzauk = r2;
        r17.zzmc();
        return;
    L_0x00a1:
        r3 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r3 = r3.zzbx();	 Catch:{ all -> 0x02db }
        r3 = r3.currentTimeMillis();	 Catch:{ all -> 0x02db }
        r7 = com.google.android.gms.measurement.internal.zzo.zzic();	 Catch:{ all -> 0x02db }
        r9 = 0;	 Catch:{ all -> 0x02db }
        r7 = r3 - r7;	 Catch:{ all -> 0x02db }
        r9 = 0;	 Catch:{ all -> 0x02db }
        r1.zzd(r9, r7);	 Catch:{ all -> 0x02db }
        r7 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r7 = r7.zzgu();	 Catch:{ all -> 0x02db }
        r7 = r7.zzanl;	 Catch:{ all -> 0x02db }
        r7 = r7.get();	 Catch:{ all -> 0x02db }
        r10 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1));	 Catch:{ all -> 0x02db }
        if (r10 == 0) goto L_0x00e0;	 Catch:{ all -> 0x02db }
    L_0x00c6:
        r5 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r5 = r5.zzgt();	 Catch:{ all -> 0x02db }
        r5 = r5.zzjn();	 Catch:{ all -> 0x02db }
        r6 = "Uploading events. Elapsed time since last upload attempt (ms)";	 Catch:{ all -> 0x02db }
        r10 = 0;	 Catch:{ all -> 0x02db }
        r7 = r3 - r7;	 Catch:{ all -> 0x02db }
        r7 = java.lang.Math.abs(r7);	 Catch:{ all -> 0x02db }
        r7 = java.lang.Long.valueOf(r7);	 Catch:{ all -> 0x02db }
        r5.zzg(r6, r7);	 Catch:{ all -> 0x02db }
    L_0x00e0:
        r5 = r17.zzjt();	 Catch:{ all -> 0x02db }
        r5 = r5.zzih();	 Catch:{ all -> 0x02db }
        r6 = android.text.TextUtils.isEmpty(r5);	 Catch:{ all -> 0x02db }
        r7 = -1;	 Catch:{ all -> 0x02db }
        if (r6 != 0) goto L_0x02b2;	 Catch:{ all -> 0x02db }
    L_0x00f0:
        r10 = r1.zzaup;	 Catch:{ all -> 0x02db }
        r6 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1));	 Catch:{ all -> 0x02db }
        if (r6 != 0) goto L_0x0100;	 Catch:{ all -> 0x02db }
    L_0x00f6:
        r6 = r17.zzjt();	 Catch:{ all -> 0x02db }
        r6 = r6.zzio();	 Catch:{ all -> 0x02db }
        r1.zzaup = r6;	 Catch:{ all -> 0x02db }
    L_0x0100:
        r6 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r6 = r6.zzgv();	 Catch:{ all -> 0x02db }
        r7 = com.google.android.gms.measurement.internal.zzag.zzajm;	 Catch:{ all -> 0x02db }
        r6 = r6.zzb(r5, r7);	 Catch:{ all -> 0x02db }
        r7 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r7 = r7.zzgv();	 Catch:{ all -> 0x02db }
        r8 = com.google.android.gms.measurement.internal.zzag.zzajn;	 Catch:{ all -> 0x02db }
        r7 = r7.zzb(r5, r8);	 Catch:{ all -> 0x02db }
        r7 = java.lang.Math.max(r2, r7);	 Catch:{ all -> 0x02db }
        r8 = r17.zzjt();	 Catch:{ all -> 0x02db }
        r6 = r8.zzb(r5, r6, r7);	 Catch:{ all -> 0x02db }
        r7 = r6.isEmpty();	 Catch:{ all -> 0x02db }
        if (r7 != 0) goto L_0x02d5;	 Catch:{ all -> 0x02db }
    L_0x012a:
        r7 = r6.iterator();	 Catch:{ all -> 0x02db }
    L_0x012e:
        r8 = r7.hasNext();	 Catch:{ all -> 0x02db }
        if (r8 == 0) goto L_0x0149;	 Catch:{ all -> 0x02db }
    L_0x0134:
        r8 = r7.next();	 Catch:{ all -> 0x02db }
        r8 = (android.util.Pair) r8;	 Catch:{ all -> 0x02db }
        r8 = r8.first;	 Catch:{ all -> 0x02db }
        r8 = (com.google.android.gms.internal.measurement.zzgl) r8;	 Catch:{ all -> 0x02db }
        r10 = r8.zzayh;	 Catch:{ all -> 0x02db }
        r10 = android.text.TextUtils.isEmpty(r10);	 Catch:{ all -> 0x02db }
        if (r10 != 0) goto L_0x012e;	 Catch:{ all -> 0x02db }
    L_0x0146:
        r7 = r8.zzayh;	 Catch:{ all -> 0x02db }
        goto L_0x014a;	 Catch:{ all -> 0x02db }
    L_0x0149:
        r7 = r9;	 Catch:{ all -> 0x02db }
    L_0x014a:
        if (r7 == 0) goto L_0x0175;	 Catch:{ all -> 0x02db }
    L_0x014c:
        r8 = 0;	 Catch:{ all -> 0x02db }
    L_0x014d:
        r10 = r6.size();	 Catch:{ all -> 0x02db }
        if (r8 >= r10) goto L_0x0175;	 Catch:{ all -> 0x02db }
    L_0x0153:
        r10 = r6.get(r8);	 Catch:{ all -> 0x02db }
        r10 = (android.util.Pair) r10;	 Catch:{ all -> 0x02db }
        r10 = r10.first;	 Catch:{ all -> 0x02db }
        r10 = (com.google.android.gms.internal.measurement.zzgl) r10;	 Catch:{ all -> 0x02db }
        r11 = r10.zzayh;	 Catch:{ all -> 0x02db }
        r11 = android.text.TextUtils.isEmpty(r11);	 Catch:{ all -> 0x02db }
        if (r11 != 0) goto L_0x0172;	 Catch:{ all -> 0x02db }
    L_0x0165:
        r10 = r10.zzayh;	 Catch:{ all -> 0x02db }
        r10 = r10.equals(r7);	 Catch:{ all -> 0x02db }
        if (r10 != 0) goto L_0x0172;	 Catch:{ all -> 0x02db }
    L_0x016d:
        r6 = r6.subList(r2, r8);	 Catch:{ all -> 0x02db }
        goto L_0x0175;	 Catch:{ all -> 0x02db }
    L_0x0172:
        r8 = r8 + 1;	 Catch:{ all -> 0x02db }
        goto L_0x014d;	 Catch:{ all -> 0x02db }
    L_0x0175:
        r7 = new com.google.android.gms.internal.measurement.zzgk;	 Catch:{ all -> 0x02db }
        r7.<init>();	 Catch:{ all -> 0x02db }
        r8 = r6.size();	 Catch:{ all -> 0x02db }
        r8 = new com.google.android.gms.internal.measurement.zzgl[r8];	 Catch:{ all -> 0x02db }
        r7.zzaxr = r8;	 Catch:{ all -> 0x02db }
        r8 = new java.util.ArrayList;	 Catch:{ all -> 0x02db }
        r10 = r6.size();	 Catch:{ all -> 0x02db }
        r8.<init>(r10);	 Catch:{ all -> 0x02db }
        r10 = com.google.android.gms.measurement.internal.zzo.zzie();	 Catch:{ all -> 0x02db }
        if (r10 == 0) goto L_0x019f;	 Catch:{ all -> 0x02db }
    L_0x0191:
        r10 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r10 = r10.zzgv();	 Catch:{ all -> 0x02db }
        r10 = r10.zzau(r5);	 Catch:{ all -> 0x02db }
        if (r10 == 0) goto L_0x019f;	 Catch:{ all -> 0x02db }
    L_0x019d:
        r10 = 1;	 Catch:{ all -> 0x02db }
        goto L_0x01a0;	 Catch:{ all -> 0x02db }
    L_0x019f:
        r10 = 0;	 Catch:{ all -> 0x02db }
    L_0x01a0:
        r11 = 0;	 Catch:{ all -> 0x02db }
    L_0x01a1:
        r12 = r7.zzaxr;	 Catch:{ all -> 0x02db }
        r12 = r12.length;	 Catch:{ all -> 0x02db }
        if (r11 >= r12) goto L_0x01f9;	 Catch:{ all -> 0x02db }
    L_0x01a6:
        r12 = r7.zzaxr;	 Catch:{ all -> 0x02db }
        r13 = r6.get(r11);	 Catch:{ all -> 0x02db }
        r13 = (android.util.Pair) r13;	 Catch:{ all -> 0x02db }
        r13 = r13.first;	 Catch:{ all -> 0x02db }
        r13 = (com.google.android.gms.internal.measurement.zzgl) r13;	 Catch:{ all -> 0x02db }
        r12[r11] = r13;	 Catch:{ all -> 0x02db }
        r12 = r6.get(r11);	 Catch:{ all -> 0x02db }
        r12 = (android.util.Pair) r12;	 Catch:{ all -> 0x02db }
        r12 = r12.second;	 Catch:{ all -> 0x02db }
        r12 = (java.lang.Long) r12;	 Catch:{ all -> 0x02db }
        r8.add(r12);	 Catch:{ all -> 0x02db }
        r12 = r7.zzaxr;	 Catch:{ all -> 0x02db }
        r12 = r12[r11];	 Catch:{ all -> 0x02db }
        r13 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r13 = r13.zzgv();	 Catch:{ all -> 0x02db }
        r13 = r13.zzhh();	 Catch:{ all -> 0x02db }
        r13 = java.lang.Long.valueOf(r13);	 Catch:{ all -> 0x02db }
        r12.zzayg = r13;	 Catch:{ all -> 0x02db }
        r12 = r7.zzaxr;	 Catch:{ all -> 0x02db }
        r12 = r12[r11];	 Catch:{ all -> 0x02db }
        r13 = java.lang.Long.valueOf(r3);	 Catch:{ all -> 0x02db }
        r12.zzaxw = r13;	 Catch:{ all -> 0x02db }
        r12 = r7.zzaxr;	 Catch:{ all -> 0x02db }
        r12 = r12[r11];	 Catch:{ all -> 0x02db }
        r13 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r13.zzgw();	 Catch:{ all -> 0x02db }
        r13 = java.lang.Boolean.valueOf(r2);	 Catch:{ all -> 0x02db }
        r12.zzayl = r13;	 Catch:{ all -> 0x02db }
        if (r10 != 0) goto L_0x01f6;	 Catch:{ all -> 0x02db }
    L_0x01f0:
        r12 = r7.zzaxr;	 Catch:{ all -> 0x02db }
        r12 = r12[r11];	 Catch:{ all -> 0x02db }
        r12.zzayt = r9;	 Catch:{ all -> 0x02db }
    L_0x01f6:
        r11 = r11 + 1;	 Catch:{ all -> 0x02db }
        goto L_0x01a1;	 Catch:{ all -> 0x02db }
    L_0x01f9:
        r6 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r6 = r6.zzgt();	 Catch:{ all -> 0x02db }
        r10 = 2;	 Catch:{ all -> 0x02db }
        r6 = r6.isLoggable(r10);	 Catch:{ all -> 0x02db }
        if (r6 == 0) goto L_0x020e;	 Catch:{ all -> 0x02db }
    L_0x0206:
        r6 = r17.zzjr();	 Catch:{ all -> 0x02db }
        r9 = r6.zzb(r7);	 Catch:{ all -> 0x02db }
    L_0x020e:
        r6 = r17.zzjr();	 Catch:{ all -> 0x02db }
        r14 = r6.zza(r7);	 Catch:{ all -> 0x02db }
        r6 = com.google.android.gms.measurement.internal.zzag.zzajw;	 Catch:{ all -> 0x02db }
        r6 = r6.get();	 Catch:{ all -> 0x02db }
        r6 = (java.lang.String) r6;	 Catch:{ all -> 0x02db }
        r13 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x029e }
        r13.<init>(r6);	 Catch:{ MalformedURLException -> 0x029e }
        r10 = r8.isEmpty();	 Catch:{ MalformedURLException -> 0x029e }
        r10 = r10 ^ r0;	 Catch:{ MalformedURLException -> 0x029e }
        com.google.android.gms.common.internal.Preconditions.checkArgument(r10);	 Catch:{ MalformedURLException -> 0x029e }
        r10 = r1.zzaun;	 Catch:{ MalformedURLException -> 0x029e }
        if (r10 == 0) goto L_0x023f;	 Catch:{ MalformedURLException -> 0x029e }
    L_0x022f:
        r8 = r1.zzadp;	 Catch:{ MalformedURLException -> 0x029e }
        r8 = r8.zzgt();	 Catch:{ MalformedURLException -> 0x029e }
        r8 = r8.zzjg();	 Catch:{ MalformedURLException -> 0x029e }
        r10 = "Set uploading progress before finishing the previous upload";	 Catch:{ MalformedURLException -> 0x029e }
        r8.zzca(r10);	 Catch:{ MalformedURLException -> 0x029e }
        goto L_0x0246;	 Catch:{ MalformedURLException -> 0x029e }
    L_0x023f:
        r10 = new java.util.ArrayList;	 Catch:{ MalformedURLException -> 0x029e }
        r10.<init>(r8);	 Catch:{ MalformedURLException -> 0x029e }
        r1.zzaun = r10;	 Catch:{ MalformedURLException -> 0x029e }
    L_0x0246:
        r8 = r1.zzadp;	 Catch:{ MalformedURLException -> 0x029e }
        r8 = r8.zzgu();	 Catch:{ MalformedURLException -> 0x029e }
        r8 = r8.zzanm;	 Catch:{ MalformedURLException -> 0x029e }
        r8.set(r3);	 Catch:{ MalformedURLException -> 0x029e }
        r3 = "?";	 Catch:{ MalformedURLException -> 0x029e }
        r4 = r7.zzaxr;	 Catch:{ MalformedURLException -> 0x029e }
        r4 = r4.length;	 Catch:{ MalformedURLException -> 0x029e }
        if (r4 <= 0) goto L_0x025e;	 Catch:{ MalformedURLException -> 0x029e }
    L_0x0258:
        r3 = r7.zzaxr;	 Catch:{ MalformedURLException -> 0x029e }
        r3 = r3[r2];	 Catch:{ MalformedURLException -> 0x029e }
        r3 = r3.zztt;	 Catch:{ MalformedURLException -> 0x029e }
    L_0x025e:
        r4 = r1.zzadp;	 Catch:{ MalformedURLException -> 0x029e }
        r4 = r4.zzgt();	 Catch:{ MalformedURLException -> 0x029e }
        r4 = r4.zzjo();	 Catch:{ MalformedURLException -> 0x029e }
        r7 = "Uploading data. app, uncompressed size, data";	 Catch:{ MalformedURLException -> 0x029e }
        r8 = r14.length;	 Catch:{ MalformedURLException -> 0x029e }
        r8 = java.lang.Integer.valueOf(r8);	 Catch:{ MalformedURLException -> 0x029e }
        r4.zzd(r7, r3, r8, r9);	 Catch:{ MalformedURLException -> 0x029e }
        r1.zzauj = r0;	 Catch:{ MalformedURLException -> 0x029e }
        r11 = r17.zzlt();	 Catch:{ MalformedURLException -> 0x029e }
        r0 = new com.google.android.gms.measurement.internal.zzfm;	 Catch:{ MalformedURLException -> 0x029e }
        r0.<init>(r1, r5);	 Catch:{ MalformedURLException -> 0x029e }
        r11.zzaf();	 Catch:{ MalformedURLException -> 0x029e }
        r11.zzcl();	 Catch:{ MalformedURLException -> 0x029e }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r13);	 Catch:{ MalformedURLException -> 0x029e }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r14);	 Catch:{ MalformedURLException -> 0x029e }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r0);	 Catch:{ MalformedURLException -> 0x029e }
        r3 = r11.zzgs();	 Catch:{ MalformedURLException -> 0x029e }
        r4 = new com.google.android.gms.measurement.internal.zzay;	 Catch:{ MalformedURLException -> 0x029e }
        r15 = 0;	 Catch:{ MalformedURLException -> 0x029e }
        r10 = r4;	 Catch:{ MalformedURLException -> 0x029e }
        r12 = r5;	 Catch:{ MalformedURLException -> 0x029e }
        r16 = r0;	 Catch:{ MalformedURLException -> 0x029e }
        r10.<init>(r11, r12, r13, r14, r15, r16);	 Catch:{ MalformedURLException -> 0x029e }
        r3.zzd(r4);	 Catch:{ MalformedURLException -> 0x029e }
        goto L_0x02d5;
    L_0x029e:
        r0 = r1.zzadp;	 Catch:{ all -> 0x02db }
        r0 = r0.zzgt();	 Catch:{ all -> 0x02db }
        r0 = r0.zzjg();	 Catch:{ all -> 0x02db }
        r3 = "Failed to parse upload URL. Not uploading. appId";	 Catch:{ all -> 0x02db }
        r4 = com.google.android.gms.measurement.internal.zzaq.zzby(r5);	 Catch:{ all -> 0x02db }
        r0.zze(r3, r4, r6);	 Catch:{ all -> 0x02db }
        goto L_0x02d5;	 Catch:{ all -> 0x02db }
    L_0x02b2:
        r1.zzaup = r7;	 Catch:{ all -> 0x02db }
        r0 = r17.zzjt();	 Catch:{ all -> 0x02db }
        r5 = com.google.android.gms.measurement.internal.zzo.zzic();	 Catch:{ all -> 0x02db }
        r7 = 0;	 Catch:{ all -> 0x02db }
        r3 = r3 - r5;	 Catch:{ all -> 0x02db }
        r0 = r0.zzah(r3);	 Catch:{ all -> 0x02db }
        r3 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x02db }
        if (r3 != 0) goto L_0x02d5;	 Catch:{ all -> 0x02db }
    L_0x02c8:
        r3 = r17.zzjt();	 Catch:{ all -> 0x02db }
        r0 = r3.zzbo(r0);	 Catch:{ all -> 0x02db }
        if (r0 == 0) goto L_0x02d5;	 Catch:{ all -> 0x02db }
    L_0x02d2:
        r1.zzb(r0);	 Catch:{ all -> 0x02db }
    L_0x02d5:
        r1.zzauk = r2;
        r17.zzmc();
        return;
    L_0x02db:
        r0 = move-exception;
        r1.zzauk = r2;
        r17.zzmc();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfk.zzlz():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @androidx.annotation.WorkerThread
    private final boolean zzd(java.lang.String r56, long r57) {
        /*
        r55 = this;
        r1 = r55;
        r2 = r55.zzjt();
        r2.beginTransaction();
        r2 = new com.google.android.gms.measurement.internal.zzfk$zza;	 Catch:{ all -> 0x0df9 }
        r3 = 0;
        r2.<init>();	 Catch:{ all -> 0x0df9 }
        r4 = r55.zzjt();	 Catch:{ all -> 0x0df9 }
        r5 = r1.zzaup;	 Catch:{ all -> 0x0df9 }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r2);	 Catch:{ all -> 0x0df9 }
        r4.zzaf();	 Catch:{ all -> 0x0df9 }
        r4.zzcl();	 Catch:{ all -> 0x0df9 }
        r8 = -1;
        r10 = 2;
        r11 = 0;
        r12 = 1;
        r15 = r4.getWritableDatabase();	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r13 = android.text.TextUtils.isEmpty(r3);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        if (r13 == 0) goto L_0x00a1;
    L_0x002d:
        r13 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r13 == 0) goto L_0x004c;
    L_0x0031:
        r13 = new java.lang.String[r10];	 Catch:{ SQLiteException -> 0x0046, all -> 0x0040 }
        r14 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0046, all -> 0x0040 }
        r13[r11] = r14;	 Catch:{ SQLiteException -> 0x0046, all -> 0x0040 }
        r14 = java.lang.String.valueOf(r57);	 Catch:{ SQLiteException -> 0x0046, all -> 0x0040 }
        r13[r12] = r14;	 Catch:{ SQLiteException -> 0x0046, all -> 0x0040 }
        goto L_0x0054;
    L_0x0040:
        r0 = move-exception;
        r5 = r1;
        r22 = r3;
        goto L_0x027a;
    L_0x0046:
        r0 = move-exception;
        r6 = r3;
        r7 = r6;
    L_0x0049:
        r3 = r0;
        goto L_0x0281;
    L_0x004c:
        r13 = new java.lang.String[r12];	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r14 = java.lang.String.valueOf(r57);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r13[r11] = r14;	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
    L_0x0054:
        r14 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r14 == 0) goto L_0x005b;
    L_0x0058:
        r14 = "rowid <= ? and ";
        goto L_0x005d;
    L_0x005b:
        r14 = "";
    L_0x005d:
        r16 = java.lang.String.valueOf(r14);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r7 = r16.length();	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r7 = r7 + 148;
        r3 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r3.<init>(r7);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r7 = "select app_id, metadata_fingerprint from raw_events where ";
        r3.append(r7);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r3.append(r14);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r7 = "app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;";
        r3.append(r7);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r3 = r3.toString();	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r3 = r15.rawQuery(r3, r13);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r7 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0271, all -> 0x0040 }
        if (r7 != 0) goto L_0x008e;
    L_0x0087:
        if (r3 == 0) goto L_0x0297;
    L_0x0089:
        r3.close();	 Catch:{ all -> 0x0df9 }
        goto L_0x0297;
    L_0x008e:
        r7 = r3.getString(r11);	 Catch:{ SQLiteException -> 0x0271, all -> 0x0040 }
        r13 = r3.getString(r12);	 Catch:{ SQLiteException -> 0x009e, all -> 0x0040 }
        r3.close();	 Catch:{ SQLiteException -> 0x009e, all -> 0x0040 }
        r23 = r3;
        r3 = r7;
        r7 = r13;
        goto L_0x00fc;
    L_0x009e:
        r0 = move-exception;
        r6 = r3;
        goto L_0x0049;
    L_0x00a1:
        r3 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r3 == 0) goto L_0x00b1;
    L_0x00a5:
        r3 = new java.lang.String[r10];	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r7 = 0;
        r3[r11] = r7;	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r7 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r3[r12] = r7;	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        goto L_0x00b7;
    L_0x00b1:
        r3 = 0;
        r7 = new java.lang.String[]{r3};	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r3 = r7;
    L_0x00b7:
        r7 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r7 == 0) goto L_0x00be;
    L_0x00bb:
        r7 = " and rowid <= ?";
        goto L_0x00c0;
    L_0x00be:
        r7 = "";
    L_0x00c0:
        r13 = java.lang.String.valueOf(r7);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r13 = r13.length();	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r13 = r13 + 84;
        r14 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r14.<init>(r13);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r13 = "select metadata_fingerprint from raw_events where app_id = ?";
        r14.append(r13);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r14.append(r7);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r7 = " order by rowid limit 1;";
        r14.append(r7);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r7 = r14.toString();	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r3 = r15.rawQuery(r7, r3);	 Catch:{ SQLiteException -> 0x027d, all -> 0x0276 }
        r7 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0271, all -> 0x0040 }
        if (r7 != 0) goto L_0x00f1;
    L_0x00ea:
        if (r3 == 0) goto L_0x0297;
    L_0x00ec:
        r3.close();	 Catch:{ all -> 0x0df9 }
        goto L_0x0297;
    L_0x00f1:
        r13 = r3.getString(r11);	 Catch:{ SQLiteException -> 0x0271, all -> 0x0040 }
        r3.close();	 Catch:{ SQLiteException -> 0x0271, all -> 0x0040 }
        r23 = r3;
        r7 = r13;
        r3 = 0;
    L_0x00fc:
        r14 = "raw_events_metadata";
        r13 = "metadata";
        r16 = new java.lang.String[]{r13};	 Catch:{ SQLiteException -> 0x026b, all -> 0x0266 }
        r17 = "app_id = ? and metadata_fingerprint = ?";
        r13 = new java.lang.String[r10];	 Catch:{ SQLiteException -> 0x026b, all -> 0x0266 }
        r13[r11] = r3;	 Catch:{ SQLiteException -> 0x026b, all -> 0x0266 }
        r13[r12] = r7;	 Catch:{ SQLiteException -> 0x026b, all -> 0x0266 }
        r18 = 0;
        r19 = 0;
        r20 = "rowid";
        r21 = "2";
        r24 = r13;
        r13 = r15;
        r25 = r15;
        r15 = r16;
        r16 = r17;
        r17 = r24;
        r15 = r13.query(r14, r15, r16, r17, r18, r19, r20, r21);	 Catch:{ SQLiteException -> 0x026b, all -> 0x0266 }
        r13 = r15.moveToFirst();	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        if (r13 != 0) goto L_0x014c;
    L_0x0129:
        r5 = r4.zzgt();	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r5 = r5.zzjg();	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r6 = "Raw event metadata record is missing. appId";
        r7 = com.google.android.gms.measurement.internal.zzaq.zzby(r3);	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r5.zzg(r6, r7);	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        if (r15 == 0) goto L_0x0297;
    L_0x013c:
        r15.close();	 Catch:{ all -> 0x0df9 }
        goto L_0x0297;
    L_0x0141:
        r0 = move-exception;
        r5 = r1;
        r22 = r15;
        goto L_0x027a;
    L_0x0147:
        r0 = move-exception;
        r7 = r3;
        r6 = r15;
        goto L_0x0049;
    L_0x014c:
        r13 = r15.getBlob(r11);	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r14 = r13.length;	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r13 = com.google.android.gms.internal.measurement.zzzi.zzj(r13, r11, r14);	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r14 = new com.google.android.gms.internal.measurement.zzgl;	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r14.<init>();	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r14.zza(r13);	 Catch:{ IOException -> 0x023d }
        r13 = r15.moveToNext();	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        if (r13 == 0) goto L_0x0174;
    L_0x0163:
        r13 = r4.zzgt();	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r13 = r13.zzjj();	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r10 = "Get multiple raw event metadata records, expected one. appId";
        r12 = com.google.android.gms.measurement.internal.zzaq.zzby(r3);	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r13.zzg(r10, r12);	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
    L_0x0174:
        r15.close();	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r2.zzb(r14);	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r10 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r10 == 0) goto L_0x0194;
    L_0x017e:
        r10 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?";
        r12 = 3;
        r13 = new java.lang.String[r12];	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r13[r11] = r3;	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r12 = 1;
        r13[r12] = r7;	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r6 = 2;
        r13[r6] = r5;	 Catch:{ SQLiteException -> 0x0147, all -> 0x0141 }
        r16 = r10;
        r17 = r13;
        goto L_0x01a2;
    L_0x0194:
        r5 = "app_id = ? and metadata_fingerprint = ?";
        r6 = 2;
        r10 = new java.lang.String[r6];	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r10[r11] = r3;	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r6 = 1;
        r10[r6] = r7;	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r16 = r5;
        r17 = r10;
    L_0x01a2:
        r14 = "raw_events";
        r5 = "rowid";
        r6 = "name";
        r7 = "timestamp";
        r10 = "data";
        r5 = new java.lang.String[]{r5, r6, r7, r10};	 Catch:{ SQLiteException -> 0x0261, all -> 0x025b }
        r18 = 0;
        r19 = 0;
        r20 = "rowid";
        r21 = 0;
        r13 = r25;
        r6 = r15;
        r15 = r5;
        r5 = r13.query(r14, r15, r16, r17, r18, r19, r20, r21);	 Catch:{ SQLiteException -> 0x0259, all -> 0x0257 }
        r6 = r5.moveToFirst();	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        if (r6 != 0) goto L_0x01de;
    L_0x01c6:
        r6 = r4.zzgt();	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r6 = r6.zzjj();	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r7 = "Raw event data disappeared while in transaction. appId";
        r10 = com.google.android.gms.measurement.internal.zzaq.zzby(r3);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r6.zzg(r7, r10);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        if (r5 == 0) goto L_0x0297;
    L_0x01d9:
        r5.close();	 Catch:{ all -> 0x0df9 }
        goto L_0x0297;
    L_0x01de:
        r6 = r5.getLong(r11);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r10 = 3;
        r12 = r5.getBlob(r10);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r10 = r12.length;	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r10 = com.google.android.gms.internal.measurement.zzzi.zzj(r12, r11, r10);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r12 = new com.google.android.gms.internal.measurement.zzgi;	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r12.<init>();	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r12.zza(r10);	 Catch:{ IOException -> 0x0213 }
        r10 = 1;
        r13 = r5.getString(r10);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r12.name = r13;	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r10 = 2;
        r13 = r5.getLong(r10);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r10 = java.lang.Long.valueOf(r13);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r12.zzaxn = r10;	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r6 = r2.zza(r6, r12);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        if (r6 != 0) goto L_0x0226;
    L_0x020c:
        if (r5 == 0) goto L_0x0297;
    L_0x020e:
        r5.close();	 Catch:{ all -> 0x0df9 }
        goto L_0x0297;
    L_0x0213:
        r0 = move-exception;
        r6 = r0;
        r7 = r4.zzgt();	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r7 = r7.zzjg();	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r10 = "Data loss. Failed to merge raw event. appId";
        r12 = com.google.android.gms.measurement.internal.zzaq.zzby(r3);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        r7.zze(r10, r12, r6);	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
    L_0x0226:
        r6 = r5.moveToNext();	 Catch:{ SQLiteException -> 0x0238, all -> 0x0233 }
        if (r6 != 0) goto L_0x01de;
    L_0x022c:
        if (r5 == 0) goto L_0x0297;
    L_0x022e:
        r5.close();	 Catch:{ all -> 0x0df9 }
        goto L_0x0297;
    L_0x0233:
        r0 = move-exception;
        r22 = r5;
        r5 = r1;
        goto L_0x027a;
    L_0x0238:
        r0 = move-exception;
        r7 = r3;
        r6 = r5;
        goto L_0x0049;
    L_0x023d:
        r0 = move-exception;
        r5 = r0;
        r6 = r15;
        r7 = r4.zzgt();	 Catch:{ SQLiteException -> 0x0259, all -> 0x0257 }
        r7 = r7.zzjg();	 Catch:{ SQLiteException -> 0x0259, all -> 0x0257 }
        r10 = "Data loss. Failed to merge raw event metadata. appId";
        r12 = com.google.android.gms.measurement.internal.zzaq.zzby(r3);	 Catch:{ SQLiteException -> 0x0259, all -> 0x0257 }
        r7.zze(r10, r12, r5);	 Catch:{ SQLiteException -> 0x0259, all -> 0x0257 }
        if (r6 == 0) goto L_0x0297;
    L_0x0253:
        r6.close();	 Catch:{ all -> 0x0df9 }
        goto L_0x0297;
    L_0x0257:
        r0 = move-exception;
        goto L_0x025d;
    L_0x0259:
        r0 = move-exception;
        goto L_0x0263;
    L_0x025b:
        r0 = move-exception;
        r6 = r15;
    L_0x025d:
        r5 = r1;
        r22 = r6;
        goto L_0x027a;
    L_0x0261:
        r0 = move-exception;
        r6 = r15;
    L_0x0263:
        r7 = r3;
        goto L_0x0049;
    L_0x0266:
        r0 = move-exception;
        r5 = r1;
        r22 = r23;
        goto L_0x027a;
    L_0x026b:
        r0 = move-exception;
        r7 = r3;
        r6 = r23;
        goto L_0x0049;
    L_0x0271:
        r0 = move-exception;
        r6 = r3;
        r7 = 0;
        goto L_0x0049;
    L_0x0276:
        r0 = move-exception;
        r5 = r1;
        r22 = 0;
    L_0x027a:
        r1 = r0;
        goto L_0x0df0;
    L_0x027d:
        r0 = move-exception;
        r3 = r0;
        r6 = 0;
        r7 = 0;
    L_0x0281:
        r4 = r4.zzgt();	 Catch:{ all -> 0x0deb }
        r4 = r4.zzjg();	 Catch:{ all -> 0x0deb }
        r5 = "Data loss. Error selecting raw event. appId";
        r7 = com.google.android.gms.measurement.internal.zzaq.zzby(r7);	 Catch:{ all -> 0x0deb }
        r4.zze(r5, r7, r3);	 Catch:{ all -> 0x0deb }
        if (r6 == 0) goto L_0x0297;
    L_0x0294:
        r6.close();	 Catch:{ all -> 0x0df9 }
    L_0x0297:
        r3 = r2.zzauv;	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x02a6;
    L_0x029b:
        r3 = r2.zzauv;	 Catch:{ all -> 0x0df9 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x02a4;
    L_0x02a3:
        goto L_0x02a6;
    L_0x02a4:
        r3 = 0;
        goto L_0x02a7;
    L_0x02a6:
        r3 = 1;
    L_0x02a7:
        if (r3 != 0) goto L_0x0dda;
    L_0x02a9:
        r3 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r4 = r2.zzauv;	 Catch:{ all -> 0x0df9 }
        r4 = r4.size();	 Catch:{ all -> 0x0df9 }
        r4 = new com.google.android.gms.internal.measurement.zzgi[r4];	 Catch:{ all -> 0x0df9 }
        r3.zzaxu = r4;	 Catch:{ all -> 0x0df9 }
        r4 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r4 = r4.zzgv();	 Catch:{ all -> 0x0df9 }
        r5 = r3.zztt;	 Catch:{ all -> 0x0df9 }
        r4 = r4.zzaw(r5);	 Catch:{ all -> 0x0df9 }
        r5 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r5 = r5.zzgv();	 Catch:{ all -> 0x0df9 }
        r6 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r6 = r6.zztt;	 Catch:{ all -> 0x0df9 }
        r7 = com.google.android.gms.measurement.internal.zzag.zzalm;	 Catch:{ all -> 0x0df9 }
        r5 = r5.zze(r6, r7);	 Catch:{ all -> 0x0df9 }
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
    L_0x02d8:
        r6 = r2.zzauv;	 Catch:{ all -> 0x0df9 }
        r6 = r6.size();	 Catch:{ all -> 0x0df9 }
        r16 = 1;
        if (r10 >= r6) goto L_0x0784;
    L_0x02e2:
        r6 = r2.zzauv;	 Catch:{ all -> 0x0df9 }
        r6 = r6.get(r10);	 Catch:{ all -> 0x0df9 }
        r6 = (com.google.android.gms.internal.measurement.zzgi) r6;	 Catch:{ all -> 0x0df9 }
        r7 = r55.zzls();	 Catch:{ all -> 0x0df9 }
        r11 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r11 = r11.zztt;	 Catch:{ all -> 0x0df9 }
        r26 = r12;
        r12 = r6.name;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzo(r11, r12);	 Catch:{ all -> 0x0df9 }
        if (r7 == 0) goto L_0x036f;
    L_0x02fc:
        r7 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzgt();	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzjj();	 Catch:{ all -> 0x0df9 }
        r11 = "Dropping blacklisted raw event. appId";
        r12 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r12 = r12.zztt;	 Catch:{ all -> 0x0df9 }
        r12 = com.google.android.gms.measurement.internal.zzaq.zzby(r12);	 Catch:{ all -> 0x0df9 }
        r27 = r10;
        r10 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zzgq();	 Catch:{ all -> 0x0df9 }
        r28 = r13;
        r13 = r6.name;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zzbv(r13);	 Catch:{ all -> 0x0df9 }
        r7.zze(r11, r12, r10);	 Catch:{ all -> 0x0df9 }
        r7 = r55.zzls();	 Catch:{ all -> 0x0df9 }
        r10 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zztt;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzcn(r10);	 Catch:{ all -> 0x0df9 }
        if (r7 != 0) goto L_0x0342;
    L_0x0331:
        r7 = r55.zzls();	 Catch:{ all -> 0x0df9 }
        r10 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zztt;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzco(r10);	 Catch:{ all -> 0x0df9 }
        if (r7 == 0) goto L_0x0340;
    L_0x033f:
        goto L_0x0342;
    L_0x0340:
        r7 = 0;
        goto L_0x0343;
    L_0x0342:
        r7 = 1;
    L_0x0343:
        if (r7 != 0) goto L_0x0368;
    L_0x0345:
        r7 = "_err";
        r10 = r6.name;	 Catch:{ all -> 0x0df9 }
        r7 = r7.equals(r10);	 Catch:{ all -> 0x0df9 }
        if (r7 != 0) goto L_0x0368;
    L_0x034f:
        r7 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r16 = r7.zzgr();	 Catch:{ all -> 0x0df9 }
        r7 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zztt;	 Catch:{ all -> 0x0df9 }
        r18 = 11;
        r19 = "_ev";
        r6 = r6.name;	 Catch:{ all -> 0x0df9 }
        r21 = 0;
        r17 = r7;
        r20 = r6;
        r16.zza(r17, r18, r19, r20, r21);	 Catch:{ all -> 0x0df9 }
    L_0x0368:
        r40 = r26;
        r13 = r28;
        r12 = 3;
        goto L_0x077d;
    L_0x036f:
        r27 = r10;
        r28 = r13;
        r7 = r55.zzls();	 Catch:{ all -> 0x0df9 }
        r10 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zztt;	 Catch:{ all -> 0x0df9 }
        r11 = r6.name;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzp(r10, r11);	 Catch:{ all -> 0x0df9 }
        if (r7 != 0) goto L_0x03cf;
    L_0x0383:
        r55.zzjr();	 Catch:{ all -> 0x0df9 }
        r11 = r6.name;	 Catch:{ all -> 0x0df9 }
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r11);	 Catch:{ all -> 0x0df9 }
        r12 = r11.hashCode();	 Catch:{ all -> 0x0df9 }
        r13 = 94660; // 0x171c4 float:1.32647E-40 double:4.67683E-319;
        if (r12 == r13) goto L_0x03b3;
    L_0x0394:
        r13 = 95025; // 0x17331 float:1.33158E-40 double:4.69486E-319;
        if (r12 == r13) goto L_0x03a9;
    L_0x0399:
        r13 = 95027; // 0x17333 float:1.33161E-40 double:4.69496E-319;
        if (r12 == r13) goto L_0x039f;
    L_0x039e:
        goto L_0x03bd;
    L_0x039f:
        r12 = "_ui";
        r11 = r11.equals(r12);	 Catch:{ all -> 0x0df9 }
        if (r11 == 0) goto L_0x03bd;
    L_0x03a7:
        r11 = 1;
        goto L_0x03be;
    L_0x03a9:
        r12 = "_ug";
        r11 = r11.equals(r12);	 Catch:{ all -> 0x0df9 }
        if (r11 == 0) goto L_0x03bd;
    L_0x03b1:
        r11 = 2;
        goto L_0x03be;
    L_0x03b3:
        r12 = "_in";
        r11 = r11.equals(r12);	 Catch:{ all -> 0x0df9 }
        if (r11 == 0) goto L_0x03bd;
    L_0x03bb:
        r11 = 0;
        goto L_0x03be;
    L_0x03bd:
        r11 = -1;
    L_0x03be:
        switch(r11) {
            case 0: goto L_0x03c3;
            case 1: goto L_0x03c3;
            case 2: goto L_0x03c3;
            default: goto L_0x03c1;
        };	 Catch:{ all -> 0x0df9 }
    L_0x03c1:
        r11 = 0;
        goto L_0x03c4;
    L_0x03c3:
        r11 = 1;
    L_0x03c4:
        if (r11 == 0) goto L_0x03c7;
    L_0x03c6:
        goto L_0x03cf;
    L_0x03c7:
        r39 = r3;
        r43 = r14;
        r40 = r26;
        goto L_0x05d3;
    L_0x03cf:
        r11 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        if (r11 != 0) goto L_0x03d8;
    L_0x03d3:
        r11 = 0;
        r12 = new com.google.android.gms.internal.measurement.zzgj[r11];	 Catch:{ all -> 0x0df9 }
        r6.zzaxm = r12;	 Catch:{ all -> 0x0df9 }
    L_0x03d8:
        r11 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r12 = r11.length;	 Catch:{ all -> 0x0df9 }
        r13 = 0;
        r18 = 0;
        r19 = 0;
    L_0x03e0:
        if (r13 >= r12) goto L_0x0414;
    L_0x03e2:
        r10 = r11[r13];	 Catch:{ all -> 0x0df9 }
        r30 = r11;
        r11 = "_c";
        r31 = r12;
        r12 = r10.name;	 Catch:{ all -> 0x0df9 }
        r11 = r11.equals(r12);	 Catch:{ all -> 0x0df9 }
        if (r11 == 0) goto L_0x03fb;
    L_0x03f2:
        r11 = java.lang.Long.valueOf(r16);	 Catch:{ all -> 0x0df9 }
        r10.zzaxq = r11;	 Catch:{ all -> 0x0df9 }
        r18 = 1;
        goto L_0x040d;
    L_0x03fb:
        r11 = "_r";
        r12 = r10.name;	 Catch:{ all -> 0x0df9 }
        r11 = r11.equals(r12);	 Catch:{ all -> 0x0df9 }
        if (r11 == 0) goto L_0x040d;
    L_0x0405:
        r11 = java.lang.Long.valueOf(r16);	 Catch:{ all -> 0x0df9 }
        r10.zzaxq = r11;	 Catch:{ all -> 0x0df9 }
        r19 = 1;
    L_0x040d:
        r13 = r13 + 1;
        r11 = r30;
        r12 = r31;
        goto L_0x03e0;
    L_0x0414:
        if (r18 != 0) goto L_0x0456;
    L_0x0416:
        if (r7 == 0) goto L_0x0456;
    L_0x0418:
        r10 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zzgt();	 Catch:{ all -> 0x0df9 }
        r10 = r10.zzjo();	 Catch:{ all -> 0x0df9 }
        r11 = "Marking event as conversion";
        r12 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r12 = r12.zzgq();	 Catch:{ all -> 0x0df9 }
        r13 = r6.name;	 Catch:{ all -> 0x0df9 }
        r12 = r12.zzbv(r13);	 Catch:{ all -> 0x0df9 }
        r10.zzg(r11, r12);	 Catch:{ all -> 0x0df9 }
        r10 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r11 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r11 = r11.length;	 Catch:{ all -> 0x0df9 }
        r12 = 1;
        r11 = r11 + r12;
        r10 = java.util.Arrays.copyOf(r10, r11);	 Catch:{ all -> 0x0df9 }
        r10 = (com.google.android.gms.internal.measurement.zzgj[]) r10;	 Catch:{ all -> 0x0df9 }
        r11 = new com.google.android.gms.internal.measurement.zzgj;	 Catch:{ all -> 0x0df9 }
        r11.<init>();	 Catch:{ all -> 0x0df9 }
        r12 = "_c";
        r11.name = r12;	 Catch:{ all -> 0x0df9 }
        r12 = java.lang.Long.valueOf(r16);	 Catch:{ all -> 0x0df9 }
        r11.zzaxq = r12;	 Catch:{ all -> 0x0df9 }
        r12 = r10.length;	 Catch:{ all -> 0x0df9 }
        r13 = 1;
        r12 = r12 - r13;
        r10[r12] = r11;	 Catch:{ all -> 0x0df9 }
        r6.zzaxm = r10;	 Catch:{ all -> 0x0df9 }
    L_0x0456:
        if (r19 != 0) goto L_0x0496;
    L_0x0458:
        r10 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zzgt();	 Catch:{ all -> 0x0df9 }
        r10 = r10.zzjo();	 Catch:{ all -> 0x0df9 }
        r11 = "Marking event as real-time";
        r12 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r12 = r12.zzgq();	 Catch:{ all -> 0x0df9 }
        r13 = r6.name;	 Catch:{ all -> 0x0df9 }
        r12 = r12.zzbv(r13);	 Catch:{ all -> 0x0df9 }
        r10.zzg(r11, r12);	 Catch:{ all -> 0x0df9 }
        r10 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r11 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r11 = r11.length;	 Catch:{ all -> 0x0df9 }
        r12 = 1;
        r11 = r11 + r12;
        r10 = java.util.Arrays.copyOf(r10, r11);	 Catch:{ all -> 0x0df9 }
        r10 = (com.google.android.gms.internal.measurement.zzgj[]) r10;	 Catch:{ all -> 0x0df9 }
        r11 = new com.google.android.gms.internal.measurement.zzgj;	 Catch:{ all -> 0x0df9 }
        r11.<init>();	 Catch:{ all -> 0x0df9 }
        r12 = "_r";
        r11.name = r12;	 Catch:{ all -> 0x0df9 }
        r12 = java.lang.Long.valueOf(r16);	 Catch:{ all -> 0x0df9 }
        r11.zzaxq = r12;	 Catch:{ all -> 0x0df9 }
        r12 = r10.length;	 Catch:{ all -> 0x0df9 }
        r13 = 1;
        r12 = r12 - r13;
        r10[r12] = r11;	 Catch:{ all -> 0x0df9 }
        r6.zzaxm = r10;	 Catch:{ all -> 0x0df9 }
    L_0x0496:
        r30 = r55.zzjt();	 Catch:{ all -> 0x0df9 }
        r31 = r55.zzly();	 Catch:{ all -> 0x0df9 }
        r10 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zztt;	 Catch:{ all -> 0x0df9 }
        r34 = 0;
        r35 = 0;
        r36 = 0;
        r37 = 0;
        r38 = 1;
        r33 = r10;
        r10 = r30.zza(r31, r33, r34, r35, r36, r37, r38);	 Catch:{ all -> 0x0df9 }
        r10 = r10.zzahx;	 Catch:{ all -> 0x0df9 }
        r12 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r12 = r12.zzgv();	 Catch:{ all -> 0x0df9 }
        r13 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r13 = r13.zztt;	 Catch:{ all -> 0x0df9 }
        r12 = r12.zzas(r13);	 Catch:{ all -> 0x0df9 }
        r12 = (long) r12;	 Catch:{ all -> 0x0df9 }
        r16 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
        if (r16 <= 0) goto L_0x0508;
    L_0x04c7:
        r10 = 0;
    L_0x04c8:
        r11 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r11 = r11.length;	 Catch:{ all -> 0x0df9 }
        if (r10 >= r11) goto L_0x0503;
    L_0x04cd:
        r11 = "_r";
        r12 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r12 = r12[r10];	 Catch:{ all -> 0x0df9 }
        r12 = r12.name;	 Catch:{ all -> 0x0df9 }
        r11 = r11.equals(r12);	 Catch:{ all -> 0x0df9 }
        if (r11 == 0) goto L_0x04fe;
    L_0x04db:
        r11 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r11 = r11.length;	 Catch:{ all -> 0x0df9 }
        r12 = 1;
        r11 = r11 - r12;
        r11 = new com.google.android.gms.internal.measurement.zzgj[r11];	 Catch:{ all -> 0x0df9 }
        if (r10 <= 0) goto L_0x04ea;
    L_0x04e4:
        r12 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r13 = 0;
        java.lang.System.arraycopy(r12, r13, r11, r13, r10);	 Catch:{ all -> 0x0df9 }
    L_0x04ea:
        r12 = r11.length;	 Catch:{ all -> 0x0df9 }
        if (r10 >= r12) goto L_0x04f9;
    L_0x04ed:
        r12 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r13 = r10 + 1;
        r39 = r3;
        r3 = r11.length;	 Catch:{ all -> 0x0df9 }
        r3 = r3 - r10;
        java.lang.System.arraycopy(r12, r13, r11, r10, r3);	 Catch:{ all -> 0x0df9 }
        goto L_0x04fb;
    L_0x04f9:
        r39 = r3;
    L_0x04fb:
        r6.zzaxm = r11;	 Catch:{ all -> 0x0df9 }
        goto L_0x0505;
    L_0x04fe:
        r39 = r3;
        r10 = r10 + 1;
        goto L_0x04c8;
    L_0x0503:
        r39 = r3;
    L_0x0505:
        r12 = r26;
        goto L_0x050b;
    L_0x0508:
        r39 = r3;
        r12 = 1;
    L_0x050b:
        r3 = r6.name;	 Catch:{ all -> 0x0df9 }
        r3 = com.google.android.gms.measurement.internal.zzfu.zzcv(r3);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x05cf;
    L_0x0513:
        if (r7 == 0) goto L_0x05cf;
    L_0x0515:
        r30 = r55.zzjt();	 Catch:{ all -> 0x0df9 }
        r31 = r55.zzly();	 Catch:{ all -> 0x0df9 }
        r3 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zztt;	 Catch:{ all -> 0x0df9 }
        r34 = 0;
        r35 = 0;
        r36 = 1;
        r37 = 0;
        r38 = 0;
        r33 = r3;
        r3 = r30.zza(r31, r33, r34, r35, r36, r37, r38);	 Catch:{ all -> 0x0df9 }
        r10 = r3.zzahv;	 Catch:{ all -> 0x0df9 }
        r3 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzgv();	 Catch:{ all -> 0x0df9 }
        r13 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r13 = r13.zztt;	 Catch:{ all -> 0x0df9 }
        r40 = r12;
        r12 = com.google.android.gms.measurement.internal.zzag.zzajt;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzb(r13, r12);	 Catch:{ all -> 0x0df9 }
        r12 = (long) r3;	 Catch:{ all -> 0x0df9 }
        r3 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
        if (r3 <= 0) goto L_0x05d1;
    L_0x054a:
        r3 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzgt();	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzjj();	 Catch:{ all -> 0x0df9 }
        r10 = "Too many conversions. Not logging as conversion. appId";
        r11 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r11 = r11.zztt;	 Catch:{ all -> 0x0df9 }
        r11 = com.google.android.gms.measurement.internal.zzaq.zzby(r11);	 Catch:{ all -> 0x0df9 }
        r3.zzg(r10, r11);	 Catch:{ all -> 0x0df9 }
        r3 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r10 = r3.length;	 Catch:{ all -> 0x0df9 }
        r11 = 0;
        r12 = 0;
        r13 = 0;
    L_0x0567:
        if (r11 >= r10) goto L_0x0591;
    L_0x0569:
        r41 = r10;
        r10 = r3[r11];	 Catch:{ all -> 0x0df9 }
        r42 = r3;
        r3 = "_c";
        r43 = r14;
        r14 = r10.name;	 Catch:{ all -> 0x0df9 }
        r3 = r3.equals(r14);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x057d;
    L_0x057b:
        r13 = r10;
        goto L_0x0588;
    L_0x057d:
        r3 = "_err";
        r10 = r10.name;	 Catch:{ all -> 0x0df9 }
        r3 = r3.equals(r10);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x0588;
    L_0x0587:
        r12 = 1;
    L_0x0588:
        r11 = r11 + 1;
        r10 = r41;
        r3 = r42;
        r14 = r43;
        goto L_0x0567;
    L_0x0591:
        r43 = r14;
        if (r12 == 0) goto L_0x05a8;
    L_0x0595:
        if (r13 == 0) goto L_0x05a8;
    L_0x0597:
        r3 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r10 = 1;
        r11 = new com.google.android.gms.internal.measurement.zzgj[r10];	 Catch:{ all -> 0x0df9 }
        r10 = 0;
        r11[r10] = r13;	 Catch:{ all -> 0x0df9 }
        r3 = com.google.android.gms.common.util.ArrayUtils.removeAll(r3, r11);	 Catch:{ all -> 0x0df9 }
        r3 = (com.google.android.gms.internal.measurement.zzgj[]) r3;	 Catch:{ all -> 0x0df9 }
        r6.zzaxm = r3;	 Catch:{ all -> 0x0df9 }
        goto L_0x05d3;
    L_0x05a8:
        if (r13 == 0) goto L_0x05b7;
    L_0x05aa:
        r3 = "_err";
        r13.name = r3;	 Catch:{ all -> 0x0df9 }
        r10 = 10;
        r3 = java.lang.Long.valueOf(r10);	 Catch:{ all -> 0x0df9 }
        r13.zzaxq = r3;	 Catch:{ all -> 0x0df9 }
        goto L_0x05d3;
    L_0x05b7:
        r3 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzgt();	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzjg();	 Catch:{ all -> 0x0df9 }
        r10 = "Did not find conversion parameter. appId";
        r11 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r11 = r11.zztt;	 Catch:{ all -> 0x0df9 }
        r11 = com.google.android.gms.measurement.internal.zzaq.zzby(r11);	 Catch:{ all -> 0x0df9 }
        r3.zzg(r10, r11);	 Catch:{ all -> 0x0df9 }
        goto L_0x05d3;
    L_0x05cf:
        r40 = r12;
    L_0x05d1:
        r43 = r14;
    L_0x05d3:
        r3 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzgv();	 Catch:{ all -> 0x0df9 }
        r10 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zztt;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzbf(r10);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x0693;
    L_0x05e3:
        if (r7 == 0) goto L_0x0693;
    L_0x05e5:
        r3 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r7 = 0;
        r10 = -1;
        r11 = -1;
    L_0x05ea:
        r12 = r3.length;	 Catch:{ all -> 0x0df9 }
        if (r7 >= r12) goto L_0x060b;
    L_0x05ed:
        r12 = "value";
        r13 = r3[r7];	 Catch:{ all -> 0x0df9 }
        r13 = r13.name;	 Catch:{ all -> 0x0df9 }
        r12 = r12.equals(r13);	 Catch:{ all -> 0x0df9 }
        if (r12 == 0) goto L_0x05fb;
    L_0x05f9:
        r10 = r7;
        goto L_0x0608;
    L_0x05fb:
        r12 = "currency";
        r13 = r3[r7];	 Catch:{ all -> 0x0df9 }
        r13 = r13.name;	 Catch:{ all -> 0x0df9 }
        r12 = r12.equals(r13);	 Catch:{ all -> 0x0df9 }
        if (r12 == 0) goto L_0x0608;
    L_0x0607:
        r11 = r7;
    L_0x0608:
        r7 = r7 + 1;
        goto L_0x05ea;
    L_0x060b:
        r7 = -1;
        if (r10 == r7) goto L_0x063b;
    L_0x060e:
        r7 = r3[r10];	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzaxq;	 Catch:{ all -> 0x0df9 }
        if (r7 != 0) goto L_0x063d;
    L_0x0614:
        r7 = r3[r10];	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzava;	 Catch:{ all -> 0x0df9 }
        if (r7 != 0) goto L_0x063d;
    L_0x061a:
        r7 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzgt();	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzjl();	 Catch:{ all -> 0x0df9 }
        r11 = "Value must be specified with a numeric type.";
        r7.zzca(r11);	 Catch:{ all -> 0x0df9 }
        r3 = zza(r3, r10);	 Catch:{ all -> 0x0df9 }
        r7 = "_c";
        r3 = zza(r3, r7);	 Catch:{ all -> 0x0df9 }
        r7 = 18;
        r10 = "value";
        r3 = zza(r3, r7, r10);	 Catch:{ all -> 0x0df9 }
    L_0x063b:
        r12 = 3;
        goto L_0x0690;
    L_0x063d:
        r7 = -1;
        if (r11 != r7) goto L_0x0643;
    L_0x0640:
        r7 = 1;
        r12 = 3;
        goto L_0x066d;
    L_0x0643:
        r7 = r3[r11];	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzamw;	 Catch:{ all -> 0x0df9 }
        if (r7 == 0) goto L_0x066b;
    L_0x0649:
        r11 = r7.length();	 Catch:{ all -> 0x0df9 }
        r12 = 3;
        if (r11 == r12) goto L_0x0651;
    L_0x0650:
        goto L_0x066c;
    L_0x0651:
        r11 = 0;
    L_0x0652:
        r13 = r7.length();	 Catch:{ all -> 0x0df9 }
        if (r11 >= r13) goto L_0x0669;
    L_0x0658:
        r13 = r7.codePointAt(r11);	 Catch:{ all -> 0x0df9 }
        r14 = java.lang.Character.isLetter(r13);	 Catch:{ all -> 0x0df9 }
        if (r14 != 0) goto L_0x0663;
    L_0x0662:
        goto L_0x066c;
    L_0x0663:
        r13 = java.lang.Character.charCount(r13);	 Catch:{ all -> 0x0df9 }
        r11 = r11 + r13;
        goto L_0x0652;
    L_0x0669:
        r7 = 0;
        goto L_0x066d;
    L_0x066b:
        r12 = 3;
    L_0x066c:
        r7 = 1;
    L_0x066d:
        if (r7 == 0) goto L_0x0690;
    L_0x066f:
        r7 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzgt();	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzjl();	 Catch:{ all -> 0x0df9 }
        r11 = "Value parameter discarded. You must also supply a 3-letter ISO_4217 currency code in the currency parameter.";
        r7.zzca(r11);	 Catch:{ all -> 0x0df9 }
        r3 = zza(r3, r10);	 Catch:{ all -> 0x0df9 }
        r7 = "_c";
        r3 = zza(r3, r7);	 Catch:{ all -> 0x0df9 }
        r7 = 19;
        r10 = "currency";
        r3 = zza(r3, r7, r10);	 Catch:{ all -> 0x0df9 }
    L_0x0690:
        r6.zzaxm = r3;	 Catch:{ all -> 0x0df9 }
        goto L_0x0694;
    L_0x0693:
        r12 = 3;
    L_0x0694:
        r3 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzgv();	 Catch:{ all -> 0x0df9 }
        r7 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zztt;	 Catch:{ all -> 0x0df9 }
        r10 = com.google.android.gms.measurement.internal.zzag.zzall;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zze(r7, r10);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x0715;
    L_0x06a6:
        r3 = "_e";
        r7 = r6.name;	 Catch:{ all -> 0x0df9 }
        r3 = r3.equals(r7);	 Catch:{ all -> 0x0df9 }
        r10 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r3 == 0) goto L_0x06e0;
    L_0x06b2:
        r55.zzjr();	 Catch:{ all -> 0x0df9 }
        r3 = "_fr";
        r3 = com.google.android.gms.measurement.internal.zzfq.zza(r6, r3);	 Catch:{ all -> 0x0df9 }
        if (r3 != 0) goto L_0x0715;
    L_0x06bd:
        if (r9 == 0) goto L_0x06de;
    L_0x06bf:
        r3 = r9.zzaxn;	 Catch:{ all -> 0x0df9 }
        r7 = r3.longValue();	 Catch:{ all -> 0x0df9 }
        r3 = r6.zzaxn;	 Catch:{ all -> 0x0df9 }
        r13 = r3.longValue();	 Catch:{ all -> 0x0df9 }
        r3 = 0;
        r7 = r7 - r13;
        r7 = java.lang.Math.abs(r7);	 Catch:{ all -> 0x0df9 }
        r3 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1));
        if (r3 > 0) goto L_0x06de;
    L_0x06d5:
        r3 = r1.zza(r6, r9);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x06de;
    L_0x06db:
        r8 = 0;
        r9 = 0;
        goto L_0x0715;
    L_0x06de:
        r8 = r6;
        goto L_0x0715;
    L_0x06e0:
        r3 = "_vs";
        r7 = r6.name;	 Catch:{ all -> 0x0df9 }
        r3 = r3.equals(r7);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x0715;
    L_0x06ea:
        r55.zzjr();	 Catch:{ all -> 0x0df9 }
        r3 = "_et";
        r3 = com.google.android.gms.measurement.internal.zzfq.zza(r6, r3);	 Catch:{ all -> 0x0df9 }
        if (r3 != 0) goto L_0x0715;
    L_0x06f5:
        if (r8 == 0) goto L_0x0714;
    L_0x06f7:
        r3 = r8.zzaxn;	 Catch:{ all -> 0x0df9 }
        r13 = r3.longValue();	 Catch:{ all -> 0x0df9 }
        r3 = r6.zzaxn;	 Catch:{ all -> 0x0df9 }
        r15 = r3.longValue();	 Catch:{ all -> 0x0df9 }
        r3 = 0;
        r13 = r13 - r15;
        r13 = java.lang.Math.abs(r13);	 Catch:{ all -> 0x0df9 }
        r3 = (r13 > r10 ? 1 : (r13 == r10 ? 0 : -1));
        if (r3 > 0) goto L_0x0714;
    L_0x070d:
        r3 = r1.zza(r8, r6);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x0714;
    L_0x0713:
        goto L_0x06db;
    L_0x0714:
        r9 = r6;
    L_0x0715:
        if (r4 == 0) goto L_0x0773;
    L_0x0717:
        if (r5 != 0) goto L_0x0773;
    L_0x0719:
        r3 = "_e";
        r7 = r6.name;	 Catch:{ all -> 0x0df9 }
        r3 = r3.equals(r7);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x0773;
    L_0x0723:
        r3 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x075c;
    L_0x0727:
        r3 = r6.zzaxm;	 Catch:{ all -> 0x0df9 }
        r3 = r3.length;	 Catch:{ all -> 0x0df9 }
        if (r3 != 0) goto L_0x072d;
    L_0x072c:
        goto L_0x075c;
    L_0x072d:
        r55.zzjr();	 Catch:{ all -> 0x0df9 }
        r3 = "_et";
        r3 = com.google.android.gms.measurement.internal.zzfq.zzb(r6, r3);	 Catch:{ all -> 0x0df9 }
        r3 = (java.lang.Long) r3;	 Catch:{ all -> 0x0df9 }
        if (r3 != 0) goto L_0x0752;
    L_0x073a:
        r3 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzgt();	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzjj();	 Catch:{ all -> 0x0df9 }
        r7 = "Engagement event does not include duration. appId";
        r10 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zztt;	 Catch:{ all -> 0x0df9 }
        r10 = com.google.android.gms.measurement.internal.zzaq.zzby(r10);	 Catch:{ all -> 0x0df9 }
        r3.zzg(r7, r10);	 Catch:{ all -> 0x0df9 }
        goto L_0x0773;
    L_0x0752:
        r10 = r3.longValue();	 Catch:{ all -> 0x0df9 }
        r3 = 0;
        r14 = r43 + r10;
        r3 = r39;
        goto L_0x0777;
    L_0x075c:
        r3 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzgt();	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzjj();	 Catch:{ all -> 0x0df9 }
        r7 = "Engagement event does not contain any parameters. appId";
        r10 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r10 = r10.zztt;	 Catch:{ all -> 0x0df9 }
        r10 = com.google.android.gms.measurement.internal.zzaq.zzby(r10);	 Catch:{ all -> 0x0df9 }
        r3.zzg(r7, r10);	 Catch:{ all -> 0x0df9 }
    L_0x0773:
        r3 = r39;
        r14 = r43;
    L_0x0777:
        r7 = r3.zzaxu;	 Catch:{ all -> 0x0df9 }
        r13 = r28 + 1;
        r7[r28] = r6;	 Catch:{ all -> 0x0df9 }
    L_0x077d:
        r10 = r27 + 1;
        r12 = r40;
        r11 = 0;
        goto L_0x02d8;
    L_0x0784:
        r26 = r12;
        r28 = r13;
        r43 = r14;
        if (r5 == 0) goto L_0x07e2;
    L_0x078c:
        r13 = r28;
        r14 = r43;
        r5 = 0;
    L_0x0791:
        if (r5 >= r13) goto L_0x07e6;
    L_0x0793:
        r6 = r3.zzaxu;	 Catch:{ all -> 0x0df9 }
        r6 = r6[r5];	 Catch:{ all -> 0x0df9 }
        r7 = "_e";
        r8 = r6.name;	 Catch:{ all -> 0x0df9 }
        r7 = r7.equals(r8);	 Catch:{ all -> 0x0df9 }
        if (r7 == 0) goto L_0x07be;
    L_0x07a1:
        r55.zzjr();	 Catch:{ all -> 0x0df9 }
        r7 = "_fr";
        r7 = com.google.android.gms.measurement.internal.zzfq.zza(r6, r7);	 Catch:{ all -> 0x0df9 }
        if (r7 == 0) goto L_0x07be;
    L_0x07ac:
        r6 = r3.zzaxu;	 Catch:{ all -> 0x0df9 }
        r7 = r5 + 1;
        r8 = r3.zzaxu;	 Catch:{ all -> 0x0df9 }
        r9 = r13 - r5;
        r10 = 1;
        r9 = r9 - r10;
        java.lang.System.arraycopy(r6, r7, r8, r5, r9);	 Catch:{ all -> 0x0df9 }
        r13 = r13 + -1;
        r5 = r5 + -1;
        goto L_0x07df;
    L_0x07be:
        if (r4 == 0) goto L_0x07df;
    L_0x07c0:
        r55.zzjr();	 Catch:{ all -> 0x0df9 }
        r7 = "_et";
        r6 = com.google.android.gms.measurement.internal.zzfq.zza(r6, r7);	 Catch:{ all -> 0x0df9 }
        if (r6 == 0) goto L_0x07df;
    L_0x07cb:
        r6 = r6.zzaxq;	 Catch:{ all -> 0x0df9 }
        if (r6 == 0) goto L_0x07df;
    L_0x07cf:
        r7 = r6.longValue();	 Catch:{ all -> 0x0df9 }
        r9 = 0;
        r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1));
        if (r11 <= 0) goto L_0x07df;
    L_0x07d9:
        r6 = r6.longValue();	 Catch:{ all -> 0x0df9 }
        r8 = 0;
        r14 = r14 + r6;
    L_0x07df:
        r6 = 1;
        r5 = r5 + r6;
        goto L_0x0791;
    L_0x07e2:
        r13 = r28;
        r14 = r43;
    L_0x07e6:
        r5 = r2.zzauv;	 Catch:{ all -> 0x0df9 }
        r5 = r5.size();	 Catch:{ all -> 0x0df9 }
        if (r13 >= r5) goto L_0x07f8;
    L_0x07ee:
        r5 = r3.zzaxu;	 Catch:{ all -> 0x0df9 }
        r5 = java.util.Arrays.copyOf(r5, r13);	 Catch:{ all -> 0x0df9 }
        r5 = (com.google.android.gms.internal.measurement.zzgi[]) r5;	 Catch:{ all -> 0x0df9 }
        r3.zzaxu = r5;	 Catch:{ all -> 0x0df9 }
    L_0x07f8:
        if (r4 == 0) goto L_0x08c7;
    L_0x07fa:
        r4 = r55.zzjt();	 Catch:{ all -> 0x0df9 }
        r5 = r3.zztt;	 Catch:{ all -> 0x0df9 }
        r6 = "_lte";
        r4 = r4.zzi(r5, r6);	 Catch:{ all -> 0x0df9 }
        if (r4 == 0) goto L_0x0833;
    L_0x0808:
        r5 = r4.value;	 Catch:{ all -> 0x0df9 }
        if (r5 != 0) goto L_0x080d;
    L_0x080c:
        goto L_0x0833;
    L_0x080d:
        r5 = new com.google.android.gms.measurement.internal.zzft;	 Catch:{ all -> 0x0df9 }
        r7 = r3.zztt;	 Catch:{ all -> 0x0df9 }
        r8 = "auto";
        r9 = "_lte";
        r6 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r6 = r6.zzbx();	 Catch:{ all -> 0x0df9 }
        r10 = r6.currentTimeMillis();	 Catch:{ all -> 0x0df9 }
        r4 = r4.value;	 Catch:{ all -> 0x0df9 }
        r4 = (java.lang.Long) r4;	 Catch:{ all -> 0x0df9 }
        r12 = r4.longValue();	 Catch:{ all -> 0x0df9 }
        r4 = 0;
        r12 = r12 + r14;
        r12 = java.lang.Long.valueOf(r12);	 Catch:{ all -> 0x0df9 }
        r6 = r5;
        r6.<init>(r7, r8, r9, r10, r12);	 Catch:{ all -> 0x0df9 }
        r4 = r5;
        goto L_0x0850;
    L_0x0833:
        r4 = new com.google.android.gms.measurement.internal.zzft;	 Catch:{ all -> 0x0df9 }
        r5 = r3.zztt;	 Catch:{ all -> 0x0df9 }
        r29 = "auto";
        r30 = "_lte";
        r6 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r6 = r6.zzbx();	 Catch:{ all -> 0x0df9 }
        r31 = r6.currentTimeMillis();	 Catch:{ all -> 0x0df9 }
        r33 = java.lang.Long.valueOf(r14);	 Catch:{ all -> 0x0df9 }
        r27 = r4;
        r28 = r5;
        r27.<init>(r28, r29, r30, r31, r33);	 Catch:{ all -> 0x0df9 }
    L_0x0850:
        r5 = new com.google.android.gms.internal.measurement.zzgo;	 Catch:{ all -> 0x0df9 }
        r5.<init>();	 Catch:{ all -> 0x0df9 }
        r6 = "_lte";
        r5.name = r6;	 Catch:{ all -> 0x0df9 }
        r6 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r6 = r6.zzbx();	 Catch:{ all -> 0x0df9 }
        r6 = r6.currentTimeMillis();	 Catch:{ all -> 0x0df9 }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0df9 }
        r5.zzazg = r6;	 Catch:{ all -> 0x0df9 }
        r6 = r4.value;	 Catch:{ all -> 0x0df9 }
        r6 = (java.lang.Long) r6;	 Catch:{ all -> 0x0df9 }
        r5.zzaxq = r6;	 Catch:{ all -> 0x0df9 }
        r6 = 0;
    L_0x0870:
        r7 = r3.zzaxv;	 Catch:{ all -> 0x0df9 }
        r7 = r7.length;	 Catch:{ all -> 0x0df9 }
        if (r6 >= r7) goto L_0x088c;
    L_0x0875:
        r7 = "_lte";
        r8 = r3.zzaxv;	 Catch:{ all -> 0x0df9 }
        r8 = r8[r6];	 Catch:{ all -> 0x0df9 }
        r8 = r8.name;	 Catch:{ all -> 0x0df9 }
        r7 = r7.equals(r8);	 Catch:{ all -> 0x0df9 }
        if (r7 == 0) goto L_0x0889;
    L_0x0883:
        r7 = r3.zzaxv;	 Catch:{ all -> 0x0df9 }
        r7[r6] = r5;	 Catch:{ all -> 0x0df9 }
        r6 = 1;
        goto L_0x088d;
    L_0x0889:
        r6 = r6 + 1;
        goto L_0x0870;
    L_0x088c:
        r6 = 0;
    L_0x088d:
        if (r6 != 0) goto L_0x08a9;
    L_0x088f:
        r6 = r3.zzaxv;	 Catch:{ all -> 0x0df9 }
        r7 = r3.zzaxv;	 Catch:{ all -> 0x0df9 }
        r7 = r7.length;	 Catch:{ all -> 0x0df9 }
        r8 = 1;
        r7 = r7 + r8;
        r6 = java.util.Arrays.copyOf(r6, r7);	 Catch:{ all -> 0x0df9 }
        r6 = (com.google.android.gms.internal.measurement.zzgo[]) r6;	 Catch:{ all -> 0x0df9 }
        r3.zzaxv = r6;	 Catch:{ all -> 0x0df9 }
        r6 = r3.zzaxv;	 Catch:{ all -> 0x0df9 }
        r7 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zzaxv;	 Catch:{ all -> 0x0df9 }
        r7 = r7.length;	 Catch:{ all -> 0x0df9 }
        r8 = 1;
        r7 = r7 - r8;
        r6[r7] = r5;	 Catch:{ all -> 0x0df9 }
    L_0x08a9:
        r5 = 0;
        r7 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1));
        if (r7 <= 0) goto L_0x08c7;
    L_0x08af:
        r5 = r55.zzjt();	 Catch:{ all -> 0x0df9 }
        r5.zza(r4);	 Catch:{ all -> 0x0df9 }
        r5 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r5 = r5.zzgt();	 Catch:{ all -> 0x0df9 }
        r5 = r5.zzjn();	 Catch:{ all -> 0x0df9 }
        r6 = "Updated lifetime engagement user property with value. Value";
        r4 = r4.value;	 Catch:{ all -> 0x0df9 }
        r5.zzg(r6, r4);	 Catch:{ all -> 0x0df9 }
    L_0x08c7:
        r4 = r3.zztt;	 Catch:{ all -> 0x0df9 }
        r5 = r3.zzaxv;	 Catch:{ all -> 0x0df9 }
        r6 = r3.zzaxu;	 Catch:{ all -> 0x0df9 }
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r4);	 Catch:{ all -> 0x0df9 }
        r7 = r55.zzjs();	 Catch:{ all -> 0x0df9 }
        r4 = r7.zza(r4, r6, r5);	 Catch:{ all -> 0x0df9 }
        r3.zzaym = r4;	 Catch:{ all -> 0x0df9 }
        r4 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r4 = r4.zzgv();	 Catch:{ all -> 0x0df9 }
        r5 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r5 = r5.zztt;	 Catch:{ all -> 0x0df9 }
        r4 = r4.zzav(r5);	 Catch:{ all -> 0x0df9 }
        if (r4 == 0) goto L_0x0c0d;
    L_0x08ea:
        r4 = new java.util.HashMap;	 Catch:{ all -> 0x0c07 }
        r4.<init>();	 Catch:{ all -> 0x0c07 }
        r5 = r3.zzaxu;	 Catch:{ all -> 0x0c07 }
        r5 = r5.length;	 Catch:{ all -> 0x0c07 }
        r5 = new com.google.android.gms.internal.measurement.zzgi[r5];	 Catch:{ all -> 0x0c07 }
        r6 = r1.zzadp;	 Catch:{ all -> 0x0c07 }
        r6 = r6.zzgr();	 Catch:{ all -> 0x0c07 }
        r6 = r6.zzmk();	 Catch:{ all -> 0x0c07 }
        r7 = r3.zzaxu;	 Catch:{ all -> 0x0c07 }
        r8 = r7.length;	 Catch:{ all -> 0x0c07 }
        r9 = 0;
        r10 = 0;
    L_0x0903:
        if (r9 >= r8) goto L_0x0bd5;
    L_0x0905:
        r11 = r7[r9];	 Catch:{ all -> 0x0c07 }
        r12 = r11.name;	 Catch:{ all -> 0x0c07 }
        r13 = "_ep";
        r12 = r12.equals(r13);	 Catch:{ all -> 0x0c07 }
        if (r12 == 0) goto L_0x098e;
    L_0x0911:
        r55.zzjr();	 Catch:{ all -> 0x0df9 }
        r12 = "_en";
        r12 = com.google.android.gms.measurement.internal.zzfq.zzb(r11, r12);	 Catch:{ all -> 0x0df9 }
        r12 = (java.lang.String) r12;	 Catch:{ all -> 0x0df9 }
        r13 = r4.get(r12);	 Catch:{ all -> 0x0df9 }
        r13 = (com.google.android.gms.measurement.internal.zzaa) r13;	 Catch:{ all -> 0x0df9 }
        if (r13 != 0) goto L_0x0933;
    L_0x0924:
        r13 = r55.zzjt();	 Catch:{ all -> 0x0df9 }
        r14 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r14 = r14.zztt;	 Catch:{ all -> 0x0df9 }
        r13 = r13.zzg(r14, r12);	 Catch:{ all -> 0x0df9 }
        r4.put(r12, r13);	 Catch:{ all -> 0x0df9 }
    L_0x0933:
        r12 = r13.zzaim;	 Catch:{ all -> 0x0df9 }
        if (r12 != 0) goto L_0x0980;
    L_0x0937:
        r12 = r13.zzain;	 Catch:{ all -> 0x0df9 }
        r14 = r12.longValue();	 Catch:{ all -> 0x0df9 }
        r12 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r12 <= 0) goto L_0x0950;
    L_0x0941:
        r55.zzjr();	 Catch:{ all -> 0x0df9 }
        r12 = r11.zzaxm;	 Catch:{ all -> 0x0df9 }
        r14 = "_sr";
        r15 = r13.zzain;	 Catch:{ all -> 0x0df9 }
        r12 = com.google.android.gms.measurement.internal.zzfq.zza(r12, r14, r15);	 Catch:{ all -> 0x0df9 }
        r11.zzaxm = r12;	 Catch:{ all -> 0x0df9 }
    L_0x0950:
        r12 = r13.zzaio;	 Catch:{ all -> 0x0df9 }
        if (r12 == 0) goto L_0x096d;
    L_0x0954:
        r12 = r13.zzaio;	 Catch:{ all -> 0x0df9 }
        r12 = r12.booleanValue();	 Catch:{ all -> 0x0df9 }
        if (r12 == 0) goto L_0x096d;
    L_0x095c:
        r55.zzjr();	 Catch:{ all -> 0x0df9 }
        r12 = r11.zzaxm;	 Catch:{ all -> 0x0df9 }
        r13 = "_efs";
        r14 = java.lang.Long.valueOf(r16);	 Catch:{ all -> 0x0df9 }
        r12 = com.google.android.gms.measurement.internal.zzfq.zza(r12, r13, r14);	 Catch:{ all -> 0x0df9 }
        r11.zzaxm = r12;	 Catch:{ all -> 0x0df9 }
    L_0x096d:
        r12 = r10 + 1;
        r5[r10] = r11;	 Catch:{ all -> 0x0df9 }
        r53 = r2;
        r47 = r3;
        r52 = r6;
        r45 = r7;
        r46 = r8;
        r48 = r9;
        r10 = r12;
        goto L_0x0bc5;
    L_0x0980:
        r53 = r2;
        r47 = r3;
        r52 = r6;
        r45 = r7;
        r46 = r8;
        r48 = r9;
        goto L_0x0bc5;
    L_0x098e:
        r12 = r55.zzls();	 Catch:{ all -> 0x0c07 }
        r13 = r2.zzaut;	 Catch:{ all -> 0x0c07 }
        r13 = r13.zztt;	 Catch:{ all -> 0x0c07 }
        r12 = r12.zzcm(r13);	 Catch:{ all -> 0x0c07 }
        r14 = r1.zzadp;	 Catch:{ all -> 0x0c07 }
        r14.zzgr();	 Catch:{ all -> 0x0c07 }
        r14 = r11.zzaxn;	 Catch:{ all -> 0x0c07 }
        r14 = r14.longValue();	 Catch:{ all -> 0x0c07 }
        r14 = com.google.android.gms.measurement.internal.zzfu.zzc(r14, r12);	 Catch:{ all -> 0x0c07 }
        r45 = r7;
        r7 = "_dbg";
        r46 = r8;
        r8 = java.lang.Long.valueOf(r16);	 Catch:{ all -> 0x0c07 }
        r18 = android.text.TextUtils.isEmpty(r7);	 Catch:{ all -> 0x0c07 }
        if (r18 != 0) goto L_0x09ff;
    L_0x09b9:
        if (r8 != 0) goto L_0x09bc;
    L_0x09bb:
        goto L_0x09ff;
    L_0x09bc:
        r47 = r3;
        r3 = r11.zzaxm;	 Catch:{ all -> 0x0df9 }
        r48 = r9;
        r9 = r3.length;	 Catch:{ all -> 0x0df9 }
        r49 = r12;
        r12 = 0;
    L_0x09c6:
        if (r12 >= r9) goto L_0x0a05;
    L_0x09c8:
        r13 = r3[r12];	 Catch:{ all -> 0x0df9 }
        r51 = r3;
        r3 = r13.name;	 Catch:{ all -> 0x0df9 }
        r3 = r7.equals(r3);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x09fa;
    L_0x09d4:
        r3 = r8 instanceof java.lang.Long;	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x09e0;
    L_0x09d8:
        r3 = r13.zzaxq;	 Catch:{ all -> 0x0df9 }
        r3 = r8.equals(r3);	 Catch:{ all -> 0x0df9 }
        if (r3 != 0) goto L_0x09f8;
    L_0x09e0:
        r3 = r8 instanceof java.lang.String;	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x09ec;
    L_0x09e4:
        r3 = r13.zzamw;	 Catch:{ all -> 0x0df9 }
        r3 = r8.equals(r3);	 Catch:{ all -> 0x0df9 }
        if (r3 != 0) goto L_0x09f8;
    L_0x09ec:
        r3 = r8 instanceof java.lang.Double;	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x0a05;
    L_0x09f0:
        r3 = r13.zzava;	 Catch:{ all -> 0x0df9 }
        r3 = r8.equals(r3);	 Catch:{ all -> 0x0df9 }
        if (r3 == 0) goto L_0x0a05;
    L_0x09f8:
        r3 = 1;
        goto L_0x0a06;
    L_0x09fa:
        r12 = r12 + 1;
        r3 = r51;
        goto L_0x09c6;
    L_0x09ff:
        r47 = r3;
        r48 = r9;
        r49 = r12;
    L_0x0a05:
        r3 = 0;
    L_0x0a06:
        if (r3 != 0) goto L_0x0a17;
    L_0x0a08:
        r3 = r55.zzls();	 Catch:{ all -> 0x0df9 }
        r7 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zztt;	 Catch:{ all -> 0x0df9 }
        r8 = r11.name;	 Catch:{ all -> 0x0df9 }
        r12 = r3.zzq(r7, r8);	 Catch:{ all -> 0x0df9 }
        goto L_0x0a18;
    L_0x0a17:
        r12 = 1;
    L_0x0a18:
        if (r12 > 0) goto L_0x0a3a;
    L_0x0a1a:
        r3 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzgt();	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzjj();	 Catch:{ all -> 0x0df9 }
        r7 = "Sample rate must be positive. event, rate";
        r8 = r11.name;	 Catch:{ all -> 0x0df9 }
        r9 = java.lang.Integer.valueOf(r12);	 Catch:{ all -> 0x0df9 }
        r3.zze(r7, r8, r9);	 Catch:{ all -> 0x0df9 }
        r3 = r10 + 1;
        r5[r10] = r11;	 Catch:{ all -> 0x0df9 }
        r53 = r2;
        r10 = r3;
        r52 = r6;
        goto L_0x0bc5;
    L_0x0a3a:
        r3 = r11.name;	 Catch:{ all -> 0x0c07 }
        r3 = r4.get(r3);	 Catch:{ all -> 0x0c07 }
        r3 = (com.google.android.gms.measurement.internal.zzaa) r3;	 Catch:{ all -> 0x0c07 }
        if (r3 != 0) goto L_0x0a8e;
    L_0x0a44:
        r3 = r55.zzjt();	 Catch:{ all -> 0x0df9 }
        r7 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zztt;	 Catch:{ all -> 0x0df9 }
        r8 = r11.name;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzg(r7, r8);	 Catch:{ all -> 0x0df9 }
        if (r3 != 0) goto L_0x0a8e;
    L_0x0a54:
        r3 = r1.zzadp;	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzgt();	 Catch:{ all -> 0x0df9 }
        r3 = r3.zzjj();	 Catch:{ all -> 0x0df9 }
        r7 = "Event being bundled has no eventAggregate. appId, eventName";
        r8 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r8 = r8.zztt;	 Catch:{ all -> 0x0df9 }
        r9 = r11.name;	 Catch:{ all -> 0x0df9 }
        r3.zze(r7, r8, r9);	 Catch:{ all -> 0x0df9 }
        r3 = new com.google.android.gms.measurement.internal.zzaa;	 Catch:{ all -> 0x0df9 }
        r7 = r2.zzaut;	 Catch:{ all -> 0x0df9 }
        r7 = r7.zztt;	 Catch:{ all -> 0x0df9 }
        r8 = r11.name;	 Catch:{ all -> 0x0df9 }
        r30 = 1;
        r32 = 1;
        r9 = r11.zzaxn;	 Catch:{ all -> 0x0df9 }
        r34 = r9.longValue();	 Catch:{ all -> 0x0df9 }
        r36 = 0;
        r38 = 0;
        r39 = 0;
        r40 = 0;
        r41 = 0;
        r27 = r3;
        r28 = r7;
        r29 = r8;
        r27.<init>(r28, r29, r30, r32, r34, r36, r38, r39, r40, r41);	 Catch:{ all -> 0x0df9 }
    L_0x0a8e:
        r55.zzjr();	 Catch:{ all -> 0x0c07 }
        r7 = "_eid";
        r7 = com.google.android.gms.measurement.internal.zzfq.zzb(r11, r7);	 Catch:{ all -> 0x0c07 }
        r7 = (java.lang.Long) r7;	 Catch:{ all -> 0x0c07 }
        if (r7 == 0) goto L_0x0a9d;
    L_0x0a9b:
        r8 = 1;
        goto L_0x0a9e;
    L_0x0a9d:
        r8 = 0;
    L_0x0a9e:
        r8 = java.lang.Boolean.valueOf(r8);	 Catch:{ all -> 0x0c07 }
        r9 = 1;
        if (r12 != r9) goto L_0x0acc;
    L_0x0aa5:
        r7 = r10 + 1;
        r5[r10] = r11;	 Catch:{ all -> 0x0df9 }
        r8 = r8.booleanValue();	 Catch:{ all -> 0x0df9 }
        if (r8 == 0) goto L_0x0ac5;
    L_0x0aaf:
        r8 = r3.zzaim;	 Catch:{ all -> 0x0df9 }
        if (r8 != 0) goto L_0x0abb;
    L_0x0ab3:
        r8 = r3.zzain;	 Catch:{ all -> 0x0df9 }
        if (r8 != 0) goto L_0x0abb;
    L_0x0ab7:
        r8 = r3.zzaio;	 Catch:{ all -> 0x0df9 }
        if (r8 == 0) goto L_0x0ac5;
    L_0x0abb:
        r8 = 0;
        r3 = r3.zza(r8, r8, r8);	 Catch:{ all -> 0x0df9 }
        r8 = r11.name;	 Catch:{ all -> 0x0df9 }
        r4.put(r8, r3);	 Catch:{ all -> 0x0df9 }
    L_0x0ac5:
        r53 = r2;
        r52 = r6;
        r10 = r7;
        goto L_0x0bc5;
    L_0x0acc:
        r9 = r6.nextInt(r12);	 Catch:{ all -> 0x0c07 }
        if (r9 != 0) goto L_0x0b0d;
    L_0x0ad2:
        r55.zzjr();	 Catch:{ all -> 0x0df9 }
        r7 = r11.zzaxm;	 Catch:{ all -> 0x0df9 }
        r9 = "_sr";
        r12 = (long) r12;	 Catch:{ all -> 0x0df9 }
        r52 = r6;
        r6 = java.lang.Long.valueOf(r12);	 Catch:{ all -> 0x0df9 }
        r6 = com.google.android.gms.measurement.internal.zzfq.zza(r7, r9, r6);	 Catch:{ all -> 0x0df9 }
        r11.zzaxm = r6;	 Catch:{ all -> 0x0df9 }
        r6 = r10 + 1;
        r5[r10] = r11;	 Catch:{ all -> 0x0df9 }
        r7 = r8.booleanValue();	 Catch:{ all -> 0x0df9 }
        if (r7 == 0) goto L_0x0af9;
    L_0x0af0:
        r7 = java.lang.Long.valueOf(r12);	 Catch:{ all -> 0x0df9 }
        r8 = 0;
        r3 = r3.zza(r8, r7, r8);	 Catch:{ all -> 0x0df9 }
    L_0x0af9:
        r7 = r11.name;	 Catch:{ all -> 0x0df9 }
        r8 = r11.zzaxn;	 Catch:{ all -> 0x0df9 }
        r8 = r8.longValue();	 Catch:{ all -> 0x0df9 }
        r3 = r3.zza(r8, r14);	 Catch:{ all -> 0x0df9 }
        r4.put(r7, r3);	 Catch:{ all -> 0x0df9 }
        r53 = r2;
        r10 = r6;
        goto L_0x0bc5;
    L_0x0b0d:
        r52 = r6;
        r6 = r1.zzadp;	 Catch:{ all -> 0x0c07 }
        r6 = r6.zzgv();	 Catch:{ all -> 0x0c07 }
        r9 = r2.zzaut;	 Catch:{ all -> 0x0c07 }
        r9 = r9.zztt;	 Catch:{ all -> 0x0c07 }
        r6 = r6.zzbh(r9);	 Catch:{ all -> 0x0c07 }
        if (r6 == 0) goto L_0x0b4b;
    L_0x0b1f:
        r6 = r3.zzail;	 Catch:{ all -> 0x0c07 }
        if (r6 == 0) goto L_0x0b2e;
    L_0x0b23:
        r6 = r3.zzail;	 Catch:{ all -> 0x0df9 }
        r18 = r6.longValue();	 Catch:{ all -> 0x0df9 }
        r53 = r2;
        r54 = r7;
        goto L_0x0b43;
    L_0x0b2e:
        r6 = r1.zzadp;	 Catch:{ all -> 0x0c07 }
        r6.zzgr();	 Catch:{ all -> 0x0c07 }
        r6 = r11.zzaxo;	 Catch:{ all -> 0x0c07 }
        r53 = r2;
        r1 = r6.longValue();	 Catch:{ all -> 0x0c07 }
        r54 = r7;
        r6 = r49;
        r18 = com.google.android.gms.measurement.internal.zzfu.zzc(r1, r6);	 Catch:{ all -> 0x0c07 }
    L_0x0b43:
        r1 = (r18 > r14 ? 1 : (r18 == r14 ? 0 : -1));
        if (r1 == 0) goto L_0x0b49;
    L_0x0b47:
        r1 = 1;
        goto L_0x0b65;
    L_0x0b49:
        r1 = 0;
        goto L_0x0b65;
    L_0x0b4b:
        r53 = r2;
        r54 = r7;
        r1 = r3.zzaik;	 Catch:{ all -> 0x0c07 }
        r6 = r11.zzaxn;	 Catch:{ all -> 0x0c07 }
        r6 = r6.longValue();	 Catch:{ all -> 0x0c07 }
        r9 = 0;
        r6 = r6 - r1;
        r1 = java.lang.Math.abs(r6);	 Catch:{ all -> 0x0c07 }
        r6 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
        r9 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
        if (r9 < 0) goto L_0x0b49;
    L_0x0b64:
        goto L_0x0b47;
    L_0x0b65:
        if (r1 == 0) goto L_0x0bb3;
    L_0x0b67:
        r55.zzjr();	 Catch:{ all -> 0x0c07 }
        r1 = r11.zzaxm;	 Catch:{ all -> 0x0c07 }
        r2 = "_efs";
        r6 = java.lang.Long.valueOf(r16);	 Catch:{ all -> 0x0c07 }
        r1 = com.google.android.gms.measurement.internal.zzfq.zza(r1, r2, r6);	 Catch:{ all -> 0x0c07 }
        r11.zzaxm = r1;	 Catch:{ all -> 0x0c07 }
        r55.zzjr();	 Catch:{ all -> 0x0c07 }
        r1 = r11.zzaxm;	 Catch:{ all -> 0x0c07 }
        r2 = "_sr";
        r6 = (long) r12;	 Catch:{ all -> 0x0c07 }
        r9 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0c07 }
        r1 = com.google.android.gms.measurement.internal.zzfq.zza(r1, r2, r9);	 Catch:{ all -> 0x0c07 }
        r11.zzaxm = r1;	 Catch:{ all -> 0x0c07 }
        r1 = r10 + 1;
        r5[r10] = r11;	 Catch:{ all -> 0x0c07 }
        r2 = r8.booleanValue();	 Catch:{ all -> 0x0c07 }
        if (r2 == 0) goto L_0x0ba2;
    L_0x0b94:
        r2 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0c07 }
        r6 = 1;
        r7 = java.lang.Boolean.valueOf(r6);	 Catch:{ all -> 0x0c07 }
        r6 = 0;
        r3 = r3.zza(r6, r2, r7);	 Catch:{ all -> 0x0c07 }
    L_0x0ba2:
        r2 = r11.name;	 Catch:{ all -> 0x0c07 }
        r6 = r11.zzaxn;	 Catch:{ all -> 0x0c07 }
        r6 = r6.longValue();	 Catch:{ all -> 0x0c07 }
        r3 = r3.zza(r6, r14);	 Catch:{ all -> 0x0c07 }
        r4.put(r2, r3);	 Catch:{ all -> 0x0c07 }
        r10 = r1;
        goto L_0x0bc5;
    L_0x0bb3:
        r1 = r8.booleanValue();	 Catch:{ all -> 0x0c07 }
        if (r1 == 0) goto L_0x0bc5;
    L_0x0bb9:
        r1 = r11.name;	 Catch:{ all -> 0x0c07 }
        r7 = r54;
        r2 = 0;
        r3 = r3.zza(r7, r2, r2);	 Catch:{ all -> 0x0c07 }
        r4.put(r1, r3);	 Catch:{ all -> 0x0c07 }
    L_0x0bc5:
        r9 = r48 + 1;
        r7 = r45;
        r8 = r46;
        r3 = r47;
        r6 = r52;
        r2 = r53;
        r1 = r55;
        goto L_0x0903;
    L_0x0bd5:
        r53 = r2;
        r1 = r3;
        r2 = r1.zzaxu;	 Catch:{ all -> 0x0c07 }
        r2 = r2.length;	 Catch:{ all -> 0x0c07 }
        if (r10 >= r2) goto L_0x0be5;
    L_0x0bdd:
        r2 = java.util.Arrays.copyOf(r5, r10);	 Catch:{ all -> 0x0c07 }
        r2 = (com.google.android.gms.internal.measurement.zzgi[]) r2;	 Catch:{ all -> 0x0c07 }
        r1.zzaxu = r2;	 Catch:{ all -> 0x0c07 }
    L_0x0be5:
        r2 = r4.entrySet();	 Catch:{ all -> 0x0c07 }
        r2 = r2.iterator();	 Catch:{ all -> 0x0c07 }
    L_0x0bed:
        r3 = r2.hasNext();	 Catch:{ all -> 0x0c07 }
        if (r3 == 0) goto L_0x0c10;
    L_0x0bf3:
        r3 = r2.next();	 Catch:{ all -> 0x0c07 }
        r3 = (java.util.Map.Entry) r3;	 Catch:{ all -> 0x0c07 }
        r4 = r55.zzjt();	 Catch:{ all -> 0x0c07 }
        r3 = r3.getValue();	 Catch:{ all -> 0x0c07 }
        r3 = (com.google.android.gms.measurement.internal.zzaa) r3;	 Catch:{ all -> 0x0c07 }
        r4.zza(r3);	 Catch:{ all -> 0x0c07 }
        goto L_0x0bed;
    L_0x0c07:
        r0 = move-exception;
        r1 = r0;
        r5 = r55;
        goto L_0x0dfc;
    L_0x0c0d:
        r53 = r2;
        r1 = r3;
    L_0x0c10:
        r2 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x0dd6 }
        r1.zzaxx = r2;	 Catch:{ all -> 0x0dd6 }
        r2 = -9223372036854775808;
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x0dd6 }
        r1.zzaxy = r2;	 Catch:{ all -> 0x0dd6 }
        r2 = 0;
    L_0x0c24:
        r3 = r1.zzaxu;	 Catch:{ all -> 0x0dd6 }
        r3 = r3.length;	 Catch:{ all -> 0x0dd6 }
        if (r2 >= r3) goto L_0x0c58;
    L_0x0c29:
        r3 = r1.zzaxu;	 Catch:{ all -> 0x0c07 }
        r3 = r3[r2];	 Catch:{ all -> 0x0c07 }
        r4 = r3.zzaxn;	 Catch:{ all -> 0x0c07 }
        r4 = r4.longValue();	 Catch:{ all -> 0x0c07 }
        r6 = r1.zzaxx;	 Catch:{ all -> 0x0c07 }
        r6 = r6.longValue();	 Catch:{ all -> 0x0c07 }
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 >= 0) goto L_0x0c41;
    L_0x0c3d:
        r4 = r3.zzaxn;	 Catch:{ all -> 0x0c07 }
        r1.zzaxx = r4;	 Catch:{ all -> 0x0c07 }
    L_0x0c41:
        r4 = r3.zzaxn;	 Catch:{ all -> 0x0c07 }
        r4 = r4.longValue();	 Catch:{ all -> 0x0c07 }
        r6 = r1.zzaxy;	 Catch:{ all -> 0x0c07 }
        r6 = r6.longValue();	 Catch:{ all -> 0x0c07 }
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 <= 0) goto L_0x0c55;
    L_0x0c51:
        r3 = r3.zzaxn;	 Catch:{ all -> 0x0c07 }
        r1.zzaxy = r3;	 Catch:{ all -> 0x0c07 }
    L_0x0c55:
        r2 = r2 + 1;
        goto L_0x0c24;
    L_0x0c58:
        r2 = r53;
        r3 = r2.zzaut;	 Catch:{ all -> 0x0dd6 }
        r3 = r3.zztt;	 Catch:{ all -> 0x0dd6 }
        r4 = r55.zzjt();	 Catch:{ all -> 0x0dd6 }
        r4 = r4.zzbo(r3);	 Catch:{ all -> 0x0dd6 }
        if (r4 != 0) goto L_0x0c82;
    L_0x0c68:
        r5 = r55;
        r4 = r5.zzadp;	 Catch:{ all -> 0x0df6 }
        r4 = r4.zzgt();	 Catch:{ all -> 0x0df6 }
        r4 = r4.zzjg();	 Catch:{ all -> 0x0df6 }
        r6 = "Bundling raw events w/o app info. appId";
        r7 = r2.zzaut;	 Catch:{ all -> 0x0df6 }
        r7 = r7.zztt;	 Catch:{ all -> 0x0df6 }
        r7 = com.google.android.gms.measurement.internal.zzaq.zzby(r7);	 Catch:{ all -> 0x0df6 }
        r4.zzg(r6, r7);	 Catch:{ all -> 0x0df6 }
        goto L_0x0ce0;
    L_0x0c82:
        r5 = r55;
        r6 = r1.zzaxu;	 Catch:{ all -> 0x0df6 }
        r6 = r6.length;	 Catch:{ all -> 0x0df6 }
        if (r6 <= 0) goto L_0x0ce0;
    L_0x0c89:
        r6 = r4.zzhe();	 Catch:{ all -> 0x0df6 }
        r8 = 0;
        r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r10 == 0) goto L_0x0c98;
    L_0x0c93:
        r8 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0df6 }
        goto L_0x0c99;
    L_0x0c98:
        r8 = 0;
    L_0x0c99:
        r1.zzaya = r8;	 Catch:{ all -> 0x0df6 }
        r8 = r4.zzhd();	 Catch:{ all -> 0x0df6 }
        r10 = 0;
        r12 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r12 != 0) goto L_0x0ca6;
    L_0x0ca5:
        goto L_0x0ca7;
    L_0x0ca6:
        r6 = r8;
    L_0x0ca7:
        r8 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
        if (r8 == 0) goto L_0x0cb0;
    L_0x0cab:
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0df6 }
        goto L_0x0cb1;
    L_0x0cb0:
        r6 = 0;
    L_0x0cb1:
        r1.zzaxz = r6;	 Catch:{ all -> 0x0df6 }
        r4.zzhm();	 Catch:{ all -> 0x0df6 }
        r6 = r4.zzhj();	 Catch:{ all -> 0x0df6 }
        r6 = (int) r6;	 Catch:{ all -> 0x0df6 }
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ all -> 0x0df6 }
        r1.zzayk = r6;	 Catch:{ all -> 0x0df6 }
        r6 = r1.zzaxx;	 Catch:{ all -> 0x0df6 }
        r6 = r6.longValue();	 Catch:{ all -> 0x0df6 }
        r4.zzs(r6);	 Catch:{ all -> 0x0df6 }
        r6 = r1.zzaxy;	 Catch:{ all -> 0x0df6 }
        r6 = r6.longValue();	 Catch:{ all -> 0x0df6 }
        r4.zzt(r6);	 Catch:{ all -> 0x0df6 }
        r6 = r4.zzhu();	 Catch:{ all -> 0x0df6 }
        r1.zzagy = r6;	 Catch:{ all -> 0x0df6 }
        r6 = r55.zzjt();	 Catch:{ all -> 0x0df6 }
        r6.zza(r4);	 Catch:{ all -> 0x0df6 }
    L_0x0ce0:
        r4 = r1.zzaxu;	 Catch:{ all -> 0x0df6 }
        r4 = r4.length;	 Catch:{ all -> 0x0df6 }
        if (r4 <= 0) goto L_0x0d35;
    L_0x0ce5:
        r4 = r5.zzadp;	 Catch:{ all -> 0x0df6 }
        r4.zzgw();	 Catch:{ all -> 0x0df6 }
        r4 = r55.zzls();	 Catch:{ all -> 0x0df6 }
        r6 = r2.zzaut;	 Catch:{ all -> 0x0df6 }
        r6 = r6.zztt;	 Catch:{ all -> 0x0df6 }
        r4 = r4.zzci(r6);	 Catch:{ all -> 0x0df6 }
        if (r4 == 0) goto L_0x0d02;
    L_0x0cf8:
        r6 = r4.zzawx;	 Catch:{ all -> 0x0df6 }
        if (r6 != 0) goto L_0x0cfd;
    L_0x0cfc:
        goto L_0x0d02;
    L_0x0cfd:
        r4 = r4.zzawx;	 Catch:{ all -> 0x0df6 }
        r1.zzayr = r4;	 Catch:{ all -> 0x0df6 }
        goto L_0x0d2c;
    L_0x0d02:
        r4 = r2.zzaut;	 Catch:{ all -> 0x0df6 }
        r4 = r4.zzafx;	 Catch:{ all -> 0x0df6 }
        r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ all -> 0x0df6 }
        if (r4 == 0) goto L_0x0d15;
    L_0x0d0c:
        r6 = -1;
        r4 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0df6 }
        r1.zzayr = r4;	 Catch:{ all -> 0x0df6 }
        goto L_0x0d2c;
    L_0x0d15:
        r4 = r5.zzadp;	 Catch:{ all -> 0x0df6 }
        r4 = r4.zzgt();	 Catch:{ all -> 0x0df6 }
        r4 = r4.zzjj();	 Catch:{ all -> 0x0df6 }
        r6 = "Did not find measurement config or missing version info. appId";
        r7 = r2.zzaut;	 Catch:{ all -> 0x0df6 }
        r7 = r7.zztt;	 Catch:{ all -> 0x0df6 }
        r7 = com.google.android.gms.measurement.internal.zzaq.zzby(r7);	 Catch:{ all -> 0x0df6 }
        r4.zzg(r6, r7);	 Catch:{ all -> 0x0df6 }
    L_0x0d2c:
        r4 = r55.zzjt();	 Catch:{ all -> 0x0df6 }
        r11 = r26;
        r4.zza(r1, r11);	 Catch:{ all -> 0x0df6 }
    L_0x0d35:
        r1 = r55.zzjt();	 Catch:{ all -> 0x0df6 }
        r2 = r2.zzauu;	 Catch:{ all -> 0x0df6 }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r2);	 Catch:{ all -> 0x0df6 }
        r1.zzaf();	 Catch:{ all -> 0x0df6 }
        r1.zzcl();	 Catch:{ all -> 0x0df6 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0df6 }
        r6 = "rowid in (";
        r4.<init>(r6);	 Catch:{ all -> 0x0df6 }
        r6 = 0;
    L_0x0d4c:
        r7 = r2.size();	 Catch:{ all -> 0x0df6 }
        if (r6 >= r7) goto L_0x0d69;
    L_0x0d52:
        if (r6 == 0) goto L_0x0d59;
    L_0x0d54:
        r7 = ",";
        r4.append(r7);	 Catch:{ all -> 0x0df6 }
    L_0x0d59:
        r7 = r2.get(r6);	 Catch:{ all -> 0x0df6 }
        r7 = (java.lang.Long) r7;	 Catch:{ all -> 0x0df6 }
        r7 = r7.longValue();	 Catch:{ all -> 0x0df6 }
        r4.append(r7);	 Catch:{ all -> 0x0df6 }
        r6 = r6 + 1;
        goto L_0x0d4c;
    L_0x0d69:
        r6 = ")";
        r4.append(r6);	 Catch:{ all -> 0x0df6 }
        r6 = r1.getWritableDatabase();	 Catch:{ all -> 0x0df6 }
        r7 = "raw_events";
        r4 = r4.toString();	 Catch:{ all -> 0x0df6 }
        r8 = 0;
        r4 = r6.delete(r7, r4, r8);	 Catch:{ all -> 0x0df6 }
        r6 = r2.size();	 Catch:{ all -> 0x0df6 }
        if (r4 == r6) goto L_0x0d9c;
    L_0x0d83:
        r1 = r1.zzgt();	 Catch:{ all -> 0x0df6 }
        r1 = r1.zzjg();	 Catch:{ all -> 0x0df6 }
        r6 = "Deleted fewer rows from raw events table than expected";
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ all -> 0x0df6 }
        r2 = r2.size();	 Catch:{ all -> 0x0df6 }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ all -> 0x0df6 }
        r1.zze(r6, r4, r2);	 Catch:{ all -> 0x0df6 }
    L_0x0d9c:
        r1 = r55.zzjt();	 Catch:{ all -> 0x0df6 }
        r2 = r1.getWritableDatabase();	 Catch:{ all -> 0x0df6 }
        r4 = "delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)";
        r6 = 2;
        r6 = new java.lang.String[r6];	 Catch:{ SQLiteException -> 0x0db3 }
        r7 = 0;
        r6[r7] = r3;	 Catch:{ SQLiteException -> 0x0db3 }
        r7 = 1;
        r6[r7] = r3;	 Catch:{ SQLiteException -> 0x0db3 }
        r2.execSQL(r4, r6);	 Catch:{ SQLiteException -> 0x0db3 }
        goto L_0x0dc6;
    L_0x0db3:
        r0 = move-exception;
        r2 = r0;
        r1 = r1.zzgt();	 Catch:{ all -> 0x0df6 }
        r1 = r1.zzjg();	 Catch:{ all -> 0x0df6 }
        r4 = "Failed to remove unused event metadata. appId";
        r3 = com.google.android.gms.measurement.internal.zzaq.zzby(r3);	 Catch:{ all -> 0x0df6 }
        r1.zze(r4, r3, r2);	 Catch:{ all -> 0x0df6 }
    L_0x0dc6:
        r1 = r55.zzjt();	 Catch:{ all -> 0x0df6 }
        r1.setTransactionSuccessful();	 Catch:{ all -> 0x0df6 }
        r1 = r55.zzjt();
        r1.endTransaction();
        r1 = 1;
        return r1;
    L_0x0dd6:
        r0 = move-exception;
        r5 = r55;
        goto L_0x0dfb;
    L_0x0dda:
        r5 = r1;
        r1 = r55.zzjt();	 Catch:{ all -> 0x0df6 }
        r1.setTransactionSuccessful();	 Catch:{ all -> 0x0df6 }
        r1 = r55.zzjt();
        r1.endTransaction();
        r1 = 0;
        return r1;
    L_0x0deb:
        r0 = move-exception;
        r5 = r1;
        r1 = r0;
        r22 = r6;
    L_0x0df0:
        if (r22 == 0) goto L_0x0df8;
    L_0x0df2:
        r22.close();	 Catch:{ all -> 0x0df6 }
        goto L_0x0df8;
    L_0x0df6:
        r0 = move-exception;
        goto L_0x0dfb;
    L_0x0df8:
        throw r1;	 Catch:{ all -> 0x0df6 }
    L_0x0df9:
        r0 = move-exception;
        r5 = r1;
    L_0x0dfb:
        r1 = r0;
    L_0x0dfc:
        r2 = r55.zzjt();
        r2.endTransaction();
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfk.zzd(java.lang.String, long):boolean");
    }

    private final boolean zza(zzgi com_google_android_gms_internal_measurement_zzgi, zzgi com_google_android_gms_internal_measurement_zzgi2) {
        Preconditions.checkArgument("_e".equals(com_google_android_gms_internal_measurement_zzgi.name));
        zzjr();
        zzgj zza = zzfq.zza(com_google_android_gms_internal_measurement_zzgi, "_sc");
        String str = null;
        Object obj;
        if (zza == null) {
            obj = null;
        } else {
            obj = zza.zzamw;
        }
        zzjr();
        zzgj zza2 = zzfq.zza(com_google_android_gms_internal_measurement_zzgi2, "_pc");
        if (zza2 != null) {
            str = zza2.zzamw;
        }
        if (str == null || !str.equals(r0)) {
            return null;
        }
        zzjr();
        zza = zzfq.zza(com_google_android_gms_internal_measurement_zzgi, "_et");
        if (zza.zzaxq != null) {
            if (zza.zzaxq.longValue() > 0) {
                long longValue = zza.zzaxq.longValue();
                zzjr();
                zzgj zza3 = zzfq.zza(com_google_android_gms_internal_measurement_zzgi2, "_et");
                if (!(zza3 == null || zza3.zzaxq == null || zza3.zzaxq.longValue() <= 0)) {
                    longValue += zza3.zzaxq.longValue();
                }
                zzjr();
                com_google_android_gms_internal_measurement_zzgi2.zzaxm = zzfq.zza(com_google_android_gms_internal_measurement_zzgi2.zzaxm, "_et", Long.valueOf(longValue));
                zzjr();
                com_google_android_gms_internal_measurement_zzgi.zzaxm = zzfq.zza(com_google_android_gms_internal_measurement_zzgi.zzaxm, "_fr", Long.valueOf(1));
                return true;
            }
        }
        return true;
    }

    @VisibleForTesting
    private static zzgj[] zza(zzgj[] com_google_android_gms_internal_measurement_zzgjArr, @NonNull String str) {
        int i = 0;
        while (i < com_google_android_gms_internal_measurement_zzgjArr.length) {
            if (str.equals(com_google_android_gms_internal_measurement_zzgjArr[i].name)) {
                break;
            }
            i++;
        }
        i = -1;
        if (i < 0) {
            return com_google_android_gms_internal_measurement_zzgjArr;
        }
        return zza(com_google_android_gms_internal_measurement_zzgjArr, i);
    }

    @VisibleForTesting
    private static zzgj[] zza(zzgj[] com_google_android_gms_internal_measurement_zzgjArr, int i) {
        Object obj = new zzgj[(com_google_android_gms_internal_measurement_zzgjArr.length - 1)];
        if (i > 0) {
            System.arraycopy(com_google_android_gms_internal_measurement_zzgjArr, 0, obj, 0, i);
        }
        if (i < obj.length) {
            System.arraycopy(com_google_android_gms_internal_measurement_zzgjArr, i + 1, obj, i, obj.length - i);
        }
        return obj;
    }

    @VisibleForTesting
    private static zzgj[] zza(zzgj[] com_google_android_gms_internal_measurement_zzgjArr, int i, String str) {
        for (zzgj com_google_android_gms_internal_measurement_zzgj : com_google_android_gms_internal_measurement_zzgjArr) {
            if ("_err".equals(com_google_android_gms_internal_measurement_zzgj.name)) {
                return com_google_android_gms_internal_measurement_zzgjArr;
            }
        }
        Object obj = new zzgj[(com_google_android_gms_internal_measurement_zzgjArr.length + 2)];
        System.arraycopy(com_google_android_gms_internal_measurement_zzgjArr, 0, obj, 0, com_google_android_gms_internal_measurement_zzgjArr.length);
        com_google_android_gms_internal_measurement_zzgjArr = new zzgj();
        com_google_android_gms_internal_measurement_zzgjArr.name = "_err";
        com_google_android_gms_internal_measurement_zzgjArr.zzaxq = Long.valueOf((long) i);
        i = new zzgj();
        i.name = "_ev";
        i.zzamw = str;
        obj[obj.length - 2] = com_google_android_gms_internal_measurement_zzgjArr;
        obj[obj.length - 1] = i;
        return obj;
    }

    @WorkerThread
    @VisibleForTesting
    final void zza(int i, Throwable th, byte[] bArr, String str) {
        zzaf();
        zzlx();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzauj = false;
                zzmc();
            }
        }
        List<Long> list = this.zzaun;
        this.zzaun = null;
        int i2 = 1;
        if ((i == Callback.DEFAULT_DRAG_ANIMATION_DURATION || i == 204) && th == null) {
            try {
                this.zzadp.zzgu().zzanl.set(this.zzadp.zzbx().currentTimeMillis());
                this.zzadp.zzgu().zzanm.set(0);
                zzmb();
                this.zzadp.zzgt().zzjo().zze("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zzjt().beginTransaction();
                try {
                    for (Long l : list) {
                        try {
                            bArr = zzjt();
                            long longValue = l.longValue();
                            bArr.zzaf();
                            bArr.zzcl();
                            if (bArr.getWritableDatabase().delete("queue", "rowid=?", new String[]{String.valueOf(longValue)}) != 1) {
                                throw new SQLiteException("Deleted fewer rows from queue than expected");
                            }
                        } catch (String str2) {
                            bArr.zzgt().zzjg().zzg("Failed to delete a bundle in a queue table", str2);
                            throw str2;
                        } catch (byte[] bArr2) {
                            if (this.zzauo == null || this.zzauo.contains(l) == null) {
                                throw bArr2;
                            }
                        }
                    }
                    zzjt().setTransactionSuccessful();
                    this.zzauo = null;
                    if (zzlt().zzfb() == 0 || zzma() == 0) {
                        this.zzaup = -1;
                        zzmb();
                    } else {
                        zzlz();
                    }
                    this.zzaue = 0;
                } finally {
                    zzjt().endTransaction();
                }
            } catch (int i3) {
                this.zzadp.zzgt().zzjg().zzg("Database error while trying to delete uploaded bundles", i3);
                this.zzaue = this.zzadp.zzbx().elapsedRealtime();
                this.zzadp.zzgt().zzjo().zzg("Disable upload, time", Long.valueOf(this.zzaue));
            }
        } else {
            this.zzadp.zzgt().zzjo().zze("Network upload failed. Will retry later. code, error", Integer.valueOf(i3), th);
            this.zzadp.zzgu().zzanm.set(this.zzadp.zzbx().currentTimeMillis());
            if (i3 != 503) {
                if (i3 != 429) {
                    i2 = 0;
                }
            }
            if (i2 != 0) {
                this.zzadp.zzgu().zzann.set(this.zzadp.zzbx().currentTimeMillis());
            }
            if (this.zzadp.zzgv().zzay(str2) != 0) {
                zzjt().zzc(list);
            }
            zzmb();
        }
        this.zzauj = false;
        zzmc();
    }

    private final boolean zzma() {
        zzaf();
        zzlx();
        if (!zzjt().zzim()) {
            if (TextUtils.isEmpty(zzjt().zzih())) {
                return false;
            }
        }
        return true;
    }

    @androidx.annotation.WorkerThread
    private final void zzb(com.google.android.gms.measurement.internal.zzg r11) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r10 = this;
        r10.zzaf();
        r0 = r11.getGmpAppId();
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 == 0) goto L_0x002b;
    L_0x000d:
        r0 = com.google.android.gms.measurement.internal.zzo.zzig();
        if (r0 == 0) goto L_0x001d;
    L_0x0013:
        r0 = r11.zzhb();
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 == 0) goto L_0x002b;
    L_0x001d:
        r2 = r11.zzal();
        r3 = 204; // 0xcc float:2.86E-43 double:1.01E-321;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r1 = r10;
        r1.zzb(r2, r3, r4, r5, r6);
        return;
    L_0x002b:
        r0 = r10.zzadp;
        r0 = r0.zzgv();
        r1 = new android.net.Uri$Builder;
        r1.<init>();
        r2 = r11.getGmpAppId();
        r3 = android.text.TextUtils.isEmpty(r2);
        if (r3 == 0) goto L_0x004a;
    L_0x0040:
        r3 = com.google.android.gms.measurement.internal.zzo.zzig();
        if (r3 == 0) goto L_0x004a;
    L_0x0046:
        r2 = r11.zzhb();
    L_0x004a:
        r3 = com.google.android.gms.measurement.internal.zzag.zzajk;
        r3 = r3.get();
        r3 = (java.lang.String) r3;
        r3 = r1.scheme(r3);
        r4 = com.google.android.gms.measurement.internal.zzag.zzajl;
        r4 = r4.get();
        r4 = (java.lang.String) r4;
        r3 = r3.encodedAuthority(r4);
        r4 = "config/app/";
        r2 = java.lang.String.valueOf(r2);
        r5 = r2.length();
        if (r5 == 0) goto L_0x0073;
    L_0x006e:
        r2 = r4.concat(r2);
        goto L_0x0078;
    L_0x0073:
        r2 = new java.lang.String;
        r2.<init>(r4);
    L_0x0078:
        r2 = r3.path(r2);
        r3 = "app_instance_id";
        r4 = r11.getAppInstanceId();
        r2 = r2.appendQueryParameter(r3, r4);
        r3 = "platform";
        r4 = "android";
        r2 = r2.appendQueryParameter(r3, r4);
        r3 = "gmp_version";
        r4 = r0.zzhh();
        r0 = java.lang.String.valueOf(r4);
        r2.appendQueryParameter(r3, r0);
        r0 = r1.build();
        r0 = r0.toString();
        r4 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x0114 }
        r4.<init>(r0);	 Catch:{ MalformedURLException -> 0x0114 }
        r1 = r10.zzadp;	 Catch:{ MalformedURLException -> 0x0114 }
        r1 = r1.zzgt();	 Catch:{ MalformedURLException -> 0x0114 }
        r1 = r1.zzjo();	 Catch:{ MalformedURLException -> 0x0114 }
        r2 = "Fetching remote configuration";	 Catch:{ MalformedURLException -> 0x0114 }
        r3 = r11.zzal();	 Catch:{ MalformedURLException -> 0x0114 }
        r1.zzg(r2, r3);	 Catch:{ MalformedURLException -> 0x0114 }
        r1 = r10.zzls();	 Catch:{ MalformedURLException -> 0x0114 }
        r2 = r11.zzal();	 Catch:{ MalformedURLException -> 0x0114 }
        r1 = r1.zzci(r2);	 Catch:{ MalformedURLException -> 0x0114 }
        r2 = 0;	 Catch:{ MalformedURLException -> 0x0114 }
        r3 = r10.zzls();	 Catch:{ MalformedURLException -> 0x0114 }
        r5 = r11.zzal();	 Catch:{ MalformedURLException -> 0x0114 }
        r3 = r3.zzcj(r5);	 Catch:{ MalformedURLException -> 0x0114 }
        if (r1 == 0) goto L_0x00e8;	 Catch:{ MalformedURLException -> 0x0114 }
    L_0x00d6:
        r1 = android.text.TextUtils.isEmpty(r3);	 Catch:{ MalformedURLException -> 0x0114 }
        if (r1 != 0) goto L_0x00e8;	 Catch:{ MalformedURLException -> 0x0114 }
    L_0x00dc:
        r1 = new androidx.collection.ArrayMap;	 Catch:{ MalformedURLException -> 0x0114 }
        r1.<init>();	 Catch:{ MalformedURLException -> 0x0114 }
        r2 = "If-Modified-Since";	 Catch:{ MalformedURLException -> 0x0114 }
        r1.put(r2, r3);	 Catch:{ MalformedURLException -> 0x0114 }
        r6 = r1;	 Catch:{ MalformedURLException -> 0x0114 }
        goto L_0x00e9;	 Catch:{ MalformedURLException -> 0x0114 }
    L_0x00e8:
        r6 = r2;	 Catch:{ MalformedURLException -> 0x0114 }
    L_0x00e9:
        r1 = 1;	 Catch:{ MalformedURLException -> 0x0114 }
        r10.zzaui = r1;	 Catch:{ MalformedURLException -> 0x0114 }
        r2 = r10.zzlt();	 Catch:{ MalformedURLException -> 0x0114 }
        r3 = r11.zzal();	 Catch:{ MalformedURLException -> 0x0114 }
        r7 = new com.google.android.gms.measurement.internal.zzfn;	 Catch:{ MalformedURLException -> 0x0114 }
        r7.<init>(r10);	 Catch:{ MalformedURLException -> 0x0114 }
        r2.zzaf();	 Catch:{ MalformedURLException -> 0x0114 }
        r2.zzcl();	 Catch:{ MalformedURLException -> 0x0114 }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r4);	 Catch:{ MalformedURLException -> 0x0114 }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r7);	 Catch:{ MalformedURLException -> 0x0114 }
        r8 = r2.zzgs();	 Catch:{ MalformedURLException -> 0x0114 }
        r9 = new com.google.android.gms.measurement.internal.zzay;	 Catch:{ MalformedURLException -> 0x0114 }
        r5 = 0;	 Catch:{ MalformedURLException -> 0x0114 }
        r1 = r9;	 Catch:{ MalformedURLException -> 0x0114 }
        r1.<init>(r2, r3, r4, r5, r6, r7);	 Catch:{ MalformedURLException -> 0x0114 }
        r8.zzd(r9);	 Catch:{ MalformedURLException -> 0x0114 }
        return;
    L_0x0114:
        r1 = r10.zzadp;
        r1 = r1.zzgt();
        r1 = r1.zzjg();
        r2 = "Failed to parse config URL. Not fetching. appId";
        r11 = r11.zzal();
        r11 = com.google.android.gms.measurement.internal.zzaq.zzby(r11);
        r1.zze(r2, r11, r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfk.zzb(com.google.android.gms.measurement.internal.zzg):void");
    }

    @WorkerThread
    @VisibleForTesting
    final void zzb(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        zzaf();
        zzlx();
        Preconditions.checkNotEmpty(str);
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzaui = false;
                zzmc();
            }
        }
        this.zzadp.zzgt().zzjo().zzg("onConfigFetched. Response size", Integer.valueOf(bArr.length));
        zzjt().beginTransaction();
        zzg zzbo = zzjt().zzbo(str);
        Object obj = 1;
        Object obj2 = ((i == Callback.DEFAULT_DRAG_ANIMATION_DURATION || i == 204 || i == 304) && th == null) ? 1 : null;
        if (zzbo == null) {
            this.zzadp.zzgt().zzjj().zzg("App does not exist in onConfigFetched. appId", zzaq.zzby(str));
        } else {
            if (obj2 == null) {
                if (i != 404) {
                    zzbo.zzz(this.zzadp.zzbx().currentTimeMillis());
                    zzjt().zza(zzbo);
                    this.zzadp.zzgt().zzjo().zze("Fetching config failed. code, error", Integer.valueOf(i), th);
                    zzls().zzck(str);
                    this.zzadp.zzgu().zzanm.set(this.zzadp.zzbx().currentTimeMillis());
                    if (i != 503) {
                        if (i != 429) {
                            obj = null;
                        }
                    }
                    if (obj != null) {
                        this.zzadp.zzgu().zzann.set(this.zzadp.zzbx().currentTimeMillis());
                    }
                    zzmb();
                }
            }
            map = map != null ? (List) map.get(HttpRequest.HEADER_LAST_MODIFIED) : null;
            map = (map == null || map.size() <= 0) ? null : (String) map.get(0);
            if (i != 404) {
                if (i != 304) {
                    if (zzls().zza(str, bArr, map) == null) {
                        zzjt().endTransaction();
                        this.zzaui = false;
                        zzmc();
                        return;
                    }
                    zzbo.zzy(this.zzadp.zzbx().currentTimeMillis());
                    zzjt().zza(zzbo);
                    if (i != 404) {
                        this.zzadp.zzgt().zzjl().zzg("Config not found. Using empty config. appId", str);
                    } else {
                        this.zzadp.zzgt().zzjo().zze("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                    }
                    if (zzlt().zzfb() != null || zzma() == null) {
                        zzmb();
                    } else {
                        zzlz();
                    }
                }
            }
            if (zzls().zzci(str) == null && zzls().zza(str, null, null) == null) {
                zzjt().endTransaction();
                this.zzaui = false;
                zzmc();
                return;
            }
            zzbo.zzy(this.zzadp.zzbx().currentTimeMillis());
            zzjt().zza(zzbo);
            if (i != 404) {
                this.zzadp.zzgt().zzjo().zze("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
            } else {
                this.zzadp.zzgt().zzjl().zzg("Config not found. Using empty config. appId", str);
            }
            if (zzlt().zzfb() != null) {
            }
            zzmb();
        }
        zzjt().setTransactionSuccessful();
        zzjt().endTransaction();
        this.zzaui = false;
        zzmc();
    }

    @WorkerThread
    private final void zzmb() {
        zzfk com_google_android_gms_measurement_internal_zzfk = this;
        zzaf();
        zzlx();
        if (zzmf() || com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zza(zzag.zzalp)) {
            long abs;
            if (com_google_android_gms_measurement_internal_zzfk.zzaue > 0) {
                abs = 3600000 - Math.abs(com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().elapsedRealtime() - com_google_android_gms_measurement_internal_zzfk.zzaue);
                if (abs > 0) {
                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzg("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(abs));
                    zzlu().unregister();
                    zzlv().cancel();
                    return;
                }
                com_google_android_gms_measurement_internal_zzfk.zzaue = 0;
            }
            if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzkv()) {
                if (zzma()) {
                    Object obj;
                    CharSequence zzid;
                    long max;
                    long j;
                    long j2;
                    long j3;
                    long j4;
                    long abs2;
                    long j5;
                    long j6;
                    int i;
                    long currentTimeMillis = com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis();
                    abs = Math.max(0, ((Long) zzag.zzakg.get()).longValue());
                    if (!zzjt().zzin()) {
                        if (!zzjt().zzii()) {
                            obj = null;
                            if (obj == null) {
                                zzid = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzid();
                                if (!TextUtils.isEmpty(zzid) || ".none.".equals(zzid)) {
                                    max = Math.max(0, ((Long) zzag.zzaka.get()).longValue());
                                } else {
                                    max = Math.max(0, ((Long) zzag.zzakb.get()).longValue());
                                }
                            } else {
                                max = Math.max(0, ((Long) zzag.zzajz.get()).longValue());
                            }
                            j = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzanl.get();
                            j2 = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzanm.get();
                            j3 = max;
                            j4 = abs;
                            abs = Math.max(zzjt().zzik(), zzjt().zzil());
                            if (abs != 0) {
                                abs = currentTimeMillis - Math.abs(abs - currentTimeMillis);
                                abs2 = currentTimeMillis - Math.abs(j - currentTimeMillis);
                                currentTimeMillis -= Math.abs(j2 - currentTimeMillis);
                                abs2 = Math.max(abs2, currentTimeMillis);
                                j5 = abs + j4;
                                if (obj != null && abs2 > 0) {
                                    j5 = Math.min(abs, abs2) + j3;
                                }
                                j6 = j3;
                                abs2 = zzjr().zzb(abs2, j6) ? abs2 + j6 : j5;
                                if (currentTimeMillis != 0 && currentTimeMillis >= abs) {
                                    for (i = 0; i < Math.min(20, Math.max(0, ((Integer) zzag.zzaki.get()).intValue())); i++) {
                                        abs2 += Math.max(0, ((Long) zzag.zzakh.get()).longValue()) * (1 << i);
                                        if (abs2 > currentTimeMillis) {
                                            break;
                                        }
                                    }
                                }
                                if (abs2 != 0) {
                                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzca("Next upload time is 0");
                                    zzlu().unregister();
                                    zzlv().cancel();
                                    return;
                                } else if (zzlt().zzfb()) {
                                    currentTimeMillis = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzann.get();
                                    abs = Math.max(0, ((Long) zzag.zzajx.get()).longValue());
                                    if (!zzjr().zzb(currentTimeMillis, abs)) {
                                        abs2 = Math.max(abs2, currentTimeMillis + abs);
                                    }
                                    zzlu().unregister();
                                    abs2 -= com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis();
                                    if (abs2 <= 0) {
                                        abs2 = Math.max(0, ((Long) zzag.zzakc.get()).longValue());
                                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzanl.set(com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis());
                                    }
                                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzg("Upload scheduled in approximately ms", Long.valueOf(abs2));
                                    zzlv().zzh(abs2);
                                    return;
                                } else {
                                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzca("No network");
                                    zzlu().zzey();
                                    zzlv().cancel();
                                    return;
                                }
                            }
                            abs2 = 0;
                            if (abs2 != 0) {
                                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzca("Next upload time is 0");
                                zzlu().unregister();
                                zzlv().cancel();
                                return;
                            } else if (zzlt().zzfb()) {
                                currentTimeMillis = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzann.get();
                                abs = Math.max(0, ((Long) zzag.zzajx.get()).longValue());
                                if (zzjr().zzb(currentTimeMillis, abs)) {
                                    abs2 = Math.max(abs2, currentTimeMillis + abs);
                                }
                                zzlu().unregister();
                                abs2 -= com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis();
                                if (abs2 <= 0) {
                                    abs2 = Math.max(0, ((Long) zzag.zzakc.get()).longValue());
                                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzanl.set(com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis());
                                }
                                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzg("Upload scheduled in approximately ms", Long.valueOf(abs2));
                                zzlv().zzh(abs2);
                                return;
                            } else {
                                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzca("No network");
                                zzlu().zzey();
                                zzlv().cancel();
                                return;
                            }
                        }
                    }
                    obj = 1;
                    if (obj == null) {
                        max = Math.max(0, ((Long) zzag.zzajz.get()).longValue());
                    } else {
                        zzid = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzid();
                        if (TextUtils.isEmpty(zzid)) {
                        }
                        max = Math.max(0, ((Long) zzag.zzaka.get()).longValue());
                    }
                    j = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzanl.get();
                    j2 = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzanm.get();
                    j3 = max;
                    j4 = abs;
                    abs = Math.max(zzjt().zzik(), zzjt().zzil());
                    if (abs != 0) {
                        abs = currentTimeMillis - Math.abs(abs - currentTimeMillis);
                        abs2 = currentTimeMillis - Math.abs(j - currentTimeMillis);
                        currentTimeMillis -= Math.abs(j2 - currentTimeMillis);
                        abs2 = Math.max(abs2, currentTimeMillis);
                        j5 = abs + j4;
                        j5 = Math.min(abs, abs2) + j3;
                        j6 = j3;
                        if (zzjr().zzb(abs2, j6)) {
                        }
                        for (i = 0; i < Math.min(20, Math.max(0, ((Integer) zzag.zzaki.get()).intValue())); i++) {
                            abs2 += Math.max(0, ((Long) zzag.zzakh.get()).longValue()) * (1 << i);
                            if (abs2 > currentTimeMillis) {
                                break;
                            }
                        }
                    }
                    abs2 = 0;
                    if (abs2 != 0) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzca("Next upload time is 0");
                        zzlu().unregister();
                        zzlv().cancel();
                        return;
                    } else if (zzlt().zzfb()) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzca("No network");
                        zzlu().zzey();
                        zzlv().cancel();
                        return;
                    } else {
                        currentTimeMillis = com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzann.get();
                        abs = Math.max(0, ((Long) zzag.zzajx.get()).longValue());
                        if (zzjr().zzb(currentTimeMillis, abs)) {
                            abs2 = Math.max(abs2, currentTimeMillis + abs);
                        }
                        zzlu().unregister();
                        abs2 -= com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis();
                        if (abs2 <= 0) {
                            abs2 = Math.max(0, ((Long) zzag.zzakc.get()).longValue());
                            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgu().zzanl.set(com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis());
                        }
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzg("Upload scheduled in approximately ms", Long.valueOf(abs2));
                        zzlv().zzh(abs2);
                        return;
                    }
                }
            }
            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjo().zzca("Nothing to upload or uploading impossible");
            zzlu().unregister();
            zzlv().cancel();
        }
    }

    @WorkerThread
    final void zzg(Runnable runnable) {
        zzaf();
        if (this.zzauf == null) {
            this.zzauf = new ArrayList();
        }
        this.zzauf.add(runnable);
    }

    @WorkerThread
    private final void zzmc() {
        zzaf();
        if (!(this.zzaui || this.zzauj)) {
            if (!this.zzauk) {
                this.zzadp.zzgt().zzjo().zzca("Stopping uploading service(s)");
                if (this.zzauf != null) {
                    for (Runnable run : this.zzauf) {
                        run.run();
                    }
                    this.zzauf.clear();
                    return;
                }
                return;
            }
        }
        this.zzadp.zzgt().zzjo().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzaui), Boolean.valueOf(this.zzauj), Boolean.valueOf(this.zzauk));
    }

    @androidx.annotation.WorkerThread
    private final java.lang.Boolean zzc(com.google.android.gms.measurement.internal.zzg r9) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = this;
        r0 = r9.zzhf();	 Catch:{ NameNotFoundException -> 0x005d }
        r2 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;	 Catch:{ NameNotFoundException -> 0x005d }
        r4 = 1;	 Catch:{ NameNotFoundException -> 0x005d }
        r5 = 0;	 Catch:{ NameNotFoundException -> 0x005d }
        r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));	 Catch:{ NameNotFoundException -> 0x005d }
        if (r6 == 0) goto L_0x002f;	 Catch:{ NameNotFoundException -> 0x005d }
    L_0x000d:
        r0 = r8.zzadp;	 Catch:{ NameNotFoundException -> 0x005d }
        r0 = r0.getContext();	 Catch:{ NameNotFoundException -> 0x005d }
        r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0);	 Catch:{ NameNotFoundException -> 0x005d }
        r1 = r9.zzal();	 Catch:{ NameNotFoundException -> 0x005d }
        r0 = r0.getPackageInfo(r1, r5);	 Catch:{ NameNotFoundException -> 0x005d }
        r0 = r0.versionCode;	 Catch:{ NameNotFoundException -> 0x005d }
        r1 = r9.zzhf();	 Catch:{ NameNotFoundException -> 0x005d }
        r6 = (long) r0;	 Catch:{ NameNotFoundException -> 0x005d }
        r9 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));	 Catch:{ NameNotFoundException -> 0x005d }
        if (r9 != 0) goto L_0x0058;	 Catch:{ NameNotFoundException -> 0x005d }
    L_0x002a:
        r9 = java.lang.Boolean.valueOf(r4);	 Catch:{ NameNotFoundException -> 0x005d }
        return r9;	 Catch:{ NameNotFoundException -> 0x005d }
    L_0x002f:
        r0 = r8.zzadp;	 Catch:{ NameNotFoundException -> 0x005d }
        r0 = r0.getContext();	 Catch:{ NameNotFoundException -> 0x005d }
        r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0);	 Catch:{ NameNotFoundException -> 0x005d }
        r1 = r9.zzal();	 Catch:{ NameNotFoundException -> 0x005d }
        r0 = r0.getPackageInfo(r1, r5);	 Catch:{ NameNotFoundException -> 0x005d }
        r0 = r0.versionName;	 Catch:{ NameNotFoundException -> 0x005d }
        r1 = r9.zzak();	 Catch:{ NameNotFoundException -> 0x005d }
        if (r1 == 0) goto L_0x0058;	 Catch:{ NameNotFoundException -> 0x005d }
    L_0x0049:
        r9 = r9.zzak();	 Catch:{ NameNotFoundException -> 0x005d }
        r9 = r9.equals(r0);	 Catch:{ NameNotFoundException -> 0x005d }
        if (r9 == 0) goto L_0x0058;	 Catch:{ NameNotFoundException -> 0x005d }
    L_0x0053:
        r9 = java.lang.Boolean.valueOf(r4);	 Catch:{ NameNotFoundException -> 0x005d }
        return r9;
    L_0x0058:
        r9 = java.lang.Boolean.valueOf(r5);
        return r9;
    L_0x005d:
        r9 = 0;
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfk.zzc(com.google.android.gms.measurement.internal.zzg):java.lang.Boolean");
    }

    @WorkerThread
    @VisibleForTesting
    private final boolean zzmd() {
        zzaf();
        try {
            this.zzaum = new RandomAccessFile(new File(this.zzadp.getContext().getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zzaul = this.zzaum.tryLock();
            if (this.zzaul != null) {
                this.zzadp.zzgt().zzjo().zzca("Storage concurrent access okay");
                return true;
            }
            this.zzadp.zzgt().zzjg().zzca("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            this.zzadp.zzgt().zzjg().zzg("Failed to acquire storage lock", e);
        } catch (IOException e2) {
            this.zzadp.zzgt().zzjg().zzg("Failed to access storage lock file", e2);
        }
    }

    @WorkerThread
    @VisibleForTesting
    private final int zza(FileChannel fileChannel) {
        zzaf();
        if (fileChannel != null) {
            if (fileChannel.isOpen()) {
                ByteBuffer allocate = ByteBuffer.allocate(4);
                try {
                    fileChannel.position(0);
                    fileChannel = fileChannel.read(allocate);
                    if (fileChannel != 4) {
                        if (fileChannel != -1) {
                            this.zzadp.zzgt().zzjj().zzg("Unexpected data length. Bytes read", Integer.valueOf(fileChannel));
                        }
                        return 0;
                    }
                    allocate.flip();
                    fileChannel = allocate.getInt();
                    return fileChannel;
                } catch (FileChannel fileChannel2) {
                    this.zzadp.zzgt().zzjg().zzg("Failed to read from channel", fileChannel2);
                    fileChannel2 = null;
                }
            }
        }
        this.zzadp.zzgt().zzjg().zzca("Bad channel to read from");
        return 0;
    }

    @WorkerThread
    @VisibleForTesting
    private final boolean zza(int i, FileChannel fileChannel) {
        zzaf();
        if (fileChannel != null) {
            if (fileChannel.isOpen()) {
                ByteBuffer allocate = ByteBuffer.allocate(4);
                allocate.putInt(i);
                allocate.flip();
                try {
                    fileChannel.truncate(0);
                    fileChannel.write(allocate);
                    fileChannel.force(true);
                    if (fileChannel.size() != 4) {
                        this.zzadp.zzgt().zzjg().zzg("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
                    }
                    return true;
                } catch (int i2) {
                    this.zzadp.zzgt().zzjg().zzg("Failed to write to channel", i2);
                    return false;
                }
            }
        }
        this.zzadp.zzgt().zzjg().zzca("Bad channel to read from");
        return false;
    }

    @WorkerThread
    final void zzme() {
        zzaf();
        zzlx();
        if (!this.zzaud) {
            this.zzaud = true;
            zzaf();
            zzlx();
            if ((this.zzadp.zzgv().zza(zzag.zzalp) || zzmf()) && zzmd()) {
                int zza = zza(this.zzaum);
                int zzjd = this.zzadp.zzgk().zzjd();
                zzaf();
                if (zza > zzjd) {
                    this.zzadp.zzgt().zzjg().zze("Panic: can't downgrade version. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzjd));
                } else if (zza < zzjd) {
                    if (zza(zzjd, this.zzaum)) {
                        this.zzadp.zzgt().zzjo().zze("Storage version upgraded. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzjd));
                    } else {
                        this.zzadp.zzgt().zzjg().zze("Storage version upgrade failed. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzjd));
                    }
                }
            }
        }
        if (!this.zzauc && !this.zzadp.zzgv().zza(zzag.zzalp)) {
            this.zzadp.zzgt().zzjm().zzca("This instance being marked as an uploader");
            this.zzauc = true;
            zzmb();
        }
    }

    @WorkerThread
    private final boolean zzmf() {
        zzaf();
        zzlx();
        return this.zzauc;
    }

    @WorkerThread
    @VisibleForTesting
    final void zzd(zzi com_google_android_gms_measurement_internal_zzi) {
        if (this.zzaun != null) {
            this.zzauo = new ArrayList();
            this.zzauo.addAll(this.zzaun);
        }
        zzcp zzjt = zzjt();
        String str = com_google_android_gms_measurement_internal_zzi.packageName;
        Preconditions.checkNotEmpty(str);
        zzjt.zzaf();
        zzjt.zzcl();
        try {
            SQLiteDatabase writableDatabase = zzjt.getWritableDatabase();
            String[] strArr = new String[]{str};
            int delete = ((((((((writableDatabase.delete("apps", "app_id=?", strArr) + 0) + writableDatabase.delete("events", "app_id=?", strArr)) + writableDatabase.delete("user_attributes", "app_id=?", strArr)) + writableDatabase.delete("conditional_properties", "app_id=?", strArr)) + writableDatabase.delete("raw_events", "app_id=?", strArr)) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr)) + writableDatabase.delete("queue", "app_id=?", strArr)) + writableDatabase.delete("audience_filter_values", "app_id=?", strArr)) + writableDatabase.delete("main_event_params", "app_id=?", strArr);
            if (delete > 0) {
                zzjt.zzgt().zzjo().zze("Reset analytics data. app, records", str, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzjt.zzgt().zzjg().zze("Error resetting analytics data. appId, error", zzaq.zzby(str), e);
        }
        zzi zza = zza(this.zzadp.getContext(), com_google_android_gms_measurement_internal_zzi.packageName, com_google_android_gms_measurement_internal_zzi.zzafx, com_google_android_gms_measurement_internal_zzi.zzagg, com_google_android_gms_measurement_internal_zzi.zzagi, com_google_android_gms_measurement_internal_zzi.zzagj, com_google_android_gms_measurement_internal_zzi.zzaha, com_google_android_gms_measurement_internal_zzi.zzagk);
        if (!this.zzadp.zzgv().zzbc(com_google_android_gms_measurement_internal_zzi.packageName) || com_google_android_gms_measurement_internal_zzi.zzagg != null) {
            zzf(zza);
        }
    }

    private final com.google.android.gms.measurement.internal.zzi zza(android.content.Context r27, java.lang.String r28, java.lang.String r29, boolean r30, boolean r31, boolean r32, long r33, java.lang.String r35) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r26 = this;
        r0 = r26;
        r2 = r28;
        r1 = "Unknown";
        r3 = "Unknown";
        r4 = "Unknown";
        r5 = r27.getPackageManager();
        r6 = 0;
        if (r5 != 0) goto L_0x0021;
    L_0x0011:
        r1 = r0.zzadp;
        r1 = r1.zzgt();
        r1 = r1.zzjg();
        r2 = "PackageManager is null, can not log app install information";
        r1.zzca(r2);
        return r6;
    L_0x0021:
        r5 = r5.getInstallerPackageName(r2);	 Catch:{ IllegalArgumentException -> 0x0027 }
        r1 = r5;
        goto L_0x003a;
    L_0x0027:
        r5 = r0.zzadp;
        r5 = r5.zzgt();
        r5 = r5.zzjg();
        r7 = "Error retrieving installer package name. appId";
        r8 = com.google.android.gms.measurement.internal.zzaq.zzby(r28);
        r5.zzg(r7, r8);
    L_0x003a:
        if (r1 != 0) goto L_0x0040;
    L_0x003c:
        r1 = "manual_install";
    L_0x003e:
        r7 = r1;
        goto L_0x004b;
    L_0x0040:
        r5 = "com.android.vending";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x003e;
    L_0x0048:
        r1 = "";
        goto L_0x003e;
    L_0x004b:
        r1 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r27);	 Catch:{ NameNotFoundException -> 0x00c2 }
        r5 = 0;	 Catch:{ NameNotFoundException -> 0x00c2 }
        r1 = r1.getPackageInfo(r2, r5);	 Catch:{ NameNotFoundException -> 0x00c2 }
        if (r1 == 0) goto L_0x0070;	 Catch:{ NameNotFoundException -> 0x00c2 }
    L_0x0056:
        r3 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r27);	 Catch:{ NameNotFoundException -> 0x00c2 }
        r3 = r3.getApplicationLabel(r2);	 Catch:{ NameNotFoundException -> 0x00c2 }
        r5 = android.text.TextUtils.isEmpty(r3);	 Catch:{ NameNotFoundException -> 0x00c2 }
        if (r5 != 0) goto L_0x0069;	 Catch:{ NameNotFoundException -> 0x00c2 }
    L_0x0064:
        r3 = r3.toString();	 Catch:{ NameNotFoundException -> 0x00c2 }
        r4 = r3;	 Catch:{ NameNotFoundException -> 0x00c2 }
    L_0x0069:
        r3 = r1.versionName;	 Catch:{ NameNotFoundException -> 0x00c2 }
        r1 = r1.versionCode;	 Catch:{ NameNotFoundException -> 0x00c2 }
        r4 = r3;
        r3 = r1;
        goto L_0x0075;
    L_0x0070:
        r1 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r4 = r3;
        r3 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
    L_0x0075:
        r16 = 0;
        r1 = r0.zzadp;
        r1.zzgw();
        r5 = 0;
        r1 = r0.zzadp;
        r1 = r1.zzgv();
        r1 = r1.zzbe(r2);
        if (r1 == 0) goto L_0x008d;
    L_0x008a:
        r18 = r33;
        goto L_0x008f;
    L_0x008d:
        r18 = r5;
    L_0x008f:
        r25 = new com.google.android.gms.measurement.internal.zzi;
        r1 = r25;
        r5 = (long) r3;
        r3 = r0.zzadp;
        r3 = r3.zzgv();
        r8 = r3.zzhh();
        r3 = r0.zzadp;
        r3 = r3.zzgr();
        r10 = r27;
        r10 = r3.zzd(r10, r2);
        r12 = 0;
        r14 = 0;
        r15 = "";
        r20 = 0;
        r23 = 0;
        r2 = r28;
        r3 = r29;
        r13 = r30;
        r21 = r31;
        r22 = r32;
        r24 = r35;
        r1.<init>(r2, r3, r4, r5, r7, r8, r10, r12, r13, r14, r15, r16, r18, r20, r21, r22, r23, r24);
        return r25;
    L_0x00c2:
        r1 = r0.zzadp;
        r1 = r1.zzgt();
        r1 = r1.zzjg();
        r3 = "Error retrieving newly installed package info. appId, appName";
        r2 = com.google.android.gms.measurement.internal.zzaq.zzby(r28);
        r1.zze(r3, r2, r4);
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfk.zza(android.content.Context, java.lang.String, java.lang.String, boolean, boolean, boolean, long, java.lang.String):com.google.android.gms.measurement.internal.zzi");
    }

    @WorkerThread
    final void zzb(zzfr com_google_android_gms_measurement_internal_zzfr, zzi com_google_android_gms_measurement_internal_zzi) {
        zzaf();
        zzlx();
        if (!TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzafx) || !TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzagk)) {
            if (com_google_android_gms_measurement_internal_zzi.zzagg) {
                int zzcx = this.zzadp.zzgr().zzcx(com_google_android_gms_measurement_internal_zzfr.name);
                if (zzcx != 0) {
                    this.zzadp.zzgr();
                    this.zzadp.zzgr().zza(com_google_android_gms_measurement_internal_zzi.packageName, zzcx, "_ev", zzfu.zza(com_google_android_gms_measurement_internal_zzfr.name, 24, true), com_google_android_gms_measurement_internal_zzfr.name != null ? com_google_android_gms_measurement_internal_zzfr.name.length() : 0);
                    return;
                }
                int zzi = this.zzadp.zzgr().zzi(com_google_android_gms_measurement_internal_zzfr.name, com_google_android_gms_measurement_internal_zzfr.getValue());
                if (zzi != 0) {
                    this.zzadp.zzgr();
                    String zza = zzfu.zza(com_google_android_gms_measurement_internal_zzfr.name, 24, true);
                    com_google_android_gms_measurement_internal_zzfr = com_google_android_gms_measurement_internal_zzfr.getValue();
                    int length = (com_google_android_gms_measurement_internal_zzfr == null || !((com_google_android_gms_measurement_internal_zzfr instanceof String) || (com_google_android_gms_measurement_internal_zzfr instanceof CharSequence))) ? 0 : String.valueOf(com_google_android_gms_measurement_internal_zzfr).length();
                    this.zzadp.zzgr().zza(com_google_android_gms_measurement_internal_zzi.packageName, zzi, "_ev", zza, length);
                    return;
                }
                Object zzj = this.zzadp.zzgr().zzj(com_google_android_gms_measurement_internal_zzfr.name, com_google_android_gms_measurement_internal_zzfr.getValue());
                if (zzj != null) {
                    if (this.zzadp.zzgv().zzbj(com_google_android_gms_measurement_internal_zzi.packageName) && "_sno".equals(com_google_android_gms_measurement_internal_zzfr.name)) {
                        long j = 0;
                        zzft zzi2 = zzjt().zzi(com_google_android_gms_measurement_internal_zzi.packageName, "_sno");
                        if (zzi2 == null || !(zzi2.value instanceof Long)) {
                            zzaa zzg = zzjt().zzg(com_google_android_gms_measurement_internal_zzi.packageName, "_s");
                            if (zzg != null) {
                                j = zzg.zzaih;
                                this.zzadp.zzgt().zzjo().zzg("Backfill the session number. Last used session number", Long.valueOf(j));
                            }
                        } else {
                            j = ((Long) zzi2.value).longValue();
                        }
                        zzj = Long.valueOf(j + 1);
                    }
                    zzft com_google_android_gms_measurement_internal_zzft = new zzft(com_google_android_gms_measurement_internal_zzi.packageName, com_google_android_gms_measurement_internal_zzfr.origin, com_google_android_gms_measurement_internal_zzfr.name, com_google_android_gms_measurement_internal_zzfr.zzaux, zzj);
                    this.zzadp.zzgt().zzjn().zze("Setting user property", this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzft.name), zzj);
                    zzjt().beginTransaction();
                    try {
                        zzg(com_google_android_gms_measurement_internal_zzi);
                        com_google_android_gms_measurement_internal_zzfr = zzjt().zza(com_google_android_gms_measurement_internal_zzft);
                        zzjt().setTransactionSuccessful();
                        if (com_google_android_gms_measurement_internal_zzfr != null) {
                            this.zzadp.zzgt().zzjn().zze("User property set", this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzft.name), com_google_android_gms_measurement_internal_zzft.value);
                        } else {
                            this.zzadp.zzgt().zzjg().zze("Too many unique user properties are set. Ignoring user property", this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzft.name), com_google_android_gms_measurement_internal_zzft.value);
                            this.zzadp.zzgr().zza(com_google_android_gms_measurement_internal_zzi.packageName, 9, null, null, 0);
                        }
                        zzjt().endTransaction();
                        return;
                    } catch (Throwable th) {
                        zzjt().endTransaction();
                    }
                } else {
                    return;
                }
            }
            zzg(com_google_android_gms_measurement_internal_zzi);
        }
    }

    @WorkerThread
    final void zzc(zzfr com_google_android_gms_measurement_internal_zzfr, zzi com_google_android_gms_measurement_internal_zzi) {
        zzaf();
        zzlx();
        if (!TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzafx) || !TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzagk)) {
            if (com_google_android_gms_measurement_internal_zzi.zzagg) {
                this.zzadp.zzgt().zzjn().zzg("Removing user property", this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzfr.name));
                zzjt().beginTransaction();
                try {
                    zzg(com_google_android_gms_measurement_internal_zzi);
                    zzjt().zzh(com_google_android_gms_measurement_internal_zzi.packageName, com_google_android_gms_measurement_internal_zzfr.name);
                    zzjt().setTransactionSuccessful();
                    this.zzadp.zzgt().zzjn().zzg("User property removed", this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzfr.name));
                } finally {
                    zzjt().endTransaction();
                }
            } else {
                zzg(com_google_android_gms_measurement_internal_zzi);
            }
        }
    }

    final void zzb(zzfj com_google_android_gms_measurement_internal_zzfj) {
        this.zzaug++;
    }

    final void zzmg() {
        this.zzauh++;
    }

    final zzbu zzmh() {
        return this.zzadp;
    }

    @WorkerThread
    final void zzf(zzi com_google_android_gms_measurement_internal_zzi) {
        zzfk com_google_android_gms_measurement_internal_zzfk = this;
        zzi com_google_android_gms_measurement_internal_zzi2 = com_google_android_gms_measurement_internal_zzi;
        zzaf();
        zzlx();
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzi);
        Preconditions.checkNotEmpty(com_google_android_gms_measurement_internal_zzi2.packageName);
        if (!TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi2.zzafx) || !TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi2.zzagk)) {
            zzg zzbo = zzjt().zzbo(com_google_android_gms_measurement_internal_zzi2.packageName);
            if (!(zzbo == null || !TextUtils.isEmpty(zzbo.getGmpAppId()) || TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi2.zzafx))) {
                zzbo.zzy(0);
                zzjt().zza(zzbo);
                zzls().zzcl(com_google_android_gms_measurement_internal_zzi2.packageName);
            }
            if (com_google_android_gms_measurement_internal_zzi2.zzagg) {
                int i;
                long j = com_google_android_gms_measurement_internal_zzi2.zzaha;
                if (j == 0) {
                    j = com_google_android_gms_measurement_internal_zzfk.zzadp.zzbx().currentTimeMillis();
                }
                int i2 = com_google_android_gms_measurement_internal_zzi2.zzahb;
                if (i2 == 0 || i2 == 1) {
                    i = i2;
                } else {
                    com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjj().zze("Incorrect app type, assuming installed app. appId, appType", zzaq.zzby(com_google_android_gms_measurement_internal_zzi2.packageName), Integer.valueOf(i2));
                    i = 0;
                }
                zzjt().beginTransaction();
                zzcp zzjt;
                String zzal;
                try {
                    zzbo = zzjt().zzbo(com_google_android_gms_measurement_internal_zzi2.packageName);
                    if (zzbo != null) {
                        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgr();
                        if (zzfu.zza(com_google_android_gms_measurement_internal_zzi2.zzafx, zzbo.getGmpAppId(), com_google_android_gms_measurement_internal_zzi2.zzagk, zzbo.zzhb())) {
                            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjj().zzg("New GMP App Id passed in. Removing cached database data. appId", zzaq.zzby(zzbo.zzal()));
                            zzjt = zzjt();
                            zzal = zzbo.zzal();
                            zzjt.zzcl();
                            zzjt.zzaf();
                            Preconditions.checkNotEmpty(zzal);
                            SQLiteDatabase writableDatabase = zzjt.getWritableDatabase();
                            String[] strArr = new String[]{zzal};
                            int delete = ((((((((writableDatabase.delete("events", "app_id=?", strArr) + 0) + writableDatabase.delete("user_attributes", "app_id=?", strArr)) + writableDatabase.delete("conditional_properties", "app_id=?", strArr)) + writableDatabase.delete("apps", "app_id=?", strArr)) + writableDatabase.delete("raw_events", "app_id=?", strArr)) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr)) + writableDatabase.delete("event_filters", "app_id=?", strArr)) + writableDatabase.delete("property_filters", "app_id=?", strArr)) + writableDatabase.delete("audience_filter_values", "app_id=?", strArr);
                            if (delete > 0) {
                                zzjt.zzgt().zzjo().zze("Deleted application data. app, records", zzal, Integer.valueOf(delete));
                            }
                            zzbo = null;
                        }
                    }
                } catch (SQLiteException e) {
                    zzjt.zzgt().zzjg().zze("Error deleting application data. appId, error", zzaq.zzby(zzal), e);
                } catch (Throwable th) {
                    zzjt().endTransaction();
                }
                if (zzbo != null) {
                    Bundle bundle;
                    if (zzbo.zzhf() != -2147483648L) {
                        if (zzbo.zzhf() != com_google_android_gms_measurement_internal_zzi2.zzagd) {
                            bundle = new Bundle();
                            bundle.putString("_pv", zzbo.zzak());
                            zzc(new zzae("_au", new zzab(bundle), "auto", j), com_google_android_gms_measurement_internal_zzi2);
                        }
                    } else if (!(zzbo.zzak() == null || zzbo.zzak().equals(com_google_android_gms_measurement_internal_zzi2.zzts))) {
                        bundle = new Bundle();
                        bundle.putString("_pv", zzbo.zzak());
                        zzc(new zzae("_au", new zzab(bundle), "auto", j), com_google_android_gms_measurement_internal_zzi2);
                    }
                }
                zzg(com_google_android_gms_measurement_internal_zzi);
                zzaa zzg = i == 0 ? zzjt().zzg(com_google_android_gms_measurement_internal_zzi2.packageName, "_f") : i == 1 ? zzjt().zzg(com_google_android_gms_measurement_internal_zzi2.packageName, "_v") : null;
                if (zzg == null) {
                    long j2;
                    Bundle bundle2;
                    long j3 = ((j / 3600000) + 1) * 3600000;
                    if (i == 0) {
                        j2 = 1;
                        zzb(new zzfr("_fot", j, Long.valueOf(j3), "auto"), com_google_android_gms_measurement_internal_zzi2);
                        if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzbg(com_google_android_gms_measurement_internal_zzi2.zzafx)) {
                            zzaf();
                            com_google_android_gms_measurement_internal_zzfk.zzadp.zzkk().zzcg(com_google_android_gms_measurement_internal_zzi2.packageName);
                        }
                        zzaf();
                        zzlx();
                        Bundle bundle3 = new Bundle();
                        bundle3.putLong("_c", j2);
                        bundle3.putLong("_r", j2);
                        bundle3.putLong("_uwa", 0);
                        bundle3.putLong("_pfo", 0);
                        bundle3.putLong("_sys", 0);
                        bundle3.putLong("_sysu", 0);
                        if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzbm(com_google_android_gms_measurement_internal_zzi2.packageName)) {
                            bundle3.putLong("_et", j2);
                        }
                        if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzbc(com_google_android_gms_measurement_internal_zzi2.packageName) && com_google_android_gms_measurement_internal_zzi2.zzahc) {
                            bundle3.putLong("_dac", j2);
                        }
                        if (com_google_android_gms_measurement_internal_zzfk.zzadp.getContext().getPackageManager() == null) {
                            com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zzg("PackageManager is null, first open report might be inaccurate. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzi2.packageName));
                        } else {
                            PackageInfo packageInfo;
                            ApplicationInfo applicationInfo;
                            try {
                                packageInfo = Wrappers.packageManager(com_google_android_gms_measurement_internal_zzfk.zzadp.getContext()).getPackageInfo(com_google_android_gms_measurement_internal_zzi2.packageName, 0);
                            } catch (NameNotFoundException e2) {
                                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zze("Package info is null, first open report might be inaccurate. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzi2.packageName), e2);
                                packageInfo = null;
                            }
                            if (!(packageInfo == null || packageInfo.firstInstallTime == 0)) {
                                Object obj;
                                if (packageInfo.firstInstallTime != packageInfo.lastUpdateTime) {
                                    bundle3.putLong("_uwa", j2);
                                    obj = null;
                                } else {
                                    obj = 1;
                                }
                                zzfr com_google_android_gms_measurement_internal_zzfr = r7;
                                zzfr com_google_android_gms_measurement_internal_zzfr2 = new zzfr("_fi", j, Long.valueOf(obj != null ? j2 : 0), "auto");
                                zzb(com_google_android_gms_measurement_internal_zzfr, com_google_android_gms_measurement_internal_zzi2);
                            }
                            try {
                                applicationInfo = Wrappers.packageManager(com_google_android_gms_measurement_internal_zzfk.zzadp.getContext()).getApplicationInfo(com_google_android_gms_measurement_internal_zzi2.packageName, 0);
                            } catch (NameNotFoundException e22) {
                                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zze("Application info is null, first open report might be inaccurate. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzi2.packageName), e22);
                                applicationInfo = null;
                            }
                            if (applicationInfo != null) {
                                if ((applicationInfo.flags & 1) != 0) {
                                    bundle3.putLong("_sys", j2);
                                }
                                if ((applicationInfo.flags & 128) != 0) {
                                    bundle3.putLong("_sysu", j2);
                                }
                            }
                        }
                        zzcp zzjt2 = zzjt();
                        String str = com_google_android_gms_measurement_internal_zzi2.packageName;
                        Preconditions.checkNotEmpty(str);
                        zzjt2.zzaf();
                        zzjt2.zzcl();
                        long zzn = zzjt2.zzn(str, "first_open_count");
                        if (zzn >= 0) {
                            bundle3.putLong("_pfo", zzn);
                        }
                        zzc(new zzae("_f", new zzab(bundle3), "auto", j), com_google_android_gms_measurement_internal_zzi2);
                    } else {
                        j2 = 1;
                        if (i == 1) {
                            zzb(new zzfr("_fvt", j, Long.valueOf(j3), "auto"), com_google_android_gms_measurement_internal_zzi2);
                            zzaf();
                            zzlx();
                            bundle2 = new Bundle();
                            bundle2.putLong("_c", j2);
                            bundle2.putLong("_r", j2);
                            if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzbm(com_google_android_gms_measurement_internal_zzi2.packageName)) {
                                bundle2.putLong("_et", j2);
                            }
                            if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzbc(com_google_android_gms_measurement_internal_zzi2.packageName) && com_google_android_gms_measurement_internal_zzi2.zzahc) {
                                bundle2.putLong("_dac", j2);
                            }
                            zzc(new zzae("_v", new zzab(bundle2), "auto", j), com_google_android_gms_measurement_internal_zzi2);
                        }
                    }
                    if (!com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zze(com_google_android_gms_measurement_internal_zzi2.packageName, zzag.zzalm)) {
                        bundle2 = new Bundle();
                        bundle2.putLong("_et", j2);
                        if (com_google_android_gms_measurement_internal_zzfk.zzadp.zzgv().zzbm(com_google_android_gms_measurement_internal_zzi2.packageName)) {
                            bundle2.putLong("_fr", j2);
                        }
                        zzc(new zzae("_e", new zzab(bundle2), "auto", j), com_google_android_gms_measurement_internal_zzi2);
                    }
                } else if (com_google_android_gms_measurement_internal_zzi2.zzagz) {
                    zzc(new zzae("_cd", new zzab(new Bundle()), "auto", j), com_google_android_gms_measurement_internal_zzi2);
                }
                zzjt().setTransactionSuccessful();
                zzjt().endTransaction();
                return;
            }
            zzg(com_google_android_gms_measurement_internal_zzi);
        }
    }

    @WorkerThread
    private final zzi zzct(String str) {
        zzfk com_google_android_gms_measurement_internal_zzfk = this;
        String str2 = str;
        zzg zzbo = zzjt().zzbo(str2);
        if (zzbo != null) {
            if (!TextUtils.isEmpty(zzbo.zzak())) {
                Boolean zzc = zzc(zzbo);
                if (zzc == null || zzc.booleanValue()) {
                    zzg com_google_android_gms_measurement_internal_zzg = zzbo;
                    return new zzi(str, zzbo.getGmpAppId(), zzbo.zzak(), zzbo.zzhf(), zzbo.zzhg(), zzbo.zzhh(), zzbo.zzhi(), null, zzbo.isMeasurementEnabled(), false, zzbo.getFirebaseInstanceId(), com_google_android_gms_measurement_internal_zzg.zzhv(), 0, 0, com_google_android_gms_measurement_internal_zzg.zzhw(), com_google_android_gms_measurement_internal_zzg.zzhx(), false, com_google_android_gms_measurement_internal_zzg.zzhb());
                }
                com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjg().zzg("App version does not match; dropping. appId", zzaq.zzby(str));
                return null;
            }
        }
        com_google_android_gms_measurement_internal_zzfk.zzadp.zzgt().zzjn().zzg("No app data available; dropping", str2);
        return null;
    }

    @WorkerThread
    final void zze(zzm com_google_android_gms_measurement_internal_zzm) {
        zzi zzct = zzct(com_google_android_gms_measurement_internal_zzm.packageName);
        if (zzct != null) {
            zzb(com_google_android_gms_measurement_internal_zzm, zzct);
        }
    }

    @WorkerThread
    final void zzb(zzm com_google_android_gms_measurement_internal_zzm, zzi com_google_android_gms_measurement_internal_zzi) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm);
        Preconditions.checkNotEmpty(com_google_android_gms_measurement_internal_zzm.packageName);
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm.origin);
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm.zzahe);
        Preconditions.checkNotEmpty(com_google_android_gms_measurement_internal_zzm.zzahe.name);
        zzaf();
        zzlx();
        if (!TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzafx) || !TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzagk)) {
            if (com_google_android_gms_measurement_internal_zzi.zzagg) {
                zzm com_google_android_gms_measurement_internal_zzm2 = new zzm(com_google_android_gms_measurement_internal_zzm);
                com_google_android_gms_measurement_internal_zzm = null;
                com_google_android_gms_measurement_internal_zzm2.active = false;
                zzjt().beginTransaction();
                try {
                    zzm zzj = zzjt().zzj(com_google_android_gms_measurement_internal_zzm2.packageName, com_google_android_gms_measurement_internal_zzm2.zzahe.name);
                    if (!(zzj == null || zzj.origin.equals(com_google_android_gms_measurement_internal_zzm2.origin))) {
                        this.zzadp.zzgt().zzjj().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzm2.zzahe.name), com_google_android_gms_measurement_internal_zzm2.origin, zzj.origin);
                    }
                    if (zzj != null && zzj.active) {
                        com_google_android_gms_measurement_internal_zzm2.origin = zzj.origin;
                        com_google_android_gms_measurement_internal_zzm2.creationTimestamp = zzj.creationTimestamp;
                        com_google_android_gms_measurement_internal_zzm2.triggerTimeout = zzj.triggerTimeout;
                        com_google_android_gms_measurement_internal_zzm2.triggerEventName = zzj.triggerEventName;
                        com_google_android_gms_measurement_internal_zzm2.zzahg = zzj.zzahg;
                        com_google_android_gms_measurement_internal_zzm2.active = zzj.active;
                        com_google_android_gms_measurement_internal_zzm2.zzahe = new zzfr(com_google_android_gms_measurement_internal_zzm2.zzahe.name, zzj.zzahe.zzaux, com_google_android_gms_measurement_internal_zzm2.zzahe.getValue(), zzj.zzahe.origin);
                    } else if (TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzm2.triggerEventName)) {
                        com_google_android_gms_measurement_internal_zzm2.zzahe = new zzfr(com_google_android_gms_measurement_internal_zzm2.zzahe.name, com_google_android_gms_measurement_internal_zzm2.creationTimestamp, com_google_android_gms_measurement_internal_zzm2.zzahe.getValue(), com_google_android_gms_measurement_internal_zzm2.zzahe.origin);
                        com_google_android_gms_measurement_internal_zzm2.active = true;
                        com_google_android_gms_measurement_internal_zzm = true;
                    }
                    if (com_google_android_gms_measurement_internal_zzm2.active) {
                        zzfr com_google_android_gms_measurement_internal_zzfr = com_google_android_gms_measurement_internal_zzm2.zzahe;
                        zzft com_google_android_gms_measurement_internal_zzft = new zzft(com_google_android_gms_measurement_internal_zzm2.packageName, com_google_android_gms_measurement_internal_zzm2.origin, com_google_android_gms_measurement_internal_zzfr.name, com_google_android_gms_measurement_internal_zzfr.zzaux, com_google_android_gms_measurement_internal_zzfr.getValue());
                        if (zzjt().zza(com_google_android_gms_measurement_internal_zzft)) {
                            this.zzadp.zzgt().zzjn().zzd("User property updated immediately", com_google_android_gms_measurement_internal_zzm2.packageName, this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzft.name), com_google_android_gms_measurement_internal_zzft.value);
                        } else {
                            this.zzadp.zzgt().zzjg().zzd("(2)Too many active user properties, ignoring", zzaq.zzby(com_google_android_gms_measurement_internal_zzm2.packageName), this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzft.name), com_google_android_gms_measurement_internal_zzft.value);
                        }
                        if (!(com_google_android_gms_measurement_internal_zzm == null || com_google_android_gms_measurement_internal_zzm2.zzahg == null)) {
                            zzd(new zzae(com_google_android_gms_measurement_internal_zzm2.zzahg, com_google_android_gms_measurement_internal_zzm2.creationTimestamp), com_google_android_gms_measurement_internal_zzi);
                        }
                    }
                    if (zzjt().zza(com_google_android_gms_measurement_internal_zzm2) != null) {
                        this.zzadp.zzgt().zzjn().zzd("Conditional property added", com_google_android_gms_measurement_internal_zzm2.packageName, this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzm2.zzahe.name), com_google_android_gms_measurement_internal_zzm2.zzahe.getValue());
                    } else {
                        this.zzadp.zzgt().zzjg().zzd("Too many conditional properties, ignoring", zzaq.zzby(com_google_android_gms_measurement_internal_zzm2.packageName), this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzm2.zzahe.name), com_google_android_gms_measurement_internal_zzm2.zzahe.getValue());
                    }
                    zzjt().setTransactionSuccessful();
                } finally {
                    zzjt().endTransaction();
                }
            } else {
                zzg(com_google_android_gms_measurement_internal_zzi);
            }
        }
    }

    @WorkerThread
    final void zzf(zzm com_google_android_gms_measurement_internal_zzm) {
        zzi zzct = zzct(com_google_android_gms_measurement_internal_zzm.packageName);
        if (zzct != null) {
            zzc(com_google_android_gms_measurement_internal_zzm, zzct);
        }
    }

    @WorkerThread
    final void zzc(zzm com_google_android_gms_measurement_internal_zzm, zzi com_google_android_gms_measurement_internal_zzi) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm);
        Preconditions.checkNotEmpty(com_google_android_gms_measurement_internal_zzm.packageName);
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm.zzahe);
        Preconditions.checkNotEmpty(com_google_android_gms_measurement_internal_zzm.zzahe.name);
        zzaf();
        zzlx();
        if (!TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzafx) || !TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzagk)) {
            if (com_google_android_gms_measurement_internal_zzi.zzagg) {
                zzjt().beginTransaction();
                try {
                    zzg(com_google_android_gms_measurement_internal_zzi);
                    zzm zzj = zzjt().zzj(com_google_android_gms_measurement_internal_zzm.packageName, com_google_android_gms_measurement_internal_zzm.zzahe.name);
                    if (zzj != null) {
                        this.zzadp.zzgt().zzjn().zze("Removing conditional user property", com_google_android_gms_measurement_internal_zzm.packageName, this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzm.zzahe.name));
                        zzjt().zzk(com_google_android_gms_measurement_internal_zzm.packageName, com_google_android_gms_measurement_internal_zzm.zzahe.name);
                        if (zzj.active) {
                            zzjt().zzh(com_google_android_gms_measurement_internal_zzm.packageName, com_google_android_gms_measurement_internal_zzm.zzahe.name);
                        }
                        if (com_google_android_gms_measurement_internal_zzm.zzahh != null) {
                            Bundle bundle = null;
                            if (com_google_android_gms_measurement_internal_zzm.zzahh.zzaig != null) {
                                bundle = com_google_android_gms_measurement_internal_zzm.zzahh.zzaig.zziy();
                            }
                            Bundle bundle2 = bundle;
                            zzd(this.zzadp.zzgr().zza(com_google_android_gms_measurement_internal_zzm.packageName, com_google_android_gms_measurement_internal_zzm.zzahh.name, bundle2, zzj.origin, com_google_android_gms_measurement_internal_zzm.zzahh.zzais, true, false), com_google_android_gms_measurement_internal_zzi);
                        }
                    } else {
                        this.zzadp.zzgt().zzjj().zze("Conditional user property doesn't exist", zzaq.zzby(com_google_android_gms_measurement_internal_zzm.packageName), this.zzadp.zzgq().zzbx(com_google_android_gms_measurement_internal_zzm.zzahe.name));
                    }
                    zzjt().setTransactionSuccessful();
                } finally {
                    zzjt().endTransaction();
                }
            } else {
                zzg(com_google_android_gms_measurement_internal_zzi);
            }
        }
    }

    @WorkerThread
    private final zzg zzg(zzi com_google_android_gms_measurement_internal_zzi) {
        Object obj;
        zzaf();
        zzlx();
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzi);
        Preconditions.checkNotEmpty(com_google_android_gms_measurement_internal_zzi.packageName);
        zzg zzbo = zzjt().zzbo(com_google_android_gms_measurement_internal_zzi.packageName);
        String zzcc = this.zzadp.zzgu().zzcc(com_google_android_gms_measurement_internal_zzi.packageName);
        if (zzbo == null) {
            zzbo = new zzg(this.zzadp, com_google_android_gms_measurement_internal_zzi.packageName);
            zzbo.zzal(this.zzadp.zzgr().zzmm());
            zzbo.zzao(zzcc);
        } else if (zzcc.equals(zzbo.zzhc())) {
            obj = null;
            if (!TextUtils.equals(com_google_android_gms_measurement_internal_zzi.zzafx, zzbo.getGmpAppId())) {
                zzbo.zzam(com_google_android_gms_measurement_internal_zzi.zzafx);
                obj = 1;
            }
            if (!TextUtils.equals(com_google_android_gms_measurement_internal_zzi.zzagk, zzbo.zzhb())) {
                zzbo.zzan(com_google_android_gms_measurement_internal_zzi.zzagk);
                obj = 1;
            }
            if (!(TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzafz) || com_google_android_gms_measurement_internal_zzi.zzafz.equals(zzbo.getFirebaseInstanceId()))) {
                zzbo.zzap(com_google_android_gms_measurement_internal_zzi.zzafz);
                obj = 1;
            }
            if (!(com_google_android_gms_measurement_internal_zzi.zzadt == 0 || com_google_android_gms_measurement_internal_zzi.zzadt == zzbo.zzhh())) {
                zzbo.zzv(com_google_android_gms_measurement_internal_zzi.zzadt);
                obj = 1;
            }
            if (!(TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzts) || com_google_android_gms_measurement_internal_zzi.zzts.equals(zzbo.zzak()))) {
                zzbo.setAppVersion(com_google_android_gms_measurement_internal_zzi.zzts);
                obj = 1;
            }
            if (com_google_android_gms_measurement_internal_zzi.zzagd != zzbo.zzhf()) {
                zzbo.zzu(com_google_android_gms_measurement_internal_zzi.zzagd);
                obj = 1;
            }
            if (!(com_google_android_gms_measurement_internal_zzi.zzage == null || com_google_android_gms_measurement_internal_zzi.zzage.equals(zzbo.zzhg()))) {
                zzbo.zzaq(com_google_android_gms_measurement_internal_zzi.zzage);
                obj = 1;
            }
            if (com_google_android_gms_measurement_internal_zzi.zzagf != zzbo.zzhi()) {
                zzbo.zzw(com_google_android_gms_measurement_internal_zzi.zzagf);
                obj = 1;
            }
            if (com_google_android_gms_measurement_internal_zzi.zzagg != zzbo.isMeasurementEnabled()) {
                zzbo.setMeasurementEnabled(com_google_android_gms_measurement_internal_zzi.zzagg);
                obj = 1;
            }
            if (!(TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzagy) || com_google_android_gms_measurement_internal_zzi.zzagy.equals(zzbo.zzht()))) {
                zzbo.zzar(com_google_android_gms_measurement_internal_zzi.zzagy);
                obj = 1;
            }
            if (com_google_android_gms_measurement_internal_zzi.zzagh != zzbo.zzhv()) {
                zzbo.zzag(com_google_android_gms_measurement_internal_zzi.zzagh);
                obj = 1;
            }
            if (com_google_android_gms_measurement_internal_zzi.zzagi != zzbo.zzhw()) {
                zzbo.zze(com_google_android_gms_measurement_internal_zzi.zzagi);
                obj = 1;
            }
            if (com_google_android_gms_measurement_internal_zzi.zzagj != zzbo.zzhx()) {
                zzbo.zzf(com_google_android_gms_measurement_internal_zzi.zzagj);
                obj = 1;
            }
            if (obj != null) {
                zzjt().zza(zzbo);
            }
            return zzbo;
        } else {
            zzbo.zzao(zzcc);
            zzbo.zzal(this.zzadp.zzgr().zzmm());
        }
        obj = 1;
        if (TextUtils.equals(com_google_android_gms_measurement_internal_zzi.zzafx, zzbo.getGmpAppId())) {
            zzbo.zzam(com_google_android_gms_measurement_internal_zzi.zzafx);
            obj = 1;
        }
        if (TextUtils.equals(com_google_android_gms_measurement_internal_zzi.zzagk, zzbo.zzhb())) {
            zzbo.zzan(com_google_android_gms_measurement_internal_zzi.zzagk);
            obj = 1;
        }
        zzbo.zzap(com_google_android_gms_measurement_internal_zzi.zzafz);
        obj = 1;
        zzbo.zzv(com_google_android_gms_measurement_internal_zzi.zzadt);
        obj = 1;
        zzbo.setAppVersion(com_google_android_gms_measurement_internal_zzi.zzts);
        obj = 1;
        if (com_google_android_gms_measurement_internal_zzi.zzagd != zzbo.zzhf()) {
            zzbo.zzu(com_google_android_gms_measurement_internal_zzi.zzagd);
            obj = 1;
        }
        zzbo.zzaq(com_google_android_gms_measurement_internal_zzi.zzage);
        obj = 1;
        if (com_google_android_gms_measurement_internal_zzi.zzagf != zzbo.zzhi()) {
            zzbo.zzw(com_google_android_gms_measurement_internal_zzi.zzagf);
            obj = 1;
        }
        if (com_google_android_gms_measurement_internal_zzi.zzagg != zzbo.isMeasurementEnabled()) {
            zzbo.setMeasurementEnabled(com_google_android_gms_measurement_internal_zzi.zzagg);
            obj = 1;
        }
        zzbo.zzar(com_google_android_gms_measurement_internal_zzi.zzagy);
        obj = 1;
        if (com_google_android_gms_measurement_internal_zzi.zzagh != zzbo.zzhv()) {
            zzbo.zzag(com_google_android_gms_measurement_internal_zzi.zzagh);
            obj = 1;
        }
        if (com_google_android_gms_measurement_internal_zzi.zzagi != zzbo.zzhw()) {
            zzbo.zze(com_google_android_gms_measurement_internal_zzi.zzagi);
            obj = 1;
        }
        if (com_google_android_gms_measurement_internal_zzi.zzagj != zzbo.zzhx()) {
            zzbo.zzf(com_google_android_gms_measurement_internal_zzi.zzagj);
            obj = 1;
        }
        if (obj != null) {
            zzjt().zza(zzbo);
        }
        return zzbo;
    }

    final String zzh(zzi com_google_android_gms_measurement_internal_zzi) {
        try {
            return (String) this.zzadp.zzgs().zzb(new zzfo(this, com_google_android_gms_measurement_internal_zzi)).get(30000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            this.zzadp.zzgt().zzjg().zze("Failed to get app instance id. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzi.packageName), e);
            return null;
        }
    }

    final void zzm(boolean z) {
        zzmb();
    }
}
