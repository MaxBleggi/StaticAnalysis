package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.RelativeSizeSpan;
import androidx.core.view.ViewCompat;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

final class SubtitlePainter {
    private static final float INNER_PADDING_RATIO = 0.125f;
    private static final String TAG = "SubtitlePainter";
    private boolean applyEmbeddedFontSizes;
    private boolean applyEmbeddedStyles;
    private int backgroundColor;
    private Rect bitmapRect;
    private float bottomPaddingFraction;
    private Bitmap cueBitmap;
    private float cueBitmapHeight;
    private float cueLine;
    private int cueLineAnchor;
    private int cueLineType;
    private float cuePosition;
    private int cuePositionAnchor;
    private float cueSize;
    private CharSequence cueText;
    private Alignment cueTextAlignment;
    private float cueTextSizePx;
    private float defaultTextSizePx;
    private int edgeColor;
    private int edgeType;
    private int foregroundColor;
    private final float outlineWidth;
    private final Paint paint;
    private int parentBottom;
    private int parentLeft;
    private int parentRight;
    private int parentTop;
    private final float shadowOffset;
    private final float shadowRadius;
    private final float spacingAdd;
    private final float spacingMult;
    private StaticLayout textLayout;
    private int textLeft;
    private int textPaddingX;
    private final TextPaint textPaint = new TextPaint();
    private int textTop;
    private int windowColor;

