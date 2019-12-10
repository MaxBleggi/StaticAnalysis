package com.google.ads.interactivemedia.v3.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint({"SetJavaScriptEnabled", "NewApi"})
/* compiled from: IMASDK */
public class je {
    private final a a;
    private final WebView b;

    /* compiled from: IMASDK */
    public interface a {
        void a(jc jcVar);
    }

    public je(Context context, a aVar) {
        this(new WebView(context), aVar);
    }

    public je(WebView webView, a aVar) {
        this.a = aVar;
        this.b = webView;
        this.b.setBackgroundColor(0);
        if (VERSION.SDK_INT == 15) {
            this.b.setLayerType(1, null);
        }
        if (VERSION.SDK_INT > 19) {
            webView.getSettings().setMixedContentMode(0);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(this) {
            final /* synthetic */ je a;

            {
                this.a = r1;
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (str.startsWith("gmsg://") == null) {
                    return null;
                }
                this.a.b(str);
                return true;
            }

            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                webView = "Started ";
                str = String.valueOf(str);
                je.c(str.length() != null ? webView.concat(str) : new String(webView));
            }

            public void onPageFinished(WebView webView, String str) {
                webView = "Finished ";
                str = String.valueOf(str);
                je.c(str.length() != 0 ? webView.concat(str) : new String(webView));
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 20) + String.valueOf(str2).length());
                stringBuilder.append("Error: ");
                stringBuilder.append(i);
                stringBuilder.append(" ");
                stringBuilder.append(str);
                stringBuilder.append(" ");
                stringBuilder.append(str2);
                je.c(stringBuilder.toString());
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        jw.a(webView.getSettings());
    }

    public WebView a() {
        return this.b;
    }

    public void a(String str) {
        this.b.loadUrl(str);
    }

    @android.annotation.TargetApi(19)
    public void a(com.google.ads.interactivemedia.v3.internal.jc r3) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r2 = this;
        r0 = r3.e();
        r1 = 1;
        a(r1, r3, r0);
        r3 = android.os.Build.VERSION.SDK_INT;
        r1 = 19;
        if (r3 < r1) goto L_0x001b;
    L_0x000e:
        r3 = r2.b;	 Catch:{ IllegalStateException -> 0x0015 }
        r1 = 0;	 Catch:{ IllegalStateException -> 0x0015 }
        r3.evaluateJavascript(r0, r1);	 Catch:{ IllegalStateException -> 0x0015 }
        goto L_0x0020;
    L_0x0015:
        r3 = r2.b;
        r3.loadUrl(r0);
        goto L_0x0020;
    L_0x001b:
        r3 = r2.b;
        r3.loadUrl(r0);
    L_0x0020:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.je.a(com.google.ads.interactivemedia.v3.internal.jc):void");
    }

    protected void b(java.lang.String r5) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = this;
        r0 = com.google.ads.interactivemedia.v3.internal.jc.a(r5);	 Catch:{ IllegalArgumentException -> 0x002b, Exception -> 0x000e }
        r1 = 0;	 Catch:{ IllegalArgumentException -> 0x002b, Exception -> 0x000e }
        a(r1, r0, r5);	 Catch:{ IllegalArgumentException -> 0x002b, Exception -> 0x000e }
        r5 = r4.a;
        r5.a(r0);
        return;
    L_0x000e:
        r0 = move-exception;
        r1 = "IMASDK";
        r2 = "An internal error occured parsing message from javascript.  Message to be parsed: ";
        r5 = java.lang.String.valueOf(r5);
        r3 = r5.length();
        if (r3 == 0) goto L_0x0022;
    L_0x001d:
        r5 = r2.concat(r5);
        goto L_0x0027;
    L_0x0022:
        r5 = new java.lang.String;
        r5.<init>(r2);
    L_0x0027:
        android.util.Log.e(r1, r5, r0);
        return;
    L_0x002b:
        r0 = "IMASDK";
        r1 = "Invalid internal message, ignoring. Please make sure the Google IMA SDK library is up to date. Message: ";
        r5 = java.lang.String.valueOf(r5);
        r2 = r5.length();
        if (r2 == 0) goto L_0x003e;
    L_0x0039:
        r5 = r1.concat(r5);
        goto L_0x0043;
    L_0x003e:
        r5 = new java.lang.String;
        r5.<init>(r1);
    L_0x0043:
        android.util.Log.w(r0, r5);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.je.b(java.lang.String):void");
    }

    static final void a(boolean z, jc jcVar, String str) {
        z = z ? "Sending javascript msg: " : "Received msg: ";
        StringBuilder stringBuilder;
        if (com.google.ads.interactivemedia.v3.internal.iz.a.a(com.google.ads.interactivemedia.v3.internal.iz.a.VERBOSE)) {
            jcVar = String.valueOf(jcVar);
            stringBuilder = new StringBuilder(((String.valueOf(z).length() + 7) + String.valueOf(jcVar).length()) + String.valueOf(str).length());
            stringBuilder.append(z);
            stringBuilder.append(jcVar);
            stringBuilder.append("; URL: ");
            stringBuilder.append(str);
            Log.d("IMASDK", stringBuilder.toString());
        } else if (com.google.ads.interactivemedia.v3.internal.iz.a.a(com.google.ads.interactivemedia.v3.internal.iz.a.ABRIDGED) != null) {
            String name = jcVar.a().name();
            jcVar = jcVar.b().name();
            stringBuilder = new StringBuilder(((String.valueOf(z).length() + 17) + String.valueOf(name).length()) + String.valueOf(jcVar).length());
            stringBuilder.append(z);
            stringBuilder.append("Channel: ");
            stringBuilder.append(name);
            stringBuilder.append("; type: ");
            stringBuilder.append(jcVar);
            Log.d("IMASDK", stringBuilder.toString());
        }
    }

    static final void c(String str) {
        if (com.google.ads.interactivemedia.v3.internal.iz.a.a(com.google.ads.interactivemedia.v3.internal.iz.a.LIFECYCLE)) {
            Log.d("IMASDK", str);
        }
    }
}
