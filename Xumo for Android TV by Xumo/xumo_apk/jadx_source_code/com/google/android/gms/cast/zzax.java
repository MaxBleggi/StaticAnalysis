package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.cast.zzco;
import org.json.JSONObject;

final class zzax extends zzb {
    private final /* synthetic */ RemoteMediaPlayer zzfr;
    private final /* synthetic */ GoogleApiClient zzfs;
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ MediaQueueItem[] zzfz;
    private final /* synthetic */ int zzga;

    zzax(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, MediaQueueItem[] mediaQueueItemArr, int i, JSONObject jSONObject) {
        this.zzfr = remoteMediaPlayer;
        this.zzfs = googleApiClient2;
        this.zzfz = mediaQueueItemArr;
        this.zzga = i;
        this.zzfy = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(com.google.android.gms.internal.cast.zzco r12) {
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
        r11 = this;
        r12 = r11.zzfr;
        r12 = r12.lock;
        monitor-enter(r12);
        r0 = r11.zzfr;	 Catch:{ all -> 0x0059 }
        r0 = r0.zzfm;	 Catch:{ all -> 0x0059 }
        r1 = r11.zzfs;	 Catch:{ all -> 0x0059 }
        r0.zza(r1);	 Catch:{ all -> 0x0059 }
        r0 = 0;
        r1 = r11.zzfr;	 Catch:{ IllegalStateException -> 0x0034, IllegalStateException -> 0x0034 }
        r2 = r1.zzfl;	 Catch:{ IllegalStateException -> 0x0034, IllegalStateException -> 0x0034 }
        r3 = r11.zzgr;	 Catch:{ IllegalStateException -> 0x0034, IllegalStateException -> 0x0034 }
        r4 = r11.zzfz;	 Catch:{ IllegalStateException -> 0x0034, IllegalStateException -> 0x0034 }
        r5 = r11.zzga;	 Catch:{ IllegalStateException -> 0x0034, IllegalStateException -> 0x0034 }
        r6 = 0;	 Catch:{ IllegalStateException -> 0x0034, IllegalStateException -> 0x0034 }
        r7 = -1;	 Catch:{ IllegalStateException -> 0x0034, IllegalStateException -> 0x0034 }
        r8 = -1;	 Catch:{ IllegalStateException -> 0x0034, IllegalStateException -> 0x0034 }
        r10 = r11.zzfy;	 Catch:{ IllegalStateException -> 0x0034, IllegalStateException -> 0x0034 }
        r2.zza(r3, r4, r5, r6, r7, r8, r10);	 Catch:{ IllegalStateException -> 0x0034, IllegalStateException -> 0x0034 }
        r1 = r11.zzfr;	 Catch:{ all -> 0x0059 }
        r1 = r1.zzfm;	 Catch:{ all -> 0x0059 }
        r1.zza(r0);	 Catch:{ all -> 0x0059 }
        goto L_0x004d;
    L_0x0032:
        r1 = move-exception;
        goto L_0x004f;
    L_0x0034:
        r1 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x0032 }
        r2 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x0032 }
        r1.<init>(r2);	 Catch:{ all -> 0x0032 }
        r1 = r11.createFailedResult(r1);	 Catch:{ all -> 0x0032 }
        r1 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r1;	 Catch:{ all -> 0x0032 }
        r11.setResult(r1);	 Catch:{ all -> 0x0032 }
        r1 = r11.zzfr;	 Catch:{ all -> 0x0059 }
        r1 = r1.zzfm;	 Catch:{ all -> 0x0059 }
        r1.zza(r0);	 Catch:{ all -> 0x0059 }
    L_0x004d:
        monitor-exit(r12);	 Catch:{ all -> 0x0059 }
        return;	 Catch:{ all -> 0x0059 }
    L_0x004f:
        r2 = r11.zzfr;	 Catch:{ all -> 0x0059 }
        r2 = r2.zzfm;	 Catch:{ all -> 0x0059 }
        r2.zza(r0);	 Catch:{ all -> 0x0059 }
        throw r1;	 Catch:{ all -> 0x0059 }
    L_0x0059:
        r0 = move-exception;	 Catch:{ all -> 0x0059 }
        monitor-exit(r12);	 Catch:{ all -> 0x0059 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzax.zza(com.google.android.gms.internal.cast.zzco):void");
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzco) anyClient);
    }
}
