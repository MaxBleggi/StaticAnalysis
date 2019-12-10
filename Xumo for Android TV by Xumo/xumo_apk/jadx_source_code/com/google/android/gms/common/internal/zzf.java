package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.HashSet;
import java.util.Set;

final class zzf implements ServiceConnection {
    private ComponentName mComponentName;
    private int mState = 2;
    private IBinder zzcy;
    private final Set<ServiceConnection> zzdz = new HashSet();
    private boolean zzea;
    private final zza zzeb;
    private final /* synthetic */ zze zzec;

    public zzf(zze com_google_android_gms_common_internal_zze, zza com_google_android_gms_common_internal_GmsClientSupervisor_zza) {
        this.zzec = com_google_android_gms_common_internal_zze;
        this.zzeb = com_google_android_gms_common_internal_GmsClientSupervisor_zza;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.zzec.zzdu) {
            this.zzec.mHandler.removeMessages(1, this.zzeb);
            this.zzcy = iBinder;
            this.mComponentName = componentName;
            for (ServiceConnection onServiceConnected : this.zzdz) {
                onServiceConnected.onServiceConnected(componentName, iBinder);
            }
            this.mState = 1;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zzec.zzdu) {
            this.zzec.mHandler.removeMessages(1, this.zzeb);
            this.zzcy = null;
            this.mComponentName = componentName;
            for (ServiceConnection onServiceDisconnected : this.zzdz) {
                onServiceDisconnected.onServiceDisconnected(componentName);
            }
            this.mState = 2;
        }
    }

    public final void zze(java.lang.String r8) {
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
        r7 = this;
        r0 = 3;
        r7.mState = r0;
        r0 = r7.zzec;
        r1 = r0.zzdw;
        r0 = r7.zzec;
        r2 = r0.zzdv;
        r0 = r7.zzeb;
        r3 = r7.zzec;
        r3 = r3.zzdv;
        r4 = r0.zzb(r3);
        r0 = r7.zzeb;
        r6 = r0.zzq();
        r3 = r8;
        r5 = r7;
        r8 = r1.zza(r2, r3, r4, r5, r6);
        r7.zzea = r8;
        r8 = r7.zzea;
        if (r8 == 0) goto L_0x004a;
    L_0x002d:
        r8 = r7.zzec;
        r8 = r8.mHandler;
        r0 = 1;
        r1 = r7.zzeb;
        r8 = r8.obtainMessage(r0, r1);
        r0 = r7.zzec;
        r0 = r0.mHandler;
        r1 = r7.zzec;
        r1 = r1.zzdy;
        r0.sendMessageDelayed(r8, r1);
        return;
    L_0x004a:
        r8 = 2;
        r7.mState = r8;
        r8 = r7.zzec;	 Catch:{ IllegalArgumentException -> 0x005d }
        r8 = r8.zzdw;	 Catch:{ IllegalArgumentException -> 0x005d }
        r0 = r7.zzec;	 Catch:{ IllegalArgumentException -> 0x005d }
        r0 = r0.zzdv;	 Catch:{ IllegalArgumentException -> 0x005d }
        r8.unbindService(r0, r7);	 Catch:{ IllegalArgumentException -> 0x005d }
        return;
    L_0x005d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzf.zze(java.lang.String):void");
    }

    public final void zzf(String str) {
        this.zzec.mHandler.removeMessages(1, this.zzeb);
        this.zzec.zzdw.unbindService(this.zzec.zzdv, this);
        this.zzea = null;
        this.mState = 2;
    }

    public final void zza(ServiceConnection serviceConnection, String str) {
        this.zzec.zzdw;
        this.zzec.zzdv;
        this.zzeb.zzb(this.zzec.zzdv);
        this.zzdz.add(serviceConnection);
    }

    public final void zzb(ServiceConnection serviceConnection, String str) {
        this.zzec.zzdw;
        this.zzec.zzdv;
        this.zzdz.remove(serviceConnection);
    }

    public final boolean isBound() {
        return this.zzea;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean zza(ServiceConnection serviceConnection) {
        return this.zzdz.contains(serviceConnection);
    }

    public final boolean zzr() {
        return this.zzdz.isEmpty();
    }

    public final IBinder getBinder() {
        return this.zzcy;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }
}
