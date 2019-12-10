package com.google.android.gms.internal.cast;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplaySessionCallbacks;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;

@Deprecated
public final class zzec extends GmsClient<zzeg> implements DeathRecipient {
    private static final zzdh zzbe = new zzdh("CastRemoteDisplayClientImpl");
    private CastDevice zzai;
    private CastRemoteDisplaySessionCallbacks zzzk;
    private Bundle zzzl;

    public zzec(Context context, Looper looper, ClientSettings clientSettings, CastDevice castDevice, Bundle bundle, CastRemoteDisplaySessionCallbacks castRemoteDisplaySessionCallbacks, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 83, clientSettings, connectionCallbacks, onConnectionFailedListener);
        zzbe.d("instance created", new Object[null]);
        this.zzzk = castRemoteDisplaySessionCallbacks;
        this.zzai = castDevice;
        this.zzzl = bundle;
    }

    public final void binderDied() {
    }

    public final int getMinApkVersion() {
        return GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    protected final String getServiceDescriptor() {
        return "com.google.android.gms.cast.remote_display.ICastRemoteDisplayService";
    }

    protected final String getStartServiceAction() {
        return "com.google.android.gms.cast.remote_display.service.START";
    }

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
        r0 = zzbe;
        r1 = "disconnect";
        r2 = 0;
        r2 = new java.lang.Object[r2];
        r0.d(r1, r2);
        r0 = 0;
        r3.zzzk = r0;
        r3.zzai = r0;
        r0 = r3.getService();	 Catch:{ RemoteException -> 0x0021, RemoteException -> 0x0021, all -> 0x001c }
        r0 = (com.google.android.gms.internal.cast.zzeg) r0;	 Catch:{ RemoteException -> 0x0021, RemoteException -> 0x0021, all -> 0x001c }
        r0.disconnect();	 Catch:{ RemoteException -> 0x0021, RemoteException -> 0x0021, all -> 0x001c }
        super.disconnect();
        return;
    L_0x001c:
        r0 = move-exception;
        super.disconnect();
        throw r0;
    L_0x0021:
        super.disconnect();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzec.disconnect():void");
    }

    public final void zza(zzee com_google_android_gms_internal_cast_zzee, zzei com_google_android_gms_internal_cast_zzei, String str) throws RemoteException {
        zzbe.d("startRemoteDisplay", new Object[0]);
        zzee com_google_android_gms_internal_cast_zzee2 = com_google_android_gms_internal_cast_zzee;
        ((zzeg) getService()).zza(com_google_android_gms_internal_cast_zzee2, new zzed(this, com_google_android_gms_internal_cast_zzei), this.zzai.getDeviceId(), str, this.zzzl);
    }

    public final void zza(zzee com_google_android_gms_internal_cast_zzee) throws RemoteException {
        zzbe.d("stopRemoteDisplay", new Object[0]);
        ((zzeg) getService()).zza(com_google_android_gms_internal_cast_zzee);
    }

    public final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.remote_display.ICastRemoteDisplayService");
        if (queryLocalInterface instanceof zzeg) {
            return (zzeg) queryLocalInterface;
        }
        return new zzeh(iBinder);
    }
}