    public SubtitlePainter(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, new int[]{16843287, 16843288}, 0, 0);
        this.spacingAdd = (float) obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.spacingMult = obtainStyledAttributes.getFloat(1, 1.0f);
        obtainStyledAttributes.recycle();
        context = (float) Math.round((((float) context.getResources().getDisplayMetrics().densityDpi) * ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT) / 160.0f);
        this.outlineWidth = context;
        this.shadowRadius = context;
        this.shadowOffset = context;
        this.textPaint.setAntiAlias(true);
        this.textPaint.setSubpixelText(true);
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Style.FILL);
    }

    public void draw(Cue cue, boolean z, boolean z2, CaptionStyleCompat captionStyleCompat, float f, float f2, float f3, Canvas canvas, int i, int i2, int i3, int i4) {
        SubtitlePainter subtitlePainter = this;
        Cue cue2 = cue;
        boolean z3 = z;
        boolean z4 = z2;
        CaptionStyleCompat captionStyleCompat2 = captionStyleCompat;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        Canvas canvas2 = canvas;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int i8 = i4;
        boolean z5 = cue2.bitmap == null;
        int i9 = ViewCompat.MEASURED_STATE_MASK;
        if (z5) {
            if (!TextUtils.isEmpty(cue2.text)) {
                i9 = (cue2.windowColorSet && z3) ? cue2.windowColor : captionStyleCompat2.windowColor;
            } else {
                return;
            }
        }
        if (areCharSequencesEqual(subtitlePainter.cueText, cue2.text) && Util.areEqual(subtitlePainter.cueTextAlignment, cue2.textAlignment) && subtitlePainter.cueBitmap == cue2.bitmap && subtitlePainter.cueLine == cue2.line && subtitlePainter.cueLineType == cue2.lineType && Util.areEqual(Integer.valueOf(subtitlePainter.cueLineAnchor), Integer.valueOf(cue2.lineAnchor)) && subtitlePainter.cuePosition == cue2.position && Util.areEqual(Integer.valueOf(subtitlePainter.cuePositionAnchor), Integer.valueOf(cue2.positionAnchor)) && subtitlePainter.cueSize == cue2.size && subtitlePainter.cueBitmapHeight == cue2.bitmapHeight && subtitlePainter.applyEmbeddedStyles == z3 && subtitlePainter.applyEmbeddedFontSizes == z4 && subtitlePainter.foregroundColor == captionStyleCompat2.foregroundColor && subtitlePainter.backgroundColor == captionStyleCompat2.backgroundColor && subtitlePainter.windowColor == i9 && subtitlePainter.edgeType == captionStyleCompat2.edgeType && subtitlePainter.edgeColor == captionStyleCompat2.edgeColor && Util.areEqual(subtitlePainter.textPaint.getTypeface(), captionStyleCompat2.typeface) && subtitlePainter.defaultTextSizePx == f4 && subtitlePainter.cueTextSizePx == f5 && subtitlePainter.bottomPaddingFraction == f6 && subtitlePainter.parentLeft == i5 && subtitlePainter.parentTop == i6 && subtitlePainter.parentRight == i7 && subtitlePainter.parentBottom == i8) {
            drawLayout(canvas, z5);
            return;
        }
        canvas2 = canvas;
        subtitlePainter.cueText = cue2.text;
        subtitlePainter.cueTextAlignment = cue2.textAlignment;
        subtitlePainter.cueBitmap = cue2.bitmap;
        subtitlePainter.cueLine = cue2.line;
        subtitlePainter.cueLineType = cue2.lineType;
        subtitlePainter.cueLineAnchor = cue2.lineAnchor;
        subtitlePainter.cuePosition = cue2.position;
        subtitlePainter.cuePositionAnchor = cue2.positionAnchor;
        subtitlePainter.cueSize = cue2.size;
        subtitlePainter.cueBitmapHeight = cue2.bitmapHeight;
        subtitlePainter.applyEmbeddedStyles = z3;
        subtitlePainter.applyEmbeddedFontSizes = z4;
        subtitlePainter.foregroundColor = captionStyleCompat2.foregroundColor;
        subtitlePainter.backgroundColor = captionStyleCompat2.backgroundColor;
        subtitlePainter.windowColor = i9;
        subtitlePainter.edgeType = captionStyleCompat2.edgeType;
        subtitlePainter.edgeColor = captionStyleCompat2.edgeColor;
        subtitlePainter.textPaint.setTypeface(captionStyleCompat2.typeface);
        subtitlePainter.defaultTextSizePx = f4;
        subtitlePainter.cueTextSizePx = f5;
        subtitlePainter.bottomPaddingFraction = f6;
        subtitlePainter.parentLeft = i5;
        subtitlePainter.parentTop = i6;
        subtitlePainter.parentRight = i7;
        subtitlePainter.parentBottom = i8;
        if (z5) {
            setupTextLayout();
        } else {
            setupBitmapLayout();
        }
        drawLayout(canvas2, z5);
    }

    private void setupTextLayout() {
        int i = this.parentRight - this.parentLeft;
        int i2 = this.parentBottom - this.parentTop;
        this.textPaint.setTextSize(this.defaultTextSizePx);
        int i3 = (int) ((this.defaultTextSizePx * INNER_PADDING_RATIO) + 0.5f);
        int i4 = i3 * 2;
        int i5 = i - i4;
        if (this.cueSize != Cue.DIMEN_UNSET) {
            i5 = (int) (((float) i5) * r0.cueSize);
        }
        if (i5 <= 0) {
            Log.w(TAG, "Skipped drawing subtitle cue (insufficient space)");
            return;
        }
        SpannableStringBuilder spannableStringBuilder;
        int length;
        CharSequence charSequence;
        CharSequence charSequence2 = r0.cueText;
        if (r0.applyEmbeddedStyles) {
            if (!r0.applyEmbeddedFontSizes) {
                spannableStringBuilder = new SpannableStringBuilder(charSequence2);
                length = spannableStringBuilder.length();
                AbsoluteSizeSpan[] absoluteSizeSpanArr = (AbsoluteSizeSpan[]) spannableStringBuilder.getSpans(0, length, AbsoluteSizeSpan.class);
                RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) spannableStringBuilder.getSpans(0, length, RelativeSizeSpan.class);
                for (Object removeSpan : absoluteSizeSpanArr) {
                    spannableStringBuilder.removeSpan(removeSpan);
                }
                for (Object removeSpan2 : relativeSizeSpanArr) {
                    spannableStringBuilder.removeSpan(removeSpan2);
                }
            } else if (r0.cueTextSizePx > 0.0f) {
                spannableStringBuilder = new SpannableStringBuilder(charSequence2);
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan((int) r0.cueTextSizePx), 0, spannableStringBuilder.length(), 16711680);
            }
            charSequence2 = spannableStringBuilder;
        } else {
            charSequence2 = charSequence2.toString();
        }
        if (Color.alpha(r0.backgroundColor) > 0) {
            spannableStringBuilder = new SpannableStringBuilder(charSequence2);
            spannableStringBuilder.setSpan(new BackgroundColorSpan(r0.backgroundColor), 0, spannableStringBuilder.length(), 16711680);
            charSequence = spannableStringBuilder;
        } else {
            charSequence = charSequence2;
        }
        Alignment alignment = r0.cueTextAlignment == null ? Alignment.ALIGN_CENTER : r0.cueTextAlignment;
        r0.textLayout = new StaticLayout(charSequence, r0.textPaint, i5, alignment, r0.spacingMult, r0.spacingAdd, true);
        length = r0.textLayout.getHeight();
        int lineCount = r0.textLayout.getLineCount();
        int i6 = 0;
        for (int i7 = 0; i7 < lineCount; i7++) {
            i6 = Math.max((int) Math.ceil((double) r0.textLayout.getLineWidth(i7)), i6);
        }
        if (r0.cueSize == Cue.DIMEN_UNSET || i6 >= i5) {
            i5 = i6;
        }
        i5 += i4;
        if (r0.cuePosition != Cue.DIMEN_UNSET) {
            i = Math.round(((float) i) * r0.cuePosition) + r0.parentLeft;
            if (r0.cuePositionAnchor == 2) {
                i -= i5;
            } else if (r0.cuePositionAnchor == 1) {
                i = ((i * 2) - i5) / 2;
            }
            i = Math.max(i, r0.parentLeft);
            i4 = Math.min(i5 + i, r0.parentRight);
        } else {
            i = (i - i5) / 2;
            i4 = i + i5;
        }
        int i8 = i4 - i;
        if (i8 <= 0) {
            Log.w(TAG, "Skipped drawing subtitle cue (invalid horizontal positioning)");
            return;
        }
        if (r0.cueLine != Cue.DIMEN_UNSET) {
            if (r0.cueLineType == 0) {
                i2 = Math.round(((float) i2) * r0.cueLine) + r0.parentTop;
            } else {
                i2 = r0.textLayout.getLineBottom(0) - r0.textLayout.getLineTop(0);
                if (r0.cueLine >= 0.0f) {
                    i2 = Math.round(r0.cueLine * ((float) i2)) + r0.parentTop;
                } else {
                    i2 = Math.round((r0.cueLine + 1.0f) * ((float) i2)) + r0.parentBottom;
                }
            }
            if (r0.cueLineAnchor == 2) {
                i2 -= length;
            } else if (r0.cueLineAnchor == 1) {
                i2 = ((i2 * 2) - length) / 2;
            }
            if (i2 + length > r0.parentBottom) {
                i2 = r0.parentBottom - length;
            } else if (i2 < r0.parentTop) {
                i2 = r0.parentTop;
            }
        } else {
            i2 = (r0.parentBottom - length) - ((int) (((float) i2) * r0.bottomPaddingFraction));
        }
        r0.textLayout = new StaticLayout(charSequence, r0.textPaint, i8, alignment, r0.spacingMult, r0.spacingAdd, true);
        r0.textLeft = i;
        r0.textTop = i2;
        r0.textPaddingX = i3;
    }

    private void setupBitmapLayout() {
        int round;
        float f;
        int round2;
        int round3;
        float f2 = (float) (this.parentRight - this.parentLeft);
        float f3 = ((float) this.parentLeft) + (this.cuePosition * f2);
        float f4 = (float) (this.parentBottom - this.parentTop);
        float f5 = ((float) this.parentTop) + (this.cueLine * f4);
        int round4 = Math.round(f2 * this.cueSize);
        if (this.cueBitmapHeight != Cue.DIMEN_UNSET) {
            round = Math.round(f4 * this.cueBitmapHeight);
        } else {
            round = Math.round(((float) round4) * (((float) this.cueBitmap.getHeight()) / ((float) this.cueBitmap.getWidth())));
        }
        if (this.cueLineAnchor == 2) {
            f = (float) round4;
        } else {
            if (this.cueLineAnchor == 1) {
                f = (float) (round4 / 2);
            }
            round2 = Math.round(f3);
            if (this.cuePositionAnchor != 2) {
                f = (float) round;
            } else {
                if (this.cuePositionAnchor == 1) {
                    f = (float) (round / 2);
                }
                round3 = Math.round(f5);
                this.bitmapRect = new Rect(round2, round3, round4 + round2, round + round3);
            }
            f5 -= f;
            round3 = Math.round(f5);
            this.bitmapRect = new Rect(round2, round3, round4 + round2, round + round3);
        }
        f3 -= f;
        round2 = Math.round(f3);
        if (this.cuePositionAnchor != 2) {
            if (this.cuePositionAnchor == 1) {
                f = (float) (round / 2);
            }
            round3 = Math.round(f5);
            this.bitmapRect = new Rect(round2, round3, round4 + round2, round + round3);
        }
        f = (float) round;
        f5 -= f;
        round3 = Math.round(f5);
        this.bitmapRect = new Rect(round2, round3, round4 + round2, round + round3);
    }

    private void drawLayout(Canvas canvas, boolean z) {
        if (z) {
            drawTextLayout(canvas);
        } else {
            drawBitmapLayout(canvas);
        }
    }

    private void drawTextLayout(Canvas canvas) {
        StaticLayout staticLayout = this.textLayout;
        if (staticLayout != null) {
            int save = canvas.save();
            canvas.translate((float) this.textLeft, (float) this.textTop);
            if (Color.alpha(this.windowColor) > 0) {
                this.paint.setColor(this.windowColor);
                canvas.drawRect((float) (-this.textPaddingX), 0.0f, (float) (staticLayout.getWidth() + this.textPaddingX), (float) staticLayout.getHeight(), this.paint);
            }
            Object obj = 1;
            if (this.edgeType == 1) {
                this.textPaint.setStrokeJoin(Join.ROUND);
                this.textPaint.setStrokeWidth(this.outlineWidth);
                this.textPaint.setColor(this.edgeColor);
                this.textPaint.setStyle(Style.FILL_AND_STROKE);
                staticLayout.draw(canvas);
            } else if (this.edgeType == 2) {
                this.textPaint.setShadowLayer(this.shadowRadius, this.shadowOffset, this.shadowOffset, this.edgeColor);
            } else if (this.edgeType == 3 || this.edgeType == 4) {
                int i;
                if (this.edgeType != 3) {
                    obj = null;
                }
                int i2 = -1;
                if (obj != null) {
                    i = -1;
                } else {
                    i = this.edgeColor;
                }
                if (obj != null) {
                    i2 = this.edgeColor;
                }
                float f = this.shadowRadius / ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT;
                this.textPaint.setColor(this.foregroundColor);
                this.textPaint.setStyle(Style.FILL);
                float f2 = -f;
                this.textPaint.setShadowLayer(this.shadowRadius, f2, f2, i);
                staticLayout.draw(canvas);
                this.textPaint.setShadowLayer(this.shadowRadius, f, f, i2);
            }
            this.textPaint.setColor(this.foregroundColor);
            this.textPaint.setStyle(Style.FILL);
            staticLayout.draw(canvas);
            this.textPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            canvas.restoreToCount(save);
        }
    }

    private void drawBitmapLayout(Canvas canvas) {
        canvas.drawBitmap(this.cueBitmap, null, this.bitmapRect, null);
    }

    private static boolean areCharSequencesEqual(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence != charSequence2) {
            if (charSequence == null || charSequence.equals(charSequence2) == null) {
                return null;
            }
        }
        return true;
    }
}
