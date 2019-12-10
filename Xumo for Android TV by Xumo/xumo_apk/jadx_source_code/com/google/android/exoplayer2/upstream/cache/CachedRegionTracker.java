package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.NonNull;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.upstream.cache.Cache.Listener;
import java.util.Arrays;
import java.util.TreeSet;

public final class CachedRegionTracker implements Listener {
    public static final int CACHED_TO_END = -2;
    public static final int NOT_CACHED = -1;
    private static final String TAG = "CachedRegionTracker";
    private final Cache cache;
    private final String cacheKey;
    private final ChunkIndex chunkIndex;
    private final Region lookupRegion = new Region(0, 0);
    private final TreeSet<Region> regions = new TreeSet();

    private static class Region implements Comparable<Region> {
        public long endOffset;
        public int endOffsetIndex;
        public long startOffset;

        public Region(long j, long j2) {
            this.startOffset = j;
            this.endOffset = j2;
        }

        public int compareTo(@NonNull Region region) {
            if (this.startOffset < region.startOffset) {
                return -1;
            }
            return this.startOffset == region.startOffset ? null : 1;
        }
    }

    public void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2) {
    }

    public CachedRegionTracker(Cache cache, String str, ChunkIndex chunkIndex) {
        this.cache = cache;
        this.cacheKey = str;
        this.chunkIndex = chunkIndex;
        synchronized (this) {
            cache = cache.addListener(str, this).descendingIterator();
            while (cache.hasNext() != null) {
                mergeSpan((CacheSpan) cache.next());
            }
        }
    }

    public void release() {
        this.cache.removeListener(this.cacheKey, this);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int getRegionEndTimeMs(long r8) {
        /*
        r7 = this;
        monitor-enter(r7);
        r0 = r7.lookupRegion;	 Catch:{ all -> 0x0068 }
        r0.startOffset = r8;	 Catch:{ all -> 0x0068 }
        r0 = r7.regions;	 Catch:{ all -> 0x0068 }
        r1 = r7.lookupRegion;	 Catch:{ all -> 0x0068 }
        r0 = r0.floor(r1);	 Catch:{ all -> 0x0068 }
        r0 = (com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.Region) r0;	 Catch:{ all -> 0x0068 }
        r1 = -1;
        if (r0 == 0) goto L_0x0066;
    L_0x0012:
        r2 = r0.endOffset;	 Catch:{ all -> 0x0068 }
        r4 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1));
        if (r4 > 0) goto L_0x0066;
    L_0x0018:
        r8 = r0.endOffsetIndex;	 Catch:{ all -> 0x0068 }
        if (r8 != r1) goto L_0x001d;
    L_0x001c:
        goto L_0x0066;
    L_0x001d:
        r8 = r0.endOffsetIndex;	 Catch:{ all -> 0x0068 }
        r9 = r7.chunkIndex;	 Catch:{ all -> 0x0068 }
        r9 = r9.length;	 Catch:{ all -> 0x0068 }
        r9 = r9 + -1;
        if (r8 != r9) goto L_0x003e;
    L_0x0027:
        r1 = r0.endOffset;	 Catch:{ all -> 0x0068 }
        r9 = r7.chunkIndex;	 Catch:{ all -> 0x0068 }
        r9 = r9.offsets;	 Catch:{ all -> 0x0068 }
        r3 = r9[r8];	 Catch:{ all -> 0x0068 }
        r9 = r7.chunkIndex;	 Catch:{ all -> 0x0068 }
        r9 = r9.sizes;	 Catch:{ all -> 0x0068 }
        r9 = r9[r8];	 Catch:{ all -> 0x0068 }
        r5 = (long) r9;
        r3 = r3 + r5;
        r9 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
        if (r9 != 0) goto L_0x003e;
    L_0x003b:
        r8 = -2;
        monitor-exit(r7);
        return r8;
    L_0x003e:
        r9 = r7.chunkIndex;	 Catch:{ all -> 0x0068 }
        r9 = r9.durationsUs;	 Catch:{ all -> 0x0068 }
        r1 = r9[r8];	 Catch:{ all -> 0x0068 }
        r3 = r0.endOffset;	 Catch:{ all -> 0x0068 }
        r9 = r7.chunkIndex;	 Catch:{ all -> 0x0068 }
        r9 = r9.offsets;	 Catch:{ all -> 0x0068 }
        r5 = r9[r8];	 Catch:{ all -> 0x0068 }
        r9 = 0;
        r3 = r3 - r5;
        r1 = r1 * r3;
        r9 = r7.chunkIndex;	 Catch:{ all -> 0x0068 }
        r9 = r9.sizes;	 Catch:{ all -> 0x0068 }
        r9 = r9[r8];	 Catch:{ all -> 0x0068 }
        r3 = (long) r9;	 Catch:{ all -> 0x0068 }
        r1 = r1 / r3;
        r9 = r7.chunkIndex;	 Catch:{ all -> 0x0068 }
        r9 = r9.timesUs;	 Catch:{ all -> 0x0068 }
        r8 = r9[r8];	 Catch:{ all -> 0x0068 }
        r0 = 0;
        r8 = r8 + r1;
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r8 = r8 / r0;
        r8 = (int) r8;
        monitor-exit(r7);
        return r8;
    L_0x0066:
        monitor-exit(r7);
        return r1;
    L_0x0068:
        r8 = move-exception;
        monitor-exit(r7);
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.getRegionEndTimeMs(long):int");
    }

    public synchronized void onSpanAdded(Cache cache, CacheSpan cacheSpan) {
        mergeSpan(cacheSpan);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onSpanRemoved(com.google.android.exoplayer2.upstream.cache.Cache r7, com.google.android.exoplayer2.upstream.cache.CacheSpan r8) {
        /*
        r6 = this;
        monitor-enter(r6);
        r7 = new com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region;	 Catch:{ all -> 0x006c }
        r0 = r8.position;	 Catch:{ all -> 0x006c }
        r2 = r8.position;	 Catch:{ all -> 0x006c }
        r4 = r8.length;	 Catch:{ all -> 0x006c }
        r8 = 0;
        r2 = r2 + r4;
        r7.<init>(r0, r2);	 Catch:{ all -> 0x006c }
        r8 = r6.regions;	 Catch:{ all -> 0x006c }
        r8 = r8.floor(r7);	 Catch:{ all -> 0x006c }
        r8 = (com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.Region) r8;	 Catch:{ all -> 0x006c }
        if (r8 != 0) goto L_0x0021;
    L_0x0018:
        r7 = "CachedRegionTracker";
        r8 = "Removed a span we were not aware of";
        com.google.android.exoplayer2.util.Log.e(r7, r8);	 Catch:{ all -> 0x006c }
        monitor-exit(r6);
        return;
    L_0x0021:
        r0 = r6.regions;	 Catch:{ all -> 0x006c }
        r0.remove(r8);	 Catch:{ all -> 0x006c }
        r0 = r8.startOffset;	 Catch:{ all -> 0x006c }
        r2 = r7.startOffset;	 Catch:{ all -> 0x006c }
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 >= 0) goto L_0x004d;
    L_0x002e:
        r0 = new com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region;	 Catch:{ all -> 0x006c }
        r1 = r8.startOffset;	 Catch:{ all -> 0x006c }
        r3 = r7.startOffset;	 Catch:{ all -> 0x006c }
        r0.<init>(r1, r3);	 Catch:{ all -> 0x006c }
        r1 = r6.chunkIndex;	 Catch:{ all -> 0x006c }
        r1 = r1.offsets;	 Catch:{ all -> 0x006c }
        r2 = r0.endOffset;	 Catch:{ all -> 0x006c }
        r1 = java.util.Arrays.binarySearch(r1, r2);	 Catch:{ all -> 0x006c }
        if (r1 >= 0) goto L_0x0046;
    L_0x0043:
        r1 = -r1;
        r1 = r1 + -2;
    L_0x0046:
        r0.endOffsetIndex = r1;	 Catch:{ all -> 0x006c }
        r1 = r6.regions;	 Catch:{ all -> 0x006c }
        r1.add(r0);	 Catch:{ all -> 0x006c }
    L_0x004d:
        r0 = r8.endOffset;	 Catch:{ all -> 0x006c }
        r2 = r7.endOffset;	 Catch:{ all -> 0x006c }
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 <= 0) goto L_0x006a;
    L_0x0055:
        r0 = new com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$Region;	 Catch:{ all -> 0x006c }
        r1 = r7.endOffset;	 Catch:{ all -> 0x006c }
        r3 = 1;
        r1 = r1 + r3;
        r3 = r8.endOffset;	 Catch:{ all -> 0x006c }
        r0.<init>(r1, r3);	 Catch:{ all -> 0x006c }
        r7 = r8.endOffsetIndex;	 Catch:{ all -> 0x006c }
        r0.endOffsetIndex = r7;	 Catch:{ all -> 0x006c }
        r7 = r6.regions;	 Catch:{ all -> 0x006c }
        r7.add(r0);	 Catch:{ all -> 0x006c }
    L_0x006a:
        monitor-exit(r6);
        return;
    L_0x006c:
        r7 = move-exception;
        monitor-exit(r6);
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.onSpanRemoved(com.google.android.exoplayer2.upstream.cache.Cache, com.google.android.exoplayer2.upstream.cache.CacheSpan):void");
    }

    private void mergeSpan(CacheSpan cacheSpan) {
        Region region = new Region(cacheSpan.position, cacheSpan.position + cacheSpan.length);
        Region region2 = (Region) this.regions.floor(region);
        Region region3 = (Region) this.regions.ceiling(region);
        boolean regionsConnect = regionsConnect(region2, region);
        if (regionsConnect(region, region3)) {
            if (regionsConnect) {
                region2.endOffset = region3.endOffset;
                region2.endOffsetIndex = region3.endOffsetIndex;
            } else {
                region.endOffset = region3.endOffset;
                region.endOffsetIndex = region3.endOffsetIndex;
                this.regions.add(region);
            }
            this.regions.remove(region3);
        } else if (regionsConnect) {
            region2.endOffset = region.endOffset;
            int i = region2.endOffsetIndex;
            while (i < this.chunkIndex.length - 1) {
                int i2 = i + 1;
                if (this.chunkIndex.offsets[i2] > region2.endOffset) {
                    break;
                }
                i = i2;
            }
            region2.endOffsetIndex = i;
        } else {
            cacheSpan = Arrays.binarySearch(this.chunkIndex.offsets, region.endOffset);
            if (cacheSpan < null) {
                cacheSpan = (-cacheSpan) - 2;
            }
            region.endOffsetIndex = cacheSpan;
            this.regions.add(region);
        }
    }

    private boolean regionsConnect(Region region, Region region2) {
        return (region == null || region2 == null || region.endOffset != region2.startOffset) ? null : true;
    }
}
