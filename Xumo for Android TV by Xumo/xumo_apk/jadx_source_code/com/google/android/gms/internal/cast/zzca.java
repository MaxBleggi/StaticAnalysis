package com.google.android.gms.internal.cast;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public final class zzca {
    private static final zzdh zzbe = new zzdh("GameManagerMessage");
    protected final int zzvi;
    protected final int zzvj;
    protected final String zzvk;
    protected final JSONObject zzvl;
    protected final int zzvm;
    protected final int zzvn;
    protected final List<zzcd> zzvo;
    protected final JSONObject zzvp;
    protected final String zzvq;
    protected final String zzvr;
    protected final long zzvs;
    protected final String zzvt;
    protected final zzbz zzvu;

    private zzca(int i, int i2, String str, JSONObject jSONObject, int i3, int i4, List<zzcd> list, JSONObject jSONObject2, String str2, String str3, long j, String str4, zzbz com_google_android_gms_internal_cast_zzbz) {
        this.zzvi = i;
        this.zzvj = i2;
        this.zzvk = str;
        this.zzvl = jSONObject;
        this.zzvm = i3;
        this.zzvn = i4;
        this.zzvo = list;
        this.zzvp = jSONObject2;
        this.zzvq = str2;
        this.zzvr = str3;
        this.zzvs = j;
        this.zzvt = str4;
        this.zzvu = com_google_android_gms_internal_cast_zzbz;
    }

    protected static zzca zzi(JSONObject jSONObject) {
        JSONObject jSONObject2 = jSONObject;
        int optInt = jSONObject2.optInt("type", -1);
        switch (optInt) {
            case 1:
                JSONObject optJSONObject = jSONObject2.optJSONObject("gameManagerConfig");
                return new zzca(optInt, jSONObject2.optInt("statusCode"), jSONObject2.optString("errorDescription"), jSONObject2.optJSONObject("extraMessageData"), jSONObject2.optInt("gameplayState"), jSONObject2.optInt("lobbyState"), zza(jSONObject2.optJSONArray("players")), jSONObject2.optJSONObject("gameData"), jSONObject2.optString("gameStatusText"), jSONObject2.optString("playerId"), jSONObject2.optLong("requestId"), jSONObject2.optString("playerToken"), optJSONObject != null ? new zzbz(optJSONObject) : null);
            case 2:
                return new zzca(optInt, jSONObject2.optInt("statusCode"), jSONObject2.optString("errorDescription"), jSONObject2.optJSONObject("extraMessageData"), jSONObject2.optInt("gameplayState"), jSONObject2.optInt("lobbyState"), zza(jSONObject2.optJSONArray("players")), jSONObject2.optJSONObject("gameData"), jSONObject2.optString("gameStatusText"), jSONObject2.optString("playerId"), -1, null, null);
            default:
                try {
                    zzbe.w("Unrecognized Game Message type %d", Integer.valueOf(optInt));
                } catch (Throwable e) {
                    zzbe.zzb(e, "Exception while parsing GameManagerMessage from json", new Object[0]);
                }
                return null;
        }
    }

    private static List<zzcd> zza(JSONArray jSONArray) {
        List<zzcd> arrayList = new ArrayList();
        if (jSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                Object obj = null;
                try {
                    obj = new zzcd(optJSONObject);
                } catch (Throwable e) {
                    zzbe.zzb(e, "Exception when attempting to parse PlayerInfoMessageComponent at index %d", Integer.valueOf(i));
                }
                if (obj != null) {
                    arrayList.add(obj);
                }
            }
        }
        return arrayList;
    }
}
