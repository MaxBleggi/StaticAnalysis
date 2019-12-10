package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.AppMeasurement.ConditionalUserProperty;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

public final class zzcy extends zzf {
    @VisibleForTesting
    protected zzds zzark;
    private zzcv zzarl;
    private final Set<zzcw> zzarm = new CopyOnWriteArraySet();
    private boolean zzarn;
    private final AtomicReference<String> zzaro = new AtomicReference();
    @VisibleForTesting
    protected boolean zzarp = true;

    protected zzcy(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
    }

    protected final boolean zzgy() {
        return false;
    }

    public final void zzkw() {
        if (getContext().getApplicationContext() instanceof Application) {
            ((Application) getContext().getApplicationContext()).unregisterActivityLifecycleCallbacks(this.zzark);
        }
    }

    public final Boolean zzkx() {
        AtomicReference atomicReference = new AtomicReference();
        return (Boolean) zzgs().zza(atomicReference, 15000, "boolean test flag value", new zzcz(this, atomicReference));
    }

    public final String zzky() {
        AtomicReference atomicReference = new AtomicReference();
        return (String) zzgs().zza(atomicReference, 15000, "String test flag value", new zzdj(this, atomicReference));
    }

    public final Long zzkz() {
        AtomicReference atomicReference = new AtomicReference();
        return (Long) zzgs().zza(atomicReference, 15000, "long test flag value", new zzdl(this, atomicReference));
    }

    public final Integer zzla() {
        AtomicReference atomicReference = new AtomicReference();
        return (Integer) zzgs().zza(atomicReference, 15000, "int test flag value", new zzdm(this, atomicReference));
    }

    public final Double zzlb() {
        AtomicReference atomicReference = new AtomicReference();
        return (Double) zzgs().zza(atomicReference, 15000, "double test flag value", new zzdn(this, atomicReference));
    }

    public final void setMeasurementEnabled(boolean z) {
        zzcl();
        zzgg();
        zzgs().zzc(new zzdo(this, z));
    }

    public final void zzd(boolean z) {
        zzcl();
        zzgg();
        zzgs().zzc(new zzdp(this, z));
    }

    @WorkerThread
    private final void zzj(boolean z) {
        zzaf();
        zzgg();
        zzcl();
        zzgt().zzjn().zzg("Setting app measurement enabled (FE)", Boolean.valueOf(z));
        zzgu().setMeasurementEnabled(z);
        zzlc();
    }

    @WorkerThread
    private final void zzlc() {
        if (zzgv().zzbc(zzgk().zzal()) && this.zzadp.isEnabled() && this.zzarp) {
            zzgt().zzjn().zzca("Recording app launch after enabling measurement for the first time (FE)");
            zzld();
            return;
        }
        zzgt().zzjn().zzca("Updating Scion state (FE)");
        zzgl().zzlg();
    }

    public final void setMinimumSessionDuration(long j) {
        zzgg();
        zzgs().zzc(new zzdq(this, j));
    }

    public final void setSessionTimeoutDuration(long j) {
        zzgg();
        zzgs().zzc(new zzdr(this, j));
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z) {
        logEvent(str, str2, bundle, false, true, zzbx().currentTimeMillis());
    }

    public final void logEvent(String str, String str2, Bundle bundle) {
        logEvent(str, str2, bundle, true, true, zzbx().currentTimeMillis());
    }

    @WorkerThread
    final void zza(String str, String str2, Bundle bundle) {
        zzgg();
        zzaf();
        zza(str, str2, zzbx().currentTimeMillis(), bundle);
    }

    @WorkerThread
    final void zza(String str, String str2, long j, Bundle bundle) {
        boolean z;
        zzgg();
        zzaf();
        if (this.zzarl != null) {
            if (!zzfu.zzda(str2)) {
                z = false;
                zza(str, str2, j, bundle, true, z, false, null);
            }
        }
        z = true;
        zza(str, str2, j, bundle, true, z, false, null);
    }

