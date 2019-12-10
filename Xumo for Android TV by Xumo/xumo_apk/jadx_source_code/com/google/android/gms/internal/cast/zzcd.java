package com.google.android.gms.internal.cast;

import com.google.android.gms.common.util.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzcd {
    private final int zzet;
    private final String zzvd;
    private final JSONObject zzwa;

    public zzcd(JSONObject jSONObject) throws JSONException {
        this(jSONObject.optString("playerId"), jSONObject.optInt("playerState"), jSONObject.optJSONObject("playerData"));
    }

    private zzcd(String str, int i, JSONObject jSONObject) {
        this.zzvd = str;
        this.zzet = i;
        this.zzwa = jSONObject;
    }

    public final int getPlayerState() {
        return this.zzet;
    }

    public final JSONObject getPlayerData() {
        return this.zzwa;
    }

    public final String getPlayerId() {
        return this.zzvd;
    }

    public final boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof zzcd) {
                zzcd com_google_android_gms_internal_cast_zzcd = (zzcd) obj;
                if (this.zzet == com_google_android_gms_internal_cast_zzcd.zzet && zzcv.zza(this.zzvd, com_google_android_gms_internal_cast_zzcd.zzvd) && JsonUtils.areJsonValuesEquivalent(this.zzwa, com_google_android_gms_internal_cast_zzcd.zzwa) != null) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
