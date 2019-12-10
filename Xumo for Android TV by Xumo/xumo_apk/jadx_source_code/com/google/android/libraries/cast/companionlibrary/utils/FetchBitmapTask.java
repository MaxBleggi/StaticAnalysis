package com.google.android.libraries.cast.companionlibrary.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;

public abstract class FetchBitmapTask extends AsyncTask<Uri, Void, Bitmap> {
    private final boolean mAllowedToScale;
    private final int mPreferredHeight;
    private final int mPreferredWidth;

    private android.graphics.Point calculateOriginalDimensions(java.net.URL r1) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.libraries.cast.companionlibrary.utils.FetchBitmapTask.calculateOriginalDimensions(java.net.URL):android.graphics.Point
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 5 more
*/
        /*
        r0 = this;
        r0 = new android.graphics.BitmapFactory$Options;
        r0.<init>();
        r1 = 1;
        r0.inJustDecodeBounds = r1;
        r1 = 0;
        r0.inSampleSize = r1;
        r2 = 0;
        r6 = r6.openConnection();
        r6 = (java.net.HttpURLConnection) r6;
        r3 = r6.getInputStream();
        android.graphics.BitmapFactory.decodeStream(r3, r2, r0);
        r2 = new android.graphics.Point;
        r4 = r0.outWidth;
        r0 = r0.outHeight;
        r2.<init>(r4, r0);
        if (r6 == 0) goto L_0x0027;
        r6.disconnect();
        if (r3 == 0) goto L_0x002c;
        r3.close();
        return r2;
        r0 = move-exception;
        goto L_0x0039;
        goto L_0x0046;
    L_0x0031:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0039;
    L_0x0034:
        r3 = r2;
        goto L_0x0046;
    L_0x0036:
        r0 = move-exception;
        r6 = r2;
        r3 = r6;
        if (r6 == 0) goto L_0x003e;
        r6.disconnect();
        if (r3 == 0) goto L_0x0043;
        r3.close();
        throw r0;
    L_0x0044:
        r6 = r2;
        r3 = r6;
        if (r6 == 0) goto L_0x004b;
        r6.disconnect();
        if (r3 == 0) goto L_0x0050;
        r3.close();
        r6 = new android.graphics.Point;
        r6.<init>(r1, r1);
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.cast.companionlibrary.utils.FetchBitmapTask.calculateOriginalDimensions(java.net.URL):android.graphics.Point");
    }

    public FetchBitmapTask(int i, int i2, boolean z) {
        this.mPreferredWidth = i;
        this.mPreferredHeight = i2;
        this.mAllowedToScale = z;
    }

    public FetchBitmapTask(int i, int i2) {
        this(i, i2, false);
    }

    public FetchBitmapTask() {
        this(0, 0);
    }

    protected android.graphics.Bitmap doInBackground(android.net.Uri... r8) {
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
        r7 = this;
        r0 = r8.length;
        r1 = 1;
        r2 = 0;
        if (r0 != r1) goto L_0x00d9;
    L_0x0005:
        r0 = 0;
        r3 = r8[r0];
        if (r3 != 0) goto L_0x000c;
    L_0x000a:
        goto L_0x00d9;
    L_0x000c:
        r3 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x00d0 }
        r8 = r8[r0];	 Catch:{ MalformedURLException -> 0x00d0 }
        r8 = r8.toString();	 Catch:{ MalformedURLException -> 0x00d0 }
        r3.<init>(r8);	 Catch:{ MalformedURLException -> 0x00d0 }
        r8 = "CCL";	 Catch:{ MalformedURLException -> 0x00d0 }
        r4 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00d0 }
        r4.<init>();	 Catch:{ MalformedURLException -> 0x00d0 }
        r5 = "url=";	 Catch:{ MalformedURLException -> 0x00d0 }
        r4.append(r5);	 Catch:{ MalformedURLException -> 0x00d0 }
        r4.append(r3);	 Catch:{ MalformedURLException -> 0x00d0 }
        r4 = r4.toString();	 Catch:{ MalformedURLException -> 0x00d0 }
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.d(r8, r4);	 Catch:{ MalformedURLException -> 0x00d0 }
        r8 = new android.graphics.BitmapFactory$Options;
        r8.<init>();
        r8.inJustDecodeBounds = r0;
        r8.inSampleSize = r1;
        r0 = r7.mPreferredWidth;
        if (r0 <= 0) goto L_0x0058;
    L_0x003a:
        r0 = r7.mPreferredHeight;
        if (r0 <= 0) goto L_0x0058;
    L_0x003e:
        r0 = r7.calculateOriginalDimensions(r3);
        r4 = r0.x;
        if (r4 <= 0) goto L_0x0058;
    L_0x0046:
        r4 = r0.y;
        if (r4 <= 0) goto L_0x0058;
    L_0x004a:
        r4 = r0.x;
        r0 = r0.y;
        r5 = r7.mPreferredWidth;
        r6 = r7.mPreferredHeight;
        r0 = r7.calculateInSampleSize(r4, r0, r5, r6);
        r8.inSampleSize = r0;
    L_0x0058:
        r0 = r3.openConnection();	 Catch:{ IOException -> 0x00ad, all -> 0x00a0 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ IOException -> 0x00ad, all -> 0x00a0 }
        r0.setDoInput(r1);	 Catch:{ IOException -> 0x009e, all -> 0x009c }
        r1 = r0.getResponseCode();	 Catch:{ IOException -> 0x009e, all -> 0x009c }
        r3 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ IOException -> 0x009e, all -> 0x009c }
        if (r1 != r3) goto L_0x008f;	 Catch:{ IOException -> 0x009e, all -> 0x009c }
    L_0x0069:
        r1 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x009e, all -> 0x009c }
        r3 = r0.getInputStream();	 Catch:{ IOException -> 0x009e, all -> 0x009c }
        r1.<init>(r3);	 Catch:{ IOException -> 0x009e, all -> 0x009c }
        r8 = android.graphics.BitmapFactory.decodeStream(r1, r2, r8);	 Catch:{ IOException -> 0x008c, all -> 0x0089 }
        r2 = r7.mPreferredWidth;	 Catch:{ IOException -> 0x008d, all -> 0x0089 }
        if (r2 <= 0) goto L_0x0087;	 Catch:{ IOException -> 0x008d, all -> 0x0089 }
    L_0x007a:
        r2 = r7.mPreferredHeight;	 Catch:{ IOException -> 0x008d, all -> 0x0089 }
        if (r2 <= 0) goto L_0x0087;	 Catch:{ IOException -> 0x008d, all -> 0x0089 }
    L_0x007e:
        r2 = r7.mAllowedToScale;	 Catch:{ IOException -> 0x008d, all -> 0x0089 }
        if (r2 == 0) goto L_0x0087;	 Catch:{ IOException -> 0x008d, all -> 0x0089 }
    L_0x0082:
        r2 = r7.scaleBitmap(r8);	 Catch:{ IOException -> 0x008d, all -> 0x0089 }
        goto L_0x0090;
    L_0x0087:
        r2 = r8;
        goto L_0x0090;
    L_0x0089:
        r8 = move-exception;
        r2 = r1;
        goto L_0x00a2;
    L_0x008c:
        r8 = r2;
    L_0x008d:
        r2 = r1;
        goto L_0x00af;
    L_0x008f:
        r1 = r2;
    L_0x0090:
        if (r0 == 0) goto L_0x0095;
    L_0x0092:
        r0.disconnect();
    L_0x0095:
        if (r1 == 0) goto L_0x009a;
    L_0x0097:
        r1.close();	 Catch:{ IOException -> 0x009a }
    L_0x009a:
        r8 = r2;
        goto L_0x00b9;
    L_0x009c:
        r8 = move-exception;
        goto L_0x00a2;
    L_0x009e:
        r8 = r2;
        goto L_0x00af;
    L_0x00a0:
        r8 = move-exception;
        r0 = r2;
    L_0x00a2:
        if (r0 == 0) goto L_0x00a7;
    L_0x00a4:
        r0.disconnect();
    L_0x00a7:
        if (r2 == 0) goto L_0x00ac;
    L_0x00a9:
        r2.close();	 Catch:{ IOException -> 0x00ac }
    L_0x00ac:
        throw r8;
    L_0x00ad:
        r8 = r2;
        r0 = r8;
    L_0x00af:
        if (r0 == 0) goto L_0x00b4;
    L_0x00b1:
        r0.disconnect();
    L_0x00b4:
        if (r2 == 0) goto L_0x00b9;
    L_0x00b6:
        r2.close();	 Catch:{ IOException -> 0x00b9 }
    L_0x00b9:
        r0 = "CCL";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "bitmap=";
        r1.append(r2);
        r1.append(r8);
        r1 = r1.toString();
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.d(r0, r1);
        return r8;
    L_0x00d0:
        r8 = move-exception;
        r0 = "CCL";
        r1 = "MalformedURLException";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.d(r0, r1, r8);
        return r2;
    L_0x00d9:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.cast.companionlibrary.utils.FetchBitmapTask.doInBackground(android.net.Uri[]):android.graphics.Bitmap");
    }

    @TargetApi(11)
    public void execute(Uri uri) {
        if (VERSION.SDK_INT >= 11) {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Uri[]{uri});
            return;
        }
        execute(new Uri[]{uri});
    }

    private Bitmap scaleBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = height - this.mPreferredHeight;
        if (width - this.mPreferredWidth == 0 && i == 0) {
            return bitmap;
        }
        float f = (float) width;
        float f2 = (float) height;
        float min = Math.min(((float) this.mPreferredHeight) / f2, ((float) this.mPreferredWidth) / f);
        return Bitmap.createScaledBitmap(bitmap, (int) ((f * min) + 0.5f), (int) ((f2 * min) + 0.5f), false);
    }

    private int calculateInSampleSize(int i, int i2, int i3, int i4) {
        int i5 = 1;
        if (i2 > i4 || i > i3) {
            i2 /= 2;
            i /= 2;
            while (i2 / i5 > i4 && i / i5 > i3) {
                i5 *= 2;
            }
        }
        return i5;
    }
}
