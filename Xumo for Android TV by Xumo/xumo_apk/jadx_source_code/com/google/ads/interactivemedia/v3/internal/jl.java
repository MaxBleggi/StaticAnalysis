package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import com.google.ads.interactivemedia.v3.api.Ad;
import com.google.ads.interactivemedia.v3.api.AdError;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventListener;
import com.google.ads.interactivemedia.v3.api.AdProgressInfo;
import com.google.ads.interactivemedia.v3.api.AdsRenderingSettings;
import com.google.ads.interactivemedia.v3.api.CuePoint;
import com.google.ads.interactivemedia.v3.api.StreamDisplayContainer;
import com.google.ads.interactivemedia.v3.api.StreamManager;
import com.google.ads.interactivemedia.v3.api.StreamRequest;
import com.google.ads.interactivemedia.v3.api.player.ContentProgressProvider;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.ads.interactivemedia.v3.internal.jd.c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: IMASDK */
public class jl extends ir implements StreamManager {
    private final String h;
    private List<CuePoint> i;
    private AdProgressInfo j;

    jl(String str, jd jdVar, jf jfVar, StreamRequest streamRequest, jh jhVar, Context context, String str2, boolean z) throws AdError {
        this(str, jdVar, jfVar, streamRequest.getStreamDisplayContainer(), streamRequest.getStreamDisplayContainer().getVideoStreamPlayer(), streamRequest.getManifestSuffix(), null, null, null, jhVar, context, str2, z);
    }

    public boolean isCustomPlaybackUsed() {
        return true;
    }

    jl(String str, jd jdVar, jf jfVar, StreamDisplayContainer streamDisplayContainer, ContentProgressProvider contentProgressProvider, String str2, jo joVar, iv ivVar, ib ibVar, jh jhVar, Context context, String str3, boolean z) throws AdError {
        jo joVar2 = joVar;
        super(str, jdVar, jfVar, streamDisplayContainer, ibVar, jhVar, context, z);
        this.i = new ArrayList();
        this.h = str3;
        if (joVar2 != null) {
            r9.d = joVar2;
        } else {
            r9.d = new jn(str, jfVar, jdVar, this, streamDisplayContainer, str2, context);
            ((jn) r9.d).f();
        }
        addAdErrorListener(r9.d);
        String str4 = str;
        jd jdVar2 = jdVar;
        jdVar.a(r9.d, str);
    }

    public void a(c cVar) {
        switch (cVar.a) {
            case AD_BREAK_STARTED:
                this.d.b();
                break;
            case AD_BREAK_ENDED:
                this.d.c();
                this.j = null;
                break;
            case CUEPOINTS_CHANGED:
                this.i = cVar.d;
                break;
            case AD_PROGRESS:
                this.j = cVar.e;
                break;
            default:
                break;
        }
        super.a(cVar);
    }

    public String getStreamId() {
        return this.h;
    }

    public List<CuePoint> getCuePoints() {
        return Collections.unmodifiableList(this.i);
    }

    public AdProgressInfo getAdProgressInfo() {
        return this.j;
    }

    public double getStreamTimeForContentTime(double d) {
        double d2 = d;
        double d3 = 0.0d;
        double d4 = d3;
        for (CuePoint cuePoint : this.i) {
            if (cuePoint.getStartTime() > cuePoint.getEndTime()) {
                return 0.0d;
            }
            d3 += cuePoint.getStartTime() - d4;
            if (d3 > d) {
                return d2;
            }
            d2 += cuePoint.getEndTime() - cuePoint.getStartTime();
            d4 = cuePoint.getEndTime();
        }
        return d2;
    }

    public double getContentTimeForStreamTime(double d) {
        double d2 = d;
        for (CuePoint cuePoint : this.i) {
            if (cuePoint.getStartTime() > cuePoint.getEndTime()) {
                return 0.0d;
            }
            if (d >= cuePoint.getEndTime()) {
                d2 -= cuePoint.getEndTime() - cuePoint.getStartTime();
            } else if (d < cuePoint.getEndTime() && d > cuePoint.getStartTime()) {
                d2 -= d - cuePoint.getStartTime();
            }
        }
        return d2;
    }

    public CuePoint getPreviousCuePointForStreamTime(double d) {
        CuePoint cuePoint = null;
        for (CuePoint cuePoint2 : this.i) {
            if (cuePoint2.getStartTime() < d) {
                cuePoint = cuePoint2;
            }
        }
        return cuePoint;
    }

    public void destroy() {
        a(jc.c.contentComplete);
        this.g = true;
        a();
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
