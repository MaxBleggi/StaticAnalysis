package com.google.ads.interactivemedia.v3.internal;

import android.webkit.WebView;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: IMASDK */
public abstract class y {
    private as a = new as(null);
    private a b;
    private double c;

    /* compiled from: IMASDK */
    enum a {
        AD_STATE_IDLE,
        AD_STATE_VISIBLE,
        AD_STATE_HIDDEN
    }

    public y() {
        f();
    }

    public void a() {
    }

    public void b() {
        this.a.clear();
    }

    void a(WebView webView) {
        this.a = new as(webView);
    }

    public WebView c() {
        return (WebView) this.a.get();
    }

    public boolean d() {
        return this.a.get() != null;
    }

    public void a(boolean z) {
        if (d()) {
            s.a().d(c(), z ? "foregrounded" : "backgrounded");
        }
    }

    public void a(String str, double d) {
        if (d > this.c) {
            this.b = a.AD_STATE_VISIBLE;
            s.a().c(c(), str);
        }
    }

    public void b(String str, double d) {
        if (d > this.c && this.b != a.AD_STATE_HIDDEN) {
            this.b = a.AD_STATE_HIDDEN;
            s.a().c(c(), str);
        }
    }

    public void a(d dVar) {
        s.a().a(c(), dVar.a());
    }

    public void a(g gVar, e eVar) {
        gVar = gVar.f();
        JSONObject jSONObject = new JSONObject();
        ac.a(jSONObject, "environment", SettingsJsonConstants.APP_KEY);
        ac.a(jSONObject, "adSessionType", eVar.f());
        ac.a(jSONObject, "deviceInfo", ab.d());
        JSONArray jSONArray = new JSONArray();
        jSONArray.put("clid");
        jSONArray.put("vlid");
        ac.a(jSONObject, "supports", jSONArray);
        JSONObject jSONObject2 = new JSONObject();
        ac.a(jSONObject2, "partnerName", eVar.a().a());
        ac.a(jSONObject2, "partnerVersion", eVar.a().b());
        ac.a(jSONObject, "omidNativeInfo", jSONObject2);
        jSONObject2 = new JSONObject();
        ac.a(jSONObject2, "libraryVersion", "1.2.0-google_20180706");
        ac.a(jSONObject2, "appId", r.a().b().getApplicationContext().getPackageName());
        ac.a(jSONObject, SettingsJsonConstants.APP_KEY, jSONObject2);
        if (eVar.d() != null) {
            ac.a(jSONObject, "customReferenceData", eVar.d());
        }
        jSONObject2 = new JSONObject();
        for (j jVar : eVar.b()) {
            ac.a(jSONObject2, jVar.a(), jVar.c());
        }
        s.a().a(c(), gVar, jSONObject, jSONObject2);
    }

    public void e() {
        s.a().a(c());
    }

    public void a(float f) {
        s.a().a(c(), f);
    }

    public void f() {
        this.c = ae.a();
        this.b = a.AD_STATE_IDLE;
    }
}