    @androidx.annotation.WorkerThread
    private final void zza(java.lang.String r29, java.lang.String r30, long r31, android.os.Bundle r33, boolean r34, boolean r35, boolean r36, java.lang.String r37) {
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
        r28 = this;
        r1 = r28;
        r8 = r29;
        r6 = r30;
        r5 = r33;
        r7 = r37;
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r29);
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r30);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r33);
        r28.zzaf();
        r28.zzcl();
        r0 = r1.zzadp;
        r0 = r0.isEnabled();
        if (r0 != 0) goto L_0x002f;
    L_0x0021:
        r0 = r28.zzgt();
        r0 = r0.zzjn();
        r2 = "Event not sent since app measurement is disabled";
        r0.zzca(r2);
        return;
    L_0x002f:
        r0 = r1.zzarn;
        r4 = 0;
        r16 = 0;
        r15 = 1;
        if (r0 != 0) goto L_0x0073;
    L_0x0037:
        r1.zzarn = r15;
        r0 = "com.google.android.gms.tagmanager.TagManagerService";	 Catch:{ ClassNotFoundException -> 0x0066 }
        r0 = java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x0066 }
        r9 = "initialize";	 Catch:{ Exception -> 0x0057 }
        r10 = new java.lang.Class[r15];	 Catch:{ Exception -> 0x0057 }
        r11 = android.content.Context.class;	 Catch:{ Exception -> 0x0057 }
        r10[r16] = r11;	 Catch:{ Exception -> 0x0057 }
        r0 = r0.getDeclaredMethod(r9, r10);	 Catch:{ Exception -> 0x0057 }
        r9 = new java.lang.Object[r15];	 Catch:{ Exception -> 0x0057 }
        r10 = r28.getContext();	 Catch:{ Exception -> 0x0057 }
        r9[r16] = r10;	 Catch:{ Exception -> 0x0057 }
        r0.invoke(r4, r9);	 Catch:{ Exception -> 0x0057 }
        goto L_0x0073;
    L_0x0057:
        r0 = move-exception;
        r9 = r28.zzgt();	 Catch:{ ClassNotFoundException -> 0x0066 }
        r9 = r9.zzjj();	 Catch:{ ClassNotFoundException -> 0x0066 }
        r10 = "Failed to invoke Tag Manager's initialize() method";	 Catch:{ ClassNotFoundException -> 0x0066 }
        r9.zzg(r10, r0);	 Catch:{ ClassNotFoundException -> 0x0066 }
        goto L_0x0073;
    L_0x0066:
        r0 = r28.zzgt();
        r0 = r0.zzjm();
        r9 = "Tag Manager is not found and thus will not be used";
        r0.zzca(r9);
    L_0x0073:
        r0 = 40;
        if (r36 == 0) goto L_0x00e1;
    L_0x0077:
        r28.zzgw();
        r9 = "_iap";
        r9 = r9.equals(r6);
        if (r9 != 0) goto L_0x00e1;
    L_0x0082:
        r9 = r1.zzadp;
        r9 = r9.zzgr();
        r10 = "event";
        r10 = r9.zzs(r10, r6);
        r11 = 2;
        if (r10 != 0) goto L_0x0092;
    L_0x0091:
        goto L_0x00ab;
    L_0x0092:
        r10 = "event";
        r12 = com.google.android.gms.measurement.internal.zzcs.zzard;
        r10 = r9.zza(r10, r12, r6);
        if (r10 != 0) goto L_0x00a1;
    L_0x009c:
        r9 = 13;
        r11 = 13;
        goto L_0x00ab;
    L_0x00a1:
        r10 = "event";
        r9 = r9.zza(r10, r0, r6);
        if (r9 != 0) goto L_0x00aa;
    L_0x00a9:
        goto L_0x00ab;
    L_0x00aa:
        r11 = 0;
    L_0x00ab:
        if (r11 == 0) goto L_0x00e1;
    L_0x00ad:
        r2 = r28.zzgt();
        r2 = r2.zzji();
        r3 = "Invalid public event name. Event will not be logged (FE)";
        r4 = r28.zzgq();
        r4 = r4.zzbv(r6);
        r2.zzg(r3, r4);
        r2 = r1.zzadp;
        r2.zzgr();
        r0 = com.google.android.gms.measurement.internal.zzfu.zza(r6, r0, r15);
        if (r6 == 0) goto L_0x00d4;
    L_0x00cd:
        r16 = r30.length();
        r2 = r16;
        goto L_0x00d5;
    L_0x00d4:
        r2 = 0;
    L_0x00d5:
        r3 = r1.zzadp;
        r3 = r3.zzgr();
        r4 = "_ev";
        r3.zza(r11, r4, r0, r2);
        return;
    L_0x00e1:
        r28.zzgw();
        r9 = r28.zzgm();
        r14 = r9.zzle();
        if (r14 == 0) goto L_0x00f8;
    L_0x00ee:
        r9 = "_sc";
        r9 = r5.containsKey(r9);
        if (r9 != 0) goto L_0x00f8;
    L_0x00f6:
        r14.zzasc = r15;
    L_0x00f8:
        if (r34 == 0) goto L_0x00fe;
    L_0x00fa:
        if (r36 == 0) goto L_0x00fe;
    L_0x00fc:
        r9 = 1;
        goto L_0x00ff;
    L_0x00fe:
        r9 = 0;
    L_0x00ff:
        com.google.android.gms.measurement.internal.zzdw.zza(r14, r5, r9);
        r9 = "am";
        r17 = r9.equals(r8);
        r9 = com.google.android.gms.measurement.internal.zzfu.zzda(r30);
        if (r34 == 0) goto L_0x0141;
    L_0x010e:
        r2 = r1.zzarl;
        if (r2 == 0) goto L_0x0141;
    L_0x0112:
        if (r9 != 0) goto L_0x0141;
    L_0x0114:
        if (r17 != 0) goto L_0x0141;
    L_0x0116:
        r0 = r28.zzgt();
        r0 = r0.zzjn();
        r2 = "Passing event to registered event handler (FE)";
        r3 = r28.zzgq();
        r3 = r3.zzbv(r6);
        r4 = r28.zzgq();
        r4 = r4.zzd(r5);
        r0.zze(r2, r3, r4);
        r2 = r1.zzarl;
        r3 = r29;
        r4 = r30;
        r5 = r33;
        r6 = r31;
        r2.interceptEvent(r3, r4, r5, r6);
        return;
    L_0x0141:
        r2 = r1.zzadp;
        r2 = r2.zzkv();
        if (r2 != 0) goto L_0x014a;
    L_0x0149:
        return;
    L_0x014a:
        r2 = r28.zzgr();
        r2 = r2.zzcw(r6);
        if (r2 == 0) goto L_0x0190;
    L_0x0154:
        r3 = r28.zzgt();
        r3 = r3.zzji();
        r4 = "Invalid event name. Event will not be logged (FE)";
        r5 = r28.zzgq();
        r5 = r5.zzbv(r6);
        r3.zzg(r4, r5);
        r28.zzgr();
        r0 = com.google.android.gms.measurement.internal.zzfu.zza(r6, r0, r15);
        if (r6 == 0) goto L_0x0178;
    L_0x0172:
        r3 = r30.length();
        r16 = r3;
    L_0x0178:
        r3 = r1.zzadp;
        r3 = r3.zzgr();
        r4 = "_ev";
        r29 = r3;
        r30 = r37;
        r31 = r2;
        r32 = r4;
        r33 = r0;
        r34 = r16;
        r29.zza(r30, r31, r32, r33, r34);
        return;
    L_0x0190:
        r0 = "_o";
        r2 = "_sn";
        r9 = "_sc";
        r10 = "_si";
        r0 = new java.lang.String[]{r0, r2, r9, r10};
        r0 = com.google.android.gms.common.util.CollectionUtils.listOf(r0);
        r9 = r28.zzgr();
        r2 = 1;
        r10 = r37;
        r11 = r30;
        r12 = r33;
        r13 = r0;
        r18 = r14;
        r14 = r36;
        r5 = 1;
        r15 = r2;
        r2 = r9.zza(r10, r11, r12, r13, r14, r15);
        if (r2 == 0) goto L_0x01ea;
    L_0x01b8:
        r9 = "_sc";
        r9 = r2.containsKey(r9);
        if (r9 == 0) goto L_0x01ea;
    L_0x01c0:
        r9 = "_si";
        r9 = r2.containsKey(r9);
        if (r9 != 0) goto L_0x01c9;
    L_0x01c8:
        goto L_0x01ea;
    L_0x01c9:
        r4 = "_sn";
        r4 = r2.getString(r4);
        r9 = "_sc";
        r9 = r2.getString(r9);
        r10 = "_si";
        r10 = r2.getLong(r10);
        r10 = java.lang.Long.valueOf(r10);
        r11 = new com.google.android.gms.measurement.internal.zzdv;
        r12 = r10.longValue();
        r11.<init>(r4, r9, r12);
        r14 = r11;
        goto L_0x01eb;
    L_0x01ea:
        r14 = r4;
    L_0x01eb:
        if (r14 != 0) goto L_0x01f0;
    L_0x01ed:
        r4 = r18;
        goto L_0x01f1;
    L_0x01f0:
        r4 = r14;
    L_0x01f1:
        r9 = r28.zzgv();
        r9 = r9.zzbm(r7);
        r10 = 0;
        if (r9 == 0) goto L_0x0225;
    L_0x01fd:
        r28.zzgw();
        r9 = r28.zzgm();
        r9 = r9.zzle();
        if (r9 == 0) goto L_0x0225;
    L_0x020a:
        r9 = "_ae";
        r9 = r9.equals(r6);
        if (r9 == 0) goto L_0x0225;
    L_0x0212:
        r9 = r28.zzgo();
        r12 = r9.zzlp();
        r9 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1));
        if (r9 <= 0) goto L_0x0225;
    L_0x021e:
        r9 = r28.zzgr();
        r9.zza(r2, r12);
    L_0x0225:
        r15 = new java.util.ArrayList;
        r15.<init>();
        r15.add(r2);
        r9 = r28.zzgr();
        r9 = r9.zzmk();
        r13 = r9.nextLong();
        r9 = r28.zzgv();
        r12 = r28.zzgk();
        r12 = r12.zzal();
        r9 = r9.zzbl(r12);
        if (r9 == 0) goto L_0x0270;
    L_0x024b:
        r9 = "extend_session";
        r9 = r2.getLong(r9, r10);
        r11 = 1;
        r18 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1));
        if (r18 != 0) goto L_0x0270;
    L_0x0257:
        r9 = r28.zzgt();
        r9 = r9.zzjo();
        r10 = "EXTEND_SESSION param attached: initiate a new session or extend the current active session";
        r9.zzca(r10);
        r9 = r1.zzadp;
        r9 = r9.zzgo();
        r11 = r31;
        r9.zza(r11, r5);
        goto L_0x0272;
    L_0x0270:
        r11 = r31;
    L_0x0272:
        r9 = r2.keySet();
        r10 = r33.size();
        r10 = new java.lang.String[r10];
        r9 = r9.toArray(r10);
        r10 = r9;
        r10 = (java.lang.String[]) r10;
        java.util.Arrays.sort(r10);
        r9 = r10.length;
        r19 = r15;
        r15 = 0;
        r20 = 0;
    L_0x028c:
        if (r15 >= r9) goto L_0x0345;
    L_0x028e:
        r5 = r10[r15];
        r18 = r2.get(r5);
        r28.zzgr();
        r22 = r15;
        r15 = com.google.android.gms.measurement.internal.zzfu.zzf(r18);
        if (r15 == 0) goto L_0x031e;
    L_0x029f:
        r3 = r15.length;
        r2.putInt(r5, r3);
        r3 = 0;
    L_0x02a4:
        r7 = r15.length;
        if (r3 >= r7) goto L_0x0308;
    L_0x02a7:
        r7 = r15[r3];
        r23 = r15;
        r15 = 1;
        com.google.android.gms.measurement.internal.zzdw.zza(r4, r7, r15);
        r18 = r28.zzgr();
        r21 = "_ep";
        r24 = 0;
        r25 = r9;
        r9 = r18;
        r18 = r10;
        r10 = r37;
        r11 = r21;
        r12 = r7;
        r7 = r13;
        r13 = r0;
        r14 = r36;
        r26 = r0;
        r27 = r4;
        r0 = r19;
        r19 = r22;
        r4 = r23;
        r21 = 1;
        r15 = r24;
        r9 = r9.zza(r10, r11, r12, r13, r14, r15);
        r10 = "_en";
        r9.putString(r10, r6);
        r10 = "_eid";
        r9.putLong(r10, r7);
        r10 = "_gn";
        r9.putString(r10, r5);
        r10 = "_ll";
        r11 = r4.length;
        r9.putInt(r10, r11);
        r10 = "_i";
        r9.putInt(r10, r3);
        r0.add(r9);
        r3 = r3 + 1;
        r11 = r31;
        r15 = r4;
        r13 = r7;
        r10 = r18;
        r9 = r25;
        r4 = r27;
        r8 = r29;
        r19 = r0;
        r0 = r26;
        goto L_0x02a4;
    L_0x0308:
        r26 = r0;
        r27 = r4;
        r25 = r9;
        r18 = r10;
        r7 = r13;
        r4 = r15;
        r0 = r19;
        r19 = r22;
        r21 = 1;
        r3 = r4.length;
        r4 = r20;
        r20 = r4 + r3;
        goto L_0x032f;
    L_0x031e:
        r26 = r0;
        r27 = r4;
        r25 = r9;
        r18 = r10;
        r7 = r13;
        r0 = r19;
        r4 = r20;
        r19 = r22;
        r21 = 1;
    L_0x032f:
        r15 = r19 + 1;
        r11 = r31;
        r19 = r0;
        r13 = r7;
        r10 = r18;
        r9 = r25;
        r0 = r26;
        r4 = r27;
        r5 = 1;
        r7 = r37;
        r8 = r29;
        goto L_0x028c;
    L_0x0345:
        r7 = r13;
        r0 = r19;
        r4 = r20;
        r21 = 1;
        if (r4 == 0) goto L_0x0358;
    L_0x034e:
        r3 = "_eid";
        r2.putLong(r3, r7);
        r3 = "_epc";
        r2.putInt(r3, r4);
    L_0x0358:
        r8 = 0;
    L_0x0359:
        r2 = r0.size();
        if (r8 >= r2) goto L_0x03e3;
    L_0x035f:
        r2 = r0.get(r8);
        r2 = (android.os.Bundle) r2;
        if (r8 == 0) goto L_0x0369;
    L_0x0367:
        r3 = 1;
        goto L_0x036a;
    L_0x0369:
        r3 = 0;
    L_0x036a:
        if (r3 == 0) goto L_0x036f;
    L_0x036c:
        r3 = "_ep";
        goto L_0x0370;
    L_0x036f:
        r3 = r6;
    L_0x0370:
        r4 = "_o";
        r9 = r29;
        r2.putString(r4, r9);
        if (r35 == 0) goto L_0x0381;
    L_0x0379:
        r4 = r28.zzgr();
        r2 = r4.zze(r2);
    L_0x0381:
        r11 = r2;
        r2 = r28.zzgt();
        r2 = r2.zzjn();
        r4 = "Logging event (FE)";
        r5 = r28.zzgq();
        r5 = r5.zzbv(r6);
        r7 = r28.zzgq();
        r7 = r7.zzd(r11);
        r2.zze(r4, r5, r7);
        r12 = new com.google.android.gms.measurement.internal.zzae;
        r4 = new com.google.android.gms.measurement.internal.zzab;
        r4.<init>(r11);
        r2 = r12;
        r13 = 1;
        r5 = r29;
        r14 = r6;
        r15 = r37;
        r6 = r31;
        r2.<init>(r3, r4, r5, r6);
        r2 = r28.zzgl();
        r2.zzc(r12, r15);
        if (r17 != 0) goto L_0x03dc;
    L_0x03bb:
        r2 = r1.zzarm;
        r12 = r2.iterator();
    L_0x03c1:
        r2 = r12.hasNext();
        if (r2 == 0) goto L_0x03dc;
    L_0x03c7:
        r2 = r12.next();
        r2 = (com.google.android.gms.measurement.internal.zzcw) r2;
        r5 = new android.os.Bundle;
        r5.<init>(r11);
        r3 = r29;
        r4 = r30;
        r6 = r31;
        r2.onEvent(r3, r4, r5, r6);
        goto L_0x03c1;
    L_0x03dc:
        r8 = r8 + 1;
        r6 = r14;
        r21 = 1;
        goto L_0x0359;
    L_0x03e3:
        r14 = r6;
        r13 = 1;
        r28.zzgw();
        r0 = r28.zzgm();
        r0 = r0.zzle();
        if (r0 == 0) goto L_0x0401;
    L_0x03f2:
        r0 = "_ae";
        r0 = r0.equals(r14);
        if (r0 == 0) goto L_0x0401;
    L_0x03fa:
        r0 = r28.zzgo();
        r0.zza(r13, r13);
    L_0x0401:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzcy.zza(java.lang.String, java.lang.String, long, android.os.Bundle, boolean, boolean, boolean, java.lang.String):void");
    }

    public final void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) {
        boolean z3;
        zzgg();
        String str3 = str == null ? SettingsJsonConstants.APP_KEY : str;
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        if (!z2) {
            zzcy com_google_android_gms_measurement_internal_zzcy = this;
        } else if (this.zzarl != null) {
            if (!zzfu.zzda(str2)) {
                z3 = false;
                zzb(str3, str2, j, bundle2, z2, z3, z ^ 1, null);
            }
        }
        z3 = true;
        zzb(str3, str2, j, bundle2, z2, z3, z ^ 1, null);
    }

    private final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zzgs().zzc(new zzda(this, str, str2, j, zzfu.zzf(bundle), z, z2, z3, str3));
    }

    public final void zzb(String str, String str2, Object obj, boolean z) {
        zza(str, str2, obj, z, zzbx().currentTimeMillis());
    }

    public final void zza(String str, String str2, Object obj, boolean z, long j) {
        if (str == null) {
            str = SettingsJsonConstants.APP_KEY;
        }
        String str3 = str;
        int i = 6;
        int i2 = 0;
        if (z) {
            i = zzgr().zzcx(str2);
        } else {
            z = zzgr();
            if (z.zzs("user property", str2)) {
                if (!z.zza("user property", zzcu.zzarh, str2)) {
                    i = 15;
                } else if (z.zza("user property", 24, str2)) {
                    i = null;
                }
            }
        }
        if (i != null) {
            zzgr();
            String zza = zzfu.zza(str2, 24, true);
            if (str2 != null) {
                i2 = str2.length();
            }
            this.zzadp.zzgr().zza(i, "_ev", zza, i2);
        } else if (obj != null) {
            i = zzgr().zzi(str2, obj);
            if (i != null) {
                zzgr();
                str2 = zzfu.zza(str2, 24, true);
                if ((obj instanceof String) || (obj instanceof CharSequence)) {
                    i2 = String.valueOf(obj).length();
                }
                this.zzadp.zzgr().zza(i, "_ev", str2, i2);
                return;
            }
            Object zzj = zzgr().zzj(str2, obj);
            if (zzj != null) {
                zza(str3, str2, j, zzj);
            }
        } else {
            zza(str3, str2, j, null);
        }
    }

    private final void zza(String str, String str2, long j, Object obj) {
        zzgs().zzc(new zzdb(this, str, str2, obj, j));
    }

    @WorkerThread
    final void zza(String str, String str2, Object obj, long j) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzgg();
        zzcl();
        if (!this.zzadp.isEnabled()) {
            zzgt().zzjn().zzca("User property not set since app measurement is disabled");
        } else if (this.zzadp.zzkv()) {
            zzgt().zzjn().zze("Setting user property (FE)", zzgq().zzbv(str2), obj);
            zzgl().zzb(new zzfr(str2, j, obj, str));
        }
    }

    public final List<zzfr> zzk(boolean z) {
        zzgg();
        zzcl();
        zzgt().zzjn().zzca("Fetching user attributes (FE)");
        if (zzgs().zzkf()) {
            zzgt().zzjg().zzca("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        } else if (zzl.isMainThread()) {
            zzgt().zzjg().zzca("Cannot get all user properties from main thread");
            return Collections.emptyList();
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                this.zzadp.zzgs().zzc(new zzdc(this, atomicReference, z));
                try {
                    atomicReference.wait(5000);
                } catch (boolean z2) {
                    zzgt().zzjj().zzg("Interrupted waiting for get user properties", z2);
                }
            }
            List list = (List) atomicReference.get();
            if (list != null) {
                return list;
            }
            zzgt().zzjj().zzca("Timed out waiting for get user properties");
            return Collections.emptyList();
        }
    }

    @Nullable
    public final String zzgc() {
        zzgg();
        return (String) this.zzaro.get();
    }

    @Nullable
    public final String zzak(long j) {
        if (zzgs().zzkf() != null) {
            zzgt().zzjg().zzca("Cannot retrieve app instance id from analytics worker thread");
            return null;
        } else if (zzl.isMainThread() != null) {
            zzgt().zzjg().zzca("Cannot retrieve app instance id from main thread");
            return null;
        } else {
            j = zzbx().elapsedRealtime();
            String zzal = zzal(120000);
            long elapsedRealtime = zzbx().elapsedRealtime() - j;
            if (zzal == null && elapsedRealtime < 120000) {
                zzal = zzal(120000 - elapsedRealtime);
            }
            return zzal;
        }
    }

    final void zzcr(@Nullable String str) {
        this.zzaro.set(str);
    }

    @androidx.annotation.Nullable
    private final java.lang.String zzal(long r4) {
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
        r3 = this;
        r0 = new java.util.concurrent.atomic.AtomicReference;
        r0.<init>();
        monitor-enter(r0);
        r1 = r3.zzgs();	 Catch:{ all -> 0x002d }
        r2 = new com.google.android.gms.measurement.internal.zzdd;	 Catch:{ all -> 0x002d }
        r2.<init>(r3, r0);	 Catch:{ all -> 0x002d }
        r1.zzc(r2);	 Catch:{ all -> 0x002d }
        r0.wait(r4);	 Catch:{ InterruptedException -> 0x001d }
        monitor-exit(r0);	 Catch:{ all -> 0x002d }
        r4 = r0.get();
        r4 = (java.lang.String) r4;
        return r4;
    L_0x001d:
        r4 = r3.zzgt();	 Catch:{ all -> 0x002d }
        r4 = r4.zzjj();	 Catch:{ all -> 0x002d }
        r5 = "Interrupted waiting for app instance id";	 Catch:{ all -> 0x002d }
        r4.zzca(r5);	 Catch:{ all -> 0x002d }
        r4 = 0;	 Catch:{ all -> 0x002d }
        monitor-exit(r0);	 Catch:{ all -> 0x002d }
        return r4;	 Catch:{ all -> 0x002d }
    L_0x002d:
        r4 = move-exception;	 Catch:{ all -> 0x002d }
        monitor-exit(r0);	 Catch:{ all -> 0x002d }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzcy.zzal(long):java.lang.String");
    }

    public final void resetAnalyticsData(long j) {
        if (zzgv().zza(zzag.zzaln)) {
            zzcr(null);
        }
        zzgs().zzc(new zzde(this, j));
    }

    @WorkerThread
    public final void zzld() {
        zzaf();
        zzgg();
        zzcl();
        if (this.zzadp.zzkv()) {
            zzgl().zzld();
            this.zzarp = false;
            String zzka = zzgu().zzka();
            if (!TextUtils.isEmpty(zzka)) {
                zzgp().zzcl();
                if (!zzka.equals(VERSION.RELEASE)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("_po", zzka);
                    logEvent("auto", "_ou", bundle);
                }
            }
        }
    }

    @WorkerThread
    public final void zza(zzcv com_google_android_gms_measurement_internal_zzcv) {
        zzaf();
        zzgg();
        zzcl();
        if (!(com_google_android_gms_measurement_internal_zzcv == null || com_google_android_gms_measurement_internal_zzcv == this.zzarl)) {
            Preconditions.checkState(this.zzarl == null, "EventInterceptor already set.");
        }
        this.zzarl = com_google_android_gms_measurement_internal_zzcv;
    }

    public final void zza(zzcw com_google_android_gms_measurement_internal_zzcw) {
        zzgg();
        zzcl();
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzcw);
        if (this.zzarm.add(com_google_android_gms_measurement_internal_zzcw) == null) {
            zzgt().zzjj().zzca("OnEventListener already registered");
        }
    }

    public final void zzb(zzcw com_google_android_gms_measurement_internal_zzcw) {
        zzgg();
        zzcl();
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzcw);
        if (this.zzarm.remove(com_google_android_gms_measurement_internal_zzcw) == null) {
            zzgt().zzjj().zzca("OnEventListener had not been registered");
        }
    }

    public final void setConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        zzgg();
        ConditionalUserProperty conditionalUserProperty2 = new ConditionalUserProperty(conditionalUserProperty);
        if (TextUtils.isEmpty(conditionalUserProperty2.mAppId) == null) {
            zzgt().zzjj().zzca("Package name should be null when calling setConditionalUserProperty");
        }
        conditionalUserProperty2.mAppId = null;
        zza(conditionalUserProperty2);
    }

    public final void setConditionalUserPropertyAs(ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mAppId);
        zzgf();
        zza(new ConditionalUserProperty(conditionalUserProperty));
    }

    private final void zza(ConditionalUserProperty conditionalUserProperty) {
        long currentTimeMillis = zzbx().currentTimeMillis();
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mName);
        Preconditions.checkNotEmpty(conditionalUserProperty.mOrigin);
        Preconditions.checkNotNull(conditionalUserProperty.mValue);
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        String str = conditionalUserProperty.mName;
        Object obj = conditionalUserProperty.mValue;
        if (zzgr().zzcx(str) != 0) {
            zzgt().zzjg().zzg("Invalid conditional user property name", zzgq().zzbx(str));
        } else if (zzgr().zzi(str, obj) != 0) {
            zzgt().zzjg().zze("Invalid conditional user property value", zzgq().zzbx(str), obj);
        } else {
            Object zzj = zzgr().zzj(str, obj);
            if (zzj == null) {
                zzgt().zzjg().zze("Unable to normalize conditional user property value", zzgq().zzbx(str), obj);
                return;
            }
            conditionalUserProperty.mValue = zzj;
            long j = conditionalUserProperty.mTriggerTimeout;
            if (TextUtils.isEmpty(conditionalUserProperty.mTriggerEventName) || (j <= 15552000000L && j >= 1)) {
                j = conditionalUserProperty.mTimeToLive;
                if (j <= 15552000000L) {
                    if (j >= 1) {
                        zzgs().zzc(new zzdg(this, conditionalUserProperty));
                        return;
                    }
                }
                zzgt().zzjg().zze("Invalid conditional user property time to live", zzgq().zzbx(str), Long.valueOf(j));
                return;
            }
            zzgt().zzjg().zze("Invalid conditional user property timeout", zzgq().zzbx(str), Long.valueOf(j));
        }
    }

    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        zzgg();
        zza(null, str, str2, bundle);
    }

    public final void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        Preconditions.checkNotEmpty(str);
        zzgf();
        zza(str, str2, str3, bundle);
    }

    private final void zza(String str, String str2, String str3, Bundle bundle) {
        long currentTimeMillis = zzbx().currentTimeMillis();
        Preconditions.checkNotEmpty(str2);
        ConditionalUserProperty conditionalUserProperty = new ConditionalUserProperty();
        conditionalUserProperty.mAppId = str;
        conditionalUserProperty.mName = str2;
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        if (str3 != null) {
            conditionalUserProperty.mExpiredEventName = str3;
            conditionalUserProperty.mExpiredEventParams = bundle;
        }
        zzgs().zzc(new zzdh(this, conditionalUserProperty));
    }

    @androidx.annotation.WorkerThread
    private final void zzb(com.google.android.gms.measurement.AppMeasurement.ConditionalUserProperty r26) {
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
        r25 = this;
        r0 = r26;
        r25.zzaf();
        r25.zzcl();
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r26);
        r1 = r0.mName;
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r1);
        r1 = r0.mOrigin;
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r1);
        r1 = r0.mValue;
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r1);
        r1 = r25;
        r2 = r1.zzadp;
        r2 = r2.isEnabled();
        if (r2 != 0) goto L_0x0032;
    L_0x0024:
        r0 = r25.zzgt();
        r0 = r0.zzjn();
        r2 = "Conditional property not sent since collection is disabled";
        r0.zzca(r2);
        return;
    L_0x0032:
        r2 = new com.google.android.gms.measurement.internal.zzfr;
        r4 = r0.mName;
        r5 = r0.mTriggeredTimestamp;
        r7 = r0.mValue;
        r8 = r0.mOrigin;
        r3 = r2;
        r3.<init>(r4, r5, r7, r8);
        r9 = r25.zzgr();	 Catch:{ IllegalArgumentException -> 0x00ae }
        r10 = r0.mAppId;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r11 = r0.mTriggeredEventName;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r12 = r0.mTriggeredEventParams;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r13 = r0.mOrigin;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r14 = 0;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r16 = 1;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r17 = 0;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r14 = r9.zza(r10, r11, r12, r13, r14, r16, r17);	 Catch:{ IllegalArgumentException -> 0x00ae }
        r3 = r25.zzgr();	 Catch:{ IllegalArgumentException -> 0x00ae }
        r4 = r0.mAppId;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r5 = r0.mTimedOutEventName;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r6 = r0.mTimedOutEventParams;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r7 = r0.mOrigin;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r8 = 0;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r10 = 1;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r11 = 0;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r11 = r3.zza(r4, r5, r6, r7, r8, r10, r11);	 Catch:{ IllegalArgumentException -> 0x00ae }
        r15 = r25.zzgr();	 Catch:{ IllegalArgumentException -> 0x00ae }
        r3 = r0.mAppId;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r4 = r0.mExpiredEventName;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r5 = r0.mExpiredEventParams;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r6 = r0.mOrigin;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r20 = 0;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r22 = 1;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r23 = 0;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r16 = r3;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r17 = r4;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r18 = r5;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r19 = r6;	 Catch:{ IllegalArgumentException -> 0x00ae }
        r17 = r15.zza(r16, r17, r18, r19, r20, r22, r23);	 Catch:{ IllegalArgumentException -> 0x00ae }
        r15 = new com.google.android.gms.measurement.internal.zzm;
        r4 = r0.mAppId;
        r5 = r0.mOrigin;
        r7 = r0.mCreationTimestamp;
        r10 = r0.mTriggerEventName;
        r12 = r0.mTriggerTimeout;
        r24 = r10;
        r9 = r0.mTimeToLive;
        r3 = r15;
        r6 = r2;
        r18 = r9;
        r0 = 0;
        r9 = r0;
        r10 = r24;
        r0 = r15;
        r15 = r18;
        r3.<init>(r4, r5, r6, r7, r9, r10, r11, r12, r14, r15, r17);
        r2 = r25.zzgl();
        r2.zzd(r0);
        return;
    L_0x00ae:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzcy.zzb(com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty):void");
    }

    @androidx.annotation.WorkerThread
    private final void zzc(com.google.android.gms.measurement.AppMeasurement.ConditionalUserProperty r23) {
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
        r22 = this;
        r0 = r23;
        r22.zzaf();
        r22.zzcl();
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r23);
        r1 = r0.mName;
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r1);
        r1 = r22;
        r2 = r1.zzadp;
        r2 = r2.isEnabled();
        if (r2 != 0) goto L_0x0028;
    L_0x001a:
        r0 = r22.zzgt();
        r0 = r0.zzjn();
        r2 = "Conditional property not cleared since collection is disabled";
        r0.zzca(r2);
        return;
    L_0x0028:
        r2 = new com.google.android.gms.measurement.internal.zzfr;
        r4 = r0.mName;
        r5 = 0;
        r7 = 0;
        r8 = 0;
        r3 = r2;
        r3.<init>(r4, r5, r7, r8);
        r9 = r22.zzgr();	 Catch:{ IllegalArgumentException -> 0x0073 }
        r10 = r0.mAppId;	 Catch:{ IllegalArgumentException -> 0x0073 }
        r11 = r0.mExpiredEventName;	 Catch:{ IllegalArgumentException -> 0x0073 }
        r12 = r0.mExpiredEventParams;	 Catch:{ IllegalArgumentException -> 0x0073 }
        r13 = r0.mOrigin;	 Catch:{ IllegalArgumentException -> 0x0073 }
        r14 = r0.mCreationTimestamp;	 Catch:{ IllegalArgumentException -> 0x0073 }
        r16 = 1;	 Catch:{ IllegalArgumentException -> 0x0073 }
        r17 = 0;	 Catch:{ IllegalArgumentException -> 0x0073 }
        r17 = r9.zza(r10, r11, r12, r13, r14, r16, r17);	 Catch:{ IllegalArgumentException -> 0x0073 }
        r15 = new com.google.android.gms.measurement.internal.zzm;
        r4 = r0.mAppId;
        r5 = r0.mOrigin;
        r7 = r0.mCreationTimestamp;
        r9 = r0.mActive;
        r10 = r0.mTriggerEventName;
        r12 = r0.mTriggerTimeout;
        r14 = 0;
        r18 = r12;
        r11 = r0.mTimeToLive;
        r3 = r15;
        r6 = r2;
        r20 = r11;
        r0 = 0;
        r11 = r0;
        r12 = r18;
        r0 = r15;
        r15 = r20;
        r3.<init>(r4, r5, r6, r7, r9, r10, r11, r12, r14, r15, r17);
        r2 = r22.zzgl();
        r2.zzd(r0);
        return;
    L_0x0073:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzcy.zzc(com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty):void");
    }

    public final List<ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        zzgg();
        return zzf(null, str, str2);
    }

    public final List<ConditionalUserProperty> getConditionalUserPropertiesAs(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzgf();
        return zzf(str, str2, str3);
    }

    @VisibleForTesting
    private final List<ConditionalUserProperty> zzf(String str, String str2, String str3) {
        if (zzgs().zzkf()) {
            zzgt().zzjg().zzca("Cannot get conditional user properties from analytics worker thread");
            return Collections.emptyList();
        } else if (zzl.isMainThread()) {
            zzgt().zzjg().zzca("Cannot get conditional user properties from main thread");
            return Collections.emptyList();
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                this.zzadp.zzgs().zzc(new zzdi(this, atomicReference, str, str2, str3));
                try {
                    atomicReference.wait(5000);
                } catch (String str22) {
                    zzgt().zzjj().zze("Interrupted waiting for get conditional user properties", str, str22);
                }
            }
            List<zzm> list = (List) atomicReference.get();
            if (list == null) {
                zzgt().zzjj().zzg("Timed out waiting for get conditional user properties", str);
                return Collections.emptyList();
            }
            str = new ArrayList(list.size());
            for (zzm com_google_android_gms_measurement_internal_zzm : list) {
                ConditionalUserProperty conditionalUserProperty = new ConditionalUserProperty();
                conditionalUserProperty.mAppId = com_google_android_gms_measurement_internal_zzm.packageName;
                conditionalUserProperty.mOrigin = com_google_android_gms_measurement_internal_zzm.origin;
                conditionalUserProperty.mCreationTimestamp = com_google_android_gms_measurement_internal_zzm.creationTimestamp;
                conditionalUserProperty.mName = com_google_android_gms_measurement_internal_zzm.zzahe.name;
                conditionalUserProperty.mValue = com_google_android_gms_measurement_internal_zzm.zzahe.getValue();
                conditionalUserProperty.mActive = com_google_android_gms_measurement_internal_zzm.active;
                conditionalUserProperty.mTriggerEventName = com_google_android_gms_measurement_internal_zzm.triggerEventName;
                if (com_google_android_gms_measurement_internal_zzm.zzahf != null) {
                    conditionalUserProperty.mTimedOutEventName = com_google_android_gms_measurement_internal_zzm.zzahf.name;
                    if (com_google_android_gms_measurement_internal_zzm.zzahf.zzaig != null) {
                        conditionalUserProperty.mTimedOutEventParams = com_google_android_gms_measurement_internal_zzm.zzahf.zzaig.zziy();
                    }
                }
                conditionalUserProperty.mTriggerTimeout = com_google_android_gms_measurement_internal_zzm.triggerTimeout;
                if (com_google_android_gms_measurement_internal_zzm.zzahg != null) {
                    conditionalUserProperty.mTriggeredEventName = com_google_android_gms_measurement_internal_zzm.zzahg.name;
                    if (com_google_android_gms_measurement_internal_zzm.zzahg.zzaig != null) {
                        conditionalUserProperty.mTriggeredEventParams = com_google_android_gms_measurement_internal_zzm.zzahg.zzaig.zziy();
                    }
                }
                conditionalUserProperty.mTriggeredTimestamp = com_google_android_gms_measurement_internal_zzm.zzahe.zzaux;
                conditionalUserProperty.mTimeToLive = com_google_android_gms_measurement_internal_zzm.timeToLive;
                if (com_google_android_gms_measurement_internal_zzm.zzahh != null) {
                    conditionalUserProperty.mExpiredEventName = com_google_android_gms_measurement_internal_zzm.zzahh.name;
                    if (com_google_android_gms_measurement_internal_zzm.zzahh.zzaig != null) {
                        conditionalUserProperty.mExpiredEventParams = com_google_android_gms_measurement_internal_zzm.zzahh.zzaig.zziy();
                    }
                }
                str.add(conditionalUserProperty);
            }
            return str;
        }
    }

    public final Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        zzgg();
        return zzb(null, str, str2, z);
    }

    public final Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        Preconditions.checkNotEmpty(str);
        zzgf();
        return zzb(str, str2, str3, z);
    }

    @VisibleForTesting
    private final Map<String, Object> zzb(String str, String str2, String str3, boolean z) {
        if (zzgs().zzkf()) {
            zzgt().zzjg().zzca("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        } else if (zzl.isMainThread()) {
            zzgt().zzjg().zzca("Cannot get user properties from main thread");
            return Collections.emptyMap();
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                this.zzadp.zzgs().zzc(new zzdk(this, atomicReference, str, str2, str3, z));
                try {
                    atomicReference.wait(5000);
                } catch (String str4) {
                    zzgt().zzjj().zzg("Interrupted waiting for get user properties", str4);
                }
            }
            List<zzfr> list = (List) atomicReference.get();
            if (list == null) {
                zzgt().zzjj().zzca("Timed out waiting for get user properties");
                return Collections.emptyMap();
            }
            str2 = new ArrayMap(list.size());
            for (zzfr com_google_android_gms_measurement_internal_zzfr : list) {
                str2.put(com_google_android_gms_measurement_internal_zzfr.name, com_google_android_gms_measurement_internal_zzfr.getValue());
            }
            return str2;
        }
    }

    @Nullable
    public final String getCurrentScreenName() {
        zzdv zzlf = this.zzadp.zzgm().zzlf();
        return zzlf != null ? zzlf.zzuw : null;
    }

    @Nullable
    public final String getCurrentScreenClass() {
        zzdv zzlf = this.zzadp.zzgm().zzlf();
        return zzlf != null ? zzlf.zzasa : null;
    }

    @Nullable
    public final String getGmpAppId() {
        if (this.zzadp.zzko() != null) {
            return this.zzadp.zzko();
        }
        try {
            return GoogleServices.getGoogleAppId();
        } catch (IllegalStateException e) {
            this.zzadp.zzgt().zzjg().zzg("getGoogleAppId failed with exception", e);
            return null;
        }
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
