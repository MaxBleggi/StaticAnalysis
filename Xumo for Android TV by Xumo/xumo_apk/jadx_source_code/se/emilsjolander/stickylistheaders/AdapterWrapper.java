package se.emilsjolander.stickylistheaders;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import java.util.LinkedList;
import java.util.List;

class AdapterWrapper extends BaseAdapter implements StickyListHeadersAdapter {
    private final Context mContext;
    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        public void onInvalidated() {
            AdapterWrapper.this.mHeaderCache.clear();
            super.notifyDataSetInvalidated();
        }

        public void onChanged() {
            super.notifyDataSetChanged();
        }
    };
    StickyListHeadersAdapter mDelegate;
    private Drawable mDivider;
    private int mDividerHeight;
    private final List<View> mHeaderCache = new LinkedList();
    private OnHeaderClickListener mOnHeaderClickListener;

    interface OnHeaderClickListener {
        void onHeaderClick(View view, int i, long j);
    }

    AdapterWrapper(Context context, StickyListHeadersAdapter stickyListHeadersAdapter) {
        this.mContext = context;
        this.mDelegate = stickyListHeadersAdapter;
        stickyListHeadersAdapter.registerDataSetObserver(this.mDataSetObserver);
    }

    void setDivider(Drawable drawable, int i) {
        this.mDivider = drawable;
        this.mDividerHeight = i;
        notifyDataSetChanged();
    }

    public boolean areAllItemsEnabled() {
        return this.mDelegate.areAllItemsEnabled();
    }

    public boolean isEnabled(int i) {
        return this.mDelegate.isEnabled(i);
    }

    public int getCount() {
        return this.mDelegate.getCount();
    }

    public Object getItem(int i) {
        return this.mDelegate.getItem(i);
    }

    public long getItemId(int i) {
        return this.mDelegate.getItemId(i);
    }

    public boolean hasStableIds() {
        return this.mDelegate.hasStableIds();
    }

    public int getItemViewType(int i) {
        return this.mDelegate.getItemViewType(i);
    }

    public int getViewTypeCount() {
        return this.mDelegate.getViewTypeCount();
    }

    public boolean isEmpty() {
        return this.mDelegate.isEmpty();
    }

    private void recycleHeaderIfExists(WrapperView wrapperView) {
        wrapperView = wrapperView.mHeader;
        if (wrapperView != null) {
            wrapperView.setVisibility(0);
            this.mHeaderCache.add(wrapperView);
        }
    }

    private View configureHeader(WrapperView wrapperView, final int i) {
        wrapperView = this.mDelegate.getHeaderView(i, wrapperView.mHeader == null ? popHeader() : wrapperView.mHeader, wrapperView);
        if (wrapperView != null) {
            wrapperView.setClickable(true);
            wrapperView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (AdapterWrapper.this.mOnHeaderClickListener != null) {
                        AdapterWrapper.this.mOnHeaderClickListener.onHeaderClick(view, i, AdapterWrapper.this.mDelegate.getHeaderId(i));
                    }
                }
            });
            return wrapperView;
        }
        throw new NullPointerException("Header view must not be null.");
    }

    private View popHeader() {
        return this.mHeaderCache.size() > 0 ? (View) this.mHeaderCache.remove(0) : null;
    }

    private boolean previousPositionHasSameHeader(int i) {
        if (i == 0 || this.mDelegate.getHeaderId(i) != this.mDelegate.getHeaderId(i - 1)) {
            return false;
        }
        return true;
    }

    public WrapperView getView(int i, View view, ViewGroup viewGroup) {
        view = view == null ? new WrapperView(this.mContext) : (WrapperView) view;
        viewGroup = this.mDelegate.getView(i, view.mItem, viewGroup);
        View view2 = null;
        if (previousPositionHasSameHeader(i)) {
            recycleHeaderIfExists(view);
        } else {
            view2 = configureHeader(view, i);
        }
        i = viewGroup instanceof Checkable;
        if (i != 0 && !(view instanceof CheckableWrapperView)) {
            view = new CheckableWrapperView(this.mContext);
        } else if (i == 0 && (view instanceof CheckableWrapperView) != 0) {
            view = new WrapperView(this.mContext);
        }
        view.update(viewGroup, view2, this.mDivider, this.mDividerHeight);
        return view;
    }

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        this.mOnHeaderClickListener = onHeaderClickListener;
    }

    public boolean equals(Object obj) {
        return this.mDelegate.equals(obj);
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return ((BaseAdapter) this.mDelegate).getDropDownView(i, view, viewGroup);
    }

    public int hashCode() {
        return this.mDelegate.hashCode();
    }

    public void notifyDataSetChanged() {
        ((BaseAdapter) this.mDelegate).notifyDataSetChanged();
    }

    public void notifyDataSetInvalidated() {
        ((BaseAdapter) this.mDelegate).notifyDataSetInvalidated();
    }

    public String toString() {
        return this.mDelegate.toString();
    }

    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        return this.mDelegate.getHeaderView(i, view, viewGroup);
    }

    public long getHeaderId(int i) {
        return this.mDelegate.getHeaderId(i);
    }
}
