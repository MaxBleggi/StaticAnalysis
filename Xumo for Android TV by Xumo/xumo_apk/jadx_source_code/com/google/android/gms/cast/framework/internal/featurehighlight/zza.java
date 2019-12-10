package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.internal.cast.zzen;
import com.google.android.gms.internal.cast.zzew;
import com.google.android.gms.internal.cast.zzez;

public final class zza extends ViewGroup {
    private View targetView;
    private final int[] zzju = new int[2];
    private final Rect zzjv = new Rect();
    private final Rect zzjw = new Rect();
    private final OuterHighlightDrawable zzjx;
    private final InnerZoneDrawable zzjy;
    private zzi zzjz;
    @Nullable
    private View zzka;
    @Nullable
    private Animator zzkb;
    private final zzj zzkc;
    private final GestureDetectorCompat zzkd;
    @Nullable
    private GestureDetectorCompat zzke;
    private zzh zzkf;
    private boolean zzkg;

    public zza(Context context) {
        super(context);
        setId(R.id.cast_featurehighlight_view);
        setWillNotDraw(false);
        this.zzjy = new InnerZoneDrawable(context);
        this.zzjy.setCallback(this);
        this.zzjx = new OuterHighlightDrawable(context);
        this.zzjx.setCallback(this);
        this.zzkc = new zzj(this);
        this.zzkd = new GestureDetectorCompat(context, new zzb(this));
        this.zzkd.setIsLongpressEnabled(false);
        setVisibility(8);
    }

    public final void zza(zzi com_google_android_gms_cast_framework_internal_featurehighlight_zzi) {
        this.zzjz = (zzi) zzez.checkNotNull(com_google_android_gms_cast_framework_internal_featurehighlight_zzi);
        addView(com_google_android_gms_cast_framework_internal_featurehighlight_zzi.asView(), 0);
    }

    public final void zza(View view, @Nullable View view2, boolean z, zzh com_google_android_gms_cast_framework_internal_featurehighlight_zzh) {
        this.targetView = (View) zzez.checkNotNull(view);
        this.zzka = null;
        this.zzkf = (zzh) zzez.checkNotNull(com_google_android_gms_cast_framework_internal_featurehighlight_zzh);
        this.zzke = new GestureDetectorCompat(getContext(), new zzc(this, view, true, com_google_android_gms_cast_framework_internal_featurehighlight_zzh));
        this.zzke.setIsLongpressEnabled(null);
        setVisibility(4);
    }

