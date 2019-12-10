package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/* compiled from: IMASDK */
public abstract class kc implements kb {
    protected MotionEvent a;
    protected DisplayMetrics b;
    protected kh c;
    private ki d;

    protected kc(android.content.Context r1, com.google.ads.interactivemedia.v3.internal.kh r2, com.google.ads.interactivemedia.v3.internal.ki r3) {
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
        r0 = this;
        r0.<init>();
        r0.c = r2;
        r0.d = r3;
        r1 = r1.getResources();	 Catch:{ UnsupportedOperationException -> 0x0012 }
        r1 = r1.getDisplayMetrics();	 Catch:{ UnsupportedOperationException -> 0x0012 }
        r0.b = r1;	 Catch:{ UnsupportedOperationException -> 0x0012 }
        goto L_0x001f;
    L_0x0012:
        r1 = new android.util.DisplayMetrics;
        r1.<init>();
        r0.b = r1;
        r1 = r0.b;
        r2 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r1.density = r2;
    L_0x001f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.kc.<init>(android.content.Context, com.google.ads.interactivemedia.v3.internal.kh, com.google.ads.interactivemedia.v3.internal.ki):void");
    }

    protected abstract void b(Context context);

    protected abstract void c(Context context);

    public String a(Context context) {
        return a(context, null, false);
    }

    public String a(Context context, String str) {
        return a(context, str, true);
    }

    private java.lang.String a(android.content.Context r2, java.lang.String r3, boolean r4) {
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
        r0 = 7;
        monitor-enter(r1);	 Catch:{ NoSuchAlgorithmException -> 0x002f, UnsupportedEncodingException -> 0x002a, IOException -> 0x0024 }
        r1.a();	 Catch:{ all -> 0x0021 }
        if (r4 == 0) goto L_0x000b;	 Catch:{ all -> 0x0021 }
    L_0x0007:
        r1.c(r2);	 Catch:{ all -> 0x0021 }
        goto L_0x000e;	 Catch:{ all -> 0x0021 }
    L_0x000b:
        r1.b(r2);	 Catch:{ all -> 0x0021 }
    L_0x000e:
        r2 = r1.b();	 Catch:{ all -> 0x0021 }
        monitor-exit(r1);	 Catch:{ all -> 0x0021 }
        r4 = r2.length;	 Catch:{ NoSuchAlgorithmException -> 0x002f, UnsupportedEncodingException -> 0x002a, IOException -> 0x0024 }
        if (r4 != 0) goto L_0x001c;	 Catch:{ NoSuchAlgorithmException -> 0x002f, UnsupportedEncodingException -> 0x002a, IOException -> 0x0024 }
    L_0x0016:
        r2 = 5;	 Catch:{ NoSuchAlgorithmException -> 0x002f, UnsupportedEncodingException -> 0x002a, IOException -> 0x0024 }
        r2 = java.lang.Integer.toString(r2);	 Catch:{ NoSuchAlgorithmException -> 0x002f, UnsupportedEncodingException -> 0x002a, IOException -> 0x0024 }
        goto L_0x0033;	 Catch:{ NoSuchAlgorithmException -> 0x002f, UnsupportedEncodingException -> 0x002a, IOException -> 0x0024 }
    L_0x001c:
        r2 = r1.a(r2, r3);	 Catch:{ NoSuchAlgorithmException -> 0x002f, UnsupportedEncodingException -> 0x002a, IOException -> 0x0024 }
        goto L_0x0033;
    L_0x0021:
        r2 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0021 }
        throw r2;	 Catch:{ NoSuchAlgorithmException -> 0x002f, UnsupportedEncodingException -> 0x002a, IOException -> 0x0024 }
    L_0x0024:
        r2 = 3;
        r2 = java.lang.Integer.toString(r2);
        goto L_0x0033;
    L_0x002a:
        r2 = java.lang.Integer.toString(r0);
        goto L_0x0033;
    L_0x002f:
        r2 = java.lang.Integer.toString(r0);
    L_0x0033:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.kc.a(android.content.Context, java.lang.String, boolean):java.lang.String");
    }

    protected void a(int i, long j) throws IOException {
        this.d.a(i, j);
    }

    protected void a(int i, String str) throws IOException {
        this.d.a(i, str);
    }

    private void a() {
        this.d.a();
    }

    private byte[] b() throws IOException {
        return this.d.b();
    }

    String a(byte[] bArr, String str) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
        byte[] bArr2;
        if (bArr.length > 239) {
            a();
            a((int) 20, 1);
            bArr = b();
        }
        if (bArr.length < 239) {
            bArr2 = new byte[(239 - bArr.length)];
            new SecureRandom().nextBytes(bArr2);
            bArr = ByteBuffer.allocate(PsExtractor.VIDEO_STREAM_MASK).put((byte) bArr.length).put(bArr).put(bArr2).array();
        } else {
            bArr = ByteBuffer.allocate(PsExtractor.VIDEO_STREAM_MASK).put((byte) bArr.length).put(bArr).array();
        }
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(bArr);
        bArr = ByteBuffer.allocate(256).put(instance.digest()).put(bArr).array();
        bArr2 = new byte[256];
        new ka().a(bArr, bArr2);
        if (str != null && str.length() > null) {
            a(str, bArr2);
        }
        return this.c.a(bArr2, true);
    }

    void a(String str, byte[] bArr) throws UnsupportedEncodingException {
        if (str.length() > 32) {
            str = str.substring(0, 32);
        }
        new lu(str.getBytes("UTF-8")).a(bArr);
    }

    protected String a(String str) {
        if (str == null || !str.matches("^[a-fA-F0-9]{8}-([a-fA-F0-9]{4}-){3}[a-fA-F0-9]{12}$")) {
            return str;
        }
        str = UUID.fromString(str);
        byte[] bArr = new byte[16];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.putLong(str.getMostSignificantBits());
        wrap.putLong(str.getLeastSignificantBits());
        return this.c.a(bArr, true);
    }
}
