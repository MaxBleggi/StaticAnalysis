package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
import com.google.ads.interactivemedia.v3.api.AdEvent;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventListener;

/* compiled from: IMASDK */
public class jh implements AdErrorListener, AdEventListener {
    private static boolean e = false;
    private static jh g;
    private final WebView a;
    private final a b;
    private final Context c;
    private View d;
    private boolean f;
    private c h;

    /* compiled from: IMASDK */
    protected static class a {
        protected a() {
        }

        protected void a(String str, Context context) {
            a.a(str, context);
        }

        protected String a() {
            return a.a();
        }

        protected d a(h hVar, h hVar2) {
            return d.a(hVar, hVar2);
        }

        protected i a(String str, String str2) {
            return i.a(str, str2);
        }

        protected e a(i iVar, WebView webView, String str) {
            return e.a(iVar, webView, str);
        }

        protected c a(d dVar, e eVar) {
            return c.a(dVar, eVar);
        }
    }

    jh(WebView webView, Context context) {
        this(webView, context, new a());
    }

    jh(WebView webView, Context context, a aVar) {
        this.f = false;
        this.a = webView;
        this.b = aVar;
        this.c = context;
        g = this;
        if (e != null) {
            aVar.a(aVar.a(), context);
        }
    }

    public static void a() {
        if (!(g == null || e)) {
            g.b.a(g.b.a(), g.c);
        }
        e = true;
    }

    public static void b() {
        e = false;
    }

    public void a(boolean z) {
        this.f = z;
    }

    public void c() {
        if (e && this.h == null) {
            if (this.d != null) {
                d a = this.b.a(h.JAVASCRIPT, h.JAVASCRIPT);
                i a2 = this.b.a("Google1", this.b.a());
                a aVar = this.b;
                WebView webView = this.a;
                String str = this.f ? "true" : "false";
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 7);
                stringBuilder.append("{ssai:");
                stringBuilder.append(str);
                stringBuilder.append("}");
                this.h = this.b.a(a, aVar.a(a2, webView, stringBuilder.toString()));
                this.h.a(this.d);
                this.h.a();
            }
        }
    }

    public static String d() {
        return new a().a();
    }

    public void a(View view) {
        this.d = view;
    }

    public boolean e() {
        if (e) {
            if (this.h != null) {
                this.h.b();
                this.h = null;
                return true;
            }
        }
        return false;
    }

    public void onAdError(AdErrorEvent adErrorEvent) {
        if (e != null) {
            if (this.h != null) {
                this.h.b();
                this.h = null;
            }
        }
    }

    public void onAdEvent(AdEvent adEvent) {
        if (e) {
            switch (adEvent.getType()) {
                case LOADED:
                case STARTED:
                    c();
                    return;
                case COMPLETED:
                case SKIPPED:
                    e();
                    return;
                default:
                    return;
            }
        }
    }
}
