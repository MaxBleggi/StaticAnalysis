package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

final class zztv extends TaskApiCall<zztr, ShortDynamicLink> {
    private final Bundle zzbtu;

    zztv(Bundle bundle) {
        this.zzbtu = bundle;
    }

    protected final /* synthetic */ void doExecute(com.google.android.gms.common.api.Api.AnyClient r2, com.google.android.gms.tasks.TaskCompletionSource r3) throws android.os.RemoteException {
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
        r1 = this;
        r2 = (com.google.android.gms.internal.measurement.zztr) r2;
        r0 = new com.google.android.gms.internal.measurement.zztu;
        r0.<init>(r3);
        r3 = r1.zzbtu;
        r2 = r2.getService();	 Catch:{ RemoteException -> 0x0013 }
        r2 = (com.google.android.gms.internal.measurement.zzua) r2;	 Catch:{ RemoteException -> 0x0013 }
        r2.zza(r0, r3);	 Catch:{ RemoteException -> 0x0013 }
        return;
    L_0x0013:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zztv.doExecute(com.google.android.gms.common.api.Api$AnyClient, com.google.android.gms.tasks.TaskCompletionSource):void");
    }
}
