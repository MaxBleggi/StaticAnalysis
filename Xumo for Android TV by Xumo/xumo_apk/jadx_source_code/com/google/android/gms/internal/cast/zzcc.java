package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.games.PlayerInfo;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.util.JsonUtils;
import org.json.JSONObject;

public final class zzcc implements PlayerInfo {
    private final int zzet;
    private final String zzvd;
    private final JSONObject zzwa;
    private final boolean zzwb;

    public zzcc(String str, int i, JSONObject jSONObject, boolean z) {
        this.zzvd = str;
        this.zzet = i;
        this.zzwa = jSONObject;
        this.zzwb = z;
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

    public final boolean isConnected() {
        switch (this.zzet) {
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    public final boolean isControllable() {
        return this.zzwb;
    }

    public final boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof PlayerInfo) {
                PlayerInfo playerInfo = (PlayerInfo) obj;
                if (this.zzwb == playerInfo.isControllable() && this.zzet == playerInfo.getPlayerState() && zzcv.zza(this.zzvd, playerInfo.getPlayerId()) && JsonUtils.areJsonValuesEquivalent(this.zzwa, playerInfo.getPlayerData()) != null) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzvd, Integer.valueOf(this.zzet), this.zzwa, Boolean.valueOf(this.zzwb));
    }
}
