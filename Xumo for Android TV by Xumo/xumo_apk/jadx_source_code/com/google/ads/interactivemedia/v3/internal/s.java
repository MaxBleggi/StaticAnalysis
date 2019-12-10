package com.google.ads.interactivemedia.v3.internal;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.WebView;
import org.json.JSONObject;

/* compiled from: IMASDK */
public class s {
    private static s a = new s();

    private s() {
    }

    public static s a() {
        return a;
    }

    public boolean a(WebView webView, String str) {
        if (webView == null || TextUtils.isEmpty(str)) {
            return null;
        }
        String str2 = "javascript: ";
        str = String.valueOf(str);
        webView.loadUrl(str.length() != 0 ? str2.concat(str) : new String(str2));
        return true;
    }

    public void b(WebView webView, String str) {
        if (str != null) {
            a(webView, "var script=document.createElement('script');script.setAttribute(\"type\",\"text/javascript\");script.setAttribute(\"src\",\"%SCRIPT_SRC%\");document.body.appendChild(script);".replace("%SCRIPT_SRC%", str));
        }
    }

    public void a(WebView webView, JSONObject jSONObject) {
        a(webView, "init", jSONObject);
    }

    public void a(WebView webView, String str, JSONObject jSONObject, JSONObject jSONObject2) {
        a(webView, "startSession", str, jSONObject, jSONObject2);
    }

    public void a(WebView webView) {
        a(webView, "finishSession", new Object[0]);
    }

    public void c(WebView webView, String str) {
        a(webView, "setNativeViewHierarchy", str);
    }

    public void d(WebView webView, String str) {
        a(webView, "setState", str);
    }

    public void a(WebView webView, float f) {
        a(webView, "setDeviceVolume", Float.valueOf(f));
    }

    void a(WebView webView, String str, Object... objArr) {
        if (webView != null) {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("javascript: if(window.omidBridge!==undefined){omidBridge.");
            stringBuilder.append(str);
            stringBuilder.append("(");
            a(stringBuilder, objArr);
            stringBuilder.append(")}");
            a(webView, stringBuilder);
            return;
        }
        webView = String.valueOf("The WebView is null for ");
        str = String.valueOf(str);
        ad.a(str.length() != null ? webView.concat(str) : new String(webView));
    }

    void a(final WebView webView, StringBuilder stringBuilder) {
        stringBuilder = stringBuilder.toString();
        Handler handler = webView.getHandler();
        if (handler != null) {
            if (Looper.myLooper() != handler.getLooper()) {
                handler.post(new Runnable(this) {
                    public void run() {
                        webView.loadUrl(stringBuilder);
                    }
                });
                return;
            }
        }
        webView.loadUrl(stringBuilder);
    }

    void a(StringBuilder stringBuilder, Object[] objArr) {
        if (objArr != null && objArr.length > 0) {
            for (Object obj : objArr) {
                if (obj == null) {
                    stringBuilder.append('\"');
                    stringBuilder.append('\"');
                } else if (obj instanceof String) {
                    String obj2 = obj.toString();
                    if (obj2.startsWith("{")) {
                        stringBuilder.append(obj2);
                    } else {
                        stringBuilder.append('\"');
                        stringBuilder.append(obj2);
                        stringBuilder.append('\"');
                    }
                } else {
                    stringBuilder.append(obj);
                }
                stringBuilder.append(",");
            }
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
    }
}
