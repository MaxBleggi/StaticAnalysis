package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.util.Log;
import com.google.ads.interactivemedia.v3.api.Ad;
import com.google.ads.interactivemedia.v3.api.AdError;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorCode;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorType;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
import com.google.ads.interactivemedia.v3.api.AdEvent;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventListener;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventType;
import com.google.ads.interactivemedia.v3.api.AdsRenderingSettings;
import com.google.ads.interactivemedia.v3.api.BaseDisplayContainer;
import com.google.ads.interactivemedia.v3.api.BaseManager;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.ads.interactivemedia.v3.impl.data.CompanionData;
import com.google.ads.interactivemedia.v3.impl.data.b;
import com.google.ads.interactivemedia.v3.internal.jc.c;
import com.google.ads.interactivemedia.v3.internal.jd.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: IMASDK */
abstract class ir implements BaseManager, d {
    protected final jd a;
    protected final String b;
    protected AdsRenderingSettings c;
    protected jo d;
    protected iu e;
    protected iv f;
    protected boolean g = null;
    private final List<AdEventListener> h = new ArrayList(1);
    private final ix i = new ix();
    private final Context j;
    private b k;
    private ib l;
    private jh m;

    protected ir(String str, jd jdVar, jf jfVar, BaseDisplayContainer baseDisplayContainer, ib ibVar, jh jhVar, Context context, boolean z) throws AdError {
        this.b = str;
        this.a = jdVar;
        this.j = context;
        this.c = new im();
        if (ibVar != null) {
            this.l = ibVar;
        } else {
            this.l = new ib(str, jdVar, baseDisplayContainer.getAdContainer());
        }
        this.l.a(z);
        if (jhVar != null) {
            addAdEventListener(jhVar);
            addAdErrorListener(jhVar);
            jhVar.a(baseDisplayContainer.getAdContainer());
            this.m = jhVar;
        }
        jdVar.a((d) this, str);
        this.l.a();
    }

    public void a(Map<String, CompanionData> map) {
    }

    public abstract boolean isCustomPlaybackUsed();

    public void init() {
        init(null);
    }

    public void init(AdsRenderingSettings adsRenderingSettings) {
        if (adsRenderingSettings == null) {
            adsRenderingSettings = this.c;
        }
        this.c = adsRenderingSettings;
        adsRenderingSettings = new HashMap();
        adsRenderingSettings.put("adsRenderingSettings", this.c);
        if (this.f != null) {
            VideoProgressUpdate a = this.f.a();
            if (!a.equals(VideoProgressUpdate.VIDEO_TIME_NOT_READY)) {
                double currentTime = (double) a.getCurrentTime();
                StringBuilder stringBuilder = new StringBuilder(68);
                stringBuilder.append("AdsManager.init -> Setting contentStartTime ");
                stringBuilder.append(currentTime);
                Log.d("IMASDK", stringBuilder.toString());
                adsRenderingSettings.put("contentStartTime", Double.valueOf(currentTime));
            }
        }
        if (!isCustomPlaybackUsed()) {
            adsRenderingSettings.put("sdkOwnedPlayer", Boolean.valueOf(true));
        }
        this.d.a(this.c.getDisableUi());
        this.a.a(this.c);
        this.a.b(new jc(jc.b.adsManager, c.init, this.b, adsRenderingSettings));
    }

    public VideoProgressUpdate getAdProgress() {
        if (this.g) {
            return VideoProgressUpdate.VIDEO_TIME_NOT_READY;
        }
        return this.d.getAdProgress();
    }

    public Ad getCurrentAd() {
        return this.k;
    }

    public void addAdErrorListener(AdErrorListener adErrorListener) {
        this.i.a(adErrorListener);
    }

    public void removeAdErrorListener(AdErrorListener adErrorListener) {
        this.i.b(adErrorListener);
    }

    public void addAdEventListener(AdEventListener adEventListener) {
        this.h.add(adEventListener);
    }

    public void removeAdEventListener(AdEventListener adEventListener) {
        this.h.remove(adEventListener);
    }

    protected void a() {
        this.d.d();
        if (this.m != null && this.m.e()) {
            Log.d("IMASDK", "OMID ad session ended on BaseManager destroy.");
        }
        if (this.f != null) {
            this.f.c();
        }
        this.l.b();
        this.a.c(this.b);
        this.k = null;
    }

    protected void a(c cVar) {
        this.a.b(new jc(jc.b.adsManager, cVar, this.b));
    }

    private void a(AdEventType adEventType) {
        a(adEventType, null);
    }

    private void a(AdEventType adEventType, Map<String, String> map) {
        AdEvent ieVar = new ie(adEventType, this.k, map);
        for (AdEventListener onAdEvent : this.h) {
            onAdEvent.onAdEvent(ieVar);
        }
    }

    void a(AdErrorEvent adErrorEvent) {
        this.i.a(adErrorEvent);
    }

    void a(b bVar) {
        this.k = bVar;
    }

    public void a(jd.c cVar) {
        AdEventType adEventType = cVar.a;
        b bVar = cVar.b;
        switch (adEventType) {
            case LOADED:
                a(bVar);
                break;
            case STARTED:
                if (bVar != null) {
                    a(bVar);
                }
                this.d.a(bVar);
                break;
            case COMPLETED:
            case SKIPPED:
                this.d.a();
                break;
            case CONTENT_PAUSE_REQUESTED:
                if (this.f != null) {
                    this.f.c();
                }
                this.l.c();
                break;
            case CONTENT_RESUME_REQUESTED:
                if (this.f != null) {
                    this.f.b();
                }
                this.l.d();
                break;
            case CLICKED:
                String clickThruUrl = bVar.getClickThruUrl();
                if (!jx.a(clickThruUrl)) {
                    this.a.d(clickThruUrl);
                    break;
                }
                break;
            case ICON_TAPPED:
                if (!jx.a(cVar.f)) {
                    this.a.d(cVar.f);
                    break;
                }
                break;
            case SKIPPABLE_STATE_CHANGED:
                if (this.c.getFocusSkipButtonWhenAvailable()) {
                    a(this.b);
                    break;
                }
                break;
            default:
                break;
        }
        if (cVar.c != null) {
            a(adEventType, cVar.c);
        } else {
            a(adEventType);
        }
        if (adEventType == AdEventType.COMPLETED || adEventType == AdEventType.SKIPPED) {
            a((b) null);
        }
    }

    protected void a(String str) {
        if (jw.a(this.j, this.a.d())) {
            this.a.b().requestFocus();
            this.a.b(new jc(jc.b.videoDisplay, c.focusSkipButton, str));
        }
    }

    public void a(AdErrorType adErrorType, int i, String str) {
        a(new id(new AdError(adErrorType, i, str)));
    }

    public void a(AdErrorType adErrorType, AdErrorCode adErrorCode, String str) {
        a(new id(new AdError(adErrorType, adErrorCode, str)));
    }
}
