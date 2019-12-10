package com.google.android.gms.cast;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Objects;
import java.util.List;

public final class zzan {
    private String name;
    private int repeatMode;
    private String zzdk;
    private String zzed;
    private int zzee;
    private zzam zzef;
    private List<MediaQueueItem> zzeg;
    private int zzeh;
    private double zzei;

    public final void zze(org.json.JSONObject r14) {
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
        r13 = this;
        if (r14 != 0) goto L_0x0003;
    L_0x0002:
        return;
    L_0x0003:
        r0 = "id";
        r1 = 0;
        r0 = r14.optString(r0, r1);
        r13.zzed = r0;
        r0 = "entity";
        r0 = r14.optString(r0, r1);
        r13.zzdk = r0;
        r0 = "queueType";
        r0 = r14.optString(r0);
        r2 = -1;
        r3 = r0.hashCode();
        r4 = 0;
        r5 = 8;
        r6 = 7;
        r7 = 6;
        r8 = 5;
        r9 = 4;
        r10 = 3;
        r11 = 2;
        r12 = 1;
        switch(r3) {
            case -1803151310: goto L_0x007e;
            case -1758903120: goto L_0x0074;
            case -1632865838: goto L_0x006a;
            case -1319760993: goto L_0x0060;
            case -1088524588: goto L_0x0056;
            case 62359119: goto L_0x004c;
            case 73549584: goto L_0x0041;
            case 393100598: goto L_0x0037;
            case 902303413: goto L_0x002d;
            default: goto L_0x002c;
        };
    L_0x002c:
        goto L_0x0087;
    L_0x002d:
        r3 = "LIVE_TV";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0087;
    L_0x0035:
        r2 = 7;
        goto L_0x0087;
    L_0x0037:
        r3 = "VIDEO_PLAYLIST";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0087;
    L_0x003f:
        r2 = 6;
        goto L_0x0087;
    L_0x0041:
        r3 = "MOVIE";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0087;
    L_0x0049:
        r2 = 8;
        goto L_0x0087;
    L_0x004c:
        r3 = "ALBUM";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0087;
    L_0x0054:
        r2 = 0;
        goto L_0x0087;
    L_0x0056:
        r3 = "TV_SERIES";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0087;
    L_0x005e:
        r2 = 5;
        goto L_0x0087;
    L_0x0060:
        r3 = "AUDIOBOOK";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0087;
    L_0x0068:
        r2 = 2;
        goto L_0x0087;
    L_0x006a:
        r3 = "PLAYLIST";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0087;
    L_0x0072:
        r2 = 1;
        goto L_0x0087;
    L_0x0074:
        r3 = "RADIO_STATION";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0087;
    L_0x007c:
        r2 = 3;
        goto L_0x0087;
    L_0x007e:
        r3 = "PODCAST_SERIES";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0087;
    L_0x0086:
        r2 = 4;
    L_0x0087:
        switch(r2) {
            case 0: goto L_0x00a5;
            case 1: goto L_0x00a2;
            case 2: goto L_0x009f;
            case 3: goto L_0x009c;
            case 4: goto L_0x0099;
            case 5: goto L_0x0096;
            case 6: goto L_0x0093;
            case 7: goto L_0x0090;
            case 8: goto L_0x008b;
            default: goto L_0x008a;
        };
    L_0x008a:
        goto L_0x00a7;
    L_0x008b:
        r0 = 9;
        r13.zzee = r0;
        goto L_0x00a7;
    L_0x0090:
        r13.zzee = r5;
        goto L_0x00a7;
    L_0x0093:
        r13.zzee = r6;
        goto L_0x00a7;
    L_0x0096:
        r13.zzee = r7;
        goto L_0x00a7;
    L_0x0099:
        r13.zzee = r8;
        goto L_0x00a7;
    L_0x009c:
        r13.zzee = r9;
        goto L_0x00a7;
    L_0x009f:
        r13.zzee = r10;
        goto L_0x00a7;
    L_0x00a2:
        r13.zzee = r11;
        goto L_0x00a7;
    L_0x00a5:
        r13.zzee = r12;
    L_0x00a7:
        r0 = "name";
        r0 = r14.optString(r0, r1);
        r13.name = r0;
        r0 = "containerMetadata";
        r0 = r14.has(r0);
        if (r0 == 0) goto L_0x00c9;
    L_0x00b7:
        r0 = new com.google.android.gms.cast.zzam;
        r0.<init>();
        r13.zzef = r0;
        r0 = r13.zzef;
        r1 = "containerMetadata";
        r1 = r14.optJSONObject(r1);
        r0.zze(r1);
    L_0x00c9:
        r0 = "repeatMode";
        r0 = r14.optString(r0);
        r0 = com.google.android.gms.internal.cast.zzdq.zzu(r0);
        if (r0 == 0) goto L_0x00db;
    L_0x00d5:
        r0 = r0.intValue();
        r13.repeatMode = r0;
    L_0x00db:
        r0 = "items";
        r0 = r14.optJSONArray(r0);
        if (r0 == 0) goto L_0x0103;
    L_0x00e3:
        r1 = new java.util.ArrayList;
        r1.<init>();
        r13.zzeg = r1;
    L_0x00ea:
        r1 = r0.length();
        if (r4 >= r1) goto L_0x0103;
    L_0x00f0:
        r1 = r0.optJSONObject(r4);
        if (r1 == 0) goto L_0x0100;
    L_0x00f6:
        r2 = r13.zzeg;	 Catch:{ JSONException -> 0x0100 }
        r3 = new com.google.android.gms.cast.MediaQueueItem;	 Catch:{ JSONException -> 0x0100 }
        r3.<init>(r1);	 Catch:{ JSONException -> 0x0100 }
        r2.add(r3);	 Catch:{ JSONException -> 0x0100 }
    L_0x0100:
        r4 = r4 + 1;
        goto L_0x00ea;
    L_0x0103:
        r0 = "startIndex";
        r1 = r13.zzeh;
        r0 = r14.optInt(r0, r1);
        r13.zzeh = r0;
        r0 = "startTime";
        r1 = r13.zzei;
        r0 = r14.optDouble(r0, r1);
        r13.zzei = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzan.zze(org.json.JSONObject):void");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzan)) {
            return false;
        }
        zzan com_google_android_gms_cast_zzan = (zzan) obj;
        return TextUtils.equals(this.zzed, com_google_android_gms_cast_zzan.zzed) && TextUtils.equals(this.zzdk, com_google_android_gms_cast_zzan.zzdk) && this.zzee == com_google_android_gms_cast_zzan.zzee && TextUtils.equals(this.name, com_google_android_gms_cast_zzan.name) && Objects.equal(this.zzef, com_google_android_gms_cast_zzan.zzef) && this.repeatMode == com_google_android_gms_cast_zzan.repeatMode && Objects.equal(this.zzeg, com_google_android_gms_cast_zzan.zzeg) && this.zzeh == com_google_android_gms_cast_zzan.zzeh && this.zzei == com_google_android_gms_cast_zzan.zzei;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzed, this.zzdk, Integer.valueOf(this.zzee), this.name, this.zzef, Integer.valueOf(this.repeatMode), this.zzeg, Integer.valueOf(this.zzeh), Double.valueOf(this.zzei));
    }
}
