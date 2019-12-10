package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzgc extends zzzl<zzgc> {
    public Integer zzawp;
    public String zzawq;
    public Boolean zzawr;
    public String[] zzaws;

    public zzgc() {
        this.zzawp = null;
        this.zzawq = null;
        this.zzawr = null;
        this.zzaws = zzzu.zzcgq;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgc)) {
            return false;
        }
        zzgc com_google_android_gms_internal_measurement_zzgc = (zzgc) obj;
        if (this.zzawp == null) {
            if (com_google_android_gms_internal_measurement_zzgc.zzawp != null) {
                return false;
            }
        } else if (!this.zzawp.equals(com_google_android_gms_internal_measurement_zzgc.zzawp)) {
            return false;
        }
        if (this.zzawq == null) {
            if (com_google_android_gms_internal_measurement_zzgc.zzawq != null) {
                return false;
            }
        } else if (!this.zzawq.equals(com_google_android_gms_internal_measurement_zzgc.zzawq)) {
            return false;
        }
        if (this.zzawr == null) {
            if (com_google_android_gms_internal_measurement_zzgc.zzawr != null) {
                return false;
            }
        } else if (!this.zzawr.equals(com_google_android_gms_internal_measurement_zzgc.zzawr)) {
            return false;
        }
        if (!zzzp.equals(this.zzaws, com_google_android_gms_internal_measurement_zzgc.zzaws)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgc.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgc.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgc.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzawp == null ? 0 : this.zzawp.intValue())) * 31) + (this.zzawq == null ? 0 : this.zzawq.hashCode())) * 31) + (this.zzawr == null ? 0 : this.zzawr.hashCode())) * 31) + zzzp.hashCode(this.zzaws)) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i = this.zzcfx.hashCode();
            }
        }
        return hashCode + i;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzawp != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(1, this.zzawp.intValue());
        }
        if (this.zzawq != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(2, this.zzawq);
        }
        if (this.zzawr != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(3, this.zzawr.booleanValue());
        }
        if (this.zzaws != null && this.zzaws.length > 0) {
            for (String str : this.zzaws) {
                if (str != null) {
                    com_google_android_gms_internal_measurement_zzzj.zzb(4, str);
                }
            }
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int zzf = super.zzf();
        if (this.zzawp != null) {
            zzf += zzzj.zzh(1, this.zzawp.intValue());
        }
        if (this.zzawq != null) {
            zzf += zzzj.zzc(2, this.zzawq);
        }
        if (this.zzawr != null) {
            this.zzawr.booleanValue();
            zzf += zzzj.zzbc(3) + 1;
        }
        if (this.zzaws == null || this.zzaws.length <= 0) {
            return zzf;
        }
        int i = 0;
        int i2 = 0;
        for (String str : this.zzaws) {
            if (str != null) {
                i2++;
                i += zzzj.zzge(str);
            }
        }
        return (zzf + i) + (i2 * 1);
    }

    private final com.google.android.gms.internal.measurement.zzgc zzd(com.google.android.gms.internal.measurement.zzzi r7) throws java.io.IOException {
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
        if (r0 == 0) goto L_0x0096;
    L_0x0006:
        r1 = 8;
        if (r0 == r1) goto L_0x0061;
    L_0x000a:
        r1 = 18;
        if (r0 == r1) goto L_0x005a;
    L_0x000e:
        r1 = 24;
        if (r0 == r1) goto L_0x004f;
    L_0x0012:
        r1 = 34;
        if (r0 == r1) goto L_0x001d;
    L_0x0016:
        r0 = super.zza(r7, r0);
        if (r0 != 0) goto L_0x0000;
    L_0x001c:
        return r6;
    L_0x001d:
        r0 = com.google.android.gms.internal.measurement.zzzu.zzb(r7, r1);
        r1 = r6.zzaws;
        r2 = 0;
        if (r1 != 0) goto L_0x0028;
    L_0x0026:
        r1 = 0;
        goto L_0x002b;
    L_0x0028:
        r1 = r6.zzaws;
        r1 = r1.length;
    L_0x002b:
        r0 = r0 + r1;
        r0 = new java.lang.String[r0];
        if (r1 == 0) goto L_0x0035;
    L_0x0030:
        r3 = r6.zzaws;
        java.lang.System.arraycopy(r3, r2, r0, r2, r1);
    L_0x0035:
        r2 = r0.length;
        r2 = r2 + -1;
        if (r1 >= r2) goto L_0x0046;
    L_0x003a:
        r2 = r7.readString();
        r0[r1] = r2;
        r7.zzuq();
        r1 = r1 + 1;
        goto L_0x0035;
    L_0x0046:
        r2 = r7.readString();
        r0[r1] = r2;
        r6.zzaws = r0;
        goto L_0x0000;
    L_0x004f:
        r0 = r7.zzuw();
        r0 = java.lang.Boolean.valueOf(r0);
        r6.zzawr = r0;
        goto L_0x0000;
    L_0x005a:
        r0 = r7.readString();
        r6.zzawq = r0;
        goto L_0x0000;
    L_0x0061:
        r1 = r7.getPosition();
        r2 = r7.zzvi();	 Catch:{ IllegalArgumentException -> 0x008e }
        if (r2 < 0) goto L_0x0075;	 Catch:{ IllegalArgumentException -> 0x008e }
    L_0x006b:
        r3 = 6;	 Catch:{ IllegalArgumentException -> 0x008e }
        if (r2 > r3) goto L_0x0075;	 Catch:{ IllegalArgumentException -> 0x008e }
    L_0x006e:
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ IllegalArgumentException -> 0x008e }
        r6.zzawp = r2;	 Catch:{ IllegalArgumentException -> 0x008e }
        goto L_0x0000;	 Catch:{ IllegalArgumentException -> 0x008e }
    L_0x0075:
        r3 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x008e }
        r4 = 41;	 Catch:{ IllegalArgumentException -> 0x008e }
        r5 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x008e }
        r5.<init>(r4);	 Catch:{ IllegalArgumentException -> 0x008e }
        r5.append(r2);	 Catch:{ IllegalArgumentException -> 0x008e }
        r2 = " is not a valid enum MatchType";	 Catch:{ IllegalArgumentException -> 0x008e }
        r5.append(r2);	 Catch:{ IllegalArgumentException -> 0x008e }
        r2 = r5.toString();	 Catch:{ IllegalArgumentException -> 0x008e }
        r3.<init>(r2);	 Catch:{ IllegalArgumentException -> 0x008e }
        throw r3;	 Catch:{ IllegalArgumentException -> 0x008e }
    L_0x008e:
        r7.zzca(r1);
        r6.zza(r7, r0);
        goto L_0x0000;
    L_0x0096:
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzgc.zzd(com.google.android.gms.internal.measurement.zzzi):com.google.android.gms.internal.measurement.zzgc");
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        return zzd(com_google_android_gms_internal_measurement_zzzi);
    }
}
