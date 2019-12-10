package com.google.android.gms.cast.framework.media;

import android.util.Log;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.cast.zzdn;
import org.json.JSONObject;

final class zzaw implements zzdn {
    private final /* synthetic */ RemoteMediaClient zzpp;
    private final /* synthetic */ zzc zzpq;

    zzaw(zzc com_google_android_gms_cast_framework_media_RemoteMediaClient_zzc, RemoteMediaClient remoteMediaClient) {
        this.zzpq = com_google_android_gms_cast_framework_media_RemoteMediaClient_zzc;
        this.zzpp = remoteMediaClient;
    }

    public final void zzb(long j) {
        try {
            this.zzpq.setResult((MediaChannelResult) this.zzpq.createFailedResult(new Status(2103)));
        } catch (long j2) {
            Log.e("RemoteMediaClient", "Result already set when calling onRequestReplaced", j2);
        }
    }

    public final void zza(long j, int i, Object obj) {
        try {
            this.zzpq.setResult(new zzd(new Status(i), (obj instanceof JSONObject) != null ? (JSONObject) obj : null));
        } catch (long j2) {
            Log.e("RemoteMediaClient", "Result already set when calling onRequestCompleted", j2);
        }
    }
}
