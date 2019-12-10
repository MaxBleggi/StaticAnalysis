package com.google.android.gms.internal.measurement;

public class zzwl {
    private static final zzvk zzbuo = zzvk.zzvy();
    private zzun zzcay;
    private volatile zzxe zzcaz;
    private volatile zzun zzcba;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzwl)) {
            return null;
        }
        zzwl com_google_android_gms_internal_measurement_zzwl = (zzwl) obj;
        zzxe com_google_android_gms_internal_measurement_zzxe = this.zzcaz;
        zzxe com_google_android_gms_internal_measurement_zzxe2 = com_google_android_gms_internal_measurement_zzwl.zzcaz;
        if (com_google_android_gms_internal_measurement_zzxe == null && com_google_android_gms_internal_measurement_zzxe2 == null) {
            return zzud().equals(com_google_android_gms_internal_measurement_zzwl.zzud());
        }
        if (com_google_android_gms_internal_measurement_zzxe != null && com_google_android_gms_internal_measurement_zzxe2 != null) {
            return com_google_android_gms_internal_measurement_zzxe.equals(com_google_android_gms_internal_measurement_zzxe2);
        }
        if (com_google_android_gms_internal_measurement_zzxe != null) {
            return com_google_android_gms_internal_measurement_zzxe.equals(com_google_android_gms_internal_measurement_zzwl.zzh(com_google_android_gms_internal_measurement_zzxe.zzwq()));
        }
        return zzh(com_google_android_gms_internal_measurement_zzxe2.zzwq()).equals(com_google_android_gms_internal_measurement_zzxe2);
    }

    private final com.google.android.gms.internal.measurement.zzxe zzh(com.google.android.gms.internal.measurement.zzxe r2) {
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
        r1 = this;
        r0 = r1.zzcaz;
        if (r0 != 0) goto L_0x001d;
    L_0x0004:
        monitor-enter(r1);
        r0 = r1.zzcaz;	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x000b;	 Catch:{ all -> 0x001a }
    L_0x0009:
        monitor-exit(r1);	 Catch:{ all -> 0x001a }
        goto L_0x001d;
    L_0x000b:
        r1.zzcaz = r2;	 Catch:{ zzwe -> 0x0012 }
        r0 = com.google.android.gms.internal.measurement.zzun.zzbuu;	 Catch:{ zzwe -> 0x0012 }
        r1.zzcba = r0;	 Catch:{ zzwe -> 0x0012 }
        goto L_0x0018;
    L_0x0012:
        r1.zzcaz = r2;	 Catch:{ all -> 0x001a }
        r2 = com.google.android.gms.internal.measurement.zzun.zzbuu;	 Catch:{ all -> 0x001a }
        r1.zzcba = r2;	 Catch:{ all -> 0x001a }
    L_0x0018:
        monitor-exit(r1);	 Catch:{ all -> 0x001a }
        goto L_0x001d;	 Catch:{ all -> 0x001a }
    L_0x001a:
        r2 = move-exception;	 Catch:{ all -> 0x001a }
        monitor-exit(r1);	 Catch:{ all -> 0x001a }
        throw r2;
    L_0x001d:
        r2 = r1.zzcaz;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzwl.zzh(com.google.android.gms.internal.measurement.zzxe):com.google.android.gms.internal.measurement.zzxe");
    }

    public final zzxe zzi(zzxe com_google_android_gms_internal_measurement_zzxe) {
        zzxe com_google_android_gms_internal_measurement_zzxe2 = this.zzcaz;
        this.zzcay = null;
        this.zzcba = null;
        this.zzcaz = com_google_android_gms_internal_measurement_zzxe;
        return com_google_android_gms_internal_measurement_zzxe2;
    }

    public final int zzwe() {
        if (this.zzcba != null) {
            return this.zzcba.size();
        }
        return this.zzcaz != null ? this.zzcaz.zzwe() : 0;
    }

    public final zzun zzud() {
        if (this.zzcba != null) {
            return this.zzcba;
        }
        synchronized (this) {
            if (this.zzcba != null) {
                zzun com_google_android_gms_internal_measurement_zzun = this.zzcba;
                return com_google_android_gms_internal_measurement_zzun;
            }
            if (this.zzcaz == null) {
                this.zzcba = zzun.zzbuu;
            } else {
                this.zzcba = this.zzcaz.zzud();
            }
            com_google_android_gms_internal_measurement_zzun = this.zzcba;
            return com_google_android_gms_internal_measurement_zzun;
        }
    }
}
