package com.google.ads.interactivemedia.v3.impl.data;

import com.google.ads.interactivemedia.v3.api.AdPodInfo;
import com.google.ads.interactivemedia.v3.internal.mb;
import com.google.ads.interactivemedia.v3.internal.md;

/* compiled from: IMASDK */
public class c implements AdPodInfo {
    public int adPosition = 1;
    public boolean isBumper = false;
    public double maxDuration = -1.0d;
    public int podIndex;
    public double timeOffset;
    public int totalAds = 1;

    public int getTotalAds() {
        return this.totalAds;
    }

    public int getAdPosition() {
        return this.adPosition;
    }

    public boolean isBumper() {
        return this.isBumper;
    }

    public double getMaxDuration() {
        return this.maxDuration;
    }

    public int getPodIndex() {
        return this.podIndex;
    }

    public double getTimeOffset() {
        return this.timeOffset;
    }

    public int hashCode() {
        return md.a(this, new String[0]);
    }

    public boolean equals(Object obj) {
        return mb.a((Object) this, obj, new String[0]);
    }

    public String toString() {
        int i = this.totalAds;
        int i2 = this.adPosition;
        boolean z = this.isBumper;
        double d = this.maxDuration;
        int i3 = this.podIndex;
        double d2 = this.timeOffset;
        StringBuilder stringBuilder = new StringBuilder(169);
        stringBuilder.append("AdPodInfo [totalAds=");
        stringBuilder.append(i);
        stringBuilder.append(", adPosition=");
        stringBuilder.append(i2);
        stringBuilder.append(", isBumper=");
        stringBuilder.append(z);
        stringBuilder.append(", maxDuration=");
        stringBuilder.append(d);
        stringBuilder.append(", podIndex=");
        stringBuilder.append(i3);
        stringBuilder.append(", timeOffset=");
        stringBuilder.append(d2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
