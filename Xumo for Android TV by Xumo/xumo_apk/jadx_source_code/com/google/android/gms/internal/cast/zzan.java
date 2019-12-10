package com.google.android.gms.internal.cast;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;
import androidx.annotation.ColorInt;
import com.google.android.gms.cast.AdBreakInfo;
import java.util.List;

public final class zzan extends View {
    private List<AdBreakInfo> zzdi;
    private final int zzra;
    private int zzrb = 1;
    private Paint zzrc;

    public zzan(Context context) {
        super(context);
        context = getContext();
        if (context == null) {
            context = (int) Math.round(3.0d);
        } else {
            double d = (double) context.getResources().getDisplayMetrics().density;
            Double.isNaN(d);
            context = (int) Math.round(d * 3.0d);
        }
        this.zzra = context;
    }

    public final synchronized void zzj(int i) {
        this.zzrb = i;
    }

    public final synchronized void zzb(List<AdBreakInfo> list, @ColorInt int i) {
        this.zzdi = list;
        this.zzrc = new Paint(1);
        this.zzrc.setColor(-1);
        this.zzrc.setStyle(Style.FILL);
        invalidate();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected final synchronized void onDraw(@androidx.annotation.NonNull android.graphics.Canvas r9) {
        /*
        r8 = this;
        monitor-enter(r8);
        super.onDraw(r9);	 Catch:{ all -> 0x0076 }
        r0 = r8.zzdi;	 Catch:{ all -> 0x0076 }
        if (r0 == 0) goto L_0x0074;
    L_0x0008:
        r0 = r8.zzdi;	 Catch:{ all -> 0x0076 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0076 }
        if (r0 == 0) goto L_0x0011;
    L_0x0010:
        goto L_0x0074;
    L_0x0011:
        r0 = r8.getMeasuredHeight();	 Catch:{ all -> 0x0076 }
        r0 = (float) r0;	 Catch:{ all -> 0x0076 }
        r1 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r0 / r1;
        r0 = java.lang.Math.round(r0);	 Catch:{ all -> 0x0076 }
        r1 = r8.getMeasuredWidth();	 Catch:{ all -> 0x0076 }
        r2 = r8.getPaddingLeft();	 Catch:{ all -> 0x0076 }
        r1 = r1 - r2;
        r2 = r8.getPaddingRight();	 Catch:{ all -> 0x0076 }
        r1 = r1 - r2;
        r2 = r8.zzdi;	 Catch:{ all -> 0x0076 }
        r2 = r2.iterator();	 Catch:{ all -> 0x0076 }
    L_0x0031:
        r3 = r2.hasNext();	 Catch:{ all -> 0x0076 }
        if (r3 == 0) goto L_0x0072;
    L_0x0037:
        r3 = r2.next();	 Catch:{ all -> 0x0076 }
        r3 = (com.google.android.gms.cast.AdBreakInfo) r3;	 Catch:{ all -> 0x0076 }
        if (r3 == 0) goto L_0x0031;
    L_0x003f:
        r3 = r3.getPlaybackPositionInMs();	 Catch:{ all -> 0x0076 }
        r5 = 0;
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r7 < 0) goto L_0x0031;
    L_0x0049:
        r5 = r8.zzrb;	 Catch:{ all -> 0x0076 }
        r5 = (long) r5;
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r7 > 0) goto L_0x0031;
    L_0x0050:
        r3 = (double) r3;
        r5 = (double) r1;
        java.lang.Double.isNaN(r3);
        java.lang.Double.isNaN(r5);
        r3 = r3 * r5;
        r5 = r8.zzrb;	 Catch:{ all -> 0x0076 }
        r5 = (double) r5;
        java.lang.Double.isNaN(r5);
        r3 = r3 / r5;
        r3 = (int) r3;
        r4 = r8.getPaddingLeft();	 Catch:{ all -> 0x0076 }
        r4 = r4 + r3;
        r3 = (float) r4;	 Catch:{ all -> 0x0076 }
        r4 = (float) r0;	 Catch:{ all -> 0x0076 }
        r5 = r8.zzra;	 Catch:{ all -> 0x0076 }
        r5 = (float) r5;	 Catch:{ all -> 0x0076 }
        r6 = r8.zzrc;	 Catch:{ all -> 0x0076 }
        r9.drawCircle(r3, r4, r5, r6);	 Catch:{ all -> 0x0076 }
        goto L_0x0031;
    L_0x0072:
        monitor-exit(r8);
        return;
    L_0x0074:
        monitor-exit(r8);
        return;
    L_0x0076:
        r9 = move-exception;
        monitor-exit(r8);
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzan.onDraw(android.graphics.Canvas):void");
    }
}
