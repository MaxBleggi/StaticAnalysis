package com.xumo.xumo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

public class XumoViewPager extends ViewPager {
    public boolean arrowScroll(int i) {
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public void setCurrentItem(int i, boolean z) {
    }

    public XumoViewPager(Context context) {
        super(context);
    }

    public XumoViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setCurrentItem(int i) {
        super.setCurrentItem(i, false);
    }
}
