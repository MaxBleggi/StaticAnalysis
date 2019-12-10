package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import androidx.annotation.ColorInt;
import androidx.annotation.Keep;
import androidx.core.graphics.ColorUtils;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.cast.zzew;
import com.google.android.gms.internal.cast.zzey;

class OuterHighlightDrawable extends Drawable {
    private float centerX;
    private float centerY;
    private final Paint zzjq = new Paint();
    private float zzjr;
    private final Rect zzjv = new Rect();
    private float zzkt = 1.0f;
    private final Rect zzkw = new Rect();
    private final int zzlc;
    private final int zzld;
    private final int zzle;
    private float zzlf = 0.0f;
    private float zzlg = 0.0f;
    private int zzlh = 244;

    public OuterHighlightDrawable(Context context) {
        if (PlatformVersion.isAtLeastLollipop()) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(16843827, typedValue, true);
            setColor(ColorUtils.setAlphaComponent(typedValue.data, 244));
        } else {
            setColor(context.getResources().getColor(R.color.cast_libraries_material_featurehighlight_outer_highlight_default_color));
        }
        this.zzjq.setAntiAlias(true);
        this.zzjq.setStyle(Style.FILL);
        context = context.getResources();
        this.zzlc = context.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_center_threshold);
        this.zzld = context.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_center_horizontal_offset);
        this.zzle = context.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_outer_padding);
    }

    public int getOpacity() {
        return -3;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(this.centerX + this.zzlf, this.centerY + this.zzlg, this.zzjr * this.zzkt, this.zzjq);
    }

    public void setAlpha(int i) {
        this.zzjq.setAlpha(i);
        invalidateSelf();
    }

    public int getAlpha() {
        return this.zzjq.getAlpha();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.zzjq.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @ColorInt
    public final int getColor() {
        return this.zzjq.getColor();
    }

    public final void setColor(@ColorInt int i) {
        this.zzjq.setColor(i);
        this.zzlh = this.zzjq.getAlpha();
        invalidateSelf();
    }

    public final void zzb(Rect rect, Rect rect2) {
        this.zzjv.set(rect);
        this.zzkw.set(rect2);
        float exactCenterX = rect.exactCenterX();
        float exactCenterY = rect.exactCenterY();
        Rect bounds = getBounds();
        if (Math.min(exactCenterY - ((float) bounds.top), ((float) bounds.bottom) - exactCenterY) < ((float) this.zzlc)) {
            this.centerX = exactCenterX;
            this.centerY = exactCenterY;
        } else {
            if ((exactCenterX <= bounds.exactCenterX() ? 1 : null) != null) {
                exactCenterX = rect2.exactCenterX() + ((float) this.zzld);
            } else {
                exactCenterX = rect2.exactCenterX() - ((float) this.zzld);
            }
            this.centerX = exactCenterX;
            this.centerY = rect2.exactCenterY();
        }
        this.zzjr = ((float) this.zzle) + Math.max(zza(this.centerX, this.centerY, rect), zza(this.centerX, this.centerY, rect2));
        invalidateSelf();
    }

    @Keep
    public void setScale(float f) {
        this.zzkt = f;
        invalidateSelf();
    }

    @Keep
    public void setTranslationX(float f) {
        this.zzlf = f;
        invalidateSelf();
    }

    @Keep
    public void setTranslationY(float f) {
        this.zzlg = f;
        invalidateSelf();
    }

    public final float getCenterX() {
        return this.centerX;
    }

    public final float getCenterY() {
        return this.centerY;
    }

    public final boolean zzb(float f, float f2) {
        return zzey.zza(f, f2, this.centerX, this.centerY) < this.zzjr;
    }

    private static float zza(float f, float f2, Rect rect) {
        float f3 = (float) rect.left;
        float f4 = (float) rect.top;
        float f5 = (float) rect.right;
        rect = (float) rect.bottom;
        float zza = zzey.zza(f, f2, f3, f4);
        f4 = zzey.zza(f, f2, f5, f4);
        f5 = zzey.zza(f, f2, f5, rect);
        f = zzey.zza(f, f2, f3, rect);
        if (zza > f4 && zza > f5 && zza > f) {
            f = zza;
        } else if (f4 > f5 && f4 > f) {
            f = f4;
        } else if (f5 > f) {
            f = f5;
        }
        return (float) Math.ceil((double) f);
    }

    public final Animator zzc(float f, float f2) {
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scale", new float[]{0.0f, 1.0f});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("translationX", new float[]{f, 0.0f});
        f = PropertyValuesHolder.ofFloat("translationY", new float[]{f2, 0.0f});
        f2 = PropertyValuesHolder.ofInt("alpha", new int[]{0, this.zzlh});
        f = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofFloat, ofFloat2, f, f2});
        f.setInterpolator(zzew.zzdx());
        return f.setDuration(350);
    }
}
