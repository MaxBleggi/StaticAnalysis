package com.google.android.gms.internal.cast;

import com.google.android.gms.common.api.Status;

final class zzbw implements zzdn {
    private final /* synthetic */ zzbm zzuy;
    private final /* synthetic */ zzbv zzvc;

    zzbw(zzbv com_google_android_gms_internal_cast_zzbv, zzbm com_google_android_gms_internal_cast_zzbm) {
        this.zzvc = com_google_android_gms_internal_cast_zzbv;
        this.zzuy = com_google_android_gms_internal_cast_zzbm;
    }

    public final void zzb(long j) {
        this.zzvc.setResult(zzbv.zzc(new Status(2103)));
    }

    public final void zza(long r5, int r7, java.lang.Object r8) {
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
        r5 = 0;
        if (r8 != 0) goto L_0x0019;
    L_0x0003:
        r6 = r4.zzvc;	 Catch:{ ClassCastException -> 0x0072 }
        r8 = new com.google.android.gms.internal.cast.zzbx;	 Catch:{ ClassCastException -> 0x0072 }
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ ClassCastException -> 0x0072 }
        r0.<init>(r7, r5, r5);	 Catch:{ ClassCastException -> 0x0072 }
        r5 = r4.zzvc;	 Catch:{ ClassCastException -> 0x0072 }
        r5 = r5.zzvb;	 Catch:{ ClassCastException -> 0x0072 }
        r8.<init>(r0, r5);	 Catch:{ ClassCastException -> 0x0072 }
        r6.setResult(r8);	 Catch:{ ClassCastException -> 0x0072 }
        return;	 Catch:{ ClassCastException -> 0x0072 }
    L_0x0019:
        r8 = (com.google.android.gms.internal.cast.zzca) r8;	 Catch:{ ClassCastException -> 0x0072 }
        r6 = r8.zzvu;	 Catch:{ ClassCastException -> 0x0072 }
        if (r6 == 0) goto L_0x005a;	 Catch:{ ClassCastException -> 0x0072 }
    L_0x001f:
        r0 = "1.0.0";	 Catch:{ ClassCastException -> 0x0072 }
        r1 = r6.getVersion();	 Catch:{ ClassCastException -> 0x0072 }
        r0 = com.google.android.gms.internal.cast.zzcv.zza(r0, r1);	 Catch:{ ClassCastException -> 0x0072 }
        if (r0 != 0) goto L_0x005a;	 Catch:{ ClassCastException -> 0x0072 }
    L_0x002b:
        r7 = r4.zzvc;	 Catch:{ ClassCastException -> 0x0072 }
        r7 = r7.zzut;	 Catch:{ ClassCastException -> 0x0072 }
        r7.zzuj = null;	 Catch:{ ClassCastException -> 0x0072 }
        r5 = r4.zzvc;	 Catch:{ ClassCastException -> 0x0072 }
        r7 = new com.google.android.gms.common.api.Status;	 Catch:{ ClassCastException -> 0x0072 }
        r8 = 2150; // 0x866 float:3.013E-42 double:1.062E-320;	 Catch:{ ClassCastException -> 0x0072 }
        r0 = java.util.Locale.ROOT;	 Catch:{ ClassCastException -> 0x0072 }
        r1 = "Incorrect Game Manager SDK version. Receiver: %s Sender: %s";	 Catch:{ ClassCastException -> 0x0072 }
        r2 = 2;	 Catch:{ ClassCastException -> 0x0072 }
        r2 = new java.lang.Object[r2];	 Catch:{ ClassCastException -> 0x0072 }
        r3 = 0;	 Catch:{ ClassCastException -> 0x0072 }
        r6 = r6.getVersion();	 Catch:{ ClassCastException -> 0x0072 }
        r2[r3] = r6;	 Catch:{ ClassCastException -> 0x0072 }
        r6 = 1;	 Catch:{ ClassCastException -> 0x0072 }
        r3 = "1.0.0";	 Catch:{ ClassCastException -> 0x0072 }
        r2[r6] = r3;	 Catch:{ ClassCastException -> 0x0072 }
        r6 = java.lang.String.format(r0, r1, r2);	 Catch:{ ClassCastException -> 0x0072 }
        r7.<init>(r8, r6);	 Catch:{ ClassCastException -> 0x0072 }
        r6 = com.google.android.gms.internal.cast.zzbv.zzc(r7);	 Catch:{ ClassCastException -> 0x0072 }
        r5.setResult(r6);	 Catch:{ ClassCastException -> 0x0072 }
        return;	 Catch:{ ClassCastException -> 0x0072 }
    L_0x005a:
        r6 = r4.zzvc;	 Catch:{ ClassCastException -> 0x0072 }
        r0 = new com.google.android.gms.internal.cast.zzbx;	 Catch:{ ClassCastException -> 0x0072 }
        r1 = new com.google.android.gms.common.api.Status;	 Catch:{ ClassCastException -> 0x0072 }
        r8 = r8.zzvk;	 Catch:{ ClassCastException -> 0x0072 }
        r1.<init>(r7, r8, r5);	 Catch:{ ClassCastException -> 0x0072 }
        r5 = r4.zzvc;	 Catch:{ ClassCastException -> 0x0072 }
        r5 = r5.zzvb;	 Catch:{ ClassCastException -> 0x0072 }
        r0.<init>(r1, r5);	 Catch:{ ClassCastException -> 0x0072 }
        r6.setResult(r0);	 Catch:{ ClassCastException -> 0x0072 }
        return;
    L_0x0072:
        r5 = r4.zzvc;
        r6 = new com.google.android.gms.common.api.Status;
        r7 = 13;
        r6.<init>(r7);
        r6 = com.google.android.gms.internal.cast.zzbv.zzc(r6);
        r5.setResult(r6);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzbw.zza(long, int, java.lang.Object):void");
    }
}
