package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.measurement.internal.zzag.zza;
import java.lang.reflect.InvocationTargetException;

public final class zzo extends zzcp {
    private Boolean zzahi;
    @NonNull
    private zzq zzahj = zzp.zzahk;
    private Boolean zzyk;

    zzo(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
        zzag.zza(com_google_android_gms_measurement_internal_zzbu);
    }

    final void zza(@NonNull zzq com_google_android_gms_measurement_internal_zzq) {
        this.zzahj = com_google_android_gms_measurement_internal_zzq;
    }

    static String zzhy() {
        return (String) zzag.zzajg.get();
    }

    @WorkerThread
    public final int zzas(@Size(min = 1) String str) {
        return zzb(str, zzag.zzaju);
    }

    public final long zzhh() {
        zzgw();
        return 14700;
    }

    public final boolean zzdw() {
        if (this.zzyk == null) {
            synchronized (this) {
                if (this.zzyk == null) {
                    ApplicationInfo applicationInfo = getContext().getApplicationInfo();
                    String myProcessName = ProcessUtils.getMyProcessName();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        boolean z = str != null && str.equals(myProcessName);
                        this.zzyk = Boolean.valueOf(z);
                    }
                    if (this.zzyk == null) {
                        this.zzyk = Boolean.TRUE;
                        zzgt().zzjg().zzca("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzyk.booleanValue();
    }

    @androidx.annotation.WorkerThread
    public final long zza(java.lang.String r3, @androidx.annotation.NonNull com.google.android.gms.measurement.internal.zzag.zza<java.lang.Long> r4) {
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
        r2 = this;
        if (r3 != 0) goto L_0x000d;
    L_0x0002:
        r3 = r4.get();
        r3 = (java.lang.Long) r3;
        r3 = r3.longValue();
        return r3;
    L_0x000d:
        r0 = r2.zzahj;
        r1 = r4.getKey();
        r3 = r0.zzf(r3, r1);
        r0 = android.text.TextUtils.isEmpty(r3);
        if (r0 == 0) goto L_0x0028;
    L_0x001d:
        r3 = r4.get();
        r3 = (java.lang.Long) r3;
        r3 = r3.longValue();
        return r3;
    L_0x0028:
        r0 = java.lang.Long.parseLong(r3);	 Catch:{ NumberFormatException -> 0x003b }
        r3 = java.lang.Long.valueOf(r0);	 Catch:{ NumberFormatException -> 0x003b }
        r3 = r4.get(r3);	 Catch:{ NumberFormatException -> 0x003b }
        r3 = (java.lang.Long) r3;	 Catch:{ NumberFormatException -> 0x003b }
        r0 = r3.longValue();	 Catch:{ NumberFormatException -> 0x003b }
        return r0;
    L_0x003b:
        r3 = r4.get();
        r3 = (java.lang.Long) r3;
        r3 = r3.longValue();
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzo.zza(java.lang.String, com.google.android.gms.measurement.internal.zzag$zza):long");
    }

    @androidx.annotation.WorkerThread
    public final int zzb(java.lang.String r3, @androidx.annotation.NonNull com.google.android.gms.measurement.internal.zzag.zza<java.lang.Integer> r4) {
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
        r2 = this;
        if (r3 != 0) goto L_0x000d;
    L_0x0002:
        r3 = r4.get();
        r3 = (java.lang.Integer) r3;
        r3 = r3.intValue();
        return r3;
    L_0x000d:
        r0 = r2.zzahj;
        r1 = r4.getKey();
        r3 = r0.zzf(r3, r1);
        r0 = android.text.TextUtils.isEmpty(r3);
        if (r0 == 0) goto L_0x0028;
    L_0x001d:
        r3 = r4.get();
        r3 = (java.lang.Integer) r3;
        r3 = r3.intValue();
        return r3;
    L_0x0028:
        r3 = java.lang.Integer.parseInt(r3);	 Catch:{ NumberFormatException -> 0x003b }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ NumberFormatException -> 0x003b }
        r3 = r4.get(r3);	 Catch:{ NumberFormatException -> 0x003b }
        r3 = (java.lang.Integer) r3;	 Catch:{ NumberFormatException -> 0x003b }
        r3 = r3.intValue();	 Catch:{ NumberFormatException -> 0x003b }
        return r3;
    L_0x003b:
        r3 = r4.get();
        r3 = (java.lang.Integer) r3;
        r3 = r3.intValue();
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzo.zzb(java.lang.String, com.google.android.gms.measurement.internal.zzag$zza):int");
    }

    @androidx.annotation.WorkerThread
    public final double zzc(java.lang.String r3, @androidx.annotation.NonNull com.google.android.gms.measurement.internal.zzag.zza<java.lang.Double> r4) {
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
        r2 = this;
        if (r3 != 0) goto L_0x000d;
    L_0x0002:
        r3 = r4.get();
        r3 = (java.lang.Double) r3;
        r3 = r3.doubleValue();
        return r3;
    L_0x000d:
        r0 = r2.zzahj;
        r1 = r4.getKey();
        r3 = r0.zzf(r3, r1);
        r0 = android.text.TextUtils.isEmpty(r3);
        if (r0 == 0) goto L_0x0028;
    L_0x001d:
        r3 = r4.get();
        r3 = (java.lang.Double) r3;
        r3 = r3.doubleValue();
        return r3;
    L_0x0028:
        r0 = java.lang.Double.parseDouble(r3);	 Catch:{ NumberFormatException -> 0x003b }
        r3 = java.lang.Double.valueOf(r0);	 Catch:{ NumberFormatException -> 0x003b }
        r3 = r4.get(r3);	 Catch:{ NumberFormatException -> 0x003b }
        r3 = (java.lang.Double) r3;	 Catch:{ NumberFormatException -> 0x003b }
        r0 = r3.doubleValue();	 Catch:{ NumberFormatException -> 0x003b }
        return r0;
    L_0x003b:
        r3 = r4.get();
        r3 = (java.lang.Double) r3;
        r3 = r3.doubleValue();
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzo.zzc(java.lang.String, com.google.android.gms.measurement.internal.zzag$zza):double");
    }

    @WorkerThread
    public final boolean zzd(String str, @NonNull zza<Boolean> com_google_android_gms_measurement_internal_zzag_zza_java_lang_Boolean) {
        if (str == null) {
            return ((Boolean) com_google_android_gms_measurement_internal_zzag_zza_java_lang_Boolean.get()).booleanValue();
        }
        str = this.zzahj.zzf(str, com_google_android_gms_measurement_internal_zzag_zza_java_lang_Boolean.getKey());
        if (TextUtils.isEmpty(str)) {
            return ((Boolean) com_google_android_gms_measurement_internal_zzag_zza_java_lang_Boolean.get()).booleanValue();
        }
        return ((Boolean) com_google_android_gms_measurement_internal_zzag_zza_java_lang_Boolean.get(Boolean.valueOf(Boolean.parseBoolean(str)))).booleanValue();
    }

    public final boolean zze(String str, zza<Boolean> com_google_android_gms_measurement_internal_zzag_zza_java_lang_Boolean) {
        return zzd(str, com_google_android_gms_measurement_internal_zzag_zza_java_lang_Boolean);
    }

    public final boolean zza(zza<Boolean> com_google_android_gms_measurement_internal_zzag_zza_java_lang_Boolean) {
        return zzd(null, com_google_android_gms_measurement_internal_zzag_zza_java_lang_Boolean);
    }

    @Nullable
    @VisibleForTesting
    final Boolean zzat(@Size(min = 1) String str) {
        Preconditions.checkNotEmpty(str);
        try {
            if (getContext().getPackageManager() == null) {
                zzgt().zzjg().zzca("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo applicationInfo = Wrappers.packageManager(getContext()).getApplicationInfo(getContext().getPackageName(), 128);
            if (applicationInfo == null) {
                zzgt().zzjg().zzca("Failed to load metadata: ApplicationInfo is null");
                return null;
            } else if (applicationInfo.metaData == null) {
                zzgt().zzjg().zzca("Failed to load metadata: Metadata bundle is null");
                return null;
            } else if (applicationInfo.metaData.containsKey(str)) {
                return Boolean.valueOf(applicationInfo.metaData.getBoolean(str));
            } else {
                return null;
            }
        } catch (String str2) {
            zzgt().zzjg().zzg("Failed to load metadata: Package name not found", str2);
            return null;
        }
    }

    public final boolean zzhz() {
        zzgw();
        Boolean zzat = zzat("firebase_analytics_collection_deactivated");
        return zzat != null && zzat.booleanValue();
    }

    public final Boolean zzia() {
        zzgw();
        return zzat("firebase_analytics_collection_enabled");
    }

    public static long zzib() {
        return ((Long) zzag.zzakj.get()).longValue();
    }

    public static long zzic() {
        return ((Long) zzag.zzajj.get()).longValue();
    }

    public final String zzid() {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{"debug.firebase.analytics.app", ""});
        } catch (ClassNotFoundException e) {
            zzgt().zzjg().zzg("Could not find SystemProperties class", e);
            return "";
        } catch (NoSuchMethodException e2) {
            zzgt().zzjg().zzg("Could not find SystemProperties.get() method", e2);
            return "";
        } catch (IllegalAccessException e3) {
            zzgt().zzjg().zzg("Could not access SystemProperties.get()", e3);
            return "";
        } catch (InvocationTargetException e4) {
            zzgt().zzjg().zzg("SystemProperties.get() threw an exception", e4);
            return "";
        }
    }

    public static boolean zzie() {
        return ((Boolean) zzag.zzajf.get()).booleanValue();
    }

    public final boolean zzau(String str) {
        return "1".equals(this.zzahj.zzf(str, "gaia_collection_enabled"));
    }

    public final boolean zzav(String str) {
        return "1".equals(this.zzahj.zzf(str, "measurement.event_sampling_enabled"));
    }

    @WorkerThread
    final boolean zzaw(String str) {
        return zzd(str, zzag.zzakt);
    }

    @WorkerThread
    final boolean zzax(String str) {
        return zzd(str, zzag.zzakv);
    }

    @WorkerThread
    final boolean zzay(String str) {
        return zzd(str, zzag.zzakw);
    }

    @WorkerThread
    final boolean zzaz(String str) {
        return zzd(str, zzag.zzakn);
    }

    @WorkerThread
    final String zzba(String str) {
        zza com_google_android_gms_measurement_internal_zzag_zza = zzag.zzako;
        if (str == null) {
            return (String) com_google_android_gms_measurement_internal_zzag_zza.get();
        }
        return (String) com_google_android_gms_measurement_internal_zzag_zza.get(this.zzahj.zzf(str, com_google_android_gms_measurement_internal_zzag_zza.getKey()));
    }

    final boolean zzbb(String str) {
        return zzd(str, zzag.zzakx);
    }

    @WorkerThread
    final boolean zzbc(String str) {
        return zzd(str, zzag.zzaky);
    }

    final boolean zzbd(String str) {
        return zzd(str, zzag.zzala);
    }

    @WorkerThread
    final boolean zzbe(String str) {
        return zzd(str, zzag.zzalb);
    }

    @WorkerThread
    final boolean zzbf(String str) {
        return zzd(str, zzag.zzalc);
    }

    @WorkerThread
    final boolean zzbg(String str) {
        return zzd(str, zzag.zzale);
    }

    @WorkerThread
    final boolean zzif() {
        if (this.zzahi == null) {
            this.zzahi = zzat("app_measurement_lite");
            if (this.zzahi == null) {
                this.zzahi = Boolean.valueOf(false);
            }
        }
        if (!this.zzahi.booleanValue()) {
            if (this.zzadp.zzkr()) {
                return false;
            }
        }
        return true;
    }

    @WorkerThread
    final boolean zzbh(String str) {
        return zzd(str, zzag.zzald);
    }

    @WorkerThread
    static boolean zzig() {
        return ((Boolean) zzag.zzalf.get()).booleanValue();
    }

    @WorkerThread
    final boolean zzbi(String str) {
        return zzd(str, zzag.zzalg);
    }

    @WorkerThread
    final boolean zzbj(String str) {
        return zzd(str, zzag.zzalh);
    }

    @WorkerThread
    final boolean zzbk(String str) {
        return zzd(str, zzag.zzali);
    }

    @WorkerThread
    final boolean zzbl(String str) {
        return zzd(str, zzag.zzalj);
    }

    @WorkerThread
    final boolean zzbm(String str) {
        return zzd(str, zzag.zzall);
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
