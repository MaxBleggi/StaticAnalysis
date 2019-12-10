package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.util.Log;
import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdError;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorCode;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorType;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
import com.google.ads.interactivemedia.v3.api.AdsLoader;
import com.google.ads.interactivemedia.v3.api.AdsLoader.AdsLoadedListener;
import com.google.ads.interactivemedia.v3.api.AdsManagerLoadedEvent;
import com.google.ads.interactivemedia.v3.api.AdsRequest;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;
import com.google.ads.interactivemedia.v3.api.StreamRequest;
import com.google.ads.interactivemedia.v3.impl.data.TestingConfiguration;
import com.google.ads.interactivemedia.v3.impl.data.l;
import com.xumo.xumo.BuildConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.UUID;

/* compiled from: IMASDK */
public class ii implements AdsLoader {
    com.google.ads.interactivemedia.v3.internal.jd.b a;
    private final jd b;
    private final Context c;
    private final ix d;
    private final List<AdsLoadedListener> e;
    private final Map<String, AdsRequest> f;
    private final Map<String, StreamRequest> g;
    private final jh h;
    private kf i;
    private final Object j;
    private ImaSdkSettings k;
    private TestingConfiguration l;

    /* compiled from: IMASDK */
    private class a extends AsyncTask<String, Void, String> {
        final /* synthetic */ ii a;
        private final AdsRequest b;
        private final String c;

        public a(ii iiVar, AdsRequest adsRequest, String str) {
            this.a = iiVar;
            this.b = adsRequest;
            this.c = str;
        }

        protected java.lang.String a(java.lang.String... r6) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r5 = this;
            r0 = 0;
            r6 = r6[r0];
            r0 = r5.a;
            r0 = r0.j;
            monitor-enter(r0);
            r1 = r5.a;	 Catch:{ all -> 0x0051 }
            r1 = r1.i;	 Catch:{ all -> 0x0051 }
            if (r1 != 0) goto L_0x0028;	 Catch:{ all -> 0x0051 }
        L_0x0012:
            r1 = r5.a;	 Catch:{ all -> 0x0051 }
            r2 = new com.google.ads.interactivemedia.v3.internal.kf;	 Catch:{ all -> 0x0051 }
            r3 = "a.3.9.4";	 Catch:{ all -> 0x0051 }
            r4 = r5.a;	 Catch:{ all -> 0x0051 }
            r4 = r4.c;	 Catch:{ all -> 0x0051 }
            r3 = com.google.ads.interactivemedia.v3.internal.ke.a(r3, r4);	 Catch:{ all -> 0x0051 }
            r2.<init>(r3);	 Catch:{ all -> 0x0051 }
            r1.i = r2;	 Catch:{ all -> 0x0051 }
        L_0x0028:
            if (r6 == 0) goto L_0x004f;	 Catch:{ all -> 0x0051 }
        L_0x002a:
            r1 = android.net.Uri.parse(r6);	 Catch:{ all -> 0x0051 }
            r2 = r5.a;	 Catch:{ all -> 0x0051 }
            r2 = r2.i;	 Catch:{ all -> 0x0051 }
            r2 = r2.b(r1);	 Catch:{ all -> 0x0051 }
            if (r2 == 0) goto L_0x004f;
        L_0x003a:
            r2 = r5.a;	 Catch:{ kg -> 0x004f }
            r2 = r2.i;	 Catch:{ kg -> 0x004f }
            r3 = r5.a;	 Catch:{ kg -> 0x004f }
            r3 = r3.c;	 Catch:{ kg -> 0x004f }
            r1 = r2.a(r1, r3);	 Catch:{ kg -> 0x004f }
            r1 = r1.toString();	 Catch:{ kg -> 0x004f }
            r6 = r1;
        L_0x004f:
            monitor-exit(r0);	 Catch:{ all -> 0x0051 }
            return r6;	 Catch:{ all -> 0x0051 }
        L_0x0051:
            r6 = move-exception;	 Catch:{ all -> 0x0051 }
            monitor-exit(r0);	 Catch:{ all -> 0x0051 }
            throw r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ii.a.a(java.lang.String[]):java.lang.String");
        }

