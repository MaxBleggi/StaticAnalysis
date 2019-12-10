package com.google.android.exoplayer2.ui.spherical;

import android.content.Context;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;

class TouchTracker extends SimpleOnGestureListener implements OnTouchListener {
    static final float MAX_PITCH_DEGREES = 45.0f;
    private final PointF accumulatedTouchOffsetDegrees = new PointF();
    private final GestureDetector gestureDetector;
    private final Listener listener;
    private final PointF previousTouchPointPx = new PointF();
    private final float pxPerDegrees;
    private volatile float roll;
    @Nullable
    private SingleTapListener singleTapListener;

    interface Listener {
        void onScrollChange(PointF pointF);
    }

    public TouchTracker(Context context, Listener listener, float f) {
        this.listener = listener;
        this.pxPerDegrees = f;
        this.gestureDetector = new GestureDetector(context, this);
        this.roll = 3.1415927f;
    }

    public void setSingleTapListener(@Nullable SingleTapListener singleTapListener) {
        this.singleTapListener = singleTapListener;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.gestureDetector.onTouchEvent(motionEvent);
    }

    public boolean onDown(MotionEvent motionEvent) {
        this.previousTouchPointPx.set(motionEvent.getX(), motionEvent.getY());
        return true;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        motionEvent = (motionEvent2.getX() - this.previousTouchPointPx.x) / this.pxPerDegrees;
        f = (motionEvent2.getY() - this.previousTouchPointPx.y) / this.pxPerDegrees;
        this.previousTouchPointPx.set(motionEvent2.getX(), motionEvent2.getY());
        double d = (double) this.roll;
        motionEvent2 = (float) Math.cos(d);
        f2 = (float) Math.sin(d);
        PointF pointF = this.accumulatedTouchOffsetDegrees;
        pointF.x -= (motionEvent2 * motionEvent) - (f2 * f);
        pointF = this.accumulatedTouchOffsetDegrees;
        pointF.y += (f2 * motionEvent) + (motionEvent2 * f);
        this.accumulatedTouchOffsetDegrees.y = Math.max(-45.0f, Math.min(MAX_PITCH_DEGREES, this.accumulatedTouchOffsetDegrees.y));
        this.listener.onScrollChange(this.accumulatedTouchOffsetDegrees);
        return true;
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return this.singleTapListener != null ? this.singleTapListener.onSingleTapUp(motionEvent) : null;
    }

    @BinderThread
    public void setRoll(float f) {
        this.roll = -f;
    }
}
