package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.CuePoint;

/* compiled from: IMASDK */
public final class iw implements CuePoint {
    private final double a;
    private final double b;
    private final boolean c;

    iw(double d, double d2, boolean z) {
        this.a = d;
        this.b = d2;
        this.c = z;
    }

    public double getStartTime() {
        return this.a;
    }

    public double getEndTime() {
        return this.b;
    }

    public boolean isPlayed() {
        return this.c;
    }
}
