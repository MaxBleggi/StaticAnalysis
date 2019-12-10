package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.player.AdProgressProvider;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;

/* compiled from: IMASDK */
public class ih extends ji {
    protected final AdProgressProvider a;

    public ih(AdProgressProvider adProgressProvider, long j) {
        super(j);
        this.a = adProgressProvider;
    }

    public VideoProgressUpdate a() {
        return this.a.getAdProgress();
    }
}
