package com.google.android.gms.measurement.internal;

import android.content.Context;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.util.Clock;

public final class zzak extends zzf {
    private String zzafx;
    private String zzage;
    private long zzagh;
    private String zzagk;
    private int zzahb;
    private int zzalv;
    private long zzalw;
    private String zztr;
    private String zzts;
    private String zztt;

    zzak(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
    }

    protected final boolean zzgy() {
        return true;
    }

    protected final void zzgz() {
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
        r0 = "unknown";
        r1 = "Unknown";
        r2 = "Unknown";
        r3 = r10.getContext();
        r3 = r3.getPackageName();
        r4 = r10.getContext();
        r4 = r4.getPackageManager();
        r5 = 0;
        r6 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        if (r4 != 0) goto L_0x002d;
    L_0x001b:
        r4 = r10.zzgt();
        r4 = r4.zzjg();
        r7 = "PackageManager is null, app identity information might be inaccurate. appId";
        r8 = com.google.android.gms.measurement.internal.zzaq.zzby(r3);
        r4.zzg(r7, r8);
        goto L_0x008b;
    L_0x002d:
        r7 = r4.getInstallerPackageName(r3);	 Catch:{ IllegalArgumentException -> 0x0033 }
        r0 = r7;
        goto L_0x0044;
    L_0x0033:
        r7 = r10.zzgt();
        r7 = r7.zzjg();
        r8 = "Error retrieving app installer package name. appId";
        r9 = com.google.android.gms.measurement.internal.zzaq.zzby(r3);
        r7.zzg(r8, r9);
    L_0x0044:
        if (r0 != 0) goto L_0x0049;
    L_0x0046:
        r0 = "manual_install";
        goto L_0x0053;
    L_0x0049:
        r7 = "com.android.vending";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x0053;
    L_0x0051:
        r0 = "";
    L_0x0053:
        r7 = r10.getContext();	 Catch:{ NameNotFoundException -> 0x007a }
        r7 = r7.getPackageName();	 Catch:{ NameNotFoundException -> 0x007a }
        r7 = r4.getPackageInfo(r7, r5);	 Catch:{ NameNotFoundException -> 0x007a }
        if (r7 == 0) goto L_0x008b;	 Catch:{ NameNotFoundException -> 0x007a }
    L_0x0061:
        r8 = r7.applicationInfo;	 Catch:{ NameNotFoundException -> 0x007a }
        r4 = r4.getApplicationLabel(r8);	 Catch:{ NameNotFoundException -> 0x007a }
        r8 = android.text.TextUtils.isEmpty(r4);	 Catch:{ NameNotFoundException -> 0x007a }
        if (r8 != 0) goto L_0x0072;	 Catch:{ NameNotFoundException -> 0x007a }
    L_0x006d:
        r4 = r4.toString();	 Catch:{ NameNotFoundException -> 0x007a }
        r2 = r4;	 Catch:{ NameNotFoundException -> 0x007a }
    L_0x0072:
        r4 = r7.versionName;	 Catch:{ NameNotFoundException -> 0x007a }
        r1 = r7.versionCode;	 Catch:{ NameNotFoundException -> 0x0079 }
        r6 = r1;
        r1 = r4;
        goto L_0x008b;
    L_0x0079:
        r1 = r4;
    L_0x007a:
        r4 = r10.zzgt();
        r4 = r4.zzjg();
        r7 = "Error retrieving package info. appId, appName";
        r8 = com.google.android.gms.measurement.internal.zzaq.zzby(r3);
        r4.zze(r7, r8, r2);
    L_0x008b:
        r10.zztt = r3;
        r10.zzage = r0;
        r10.zzts = r1;
        r10.zzalv = r6;
        r10.zztr = r2;
        r0 = 0;
        r10.zzalw = r0;
        r10.zzgw();
        r2 = r10.getContext();
        r2 = com.google.android.gms.common.api.internal.GoogleServices.initialize(r2);
        r4 = 1;
        if (r2 == 0) goto L_0x00af;
    L_0x00a7:
        r6 = r2.isSuccess();
        if (r6 == 0) goto L_0x00af;
    L_0x00ad:
        r6 = 1;
        goto L_0x00b0;
    L_0x00af:
        r6 = 0;
    L_0x00b0:
        r7 = r10.zzadp;
        r7 = r7.zzko();
        r7 = android.text.TextUtils.isEmpty(r7);
        if (r7 != 0) goto L_0x00cc;
    L_0x00bc:
        r7 = "am";
        r8 = r10.zzadp;
        r8 = r8.zzkp();
        r7 = r7.equals(r8);
        if (r7 == 0) goto L_0x00cc;
    L_0x00ca:
        r7 = 1;
        goto L_0x00cd;
    L_0x00cc:
        r7 = 0;
    L_0x00cd:
        r6 = r6 | r7;
        if (r6 != 0) goto L_0x00f9;
    L_0x00d0:
        if (r2 != 0) goto L_0x00e0;
    L_0x00d2:
        r2 = r10.zzgt();
        r2 = r2.zzjg();
        r7 = "GoogleService failed to initialize (no status)";
        r2.zzca(r7);
        goto L_0x00f9;
    L_0x00e0:
        r7 = r10.zzgt();
        r7 = r7.zzjg();
        r8 = "GoogleService failed to initialize, status";
        r9 = r2.getStatusCode();
        r9 = java.lang.Integer.valueOf(r9);
        r2 = r2.getStatusMessage();
        r7.zze(r8, r9, r2);
    L_0x00f9:
        if (r6 == 0) goto L_0x0165;
    L_0x00fb:
        r2 = r10.zzgv();
        r2 = r2.zzia();
        r6 = r10.zzgv();
        r6 = r6.zzhz();
        if (r6 == 0) goto L_0x0123;
    L_0x010d:
        r2 = r10.zzadp;
        r2 = r2.zzkn();
        if (r2 == 0) goto L_0x0165;
    L_0x0115:
        r2 = r10.zzgt();
        r2 = r2.zzjm();
        r4 = "Collection disabled with firebase_analytics_collection_deactivated=1";
        r2.zzca(r4);
        goto L_0x0165;
    L_0x0123:
        if (r2 == 0) goto L_0x0141;
    L_0x0125:
        r6 = r2.booleanValue();
        if (r6 != 0) goto L_0x0141;
    L_0x012b:
        r2 = r10.zzadp;
        r2 = r2.zzkn();
        if (r2 == 0) goto L_0x0165;
    L_0x0133:
        r2 = r10.zzgt();
        r2 = r2.zzjm();
        r4 = "Collection disabled with firebase_analytics_collection_enabled=0";
        r2.zzca(r4);
        goto L_0x0165;
    L_0x0141:
        if (r2 != 0) goto L_0x0157;
    L_0x0143:
        r2 = com.google.android.gms.common.api.internal.GoogleServices.isMeasurementExplicitlyDisabled();
        if (r2 == 0) goto L_0x0157;
    L_0x0149:
        r2 = r10.zzgt();
        r2 = r2.zzjm();
        r4 = "Collection disabled with google_app_measurement_enable=0";
        r2.zzca(r4);
        goto L_0x0165;
    L_0x0157:
        r2 = r10.zzgt();
        r2 = r2.zzjo();
        r6 = "Collection enabled";
        r2.zzca(r6);
        goto L_0x0166;
    L_0x0165:
        r4 = 0;
    L_0x0166:
        r2 = "";
        r10.zzafx = r2;
        r2 = "";
        r10.zzagk = r2;
        r10.zzagh = r0;
        r10.zzgw();
        r0 = r10.zzadp;
        r0 = r0.zzko();
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x0195;
    L_0x017f:
        r0 = "am";
        r1 = r10.zzadp;
        r1 = r1.zzkp();
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0195;
    L_0x018d:
        r0 = r10.zzadp;
        r0 = r0.zzko();
        r10.zzagk = r0;
    L_0x0195:
        r0 = com.google.android.gms.common.api.internal.GoogleServices.getGoogleAppId();	 Catch:{ IllegalStateException -> 0x01d0 }
        r1 = android.text.TextUtils.isEmpty(r0);	 Catch:{ IllegalStateException -> 0x01d0 }
        if (r1 == 0) goto L_0x01a2;	 Catch:{ IllegalStateException -> 0x01d0 }
    L_0x019f:
        r1 = "";	 Catch:{ IllegalStateException -> 0x01d0 }
        goto L_0x01a3;	 Catch:{ IllegalStateException -> 0x01d0 }
    L_0x01a2:
        r1 = r0;	 Catch:{ IllegalStateException -> 0x01d0 }
    L_0x01a3:
        r10.zzafx = r1;	 Catch:{ IllegalStateException -> 0x01d0 }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ IllegalStateException -> 0x01d0 }
        if (r0 != 0) goto L_0x01bc;	 Catch:{ IllegalStateException -> 0x01d0 }
    L_0x01ab:
        r0 = new com.google.android.gms.common.internal.StringResourceValueReader;	 Catch:{ IllegalStateException -> 0x01d0 }
        r1 = r10.getContext();	 Catch:{ IllegalStateException -> 0x01d0 }
        r0.<init>(r1);	 Catch:{ IllegalStateException -> 0x01d0 }
        r1 = "admob_app_id";	 Catch:{ IllegalStateException -> 0x01d0 }
        r0 = r0.getString(r1);	 Catch:{ IllegalStateException -> 0x01d0 }
        r10.zzagk = r0;	 Catch:{ IllegalStateException -> 0x01d0 }
    L_0x01bc:
        if (r4 == 0) goto L_0x01e2;	 Catch:{ IllegalStateException -> 0x01d0 }
    L_0x01be:
        r0 = r10.zzgt();	 Catch:{ IllegalStateException -> 0x01d0 }
        r0 = r0.zzjo();	 Catch:{ IllegalStateException -> 0x01d0 }
        r1 = "App package, google app id";	 Catch:{ IllegalStateException -> 0x01d0 }
        r2 = r10.zztt;	 Catch:{ IllegalStateException -> 0x01d0 }
        r4 = r10.zzafx;	 Catch:{ IllegalStateException -> 0x01d0 }
        r0.zze(r1, r2, r4);	 Catch:{ IllegalStateException -> 0x01d0 }
        goto L_0x01e2;
    L_0x01d0:
        r0 = move-exception;
        r1 = r10.zzgt();
        r1 = r1.zzjg();
        r2 = "getGoogleAppId or isMeasurementEnabled failed with exception. appId";
        r3 = com.google.android.gms.measurement.internal.zzaq.zzby(r3);
        r1.zze(r2, r3, r0);
    L_0x01e2:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 16;
        if (r0 < r1) goto L_0x01f3;
    L_0x01e8:
        r0 = r10.getContext();
        r0 = com.google.android.gms.common.wrappers.InstantApps.isInstantApp(r0);
        r10.zzahb = r0;
        return;
    L_0x01f3:
        r10.zzahb = r5;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzak.zzgz():void");
    }

    @WorkerThread
    final zzi zzbu(String str) {
        String zzjc;
        boolean z;
        boolean booleanValue;
        boolean z2;
        long j;
        zzaf();
        zzgg();
        String zzal = zzal();
        String gmpAppId = getGmpAppId();
        zzcl();
        String str2 = this.zzts;
        long zzjd = (long) zzjd();
        zzcl();
        String str3 = this.zzage;
        long zzhh = zzgv().zzhh();
        zzcl();
        zzaf();
        if (this.zzalw == 0) {
            r0.zzalw = r0.zzadp.zzgr().zzd(getContext(), getContext().getPackageName());
        }
        long j2 = r0.zzalw;
        boolean isEnabled = r0.zzadp.isEnabled();
        boolean z3 = zzgu().zzaob ^ 1;
        zzaf();
        zzgg();
        if (!zzgv().zzbb(r0.zztt) || r0.zzadp.isEnabled()) {
            zzjc = zzjc();
        } else {
            zzjc = null;
        }
        String str4 = zzjc;
        zzcl();
        boolean z4 = z3;
        String str5 = str4;
        long j3 = r0.zzagh;
        long zzkt = r0.zzadp.zzkt();
        int zzje = zzje();
        zzcp zzgv = zzgv();
        zzgv.zzgg();
        Boolean zzat = zzgv.zzat("google_analytics_adid_collection_enabled");
        if (zzat != null) {
            if (!zzat.booleanValue()) {
                z = false;
                booleanValue = Boolean.valueOf(z).booleanValue();
                zzgv = zzgv();
                zzgv.zzgg();
                zzat = zzgv.zzat("google_analytics_ssaid_collection_enabled");
                if (zzat != null) {
                    if (zzat.booleanValue()) {
                        z2 = false;
                        j = j3;
                        return new zzi(zzal, gmpAppId, str2, zzjd, str3, zzhh, j2, str, isEnabled, z4, str5, j, zzkt, zzje, booleanValue, Boolean.valueOf(z2).booleanValue(), zzgu().zzkb(), zzhb());
                    }
                }
                z2 = true;
                j = j3;
                return new zzi(zzal, gmpAppId, str2, zzjd, str3, zzhh, j2, str, isEnabled, z4, str5, j, zzkt, zzje, booleanValue, Boolean.valueOf(z2).booleanValue(), zzgu().zzkb(), zzhb());
            }
        }
        z = true;
        booleanValue = Boolean.valueOf(z).booleanValue();
        zzgv = zzgv();
        zzgv.zzgg();
        zzat = zzgv.zzat("google_analytics_ssaid_collection_enabled");
        if (zzat != null) {
            if (zzat.booleanValue()) {
                z2 = false;
                j = j3;
                return new zzi(zzal, gmpAppId, str2, zzjd, str3, zzhh, j2, str, isEnabled, z4, str5, j, zzkt, zzje, booleanValue, Boolean.valueOf(z2).booleanValue(), zzgu().zzkb(), zzhb());
            }
        }
        z2 = true;
        j = j3;
        return new zzi(zzal, gmpAppId, str2, zzjd, str3, zzhh, j2, str, isEnabled, z4, str5, j, zzkt, zzje, booleanValue, Boolean.valueOf(z2).booleanValue(), zzgu().zzkb(), zzhb());
    }

    @androidx.annotation.WorkerThread
    @com.google.android.gms.common.util.VisibleForTesting
    private final java.lang.String zzjc() {
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
        r7 = this;
        r0 = 0;
        r1 = r7.getContext();	 Catch:{ ClassNotFoundException -> 0x005c }
        r1 = r1.getClassLoader();	 Catch:{ ClassNotFoundException -> 0x005c }
        r2 = "com.google.firebase.analytics.FirebaseAnalytics";	 Catch:{ ClassNotFoundException -> 0x005c }
        r1 = r1.loadClass(r2);	 Catch:{ ClassNotFoundException -> 0x005c }
        if (r1 != 0) goto L_0x0012;
    L_0x0011:
        return r0;
    L_0x0012:
        r2 = "getInstance";	 Catch:{ Exception -> 0x004e }
        r3 = 1;	 Catch:{ Exception -> 0x004e }
        r4 = new java.lang.Class[r3];	 Catch:{ Exception -> 0x004e }
        r5 = android.content.Context.class;	 Catch:{ Exception -> 0x004e }
        r6 = 0;	 Catch:{ Exception -> 0x004e }
        r4[r6] = r5;	 Catch:{ Exception -> 0x004e }
        r2 = r1.getDeclaredMethod(r2, r4);	 Catch:{ Exception -> 0x004e }
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x004e }
        r4 = r7.getContext();	 Catch:{ Exception -> 0x004e }
        r3[r6] = r4;	 Catch:{ Exception -> 0x004e }
        r2 = r2.invoke(r0, r3);	 Catch:{ Exception -> 0x004e }
        if (r2 != 0) goto L_0x002f;
    L_0x002e:
        return r0;
    L_0x002f:
        r3 = "getFirebaseInstanceId";	 Catch:{ Exception -> 0x0040 }
        r4 = new java.lang.Class[r6];	 Catch:{ Exception -> 0x0040 }
        r1 = r1.getDeclaredMethod(r3, r4);	 Catch:{ Exception -> 0x0040 }
        r3 = new java.lang.Object[r6];	 Catch:{ Exception -> 0x0040 }
        r1 = r1.invoke(r2, r3);	 Catch:{ Exception -> 0x0040 }
        r1 = (java.lang.String) r1;	 Catch:{ Exception -> 0x0040 }
        return r1;
    L_0x0040:
        r1 = r7.zzgt();
        r1 = r1.zzjl();
        r2 = "Failed to retrieve Firebase Instance Id";
        r1.zzca(r2);
        return r0;
    L_0x004e:
        r1 = r7.zzgt();
        r1 = r1.zzjk();
        r2 = "Failed to obtain Firebase Analytics instance";
        r1.zzca(r2);
        return r0;
    L_0x005c:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzak.zzjc():java.lang.String");
    }

    final String zzal() {
        zzcl();
        return this.zztt;
    }

    final String getGmpAppId() {
        zzcl();
        return this.zzafx;
    }

    final String zzhb() {
        zzcl();
        return this.zzagk;
    }

    final int zzjd() {
        zzcl();
        return this.zzalv;
    }

    final int zzje() {
        zzcl();
        return this.zzahb;
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
