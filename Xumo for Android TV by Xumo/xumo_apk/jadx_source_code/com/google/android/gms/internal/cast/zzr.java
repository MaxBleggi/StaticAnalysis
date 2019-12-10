package com.google.android.gms.internal.cast;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.cast.framework.IntroductoryOverlay;
import com.google.android.gms.cast.framework.IntroductoryOverlay.Builder;
import com.google.android.gms.cast.framework.IntroductoryOverlay.OnOverlayDismissedListener;
import com.google.android.gms.cast.framework.IntroductoryOverlay.zza;
import com.google.android.gms.cast.framework.R;

public final class zzr extends RelativeLayout implements IntroductoryOverlay {
    private Activity zzim;
    private OnOverlayDismissedListener zziq;
    private final boolean zzjh;
    private boolean zzjj;
    private int zzjm;
    private final zzu zzjn;

    public zzr(Builder builder) {
        this(builder, null, R.attr.castIntroOverlayStyle);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    private zzr(Builder builder, AttributeSet attributeSet, int i) {
        super(builder.getActivity(), null, i);
        this.zzim = builder.getActivity();
        this.zzjh = builder.zzaf();
        this.zziq = builder.zzad();
        attributeSet = this.zzim.getTheme().obtainStyledAttributes(null, R.styleable.CastIntroOverlay, i, R.style.CastIntroOverlay);
        if (builder.zzac() != 0) {
            i = new Rect();
            builder.zzac().getGlobalVisibleRect(i);
            this.zzjn = new zzu();
            this.zzjn.x = i.centerX();
            this.zzjn.y = i.centerY();
            i = this.zzjn;
            Xfermode porterDuffXfermode = new PorterDuffXfermode(Mode.MULTIPLY);
            Paint paint = new Paint();
            paint.setColor(-1);
            paint.setAlpha(0);
            paint.setXfermode(porterDuffXfermode);
            paint.setAntiAlias(true);
            i.zzjq = paint;
            this.zzjn.zzjr = builder.zzai();
            if (this.zzjn.zzjr == 0) {
                this.zzjn.zzjr = attributeSet.getDimension(R.styleable.CastIntroOverlay_castFocusRadius, 0.0f);
            }
        } else {
            this.zzjn = null;
        }
        LayoutInflater.from(this.zzim).inflate(R.layout.cast_intro_overlay, this);
        this.zzjm = builder.zzae();
        if (this.zzjm == 0) {
            this.zzjm = attributeSet.getColor(R.styleable.CastIntroOverlay_castBackgroundColor, Color.argb(0, 0, 0, 0));
        }
        TextView textView = (TextView) findViewById(R.id.textTitle);
        if (!TextUtils.isEmpty(builder.zzag())) {
            textView.setText(builder.zzag());
            int resourceId = attributeSet.getResourceId(R.styleable.CastIntroOverlay_castTitleTextAppearance, 0);
            if (resourceId != 0) {
                textView.setTextAppearance(this.zzim, resourceId);
            }
        }
        builder = builder.zzah();
        if (TextUtils.isEmpty(builder) != 0) {
            builder = attributeSet.getString(R.styleable.CastIntroOverlay_castButtonText);
        }
        i = attributeSet.getColor(R.styleable.CastIntroOverlay_castButtonBackgroundColor, Color.argb(0, 0, 0, 0));
        Button button = (Button) findViewById(R.id.button);
        button.setText(builder);
        button.getBackground().setColorFilter(i, Mode.MULTIPLY);
        builder = attributeSet.getResourceId(R.styleable.CastIntroOverlay_castButtonTextAppearance, 0);
        if (builder != null) {
            button.setTextAppearance(this.zzim, builder);
        }
        button.setOnClickListener(new zzs(this));
        attributeSet.recycle();
        setFitsSystemWindows(true);
    }

    public final void show() {
        if (this.zzim != null && !zzn.zzg(this.zzim)) {
            if (this.zzjh && zza.zze(this.zzim)) {
                this.zzim = null;
                this.zziq = null;
                return;
            }
            if (!this.zzjj) {
                this.zzjj = true;
                ((ViewGroup) this.zzim.getWindow().getDecorView()).addView(this);
            }
        }
    }

    public final void remove() {
        if (this.zzim != null) {
            ((ViewGroup) this.zzim.getWindow().getDecorView()).removeView(this);
            this.zzim = null;
        }
        this.zziq = null;
    }

    protected final void dispatchDraw(Canvas canvas) {
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap);
        canvas2.drawColor(this.zzjm);
        if (this.zzjn != null) {
            canvas2.drawCircle((float) this.zzjn.x, (float) this.zzjn.y, this.zzjn.zzjr, this.zzjn.zzjq);
        }
        canvas.drawBitmap(createBitmap, 0.0f, 0.0f, null);
        createBitmap.recycle();
        super.dispatchDraw(canvas);
    }

    protected final void onDetachedFromWindow() {
        if (this.zzim != null) {
            this.zzim = null;
        }
        super.onDetachedFromWindow();
    }

    private final void zzap() {
        zza.zzd(this.zzim);
        if (this.zziq != null) {
            this.zziq.onOverlayDismissed();
            this.zziq = null;
        }
        remove();
    }
}
