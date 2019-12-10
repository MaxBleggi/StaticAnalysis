package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Cache.Entry;
import com.android.volley.Header;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class BasicNetwork implements Network {
    protected static final boolean DEBUG = VolleyLog.DEBUG;
    private static final int DEFAULT_POOL_SIZE = 4096;
    private static final int SLOW_REQUEST_THRESHOLD_MS = 3000;
    private final BaseHttpStack mBaseHttpStack;
    @Deprecated
    protected final HttpStack mHttpStack;
    protected final ByteArrayPool mPool;

    @Deprecated
    public BasicNetwork(HttpStack httpStack) {
        this(httpStack, new ByteArrayPool(4096));
    }

    @Deprecated
    public BasicNetwork(HttpStack httpStack, ByteArrayPool byteArrayPool) {
        this.mHttpStack = httpStack;
        this.mBaseHttpStack = new AdaptedHttpStack(httpStack);
        this.mPool = byteArrayPool;
    }

    public BasicNetwork(BaseHttpStack baseHttpStack) {
        this(baseHttpStack, new ByteArrayPool(4096));
    }

    public BasicNetwork(BaseHttpStack baseHttpStack, ByteArrayPool byteArrayPool) {
        this.mBaseHttpStack = baseHttpStack;
        this.mHttpStack = baseHttpStack;
        this.mPool = byteArrayPool;
    }

    public com.android.volley.NetworkResponse performRequest(com.android.volley.Request<?> r29) throws com.android.volley.VolleyError {
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
        r28 = this;
        r7 = r28;
        r8 = r29;
        r9 = android.os.SystemClock.elapsedRealtime();
    L_0x0008:
        r1 = java.util.Collections.emptyList();
        r11 = 0;
        r2 = 0;
        r0 = r29.getCacheEntry();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00be }
        r0 = r7.getCacheHeaders(r0);	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00be }
        r3 = r7.mBaseHttpStack;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00be }
        r12 = r3.executeRequest(r8, r0);	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00be }
        r14 = r12.getStatusCode();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00b9 }
        r13 = r12.getHeaders();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00b9 }
        r0 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        if (r14 != r0) goto L_0x0065;
    L_0x0028:
        r0 = r29.getCacheEntry();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        if (r0 != 0) goto L_0x0044;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
    L_0x002e:
        r0 = new com.android.volley.NetworkResponse;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r16 = 304; // 0x130 float:4.26E-43 double:1.5E-321;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r17 = 0;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r18 = 1;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r3 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r1 = 0;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r19 = r3 - r9;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r15 = r0;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r21 = r13;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r15.<init>(r16, r17, r18, r19, r21);	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        return r0;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
    L_0x0044:
        r27 = combineHeaders(r13, r0);	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r1 = new com.android.volley.NetworkResponse;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r22 = 304; // 0x130 float:4.26E-43 double:1.5E-321;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r0 = r0.data;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r24 = 1;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r3 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r5 = 0;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r25 = r3 - r9;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r21 = r1;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r23 = r0;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r21.<init>(r22, r23, r24, r25, r27);	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        return r1;
    L_0x005f:
        r0 = move-exception;
        r15 = r2;
        r19 = r13;
        goto L_0x00c3;
    L_0x0065:
        r0 = r12.getContent();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00b6 }
        if (r0 == 0) goto L_0x0074;
    L_0x006b:
        r1 = r12.getContentLength();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        r0 = r7.inputStreamToBytes(r0, r1);	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x005f }
        goto L_0x0076;
    L_0x0074:
        r0 = new byte[r11];	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00b6 }
    L_0x0076:
        r20 = r0;
        r0 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r2 = 0;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r2 = r0 - r9;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r1 = r28;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r4 = r29;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r5 = r20;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r6 = r14;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r1.logSlowRequests(r2, r4, r5, r6);	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r0 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        if (r14 < r0) goto L_0x00a6;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
    L_0x008d:
        r0 = 299; // 0x12b float:4.19E-43 double:1.477E-321;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        if (r14 > r0) goto L_0x00a6;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
    L_0x0091:
        r0 = new com.android.volley.NetworkResponse;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r16 = 0;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r1 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00af }
        r3 = 0;
        r17 = r1 - r9;
        r1 = r13;
        r13 = r0;
        r15 = r20;
        r19 = r1;
        r13.<init>(r14, r15, r16, r17, r19);	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00ad }
        return r0;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00ad }
    L_0x00a6:
        r1 = r13;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00ad }
        r0 = new java.io.IOException;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00ad }
        r0.<init>();	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00ad }
        throw r0;	 Catch:{ SocketTimeoutException -> 0x0167, MalformedURLException -> 0x014b, IOException -> 0x00ad }
    L_0x00ad:
        r0 = move-exception;
        goto L_0x00b1;
    L_0x00af:
        r0 = move-exception;
        r1 = r13;
    L_0x00b1:
        r19 = r1;
        r15 = r20;
        goto L_0x00c3;
    L_0x00b6:
        r0 = move-exception;
        r1 = r13;
        goto L_0x00ba;
    L_0x00b9:
        r0 = move-exception;
    L_0x00ba:
        r19 = r1;
        r15 = r2;
        goto L_0x00c3;
    L_0x00be:
        r0 = move-exception;
        r19 = r1;
        r12 = r2;
        r15 = r12;
    L_0x00c3:
        if (r12 == 0) goto L_0x0145;
    L_0x00c5:
        r0 = r12.getStatusCode();
        r1 = "Unexpected response code %d for %s";
        r2 = 2;
        r2 = new java.lang.Object[r2];
        r3 = java.lang.Integer.valueOf(r0);
        r2[r11] = r3;
        r3 = 1;
        r4 = r29.getUrl();
        r2[r3] = r4;
        com.android.volley.VolleyLog.e(r1, r2);
        if (r15 == 0) goto L_0x0139;
    L_0x00e0:
        r1 = new com.android.volley.NetworkResponse;
        r16 = 0;
        r2 = android.os.SystemClock.elapsedRealtime();
        r17 = r2 - r9;
        r13 = r1;
        r14 = r0;
        r13.<init>(r14, r15, r16, r17, r19);
        r2 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        if (r0 == r2) goto L_0x012d;
    L_0x00f3:
        r2 = 403; // 0x193 float:5.65E-43 double:1.99E-321;
        if (r0 != r2) goto L_0x00f8;
    L_0x00f7:
        goto L_0x012d;
    L_0x00f8:
        r2 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r0 < r2) goto L_0x0107;
    L_0x00fc:
        r2 = 499; // 0x1f3 float:6.99E-43 double:2.465E-321;
        if (r0 <= r2) goto L_0x0101;
    L_0x0100:
        goto L_0x0107;
    L_0x0101:
        r0 = new com.android.volley.ClientError;
        r0.<init>(r1);
        throw r0;
    L_0x0107:
        r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        if (r0 < r2) goto L_0x0127;
    L_0x010b:
        r2 = 599; // 0x257 float:8.4E-43 double:2.96E-321;
        if (r0 > r2) goto L_0x0127;
    L_0x010f:
        r0 = r29.shouldRetryServerErrors();
        if (r0 == 0) goto L_0x0121;
    L_0x0115:
        r0 = "server";
        r2 = new com.android.volley.ServerError;
        r2.<init>(r1);
        attemptRetryOnException(r0, r8, r2);
        goto L_0x0008;
    L_0x0121:
        r0 = new com.android.volley.ServerError;
        r0.<init>(r1);
        throw r0;
    L_0x0127:
        r0 = new com.android.volley.ServerError;
        r0.<init>(r1);
        throw r0;
    L_0x012d:
        r0 = "auth";
        r2 = new com.android.volley.AuthFailureError;
        r2.<init>(r1);
        attemptRetryOnException(r0, r8, r2);
        goto L_0x0008;
    L_0x0139:
        r0 = "network";
        r1 = new com.android.volley.NetworkError;
        r1.<init>();
        attemptRetryOnException(r0, r8, r1);
        goto L_0x0008;
    L_0x0145:
        r1 = new com.android.volley.NoConnectionError;
        r1.<init>(r0);
        throw r1;
    L_0x014b:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Bad URL ";
        r2.append(r3);
        r3 = r29.getUrl();
        r2.append(r3);
        r2 = r2.toString();
        r1.<init>(r2, r0);
        throw r1;
    L_0x0167:
        r0 = "socket";
        r1 = new com.android.volley.TimeoutError;
        r1.<init>();
        attemptRetryOnException(r0, r8, r1);
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.BasicNetwork.performRequest(com.android.volley.Request):com.android.volley.NetworkResponse");
    }

    private void logSlowRequests(long j, Request<?> request, byte[] bArr, int i) {
        if (DEBUG || j > 3000) {
            String str = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]";
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(j);
            objArr[2] = bArr != null ? Integer.valueOf(bArr.length) : "null";
            objArr[3] = Integer.valueOf(i);
            objArr[4] = Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount());
            VolleyLog.d(str, objArr);
        }
    }

    private static void attemptRetryOnException(String str, Request<?> request, VolleyError volleyError) throws VolleyError {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int timeoutMs = request.getTimeoutMs();
        try {
            retryPolicy.retry(volleyError);
            request.addMarker(String.format("%s-retry [timeout=%s]", new Object[]{str, Integer.valueOf(timeoutMs)}));
        } catch (VolleyError volleyError2) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{str, Integer.valueOf(timeoutMs)}));
            throw volleyError2;
        }
    }

    private Map<String, String> getCacheHeaders(Entry entry) {
        if (entry == null) {
            return Collections.emptyMap();
        }
        Map<String, String> hashMap = new HashMap();
        if (entry.etag != null) {
            hashMap.put(HttpRequest.HEADER_IF_NONE_MATCH, entry.etag);
        }
        if (entry.lastModified > 0) {
            hashMap.put("If-Modified-Since", HttpHeaderParser.formatEpochAsRfc1123(entry.lastModified));
        }
        return hashMap;
    }

    protected void logError(String str, String str2, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        VolleyLog.v("HTTP ERROR(%s) %d ms to fetch %s", str, Long.valueOf(elapsedRealtime - j), str2);
    }

    private byte[] inputStreamToBytes(java.io.InputStream r6, int r7) throws java.io.IOException, com.android.volley.ServerError {
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
        r5 = this;
        r0 = new com.android.volley.toolbox.PoolingByteArrayOutputStream;
        r1 = r5.mPool;
        r0.<init>(r1, r7);
        r7 = 0;
        r1 = 0;
        if (r6 == 0) goto L_0x003d;
    L_0x000b:
        r2 = r5.mPool;	 Catch:{ all -> 0x0043 }
        r3 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;	 Catch:{ all -> 0x0043 }
        r2 = r2.getBuf(r3);	 Catch:{ all -> 0x0043 }
    L_0x0013:
        r1 = r6.read(r2);	 Catch:{ all -> 0x0038 }
        r3 = -1;	 Catch:{ all -> 0x0038 }
        if (r1 == r3) goto L_0x001e;	 Catch:{ all -> 0x0038 }
    L_0x001a:
        r0.write(r2, r7, r1);	 Catch:{ all -> 0x0038 }
        goto L_0x0013;	 Catch:{ all -> 0x0038 }
    L_0x001e:
        r1 = r0.toByteArray();	 Catch:{ all -> 0x0038 }
        if (r6 == 0) goto L_0x002f;
    L_0x0024:
        r6.close();	 Catch:{ IOException -> 0x0028 }
        goto L_0x002f;
    L_0x0028:
        r6 = "Error occurred when closing InputStream";
        r7 = new java.lang.Object[r7];
        com.android.volley.VolleyLog.v(r6, r7);
    L_0x002f:
        r6 = r5.mPool;
        r6.returnBuf(r2);
        r0.close();
        return r1;
    L_0x0038:
        r1 = move-exception;
        r4 = r2;
        r2 = r1;
        r1 = r4;
        goto L_0x0044;
    L_0x003d:
        r2 = new com.android.volley.ServerError;	 Catch:{ all -> 0x0043 }
        r2.<init>();	 Catch:{ all -> 0x0043 }
        throw r2;	 Catch:{ all -> 0x0043 }
    L_0x0043:
        r2 = move-exception;
    L_0x0044:
        if (r6 == 0) goto L_0x0051;
    L_0x0046:
        r6.close();	 Catch:{ IOException -> 0x004a }
        goto L_0x0051;
    L_0x004a:
        r6 = new java.lang.Object[r7];
        r7 = "Error occurred when closing InputStream";
        com.android.volley.VolleyLog.v(r7, r6);
    L_0x0051:
        r6 = r5.mPool;
        r6.returnBuf(r1);
        r0.close();
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.BasicNetwork.inputStreamToBytes(java.io.InputStream, int):byte[]");
    }

    @Deprecated
    protected static Map<String, String> convertHeaders(Header[] headerArr) {
        Map<String, String> treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < headerArr.length; i++) {
            treeMap.put(headerArr[i].getName(), headerArr[i].getValue());
        }
        return treeMap;
    }

    private static List<Header> combineHeaders(List<Header> list, Entry entry) {
        Set treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        if (!list.isEmpty()) {
            for (Header name : list) {
                treeSet.add(name.getName());
            }
        }
        List<Header> arrayList = new ArrayList(list);
        if (entry.allResponseHeaders != null) {
            if (entry.allResponseHeaders.isEmpty() == null) {
                for (Header header : entry.allResponseHeaders) {
                    if (!treeSet.contains(header.getName())) {
                        arrayList.add(header);
                    }
                }
            }
        } else if (entry.responseHeaders.isEmpty() == null) {
            list = entry.responseHeaders.entrySet().iterator();
            while (list.hasNext() != null) {
                Map.Entry entry2 = (Map.Entry) list.next();
                if (!treeSet.contains(entry2.getKey())) {
                    arrayList.add(new Header((String) entry2.getKey(), (String) entry2.getValue()));
                }
            }
        }
        return arrayList;
    }
}
