package com.google.android.gms.cast;

import android.util.Log;
import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.cast.zzdn;
import org.json.JSONObject;

final class zzbr implements zzdn {
    private final /* synthetic */ zzb zzgs;

    zzbr(zzb com_google_android_gms_cast_RemoteMediaPlayer_zzb) {
        this.zzgs = com_google_android_gms_cast_RemoteMediaPlayer_zzb;
    }

    public final void zzb(long j) {
        try {
            this.zzgs.setResult((MediaChannelResult) this.zzgs.createFailedResult(new Status(2103)));
        } catch (long j2) {
            Log.e("RemoteMediaPlayer", "Result already set when calling onRequestReplaced", j2);
        }
    }

    public final void zza(long j, int i, Object obj) {
        try {
            this.zzgs.setResult(new zzc(new Status(i), (obj instanceof JSONObject) != null ? (JSONObject) obj : null));
        } catch (long j2) {
            Log.e("RemoteMediaPlayer", "Result already set when calling onRequestCompleted", j2);
        }
    }
}
