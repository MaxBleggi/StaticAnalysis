package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

final class zzx extends zzc {
    private final /* synthetic */ int val$repeatMode;
    private final /* synthetic */ MediaQueueItem[] zzfv;
    private final /* synthetic */ int zzfw;
    private final /* synthetic */ long zzfx;
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ RemoteMediaClient zzpe;

    zzx(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, MediaQueueItem[] mediaQueueItemArr, int i, int i2, long j, JSONObject jSONObject) {
        this.zzpe = remoteMediaClient;
        this.zzfv = mediaQueueItemArr;
        this.zzfw = i;
        this.val$repeatMode = i2;
        this.zzfx = j;
        this.zzfy = jSONObject;
        super(remoteMediaClient, googleApiClient);
    }

    protected final void zzb(com.google.android.gms.internal.cast.zzco r10) {
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
        r9 = this;
        r10 = r9.zzpe;
        r10 = r10.lock;
        monitor-enter(r10);
        r0 = r9.zzpe;	 Catch:{ IllegalStateException -> 0x001f }
        r1 = r0.zzfl;	 Catch:{ IllegalStateException -> 0x001f }
        r2 = r9.zzgr;	 Catch:{ IllegalStateException -> 0x001f }
        r3 = r9.zzfv;	 Catch:{ IllegalStateException -> 0x001f }
        r4 = r9.zzfw;	 Catch:{ IllegalStateException -> 0x001f }
        r5 = r9.val$repeatMode;	 Catch:{ IllegalStateException -> 0x001f }
        r6 = r9.zzfx;	 Catch:{ IllegalStateException -> 0x001f }
        r8 = r9.zzfy;	 Catch:{ IllegalStateException -> 0x001f }
        r1.zza(r2, r3, r4, r5, r6, r8);	 Catch:{ IllegalStateException -> 0x001f }
        goto L_0x002f;
    L_0x001d:
        r0 = move-exception;
        goto L_0x0031;
    L_0x001f:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x001d }
        r1 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x001d }
        r0.<init>(r1);	 Catch:{ all -> 0x001d }
        r0 = r9.createFailedResult(r0);	 Catch:{ all -> 0x001d }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x001d }
        r9.setResult(r0);	 Catch:{ all -> 0x001d }
    L_0x002f:
        monitor-exit(r10);	 Catch:{ all -> 0x001d }
        return;	 Catch:{ all -> 0x001d }
    L_0x0031:
        monitor-exit(r10);	 Catch:{ all -> 0x001d }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzx.zzb(com.google.android.gms.internal.cast.zzco):void");
    }
}
