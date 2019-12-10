package com.google.ads.interactivemedia.v3.internal;

import android.annotation.SuppressLint;
import android.webkit.WebView;

/* compiled from: IMASDK */
public class z extends y {
    @SuppressLint({"SetJavaScriptEnabled"})
    public z(WebView webView) {
        if (!(webView == null || webView.getSettings().getJavaScriptEnabled())) {
            webView.getSettings().setJavaScriptEnabled(true);
        }
        a(webView);
    }
}
