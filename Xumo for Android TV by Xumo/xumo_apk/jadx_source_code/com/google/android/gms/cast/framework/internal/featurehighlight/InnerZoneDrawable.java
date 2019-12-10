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
import androidx.annotation.Keep;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.internal.cast.zzew;

class InnerZoneDrawable extends Drawable {
    private float centerX;
    private float centerY;
    private final Paint zzjq = new Paint();
    private float zzjr;
    private final Rect zzjv = new Rect();
    private final Paint zzkq = new Paint();
    private final int zzkr;
    private final int zzks;
    private float zzkt = 1.0f;
    private float zzku;
    private float zzkv;

    public InnerZoneDrawable(Context context) {
        context = context.getResources();
        this.zzkr = context.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_inner_radius);
        this.zzks = context.getInteger(R.integer.cast_libraries_material_featurehighlight_pulse_base_alpha);
        this.zzjq.setAntiAlias(true);
        this.zzjq.setStyle(Style.FILL);
        this.zzkq.setAntiAlias(true);
        this.zzkq.setStyle(Style.FILL);
        this.zzjq.setColor(-1);
        this.zzkq.setColor(-1);
        invalidateSelf();
    }

    public int getOpacity() {
        return -3;
    }

    public void draw(Canvas canvas) {
        if (this.zzkv > 0.0f) {
            float f = this.zzjr * this.zzku;
            this.zzkq.setAlpha((int) (((float) this.zzks) * this.zzkv));
            canvas.drawCircle(this.centerX, this.centerY, f, this.zzkq);
        }
        canvas.drawCircle(this.centerX, this.centerY, this.zzjr * this.zzkt, this.zzjq);
    }

    public void setAlpha(int i) {
        this.zzjq.setAlpha(i);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.zzjq.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Keep
    public void setScale(float f) {
        this.zzkt = f;
        invalidateSelf();
    }

    @Keep
    public void setPulseScale(float f) {
        this.zzku = f;
        invalidateSelf();
    }

    @Keep
    public void setPulseAlpha(float f) {
        this.zzkv = f;
        invalidateSelf();
    }

    public final void zza(Rect rect) {
        this.zzjv.set(rect);
        this.centerX = this.zzjv.exactCenterX();
        this.centerY = this.zzjv.exactCenterY();
        this.zzjr = Math.max((float) this.zzkr, Math.max(((float) this.zzjv.width()) / ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ((float) this.zzjv.height()) / ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT));
        invalidateSelf();
    }

    public final Animator zzav() {
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scale", new float[]{0.0f});
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("alpha", new int[]{0});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("pulseScale", new float[]{0.0f});
        PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat("pulseAlpha", new float[]{0.0f});
        Animator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofFloat, ofInt, ofFloat2, ofFloat3});
        ofPropertyValuesHolder.setInterpolator(zzew.zzdy());
        return ofPropertyValuesHolder.setDuration(200);
    }
}
