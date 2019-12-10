package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.cast.zzco;
import org.json.JSONObject;

final class zzba extends zzb {
    private final /* synthetic */ RemoteMediaPlayer zzfr;
    private final /* synthetic */ GoogleApiClient zzfs;
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ int[] zzgd;

    zzba(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, int[] iArr, JSONObject jSONObject) {
        this.zzfr = remoteMediaPlayer;
        this.zzfs = googleApiClient2;
        this.zzgd = iArr;
        this.zzfy = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(com.google.android.gms.internal.cast.zzco r6) {
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
        r5 = this;
        r6 = r5.zzfr;
        r6 = r6.lock;
        monitor-enter(r6);
        r0 = r5.zzfr;	 Catch:{ all -> 0x0053 }
        r0 = r0.zzfm;	 Catch:{ all -> 0x0053 }
        r1 = r5.zzfs;	 Catch:{ all -> 0x0053 }
        r0.zza(r1);	 Catch:{ all -> 0x0053 }
        r0 = 0;
        r1 = r5.zzfr;	 Catch:{ IllegalStateException -> 0x002e, IllegalStateException -> 0x002e }
        r1 = r1.zzfl;	 Catch:{ IllegalStateException -> 0x002e, IllegalStateException -> 0x002e }
        r2 = r5.zzgr;	 Catch:{ IllegalStateException -> 0x002e, IllegalStateException -> 0x002e }
        r3 = r5.zzgd;	 Catch:{ IllegalStateException -> 0x002e, IllegalStateException -> 0x002e }
        r4 = r5.zzfy;	 Catch:{ IllegalStateException -> 0x002e, IllegalStateException -> 0x002e }
        r1.zza(r2, r3, r4);	 Catch:{ IllegalStateException -> 0x002e, IllegalStateException -> 0x002e }
        r1 = r5.zzfr;	 Catch:{ all -> 0x0053 }
        r1 = r1.zzfm;	 Catch:{ all -> 0x0053 }
        r1.zza(r0);	 Catch:{ all -> 0x0053 }
        goto L_0x0047;
    L_0x002c:
        r1 = move-exception;
        goto L_0x0049;
    L_0x002e:
        r1 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x002c }
        r2 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x002c }
        r1.<init>(r2);	 Catch:{ all -> 0x002c }
        r1 = r5.createFailedResult(r1);	 Catch:{ all -> 0x002c }
        r1 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r1;	 Catch:{ all -> 0x002c }
        r5.setResult(r1);	 Catch:{ all -> 0x002c }
        r1 = r5.zzfr;	 Catch:{ all -> 0x0053 }
        r1 = r1.zzfm;	 Catch:{ all -> 0x0053 }
        r1.zza(r0);	 Catch:{ all -> 0x0053 }
    L_0x0047:
        monitor-exit(r6);	 Catch:{ all -> 0x0053 }
        return;	 Catch:{ all -> 0x0053 }
    L_0x0049:
        r2 = r5.zzfr;	 Catch:{ all -> 0x0053 }
        r2 = r2.zzfm;	 Catch:{ all -> 0x0053 }
        r2.zza(r0);	 Catch:{ all -> 0x0053 }
        throw r1;	 Catch:{ all -> 0x0053 }
    L_0x0053:
        r0 = move-exception;	 Catch:{ all -> 0x0053 }
        monitor-exit(r6);	 Catch:{ all -> 0x0053 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzba.zza(com.google.android.gms.internal.cast.zzco):void");
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzco) anyClient);
    }
}
