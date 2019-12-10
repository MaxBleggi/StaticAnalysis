package com.google.android.gms.cast.framework.media;

import android.util.LruCache;
import com.google.android.gms.cast.MediaQueueItem;

final class zzl extends LruCache<Integer, MediaQueueItem> {
    private final /* synthetic */ MediaQueue zznf;

    zzl(MediaQueue mediaQueue, int i) {
        this.zznf = mediaQueue;
        super(i);
    }

    protected final /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
        Integer num = (Integer) obj;
        if (z) {
            this.zznf.zzmu.add(num);
        }
    }
}
