package com.google.android.gms.cast.framework.media;

import com.google.android.gms.common.api.GoogleApiClient;

final class zzt extends zzc {
    private final /* synthetic */ RemoteMediaClient zzpe;

    zzt(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient) {
        this.zzpe = remoteMediaClient;
        super(remoteMediaClient, googleApiClient);
    }

    protected final void zzb(com.google.android.gms.internal.cast.zzco r3) {
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
        r2 = this;
        r3 = r2.zzpe;
        r3 = r3.lock;
        monitor-enter(r3);
        r0 = r2.zzpe;	 Catch:{ IllegalStateException -> 0x0015, IllegalStateException -> 0x0015 }
        r0 = r0.zzfl;	 Catch:{ IllegalStateException -> 0x0015, IllegalStateException -> 0x0015 }
        r1 = r2.zzgr;	 Catch:{ IllegalStateException -> 0x0015, IllegalStateException -> 0x0015 }
        r0.zza(r1);	 Catch:{ IllegalStateException -> 0x0015, IllegalStateException -> 0x0015 }
        goto L_0x0025;
    L_0x0013:
        r0 = move-exception;
        goto L_0x0027;
    L_0x0015:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x0013 }
        r1 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x0013 }
        r0.<init>(r1);	 Catch:{ all -> 0x0013 }
        r0 = r2.createFailedResult(r0);	 Catch:{ all -> 0x0013 }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x0013 }
        r2.setResult(r0);	 Catch:{ all -> 0x0013 }
    L_0x0025:
        monitor-exit(r3);	 Catch:{ all -> 0x0013 }
        return;	 Catch:{ all -> 0x0013 }
    L_0x0027:
        monitor-exit(r3);	 Catch:{ all -> 0x0013 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzt.zzb(com.google.android.gms.internal.cast.zzco):void");
    }
}
