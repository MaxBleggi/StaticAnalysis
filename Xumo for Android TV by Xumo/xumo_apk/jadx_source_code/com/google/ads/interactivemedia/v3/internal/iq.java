package com.google.ads.interactivemedia.v3.internal;

import android.view.ViewGroup;
import com.google.ads.interactivemedia.v3.api.BaseDisplayContainer;
import com.google.ads.interactivemedia.v3.api.CompanionAdSlot;
import com.google.ads.interactivemedia.v3.internal.lf.a;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/* compiled from: IMASDK */
public class iq implements BaseDisplayContainer {
    private static int d;
    private ViewGroup a;
    private Collection<CompanionAdSlot> b = Collections.emptyList();
    private Map<String, CompanionAdSlot> c = null;

    public ViewGroup getAdContainer() {
        return this.a;
    }

    public void setAdContainer(ViewGroup viewGroup) {
        this.a = viewGroup;
    }

    public Collection<CompanionAdSlot> getCompanionSlots() {
        return this.b;
    }

    public void setCompanionSlots(Collection<CompanionAdSlot> collection) {
        this.b = collection;
    }

    public Map<String, CompanionAdSlot> a() {
        if (this.c == null) {
            a aVar = new a();
            for (CompanionAdSlot companionAdSlot : this.b) {
                if (companionAdSlot != null) {
                    int i = d;
                    d = i + 1;
                    StringBuilder stringBuilder = new StringBuilder(20);
                    stringBuilder.append("compSlot_");
                    stringBuilder.append(i);
                    aVar.a(stringBuilder.toString(), companionAdSlot);
                }
            }
            this.c = aVar.a();
        }
        return this.c;
    }
}
