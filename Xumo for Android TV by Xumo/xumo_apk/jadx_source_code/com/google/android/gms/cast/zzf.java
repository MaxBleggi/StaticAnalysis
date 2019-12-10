package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast.CastApi.zza;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.cast.zzco;
import com.google.android.gms.internal.cast.zzcz;

final class zzf extends zzcz {
    private final /* synthetic */ String zzad;
    private final /* synthetic */ String zzae;

    zzf(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient, String str, String str2) {
        this.zzad = str;
        this.zzae = str2;
        super(googleApiClient);
    }

    public final void zza(com.google.android.gms.internal.cast.zzco r3) throws android.os.RemoteException {
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
        r0 = r2.zzad;	 Catch:{ IllegalArgumentException -> 0x0008, IllegalArgumentException -> 0x0008 }
        r1 = r2.zzae;	 Catch:{ IllegalArgumentException -> 0x0008, IllegalArgumentException -> 0x0008 }
        r3.zza(r0, r1, r2);	 Catch:{ IllegalArgumentException -> 0x0008, IllegalArgumentException -> 0x0008 }
        return;
    L_0x0008:
        r3 = 2001; // 0x7d1 float:2.804E-42 double:9.886E-321;
        r2.zzk(r3);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzf.zza(com.google.android.gms.internal.cast.zzco):void");
    }

    public final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzco) anyClient);
    }
}
