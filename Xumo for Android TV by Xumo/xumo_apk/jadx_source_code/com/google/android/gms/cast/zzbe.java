package com.google.android.gms.cast;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.cast.MediaLoadOptions.Builder;
import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.cast.zzco;
import org.json.JSONObject;

final class zzbe extends zzb {
    private final /* synthetic */ RemoteMediaPlayer zzfr;
    private final /* synthetic */ GoogleApiClient zzfs;
    private final /* synthetic */ long zzfx;
    private final /* synthetic */ JSONObject zzfy;
    private final /* synthetic */ boolean zzgf;
    private final /* synthetic */ long[] zzgg;
    private final /* synthetic */ MediaInfo zzgh;

    zzbe(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, boolean z, long j, long[] jArr, JSONObject jSONObject, MediaInfo mediaInfo) {
        this.zzfr = remoteMediaPlayer;
        this.zzfs = googleApiClient2;
        this.zzgf = z;
        this.zzfx = j;
        this.zzgg = jArr;
        this.zzfy = jSONObject;
        this.zzgh = mediaInfo;
        super(googleApiClient);
    }

    protected final void zza(zzco com_google_android_gms_internal_cast_zzco) {
        synchronized (this.zzfr.lock) {
            this.zzfr.zzfm.zza(this.zzfs);
            try {
                this.zzfr.zzfl.zza(this.zzgr, this.zzgh, new Builder().setAutoplay(this.zzgf).setPlayPosition(this.zzfx).setActiveTrackIds(this.zzgg).setCustomData(this.zzfy).build());
                this.zzfr.zzfm.zza(null);
            } catch (Throwable e) {
                try {
                    Log.e("RemoteMediaPlayer", "load - channel error", e);
                    setResult((MediaChannelResult) createFailedResult(new Status(2100)));
                } finally {
                    this.zzfr.zzfm.zza(null);
                }
            }
        }
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
        zza((zzco) anyClient);
    }
}
