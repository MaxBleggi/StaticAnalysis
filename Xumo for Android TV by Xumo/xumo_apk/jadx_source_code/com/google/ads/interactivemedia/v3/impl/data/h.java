package com.google.ads.interactivemedia.v3.impl.data;

import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;
import com.google.ads.interactivemedia.v3.internal.ii.b;
import com.google.ads.interactivemedia.v3.internal.in;
import java.util.List;
import java.util.Map;

/* compiled from: IMASDK */
final class h extends l {
    private final com.google.ads.interactivemedia.v3.impl.data.a.a adContainerBounds;
    private final Map<String, String> adTagParameters;
    private final String adTagUrl;
    private final String adsResponse;
    private final String apiKey;
    private final String assetKey;
    private final String authToken;
    private final Map<String, String> companionSlots;
    private final Float contentDuration;
    private final List<String> contentKeywords;
    private final String contentSourceId;
    private final String contentTitle;
    private final String env;
    private final Map<String, String> extraParameters;
    private final String format;
    private final String idType;
    private final Boolean isAdContainerAttachedToWindow;
    private final String isLat;
    private final Boolean isTv;
    private final Float liveStreamPrefetchSeconds;
    private final b marketAppInfo;
    private final String msParameter;
    private final String network;
    private final String rdid;
    private final ImaSdkSettings settings;
    private final String streamActivityMonitorId;
    private final Boolean useQAStreamBaseUrl;
    private final Float vastLoadTimeout;
    private final String videoId;
    private final com.google.ads.interactivemedia.v3.internal.in.a videoPlayActivation;
    private final in.b videoPlayMuted;

    /* compiled from: IMASDK */
    static final class a implements com.google.ads.interactivemedia.v3.impl.data.l.a {
        private com.google.ads.interactivemedia.v3.impl.data.a.a adContainerBounds;
        private Map<String, String> adTagParameters;
        private String adTagUrl;
        private String adsResponse;
        private String apiKey;
        private String assetKey;
        private String authToken;
        private Map<String, String> companionSlots;
        private Float contentDuration;
        private List<String> contentKeywords;
        private String contentSourceId;
        private String contentTitle;
        private String env;
        private Map<String, String> extraParameters;
        private String format;
        private String idType;
        private Boolean isAdContainerAttachedToWindow;
        private String isLat;
        private Boolean isTv;
        private Float liveStreamPrefetchSeconds;
        private b marketAppInfo;
        private String msParameter;
        private String network;
        private String rdid;
        private ImaSdkSettings settings;
        private String streamActivityMonitorId;
        private Boolean useQAStreamBaseUrl;
        private Float vastLoadTimeout;
        private String videoId;
        private com.google.ads.interactivemedia.v3.internal.in.a videoPlayActivation;
        private in.b videoPlayMuted;

