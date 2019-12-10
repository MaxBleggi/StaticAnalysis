package com.android.volley;

import com.android.volley.Cache.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class CacheDispatcher extends Thread {
    private static final boolean DEBUG = VolleyLog.DEBUG;
    private final Cache mCache;
    private final BlockingQueue<Request<?>> mCacheQueue;
    private final ResponseDelivery mDelivery;
    private final BlockingQueue<Request<?>> mNetworkQueue;
    private volatile boolean mQuit = false;
    private final WaitingRequestManager mWaitingRequestManager;

    private static class WaitingRequestManager implements NetworkRequestCompleteListener {
        private final CacheDispatcher mCacheDispatcher;
        private final Map<String, List<Request<?>>> mWaitingRequests = new HashMap();

        WaitingRequestManager(CacheDispatcher cacheDispatcher) {
            this.mCacheDispatcher = cacheDispatcher;
        }

        public void onResponseReceived(Request<?> request, Response<?> response) {
            if (response.cacheEntry != null) {
                if (!response.cacheEntry.isExpired()) {
                    List<Request> list;
                    request = request.getCacheKey();
                    synchronized (this) {
                        list = (List) this.mWaitingRequests.remove(request);
                    }
                    if (list != null) {
                        if (VolleyLog.DEBUG) {
                            VolleyLog.v("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf(list.size()), request);
                        }
                        for (Request postResponse : list) {
                            this.mCacheDispatcher.mDelivery.postResponse(postResponse, response);
                        }
                    }
                    return;
                }
            }
            onNoUsableResponseReceived(request);
        }

        public synchronized void onNoUsableResponseReceived(Request<?> request) {
            request = request.getCacheKey();
            List list = (List) this.mWaitingRequests.remove(request);
            if (!(list == null || list.isEmpty())) {
                if (VolleyLog.DEBUG) {
                    VolleyLog.v("%d waiting requests for cacheKey=%s; resend to network", Integer.valueOf(list.size()), request);
                }
                Request request2 = (Request) list.remove(0);
                this.mWaitingRequests.put(request, list);
                request2.setNetworkRequestCompleteListener(this);
                try {
                    this.mCacheDispatcher.mNetworkQueue.put(request2);
                } catch (Request<?> request3) {
                    VolleyLog.e("Couldn't add request to queue. %s", request3.toString());
                    Thread.currentThread().interrupt();
                    this.mCacheDispatcher.quit();
                }
            }
            return;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private synchronized boolean maybeAddToWaitingRequests(com.android.volley.Request<?> r6) {
            /*
            r5 = this;
            monitor-enter(r5);
            r0 = r6.getCacheKey();	 Catch:{ all -> 0x0052 }
            r1 = r5.mWaitingRequests;	 Catch:{ all -> 0x0052 }
            r1 = r1.containsKey(r0);	 Catch:{ all -> 0x0052 }
            r2 = 1;
            r3 = 0;
            if (r1 == 0) goto L_0x003a;
        L_0x000f:
            r1 = r5.mWaitingRequests;	 Catch:{ all -> 0x0052 }
            r1 = r1.get(r0);	 Catch:{ all -> 0x0052 }
            r1 = (java.util.List) r1;	 Catch:{ all -> 0x0052 }
            if (r1 != 0) goto L_0x001e;
        L_0x0019:
            r1 = new java.util.ArrayList;	 Catch:{ all -> 0x0052 }
            r1.<init>();	 Catch:{ all -> 0x0052 }
        L_0x001e:
            r4 = "waiting-for-response";
            r6.addMarker(r4);	 Catch:{ all -> 0x0052 }
            r1.add(r6);	 Catch:{ all -> 0x0052 }
            r6 = r5.mWaitingRequests;	 Catch:{ all -> 0x0052 }
            r6.put(r0, r1);	 Catch:{ all -> 0x0052 }
            r6 = com.android.volley.VolleyLog.DEBUG;	 Catch:{ all -> 0x0052 }
            if (r6 == 0) goto L_0x0038;
        L_0x002f:
            r6 = "Request for cacheKey=%s is in flight, putting on hold.";
            r1 = new java.lang.Object[r2];	 Catch:{ all -> 0x0052 }
            r1[r3] = r0;	 Catch:{ all -> 0x0052 }
            com.android.volley.VolleyLog.d(r6, r1);	 Catch:{ all -> 0x0052 }
        L_0x0038:
            monitor-exit(r5);
            return r2;
        L_0x003a:
            r1 = r5.mWaitingRequests;	 Catch:{ all -> 0x0052 }
            r4 = 0;
            r1.put(r0, r4);	 Catch:{ all -> 0x0052 }
            r6.setNetworkRequestCompleteListener(r5);	 Catch:{ all -> 0x0052 }
            r6 = com.android.volley.VolleyLog.DEBUG;	 Catch:{ all -> 0x0052 }
            if (r6 == 0) goto L_0x0050;
        L_0x0047:
            r6 = "new request, sending to network %s";
            r1 = new java.lang.Object[r2];	 Catch:{ all -> 0x0052 }
            r1[r3] = r0;	 Catch:{ all -> 0x0052 }
            com.android.volley.VolleyLog.d(r6, r1);	 Catch:{ all -> 0x0052 }
        L_0x0050:
            monitor-exit(r5);
            return r3;
        L_0x0052:
            r6 = move-exception;
            monitor-exit(r5);
            throw r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.volley.CacheDispatcher.WaitingRequestManager.maybeAddToWaitingRequests(com.android.volley.Request):boolean");
        }
    }

    public CacheDispatcher(BlockingQueue<Request<?>> blockingQueue, BlockingQueue<Request<?>> blockingQueue2, Cache cache, ResponseDelivery responseDelivery) {
        this.mCacheQueue = blockingQueue;
        this.mNetworkQueue = blockingQueue2;
        this.mCache = cache;
        this.mDelivery = responseDelivery;
        this.mWaitingRequestManager = new WaitingRequestManager(this);
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    public void run() {
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
        r0 = DEBUG;
        if (r0 == 0) goto L_0x000c;
    L_0x0004:
        r0 = "start new dispatcher";
        r1 = 0;
        r1 = new java.lang.Object[r1];
        com.android.volley.VolleyLog.v(r0, r1);
    L_0x000c:
        r0 = 10;
        android.os.Process.setThreadPriority(r0);
        r0 = r2.mCache;
        r0.initialize();
    L_0x0016:
        r2.processRequest();	 Catch:{ InterruptedException -> 0x001a }
        goto L_0x0016;
    L_0x001a:
        r0 = r2.mQuit;
        if (r0 == 0) goto L_0x0016;
    L_0x001e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.CacheDispatcher.run():void");
    }

    private void processRequest() throws InterruptedException {
        final Request request = (Request) this.mCacheQueue.take();
        request.addMarker("cache-queue-take");
        if (request.isCanceled()) {
            request.finish("cache-discard-canceled");
            return;
        }
        Entry entry = this.mCache.get(request.getCacheKey());
        if (entry == null) {
            request.addMarker("cache-miss");
            if (!this.mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                this.mNetworkQueue.put(request);
            }
        } else if (entry.isExpired()) {
            request.addMarker("cache-hit-expired");
            request.setCacheEntry(entry);
            if (!this.mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                this.mNetworkQueue.put(request);
            }
        } else {
            request.addMarker("cache-hit");
            Response parseNetworkResponse = request.parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
            request.addMarker("cache-hit-parsed");
            if (entry.refreshNeeded()) {
                request.addMarker("cache-hit-refresh-needed");
                request.setCacheEntry(entry);
                parseNetworkResponse.intermediate = true;
                if (this.mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                    this.mDelivery.postResponse(request, parseNetworkResponse);
                } else {
                    this.mDelivery.postResponse(request, parseNetworkResponse, new Runnable() {
                        public void run() {
                            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                            /*
                            r2 = this;
                            r0 = com.android.volley.CacheDispatcher.this;	 Catch:{ InterruptedException -> 0x000c }
                            r0 = r0.mNetworkQueue;	 Catch:{ InterruptedException -> 0x000c }
                            r1 = r0;	 Catch:{ InterruptedException -> 0x000c }
                            r0.put(r1);	 Catch:{ InterruptedException -> 0x000c }
                            goto L_0x0013;
                        L_0x000c:
                            r0 = java.lang.Thread.currentThread();
                            r0.interrupt();
                        L_0x0013:
                            return;
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.volley.CacheDispatcher.1.run():void");
                        }
                    });
                }
            } else {
                this.mDelivery.postResponse(request, parseNetworkResponse);
            }
        }
    }
}
