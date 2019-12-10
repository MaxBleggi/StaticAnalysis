package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

final class zabo implements Runnable {
    private final /* synthetic */ ConnectionResult zaiy;
    private final /* synthetic */ zac zajf;

    zabo(zac com_google_android_gms_common_api_internal_GoogleApiManager_zac, ConnectionResult connectionResult) {
        this.zajf = com_google_android_gms_common_api_internal_GoogleApiManager_zac;
        this.zaiy = connectionResult;
    }

    public final void run() {
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
        r3 = this;
        r0 = r3.zaiy;
        r0 = r0.isSuccess();
        if (r0 == 0) goto L_0x004e;
    L_0x0008:
        r0 = r3.zajf;
        r1 = 1;
        r0.zaje = true;
        r0 = r3.zajf;
        r0 = r0.zain;
        r0 = r0.requiresSignIn();
        if (r0 == 0) goto L_0x0020;
    L_0x001a:
        r0 = r3.zajf;
        r0.zabr();
        return;
    L_0x0020:
        r0 = r3.zajf;	 Catch:{ SecurityException -> 0x002f }
        r0 = r0.zain;	 Catch:{ SecurityException -> 0x002f }
        r1 = 0;	 Catch:{ SecurityException -> 0x002f }
        r2 = java.util.Collections.emptySet();	 Catch:{ SecurityException -> 0x002f }
        r0.getRemoteService(r1, r2);	 Catch:{ SecurityException -> 0x002f }
        return;
    L_0x002f:
        r0 = r3.zajf;
        r0 = r0.zail;
        r0 = r0.zaih;
        r1 = r3.zajf;
        r1 = r1.zafp;
        r0 = r0.get(r1);
        r0 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r0;
        r1 = new com.google.android.gms.common.ConnectionResult;
        r2 = 10;
        r1.<init>(r2);
        r0.onConnectionFailed(r1);
        return;
    L_0x004e:
        r0 = r3.zajf;
        r0 = r0.zail;
        r0 = r0.zaih;
        r1 = r3.zajf;
        r1 = r1.zafp;
        r0 = r0.get(r1);
        r0 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r0;
        r1 = r3.zaiy;
        r0.onConnectionFailed(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zabo.run():void");
    }
}
