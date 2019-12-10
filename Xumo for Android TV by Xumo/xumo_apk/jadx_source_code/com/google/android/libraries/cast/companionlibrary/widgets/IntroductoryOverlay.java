package com.google.android.libraries.cast.companionlibrary.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.StringRes;
import androidx.core.view.ViewCompat;
import androidx.mediarouter.app.MediaRouteButton;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;

public class IntroductoryOverlay extends RelativeLayout {
    private static final String ALPHA_PROPERTY = "alpha";
    private static final long FADE_OUT_LENGTH_MS = 400;
    public static final String FTU_SHOWN_KEY = "ccl_ftu_shown";
    private static final float INVISIBLE_VALUE = 0.0f;
    private Bitmap mBitmap;
    private Button mButton;
    private int mCenterX;
    private int mCenterY;
    private float mFocusRadius;
    private Paint mHolePaint;
    private boolean mIsOverlayVisible;
    private boolean mIsSingleTime;
    private OnOverlayDismissedListener mListener;
    private int mOverlayColorId;
    private TextView mSubtitleText;
    private TextView mTitleText;

    public static class Builder {
        private String mButtonText;
        private Context mContext;
        private OnOverlayDismissedListener mListener;
        private int mOverlayColor;
        @ColorRes
        private float mRadius;
        private boolean mSingleTime;
        private String mSubtitleText;
        private String mTitleText;
        private View mView;

        public Builder(Context context) {
            this.mContext = context;
        }

        public IntroductoryOverlay build() {
            Utils.assertNotNull(this.mView, "MenuItem or MediaRouteButton");
            return new IntroductoryOverlay();
        }

        @TargetApi(11)
        public Builder setMenuItem(MenuItem menuItem) {
            this.mView = menuItem.getActionView();
            return this;
        }

        public Builder setMediaRouteButton(MediaRouteButton mediaRouteButton) {
            this.mView = mediaRouteButton;
            return this;
        }

        public Builder setOverlayColor(@ColorRes int i) {
            this.mOverlayColor = this.mContext.getResources().getColor(i);
            return this;
        }

        public Builder setFocusRadiusId(@DimenRes int i) {
            this.mRadius = this.mContext.getResources().getDimension(i);
            return this;
        }

        public Builder setFocusRadius(float f) {
            this.mRadius = f;
            return this;
        }

        public Builder setButtonText(String str) {
            this.mButtonText = str;
            return this;
        }

        public Builder setButtonText(@StringRes int i) {
            this.mButtonText = this.mContext.getResources().getString(i);
            return this;
        }

        public Builder setTitleText(@StringRes int i) {
            this.mTitleText = this.mContext.getResources().getString(i);
            return this;
        }

        public Builder setTitleText(String str) {
            this.mTitleText = str;
            return this;
        }

        public Builder setSubtitleText(@StringRes int i) {
            this.mSubtitleText = this.mContext.getResources().getString(i);
            return this;
        }

        public Builder setSubtitleText(String str) {
            this.mSubtitleText = str;
            return this;
        }

        public Builder setOnDismissed(OnOverlayDismissedListener onOverlayDismissedListener) {
            this.mListener = onOverlayDismissedListener;
            return this;
        }

        public Builder setSingleTime() {
            this.mSingleTime = true;
            return this;
        }
    }

    public interface OnOverlayDismissedListener {
        void onOverlayDismissed();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    private IntroductoryOverlay(Builder builder) {
        this(builder, null, R.styleable.CustomTheme_CCLIntroOverlayStyle);
    }