        a() {
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a adsResponse(String str) {
            this.adsResponse = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a adTagUrl(String str) {
            this.adTagUrl = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a assetKey(String str) {
            this.assetKey = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a authToken(String str) {
            this.authToken = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a contentSourceId(String str) {
            this.contentSourceId = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a videoId(String str) {
            this.videoId = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a apiKey(String str) {
            this.apiKey = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a format(String str) {
            this.format = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a adTagParameters(Map<String, String> map) {
            this.adTagParameters = map;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a env(String str) {
            this.env = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a network(String str) {
            this.network = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a contentDuration(Float f) {
            this.contentDuration = f;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a contentKeywords(List<String> list) {
            this.contentKeywords = list;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a contentTitle(String str) {
            this.contentTitle = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a vastLoadTimeout(Float f) {
            this.vastLoadTimeout = f;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a liveStreamPrefetchSeconds(Float f) {
            this.liveStreamPrefetchSeconds = f;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a companionSlots(Map<String, String> map) {
            this.companionSlots = map;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a extraParameters(Map<String, String> map) {
            this.extraParameters = map;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a isTv(Boolean bool) {
            this.isTv = bool;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a msParameter(String str) {
            this.msParameter = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a isAdContainerAttachedToWindow(Boolean bool) {
            this.isAdContainerAttachedToWindow = bool;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a streamActivityMonitorId(String str) {
            this.streamActivityMonitorId = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a rdid(String str) {
            this.rdid = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a idType(String str) {
            this.idType = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a isLat(String str) {
            this.isLat = str;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a useQAStreamBaseUrl(Boolean bool) {
            this.useQAStreamBaseUrl = bool;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a videoPlayActivation(com.google.ads.interactivemedia.v3.internal.in.a aVar) {
            this.videoPlayActivation = aVar;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a videoPlayMuted(in.b bVar) {
            this.videoPlayMuted = bVar;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a adContainerBounds(com.google.ads.interactivemedia.v3.impl.data.a.a aVar) {
            this.adContainerBounds = aVar;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a settings(ImaSdkSettings imaSdkSettings) {
            this.settings = imaSdkSettings;
            return this;
        }

        public com.google.ads.interactivemedia.v3.impl.data.l.a marketAppInfo(b bVar) {
            this.marketAppInfo = bVar;
            return this;
        }

        public l build() {
            return new h(this.adsResponse, this.adTagUrl, this.assetKey, this.authToken, this.contentSourceId, this.videoId, this.apiKey, this.format, this.adTagParameters, this.env, this.network, this.contentDuration, this.contentKeywords, this.contentTitle, this.vastLoadTimeout, this.liveStreamPrefetchSeconds, this.companionSlots, this.extraParameters, this.isTv, this.msParameter, this.isAdContainerAttachedToWindow, this.streamActivityMonitorId, this.rdid, this.idType, this.isLat, this.useQAStreamBaseUrl, this.videoPlayActivation, this.videoPlayMuted, this.adContainerBounds, this.settings, this.marketAppInfo);
        }
    }

    private h(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, Map<String, String> map, String str9, String str10, Float f, List<String> list, String str11, Float f2, Float f3, Map<String, String> map2, Map<String, String> map3, Boolean bool, String str12, Boolean bool2, String str13, String str14, String str15, String str16, Boolean bool3, com.google.ads.interactivemedia.v3.internal.in.a aVar, in.b bVar, com.google.ads.interactivemedia.v3.impl.data.a.a aVar2, ImaSdkSettings imaSdkSettings, b bVar2) {
        this.adsResponse = str;
        this.adTagUrl = str2;
        this.assetKey = str3;
        this.authToken = str4;
        this.contentSourceId = str5;
        this.videoId = str6;
        this.apiKey = str7;
        this.format = str8;
        this.adTagParameters = map;
        this.env = str9;
        this.network = str10;
        this.contentDuration = f;
        this.contentKeywords = list;
        this.contentTitle = str11;
        this.vastLoadTimeout = f2;
        this.liveStreamPrefetchSeconds = f3;
        this.companionSlots = map2;
        this.extraParameters = map3;
        this.isTv = bool;
        this.msParameter = str12;
        this.isAdContainerAttachedToWindow = bool2;
        this.streamActivityMonitorId = str13;
        this.rdid = str14;
        this.idType = str15;
        this.isLat = str16;
        this.useQAStreamBaseUrl = bool3;
        this.videoPlayActivation = aVar;
        this.videoPlayMuted = bVar;
        this.adContainerBounds = aVar2;
        this.settings = imaSdkSettings;
        this.marketAppInfo = bVar2;
    }

    public String adsResponse() {
        return this.adsResponse;
    }

    public String adTagUrl() {
        return this.adTagUrl;
    }

    public String assetKey() {
        return this.assetKey;
    }

    public String authToken() {
        return this.authToken;
    }

    public String contentSourceId() {
        return this.contentSourceId;
    }

    public String videoId() {
        return this.videoId;
    }

    public String apiKey() {
        return this.apiKey;
    }

    public String format() {
        return this.format;
    }

    public Map<String, String> adTagParameters() {
        return this.adTagParameters;
    }

    public String env() {
        return this.env;
    }

    public String network() {
        return this.network;
    }

    public Float contentDuration() {
        return this.contentDuration;
    }

    public List<String> contentKeywords() {
        return this.contentKeywords;
    }

    public String contentTitle() {
        return this.contentTitle;
    }

    public Float vastLoadTimeout() {
        return this.vastLoadTimeout;
    }

    public Float liveStreamPrefetchSeconds() {
        return this.liveStreamPrefetchSeconds;
    }

    public Map<String, String> companionSlots() {
        return this.companionSlots;
    }

    public Map<String, String> extraParameters() {
        return this.extraParameters;
    }

    public Boolean isTv() {
        return this.isTv;
    }

    public String msParameter() {
        return this.msParameter;
    }

    public Boolean isAdContainerAttachedToWindow() {
        return this.isAdContainerAttachedToWindow;
    }

    public String streamActivityMonitorId() {
        return this.streamActivityMonitorId;
    }

    public String rdid() {
        return this.rdid;
    }

    public String idType() {
        return this.idType;
    }

    public String isLat() {
        return this.isLat;
    }

    public Boolean useQAStreamBaseUrl() {
        return this.useQAStreamBaseUrl;
    }

    public com.google.ads.interactivemedia.v3.internal.in.a videoPlayActivation() {
        return this.videoPlayActivation;
    }

    public in.b videoPlayMuted() {
        return this.videoPlayMuted;
    }

    public com.google.ads.interactivemedia.v3.impl.data.a.a adContainerBounds() {
        return this.adContainerBounds;
    }

    public ImaSdkSettings settings() {
        return this.settings;
    }

    public b marketAppInfo() {
        return this.marketAppInfo;
    }

    public String toString() {
        String str = this.adsResponse;
        String str2 = this.adTagUrl;
        String str3 = this.assetKey;
        String str4 = this.authToken;
        String str5 = this.contentSourceId;
        String str6 = this.videoId;
        String str7 = this.apiKey;
        String str8 = this.format;
        String valueOf = String.valueOf(this.adTagParameters);
        String str9 = this.env;
        String str10 = this.network;
        String valueOf2 = String.valueOf(this.contentDuration);
        String valueOf3 = String.valueOf(this.contentKeywords);
        String str11 = this.contentTitle;
        String valueOf4 = String.valueOf(this.vastLoadTimeout);
        String valueOf5 = String.valueOf(this.liveStreamPrefetchSeconds);
        String valueOf6 = String.valueOf(this.companionSlots);
        String valueOf7 = String.valueOf(this.extraParameters);
        String valueOf8 = String.valueOf(this.isTv);
        String str12 = this.msParameter;
        String valueOf9 = String.valueOf(this.isAdContainerAttachedToWindow);
        String str13 = this.streamActivityMonitorId;
        String str14 = this.rdid;
        String str15 = this.idType;
        String str16 = this.isLat;
        String valueOf10 = String.valueOf(this.useQAStreamBaseUrl);
        String valueOf11 = String.valueOf(this.videoPlayActivation);
        String valueOf12 = String.valueOf(this.videoPlayMuted);
        String valueOf13 = String.valueOf(this.adContainerBounds);
        String valueOf14 = String.valueOf(this.settings);
        String valueOf15 = String.valueOf(this.marketAppInfo);
        String str17 = valueOf15;
        StringBuilder stringBuilder = new StringBuilder(((((((((((((((((((((((((((((((String.valueOf(str).length() + 484) + String.valueOf(str2).length()) + String.valueOf(str3).length()) + String.valueOf(str4).length()) + String.valueOf(str5).length()) + String.valueOf(str6).length()) + String.valueOf(str7).length()) + String.valueOf(str8).length()) + String.valueOf(valueOf).length()) + String.valueOf(str9).length()) + String.valueOf(str10).length()) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()) + String.valueOf(str11).length()) + String.valueOf(valueOf4).length()) + String.valueOf(valueOf5).length()) + String.valueOf(valueOf6).length()) + String.valueOf(valueOf7).length()) + String.valueOf(valueOf8).length()) + String.valueOf(str12).length()) + String.valueOf(valueOf9).length()) + String.valueOf(str13).length()) + String.valueOf(str14).length()) + String.valueOf(str15).length()) + String.valueOf(str16).length()) + String.valueOf(valueOf10).length()) + String.valueOf(valueOf11).length()) + String.valueOf(valueOf12).length()) + String.valueOf(valueOf13).length()) + String.valueOf(valueOf14).length()) + String.valueOf(valueOf15).length());
        stringBuilder.append("GsonAdsRequest{adsResponse=");
        stringBuilder.append(str);
        stringBuilder.append(", adTagUrl=");
        stringBuilder.append(str2);
        stringBuilder.append(", assetKey=");
        stringBuilder.append(str3);
        stringBuilder.append(", authToken=");
        stringBuilder.append(str4);
        stringBuilder.append(", contentSourceId=");
        stringBuilder.append(str5);
        stringBuilder.append(", videoId=");
        stringBuilder.append(str6);
        stringBuilder.append(", apiKey=");
        stringBuilder.append(str7);
        stringBuilder.append(", format=");
        stringBuilder.append(str8);
        stringBuilder.append(", adTagParameters=");
        stringBuilder.append(valueOf);
        stringBuilder.append(", env=");
        stringBuilder.append(str9);
        stringBuilder.append(", network=");
        stringBuilder.append(str10);
        stringBuilder.append(", contentDuration=");
        stringBuilder.append(valueOf2);
        stringBuilder.append(", contentKeywords=");
        stringBuilder.append(valueOf3);
        stringBuilder.append(", contentTitle=");
        stringBuilder.append(str11);
        stringBuilder.append(", vastLoadTimeout=");
        stringBuilder.append(valueOf4);
        stringBuilder.append(", liveStreamPrefetchSeconds=");
        stringBuilder.append(valueOf5);
        stringBuilder.append(", companionSlots=");
        stringBuilder.append(valueOf6);
        stringBuilder.append(", extraParameters=");
        stringBuilder.append(valueOf7);
        stringBuilder.append(", isTv=");
        stringBuilder.append(valueOf8);
        stringBuilder.append(", msParameter=");
        stringBuilder.append(str12);
        stringBuilder.append(", isAdContainerAttachedToWindow=");
        stringBuilder.append(valueOf9);
        stringBuilder.append(", streamActivityMonitorId=");
        stringBuilder.append(str13);
        stringBuilder.append(", rdid=");
        stringBuilder.append(str14);
        stringBuilder.append(", idType=");
        stringBuilder.append(str15);
        stringBuilder.append(", isLat=");
        stringBuilder.append(str16);
        stringBuilder.append(", useQAStreamBaseUrl=");
        stringBuilder.append(valueOf10);
        stringBuilder.append(", videoPlayActivation=");
        stringBuilder.append(valueOf11);
        stringBuilder.append(", videoPlayMuted=");
        stringBuilder.append(valueOf12);
        stringBuilder.append(", adContainerBounds=");
        stringBuilder.append(valueOf13);
        stringBuilder.append(", settings=");
        stringBuilder.append(valueOf14);
        stringBuilder.append(", marketAppInfo=");
        stringBuilder.append(str17);
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
        r1 = r5 instanceof com.google.ads.interactivemedia.v3.impl.data.l;
        r2 = 0;
        if (r1 == 0) goto L_0x02d7;
    L_0x0009:
        r5 = (com.google.ads.interactivemedia.v3.impl.data.l) r5;
        r1 = r4.adsResponse;
        if (r1 != 0) goto L_0x0016;
    L_0x000f:
        r1 = r5.adsResponse();
        if (r1 != 0) goto L_0x02d5;
    L_0x0015:
        goto L_0x0022;
    L_0x0016:
        r1 = r4.adsResponse;
        r3 = r5.adsResponse();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0022:
        r1 = r4.adTagUrl;
        if (r1 != 0) goto L_0x002d;
    L_0x0026:
        r1 = r5.adTagUrl();
        if (r1 != 0) goto L_0x02d5;
    L_0x002c:
        goto L_0x0039;
    L_0x002d:
        r1 = r4.adTagUrl;
        r3 = r5.adTagUrl();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0039:
        r1 = r4.assetKey;
        if (r1 != 0) goto L_0x0044;
    L_0x003d:
        r1 = r5.assetKey();
        if (r1 != 0) goto L_0x02d5;
    L_0x0043:
        goto L_0x0050;
    L_0x0044:
        r1 = r4.assetKey;
        r3 = r5.assetKey();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0050:
        r1 = r4.authToken;
        if (r1 != 0) goto L_0x005b;
    L_0x0054:
        r1 = r5.authToken();
        if (r1 != 0) goto L_0x02d5;
    L_0x005a:
        goto L_0x0067;
    L_0x005b:
        r1 = r4.authToken;
        r3 = r5.authToken();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0067:
        r1 = r4.contentSourceId;
        if (r1 != 0) goto L_0x0072;
    L_0x006b:
        r1 = r5.contentSourceId();
        if (r1 != 0) goto L_0x02d5;
    L_0x0071:
        goto L_0x007e;
    L_0x0072:
        r1 = r4.contentSourceId;
        r3 = r5.contentSourceId();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x007e:
        r1 = r4.videoId;
        if (r1 != 0) goto L_0x0089;
    L_0x0082:
        r1 = r5.videoId();
        if (r1 != 0) goto L_0x02d5;
    L_0x0088:
        goto L_0x0095;
    L_0x0089:
        r1 = r4.videoId;
        r3 = r5.videoId();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0095:
        r1 = r4.apiKey;
        if (r1 != 0) goto L_0x00a0;
    L_0x0099:
        r1 = r5.apiKey();
        if (r1 != 0) goto L_0x02d5;
    L_0x009f:
        goto L_0x00ac;
    L_0x00a0:
        r1 = r4.apiKey;
        r3 = r5.apiKey();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x00ac:
        r1 = r4.format;
        if (r1 != 0) goto L_0x00b7;
    L_0x00b0:
        r1 = r5.format();
        if (r1 != 0) goto L_0x02d5;
    L_0x00b6:
        goto L_0x00c3;
    L_0x00b7:
        r1 = r4.format;
        r3 = r5.format();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x00c3:
        r1 = r4.adTagParameters;
        if (r1 != 0) goto L_0x00ce;
    L_0x00c7:
        r1 = r5.adTagParameters();
        if (r1 != 0) goto L_0x02d5;
    L_0x00cd:
        goto L_0x00da;
    L_0x00ce:
        r1 = r4.adTagParameters;
        r3 = r5.adTagParameters();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x00da:
        r1 = r4.env;
        if (r1 != 0) goto L_0x00e5;
    L_0x00de:
        r1 = r5.env();
        if (r1 != 0) goto L_0x02d5;
    L_0x00e4:
        goto L_0x00f1;
    L_0x00e5:
        r1 = r4.env;
        r3 = r5.env();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x00f1:
        r1 = r4.network;
        if (r1 != 0) goto L_0x00fc;
    L_0x00f5:
        r1 = r5.network();
        if (r1 != 0) goto L_0x02d5;
    L_0x00fb:
        goto L_0x0108;
    L_0x00fc:
        r1 = r4.network;
        r3 = r5.network();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0108:
        r1 = r4.contentDuration;
        if (r1 != 0) goto L_0x0113;
    L_0x010c:
        r1 = r5.contentDuration();
        if (r1 != 0) goto L_0x02d5;
    L_0x0112:
        goto L_0x011f;
    L_0x0113:
        r1 = r4.contentDuration;
        r3 = r5.contentDuration();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x011f:
        r1 = r4.contentKeywords;
        if (r1 != 0) goto L_0x012a;
    L_0x0123:
        r1 = r5.contentKeywords();
        if (r1 != 0) goto L_0x02d5;
    L_0x0129:
        goto L_0x0136;
    L_0x012a:
        r1 = r4.contentKeywords;
        r3 = r5.contentKeywords();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0136:
        r1 = r4.contentTitle;
        if (r1 != 0) goto L_0x0141;
    L_0x013a:
        r1 = r5.contentTitle();
        if (r1 != 0) goto L_0x02d5;
    L_0x0140:
        goto L_0x014d;
    L_0x0141:
        r1 = r4.contentTitle;
        r3 = r5.contentTitle();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x014d:
        r1 = r4.vastLoadTimeout;
        if (r1 != 0) goto L_0x0158;
    L_0x0151:
        r1 = r5.vastLoadTimeout();
        if (r1 != 0) goto L_0x02d5;
    L_0x0157:
        goto L_0x0164;
    L_0x0158:
        r1 = r4.vastLoadTimeout;
        r3 = r5.vastLoadTimeout();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0164:
        r1 = r4.liveStreamPrefetchSeconds;
        if (r1 != 0) goto L_0x016f;
    L_0x0168:
        r1 = r5.liveStreamPrefetchSeconds();
        if (r1 != 0) goto L_0x02d5;
    L_0x016e:
        goto L_0x017b;
    L_0x016f:
        r1 = r4.liveStreamPrefetchSeconds;
        r3 = r5.liveStreamPrefetchSeconds();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x017b:
        r1 = r4.companionSlots;
        if (r1 != 0) goto L_0x0186;
    L_0x017f:
        r1 = r5.companionSlots();
        if (r1 != 0) goto L_0x02d5;
    L_0x0185:
        goto L_0x0192;
    L_0x0186:
        r1 = r4.companionSlots;
        r3 = r5.companionSlots();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0192:
        r1 = r4.extraParameters;
        if (r1 != 0) goto L_0x019d;
    L_0x0196:
        r1 = r5.extraParameters();
        if (r1 != 0) goto L_0x02d5;
    L_0x019c:
        goto L_0x01a9;
    L_0x019d:
        r1 = r4.extraParameters;
        r3 = r5.extraParameters();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x01a9:
        r1 = r4.isTv;
        if (r1 != 0) goto L_0x01b4;
    L_0x01ad:
        r1 = r5.isTv();
        if (r1 != 0) goto L_0x02d5;
    L_0x01b3:
        goto L_0x01c0;
    L_0x01b4:
        r1 = r4.isTv;
        r3 = r5.isTv();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x01c0:
        r1 = r4.msParameter;
        if (r1 != 0) goto L_0x01cb;
    L_0x01c4:
        r1 = r5.msParameter();
        if (r1 != 0) goto L_0x02d5;
    L_0x01ca:
        goto L_0x01d7;
    L_0x01cb:
        r1 = r4.msParameter;
        r3 = r5.msParameter();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x01d7:
        r1 = r4.isAdContainerAttachedToWindow;
        if (r1 != 0) goto L_0x01e2;
    L_0x01db:
        r1 = r5.isAdContainerAttachedToWindow();
        if (r1 != 0) goto L_0x02d5;
    L_0x01e1:
        goto L_0x01ee;
    L_0x01e2:
        r1 = r4.isAdContainerAttachedToWindow;
        r3 = r5.isAdContainerAttachedToWindow();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x01ee:
        r1 = r4.streamActivityMonitorId;
        if (r1 != 0) goto L_0x01f9;
    L_0x01f2:
        r1 = r5.streamActivityMonitorId();
        if (r1 != 0) goto L_0x02d5;
    L_0x01f8:
        goto L_0x0205;
    L_0x01f9:
        r1 = r4.streamActivityMonitorId;
        r3 = r5.streamActivityMonitorId();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0205:
        r1 = r4.rdid;
        if (r1 != 0) goto L_0x0210;
    L_0x0209:
        r1 = r5.rdid();
        if (r1 != 0) goto L_0x02d5;
    L_0x020f:
        goto L_0x021c;
    L_0x0210:
        r1 = r4.rdid;
        r3 = r5.rdid();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x021c:
        r1 = r4.idType;
        if (r1 != 0) goto L_0x0227;
    L_0x0220:
        r1 = r5.idType();
        if (r1 != 0) goto L_0x02d5;
    L_0x0226:
        goto L_0x0233;
    L_0x0227:
        r1 = r4.idType;
        r3 = r5.idType();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0233:
        r1 = r4.isLat;
        if (r1 != 0) goto L_0x023e;
    L_0x0237:
        r1 = r5.isLat();
        if (r1 != 0) goto L_0x02d5;
    L_0x023d:
        goto L_0x024a;
    L_0x023e:
        r1 = r4.isLat;
        r3 = r5.isLat();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x024a:
        r1 = r4.useQAStreamBaseUrl;
        if (r1 != 0) goto L_0x0255;
    L_0x024e:
        r1 = r5.useQAStreamBaseUrl();
        if (r1 != 0) goto L_0x02d5;
    L_0x0254:
        goto L_0x0261;
    L_0x0255:
        r1 = r4.useQAStreamBaseUrl;
        r3 = r5.useQAStreamBaseUrl();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0261:
        r1 = r4.videoPlayActivation;
        if (r1 != 0) goto L_0x026c;
    L_0x0265:
        r1 = r5.videoPlayActivation();
        if (r1 != 0) goto L_0x02d5;
    L_0x026b:
        goto L_0x0278;
    L_0x026c:
        r1 = r4.videoPlayActivation;
        r3 = r5.videoPlayActivation();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x0278:
        r1 = r4.videoPlayMuted;
        if (r1 != 0) goto L_0x0283;
    L_0x027c:
        r1 = r5.videoPlayMuted();
        if (r1 != 0) goto L_0x02d5;
    L_0x0282:
        goto L_0x028f;
    L_0x0283:
        r1 = r4.videoPlayMuted;
        r3 = r5.videoPlayMuted();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x028f:
        r1 = r4.adContainerBounds;
        if (r1 != 0) goto L_0x029a;
    L_0x0293:
        r1 = r5.adContainerBounds();
        if (r1 != 0) goto L_0x02d5;
    L_0x0299:
        goto L_0x02a6;
    L_0x029a:
        r1 = r4.adContainerBounds;
        r3 = r5.adContainerBounds();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x02a6:
        r1 = r4.settings;
        if (r1 != 0) goto L_0x02b1;
    L_0x02aa:
        r1 = r5.settings();
        if (r1 != 0) goto L_0x02d5;
    L_0x02b0:
        goto L_0x02bd;
    L_0x02b1:
        r1 = r4.settings;
        r3 = r5.settings();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x02d5;
    L_0x02bd:
        r1 = r4.marketAppInfo;
        if (r1 != 0) goto L_0x02c8;
    L_0x02c1:
        r5 = r5.marketAppInfo();
        if (r5 != 0) goto L_0x02d5;
    L_0x02c7:
        goto L_0x02d6;
    L_0x02c8:
        r1 = r4.marketAppInfo;
        r5 = r5.marketAppInfo();
        r5 = r1.equals(r5);
        if (r5 == 0) goto L_0x02d5;
    L_0x02d4:
        goto L_0x02d6;
    L_0x02d5:
        r0 = 0;
    L_0x02d6:
        return r0;
    L_0x02d7:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.impl.data.h.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((this.adsResponse == null ? 0 : this.adsResponse.hashCode()) ^ 1000003) * 1000003) ^ (this.adTagUrl == null ? 0 : this.adTagUrl.hashCode())) * 1000003) ^ (this.assetKey == null ? 0 : this.assetKey.hashCode())) * 1000003) ^ (this.authToken == null ? 0 : this.authToken.hashCode())) * 1000003) ^ (this.contentSourceId == null ? 0 : this.contentSourceId.hashCode())) * 1000003) ^ (this.videoId == null ? 0 : this.videoId.hashCode())) * 1000003) ^ (this.apiKey == null ? 0 : this.apiKey.hashCode())) * 1000003) ^ (this.format == null ? 0 : this.format.hashCode())) * 1000003) ^ (this.adTagParameters == null ? 0 : this.adTagParameters.hashCode())) * 1000003) ^ (this.env == null ? 0 : this.env.hashCode())) * 1000003) ^ (this.network == null ? 0 : this.network.hashCode())) * 1000003) ^ (this.contentDuration == null ? 0 : this.contentDuration.hashCode())) * 1000003) ^ (this.contentKeywords == null ? 0 : this.contentKeywords.hashCode())) * 1000003) ^ (this.contentTitle == null ? 0 : this.contentTitle.hashCode())) * 1000003) ^ (this.vastLoadTimeout == null ? 0 : this.vastLoadTimeout.hashCode())) * 1000003) ^ (this.liveStreamPrefetchSeconds == null ? 0 : this.liveStreamPrefetchSeconds.hashCode())) * 1000003) ^ (this.companionSlots == null ? 0 : this.companionSlots.hashCode())) * 1000003) ^ (this.extraParameters == null ? 0 : this.extraParameters.hashCode())) * 1000003) ^ (this.isTv == null ? 0 : this.isTv.hashCode())) * 1000003) ^ (this.msParameter == null ? 0 : this.msParameter.hashCode())) * 1000003) ^ (this.isAdContainerAttachedToWindow == null ? 0 : this.isAdContainerAttachedToWindow.hashCode())) * 1000003) ^ (this.streamActivityMonitorId == null ? 0 : this.streamActivityMonitorId.hashCode())) * 1000003) ^ (this.rdid == null ? 0 : this.rdid.hashCode())) * 1000003) ^ (this.idType == null ? 0 : this.idType.hashCode())) * 1000003) ^ (this.isLat == null ? 0 : this.isLat.hashCode())) * 1000003) ^ (this.useQAStreamBaseUrl == null ? 0 : this.useQAStreamBaseUrl.hashCode())) * 1000003) ^ (this.videoPlayActivation == null ? 0 : this.videoPlayActivation.hashCode())) * 1000003) ^ (this.videoPlayMuted == null ? 0 : this.videoPlayMuted.hashCode())) * 1000003) ^ (this.adContainerBounds == null ? 0 : this.adContainerBounds.hashCode())) * 1000003) ^ (this.settings == null ? 0 : this.settings.hashCode())) * 1000003;
        if (this.marketAppInfo != null) {
            i = this.marketAppInfo.hashCode();
        }
        return hashCode ^ i;
    }
}
