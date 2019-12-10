package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/* compiled from: IMASDK */
public class jq extends LinearLayout {
    private jt a;
    private TextView b;
    private TextView c;
    private List<a> d;

    /* compiled from: IMASDK */
    public interface a {
        void c();
    }

    public jq(Context context, jt jtVar) {
        this(context, jtVar, new TextView(context), new TextView(context));
    }

    jq(Context context, jt jtVar, TextView textView, TextView textView2) {
        super(context);
        this.d = new ArrayList();
        this.a = jtVar;
        this.b = textView;
        this.c = textView2;
        textView.setTextColor(jtVar.i);
        textView.setIncludeFontPadding(false);
        textView.setGravity(16);
        textView.setEllipsize(TruncateAt.END);
        textView.setSingleLine();
        int a = js.a(jtVar.l, getResources().getDisplayMetrics().density);
        textView.setPadding(a, a, a, a);
        addView(textView, new LayoutParams(-2, -2, 1.0f));
        if (jtVar.m != null) {
            textView2.setTextColor(jtVar.p);
            textView2.setTextSize(jtVar.q);
            textView2.setText(jtVar.o);
            textView2.setIncludeFontPadding(false);
            textView2.setPadding(10, 10, 10, 10);
            textView2.setGravity(16);
            textView2.setEllipsize(TruncateAt.END);
            textView2.setSingleLine();
            context = new ShapeDrawable(new Shape(this) {
                public void draw(Canvas canvas, Paint paint) {
                    canvas.drawLine(0.0f, 0.0f, 0.0f, getHeight(), paint);
                }
            });
            context.getPaint().setColor(jtVar.f);
            context.getPaint().setStrokeWidth((float) jtVar.g);
            context.getPaint().setStyle(Style.STROKE);
            textView2.setBackgroundDrawable(context);
            textView2.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ jq a;

                {
                    this.a = r1;
                }

                public void onClick(View view) {
                    this.a.a();
                }
            });
            context = new LayoutParams(-2, -2);
            textView2.setLayoutParams(context);
            addView(textView2, context);
        }
    }

    public void a(String str) {
        this.b.setText(str);
    }

    public void b(String str) {
        this.c.setText(str);
    }

    public void a(a aVar) {
        this.d.add(aVar);
    }

    protected void a() {
        for (a c : this.d) {
            c.c();
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        new GradientDrawable(Orientation.TOP_BOTTOM, this.a.c).setBounds(0, 0, i, i2);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new Shape(this) {
            public void draw(Canvas canvas, Paint paint) {
                canvas.drawLine(0.0f, getHeight(), getWidth(), getHeight(), paint);
            }
        });
        shapeDrawable.getPaint().setColor(this.a.d);
        shapeDrawable.getPaint().setStrokeWidth((float) this.a.e);
        shapeDrawable.getPaint().setStyle(Style.STROKE);
        shapeDrawable.setBounds(0, 0, i, i2);
        setBackgroundDrawable(new LayerDrawable(new Drawable[]{i3, shapeDrawable}));
    }
}
