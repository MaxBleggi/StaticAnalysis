package com.google.android.gms.internal.measurement;

final class zzsz extends zzsx<Integer> {
    zzsz(zztd com_google_android_gms_internal_measurement_zztd, String str, Integer num) {
        super(com_google_android_gms_internal_measurement_zztd, str, num);
    }

    private final java.lang.Integer zzu(java.lang.Object r5) {
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
        r0 = r5 instanceof java.lang.Integer;
        if (r0 == 0) goto L_0x0007;
    L_0x0004:
        r5 = (java.lang.Integer) r5;
        return r5;
    L_0x0007:
        r0 = r5 instanceof java.lang.Long;
        if (r0 == 0) goto L_0x0016;
    L_0x000b:
        r5 = (java.lang.Long) r5;
        r5 = r5.intValue();
        r5 = java.lang.Integer.valueOf(r5);
        return r5;
    L_0x0016:
        r0 = r5 instanceof java.lang.String;
        if (r0 == 0) goto L_0x0026;
    L_0x001a:
        r0 = r5;	 Catch:{ NumberFormatException -> 0x0026 }
        r0 = (java.lang.String) r0;	 Catch:{ NumberFormatException -> 0x0026 }
        r0 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x0026 }
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ NumberFormatException -> 0x0026 }
        return r0;
    L_0x0026:
        r0 = "PhenotypeFlag";
        r1 = super.zztr();
        r5 = java.lang.String.valueOf(r5);
        r2 = java.lang.String.valueOf(r1);
        r2 = r2.length();
        r2 = r2 + 24;
        r3 = java.lang.String.valueOf(r5);
        r3 = r3.length();
        r2 = r2 + r3;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Invalid int value for ";
        r3.append(r2);
        r3.append(r1);
        r1 = ": ";
        r3.append(r1);
        r3.append(r5);
        r5 = r3.toString();
        android.util.Log.e(r0, r5);
        r5 = 0;
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzsz.zzu(java.lang.Object):java.lang.Integer");
    }

    final /* synthetic */ Object zzs(Object obj) {
        return zzu(obj);
    }
}
