package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.CastStatusCodes;
import org.json.JSONObject;

final class zzbp extends zzbs {
    private final /* synthetic */ int val$playerState;
    private final /* synthetic */ zzbm zzut;
    private final /* synthetic */ String zzuv;
    private final /* synthetic */ JSONObject zzuw;

    zzbp(zzbm com_google_android_gms_internal_cast_zzbm, int i, String str, JSONObject jSONObject) {
        this.zzut = com_google_android_gms_internal_cast_zzbm;
        this.val$playerState = i;
        this.zzuv = str;
        this.zzuw = jSONObject;
        super(com_google_android_gms_internal_cast_zzbm);
    }

    public final void execute() {
        int i;
        switch (this.val$playerState) {
            case 2:
                i = 5;
                break;
            case 3:
                i = 1;
                break;
            case 4:
                i = 2;
                break;
            case 5:
                i = 3;
                break;
            case 6:
                i = 4;
                break;
            default:
                i = 0;
                break;
        }
        if (i == 0) {
            this.zzva.zza(-1, CastStatusCodes.INVALID_REQUEST, null);
            zzbm.zzbe.w("sendPlayerRequest for unsupported playerState: %d", Integer.valueOf(this.val$playerState));
            return;
        }
        this.zzut.zza(this.zzuv, i, this.zzuw, this.zzva);
    }
}
