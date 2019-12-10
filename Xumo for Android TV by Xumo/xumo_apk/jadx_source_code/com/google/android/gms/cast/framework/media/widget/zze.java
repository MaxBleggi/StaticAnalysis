package com.google.android.gms.cast.framework.media.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.gms.internal.cast.zzdh;

final class zze {
    private static final zzdh zzbe = new zzdh("WidgetUtil");

    @TargetApi(17)
    public static Bitmap zza(Context context, Bitmap bitmap, float f, float f2) {
        zzbe.d("Begin blurring bitmap %s, original width = %d, original height = %d.", bitmap, Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()));
        f = Math.round(((float) bitmap.getWidth()) * 0.25f);
        f2 = Math.round(((float) bitmap.getHeight()) * 0.25f);
        bitmap = Bitmap.createScaledBitmap(bitmap, f, f2, false);
        Bitmap createBitmap = Bitmap.createBitmap(f, f2, bitmap.getConfig());
        context = RenderScript.create(context);
        Allocation createFromBitmap = Allocation.createFromBitmap(context, bitmap);
        Allocation createTyped = Allocation.createTyped(context, createFromBitmap.getType());
        ScriptIntrinsicBlur create = ScriptIntrinsicBlur.create(context, createFromBitmap.getElement());
        create.setInput(createFromBitmap);
        create.setRadius(7.5f);
        create.forEach(createTyped);
        createTyped.copyTo(createBitmap);
        context.destroy();
        zzbe.d("End blurring bitmap %s, original width = %d, original height = %d.", bitmap, Integer.valueOf(f), Integer.valueOf(f2));
        return createBitmap;
    }

    public static Drawable zza(Context context, int i, @DrawableRes int i2) {
        return zza(context, i, i2, 16842800, 0);
    }

    public static Drawable zzb(Context context, int i, @DrawableRes int i2) {
        return zza(context, i, i2, 0, 17170443);
    }

    private static Drawable zza(Context context, int i, @DrawableRes int i2, @AttrRes int i3, @ColorRes int i4) {
        i2 = DrawableCompat.wrap(context.getResources().getDrawable(i2).mutate());
        DrawableCompat.setTintMode(i2, Mode.SRC_IN);
        if (i != 0) {
            context = ContextCompat.getColorStateList(context, i);
        } else {
            if (i3 != 0) {
                context = context.obtainStyledAttributes(new int[]{i3});
                i3 = context.getColor(0, 0);
                context.recycle();
            } else {
                i3 = ContextCompat.getColor(context, i4);
            }
            context = ColorUtils.setAlphaComponent(i3, 128);
            int[] iArr = new int[]{i3, context};
            context = new int[2][];
            context[0] = new int[]{16842910};
            context[1] = new int[]{-16842910};
            context = new ColorStateList(context, iArr);
        }
        DrawableCompat.setTintList(i2, context);
        return i2;
    }
}
