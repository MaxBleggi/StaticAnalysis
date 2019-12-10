package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.AdsRenderingSettings;
import com.google.ads.interactivemedia.v3.api.UiElement;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import java.util.List;
import java.util.Set;

/* compiled from: IMASDK */
public class im implements AdsRenderingSettings {
    @gt(a = "bitrate")
    private int a = -1;
    @gt(a = "mimeTypes")
    private List<String> b = null;
    @gt(a = "uiElements")
    private Set<UiElement> c;
    @gt(a = "enablePreloading")
    private boolean d;
    @gt(a = "enableFocusSkipButton")
    private boolean e = true;
    @gt(a = "playAdsAfterTime")
    private double f = -1.0d;
    @gt(a = "disableUi")
    private boolean g = false;
    @gt(a = "loadVideoTimeout")
    private int h = -1;
    private boolean i = true;

    public int getBitrateKbps() {
        return this.a;
    }

    public void setBitrateKbps(int i) {
        this.a = i;
    }

    public List<String> getMimeTypes() {
        return this.b;
    }

    public void setMimeTypes(List<String> list) {
        this.b = list;
    }

    public void setLoadVideoTimeout(int i) {
        this.h = i;
    }

    public void setUiElements(Set<UiElement> set) {
        this.c = set;
    }

    public boolean getEnablePreloading() {
        return this.d;
    }

    public void setEnablePreloading(boolean z) {
        this.d = z;
    }

    public boolean getFocusSkipButtonWhenAvailable() {
        return this.e;
    }

    public void setFocusSkipButtonWhenAvailable(boolean z) {
        this.e = z;
    }

    public String toString() {
        int i = this.a;
        String valueOf = String.valueOf(this.b);
        String valueOf2 = String.valueOf(this.c);
        boolean z = this.d;
        double d = this.f;
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + TsExtractor.TS_STREAM_TYPE_SPLICE_INFO) + String.valueOf(valueOf2).length());
        stringBuilder.append("AdsRenderingSettings [bitrate=");
        stringBuilder.append(i);
        stringBuilder.append(", mimeTypes=");
        stringBuilder.append(valueOf);
        stringBuilder.append(", uiElements=");
        stringBuilder.append(valueOf2);
        stringBuilder.append(", enablePreloading=");
        stringBuilder.append(z);
        stringBuilder.append(", playAdsAfterTime=");
        stringBuilder.append(d);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public boolean isRenderCompanions() {
        return this.i;
    }

    public void setRenderCompanions(boolean z) {
        this.i = z;
    }

    public void setPlayAdsAfterTime(double d) {
        this.f = d;
    }

    public void setDisableUi(boolean z) {
        this.g = z;
    }

    public boolean getDisableUi() {
        return this.g;
    }
}
