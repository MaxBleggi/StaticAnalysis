package com.google.android.gms.internal.cast;

import android.view.View;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzbf extends UIController {
    private final View view;
    private final int zzsg;

    public zzbf(View view, int i) {
        this.view = view;
        this.zzsg = i;
        this.view.setEnabled(0);
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        zzcs();
    }

    public final void onSessionEnded() {
        this.view.setEnabled(false);
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        zzcs();
    }

    public final void onSendingRemoteMediaRequest() {
        this.view.setEnabled(false);
    }

    private final void zzcs() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null) {
            if (remoteMediaClient.hasMediaSession()) {
                Object obj;
                MediaStatus mediaStatus = remoteMediaClient.getMediaStatus();
                if (mediaStatus.getQueueRepeatMode() == 0) {
                    Integer indexById = mediaStatus.getIndexById(mediaStatus.getCurrentItemId());
                    if (indexById == null || indexById.intValue() >= mediaStatus.getQueueItemCount() - 1) {
                        obj = null;
                        if (obj != null || remoteMediaClient.isPlayingAd()) {
                            this.view.setVisibility(this.zzsg);
                            this.view.setEnabled(false);
                            return;
                        }
                        this.view.setVisibility(0);
                        this.view.setEnabled(true);
                        return;
                    }
                }
                obj = 1;
                if (obj != null) {
                }
                this.view.setVisibility(this.zzsg);
                this.view.setEnabled(false);
                return;
            }
        }
        this.view.setEnabled(false);
    }
}
