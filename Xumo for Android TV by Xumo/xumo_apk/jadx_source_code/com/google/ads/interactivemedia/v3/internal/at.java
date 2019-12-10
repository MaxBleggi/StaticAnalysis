package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

/* compiled from: IMASDK */
public final class at extends FrameLayout {
    private float a;

    public at(Context context) {
        super(context);
    }

    public void a(float f) {
        if (this.a != f) {
            this.a = f;
            requestLayout();
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.a != 0) {
            i = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f = (float) i;
            float f2 = (float) measuredHeight;
            float f3 = (this.a / (f / f2)) - 1.0f;
            if (Math.abs(f3) > 0.01f) {
                if (f3 > 0.0f) {
                    measuredHeight = (int) (f / this.a);
                } else {
                    i = (int) (f2 * this.a);
                }
                super.onMeasure(MeasureSpec.makeMeasureSpec(i, 1073741824), MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
            }
        }
    }
}
