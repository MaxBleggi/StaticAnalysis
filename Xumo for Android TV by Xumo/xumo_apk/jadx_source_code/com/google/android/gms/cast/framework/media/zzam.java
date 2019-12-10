package com.google.android.gms.cast.framework.media;

import com.google.android.gms.common.api.GoogleApiClient;

final class zzam extends zzc {
    private final /* synthetic */ int zzgi;
    private final /* synthetic */ RemoteMediaClient zzpe;
    private final /* synthetic */ int zzpi;
    private final /* synthetic */ int zzpj;

    zzam(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, boolean z, int i, int i2, int i3) {
        this.zzpe = remoteMediaClient;
        this.zzgi = i;
        this.zzpi = i2;
        this.zzpj = i3;
        super(remoteMediaClient, googleApiClient, true);
    }

    protected final void zzb(com.google.android.gms.internal.cast.zzco r6) {
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
        r6 = r5.zzpe;
        r6 = r6.lock;
        monitor-enter(r6);
        r0 = r5.zzpe;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r0 = r0.zzfl;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r1 = r5.zzgr;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r2 = r5.zzgi;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r3 = r5.zzpi;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r4 = r5.zzpj;	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        r0.zza(r1, r2, r3, r4);	 Catch:{ IllegalStateException -> 0x001b, IllegalStateException -> 0x001b, IllegalStateException -> 0x001b }
        goto L_0x002b;
    L_0x0019:
        r0 = move-exception;
        goto L_0x002d;
    L_0x001b:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x0019 }
        r1 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x0019 }
        r0.<init>(r1);	 Catch:{ all -> 0x0019 }
        r0 = r5.createFailedResult(r0);	 Catch:{ all -> 0x0019 }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x0019 }
        r5.setResult(r0);	 Catch:{ all -> 0x0019 }
    L_0x002b:
        monitor-exit(r6);	 Catch:{ all -> 0x0019 }
        return;	 Catch:{ all -> 0x0019 }
    L_0x002d:
        monitor-exit(r6);	 Catch:{ all -> 0x0019 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzam.zzb(com.google.android.gms.internal.cast.zzco):void");
    }
}
