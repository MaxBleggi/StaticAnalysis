package com.google.android.gms.cast.framework.media;

import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

final class zzar extends zzc {
    private final /* synthetic */ long val$position;
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ int zzgk;
    private final /* synthetic */ RemoteMediaClient zzpe;

    zzar(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, long j, int i, JSONObject jSONObject) {
        this.zzpe = remoteMediaClient;
        this.val$position = j;
        this.zzgk = i;
        this.zzfy = jSONObject;
        super(remoteMediaClient, googleApiClient);
    }

    protected final void zzb(com.google.android.gms.internal.cast.zzco r8) {
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
        r8 = r7.zzpe;
        r8 = r8.lock;
        monitor-enter(r8);
        r0 = r7.zzpe;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r1 = r0.zzfl;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r2 = r7.zzgr;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r3 = r7.val$position;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r5 = r7.zzgk;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r6 = r7.zzfy;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r1.zza(r2, r3, r5, r6);	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        goto L_0x002b;
    L_0x0019:
        r0 = move-exception;
        goto L_0x002d;
    L_0x001b:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x0019 }
        r1 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x0019 }
        r0.<init>(r1);	 Catch:{ all -> 0x0019 }
        r0 = r7.createFailedResult(r0);	 Catch:{ all -> 0x0019 }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x0019 }
        r7.setResult(r0);	 Catch:{ all -> 0x0019 }
    L_0x002b:
        monitor-exit(r8);	 Catch:{ all -> 0x0019 }
        return;	 Catch:{ all -> 0x0019 }
    L_0x002d:
        monitor-exit(r8);	 Catch:{ all -> 0x0019 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzar.zzb(com.google.android.gms.internal.cast.zzco):void");
    }
}
