package com.google.android.material.card;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import com.google.android.material.R;

@RestrictTo({Scope.LIBRARY_GROUP})
class MaterialCardViewHelper {
    private static final int DEFAULT_STROKE_VALUE = -1;
    private final MaterialCardView materialCardView;
    private int strokeColor;
    private int strokeWidth;

    public MaterialCardViewHelper(MaterialCardView materialCardView) {
        this.materialCardView = materialCardView;
    }

    public void loadFromAttributes(TypedArray typedArray) {
        this.strokeColor = typedArray.getColor(R.styleable.MaterialCardView_strokeColor, -1);
        this.strokeWidth = typedArray.getDimensionPixelSize(R.styleable.MaterialCardView_strokeWidth, 0);
        updateForeground();
        adjustContentPadding();
    }

    void setStrokeColor(@ColorInt int i) {
        this.strokeColor = i;
        updateForeground();
    }

    @ColorInt
    int getStrokeColor() {
        return this.strokeColor;
    }

    void setStrokeWidth(@Dimension int i) {
        this.strokeWidth = i;
        updateForeground();
        adjustContentPadding();
    }

    @Dimension
    int getStrokeWidth() {
        return this.strokeWidth;
    }

    void updateForeground() {
        this.materialCardView.setForeground(createForegroundDrawable());
    }

    private Drawable createForegroundDrawable() {
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(this.materialCardView.getRadius());
        if (this.strokeColor != -1) {
            gradientDrawable.setStroke(this.strokeWidth, this.strokeColor);
        }
        return gradientDrawable;
    }

    private void adjustContentPadding() {
        this.materialCardView.setContentPadding(this.materialCardView.getContentPaddingLeft() + this.strokeWidth, this.materialCardView.getContentPaddingTop() + this.strokeWidth, this.materialCardView.getContentPaddingRight() + this.strokeWidth, this.materialCardView.getContentPaddingBottom() + this.strokeWidth);
    }
}
