package com.google.android.exoplayer2.upstream.cache;

import android.os.ConditionVariable;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.upstream.cache.Cache.CacheException;
import com.google.android.exoplayer2.upstream.cache.Cache.Listener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public final class SimpleCache implements Cache {
    private static final String TAG = "SimpleCache";
    private static boolean cacheFolderLockingDisabled;
    private static final HashSet<File> lockedCacheDirs = new HashSet();
    private final File cacheDir;
    private final CacheEvictor evictor;
    private final CachedContentIndex index;
    private final HashMap<String, ArrayList<Listener>> listeners;
    private boolean released;
    private long totalSpace;

    public static synchronized boolean isCacheFolderLocked(File file) {
        synchronized (SimpleCache.class) {
            file = lockedCacheDirs.contains(file.getAbsoluteFile());
        }
        return file;
    }

    @Deprecated
    public static synchronized void disableCacheFolderLocking() {
        synchronized (SimpleCache.class) {
            cacheFolderLockingDisabled = true;
            lockedCacheDirs.clear();
        }
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor) {
        this(file, cacheEvictor, null, false);
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor, byte[] bArr) {
        this(file, cacheEvictor, bArr, bArr != null);
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor, byte[] bArr, boolean z) {
        this(file, cacheEvictor, new CachedContentIndex(file, bArr, z));
    }

    SimpleCache(File file, CacheEvictor cacheEvictor, CachedContentIndex cachedContentIndex) {
        if (lockFolder(file)) {
            this.cacheDir = file;
            this.evictor = cacheEvictor;
            this.index = cachedContentIndex;
            this.listeners = new HashMap();
            file = new ConditionVariable();
            new Thread("SimpleCache.initialize()") {
                public void run() {
                    synchronized (SimpleCache.this) {
                        file.open();
                        SimpleCache.this.initialize();
                        SimpleCache.this.evictor.onCacheInitialized();
                    }
                }
            }.start();
            file.block();
            return;
        }
        cachedContentIndex = new StringBuilder();
        cachedContentIndex.append("Another SimpleCache instance uses the folder: ");
        cachedContentIndex.append(file);
        throw new IllegalStateException(cachedContentIndex.toString());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void release() throws com.google.android.exoplayer2.upstream.cache.Cache.CacheException {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.released;	 Catch:{ all -> 0x0022 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r3);
        return;
    L_0x0007:
        r0 = r3.listeners;	 Catch:{ all -> 0x0022 }
        r0.clear();	 Catch:{ all -> 0x0022 }
        r0 = 1;
        r3.removeStaleSpansAndCachedContents();	 Catch:{ all -> 0x0019 }
        r1 = r3.cacheDir;	 Catch:{ all -> 0x0022 }
        unlockFolder(r1);	 Catch:{ all -> 0x0022 }
        r3.released = r0;	 Catch:{ all -> 0x0022 }
        monitor-exit(r3);
        return;
    L_0x0019:
        r1 = move-exception;
        r2 = r3.cacheDir;	 Catch:{ all -> 0x0022 }
        unlockFolder(r2);	 Catch:{ all -> 0x0022 }
        r3.released = r0;	 Catch:{ all -> 0x0022 }
        throw r1;	 Catch:{ all -> 0x0022 }
    L_0x0022:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.SimpleCache.release():void");
    }

    public synchronized NavigableSet<CacheSpan> addListener(String str, Listener listener) {
        Assertions.checkState(this.released ^ 1);
        ArrayList arrayList = (ArrayList) this.listeners.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.listeners.put(str, arrayList);
        }
        arrayList.add(listener);
        return getCachedSpans(str);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void removeListener(java.lang.String r2, com.google.android.exoplayer2.upstream.cache.Cache.Listener r3) {
        /*
        r1 = this;
        monitor-enter(r1);
        r0 = r1.released;	 Catch:{ all -> 0x0021 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r1);
        return;
    L_0x0007:
        r0 = r1.listeners;	 Catch:{ all -> 0x0021 }
        r0 = r0.get(r2);	 Catch:{ all -> 0x0021 }
        r0 = (java.util.ArrayList) r0;	 Catch:{ all -> 0x0021 }
        if (r0 == 0) goto L_0x001f;
    L_0x0011:
        r0.remove(r3);	 Catch:{ all -> 0x0021 }
        r3 = r0.isEmpty();	 Catch:{ all -> 0x0021 }
        if (r3 == 0) goto L_0x001f;
    L_0x001a:
        r3 = r1.listeners;	 Catch:{ all -> 0x0021 }
        r3.remove(r2);	 Catch:{ all -> 0x0021 }
    L_0x001f:
        monitor-exit(r1);
        return;
    L_0x0021:
        r2 = move-exception;
        monitor-exit(r1);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.SimpleCache.removeListener(java.lang.String, com.google.android.exoplayer2.upstream.cache.Cache$Listener):void");
    }

    @NonNull
    public synchronized NavigableSet<CacheSpan> getCachedSpans(String str) {
        NavigableSet<CacheSpan> treeSet;
        Assertions.checkState(this.released ^ 1);
        str = this.index.get(str);
        if (str != null) {
            if (!str.isEmpty()) {
                treeSet = new TreeSet(str.getSpans());
            }
        }
        treeSet = new TreeSet();
        return treeSet;
    }

    public synchronized Set<String> getKeys() {
        Assertions.checkState(this.released ^ 1);
        return new HashSet(this.index.getKeys());
    }

    public synchronized long getCacheSpace() {
        Assertions.checkState(this.released ^ 1);
        return this.totalSpace;
    }

    public synchronized SimpleCacheSpan startReadWrite(String str, long j) throws InterruptedException, CacheException {
        SimpleCacheSpan startReadWriteNonBlocking;
        while (true) {
            startReadWriteNonBlocking = startReadWriteNonBlocking(str, j);
            if (startReadWriteNonBlocking == null) {
                wait();
            }
        }
        return startReadWriteNonBlocking;
    }

    @androidx.annotation.Nullable
    public synchronized com.google.android.exoplayer2.upstream.cache.SimpleCacheSpan startReadWriteNonBlocking(java.lang.String r3, long r4) throws com.google.android.exoplayer2.upstream.cache.Cache.CacheException {
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
        r2 = this;
        monitor-enter(r2);
        r0 = r2.released;	 Catch:{ all -> 0x0035 }
        r1 = 1;	 Catch:{ all -> 0x0035 }
        r0 = r0 ^ r1;	 Catch:{ all -> 0x0035 }
        com.google.android.exoplayer2.util.Assertions.checkState(r0);	 Catch:{ all -> 0x0035 }
        r4 = r2.getSpan(r3, r4);	 Catch:{ all -> 0x0035 }
        r5 = r4.isCached;	 Catch:{ all -> 0x0035 }
        if (r5 == 0) goto L_0x0021;
    L_0x0010:
        r5 = r2.index;	 Catch:{ CacheException -> 0x001f }
        r3 = r5.get(r3);	 Catch:{ CacheException -> 0x001f }
        r3 = r3.touch(r4);	 Catch:{ CacheException -> 0x001f }
        r2.notifySpanTouched(r4, r3);	 Catch:{ CacheException -> 0x001f }
        monitor-exit(r2);
        return r3;
    L_0x001f:
        monitor-exit(r2);
        return r4;
    L_0x0021:
        r5 = r2.index;	 Catch:{ all -> 0x0035 }
        r3 = r5.getOrAdd(r3);	 Catch:{ all -> 0x0035 }
        r5 = r3.isLocked();	 Catch:{ all -> 0x0035 }
        if (r5 != 0) goto L_0x0032;	 Catch:{ all -> 0x0035 }
    L_0x002d:
        r3.setLocked(r1);	 Catch:{ all -> 0x0035 }
        monitor-exit(r2);
        return r4;
    L_0x0032:
        r3 = 0;
        monitor-exit(r2);
        return r3;
    L_0x0035:
        r3 = move-exception;
        monitor-exit(r2);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.SimpleCache.startReadWriteNonBlocking(java.lang.String, long):com.google.android.exoplayer2.upstream.cache.SimpleCacheSpan");
    }

    public synchronized File startFile(String str, long j, long j2) throws CacheException {
        CachedContent cachedContent;
        Assertions.checkState(this.released ^ 1);
        cachedContent = this.index.get(str);
        Assertions.checkNotNull(cachedContent);
        Assertions.checkState(cachedContent.isLocked());
        if (!this.cacheDir.exists()) {
            this.cacheDir.mkdirs();
            removeStaleSpansAndCachedContents();
        }
        this.evictor.onStartFile(this, str, j, j2);
        return SimpleCacheSpan.getCacheFile(this.cacheDir, cachedContent.id, j, System.currentTimeMillis());
    }

    public synchronized void commitFile(File file) throws CacheException {
        boolean z = true;
        Assertions.checkState(this.released ^ true);
        SimpleCacheSpan createCacheEntry = SimpleCacheSpan.createCacheEntry(file, this.index);
        Assertions.checkState(createCacheEntry != null);
        CachedContent cachedContent = this.index.get(createCacheEntry.key);
        Assertions.checkNotNull(cachedContent);
        Assertions.checkState(cachedContent.isLocked());
        if (!file.exists()) {
            return;
        }
        if (file.length() == 0) {
            file.delete();
            return;
        }
        long contentLength = ContentMetadataInternal.getContentLength(cachedContent.getMetadata());
        if (contentLength != -1) {
            if (createCacheEntry.position + createCacheEntry.length > contentLength) {
                z = false;
            }
            Assertions.checkState(z);
        }
        addSpan(createCacheEntry);
        this.index.store();
        notifyAll();
    }

    public synchronized void releaseHoleSpan(CacheSpan cacheSpan) {
        Assertions.checkState(this.released ^ 1);
        cacheSpan = this.index.get(cacheSpan.key);
        Assertions.checkNotNull(cacheSpan);
        Assertions.checkState(cacheSpan.isLocked());
        cacheSpan.setLocked(false);
        this.index.maybeRemove(cacheSpan.key);
        notifyAll();
    }

    public synchronized void removeSpan(CacheSpan cacheSpan) throws CacheException {
        Assertions.checkState(this.released ^ true);
        removeSpan(cacheSpan, true);
    }

    public synchronized boolean isCached(String str, long j, long j2) {
        boolean z;
        z = true;
        Assertions.checkState(this.released ^ true);
        str = this.index.get(str);
        if (str == null || str.getCachedBytesLength(j, j2) < j2) {
            z = false;
        }
        return z;
    }

    public synchronized long getCachedLength(String str, long j, long j2) {
        Assertions.checkState(this.released ^ 1);
        str = this.index.get(str);
        return str != null ? str.getCachedBytesLength(j, j2) : -j2;
    }

    public synchronized void setContentLength(String str, long j) throws CacheException {
        ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
        ContentMetadataInternal.setContentLength(contentMetadataMutations, j);
        applyContentMetadataMutations(str, contentMetadataMutations);
    }

    public synchronized long getContentLength(String str) {
        return ContentMetadataInternal.getContentLength(getContentMetadata(str));
    }

    public synchronized void applyContentMetadataMutations(String str, ContentMetadataMutations contentMetadataMutations) throws CacheException {
        Assertions.checkState(this.released ^ 1);
        this.index.applyContentMetadataMutations(str, contentMetadataMutations);
        this.index.store();
    }

    public synchronized ContentMetadata getContentMetadata(String str) {
        Assertions.checkState(this.released ^ 1);
        return this.index.getContentMetadata(str);
    }

    private SimpleCacheSpan getSpan(String str, long j) throws CacheException {
        CachedContent cachedContent = this.index.get(str);
        if (cachedContent == null) {
            return SimpleCacheSpan.createOpenHole(str, j);
        }
        while (true) {
            str = cachedContent.getSpan(j);
            if (!str.isCached || str.file.exists()) {
                return str;
            }
            removeStaleSpansAndCachedContents();
        }
        return str;
    }

    private void initialize() {
        if (this.cacheDir.exists()) {
            this.index.load();
            File[] listFiles = this.cacheDir.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (!file.getName().equals(CachedContentIndex.FILE_NAME)) {
                        SimpleCacheSpan createCacheEntry = file.length() > 0 ? SimpleCacheSpan.createCacheEntry(file, this.index) : null;
                        if (createCacheEntry != null) {
                            addSpan(createCacheEntry);
                        } else {
                            file.delete();
                        }
                    }
                }
                this.index.removeEmpty();
                try {
                    this.index.store();
                } catch (Throwable e) {
                    Log.e(TAG, "Storing index file failed", e);
                }
                return;
            }
            return;
        }
        this.cacheDir.mkdirs();
    }

    private void addSpan(SimpleCacheSpan simpleCacheSpan) {
        this.index.getOrAdd(simpleCacheSpan.key).addSpan(simpleCacheSpan);
        this.totalSpace += simpleCacheSpan.length;
        notifySpanAdded(simpleCacheSpan);
    }

    private void removeSpan(CacheSpan cacheSpan, boolean z) throws CacheException {
        CachedContent cachedContent = this.index.get(cacheSpan.key);
        if (cachedContent != null) {
            if (cachedContent.removeSpan(cacheSpan)) {
                this.totalSpace -= cacheSpan.length;
                if (z) {
                    try {
                        this.index.maybeRemove(cachedContent.key);
                        this.index.store();
                    } catch (Throwable th) {
                        notifySpanRemoved(cacheSpan);
                    }
                }
                notifySpanRemoved(cacheSpan);
            }
        }
    }

    private void removeStaleSpansAndCachedContents() throws CacheException {
        ArrayList arrayList = new ArrayList();
        for (CachedContent spans : this.index.getAll()) {
            Iterator it = spans.getSpans().iterator();
            while (it.hasNext()) {
                CacheSpan cacheSpan = (CacheSpan) it.next();
                if (!cacheSpan.file.exists()) {
                    arrayList.add(cacheSpan);
                }
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            removeSpan((CacheSpan) arrayList.get(i), false);
        }
        this.index.removeEmpty();
        this.index.store();
    }

    private void notifySpanRemoved(CacheSpan cacheSpan) {
        ArrayList arrayList = (ArrayList) this.listeners.get(cacheSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((Listener) arrayList.get(size)).onSpanRemoved(this, cacheSpan);
            }
        }
        this.evictor.onSpanRemoved(this, cacheSpan);
    }

    private void notifySpanAdded(SimpleCacheSpan simpleCacheSpan) {
        ArrayList arrayList = (ArrayList) this.listeners.get(simpleCacheSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((Listener) arrayList.get(size)).onSpanAdded(this, simpleCacheSpan);
            }
        }
        this.evictor.onSpanAdded(this, simpleCacheSpan);
    }

    private void notifySpanTouched(SimpleCacheSpan simpleCacheSpan, CacheSpan cacheSpan) {
        ArrayList arrayList = (ArrayList) this.listeners.get(simpleCacheSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((Listener) arrayList.get(size)).onSpanTouched(this, simpleCacheSpan, cacheSpan);
            }
        }
        this.evictor.onSpanTouched(this, simpleCacheSpan, cacheSpan);
    }

    private static synchronized boolean lockFolder(File file) {
        synchronized (SimpleCache.class) {
            if (cacheFolderLockingDisabled) {
                return true;
            }
            file = lockedCacheDirs.add(file.getAbsoluteFile());
            return file;
        }
    }

    private static synchronized void unlockFolder(File file) {
        synchronized (SimpleCache.class) {
            if (!cacheFolderLockingDisabled) {
                lockedCacheDirs.remove(file.getAbsoluteFile());
            }
        }
    }
}
