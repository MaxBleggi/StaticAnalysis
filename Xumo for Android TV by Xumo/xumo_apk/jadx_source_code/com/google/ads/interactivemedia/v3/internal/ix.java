package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: IMASDK */
public class ix {
    private final List<AdErrorListener> a = new ArrayList(1);

    public void a(AdErrorListener adErrorListener) {
        this.a.add(adErrorListener);
    }

    public void b(AdErrorListener adErrorListener) {
        this.a.remove(adErrorListener);
    }

    public void a(AdErrorEvent adErrorEvent) {
        for (AdErrorListener onAdError : this.a) {
            onAdError.onAdError(adErrorEvent);
        }
    }

    public String toString() {
        String valueOf = String.valueOf(this.a);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 38);
        stringBuilder.append("ErrorListenerSupport [errorListeners=");
        stringBuilder.append(valueOf);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
