package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.games.GameManagerClient.GameManagerResult;
import com.google.android.gms.common.api.Status;
import org.json.JSONObject;

final class zzby implements GameManagerResult {
    private final Status zzgt;
    private final String zzvd;
    private final long zzve;
    private final JSONObject zzvf;

    zzby(Status status, String str, long j, JSONObject jSONObject) {
        this.zzgt = status;
        this.zzvd = str;
        this.zzve = j;
        this.zzvf = jSONObject;
    }

    public final Status getStatus() {
        return this.zzgt;
    }

    public final String getPlayerId() {
        return this.zzvd;
    }

    public final long getRequestId() {
        return this.zzve;
    }

    public final JSONObject getExtraMessageData() {
        return this.zzvf;
    }
}
