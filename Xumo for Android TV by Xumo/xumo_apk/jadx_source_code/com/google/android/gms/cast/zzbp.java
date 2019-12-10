package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.cast.zzco;

final class zzbp extends zzb {
    private final /* synthetic */ RemoteMediaPlayer zzfr;
    private final /* synthetic */ GoogleApiClient zzfs;

    zzbp(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2) {
        this.zzfr = remoteMediaPlayer;
        this.zzfs = googleApiClient2;
        super(googleApiClient);
    }

    protected final void zza(com.google.android.gms.internal.cast.zzco r4) {
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
        r3 = this;
        r4 = r3.zzfr;
        r4 = r4.lock;
        monitor-enter(r4);
        r0 = r3.zzfr;	 Catch:{ all -> 0x004f }
        r0 = r0.zzfm;	 Catch:{ all -> 0x004f }
        r1 = r3.zzfs;	 Catch:{ all -> 0x004f }
        r0.zza(r1);	 Catch:{ all -> 0x004f }
        r0 = 0;
        r1 = r3.zzfr;	 Catch:{ IllegalStateException -> 0x002a }
        r1 = r1.zzfl;	 Catch:{ IllegalStateException -> 0x002a }
        r2 = r3.zzgr;	 Catch:{ IllegalStateException -> 0x002a }
        r1.zzb(r2);	 Catch:{ IllegalStateException -> 0x002a }
        r1 = r3.zzfr;	 Catch:{ all -> 0x004f }
        r1 = r1.zzfm;	 Catch:{ all -> 0x004f }
        r1.zza(r0);	 Catch:{ all -> 0x004f }
        goto L_0x0043;
    L_0x0028:
        r1 = move-exception;
        goto L_0x0045;
    L_0x002a:
        r1 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x0028 }
        r2 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x0028 }
        r1.<init>(r2);	 Catch:{ all -> 0x0028 }
        r1 = r3.createFailedResult(r1);	 Catch:{ all -> 0x0028 }
        r1 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r1;	 Catch:{ all -> 0x0028 }
        r3.setResult(r1);	 Catch:{ all -> 0x0028 }
        r1 = r3.zzfr;	 Catch:{ all -> 0x004f }
        r1 = r1.zzfm;	 Catch:{ all -> 0x004f }
        r1.zza(r0);	 Catch:{ all -> 0x004f }
    L_0x0043:
        monitor-exit(r4);	 Catch:{ all -> 0x004f }
        return;	 Catch:{ all -> 0x004f }
    L_0x0045:
        r2 = r3.zzfr;	 Catch:{ all -> 0x004f }
        r2 = r2.zzfm;	 Catch:{ all -> 0x004f }
        r2.zza(r0);	 Catch:{ all -> 0x004f }
        throw r1;	 Catch:{ all -> 0x004f }
    L_0x004f:
        r0 = move-exception;	 Catch:{ all -> 0x004f }
        monitor-exit(r4);	 Catch:{ all -> 0x004f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzbp.zza(com.google.android.gms.internal.cast.zzco):void");
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzco) anyClient);
    }
}
