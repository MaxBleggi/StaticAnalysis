package com.google.android.gms.internal.cast;

import android.view.View;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzbe extends UIController {
    private final View view;

    public zzbe(View view) {
        this.view = view;
        this.view.setEnabled(false);
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        zzcp();
    }

    public final void onSessionEnded() {
        this.view.setEnabled(false);
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        zzcp();
    }

    public final void onSendingRemoteMediaRequest() {
        this.view.setEnabled(false);
    }

    private final void zzcp() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (!(remoteMediaClient == null || !remoteMediaClient.hasMediaSession() || remoteMediaClient.isLiveStream())) {
            if (!remoteMediaClient.isPlayingAd()) {
                this.view.setEnabled(true);
                return;
            }
        }
        this.view.setEnabled(false);
    }
}
