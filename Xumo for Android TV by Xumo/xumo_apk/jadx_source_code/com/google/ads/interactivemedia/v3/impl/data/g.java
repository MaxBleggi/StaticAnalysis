package com.google.ads.interactivemedia.v3.impl.data;

/* compiled from: IMASDK */
final class g extends k {
    private final double end;
    private final boolean played;
    private final double start;

    g(double d, double d2, boolean z) {
        this.start = d;
        this.end = d2;
        this.played = z;
    }

    public double start() {
        return this.start;
    }

    public double end() {
        return this.end;
    }

    public boolean played() {
        return this.played;
    }

    public String toString() {
        double d = this.start;
        double d2 = this.end;
        boolean z = this.played;
        StringBuilder stringBuilder = new StringBuilder(88);
        stringBuilder.append("CuePointData{start=");
        stringBuilder.append(d);
        stringBuilder.append(", end=");
        stringBuilder.append(d2);
        stringBuilder.append(", played=");
        stringBuilder.append(z);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof k)) {
            return false;
        }
        k kVar = (k) obj;
        if (Double.doubleToLongBits(this.start) != Double.doubleToLongBits(kVar.start()) || Double.doubleToLongBits(this.end) != Double.doubleToLongBits(kVar.end()) || this.played != kVar.played()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((int) ((Double.doubleToLongBits(this.start) >>> 32) ^ Double.doubleToLongBits(this.start))) ^ 1000003) * 1000003) ^ ((int) ((Double.doubleToLongBits(this.end) >>> 32) ^ Double.doubleToLongBits(this.end)))) * 1000003) ^ (this.played ? 1231 : 1237);
    }
}
