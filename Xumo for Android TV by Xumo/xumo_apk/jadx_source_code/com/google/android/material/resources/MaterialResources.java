package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.annotation.StyleableRes;
import androidx.appcompat.content.res.AppCompatResources;

@RestrictTo({Scope.LIBRARY_GROUP})
public class MaterialResources {
    private MaterialResources() {
    }

    @Nullable
    public static ColorStateList getColorStateList(Context context, TypedArray typedArray, @StyleableRes int i) {
        if (typedArray.hasValue(i)) {
            int resourceId = typedArray.getResourceId(i, 0);
            if (resourceId != 0) {
                context = AppCompatResources.getColorStateList(context, resourceId);
                if (context != null) {
                    return context;
                }
            }
        }
        return typedArray.getColorStateList(i);
    }

    @Nullable
    public static Drawable getDrawable(Context context, TypedArray typedArray, @StyleableRes int i) {
        if (typedArray.hasValue(i)) {
            int resourceId = typedArray.getResourceId(i, 0);
            if (resourceId != 0) {
                context = AppCompatResources.getDrawable(context, resourceId);
                if (context != null) {
                    return context;
                }
            }
        }
        return typedArray.getDrawable(i);
    }

    @Nullable
    public static TextAppearance getTextAppearance(Context context, TypedArray typedArray, @StyleableRes int i) {
        if (typedArray.hasValue(i)) {
            typedArray = typedArray.getResourceId(i, 0);
            if (typedArray != null) {
                return new TextAppearance(context, typedArray);
            }
        }
        return null;
    }

    @StyleableRes
    static int getIndexWithValue(TypedArray typedArray, @StyleableRes int i, @StyleableRes int i2) {
        return typedArray.hasValue(i) != null ? i : i2;
    }
}
