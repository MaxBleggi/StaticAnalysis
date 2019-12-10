package com.google.android.gms.cast;

import android.content.Context;
import android.content.ServiceConnection;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.Options;

final class zzw implements ServiceConnection {
    private final /* synthetic */ String zzaf;
    private final /* synthetic */ CastDevice zzci;
    private final /* synthetic */ Options zzcj;
    private final /* synthetic */ NotificationSettings zzck;
    private final /* synthetic */ Context zzcl;
    private final /* synthetic */ Callbacks zzcm;

    zzw(String str, CastDevice castDevice, Options options, NotificationSettings notificationSettings, Context context, Callbacks callbacks) {
        this.zzaf = str;
        this.zzci = castDevice;
        this.zzcj = options;
        this.zzck = notificationSettings;
        this.zzcl = context;
        this.zzcm = callbacks;
    }

    public final void onServiceConnected(android.content.ComponentName r9, android.os.IBinder r10) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = this;
        r10 = (com.google.android.gms.cast.CastRemoteDisplayLocalService.zza) r10;
        r0 = r10.zzch;
        if (r0 == 0) goto L_0x0019;
    L_0x0006:
        r1 = r8.zzaf;
        r2 = r8.zzci;
        r3 = r8.zzcj;
        r4 = r8.zzck;
        r5 = r8.zzcl;
        r7 = r8.zzcm;
        r6 = r8;
        r9 = r0.zza(r1, r2, r3, r4, r5, r6, r7);
        if (r9 != 0) goto L_0x0049;
    L_0x0019:
        r9 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzbe;
        r10 = "Connected but unable to get the service instance";
        r0 = 0;
        r1 = new java.lang.Object[r0];
        r9.e(r10, r1);
        r9 = r8.zzcm;
        r10 = new com.google.android.gms.common.api.Status;
        r1 = 2200; // 0x898 float:3.083E-42 double:1.087E-320;
        r10.<init>(r1);
        r9.onRemoteDisplaySessionError(r10);
        r9 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzbq;
        r9.set(r0);
        r9 = r8.zzcl;	 Catch:{ IllegalArgumentException -> 0x003e }
        r9.unbindService(r8);	 Catch:{ IllegalArgumentException -> 0x003e }
        return;
    L_0x003e:
        r9 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzbe;
        r10 = "No need to unbind service, already unbound";
        r0 = new java.lang.Object[r0];
        r9.d(r10, r0);
    L_0x0049:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzw.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
    }

    public final void onServiceDisconnected(android.content.ComponentName r5) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = this;
        r5 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzbe;
        r0 = "onServiceDisconnected";
        r1 = 0;
        r2 = new java.lang.Object[r1];
        r5.d(r0, r2);
        r5 = r4.zzcm;
        r0 = new com.google.android.gms.common.api.Status;
        r2 = "Service Disconnected";
        r3 = 2201; // 0x899 float:3.084E-42 double:1.0874E-320;
        r0.<init>(r3, r2);
        r5.onRemoteDisplaySessionError(r0);
        r5 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzbq;
        r5.set(r1);
        r5 = r4.zzcl;	 Catch:{ IllegalArgumentException -> 0x0027 }
        r5.unbindService(r4);	 Catch:{ IllegalArgumentException -> 0x0027 }
        return;
    L_0x0027:
        r5 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzbe;
        r0 = "No need to unbind service, already unbound";
        r1 = new java.lang.Object[r1];
        r5.d(r0, r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzw.onServiceDisconnected(android.content.ComponentName):void");
    }
}
