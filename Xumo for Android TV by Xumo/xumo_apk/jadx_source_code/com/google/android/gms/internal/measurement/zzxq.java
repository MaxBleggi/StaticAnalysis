package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzxq {
    private static final zzxq zzccn = new zzxq();
    private final zzxv zzcco;
    private final ConcurrentMap<Class<?>, zzxu<?>> zzccp = new ConcurrentHashMap();

    public static zzxq zzya() {
        return zzccn;
    }

    public final <T> zzxu<T> zzi(Class<T> cls) {
        zzvz.zza(cls, "messageType");
        zzxu<T> com_google_android_gms_internal_measurement_zzxu_T = (zzxu) this.zzccp.get(cls);
        if (com_google_android_gms_internal_measurement_zzxu_T != null) {
            return com_google_android_gms_internal_measurement_zzxu_T;
        }
        com_google_android_gms_internal_measurement_zzxu_T = this.zzcco.zzh(cls);
        zzvz.zza(cls, "messageType");
        zzvz.zza(com_google_android_gms_internal_measurement_zzxu_T, "schema");
        zzxu<T> com_google_android_gms_internal_measurement_zzxu_T2 = (zzxu) this.zzccp.putIfAbsent(cls, com_google_android_gms_internal_measurement_zzxu_T);
        return com_google_android_gms_internal_measurement_zzxu_T2 != null ? com_google_android_gms_internal_measurement_zzxu_T2 : com_google_android_gms_internal_measurement_zzxu_T;
    }

    public final <T> zzxu<T> zzak(T t) {
        return zzi(t.getClass());
    }

    private zzxq() {
        String[] strArr = new String[]{"com.google.protobuf.AndroidProto3SchemaFactory"};
        zzxv com_google_android_gms_internal_measurement_zzxv = null;
        for (int i = 0; i <= 0; i++) {
            com_google_android_gms_internal_measurement_zzxv = zzgi(strArr[0]);
            if (com_google_android_gms_internal_measurement_zzxv != null) {
                break;
            }
        }
        if (com_google_android_gms_internal_measurement_zzxv == null) {
            com_google_android_gms_internal_measurement_zzxv = new zzwt();
        }
        this.zzcco = com_google_android_gms_internal_measurement_zzxv;
    }

    private static com.google.android.gms.internal.measurement.zzxv zzgi(java.lang.String r2) {
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
        r2 = java.lang.Class.forName(r2);	 Catch:{ Throwable -> 0x0014 }
        r0 = 0;	 Catch:{ Throwable -> 0x0014 }
        r1 = new java.lang.Class[r0];	 Catch:{ Throwable -> 0x0014 }
        r2 = r2.getConstructor(r1);	 Catch:{ Throwable -> 0x0014 }
        r0 = new java.lang.Object[r0];	 Catch:{ Throwable -> 0x0014 }
        r2 = r2.newInstance(r0);	 Catch:{ Throwable -> 0x0014 }
        r2 = (com.google.android.gms.internal.measurement.zzxv) r2;	 Catch:{ Throwable -> 0x0014 }
        return r2;
    L_0x0014:
        r2 = 0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzxq.zzgi(java.lang.String):com.google.android.gms.internal.measurement.zzxv");
    }
}
