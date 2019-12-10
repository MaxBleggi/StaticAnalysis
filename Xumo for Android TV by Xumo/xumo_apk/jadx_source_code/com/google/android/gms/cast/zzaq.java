package com.google.android.gms.cast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.cast.zzdh;

public final class zzaq {
    private static final zzdh zzbe = new zzdh("MediaSeekableRange");
    private final long startTime;
    private final long zzeo;
    private final boolean zzep;
    private final boolean zzeq;

    private zzaq(long j, long j2, boolean z, boolean z2) {
        this.startTime = Math.max(j, 0);
        this.zzeo = Math.max(j2, 0);
        this.zzep = z;
        this.zzeq = z2;
    }

    public final int hashCode() {
        return Objects.hashCode(Long.valueOf(this.startTime), Long.valueOf(this.zzeo), Boolean.valueOf(this.zzep), Boolean.valueOf(this.zzeq));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaq)) {
            return false;
        }
        zzaq com_google_android_gms_cast_zzaq = (zzaq) obj;
        return this.startTime == com_google_android_gms_cast_zzaq.startTime && this.zzeo == com_google_android_gms_cast_zzaq.zzeo && this.zzep == com_google_android_gms_cast_zzaq.zzep && this.zzeq == com_google_android_gms_cast_zzaq.zzeq;
    }

    static com.google.android.gms.cast.zzaq zzg(org.json.JSONObject r12) {
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
        r0 = 0;
        if (r12 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = "startTime";
        r1 = r12.has(r1);
        if (r1 == 0) goto L_0x0077;
    L_0x000c:
        r1 = "endTime";
        r1 = r12.has(r1);
        if (r1 == 0) goto L_0x0077;
    L_0x0014:
        r1 = "isMovingWindow";
        r1 = r12.has(r1);
        if (r1 == 0) goto L_0x0077;
    L_0x001c:
        r1 = "isLiveDone";
        r1 = r12.has(r1);
        if (r1 != 0) goto L_0x0025;
    L_0x0024:
        goto L_0x0077;
    L_0x0025:
        r1 = "startTime";	 Catch:{ JSONException -> 0x004f }
        r1 = r12.getDouble(r1);	 Catch:{ JSONException -> 0x004f }
        r3 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;	 Catch:{ JSONException -> 0x004f }
        r1 = r1 * r3;	 Catch:{ JSONException -> 0x004f }
        r6 = (long) r1;	 Catch:{ JSONException -> 0x004f }
        r1 = "endTime";	 Catch:{ JSONException -> 0x004f }
        r1 = r12.getDouble(r1);	 Catch:{ JSONException -> 0x004f }
        r1 = r1 * r3;	 Catch:{ JSONException -> 0x004f }
        r8 = (long) r1;	 Catch:{ JSONException -> 0x004f }
        r1 = "isMovingWindow";	 Catch:{ JSONException -> 0x004f }
        r10 = r12.getBoolean(r1);	 Catch:{ JSONException -> 0x004f }
        r1 = "isLiveDone";	 Catch:{ JSONException -> 0x004f }
        r11 = r12.getBoolean(r1);	 Catch:{ JSONException -> 0x004f }
        r1 = new com.google.android.gms.cast.zzaq;	 Catch:{ JSONException -> 0x004f }
        r5 = r1;	 Catch:{ JSONException -> 0x004f }
        r5.<init>(r6, r8, r10, r11);	 Catch:{ JSONException -> 0x004f }
        return r1;
    L_0x004f:
        r1 = zzbe;
        r12 = java.lang.String.valueOf(r12);
        r2 = java.lang.String.valueOf(r12);
        r2 = r2.length();
        r2 = r2 + 39;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Ignoring Malformed MediaSeekableRange: ";
        r3.append(r2);
        r3.append(r12);
        r12 = r3.toString();
        r2 = 0;
        r2 = new java.lang.Object[r2];
        r1.e(r12, r2);
        return r0;
    L_0x0077:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzaq.zzg(org.json.JSONObject):com.google.android.gms.cast.zzaq");
    }
}
