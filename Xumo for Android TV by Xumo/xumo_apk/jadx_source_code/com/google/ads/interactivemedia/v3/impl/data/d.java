package com.google.ads.interactivemedia.v3.impl.data;

import com.google.ads.interactivemedia.v3.impl.data.a.b;

/* compiled from: IMASDK */
final class d extends a {
    private final String appState;
    private final String eventId;
    private final long nativeTime;
    private final boolean nativeViewAttached;
    private final com.google.ads.interactivemedia.v3.impl.data.a.a nativeViewBounds;
    private final boolean nativeViewHidden;
    private final com.google.ads.interactivemedia.v3.impl.data.a.a nativeViewVisibleBounds;
    private final double nativeVolume;
    private final String queryId;
    private final String vastEvent;

    /* compiled from: IMASDK */
    static final class a implements b {
        private String appState;
        private String eventId;
        private Long nativeTime;
        private Boolean nativeViewAttached;
        private com.google.ads.interactivemedia.v3.impl.data.a.a nativeViewBounds;
        private Boolean nativeViewHidden;
        private com.google.ads.interactivemedia.v3.impl.data.a.a nativeViewVisibleBounds;
        private Double nativeVolume;
        private String queryId;
        private String vastEvent;

        a() {
        }

        public b queryId(String str) {
            if (str != null) {
                this.queryId = str;
                return this;
            }
            throw new NullPointerException("Null queryId");
        }

        public b eventId(String str) {
            if (str != null) {
                this.eventId = str;
                return this;
            }
            throw new NullPointerException("Null eventId");
        }

        public b vastEvent(String str) {
            if (str != null) {
                this.vastEvent = str;
                return this;
            }
            throw new NullPointerException("Null vastEvent");
        }

        public b appState(String str) {
            if (str != null) {
                this.appState = str;
                return this;
            }
            throw new NullPointerException("Null appState");
        }

        public b nativeTime(long j) {
            this.nativeTime = Long.valueOf(j);
            return this;
        }

        public b nativeVolume(double d) {
            this.nativeVolume = Double.valueOf(d);
            return this;
        }

        public b nativeViewAttached(boolean z) {
            this.nativeViewAttached = Boolean.valueOf(z);
            return this;
        }

        public b nativeViewHidden(boolean z) {
            this.nativeViewHidden = Boolean.valueOf(z);
            return this;
        }

        public b nativeViewBounds(com.google.ads.interactivemedia.v3.impl.data.a.a aVar) {
            if (aVar != null) {
                this.nativeViewBounds = aVar;
                return this;
            }
            throw new NullPointerException("Null nativeViewBounds");
        }

        public b nativeViewVisibleBounds(com.google.ads.interactivemedia.v3.impl.data.a.a aVar) {
            if (aVar != null) {
                this.nativeViewVisibleBounds = aVar;
                return this;
            }
            throw new NullPointerException("Null nativeViewVisibleBounds");
        }

        public a build() {
            String str = "";
            if (this.queryId == null) {
                str = String.valueOf(str).concat(" queryId");
            }
            if (r0.eventId == null) {
                str = String.valueOf(str).concat(" eventId");
            }
            if (r0.vastEvent == null) {
                str = String.valueOf(str).concat(" vastEvent");
            }
            if (r0.appState == null) {
                str = String.valueOf(str).concat(" appState");
            }
            if (r0.nativeTime == null) {
                str = String.valueOf(str).concat(" nativeTime");
            }
            if (r0.nativeVolume == null) {
                str = String.valueOf(str).concat(" nativeVolume");
            }
            if (r0.nativeViewAttached == null) {
                str = String.valueOf(str).concat(" nativeViewAttached");
            }
            if (r0.nativeViewHidden == null) {
                str = String.valueOf(str).concat(" nativeViewHidden");
            }
            if (r0.nativeViewBounds == null) {
                str = String.valueOf(str).concat(" nativeViewBounds");
            }
            if (r0.nativeViewVisibleBounds == null) {
                str = String.valueOf(str).concat(" nativeViewVisibleBounds");
            }
            if (str.isEmpty()) {
                return new d(r0.queryId, r0.eventId, r0.vastEvent, r0.appState, r0.nativeTime.longValue(), r0.nativeVolume.doubleValue(), r0.nativeViewAttached.booleanValue(), r0.nativeViewHidden.booleanValue(), r0.nativeViewBounds, r0.nativeViewVisibleBounds);
            }
            String str2 = "Missing required properties:";
            str = String.valueOf(str);
            throw new IllegalStateException(str.length() != 0 ? str2.concat(str) : new String(str2));
        }
    }

