package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

final class zztx extends TaskApiCall<zztr, PendingDynamicLinkData> {
    private final String zzbua;
    private final Context zzwq;

    zztx(Context context, String str) {
        this.zzwq = context;
        this.zzbua = str;
    }

    protected final /* synthetic */ void doExecute(com.google.android.gms.common.api.Api.AnyClient r3, com.google.android.gms.tasks.TaskCompletionSource r4) throws android.os.RemoteException {
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
        r3 = (com.google.android.gms.internal.measurement.zztr) r3;
        r0 = new com.google.android.gms.internal.measurement.zztw;
        r1 = r2.zzwq;
        r0.<init>(r1, r4);
        r4 = r2.zzbua;
        r3 = r3.getService();	 Catch:{ RemoteException -> 0x0015 }
        r3 = (com.google.android.gms.internal.measurement.zzua) r3;	 Catch:{ RemoteException -> 0x0015 }
        r3.zza(r0, r4);	 Catch:{ RemoteException -> 0x0015 }
        return;
    L_0x0015:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zztx.doExecute(com.google.android.gms.common.api.Api$AnyClient, com.google.android.gms.tasks.TaskCompletionSource):void");
    }
}
