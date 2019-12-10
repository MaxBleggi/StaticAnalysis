package com.google.android.gms.cast.framework.media;

import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

final class zzaf extends zzc {
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ RemoteMediaClient zzpe;

    zzaf(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, JSONObject jSONObject) {
        this.zzpe = remoteMediaClient;
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
        r0 = r10.zzpe;	 Catch:{ IllegalStateException -> 0x001d, IllegalStateException -> 0x001d }
        r1 = r0.zzfl;	 Catch:{ IllegalStateException -> 0x001d, IllegalStateException -> 0x001d }
        r2 = r10.zzgr;	 Catch:{ IllegalStateException -> 0x001d, IllegalStateException -> 0x001d }
        r3 = 0;	 Catch:{ IllegalStateException -> 0x001d, IllegalStateException -> 0x001d }
        r4 = -1;	 Catch:{ IllegalStateException -> 0x001d, IllegalStateException -> 0x001d }
        r6 = 0;	 Catch:{ IllegalStateException -> 0x001d, IllegalStateException -> 0x001d }
        r7 = 1;	 Catch:{ IllegalStateException -> 0x001d, IllegalStateException -> 0x001d }
        r8 = 0;	 Catch:{ IllegalStateException -> 0x001d, IllegalStateException -> 0x001d }
        r9 = r10.zzfy;	 Catch:{ IllegalStateException -> 0x001d, IllegalStateException -> 0x001d }
        r1.zza(r2, r3, r4, r6, r7, r8, r9);	 Catch:{ IllegalStateException -> 0x001d, IllegalStateException -> 0x001d }
        goto L_0x002d;
    L_0x001b:
        r0 = move-exception;
        goto L_0x002f;
    L_0x001d:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x001b }
        r1 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x001b }
        r0.<init>(r1);	 Catch:{ all -> 0x001b }
        r0 = r10.createFailedResult(r0);	 Catch:{ all -> 0x001b }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x001b }
        r10.setResult(r0);	 Catch:{ all -> 0x001b }
    L_0x002d:
        monitor-exit(r11);	 Catch:{ all -> 0x001b }
        return;	 Catch:{ all -> 0x001b }
    L_0x002f:
        monitor-exit(r11);	 Catch:{ all -> 0x001b }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzaf.zzb(com.google.android.gms.internal.cast.zzco):void");
    }
}
