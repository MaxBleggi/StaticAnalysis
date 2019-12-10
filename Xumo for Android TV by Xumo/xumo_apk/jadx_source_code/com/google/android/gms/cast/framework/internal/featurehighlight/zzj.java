package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.graphics.Rect;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.internal.cast.zzez;

final class zzj {
    private final Rect zzkw = new Rect();
    private final int zzkx;
    private final int zzky;
    private final int zzkz;
    private final int zzla;
    private final zza zzlb;

    zzj(zza com_google_android_gms_cast_framework_internal_featurehighlight_zza) {
        this.zzlb = (zza) zzez.checkNotNull(com_google_android_gms_cast_framework_internal_featurehighlight_zza);
        com_google_android_gms_cast_framework_internal_featurehighlight_zza = com_google_android_gms_cast_framework_internal_featurehighlight_zza.getResources();
        this.zzkx = com_google_android_gms_cast_framework_internal_featurehighlight_zza.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_inner_radius);
        this.zzky = com_google_android_gms_cast_framework_internal_featurehighlight_zza.getDimensionPixelOffset(R.dimen.cast_libraries_material_featurehighlight_inner_margin);
        this.zzkz = com_google_android_gms_cast_framework_internal_featurehighlight_zza.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_text_max_width);
        this.zzla = com_google_android_gms_cast_framework_internal_featurehighlight_zza.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_text_horizontal_offset);
    }

    final void zza(Rect rect, Rect rect2) {
        View zzar = this.zzlb.zzar();
        int i = 0;
        if (!rect.isEmpty()) {
            if (!rect2.isEmpty()) {
                int centerY = rect.centerY();
                int centerX = rect.centerX();
                if (centerY < rect2.centerY()) {
                    i = 1;
                }
                int max = Math.max(this.zzkx * 2, rect.height()) / 2;
                int i2 = (centerY + max) + this.zzky;
                if (i != 0) {
                    zza(zzar, rect2.width(), rect2.bottom - i2);
                    rect2 = zza(zzar, rect2.left, rect2.right, zzar.getMeasuredWidth(), centerX);
                    zzar.layout(rect2, i2, zzar.getMeasuredWidth() + rect2, zzar.getMeasuredHeight() + i2);
                } else {
                    i2 = (centerY - max) - this.zzky;
                    zza(zzar, rect2.width(), i2 - rect2.top);
                    rect2 = zza(zzar, rect2.left, rect2.right, zzar.getMeasuredWidth(), centerX);
                    zzar.layout(rect2, i2 - zzar.getMeasuredHeight(), zzar.getMeasuredWidth() + rect2, i2);
                }
                this.zzkw.set(zzar.getLeft(), zzar.getTop(), zzar.getRight(), zzar.getBottom());
                this.zzlb.zzas().zzb(rect, this.zzkw);
                this.zzlb.zzat().zza(rect);
            }
        }
        zzar.layout(0, 0, 0, 0);
        this.zzkw.set(zzar.getLeft(), zzar.getTop(), zzar.getRight(), zzar.getBottom());
        this.zzlb.zzas().zzb(rect, this.zzkw);
        this.zzlb.zzat().zza(rect);
    }

    private final int zza(View view, int i, int i2, int i3, int i4) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        int i5 = i3 / 2;
        if (i4 - i <= i2 - i4) {
            i4 = (i4 - i5) + this.zzla;
        } else {
            i4 = (i4 - i5) - this.zzla;
        }
        if (i4 - marginLayoutParams.leftMargin < i) {
            return i + marginLayoutParams.leftMargin;
        }
        return (i4 + i3) + marginLayoutParams.rightMargin > i2 ? (i2 - i3) - marginLayoutParams.rightMargin : i4;
    }

    private final void zza(View view, int i, int i2) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        view.measure(MeasureSpec.makeMeasureSpec(Math.min((i - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin, this.zzkz), 1073741824), MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
    }
}
