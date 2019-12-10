package com.google.android.gms.internal.cast;

import android.annotation.TargetApi;
import android.hardware.display.VirtualDisplay;
import com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplaySessionResult;
import com.google.android.gms.cast.CastRemoteDisplayApi;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;

@Deprecated
public final class zzds implements CastRemoteDisplayApi {
    private static final zzdh zzbe = new zzdh("CastRemoteDisplayApiImpl");
    private VirtualDisplay zzbf;
    private Api<?> zzze;
    private final zzei zzzf = new zzdt(this);

    public zzds(Api api) {
        this.zzze = api;
    }

    public final PendingResult<CastRemoteDisplaySessionResult> startRemoteDisplay(GoogleApiClient googleApiClient, String str) {
        zzbe.d("startRemoteDisplay", new Object[0]);
        return googleApiClient.execute(new zzdu(this, googleApiClient, str));
    }

    public final PendingResult<CastRemoteDisplaySessionResult> stopRemoteDisplay(GoogleApiClient googleApiClient) {
        zzbe.d("stopRemoteDisplay", new Object[0]);
        return googleApiClient.execute(new zzdv(this, googleApiClient));
    }

    @TargetApi(19)
    private final void zzb() {
        if (this.zzbf != null) {
            if (this.zzbf.getDisplay() != null) {
                zzdh com_google_android_gms_internal_cast_zzdh = zzbe;
                int displayId = this.zzbf.getDisplay().getDisplayId();
                StringBuilder stringBuilder = new StringBuilder(38);
                stringBuilder.append("releasing virtual display: ");
                stringBuilder.append(displayId);
                com_google_android_gms_internal_cast_zzdh.d(stringBuilder.toString(), new Object[0]);
            }
            this.zzbf.release();
            this.zzbf = null;
        }
    }
}
