package se.emilsjolander.stickylistheaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class WrapperViewList extends ListView {
    private boolean mBlockLayoutChildren = false;
    private boolean mClippingToPadding = true;
    private List<View> mFooterViews;
    private LifeCycleListener mLifeCycleListener;
    private Field mSelectorPositionField;
    private Rect mSelectorRect = new Rect();
    private int mTopClippingLength;

    interface LifeCycleListener {
        void onDispatchDrawOccurred(Canvas canvas);
    }

    public WrapperViewList(Context context) {
        super(context);
        try {
            Field declaredField = AbsListView.class.getDeclaredField("mSelectorRect");
            declaredField.setAccessible(true);
            this.mSelectorRect = (Rect) declaredField.get(this);
            if (VERSION.SDK_INT >= 14) {
                this.mSelectorPositionField = AbsListView.class.getDeclaredField("mSelectorPosition");
                this.mSelectorPositionField.setAccessible(true);
            }
        } catch (Context context2) {
            context2.printStackTrace();
        } catch (Context context22) {
            context22.printStackTrace();
        } catch (Context context222) {
            context222.printStackTrace();
        }
    }

    public boolean performItemClick(View view, int i, long j) {
        if (view instanceof WrapperView) {
            view = ((WrapperView) view).mItem;
        }
        return super.performItemClick(view, i, j);
    }

    private void positionSelectorRect() {
        if (!this.mSelectorRect.isEmpty()) {
            int selectorPosition = getSelectorPosition();
            if (selectorPosition >= 0) {
                View childAt = getChildAt(selectorPosition - getFixedFirstVisibleItem());
                if (childAt instanceof WrapperView) {
                    WrapperView wrapperView = (WrapperView) childAt;
                    this.mSelectorRect.top = wrapperView.getTop() + wrapperView.mItemTop;
                }
            }
        }
    }

    private int getSelectorPosition() {
        if (this.mSelectorPositionField == null) {
            for (int i = 0; i < getChildCount(); i++) {
                if (getChildAt(i).getBottom() == this.mSelectorRect.bottom) {
                    return i + getFixedFirstVisibleItem();
                }
            }
        } else {
            try {
                return this.mSelectorPositionField.getInt(this);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
        return -1;
    }

    protected void dispatchDraw(Canvas canvas) {
        positionSelectorRect();
        if (this.mTopClippingLength != 0) {
            canvas.save();
            Rect clipBounds = canvas.getClipBounds();
            clipBounds.top = this.mTopClippingLength;
            canvas.clipRect(clipBounds);
            super.dispatchDraw(canvas);
            canvas.restore();
        } else {
            super.dispatchDraw(canvas);
        }
        this.mLifeCycleListener.onDispatchDrawOccurred(canvas);
    }

    void setLifeCycleListener(LifeCycleListener lifeCycleListener) {
        this.mLifeCycleListener = lifeCycleListener;
    }

    public void addFooterView(View view) {
        super.addFooterView(view);
        addInternalFooterView(view);
    }

    public void addFooterView(View view, Object obj, boolean z) {
        super.addFooterView(view, obj, z);
        addInternalFooterView(view);
    }

    private void addInternalFooterView(View view) {
        if (this.mFooterViews == null) {
            this.mFooterViews = new ArrayList();
        }
        this.mFooterViews.add(view);
    }

    public boolean removeFooterView(View view) {
        if (!super.removeFooterView(view)) {
            return null;
        }
        this.mFooterViews.remove(view);
        return true;
    }

    boolean containsFooterView(View view) {
        if (this.mFooterViews == null) {
            return null;
        }
        return this.mFooterViews.contains(view);
    }

    void setTopClippingLength(int i) {
        this.mTopClippingLength = i;
    }

    int getFixedFirstVisibleItem() {
        int firstVisiblePosition = getFirstVisiblePosition();
        if (VERSION.SDK_INT >= 11) {
            return firstVisiblePosition;
        }
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getBottom() >= 0) {
                firstVisiblePosition += i;
                break;
            }
        }
        if (!this.mClippingToPadding && getPaddingTop() > 0 && firstVisiblePosition > 0 && getChildAt(0).getTop() > 0) {
            firstVisiblePosition--;
        }
        return firstVisiblePosition;
    }

    public void setClipToPadding(boolean z) {
        this.mClippingToPadding = z;
        super.setClipToPadding(z);
    }

    public void setBlockLayoutChildren(boolean z) {
        this.mBlockLayoutChildren = z;
    }

    protected void layoutChildren() {
        if (!this.mBlockLayoutChildren) {
            super.layoutChildren();
        }
    }
}
