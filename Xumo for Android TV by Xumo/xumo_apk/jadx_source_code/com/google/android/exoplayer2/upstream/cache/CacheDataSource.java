package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSource.-CC;
import com.google.android.exoplayer2.upstream.DataSourceException;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.TeeDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.cache.Cache.CacheException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;

public final class CacheDataSource implements DataSource {
    public static final int CACHE_IGNORED_REASON_ERROR = 0;
    public static final int CACHE_IGNORED_REASON_UNSET_LENGTH = 1;
    private static final int CACHE_NOT_IGNORED = -1;
    public static final long DEFAULT_MAX_CACHE_FILE_SIZE = 2097152;
    public static final int FLAG_BLOCK_ON_CACHE = 1;
    public static final int FLAG_IGNORE_CACHE_FOR_UNSET_LENGTH_REQUESTS = 4;
    public static final int FLAG_IGNORE_CACHE_ON_ERROR = 2;
    private static final long MIN_READ_BEFORE_CHECKING_CACHE = 102400;
    @Nullable
    private Uri actualUri;
    private final boolean blockOnCache;
    private long bytesRemaining;
    private final Cache cache;
    private final CacheKeyFactory cacheKeyFactory;
    private final DataSource cacheReadDataSource;
    @Nullable
    private final DataSource cacheWriteDataSource;
    private long checkCachePosition;
    @Nullable
    private DataSource currentDataSource;
    private boolean currentDataSpecLengthUnset;
    @Nullable
    private CacheSpan currentHoleSpan;
    private boolean currentRequestIgnoresCache;
    @Nullable
    private final EventListener eventListener;
    private int flags;
    private int httpMethod;
    private final boolean ignoreCacheForUnsetLengthRequests;
    private final boolean ignoreCacheOnError;
    @Nullable
    private String key;
    private long readPosition;
    private boolean seenCacheError;
    private long totalCachedBytesRead;
    private final DataSource upstreamDataSource;
    @Nullable
    private Uri uri;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CacheIgnoredReason {
    }

    public interface EventListener {
        void onCacheIgnored(int i);

