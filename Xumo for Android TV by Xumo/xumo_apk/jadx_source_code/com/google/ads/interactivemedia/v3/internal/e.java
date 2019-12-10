package com.google.ads.interactivemedia.v3.internal;

import android.webkit.WebView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: IMASDK */
public final class e {
    private final i a;
    private final WebView b;
    private final List<j> c = new ArrayList();
    private final String d;
    private final String e;
    private final f f;

    private e(i iVar, WebView webView, String str, List<j> list, String str2) {
        this.a = iVar;
        this.b = webView;
        this.d = str;
        if (list != null) {
            this.c.addAll(list);
            this.f = f.NATIVE;
        } else {
            this.f = f.HTML;
        }
        this.e = str2;
    }

    public static e a(i iVar, WebView webView, String str) {
        af.a((Object) iVar, "Partner is null");
        af.a((Object) webView, "WebView is null");
        if (str != null) {
            af.a(str, 256, "CustomReferenceData is greater than 256 characters");
        }
        return new e(iVar, webView, null, null, str);
    }

    public i a() {
        return this.a;
    }

    public List<j> b() {
        return Collections.unmodifiableList(this.c);
    }

    public WebView c() {
        return this.b;
    }

    public String d() {
        return this.e;
    }

    public String e() {
        return this.d;
    }

    public f f() {
        return this.f;
    }
}
