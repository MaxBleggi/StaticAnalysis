package com.google.android.gms.internal.cast;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast.CastApi;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerClient.GameManagerInstanceResult;
import com.google.android.gms.cast.games.GameManagerClient.GameManagerResult;
import com.google.android.gms.cast.games.GameManagerClient.Listener;
import com.google.android.gms.cast.games.GameManagerState;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzbm extends zzch {
    private static final String NAMESPACE = zzcv.zzp("com.google.cast.games");
    private static final zzdh zzbe = new zzdh("GameManagerChannel");
    private final CastApi zzic;
    private final GoogleApiClient zzoy;
    private final Map<String, String> zzug = new ConcurrentHashMap();
    private final SharedPreferences zzuh;
    private final String zzui;
    private zzbz zzuj;
    private boolean zzuk = false;
    private GameManagerState zzul;
    private GameManagerState zzum;
    private String zzun;
    private JSONObject zzuo;
    private long zzup = 0;
    private Listener zzuq;
    private final Clock zzur;
    private String zzus;

    public zzbm(GoogleApiClient googleApiClient, String str, CastApi castApi) throws IllegalArgumentException, IllegalStateException {
        super(NAMESPACE, "CastGameManagerChannel", null);
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("castSessionId cannot be null.");
        } else if (googleApiClient != null && googleApiClient.isConnected() && googleApiClient.hasConnectedApi(Cast.API)) {
            this.zzur = DefaultClock.getInstance();
            this.zzui = str;
            this.zzic = castApi;
            this.zzoy = googleApiClient;
            this.zzuh = googleApiClient.getApplicationContext().getSharedPreferences(String.format(Locale.ROOT, "%s.%s", new Object[]{googleApiClient.getContext().getApplicationContext().getPackageName(), "game_manager_channel_data"}), 0);
            this.zzum = null;
            this.zzul = new zzcb(0, 0, "", null, new ArrayList(), "", -1);
        } else {
            throw new IllegalArgumentException("googleApiClient needs to be connected and contain the Cast.API API.");
        }
    }

    public final synchronized PendingResult<GameManagerInstanceResult> zza(GameManagerClient gameManagerClient) throws IllegalArgumentException {
        return this.zzoy.execute(new zzbn(this, gameManagerClient));
    }

    public final synchronized void dispose() throws IllegalStateException {
        if (!this.zzuk) {
            this.zzul = null;
            this.zzum = null;
            this.zzun = null;
            this.zzuo = null;
            this.zzuk = true;
            try {
                this.zzic.removeMessageReceivedCallbacks(this.zzoy, getNamespace());
            } catch (IOException e) {
                zzbe.w("Exception while detaching game manager channel.", e);
            }
        }
    }

    public final synchronized PendingResult<GameManagerResult> zza(String str, int i, JSONObject jSONObject) throws IllegalStateException {
        zzcx();
        return this.zzoy.execute(new zzbp(this, i, str, jSONObject));
    }

    public final synchronized void sendGameMessage(String str, JSONObject jSONObject) throws IllegalStateException {
        zzcx();
        long j = this.zzup + 1;
        this.zzup = j;
        str = zza(j, str, 7, jSONObject);
        if (str != null) {
            this.zzic.sendMessage(this.zzoy, getNamespace(), str.toString());
        }
    }

    public final synchronized PendingResult<GameManagerResult> sendGameRequest(String str, JSONObject jSONObject) throws IllegalStateException {
        zzcx();
        return this.zzoy.execute(new zzbq(this, str, jSONObject));
    }

    public final synchronized GameManagerState getCurrentState() throws IllegalStateException {
        zzcx();
        return this.zzul;
    }

    public final synchronized String getLastUsedPlayerId() throws IllegalStateException {
        zzcx();
        return this.zzus;
    }

    private final synchronized String zzm(String str) throws IllegalStateException {
        if (str == null) {
            return null;
        }
        return (String) this.zzug.get(str);
    }

    public final synchronized void setListener(Listener listener) {
        this.zzuq = listener;
    }

    public final void zzn(String str) {
        Object[] objArr = new Object[1];
        int i = 0;
        objArr[0] = str;
        zzbe.d("message received: %s", objArr);
        try {
            zzca zzi = zzca.zzi(new JSONObject(str));
            if (zzi == null) {
                zzbe.w("Could not parse game manager message from string: %s", str);
            } else if (!(isInitialized() == null && zzi.zzvu == null) && isDisposed() == null) {
                str = zzi.zzvi == 1 ? true : null;
                if (!(str == null || TextUtils.isEmpty(zzi.zzvt))) {
                    this.zzug.put(zzi.zzvr, zzi.zzvt);
                    zzcy();
                }
                if (zzi.zzvj == 0) {
                    zza(zzi);
                } else {
                    zzbe.w("Not updating from game message because the message contains error code: %d", Integer.valueOf(zzi.zzvj));
                }
                int i2 = zzi.zzvj;
                switch (i2) {
                    case 0:
                        break;
                    case 1:
                        i = CastStatusCodes.INVALID_REQUEST;
                        break;
                    case 2:
                        i = CastStatusCodes.NOT_ALLOWED;
                        break;
                    case 3:
                        i = GameManagerClient.STATUS_INCORRECT_VERSION;
                        break;
                    case 4:
                        i = GameManagerClient.STATUS_TOO_MANY_PLAYERS;
                        break;
                    default:
                        zzdh com_google_android_gms_internal_cast_zzdh = zzbe;
                        StringBuilder stringBuilder = new StringBuilder(53);
                        stringBuilder.append("Unknown GameManager protocol status code: ");
                        stringBuilder.append(i2);
                        com_google_android_gms_internal_cast_zzdh.w(stringBuilder.toString(), new Object[0]);
                        i = 13;
                        break;
                }
                if (str != null) {
                    zzb(zzi.zzvs, i, zzi);
                }
                if (isInitialized() != null && i == 0) {
                    if (this.zzuq != null) {
                        if (this.zzum != null && this.zzul.equals(this.zzum) == null) {
                            this.zzuq.onStateChanged(this.zzul, this.zzum);
                        }
                        if (!(this.zzuo == null || this.zzun == null)) {
                            this.zzuq.onGameMessageReceived(this.zzun, this.zzuo);
                        }
                    }
                    this.zzum = null;
                    this.zzun = null;
                    this.zzuo = null;
                }
            }
        } catch (JSONException e) {
            zzbe.w("Message is malformed (%s); ignoring: %s", e.getMessage(), str);
        }
    }

    public final synchronized boolean isDisposed() {
        return this.zzuk;
    }

    private final synchronized boolean isInitialized() {
        return this.zzuj != null;
    }

    public final void zza(long j, int i) {
        zzb(j, i, null);
    }

    private final synchronized void zzcx() throws IllegalStateException {
        if (!isInitialized()) {
            throw new IllegalStateException("Attempted to perform an operation on the GameManagerChannel before it is initialized.");
        } else if (isDisposed()) {
            throw new IllegalStateException("Attempted to perform an operation on the GameManagerChannel after it has been disposed.");
        }
    }

    private final void zza(String str, int i, JSONObject jSONObject, zzdn com_google_android_gms_internal_cast_zzdn) {
        long j = this.zzup + 1;
        this.zzup = j;
        str = zza(j, str, i, jSONObject);
        if (str == null) {
            com_google_android_gms_internal_cast_zzdn.zza(-1, CastStatusCodes.INVALID_REQUEST, null);
            zzbe.w("Not sending request because it was invalid.", new Object[null]);
            return;
        }
        i = new zzdo(30000);
        i.zza(j, com_google_android_gms_internal_cast_zzdn);
        zza(i);
        this.zzic.sendMessage(this.zzoy, getNamespace(), str.toString()).setResultCallback(new zzbr(this, j));
    }

    private final JSONObject zza(long j, String str, int i, JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("requestId", j);
            jSONObject2.put("type", i);
            jSONObject2.put("extraMessageData", jSONObject);
            jSONObject2.put("playerId", str);
            jSONObject2.put("playerToken", zzm(str));
            return jSONObject2;
        } catch (long j2) {
            zzbe.w("JSONException when trying to create a message: %s", new Object[]{j2.getMessage()});
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final synchronized void zza(com.google.android.gms.internal.cast.zzca r10) {
        /*
        r9 = this;
        monitor-enter(r9);
        r0 = r10.zzvi;	 Catch:{ all -> 0x008b }
        r1 = 1;
        if (r0 != r1) goto L_0x0007;
    L_0x0006:
        goto L_0x0008;
    L_0x0007:
        r1 = 0;
    L_0x0008:
        r0 = r9.zzul;	 Catch:{ all -> 0x008b }
        r9.zzum = r0;	 Catch:{ all -> 0x008b }
        if (r1 == 0) goto L_0x0016;
    L_0x000e:
        r0 = r10.zzvu;	 Catch:{ all -> 0x008b }
        if (r0 == 0) goto L_0x0016;
    L_0x0012:
        r0 = r10.zzvu;	 Catch:{ all -> 0x008b }
        r9.zzuj = r0;	 Catch:{ all -> 0x008b }
    L_0x0016:
        r0 = r9.isInitialized();	 Catch:{ all -> 0x008b }
        if (r0 != 0) goto L_0x001e;
    L_0x001c:
        monitor-exit(r9);
        return;
    L_0x001e:
        r6 = new java.util.ArrayList;	 Catch:{ all -> 0x008b }
        r6.<init>();	 Catch:{ all -> 0x008b }
        r0 = r10.zzvo;	 Catch:{ all -> 0x008b }
        r0 = r0.iterator();	 Catch:{ all -> 0x008b }
    L_0x0029:
        r1 = r0.hasNext();	 Catch:{ all -> 0x008b }
        if (r1 == 0) goto L_0x0050;
    L_0x002f:
        r1 = r0.next();	 Catch:{ all -> 0x008b }
        r1 = (com.google.android.gms.internal.cast.zzcd) r1;	 Catch:{ all -> 0x008b }
        r2 = r1.getPlayerId();	 Catch:{ all -> 0x008b }
        r3 = new com.google.android.gms.internal.cast.zzcc;	 Catch:{ all -> 0x008b }
        r4 = r1.getPlayerState();	 Catch:{ all -> 0x008b }
        r1 = r1.getPlayerData();	 Catch:{ all -> 0x008b }
        r5 = r9.zzug;	 Catch:{ all -> 0x008b }
        r5 = r5.containsKey(r2);	 Catch:{ all -> 0x008b }
        r3.<init>(r2, r4, r1, r5);	 Catch:{ all -> 0x008b }
        r6.add(r3);	 Catch:{ all -> 0x008b }
        goto L_0x0029;
    L_0x0050:
        r0 = new com.google.android.gms.internal.cast.zzcb;	 Catch:{ all -> 0x008b }
        r2 = r10.zzvn;	 Catch:{ all -> 0x008b }
        r3 = r10.zzvm;	 Catch:{ all -> 0x008b }
        r4 = r10.zzvq;	 Catch:{ all -> 0x008b }
        r5 = r10.zzvp;	 Catch:{ all -> 0x008b }
        r1 = r9.zzuj;	 Catch:{ all -> 0x008b }
        r7 = r1.zzdb();	 Catch:{ all -> 0x008b }
        r1 = r9.zzuj;	 Catch:{ all -> 0x008b }
        r8 = r1.getMaxPlayers();	 Catch:{ all -> 0x008b }
        r1 = r0;
        r1.<init>(r2, r3, r4, r5, r6, r7, r8);	 Catch:{ all -> 0x008b }
        r9.zzul = r0;	 Catch:{ all -> 0x008b }
        r0 = r9.zzul;	 Catch:{ all -> 0x008b }
        r1 = r10.zzvr;	 Catch:{ all -> 0x008b }
        r0 = r0.getPlayer(r1);	 Catch:{ all -> 0x008b }
        if (r0 == 0) goto L_0x0089;
    L_0x0076:
        r0 = r0.isControllable();	 Catch:{ all -> 0x008b }
        if (r0 == 0) goto L_0x0089;
    L_0x007c:
        r0 = r10.zzvi;	 Catch:{ all -> 0x008b }
        r1 = 2;
        if (r0 != r1) goto L_0x0089;
    L_0x0081:
        r0 = r10.zzvr;	 Catch:{ all -> 0x008b }
        r9.zzun = r0;	 Catch:{ all -> 0x008b }
        r10 = r10.zzvl;	 Catch:{ all -> 0x008b }
        r9.zzuo = r10;	 Catch:{ all -> 0x008b }
    L_0x0089:
        monitor-exit(r9);
        return;
    L_0x008b:
        r10 = move-exception;
        monitor-exit(r9);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzbm.zza(com.google.android.gms.internal.cast.zzca):void");
    }

    private final void zzb(long j, int i, Object obj) {
        List zzde = zzde();
        synchronized (zzde) {
            Iterator it = zzde.iterator();
            while (it.hasNext()) {
                if (((zzdo) it.next()).zzc(j, i, obj)) {
                    it.remove();
                }
            }
        }
    }

    private final synchronized void zzcy() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("castSessionId", this.zzui);
            jSONObject.put("playerTokenMap", new JSONObject(this.zzug));
            this.zzuh.edit().putString("save_data", jSONObject.toString()).commit();
        } catch (JSONException e) {
            zzbe.w("Error while saving data: %s", e.getMessage());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final synchronized void zzcz() {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = r5.zzuh;	 Catch:{ all -> 0x005b }
        r1 = "save_data";
        r2 = 0;
        r0 = r0.getString(r1, r2);	 Catch:{ all -> 0x005b }
        if (r0 != 0) goto L_0x000e;
    L_0x000c:
        monitor-exit(r5);
        return;
    L_0x000e:
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0047 }
        r1.<init>(r0);	 Catch:{ JSONException -> 0x0047 }
        r0 = "castSessionId";
        r0 = r1.getString(r0);	 Catch:{ JSONException -> 0x0047 }
        r2 = r5.zzui;	 Catch:{ JSONException -> 0x0047 }
        r0 = r2.equals(r0);	 Catch:{ JSONException -> 0x0047 }
        if (r0 == 0) goto L_0x0045;
    L_0x0021:
        r0 = "playerTokenMap";
        r0 = r1.getJSONObject(r0);	 Catch:{ JSONException -> 0x0047 }
        r1 = r0.keys();	 Catch:{ JSONException -> 0x0047 }
    L_0x002b:
        r2 = r1.hasNext();	 Catch:{ JSONException -> 0x0047 }
        if (r2 == 0) goto L_0x0041;
    L_0x0031:
        r2 = r1.next();	 Catch:{ JSONException -> 0x0047 }
        r2 = (java.lang.String) r2;	 Catch:{ JSONException -> 0x0047 }
        r3 = r5.zzug;	 Catch:{ JSONException -> 0x0047 }
        r4 = r0.getString(r2);	 Catch:{ JSONException -> 0x0047 }
        r3.put(r2, r4);	 Catch:{ JSONException -> 0x0047 }
        goto L_0x002b;
    L_0x0041:
        r0 = 0;
        r5.zzup = r0;	 Catch:{ JSONException -> 0x0047 }
    L_0x0045:
        monitor-exit(r5);
        return;
    L_0x0047:
        r0 = move-exception;
        r1 = zzbe;	 Catch:{ all -> 0x005b }
        r2 = "Error while loading data: %s";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x005b }
        r4 = 0;
        r0 = r0.getMessage();	 Catch:{ all -> 0x005b }
        r3[r4] = r0;	 Catch:{ all -> 0x005b }
        r1.w(r2, r3);	 Catch:{ all -> 0x005b }
        monitor-exit(r5);
        return;
    L_0x005b:
        r0 = move-exception;
        monitor-exit(r5);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzbm.zzcz():void");
    }
}
