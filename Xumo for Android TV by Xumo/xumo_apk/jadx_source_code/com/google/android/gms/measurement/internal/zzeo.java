package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Looper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks;
import com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.VisibleForTesting;

@VisibleForTesting
public final class zzeo implements ServiceConnection, BaseConnectionCallbacks, BaseOnConnectionFailedListener {
    final /* synthetic */ zzdz zzasv;
    private volatile boolean zzatb;
    private volatile zzap zzatc;

    protected zzeo(zzdz com_google_android_gms_measurement_internal_zzdz) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
    }

    @WorkerThread
    public final void zzc(Intent intent) {
        this.zzasv.zzaf();
        Context context = this.zzasv.getContext();
        ConnectionTracker instance = ConnectionTracker.getInstance();
        synchronized (this) {
            if (this.zzatb) {
                this.zzasv.zzgt().zzjo().zzca("Connection attempt already in progress");
                return;
            }
            this.zzasv.zzgt().zzjo().zzca("Using local app measurement service");
            this.zzatb = true;
            instance.bindService(context, intent, this.zzasv.zzaso, TsExtractor.TS_STREAM_TYPE_AC3);
        }
    }

    @WorkerThread
    public final void zzlk() {
        if (this.zzatc != null && (this.zzatc.isConnected() || this.zzatc.isConnecting())) {
            this.zzatc.disconnect();
        }
        this.zzatc = null;
    }

    @androidx.annotation.MainThread
    public final void onServiceConnected(android.content.ComponentName r4, android.os.IBinder r5) {
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
        r4 = "MeasurementServiceConnection.onServiceConnected";
        com.google.android.gms.common.internal.Preconditions.checkMainThread(r4);
        monitor-enter(r3);
        r4 = 0;
        if (r5 != 0) goto L_0x001f;
    L_0x0009:
        r3.zzatb = r4;	 Catch:{ all -> 0x001c }
        r4 = r3.zzasv;	 Catch:{ all -> 0x001c }
        r4 = r4.zzgt();	 Catch:{ all -> 0x001c }
        r4 = r4.zzjg();	 Catch:{ all -> 0x001c }
        r5 = "Service connected with null binder";	 Catch:{ all -> 0x001c }
        r4.zzca(r5);	 Catch:{ all -> 0x001c }
        monitor-exit(r3);	 Catch:{ all -> 0x001c }
        return;
    L_0x001c:
        r4 = move-exception;
        goto L_0x009a;
    L_0x001f:
        r0 = 0;
        r1 = r5.getInterfaceDescriptor();	 Catch:{ RemoteException -> 0x0063 }
        r2 = "com.google.android.gms.measurement.internal.IMeasurementService";	 Catch:{ RemoteException -> 0x0063 }
        r2 = r2.equals(r1);	 Catch:{ RemoteException -> 0x0063 }
        if (r2 == 0) goto L_0x0053;	 Catch:{ RemoteException -> 0x0063 }
    L_0x002c:
        if (r5 != 0) goto L_0x002f;	 Catch:{ RemoteException -> 0x0063 }
    L_0x002e:
        goto L_0x0043;	 Catch:{ RemoteException -> 0x0063 }
    L_0x002f:
        r1 = "com.google.android.gms.measurement.internal.IMeasurementService";	 Catch:{ RemoteException -> 0x0063 }
        r1 = r5.queryLocalInterface(r1);	 Catch:{ RemoteException -> 0x0063 }
        r2 = r1 instanceof com.google.android.gms.measurement.internal.zzah;	 Catch:{ RemoteException -> 0x0063 }
        if (r2 == 0) goto L_0x003d;	 Catch:{ RemoteException -> 0x0063 }
    L_0x0039:
        r1 = (com.google.android.gms.measurement.internal.zzah) r1;	 Catch:{ RemoteException -> 0x0063 }
    L_0x003b:
        r0 = r1;	 Catch:{ RemoteException -> 0x0063 }
        goto L_0x0043;	 Catch:{ RemoteException -> 0x0063 }
    L_0x003d:
        r1 = new com.google.android.gms.measurement.internal.zzaj;	 Catch:{ RemoteException -> 0x0063 }
        r1.<init>(r5);	 Catch:{ RemoteException -> 0x0063 }
        goto L_0x003b;	 Catch:{ RemoteException -> 0x0063 }
    L_0x0043:
        r5 = r3.zzasv;	 Catch:{ RemoteException -> 0x0063 }
        r5 = r5.zzgt();	 Catch:{ RemoteException -> 0x0063 }
        r5 = r5.zzjo();	 Catch:{ RemoteException -> 0x0063 }
        r1 = "Bound to IMeasurementService interface";	 Catch:{ RemoteException -> 0x0063 }
        r5.zzca(r1);	 Catch:{ RemoteException -> 0x0063 }
        goto L_0x0072;	 Catch:{ RemoteException -> 0x0063 }
    L_0x0053:
        r5 = r3.zzasv;	 Catch:{ RemoteException -> 0x0063 }
        r5 = r5.zzgt();	 Catch:{ RemoteException -> 0x0063 }
        r5 = r5.zzjg();	 Catch:{ RemoteException -> 0x0063 }
        r2 = "Got binder with a wrong descriptor";	 Catch:{ RemoteException -> 0x0063 }
        r5.zzg(r2, r1);	 Catch:{ RemoteException -> 0x0063 }
        goto L_0x0072;
    L_0x0063:
        r5 = r3.zzasv;	 Catch:{ all -> 0x001c }
        r5 = r5.zzgt();	 Catch:{ all -> 0x001c }
        r5 = r5.zzjg();	 Catch:{ all -> 0x001c }
        r1 = "Service connect failed to get IMeasurementService";	 Catch:{ all -> 0x001c }
        r5.zzca(r1);	 Catch:{ all -> 0x001c }
    L_0x0072:
        if (r0 != 0) goto L_0x008a;	 Catch:{ all -> 0x001c }
    L_0x0074:
        r3.zzatb = r4;	 Catch:{ all -> 0x001c }
        r4 = com.google.android.gms.common.stats.ConnectionTracker.getInstance();	 Catch:{ IllegalArgumentException -> 0x0098 }
        r5 = r3.zzasv;	 Catch:{ IllegalArgumentException -> 0x0098 }
        r5 = r5.getContext();	 Catch:{ IllegalArgumentException -> 0x0098 }
        r0 = r3.zzasv;	 Catch:{ IllegalArgumentException -> 0x0098 }
        r0 = r0.zzaso;	 Catch:{ IllegalArgumentException -> 0x0098 }
        r4.unbindService(r5, r0);	 Catch:{ IllegalArgumentException -> 0x0098 }
        goto L_0x0098;
    L_0x008a:
        r4 = r3.zzasv;	 Catch:{ all -> 0x001c }
        r4 = r4.zzgs();	 Catch:{ all -> 0x001c }
        r5 = new com.google.android.gms.measurement.internal.zzep;	 Catch:{ all -> 0x001c }
        r5.<init>(r3, r0);	 Catch:{ all -> 0x001c }
        r4.zzc(r5);	 Catch:{ all -> 0x001c }
    L_0x0098:
        monitor-exit(r3);	 Catch:{ all -> 0x001c }
        return;	 Catch:{ all -> 0x001c }
    L_0x009a:
        monitor-exit(r3);	 Catch:{ all -> 0x001c }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzeo.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
    }

    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceDisconnected");
        this.zzasv.zzgt().zzjn().zzca("Service disconnected");
        this.zzasv.zzgs().zzc(new zzeq(this, componentName));
    }

    @WorkerThread
    public final void zzll() {
        this.zzasv.zzaf();
        Context context = this.zzasv.getContext();
        synchronized (this) {
            if (this.zzatb) {
                this.zzasv.zzgt().zzjo().zzca("Connection attempt already in progress");
            } else if (this.zzatc == null || !(this.zzatc.isConnecting() || this.zzatc.isConnected())) {
                this.zzatc = new zzap(context, Looper.getMainLooper(), this, this);
                this.zzasv.zzgt().zzjo().zzca("Connecting to remote service");
                this.zzatb = true;
                this.zzatc.checkAvailabilityAndConnect();
            } else {
                this.zzasv.zzgt().zzjo().zzca("Already awaiting connection attempt");
            }
        }
    }

    @androidx.annotation.MainThread
    public final void onConnected(@androidx.annotation.Nullable android.os.Bundle r3) {
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
        r3 = "MeasurementServiceConnection.onConnected";
        com.google.android.gms.common.internal.Preconditions.checkMainThread(r3);
        monitor-enter(r2);
        r3 = r2.zzatc;	 Catch:{ DeadObjectException -> 0x001f, DeadObjectException -> 0x001f }
        r3 = r3.getService();	 Catch:{ DeadObjectException -> 0x001f, DeadObjectException -> 0x001f }
        r3 = (com.google.android.gms.measurement.internal.zzah) r3;	 Catch:{ DeadObjectException -> 0x001f, DeadObjectException -> 0x001f }
        r0 = r2.zzasv;	 Catch:{ DeadObjectException -> 0x001f, DeadObjectException -> 0x001f }
        r0 = r0.zzgs();	 Catch:{ DeadObjectException -> 0x001f, DeadObjectException -> 0x001f }
        r1 = new com.google.android.gms.measurement.internal.zzer;	 Catch:{ DeadObjectException -> 0x001f, DeadObjectException -> 0x001f }
        r1.<init>(r2, r3);	 Catch:{ DeadObjectException -> 0x001f, DeadObjectException -> 0x001f }
        r0.zzc(r1);	 Catch:{ DeadObjectException -> 0x001f, DeadObjectException -> 0x001f }
        goto L_0x0025;
    L_0x001d:
        r3 = move-exception;
        goto L_0x0027;
    L_0x001f:
        r3 = 0;
        r2.zzatc = r3;	 Catch:{ all -> 0x001d }
        r3 = 0;	 Catch:{ all -> 0x001d }
        r2.zzatb = r3;	 Catch:{ all -> 0x001d }
    L_0x0025:
        monitor-exit(r2);	 Catch:{ all -> 0x001d }
        return;	 Catch:{ all -> 0x001d }
    L_0x0027:
        monitor-exit(r2);	 Catch:{ all -> 0x001d }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzeo.onConnected(android.os.Bundle):void");
    }

    @MainThread
    public final void onConnectionSuspended(int i) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionSuspended");
        this.zzasv.zzgt().zzjn().zzca("Service connection suspended");
        this.zzasv.zzgs().zzc(new zzes(this));
    }

    @MainThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionFailed");
        zzaq zzkj = this.zzasv.zzadp.zzkj();
        if (zzkj != null) {
            zzkj.zzjj().zzg("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzatb = null;
            this.zzatc = null;
        }
        this.zzasv.zzgs().zzc(new zzet(this));
    }
}