    @TargetApi(14)
    public IntroductoryOverlay(Builder builder, AttributeSet attributeSet, int i) {
        super(builder.mContext, attributeSet, i);
        this.mIsSingleTime = builder.mSingleTime;
        LayoutInflater.from(getContext()).inflate(R.layout.ccl_intro_overlay, this);
        this.mButton = (Button) findViewById(R.id.button);
        this.mTitleText = (TextView) findViewById(R.id.textTitle);
        this.mSubtitleText = (TextView) findViewById(R.id.textSubtitle);
        attributeSet = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.CCLIntroOverlay, R.attr.CCLIntroOverlayStyle, R.style.CCLIntroOverlay);
        if (builder.mOverlayColor != 0) {
            this.mOverlayColorId = builder.mOverlayColor;
        } else {
            this.mOverlayColorId = attributeSet.getColor(R.styleable.CCLIntroOverlay_ccl_IntroBackgroundColor, Color.argb(0, 0, 0, 0));
        }
        this.mFocusRadius = builder.mRadius;
        this.mListener = builder.mListener;
        if (this.mFocusRadius == 0) {
            this.mFocusRadius = attributeSet.getDimension(R.styleable.CCLIntroOverlay_ccl_IntroFocusRadius, 0.0f);
        }
        i = builder.mView;
        Rect rect = new Rect();
        i.getGlobalVisibleRect(rect);
        this.mCenterX = rect.centerX();
        this.mCenterY = rect.centerY();
        setFitsSystemWindows(1);
        setupHolePaint();
        setText(builder.mTitleText, builder.mSubtitleText);
        setButton(builder.mButtonText, attributeSet);
        attributeSet.recycle();
    }

    public void show() {
        if (this.mIsSingleTime && isFtuShown()) {
            this.mListener = null;
            return;
        }
        if (!this.mIsOverlayVisible) {
            this.mIsOverlayVisible = true;
            ((ViewGroup) ((Activity) getContext()).getWindow().getDecorView()).addView(this);
        }
    }

    public void remove() {
        if (getContext() != null) {
            ((ViewGroup) ((Activity) getContext()).getWindow().getDecorView()).removeView(this);
        }
        if (!(this.mBitmap == null || this.mBitmap.isRecycled())) {
            this.mBitmap.recycle();
        }
        this.mBitmap = null;
        this.mListener = null;
    }

    private void setButton(String str, TypedArray typedArray) {
        if (TextUtils.isEmpty(str)) {
            str = typedArray.getString(R.styleable.CCLIntroOverlay_ccl_IntroButtonText);
        }
        typedArray = typedArray.getColor(R.styleable.CCLIntroOverlay_ccl_IntroButtonBackgroundColor, Color.argb(0, 0, 0, 0));
        this.mButton.setText(str);
        this.mButton.getBackground().setColorFilter(typedArray, Mode.MULTIPLY);
        this.mButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                IntroductoryOverlay.this.fadeOut(IntroductoryOverlay.FADE_OUT_LENGTH_MS);
            }
        });
    }

    private void setText(CharSequence charSequence, CharSequence charSequence2) {
        if (!TextUtils.isEmpty(charSequence)) {
            this.mTitleText.setText(charSequence);
        }
        if (TextUtils.isEmpty(charSequence2) == null) {
            this.mSubtitleText.append(charSequence2);
        }
    }

    private void setupHolePaint() {
        Xfermode porterDuffXfermode = new PorterDuffXfermode(Mode.MULTIPLY);
        this.mHolePaint = new Paint();
        this.mHolePaint.setColor(ViewCompat.MEASURED_SIZE_MASK);
        this.mHolePaint.setAlpha(0);
        this.mHolePaint.setXfermode(porterDuffXfermode);
        this.mHolePaint.setAntiAlias(true);
    }

    protected void dispatchDraw(Canvas canvas) {
        this.mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        Canvas canvas2 = new Canvas(this.mBitmap);
        canvas2.drawColor(this.mOverlayColorId);
        canvas2.drawCircle((float) this.mCenterX, (float) this.mCenterY, this.mFocusRadius, this.mHolePaint);
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, null);
        super.dispatchDraw(canvas);
    }

    @TargetApi(11)
    public void fadeOut(long j) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ALPHA_PROPERTY, new float[]{0.0f});
        ofFloat.setDuration(j).addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                IntroductoryOverlay.this.setFtuShown();
                if (IntroductoryOverlay.this.mListener != null) {
                    IntroductoryOverlay.this.mListener.onOverlayDismissed();
                    IntroductoryOverlay.this.mListener = null;
                }
                IntroductoryOverlay.this.remove();
            }
        });
        ofFloat.start();
    }

    private void setFtuShown() {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(FTU_SHOWN_KEY, true).apply();
    }

    private boolean isFtuShown() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(FTU_SHOWN_KEY, false);
    }
}
