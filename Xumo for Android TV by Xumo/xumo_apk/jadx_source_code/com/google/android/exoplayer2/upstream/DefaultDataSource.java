package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource.-CC;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class DefaultDataSource implements DataSource {
    private static final String SCHEME_ASSET = "asset";
    private static final String SCHEME_CONTENT = "content";
    private static final String SCHEME_RAW = "rawresource";
    private static final String SCHEME_RTMP = "rtmp";
    private static final String TAG = "DefaultDataSource";
    @Nullable
    private DataSource assetDataSource;
    private final DataSource baseDataSource;
    @Nullable
    private DataSource contentDataSource;
    private final Context context;
    @Nullable
    private DataSource dataSchemeDataSource;
    @Nullable
    private DataSource dataSource;
    @Nullable
    private DataSource fileDataSource;
    @Nullable
    private DataSource rawResourceDataSource;
    @Nullable
    private DataSource rtmpDataSource;
    private final List<TransferListener> transferListeners;

    public DefaultDataSource(Context context, String str, boolean z) {
        this(context, str, 8000, 8000, z);
    }

    public DefaultDataSource(Context context, String str, int i, int i2, boolean z) {
        this(context, new DefaultHttpDataSource(str, null, i, i2, z, null));
    }

    public DefaultDataSource(Context context, DataSource dataSource) {
        this.context = context.getApplicationContext();
        this.baseDataSource = (DataSource) Assertions.checkNotNull(dataSource);
        this.transferListeners = new ArrayList();
    }

    @Deprecated
    public DefaultDataSource(Context context, @Nullable TransferListener transferListener, String str, boolean z) {
        this(context, transferListener, str, 8000, 8000, z);
    }

    @Deprecated
    public DefaultDataSource(Context context, @Nullable TransferListener transferListener, String str, int i, int i2, boolean z) {
        this(context, transferListener, new DefaultHttpDataSource(str, null, transferListener, i, i2, z, null));
    }

    @Deprecated
    public DefaultDataSource(Context context, @Nullable TransferListener transferListener, DataSource dataSource) {
        this(context, dataSource);
        if (transferListener != null) {
            this.transferListeners.add(transferListener);
        }
    }

    public void addTransferListener(TransferListener transferListener) {
        this.baseDataSource.addTransferListener(transferListener);
        this.transferListeners.add(transferListener);
        maybeAddListenerToDataSource(this.fileDataSource, transferListener);
        maybeAddListenerToDataSource(this.assetDataSource, transferListener);
        maybeAddListenerToDataSource(this.contentDataSource, transferListener);
        maybeAddListenerToDataSource(this.rtmpDataSource, transferListener);
        maybeAddListenerToDataSource(this.dataSchemeDataSource, transferListener);
        maybeAddListenerToDataSource(this.rawResourceDataSource, transferListener);
    }

    public long open(DataSpec dataSpec) throws IOException {
        Assertions.checkState(this.dataSource == null);
        String scheme = dataSpec.uri.getScheme();
        if (Util.isLocalFileUri(dataSpec.uri)) {
            if (dataSpec.uri.getPath().startsWith("/android_asset/")) {
                this.dataSource = getAssetDataSource();
            } else {
                this.dataSource = getFileDataSource();
            }
        } else if (SCHEME_ASSET.equals(scheme)) {
            this.dataSource = getAssetDataSource();
        } else if ("content".equals(scheme)) {
            this.dataSource = getContentDataSource();
        } else if (SCHEME_RTMP.equals(scheme)) {
            this.dataSource = getRtmpDataSource();
        } else if (DataSchemeDataSource.SCHEME_DATA.equals(scheme)) {
            this.dataSource = getDataSchemeDataSource();
        } else if ("rawresource".equals(scheme)) {
            this.dataSource = getRawResourceDataSource();
        } else {
            this.dataSource = this.baseDataSource;
        }
        return this.dataSource.open(dataSpec);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return ((DataSource) Assertions.checkNotNull(this.dataSource)).read(bArr, i, i2);
    }

    @Nullable
    public Uri getUri() {
        return this.dataSource == null ? null : this.dataSource.getUri();
    }

    public Map<String, List<String>> getResponseHeaders() {
        if (this.dataSource == null) {
            return -CC.$default$getResponseHeaders(this);
        }
        return this.dataSource.getResponseHeaders();
    }

    public void close() throws IOException {
        if (this.dataSource != null) {
            try {
                this.dataSource.close();
            } finally {
                this.dataSource = null;
            }
        }
    }

    private DataSource getFileDataSource() {
        if (this.fileDataSource == null) {
            this.fileDataSource = new FileDataSource();
            addListenersToDataSource(this.fileDataSource);
        }
        return this.fileDataSource;
    }

    private DataSource getAssetDataSource() {
        if (this.assetDataSource == null) {
            this.assetDataSource = new AssetDataSource(this.context);
            addListenersToDataSource(this.assetDataSource);
        }
        return this.assetDataSource;
    }

    private DataSource getContentDataSource() {
        if (this.contentDataSource == null) {
            this.contentDataSource = new ContentDataSource(this.context);
            addListenersToDataSource(this.contentDataSource);
        }
        return this.contentDataSource;
    }

    private com.google.android.exoplayer2.upstream.DataSource getRtmpDataSource() {
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
        r0 = r3.rtmpDataSource;
        if (r0 != 0) goto L_0x0039;
    L_0x0004:
        r0 = "com.google.android.exoplayer2.ext.rtmp.RtmpDataSource";	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        r0 = java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        r1 = 0;	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        r2 = new java.lang.Class[r1];	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        r0 = r0.getConstructor(r2);	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        r1 = new java.lang.Object[r1];	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        r0 = r0.newInstance(r1);	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        r0 = (com.google.android.exoplayer2.upstream.DataSource) r0;	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        r3.rtmpDataSource = r0;	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        r0 = r3.rtmpDataSource;	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        r3.addListenersToDataSource(r0);	 Catch:{ ClassNotFoundException -> 0x002a, Exception -> 0x0021 }
        goto L_0x0031;
    L_0x0021:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r2 = "Error instantiating RTMP extension";
        r1.<init>(r2, r0);
        throw r1;
    L_0x002a:
        r0 = "DefaultDataSource";
        r1 = "Attempting to play RTMP stream without depending on the RTMP extension";
        com.google.android.exoplayer2.util.Log.w(r0, r1);
    L_0x0031:
        r0 = r3.rtmpDataSource;
        if (r0 != 0) goto L_0x0039;
    L_0x0035:
        r0 = r3.baseDataSource;
        r3.rtmpDataSource = r0;
    L_0x0039:
        r0 = r3.rtmpDataSource;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultDataSource.getRtmpDataSource():com.google.android.exoplayer2.upstream.DataSource");
    }

    private DataSource getDataSchemeDataSource() {
        if (this.dataSchemeDataSource == null) {
            this.dataSchemeDataSource = new DataSchemeDataSource();
            addListenersToDataSource(this.dataSchemeDataSource);
        }
        return this.dataSchemeDataSource;
    }

    private DataSource getRawResourceDataSource() {
        if (this.rawResourceDataSource == null) {
            this.rawResourceDataSource = new RawResourceDataSource(this.context);
            addListenersToDataSource(this.rawResourceDataSource);
        }
        return this.rawResourceDataSource;
    }

    private void addListenersToDataSource(DataSource dataSource) {
        for (int i = 0; i < this.transferListeners.size(); i++) {
            dataSource.addTransferListener((TransferListener) this.transferListeners.get(i));
        }
    }

    private void maybeAddListenerToDataSource(@Nullable DataSource dataSource, TransferListener transferListener) {
        if (dataSource != null) {
            dataSource.addTransferListener(transferListener);
        }
    }
}
