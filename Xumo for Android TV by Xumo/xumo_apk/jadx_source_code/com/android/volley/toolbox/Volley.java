package com.android.volley.toolbox;

import android.content.Context;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import java.io.File;

public class Volley {
    private static final String DEFAULT_CACHE_DIR = "volley";

    public static com.android.volley.RequestQueue newRequestQueue(android.content.Context r3, com.android.volley.toolbox.BaseHttpStack r4) {
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
        if (r4 != 0) goto L_0x0049;
    L_0x0002:
        r4 = android.os.Build.VERSION.SDK_INT;
        r0 = 9;
        if (r4 < r0) goto L_0x0014;
    L_0x0008:
        r4 = new com.android.volley.toolbox.BasicNetwork;
        r0 = new com.android.volley.toolbox.HurlStack;
        r0.<init>();
        r4.<init>(r0);
        r0 = r4;
        goto L_0x004e;
    L_0x0014:
        r4 = "volley/0";
        r0 = r3.getPackageName();	 Catch:{ NameNotFoundException -> 0x003a }
        r1 = r3.getPackageManager();	 Catch:{ NameNotFoundException -> 0x003a }
        r2 = 0;	 Catch:{ NameNotFoundException -> 0x003a }
        r1 = r1.getPackageInfo(r0, r2);	 Catch:{ NameNotFoundException -> 0x003a }
        r2 = new java.lang.StringBuilder;	 Catch:{ NameNotFoundException -> 0x003a }
        r2.<init>();	 Catch:{ NameNotFoundException -> 0x003a }
        r2.append(r0);	 Catch:{ NameNotFoundException -> 0x003a }
        r0 = "/";	 Catch:{ NameNotFoundException -> 0x003a }
        r2.append(r0);	 Catch:{ NameNotFoundException -> 0x003a }
        r0 = r1.versionCode;	 Catch:{ NameNotFoundException -> 0x003a }
        r2.append(r0);	 Catch:{ NameNotFoundException -> 0x003a }
        r0 = r2.toString();	 Catch:{ NameNotFoundException -> 0x003a }
        r4 = r0;
    L_0x003a:
        r0 = new com.android.volley.toolbox.BasicNetwork;
        r1 = new com.android.volley.toolbox.HttpClientStack;
        r4 = android.net.http.AndroidHttpClient.newInstance(r4);
        r1.<init>(r4);
        r0.<init>(r1);
        goto L_0x004e;
    L_0x0049:
        r0 = new com.android.volley.toolbox.BasicNetwork;
        r0.<init>(r4);
    L_0x004e:
        r3 = newRequestQueue(r3, r0);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.Volley.newRequestQueue(android.content.Context, com.android.volley.toolbox.BaseHttpStack):com.android.volley.RequestQueue");
    }

    @Deprecated
    public static RequestQueue newRequestQueue(Context context, HttpStack httpStack) {
        if (httpStack == null) {
            return newRequestQueue(context, (BaseHttpStack) null);
        }
        return newRequestQueue(context, new BasicNetwork(httpStack));
    }

    private static RequestQueue newRequestQueue(Context context, Network network) {
        context = new RequestQueue(new DiskBasedCache(new File(context.getCacheDir(), DEFAULT_CACHE_DIR)), network);
        context.start();
        return context;
    }

    public static RequestQueue newRequestQueue(Context context) {
        return newRequestQueue(context, (BaseHttpStack) null);
    }
}
