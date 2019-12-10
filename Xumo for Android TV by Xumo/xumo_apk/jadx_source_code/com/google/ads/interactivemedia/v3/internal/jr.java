package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;

/* compiled from: IMASDK */
public class jr extends FrameLayout {
    private final float a = getResources().getDisplayMetrics().density;
    private final TextView b;

    /* compiled from: IMASDK */
    private static class a extends ShapeDrawable {
        private Paint a = new Paint();
        private Paint b;

        public a() {
            super(new Shape() {
                private Path a;

                public void onResize(float f, float f2) {
                    this.a = new Path();
                    this.a.moveTo(getWidth(), getHeight());
                    f2 = (float) 8.4E-45f;
                    this.a.lineTo(f2, getHeight());
                    float f3 = (float) 12;
                    this.a.arcTo(new RectF(0.0f, getHeight() - f3, f3, getHeight()), 90.0f, 90.0f);
                    this.a.lineTo(0.0f, f2);
                    this.a.arcTo(new RectF(0.0f, 0.0f, f3, f3), 180.0f, 90.0f);
                    this.a.lineTo(getWidth(), 0.0f);
                }

                public void draw(Canvas canvas, Paint paint) {
                    canvas.drawPath(this.a, paint);
                }
            });
            this.a.setAntiAlias(true);
            this.a.setStyle(Style.STROKE);
            this.a.setStrokeWidth(1.0f);
            this.a.setARGB(150, 255, 255, 255);
            this.b = new Paint();
            this.b.setStyle(Style.FILL);
            this.b.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.b.setAlpha(140);
        }

        protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
            shape.draw(canvas, this.b);
            shape.draw(canvas, this.a);
        }
    }

    public jr(Context context, jt jtVar) {
        super(context);
        setBackgroundDrawable(new a());
        jtVar = a(jtVar.t, this.a);
        setPadding(jtVar, jtVar, jtVar, jtVar);
        this.b = new TextView(context);
        this.b.setTextColor(-3355444);
        this.b.setIncludeFontPadding(null);
        this.b.setGravity(17);
        addView(this.b);
    }

    private int a(int i, float f) {
        return (int) ((((float) i) * f) + 0.5f);
    }

    public void a(String str) {
        this.b.setText(str);
    }
}
