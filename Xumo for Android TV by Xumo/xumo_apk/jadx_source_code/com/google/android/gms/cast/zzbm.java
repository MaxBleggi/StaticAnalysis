package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.cast.zzco;
import org.json.JSONObject;

final class zzbm extends zzb {
    private final /* synthetic */ long val$position;
    private final /* synthetic */ RemoteMediaPlayer zzfr;
    private final /* synthetic */ GoogleApiClient zzfs;
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ int zzgk;

    zzbm(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, long j, int i, JSONObject jSONObject) {
        this.zzfr = remoteMediaPlayer;
        this.zzfs = googleApiClient2;
        this.val$position = j;
        this.zzgk = i;
        this.zzfy = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(com.google.android.gms.internal.cast.zzco r9) {
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
        r9 = r8.zzfr;
        r9 = r9.lock;
        monitor-enter(r9);
        r0 = r8.zzfr;	 Catch:{ all -> 0x0055 }
        r0 = r0.zzfm;	 Catch:{ all -> 0x0055 }
        r1 = r8.zzfs;	 Catch:{ all -> 0x0055 }
        r0.zza(r1);	 Catch:{ all -> 0x0055 }
        r0 = 0;
        r1 = r8.zzfr;	 Catch:{ IllegalStateException -> 0x0030, IllegalStateException -> 0x0030 }
        r2 = r1.zzfl;	 Catch:{ IllegalStateException -> 0x0030, IllegalStateException -> 0x0030 }
        r3 = r8.zzgr;	 Catch:{ IllegalStateException -> 0x0030, IllegalStateException -> 0x0030 }
        r4 = r8.val$position;	 Catch:{ IllegalStateException -> 0x0030, IllegalStateException -> 0x0030 }
        r6 = r8.zzgk;	 Catch:{ IllegalStateException -> 0x0030, IllegalStateException -> 0x0030 }
        r7 = r8.zzfy;	 Catch:{ IllegalStateException -> 0x0030, IllegalStateException -> 0x0030 }
        r2.zza(r3, r4, r6, r7);	 Catch:{ IllegalStateException -> 0x0030, IllegalStateException -> 0x0030 }
        r1 = r8.zzfr;	 Catch:{ all -> 0x0055 }
        r1 = r1.zzfm;	 Catch:{ all -> 0x0055 }
        r1.zza(r0);	 Catch:{ all -> 0x0055 }
        goto L_0x0049;
    L_0x002e:
        r1 = move-exception;
        goto L_0x004b;
    L_0x0030:
        r1 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x002e }
        r2 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x002e }
        r1.<init>(r2);	 Catch:{ all -> 0x002e }
        r1 = r8.createFailedResult(r1);	 Catch:{ all -> 0x002e }
        r1 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r1;	 Catch:{ all -> 0x002e }
        r8.setResult(r1);	 Catch:{ all -> 0x002e }
        r1 = r8.zzfr;	 Catch:{ all -> 0x0055 }
        r1 = r1.zzfm;	 Catch:{ all -> 0x0055 }
        r1.zza(r0);	 Catch:{ all -> 0x0055 }
    L_0x0049:
        monitor-exit(r9);	 Catch:{ all -> 0x0055 }
        return;	 Catch:{ all -> 0x0055 }
    L_0x004b:
        r2 = r8.zzfr;	 Catch:{ all -> 0x0055 }
        r2 = r2.zzfm;	 Catch:{ all -> 0x0055 }
        r2.zza(r0);	 Catch:{ all -> 0x0055 }
        throw r1;	 Catch:{ all -> 0x0055 }
    L_0x0055:
        r0 = move-exception;	 Catch:{ all -> 0x0055 }
        monitor-exit(r9);	 Catch:{ all -> 0x0055 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzbm.zza(com.google.android.gms.internal.cast.zzco):void");
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzco) anyClient);
    }
}
