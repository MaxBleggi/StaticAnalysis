package com.google.android.gms.cast.framework.media;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.framework.media.MediaQueue.Callback;

public abstract class MediaQueueRecyclerViewAdapter<VH extends ViewHolder> extends Adapter<VH> {
    private final MediaQueue zzng;
    private final Callback zznh = new zza();

    private class zza extends Callback {
        private final /* synthetic */ MediaQueueRecyclerViewAdapter zznj;

        private zza(MediaQueueRecyclerViewAdapter mediaQueueRecyclerViewAdapter) {
            this.zznj = mediaQueueRecyclerViewAdapter;
        }

        public final void mediaQueueChanged() {
        }

        public final void mediaQueueWillChange() {
        }

        public final void itemsReloaded() {
            this.zznj.notifyDataSetChanged();
        }

        public final void itemsInsertedInRange(int i, int i2) {
            this.zznj.notifyItemRangeInserted(i, i2);
        }

        public final void itemsUpdatedAtIndexes(int[] iArr) {
            for (int notifyItemChanged : iArr) {
                this.zznj.notifyItemChanged(notifyItemChanged);
            }
        }

        public final void itemsRemovedAtIndexes(int[] iArr) {
            if (iArr.length > 1) {
                this.zznj.notifyDataSetChanged();
                return;
            }
            for (int notifyItemRemoved : iArr) {
                this.zznj.notifyItemRemoved(notifyItemRemoved);
            }
        }
    }

    public MediaQueueRecyclerViewAdapter(MediaQueue mediaQueue) {
        this.zzng = mediaQueue;
        this.zzng.registerCallback(this.zznh);
    }

    public void dispose() {
        this.zzng.unregisterCallback(this.zznh);
    }

    public int getItemCount() {
        return this.zzng.getItemCount();
    }

    public long getItemId(int i) {
        return (long) this.zzng.itemIdAtIndex(i);
    }

    public MediaQueueItem getItem(int i) {
        return this.zzng.getItemAtIndex(i);
    }

    public MediaQueue getMediaQueue() {
        return this.zzng;
    }
}
