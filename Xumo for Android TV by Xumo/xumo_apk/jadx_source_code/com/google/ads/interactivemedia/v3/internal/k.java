package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
public class k {
    public float a(int i, int i2) {
        if (i2 > 0) {
            if (i > 0) {
                float f = ((float) i) / ((float) i2);
                if (f > 1.0f) {
                    f = 1.0f;
                }
                return f;
            }
        }
        return 0.0f;
    }
}
