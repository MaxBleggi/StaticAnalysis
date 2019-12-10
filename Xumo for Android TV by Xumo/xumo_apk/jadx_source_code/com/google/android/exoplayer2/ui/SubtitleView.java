package com.google.android.exoplayer2.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;

public final class SubtitleView extends View implements TextOutput {
    public static final float DEFAULT_BOTTOM_PADDING_FRACTION = 0.08f;
    public static final float DEFAULT_TEXT_SIZE_FRACTION = 0.0533f;
    private boolean applyEmbeddedFontSizes;
    private boolean applyEmbeddedStyles;
    private float bottomPaddingFraction;
    private List<Cue> cues;
    private final List<SubtitlePainter> painters;
    private CaptionStyleCompat style;
    private float textSize;
    private int textSizeType;

    private float resolveTextSize(int i, float f, int i2, int i3) {
        switch (i) {
            case 0:
                return f * ((float) i3);
            case 1:
                return f * ((float) i2);
            case 2:
                return f;
            default:
                return Cue.DIMEN_UNSET;
        }
    }

    public SubtitleView(Context context) {
        this(context, null);
    }

    public SubtitleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.painters = new ArrayList();
        this.textSizeType = null;
        this.textSize = DEFAULT_TEXT_SIZE_FRACTION;
        this.applyEmbeddedStyles = true;
        this.applyEmbeddedFontSizes = true;
        this.style = CaptionStyleCompat.DEFAULT;
        this.bottomPaddingFraction = DEFAULT_BOTTOM_PADDING_FRACTION;
    }

    public void onCues(List<Cue> list) {
        setCues(list);
    }

    public void setCues(@Nullable List<Cue> list) {
        if (this.cues != list) {
            this.cues = list;
            if (list == null) {
                list = null;
            } else {
                list = list.size();
            }
            while (this.painters.size() < list) {
                this.painters.add(new SubtitlePainter(getContext()));
            }
            invalidate();
        }
    }

    public void setFixedTextSize(int i, float f) {
        Resources system;
        Context context = getContext();
        if (context == null) {
            system = Resources.getSystem();
        } else {
            system = context.getResources();
        }
        setTextSize(2, TypedValue.applyDimension(i, f, system.getDisplayMetrics()));
    }

    public void setUserDefaultTextSize() {
        float userCaptionFontScaleV19 = (Util.SDK_INT < 19 || isInEditMode()) ? 1.0f : getUserCaptionFontScaleV19();
        setFractionalTextSize(userCaptionFontScaleV19 * DEFAULT_TEXT_SIZE_FRACTION);
    }

    public void setFractionalTextSize(float f) {
        setFractionalTextSize(f, false);
    }

    public void setFractionalTextSize(float f, boolean z) {
        setTextSize(z, f);
    }

    private void setTextSize(int i, float f) {
        if (this.textSizeType != i || this.textSize != f) {
            this.textSizeType = i;
            this.textSize = f;
            invalidate();
        }
    }

    public void setApplyEmbeddedStyles(boolean z) {
        if (this.applyEmbeddedStyles != z || this.applyEmbeddedFontSizes != z) {
            this.applyEmbeddedStyles = z;
            this.applyEmbeddedFontSizes = z;
            invalidate();
        }
    }

    public void setApplyEmbeddedFontSizes(boolean z) {
        if (this.applyEmbeddedFontSizes != z) {
            this.applyEmbeddedFontSizes = z;
            invalidate();
        }
    }

    public void setUserDefaultStyle() {
        CaptionStyleCompat userCaptionStyleV19 = (Util.SDK_INT < 19 || !isCaptionManagerEnabled() || isInEditMode()) ? CaptionStyleCompat.DEFAULT : getUserCaptionStyleV19();
        setStyle(userCaptionStyleV19);
    }

    public void setStyle(CaptionStyleCompat captionStyleCompat) {
        if (this.style != captionStyleCompat) {
            this.style = captionStyleCompat;
            invalidate();
        }
    }

    public void setBottomPaddingFraction(float f) {
        if (this.bottomPaddingFraction != f) {
            this.bottomPaddingFraction = f;
            invalidate();
        }
    }

    public void dispatchDraw(Canvas canvas) {
        int i = 0;
        int size = this.cues == null ? 0 : r0.cues.size();
        int top = getTop();
        int bottom = getBottom();
        int left = getLeft() + getPaddingLeft();
        int paddingTop = getPaddingTop() + top;
        int right = getRight() - getPaddingRight();
        int paddingBottom = bottom - getPaddingBottom();
        if (paddingBottom > paddingTop) {
            if (right > left) {
                bottom -= top;
                top = paddingBottom - paddingTop;
                float resolveTextSize = resolveTextSize(r0.textSizeType, r0.textSize, bottom, top);
                if (resolveTextSize > 0.0f) {
                    while (i < size) {
                        Cue cue = (Cue) r0.cues.get(i);
                        int i2 = paddingBottom;
                        int i3 = right;
                        ((SubtitlePainter) r0.painters.get(i)).draw(cue, r0.applyEmbeddedStyles, r0.applyEmbeddedFontSizes, r0.style, resolveTextSize, resolveCueTextSize(cue, bottom, top), r0.bottomPaddingFraction, canvas, left, paddingTop, i3, i2);
                        i++;
                        paddingBottom = i2;
                        right = i3;
                    }
                }
            }
        }
    }

    private float resolveCueTextSize(Cue cue, int i, int i2) {
        if (cue.textSizeType != Integer.MIN_VALUE) {
            if (cue.textSize != Cue.DIMEN_UNSET) {
                return Math.max(resolveTextSize(cue.textSizeType, cue.textSize, i, i2), 0.0f);
            }
        }
        return 0.0f;
    }

    @TargetApi(19)
    private boolean isCaptionManagerEnabled() {
        return ((CaptioningManager) getContext().getSystemService("captioning")).isEnabled();
    }

    @TargetApi(19)
    private float getUserCaptionFontScaleV19() {
        return ((CaptioningManager) getContext().getSystemService("captioning")).getFontScale();
    }

    @TargetApi(19)
    private CaptionStyleCompat getUserCaptionStyleV19() {
        return CaptionStyleCompat.createFromCaptionStyle(((CaptioningManager) getContext().getSystemService("captioning")).getUserStyle());
    }
}
