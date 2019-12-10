package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import androidx.annotation.Nullable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public final class RawResourceDataSource extends BaseDataSource {
    public static final String RAW_RESOURCE_SCHEME = "rawresource";
    @Nullable
    private AssetFileDescriptor assetFileDescriptor;
    private long bytesRemaining;
    @Nullable
    private InputStream inputStream;
    private boolean opened;
    private final Resources resources;
    @Nullable
    private Uri uri;

    public static class RawResourceDataSourceException extends IOException {
        public RawResourceDataSourceException(String str) {
            super(str);
        }

        public RawResourceDataSourceException(IOException iOException) {
            super(iOException);
        }
    }

    public static Uri buildRawResourceUri(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("rawresource:///");
        stringBuilder.append(i);
        return Uri.parse(stringBuilder.toString());
    }

    public RawResourceDataSource(Context context) {
        super(false);
        this.resources = context.getResources();
    }

    @Deprecated
    public RawResourceDataSource(Context context, @Nullable TransferListener transferListener) {
        this(context);
        if (transferListener != null) {
            addTransferListener(transferListener);
        }
    }

    public long open(com.google.android.exoplayer2.upstream.DataSpec r6) throws com.google.android.exoplayer2.upstream.RawResourceDataSource.RawResourceDataSourceException {
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
        r0 = r6.uri;	 Catch:{ IOException -> 0x008b }
        r5.uri = r0;	 Catch:{ IOException -> 0x008b }
        r0 = "rawresource";	 Catch:{ IOException -> 0x008b }
        r1 = r5.uri;	 Catch:{ IOException -> 0x008b }
        r1 = r1.getScheme();	 Catch:{ IOException -> 0x008b }
        r0 = android.text.TextUtils.equals(r0, r1);	 Catch:{ IOException -> 0x008b }
        if (r0 == 0) goto L_0x0083;
    L_0x0012:
        r0 = r5.uri;	 Catch:{ NumberFormatException -> 0x007b }
        r0 = r0.getLastPathSegment();	 Catch:{ NumberFormatException -> 0x007b }
        r0 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x007b }
        r5.transferInitializing(r6);	 Catch:{ IOException -> 0x008b }
        r1 = r5.resources;	 Catch:{ IOException -> 0x008b }
        r0 = r1.openRawResourceFd(r0);	 Catch:{ IOException -> 0x008b }
        r5.assetFileDescriptor = r0;	 Catch:{ IOException -> 0x008b }
        r0 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x008b }
        r1 = r5.assetFileDescriptor;	 Catch:{ IOException -> 0x008b }
        r1 = r1.getFileDescriptor();	 Catch:{ IOException -> 0x008b }
        r0.<init>(r1);	 Catch:{ IOException -> 0x008b }
        r5.inputStream = r0;	 Catch:{ IOException -> 0x008b }
        r0 = r5.inputStream;	 Catch:{ IOException -> 0x008b }
        r1 = r5.assetFileDescriptor;	 Catch:{ IOException -> 0x008b }
        r1 = r1.getStartOffset();	 Catch:{ IOException -> 0x008b }
        r0.skip(r1);	 Catch:{ IOException -> 0x008b }
        r0 = r5.inputStream;	 Catch:{ IOException -> 0x008b }
        r1 = r6.position;	 Catch:{ IOException -> 0x008b }
        r0 = r0.skip(r1);	 Catch:{ IOException -> 0x008b }
        r2 = r6.position;	 Catch:{ IOException -> 0x008b }
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));	 Catch:{ IOException -> 0x008b }
        if (r4 < 0) goto L_0x0075;	 Catch:{ IOException -> 0x008b }
    L_0x004d:
        r0 = r6.length;	 Catch:{ IOException -> 0x008b }
        r2 = -1;	 Catch:{ IOException -> 0x008b }
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));	 Catch:{ IOException -> 0x008b }
        if (r4 == 0) goto L_0x005a;	 Catch:{ IOException -> 0x008b }
    L_0x0055:
        r0 = r6.length;	 Catch:{ IOException -> 0x008b }
        r5.bytesRemaining = r0;	 Catch:{ IOException -> 0x008b }
        goto L_0x006c;	 Catch:{ IOException -> 0x008b }
    L_0x005a:
        r0 = r5.assetFileDescriptor;	 Catch:{ IOException -> 0x008b }
        r0 = r0.getLength();	 Catch:{ IOException -> 0x008b }
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));	 Catch:{ IOException -> 0x008b }
        if (r4 != 0) goto L_0x0065;	 Catch:{ IOException -> 0x008b }
    L_0x0064:
        goto L_0x006a;	 Catch:{ IOException -> 0x008b }
    L_0x0065:
        r2 = r6.position;	 Catch:{ IOException -> 0x008b }
        r4 = 0;	 Catch:{ IOException -> 0x008b }
        r2 = r0 - r2;	 Catch:{ IOException -> 0x008b }
    L_0x006a:
        r5.bytesRemaining = r2;	 Catch:{ IOException -> 0x008b }
    L_0x006c:
        r0 = 1;
        r5.opened = r0;
        r5.transferStarted(r6);
        r0 = r5.bytesRemaining;
        return r0;
    L_0x0075:
        r6 = new java.io.EOFException;	 Catch:{ IOException -> 0x008b }
        r6.<init>();	 Catch:{ IOException -> 0x008b }
        throw r6;	 Catch:{ IOException -> 0x008b }
    L_0x007b:
        r6 = new com.google.android.exoplayer2.upstream.RawResourceDataSource$RawResourceDataSourceException;	 Catch:{ IOException -> 0x008b }
        r0 = "Resource identifier must be an integer.";	 Catch:{ IOException -> 0x008b }
        r6.<init>(r0);	 Catch:{ IOException -> 0x008b }
        throw r6;	 Catch:{ IOException -> 0x008b }
    L_0x0083:
        r6 = new com.google.android.exoplayer2.upstream.RawResourceDataSource$RawResourceDataSourceException;	 Catch:{ IOException -> 0x008b }
        r0 = "URI must use scheme rawresource";	 Catch:{ IOException -> 0x008b }
        r6.<init>(r0);	 Catch:{ IOException -> 0x008b }
        throw r6;	 Catch:{ IOException -> 0x008b }
    L_0x008b:
        r6 = move-exception;
        r0 = new com.google.android.exoplayer2.upstream.RawResourceDataSource$RawResourceDataSourceException;
        r0.<init>(r6);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.RawResourceDataSource.open(com.google.android.exoplayer2.upstream.DataSpec):long");
    }

    public int read(byte[] bArr, int i, int i2) throws RawResourceDataSourceException {
        if (i2 == 0) {
            return null;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        try {
            if (this.bytesRemaining != -1) {
                i2 = (int) Math.min(this.bytesRemaining, (long) i2);
            }
            bArr = this.inputStream.read(bArr, i, i2);
            if (bArr != -1) {
                if (this.bytesRemaining != -1) {
                    this.bytesRemaining -= (long) bArr;
                }
                bytesTransferred(bArr);
                return bArr;
            } else if (this.bytesRemaining == -1) {
                return -1;
            } else {
                throw new RawResourceDataSourceException(new EOFException());
            }
        } catch (IOException e) {
            throw new RawResourceDataSourceException(e);
        }
    }

    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    public void close() throws RawResourceDataSourceException {
        this.uri = null;
        try {
            if (this.inputStream != null) {
                this.inputStream.close();
            }
            this.inputStream = null;
            try {
                if (this.assetFileDescriptor != null) {
                    this.assetFileDescriptor.close();
                }
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            } catch (IOException e) {
                throw new RawResourceDataSourceException(e);
            } catch (Throwable th) {
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            }
        } catch (IOException e2) {
            throw new RawResourceDataSourceException(e2);
        } catch (Throwable th2) {
            this.inputStream = null;
            try {
                if (this.assetFileDescriptor != null) {
                    this.assetFileDescriptor.close();
                }
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            } catch (IOException e22) {
                throw new RawResourceDataSourceException(e22);
            } catch (Throwable th3) {
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            }
        }
    }
}
