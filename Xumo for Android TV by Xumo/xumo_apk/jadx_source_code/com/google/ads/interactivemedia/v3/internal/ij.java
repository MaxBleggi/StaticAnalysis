package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import com.google.ads.interactivemedia.v3.api.Ad;
import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdError;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorCode;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorType;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventListener;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventType;
import com.google.ads.interactivemedia.v3.api.AdsManager;
import com.google.ads.interactivemedia.v3.api.AdsRenderingSettings;
import com.google.ads.interactivemedia.v3.api.BaseDisplayContainer;
import com.google.ads.interactivemedia.v3.api.player.ContentProgressProvider;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.ads.interactivemedia.v3.impl.data.CompanionData;
import com.google.ads.interactivemedia.v3.internal.jc.a;
import com.google.ads.interactivemedia.v3.internal.jc.b;
import com.google.ads.interactivemedia.v3.internal.jc.c;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

/* compiled from: IMASDK */
public class ij extends ir implements AdsManager {
    private List<CompanionData> h;
    private List<Float> i;

    /* compiled from: IMASDK */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] b = new int[AdEventType.values().length];

        static {
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
            r0 = com.google.ads.interactivemedia.v3.api.AdEvent.AdEventType.values();
            r0 = r0.length;
            r0 = new int[r0];
            b = r0;
            r0 = 1;
            r1 = b;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = com.google.ads.interactivemedia.v3.api.AdEvent.AdEventType.ALL_ADS_COMPLETED;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = com.google.ads.interactivemedia.v3.internal.jc.a.values();
            r1 = r1.length;
            r1 = new int[r1];
            a = r1;
            r1 = a;	 Catch:{ NoSuchFieldError -> 0x0027 }
            r2 = com.google.ads.interactivemedia.v3.internal.jc.a.webViewUi;	 Catch:{ NoSuchFieldError -> 0x0027 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0027 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0027 }
        L_0x0027:
            r0 = a;	 Catch:{ NoSuchFieldError -> 0x0032 }
            r1 = com.google.ads.interactivemedia.v3.internal.jc.a.nativeUi;	 Catch:{ NoSuchFieldError -> 0x0032 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0032 }
            r2 = 2;	 Catch:{ NoSuchFieldError -> 0x0032 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0032 }
        L_0x0032:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ij.1.<clinit>():void");
        }
    }

    ij(String str, jd jdVar, jf jfVar, BaseDisplayContainer baseDisplayContainer, ContentProgressProvider contentProgressProvider, List<Float> list, SortedSet<Float> sortedSet, jh jhVar, Context context, boolean z) throws AdError {
        this(str, jdVar, jfVar, baseDisplayContainer, contentProgressProvider, list, sortedSet, null, null, null, jhVar, context, z);
    }

    ij(String str, jd jdVar, jf jfVar, BaseDisplayContainer baseDisplayContainer, ContentProgressProvider contentProgressProvider, List<Float> list, SortedSet<Float> sortedSet, jo joVar, iv ivVar, ib ibVar, jh jhVar, Context context, boolean z) throws AdError {
        String str2 = str;
        jd jdVar2 = jdVar;
        ContentProgressProvider contentProgressProvider2 = contentProgressProvider;
        SortedSet<Float> sortedSet2 = sortedSet;
        jo joVar2 = joVar;
        iv ivVar2 = ivVar;
        super(str, jdVar, jfVar, baseDisplayContainer, ibVar, jhVar, context, z);
        this.i = list;
        if (!(sortedSet2 == null || sortedSet.isEmpty())) {
            if (contentProgressProvider2 != null) {
                if (ivVar2 != null) {
                    r9.f = ivVar2;
                } else {
                    r9.f = new iv(contentProgressProvider2, jfVar.a());
                }
                r9.e = new iu(jdVar2, sortedSet2, str2);
                r9.f.a(r9.e);
                r9.f.b();
            } else {
                throw new AdError(AdErrorType.PLAY, AdErrorCode.PLAYLIST_NO_CONTENT_TRACKING, "Unable to handle cue points, no content progress provider configured.");
            }
        }
        if (joVar2 != null) {
            r9.d = joVar2;
        } else {
            a b = jfVar.b();
            switch (b) {
                case webViewUi:
                case nativeUi:
                    r9.d = new jg(str, jfVar, jdVar, this, (AdDisplayContainer) baseDisplayContainer, context);
                    break;
                default:
                    AdErrorType adErrorType = AdErrorType.PLAY;
                    AdErrorCode adErrorCode = AdErrorCode.INTERNAL_ERROR;
                    String str3 = "UI style not supported: ";
                    String valueOf = String.valueOf(b.name());
                    throw new AdError(adErrorType, adErrorCode, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            }
        }
        addAdErrorListener(r9.d);
        jdVar2.a(r9.d, str2);
    }

    protected void a() {
        this.h = null;
        super.a();
    }

    public void discardAdBreak() {
        a(c.discardAdBreak);
    }

    public void requestNextAdBreak() {
        if (this.f != null) {
            this.a.b(new jc(b.contentTimeUpdate, c.contentTimeUpdate, this.b, this.f.a()));
            a(c.requestNextAdBreak);
        }
    }

    public void start() {
        a(c.start);
    }

    public void pause() {
        a(c.pause);
    }

    public void resume() {
        a(c.resume);
    }

    public void skip() {
        a(c.skip);
    }

    public boolean isCustomPlaybackUsed() {
        return this.d.e();
    }

    private List<CompanionData> getCurrentCompanions() {
        return this.h;
    }

    private void onCompanionRendered(String str) {
        this.a.a(str, this.b);
    }

    private void b(Map<String, CompanionData> map) {
        if (map != null) {
            this.h = le.a(map.values());
        } else {
            this.h = null;
        }
    }

    public void a(jd.c cVar) {
        AdEventType adEventType = cVar.a;
        if (AnonymousClass1.b[adEventType.ordinal()] == 1) {
            a();
            if (!this.g) {
                a(c.destroy);
            }
        }
        if (adEventType == AdEventType.COMPLETED || adEventType == AdEventType.SKIPPED) {
            b(null);
        }
        super.a(cVar);
    }

    public List<Float> getAdCuePoints() {
        return this.i;
    }

    public void a(Map<String, CompanionData> map) {
        b(map);
    }

    public void clicked() {
        this.a.b(new jc(b.adsManager, c.click, this.b));
    }

    public void focusSkipButton() {
        if (getCurrentAd() != null && getCurrentAd().isSkippable()) {
            a(this.b);
        }
    }

    public void destroy() {
        if (this.f != null) {
            this.f.c();
        }
        a(c.destroy);
        this.g = true;
    }

    public /* bridge */ /* synthetic */ void removeAdEventListener(AdEventListener adEventListener) {
        super.removeAdEventListener(adEventListener);
    }

    public /* bridge */ /* synthetic */ void addAdEventListener(AdEventListener adEventListener) {
        super.addAdEventListener(adEventListener);
    }

    public /* bridge */ /* synthetic */ void removeAdErrorListener(AdErrorListener adErrorListener) {
        super.removeAdErrorListener(adErrorListener);
    }

    public /* bridge */ /* synthetic */ void addAdErrorListener(AdErrorListener adErrorListener) {
        super.addAdErrorListener(adErrorListener);
    }

    public /* bridge */ /* synthetic */ Ad getCurrentAd() {
        return super.getCurrentAd();
    }

    public /* bridge */ /* synthetic */ VideoProgressUpdate getAdProgress() {
        return super.getAdProgress();
    }

    public /* bridge */ /* synthetic */ void init(AdsRenderingSettings adsRenderingSettings) {
        super.init(adsRenderingSettings);
    }

    public /* bridge */ /* synthetic */ void init() {
        super.init();
    }
}
