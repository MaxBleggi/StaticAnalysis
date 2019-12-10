package com.google.ads.interactivemedia.v3.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.collection.ArrayMap;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorCode;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorType;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventType;
import com.google.ads.interactivemedia.v3.api.AdProgressInfo;
import com.google.ads.interactivemedia.v3.api.AdsRenderingSettings;
import com.google.ads.interactivemedia.v3.api.BaseDisplayContainer;
import com.google.ads.interactivemedia.v3.api.CompanionAdSlot;
import com.google.ads.interactivemedia.v3.api.CompanionAdSlot.ClickListener;
import com.google.ads.interactivemedia.v3.api.CuePoint;
import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;
import com.google.ads.interactivemedia.v3.impl.data.CompanionData;
import com.google.ads.interactivemedia.v3.impl.data.TestingConfiguration;
import com.google.ads.interactivemedia.v3.impl.data.k;
import com.google.ads.interactivemedia.v3.impl.data.m;
import com.google.android.exoplayer2.C;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

/* compiled from: IMASDK */
public class jd implements com.google.ads.interactivemedia.v3.internal.je.a {
    private Map<String, d> a = new HashMap();
    private Map<String, a> b = new HashMap();
    private final Set<String> c = new HashSet();
    private Map<String, b> d = new HashMap();
    private Map<String, e> e = new HashMap();
    private Map<String, jo> f = new HashMap();
    private Map<String, BaseDisplayContainer> g = new HashMap();
    private final Context h;
    private final je i;
    private jf j;
    private boolean k = false;
    private final Queue<jc> l = new ArrayDeque();
    private long m;
    private TestingConfiguration n;
    private String o;
    private AdsRenderingSettings p;

    /* compiled from: IMASDK */
    public interface a {
        void a(String str, String str2);

        void a(String str, String str2, String str3);
    }

    /* compiled from: IMASDK */
    public interface b {
        void a(String str, AdErrorType adErrorType, int i, String str2);

        void a(String str, AdErrorType adErrorType, AdErrorCode adErrorCode, String str2);

        void a(String str, jf jfVar, String str2, boolean z);

        void a(String str, jf jfVar, List<Float> list, SortedSet<Float> sortedSet, boolean z);
    }

    /* compiled from: IMASDK */
    public static class c {
        public final AdEventType a;
        public final com.google.ads.interactivemedia.v3.impl.data.b b;
        public Map<String, String> c;
        public List<CuePoint> d;
        AdProgressInfo e;
        public String f;

        public c(AdEventType adEventType, com.google.ads.interactivemedia.v3.impl.data.b bVar) {
            this.a = adEventType;
            this.b = bVar;
        }

        public boolean equals(Object obj) {
            return mb.a((Object) this, obj, new String[0]);
        }

        public int hashCode() {
            return md.a(this, new String[0]);
        }
    }

    /* compiled from: IMASDK */
    public interface d {
        void a(AdErrorType adErrorType, int i, String str);

        void a(AdErrorType adErrorType, AdErrorCode adErrorCode, String str);

        void a(c cVar);

        void a(Map<String, CompanionData> map);
    }

    /* compiled from: IMASDK */
    public interface e {
        void a(com.google.ads.interactivemedia.v3.internal.jc.c cVar, String str);
    }

    protected Uri a(Uri uri, ImaSdkSettings imaSdkSettings) {
        uri = uri.buildUpon().appendQueryParameter("sdk_version", "a.3.9.4").appendQueryParameter("hl", imaSdkSettings.getLanguage()).appendQueryParameter("omv", jh.d()).appendQueryParameter("omvx", imaSdkSettings.getEnableOmidExperimentally() != null ? "1" : "0").appendQueryParameter("wvr", "2").appendQueryParameter(SettingsJsonConstants.APP_KEY, this.h.getApplicationContext().getPackageName());
        if (this.n != null) {
            uri.appendQueryParameter(TestingConfiguration.PARAMETER_KEY, new ga().a(new ko()).a(new kn()).a().a(this.n));
        }
        return uri.build();
    }

    public jd(Context context, Uri uri, ImaSdkSettings imaSdkSettings, TestingConfiguration testingConfiguration) {
        this.h = context;
        this.n = testingConfiguration;
        this.i = new je(context, (com.google.ads.interactivemedia.v3.internal.je.a) this);
        this.o = a(uri, imaSdkSettings).toString();
    }

