package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult;
import com.google.android.gms.common.api.Status;
import org.json.JSONObject;

final class zzav implements MediaChannelResult {
    private final /* synthetic */ Status zzal;

    zzav(zzb com_google_android_gms_cast_framework_media_RemoteMediaClient_zzb, Status status) {
        this.zzal = status;
    }

    public final JSONObject getCustomData() {
        return null;
    }

    public final Status getStatus() {
        return this.zzal;
    }
}
