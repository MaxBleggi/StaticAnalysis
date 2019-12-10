package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdsRequest;
import com.google.ads.interactivemedia.v3.api.player.ContentProgressProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: IMASDK */
public class in implements AdsRequest {
    private String a;
    private AdDisplayContainer b;
    private Map<String, String> c;
    private String d;
    private ContentProgressProvider e;
    private a f = a.UNKNOWN;
    private b g = b.UNKNOWN;
    private Float h;
    private List<String> i;
    private String j;
    private Float k;
    private Float l;
    private transient Object m;

    /* compiled from: IMASDK */
    public enum a {
        AUTO,
        CLICK,
        UNKNOWN
    }

    /* compiled from: IMASDK */
    public enum b {
        MUTED,
        UNKNOWN,
        UNMUTED
    }

    public void setAdTagUrl(String str) {
        this.a = str;
    }

    public String getAdTagUrl() {
        return this.a;
    }

    public void setExtraParameter(String str, String str2) {
        if (this.c == null) {
            this.c = new HashMap();
        }
        this.c.put(str, str2);
    }

    public String getExtraParameter(String str) {
        if (this.c == null) {
            return null;
        }
        return (String) this.c.get(str);
    }

    public Map<String, String> getExtraParameters() {
        return this.c;
    }

    public void setUserRequestContext(Object obj) {
        this.m = obj;
    }

    public Object getUserRequestContext() {
        return this.m;
    }

    public AdDisplayContainer getAdDisplayContainer() {
        return this.b;
    }

    public void setAdDisplayContainer(AdDisplayContainer adDisplayContainer) {
        this.b = adDisplayContainer;
    }

    public ContentProgressProvider getContentProgressProvider() {
        return this.e;
    }

    public void setContentProgressProvider(ContentProgressProvider contentProgressProvider) {
        this.e = contentProgressProvider;
    }

    public String getAdsResponse() {
        return this.d;
    }

    public void setAdsResponse(String str) {
        this.d = str;
    }

    public a a() {
        return this.f;
    }

    public b b() {
        return this.g;
    }

    public void setAdWillAutoPlay(boolean z) {
        if (z) {
            this.f = a.AUTO;
        } else {
            this.f = a.CLICK;
        }
    }

    public void setAdWillPlayMuted(boolean z) {
        if (z) {
            this.g = b.MUTED;
        } else {
            this.g = b.UNMUTED;
        }
    }

    public Float c() {
        return this.h;
    }

    public void setContentDuration(float f) {
        this.h = Float.valueOf(f);
    }

    public List<String> d() {
        return this.i;
    }

    public void setContentKeywords(List<String> list) {
        this.i = list;
    }

    public String e() {
        return this.j;
    }

    public void setContentTitle(String str) {
        this.j = str;
    }

    public void setVastLoadTimeout(float f) {
        this.k = Float.valueOf(f);
    }

    public Float f() {
        return this.k;
    }

    public void setLiveStreamPrefetchSeconds(float f) {
        this.l = Float.valueOf(f);
    }

    public Float g() {
        return this.l;
    }
}
