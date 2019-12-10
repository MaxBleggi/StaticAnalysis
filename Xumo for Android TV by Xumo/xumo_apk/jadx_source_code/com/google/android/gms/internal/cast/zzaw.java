package com.google.android.gms.internal.cast;

import android.view.View;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzaw extends UIController {
    private final View view;

    public zzaw(View view) {
        this.view = view;
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        zzcp();
    }

    public final void onSessionEnded() {
        this.view.setVisibility(8);
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        zzcp();
    }

    public final void onSendingRemoteMediaRequest() {
        this.view.setVisibility(0);
    }

    private final void zzcp() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null && remoteMediaClient.hasMediaSession()) {
            if (!remoteMediaClient.isBuffering()) {
                this.view.setVisibility(8);
                return;
            }
        }
        this.view.setVisibility(0);
    }
}
