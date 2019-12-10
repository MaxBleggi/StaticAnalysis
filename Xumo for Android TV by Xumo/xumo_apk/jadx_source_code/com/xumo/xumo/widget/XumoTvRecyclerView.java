package com.xumo.xumo.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.xumo.xumo.util.LogUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class XumoTvRecyclerView extends RecyclerView {
    private static final String TAG = "com.xumo.xumo.widget.XumoRecyclerView";
    private int selectedItemIndex;

    public static abstract class ListAdapter<T, VH extends ViewHolder> extends Adapter<VH> {
        private List<T> mObjects = new ArrayList();
        private int selectedItemIndex = -1;

        public long getItemId(int i) {
            return (long) i;
        }

        public ListAdapter() {
            super.setHasStableIds(true);
        }

        public void add(@Nullable T t) {
            this.mObjects.add(t);
            super.notifyItemInserted(getItemCount() - 1);
        }

        public void add(int i, @Nullable T t) {
            add(i, t, true);
        }

        public void add(int i, @Nullable T t, boolean z) {
            this.mObjects.add(i, t);
            super.notifyItemInserted(i);
            if (z && i <= this.selectedItemIndex) {
                this.selectedItemIndex++;
            }
        }

        public void addAll(@NonNull List<T> list) {
            int size = list.size();
            this.mObjects.addAll(list);
            super.notifyItemRangeInserted(getItemCount() - size, size);
        }

        public void addAll(int i, @NonNull List<T> list) {
            addAll(i, list, true);
        }

        public void addAll(int i, @NonNull List<T> list, boolean z) {
            int size = list.size();
            this.mObjects.addAll(i, list);
            super.notifyItemRangeInserted(i, size);
            if (z && i <= this.selectedItemIndex) {
                this.selectedItemIndex += size;
            }
        }

        public void set(int i, @Nullable T t) {
            if (-1 < i && i < this.mObjects.size()) {
                this.mObjects.set(i, t);
                super.notifyItemInserted(i);
            }
        }

        public int update(@NonNull List<T> list) {
            if (this.mObjects.size() > 0) {
                this.mObjects.clear();
            }
            this.mObjects.addAll(list);
            super.notifyDataSetChanged();
            if (getItemCount() <= this.selectedItemIndex) {
                this.selectedItemIndex = getItemCount() - 1;
            }
            return getItemCount();
        }

        public int remove(int i) {
            return remove(i, true);
        }

        public int remove(int i, boolean z) {
            if (-1 >= i || i >= getItemCount()) {
                return -1;
            }
            this.mObjects.remove(i);
            super.notifyItemRemoved(i);
            if (z && this.selectedItemIndex <= false && i <= this.selectedItemIndex) {
                this.selectedItemIndex--;
            }
            return i;
        }

        public int remove(@Nullable T t) {
            return remove(this.mObjects.indexOf(t), true);
        }

        public int remove(@Nullable T t, boolean z) {
            return remove(this.mObjects.indexOf(t), z);
        }

        public void removeAll() {
            this.mObjects.clear();
            super.notifyDataSetChanged();
            this.selectedItemIndex = -1;
        }

        public void sort(@NonNull Comparator<? super T> comparator) {
            Collections.sort(this.mObjects, comparator);
            notifyDataSetChanged();
        }

        public int getSelectedItemIndex() {
            return this.selectedItemIndex;
        }

        public void setSelectedItemIndex(int i) {
            this.selectedItemIndex = i;
        }

        public void notifySelectedItemChanged() {
            super.notifyItemChanged(this.selectedItemIndex);
        }

        @Nullable
        public T getItem(int i) {
            return (-1 >= i || i >= getItemCount()) ? null : this.mObjects.get(i);
        }

        public int getItemIndex(@Nullable T t) {
            return this.mObjects != null ? this.mObjects.indexOf(t) : -1;
        }

        public int getItemCount() {
            return this.mObjects != null ? this.mObjects.size() : 0;
        }
    }

    public XumoTvRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public XumoTvRecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XumoTvRecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.selectedItemIndex = -1;
        setItemAnimator(null);
    }

    public void setListAdapter(@Nullable ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
    }

    public ListAdapter getListAdapter() {
        return getAdapter() instanceof ListAdapter ? (ListAdapter) getAdapter() : null;
    }

    public int selectItem(int i) {
        if (getAdapter() == null) {
            LogUtil.e(TAG, "Cannot scroll without a Adapter set. Call setAdapter with a non-null argument.");
            return -1;
        } else if (-1 >= i || i >= getAdapter().getItemCount()) {
            return -1;
        } else {
            setSelectedItemIndex(i);
            scrollToPositionWithOffset(getSelectedItemIndex(), 0);
            getAdapter().notifyDataSetChanged();
            return getSelectedItemIndex();
        }
    }

    public int selectPrevItem() {
        return selectItem(getSelectedItemIndex() - 1);
    }

    public int selectNextItem() {
        return selectItem(getSelectedItemIndex() + 1);
    }

    public void selectGenreItem(boolean z) {
        if (z) {
            z = getSelectedItemIndex() - 1;
        } else {
            z = getSelectedItemIndex() + 1;
        }
        if (getAdapter() == null) {
            LogUtil.e(TAG, "Cannot scroll without a Adapter set. Call setAdapter with a non-null argument.");
        } else if (true < z && z < getAdapter().getItemCount()) {
            setSelectedItemIndex(z);
            scrollToPosition(getSelectedItemIndex());
            getAdapter().notifyDataSetChanged();
        }
    }

    public void updateSelectedItem() {
        scrollToPositionWithOffset(getSelectedItemIndex(), 0);
        getAdapter().notifyDataSetChanged();
    }

    public int getSelectedItemIndex() {
        if (getListAdapter() != null) {
            return getListAdapter().getSelectedItemIndex();
        }
        return this.selectedItemIndex;
    }

    public void setSelectedItemIndex(int i) {
        if (getListAdapter() != null) {
            getListAdapter().setSelectedItemIndex(i);
        } else {
            this.selectedItemIndex = i;
        }
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) {
            LogUtil.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (layoutManager != null && (layoutManager instanceof LinearLayoutManager)) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(i, i2);
        }
    }
}
