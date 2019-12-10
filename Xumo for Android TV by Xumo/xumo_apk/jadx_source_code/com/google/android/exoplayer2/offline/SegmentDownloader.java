package com.google.android.exoplayer2.offline;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheUtil;
import com.google.android.exoplayer2.upstream.cache.CacheUtil.CachingCounters;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SegmentDownloader<M extends FilterableManifest<M>> implements Downloader {
    private static final int BUFFER_SIZE_BYTES = 131072;
    private final Cache cache;
    private final CacheDataSource dataSource;
    private volatile long downloadedBytes;
    private volatile int downloadedSegments;
    private final AtomicBoolean isCanceled = new AtomicBoolean();
    private final Uri manifestUri;
    private final CacheDataSource offlineDataSource;
    private final PriorityTaskManager priorityTaskManager;
    private final ArrayList<StreamKey> streamKeys;
    private volatile int totalSegments = -1;

    protected static class Segment implements Comparable<Segment> {
        public final DataSpec dataSpec;
        public final long startTimeUs;

        public Segment(long j, DataSpec dataSpec) {
            this.startTimeUs = j;
            this.dataSpec = dataSpec;
        }

        public int compareTo(@NonNull Segment segment) {
            return Util.compareLong(this.startTimeUs, segment.startTimeUs);
        }
    }

    protected abstract M getManifest(DataSource dataSource, Uri uri) throws IOException;

    protected abstract List<Segment> getSegments(DataSource dataSource, M m, boolean z) throws InterruptedException, IOException;

    public SegmentDownloader(Uri uri, List<StreamKey> list, DownloaderConstructorHelper downloaderConstructorHelper) {
        this.manifestUri = uri;
        this.streamKeys = new ArrayList(list);
        this.cache = downloaderConstructorHelper.getCache();
        this.dataSource = downloaderConstructorHelper.buildCacheDataSource(null);
        this.offlineDataSource = downloaderConstructorHelper.buildCacheDataSource(true);
        this.priorityTaskManager = downloaderConstructorHelper.getPriorityTaskManager();
    }

    public final void download() throws IOException, InterruptedException {
        this.priorityTaskManager.add(-1000);
        List initDownload = initDownload();
        Collections.sort(initDownload);
        byte[] bArr = new byte[131072];
        CachingCounters cachingCounters = new CachingCounters();
        int i = 0;
        while (i < initDownload.size()) {
            try {
                CacheUtil.cache(((Segment) initDownload.get(i)).dataSpec, this.cache, this.dataSource, bArr, this.priorityTaskManager, -1000, cachingCounters, this.isCanceled, true);
                this.downloadedSegments++;
                this.downloadedBytes += cachingCounters.newlyCachedBytes;
                i++;
            } catch (Throwable th) {
                this.priorityTaskManager.remove(-1000);
            }
        }
        this.priorityTaskManager.remove(-1000);
    }

    public void cancel() {
        this.isCanceled.set(true);
    }

    public final long getDownloadedBytes() {
        return this.downloadedBytes;
    }

    public final float getDownloadPercentage() {
        int i = this.totalSegments;
        int i2 = this.downloadedSegments;
        if (i != -1) {
            if (i2 != -1) {
                float f = 100.0f;
                if (i != 0) {
                    f = (((float) i2) * 100.0f) / ((float) i);
                }
                return f;
            }
        }
        return -1.0f;
    }

    public final void remove() throws java.lang.InterruptedException {
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
        r0 = r3.offlineDataSource;	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r1 = r3.manifestUri;	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r0 = r3.getManifest(r0, r1);	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r1 = r3.offlineDataSource;	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r2 = 1;	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r0 = r3.getSegments(r1, r0, r2);	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r1 = 0;	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
    L_0x0010:
        r2 = r0.size();	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        if (r1 >= r2) goto L_0x002d;	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
    L_0x0016:
        r2 = r0.get(r1);	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r2 = (com.google.android.exoplayer2.offline.SegmentDownloader.Segment) r2;	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r2 = r2.dataSpec;	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r2 = r2.uri;	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r3.removeUri(r2);	 Catch:{ IOException -> 0x002d, all -> 0x0026 }
        r1 = r1 + 1;
        goto L_0x0010;
    L_0x0026:
        r0 = move-exception;
        r1 = r3.manifestUri;
        r3.removeUri(r1);
        throw r0;
    L_0x002d:
        r0 = r3.manifestUri;
        r3.removeUri(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.SegmentDownloader.remove():void");
    }

    private List<Segment> initDownload() throws IOException, InterruptedException {
        FilterableManifest manifest = getManifest(this.dataSource, this.manifestUri);
        if (!this.streamKeys.isEmpty()) {
            manifest = (FilterableManifest) manifest.copy(this.streamKeys);
        }
        List<Segment> segments = getSegments(this.dataSource, manifest, false);
        CachingCounters cachingCounters = new CachingCounters();
        this.totalSegments = segments.size();
        this.downloadedSegments = 0;
        this.downloadedBytes = 0;
        for (int size = segments.size() - 1; size >= 0; size--) {
            CacheUtil.getCached(((Segment) segments.get(size)).dataSpec, this.cache, cachingCounters);
            this.downloadedBytes += cachingCounters.alreadyCachedBytes;
            if (cachingCounters.alreadyCachedBytes == cachingCounters.contentLength) {
                this.downloadedSegments++;
                segments.remove(size);
            }
        }
        return segments;
    }

    private void removeUri(Uri uri) {
        CacheUtil.remove(this.cache, CacheUtil.generateKey(uri));
    }
}
