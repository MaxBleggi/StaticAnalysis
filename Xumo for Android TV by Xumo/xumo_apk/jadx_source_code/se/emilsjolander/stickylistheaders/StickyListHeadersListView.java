package se.emilsjolander.stickylistheaders;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;

public class StickyListHeadersListView extends FrameLayout {
    private AdapterWrapper mAdapter;
    private boolean mAreHeadersSticky;
    private boolean mClippingToPadding;
    private AdapterWrapperDataSetObserver mDataSetObserver;
    private Drawable mDivider;
    private int mDividerHeight;
    private float mDownY;
    private View mHeader;
    private Long mHeaderId;
    private Integer mHeaderOffset;
    private boolean mHeaderOwnsTouch;
    private Integer mHeaderPosition;
    private boolean mIsDrawingListUnderStickyHeader;
    private WrapperViewList mList;
    private OnHeaderClickListener mOnHeaderClickListener;
    private OnScrollListener mOnScrollListenerDelegate;
    private OnStickyHeaderChangedListener mOnStickyHeaderChangedListener;
    private OnStickyHeaderOffsetChangedListener mOnStickyHeaderOffsetChangedListener;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mStickyHeaderTopOffset;
    private float mTouchSlop;

    private class AdapterWrapperDataSetObserver extends DataSetObserver {
        private AdapterWrapperDataSetObserver() {
        }

        public void onChanged() {
            StickyListHeadersListView.this.clearHeader();
        }

        public void onInvalidated() {
            StickyListHeadersListView.this.clearHeader();
        }
    }

    public interface OnHeaderClickListener {
        void onHeaderClick(StickyListHeadersListView stickyListHeadersListView, View view, int i, long j, boolean z);
    }

    public interface OnStickyHeaderChangedListener {
        void onStickyHeaderChanged(StickyListHeadersListView stickyListHeadersListView, View view, int i, long j);
    }

    public interface OnStickyHeaderOffsetChangedListener {
        void onStickyHeaderOffsetChanged(StickyListHeadersListView stickyListHeadersListView, View view, int i);
    }

