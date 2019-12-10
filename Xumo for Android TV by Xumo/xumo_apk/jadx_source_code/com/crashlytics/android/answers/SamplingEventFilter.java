package com.crashlytics.android.answers;

import java.util.HashSet;
import java.util.Set;

class SamplingEventFilter implements EventFilter {
    static final Set<Type> EVENTS_TYPE_TO_SAMPLE = new HashSet<Type>() {
        {
            add(Type.START);
            add(Type.RESUME);
            add(Type.PAUSE);
            add(Type.STOP);
        }
    };
    final int samplingRate;

    public SamplingEventFilter(int i) {
        this.samplingRate = i;
    }

    public boolean skipEvent(SessionEvent sessionEvent) {
        Object obj = (EVENTS_TYPE_TO_SAMPLE.contains(sessionEvent.type) && sessionEvent.sessionEventMetadata.betaDeviceToken == null) ? 1 : null;
        sessionEvent = Math.abs(sessionEvent.sessionEventMetadata.installationId.hashCode() % this.samplingRate) != null ? true : null;
        if (obj == null || sessionEvent == null) {
            return false;
        }
        return true;
    }
}
