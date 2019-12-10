package com.google.android.gms.internal.cast;

import org.json.JSONException;
import org.json.JSONObject;

public final class zzbz {
    private final String version;
    private final String zzvg;
    private final int zzvh;

    public zzbz(JSONObject jSONObject) throws JSONException {
        this(jSONObject.optString("applicationName"), jSONObject.optInt("maxPlayers"), jSONObject.optString("version"));
    }

    private zzbz(String str, int i, String str2) {
        this.zzvg = str;
        this.zzvh = i;
        this.version = str2;
    }

    public final String zzdb() {
        return this.zzvg;
    }

    public final int getMaxPlayers() {
        return this.zzvh;
    }

    public final String getVersion() {
        return this.version;
    }
}
