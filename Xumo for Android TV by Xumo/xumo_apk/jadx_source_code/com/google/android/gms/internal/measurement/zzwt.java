package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzvx.zze;

final class zzwt implements zzxv {
    private static final zzxd zzcbk = new zzwu();
    private final zzxd zzcbj;

    public zzwt() {
        this(new zzwv(zzvw.zzwl(), zzxm()));
    }

    private zzwt(zzxd com_google_android_gms_internal_measurement_zzxd) {
        this.zzcbj = (zzxd) zzvz.zza(com_google_android_gms_internal_measurement_zzxd, "messageInfoFactory");
    }

    public final <T> zzxu<T> zzh(Class<T> cls) {
        zzxw.zzj(cls);
        zzxc zzf = this.zzcbj.zzf(cls);
        if (zzf.zzxu()) {
            if (zzvx.class.isAssignableFrom(cls) != null) {
                return zzxj.zza(zzxw.zzyg(), zzvn.zzwb(), zzf.zzxv());
            }
            return zzxj.zza(zzxw.zzye(), zzvn.zzwc(), zzf.zzxv());
        } else if (zzvx.class.isAssignableFrom(cls)) {
            if (zza(zzf)) {
                return zzxi.zza(cls, zzf, zzxn.zzxy(), zzwo.zzxl(), zzxw.zzyg(), zzvn.zzwb(), zzxb.zzxr());
            }
            return zzxi.zza(cls, zzf, zzxn.zzxy(), zzwo.zzxl(), zzxw.zzyg(), null, zzxb.zzxr());
        } else if (zza(zzf)) {
            return zzxi.zza(cls, zzf, zzxn.zzxx(), zzwo.zzxk(), zzxw.zzye(), zzvn.zzwc(), zzxb.zzxq());
        } else {
            return zzxi.zza(cls, zzf, zzxn.zzxx(), zzwo.zzxk(), zzxw.zzyf(), null, zzxb.zzxq());
        }
    }

    private static boolean zza(zzxc com_google_android_gms_internal_measurement_zzxc) {
        return com_google_android_gms_internal_measurement_zzxc.zzxt() == zze.zzbzw ? true : null;
    }

    private static com.google.android.gms.internal.measurement.zzxd zzxm() {
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
        r0 = "com.google.protobuf.DescriptorMessageInfoFactory";	 Catch:{ Exception -> 0x0019 }
        r0 = java.lang.Class.forName(r0);	 Catch:{ Exception -> 0x0019 }
        r1 = "getInstance";	 Catch:{ Exception -> 0x0019 }
        r2 = 0;	 Catch:{ Exception -> 0x0019 }
        r3 = new java.lang.Class[r2];	 Catch:{ Exception -> 0x0019 }
        r0 = r0.getDeclaredMethod(r1, r3);	 Catch:{ Exception -> 0x0019 }
        r1 = 0;	 Catch:{ Exception -> 0x0019 }
        r2 = new java.lang.Object[r2];	 Catch:{ Exception -> 0x0019 }
        r0 = r0.invoke(r1, r2);	 Catch:{ Exception -> 0x0019 }
        r0 = (com.google.android.gms.internal.measurement.zzxd) r0;	 Catch:{ Exception -> 0x0019 }
        return r0;
    L_0x0019:
        r0 = zzcbk;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzwt.zzxm():com.google.android.gms.internal.measurement.zzxd");
    }
}
