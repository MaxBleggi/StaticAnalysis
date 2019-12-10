package com.google.ads.interactivemedia.v3.impl.data;

import com.google.ads.interactivemedia.v3.impl.data.TestingConfiguration.Builder;
import java.util.List;
import java.util.Map;

/* compiled from: IMASDK */
final class i extends TestingConfiguration {
    private final boolean disableExperiments;
    private final boolean enableMonitorAppLifecycle;
    private final Map<String, Object> extraParams;
    private final List<Long> forceExperimentIds;
    private final boolean forceTvMode;
    private final boolean ignoreStrictModeFalsePositives;
    private final boolean useTestStreamManager;
    private final boolean useVideoElementMock;
    private final float videoElementMockDuration;

    /* compiled from: IMASDK */
    static final class a implements Builder {
        private Boolean disableExperiments;
        private Boolean enableMonitorAppLifecycle;
        private Map<String, Object> extraParams;
        private List<Long> forceExperimentIds;
        private Boolean forceTvMode;
        private Boolean ignoreStrictModeFalsePositives;
        private Boolean useTestStreamManager;
        private Boolean useVideoElementMock;
        private Float videoElementMockDuration;

        a() {
        }

        public Builder disableExperiments(boolean z) {
            this.disableExperiments = Boolean.valueOf(z);
            return this;
        }

        public Builder useVideoElementMock(boolean z) {
            this.useVideoElementMock = Boolean.valueOf(z);
            return this;
        }

        public Builder videoElementMockDuration(float f) {
            this.videoElementMockDuration = Float.valueOf(f);
            return this;
        }

        public Builder useTestStreamManager(boolean z) {
            this.useTestStreamManager = Boolean.valueOf(z);
            return this;
        }

        public Builder enableMonitorAppLifecycle(boolean z) {
            this.enableMonitorAppLifecycle = Boolean.valueOf(z);
            return this;
        }

        public Builder forceExperimentIds(List<Long> list) {
            this.forceExperimentIds = list;
            return this;
        }

        public Builder forceTvMode(boolean z) {
            this.forceTvMode = Boolean.valueOf(z);
            return this;
        }

        public Builder ignoreStrictModeFalsePositives(boolean z) {
            this.ignoreStrictModeFalsePositives = Boolean.valueOf(z);
            return this;
        }

        public Builder extraParams(Map<String, Object> map) {
            this.extraParams = map;
            return this;
        }

        public TestingConfiguration build() {
            String str = "";
            if (this.disableExperiments == null) {
                str = String.valueOf(str).concat(" disableExperiments");
            }
            if (this.useVideoElementMock == null) {
                str = String.valueOf(str).concat(" useVideoElementMock");
            }
            if (this.videoElementMockDuration == null) {
                str = String.valueOf(str).concat(" videoElementMockDuration");
            }
            if (this.useTestStreamManager == null) {
                str = String.valueOf(str).concat(" useTestStreamManager");
            }
            if (this.enableMonitorAppLifecycle == null) {
                str = String.valueOf(str).concat(" enableMonitorAppLifecycle");
            }
            if (this.forceTvMode == null) {
                str = String.valueOf(str).concat(" forceTvMode");
            }
            if (this.ignoreStrictModeFalsePositives == null) {
                str = String.valueOf(str).concat(" ignoreStrictModeFalsePositives");
            }
            if (str.isEmpty()) {
                return new i(this.disableExperiments.booleanValue(), this.useVideoElementMock.booleanValue(), this.videoElementMockDuration.floatValue(), this.useTestStreamManager.booleanValue(), this.enableMonitorAppLifecycle.booleanValue(), this.forceExperimentIds, this.forceTvMode.booleanValue(), this.ignoreStrictModeFalsePositives.booleanValue(), this.extraParams);
            }
            String str2 = "Missing required properties:";
            str = String.valueOf(str);
            throw new IllegalStateException(str.length() != 0 ? str2.concat(str) : new String(str2));
        }
    }

    private i(boolean z, boolean z2, float f, boolean z3, boolean z4, List<Long> list, boolean z5, boolean z6, Map<String, Object> map) {
        this.disableExperiments = z;
        this.useVideoElementMock = z2;
        this.videoElementMockDuration = f;
        this.useTestStreamManager = z3;
        this.enableMonitorAppLifecycle = z4;
        this.forceExperimentIds = list;
        this.forceTvMode = z5;
        this.ignoreStrictModeFalsePositives = z6;
        this.extraParams = map;
    }

    public boolean disableExperiments() {
        return this.disableExperiments;
    }

    public boolean useVideoElementMock() {
        return this.useVideoElementMock;
    }

    public float videoElementMockDuration() {
        return this.videoElementMockDuration;
    }

    public boolean useTestStreamManager() {
        return this.useTestStreamManager;
    }

    public boolean enableMonitorAppLifecycle() {
        return this.enableMonitorAppLifecycle;
    }

    public List<Long> forceExperimentIds() {
        return this.forceExperimentIds;
    }

    public boolean forceTvMode() {
        return this.forceTvMode;
    }

    public boolean ignoreStrictModeFalsePositives() {
        return this.ignoreStrictModeFalsePositives;
    }

