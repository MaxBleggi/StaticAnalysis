package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.core.view.PointerIconCompat;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzef;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@VisibleForTesting
public final class zzdz extends zzf {
    private final zzeo zzaso;
    private zzah zzasp;
    private volatile Boolean zzasq;
    private final zzw zzasr;
    private final zzff zzass;
    private final List<Runnable> zzast = new ArrayList();
    private final zzw zzasu;

    protected zzdz(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
        this.zzass = new zzff(com_google_android_gms_measurement_internal_zzbu.zzbx());
        this.zzaso = new zzeo(this);
        this.zzasr = new zzea(this, com_google_android_gms_measurement_internal_zzbu);
        this.zzasu = new zzeg(this, com_google_android_gms_measurement_internal_zzbu);
    }

    protected final boolean zzgy() {
        return false;
    }

    @WorkerThread
    public final boolean isConnected() {
        zzaf();
        zzcl();
        return this.zzasp != null;
    }

    @WorkerThread
    protected final void zzlg() {
        zzaf();
        zzcl();
        zzf(new zzeh(this, zzl(true)));
    }

    @WorkerThread
    @VisibleForTesting
    final void zza(zzah com_google_android_gms_measurement_internal_zzah, AbstractSafeParcelable abstractSafeParcelable, zzi com_google_android_gms_measurement_internal_zzi) {
        zzaf();
        zzgg();
        zzcl();
        boolean zzlh = zzlh();
        int i = 0;
        int i2 = 100;
        while (i < PointerIconCompat.TYPE_CONTEXT_MENU && r4 == 100) {
            int size;
            ArrayList arrayList;
            int size2;
            int i3;
            AbstractSafeParcelable abstractSafeParcelable2;
            List arrayList2 = new ArrayList();
            if (zzlh) {
                Object zzr = zzgn().zzr(100);
                if (zzr != null) {
                    arrayList2.addAll(zzr);
                    size = zzr.size();
                    if (abstractSafeParcelable != null && size < 100) {
                        arrayList2.add(abstractSafeParcelable);
                    }
                    arrayList = (ArrayList) arrayList2;
                    size2 = arrayList.size();
                    i3 = 0;
                    while (i3 < size2) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        abstractSafeParcelable2 = (AbstractSafeParcelable) obj;
                        if (abstractSafeParcelable2 instanceof zzae) {
                            try {
                                com_google_android_gms_measurement_internal_zzah.zza((zzae) abstractSafeParcelable2, com_google_android_gms_measurement_internal_zzi);
                            } catch (RemoteException e) {
                                zzgt().zzjg().zzg("Failed to send event to the service", e);
                            }
                        } else if (abstractSafeParcelable2 instanceof zzfr) {
                            try {
                                com_google_android_gms_measurement_internal_zzah.zza((zzfr) abstractSafeParcelable2, com_google_android_gms_measurement_internal_zzi);
                            } catch (RemoteException e2) {
                                zzgt().zzjg().zzg("Failed to send attribute to the service", e2);
                            }
                        } else if (abstractSafeParcelable2 instanceof zzm) {
                            zzgt().zzjg().zzca("Discarding data. Unrecognized parcel type.");
                        } else {
                            try {
                                com_google_android_gms_measurement_internal_zzah.zza((zzm) abstractSafeParcelable2, com_google_android_gms_measurement_internal_zzi);
                            } catch (RemoteException e22) {
                                zzgt().zzjg().zzg("Failed to send conditional property to the service", e22);
                            }
                        }
                    }
                    i++;
                    i2 = size;
                }
            }
            size = 0;
            arrayList2.add(abstractSafeParcelable);
            arrayList = (ArrayList) arrayList2;
            size2 = arrayList.size();
            i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList.get(i3);
                i3++;
                abstractSafeParcelable2 = (AbstractSafeParcelable) obj2;
                if (abstractSafeParcelable2 instanceof zzae) {
                    com_google_android_gms_measurement_internal_zzah.zza((zzae) abstractSafeParcelable2, com_google_android_gms_measurement_internal_zzi);
                } else if (abstractSafeParcelable2 instanceof zzfr) {
                    com_google_android_gms_measurement_internal_zzah.zza((zzfr) abstractSafeParcelable2, com_google_android_gms_measurement_internal_zzi);
                } else if (abstractSafeParcelable2 instanceof zzm) {
                    zzgt().zzjg().zzca("Discarding data. Unrecognized parcel type.");
                } else {
                    com_google_android_gms_measurement_internal_zzah.zza((zzm) abstractSafeParcelable2, com_google_android_gms_measurement_internal_zzi);
                }
            }
            i++;
            i2 = size;
        }
    }

    @WorkerThread
    protected final void zzc(zzae com_google_android_gms_measurement_internal_zzae, String str) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzae);
        zzaf();
        zzcl();
        boolean zzlh = zzlh();
        boolean z = zzlh && zzgn().zza(com_google_android_gms_measurement_internal_zzae);
        zzf(new zzei(this, zzlh, z, com_google_android_gms_measurement_internal_zzae, zzl(true), str));
    }

    @WorkerThread
    protected final void zzd(zzm com_google_android_gms_measurement_internal_zzm) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm);
        zzaf();
        zzcl();
        zzgw();
        zzf(new zzej(this, true, zzgn().zzc(com_google_android_gms_measurement_internal_zzm), new zzm(com_google_android_gms_measurement_internal_zzm), zzl(true), com_google_android_gms_measurement_internal_zzm));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzm>> atomicReference, String str, String str2, String str3) {
        zzaf();
        zzcl();
        zzf(new zzek(this, atomicReference, str, str2, str3, zzl(false)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzfr>> atomicReference, String str, String str2, String str3, boolean z) {
        zzaf();
        zzcl();
        zzf(new zzel(this, atomicReference, str, str2, str3, z, zzl(false)));
    }

    @WorkerThread
    protected final void zzb(zzfr com_google_android_gms_measurement_internal_zzfr) {
        zzaf();
        zzcl();
        boolean z = zzlh() && zzgn().zza(com_google_android_gms_measurement_internal_zzfr);
        zzf(new zzem(this, z, com_google_android_gms_measurement_internal_zzfr, zzl(true)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzfr>> atomicReference, boolean z) {
        zzaf();
        zzcl();
        zzf(new zzen(this, atomicReference, zzl(false), z));
    }

    @WorkerThread
    protected final void resetAnalyticsData() {
        zzaf();
        zzgg();
        zzcl();
        zzi zzl = zzl(false);
        if (zzlh()) {
            zzgn().resetAnalyticsData();
        }
        zzf(new zzeb(this, zzl));
    }

    private final boolean zzlh() {
        zzgw();
        return true;
    }

    @WorkerThread
    public final void zza(AtomicReference<String> atomicReference) {
        zzaf();
        zzcl();
        zzf(new zzec(this, atomicReference, zzl(false)));
    }

    @WorkerThread
    public final void getAppInstanceId(zzef com_google_android_gms_internal_measurement_zzef) {
        zzaf();
        zzcl();
        zzf(new zzed(this, zzl(false), com_google_android_gms_internal_measurement_zzef));
    }

    @WorkerThread
    protected final void zzld() {
        zzaf();
        zzcl();
        zzf(new zzee(this, zzl(true)));
    }

    @WorkerThread
    protected final void zza(zzdv com_google_android_gms_measurement_internal_zzdv) {
        zzaf();
        zzcl();
        zzf(new zzef(this, com_google_android_gms_measurement_internal_zzdv));
    }

    @WorkerThread
    private final void zzcy() {
        zzaf();
        this.zzass.start();
        this.zzasr.zzh(((Long) zzag.zzakm.get()).longValue());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @androidx.annotation.WorkerThread
    final void zzdj() {
        /*
        r6 = this;
        r6.zzaf();
        r6.zzcl();
        r0 = r6.isConnected();
        if (r0 == 0) goto L_0x000d;
    L_0x000c:
        return;
    L_0x000d:
        r0 = r6.zzasq;
        r1 = 0;
        r2 = 1;
        if (r0 != 0) goto L_0x011c;
    L_0x0013:
        r6.zzaf();
        r6.zzcl();
        r0 = r6.zzgu();
        r0 = r0.zzjx();
        if (r0 == 0) goto L_0x002c;
    L_0x0023:
        r0 = r0.booleanValue();
        if (r0 == 0) goto L_0x002c;
    L_0x0029:
        r0 = 1;
        goto L_0x0116;
    L_0x002c:
        r6.zzgw();
        r0 = r6.zzgk();
        r0 = r0.zzje();
        if (r0 != r2) goto L_0x003d;
    L_0x0039:
        r0 = 1;
    L_0x003a:
        r3 = 1;
        goto L_0x00f3;
    L_0x003d:
        r0 = r6.zzgt();
        r0 = r0.zzjo();
        r3 = "Checking service availability";
        r0.zzca(r3);
        r0 = r6.zzgr();
        r3 = com.google.android.gms.common.GoogleApiAvailabilityLight.getInstance();
        r0 = r0.getContext();
        r4 = 12451000; // 0xbdfcb8 float:1.7447567E-38 double:6.1516114E-317;
        r0 = r3.isGooglePlayServicesAvailable(r0, r4);
        r3 = 9;
        if (r0 == r3) goto L_0x00e5;
    L_0x0061:
        r3 = 18;
        if (r0 == r3) goto L_0x00d6;
    L_0x0065:
        switch(r0) {
            case 0: goto L_0x00c7;
            case 1: goto L_0x00b7;
            case 2: goto L_0x008b;
            case 3: goto L_0x007d;
            default: goto L_0x0068;
        };
    L_0x0068:
        r3 = r6.zzgt();
        r3 = r3.zzjj();
        r4 = "Unexpected service status";
        r0 = java.lang.Integer.valueOf(r0);
        r3.zzg(r4, r0);
    L_0x0079:
        r0 = 0;
    L_0x007a:
        r3 = 0;
        goto L_0x00f3;
    L_0x007d:
        r0 = r6.zzgt();
        r0 = r0.zzjj();
        r3 = "Service disabled";
        r0.zzca(r3);
        goto L_0x0079;
    L_0x008b:
        r0 = r6.zzgt();
        r0 = r0.zzjn();
        r3 = "Service container out of date";
        r0.zzca(r3);
        r0 = r6.zzgr();
        r0 = r0.zzml();
        r3 = 14500; // 0x38a4 float:2.0319E-41 double:7.164E-320;
        if (r0 >= r3) goto L_0x00a5;
    L_0x00a4:
        goto L_0x00c4;
    L_0x00a5:
        r0 = r6.zzgu();
        r0 = r0.zzjx();
        if (r0 == 0) goto L_0x00b5;
    L_0x00af:
        r0 = r0.booleanValue();
        if (r0 == 0) goto L_0x0079;
    L_0x00b5:
        r0 = 1;
        goto L_0x007a;
    L_0x00b7:
        r0 = r6.zzgt();
        r0 = r0.zzjo();
        r3 = "Service missing";
        r0.zzca(r3);
    L_0x00c4:
        r0 = 0;
        goto L_0x003a;
    L_0x00c7:
        r0 = r6.zzgt();
        r0 = r0.zzjo();
        r3 = "Service available";
        r0.zzca(r3);
        goto L_0x0039;
    L_0x00d6:
        r0 = r6.zzgt();
        r0 = r0.zzjj();
        r3 = "Service updating";
        r0.zzca(r3);
        goto L_0x0039;
    L_0x00e5:
        r0 = r6.zzgt();
        r0 = r0.zzjj();
        r3 = "Service invalid";
        r0.zzca(r3);
        goto L_0x0079;
    L_0x00f3:
        if (r0 != 0) goto L_0x010d;
    L_0x00f5:
        r4 = r6.zzgv();
        r4 = r4.zzif();
        if (r4 == 0) goto L_0x010d;
    L_0x00ff:
        r3 = r6.zzgt();
        r3 = r3.zzjg();
        r4 = "No way to upload. Consider using the full version of Analytics";
        r3.zzca(r4);
        r3 = 0;
    L_0x010d:
        if (r3 == 0) goto L_0x0116;
    L_0x010f:
        r3 = r6.zzgu();
        r3.zzg(r0);
    L_0x0116:
        r0 = java.lang.Boolean.valueOf(r0);
        r6.zzasq = r0;
    L_0x011c:
        r0 = r6.zzasq;
        r0 = r0.booleanValue();
        if (r0 == 0) goto L_0x012a;
    L_0x0124:
        r0 = r6.zzaso;
        r0.zzll();
        return;
    L_0x012a:
        r0 = r6.zzgv();
        r0 = r0.zzif();
        if (r0 != 0) goto L_0x018a;
    L_0x0134:
        r6.zzgw();
        r0 = r6.getContext();
        r0 = r0.getPackageManager();
        r3 = new android.content.Intent;
        r3.<init>();
        r4 = r6.getContext();
        r5 = "com.google.android.gms.measurement.AppMeasurementService";
        r3 = r3.setClassName(r4, r5);
        r4 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r0 = r0.queryIntentServices(r3, r4);
        if (r0 == 0) goto L_0x015d;
    L_0x0156:
        r0 = r0.size();
        if (r0 <= 0) goto L_0x015d;
    L_0x015c:
        r1 = 1;
    L_0x015d:
        if (r1 == 0) goto L_0x017d;
    L_0x015f:
        r0 = new android.content.Intent;
        r1 = "com.google.android.gms.measurement.START";
        r0.<init>(r1);
        r1 = new android.content.ComponentName;
        r2 = r6.getContext();
        r6.zzgw();
        r3 = "com.google.android.gms.measurement.AppMeasurementService";
        r1.<init>(r2, r3);
        r0.setComponent(r1);
        r1 = r6.zzaso;
        r1.zzc(r0);
        return;
    L_0x017d:
        r0 = r6.zzgt();
        r0 = r0.zzjg();
        r1 = "Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest";
        r0.zzca(r1);
    L_0x018a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzdz.zzdj():void");
    }

    final Boolean zzli() {
        return this.zzasq;
    }

    @WorkerThread
    @VisibleForTesting
    protected final void zza(zzah com_google_android_gms_measurement_internal_zzah) {
        zzaf();
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzah);
        this.zzasp = com_google_android_gms_measurement_internal_zzah;
        zzcy();
        zzlj();
    }

    @androidx.annotation.WorkerThread
    public final void disconnect() {
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
        r3.zzaf();
        r3.zzcl();
        r0 = r3.zzaso;
        r0.zzlk();
        r0 = com.google.android.gms.common.stats.ConnectionTracker.getInstance();	 Catch:{ IllegalStateException -> 0x0018, IllegalStateException -> 0x0018 }
        r1 = r3.getContext();	 Catch:{ IllegalStateException -> 0x0018, IllegalStateException -> 0x0018 }
        r2 = r3.zzaso;	 Catch:{ IllegalStateException -> 0x0018, IllegalStateException -> 0x0018 }
        r0.unbindService(r1, r2);	 Catch:{ IllegalStateException -> 0x0018, IllegalStateException -> 0x0018 }
    L_0x0018:
        r0 = 0;
        r3.zzasp = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzdz.disconnect():void");
    }

    @WorkerThread
    private final void onServiceDisconnected(ComponentName componentName) {
        zzaf();
        if (this.zzasp != null) {
            this.zzasp = null;
            zzgt().zzjo().zzg("Disconnected from device MeasurementService", componentName);
            zzaf();
            zzdj();
        }
    }

    @WorkerThread
    private final void zzcz() {
        zzaf();
        if (isConnected()) {
            zzgt().zzjo().zzca("Inactivity, disconnecting from the service");
            disconnect();
        }
    }

    @WorkerThread
    private final void zzf(Runnable runnable) throws IllegalStateException {
        zzaf();
        if (isConnected()) {
            runnable.run();
        } else if (((long) this.zzast.size()) >= 1000) {
            zzgt().zzjg().zzca("Discarding data. Max runnable queue size reached");
        } else {
            this.zzast.add(runnable);
            this.zzasu.zzh(DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS);
            zzdj();
        }
    }

    @WorkerThread
    private final void zzlj() {
        zzaf();
        zzgt().zzjo().zzg("Processing queued up service tasks", Integer.valueOf(this.zzast.size()));
        for (Runnable run : this.zzast) {
            try {
                run.run();
            } catch (Exception e) {
                zzgt().zzjg().zzg("Task exception while flushing queue", e);
            }
        }
        this.zzast.clear();
        this.zzasu.cancel();
    }

    @WorkerThread
    @Nullable
    private final zzi zzl(boolean z) {
        zzgw();
        return zzgk().zzbu(z ? zzgt().zzjq() : false);
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
