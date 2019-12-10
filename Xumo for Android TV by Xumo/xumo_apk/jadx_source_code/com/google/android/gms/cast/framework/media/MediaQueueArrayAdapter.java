package com.google.android.gms.cast.framework.media;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.framework.media.MediaQueue.Callback;

public class MediaQueueArrayAdapter extends ArrayAdapter<MediaQueueItem> {
    private final MediaQueue zzng;
    private final Callback zznh = new zza();

    private class zza extends Callback {
        private final /* synthetic */ MediaQueueArrayAdapter zzni;

        private zza(MediaQueueArrayAdapter mediaQueueArrayAdapter) {
            this.zzni = mediaQueueArrayAdapter;
        }

        public final void mediaQueueWillChange() {
        }

        public final void mediaQueueChanged() {
            this.zzni.notifyDataSetChanged();
        }

        public final void itemsReloaded() {
            this.zzni.notifyDataSetChanged();
        }

        public final void itemsInsertedInRange(int i, int i2) {
            this.zzni.notifyDataSetChanged();
        }

        public final void itemsUpdatedAtIndexes(int[] iArr) {
            this.zzni.notifyDataSetChanged();
        }

        public final void itemsRemovedAtIndexes(int[] iArr) {
            this.zzni.notifyDataSetChanged();
        }
    }

    public MediaQueueArrayAdapter(MediaQueue mediaQueue, Context context, int i) {
        super(context, i);
        this.zzng = mediaQueue;
        this.zzng.registerCallback(this.zznh);
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void dispose() {
        this.zzng.unregisterCallback(this.zznh);
    }

    public boolean isEnabled(int i) {
        return this.zzng.getItemAtIndex(i, false) != 0;
    }

    public int getCount() {
        return this.zzng.getItemCount();
    }

    public MediaQueueItem getItem(int i) {
        return this.zzng.getItemAtIndex(i);
    }

    public long getItemId(int i) {
        return (long) this.zzng.itemIdAtIndex(i);
    }

    public boolean isEmpty() {
        return this.zzng.getItemCount() == 0;
    }

    public MediaQueue getMediaQueue() {
        return this.zzng;
    }
}