        void onCachedBytesRead(long j, long j2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public CacheDataSource(Cache cache, DataSource dataSource) {
        this(cache, dataSource, 0, 2097152);
    }

    public CacheDataSource(Cache cache, DataSource dataSource, int i) {
        this(cache, dataSource, i, 2097152);
    }

    public CacheDataSource(Cache cache, DataSource dataSource, int i, long j) {
        this(cache, dataSource, new FileDataSource(), new CacheDataSink(cache, j), i, null);
    }

    public CacheDataSource(Cache cache, DataSource dataSource, DataSource dataSource2, DataSink dataSink, int i, @Nullable EventListener eventListener) {
        this(cache, dataSource, dataSource2, dataSink, i, eventListener, null);
    }

    public CacheDataSource(Cache cache, DataSource dataSource, DataSource dataSource2, DataSink dataSink, int i, @Nullable EventListener eventListener, @Nullable CacheKeyFactory cacheKeyFactory) {
        this.cache = cache;
        this.cacheReadDataSource = dataSource2;
        if (cacheKeyFactory == null) {
            cacheKeyFactory = CacheUtil.DEFAULT_CACHE_KEY_FACTORY;
        }
        this.cacheKeyFactory = cacheKeyFactory;
        dataSource2 = null;
        this.blockOnCache = (i & 1) != null ? true : null;
        this.ignoreCacheOnError = (i & 2) != null ? true : null;
        if ((i & 4) != null) {
            dataSource2 = true;
        }
        this.ignoreCacheForUnsetLengthRequests = dataSource2;
        this.upstreamDataSource = dataSource;
        if (dataSink != null) {
            this.cacheWriteDataSource = new TeeDataSource(dataSource, dataSink);
        } else {
            this.cacheWriteDataSource = null;
        }
        this.eventListener = eventListener;
    }

    public void addTransferListener(TransferListener transferListener) {
        this.cacheReadDataSource.addTransferListener(transferListener);
        this.upstreamDataSource.addTransferListener(transferListener);
    }

    public long open(DataSpec dataSpec) throws IOException {
        try {
            this.key = this.cacheKeyFactory.buildCacheKey(dataSpec);
            this.uri = dataSpec.uri;
            this.actualUri = getRedirectedUriOrDefault(this.cache, this.key, this.uri);
            this.httpMethod = dataSpec.httpMethod;
            this.flags = dataSpec.flags;
            this.readPosition = dataSpec.position;
            int shouldIgnoreCacheForRequest = shouldIgnoreCacheForRequest(dataSpec);
            this.currentRequestIgnoresCache = shouldIgnoreCacheForRequest != -1;
            if (this.currentRequestIgnoresCache) {
                notifyCacheIgnored(shouldIgnoreCacheForRequest);
            }
            if (dataSpec.length == -1) {
                if (!this.currentRequestIgnoresCache) {
                    this.bytesRemaining = this.cache.getContentLength(this.key);
                    if (this.bytesRemaining != -1) {
                        this.bytesRemaining -= dataSpec.position;
                        if (this.bytesRemaining <= 0) {
                            throw new DataSourceException(0);
                        }
                    }
                    openNextSource(false);
                    return this.bytesRemaining;
                }
            }
            this.bytesRemaining = dataSpec.length;
            openNextSource(false);
            return this.bytesRemaining;
        } catch (DataSpec dataSpec2) {
            handleBeforeThrow(dataSpec2);
            throw dataSpec2;
        }
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        try {
            if (this.readPosition >= this.checkCachePosition) {
                openNextSource(true);
            }
            int read = this.currentDataSource.read(bArr, i, i2);
            if (read != -1) {
                if (isReadingFromCache() != null) {
                    this.totalCachedBytesRead += (long) read;
                }
                long j = (long) read;
                this.readPosition += j;
                if (this.bytesRemaining != -1) {
                    this.bytesRemaining -= j;
                }
            } else if (this.currentDataSpecLengthUnset) {
                setNoBytesRemainingAndMaybeStoreLength();
            } else {
                if (this.bytesRemaining <= 0) {
                    if (this.bytesRemaining == -1) {
                    }
                }
                closeCurrentSource();
                openNextSource(false);
                return read(bArr, i, i2);
            }
            return read;
        } catch (byte[] bArr2) {
            if (this.currentDataSpecLengthUnset == 0 || isCausedByPositionOutOfRange(bArr2) == 0) {
                handleBeforeThrow(bArr2);
                throw bArr2;
            }
            setNoBytesRemainingAndMaybeStoreLength();
            return -1;
        }
    }

    @Nullable
    public Uri getUri() {
        return this.actualUri;
    }

    public Map<String, List<String>> getResponseHeaders() {
        if (isReadingFromUpstream()) {
            return this.upstreamDataSource.getResponseHeaders();
        }
        return -CC.$default$getResponseHeaders(this);
    }

    public void close() throws IOException {
        this.uri = null;
        this.actualUri = null;
        this.httpMethod = 1;
        notifyBytesRead();
        try {
            closeCurrentSource();
        } catch (IOException e) {
            handleBeforeThrow(e);
            throw e;
        }
    }

    private void openNextSource(boolean r21) throws java.io.IOException {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r20 = this;
        r1 = r20;
        r0 = r1.currentRequestIgnoresCache;
        r2 = 0;
        if (r0 == 0) goto L_0x0009;
    L_0x0007:
        r0 = r2;
        goto L_0x002f;
    L_0x0009:
        r0 = r1.blockOnCache;
        if (r0 == 0) goto L_0x0025;
    L_0x000d:
        r0 = r1.cache;	 Catch:{ InterruptedException -> 0x0018 }
        r3 = r1.key;	 Catch:{ InterruptedException -> 0x0018 }
        r4 = r1.readPosition;	 Catch:{ InterruptedException -> 0x0018 }
        r0 = r0.startReadWrite(r3, r4);	 Catch:{ InterruptedException -> 0x0018 }
        goto L_0x002f;
    L_0x0018:
        r0 = java.lang.Thread.currentThread();
        r0.interrupt();
        r0 = new java.io.InterruptedIOException;
        r0.<init>();
        throw r0;
    L_0x0025:
        r0 = r1.cache;
        r3 = r1.key;
        r4 = r1.readPosition;
        r0 = r0.startReadWriteNonBlocking(r3, r4);
    L_0x002f:
        r3 = -1;
        if (r0 != 0) goto L_0x0052;
    L_0x0033:
        r2 = r1.upstreamDataSource;
        r17 = new com.google.android.exoplayer2.upstream.DataSpec;
        r6 = r1.uri;
        r7 = r1.httpMethod;
        r8 = 0;
        r9 = r1.readPosition;
        r11 = r1.readPosition;
        r13 = r1.bytesRemaining;
        r15 = r1.key;
        r5 = r1.flags;
        r16 = r5;
        r5 = r17;
        r5.<init>(r6, r7, r8, r9, r11, r13, r15, r16);
        r5 = r0;
        r0 = r17;
        goto L_0x00cd;
    L_0x0052:
        r5 = r0.isCached;
        if (r5 == 0) goto L_0x0087;
    L_0x0056:
        r2 = r0.file;
        r6 = android.net.Uri.fromFile(r2);
        r7 = r1.readPosition;
        r9 = r0.position;
        r9 = r7 - r9;
        r7 = r0.length;
        r7 = r7 - r9;
        r11 = r1.bytesRemaining;
        r2 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1));
        if (r2 == 0) goto L_0x0071;
    L_0x006b:
        r11 = r1.bytesRemaining;
        r7 = java.lang.Math.min(r7, r11);
    L_0x0071:
        r11 = r7;
        r2 = new com.google.android.exoplayer2.upstream.DataSpec;
        r7 = r1.readPosition;
        r13 = r1.key;
        r14 = r1.flags;
        r5 = r2;
        r5.<init>(r6, r7, r9, r11, r13, r14);
        r5 = r1.cacheReadDataSource;
        r19 = r5;
        r5 = r0;
        r0 = r2;
        r2 = r19;
        goto L_0x00cd;
    L_0x0087:
        r5 = r0.isOpenEnded();
        if (r5 == 0) goto L_0x0091;
    L_0x008d:
        r5 = r1.bytesRemaining;
    L_0x008f:
        r15 = r5;
        goto L_0x00a0;
    L_0x0091:
        r5 = r0.length;
        r7 = r1.bytesRemaining;
        r9 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1));
        if (r9 == 0) goto L_0x008f;
    L_0x0099:
        r7 = r1.bytesRemaining;
        r5 = java.lang.Math.min(r5, r7);
        goto L_0x008f;
    L_0x00a0:
        r5 = new com.google.android.exoplayer2.upstream.DataSpec;
        r8 = r1.uri;
        r9 = r1.httpMethod;
        r10 = 0;
        r11 = r1.readPosition;
        r13 = r1.readPosition;
        r6 = r1.key;
        r7 = r1.flags;
        r18 = r7;
        r7 = r5;
        r17 = r6;
        r7.<init>(r8, r9, r10, r11, r13, r15, r17, r18);
        r6 = r1.cacheWriteDataSource;
        if (r6 == 0) goto L_0x00c3;
    L_0x00bb:
        r2 = r1.cacheWriteDataSource;
        r19 = r5;
        r5 = r0;
        r0 = r19;
        goto L_0x00cd;
    L_0x00c3:
        r6 = r1.upstreamDataSource;
        r7 = r1.cache;
        r7.releaseHoleSpan(r0);
        r0 = r5;
        r5 = r2;
        r2 = r6;
    L_0x00cd:
        r6 = r1.currentRequestIgnoresCache;
        if (r6 != 0) goto L_0x00dc;
    L_0x00d1:
        r6 = r1.upstreamDataSource;
        if (r2 != r6) goto L_0x00dc;
    L_0x00d5:
        r6 = r1.readPosition;
        r8 = 102400; // 0x19000 float:1.43493E-40 double:5.05923E-319;
        r6 = r6 + r8;
        goto L_0x00e1;
    L_0x00dc:
        r6 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
    L_0x00e1:
        r1.checkCachePosition = r6;
        if (r21 == 0) goto L_0x0103;
    L_0x00e5:
        r6 = r20.isBypassingCache();
        com.google.android.exoplayer2.util.Assertions.checkState(r6);
        r6 = r1.upstreamDataSource;
        if (r2 != r6) goto L_0x00f1;
    L_0x00f0:
        return;
    L_0x00f1:
        r20.closeCurrentSource();	 Catch:{ Throwable -> 0x00f5 }
        goto L_0x0103;
    L_0x00f5:
        r0 = move-exception;
        r2 = r0;
        r0 = r5.isHoleSpan();
        if (r0 == 0) goto L_0x0102;
    L_0x00fd:
        r0 = r1.cache;
        r0.releaseHoleSpan(r5);
    L_0x0102:
        throw r2;
    L_0x0103:
        if (r5 == 0) goto L_0x010d;
    L_0x0105:
        r6 = r5.isHoleSpan();
        if (r6 == 0) goto L_0x010d;
    L_0x010b:
        r1.currentHoleSpan = r5;
    L_0x010d:
        r1.currentDataSource = r2;
        r5 = r0.length;
        r7 = 1;
        r8 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r8 != 0) goto L_0x0118;
    L_0x0116:
        r5 = 1;
        goto L_0x0119;
    L_0x0118:
        r5 = 0;
    L_0x0119:
        r1.currentDataSpecLengthUnset = r5;
        r5 = r2.open(r0);
        r0 = new com.google.android.exoplayer2.upstream.cache.ContentMetadataMutations;
        r0.<init>();
        r2 = r1.currentDataSpecLengthUnset;
        if (r2 == 0) goto L_0x0136;
    L_0x0128:
        r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r2 == 0) goto L_0x0136;
    L_0x012c:
        r1.bytesRemaining = r5;
        r2 = r1.readPosition;
        r4 = r1.bytesRemaining;
        r2 = r2 + r4;
        com.google.android.exoplayer2.upstream.cache.ContentMetadataInternal.setContentLength(r0, r2);
    L_0x0136:
        r2 = r20.isReadingFromUpstream();
        if (r2 == 0) goto L_0x0158;
    L_0x013c:
        r2 = r1.currentDataSource;
        r2 = r2.getUri();
        r1.actualUri = r2;
        r2 = r1.uri;
        r3 = r1.actualUri;
        r2 = r2.equals(r3);
        r2 = r2 ^ r7;
        if (r2 == 0) goto L_0x0155;
    L_0x014f:
        r2 = r1.actualUri;
        com.google.android.exoplayer2.upstream.cache.ContentMetadataInternal.setRedirectedUri(r0, r2);
        goto L_0x0158;
    L_0x0155:
        com.google.android.exoplayer2.upstream.cache.ContentMetadataInternal.removeRedirectedUri(r0);
    L_0x0158:
        r2 = r20.isWritingToCache();
        if (r2 == 0) goto L_0x0165;
    L_0x015e:
        r2 = r1.cache;
        r3 = r1.key;
        r2.applyContentMetadataMutations(r3, r0);
    L_0x0165:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheDataSource.openNextSource(boolean):void");
    }

    private void setNoBytesRemainingAndMaybeStoreLength() throws IOException {
        this.bytesRemaining = 0;
        if (isWritingToCache()) {
            this.cache.setContentLength(this.key, this.readPosition);
        }
    }

    private static Uri getRedirectedUriOrDefault(Cache cache, String str, Uri uri) {
        cache = ContentMetadataInternal.getRedirectedUri(cache.getContentMetadata(str));
        return cache == null ? uri : cache;
    }

    private static boolean isCausedByPositionOutOfRange(IOException iOException) {
        while (iOException != null) {
            if ((iOException instanceof DataSourceException) && ((DataSourceException) iOException).reason == 0) {
                return true;
            }
            iOException = iOException.getCause();
        }
        return null;
    }

    private boolean isReadingFromUpstream() {
        return isReadingFromCache() ^ 1;
    }

    private boolean isBypassingCache() {
        return this.currentDataSource == this.upstreamDataSource;
    }

    private boolean isReadingFromCache() {
        return this.currentDataSource == this.cacheReadDataSource;
    }

    private boolean isWritingToCache() {
        return this.currentDataSource == this.cacheWriteDataSource;
    }

    private void closeCurrentSource() throws IOException {
        if (this.currentDataSource != null) {
            try {
                this.currentDataSource.close();
            } finally {
                this.currentDataSource = null;
                this.currentDataSpecLengthUnset = false;
                if (this.currentHoleSpan != null) {
                    this.cache.releaseHoleSpan(this.currentHoleSpan);
                    this.currentHoleSpan = null;
                }
            }
        }
    }

    private void handleBeforeThrow(IOException iOException) {
        if (isReadingFromCache() || (iOException instanceof CacheException) != null) {
            this.seenCacheError = true;
        }
    }

    private int shouldIgnoreCacheForRequest(DataSpec dataSpec) {
        if (this.ignoreCacheOnError && this.seenCacheError) {
            return null;
        }
        return (this.ignoreCacheForUnsetLengthRequests && dataSpec.length == -1) ? 1 : -1;
    }

    private void notifyCacheIgnored(int i) {
        if (this.eventListener != null) {
            this.eventListener.onCacheIgnored(i);
        }
    }

    private void notifyBytesRead() {
        if (this.eventListener != null && this.totalCachedBytesRead > 0) {
            this.eventListener.onCachedBytesRead(this.cache.getCacheSpace(), this.totalCachedBytesRead);
            this.totalCachedBytesRead = 0;
        }
    }
}
