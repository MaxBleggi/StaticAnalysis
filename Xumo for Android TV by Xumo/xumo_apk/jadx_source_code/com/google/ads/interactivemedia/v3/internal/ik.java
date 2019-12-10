package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.AdsManager;
import com.google.ads.interactivemedia.v3.api.AdsManagerLoadedEvent;
import com.google.ads.interactivemedia.v3.api.StreamManager;

/* compiled from: IMASDK */
public class ik implements AdsManagerLoadedEvent {
    private final AdsManager a;
    private final StreamManager b;
    private final Object c;

    ik(AdsManager adsManager, Object obj) {
        this.a = adsManager;
        this.b = null;
        this.c = obj;
    }

    ik(StreamManager streamManager, Object obj) {
        this.a = null;
        this.b = streamManager;
        this.c = obj;
    }

    public AdsManager getAdsManager() {
        return this.a;
    }

    public StreamManager getStreamManager() {
        return this.b;
    }

    public Object getUserRequestContext() {
        return this.c;
    }
}