    public void a() {
        this.m = SystemClock.elapsedRealtime();
        this.i.a(this.o);
    }

    public void a(AdsRenderingSettings adsRenderingSettings) {
        this.p = adsRenderingSettings;
    }

    public WebView b() {
        return this.i.a();
    }

    public jf c() {
        return this.j;
    }

    TestingConfiguration d() {
        return this.n;
    }

    public void a(jc jcVar) {
        m mVar = (m) jcVar.c();
        String d = jcVar.d();
        com.google.ads.interactivemedia.v3.internal.jc.c b = jcVar.b();
        switch (jcVar.a()) {
            case adsManager:
                f(b, d, mVar);
                return;
            case activityMonitor:
                g(b, d, mVar);
                return;
            case videoDisplay:
                e(b, d, mVar);
                return;
            case adsLoader:
                d(b, d, mVar);
                return;
            case displayContainer:
                b(b, d, mVar);
                return;
            case i18n:
                c(b, d, mVar);
                return;
            case omid:
                a(b);
                return;
            case webViewLoaded:
            case log:
                a(b, d, mVar);
                return;
            default:
                jcVar = String.valueOf(jcVar.a());
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(jcVar).length() + 25);
                stringBuilder.append("Unknown message channel: ");
                stringBuilder.append(jcVar);
                Log.e("IMASDK", stringBuilder.toString());
                return;
        }
    }

    private void a(com.google.ads.interactivemedia.v3.internal.jc.c cVar) {
        switch (cVar) {
            case omidReady:
                jh.a();
                return;
            case omidUnavailable:
                jh.b();
                return;
            default:
                return;
        }
    }

    private void a(com.google.ads.interactivemedia.v3.internal.jc.c r5, java.lang.String r6, com.google.ads.interactivemedia.v3.impl.data.m r7) {
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
        r0 = com.google.ads.interactivemedia.v3.internal.jd.AnonymousClass2.b;
        r1 = r5.ordinal();
        r0 = r0[r1];
        switch(r0) {
            case 3: goto L_0x00aa;
            case 4: goto L_0x0012;
            default: goto L_0x000b;
        };
    L_0x000b:
        r6 = "other";
        r4.a(r6, r5);
        goto L_0x00d0;
    L_0x0012:
        r5 = r7.ln;
        if (r5 == 0) goto L_0x0085;
    L_0x0016:
        r5 = r7.n;
        if (r5 == 0) goto L_0x0085;
    L_0x001a:
        r5 = r7.m;
        if (r5 != 0) goto L_0x001f;
    L_0x001e:
        goto L_0x0085;
    L_0x001f:
        r5 = "SDK_LOG:";
        r6 = r7.n;
        r6 = java.lang.String.valueOf(r6);
        r0 = r6.length();
        if (r0 == 0) goto L_0x0032;
    L_0x002d:
        r5 = r5.concat(r6);
        goto L_0x0038;
    L_0x0032:
        r6 = new java.lang.String;
        r6.<init>(r5);
        r5 = r6;
    L_0x0038:
        r6 = r7.ln;
        r0 = 0;
        r6 = r6.charAt(r0);
        switch(r6) {
            case 68: goto L_0x0070;
            case 69: goto L_0x006a;
            case 73: goto L_0x0064;
            case 83: goto L_0x006a;
            case 86: goto L_0x005e;
            case 87: goto L_0x0057;
            default: goto L_0x0042;
        };
    L_0x0042:
        r6 = "IMASDK";
        r0 = "Unrecognized log level: ";
        r1 = r7.ln;
        r1 = java.lang.String.valueOf(r1);
        r2 = r1.length();
        if (r2 == 0) goto L_0x0076;
    L_0x0052:
        r0 = r0.concat(r1);
        goto L_0x007c;
    L_0x0057:
        r6 = r7.m;
        android.util.Log.w(r5, r6);
        goto L_0x00d0;
    L_0x005e:
        r6 = r7.m;
        android.util.Log.v(r5, r6);
        goto L_0x00d0;
    L_0x0064:
        r6 = r7.m;
        android.util.Log.i(r5, r6);
        goto L_0x00d0;
    L_0x006a:
        r6 = r7.m;
        android.util.Log.e(r5, r6);
        goto L_0x00d0;
    L_0x0070:
        r6 = r7.m;
        android.util.Log.d(r5, r6);
        goto L_0x00d0;
    L_0x0076:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1;
    L_0x007c:
        android.util.Log.w(r6, r0);
        r6 = r7.m;
        android.util.Log.w(r5, r6);
        goto L_0x00d0;
    L_0x0085:
        r5 = "IMASDK";
        r6 = java.lang.String.valueOf(r7);
        r7 = java.lang.String.valueOf(r6);
        r7 = r7.length();
        r7 = r7 + 30;
        r0 = new java.lang.StringBuilder;
        r0.<init>(r7);
        r7 = "Invalid logging message data: ";
        r0.append(r7);
        r0.append(r6);
        r6 = r0.toString();
        android.util.Log.e(r5, r6);
        goto L_0x00d0;
    L_0x00aa:
        r5 = com.google.ads.interactivemedia.v3.internal.jc.a.nativeUi;
        r0 = r7.adUiStyle;	 Catch:{ IllegalArgumentException -> 0x00b7 }
        if (r0 == 0) goto L_0x00b7;	 Catch:{ IllegalArgumentException -> 0x00b7 }
    L_0x00b0:
        r0 = r7.adUiStyle;	 Catch:{ IllegalArgumentException -> 0x00b7 }
        r0 = com.google.ads.interactivemedia.v3.internal.jc.a.valueOf(r0);	 Catch:{ IllegalArgumentException -> 0x00b7 }
        r5 = r0;
    L_0x00b7:
        r0 = new com.google.ads.interactivemedia.v3.internal.jf;
        r1 = r7.adTimeUpdateMs;
        r0.<init>(r1, r5);
        r4.j = r0;
        r5 = 1;
        r4.k = r5;
        r0 = android.os.SystemClock.elapsedRealtime();
        r2 = r4.m;
        r0 = r0 - r2;
        r4.a(r0, r6);
        r4.e();
    L_0x00d0:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.jd.a(com.google.ads.interactivemedia.v3.internal.jc$c, java.lang.String, com.google.ads.interactivemedia.v3.impl.data.m):void");
    }

    private void a(long j, String str) {
        Map hashMap = new HashMap();
        hashMap.put("webViewLoadingTime", Long.valueOf(j));
        b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.webViewLoaded, com.google.ads.interactivemedia.v3.internal.jc.c.csi, str, hashMap));
    }

    private void b(com.google.ads.interactivemedia.v3.internal.jc.c cVar, String str, m mVar) {
        iq iqVar = (iq) this.g.get(str);
        d dVar = (d) this.a.get(str);
        jo joVar = (jo) this.f.get(str);
        if (!(iqVar == null || dVar == null)) {
            if (joVar != null) {
                if (!joVar.b(cVar, mVar)) {
                    if (AnonymousClass2.b[cVar.ordinal()] != 5) {
                        a(com.google.ads.interactivemedia.v3.internal.jc.b.displayContainer.toString(), cVar);
                    } else {
                        if (mVar != null) {
                            if (mVar.companions != null) {
                                cVar = a(iqVar, mVar.companions.keySet());
                                dVar.a(mVar.companions);
                                if (cVar == null) {
                                    dVar.a(AdErrorType.LOAD, AdErrorCode.INTERNAL_ERROR, "Display requested for invalid companion slot.");
                                } else if (this.p == null || this.p.isRenderCompanions()) {
                                    for (String str2 : cVar.keySet()) {
                                        a((ViewGroup) cVar.get(str2), (CompanionData) mVar.companions.get(str2), str, (CompanionAdSlot) iqVar.a().get(str2));
                                    }
                                }
                            }
                        }
                        dVar.a(AdErrorType.LOAD, AdErrorCode.INTERNAL_ERROR, "Display companions message requires companions in data.");
                    }
                }
                return;
            }
        }
        cVar = String.valueOf(cVar);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(cVar).length() + 60) + String.valueOf(str).length());
        stringBuilder.append("Received displayContainer message: ");
        stringBuilder.append(cVar);
        stringBuilder.append(" for invalid session id: ");
        stringBuilder.append(str);
        Log.e("IMASDK", stringBuilder.toString());
    }

    private void c(com.google.ads.interactivemedia.v3.internal.jc.c cVar, String str, m mVar) {
        e eVar = (e) this.e.get(str);
        if (eVar != null) {
            eVar.a(cVar, mVar.translation);
        }
    }

    private Map<String, ViewGroup> a(iq iqVar, Set<String> set) {
        Map<String, ViewGroup> hashMap = new HashMap(set.size());
        for (String str : set) {
            CompanionAdSlot companionAdSlot = (CompanionAdSlot) iqVar.a().get(str);
            if (companionAdSlot.getContainer() == null) {
                return null;
            }
            hashMap.put(str, companionAdSlot.getContainer());
        }
        return hashMap;
    }

    private void d(com.google.ads.interactivemedia.v3.internal.jc.c cVar, String str, m mVar) {
        b bVar = (b) this.d.get(str);
        if (bVar == null) {
            cVar = String.valueOf(cVar);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(cVar).length() + 51) + String.valueOf(str).length());
            stringBuilder.append("Received request message: ");
            stringBuilder.append(cVar);
            stringBuilder.append(" for invalid session id: ");
            stringBuilder.append(str);
            Log.e("IMASDK", stringBuilder.toString());
            return;
        }
        switch (cVar) {
            case adsLoaded:
                if (mVar != null) {
                    bVar.a(str, this.j, mVar.adCuePoints, mVar.internalCuePoints, mVar.monitorAppLifecycle);
                    break;
                } else {
                    bVar.a(str, AdErrorType.LOAD, AdErrorCode.INTERNAL_ERROR, "adsLoaded message did not contain cue points.");
                    break;
                }
            case streamInitialized:
                bVar.a(str, this.j, mVar.streamId, mVar.monitorAppLifecycle);
                cVar = "IMASDK";
                str = "Stream initialized with streamId: ";
                mVar = String.valueOf(mVar.streamId);
                Log.i(cVar, mVar.length() != 0 ? str.concat(mVar) : new String(str));
                break;
            case error:
                bVar.a(str, AdErrorType.LOAD, mVar.errorCode, b(mVar.errorMessage, mVar.innerError));
                break;
            default:
                a(com.google.ads.interactivemedia.v3.internal.jc.b.adsLoader.toString(), cVar);
                break;
        }
    }

    private void e(com.google.ads.interactivemedia.v3.internal.jc.c cVar, String str, m mVar) {
        jo joVar = (jo) this.f.get(str);
        if (joVar == null) {
            cVar = String.valueOf(cVar);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(cVar).length() + 56) + String.valueOf(str).length());
            stringBuilder.append("Received videoDisplay message: ");
            stringBuilder.append(cVar);
            stringBuilder.append(" for invalid session id: ");
            stringBuilder.append(str);
            Log.w("IMASDK", stringBuilder.toString());
            return;
        }
        joVar.a(cVar, mVar);
    }

    private void f(com.google.ads.interactivemedia.v3.internal.jc.c cVar, String str, m mVar) {
        d dVar = (d) this.a.get(str);
        if (dVar == null) {
            cVar = String.valueOf(cVar);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(cVar).length() + 51) + String.valueOf(str).length());
            stringBuilder.append("Received manager message: ");
            stringBuilder.append(cVar);
            stringBuilder.append(" for invalid session id: ");
            stringBuilder.append(str);
            Log.e("IMASDK", stringBuilder.toString());
            return;
        }
        com.google.ads.interactivemedia.v3.impl.data.b bVar = (mVar == null || mVar.adData == null) ? null : mVar.adData;
        int i = AnonymousClass2.b[cVar.ordinal()];
        c cVar2;
        if (i != 4) {
            switch (i) {
                case 8:
                    dVar.a(AdErrorType.PLAY, mVar.errorCode, b(mVar.errorMessage, mVar.innerError));
                    break;
                case 9:
                case 31:
                case 32:
                case 33:
                case 34:
                    break;
                case 10:
                    if (bVar == null) {
                        Log.e("IMASDK", "Ad loaded message requires adData");
                        dVar.a(AdErrorType.LOAD, AdErrorCode.INTERNAL_ERROR, "Ad loaded message did not contain adData.");
                        break;
                    }
                    dVar.a(new c(AdEventType.LOADED, bVar));
                    break;
                case 11:
                    dVar.a(new c(AdEventType.CONTENT_PAUSE_REQUESTED, null));
                    break;
                case 12:
                    dVar.a(new c(AdEventType.CONTENT_RESUME_REQUESTED, null));
                    break;
                case 13:
                    dVar.a(new c(AdEventType.COMPLETED, bVar));
                    break;
                case 14:
                    dVar.a(new c(AdEventType.ALL_ADS_COMPLETED, null));
                    break;
                case 15:
                    cVar2 = new c(AdEventType.CUEPOINTS_CHANGED, null);
                    cVar2.d = new ArrayList();
                    for (k kVar : mVar.cuepoints) {
                        cVar2.d.add(new iw(kVar.start(), kVar.end(), kVar.played()));
                    }
                    dVar.a(cVar2);
                    break;
                case 16:
                    dVar.a(new c(AdEventType.SKIPPED, bVar));
                    break;
                case 17:
                    dVar.a(new c(AdEventType.STARTED, bVar));
                    break;
                case 18:
                    dVar.a(new c(AdEventType.PAUSED, bVar));
                    break;
                case 19:
                    dVar.a(new c(AdEventType.RESUMED, bVar));
                    break;
                case 20:
                    dVar.a(new c(AdEventType.FIRST_QUARTILE, bVar));
                    break;
                case 21:
                    dVar.a(new c(AdEventType.MIDPOINT, bVar));
                    break;
                case 22:
                    dVar.a(new c(AdEventType.THIRD_QUARTILE, bVar));
                    break;
                case 23:
                    dVar.a(new c(AdEventType.CLICKED, bVar));
                    break;
                case 24:
                    dVar.a(new c(AdEventType.SKIPPABLE_STATE_CHANGED, bVar));
                    break;
                case 25:
                    dVar.a(new c(AdEventType.TAPPED, bVar));
                    break;
                case 26:
                    cVar2 = new c(AdEventType.ICON_TAPPED, null);
                    cVar2.f = mVar.clickThroughUrl;
                    dVar.a(cVar2);
                    break;
                case 27:
                    cVar2 = new c(AdEventType.AD_PROGRESS, bVar);
                    cVar2.e = new ig(mVar.currentTime, mVar.duration, mVar.adPosition, mVar.totalAds, mVar.adBreakDuration);
                    dVar.a(cVar2);
                    break;
                case 28:
                    cVar2 = new c(AdEventType.AD_BREAK_READY, null);
                    cVar2.c = new ArrayMap(1);
                    cVar2.c.put("adBreakTime", mVar.adBreakTime);
                    dVar.a(cVar2);
                    break;
                case 29:
                    dVar.a(new c(AdEventType.AD_BREAK_STARTED, bVar));
                    break;
                case 30:
                    dVar.a(new c(AdEventType.AD_BREAK_ENDED, bVar));
                    break;
                default:
                    a(com.google.ads.interactivemedia.v3.internal.jc.b.adsManager.toString(), cVar);
                    break;
            }
        }
        cVar2 = new c(AdEventType.LOG, bVar);
        cVar2.c = mVar.logData.constructMap();
        dVar.a(cVar2);
    }

    private void g(com.google.ads.interactivemedia.v3.internal.jc.c cVar, String str, m mVar) {
        if (!this.c.contains(str)) {
            a aVar = (a) this.b.get(str);
            StringBuilder stringBuilder;
            if (aVar == null) {
                cVar = String.valueOf(cVar);
                stringBuilder = new StringBuilder((String.valueOf(cVar).length() + 51) + String.valueOf(str).length());
                stringBuilder.append("Received monitor message: ");
                stringBuilder.append(cVar);
                stringBuilder.append(" for invalid session id: ");
                stringBuilder.append(str);
                Log.e("IMASDK", stringBuilder.toString());
            } else if (mVar == null) {
                cVar = String.valueOf(cVar);
                stringBuilder = new StringBuilder((String.valueOf(cVar).length() + 56) + String.valueOf(str).length());
                stringBuilder.append("Received monitor message: ");
                stringBuilder.append(cVar);
                stringBuilder.append(" for session id: ");
                stringBuilder.append(str);
                stringBuilder.append(" with no data");
                Log.e("IMASDK", stringBuilder.toString());
            } else {
                switch (cVar) {
                    case getViewability:
                        aVar.a(mVar.queryId, mVar.eventId);
                        break;
                    case reportVastEvent:
                        aVar.a(mVar.queryId, mVar.eventId, mVar.vastEvent);
                        break;
                    default:
                        a(com.google.ads.interactivemedia.v3.internal.jc.b.activityMonitor.toString(), cVar);
                        break;
                }
            }
        }
    }

    private void a(String str, com.google.ads.interactivemedia.v3.internal.jc.c cVar) {
        cVar = String.valueOf(cVar);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(cVar).length() + 43) + String.valueOf(str).length());
        stringBuilder.append("Illegal message type ");
        stringBuilder.append(cVar);
        stringBuilder.append(" received for ");
        stringBuilder.append(str);
        stringBuilder.append(" channel");
        Log.i("IMASDK", stringBuilder.toString());
    }

    private String b(String str, String str2) {
        if (str2 != null) {
            if (str2.length() != 0) {
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 12) + String.valueOf(str2).length());
                stringBuilder.append(str);
                stringBuilder.append(" Caused by: ");
                stringBuilder.append(str2);
                return stringBuilder.toString();
            }
        }
        return str;
    }

    public void a(b bVar, String str) {
        this.d.put(str, bVar);
    }

    public void a(e eVar, String str) {
        this.e.put(str, eVar);
    }

    public void a(String str) {
        this.e.remove(str);
    }

    public void a(d dVar, String str) {
        this.a.put(str, dVar);
    }

    public void a(a aVar, String str) {
        this.b.put(str, aVar);
    }

    public void b(String str) {
        this.b.remove(str);
        this.c.add(str);
    }

    public void a(jo joVar, String str) {
        this.f.put(str, joVar);
    }

    public void a(BaseDisplayContainer baseDisplayContainer, String str) {
        this.g.put(str, baseDisplayContainer);
    }

    public void c(String str) {
        this.a.remove(str);
        this.g.remove(str);
        this.f.remove(str);
    }

    public void b(jc jcVar) {
        this.l.add(jcVar);
        e();
    }

    private void e() {
        while (this.k && !this.l.isEmpty()) {
            this.i.a((jc) this.l.remove());
        }
    }

    public void d(final String str) {
        if (str != null && str.length() > 0) {
            new AsyncTask<Void, Void, Void>(this) {
                final /* synthetic */ jd b;

                protected Void a(Void... voidArr) {
                    voidArr = new Intent("android.intent.action.VIEW", Uri.parse(str));
                    if (!(this.b.h instanceof Activity)) {
                        voidArr.setFlags(C.ENCODING_PCM_MU_LAW);
                    }
                    this.b.h.startActivity(voidArr);
                    return null;
                }

                protected /* synthetic */ Object doInBackground(Object[] objArr) {
                    return a((Void[]) objArr);
                }
            }.execute(new Void[null]);
        }
    }

    private void a(ViewGroup viewGroup, CompanionData companionData, String str, CompanionAdSlot companionAdSlot) {
        viewGroup.removeAllViews();
        is isVar = (is) companionAdSlot;
        List a = isVar.a();
        switch (companionData.type()) {
            case Html:
            case IFrame:
                companionData = a(viewGroup.getContext(), companionData, a);
                break;
            case Static:
                companionData = a(viewGroup.getContext(), companionData, str, a);
                break;
            default:
                companionData = null;
                break;
        }
        companionData.setTag(str);
        isVar.a(str);
        viewGroup.addView(companionData);
    }

    public void a(String str, String str2) {
        if (!kw.a(str) && !kw.a(str2)) {
            Map hashMap = new HashMap();
            hashMap.put("companionId", str);
            b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.displayContainer, com.google.ads.interactivemedia.v3.internal.jc.c.companionView, str2, hashMap));
        }
    }

    protected View a(Context context, CompanionData companionData, List<ClickListener> list) {
        return new it(context, this, companionData, list);
    }

    protected View a(Context context, CompanionData companionData, String str, List<ClickListener> list) {
        View jbVar = new jb(context, this, companionData, str, list);
        jbVar.a();
        return jbVar;
    }
}
