package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.net.Uri;

/* compiled from: IMASDK */
public class kf {
    private static final String[] e = new String[]{"/aclk", "/pcs/click"};
    private String a = "googleads.g.doubleclick.net";
    private String b = "/pagead/ads";
    private String c = "ad.doubleclick.net";
    private String[] d = new String[]{".doubleclick.net", ".googleadservices.com", ".googlesyndication.com"};
    private kb f;

    public kf(kb kbVar) {
        this.f = kbVar;
    }

    public boolean a(android.net.Uri r2) {
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
        r1 = this;
        if (r2 == 0) goto L_0x000f;
    L_0x0002:
        r2 = r2.getHost();	 Catch:{ NullPointerException -> 0x000d }
        r0 = r1.c;	 Catch:{ NullPointerException -> 0x000d }
        r2 = r2.equals(r0);	 Catch:{ NullPointerException -> 0x000d }
        return r2;
    L_0x000d:
        r2 = 0;
        return r2;
    L_0x000f:
        r2 = new java.lang.NullPointerException;
        r2.<init>();
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.kf.a(android.net.Uri):boolean");
    }

    public boolean b(android.net.Uri r6) {
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
        if (r6 == 0) goto L_0x001c;
    L_0x0002:
        r0 = 0;
        r6 = r6.getHost();	 Catch:{ NullPointerException -> 0x001b }
        r1 = r5.d;	 Catch:{ NullPointerException -> 0x001b }
        r2 = r1.length;	 Catch:{ NullPointerException -> 0x001b }
        r3 = 0;	 Catch:{ NullPointerException -> 0x001b }
    L_0x000b:
        if (r3 >= r2) goto L_0x001a;	 Catch:{ NullPointerException -> 0x001b }
    L_0x000d:
        r4 = r1[r3];	 Catch:{ NullPointerException -> 0x001b }
        r4 = r6.endsWith(r4);	 Catch:{ NullPointerException -> 0x001b }
        if (r4 == 0) goto L_0x0017;
    L_0x0015:
        r6 = 1;
        return r6;
    L_0x0017:
        r3 = r3 + 1;
        goto L_0x000b;
    L_0x001a:
        return r0;
    L_0x001b:
        return r0;
    L_0x001c:
        r6 = new java.lang.NullPointerException;
        r6.<init>();
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.kf.b(android.net.Uri):boolean");
    }

    public kb a() {
        return this.f;
    }

    public Uri a(Uri uri, Context context) throws kg {
        return a(uri, context, null, false);
    }

    private Uri a(Uri uri, String str, String str2) throws UnsupportedOperationException {
        String uri2 = uri.toString();
        int indexOf = uri2.indexOf("&adurl");
        if (indexOf == -1) {
            indexOf = uri2.indexOf("?adurl");
        }
        if (indexOf == -1) {
            return uri.buildUpon().appendQueryParameter(str, str2).build();
        }
        indexOf++;
        uri = new StringBuilder(uri2.substring(0, indexOf));
        uri.append(str);
        uri.append("=");
        uri.append(str2);
        uri.append("&");
        uri.append(uri2.substring(indexOf));
        return Uri.parse(uri.toString());
    }

    private Uri b(Uri uri, String str, String str2) {
        String uri2 = uri.toString();
        int indexOf = uri2.indexOf(";adurl");
        if (indexOf != -1) {
            indexOf++;
            uri = new StringBuilder(uri2.substring(0, indexOf));
            uri.append(str);
            uri.append("=");
            uri.append(str2);
            uri.append(";");
            uri.append(uri2.substring(indexOf));
            return Uri.parse(uri.toString());
        }
        uri = uri.getEncodedPath();
        indexOf = uri2.indexOf(uri);
        StringBuilder stringBuilder = new StringBuilder(uri2.substring(0, uri.length() + indexOf));
        stringBuilder.append(";");
        stringBuilder.append(str);
        stringBuilder.append("=");
        stringBuilder.append(str2);
        stringBuilder.append(";");
        stringBuilder.append(uri2.substring(indexOf + uri.length()));
        return Uri.parse(stringBuilder.toString());
    }

    private android.net.Uri a(android.net.Uri r4, android.content.Context r5, java.lang.String r6, boolean r7) throws com.google.ads.interactivemedia.v3.internal.kg {
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
        r0 = r3.a(r4);	 Catch:{ UnsupportedOperationException -> 0x004a }
        if (r0 == 0) goto L_0x001b;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x0006:
        r1 = r4.toString();	 Catch:{ UnsupportedOperationException -> 0x004a }
        r2 = "dc_ms=";	 Catch:{ UnsupportedOperationException -> 0x004a }
        r1 = r1.contains(r2);	 Catch:{ UnsupportedOperationException -> 0x004a }
        if (r1 != 0) goto L_0x0013;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x0012:
        goto L_0x0023;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x0013:
        r4 = new com.google.ads.interactivemedia.v3.internal.kg;	 Catch:{ UnsupportedOperationException -> 0x004a }
        r5 = "Parameter already exists: dc_ms";	 Catch:{ UnsupportedOperationException -> 0x004a }
        r4.<init>(r5);	 Catch:{ UnsupportedOperationException -> 0x004a }
        throw r4;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x001b:
        r1 = "ms";	 Catch:{ UnsupportedOperationException -> 0x004a }
        r1 = r4.getQueryParameter(r1);	 Catch:{ UnsupportedOperationException -> 0x004a }
        if (r1 != 0) goto L_0x0042;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x0023:
        if (r7 == 0) goto L_0x002c;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x0025:
        r7 = r3.f;	 Catch:{ UnsupportedOperationException -> 0x004a }
        r5 = r7.a(r5, r6);	 Catch:{ UnsupportedOperationException -> 0x004a }
        goto L_0x0032;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x002c:
        r6 = r3.f;	 Catch:{ UnsupportedOperationException -> 0x004a }
        r5 = r6.a(r5);	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x0032:
        if (r0 == 0) goto L_0x003b;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x0034:
        r6 = "dc_ms";	 Catch:{ UnsupportedOperationException -> 0x004a }
        r4 = r3.b(r4, r6, r5);	 Catch:{ UnsupportedOperationException -> 0x004a }
        return r4;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x003b:
        r6 = "ms";	 Catch:{ UnsupportedOperationException -> 0x004a }
        r4 = r3.a(r4, r6, r5);	 Catch:{ UnsupportedOperationException -> 0x004a }
        return r4;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x0042:
        r4 = new com.google.ads.interactivemedia.v3.internal.kg;	 Catch:{ UnsupportedOperationException -> 0x004a }
        r5 = "Query parameter already exists: ms";	 Catch:{ UnsupportedOperationException -> 0x004a }
        r4.<init>(r5);	 Catch:{ UnsupportedOperationException -> 0x004a }
        throw r4;	 Catch:{ UnsupportedOperationException -> 0x004a }
    L_0x004a:
        r4 = new com.google.ads.interactivemedia.v3.internal.kg;
        r5 = "Provided Uri is not in a valid state";
        r4.<init>(r5);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.kf.a(android.net.Uri, android.content.Context, java.lang.String, boolean):android.net.Uri");
    }
}
