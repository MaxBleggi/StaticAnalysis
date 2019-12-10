package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.cast.zzco;
import org.json.JSONObject;

final class zzbg extends zzb {
    private final /* synthetic */ RemoteMediaPlayer zzfr;
    private final /* synthetic */ GoogleApiClient zzfs;
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ int zzgi;

    zzbg(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, int i, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
        this.zzfr = remoteMediaPlayer;
        this.zzgi = i;
        this.zzfs = googleApiClient2;
        this.zzfy = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(com.google.android.gms.internal.cast.zzco r7) {
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
        r6 = this;
        r7 = r6.zzfr;
        r7 = r7.lock;
        monitor-enter(r7);
        r0 = r6.zzfr;	 Catch:{ all -> 0x0074 }
        r1 = r6.zzgi;	 Catch:{ all -> 0x0074 }
        r0 = r0.zzc(r1);	 Catch:{ all -> 0x0074 }
        r1 = -1;	 Catch:{ all -> 0x0074 }
        r2 = 0;	 Catch:{ all -> 0x0074 }
        if (r0 != r1) goto L_0x0023;	 Catch:{ all -> 0x0074 }
    L_0x0013:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x0074 }
        r0.<init>(r2);	 Catch:{ all -> 0x0074 }
        r0 = r6.createFailedResult(r0);	 Catch:{ all -> 0x0074 }
        r0 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r0;	 Catch:{ all -> 0x0074 }
        r6.setResult(r0);	 Catch:{ all -> 0x0074 }
        monitor-exit(r7);	 Catch:{ all -> 0x0074 }
        return;	 Catch:{ all -> 0x0074 }
    L_0x0023:
        r0 = r6.zzfr;	 Catch:{ all -> 0x0074 }
        r0 = r0.zzfm;	 Catch:{ all -> 0x0074 }
        r1 = r6.zzfs;	 Catch:{ all -> 0x0074 }
        r0.zza(r1);	 Catch:{ all -> 0x0074 }
        r0 = 0;
        r1 = r6.zzfr;	 Catch:{ IllegalStateException -> 0x004f, IllegalStateException -> 0x004f }
        r1 = r1.zzfl;	 Catch:{ IllegalStateException -> 0x004f, IllegalStateException -> 0x004f }
        r3 = r6.zzgr;	 Catch:{ IllegalStateException -> 0x004f, IllegalStateException -> 0x004f }
        r4 = 1;	 Catch:{ IllegalStateException -> 0x004f, IllegalStateException -> 0x004f }
        r4 = new int[r4];	 Catch:{ IllegalStateException -> 0x004f, IllegalStateException -> 0x004f }
        r5 = r6.zzgi;	 Catch:{ IllegalStateException -> 0x004f, IllegalStateException -> 0x004f }
        r4[r2] = r5;	 Catch:{ IllegalStateException -> 0x004f, IllegalStateException -> 0x004f }
        r2 = r6.zzfy;	 Catch:{ IllegalStateException -> 0x004f, IllegalStateException -> 0x004f }
        r1.zza(r3, r4, r2);	 Catch:{ IllegalStateException -> 0x004f, IllegalStateException -> 0x004f }
        r1 = r6.zzfr;	 Catch:{ all -> 0x0074 }
        r1 = r1.zzfm;	 Catch:{ all -> 0x0074 }
        r1.zza(r0);	 Catch:{ all -> 0x0074 }
        goto L_0x0068;
    L_0x004d:
        r1 = move-exception;
        goto L_0x006a;
    L_0x004f:
        r1 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x004d }
        r2 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x004d }
        r1.<init>(r2);	 Catch:{ all -> 0x004d }
        r1 = r6.createFailedResult(r1);	 Catch:{ all -> 0x004d }
        r1 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r1;	 Catch:{ all -> 0x004d }
        r6.setResult(r1);	 Catch:{ all -> 0x004d }
        r1 = r6.zzfr;	 Catch:{ all -> 0x0074 }
        r1 = r1.zzfm;	 Catch:{ all -> 0x0074 }
        r1.zza(r0);	 Catch:{ all -> 0x0074 }
    L_0x0068:
        monitor-exit(r7);	 Catch:{ all -> 0x0074 }
        return;	 Catch:{ all -> 0x0074 }
    L_0x006a:
        r2 = r6.zzfr;	 Catch:{ all -> 0x0074 }
        r2 = r2.zzfm;	 Catch:{ all -> 0x0074 }
        r2.zza(r0);	 Catch:{ all -> 0x0074 }
        throw r1;	 Catch:{ all -> 0x0074 }
    L_0x0074:
        r0 = move-exception;	 Catch:{ all -> 0x0074 }
        monitor-exit(r7);	 Catch:{ all -> 0x0074 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzbg.zza(com.google.android.gms.internal.cast.zzco):void");
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzco) anyClient);
    }
}
