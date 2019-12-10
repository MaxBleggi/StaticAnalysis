package com.google.android.libraries.cast.companionlibrary.cast;

import com.google.android.gms.cast.MediaQueueItem;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MediaQueue {
    public static final int INVALID_POSITION = -1;
    private MediaQueueItem mCurrentItem;
    private List<MediaQueueItem> mQueueItems = new CopyOnWriteArrayList();
    private int mRepeatMode;
    private boolean mShuffle;

    public MediaQueue(List<MediaQueueItem> list, MediaQueueItem mediaQueueItem, boolean z, int i) {
        this.mQueueItems = list;
        this.mCurrentItem = mediaQueueItem;
        this.mShuffle = z;
        this.mRepeatMode = i;
    }

    public final List<MediaQueueItem> getQueueItems() {
        return this.mQueueItems;
    }

    public final void setQueueItems(List<MediaQueueItem> list) {
        if (list == null) {
            this.mQueueItems = null;
        } else {
            this.mQueueItems = new CopyOnWriteArrayList(list);
        }
    }

    public final MediaQueueItem getCurrentItem() {
        return this.mCurrentItem;
    }

    public final void setCurrentItem(MediaQueueItem mediaQueueItem) {
        this.mCurrentItem = mediaQueueItem;
    }

    public final boolean isShuffle() {
        return this.mShuffle;
    }

    public final void setShuffle(boolean z) {
        this.mShuffle = z;
    }

    public final int getRepeatMode() {
        return this.mRepeatMode;
    }

    public final void setRepeatMode(int i) {
        this.mRepeatMode = i;
    }

    public final int getCount() {
        if (this.mQueueItems != null) {
            if (!this.mQueueItems.isEmpty()) {
                return this.mQueueItems.size();
            }
        }
        return 0;
    }

    public final boolean isEmpty() {
        if (this.mQueueItems != null) {
            if (!this.mQueueItems.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public final int getCurrentItemPosition() {
        if (this.mQueueItems == null) {
            return -1;
        }
        if (this.mQueueItems.isEmpty()) {
            return 0;
        }
        return this.mQueueItems.indexOf(this.mCurrentItem);
    }
}
