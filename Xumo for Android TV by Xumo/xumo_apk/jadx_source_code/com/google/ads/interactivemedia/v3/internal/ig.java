package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.AdProgressInfo;

/* compiled from: IMASDK */
public class ig implements AdProgressInfo {
    private final double a;
    private final double b;
    private final int c;
    private final int d;
    private final double e;

    ig(double d, double d2, int i, int i2, double d3) {
        this.a = d;
        this.b = d2;
        this.c = i;
        this.d = i2;
        this.e = d3;
    }

    public double getCurrentTime() {
        return this.a;
    }

    public double getDuration() {
        return this.b;
    }

    public int getAdPosition() {
        return this.c;
    }

    public int getTotalAds() {
        return this.d;
    }

    public double getAdBreakDuration() {
        return this.e;
    }
}
