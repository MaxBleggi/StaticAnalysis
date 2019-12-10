package com.google.android.gms.internal.cast;

import android.os.SystemClock;
import androidx.annotation.NonNull;
import com.google.android.gms.cast.AdBreakClipInfo;
import com.google.android.gms.cast.AdBreakStatus;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadOptions;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.cast.zzbt;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@VisibleForTesting
public final class zzdi extends zzch {
    public static final String NAMESPACE = zzcv.zzp("com.google.cast.media");
    private long zzya;
    private MediaStatus zzyb;
    private Long zzyc;
    private zzdk zzyd;
    private final zzdo zzye = new zzdo(86400000);
    private final zzdo zzyf = new zzdo(86400000);
    private final zzdo zzyg = new zzdo(86400000);
    private final zzdo zzyh = new zzdo(86400000);
    private final zzdo zzyi = new zzdo(NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
    private final zzdo zzyj = new zzdo(86400000);
    private final zzdo zzyk = new zzdo(86400000);
    private final zzdo zzyl = new zzdo(86400000);
    private final zzdo zzym = new zzdo(86400000);
    private final zzdo zzyn = new zzdo(86400000);
    private final zzdo zzyo = new zzdo(86400000);
    private final zzdo zzyp = new zzdo(86400000);
    private final zzdo zzyq = new zzdo(86400000);
    private final zzdo zzyr = new zzdo(86400000);
    private final zzdo zzys = new zzdo(86400000);
    private final zzdo zzyt = new zzdo(86400000);
    private final zzdo zzyu = new zzdo(86400000);
    private final zzdo zzyv = new zzdo(86400000);

    public zzdi(String str) {
        super(NAMESPACE, "MediaControlChannel", null);
        zza(this.zzye);
        zza(this.zzyf);
        zza(this.zzyg);
        zza(this.zzyh);
        zza(this.zzyi);
        zza(this.zzyj);
        zza(this.zzyk);
        zza(this.zzyl);
        zza(this.zzym);
        zza(this.zzyn);
        zza(this.zzyo);
        zza(this.zzyp);
        zza(this.zzyq);
        zza(this.zzyr);
        zza(this.zzys);
        zza(this.zzyu);
        zza(this.zzyu);
        zza(this.zzyv);
        zzdp();
    }

    public final void zza(zzdk com_google_android_gms_internal_cast_zzdk) {
        this.zzyd = com_google_android_gms_internal_cast_zzdk;
    }

    public final long zza(@NonNull zzdn com_google_android_gms_internal_cast_zzdn, @NonNull MediaInfo mediaInfo, @NonNull MediaLoadOptions mediaLoadOptions) {
        return zza(com_google_android_gms_internal_cast_zzdn, mediaInfo, null, mediaLoadOptions);
    }

    private final long zza(@androidx.annotation.NonNull com.google.android.gms.internal.cast.zzdn r7, @androidx.annotation.Nullable com.google.android.gms.cast.MediaInfo r8, @androidx.annotation.Nullable com.google.android.gms.cast.zzan r9, @androidx.annotation.NonNull com.google.android.gms.cast.MediaLoadOptions r10) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException {
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
        if (r8 == 0) goto L_0x009a;
    L_0x0002:
        r9 = new org.json.JSONObject;
        r9.<init>();
        r0 = r6.zzdf();
        r2 = r6.zzye;
        r2.zza(r0, r7);
        r7 = "requestId";	 Catch:{ JSONException -> 0x0091 }
        r9.put(r7, r0);	 Catch:{ JSONException -> 0x0091 }
        r7 = "type";	 Catch:{ JSONException -> 0x0091 }
        r2 = "LOAD";	 Catch:{ JSONException -> 0x0091 }
        r9.put(r7, r2);	 Catch:{ JSONException -> 0x0091 }
        if (r8 == 0) goto L_0x0027;	 Catch:{ JSONException -> 0x0091 }
    L_0x001e:
        r7 = "media";	 Catch:{ JSONException -> 0x0091 }
        r8 = r8.toJson();	 Catch:{ JSONException -> 0x0091 }
        r9.put(r7, r8);	 Catch:{ JSONException -> 0x0091 }
    L_0x0027:
        r7 = "autoplay";	 Catch:{ JSONException -> 0x0091 }
        r8 = r10.getAutoplay();	 Catch:{ JSONException -> 0x0091 }
        r9.put(r7, r8);	 Catch:{ JSONException -> 0x0091 }
        r7 = "currentTime";	 Catch:{ JSONException -> 0x0091 }
        r2 = r10.getPlayPosition();	 Catch:{ JSONException -> 0x0091 }
        r2 = (double) r2;
        r4 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        java.lang.Double.isNaN(r2);
        r2 = r2 / r4;
        r9.put(r7, r2);	 Catch:{ JSONException -> 0x0091 }
        r7 = "playbackRate";	 Catch:{ JSONException -> 0x0091 }
        r2 = r10.getPlaybackRate();	 Catch:{ JSONException -> 0x0091 }
        r9.put(r7, r2);	 Catch:{ JSONException -> 0x0091 }
        r7 = r10.getCredentials();	 Catch:{ JSONException -> 0x0091 }
        if (r7 == 0) goto L_0x005b;	 Catch:{ JSONException -> 0x0091 }
    L_0x0052:
        r7 = "credentials";	 Catch:{ JSONException -> 0x0091 }
        r8 = r10.getCredentials();	 Catch:{ JSONException -> 0x0091 }
        r9.put(r7, r8);	 Catch:{ JSONException -> 0x0091 }
    L_0x005b:
        r7 = r10.getCredentialsType();	 Catch:{ JSONException -> 0x0091 }
        if (r7 == 0) goto L_0x006a;	 Catch:{ JSONException -> 0x0091 }
    L_0x0061:
        r7 = "credentialsType";	 Catch:{ JSONException -> 0x0091 }
        r8 = r10.getCredentialsType();	 Catch:{ JSONException -> 0x0091 }
        r9.put(r7, r8);	 Catch:{ JSONException -> 0x0091 }
    L_0x006a:
        r7 = r10.getActiveTrackIds();	 Catch:{ JSONException -> 0x0091 }
        if (r7 == 0) goto L_0x0086;	 Catch:{ JSONException -> 0x0091 }
    L_0x0070:
        r8 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0091 }
        r8.<init>();	 Catch:{ JSONException -> 0x0091 }
        r2 = 0;	 Catch:{ JSONException -> 0x0091 }
    L_0x0076:
        r3 = r7.length;	 Catch:{ JSONException -> 0x0091 }
        if (r2 >= r3) goto L_0x0081;	 Catch:{ JSONException -> 0x0091 }
    L_0x0079:
        r3 = r7[r2];	 Catch:{ JSONException -> 0x0091 }
        r8.put(r2, r3);	 Catch:{ JSONException -> 0x0091 }
        r2 = r2 + 1;	 Catch:{ JSONException -> 0x0091 }
        goto L_0x0076;	 Catch:{ JSONException -> 0x0091 }
    L_0x0081:
        r7 = "activeTrackIds";	 Catch:{ JSONException -> 0x0091 }
        r9.put(r7, r8);	 Catch:{ JSONException -> 0x0091 }
    L_0x0086:
        r7 = r10.getCustomData();	 Catch:{ JSONException -> 0x0091 }
        if (r7 == 0) goto L_0x0091;	 Catch:{ JSONException -> 0x0091 }
    L_0x008c:
        r8 = "customData";	 Catch:{ JSONException -> 0x0091 }
        r9.put(r8, r7);	 Catch:{ JSONException -> 0x0091 }
    L_0x0091:
        r7 = r9.toString();
        r8 = 0;
        r6.zza(r7, r0, r8);
        return r0;
    L_0x009a:
        r7 = new java.lang.IllegalArgumentException;
        r8 = "At least one of MediaInfo or MediaQueueData should be non-null";
        r7.<init>(r8);
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, com.google.android.gms.cast.MediaInfo, com.google.android.gms.cast.zzan, com.google.android.gms.cast.MediaLoadOptions):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r6, org.json.JSONObject r7) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl {
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
        r5 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzyf;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x002a }
        r6 = "type";	 Catch:{ JSONException -> 0x002a }
        r3 = "PAUSE";	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x002a }
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x002a }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x002a }
        if (r7 == 0) goto L_0x002a;	 Catch:{ JSONException -> 0x002a }
    L_0x0025:
        r6 = "customData";	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r7);	 Catch:{ JSONException -> 0x002a }
    L_0x002a:
        r6 = r0.toString();
        r7 = 0;
        r5.zza(r6, r1, r7);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, org.json.JSONObject):long");
    }

    public final long zzb(com.google.android.gms.internal.cast.zzdn r6, org.json.JSONObject r7) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl {
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
        r5 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzyh;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x002a }
        r6 = "type";	 Catch:{ JSONException -> 0x002a }
        r3 = "STOP";	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x002a }
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x002a }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x002a }
        if (r7 == 0) goto L_0x002a;	 Catch:{ JSONException -> 0x002a }
    L_0x0025:
        r6 = "customData";	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r7);	 Catch:{ JSONException -> 0x002a }
    L_0x002a:
        r6 = r0.toString();
        r7 = 0;
        r5.zza(r6, r1, r7);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zzb(com.google.android.gms.internal.cast.zzdn, org.json.JSONObject):long");
    }

    public final long zzc(com.google.android.gms.internal.cast.zzdn r6, org.json.JSONObject r7) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl {
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
        r5 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzyg;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x002a }
        r6 = "type";	 Catch:{ JSONException -> 0x002a }
        r3 = "PLAY";	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x002a }
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x002a }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x002a }
        if (r7 == 0) goto L_0x002a;	 Catch:{ JSONException -> 0x002a }
    L_0x0025:
        r6 = "customData";	 Catch:{ JSONException -> 0x002a }
        r0.put(r6, r7);	 Catch:{ JSONException -> 0x002a }
    L_0x002a:
        r6 = r0.toString();
        r7 = 0;
        r5.zza(r6, r1, r7);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zzc(com.google.android.gms.internal.cast.zzdn, org.json.JSONObject):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r6, long r7, int r9, org.json.JSONObject r10) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl {
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
        r5 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = java.lang.Long.valueOf(r7);
        r5.zzyc = r3;
        r3 = r5.zzyi;
        r4 = new com.google.android.gms.internal.cast.zzdj;
        r4.<init>(r5, r6);
        r3.zza(r1, r4);
        r6 = "requestId";	 Catch:{ JSONException -> 0x0059 }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x0059 }
        r6 = "type";	 Catch:{ JSONException -> 0x0059 }
        r3 = "SEEK";	 Catch:{ JSONException -> 0x0059 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0059 }
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x0059 }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x0059 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0059 }
        r6 = "currentTime";	 Catch:{ JSONException -> 0x0059 }
        r7 = (double) r7;
        r3 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        java.lang.Double.isNaN(r7);
        r7 = r7 / r3;
        r0.put(r6, r7);	 Catch:{ JSONException -> 0x0059 }
        r6 = 1;	 Catch:{ JSONException -> 0x0059 }
        if (r9 != r6) goto L_0x0048;	 Catch:{ JSONException -> 0x0059 }
    L_0x0040:
        r6 = "resumeState";	 Catch:{ JSONException -> 0x0059 }
        r7 = "PLAYBACK_START";	 Catch:{ JSONException -> 0x0059 }
        r0.put(r6, r7);	 Catch:{ JSONException -> 0x0059 }
        goto L_0x0052;	 Catch:{ JSONException -> 0x0059 }
    L_0x0048:
        r6 = 2;	 Catch:{ JSONException -> 0x0059 }
        if (r9 != r6) goto L_0x0052;	 Catch:{ JSONException -> 0x0059 }
    L_0x004b:
        r6 = "resumeState";	 Catch:{ JSONException -> 0x0059 }
        r7 = "PLAYBACK_PAUSE";	 Catch:{ JSONException -> 0x0059 }
        r0.put(r6, r7);	 Catch:{ JSONException -> 0x0059 }
    L_0x0052:
        if (r10 == 0) goto L_0x0059;	 Catch:{ JSONException -> 0x0059 }
    L_0x0054:
        r6 = "customData";	 Catch:{ JSONException -> 0x0059 }
        r0.put(r6, r10);	 Catch:{ JSONException -> 0x0059 }
    L_0x0059:
        r6 = r0.toString();
        r7 = 0;
        r5.zza(r6, r1, r7);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, long, int, org.json.JSONObject):long");
    }

    public final long zza(zzdn com_google_android_gms_internal_cast_zzdn) throws IllegalStateException, zzdl {
        JSONObject jSONObject = new JSONObject();
        long zzdf = zzdf();
        this.zzyj.zza(zzdf, com_google_android_gms_internal_cast_zzdn);
        try {
            jSONObject.put("requestId", zzdf);
            jSONObject.put("type", "SKIP_AD");
            jSONObject.put("mediaSessionId", zzj());
        } catch (zzdn com_google_android_gms_internal_cast_zzdn2) {
            this.zzwg.w(String.format(Locale.ROOT, "Error creating SkipAd message: %s", new Object[]{com_google_android_gms_internal_cast_zzdn2.getMessage()}), new Object[0]);
        }
        zza(jSONObject.toString(), zzdf, null);
        return zzdf;
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r6, double r7, org.json.JSONObject r9) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl, java.lang.IllegalArgumentException {
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
        r5 = this;
        r0 = java.lang.Double.isInfinite(r7);
        if (r0 != 0) goto L_0x004e;
    L_0x0006:
        r0 = java.lang.Double.isNaN(r7);
        if (r0 != 0) goto L_0x004e;
    L_0x000c:
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzyj;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x0045 }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x0045 }
        r6 = "type";	 Catch:{ JSONException -> 0x0045 }
        r3 = "SET_VOLUME";	 Catch:{ JSONException -> 0x0045 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0045 }
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x0045 }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x0045 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0045 }
        r6 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0045 }
        r6.<init>();	 Catch:{ JSONException -> 0x0045 }
        r3 = "level";	 Catch:{ JSONException -> 0x0045 }
        r6.put(r3, r7);	 Catch:{ JSONException -> 0x0045 }
        r7 = "volume";	 Catch:{ JSONException -> 0x0045 }
        r0.put(r7, r6);	 Catch:{ JSONException -> 0x0045 }
        if (r9 == 0) goto L_0x0045;	 Catch:{ JSONException -> 0x0045 }
    L_0x0040:
        r6 = "customData";	 Catch:{ JSONException -> 0x0045 }
        r0.put(r6, r9);	 Catch:{ JSONException -> 0x0045 }
    L_0x0045:
        r6 = r0.toString();
        r7 = 0;
        r5.zza(r6, r1, r7);
        return r1;
    L_0x004e:
        r6 = new java.lang.IllegalArgumentException;
        r9 = 41;
        r0 = new java.lang.StringBuilder;
        r0.<init>(r9);
        r9 = "Volume cannot be ";
        r0.append(r9);
        r0.append(r7);
        r7 = r0.toString();
        r6.<init>(r7);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, double, org.json.JSONObject):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r6, boolean r7, org.json.JSONObject r8) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl {
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
        r5 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzyk;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x0039 }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x0039 }
        r6 = "type";	 Catch:{ JSONException -> 0x0039 }
        r3 = "SET_VOLUME";	 Catch:{ JSONException -> 0x0039 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0039 }
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x0039 }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x0039 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0039 }
        r6 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0039 }
        r6.<init>();	 Catch:{ JSONException -> 0x0039 }
        r3 = "muted";	 Catch:{ JSONException -> 0x0039 }
        r6.put(r3, r7);	 Catch:{ JSONException -> 0x0039 }
        r7 = "volume";	 Catch:{ JSONException -> 0x0039 }
        r0.put(r7, r6);	 Catch:{ JSONException -> 0x0039 }
        if (r8 == 0) goto L_0x0039;	 Catch:{ JSONException -> 0x0039 }
    L_0x0034:
        r6 = "customData";	 Catch:{ JSONException -> 0x0039 }
        r0.put(r6, r8);	 Catch:{ JSONException -> 0x0039 }
    L_0x0039:
        r6 = r0.toString();
        r7 = 0;
        r5.zza(r6, r1, r7);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, boolean, org.json.JSONObject):long");
    }

    public final long zzb(com.google.android.gms.internal.cast.zzdn r5, double r6, org.json.JSONObject r8) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl {
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
        r4 = this;
        r0 = r4.zzyb;
        if (r0 == 0) goto L_0x003e;
    L_0x0004:
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r4.zzdf();
        r3 = r4.zzyv;
        r3.zza(r1, r5);
        r5 = "requestId";	 Catch:{ JSONException -> 0x0035 }
        r0.put(r5, r1);	 Catch:{ JSONException -> 0x0035 }
        r5 = "type";	 Catch:{ JSONException -> 0x0035 }
        r3 = "SET_PLAYBACK_RATE";	 Catch:{ JSONException -> 0x0035 }
        r0.put(r5, r3);	 Catch:{ JSONException -> 0x0035 }
        r5 = "playbackRate";	 Catch:{ JSONException -> 0x0035 }
        r0.put(r5, r6);	 Catch:{ JSONException -> 0x0035 }
        r5 = "mediaSessionId";	 Catch:{ JSONException -> 0x0035 }
        r6 = r4.zzyb;	 Catch:{ JSONException -> 0x0035 }
        r6 = r6.zzj();	 Catch:{ JSONException -> 0x0035 }
        r0.put(r5, r6);	 Catch:{ JSONException -> 0x0035 }
        if (r8 == 0) goto L_0x0035;	 Catch:{ JSONException -> 0x0035 }
    L_0x0030:
        r5 = "customData";	 Catch:{ JSONException -> 0x0035 }
        r0.put(r5, r8);	 Catch:{ JSONException -> 0x0035 }
    L_0x0035:
        r5 = r0.toString();
        r6 = 0;
        r4.zza(r5, r1, r6);
        return r1;
    L_0x003e:
        r5 = new com.google.android.gms.internal.cast.zzdl;
        r5.<init>();
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zzb(com.google.android.gms.internal.cast.zzdn, double, org.json.JSONObject):long");
    }

    public final long zzb(com.google.android.gms.internal.cast.zzdn r6) throws java.lang.IllegalStateException {
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
        r5 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzyl;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x0029 }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x0029 }
        r6 = "type";	 Catch:{ JSONException -> 0x0029 }
        r3 = "GET_STATUS";	 Catch:{ JSONException -> 0x0029 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0029 }
        r6 = r5.zzyb;	 Catch:{ JSONException -> 0x0029 }
        if (r6 == 0) goto L_0x0029;	 Catch:{ JSONException -> 0x0029 }
    L_0x001e:
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x0029 }
        r3 = r5.zzyb;	 Catch:{ JSONException -> 0x0029 }
        r3 = r3.zzj();	 Catch:{ JSONException -> 0x0029 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0029 }
    L_0x0029:
        r6 = r0.toString();
        r0 = 0;
        r5.zza(r6, r1, r0);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zzb(com.google.android.gms.internal.cast.zzdn):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r7, long[] r8) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl {
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
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r6.zzdf();
        r3 = r6.zzym;
        r3.zza(r1, r7);
        r7 = "requestId";	 Catch:{ JSONException -> 0x0039 }
        r0.put(r7, r1);	 Catch:{ JSONException -> 0x0039 }
        r7 = "type";	 Catch:{ JSONException -> 0x0039 }
        r3 = "EDIT_TRACKS_INFO";	 Catch:{ JSONException -> 0x0039 }
        r0.put(r7, r3);	 Catch:{ JSONException -> 0x0039 }
        r7 = "mediaSessionId";	 Catch:{ JSONException -> 0x0039 }
        r3 = r6.zzj();	 Catch:{ JSONException -> 0x0039 }
        r0.put(r7, r3);	 Catch:{ JSONException -> 0x0039 }
        r7 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0039 }
        r7.<init>();	 Catch:{ JSONException -> 0x0039 }
        r3 = 0;	 Catch:{ JSONException -> 0x0039 }
    L_0x0029:
        r4 = r8.length;	 Catch:{ JSONException -> 0x0039 }
        if (r3 >= r4) goto L_0x0034;	 Catch:{ JSONException -> 0x0039 }
    L_0x002c:
        r4 = r8[r3];	 Catch:{ JSONException -> 0x0039 }
        r7.put(r3, r4);	 Catch:{ JSONException -> 0x0039 }
        r3 = r3 + 1;	 Catch:{ JSONException -> 0x0039 }
        goto L_0x0029;	 Catch:{ JSONException -> 0x0039 }
    L_0x0034:
        r8 = "activeTrackIds";	 Catch:{ JSONException -> 0x0039 }
        r0.put(r8, r7);	 Catch:{ JSONException -> 0x0039 }
    L_0x0039:
        r7 = r0.toString();
        r8 = 0;
        r6.zza(r7, r1, r8);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, long[]):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r6, com.google.android.gms.cast.TextTrackStyle r7) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl {
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
        r5 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzyn;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x002e }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x002e }
        r6 = "type";	 Catch:{ JSONException -> 0x002e }
        r3 = "EDIT_TRACKS_INFO";	 Catch:{ JSONException -> 0x002e }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x002e }
        if (r7 == 0) goto L_0x0025;	 Catch:{ JSONException -> 0x002e }
    L_0x001c:
        r6 = "textTrackStyle";	 Catch:{ JSONException -> 0x002e }
        r7 = r7.toJson();	 Catch:{ JSONException -> 0x002e }
        r0.put(r6, r7);	 Catch:{ JSONException -> 0x002e }
    L_0x0025:
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x002e }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x002e }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x002e }
    L_0x002e:
        r6 = r0.toString();
        r7 = 0;
        r5.zza(r6, r1, r7);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, com.google.android.gms.cast.TextTrackStyle):long");
    }

    public final long getApproximateStreamPosition() {
        MediaInfo mediaInfo = getMediaInfo();
        if (mediaInfo == null) {
            return 0;
        }
        if (this.zzyc != null) {
            return this.zzyc.longValue();
        }
        if (this.zzya == 0) {
            return 0;
        }
        double playbackRate = this.zzyb.getPlaybackRate();
        long streamPosition = this.zzyb.getStreamPosition();
        int playerState = this.zzyb.getPlayerState();
        if (playbackRate != 0.0d) {
            if (playerState == 2) {
                return zza(playbackRate, streamPosition, mediaInfo.getStreamDuration());
            }
        }
        return streamPosition;
    }

    public final long getApproximateAdBreakClipPositionMs() {
        if (this.zzya != 0) {
            if (this.zzyb != null) {
                AdBreakStatus adBreakStatus = this.zzyb.getAdBreakStatus();
                if (adBreakStatus == null) {
                    return 0;
                }
                AdBreakClipInfo currentAdBreakClip = this.zzyb.getCurrentAdBreakClip();
                if (currentAdBreakClip == null) {
                    return 0;
                }
                double d = 0.0d;
                if (this.zzyb.getPlaybackRate() == 0.0d && this.zzyb.getPlayerState() == 2) {
                    d = 1.0d;
                }
                return zza(d, adBreakStatus.getCurrentBreakClipTimeInMs(), currentAdBreakClip.getDurationInMs());
            }
        }
        return 0;
    }

    private final long zza(double d, long j, long j2) {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.zzya;
        if (elapsedRealtime < 0) {
            elapsedRealtime = 0;
        }
        if (elapsedRealtime == 0) {
            return j;
        }
        double d2 = (double) elapsedRealtime;
        Double.isNaN(d2);
        d = ((long) (d2 * d)) + j;
        if (j2 > 0 && d > j2) {
            d = j2;
        } else if (d < 0.0d) {
            d = 0.0d;
        }
        return d;
    }

    public final long getStreamDuration() {
        MediaInfo mediaInfo = getMediaInfo();
        return mediaInfo != null ? mediaInfo.getStreamDuration() : 0;
    }

    public final MediaStatus getMediaStatus() {
        return this.zzyb;
    }

    public final MediaInfo getMediaInfo() {
        return this.zzyb == null ? null : this.zzyb.getMediaInfo();
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r8, com.google.android.gms.cast.MediaQueueItem[] r9, int r10, int r11, long r12, org.json.JSONObject r14) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException {
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
        r7 = this;
        if (r9 == 0) goto L_0x00ce;
    L_0x0002:
        r0 = r9.length;
        if (r0 == 0) goto L_0x00ce;
    L_0x0005:
        if (r10 < 0) goto L_0x00b5;
    L_0x0007:
        r0 = r9.length;
        if (r10 >= r0) goto L_0x00b5;
    L_0x000a:
        r0 = -1;
        r2 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1));
        if (r2 == 0) goto L_0x0030;
    L_0x0010:
        r2 = 0;
        r4 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1));
        if (r4 < 0) goto L_0x0017;
    L_0x0016:
        goto L_0x0030;
    L_0x0017:
        r8 = new java.lang.IllegalArgumentException;
        r9 = 54;
        r10 = new java.lang.StringBuilder;
        r10.<init>(r9);
        r9 = "playPosition can not be negative: ";
        r10.append(r9);
        r10.append(r12);
        r9 = r10.toString();
        r8.<init>(r9);
        throw r8;
    L_0x0030:
        r2 = new org.json.JSONObject;
        r2.<init>();
        r3 = r7.zzdf();
        r5 = r7.zzye;
        r5.zza(r3, r8);
        r8 = "requestId";	 Catch:{ JSONException -> 0x00ac }
        r2.put(r8, r3);	 Catch:{ JSONException -> 0x00ac }
        r8 = "type";	 Catch:{ JSONException -> 0x00ac }
        r5 = "QUEUE_LOAD";	 Catch:{ JSONException -> 0x00ac }
        r2.put(r8, r5);	 Catch:{ JSONException -> 0x00ac }
        r8 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x00ac }
        r8.<init>();	 Catch:{ JSONException -> 0x00ac }
        r5 = 0;	 Catch:{ JSONException -> 0x00ac }
    L_0x0050:
        r6 = r9.length;	 Catch:{ JSONException -> 0x00ac }
        if (r5 >= r6) goto L_0x005f;	 Catch:{ JSONException -> 0x00ac }
    L_0x0053:
        r6 = r9[r5];	 Catch:{ JSONException -> 0x00ac }
        r6 = r6.toJson();	 Catch:{ JSONException -> 0x00ac }
        r8.put(r5, r6);	 Catch:{ JSONException -> 0x00ac }
        r5 = r5 + 1;	 Catch:{ JSONException -> 0x00ac }
        goto L_0x0050;	 Catch:{ JSONException -> 0x00ac }
    L_0x005f:
        r9 = "items";	 Catch:{ JSONException -> 0x00ac }
        r2.put(r9, r8);	 Catch:{ JSONException -> 0x00ac }
        r8 = java.lang.Integer.valueOf(r11);	 Catch:{ JSONException -> 0x00ac }
        r8 = com.google.android.gms.internal.cast.zzdq.zza(r8);	 Catch:{ JSONException -> 0x00ac }
        if (r8 == 0) goto L_0x0093;	 Catch:{ JSONException -> 0x00ac }
    L_0x006e:
        r9 = "repeatMode";	 Catch:{ JSONException -> 0x00ac }
        r2.put(r9, r8);	 Catch:{ JSONException -> 0x00ac }
        r8 = "startIndex";	 Catch:{ JSONException -> 0x00ac }
        r2.put(r8, r10);	 Catch:{ JSONException -> 0x00ac }
        r8 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1));	 Catch:{ JSONException -> 0x00ac }
        if (r8 == 0) goto L_0x008b;	 Catch:{ JSONException -> 0x00ac }
    L_0x007c:
        r8 = "currentTime";	 Catch:{ JSONException -> 0x00ac }
        r9 = (double) r12;
        r11 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        java.lang.Double.isNaN(r9);
        r9 = r9 / r11;
        r2.put(r8, r9);	 Catch:{ JSONException -> 0x00ac }
    L_0x008b:
        if (r14 == 0) goto L_0x00ac;	 Catch:{ JSONException -> 0x00ac }
    L_0x008d:
        r8 = "customData";	 Catch:{ JSONException -> 0x00ac }
        r2.put(r8, r14);	 Catch:{ JSONException -> 0x00ac }
        goto L_0x00ac;	 Catch:{ JSONException -> 0x00ac }
    L_0x0093:
        r8 = new java.lang.IllegalArgumentException;	 Catch:{ JSONException -> 0x00ac }
        r9 = 32;	 Catch:{ JSONException -> 0x00ac }
        r10 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x00ac }
        r10.<init>(r9);	 Catch:{ JSONException -> 0x00ac }
        r9 = "Invalid repeat mode: ";	 Catch:{ JSONException -> 0x00ac }
        r10.append(r9);	 Catch:{ JSONException -> 0x00ac }
        r10.append(r11);	 Catch:{ JSONException -> 0x00ac }
        r9 = r10.toString();	 Catch:{ JSONException -> 0x00ac }
        r8.<init>(r9);	 Catch:{ JSONException -> 0x00ac }
        throw r8;	 Catch:{ JSONException -> 0x00ac }
    L_0x00ac:
        r8 = r2.toString();
        r9 = 0;
        r7.zza(r8, r3, r9);
        return r3;
    L_0x00b5:
        r8 = new java.lang.IllegalArgumentException;
        r9 = 31;
        r11 = new java.lang.StringBuilder;
        r11.<init>(r9);
        r9 = "Invalid startIndex: ";
        r11.append(r9);
        r11.append(r10);
        r9 = r11.toString();
        r8.<init>(r9);
        throw r8;
    L_0x00ce:
        r8 = new java.lang.IllegalArgumentException;
        r9 = "items must not be null or empty.";
        r8.<init>(r9);
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, com.google.android.gms.cast.MediaQueueItem[], int, int, long, org.json.JSONObject):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r17, com.google.android.gms.cast.MediaQueueItem[] r18, int r19, int r20, int r21, long r22, org.json.JSONObject r24) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl, java.lang.IllegalArgumentException {
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
        r16 = this;
        r0 = r16;
        r1 = r18;
        r2 = r19;
        r3 = r21;
        r4 = r22;
        r6 = r24;
        if (r1 == 0) goto L_0x00d1;
    L_0x000e:
        r7 = r1.length;
        if (r7 == 0) goto L_0x00d1;
    L_0x0011:
        r7 = 0;
        r8 = -1;
        if (r3 == r8) goto L_0x003a;
    L_0x0015:
        if (r3 < 0) goto L_0x001b;
    L_0x0017:
        r9 = r1.length;
        if (r3 >= r9) goto L_0x001b;
    L_0x001a:
        goto L_0x003a;
    L_0x001b:
        r2 = new java.lang.IllegalArgumentException;
        r4 = java.util.Locale.ROOT;
        r5 = 2;
        r5 = new java.lang.Object[r5];
        r3 = java.lang.Integer.valueOf(r21);
        r5[r7] = r3;
        r1 = r1.length;
        r1 = java.lang.Integer.valueOf(r1);
        r3 = 1;
        r5[r3] = r1;
        r1 = "currentItemIndexInItemsToInsert %d out of range [0, %d).";
        r1 = java.lang.String.format(r4, r1, r5);
        r2.<init>(r1);
        throw r2;
    L_0x003a:
        r9 = -1;
        r11 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1));
        if (r11 == 0) goto L_0x0060;
    L_0x0040:
        r11 = 0;
        r13 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1));
        if (r13 < 0) goto L_0x0047;
    L_0x0046:
        goto L_0x0060;
    L_0x0047:
        r1 = new java.lang.IllegalArgumentException;
        r2 = 54;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "playPosition can not be negative: ";
        r3.append(r2);
        r3.append(r4);
        r2 = r3.toString();
        r1.<init>(r2);
        throw r1;
    L_0x0060:
        r11 = new org.json.JSONObject;
        r11.<init>();
        r12 = r16.zzdf();
        r14 = r0.zzyo;
        r15 = r17;
        r14.zza(r12, r15);
        r14 = "requestId";	 Catch:{ JSONException -> 0x00c8 }
        r11.put(r14, r12);	 Catch:{ JSONException -> 0x00c8 }
        r14 = "type";	 Catch:{ JSONException -> 0x00c8 }
        r15 = "QUEUE_INSERT";	 Catch:{ JSONException -> 0x00c8 }
        r11.put(r14, r15);	 Catch:{ JSONException -> 0x00c8 }
        r14 = "mediaSessionId";	 Catch:{ JSONException -> 0x00c8 }
        r9 = r16.zzj();	 Catch:{ JSONException -> 0x00c8 }
        r11.put(r14, r9);	 Catch:{ JSONException -> 0x00c8 }
        r9 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x00c8 }
        r9.<init>();	 Catch:{ JSONException -> 0x00c8 }
    L_0x008a:
        r10 = r1.length;	 Catch:{ JSONException -> 0x00c8 }
        if (r7 >= r10) goto L_0x0099;	 Catch:{ JSONException -> 0x00c8 }
    L_0x008d:
        r10 = r1[r7];	 Catch:{ JSONException -> 0x00c8 }
        r10 = r10.toJson();	 Catch:{ JSONException -> 0x00c8 }
        r9.put(r7, r10);	 Catch:{ JSONException -> 0x00c8 }
        r7 = r7 + 1;	 Catch:{ JSONException -> 0x00c8 }
        goto L_0x008a;	 Catch:{ JSONException -> 0x00c8 }
    L_0x0099:
        r1 = "items";	 Catch:{ JSONException -> 0x00c8 }
        r11.put(r1, r9);	 Catch:{ JSONException -> 0x00c8 }
        if (r2 == 0) goto L_0x00a5;	 Catch:{ JSONException -> 0x00c8 }
    L_0x00a0:
        r1 = "insertBefore";	 Catch:{ JSONException -> 0x00c8 }
        r11.put(r1, r2);	 Catch:{ JSONException -> 0x00c8 }
    L_0x00a5:
        if (r3 == r8) goto L_0x00ac;	 Catch:{ JSONException -> 0x00c8 }
    L_0x00a7:
        r1 = "currentItemIndex";	 Catch:{ JSONException -> 0x00c8 }
        r11.put(r1, r3);	 Catch:{ JSONException -> 0x00c8 }
    L_0x00ac:
        r1 = -1;	 Catch:{ JSONException -> 0x00c8 }
        r3 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1));	 Catch:{ JSONException -> 0x00c8 }
        if (r3 == 0) goto L_0x00c1;	 Catch:{ JSONException -> 0x00c8 }
    L_0x00b2:
        r1 = "currentTime";	 Catch:{ JSONException -> 0x00c8 }
        r2 = (double) r4;
        r4 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        java.lang.Double.isNaN(r2);
        r2 = r2 / r4;
        r11.put(r1, r2);	 Catch:{ JSONException -> 0x00c8 }
    L_0x00c1:
        if (r6 == 0) goto L_0x00c8;	 Catch:{ JSONException -> 0x00c8 }
    L_0x00c3:
        r1 = "customData";	 Catch:{ JSONException -> 0x00c8 }
        r11.put(r1, r6);	 Catch:{ JSONException -> 0x00c8 }
    L_0x00c8:
        r1 = r11.toString();
        r2 = 0;
        r0.zza(r1, r12, r2);
        return r12;
    L_0x00d1:
        r1 = new java.lang.IllegalArgumentException;
        r2 = "itemsToInsert must not be null or empty.";
        r1.<init>(r2);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, com.google.android.gms.cast.MediaQueueItem[], int, int, int, long, org.json.JSONObject):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r8, int r9, long r10, com.google.android.gms.cast.MediaQueueItem[] r12, int r13, java.lang.Integer r14, org.json.JSONObject r15) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl {
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
        r7 = this;
        r0 = -1;
        r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1));
        if (r2 == 0) goto L_0x0026;
    L_0x0006:
        r2 = 0;
        r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1));
        if (r4 < 0) goto L_0x000d;
    L_0x000c:
        goto L_0x0026;
    L_0x000d:
        r8 = new java.lang.IllegalArgumentException;
        r9 = 53;
        r12 = new java.lang.StringBuilder;
        r12.<init>(r9);
        r9 = "playPosition cannot be negative: ";
        r12.append(r9);
        r12.append(r10);
        r9 = r12.toString();
        r8.<init>(r9);
        throw r8;
    L_0x0026:
        r2 = new org.json.JSONObject;
        r2.<init>();
        r3 = r7.zzdf();
        r5 = r7.zzyp;
        r5.zza(r3, r8);
        r8 = "requestId";	 Catch:{ JSONException -> 0x009b }
        r2.put(r8, r3);	 Catch:{ JSONException -> 0x009b }
        r8 = "type";	 Catch:{ JSONException -> 0x009b }
        r5 = "QUEUE_UPDATE";	 Catch:{ JSONException -> 0x009b }
        r2.put(r8, r5);	 Catch:{ JSONException -> 0x009b }
        r8 = "mediaSessionId";	 Catch:{ JSONException -> 0x009b }
        r5 = r7.zzj();	 Catch:{ JSONException -> 0x009b }
        r2.put(r8, r5);	 Catch:{ JSONException -> 0x009b }
        if (r9 == 0) goto L_0x0050;	 Catch:{ JSONException -> 0x009b }
    L_0x004b:
        r8 = "currentItemId";	 Catch:{ JSONException -> 0x009b }
        r2.put(r8, r9);	 Catch:{ JSONException -> 0x009b }
    L_0x0050:
        if (r13 == 0) goto L_0x0057;	 Catch:{ JSONException -> 0x009b }
    L_0x0052:
        r8 = "jump";	 Catch:{ JSONException -> 0x009b }
        r2.put(r8, r13);	 Catch:{ JSONException -> 0x009b }
    L_0x0057:
        if (r12 == 0) goto L_0x0076;	 Catch:{ JSONException -> 0x009b }
    L_0x0059:
        r8 = r12.length;	 Catch:{ JSONException -> 0x009b }
        if (r8 <= 0) goto L_0x0076;	 Catch:{ JSONException -> 0x009b }
    L_0x005c:
        r8 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x009b }
        r8.<init>();	 Catch:{ JSONException -> 0x009b }
        r9 = 0;	 Catch:{ JSONException -> 0x009b }
    L_0x0062:
        r13 = r12.length;	 Catch:{ JSONException -> 0x009b }
        if (r9 >= r13) goto L_0x0071;	 Catch:{ JSONException -> 0x009b }
    L_0x0065:
        r13 = r12[r9];	 Catch:{ JSONException -> 0x009b }
        r13 = r13.toJson();	 Catch:{ JSONException -> 0x009b }
        r8.put(r9, r13);	 Catch:{ JSONException -> 0x009b }
        r9 = r9 + 1;	 Catch:{ JSONException -> 0x009b }
        goto L_0x0062;	 Catch:{ JSONException -> 0x009b }
    L_0x0071:
        r9 = "items";	 Catch:{ JSONException -> 0x009b }
        r2.put(r9, r8);	 Catch:{ JSONException -> 0x009b }
    L_0x0076:
        r8 = com.google.android.gms.internal.cast.zzdq.zza(r14);	 Catch:{ JSONException -> 0x009b }
        if (r8 == 0) goto L_0x0081;	 Catch:{ JSONException -> 0x009b }
    L_0x007c:
        r9 = "repeatMode";	 Catch:{ JSONException -> 0x009b }
        r2.put(r9, r8);	 Catch:{ JSONException -> 0x009b }
    L_0x0081:
        r8 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1));	 Catch:{ JSONException -> 0x009b }
        if (r8 == 0) goto L_0x0094;	 Catch:{ JSONException -> 0x009b }
    L_0x0085:
        r8 = "currentTime";	 Catch:{ JSONException -> 0x009b }
        r9 = (double) r10;
        r11 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        java.lang.Double.isNaN(r9);
        r9 = r9 / r11;
        r2.put(r8, r9);	 Catch:{ JSONException -> 0x009b }
    L_0x0094:
        if (r15 == 0) goto L_0x009b;	 Catch:{ JSONException -> 0x009b }
    L_0x0096:
        r8 = "customData";	 Catch:{ JSONException -> 0x009b }
        r2.put(r8, r15);	 Catch:{ JSONException -> 0x009b }
    L_0x009b:
        r8 = r2.toString();
        r9 = 0;
        r7.zza(r8, r3, r9);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, int, long, com.google.android.gms.cast.MediaQueueItem[], int, java.lang.Integer, org.json.JSONObject):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r6, int[] r7, org.json.JSONObject r8) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl, java.lang.IllegalArgumentException {
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
        r5 = this;
        if (r7 == 0) goto L_0x004e;
    L_0x0002:
        r0 = r7.length;
        if (r0 == 0) goto L_0x004e;
    L_0x0005:
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzyq;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x0045 }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x0045 }
        r6 = "type";	 Catch:{ JSONException -> 0x0045 }
        r3 = "QUEUE_REMOVE";	 Catch:{ JSONException -> 0x0045 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0045 }
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x0045 }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x0045 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0045 }
        r6 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0045 }
        r6.<init>();	 Catch:{ JSONException -> 0x0045 }
        r3 = 0;	 Catch:{ JSONException -> 0x0045 }
    L_0x002e:
        r4 = r7.length;	 Catch:{ JSONException -> 0x0045 }
        if (r3 >= r4) goto L_0x0039;	 Catch:{ JSONException -> 0x0045 }
    L_0x0031:
        r4 = r7[r3];	 Catch:{ JSONException -> 0x0045 }
        r6.put(r3, r4);	 Catch:{ JSONException -> 0x0045 }
        r3 = r3 + 1;	 Catch:{ JSONException -> 0x0045 }
        goto L_0x002e;	 Catch:{ JSONException -> 0x0045 }
    L_0x0039:
        r7 = "itemIds";	 Catch:{ JSONException -> 0x0045 }
        r0.put(r7, r6);	 Catch:{ JSONException -> 0x0045 }
        if (r8 == 0) goto L_0x0045;	 Catch:{ JSONException -> 0x0045 }
    L_0x0040:
        r6 = "customData";	 Catch:{ JSONException -> 0x0045 }
        r0.put(r6, r8);	 Catch:{ JSONException -> 0x0045 }
    L_0x0045:
        r6 = r0.toString();
        r7 = 0;
        r5.zza(r6, r1, r7);
        return r1;
    L_0x004e:
        r6 = new java.lang.IllegalArgumentException;
        r7 = "itemIdsToRemove must not be null or empty.";
        r6.<init>(r7);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, int[], org.json.JSONObject):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r6, int[] r7, int r8, org.json.JSONObject r9) throws java.lang.IllegalStateException, com.google.android.gms.internal.cast.zzdl, java.lang.IllegalArgumentException {
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
        r5 = this;
        if (r7 == 0) goto L_0x0055;
    L_0x0002:
        r0 = r7.length;
        if (r0 == 0) goto L_0x0055;
    L_0x0005:
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzyr;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x004c }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x004c }
        r6 = "type";	 Catch:{ JSONException -> 0x004c }
        r3 = "QUEUE_REORDER";	 Catch:{ JSONException -> 0x004c }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x004c }
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x004c }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x004c }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x004c }
        r6 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x004c }
        r6.<init>();	 Catch:{ JSONException -> 0x004c }
        r3 = 0;	 Catch:{ JSONException -> 0x004c }
    L_0x002e:
        r4 = r7.length;	 Catch:{ JSONException -> 0x004c }
        if (r3 >= r4) goto L_0x0039;	 Catch:{ JSONException -> 0x004c }
    L_0x0031:
        r4 = r7[r3];	 Catch:{ JSONException -> 0x004c }
        r6.put(r3, r4);	 Catch:{ JSONException -> 0x004c }
        r3 = r3 + 1;	 Catch:{ JSONException -> 0x004c }
        goto L_0x002e;	 Catch:{ JSONException -> 0x004c }
    L_0x0039:
        r7 = "itemIds";	 Catch:{ JSONException -> 0x004c }
        r0.put(r7, r6);	 Catch:{ JSONException -> 0x004c }
        if (r8 == 0) goto L_0x0045;	 Catch:{ JSONException -> 0x004c }
    L_0x0040:
        r6 = "insertBefore";	 Catch:{ JSONException -> 0x004c }
        r0.put(r6, r8);	 Catch:{ JSONException -> 0x004c }
    L_0x0045:
        if (r9 == 0) goto L_0x004c;	 Catch:{ JSONException -> 0x004c }
    L_0x0047:
        r6 = "customData";	 Catch:{ JSONException -> 0x004c }
        r0.put(r6, r9);	 Catch:{ JSONException -> 0x004c }
    L_0x004c:
        r6 = r0.toString();
        r7 = 0;
        r5.zza(r6, r1, r7);
        return r1;
    L_0x0055:
        r6 = new java.lang.IllegalArgumentException;
        r7 = "itemIdsToReorder must not be null or empty.";
        r6.<init>(r7);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, int[], int, org.json.JSONObject):long");
    }

    public final long zzc(com.google.android.gms.internal.cast.zzdn r6) throws com.google.android.gms.internal.cast.zzdl, java.lang.IllegalStateException {
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
        r5 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzys;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x0023 }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x0023 }
        r6 = "type";	 Catch:{ JSONException -> 0x0023 }
        r3 = "QUEUE_GET_ITEM_IDS";	 Catch:{ JSONException -> 0x0023 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0023 }
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x0023 }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x0023 }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x0023 }
    L_0x0023:
        r6 = r0.toString();
        r0 = 0;
        r5.zza(r6, r1, r0);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zzc(com.google.android.gms.internal.cast.zzdn):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r6, int r7, int r8, int r9) throws com.google.android.gms.internal.cast.zzdl, java.lang.IllegalArgumentException {
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
        r5 = this;
        if (r8 <= 0) goto L_0x0004;
    L_0x0002:
        if (r9 == 0) goto L_0x0008;
    L_0x0004:
        if (r8 != 0) goto L_0x0047;
    L_0x0006:
        if (r9 <= 0) goto L_0x0047;
    L_0x0008:
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r5.zzdf();
        r3 = r5.zzyu;
        r3.zza(r1, r6);
        r6 = "requestId";	 Catch:{ JSONException -> 0x003e }
        r0.put(r6, r1);	 Catch:{ JSONException -> 0x003e }
        r6 = "type";	 Catch:{ JSONException -> 0x003e }
        r3 = "QUEUE_GET_ITEM_RANGE";	 Catch:{ JSONException -> 0x003e }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x003e }
        r6 = "mediaSessionId";	 Catch:{ JSONException -> 0x003e }
        r3 = r5.zzj();	 Catch:{ JSONException -> 0x003e }
        r0.put(r6, r3);	 Catch:{ JSONException -> 0x003e }
        r6 = "itemId";	 Catch:{ JSONException -> 0x003e }
        r0.put(r6, r7);	 Catch:{ JSONException -> 0x003e }
        if (r8 <= 0) goto L_0x0037;	 Catch:{ JSONException -> 0x003e }
    L_0x0032:
        r6 = "nextCount";	 Catch:{ JSONException -> 0x003e }
        r0.put(r6, r8);	 Catch:{ JSONException -> 0x003e }
    L_0x0037:
        if (r9 <= 0) goto L_0x003e;	 Catch:{ JSONException -> 0x003e }
    L_0x0039:
        r6 = "prevCount";	 Catch:{ JSONException -> 0x003e }
        r0.put(r6, r9);	 Catch:{ JSONException -> 0x003e }
    L_0x003e:
        r6 = r0.toString();
        r7 = 0;
        r5.zza(r6, r1, r7);
        return r1;
    L_0x0047:
        r6 = new java.lang.IllegalArgumentException;
        r7 = "Exactly one of nextCount and prevCount must be positive and the other must be zero";
        r6.<init>(r7);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, int, int, int):long");
    }

    public final long zza(com.google.android.gms.internal.cast.zzdn r7, int[] r8) throws com.google.android.gms.internal.cast.zzdl, java.lang.IllegalArgumentException {
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
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = r6.zzdf();
        r3 = r6.zzyt;
        r3.zza(r1, r7);
        r7 = "requestId";	 Catch:{ JSONException -> 0x0039 }
        r0.put(r7, r1);	 Catch:{ JSONException -> 0x0039 }
        r7 = "type";	 Catch:{ JSONException -> 0x0039 }
        r3 = "QUEUE_GET_ITEMS";	 Catch:{ JSONException -> 0x0039 }
        r0.put(r7, r3);	 Catch:{ JSONException -> 0x0039 }
        r7 = "mediaSessionId";	 Catch:{ JSONException -> 0x0039 }
        r3 = r6.zzj();	 Catch:{ JSONException -> 0x0039 }
        r0.put(r7, r3);	 Catch:{ JSONException -> 0x0039 }
        r7 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0039 }
        r7.<init>();	 Catch:{ JSONException -> 0x0039 }
        r3 = r8.length;	 Catch:{ JSONException -> 0x0039 }
        r4 = 0;	 Catch:{ JSONException -> 0x0039 }
    L_0x002a:
        if (r4 >= r3) goto L_0x0034;	 Catch:{ JSONException -> 0x0039 }
    L_0x002c:
        r5 = r8[r4];	 Catch:{ JSONException -> 0x0039 }
        r7.put(r5);	 Catch:{ JSONException -> 0x0039 }
        r4 = r4 + 1;	 Catch:{ JSONException -> 0x0039 }
        goto L_0x002a;	 Catch:{ JSONException -> 0x0039 }
    L_0x0034:
        r8 = "itemIds";	 Catch:{ JSONException -> 0x0039 }
        r0.put(r8, r7);	 Catch:{ JSONException -> 0x0039 }
    L_0x0039:
        r7 = r0.toString();
        r8 = 0;
        r6.zza(r7, r1, r8);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(com.google.android.gms.internal.cast.zzdn, int[]):long");
    }

    public final long zzb(String str, List<zzbt> list) throws IllegalStateException {
        long zzdf = zzdf();
        zza(zza(str, (List) list, zzdf), zzdf, null);
        return zzdf;
    }

    private static java.lang.String zza(java.lang.String r2, java.util.List<com.google.android.gms.cast.zzbt> r3, long r4) {
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
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "requestId";	 Catch:{ JSONException -> 0x0041 }
        r0.put(r1, r4);	 Catch:{ JSONException -> 0x0041 }
        r4 = "type";	 Catch:{ JSONException -> 0x0041 }
        r5 = "PRECACHE";	 Catch:{ JSONException -> 0x0041 }
        r0.put(r4, r5);	 Catch:{ JSONException -> 0x0041 }
        if (r2 == 0) goto L_0x0018;	 Catch:{ JSONException -> 0x0041 }
    L_0x0013:
        r4 = "precacheData";	 Catch:{ JSONException -> 0x0041 }
        r0.put(r4, r2);	 Catch:{ JSONException -> 0x0041 }
    L_0x0018:
        if (r3 == 0) goto L_0x0041;	 Catch:{ JSONException -> 0x0041 }
    L_0x001a:
        r2 = r3.isEmpty();	 Catch:{ JSONException -> 0x0041 }
        if (r2 != 0) goto L_0x0041;	 Catch:{ JSONException -> 0x0041 }
    L_0x0020:
        r2 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0041 }
        r2.<init>();	 Catch:{ JSONException -> 0x0041 }
        r4 = 0;	 Catch:{ JSONException -> 0x0041 }
    L_0x0026:
        r5 = r3.size();	 Catch:{ JSONException -> 0x0041 }
        if (r4 >= r5) goto L_0x003c;	 Catch:{ JSONException -> 0x0041 }
    L_0x002c:
        r5 = r3.get(r4);	 Catch:{ JSONException -> 0x0041 }
        r5 = (com.google.android.gms.cast.zzbt) r5;	 Catch:{ JSONException -> 0x0041 }
        r5 = r5.toJson();	 Catch:{ JSONException -> 0x0041 }
        r2.put(r4, r5);	 Catch:{ JSONException -> 0x0041 }
        r4 = r4 + 1;	 Catch:{ JSONException -> 0x0041 }
        goto L_0x0026;	 Catch:{ JSONException -> 0x0041 }
    L_0x003c:
        r3 = "requestItems";	 Catch:{ JSONException -> 0x0041 }
        r0.put(r3, r2);	 Catch:{ JSONException -> 0x0041 }
    L_0x0041:
        r2 = r0.toString();
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zza(java.lang.String, java.util.List, long):java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzn(java.lang.String r13) {
        /*
        r12 = this;
        r0 = r12.zzwg;
        r1 = "message received: %s";
        r2 = 1;
        r3 = new java.lang.Object[r2];
        r4 = 0;
        r3[r4] = r13;
        r0.d(r1, r3);
        r0 = 2;
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x028f }
        r1.<init>(r13);	 Catch:{ JSONException -> 0x028f }
        r3 = "type";
        r3 = r1.getString(r3);	 Catch:{ JSONException -> 0x028f }
        r5 = "requestId";
        r6 = -1;
        r5 = r1.optLong(r5, r6);	 Catch:{ JSONException -> 0x028f }
        r7 = r3.hashCode();	 Catch:{ JSONException -> 0x028f }
        r8 = 3;
        r9 = -1;
        r10 = 4;
        switch(r7) {
            case -1830647528: goto L_0x0072;
            case -1790231854: goto L_0x0068;
            case -1125000185: goto L_0x005e;
            case -262628938: goto L_0x0054;
            case 154411710: goto L_0x004a;
            case 431600379: goto L_0x0040;
            case 823510221: goto L_0x0036;
            case 2107149050: goto L_0x002c;
            default: goto L_0x002b;
        };	 Catch:{ JSONException -> 0x028f }
    L_0x002b:
        goto L_0x007c;
    L_0x002c:
        r7 = "QUEUE_ITEM_IDS";
        r3 = r3.equals(r7);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x007c;
    L_0x0034:
        r3 = 5;
        goto L_0x007d;
    L_0x0036:
        r7 = "MEDIA_STATUS";
        r3 = r3.equals(r7);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x007c;
    L_0x003e:
        r3 = 0;
        goto L_0x007d;
    L_0x0040:
        r7 = "INVALID_PLAYER_STATE";
        r3 = r3.equals(r7);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x007c;
    L_0x0048:
        r3 = 1;
        goto L_0x007d;
    L_0x004a:
        r7 = "QUEUE_CHANGE";
        r3 = r3.equals(r7);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x007c;
    L_0x0052:
        r3 = 6;
        goto L_0x007d;
    L_0x0054:
        r7 = "LOAD_FAILED";
        r3 = r3.equals(r7);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x007c;
    L_0x005c:
        r3 = 2;
        goto L_0x007d;
    L_0x005e:
        r7 = "INVALID_REQUEST";
        r3 = r3.equals(r7);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x007c;
    L_0x0066:
        r3 = 4;
        goto L_0x007d;
    L_0x0068:
        r7 = "QUEUE_ITEMS";
        r3 = r3.equals(r7);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x007c;
    L_0x0070:
        r3 = 7;
        goto L_0x007d;
    L_0x0072:
        r7 = "LOAD_CANCELLED";
        r3 = r3.equals(r7);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x007c;
    L_0x007a:
        r3 = 3;
        goto L_0x007d;
    L_0x007c:
        r3 = -1;
    L_0x007d:
        r7 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;
        r11 = 0;
        switch(r3) {
            case 0: goto L_0x01b7;
            case 1: goto L_0x018f;
            case 2: goto L_0x0183;
            case 3: goto L_0x0175;
            case 4: goto L_0x014d;
            case 5: goto L_0x0132;
            case 6: goto L_0x00ba;
            case 7: goto L_0x0085;
            default: goto L_0x0083;
        };	 Catch:{ JSONException -> 0x028f }
    L_0x0083:
        goto L_0x028e;
    L_0x0085:
        r3 = r12.zzyt;	 Catch:{ JSONException -> 0x028f }
        r3.zzc(r5, r4, r11);	 Catch:{ JSONException -> 0x028f }
        r3 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x028e;
    L_0x008e:
        r3 = "items";
        r1 = r1.getJSONArray(r3);	 Catch:{ JSONException -> 0x028f }
        r3 = r1.length();	 Catch:{ JSONException -> 0x028f }
        r3 = new com.google.android.gms.cast.MediaQueueItem[r3];	 Catch:{ JSONException -> 0x028f }
        r5 = 0;
    L_0x009b:
        r6 = r1.length();	 Catch:{ JSONException -> 0x028f }
        if (r5 >= r6) goto L_0x00b3;
    L_0x00a1:
        r6 = new com.google.android.gms.cast.MediaQueueItem$Builder;	 Catch:{ JSONException -> 0x028f }
        r7 = r1.getJSONObject(r5);	 Catch:{ JSONException -> 0x028f }
        r6.<init>(r7);	 Catch:{ JSONException -> 0x028f }
        r6 = r6.build();	 Catch:{ JSONException -> 0x028f }
        r3[r5] = r6;	 Catch:{ JSONException -> 0x028f }
        r5 = r5 + 1;
        goto L_0x009b;
    L_0x00b3:
        r1 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        r1.zzb(r3);	 Catch:{ JSONException -> 0x028f }
        goto L_0x028e;
    L_0x00ba:
        r3 = r12.zzyu;	 Catch:{ JSONException -> 0x028f }
        r3.zzc(r5, r4, r11);	 Catch:{ JSONException -> 0x028f }
        r3 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x0131;
    L_0x00c3:
        r3 = "changeType";
        r3 = r1.getString(r3);	 Catch:{ JSONException -> 0x028f }
        r5 = "itemIds";
        r5 = r1.getJSONArray(r5);	 Catch:{ JSONException -> 0x028f }
        r5 = zzb(r5);	 Catch:{ JSONException -> 0x028f }
        r6 = "insertBefore";
        r1 = r1.optInt(r6, r4);	 Catch:{ JSONException -> 0x028f }
        if (r5 == 0) goto L_0x0131;
    L_0x00db:
        r6 = r3.hashCode();	 Catch:{ JSONException -> 0x028f }
        switch(r6) {
            case -2130463047: goto L_0x010a;
            case -1881281404: goto L_0x0100;
            case -1785516855: goto L_0x00f7;
            case 1122976047: goto L_0x00ed;
            case 1395699694: goto L_0x00e3;
            default: goto L_0x00e2;
        };	 Catch:{ JSONException -> 0x028f }
    L_0x00e2:
        goto L_0x0114;
    L_0x00e3:
        r6 = "NO_CHANGE";
        r3 = r3.equals(r6);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x0114;
    L_0x00eb:
        r8 = 4;
        goto L_0x0115;
    L_0x00ed:
        r6 = "ITEMS_CHANGE";
        r3 = r3.equals(r6);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x0114;
    L_0x00f5:
        r8 = 1;
        goto L_0x0115;
    L_0x00f7:
        r6 = "UPDATE";
        r3 = r3.equals(r6);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x0114;
    L_0x00ff:
        goto L_0x0115;
    L_0x0100:
        r6 = "REMOVE";
        r3 = r3.equals(r6);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x0114;
    L_0x0108:
        r8 = 2;
        goto L_0x0115;
    L_0x010a:
        r6 = "INSERT";
        r3 = r3.equals(r6);	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x0114;
    L_0x0112:
        r8 = 0;
        goto L_0x0115;
    L_0x0114:
        r8 = -1;
    L_0x0115:
        switch(r8) {
            case 0: goto L_0x012b;
            case 1: goto L_0x0125;
            case 2: goto L_0x011f;
            case 3: goto L_0x0119;
            default: goto L_0x0118;
        };	 Catch:{ JSONException -> 0x028f }
    L_0x0118:
        goto L_0x0131;
    L_0x0119:
        r1 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        r1.zza(r5);	 Catch:{ JSONException -> 0x028f }
        goto L_0x0131;
    L_0x011f:
        r1 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        r1.zzc(r5);	 Catch:{ JSONException -> 0x028f }
        return;
    L_0x0125:
        r1 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        r1.zzb(r5);	 Catch:{ JSONException -> 0x028f }
        return;
    L_0x012b:
        r3 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        r3.zza(r5, r1);	 Catch:{ JSONException -> 0x028f }
        return;
    L_0x0131:
        return;
    L_0x0132:
        r3 = r12.zzys;	 Catch:{ JSONException -> 0x028f }
        r3.zzc(r5, r4, r11);	 Catch:{ JSONException -> 0x028f }
        r3 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x014c;
    L_0x013b:
        r3 = "itemIds";
        r1 = r1.getJSONArray(r3);	 Catch:{ JSONException -> 0x028f }
        r1 = zzb(r1);	 Catch:{ JSONException -> 0x028f }
        if (r1 == 0) goto L_0x014c;
    L_0x0147:
        r3 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        r3.zza(r1);	 Catch:{ JSONException -> 0x028f }
    L_0x014c:
        return;
    L_0x014d:
        r3 = r12.zzwg;	 Catch:{ JSONException -> 0x028f }
        r8 = "received unexpected error: Invalid Request.";
        r9 = new java.lang.Object[r4];	 Catch:{ JSONException -> 0x028f }
        r3.w(r8, r9);	 Catch:{ JSONException -> 0x028f }
        r3 = "customData";
        r1 = r1.optJSONObject(r3);	 Catch:{ JSONException -> 0x028f }
        r3 = r12.zzde();	 Catch:{ JSONException -> 0x028f }
        r3 = r3.iterator();	 Catch:{ JSONException -> 0x028f }
    L_0x0164:
        r8 = r3.hasNext();	 Catch:{ JSONException -> 0x028f }
        if (r8 == 0) goto L_0x0174;
    L_0x016a:
        r8 = r3.next();	 Catch:{ JSONException -> 0x028f }
        r8 = (com.google.android.gms.internal.cast.zzdo) r8;	 Catch:{ JSONException -> 0x028f }
        r8.zzc(r5, r7, r1);	 Catch:{ JSONException -> 0x028f }
        goto L_0x0164;
    L_0x0174:
        return;
    L_0x0175:
        r3 = "customData";
        r1 = r1.optJSONObject(r3);	 Catch:{ JSONException -> 0x028f }
        r3 = r12.zzye;	 Catch:{ JSONException -> 0x028f }
        r7 = 2101; // 0x835 float:2.944E-42 double:1.038E-320;
        r3.zzc(r5, r7, r1);	 Catch:{ JSONException -> 0x028f }
        return;
    L_0x0183:
        r3 = "customData";
        r1 = r1.optJSONObject(r3);	 Catch:{ JSONException -> 0x028f }
        r3 = r12.zzye;	 Catch:{ JSONException -> 0x028f }
        r3.zzc(r5, r7, r1);	 Catch:{ JSONException -> 0x028f }
        return;
    L_0x018f:
        r3 = r12.zzwg;	 Catch:{ JSONException -> 0x028f }
        r8 = "received unexpected error: Invalid Player State.";
        r9 = new java.lang.Object[r4];	 Catch:{ JSONException -> 0x028f }
        r3.w(r8, r9);	 Catch:{ JSONException -> 0x028f }
        r3 = "customData";
        r1 = r1.optJSONObject(r3);	 Catch:{ JSONException -> 0x028f }
        r3 = r12.zzde();	 Catch:{ JSONException -> 0x028f }
        r3 = r3.iterator();	 Catch:{ JSONException -> 0x028f }
    L_0x01a6:
        r8 = r3.hasNext();	 Catch:{ JSONException -> 0x028f }
        if (r8 == 0) goto L_0x01b6;
    L_0x01ac:
        r8 = r3.next();	 Catch:{ JSONException -> 0x028f }
        r8 = (com.google.android.gms.internal.cast.zzdo) r8;	 Catch:{ JSONException -> 0x028f }
        r8.zzc(r5, r7, r1);	 Catch:{ JSONException -> 0x028f }
        goto L_0x01a6;
    L_0x01b6:
        return;
    L_0x01b7:
        r3 = "status";
        r1 = r1.getJSONArray(r3);	 Catch:{ JSONException -> 0x028f }
        r3 = r1.length();	 Catch:{ JSONException -> 0x028f }
        if (r3 <= 0) goto L_0x0267;
    L_0x01c3:
        r1 = r1.getJSONObject(r4);	 Catch:{ JSONException -> 0x028f }
        r3 = r12.zzye;	 Catch:{ JSONException -> 0x028f }
        r3 = r3.test(r5);	 Catch:{ JSONException -> 0x028f }
        r7 = r12.zzyj;	 Catch:{ JSONException -> 0x028f }
        r7 = r7.zzdq();	 Catch:{ JSONException -> 0x028f }
        if (r7 == 0) goto L_0x01dd;
    L_0x01d5:
        r7 = r12.zzyj;	 Catch:{ JSONException -> 0x028f }
        r7 = r7.test(r5);	 Catch:{ JSONException -> 0x028f }
        if (r7 == 0) goto L_0x01ed;
    L_0x01dd:
        r7 = r12.zzyk;	 Catch:{ JSONException -> 0x028f }
        r7 = r7.zzdq();	 Catch:{ JSONException -> 0x028f }
        if (r7 == 0) goto L_0x01ef;
    L_0x01e5:
        r7 = r12.zzyk;	 Catch:{ JSONException -> 0x028f }
        r7 = r7.test(r5);	 Catch:{ JSONException -> 0x028f }
        if (r7 != 0) goto L_0x01ef;
    L_0x01ed:
        r7 = 1;
        goto L_0x01f0;
    L_0x01ef:
        r7 = 0;
    L_0x01f0:
        if (r3 != 0) goto L_0x01fe;
    L_0x01f2:
        r3 = r12.zzyb;	 Catch:{ JSONException -> 0x028f }
        if (r3 != 0) goto L_0x01f7;
    L_0x01f6:
        goto L_0x01fe;
    L_0x01f7:
        r3 = r12.zzyb;	 Catch:{ JSONException -> 0x028f }
        r1 = r3.zza(r1, r7);	 Catch:{ JSONException -> 0x028f }
        goto L_0x020d;
    L_0x01fe:
        r3 = new com.google.android.gms.cast.MediaStatus;	 Catch:{ JSONException -> 0x028f }
        r3.<init>(r1);	 Catch:{ JSONException -> 0x028f }
        r12.zzyb = r3;	 Catch:{ JSONException -> 0x028f }
        r7 = android.os.SystemClock.elapsedRealtime();	 Catch:{ JSONException -> 0x028f }
        r12.zzya = r7;	 Catch:{ JSONException -> 0x028f }
        r1 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
    L_0x020d:
        r3 = r1 & 1;
        if (r3 == 0) goto L_0x021a;
    L_0x0211:
        r7 = android.os.SystemClock.elapsedRealtime();	 Catch:{ JSONException -> 0x028f }
        r12.zzya = r7;	 Catch:{ JSONException -> 0x028f }
        r12.onStatusUpdated();	 Catch:{ JSONException -> 0x028f }
    L_0x021a:
        r3 = r1 & 2;
        if (r3 == 0) goto L_0x0227;
    L_0x021e:
        r7 = android.os.SystemClock.elapsedRealtime();	 Catch:{ JSONException -> 0x028f }
        r12.zzya = r7;	 Catch:{ JSONException -> 0x028f }
        r12.onStatusUpdated();	 Catch:{ JSONException -> 0x028f }
    L_0x0227:
        r3 = r1 & 128;
        if (r3 == 0) goto L_0x0231;
    L_0x022b:
        r7 = android.os.SystemClock.elapsedRealtime();	 Catch:{ JSONException -> 0x028f }
        r12.zzya = r7;	 Catch:{ JSONException -> 0x028f }
    L_0x0231:
        r3 = r1 & 4;
        if (r3 == 0) goto L_0x0238;
    L_0x0235:
        r12.onMetadataUpdated();	 Catch:{ JSONException -> 0x028f }
    L_0x0238:
        r3 = r1 & 8;
        if (r3 == 0) goto L_0x023f;
    L_0x023c:
        r12.onQueueStatusUpdated();	 Catch:{ JSONException -> 0x028f }
    L_0x023f:
        r3 = r1 & 16;
        if (r3 == 0) goto L_0x0246;
    L_0x0243:
        r12.onPreloadStatusUpdated();	 Catch:{ JSONException -> 0x028f }
    L_0x0246:
        r3 = r1 & 32;
        if (r3 == 0) goto L_0x0259;
    L_0x024a:
        r7 = android.os.SystemClock.elapsedRealtime();	 Catch:{ JSONException -> 0x028f }
        r12.zzya = r7;	 Catch:{ JSONException -> 0x028f }
        r3 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x0259;
    L_0x0254:
        r3 = r12.zzyd;	 Catch:{ JSONException -> 0x028f }
        r3.onAdBreakStatusUpdated();	 Catch:{ JSONException -> 0x028f }
    L_0x0259:
        r1 = r1 & 64;
        if (r1 == 0) goto L_0x0275;
    L_0x025d:
        r7 = android.os.SystemClock.elapsedRealtime();	 Catch:{ JSONException -> 0x028f }
        r12.zzya = r7;	 Catch:{ JSONException -> 0x028f }
        r12.onStatusUpdated();	 Catch:{ JSONException -> 0x028f }
        goto L_0x0275;
    L_0x0267:
        r12.zzyb = r11;	 Catch:{ JSONException -> 0x028f }
        r12.onStatusUpdated();	 Catch:{ JSONException -> 0x028f }
        r12.onMetadataUpdated();	 Catch:{ JSONException -> 0x028f }
        r12.onQueueStatusUpdated();	 Catch:{ JSONException -> 0x028f }
        r12.onPreloadStatusUpdated();	 Catch:{ JSONException -> 0x028f }
    L_0x0275:
        r1 = r12.zzde();	 Catch:{ JSONException -> 0x028f }
        r1 = r1.iterator();	 Catch:{ JSONException -> 0x028f }
    L_0x027d:
        r3 = r1.hasNext();	 Catch:{ JSONException -> 0x028f }
        if (r3 == 0) goto L_0x028d;
    L_0x0283:
        r3 = r1.next();	 Catch:{ JSONException -> 0x028f }
        r3 = (com.google.android.gms.internal.cast.zzdo) r3;	 Catch:{ JSONException -> 0x028f }
        r3.zzc(r5, r4, r11);	 Catch:{ JSONException -> 0x028f }
        goto L_0x027d;
    L_0x028d:
        return;
    L_0x028e:
        return;
    L_0x028f:
        r1 = move-exception;
        r3 = r12.zzwg;
        r5 = "Message is malformed (%s); ignoring: %s";
        r0 = new java.lang.Object[r0];
        r1 = r1.getMessage();
        r0[r4] = r1;
        r0[r2] = r13;
        r3.w(r5, r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdi.zzn(java.lang.String):void");
    }

    private static int[] zzb(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return null;
        }
        int[] iArr = new int[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            iArr[i] = jSONArray.getInt(i);
        }
        return iArr;
    }

    private final long zzj() throws zzdl {
        if (this.zzyb != null) {
            return this.zzyb.zzj();
        }
        throw new zzdl();
    }

    private final void onStatusUpdated() {
        if (this.zzyd != null) {
            this.zzyd.onStatusUpdated();
        }
    }

    private final void onMetadataUpdated() {
        if (this.zzyd != null) {
            this.zzyd.onMetadataUpdated();
        }
    }

    private final void onQueueStatusUpdated() {
        if (this.zzyd != null) {
            this.zzyd.onQueueStatusUpdated();
        }
    }

    private final void onPreloadStatusUpdated() {
        if (this.zzyd != null) {
            this.zzyd.onPreloadStatusUpdated();
        }
    }

    private final void zzdp() {
        this.zzya = 0;
        this.zzyb = null;
        for (zzdo zzq : zzde()) {
            zzq.zzq(CastStatusCodes.CANCELED);
        }
    }

    public final void zzdd() {
        super.zzdd();
        zzdp();
    }

    public final void zza(long j, int i) {
        for (zzdo zzc : zzde()) {
            zzc.zzc(j, i, null);
        }
    }
}