    private class WrapperListScrollListener implements OnScrollListener {
        private WrapperListScrollListener() {
        }

        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            if (StickyListHeadersListView.this.mOnScrollListenerDelegate != null) {
                StickyListHeadersListView.this.mOnScrollListenerDelegate.onScroll(absListView, i, i2, i3);
            }
            StickyListHeadersListView.this.updateOrClearHeader(StickyListHeadersListView.this.mList.getFixedFirstVisibleItem());
        }

        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (StickyListHeadersListView.this.mOnScrollListenerDelegate != null) {
                StickyListHeadersListView.this.mOnScrollListenerDelegate.onScrollStateChanged(absListView, i);
            }
        }
    }

    private class AdapterWrapperHeaderClickHandler implements OnHeaderClickListener {
        private AdapterWrapperHeaderClickHandler() {
        }

        public void onHeaderClick(View view, int i, long j) {
            StickyListHeadersListView.this.mOnHeaderClickListener.onHeaderClick(StickyListHeadersListView.this, view, i, j, false);
        }
    }

    private class WrapperViewListLifeCycleListener implements LifeCycleListener {
        private WrapperViewListLifeCycleListener() {
        }

        public void onDispatchDrawOccurred(Canvas canvas) {
            if (VERSION.SDK_INT < 8) {
                StickyListHeadersListView.this.updateOrClearHeader(StickyListHeadersListView.this.mList.getFixedFirstVisibleItem());
            }
            if (StickyListHeadersListView.this.mHeader == null) {
                return;
            }
            if (StickyListHeadersListView.this.mClippingToPadding) {
                canvas.save();
                canvas.clipRect(0, StickyListHeadersListView.this.mPaddingTop, StickyListHeadersListView.this.getRight(), StickyListHeadersListView.this.getBottom());
                StickyListHeadersListView.this.drawChild(canvas, StickyListHeadersListView.this.mHeader, 0);
                canvas.restore();
                return;
            }
            StickyListHeadersListView.this.drawChild(canvas, StickyListHeadersListView.this.mHeader, 0);
        }
    }

    public StickyListHeadersListView(Context context) {
        this(context, null);
    }

    public StickyListHeadersListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.stickyListHeadersListViewStyle);
    }

    @TargetApi(11)
    public StickyListHeadersListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAreHeadersSticky = true;
        this.mClippingToPadding = true;
        this.mIsDrawingListUnderStickyHeader = true;
        this.mStickyHeaderTopOffset = 0;
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        this.mPaddingRight = 0;
        this.mPaddingBottom = 0;
        this.mTouchSlop = (float) ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mList = new WrapperViewList(context);
        this.mDivider = this.mList.getDivider();
        this.mDividerHeight = this.mList.getDividerHeight();
        this.mList.setDivider(null);
        this.mList.setDividerHeight(0);
        if (attributeSet != null) {
            context = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.StickyListHeadersListView, i, 0);
            try {
                attributeSet = context.getDimensionPixelSize(R.styleable.StickyListHeadersListView_android_padding, 0);
                this.mPaddingLeft = context.getDimensionPixelSize(R.styleable.StickyListHeadersListView_android_paddingLeft, attributeSet);
                this.mPaddingTop = context.getDimensionPixelSize(R.styleable.StickyListHeadersListView_android_paddingTop, attributeSet);
                this.mPaddingRight = context.getDimensionPixelSize(R.styleable.StickyListHeadersListView_android_paddingRight, attributeSet);
                this.mPaddingBottom = context.getDimensionPixelSize(R.styleable.StickyListHeadersListView_android_paddingBottom, attributeSet);
                setPadding(this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom);
                this.mClippingToPadding = context.getBoolean(R.styleable.StickyListHeadersListView_android_clipToPadding, true);
                super.setClipToPadding(true);
                this.mList.setClipToPadding(this.mClippingToPadding);
                attributeSet = context.getInt(R.styleable.StickyListHeadersListView_android_scrollbars, 512);
                this.mList.setVerticalScrollBarEnabled((attributeSet & 512) != 0);
                this.mList.setHorizontalScrollBarEnabled((attributeSet & 256) != null ? true : null);
                if (VERSION.SDK_INT >= 9) {
                    this.mList.setOverScrollMode(context.getInt(R.styleable.StickyListHeadersListView_android_overScrollMode, 0));
                }
                this.mList.setFadingEdgeLength(context.getDimensionPixelSize(R.styleable.StickyListHeadersListView_android_fadingEdgeLength, this.mList.getVerticalFadingEdgeLength()));
                attributeSet = context.getInt(R.styleable.StickyListHeadersListView_android_requiresFadingEdge, 0);
                if (attributeSet == 4096) {
                    this.mList.setVerticalFadingEdgeEnabled(false);
                    this.mList.setHorizontalFadingEdgeEnabled(true);
                } else if (attributeSet == 8192) {
                    this.mList.setVerticalFadingEdgeEnabled(true);
                    this.mList.setHorizontalFadingEdgeEnabled(false);
                } else {
                    this.mList.setVerticalFadingEdgeEnabled(false);
                    this.mList.setHorizontalFadingEdgeEnabled(false);
                }
                this.mList.setCacheColorHint(context.getColor(R.styleable.StickyListHeadersListView_android_cacheColorHint, this.mList.getCacheColorHint()));
                if (VERSION.SDK_INT >= 11) {
                    this.mList.setChoiceMode(context.getInt(R.styleable.StickyListHeadersListView_android_choiceMode, this.mList.getChoiceMode()));
                }
                this.mList.setDrawSelectorOnTop(context.getBoolean(R.styleable.StickyListHeadersListView_android_drawSelectorOnTop, false));
                this.mList.setFastScrollEnabled(context.getBoolean(R.styleable.StickyListHeadersListView_android_fastScrollEnabled, this.mList.isFastScrollEnabled()));
                if (VERSION.SDK_INT >= 11) {
                    this.mList.setFastScrollAlwaysVisible(context.getBoolean(R.styleable.StickyListHeadersListView_android_fastScrollAlwaysVisible, this.mList.isFastScrollAlwaysVisible()));
                }
                this.mList.setScrollBarStyle(context.getInt(R.styleable.StickyListHeadersListView_android_scrollbarStyle, 0));
                if (context.hasValue(R.styleable.StickyListHeadersListView_android_listSelector) != null) {
                    this.mList.setSelector(context.getDrawable(R.styleable.StickyListHeadersListView_android_listSelector));
                }
                this.mList.setScrollingCacheEnabled(context.getBoolean(R.styleable.StickyListHeadersListView_android_scrollingCache, this.mList.isScrollingCacheEnabled()));
                if (context.hasValue(R.styleable.StickyListHeadersListView_android_divider) != null) {
                    this.mDivider = context.getDrawable(R.styleable.StickyListHeadersListView_android_divider);
                }
                this.mList.setStackFromBottom(context.getBoolean(R.styleable.StickyListHeadersListView_android_stackFromBottom, false));
                this.mDividerHeight = context.getDimensionPixelSize(R.styleable.StickyListHeadersListView_android_dividerHeight, this.mDividerHeight);
                this.mList.setTranscriptMode(context.getInt(R.styleable.StickyListHeadersListView_android_transcriptMode, 0));
                this.mAreHeadersSticky = context.getBoolean(R.styleable.StickyListHeadersListView_hasStickyHeaders, true);
                this.mIsDrawingListUnderStickyHeader = context.getBoolean(R.styleable.StickyListHeadersListView_isDrawingListUnderStickyHeader, true);
            } finally {
                context.recycle();
            }
        }
        this.mList.setLifeCycleListener(new WrapperViewListLifeCycleListener());
        this.mList.setOnScrollListener(new WrapperListScrollListener());
        addView(this.mList);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        measureHeader(this.mHeader);
    }

    private void ensureHeaderHasCorrectLayoutParams(View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            view.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        } else if (layoutParams.height == -1 || layoutParams.width == -2) {
            layoutParams.height = -2;
            layoutParams.width = -1;
            view.setLayoutParams(layoutParams);
        }
    }

    private void measureHeader(View view) {
        if (view != null) {
            measureChild(view, MeasureSpec.makeMeasureSpec((getMeasuredWidth() - this.mPaddingLeft) - this.mPaddingRight, 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mList.layout(0, 0, this.mList.getMeasuredWidth(), getHeight());
        if (this.mHeader) {
            z = ((MarginLayoutParams) this.mHeader.getLayoutParams()).topMargin;
            this.mHeader.layout(this.mPaddingLeft, z, this.mHeader.getMeasuredWidth() + this.mPaddingLeft, this.mHeader.getMeasuredHeight() + z);
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        if (this.mList.getVisibility() == 0 || this.mList.getAnimation() != null) {
            drawChild(canvas, this.mList, 0);
        }
    }

    private void clearHeader() {
        if (this.mHeader != null) {
            removeView(this.mHeader);
            this.mHeader = null;
            this.mHeaderId = null;
            this.mHeaderPosition = null;
            this.mHeaderOffset = null;
            this.mList.setTopClippingLength(0);
            updateHeaderVisibilities();
        }
    }

    private void updateOrClearHeader(int i) {
        int i2 = 0;
        int count = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        if (count != 0) {
            if (this.mAreHeadersSticky) {
                i -= this.mList.getHeaderViewsCount();
                if (this.mList.getChildCount() > 0 && this.mList.getChildAt(0).getBottom() < stickyHeaderTop()) {
                    i++;
                }
                Object obj = this.mList.getChildCount() != 0 ? 1 : null;
                Object obj2 = (obj == null || this.mList.getFirstVisiblePosition() != 0 || this.mList.getChildAt(0).getTop() < stickyHeaderTop()) ? null : 1;
                if (i > count - 1 || i < 0) {
                    i2 = 1;
                }
                if (obj != null && r1 == 0) {
                    if (obj2 == null) {
                        updateHeader(i);
                        return;
                    }
                }
                clearHeader();
            }
        }
    }

    private void updateHeader(int i) {
        if (this.mHeaderPosition == null || this.mHeaderPosition.intValue() != i) {
            this.mHeaderPosition = Integer.valueOf(i);
            long headerId = this.mAdapter.getHeaderId(i);
            if (this.mHeaderId == null || this.mHeaderId.longValue() != headerId) {
                this.mHeaderId = Long.valueOf(headerId);
                View headerView = this.mAdapter.getHeaderView(this.mHeaderPosition.intValue(), this.mHeader, this);
                if (this.mHeader != headerView) {
                    if (headerView != null) {
                        swapHeader(headerView);
                    } else {
                        throw new NullPointerException("header may not be null");
                    }
                }
                ensureHeaderHasCorrectLayoutParams(this.mHeader);
                measureHeader(this.mHeader);
                if (this.mOnStickyHeaderChangedListener != null) {
                    this.mOnStickyHeaderChangedListener.onStickyHeaderChanged(this, this.mHeader, i, this.mHeaderId.longValue());
                }
                this.mHeaderOffset = 0;
            }
        }
        i = stickyHeaderTop();
        for (int i2 = 0; i2 < this.mList.getChildCount(); i2++) {
            View childAt = this.mList.getChildAt(i2);
            Object obj = ((childAt instanceof WrapperView) && ((WrapperView) childAt).hasHeader()) ? 1 : null;
            boolean containsFooterView = this.mList.containsFooterView(childAt);
            if (childAt.getTop() >= stickyHeaderTop() && (obj != null || containsFooterView)) {
                i = Math.min(childAt.getTop() - this.mHeader.getMeasuredHeight(), i);
                break;
            }
        }
        setHeaderOffet(i);
        if (this.mIsDrawingListUnderStickyHeader == 0) {
            this.mList.setTopClippingLength(this.mHeader.getMeasuredHeight() + this.mHeaderOffset.intValue());
        }
        updateHeaderVisibilities();
    }

    private void swapHeader(View view) {
        if (this.mHeader != null) {
            removeView(this.mHeader);
        }
        this.mHeader = view;
        addView(this.mHeader);
        if (this.mOnHeaderClickListener != null) {
            this.mHeader.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    StickyListHeadersListView.this.mOnHeaderClickListener.onHeaderClick(StickyListHeadersListView.this, StickyListHeadersListView.this.mHeader, StickyListHeadersListView.this.mHeaderPosition.intValue(), StickyListHeadersListView.this.mHeaderId.longValue(), true);
                }
            });
        }
        this.mHeader.setClickable(true);
    }

    private void updateHeaderVisibilities() {
        int stickyHeaderTop = stickyHeaderTop();
        int childCount = this.mList.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mList.getChildAt(i);
            if (childAt instanceof WrapperView) {
                WrapperView wrapperView = (WrapperView) childAt;
                if (wrapperView.hasHeader()) {
                    View view = wrapperView.mHeader;
                    if (wrapperView.getTop() < stickyHeaderTop) {
                        if (view.getVisibility() != 4) {
                            view.setVisibility(4);
                        }
                    } else if (view.getVisibility() != 0) {
                        view.setVisibility(0);
                    }
                }
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void setHeaderOffet(int i) {
        if (this.mHeaderOffset == null || this.mHeaderOffset.intValue() != i) {
            this.mHeaderOffset = Integer.valueOf(i);
            if (VERSION.SDK_INT >= 11) {
                this.mHeader.setTranslationY((float) this.mHeaderOffset.intValue());
            } else {
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mHeader.getLayoutParams();
                marginLayoutParams.topMargin = this.mHeaderOffset.intValue();
                this.mHeader.setLayoutParams(marginLayoutParams);
            }
            if (this.mOnStickyHeaderOffsetChangedListener != 0) {
                this.mOnStickyHeaderOffsetChangedListener.onStickyHeaderOffsetChanged(this, this.mHeader, -this.mHeaderOffset.intValue());
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if ((motionEvent.getAction() & 255) == 0) {
            this.mDownY = motionEvent.getY();
            boolean z = this.mHeader != null && this.mDownY <= ((float) (this.mHeader.getHeight() + this.mHeaderOffset.intValue()));
            this.mHeaderOwnsTouch = z;
        }
        if (!this.mHeaderOwnsTouch) {
            return this.mList.dispatchTouchEvent(motionEvent);
        }
        if (this.mHeader != null && Math.abs(this.mDownY - motionEvent.getY()) <= this.mTouchSlop) {
            return this.mHeader.dispatchTouchEvent(motionEvent);
        }
        if (this.mHeader != null) {
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            obtain.setAction(3);
            this.mHeader.dispatchTouchEvent(obtain);
            obtain.recycle();
        }
        motionEvent = MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), motionEvent.getAction(), motionEvent.getX(), this.mDownY, motionEvent.getMetaState());
        motionEvent.setAction(0);
        z = this.mList.dispatchTouchEvent(motionEvent);
        motionEvent.recycle();
        this.mHeaderOwnsTouch = false;
        return z;
    }

    private boolean isStartOfSection(int i) {
        if (i != 0) {
            return this.mAdapter.getHeaderId(i) != this.mAdapter.getHeaderId(i - 1);
        } else {
            return true;
        }
    }

    public int getHeaderOverlap(int i) {
        if (isStartOfSection(Math.max(0, i - getHeaderViewsCount()))) {
            return 0;
        }
        i = this.mAdapter.getHeaderView(i, null, this.mList);
        if (i != 0) {
            ensureHeaderHasCorrectLayoutParams(i);
            measureHeader(i);
            return i.getMeasuredHeight();
        }
        throw new NullPointerException("header may not be null");
    }

    private int stickyHeaderTop() {
        return this.mStickyHeaderTopOffset + (this.mClippingToPadding ? this.mPaddingTop : 0);
    }

    public void setAreHeadersSticky(boolean z) {
        this.mAreHeadersSticky = z;
        if (z) {
            updateOrClearHeader(this.mList.getFixedFirstVisibleItem());
        } else {
            clearHeader();
        }
        this.mList.invalidate();
    }

    public boolean areHeadersSticky() {
        return this.mAreHeadersSticky;
    }

    @Deprecated
    public boolean getAreHeadersSticky() {
        return areHeadersSticky();
    }

    public void setStickyHeaderTopOffset(int i) {
        this.mStickyHeaderTopOffset = i;
        updateOrClearHeader(this.mList.getFixedFirstVisibleItem());
    }

    public int getStickyHeaderTopOffset() {
        return this.mStickyHeaderTopOffset;
    }

    public void setDrawingListUnderStickyHeader(boolean z) {
        this.mIsDrawingListUnderStickyHeader = z;
        this.mList.setTopClippingLength(0);
    }

    public boolean isDrawingListUnderStickyHeader() {
        return this.mIsDrawingListUnderStickyHeader;
    }

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        this.mOnHeaderClickListener = onHeaderClickListener;
        if (this.mAdapter == null) {
            return;
        }
        if (this.mOnHeaderClickListener != null) {
            this.mAdapter.setOnHeaderClickListener(new AdapterWrapperHeaderClickHandler());
            if (this.mHeader != null) {
                this.mHeader.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        StickyListHeadersListView.this.mOnHeaderClickListener.onHeaderClick(StickyListHeadersListView.this, StickyListHeadersListView.this.mHeader, StickyListHeadersListView.this.mHeaderPosition.intValue(), StickyListHeadersListView.this.mHeaderId.longValue(), true);
                    }
                });
                return;
            }
            return;
        }
        this.mAdapter.setOnHeaderClickListener(null);
    }

    public void setOnStickyHeaderOffsetChangedListener(OnStickyHeaderOffsetChangedListener onStickyHeaderOffsetChangedListener) {
        this.mOnStickyHeaderOffsetChangedListener = onStickyHeaderOffsetChangedListener;
    }

    public void setOnStickyHeaderChangedListener(OnStickyHeaderChangedListener onStickyHeaderChangedListener) {
        this.mOnStickyHeaderChangedListener = onStickyHeaderChangedListener;
    }

    public View getListChildAt(int i) {
        return this.mList.getChildAt(i);
    }

    public int getListChildCount() {
        return this.mList.getChildCount();
    }

    public ListView getWrappedList() {
        return this.mList;
    }

    private boolean requireSdkVersion(int i) {
        if (VERSION.SDK_INT >= i) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Api lvl must be at least ");
        stringBuilder.append(i);
        stringBuilder.append(" to call this method");
        Log.e("StickyListHeaders", stringBuilder.toString());
        return false;
    }

    public void setAdapter(StickyListHeadersAdapter stickyListHeadersAdapter) {
        if (stickyListHeadersAdapter == null) {
            if ((this.mAdapter instanceof SectionIndexerAdapterWrapper) != null) {
                ((SectionIndexerAdapterWrapper) this.mAdapter).mSectionIndexerDelegate = null;
            }
            if (this.mAdapter != null) {
                this.mAdapter.mDelegate = null;
            }
            this.mList.setAdapter(null);
            clearHeader();
            return;
        }
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        if (stickyListHeadersAdapter instanceof SectionIndexer) {
            this.mAdapter = new SectionIndexerAdapterWrapper(getContext(), stickyListHeadersAdapter);
        } else {
            this.mAdapter = new AdapterWrapper(getContext(), stickyListHeadersAdapter);
        }
        this.mDataSetObserver = new AdapterWrapperDataSetObserver();
        this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
        if (this.mOnHeaderClickListener != null) {
            this.mAdapter.setOnHeaderClickListener(new AdapterWrapperHeaderClickHandler());
        } else {
            this.mAdapter.setOnHeaderClickListener(null);
        }
        this.mAdapter.setDivider(this.mDivider, this.mDividerHeight);
        this.mList.setAdapter(this.mAdapter);
        clearHeader();
    }

    public StickyListHeadersAdapter getAdapter() {
        return this.mAdapter == null ? null : this.mAdapter.mDelegate;
    }

    public void setDivider(Drawable drawable) {
        this.mDivider = drawable;
        if (this.mAdapter != null) {
            this.mAdapter.setDivider(this.mDivider, this.mDividerHeight);
        }
    }

    public void setDividerHeight(int i) {
        this.mDividerHeight = i;
        if (this.mAdapter != 0) {
            this.mAdapter.setDivider(this.mDivider, this.mDividerHeight);
        }
    }

    public Drawable getDivider() {
        return this.mDivider;
    }

    public int getDividerHeight() {
        return this.mDividerHeight;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListenerDelegate = onScrollListener;
    }

    public void setOnTouchListener(final OnTouchListener onTouchListener) {
        if (onTouchListener != null) {
            this.mList.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return onTouchListener.onTouch(StickyListHeadersListView.this, motionEvent);
                }
            });
        } else {
            this.mList.setOnTouchListener(null);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mList.setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mList.setOnItemLongClickListener(onItemLongClickListener);
    }

    public void addHeaderView(View view, Object obj, boolean z) {
        this.mList.addHeaderView(view, obj, z);
    }

    public void addHeaderView(View view) {
        this.mList.addHeaderView(view);
    }

    public void removeHeaderView(View view) {
        this.mList.removeHeaderView(view);
    }

    public int getHeaderViewsCount() {
        return this.mList.getHeaderViewsCount();
    }

    public void addFooterView(View view, Object obj, boolean z) {
        this.mList.addFooterView(view, obj, z);
    }

    public void addFooterView(View view) {
        this.mList.addFooterView(view);
    }

    public void removeFooterView(View view) {
        this.mList.removeFooterView(view);
    }

    public int getFooterViewsCount() {
        return this.mList.getFooterViewsCount();
    }

    public void setEmptyView(View view) {
        this.mList.setEmptyView(view);
    }

    public View getEmptyView() {
        return this.mList.getEmptyView();
    }

    public boolean isVerticalScrollBarEnabled() {
        return this.mList.isVerticalScrollBarEnabled();
    }

    public boolean isHorizontalScrollBarEnabled() {
        return this.mList.isHorizontalScrollBarEnabled();
    }

    public void setVerticalScrollBarEnabled(boolean z) {
        this.mList.setVerticalScrollBarEnabled(z);
    }

    public void setHorizontalScrollBarEnabled(boolean z) {
        this.mList.setHorizontalScrollBarEnabled(z);
    }

    @TargetApi(9)
    public int getOverScrollMode() {
        return requireSdkVersion(9) ? this.mList.getOverScrollMode() : 0;
    }

    @TargetApi(9)
    public void setOverScrollMode(int i) {
        if (requireSdkVersion(9) && this.mList != null) {
            this.mList.setOverScrollMode(i);
        }
    }

    @TargetApi(8)
    public void smoothScrollBy(int i, int i2) {
        if (requireSdkVersion(8)) {
            this.mList.smoothScrollBy(i, i2);
        }
    }

    @TargetApi(11)
    public void smoothScrollByOffset(int i) {
        if (requireSdkVersion(11)) {
            this.mList.smoothScrollByOffset(i);
        }
    }

    @SuppressLint({"NewApi"})
    @TargetApi(8)
    public void smoothScrollToPosition(int i) {
        if (!requireSdkVersion(8)) {
            return;
        }
        if (VERSION.SDK_INT < 11) {
            this.mList.smoothScrollToPosition(i);
            return;
        }
        int i2 = 0;
        int headerOverlap = this.mAdapter == null ? 0 : getHeaderOverlap(i);
        if (!this.mClippingToPadding) {
            i2 = this.mPaddingTop;
        }
        this.mList.smoothScrollToPositionFromTop(i, headerOverlap - i2);
    }

    @TargetApi(8)
    public void smoothScrollToPosition(int i, int i2) {
        if (requireSdkVersion(8)) {
            this.mList.smoothScrollToPosition(i, i2);
        }
    }

    @TargetApi(11)
    public void smoothScrollToPositionFromTop(int i, int i2) {
        if (requireSdkVersion(11)) {
            int i3 = 0;
            i2 += this.mAdapter == null ? 0 : getHeaderOverlap(i);
            if (!this.mClippingToPadding) {
                i3 = this.mPaddingTop;
            }
            this.mList.smoothScrollToPositionFromTop(i, i2 - i3);
        }
    }

    @TargetApi(11)
    public void smoothScrollToPositionFromTop(int i, int i2, int i3) {
        if (requireSdkVersion(11)) {
            int i4 = 0;
            i2 += this.mAdapter == null ? 0 : getHeaderOverlap(i);
            if (!this.mClippingToPadding) {
                i4 = this.mPaddingTop;
            }
            this.mList.smoothScrollToPositionFromTop(i, i2 - i4, i3);
        }
    }

    public void setSelection(int i) {
        setSelectionFromTop(i, 0);
    }

    public void setSelectionAfterHeaderView() {
        this.mList.setSelectionAfterHeaderView();
    }

    public void setSelectionFromTop(int i, int i2) {
        int i3 = 0;
        i2 += this.mAdapter == null ? 0 : getHeaderOverlap(i);
        if (!this.mClippingToPadding) {
            i3 = this.mPaddingTop;
        }
        this.mList.setSelectionFromTop(i, i2 - i3);
    }

    public void setSelector(Drawable drawable) {
        this.mList.setSelector(drawable);
    }

    public void setSelector(int i) {
        this.mList.setSelector(i);
    }

    public int getFirstVisiblePosition() {
        return this.mList.getFirstVisiblePosition();
    }

    public int getLastVisiblePosition() {
        return this.mList.getLastVisiblePosition();
    }

    @TargetApi(11)
    public void setChoiceMode(int i) {
        this.mList.setChoiceMode(i);
    }

    @TargetApi(11)
    public void setItemChecked(int i, boolean z) {
        this.mList.setItemChecked(i, z);
    }

    @TargetApi(11)
    public int getCheckedItemCount() {
        return requireSdkVersion(11) ? this.mList.getCheckedItemCount() : 0;
    }

    @TargetApi(8)
    public long[] getCheckedItemIds() {
        return requireSdkVersion(8) ? this.mList.getCheckedItemIds() : null;
    }

    @TargetApi(11)
    public int getCheckedItemPosition() {
        return this.mList.getCheckedItemPosition();
    }

    @TargetApi(11)
    public SparseBooleanArray getCheckedItemPositions() {
        return this.mList.getCheckedItemPositions();
    }

    public int getCount() {
        return this.mList.getCount();
    }

    public Object getItemAtPosition(int i) {
        return this.mList.getItemAtPosition(i);
    }

    public long getItemIdAtPosition(int i) {
        return this.mList.getItemIdAtPosition(i);
    }

    public void setOnCreateContextMenuListener(OnCreateContextMenuListener onCreateContextMenuListener) {
        this.mList.setOnCreateContextMenuListener(onCreateContextMenuListener);
    }

    public boolean showContextMenu() {
        return this.mList.showContextMenu();
    }

    public void invalidateViews() {
        this.mList.invalidateViews();
    }

    public void setClipToPadding(boolean z) {
        if (this.mList != null) {
            this.mList.setClipToPadding(z);
        }
        this.mClippingToPadding = z;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.mPaddingLeft = i;
        this.mPaddingTop = i2;
        this.mPaddingRight = i3;
        this.mPaddingBottom = i4;
        if (this.mList != null) {
            this.mList.setPadding(i, i2, i3, i4);
        }
        super.setPadding(0, 0, 0, 0);
        requestLayout();
    }

    protected void recomputePadding() {
        setPadding(this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom);
    }

    public int getPaddingLeft() {
        return this.mPaddingLeft;
    }

    public int getPaddingTop() {
        return this.mPaddingTop;
    }

    public int getPaddingRight() {
        return this.mPaddingRight;
    }

    public int getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public void setFastScrollEnabled(boolean z) {
        this.mList.setFastScrollEnabled(z);
    }

    @TargetApi(11)
    public void setFastScrollAlwaysVisible(boolean z) {
        if (requireSdkVersion(11)) {
            this.mList.setFastScrollAlwaysVisible(z);
        }
    }

    @TargetApi(11)
    public boolean isFastScrollAlwaysVisible() {
        if (VERSION.SDK_INT < 11) {
            return false;
        }
        return this.mList.isFastScrollAlwaysVisible();
    }

    public void setScrollBarStyle(int i) {
        this.mList.setScrollBarStyle(i);
    }

    public int getScrollBarStyle() {
        return this.mList.getScrollBarStyle();
    }

    public int getPositionForView(View view) {
        return this.mList.getPositionForView(view);
    }

    @TargetApi(11)
    public void setMultiChoiceModeListener(MultiChoiceModeListener multiChoiceModeListener) {
        if (requireSdkVersion(11)) {
            this.mList.setMultiChoiceModeListener(multiChoiceModeListener);
        }
    }

    public Parcelable onSaveInstanceState() {
        if (super.onSaveInstanceState() == BaseSavedState.EMPTY_STATE) {
            return this.mList.onSaveInstanceState();
        }
        throw new IllegalStateException("Handling non empty state of parent class is not implemented");
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(BaseSavedState.EMPTY_STATE);
        this.mList.onRestoreInstanceState(parcelable);
    }

    @TargetApi(14)
    public boolean canScrollVertically(int i) {
        return this.mList.canScrollVertically(i);
    }

    public void setTranscriptMode(int i) {
        this.mList.setTranscriptMode(i);
    }

    public void setBlockLayoutChildren(boolean z) {
        this.mList.setBlockLayoutChildren(z);
    }

    public void setStackFromBottom(boolean z) {
        this.mList.setStackFromBottom(z);
    }

    public boolean isStackFromBottom() {
        return this.mList.isStackFromBottom();
    }
}
