package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;
import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdError;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorCode;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorType;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.ads.interactivemedia.v3.impl.data.b;
import com.google.ads.interactivemedia.v3.impl.data.m;
import com.google.ads.interactivemedia.v3.internal.jc.c;

/* compiled from: IMASDK */
public class jg implements jo {
    private final VideoAdPlayer a;
    private final ih b;
    private final ij c;
    private final il d;
    private final if e;
    private boolean f;
    private boolean g;

    public jg(String str, jf jfVar, jd jdVar, ij ijVar, AdDisplayContainer adDisplayContainer, Context context) throws AdError {
        this(str, jfVar, jdVar, ijVar, adDisplayContainer, null, null, context);
    }

    public void b() {
    }

    public void c() {
    }

    public jg(String str, jf jfVar, jd jdVar, ij ijVar, AdDisplayContainer adDisplayContainer, ih ihVar, il ilVar, Context context) throws AdError {
        ih ihVar2 = ihVar;
        il ilVar2 = ilVar;
        this.g = false;
        if (adDisplayContainer.getPlayer() != null) {
            r0.a = adDisplayContainer.getPlayer();
            r0.f = true;
            Context context2 = context;
        } else if (VERSION.SDK_INT >= 16) {
            r0.a = new iy(context, adDisplayContainer.getAdContainer());
            r0.f = false;
        } else {
            throw new AdError(AdErrorType.LOAD, AdErrorCode.INVALID_ARGUMENTS, "Ad Player was not provided. SDK-owned ad playback requires API 16+");
        }
        if (ihVar2 != null) {
            r0.b = ihVar2;
        } else {
            r0.b = new ih(r0.a, jfVar.a());
        }
        r0.c = ijVar;
        if (ilVar2 != null) {
            r0.d = ilVar2;
        } else {
            r0.d = new il(str, jfVar, jdVar, adDisplayContainer, context);
        }
        String str2 = str;
        jd jdVar2 = jdVar;
        r0.e = new if(jdVar, str, r0.b, r0.a);
    }

    public void a(boolean z) {
        this.b.a(this.d);
        this.b.a(this.e);
        this.g = z;
    }

    public VideoProgressUpdate getAdProgress() {
        return this.a.getAdProgress();
    }

    public boolean a(c cVar, m mVar) {
        switch (cVar) {
            case play:
                this.a.playAd();
                break;
            case pause:
                this.a.pauseAd();
                break;
            case resume:
                this.a.resumeAd();
                break;
            case load:
                if (mVar != null && mVar.videoUrl != null) {
                    this.a.loadAd(mVar.videoUrl);
                    break;
                }
                this.c.a((AdErrorEvent) new id(new AdError(AdErrorType.LOAD, AdErrorCode.INTERNAL_ERROR, "Load message must contain video url.")));
                break;
                break;
            case startTracking:
                this.b.b();
                break;
            case stopTracking:
                this.b.c();
                break;
            default:
                return null;
        }
        return true;
    }

    public boolean b(c cVar, m mVar) {
        switch (cVar) {
            case showVideo:
                if (this.f == null) {
                    ((jj) this.a).a();
                }
                this.a.addCallback(this.e);
                break;
            case hide:
                if (this.f == null) {
                    ((jj) this.a).b();
                }
                this.a.removeCallback(this.e);
                break;
            default:
                return null;
        }
        return true;
    }

    public void a(b bVar) {
        if (this.g && bVar.canDisableUi()) {
            bVar.setUiDisabled(true);
            return;
        }
        bVar.setUiDisabled(false);
        this.d.a(bVar);
    }

    public void a() {
        this.a.stopAd();
        this.d.a();
    }

    public void onAdError(AdErrorEvent adErrorEvent) {
        this.d.a();
    }

    public void d() {
        Log.d("SDK_DEBUG", "Destroying NativeVideoDisplay");
        this.b.c();
        this.b.b(this.d);
        this.b.b(this.e);
        this.d.b();
        this.a.removeCallback(this.e);
        if (this.a instanceof jj) {
            ((jj) this.a).c();
        }
    }

    public boolean e() {
        return this.f;
    }
}
