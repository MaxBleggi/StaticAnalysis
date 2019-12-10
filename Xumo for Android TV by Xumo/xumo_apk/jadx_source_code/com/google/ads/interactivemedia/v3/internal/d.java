package com.google.ads.interactivemedia.v3.internal;

import org.json.JSONObject;

/* compiled from: IMASDK */
public class d {
    private final h a;
    private final h b;
    private final boolean c;

    private d(h hVar, h hVar2, boolean z) {
        this.a = hVar;
        if (hVar2 == null) {
            this.b = h.NONE;
        } else {
            this.b = hVar2;
        }
        this.c = z;
    }

    public static d a(h hVar, h hVar2, boolean z) {
        af.a((Object) hVar, "Impression owner is null");
        af.a(hVar);
        return new d(hVar, hVar2, z);
    }

    @Deprecated
    public static d a(h hVar, h hVar2) {
        return a(hVar, hVar2, true);
    }

    public JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        ac.a(jSONObject, "impressionOwner", this.a);
        ac.a(jSONObject, "videoEventsOwner", this.b);
        ac.a(jSONObject, "isolateVerificationScripts", Boolean.valueOf(this.c));
        return jSONObject;
    }
}