    public Map<String, Object> extraParams() {
        return this.extraParams;
    }

    public String toString() {
        boolean z = this.disableExperiments;
        boolean z2 = this.useVideoElementMock;
        float f = this.videoElementMockDuration;
        boolean z3 = this.useTestStreamManager;
        boolean z4 = this.enableMonitorAppLifecycle;
        String valueOf = String.valueOf(this.forceExperimentIds);
        boolean z5 = this.forceTvMode;
        boolean z6 = this.ignoreStrictModeFalsePositives;
        String valueOf2 = String.valueOf(this.extraParams);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 268) + String.valueOf(valueOf2).length());
        stringBuilder.append("TestingConfiguration{disableExperiments=");
        stringBuilder.append(z);
        stringBuilder.append(", useVideoElementMock=");
        stringBuilder.append(z2);
        stringBuilder.append(", videoElementMockDuration=");
        stringBuilder.append(f);
        stringBuilder.append(", useTestStreamManager=");
        stringBuilder.append(z3);
        stringBuilder.append(", enableMonitorAppLifecycle=");
        stringBuilder.append(z4);
        stringBuilder.append(", forceExperimentIds=");
        stringBuilder.append(valueOf);
        stringBuilder.append(", forceTvMode=");
        stringBuilder.append(z5);
        stringBuilder.append(", ignoreStrictModeFalsePositives=");
        stringBuilder.append(z6);
        stringBuilder.append(", extraParams=");
        stringBuilder.append(valueOf2);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
        r4 = this;
        r0 = 1;
        if (r5 != r4) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = r5 instanceof com.google.ads.interactivemedia.v3.impl.data.TestingConfiguration;
        r2 = 0;
        if (r1 == 0) goto L_0x007c;
    L_0x0009:
        r5 = (com.google.ads.interactivemedia.v3.impl.data.TestingConfiguration) r5;
        r1 = r4.disableExperiments;
        r3 = r5.disableExperiments();
        if (r1 != r3) goto L_0x007a;
    L_0x0013:
        r1 = r4.useVideoElementMock;
        r3 = r5.useVideoElementMock();
        if (r1 != r3) goto L_0x007a;
    L_0x001b:
        r1 = r4.videoElementMockDuration;
        r1 = java.lang.Float.floatToIntBits(r1);
        r3 = r5.videoElementMockDuration();
        r3 = java.lang.Float.floatToIntBits(r3);
        if (r1 != r3) goto L_0x007a;
    L_0x002b:
        r1 = r4.useTestStreamManager;
        r3 = r5.useTestStreamManager();
        if (r1 != r3) goto L_0x007a;
    L_0x0033:
        r1 = r4.enableMonitorAppLifecycle;
        r3 = r5.enableMonitorAppLifecycle();
        if (r1 != r3) goto L_0x007a;
    L_0x003b:
        r1 = r4.forceExperimentIds;
        if (r1 != 0) goto L_0x0046;
    L_0x003f:
        r1 = r5.forceExperimentIds();
        if (r1 != 0) goto L_0x007a;
    L_0x0045:
        goto L_0x0052;
    L_0x0046:
        r1 = r4.forceExperimentIds;
        r3 = r5.forceExperimentIds();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x007a;
    L_0x0052:
        r1 = r4.forceTvMode;
        r3 = r5.forceTvMode();
        if (r1 != r3) goto L_0x007a;
    L_0x005a:
        r1 = r4.ignoreStrictModeFalsePositives;
        r3 = r5.ignoreStrictModeFalsePositives();
        if (r1 != r3) goto L_0x007a;
    L_0x0062:
        r1 = r4.extraParams;
        if (r1 != 0) goto L_0x006d;
    L_0x0066:
        r5 = r5.extraParams();
        if (r5 != 0) goto L_0x007a;
    L_0x006c:
        goto L_0x007b;
    L_0x006d:
        r1 = r4.extraParams;
        r5 = r5.extraParams();
        r5 = r1.equals(r5);
        if (r5 == 0) goto L_0x007a;
    L_0x0079:
        goto L_0x007b;
    L_0x007a:
        r0 = 0;
    L_0x007b:
        return r0;
    L_0x007c:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.impl.data.i.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int i = 1237;
        int i2 = 0;
        int floatToIntBits = ((((((((((((((this.disableExperiments ? 1231 : 1237) ^ 1000003) * 1000003) ^ (this.useVideoElementMock ? 1231 : 1237)) * 1000003) ^ Float.floatToIntBits(this.videoElementMockDuration)) * 1000003) ^ (this.useTestStreamManager ? 1231 : 1237)) * 1000003) ^ (this.enableMonitorAppLifecycle ? 1231 : 1237)) * 1000003) ^ (this.forceExperimentIds == null ? 0 : this.forceExperimentIds.hashCode())) * 1000003) ^ (this.forceTvMode ? 1231 : 1237)) * 1000003;
        if (this.ignoreStrictModeFalsePositives) {
            i = 1231;
        }
        floatToIntBits = (floatToIntBits ^ i) * 1000003;
        if (this.extraParams != null) {
            i2 = this.extraParams.hashCode();
        }
        return floatToIntBits ^ i2;
    }
}