    private d(String str, String str2, String str3, String str4, long j, double d, boolean z, boolean z2, com.google.ads.interactivemedia.v3.impl.data.a.a aVar, com.google.ads.interactivemedia.v3.impl.data.a.a aVar2) {
        this.queryId = str;
        this.eventId = str2;
        this.vastEvent = str3;
        this.appState = str4;
        this.nativeTime = j;
        this.nativeVolume = d;
        this.nativeViewAttached = z;
        this.nativeViewHidden = z2;
        this.nativeViewBounds = aVar;
        this.nativeViewVisibleBounds = aVar2;
    }

    public String queryId() {
        return this.queryId;
    }

    public String eventId() {
        return this.eventId;
    }

    public String vastEvent() {
        return this.vastEvent;
    }

    public String appState() {
        return this.appState;
    }

    public long nativeTime() {
        return this.nativeTime;
    }

    public double nativeVolume() {
        return this.nativeVolume;
    }

    public boolean nativeViewAttached() {
        return this.nativeViewAttached;
    }

    public boolean nativeViewHidden() {
        return this.nativeViewHidden;
    }

    public com.google.ads.interactivemedia.v3.impl.data.a.a nativeViewBounds() {
        return this.nativeViewBounds;
    }

    public com.google.ads.interactivemedia.v3.impl.data.a.a nativeViewVisibleBounds() {
        return this.nativeViewVisibleBounds;
    }

    public String toString() {
        String str = this.queryId;
        String str2 = this.eventId;
        String str3 = this.vastEvent;
        String str4 = this.appState;
        long j = this.nativeTime;
        double d = this.nativeVolume;
        boolean z = this.nativeViewAttached;
        boolean z2 = this.nativeViewHidden;
        String valueOf = String.valueOf(this.nativeViewBounds);
        String valueOf2 = String.valueOf(this.nativeViewVisibleBounds);
        StringBuilder stringBuilder = new StringBuilder((((((String.valueOf(str).length() + 229) + String.valueOf(str2).length()) + String.valueOf(str3).length()) + String.valueOf(str4).length()) + String.valueOf(valueOf).length()) + String.valueOf(valueOf2).length());
        stringBuilder.append("ActivityMonitorData{queryId=");
        stringBuilder.append(str);
        stringBuilder.append(", eventId=");
        stringBuilder.append(str2);
        stringBuilder.append(", vastEvent=");
        stringBuilder.append(str3);
        stringBuilder.append(", appState=");
        stringBuilder.append(str4);
        stringBuilder.append(", nativeTime=");
        stringBuilder.append(j);
        stringBuilder.append(", nativeVolume=");
        stringBuilder.append(d);
        stringBuilder.append(", nativeViewAttached=");
        stringBuilder.append(z);
        stringBuilder.append(", nativeViewHidden=");
        stringBuilder.append(z2);
        stringBuilder.append(", nativeViewBounds=");
        stringBuilder.append(valueOf);
        stringBuilder.append(", nativeViewVisibleBounds=");
        stringBuilder.append(valueOf2);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        if (!this.queryId.equals(aVar.queryId()) || !this.eventId.equals(aVar.eventId()) || !this.vastEvent.equals(aVar.vastEvent()) || !this.appState.equals(aVar.appState()) || this.nativeTime != aVar.nativeTime() || Double.doubleToLongBits(this.nativeVolume) != Double.doubleToLongBits(aVar.nativeVolume()) || this.nativeViewAttached != aVar.nativeViewAttached() || this.nativeViewHidden != aVar.nativeViewHidden() || !this.nativeViewBounds.equals(aVar.nativeViewBounds()) || this.nativeViewVisibleBounds.equals(aVar.nativeViewVisibleBounds()) == null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i = 1237;
        int hashCode = (((((((((((((this.queryId.hashCode() ^ 1000003) * 1000003) ^ this.eventId.hashCode()) * 1000003) ^ this.vastEvent.hashCode()) * 1000003) ^ this.appState.hashCode()) * 1000003) ^ ((int) ((this.nativeTime >>> 32) ^ this.nativeTime))) * 1000003) ^ ((int) ((Double.doubleToLongBits(this.nativeVolume) >>> 32) ^ Double.doubleToLongBits(this.nativeVolume)))) * 1000003) ^ (this.nativeViewAttached ? 1231 : 1237)) * 1000003;
        if (this.nativeViewHidden) {
            i = 1231;
        }
        return ((((hashCode ^ i) * 1000003) ^ this.nativeViewBounds.hashCode()) * 1000003) ^ this.nativeViewVisibleBounds.hashCode();
    }
}
