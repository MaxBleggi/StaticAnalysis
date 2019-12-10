package se.emilsjolander.stickylistheaders;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

class ExpandableStickyListHeadersAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    List<Long> mCollapseHeaderIds = new ArrayList();
    DistinctMultiHashMap<Integer, View> mHeaderIdToViewMap = new DistinctMultiHashMap();
    private final StickyListHeadersAdapter mInnerAdapter;
    DualHashMap<View, Long> mViewToItemIdMap = new DualHashMap();

    ExpandableStickyListHeadersAdapter(StickyListHeadersAdapter stickyListHeadersAdapter) {
        this.mInnerAdapter = stickyListHeadersAdapter;
    }

    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        return this.mInnerAdapter.getHeaderView(i, view, viewGroup);
    }

    public long getHeaderId(int i) {
        return this.mInnerAdapter.getHeaderId(i);
    }

    public boolean areAllItemsEnabled() {
        return this.mInnerAdapter.areAllItemsEnabled();
    }

    public boolean isEnabled(int i) {
        return this.mInnerAdapter.isEnabled(i);
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mInnerAdapter.registerDataSetObserver(dataSetObserver);
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mInnerAdapter.unregisterDataSetObserver(dataSetObserver);
    }

    public int getCount() {
        return this.mInnerAdapter.getCount();
    }

    public Object getItem(int i) {
        return this.mInnerAdapter.getItem(i);
    }

    public long getItemId(int i) {
        return this.mInnerAdapter.getItemId(i);
    }

    public boolean hasStableIds() {
        return this.mInnerAdapter.hasStableIds();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.mInnerAdapter.getView(i, view, viewGroup);
        this.mViewToItemIdMap.put(view, Long.valueOf(getItemId(i)));
        this.mHeaderIdToViewMap.add(Integer.valueOf((int) getHeaderId(i)), view);
        if (this.mCollapseHeaderIds.contains(Long.valueOf(getHeaderId(i))) != 0) {
            view.setVisibility(8);
        } else {
            view.setVisibility(0);
        }
        return view;
    }

    public int getItemViewType(int i) {
        return this.mInnerAdapter.getItemViewType(i);
    }

    public int getViewTypeCount() {
        return this.mInnerAdapter.getViewTypeCount();
    }

    public boolean isEmpty() {
        return this.mInnerAdapter.isEmpty();
    }

    public List<View> getItemViewsByHeaderId(long j) {
        return this.mHeaderIdToViewMap.get(Integer.valueOf((int) j));
    }

    public boolean isHeaderCollapsed(long j) {
        return this.mCollapseHeaderIds.contains(Long.valueOf(j));
    }

    public void expand(long j) {
        if (isHeaderCollapsed(j)) {
            this.mCollapseHeaderIds.remove(Long.valueOf(j));
        }
    }

    public void collapse(long j) {
        if (!isHeaderCollapsed(j)) {
            this.mCollapseHeaderIds.add(Long.valueOf(j));
        }
    }

    public View findViewByItemId(long j) {
        return (View) this.mViewToItemIdMap.getKey(Long.valueOf(j));
    }

    public long findItemIdByView(View view) {
        return ((Long) this.mViewToItemIdMap.get(view)).longValue();
    }
}
