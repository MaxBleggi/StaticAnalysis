package com.google.ads.interactivemedia.v3.impl.data;

import com.google.ads.interactivemedia.v3.internal.km;

@km(a = j.class)
/* compiled from: IMASDK */
public abstract class o {

    /* compiled from: IMASDK */
    public static abstract class a {
        public abstract o build();

        public abstract a volume(float f);

        public a volumePercentage(int i) {
            return volume(((float) i) / 1120403456);
        }
    }

    public abstract float volume();

    public static a builder() {
        return new a();
    }
}
