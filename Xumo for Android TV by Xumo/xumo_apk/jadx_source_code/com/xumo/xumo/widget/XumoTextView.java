package com.xumo.xumo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;
import com.xumo.xumo.repository.UserPreferences;
import java.lang.reflect.Field;

public class XumoTextView extends TextView {
    int mInnerColor;
    int mOuterColor;
    TextPaint m_TextPaint = getPaint();
    private boolean m_bDrawSideLine = null;

    public XumoTextView(Context context) {
        super(context);
    }

    public XumoTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public XumoTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setColor(boolean z, int i) {
        this.m_bDrawSideLine = z;
        switch (UserPreferences.getInstance().getTextColor()) {
            case true:
                this.mInnerColor = true;
                break;
            case true:
                this.mInnerColor = true;
                break;
            case true:
                this.mInnerColor = true;
                break;
            case true:
                this.mInnerColor = true;
                break;
            case true:
                this.mInnerColor = true;
                break;
            case true:
                this.mInnerColor = true;
                break;
            default:
                break;
        }
        this.mOuterColor = i;
    }

    public void setTextColor(int i) {
        super.setTextColor(i);
        this.mInnerColor = i;
    }

    protected void onDraw(Canvas canvas) {
        if (this.m_bDrawSideLine) {
            setTextColorUseReflection(this.mOuterColor);
            this.m_TextPaint.setStrokeWidth(3.0f);
            this.m_TextPaint.setStyle(Style.FILL_AND_STROKE);
            this.m_TextPaint.setFakeBoldText(true);
            super.onDraw(canvas);
            setTextColorUseReflection(this.mInnerColor);
            this.m_TextPaint.setStrokeWidth(0.0f);
            this.m_TextPaint.setStyle(Style.FILL_AND_STROKE);
            this.m_TextPaint.setFakeBoldText(false);
        }
        super.onDraw(canvas);
    }

    private void setTextColorUseReflection(int i) {
        try {
            Field declaredField = TextView.class.getDeclaredField("mCurTextColor");
            declaredField.setAccessible(true);
            declaredField.set(this, Integer.valueOf(i));
            declaredField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
        this.m_TextPaint.setColor(i);
    }
}
