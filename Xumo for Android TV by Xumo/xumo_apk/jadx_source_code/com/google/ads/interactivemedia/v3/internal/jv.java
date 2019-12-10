package com.google.ads.interactivemedia.v3.internal;

import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

/* compiled from: IMASDK */
public class jv {
    private final ViewGroup a;
    private final WebView b;

    public jv(jd jdVar, ViewGroup viewGroup) {
        this.a = viewGroup;
        this.b = jdVar.b();
    }

    public void a() {
        ViewGroup viewGroup = (ViewGroup) this.b.getParent();
        if (viewGroup != null) {
            this.b.setVisibility(4);
            viewGroup.removeView(this.b);
        }
        this.a.addView(this.b, new LayoutParams(-1, -1));
        this.b.setVisibility(0);
    }

    public void b() {
        this.b.setVisibility(4);
    }

    public void c() {
        b();
        this.a.removeView(this.b);
    }
}
