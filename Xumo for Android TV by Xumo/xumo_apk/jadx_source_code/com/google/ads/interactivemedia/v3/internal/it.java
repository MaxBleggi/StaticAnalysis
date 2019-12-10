package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.os.Message;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebView.WebViewTransport;
import android.webkit.WebViewClient;
import com.google.ads.interactivemedia.v3.api.CompanionAdSlot.ClickListener;
import com.google.ads.interactivemedia.v3.impl.data.CompanionData;
import com.google.ads.interactivemedia.v3.impl.data.CompanionData.a;
import java.util.List;

/* compiled from: IMASDK */
public class it extends WebView {
    public it(final Context context, final jd jdVar, CompanionData companionData, final List<ClickListener> list) {
        super(context);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setSupportMultipleWindows(true);
        setWebChromeClient(new WebChromeClient(this) {
            public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
                WebViewTransport webViewTransport = (WebViewTransport) message.obj;
                webViewTransport.setWebView(new WebView(context));
                webViewTransport.getWebView().setWebViewClient(new WebViewClient(this) {
                    final /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                        jdVar.d(str);
                        for (ClickListener onCompanionAdClick : list) {
                            onCompanionAdClick.onCompanionAdClick();
                        }
                        return true;
                    }
                });
                message.sendToTarget();
                return true;
            }
        });
        if (companionData.type() == a.Html) {
            loadData(companionData.src(), "text/html", null);
        } else if (companionData.type() == a.IFrame) {
            loadUrl(companionData.src());
        } else {
            jdVar = String.valueOf(companionData.type());
            list = new StringBuilder(String.valueOf(jdVar).length() + 51);
            list.append("Companion type ");
            list.append(jdVar);
            list.append(" is not valid for a CompanionWebView");
            throw new IllegalArgumentException(list.toString());
        }
    }
}
