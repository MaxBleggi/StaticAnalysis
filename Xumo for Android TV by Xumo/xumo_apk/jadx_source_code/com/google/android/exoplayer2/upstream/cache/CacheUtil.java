package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CacheUtil {
    public static final int DEFAULT_BUFFER_SIZE_BYTES = 131072;
    public static final CacheKeyFactory DEFAULT_CACHE_KEY_FACTORY = -$$Lambda$u97poD-IIwob7OPYcVJkh9jokx0.INSTANCE;

    public static class CachingCounters {
        public volatile long alreadyCachedBytes;
        public volatile long contentLength = -1;
        public volatile long newlyCachedBytes;

        public long totalCachedBytes() {
            return this.alreadyCachedBytes + this.newlyCachedBytes;
        }
    }

    public static String generateKey(Uri uri) {
        return uri.toString();
    }

    public static String getKey(DataSpec dataSpec) {
        return dataSpec.key != null ? dataSpec.key : generateKey(dataSpec.uri);
    }

    public static void getCached(DataSpec dataSpec, Cache cache, CachingCounters cachingCounters) {
        long j;
        DataSpec dataSpec2 = dataSpec;
        CachingCounters cachingCounters2 = cachingCounters;
        String key = getKey(dataSpec);
        long j2 = dataSpec2.absoluteStreamPosition;
        if (dataSpec2.length != -1) {
            j = dataSpec2.length;
            Cache cache2 = cache;
        } else {
            j = cache.getContentLength(key);
        }
        cachingCounters2.contentLength = j;
        cachingCounters2.alreadyCachedBytes = 0;
        cachingCounters2.newlyCachedBytes = 0;
        long j3 = j2;
        long j4 = j;
        while (j4 != 0) {
            j2 = cache.getCachedLength(key, j3, j4 != -1 ? j4 : Long.MAX_VALUE);
            if (j2 > 0) {
                cachingCounters2.alreadyCachedBytes += j2;
            } else {
                j2 = -j2;
                if (j2 == Long.MAX_VALUE) {
                    return;
                }
            }
            j3 += j2;
            if (j4 == -1) {
                j2 = 0;
            }
            j4 -= j2;
        }
    }

    public static void cache(DataSpec dataSpec, Cache cache, DataSource dataSource, @Nullable CachingCounters cachingCounters, @Nullable AtomicBoolean atomicBoolean) throws IOException, InterruptedException {
        cache(dataSpec, cache, new CacheDataSource(cache, dataSource), new byte[131072], null, 0, cachingCounters, atomicBoolean, false);
    }

    public static void cache(DataSpec dataSpec, Cache cache, CacheDataSource cacheDataSource, byte[] bArr, PriorityTaskManager priorityTaskManager, int i, @Nullable CachingCounters cachingCounters, @Nullable AtomicBoolean atomicBoolean, boolean z) throws IOException, InterruptedException {
        DataSpec dataSpec2 = dataSpec;
        Cache cache2 = cache;
        CachingCounters cachingCounters2 = cachingCounters;
        Assertions.checkNotNull(cacheDataSource);
        Assertions.checkNotNull(bArr);
        if (cachingCounters2 != null) {
            getCached(dataSpec2, cache2, cachingCounters2);
        } else {
            cachingCounters2 = new CachingCounters();
        }
        CachingCounters cachingCounters3 = cachingCounters2;
        String key = getKey(dataSpec);
        long j = dataSpec2.absoluteStreamPosition;
        long contentLength = dataSpec2.length != -1 ? dataSpec2.length : cache2.getContentLength(key);
        while (contentLength != 0) {
            long j2;
            throwExceptionIfInterruptedOrCancelled(atomicBoolean);
            long cachedLength = cache.getCachedLength(key, j, contentLength != -1 ? contentLength : Long.MAX_VALUE);
            if (cachedLength > 0) {
                j2 = cachedLength;
            } else {
                long j3 = -cachedLength;
                j2 = j3;
                if (readAndDiscard(dataSpec, j, j3, cacheDataSource, bArr, priorityTaskManager, i, cachingCounters3, atomicBoolean) < j2) {
                    if (!z) {
                        return;
                    }
                    if (contentLength != -1) {
                        throw new EOFException();
                    }
                    return;
                }
            }
            j += j2;
            if (contentLength == -1) {
                j2 = 0;
            }
            contentLength -= j2;
        }
    }

    private static long readAndDiscard(com.google.android.exoplayer2.upstream.DataSpec r19, long r20, long r22, com.google.android.exoplayer2.upstream.DataSource r24, byte[] r25, com.google.android.exoplayer2.util.PriorityTaskManager r26, int r27, com.google.android.exoplayer2.upstream.cache.CacheUtil.CachingCounters r28, java.util.concurrent.atomic.AtomicBoolean r29) throws java.io.IOException, java.lang.InterruptedException {
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
        r1 = r24;
        r2 = r25;
        r3 = r28;
        r5 = r19;
    L_0x0008:
        if (r26 == 0) goto L_0x000d;
    L_0x000a:
        r26.proceed(r27);
    L_0x000d:
        throwExceptionIfInterruptedOrCancelled(r29);	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r14 = new com.google.android.exoplayer2.upstream.DataSpec;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r7 = r5.uri;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r8 = r5.httpMethod;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r9 = r5.httpBody;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r10 = r5.position;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r6 = 0;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r10 = r10 + r20;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r12 = r5.absoluteStreamPosition;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r6 = 0;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r12 = r10 - r12;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r15 = -1;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r10 = r5.key;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r6 = r5.flags;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r17 = r6 | 2;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r6 = r14;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r18 = r10;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r10 = r20;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r4 = r14;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r14 = r15;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r16 = r18;	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r6.<init>(r7, r8, r9, r10, r12, r14, r16, r17);	 Catch:{ PriorityTooLowException -> 0x008d, all -> 0x0088 }
        r5 = r1.open(r4);	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r7 = r3.contentLength;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r9 = -1;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1));	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        if (r11 != 0) goto L_0x004c;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x0042:
        r7 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1));	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        if (r7 == 0) goto L_0x004c;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x0046:
        r7 = r4.absoluteStreamPosition;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r11 = 0;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r7 = r7 + r5;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r3.contentLength = r7;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x004c:
        r5 = 0;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x004e:
        r7 = (r5 > r22 ? 1 : (r5 == r22 ? 0 : -1));	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        if (r7 == 0) goto L_0x0082;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x0052:
        throwExceptionIfInterruptedOrCancelled(r29);	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r7 = 0;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r8 = (r22 > r9 ? 1 : (r22 == r9 ? 0 : -1));	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        if (r8 == 0) goto L_0x0064;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x005a:
        r8 = r2.length;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r11 = (long) r8;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r13 = r22 - r5;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r11 = java.lang.Math.min(r11, r13);	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r8 = (int) r11;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        goto L_0x0065;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x0064:
        r8 = r2.length;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x0065:
        r7 = r1.read(r2, r7, r8);	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r8 = -1;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        if (r7 != r8) goto L_0x0079;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x006c:
        r7 = r3.contentLength;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1));	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        if (r11 != 0) goto L_0x0082;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x0072:
        r7 = r4.absoluteStreamPosition;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r9 = 0;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r7 = r7 + r5;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r3.contentLength = r7;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        goto L_0x0082;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
    L_0x0079:
        r7 = (long) r7;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r5 = r5 + r7;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r11 = r3.newlyCachedBytes;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r13 = 0;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r11 = r11 + r7;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        r3.newlyCachedBytes = r11;	 Catch:{ PriorityTooLowException -> 0x0086, all -> 0x0088 }
        goto L_0x004e;
    L_0x0082:
        com.google.android.exoplayer2.util.Util.closeQuietly(r24);
        return r5;
    L_0x0086:
        r5 = r4;
        goto L_0x008d;
    L_0x0088:
        r0 = move-exception;
        com.google.android.exoplayer2.util.Util.closeQuietly(r24);
        throw r0;
    L_0x008d:
        com.google.android.exoplayer2.util.Util.closeQuietly(r24);
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheUtil.readAndDiscard(com.google.android.exoplayer2.upstream.DataSpec, long, long, com.google.android.exoplayer2.upstream.DataSource, byte[], com.google.android.exoplayer2.util.PriorityTaskManager, int, com.google.android.exoplayer2.upstream.cache.CacheUtil$CachingCounters, java.util.concurrent.atomic.AtomicBoolean):long");
    }

    public static void remove(com.google.android.exoplayer2.upstream.cache.Cache r1, java.lang.String r2) {
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
        r2 = r1.getCachedSpans(r2);
        r2 = r2.iterator();
    L_0x0008:
        r0 = r2.hasNext();
        if (r0 == 0) goto L_0x0018;
    L_0x000e:
        r0 = r2.next();
        r0 = (com.google.android.exoplayer2.upstream.cache.CacheSpan) r0;
        r1.removeSpan(r0);	 Catch:{ CacheException -> 0x0008 }
        goto L_0x0008;
    L_0x0018:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheUtil.remove(com.google.android.exoplayer2.upstream.cache.Cache, java.lang.String):void");
    }

    private static void throwExceptionIfInterruptedOrCancelled(AtomicBoolean atomicBoolean) throws InterruptedException {
        if (Thread.interrupted() || !(atomicBoolean == null || atomicBoolean.get() == null)) {
            throw new InterruptedException();
        }
    }

    private CacheUtil() {
    }
}
