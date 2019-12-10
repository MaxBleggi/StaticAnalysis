package com.google.android.gms.cast.games;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.cast.zzbm;
import org.json.JSONObject;

@Deprecated
public final class GameManagerClient {
    public static final int GAMEPLAY_STATE_LOADING = 1;
    public static final int GAMEPLAY_STATE_PAUSED = 3;
    public static final int GAMEPLAY_STATE_RUNNING = 2;
    public static final int GAMEPLAY_STATE_SHOWING_INFO_SCREEN = 4;
    public static final int GAMEPLAY_STATE_UNKNOWN = 0;
    public static final int LOBBY_STATE_CLOSED = 2;
    public static final int LOBBY_STATE_OPEN = 1;
    public static final int LOBBY_STATE_UNKNOWN = 0;
    public static final int PLAYER_STATE_AVAILABLE = 3;
    public static final int PLAYER_STATE_DROPPED = 1;
    public static final int PLAYER_STATE_IDLE = 5;
    public static final int PLAYER_STATE_PLAYING = 6;
    public static final int PLAYER_STATE_QUIT = 2;
    public static final int PLAYER_STATE_READY = 4;
    public static final int PLAYER_STATE_UNKNOWN = 0;
    public static final int STATUS_INCORRECT_VERSION = 2150;
    public static final int STATUS_TOO_MANY_PLAYERS = 2151;
    private final zzbm zzuf;

    @Deprecated
    public interface Listener {
        void onGameMessageReceived(String str, JSONObject jSONObject);

        void onStateChanged(GameManagerState gameManagerState, GameManagerState gameManagerState2);
    }

    @Deprecated
    public interface GameManagerInstanceResult extends Result {
        GameManagerClient getGameManagerClient();
    }

    @Deprecated
    public interface GameManagerResult extends Result {
        JSONObject getExtraMessageData();

        String getPlayerId();

        long getRequestId();
    }

    @VisibleForTesting
    private GameManagerClient(zzbm com_google_android_gms_internal_cast_zzbm) {
        this.zzuf = com_google_android_gms_internal_cast_zzbm;
    }

    public static PendingResult<GameManagerInstanceResult> getInstanceFor(GoogleApiClient googleApiClient, String str) throws IllegalArgumentException {
        zzbm com_google_android_gms_internal_cast_zzbm = new zzbm(googleApiClient, str, Cast.CastApi);
        return com_google_android_gms_internal_cast_zzbm.zza(new GameManagerClient(com_google_android_gms_internal_cast_zzbm));
    }

    public final PendingResult<GameManagerResult> sendPlayerAvailableRequest(JSONObject jSONObject) throws IllegalStateException {
        return zza(getLastUsedPlayerId(), 3, jSONObject);
    }

    public final PendingResult<GameManagerResult> sendPlayerAvailableRequest(String str, JSONObject jSONObject) throws IllegalStateException {
        return zza(str, 3, jSONObject);
    }

    public final PendingResult<GameManagerResult> sendPlayerReadyRequest(JSONObject jSONObject) throws IllegalStateException {
        return zza(getLastUsedPlayerId(), 4, jSONObject);
    }

    public final PendingResult<GameManagerResult> sendPlayerReadyRequest(String str, JSONObject jSONObject) throws IllegalStateException {
        return zza(str, 4, jSONObject);
    }

    public final PendingResult<GameManagerResult> sendPlayerPlayingRequest(JSONObject jSONObject) throws IllegalStateException {
        return zza(getLastUsedPlayerId(), 6, jSONObject);
    }

    public final PendingResult<GameManagerResult> sendPlayerPlayingRequest(String str, JSONObject jSONObject) throws IllegalStateException {
        return zza(str, 6, jSONObject);
    }

    public final PendingResult<GameManagerResult> sendPlayerIdleRequest(JSONObject jSONObject) throws IllegalStateException {
        return zza(getLastUsedPlayerId(), 5, jSONObject);
    }

    public final PendingResult<GameManagerResult> sendPlayerIdleRequest(String str, JSONObject jSONObject) throws IllegalStateException {
        return zza(str, 5, jSONObject);
    }

    public final PendingResult<GameManagerResult> sendPlayerQuitRequest(JSONObject jSONObject) throws IllegalStateException {
        return zza(getLastUsedPlayerId(), 2, jSONObject);
    }

    public final PendingResult<GameManagerResult> sendPlayerQuitRequest(String str, JSONObject jSONObject) throws IllegalStateException {
        return zza(str, 2, jSONObject);
    }

    private final PendingResult<GameManagerResult> zza(String str, int i, JSONObject jSONObject) throws IllegalStateException {
        return this.zzuf.zza(str, i, jSONObject);
    }

    public final PendingResult<GameManagerResult> sendGameRequest(JSONObject jSONObject) throws IllegalStateException {
        return sendGameRequest(getLastUsedPlayerId(), jSONObject);
    }

    public final PendingResult<GameManagerResult> sendGameRequest(String str, JSONObject jSONObject) throws IllegalStateException {
        return this.zzuf.sendGameRequest(str, jSONObject);
    }

    public final void sendGameMessage(JSONObject jSONObject) throws IllegalStateException {
        sendGameMessage(getLastUsedPlayerId(), jSONObject);
    }

    public final void sendGameMessage(String str, JSONObject jSONObject) throws IllegalStateException {
        this.zzuf.sendGameMessage(str, jSONObject);
    }

    public final synchronized GameManagerState getCurrentState() throws IllegalStateException {
        return this.zzuf.getCurrentState();
    }

    public final String getLastUsedPlayerId() throws IllegalStateException {
        return this.zzuf.getLastUsedPlayerId();
    }

    public final void setListener(Listener listener) {
        this.zzuf.setListener(listener);
    }

    public final void dispose() {
        this.zzuf.dispose();
    }

    public final boolean isDisposed() {
        return this.zzuf.isDisposed();
    }

    public final void setSessionLabel(String str) {
        this.zzuf.setSessionLabel(str);
    }
}
