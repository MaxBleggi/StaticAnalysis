package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import com.google.ads.interactivemedia.v3.api.Ad;
import com.google.ads.interactivemedia.v3.api.AdError;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorCode;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorType;
import com.google.ads.interactivemedia.v3.api.BaseDisplayContainer;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.ads.interactivemedia.v3.internal.jc.c;
import com.google.ads.interactivemedia.v3.internal.ji.b;

/* compiled from: IMASDK */
public class il implements b {
    private final String a;
    private jd b;
    private BaseDisplayContainer c;
    private ju d;
    private jv e;
    private Context f;
    private com.google.ads.interactivemedia.v3.impl.data.b g;
    private a h;
    private jf i;

    /* compiled from: IMASDK */
    private class a implements com.google.ads.interactivemedia.v3.internal.ju.a {
        final /* synthetic */ il a;

        private a(il ilVar) {
            this.a = ilVar;
        }

        public void a() {
            this.a.b.b(new jc(jc.b.videoDisplay, c.skip, this.a.a));
        }

        public void b() {
            this.a.b.b(new jc(jc.b.videoDisplay, c.skipShown, this.a.a));
        }

        public void c() {
            this.a.b.b(new jc(jc.b.videoDisplay, c.click, this.a.a));
        }
    }

    public il(String str, jf jfVar, jd jdVar, BaseDisplayContainer baseDisplayContainer, Context context) throws AdError {
        if (jfVar.b() != com.google.ads.interactivemedia.v3.internal.jc.a.nativeUi) {
            if (jfVar.b() != com.google.ads.interactivemedia.v3.internal.jc.a.webViewUi) {
                AdErrorType adErrorType = AdErrorType.PLAY;
                AdErrorCode adErrorCode = AdErrorCode.INTERNAL_ERROR;
                jfVar = String.valueOf(jfVar.b());
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(jfVar).length() + 50);
                stringBuilder.append("AdsManagerUi is used for an unsupported UI style: ");
                stringBuilder.append(jfVar);
                throw new AdError(adErrorType, adErrorCode, stringBuilder.toString());
            }
        }
        this.i = jfVar;
        this.b = jdVar;
        this.f = context;
        this.a = str;
        this.c = baseDisplayContainer;
        this.h = new a();
    }

    public void a(com.google.ads.interactivemedia.v3.impl.data.b bVar) {
        if (this.g != null) {
            b();
        }
        if (bVar.isLinear()) {
            this.g = bVar;
            a((Ad) bVar);
        }
    }

    public void a() {
        if (this.d != null) {
            this.d.b();
            this.c.getAdContainer().removeView(this.d.a());
            this.d = null;
            this.b.a(this.a);
        } else if (this.e != null) {
            this.e.b();
        }
        this.g = null;
    }

    public void b() {
        a();
        if (this.e != null) {
            this.e.c();
        }
        this.e = null;
    }

    private void a(Ad ad) {
        if (this.i.b() == com.google.ads.interactivemedia.v3.internal.jc.a.nativeUi) {
            this.d = new ju(this.f, jt.a(ad), this.b, this.a);
            this.b.a(this.d, this.a);
            this.d.a(this.h);
            this.c.getAdContainer().addView(this.d.a());
            this.d.a(ad);
            return;
        }
        this.e = new jv(this.b, this.c.getAdContainer());
        this.e.a();
    }

    public void a(VideoProgressUpdate videoProgressUpdate) {
        if (this.d != null) {
            this.d.a(videoProgressUpdate);
        }
    }
}