        protected void a(String str) {
            this.b.setAdTagUrl(str);
            this.a.b.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.adsLoader, com.google.ads.interactivemedia.v3.internal.jc.c.requestAds, this.c, l.create(this.b, this.a.c(), this.a.d(), this.a.k, this.a.e(), jw.a(this.a.c, this.a.l))));
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            a((String) obj);
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return a((String[]) objArr);
        }
    }

    /* compiled from: IMASDK */
    public static abstract class b {
        public abstract int appVersion();

        public abstract String packageName();

        public static b create(int i, String str) {
            return new io(i, str);
        }
    }

    /* compiled from: IMASDK */
    private class c extends AsyncTask<Void, Void, String> {
        final /* synthetic */ ii a;
        private final StreamRequest b;
        private final String c;
        private String d = "";
        private String e = "";
        private String f = "";

        public c(ii iiVar, StreamRequest streamRequest, String str) {
            this.a = iiVar;
            this.b = streamRequest;
            this.c = str;
        }

        protected java.lang.String a(java.lang.Void... r5) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r5 = r4.a;
            r5 = r5.j;
            monitor-enter(r5);
            r0 = r4.a;	 Catch:{ all -> 0x0096 }
            r0 = r0.i;	 Catch:{ all -> 0x0096 }
            if (r0 != 0) goto L_0x0025;	 Catch:{ all -> 0x0096 }
        L_0x000f:
            r0 = r4.a;	 Catch:{ all -> 0x0096 }
            r1 = new com.google.ads.interactivemedia.v3.internal.kf;	 Catch:{ all -> 0x0096 }
            r2 = "a.3.9.4";	 Catch:{ all -> 0x0096 }
            r3 = r4.a;	 Catch:{ all -> 0x0096 }
            r3 = r3.c;	 Catch:{ all -> 0x0096 }
            r2 = com.google.ads.interactivemedia.v3.internal.ke.a(r2, r3);	 Catch:{ all -> 0x0096 }
            r1.<init>(r2);	 Catch:{ all -> 0x0096 }
            r0.i = r1;	 Catch:{ all -> 0x0096 }
        L_0x0025:
            r0 = r4.a;	 Catch:{ all -> 0x0096 }
            r0 = r0.i;	 Catch:{ all -> 0x0096 }
            r0 = r0.a();	 Catch:{ all -> 0x0096 }
            r1 = r4.a;	 Catch:{ all -> 0x0096 }
            r1 = r1.c;	 Catch:{ all -> 0x0096 }
            r0 = r0.a(r1);	 Catch:{ all -> 0x0096 }
            monitor-exit(r5);	 Catch:{ all -> 0x0096 }
            r5 = r4.a;	 Catch:{ Exception -> 0x005c }
            r5 = r5.c;	 Catch:{ Exception -> 0x005c }
            r5 = com.google.android.gms.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo(r5);	 Catch:{ Exception -> 0x005c }
            r1 = r5.getId();	 Catch:{ Exception -> 0x005c }
            r4.d = r1;	 Catch:{ Exception -> 0x005c }
            r1 = "adid";	 Catch:{ Exception -> 0x005c }
            r4.e = r1;	 Catch:{ Exception -> 0x005c }
            r5 = r5.isLimitAdTrackingEnabled();	 Catch:{ Exception -> 0x005c }
            if (r5 == 0) goto L_0x0057;	 Catch:{ Exception -> 0x005c }
        L_0x0054:
            r5 = "1";	 Catch:{ Exception -> 0x005c }
            goto L_0x0059;	 Catch:{ Exception -> 0x005c }
        L_0x0057:
            r5 = "0";	 Catch:{ Exception -> 0x005c }
        L_0x0059:
            r4.f = r5;	 Catch:{ Exception -> 0x005c }
            goto L_0x0095;
        L_0x005c:
            r5 = r4.a;	 Catch:{ SettingNotFoundException -> 0x0082 }
            r5 = r5.c;	 Catch:{ SettingNotFoundException -> 0x0082 }
            r5 = r5.getContentResolver();	 Catch:{ SettingNotFoundException -> 0x0082 }
            r1 = "advertising_id";	 Catch:{ SettingNotFoundException -> 0x0082 }
            r1 = android.provider.Settings.Secure.getString(r5, r1);	 Catch:{ SettingNotFoundException -> 0x0082 }
            r4.d = r1;	 Catch:{ SettingNotFoundException -> 0x0082 }
            r1 = "afai";	 Catch:{ SettingNotFoundException -> 0x0082 }
            r4.e = r1;	 Catch:{ SettingNotFoundException -> 0x0082 }
            r1 = "limit_ad_tracking";	 Catch:{ SettingNotFoundException -> 0x0082 }
            r5 = android.provider.Settings.Secure.getInt(r5, r1);	 Catch:{ SettingNotFoundException -> 0x0082 }
            if (r5 != 0) goto L_0x007d;	 Catch:{ SettingNotFoundException -> 0x0082 }
        L_0x007a:
            r5 = "0";	 Catch:{ SettingNotFoundException -> 0x0082 }
            goto L_0x007f;	 Catch:{ SettingNotFoundException -> 0x0082 }
        L_0x007d:
            r5 = "1";	 Catch:{ SettingNotFoundException -> 0x0082 }
        L_0x007f:
            r4.f = r5;	 Catch:{ SettingNotFoundException -> 0x0082 }
            goto L_0x0095;
        L_0x0082:
            r5 = "IMASDK";
            r1 = "Failed to get advertising ID.";
            android.util.Log.w(r5, r1);
            r5 = "";
            r4.d = r5;
            r5 = "";
            r4.e = r5;
            r5 = "";
            r4.f = r5;
        L_0x0095:
            return r0;
        L_0x0096:
            r0 = move-exception;
            monitor-exit(r5);	 Catch:{ all -> 0x0096 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ii.c.a(java.lang.Void[]):java.lang.String");
        }

        protected void a(String str) {
            this.a.b.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.adsLoader, com.google.ads.interactivemedia.v3.internal.jc.c.requestStream, this.c, l.createFromStreamRequest(this.b, this.a.c(), this.a.d(), this.a.k, this.a.e(), jw.a(this.a.c, this.a.l), str, this.d, this.e, this.f)));
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            a((String) obj);
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return a((Void[]) objArr);
        }
    }

    public ii(Context context, Uri uri, ImaSdkSettings imaSdkSettings) {
        this(context, uri, imaSdkSettings, null);
        this.b.a();
    }

    public ii(Context context, Uri uri, ImaSdkSettings imaSdkSettings, TestingConfiguration testingConfiguration) {
        this(new jd(context, uri, imaSdkSettings, testingConfiguration), context);
        this.k = imaSdkSettings;
        this.l = testingConfiguration;
    }

    public ii(jd jdVar, Context context) {
        this.a = new com.google.ads.interactivemedia.v3.internal.jd.b(this) {
            final /* synthetic */ ii a;

            {
                this.a = r1;
            }

            public void a(String str, jf jfVar, List<Float> list, SortedSet<Float> sortedSet, boolean z) {
                AdsRequest adsRequest = (AdsRequest) this.a.f.get(str);
                try {
                    r1.a.a(new ik(new ij(str, r1.a.b, jfVar, adsRequest.getAdDisplayContainer(), adsRequest.getContentProgressProvider(), list, sortedSet, r1.a.h, r1.a.c, z), adsRequest.getUserRequestContext()));
                } catch (AdError e) {
                    r1.a.d.a(new id(e, adsRequest.getUserRequestContext()));
                }
            }

            public void a(String str, jf jfVar, String str2, boolean z) {
                String str3 = str;
                StreamRequest streamRequest = (StreamRequest) this.a.g.get(str);
                this.a.h.a(true);
                try {
                    r1.a.a(new ik(new jl(str, r1.a.b, jfVar, streamRequest, r1.a.h, r1.a.c, str2, z), streamRequest.getUserRequestContext()));
                } catch (AdError e) {
                    r1.a.d.a(new id(e, streamRequest.getUserRequestContext()));
                }
            }

            public void a(String str, AdErrorType adErrorType, int i, String str2) {
                if (this.a.f.get(str) != null) {
                    str = ((AdsRequest) this.a.f.get(str)).getUserRequestContext();
                } else {
                    str = ((StreamRequest) this.a.g.get(str)).getUserRequestContext();
                }
                this.a.d.a(new id(new AdError(adErrorType, i, str2), str));
            }

            public void a(String str, AdErrorType adErrorType, AdErrorCode adErrorCode, String str2) {
                if (this.a.f.get(str) != null) {
                    str = ((AdsRequest) this.a.f.get(str)).getUserRequestContext();
                } else {
                    str = ((StreamRequest) this.a.g.get(str)).getUserRequestContext();
                }
                this.a.d.a(new id(new AdError(adErrorType, adErrorCode, str2), str));
            }
        };
        this.d = new ix();
        this.e = new ArrayList(1);
        this.f = new HashMap();
        this.g = new HashMap();
        this.j = new Object();
        this.b = jdVar;
        this.c = context;
        this.h = new jh(jdVar.b(), context);
        this.k = ImaSdkFactory.getInstance().createImaSdkSettings();
    }

    public void a() {
        this.b.a();
    }

    public void contentComplete() {
        this.b.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.adsLoader, com.google.ads.interactivemedia.v3.internal.jc.c.contentComplete, "*"));
    }

    public void requestAds(AdsRequest adsRequest) {
        a(adsRequest, b());
    }

    void a(AdsRequest adsRequest, String str) {
        if (a(adsRequest)) {
            this.f.put(str, adsRequest);
            this.b.a(this.a, str);
            this.b.a(adsRequest.getAdDisplayContainer(), str);
            new a(this, adsRequest, str).execute(new String[]{adsRequest.getAdTagUrl()});
        }
    }

    public String requestStream(StreamRequest streamRequest) {
        String b = b();
        a(streamRequest, b);
        return b;
    }

    void a(StreamRequest streamRequest, String str) {
        if (a(streamRequest)) {
            this.g.put(str, streamRequest);
            this.b.a(this.a, str);
            this.b.a(streamRequest.getStreamDisplayContainer(), str);
            new c(this, streamRequest, str).execute(new Void[null]);
        }
    }

    private String b() {
        if (this.l == null || !this.l.ignoreStrictModeFalsePositives()) {
            return UUID.randomUUID().toString();
        }
        ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new Builder(threadPolicy).permitDiskReads().build());
        String uuid = UUID.randomUUID().toString();
        StrictMode.setThreadPolicy(threadPolicy);
        return uuid;
    }

    private boolean a(AdsRequest adsRequest) {
        if (adsRequest == null) {
            this.d.a(new id(new AdError(AdErrorType.LOAD, AdErrorCode.INVALID_ARGUMENTS, "AdsRequest cannot be null.")));
            return false;
        }
        AdDisplayContainer adDisplayContainer = adsRequest.getAdDisplayContainer();
        if (adDisplayContainer == null) {
            this.d.a(new id(new AdError(AdErrorType.LOAD, AdErrorCode.INVALID_ARGUMENTS, "Ad display container must be provided in the AdsRequest.")));
            return false;
        } else if (adDisplayContainer.getAdContainer() == null) {
            this.d.a(new id(new AdError(AdErrorType.LOAD, AdErrorCode.INVALID_ARGUMENTS, "Ad display container must have a UI container.")));
            return false;
        } else if (this.b.c() != null && this.b.c().b() == com.google.ads.interactivemedia.v3.internal.jc.a.nativeUi && adDisplayContainer.getPlayer() == null) {
            this.d.a(new id(new AdError(AdErrorType.LOAD, AdErrorCode.INVALID_ARGUMENTS, "Ad Player was not provided.")));
            return false;
        } else if (!kw.a(adsRequest.getAdTagUrl()) || kw.a(adsRequest.getAdsResponse()) == null) {
            return true;
        } else {
            this.d.a(new id(new AdError(AdErrorType.LOAD, AdErrorCode.INVALID_ARGUMENTS, "Ad tag url must non-null and non empty.")));
            return false;
        }
    }

    private boolean a(StreamRequest streamRequest) {
        if (streamRequest == null) {
            this.d.a(new id(new AdError(AdErrorType.LOAD, AdErrorCode.INVALID_ARGUMENTS, "StreamRequest cannot be null.")));
            return false;
        }
        streamRequest = streamRequest.getStreamDisplayContainer();
        if (streamRequest == null) {
            this.d.a(new id(new AdError(AdErrorType.LOAD, AdErrorCode.INVALID_ARGUMENTS, "Ad display container must be provided in the StreamRequest.")));
            return false;
        } else if (streamRequest.getVideoStreamPlayer() != null) {
            return true;
        } else {
            this.d.a(new id(new AdError(AdErrorType.LOAD, AdErrorCode.INVALID_ARGUMENTS, "Stream requests must specify a player.")));
            return false;
        }
    }

    public ImaSdkSettings getSettings() {
        return this.k;
    }

    public void addAdsLoadedListener(AdsLoadedListener adsLoadedListener) {
        this.e.add(adsLoadedListener);
    }

    public void removeAdsLoadedListener(AdsLoadedListener adsLoadedListener) {
        this.e.remove(adsLoadedListener);
    }

    public void addAdErrorListener(AdErrorListener adErrorListener) {
        this.d.a(adErrorListener);
    }

    public void removeAdErrorListener(AdErrorListener adErrorListener) {
        this.d.b(adErrorListener);
    }

    void a(AdsManagerLoadedEvent adsManagerLoadedEvent) {
        for (AdsLoadedListener onAdsManagerLoaded : this.e) {
            onAdsManagerLoaded.onAdsManagerLoaded(adsManagerLoadedEvent);
        }
    }

    private String c() {
        return String.format("android%s:%s:%s", new Object[]{VERSION.RELEASE, BuildConfig.AD_LIB_VER, this.c.getPackageName()});
    }

    private String d() {
        if (this.c.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            Log.w("IMASDK", "Host application doesn't have ACCESS_NETWORK_STATE permission");
            return "android:0";
        }
        if (((ConnectivityManager) this.c.getSystemService("connectivity")).getActiveNetworkInfo() == null) {
            return "android:0";
        }
        return String.format(Locale.US, "android:%d:%d", new Object[]{Integer.valueOf(((ConnectivityManager) this.c.getSystemService("connectivity")).getActiveNetworkInfo().getType()), Integer.valueOf(((ConnectivityManager) this.c.getSystemService("connectivity")).getActiveNetworkInfo().getSubtype())});
    }

    private com.google.ads.interactivemedia.v3.internal.ii.b e() {
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
        r0 = r5.c;
        r0 = r0.getPackageManager();
        r1 = new android.content.Intent;
        r2 = "android.intent.action.VIEW";
        r3 = "market://details?id=com.google.ads.interactivemedia.v3";
        r3 = android.net.Uri.parse(r3);
        r1.<init>(r2, r3);
        r2 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r1 = r0.resolveActivity(r1, r2);
        r2 = 0;
        if (r1 != 0) goto L_0x001d;
    L_0x001c:
        return r2;
    L_0x001d:
        r1 = r1.activityInfo;
        if (r1 != 0) goto L_0x0022;
    L_0x0021:
        return r2;
    L_0x0022:
        r3 = r1.packageName;	 Catch:{ NameNotFoundException -> 0x0035 }
        r4 = 0;	 Catch:{ NameNotFoundException -> 0x0035 }
        r0 = r0.getPackageInfo(r3, r4);	 Catch:{ NameNotFoundException -> 0x0035 }
        if (r0 != 0) goto L_0x002c;
    L_0x002b:
        return r2;
    L_0x002c:
        r0 = r0.versionCode;
        r1 = r1.packageName;
        r0 = com.google.ads.interactivemedia.v3.internal.ii.b.create(r0, r1);
        return r0;
    L_0x0035:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ii.e():com.google.ads.interactivemedia.v3.internal.ii$b");
    }
}
