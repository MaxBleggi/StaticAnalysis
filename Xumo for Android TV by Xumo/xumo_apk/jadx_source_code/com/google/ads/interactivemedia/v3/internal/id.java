package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.AdError;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;

/* compiled from: IMASDK */
public class id implements AdErrorEvent {
    private final AdError a;
    private final Object b;

    id(AdError adError) {
        this.a = adError;
        this.b = null;
    }

    id(AdError adError, Object obj) {
        this.a = adError;
        this.b = obj;
    }

    public AdError getError() {
        return this.a;
    }

    public Object getUserRequestContext() {
        return this.b;
    }
}
