package com.google.android.gms.cast.framework.media;

import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

final class zzat extends zzc {
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ RemoteMediaClient zzpe;
    private final /* synthetic */ boolean zzpm;

    zzat(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, boolean z, JSONObject jSONObject) {
        this.zzpe = remoteMediaClient;
        this.zzpm = z;
        this.zzfy = jSONObject;
        super(remoteMediaClient, googleApiClient);
    }

    protected final void zzb(com.google.android.gms.internal.cast.zzco r5) {
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
        r4 = this;
        r5 = r4.zzpe;
        r5 = r5.lock;
        monitor-enter(r5);
        r0 = r4.zzpe;	 Catch:{ IllegalStateException -> 0x0019, IllegalStateException -> 0x0019 }
        r0 = r0.zzfl;	 Catch:{ IllegalStateException -> 0x0019, IllegalStateException -> 0x0019 }
        r1 = r4.zzgr;	 Catch:{ IllegalStateException -> 0x0019, IllegalStateException -> 0x0019 }
        r2 = r4.zzpm;	 Catch:{ IllegalStateException -> 0x0019, IllegalStateException -> 0x0019 }
        r3 = r4.zzfy;	 Catch:{ IllegalStateException -> 0x0019, IllegalStateException -> 0x0019 }
        r0.zza(r1, r2, r3);	 Catch:{ IllegalStateException -> 0x0019, IllegalStateException -> 0x0019 }
        goto L_0x0029;
    L_0x0017:
        r0 = move-exception;
        goto L_0x002b;
    L_0x0019:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x0017 }
        r1 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x0017 }
        r0.<init>(r1);	 Catch:{ all -> 0x0017 }
        r0 = r4.createFailedResult(r0);	 Catch:{ all -> 0x0017 }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x0017 }
        r4.setResult(r0);	 Catch:{ all -> 0x0017 }
    L_0x0029:
        monitor-exit(r5);	 Catch:{ all -> 0x0017 }
        return;	 Catch:{ all -> 0x0017 }
    L_0x002b:
        monitor-exit(r5);	 Catch:{ all -> 0x0017 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzat.zzb(com.google.android.gms.internal.cast.zzco):void");
    }
}
