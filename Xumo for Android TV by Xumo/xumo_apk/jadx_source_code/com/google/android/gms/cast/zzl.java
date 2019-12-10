package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast.CastApi.zza;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.cast.zzco;
import com.google.android.gms.internal.cast.zzcz;

final class zzl extends zzcz {
    private final /* synthetic */ String val$sessionId;

    zzl(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient, String str) {
        this.val$sessionId = str;
        super(googleApiClient);
    }

    public final void zza(com.google.android.gms.internal.cast.zzco r4) throws android.os.RemoteException {
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
        r0 = r3.val$sessionId;
        r0 = android.text.TextUtils.isEmpty(r0);
        r1 = 2001; // 0x7d1 float:2.804E-42 double:9.886E-321;
        if (r0 == 0) goto L_0x001a;
    L_0x000a:
        r4 = "IllegalArgument: sessionId cannot be null or empty";
        r0 = new com.google.android.gms.common.api.Status;
        r2 = 0;
        r0.<init>(r1, r4, r2);
        r4 = r3.createFailedResult(r0);
        r3.setResult(r4);
        return;
    L_0x001a:
        r0 = r3.val$sessionId;	 Catch:{ IllegalStateException -> 0x0020 }
        r4.zza(r0, r3);	 Catch:{ IllegalStateException -> 0x0020 }
        return;
    L_0x0020:
        r3.zzk(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzl.zza(com.google.android.gms.internal.cast.zzco):void");
    }

    public final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzco) anyClient);
    }
}
