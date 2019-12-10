package com.google.android.exoplayer2.drm;

import android.annotation.TargetApi;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.ExoMediaDrm.KeyRequest;
import com.google.android.exoplayer2.drm.ExoMediaDrm.ProvisionRequest;
import com.google.android.exoplayer2.upstream.HttpDataSource.Factory;
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@TargetApi(18)
public final class HttpMediaDrmCallback implements MediaDrmCallback {
    private static final int MAX_MANUAL_REDIRECTS = 5;
    private final Factory dataSourceFactory;
    private final String defaultLicenseUrl;
    private final boolean forceDefaultLicenseUrl;
    private final Map<String, String> keyRequestProperties;

    private static byte[] executePost(com.google.android.exoplayer2.upstream.HttpDataSource.Factory r17, java.lang.String r18, byte[] r19, java.util.Map<java.lang.String, java.lang.String> r20) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r1 = r17.createDataSource();
        if (r20 == 0) goto L_0x002a;
    L_0x0006:
        r0 = r20.entrySet();
        r0 = r0.iterator();
    L_0x000e:
        r2 = r0.hasNext();
        if (r2 == 0) goto L_0x002a;
    L_0x0014:
        r2 = r0.next();
        r2 = (java.util.Map.Entry) r2;
        r3 = r2.getKey();
        r3 = (java.lang.String) r3;
        r2 = r2.getValue();
        r2 = (java.lang.String) r2;
        r1.setRequestProperty(r3, r2);
        goto L_0x000e;
    L_0x002a:
        r2 = 0;
        r0 = r18;
        r3 = 0;
    L_0x002e:
        r15 = new com.google.android.exoplayer2.upstream.DataSpec;
        r5 = android.net.Uri.parse(r0);
        r7 = 0;
        r9 = 0;
        r11 = -1;
        r13 = 0;
        r14 = 1;
        r4 = r15;
        r6 = r19;
        r4.<init>(r5, r6, r7, r9, r11, r13, r14);
        r4 = new com.google.android.exoplayer2.upstream.DataSourceInputStream;
        r4.<init>(r1, r15);
        r0 = com.google.android.exoplayer2.util.Util.toByteArray(r4);	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
        com.google.android.exoplayer2.util.Util.closeQuietly(r4);
        return r0;
    L_0x004f:
        r0 = move-exception;
        goto L_0x007e;
    L_0x0051:
        r0 = move-exception;
        r5 = r0;
        r0 = r5.responseCode;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
        r6 = 307; // 0x133 float:4.3E-43 double:1.517E-321;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
        if (r0 == r6) goto L_0x0062;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
    L_0x0059:
        r0 = r5.responseCode;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
        r6 = 308; // 0x134 float:4.32E-43 double:1.52E-321;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
        if (r0 != r6) goto L_0x0060;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
    L_0x005f:
        goto L_0x0062;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
    L_0x0060:
        r0 = r3;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
        goto L_0x0069;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
    L_0x0062:
        r0 = r3 + 1;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
        r6 = 5;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
        if (r3 >= r6) goto L_0x0069;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
    L_0x0067:
        r3 = 1;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
        goto L_0x006a;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
    L_0x0069:
        r3 = 0;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
    L_0x006a:
        if (r3 == 0) goto L_0x0071;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
    L_0x006c:
        r3 = getRedirectUrl(r5);	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
        goto L_0x0072;
    L_0x0071:
        r3 = 0;
    L_0x0072:
        if (r3 == 0) goto L_0x007d;
    L_0x0074:
        com.google.android.exoplayer2.util.Util.closeQuietly(r4);
        r16 = r3;
        r3 = r0;
        r0 = r16;
        goto L_0x002e;
    L_0x007d:
        throw r5;	 Catch:{ InvalidResponseCodeException -> 0x0051, all -> 0x004f }
    L_0x007e:
        com.google.android.exoplayer2.util.Util.closeQuietly(r4);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.HttpMediaDrmCallback.executePost(com.google.android.exoplayer2.upstream.HttpDataSource$Factory, java.lang.String, byte[], java.util.Map):byte[]");
    }

    public HttpMediaDrmCallback(String str, Factory factory) {
        this(str, false, factory);
    }

    public HttpMediaDrmCallback(String str, boolean z, Factory factory) {
        this.dataSourceFactory = factory;
        this.defaultLicenseUrl = str;
        this.forceDefaultLicenseUrl = z;
        this.keyRequestProperties = new HashMap();
    }

    public void setKeyRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.put(str, str2);
        }
    }

    public void clearKeyRequestProperty(String str) {
        Assertions.checkNotNull(str);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.remove(str);
        }
    }

    public void clearAllKeyRequestProperties() {
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.clear();
        }
    }

    public byte[] executeProvisionRequest(UUID uuid, ProvisionRequest provisionRequest) throws IOException {
        uuid = new StringBuilder();
        uuid.append(provisionRequest.getDefaultUrl());
        uuid.append("&signedRequest=");
        uuid.append(Util.fromUtf8Bytes(provisionRequest.getData()));
        return executePost(this.dataSourceFactory, uuid.toString(), Util.EMPTY_BYTE_ARRAY, null);
    }

    public byte[] executeKeyRequest(UUID uuid, KeyRequest keyRequest) throws Exception {
        String licenseServerUrl = keyRequest.getLicenseServerUrl();
        if (this.forceDefaultLicenseUrl || TextUtils.isEmpty(licenseServerUrl)) {
            licenseServerUrl = this.defaultLicenseUrl;
        }
        Map hashMap = new HashMap();
        Object obj = C.PLAYREADY_UUID.equals(uuid) ? "text/xml" : C.CLEARKEY_UUID.equals(uuid) ? "application/json" : "application/octet-stream";
        hashMap.put(HttpRequest.HEADER_CONTENT_TYPE, obj);
        if (C.PLAYREADY_UUID.equals(uuid) != null) {
            hashMap.put("SOAPAction", "http://schemas.microsoft.com/DRM/2007/03/protocols/AcquireLicense");
        }
        synchronized (this.keyRequestProperties) {
            hashMap.putAll(this.keyRequestProperties);
        }
        return executePost(this.dataSourceFactory, licenseServerUrl, keyRequest.getData(), hashMap);
    }

    private static String getRedirectUrl(InvalidResponseCodeException invalidResponseCodeException) {
        invalidResponseCodeException = invalidResponseCodeException.headerFields;
        if (invalidResponseCodeException != null) {
            List list = (List) invalidResponseCodeException.get(HttpRequest.HEADER_LOCATION);
            if (!(list == null || list.isEmpty())) {
                return (String) list.get(0);
            }
        }
        return null;
    }
}
