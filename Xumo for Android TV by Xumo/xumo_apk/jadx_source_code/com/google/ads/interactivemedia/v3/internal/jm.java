package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.StreamDisplayContainer;
import com.google.ads.interactivemedia.v3.api.StreamRequest;
import com.google.ads.interactivemedia.v3.api.StreamRequest.StreamFormat;
import java.util.Map;

/* compiled from: IMASDK */
public class jm implements StreamRequest {
    private StreamDisplayContainer a;
    private String b;
    private String c;
    private String d;
    private String e;
    private Map<String, String> f;
    private String g;
    private String h;
    private String i;
    private StreamFormat j;
    private Boolean k;
    private transient Object l;

    public void a(String str) {
        this.b = str;
    }

    public String getAssetKey() {
        return this.b;
    }

    public void setAuthToken(String str) {
        this.h = str;
    }

    public String getAuthToken() {
        return this.h;
    }

    public void b(String str) {
        this.d = str;
    }

    public String getContentSourceId() {
        return this.d;
    }

    public void c(String str) {
        this.e = str;
    }

    public String getVideoId() {
        return this.e;
    }

    public void setFormat(StreamFormat streamFormat) {
        this.j = streamFormat;
    }

    public StreamFormat getFormat() {
        return this.j;
    }

    public void d(String str) {
        this.c = str;
    }

    public String getApiKey() {
        return this.c;
    }

    public void setStreamActivityMonitorId(String str) {
        this.i = str;
    }

    public String getStreamActivityMonitorId() {
        return this.i;
    }

    public void setUserRequestContext(Object obj) {
        this.l = obj;
    }

    public Object getUserRequestContext() {
        return this.l;
    }

    public StreamDisplayContainer getStreamDisplayContainer() {
        return this.a;
    }

    public void a(StreamDisplayContainer streamDisplayContainer) {
        this.a = streamDisplayContainer;
    }

    public void setAdTagParameters(Map<String, String> map) {
        this.f = map;
    }

    public Map<String, String> getAdTagParameters() {
        return this.f;
    }

    public void setManifestSuffix(String str) {
        this.g = str;
    }

    public String getManifestSuffix() {
        return this.g;
    }

    public Boolean getUseQAStreamBaseUrl() {
        return this.k;
    }

    public void setUseQAStreamBaseUrl(Boolean bool) {
        this.k = bool;
    }
}
