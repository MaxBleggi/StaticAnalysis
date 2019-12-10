package com.google.android.gms.cast.framework.media;

import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

final class zzai extends zzc {
    private final /* synthetic */ long zzfx;
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ int zzgi;
    private final /* synthetic */ RemoteMediaClient zzpe;

    zzai(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, int i, long j, JSONObject jSONObject) {
        this.zzpe = remoteMediaClient;
        this.zzgi = i;
        this.zzfx = j;
        this.zzfy = jSONObject;
        super(remoteMediaClient, googleApiClient);
    }

    protected final void zzb(com.google.android.gms.internal.cast.zzco r11) {
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
        r10 = this;
        r11 = r10.zzpe;
        r11 = r11.lock;
        monitor-enter(r11);
        r0 = r10.zzpe;	 Catch:{ all -> 0x004a }
        r1 = r10.zzgi;	 Catch:{ all -> 0x004a }
        r0 = r0.zzc(r1);	 Catch:{ all -> 0x004a }
        r1 = -1;	 Catch:{ all -> 0x004a }
        if (r0 != r1) goto L_0x0023;	 Catch:{ all -> 0x004a }
    L_0x0012:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x004a }
        r1 = 0;	 Catch:{ all -> 0x004a }
        r0.<init>(r1);	 Catch:{ all -> 0x004a }
        r0 = r10.createFailedResult(r0);	 Catch:{ all -> 0x004a }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x004a }
        r10.setResult(r0);	 Catch:{ all -> 0x004a }
        monitor-exit(r11);	 Catch:{ all -> 0x004a }
        return;
    L_0x0023:
        r0 = r10.zzpe;	 Catch:{ IllegalStateException -> 0x0038, IllegalStateException -> 0x0038 }
        r1 = r0.zzfl;	 Catch:{ IllegalStateException -> 0x0038, IllegalStateException -> 0x0038 }
        r2 = r10.zzgr;	 Catch:{ IllegalStateException -> 0x0038, IllegalStateException -> 0x0038 }
        r3 = r10.zzgi;	 Catch:{ IllegalStateException -> 0x0038, IllegalStateException -> 0x0038 }
        r4 = r10.zzfx;	 Catch:{ IllegalStateException -> 0x0038, IllegalStateException -> 0x0038 }
        r6 = 0;	 Catch:{ IllegalStateException -> 0x0038, IllegalStateException -> 0x0038 }
        r7 = 0;	 Catch:{ IllegalStateException -> 0x0038, IllegalStateException -> 0x0038 }
        r8 = 0;	 Catch:{ IllegalStateException -> 0x0038, IllegalStateException -> 0x0038 }
        r9 = r10.zzfy;	 Catch:{ IllegalStateException -> 0x0038, IllegalStateException -> 0x0038 }
        r1.zza(r2, r3, r4, r6, r7, r8, r9);	 Catch:{ IllegalStateException -> 0x0038, IllegalStateException -> 0x0038 }
        goto L_0x0048;
    L_0x0038:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x004a }
        r1 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x004a }
        r0.<init>(r1);	 Catch:{ all -> 0x004a }
        r0 = r10.createFailedResult(r0);	 Catch:{ all -> 0x004a }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x004a }
        r10.setResult(r0);	 Catch:{ all -> 0x004a }
    L_0x0048:
        monitor-exit(r11);	 Catch:{ all -> 0x004a }
        return;	 Catch:{ all -> 0x004a }
    L_0x004a:
        r0 = move-exception;	 Catch:{ all -> 0x004a }
        monitor-exit(r11);	 Catch:{ all -> 0x004a }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzai.zzb(com.google.android.gms.internal.cast.zzco):void");
    }
}
