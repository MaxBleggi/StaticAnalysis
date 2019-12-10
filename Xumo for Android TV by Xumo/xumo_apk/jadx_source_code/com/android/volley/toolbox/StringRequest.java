package com.android.volley.toolbox;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

public class StringRequest extends Request<String> {
    private Listener<String> mListener;
    private final Object mLock;

    public StringRequest(int i, String str, Listener<String> listener, ErrorListener errorListener) {
        super(i, str, errorListener);
        this.mLock = new Object();
        this.mListener = listener;
    }

    public StringRequest(String str, Listener<String> listener, ErrorListener errorListener) {
        this(0, str, listener, errorListener);
    }

    public void cancel() {
        super.cancel();
        synchronized (this.mLock) {
            this.mListener = null;
        }
    }

    protected void deliverResponse(String str) {
        synchronized (this.mLock) {
            Listener listener = this.mListener;
        }
        if (listener != null) {
            listener.onResponse(str);
        }
    }

    protected com.android.volley.Response<java.lang.String> parseNetworkResponse(com.android.volley.NetworkResponse r4) {
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
        r3 = this;
        r0 = new java.lang.String;	 Catch:{ UnsupportedEncodingException -> 0x000e }
        r1 = r4.data;	 Catch:{ UnsupportedEncodingException -> 0x000e }
        r2 = r4.headers;	 Catch:{ UnsupportedEncodingException -> 0x000e }
        r2 = com.android.volley.toolbox.HttpHeaderParser.parseCharset(r2);	 Catch:{ UnsupportedEncodingException -> 0x000e }
        r0.<init>(r1, r2);	 Catch:{ UnsupportedEncodingException -> 0x000e }
        goto L_0x0015;
    L_0x000e:
        r0 = new java.lang.String;
        r1 = r4.data;
        r0.<init>(r1);
    L_0x0015:
        r4 = com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders(r4);
        r4 = com.android.volley.Response.success(r0, r4);
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.StringRequest.parseNetworkResponse(com.android.volley.NetworkResponse):com.android.volley.Response<java.lang.String>");
    }
}
