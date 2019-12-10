package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerClient.GameManagerInstanceResult;
import com.google.android.gms.common.api.Status;

final class zzbx implements GameManagerInstanceResult {
    private final Status zzgt;
    private final GameManagerClient zzvb;

    zzbx(Status status, GameManagerClient gameManagerClient) {
        this.zzgt = status;
        this.zzvb = gameManagerClient;
    }

    public final Status getStatus() {
        return this.zzgt;
    }

    public final GameManagerClient getGameManagerClient() {
        return this.zzvb;
    }
}
