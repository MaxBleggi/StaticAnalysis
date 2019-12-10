package com.google.ads.interactivemedia.v3.internal;

import android.view.ViewGroup;
import com.google.ads.interactivemedia.v3.api.CompanionAdSlot;
import com.google.ads.interactivemedia.v3.api.CompanionAdSlot.ClickListener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: IMASDK */
public class is implements CompanionAdSlot {
    private int a;
    private int b;
    private ViewGroup c;
    private String d;
    private final List<ClickListener> e = new ArrayList(1);

    public boolean isFilled() {
        return this.c.findViewWithTag(this.d) != null;
    }

    public int getWidth() {
        return this.a;
    }

    public int getHeight() {
        return this.b;
    }

    public void setSize(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public void a(String str) {
        this.d = str;
    }

    public ViewGroup getContainer() {
        return this.c;
    }

    public void setContainer(ViewGroup viewGroup) {
        this.c = viewGroup;
    }

    public void addClickListener(ClickListener clickListener) {
        this.e.add(clickListener);
    }

    public void removeClickListener(ClickListener clickListener) {
        this.e.remove(clickListener);
    }

    public List<ClickListener> a() {
        return this.e;
    }
}
