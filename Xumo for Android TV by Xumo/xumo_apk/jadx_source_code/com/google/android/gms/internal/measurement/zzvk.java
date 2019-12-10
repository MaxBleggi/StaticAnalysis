package com.google.android.gms.internal.measurement;

import androidx.core.internal.view.SupportMenu;
import com.google.android.gms.internal.measurement.zzvx.zzd;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzvk {
    private static volatile boolean zzbwe = false;
    private static final Class<?> zzbwf = zzvx();
    private static volatile zzvk zzbwg;
    static final zzvk zzbwh = new zzvk(true);
    private final Map<zza, zzd<?, ?>> zzbwi;

    static final class zza {
        private final int number;
        private final Object object;

        zza(Object obj, int i) {
            this.object = obj;
            this.number = i;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.object) * SupportMenu.USER_MASK) + this.number;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_measurement_zzvk_zza = (zza) obj;
            if (this.object == com_google_android_gms_internal_measurement_zzvk_zza.object && this.number == com_google_android_gms_internal_measurement_zzvk_zza.number) {
                return true;
            }
            return false;
        }
    }

    private static java.lang.Class<?> zzvx() {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = "com.google.protobuf.Extension";	 Catch:{ ClassNotFoundException -> 0x0007 }
        r0 = java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x0007 }
        return r0;
    L_0x0007:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzvk.zzvx():java.lang.Class<?>");
    }

    public static zzvk zzvy() {
        return zzvj.zzvv();
    }

    public static zzvk zzvz() {
        zzvk com_google_android_gms_internal_measurement_zzvk = zzbwg;
        if (com_google_android_gms_internal_measurement_zzvk == null) {
            synchronized (zzvk.class) {
                com_google_android_gms_internal_measurement_zzvk = zzbwg;
                if (com_google_android_gms_internal_measurement_zzvk == null) {
                    com_google_android_gms_internal_measurement_zzvk = zzvj.zzvw();
                    zzbwg = com_google_android_gms_internal_measurement_zzvk;
                }
            }
        }
        return com_google_android_gms_internal_measurement_zzvk;
    }

    static zzvk zzvw() {
        return zzvv.zzd(zzvk.class);
    }

    public final <ContainingType extends zzxe> zzd<ContainingType, ?> zza(ContainingType containingType, int i) {
        return (zzd) this.zzbwi.get(new zza(containingType, i));
    }

    zzvk() {
        this.zzbwi = new HashMap();
    }

    private zzvk(boolean z) {
        this.zzbwi = Collections.emptyMap();
    }
}
