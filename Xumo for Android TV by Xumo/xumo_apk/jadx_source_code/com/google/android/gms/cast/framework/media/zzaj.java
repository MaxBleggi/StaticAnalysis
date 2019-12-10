package com.google.android.gms.cast.framework.media;

import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

final class zzaj extends zzc {
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ int zzgi;
    private final /* synthetic */ int zzgj;
    private final /* synthetic */ RemoteMediaClient zzpe;

    zzaj(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, int i, int i2, JSONObject jSONObject) {
        this.zzpe = remoteMediaClient;
        this.zzgi = i;
        this.zzgj = i2;
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
        r0 = r7.zzpe;	 Catch:{ all -> 0x00a2 }
        r1 = r7.zzgi;	 Catch:{ all -> 0x00a2 }
        r0 = r0.zzc(r1);	 Catch:{ all -> 0x00a2 }
        r1 = -1;	 Catch:{ all -> 0x00a2 }
        r2 = 0;	 Catch:{ all -> 0x00a2 }
        if (r0 != r1) goto L_0x0023;	 Catch:{ all -> 0x00a2 }
    L_0x0013:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x00a2 }
        r0.<init>(r2);	 Catch:{ all -> 0x00a2 }
        r0 = r7.createFailedResult(r0);	 Catch:{ all -> 0x00a2 }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x00a2 }
        r7.setResult(r0);	 Catch:{ all -> 0x00a2 }
        monitor-exit(r8);	 Catch:{ all -> 0x00a2 }
        return;	 Catch:{ all -> 0x00a2 }
    L_0x0023:
        r1 = r7.zzgj;	 Catch:{ all -> 0x00a2 }
        r3 = 1;	 Catch:{ all -> 0x00a2 }
        if (r1 >= 0) goto L_0x004c;	 Catch:{ all -> 0x00a2 }
    L_0x0028:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x00a2 }
        r1 = 2001; // 0x7d1 float:2.804E-42 double:9.886E-321;	 Catch:{ all -> 0x00a2 }
        r4 = java.util.Locale.ROOT;	 Catch:{ all -> 0x00a2 }
        r5 = "Invalid request: Invalid newIndex %d.";	 Catch:{ all -> 0x00a2 }
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x00a2 }
        r6 = r7.zzgj;	 Catch:{ all -> 0x00a2 }
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ all -> 0x00a2 }
        r3[r2] = r6;	 Catch:{ all -> 0x00a2 }
        r2 = java.lang.String.format(r4, r5, r3);	 Catch:{ all -> 0x00a2 }
        r0.<init>(r1, r2);	 Catch:{ all -> 0x00a2 }
        r0 = r7.createFailedResult(r0);	 Catch:{ all -> 0x00a2 }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x00a2 }
        r7.setResult(r0);	 Catch:{ all -> 0x00a2 }
        monitor-exit(r8);	 Catch:{ all -> 0x00a2 }
        return;	 Catch:{ all -> 0x00a2 }
    L_0x004c:
        r1 = r7.zzgj;	 Catch:{ all -> 0x00a2 }
        if (r0 != r1) goto L_0x0060;	 Catch:{ all -> 0x00a2 }
    L_0x0050:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x00a2 }
        r0.<init>(r2);	 Catch:{ all -> 0x00a2 }
        r0 = r7.createFailedResult(r0);	 Catch:{ all -> 0x00a2 }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x00a2 }
        r7.setResult(r0);	 Catch:{ all -> 0x00a2 }
        monitor-exit(r8);	 Catch:{ all -> 0x00a2 }
        return;	 Catch:{ all -> 0x00a2 }
    L_0x0060:
        r1 = r7.zzgj;	 Catch:{ all -> 0x00a2 }
        if (r1 <= r0) goto L_0x0068;	 Catch:{ all -> 0x00a2 }
    L_0x0064:
        r0 = r7.zzgj;	 Catch:{ all -> 0x00a2 }
        r0 = r0 + r3;	 Catch:{ all -> 0x00a2 }
        goto L_0x006a;	 Catch:{ all -> 0x00a2 }
    L_0x0068:
        r0 = r7.zzgj;	 Catch:{ all -> 0x00a2 }
    L_0x006a:
        r1 = r7.zzpe;	 Catch:{ all -> 0x00a2 }
        r1 = r1.getMediaStatus();	 Catch:{ all -> 0x00a2 }
        r0 = r1.getQueueItem(r0);	 Catch:{ all -> 0x00a2 }
        if (r0 == 0) goto L_0x007b;	 Catch:{ all -> 0x00a2 }
    L_0x0076:
        r0 = r0.getItemId();	 Catch:{ all -> 0x00a2 }
        goto L_0x007c;
    L_0x007b:
        r0 = 0;
    L_0x007c:
        r1 = r7.zzpe;	 Catch:{ IllegalStateException -> 0x0090, IllegalStateException -> 0x0090 }
        r1 = r1.zzfl;	 Catch:{ IllegalStateException -> 0x0090, IllegalStateException -> 0x0090 }
        r4 = r7.zzgr;	 Catch:{ IllegalStateException -> 0x0090, IllegalStateException -> 0x0090 }
        r3 = new int[r3];	 Catch:{ IllegalStateException -> 0x0090, IllegalStateException -> 0x0090 }
        r5 = r7.zzgi;	 Catch:{ IllegalStateException -> 0x0090, IllegalStateException -> 0x0090 }
        r3[r2] = r5;	 Catch:{ IllegalStateException -> 0x0090, IllegalStateException -> 0x0090 }
        r2 = r7.zzfy;	 Catch:{ IllegalStateException -> 0x0090, IllegalStateException -> 0x0090 }
        r1.zza(r4, r3, r0, r2);	 Catch:{ IllegalStateException -> 0x0090, IllegalStateException -> 0x0090 }
        goto L_0x00a0;
    L_0x0090:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x00a2 }
        r1 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x00a2 }
        r0.<init>(r1);	 Catch:{ all -> 0x00a2 }
        r0 = r7.createFailedResult(r0);	 Catch:{ all -> 0x00a2 }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x00a2 }
        r7.setResult(r0);	 Catch:{ all -> 0x00a2 }
    L_0x00a0:
        monitor-exit(r8);	 Catch:{ all -> 0x00a2 }
        return;	 Catch:{ all -> 0x00a2 }
    L_0x00a2:
        r0 = move-exception;	 Catch:{ all -> 0x00a2 }
        monitor-exit(r8);	 Catch:{ all -> 0x00a2 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzaj.zzb(com.google.android.gms.internal.cast.zzco):void");
    }
}