    protected final void onMeasure(int i, int i2) {
        setMeasuredDimension(resolveSize(MeasureSpec.getSize(i), i), resolveSize(MeasureSpec.getSize(i2), i2));
    }

    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.targetView) {
            if (this.targetView.getParent()) {
                z = this.zzju;
                View view = this.targetView;
                getLocationInWindow(z);
                int i5 = z[0];
                int i6 = z[1];
                view.getLocationInWindow(z);
                z[0] = z[0] - i5;
                z[1] = z[1] - i6;
            }
            this.zzjv.set(this.zzju[0], this.zzju[1], this.zzju[0] + this.targetView.getWidth(), this.zzju[1] + this.targetView.getHeight());
            this.zzjw.set(i, i2, i3, i4);
            this.zzjx.setBounds(this.zzjw);
            this.zzjy.setBounds(this.zzjw);
            this.zzkc.zza(this.zzjv, this.zzjw);
            return;
        }
        throw new IllegalStateException("Target view must be set before layout");
    }

    protected final void onDraw(Canvas canvas) {
        canvas.save();
        this.zzjx.draw(canvas);
        this.zzjy.draw(canvas);
        if (this.targetView != null) {
            if (this.targetView.getParent() != null) {
                Bitmap createBitmap = Bitmap.createBitmap(this.targetView.getWidth(), this.targetView.getHeight(), Config.ARGB_8888);
                this.targetView.draw(new Canvas(createBitmap));
                int color = this.zzjx.getColor();
                int red = Color.red(color);
                int green = Color.green(color);
                color = Color.blue(color);
                for (int i = 0; i < createBitmap.getHeight(); i++) {
                    for (int i2 = 0; i2 < createBitmap.getWidth(); i2++) {
                        int pixel = createBitmap.getPixel(i2, i);
                        if (Color.alpha(pixel) != 0) {
                            createBitmap.setPixel(i2, i, Color.argb(Color.alpha(pixel), red, green, color));
                        }
                    }
                }
                canvas.drawBitmap(createBitmap, (float) this.zzjv.left, (float) this.zzjv.top, null);
            }
            canvas.restore();
            return;
        }
        throw new IllegalStateException("Neither target view nor drawable was set");
    }

    public final LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new MarginLayoutParams(getContext(), attributeSet);
    }

    protected final LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(-2, -2);
    }

    protected final LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        return new MarginLayoutParams(layoutParams);
    }

    protected final boolean checkLayoutParams(LayoutParams layoutParams) {
        return layoutParams instanceof MarginLayoutParams;
    }

    protected final boolean verifyDrawable(Drawable drawable) {
        if (!(super.verifyDrawable(drawable) || drawable == this.zzjx || drawable == this.zzjy)) {
            if (drawable != null) {
                return null;
            }
        }
        return true;
    }

    public final void zza(@Nullable Runnable runnable) {
        addOnLayoutChangeListener(new zzd(this, null));
    }

    public final void zzaq() {
        if (this.targetView != null) {
            setVisibility(0);
            ObjectAnimator.ofFloat(this.zzjz.asView(), "alpha", new float[]{0.0f, 1.0f}).setDuration(350).setInterpolator(zzew.zzdx());
            Animator zzc = this.zzjx.zzc(this.zzjv.exactCenterX() - this.zzjx.getCenterX(), this.zzjv.exactCenterY() - this.zzjx.getCenterY());
            InnerZoneDrawable innerZoneDrawable = this.zzjy;
            PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scale", new float[]{0.0f, 1.0f});
            PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("alpha", new int[]{0, 255});
            Animator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(innerZoneDrawable, new PropertyValuesHolder[]{ofFloat, ofInt});
            ofPropertyValuesHolder.setInterpolator(zzew.zzdx());
            Animator duration = ofPropertyValuesHolder.setDuration(350);
            Animator animatorSet = new AnimatorSet();
            animatorSet.playTogether(new Animator[]{r1, zzc, duration});
            animatorSet.addListener(new zze(this));
            zza(animatorSet);
            return;
        }
        throw new IllegalStateException("Target view must be set before animation");
    }

    public final void zzb(@Nullable Runnable runnable) {
        ObjectAnimator.ofFloat(this.zzjz.asView(), "alpha", new float[]{0.0f}).setDuration(200).setInterpolator(zzew.zzdy());
        float exactCenterX = this.zzjv.exactCenterX() - this.zzjx.getCenterX();
        float exactCenterY = this.zzjv.exactCenterY() - this.zzjx.getCenterY();
        OuterHighlightDrawable outerHighlightDrawable = this.zzjx;
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scale", new float[]{0.0f});
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("alpha", new int[]{0});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("translationX", new float[]{0.0f, exactCenterX});
        PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat("translationY", new float[]{0.0f, exactCenterY});
        Animator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(outerHighlightDrawable, new PropertyValuesHolder[]{ofFloat, ofFloat2, ofFloat3, ofInt});
        ofPropertyValuesHolder.setInterpolator(zzew.zzdy());
        ofPropertyValuesHolder = ofPropertyValuesHolder.setDuration(200);
        Animator zzav = this.zzjy.zzav();
        Animator animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{r0, ofPropertyValuesHolder, zzav});
        animatorSet.addListener(new zzg(this, runnable));
        zza(animatorSet);
    }

    public final void zzc(@Nullable Runnable runnable) {
        ObjectAnimator.ofFloat(this.zzjz.asView(), "alpha", new float[]{0.0f}).setDuration(200).setInterpolator(zzew.zzdy());
        OuterHighlightDrawable outerHighlightDrawable = this.zzjx;
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scale", new float[]{1.125f});
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("alpha", new int[]{0});
        Animator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(outerHighlightDrawable, new PropertyValuesHolder[]{ofFloat, ofInt});
        ofPropertyValuesHolder.setInterpolator(zzew.zzdy());
        ofPropertyValuesHolder = ofPropertyValuesHolder.setDuration(200);
        Animator zzav = this.zzjy.zzav();
        Animator animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{r0, ofPropertyValuesHolder, zzav});
        animatorSet.addListener(new zzf(this, runnable));
        zza(animatorSet);
    }

    public final void zzg(@ColorInt int i) {
        this.zzjx.setColor(i);
    }

    final View zzar() {
        return this.zzjz.asView();
    }

    final OuterHighlightDrawable zzas() {
        return this.zzjx;
    }

    final InnerZoneDrawable zzat() {
        return this.zzjy;
    }

    private final void zza(Animator animator) {
        if (this.zzkb != null) {
            this.zzkb.cancel();
        }
        this.zzkb = animator;
        this.zzkb.start();
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.zzkg = this.zzjv.contains((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        if (this.zzkg) {
            if (this.zzke != null) {
                this.zzke.onTouchEvent(motionEvent);
                if (actionMasked == 1) {
                    motionEvent = MotionEvent.obtain(motionEvent);
                    motionEvent.setAction(3);
                }
            }
            if (this.targetView.getParent() != null) {
                this.targetView.onTouchEvent(motionEvent);
            }
        } else {
            this.zzkd.onTouchEvent(motionEvent);
        }
        return true;
    }

    private final boolean zza(float f, float f2) {
        return this.zzjw.contains(Math.round(f), Math.round(f2));
    }

    private final Animator zzau() {
        InnerZoneDrawable innerZoneDrawable = this.zzjy;
        Animator animatorSet = new AnimatorSet();
        Animator duration = ObjectAnimator.ofFloat(innerZoneDrawable, "scale", new float[]{1.0f, 1.1f}).setDuration(500);
        Animator duration2 = ObjectAnimator.ofFloat(innerZoneDrawable, "scale", new float[]{1.1f, 1.0f}).setDuration(500);
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("pulseScale", new float[]{1.1f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("pulseAlpha", new float[]{1.0f, 0.0f});
        Animator duration3 = ObjectAnimator.ofPropertyValuesHolder(innerZoneDrawable, new PropertyValuesHolder[]{ofFloat, ofFloat2}).setDuration(500);
        animatorSet.play(duration);
        animatorSet.play(duration2).with(duration3).after(duration);
        animatorSet.setInterpolator(zzew.zzdz());
        animatorSet.setStartDelay(500);
        zzen.zza(animatorSet, -1, null);
        return animatorSet;
    }
}
