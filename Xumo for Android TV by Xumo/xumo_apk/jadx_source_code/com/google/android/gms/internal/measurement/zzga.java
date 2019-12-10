package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzga extends zzzl<zzga> {
    public Integer zzawh;
    public Boolean zzawi;
    public String zzawj;
    public String zzawk;
    public String zzawl;

    public zzga() {
        this.zzawh = null;
        this.zzawi = null;
        this.zzawj = null;
        this.zzawk = null;
        this.zzawl = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzga)) {
            return false;
        }
        zzga com_google_android_gms_internal_measurement_zzga = (zzga) obj;
        if (this.zzawh == null) {
            if (com_google_android_gms_internal_measurement_zzga.zzawh != null) {
                return false;
            }
        } else if (!this.zzawh.equals(com_google_android_gms_internal_measurement_zzga.zzawh)) {
            return false;
        }
        if (this.zzawi == null) {
            if (com_google_android_gms_internal_measurement_zzga.zzawi != null) {
                return false;
            }
        } else if (!this.zzawi.equals(com_google_android_gms_internal_measurement_zzga.zzawi)) {
            return false;
        }
        if (this.zzawj == null) {
            if (com_google_android_gms_internal_measurement_zzga.zzawj != null) {
                return false;
            }
        } else if (!this.zzawj.equals(com_google_android_gms_internal_measurement_zzga.zzawj)) {
            return false;
        }
        if (this.zzawk == null) {
            if (com_google_android_gms_internal_measurement_zzga.zzawk != null) {
                return false;
            }
        } else if (!this.zzawk.equals(com_google_android_gms_internal_measurement_zzga.zzawk)) {
            return false;
        }
        if (this.zzawl == null) {
            if (com_google_android_gms_internal_measurement_zzga.zzawl != null) {
                return false;
            }
        } else if (!this.zzawl.equals(com_google_android_gms_internal_measurement_zzga.zzawl)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzga.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzga.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzga.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzawh == null ? 0 : this.zzawh.intValue())) * 31) + (this.zzawi == null ? 0 : this.zzawi.hashCode())) * 31) + (this.zzawj == null ? 0 : this.zzawj.hashCode())) * 31) + (this.zzawk == null ? 0 : this.zzawk.hashCode())) * 31) + (this.zzawl == null ? 0 : this.zzawl.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i = this.zzcfx.hashCode();
            }
        }
        return hashCode + i;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzawh != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(1, this.zzawh.intValue());
        }
        if (this.zzawi != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(2, this.zzawi.booleanValue());
        }
        if (this.zzawj != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(3, this.zzawj);
        }
        if (this.zzawk != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(4, this.zzawk);
        }
        if (this.zzawl != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(5, this.zzawl);
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzawh != null) {
            zzf += zzzj.zzh(1, this.zzawh.intValue());
        }
        if (this.zzawi != null) {
            this.zzawi.booleanValue();
            zzf += zzzj.zzbc(2) + 1;
        }
        if (this.zzawj != null) {
            zzf += zzzj.zzc(3, this.zzawj);
        }
        if (this.zzawk != null) {
            zzf += zzzj.zzc(4, this.zzawk);
        }
        return this.zzawl != null ? zzf + zzzj.zzc(5, this.zzawl) : zzf;
    }

    private final com.google.android.gms.internal.measurement.zzga zzc(com.google.android.gms.internal.measurement.zzzi r7) throws java.io.IOException {
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
        r6 = this;
    L_0x0000:
        r0 = r7.zzuq();
        if (r0 == 0) goto L_0x0075;
    L_0x0006:
        r1 = 8;
        if (r0 == r1) goto L_0x0041;
    L_0x000a:
        r1 = 16;
        if (r0 == r1) goto L_0x0036;
    L_0x000e:
        r1 = 26;
        if (r0 == r1) goto L_0x002f;
    L_0x0012:
        r1 = 34;
        if (r0 == r1) goto L_0x0028;
    L_0x0016:
        r1 = 42;
        if (r0 == r1) goto L_0x0021;
    L_0x001a:
        r0 = super.zza(r7, r0);
        if (r0 != 0) goto L_0x0000;
    L_0x0020:
        return r6;
    L_0x0021:
        r0 = r7.readString();
        r6.zzawl = r0;
        goto L_0x0000;
    L_0x0028:
        r0 = r7.readString();
        r6.zzawk = r0;
        goto L_0x0000;
    L_0x002f:
        r0 = r7.readString();
        r6.zzawj = r0;
        goto L_0x0000;
    L_0x0036:
        r0 = r7.zzuw();
        r0 = java.lang.Boolean.valueOf(r0);
        r6.zzawi = r0;
        goto L_0x0000;
    L_0x0041:
        r1 = r7.getPosition();
        r2 = r7.zzvi();	 Catch:{ IllegalArgumentException -> 0x006e }
        if (r2 < 0) goto L_0x0055;	 Catch:{ IllegalArgumentException -> 0x006e }
    L_0x004b:
        r3 = 4;	 Catch:{ IllegalArgumentException -> 0x006e }
        if (r2 > r3) goto L_0x0055;	 Catch:{ IllegalArgumentException -> 0x006e }
    L_0x004e:
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ IllegalArgumentException -> 0x006e }
        r6.zzawh = r2;	 Catch:{ IllegalArgumentException -> 0x006e }
        goto L_0x0000;	 Catch:{ IllegalArgumentException -> 0x006e }
    L_0x0055:
        r3 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x006e }
        r4 = 46;	 Catch:{ IllegalArgumentException -> 0x006e }
        r5 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x006e }
        r5.<init>(r4);	 Catch:{ IllegalArgumentException -> 0x006e }
        r5.append(r2);	 Catch:{ IllegalArgumentException -> 0x006e }
        r2 = " is not a valid enum ComparisonType";	 Catch:{ IllegalArgumentException -> 0x006e }
        r5.append(r2);	 Catch:{ IllegalArgumentException -> 0x006e }
        r2 = r5.toString();	 Catch:{ IllegalArgumentException -> 0x006e }
        r3.<init>(r2);	 Catch:{ IllegalArgumentException -> 0x006e }
        throw r3;	 Catch:{ IllegalArgumentException -> 0x006e }
    L_0x006e:
        r7.zzca(r1);
        r6.zza(r7, r0);
        goto L_0x0000;
    L_0x0075:
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzga.zzc(com.google.android.gms.internal.measurement.zzzi):com.google.android.gms.internal.measurement.zzga");
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        return zzc(com_google_android_gms_internal_measurement_zzzi);
    }
}
