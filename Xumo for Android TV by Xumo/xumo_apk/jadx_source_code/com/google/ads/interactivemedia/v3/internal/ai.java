package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.am.b;
import java.util.HashSet;
import org.json.JSONObject;

/* compiled from: IMASDK */
public class ai implements b {
    private JSONObject a;
    private final an b;

    public ai(an anVar) {
        this.b = anVar;
    }

    public void a(JSONObject jSONObject, HashSet<String> hashSet, double d) {
        this.b.b(new aq(this, hashSet, jSONObject, d));
    }

    public void b(JSONObject jSONObject, HashSet<String> hashSet, double d) {
        this.b.b(new ap(this, hashSet, jSONObject, d));
    }

    public void a() {
        this.b.b(new ao(this));
    }

    public JSONObject b() {
        return this.a;
    }

    public void a(JSONObject jSONObject) {
        this.a = jSONObject;
    }
}
